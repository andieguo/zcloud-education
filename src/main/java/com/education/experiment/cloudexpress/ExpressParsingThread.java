package com.education.experiment.cloudexpress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

public class ExpressParsingThread extends Thread {

	/**
	 * 此类是一个线程类，当服务端扫描到新的数据文件后，会启动一个改线程，用于向hadoop集群提交分析数据的job任务. hadoop集群会根据快递数据文件和快递人员的信息来启动Map/Reduce任务， 分配快递给最近的快递人员,最后把产生的结果信息写入到HDFS上.
	 */
	private static final Configuration conf = HadoopConfiguration.getConfiguration();
	private static boolean isLaunch = false;
	private static final Map<String, GPRSBean> map = new HashMap<String, GPRSBean>();
	private static final byte[] lock = new byte[0];
	private static final Map<String, ArrayList<String>> collect = new HashMap<String, ArrayList<String>>();
	private static FileSystem fs = null;
	private static Path lockFile = new Path("/tomcat/experiment/expresscloud/results/_lock");

	/**
	 * Map任务，负责从HDFS上读取快递的数据文件，读取到一行数据，然后遍历所有的快递人员信息， 把此快递分配给离这个快递最近的一个快递人员
	 */
	public static class ExpressMapper extends Mapper<LongWritable, Text, NullWritable, NullWritable> {
		public static enum Counters {
			ROWS
		}

		// 启动map任务之前的数据准备工作
		protected void setup(Context context) {
			try {
				synchronized (lock) {
					fs = FileSystem.get(context.getConfiguration());
					if (map.size() == 0) {
						Path path = new Path("/tomcat/experiment/expresscloud/courier/courier.txt");
						FSDataInputStream fsdis = fs.open(path);
						BufferedReader br = new BufferedReader(new InputStreamReader(fsdis, "UTF-8"));
						String line = null;
						while ((line = br.readLine()) != null) {
							String[] array = line.split("\t");
							try {
								if (array.length == 2) {
									GPRSBean gprsb = new GPRSBean();
									gprsb.setLongitude(Float.parseFloat((array[1].split(",")[0])));
									gprsb.setLatitude(Float.parseFloat((array[1].split(",")[1])));
									map.put(array[0], gprsb);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 分析快递信息，逐行读取数据文件，一行分析一次。
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String[] array = value.toString().split("\t");

			if (array.length == 4) {
				try {
					String[] ll = array[2].substring(array[2].indexOf("经纬度:") + "经纬度:".length()).split(",");
					float longitude = Float.parseFloat(ll[0]);
					float latitude = Float.parseFloat(ll[1]);
					String result = null;
					double distance = -1;
					for (Map.Entry<String, GPRSBean> me : map.entrySet()) {
						if (distance == -1) {
							distance = Math.sqrt(Math.pow((longitude - me.getValue().getLongitude()), 2) + Math.pow(latitude - me.getValue().getLatitude(), 2));
							result = me.getKey();
						} else if (Math.sqrt(Math.pow((longitude - me.getValue().getLongitude()), 2) + Math.pow(latitude - me.getValue().getLatitude(), 2)) < distance) {
							result = me.getKey();
						}
					}
					if (collect.containsKey(result)) {
						collect.get(result).add(array[1] + "\t" + array[3]);
					} else {
						collect.put(result, new ArrayList<String>());
						collect.get(result).add(array[1] + "\t" + array[3]);
					}
					context.getCounter(Counters.ROWS).increment(1);
					if (context.getCounter(Counters.ROWS).getValue() % 100 == 0) {
						// /tomcat/experiment/expresscloud/results
						synchronized (lock) {
							while (fs.exists(lockFile)) {
								Thread.sleep(1000);
							}
							FSDataOutputStream fsdos = fs.create(lockFile);
							fsdos.write("".getBytes());
							fsdos.close();
							for (Map.Entry<String, ArrayList<String>> me : collect.entrySet()) {
								Path user = new Path("/tomcat/experiment/expresscloud/results/" + me.getKey() + "/" + System.currentTimeMillis() + ".order");
								fsdos = fs.create(user, true);
								for (String ele : me.getValue()) {
									fsdos.write((ele + "\n").getBytes());
								}
								fsdos.close();
							}
							collect.clear();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new IOException(e);
				} finally {
					if (fs.exists(lockFile)) {
						fs.delete(lockFile, false);
					}
				}
			}
		}

		// 清理工作，主要是把此map任务产生的结果写入到HDFS上,在写入的过程中，会产生同步锁.
		protected void cleanup(Context context) {
			if (collect.size() > 0) {
				synchronized (lock) {
					try {
						while (fs.exists(lockFile)) {
							Thread.sleep(1000);
						}
						FSDataOutputStream fsdos = fs.create(lockFile);
						fsdos.write("".getBytes());
						fsdos.close();
						for (Map.Entry<String, ArrayList<String>> me : collect.entrySet()) {
							Path user = new Path("/tomcat/experiment/expresscloud/results/" + me.getKey() + "/" + System.currentTimeMillis() + ".order");
							fsdos = fs.create(user, true);
							for (String ele : me.getValue()) {
								fsdos.write((ele + "\n").getBytes());
							}
							fsdos.close();
						}
						collect.clear();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							if (fs.exists(lockFile)) {
								fs.delete(lockFile, false);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	// 线程的启动方法，该线程启动的时候首先调用此方法来启动所有的操作
	public void run() {
		isLaunch = true;
		while (isLaunch) {
			try {
				FileSystem fs = FileSystem.get(conf);
				Path courier = new Path("/tomcat/experiment/expresscloud/courier/courier.txt");
				if (fs.exists(courier)) {
					Path data = new Path("/tomcat/experiment/expresscloud/uploaddata");
					FileStatus[] dataes = fs.listStatus(data);
					if (dataes.length > 0) {
						Path out = new Path("/tomcat/experiment/expresscloud/mapreduce");
						if (fs.exists(out)) {
							fs.delete(out, true);
						}
						// 初始化job的相关信息
						conf.set("mapred.jar", Constants.JAR_HOME);
						Job job = new Job(conf, "Parsing Express Data");
						job.setJarByClass(ExpressParsingThread.class);
						FileInputFormat.setInputPaths(job, data);
						FileOutputFormat.setOutputPath(job, out);

						job.setMapperClass(ExpressMapper.class);
						// job.setReducerClass(ExpressReducer.class);

						job.setInputFormatClass(TextInputFormat.class);
						job.setOutputFormatClass(NullOutputFormat.class);
						job.setOutputKeyClass(NullWritable.class);
						job.setOutputValueClass(NullWritable.class);
						job.setNumReduceTasks(0);
						// 提交job到hadoop集群上，并让其开始执行此任务
						job.waitForCompletion(true);
						for (FileStatus ele : dataes) {
							fs.delete(ele.getPath(), true);
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(60 * 1000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void termination() {
		isLaunch = false;
	}
}
