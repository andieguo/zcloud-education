package com.education.experiment.cloudwechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.education.experiment.commons.DerbyUtilCase;
import com.education.experiment.commons.HadoopConfiguration;
import com.education.experiment.commons.UserBean;

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
	private static final Configuration conf = HadoopConfiguration
			.getConfiguration();

	public WeixinParsingServlet() {
		super();
	}

	/*
	 * Map任务,主要执行的操作是从数据库中读取微信任务信息，然后从分析条件文件里读取信息，把这些分析数据准备信息先读取到内存当中,
	 * 然后开始逐行读取数据文件信息，并开始分析。
	 */
	public static class WeixinMapper extends
			Mapper<LongWritable, Text, Text, Text> {
		private static final SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		private static final byte[] lock = new byte[0];
		private static Map<String, WeixinParsingBean> parsingMap = null;
		private static Map<String, WeixinUserBean> weixinuserMap = null;

		public static enum Counters {
			ROWS
		}

		// 每个数据块开始执行之前进行的设置信息，这里主要是从数据库中和分析条件文件中读取预处理的信息,为数据文件的分析做准备.
		public void setup(Context context) {
			synchronized (lock) {
				if (parsingMap == null || weixinuserMap == null) {
					parsingMap = new HashMap<String, WeixinParsingBean>();
					weixinuserMap = new HashMap<String, WeixinUserBean>();
					FileSystem fs;
					try {
						// 开始读取分析数据文件
						fs = FileSystem.get(context.getConfiguration());
						FileStatus[] uploadparsing = fs
								.listStatus(new Path(
										"/tomcat/experiment/weixincloud/uploadparsing"));
						for (FileStatus ele : uploadparsing) {
							FSDataInputStream fsdis = fs.open(ele.getPath());
							String fileName = ele.getPath().getName()
									.split("\\.")[0];
							BufferedReader br = new BufferedReader(
									new InputStreamReader(fsdis, "UTF-8"));
							String line = null;
							// timePoint:2013-03-05 13:57:40 durationTime:56
							// gender:男男
							// isFriend:否 ageSpan:22至22 vocation:null
							// communicationPlace:北京市海淀区，北京市朝阳区 keywords:我爱你
							try {
								while ((line = br.readLine()) != null) {
									WeixinParsingBean wpb = new WeixinParsingBean();
									String[] original = line.split("\t");
									if (original.length == 8) {
										if (!original[0].contains("null")) {
											wpb.setTimePoint(sdf
													.parse(original[0]
															.substring(original[0]
																	.indexOf(":") + 1))
													.getTime());
										}

										if (!original[1].split(":")[1]
												.equals("null")) {
											wpb.setDurationTime(Integer
													.parseInt(original[1]
															.split(":")[1]));
										}

										String gender = original[2].split(":")[1];
										if (!gender.equals("null")) {
											if (gender != null) {
												if (gender.equals("男男")) {
													wpb.setGender("11");
												} else if (gender.equals("女女")) {
													wpb.setGender("00");
												} else if (gender.equals("异性")) {
													wpb.setGender("01");
												} else {
													wpb.setGender("ALL");
												}
											}
										}

										if (!original[3].split(":")[1]
												.equals("null")) {
											if (original[3].split(":")[1]
													.equals("是")) {
												wpb.setFriend("true");
											} else if (original[3].split(":")[1]
													.equals("否")) {
												wpb.setFriend("false");
											} else {
												wpb.setFriend("ALL");
											}
										}

										String ageSpan = original[4].split(":")[1];
										if (!ageSpan.equals("null")) {
											wpb.setMinAge(Integer
													.parseInt(ageSpan
															.split("至")[0]));
											wpb.setMaxAge(Integer
													.parseInt(ageSpan
															.split("至")[1]));
										}

										if (!"null".equals(original[5]
												.split(":")[1])) {
											wpb.setVocations(original[5]
													.split(":")[1]);
										}
										if (!"null".equals(original[6]
												.split(":")[1])) {
											wpb.setCommunicationPlace(original[6]
													.split(":")[1].split(","));
										}
										if (!"null".equals(original[7]
												.split(":")[1])) {
											wpb.setKeywords(original[7]
													.split(":")[1].split(","));
										}
									}
									parsingMap.put(fileName, wpb);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							fsdis.close();
						}
						// 读取数据库的微信任务信息
						Path path = new Path(
								"/tomcat/experiment/weixincloud/tmp/DERBY.db");
						FSDataInputStream fsdis = fs.open(path);
						BufferedReader br = new BufferedReader(
								new InputStreamReader(fsdis, "UTF-8"));
						String line = null;
						while ((line = br.readLine()) != null) {
							String[] array = line.split("\t");
							try {
								if (array.length == 6) {
									WeixinUserBean wub = new WeixinUserBean();
									wub.setId(Integer.valueOf(array[0]));
									wub.setName(array[1]);
									wub.setAge(Integer.valueOf(array[2]));
									wub.setSex(array[3]);
									wub.setVocation(array[4]);
									wub.setFriends(array[5]);
									weixinuserMap.put(array[0], wub);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		// 数据文件里的每一个行数据都会经过该方法的处理,这里主要分析了每条数据是不是符合用户定义的分析条件,如果符合则把该数据发给reduce任务
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			// {10001,10084} \t 2013-02-16 19:22:46 \t 2013-02-16 21:32:45 \t
			// 北京市十六中学
			// 　\t 办好了？
			String[] array = value.toString().split("\t");
			if (array.length == 5) {
				try {
					// 开始对每行数据进行逐条分析，看是不是满足用户定义的条件信息
					long beginTime = sdf.parse(array[1]).getTime();
					long endTime = sdf.parse(array[2]).getTime();
					String[] ids = array[0].substring(
							array[0].indexOf("{") + 1, array[0].indexOf("}"))
							.split(",");
					if (ids.length == 2) {
						for (Map.Entry<String, WeixinParsingBean> me : parsingMap
								.entrySet()) {
							if (me.getValue().getTimePoint() != 0L) {
								if (beginTime < me.getValue().getTimePoint()) {
									continue;
								}
							}
							if (me.getValue().getDurationTime() != 0) {
								if (endTime - beginTime < 1000L * me.getValue()
										.getDurationTime() * 60) {
									continue;
								}
							}
							if (me.getValue().getCommunicationPlace() != null) {
								boolean pass = false;
								for (String ele : me.getValue()
										.getCommunicationPlace()) {
									if (ele.trim().equals(array[3].trim())) {
										pass = true;
										break;
									}
								}
								if (!pass) {
									continue;
								}
							}
							if (me.getValue().getKeywords() != null) {
								boolean nopass = false;
								for (String ele : me.getValue().getKeywords()) {
									if (!array[4].contains(ele)) {
										nopass = true;
										break;
									}
								}
								if (nopass) {
									continue;
								}
							}
							if (!(me.getValue().getGender().equals("ALL"))) {
								if (me.getValue().getGender().equals("00")) {
									if (!(weixinuserMap.get(ids[0]).getSex()
											.equals("女"))) {
										continue;
									}
									if (!(weixinuserMap.get(ids[1]).getSex()
											.equals("女"))) {
										continue;
									}
								} else if (me.getValue().getGender()
										.equals("11")) {
									if (!(weixinuserMap.get(ids[0]).getSex()
											.equals("男"))) {
										continue;
									}
									if (!(weixinuserMap.get(ids[1]).getSex()
											.equals("男"))) {
										continue;
									}
								} else if (me.getValue().getGender()
										.equals("01")) {
									if (weixinuserMap
											.get(ids[0])
											.getSex()
											.equals(weixinuserMap.get(ids[1])
													.getSex())) {
										continue;
									}
								}
							}
							if (!(me.getValue().getFriend().equals("ALL"))) {
								if (me.getValue().getFriend().equals("true")) {
									if (!(weixinuserMap.get(ids[0])
											.getFriends().contains(ids[1]))) {
										continue;
									}
									if (!(weixinuserMap.get(ids[1])
											.getFriends().contains(ids[0]))) {
										continue;
									}
								} else if (me.getValue().getFriend()
										.equals("false")) {
									if (weixinuserMap.get(ids[0]).getFriends()
											.contains(ids[1])
											&& weixinuserMap.get(ids[1])
													.getFriends()
													.contains(ids[0])) {
										continue;
									}
								}
							}
							if (me.getValue().getMinAge() > 0
									&& me.getValue().getMaxAge() > 0) {
								if (weixinuserMap.get(ids[0]).getAge() < me
										.getValue().getMinAge()
										|| weixinuserMap.get(ids[0]).getAge() > me
												.getValue().getMaxAge()) {
									continue;
								}
								if (weixinuserMap.get(ids[1]).getAge() < me
										.getValue().getMinAge()
										|| weixinuserMap.get(ids[1]).getAge() > me
												.getValue().getMaxAge()) {
									continue;
								}
							}
							if (me.getValue().getVocations() != null) {
								if (!(me.getValue().getVocations()
										.contains(weixinuserMap.get(ids[0])
												.getVocation()))) {
									continue;
								}
								if (!(me.getValue().getVocations()
										.contains(weixinuserMap.get(ids[1])
												.getVocation()))) {
									continue;
								}
							}
							context.write(new Text(me.getKey()), value);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				context.getCounter(Counters.ROWS).increment(1);
			}
		}

		public void cleanup(Context context) {
		}
	}

	/*
	 * reduce任务，主要用来收集map任务过程中产生的符合条件的结果信息，然后对这些结果进行用户归并，并把这些结果信息写入到 // *
	 * hadoop的HDFS文件系统当中。
	 */
	public static class WeixinReducer extends Reducer<Text, Text, Text, Text> {
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			String content = "";
			// 遍历对结果进行归并
			for (Text value : values) {
				content += value.toString() + "\n";
			}
			// context.write(new Text(title), new Text(content));
			// 把结果写入到HDFS中
			FileSystem hdfs = FileSystem.get(context.getConfiguration());
			Path path = new Path("/tomcat/experiment/weixincloud/results/"
					+ key.toString() + ".result");
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
	 * 处理用户提交的分析微信数据的云计算请求，客户端提交任务后，服务端负责生成job信息，然后把这个job信息传递给hadoop集群，
	 * 让hadoop集群开始执行分析任务.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		if (ub == null) {
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
		} else if (ub.getUserId().equals("admin")) {
			generateUserFile();
			FileSystem fs = FileSystem.get(conf);
			Path out = new Path("/tomcat/experiment/weixincloud/results");
			if (fs.exists(out)) {
				fs.delete(out, true);
			}
			// 开始根据配置文件生成job信息
			Job job = new Job(conf, "Parsing Weixin Data");
			job.setJarByClass(WeixinParsingServlet.class);
			Path in = new Path("/tomcat/experiment/weixincloud/uploaddata");
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
			try {
				// 提交job到hadoop集群，并让集群开始执行该job.
				job.submit();
				request.getRequestDispatcher("/mrlink.jsp").forward(request,
						response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.getRequestDispatcher(
						"/error.jsp?result=任务作业提交失败,请查看集群是否正常运行.").forward(
						request, response);
			}
		}
	}

	private static void generateUserFile() {
		try {
			Connection conn = DerbyUtilCase.getDerbyConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from WEIXIN_INFO");
			StringBuffer sb = new StringBuffer();
			while (rs.next()) {
				sb.append(rs.getInt(1) + "\t");
				sb.append(rs.getString(2) + "\t");
				sb.append(rs.getInt(3) + "\t");
				if (rs.getInt(4) == 1) {
					sb.append("男" + "\t");
				} else {
					sb.append("女" + "\t");
				}
				sb.append(rs.getString(5) + "\t");
				sb.append(rs.getString(6) + "\t");
				sb.append("\n");
			}
			st.close();
			rs.close();
			conn.close();
			FileSystem hdfs = FileSystem.get(conf);
			Path path = new Path("/tomcat/experiment/weixincloud/tmp/DERBY.db");
			if (hdfs.exists(path)) {
				hdfs.delete(path, true);
			}
			FSDataOutputStream hdfsOut = hdfs.create(path);
			hdfsOut.write(sb.toString().getBytes());
			hdfsOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws ParseException {
		Map<String, WeixinParsingBean> parsingMap = new HashMap<String, WeixinParsingBean>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		WeixinParsingBean wpb = new WeixinParsingBean();
		wpb.setCommunicationPlace("北京市建欣园,北京市上寨".split(","));
		wpb.setDurationTime(80);
		wpb.setFriend("true");
		wpb.setGender("11");
		wpb.setKeywords("好像,男人".split(","));
		wpb.setMaxAge(30);
		wpb.setMinAge(15);
		wpb.setTimePoint(sdf.parse("2013-03-01 11:18:35").getTime());
		wpb.setVocations("无业,军人,体育");
		parsingMap.put("xiaoshang", wpb);
		Map<String, WeixinUserBean> weixinuserMap = new HashMap<String, WeixinUserBean>();
		WeixinUserBean wub = new WeixinUserBean();
		wub.setAge(25);
		wub.setFriends("10003,10071");
		wub.setId(10001);
		wub.setName("张三");
		wub.setSex("男");
		wub.setVocation("无业");
		weixinuserMap.put("10001", wub);
		wub = new WeixinUserBean();
		wub.setAge(25);
		wub.setFriends("10003,10001");
		wub.setId(10071);
		wub.setName("李");
		wub.setSex("男");
		wub.setVocation("无业");
		weixinuserMap.put("10071", wub);
		String value = "{10001,10071}	2013-03-01 11:18:35	2013-03-01 12:46:20	北京市建欣园	　　“你好像不止一次这么问了吧？”男人绕起她一束长发，“既然心有怀疑，就该求证才是，他为什么会变成这样，你不想知道吗？";
		String[] array = value.toString().split("\t");
		if (array.length == 5) {
			try {
				long beginTime = sdf.parse(array[1]).getTime();
				long endTime = sdf.parse(array[2]).getTime();
				String[] ids = array[0].substring(array[0].indexOf("{") + 1,
						array[0].indexOf("}")).split(",");
				for (Map.Entry<String, WeixinParsingBean> me : parsingMap
						.entrySet()) {
					if (me.getValue().getTimePoint() != 0L) {
						if (beginTime < me.getValue().getTimePoint()) {
							continue;
						}
					}
					if (me.getValue().getDurationTime() != 0) {
						if (endTime - beginTime < 1000L * me.getValue()
								.getDurationTime() * 60) {
							continue;
						}
					}
					if (me.getValue().getCommunicationPlace() != null) {
						boolean pass = false;
						for (String ele : me.getValue().getCommunicationPlace()) {
							if (ele.trim().equals(array[3].trim())) {
								pass = true;
								break;
							}
						}
						if (!pass) {
							continue;
						}
					}
					if (me.getValue().getKeywords() != null) {
						boolean nopass = false;
						for (String ele : me.getValue().getKeywords()) {
							if (!array[4].contains(ele)) {
								nopass = true;
								break;
							}
						}
						if (nopass) {
							continue;
						}
					}
					if (ids.length != 2) {
						continue;
					} else {
						if (!(me.getValue().getGender().equals("ALL"))) {
							if (me.getValue().getGender().equals("00")) {
								if (!(weixinuserMap.get(ids[0]).getSex()
										.equals("女"))) {
									continue;
								}
								if (!(weixinuserMap.get(ids[1]).getSex()
										.equals("女"))) {
									continue;
								}
							} else if (me.getValue().getGender().equals("11")) {
								if (!(weixinuserMap.get(ids[0]).getSex()
										.equals("男"))) {
									continue;
								}
								if (!(weixinuserMap.get(ids[1]).getSex()
										.equals("男"))) {
									continue;
								}
							} else if (me.getValue().getGender().equals("01")) {
								if (weixinuserMap
										.get(ids[0])
										.getSex()
										.equals(weixinuserMap.get(ids[1])
												.getSex())) {
									continue;
								}
							}
						}
						if (!(me.getValue().getFriend().equals("ALL"))) {
							if (me.getValue().getFriend().equals("true")) {
								if (!(weixinuserMap.get(ids[0]).getFriends()
										.contains(ids[1]))) {
									continue;
								}
								if (!(weixinuserMap.get(ids[1]).getFriends()
										.contains(ids[0]))) {
									continue;
								}
							} else if (me.getValue().getFriend()
									.equals("false")) {
								if (weixinuserMap.get(ids[0]).getFriends()
										.contains(ids[1])
										&& weixinuserMap.get(ids[1])
												.getFriends().contains(ids[0])) {
									continue;
								}
							}
						}
						if (me.getValue().getMinAge() > 0
								&& me.getValue().getMaxAge() > 0) {
							if (weixinuserMap.get(ids[0]).getAge() < me
									.getValue().getMinAge()
									|| weixinuserMap.get(ids[0]).getAge() > me
											.getValue().getMaxAge()) {
								continue;
							}
							if (weixinuserMap.get(ids[1]).getAge() < me
									.getValue().getMinAge()
									|| weixinuserMap.get(ids[1]).getAge() > me
											.getValue().getMaxAge()) {
								continue;
							}
						}
						if (me.getValue().getVocations() != null) {
							if (!(me.getValue().getVocations()
									.contains(weixinuserMap.get(ids[0])
											.getVocation()))) {
								continue;
							}
							if (!(me.getValue().getVocations()
									.contains(weixinuserMap.get(ids[1])
											.getVocation()))) {
								continue;
							}
						}
					}
					System.out.println(me.getKey() + "\t" + value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
