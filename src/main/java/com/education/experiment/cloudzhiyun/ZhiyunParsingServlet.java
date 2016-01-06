package com.education.experiment.cloudzhiyun;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.education.experiment.cloudzhiyun.AvgPMReduce;
import com.education.experiment.cloudzhiyun.ClassifyPMMapper;
import com.education.experiment.cloudzhiyun.ClassifyPMReduce;
import com.education.experiment.cloudzhiyun.Main;
import com.education.experiment.cloudzhiyun.MinMaxPMMapper;
import com.education.experiment.cloudzhiyun.MinMaxPMReduce;
import com.education.experiment.commons.Constants;
import com.education.experiment.commons.HadoopConfiguration;
import com.education.experiment.commons.UserBean;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ZhiyunParsingServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Configuration conf = HadoopConfiguration.getConfiguration();
	
	/*
	 * 处理用户提交的天气数据云计算分析，用户提交了分析的命令以后，该方法会想hadoop集群提交一个Map/Reduce任务， 用于执行天气分析的任务
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		String type = (String) request.getParameter("type");
		String date = (String) request.getParameter("date");
		System.out.println("type:" + type);
		System.out.println("date:" + date);
		if (ub == null) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			FileSystem fs = FileSystem.get(conf);
			Path in = new Path("/tomcat/experiment/zhiyuncloud/uploaddata");
			Path out = new Path("/tomcat/experiment/zhiyuncloud/results");
			if (fs.exists(out)) {
				fs.delete(out, true);
			}
			String classpath = conf.get("mapred.job.classpath.archives");
			System.out.println("archives-classpath:"+classpath);
			conf.set("mapred.jar", Constants.JAR_HOME);
			conf.set("type", type);
			conf.set("date", date);
			// 开始生成处理天气数据的Map/Reduce任务job信息，然后提交给hadoop集群开始执行.
			Job job = new Job(conf, "MinPMByMonth");
			job.setJarByClass(Main.class);
			if (type.equals("min") || type.equals("max")) {
				job.setMapperClass(MinMaxPMMapper.class);
				job.setReducerClass(MinMaxPMReduce.class);
				job.setCombinerClass(MinMaxPMReduce.class);
				job.setOutputKeyClass(Text.class);
				job.setOutputValueClass(DoubleWritable.class);
			} else if (type.equals("avg")) {
				job.setMapperClass(MinMaxPMMapper.class);
				job.setReducerClass(AvgPMReduce.class);
				job.setOutputKeyClass(Text.class);
				job.setOutputValueClass(DoubleWritable.class);
			} else if (type.equals("classify")) {
				job.setMapperClass(ClassifyPMMapper.class);
				job.setReducerClass(ClassifyPMReduce.class);
				job.setOutputKeyClass(Text.class);
				job.setOutputValueClass(Text.class);
			}
			FileInputFormat.addInputPath(job, in);
			FileOutputFormat.setOutputPath(job, out);
			// 生成job信息完成
			try {
				// 提交job给hadoop集群，然后hadoop集群开始执行.
				job.submit();
				request.getRequestDispatcher("/mrlink.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.getRequestDispatcher("/error.jsp?result=任务作业提交失败,请查看集群是否正常运行.").forward(request, response);
			}
		}
	}

}
