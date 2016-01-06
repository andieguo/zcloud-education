package com.education.experiment.cloudwechat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.education.experiment.commons.Constants;
import com.education.experiment.commons.HadoopConfiguration;
import com.education.experiment.commons.UserBean;
import com.education.experiment.commons.WeiXinParsingUtil;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class WeixinParsingServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Configuration conf = HadoopConfiguration.getConfiguration();

	public WeixinParsingServlet() {
		super();
	}

	/*
	 * Map任务,主要执行的操作是从数据库中读取微信任务信息，然后从分析条件文件里读取信息，把这些分析数据准备信息先读取到内存当中, 然后开始逐行读取数据文件信息，并开始分析。
	 */
	public static class WeixinMapper extends Mapper<LongWritable, Text, Text, Text> {
		private static final byte[] lock = new byte[0];
		private Map<String, WeixinUserBean> weixinuserMap = new HashMap<String, WeixinUserBean>();
		private Map<String, WeixinParsingBean> parsingMap = new HashMap<String, WeixinParsingBean>();
		public static enum Counters {
			ROWS
		}

		// 每个数据块开始执行之前进行的设置信息，这里主要是从数据库中和分析条件文件中读取预处理的信息,为数据文件的分析做准备.
		public void setup(Context context) {
			synchronized (lock) {
				try {
					FileSystem fs = FileSystem.get(context.getConfiguration());
					weixinuserMap = WeiXinParsingUtil.genUserMap(fs);
					parsingMap = WeiXinParsingUtil.genParsingMap(fs);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// 数据文件里的每一个行数据都会经过该方法的处理,这里主要分析了每条数据是不是符合用户定义的分析条件,如果符合则把该数据发给reduce任务
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			// {10001,10084} \t 2013-02-16 19:22:46 \t 2013-02-16 21:32:45 \t
			// 北京市十六中学
			// 　\t 办好了？
			Map<String,Boolean> map = WeiXinParsingUtil.parsing(parsingMap,weixinuserMap,value.toString());
			for(String k : map.keySet()){
				if(map.get(k)){
					context.write(new Text(k), value);
				}
			}
			context.getCounter(Counters.ROWS).increment(1);
		}

		public void cleanup(Context context) {
			
		}
	}

	/*
	 * reduce任务，主要用来收集map任务过程中产生的符合条件的结果信息，然后对这些结果进行用户归并，并把这些结果信息写入到 // * hadoop的HDFS文件系统当中。
	 */
	public static class WeixinReducer extends Reducer<Text, Text, Text, Text> {
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			String content = "";
			// 遍历对结果进行归并
			for (Text value : values) {
				content += value.toString() + "\n";
			}
			context.write(key, new Text(content));
			// 把结果写入到HDFS中
			FileSystem hdfs = FileSystem.get(context.getConfiguration());
			Path path = new Path(Constants.HDFS_WEIXIN_RESULTS + key.toString() + ".result");
			if (hdfs.exists(path)) {
				hdfs.delete(path, true);
			}
			FSDataOutputStream hdfsOut = hdfs.create(path);
			hdfsOut.write(content.getBytes());
			hdfsOut.close();
			// 写入完成，并关闭相关的资源。
		}
	}

	/*
	 * 处理用户提交的分析微信数据的云计算请求，客户端提交任务后，服务端负责生成job信息，然后把这个job信息传递给hadoop集群， 让hadoop集群开始执行分析任务.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		if (ub == null) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			WeiXinParsingUtil.generateUserFile();//产生weixin_info.mysql文件
			FileSystem fs = FileSystem.get(conf);
			Path out = new Path(Constants.HDFS_WEIXIN_RESULTS);
			if (fs.exists(out)) {
				fs.delete(out, true);
			}
			conf.set("mapred.jar", Constants.JAR_HOME);
			// 开始根据配置文件生成job信息
			Job job = new Job(conf, "Parsing Weixin Data");
			job.setJarByClass(WeixinParsingServlet.class);
			Path in = new Path(Constants.HDFS_WEIXIN_UPLOADDATA);
			FileInputFormat.setInputPaths(job, in);
			FileOutputFormat.setOutputPath(job, out);

			job.setMapperClass(WeixinMapper.class);
			job.setReducerClass(WeixinReducer.class);

			job.setInputFormatClass(TextInputFormat.class);
			job.setOutputFormatClass(TextOutputFormat.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);

			job.setNumReduceTasks(1);
			// 生成job信息完成
			// 生成job信息完成
			try {
				// 提交job给hadoop集群，然后hadoop集群开始执行.
				job.submit();
				request.getRequestDispatcher("/launchweixin.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.getRequestDispatcher("/error.jsp?result=任务作业提交失败,请查看集群是否正常运行.").forward(request, response);
			}
		}
	}

	

	public static void parse() throws Exception {
		// 构造解析Map
		Map<String, WeixinParsingBean> parsingMap = new HashMap<String, WeixinParsingBean>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		WeixinParsingBean wpb = new WeixinParsingBean();
		wpb.setCommunicationPlace("北京市义和庄北口,北京市上寨".split(","));
		wpb.setDurationTime(1);
		wpb.setFriend("ALL");
		wpb.setGender("ALL");
		wpb.setKeywords("阿里巴巴".split(","));
		wpb.setMaxAge(100);
		wpb.setMinAge(0);
		wpb.setTimePoint(sdf.parse("2013-02-10 12:50:19").getTime());
		wpb.setVocations("餐饮,教育,金融,律师,娱乐,军人,体育,建筑,无业");
		parsingMap.put("admin", wpb);
		// 构造用户数据MAP
		FileSystem fs = FileSystem.get(HadoopConfiguration.getConfiguration());
		Map<String, WeixinUserBean> weixinuserMap = WeiXinParsingUtil.genUserMap(fs);
		String value = "{10002,10072}	2013-02-10 12:50:19	2013-02-10 12:55:46	北京市义和庄北口	　　【新版阿里巴巴和四十大盗】还记得阿里巴巴的故事吗？“匪徒在阿里巴巴家的门柱上画了记号。马尔基娜发现后，又在别家的门柱上也画了同样 的记号。”如今，阿里巴巴与新浪微博结盟。新浪知道你喜欢啥，然后在你的Cookie里留下点记号，阿里巴巴再一看，就把你想要的东东推荐给你......大数据很值钱哦！";
		Map<String,Boolean> results = WeiXinParsingUtil.parsing(parsingMap, weixinuserMap, value);
		for(String key : results.keySet()){
			if(results.get(key)){
				System.out.println(value);
			}
		}
	}

	public static void main(String[] args){
		try {
			parse();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
