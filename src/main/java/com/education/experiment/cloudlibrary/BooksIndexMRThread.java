package com.education.experiment.cloudlibrary;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.education.experiment.commons.Constants;
import com.education.experiment.commons.HadoopConfiguration;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class BooksIndexMRThread extends Thread {

	/**
	 * 该类为一个线程类，每一个用户提交一个课本文件后，系统会为其分配一个线程；该线程执行的主要任务是向Hadoop集群提交一个建立索引文件的任务， hadoop接受到该任务时会开始对文件进行分词，把分词结果写入到索引文件里
	 */
	private static final byte[] lock = new byte[0];
	private static final Configuration conf = HadoopConfiguration.getConfiguration();
	private Book book = null;

	public BooksIndexMRThread(Book book) {
		this.book = book;
	}

	// 线程启动方法,当线程开始执行时，首先启动该方法
	public void run() {
		try {
			index(book);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static class BooksMapper extends Mapper<LongWritable, Text, NullWritable, NullWritable> {
		private static final Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);
		private IndexWriterConfig iwc = null;
		private RAMDirectory ramDir = null;
		private IndexWriter writer = null;
		private StringBuffer sb = null;

		public static enum Counters {
			ROWS
		}

		// 每一个map任务执行开始前的准备工作,这里主要是生成了建立索引的对象,并对其进行初始化配置
		protected void setup(Context context) {
			sb = new StringBuffer();
			iwc = new IndexWriterConfig(Version.LUCENE_42, analyzer);
			iwc.setOpenMode(OpenMode.CREATE);
			iwc.setRAMBufferSizeMB(128.0);
			iwc.setMaxBufferedDocs(1000);
			ramDir = new RAMDirectory();//RAMDirectory将索引保存到内存中
			try {
				writer = new IndexWriter(ramDir, iwc);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 对map分配到的任务文件进行读取操作，每读取一个都要进行分词，然后把分词结果写入到内存当中，当内存满足到一定程度，刷新到磁盘上
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			sb.append(value.toString());
			context.getCounter(Counters.ROWS).increment(1);
			if (context.getCounter(Counters.ROWS).getValue() % 100 == 0) {
				if (sb.toString() != null && !"".equals(sb.toString())) {
					Document doc = new Document();
					doc.add(new TextField("name", context.getConfiguration().get("bookname"), Store.YES));
					doc.add(new TextField("author", context.getConfiguration().get("bookauthor"), Store.YES));
					doc.add(new TextField("publishdate", context.getConfiguration().get("publishdate"), Store.YES));
					doc.add(new TextField("sections", sb.toString(), Store.YES));
					writer.addDocument(doc);
					sb = new StringBuffer();
				}
			}
		}

		// 每一个任务执行完成后的清理工作
		// map任务完成的索引数据块会以文件夹的目录存在于本地磁盘上，在map任务结束时会把这些索引数据块写回到HDFS上，最后让Master服务器来进行最后的合并工作
		protected void cleanup(Context context) {
			try {
				if (sb.toString() != null) {
					Document doc = new Document();
					doc.add(new TextField("name", context.getConfiguration().get("bookname"), Store.YES));
					doc.add(new TextField("author", context.getConfiguration().get("bookauthor"), Store.YES));
					doc.add(new TextField("publishdate", context.getConfiguration().get("publishdate"), Store.YES));
					doc.add(new TextField("sections", sb.toString(), Store.YES));
					writer.addDocument(doc);
				}
				writer.close();
			} catch (CorruptIndexException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			long timeMillis = System.currentTimeMillis();
			// 把内存中的索引库写到文件系统中
			String localIndexDir = Constants.PROJECTPATH + File.separator + timeMillis;
			File indexDir = new File(localIndexDir);
			IndexWriterConfig fsIndexWriterConfig = new IndexWriterConfig(Version.LUCENE_42, analyzer);
			fsIndexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			fsIndexWriterConfig.setRAMBufferSizeMB(128.0);
			fsIndexWriterConfig.setMaxBufferedDocs(1000);
			Directory directory;
			try {
				directory = FSDirectory.open(indexDir);//FSDirectory将索引保存在本地
				IndexWriter fsIndexWriter = new IndexWriter(directory, fsIndexWriterConfig);
				fsIndexWriter.addIndexes(ramDir);
				fsIndexWriter.close();
				// 开始读取本地的磁盘索引文件目录，然后通过文件流的方式把这些文件回写到HDFS上。
				FileSystem hdfs = FileSystem.get(context.getConfiguration());
				FileSystem local = FileSystem.getLocal(context.getConfiguration());
				Path inPath = new Path(localIndexDir);
				String hdfsPath = Constants.HDFS_BOOK_INDEXES + timeMillis + "-lock" + "/";
				FileStatus[] inputFiles = local.listStatus(inPath);
				for (FileStatus ele : inputFiles) {
					FSDataOutputStream fsdos = hdfs.create(new Path(hdfsPath + ele.getPath().getName()));
					FSDataInputStream fsdis = local.open(ele.getPath());
					byte[] buffer = new byte[256];
					int readByte = 0;
					while ((readByte = fsdis.read(buffer)) > 0) {
						fsdos.write(buffer, 0, readByte);
					}
					fsdis.close();
					fsdos.close();
				}
				local.delete(inPath, true);
				hdfs.rename(new Path(hdfsPath), new Path(Constants.HDFS_BOOK_INDEXES + timeMillis + "/"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 配置job相关信息，然后提交给Hadoop集群，让其开始执行该job
	private void index(Book book) throws IOException, InterruptedException, ClassNotFoundException {
		conf.set("bookname", book.getName());
		conf.set("bookauthor", book.getAuthor());
		conf.set("publishdate", book.getPublishDate());
		conf.set("mapred.jar", Constants.JAR_HOME);
		// 开始配置job信息
		Job job = new Job(conf, "Indexing Book Data");
		job.setJarByClass(BooksIndexMRThread.class);
		String perfix = Constants.HDFS_BOOK_UPLOADDATA + book.getAuthor() + "-" + book.getName();
		Path in = new Path(perfix + ".book");
		Path out = new Path(perfix + "-result");
		FileInputFormat.setInputPaths(job, in);//输入
		FileOutputFormat.setOutputPath(job, out);//输出

		job.setMapperClass(BooksMapper.class);
		// job.setReducerClass(null);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(NullOutputFormat.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(NullWritable.class);

		job.setNumReduceTasks(0);// 没有reduce任务
		// 配置job信息完成
		// 提交job到hadoop集群
		job.waitForCompletion(true);
		// master服务端开始从HDFS读取每一个map任务产生的数据块，然后往本地的索引目录下合并;
		// 由于是多线程的操作，在合并的同时可能会多个线程对本地目录进行操作，以免文件产生错误，
		// 对本地的文件目录加锁，在每一个时刻只允许一个线程对其进行索引文件写入
		synchronized (lock) {
			FileSystem hdfs = FileSystem.get(conf);
			FileSystem local = FileSystem.getLocal(conf);
			Path indexes = new Path(Constants.HDFS_BOOK_INDEXES);
			FileStatus[] list = hdfs.listStatus(indexes);
			for (FileStatus fs : list) {
				if (fs.isDir() && !fs.getPath().getName().contains("lock")) {
					FileStatus[] indexList = hdfs.listStatus(fs.getPath());
					for (FileStatus ele : indexList) {
						FSDataInputStream fsdis = hdfs.open(ele.getPath());
						FSDataOutputStream fsdos = local.create(new Path(Constants.LOCAL_BOOK_TMP + File.separator + fs.getPath().getName() + File.separator + ele.getPath().getName()));
						byte[] buffer = new byte[256];
						int readByte = 0;
						while ((readByte = fsdis.read(buffer)) > 0) {
							fsdos.write(buffer, 0, readByte);
						}
						fsdis.close();
						fsdos.close();
					}
					hdfs.delete(fs.getPath(), true);
				}
			}
			//合并之后的本地临时索引库
			File indexTmpDir = new File(Constants.LOCAL_BOOK_TMP);
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);
			IndexWriterConfig fsIndexWriterConfig = new IndexWriterConfig(Version.LUCENE_42, analyzer);
			fsIndexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			fsIndexWriterConfig.setRAMBufferSizeMB(128.0);
			fsIndexWriterConfig.setMaxBufferedDocs(1000);
			//本地正式的索引库
			Directory directory = FSDirectory.open(new File(Constants.LOCAL_BOOK_IDNEX));
			File[] arrayFile = indexTmpDir.listFiles();
			for (File file : arrayFile) {
				if (file.isDirectory()) {
					IndexWriter fsIndexWriter = new IndexWriter(directory, fsIndexWriterConfig);
					// 把本地临时indexes索引库添加到本地正式的indexes索引库中
					Directory tmp = FSDirectory.open(file);
					fsIndexWriter.addIndexes(tmp);
					fsIndexWriter.close();
					tmp.close();
					deleteDir(file);
				}
			}
		}
	}

	// 删除临时的索引文件目录
	private void deleteDir(File dir) {
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			for (File ele : children) {
				deleteDir(ele);
			}
		}
		dir.delete();
	}
}
