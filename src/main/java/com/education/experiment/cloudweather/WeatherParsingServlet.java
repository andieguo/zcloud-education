package com.education.experiment.cloudweather;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

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

public class WeatherParsingServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Configuration conf = HadoopConfiguration.getConfiguration();

	public WeatherParsingServlet() {
		super();
	}

	/*
	 * Map任务，用于从HDFS上读取天气数据文件，然后分析这些数据文件，每一条数据代表一日的天气信息， 然后把这些数据文件传输给Reduce任务
	 */
	public static class MeteorologicalMapper extends Mapper<LongWritable, Text, Text, MeteorologicalBean> {
		public static enum Counters {
			ROWS
		}
		// 按月统计
		// 读取一行数据后，该方法就开始处理
		// 2011-01-01 Temp(max:33.106℃/min:17.822℃);Humidity(99.196%);WSP(28.319m/s)
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String[] array = value.toString().split(" ");
			if (array.length == 2) {
				String[] metes = array[1].split(";");
				if (metes.length == 3) {
					try {
						String[] temps = metes[0].substring(metes[0].indexOf("(") + 1, metes[0].indexOf(")")).split("/");
						MeteorologicalBean mb = new MeteorologicalBean();
						if (temps.length == 2) {
							mb.setMaxTemp(Float.parseFloat(temps[0].substring(temps[0].indexOf("max:") + "max:".length(), temps[0].indexOf("℃"))));
							mb.setMinTemp(Float.parseFloat(temps[1].substring(temps[1].indexOf("min:") + "min:".length(), temps[1].indexOf("℃"))));
						}
						String humidity = metes[1].substring(metes[1].indexOf("(") + 1, metes[1].indexOf(")"));
						mb.setHumidity(Float.parseFloat(humidity.substring(0, humidity.indexOf("%"))));
						String WSP = metes[2].substring(metes[2].indexOf("(") + 1, metes[2].indexOf(")"));
						mb.setWSP(Float.parseFloat(WSP.substring(0, WSP.indexOf("m/s"))));
						context.write(new Text(array[0].substring(0, array[0].lastIndexOf("-"))), mb);//[2011-01,mb]
						context.getCounter(Counters.ROWS).increment(1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/*
	 * Reduce任务类,该类用户处理Map任务提交过来的每日天气数据信息，然后把这些信息相加并平均，最后把结果写入HDFS文件当中去.
	 */
	public static class MeteorologicalReducer extends Reducer<Text, MeteorologicalBean, Text, Text> {
		public void reduce(Text key, Iterable<MeteorologicalBean> values, Context context) throws IOException, InterruptedException {
			int count = 0;
			float maxTempTotal = 0.0f;
			float minTempTotal = 0.0f;
			float humidityTotal = 0.0f;
			float WSPTotal = 0.0f;
			for (MeteorologicalBean mb : values) {
				count++;
				maxTempTotal += mb.getMaxTemp();
				minTempTotal += mb.getMinTemp();
				humidityTotal += mb.getHumidity();
				WSPTotal += mb.getWSP();
			}
			context.write(key, new Text("AVG{Temp(max:" + maxTempTotal / count + "℃/min:" + minTempTotal / count + "℃);Humidity(" + humidityTotal / count + "%);WSP(" + WSPTotal / count + "m/s)}"));
		}
	}

	/*
	 * 处理用户提交的天气数据云计算分析，用户提交了分析的命令以后，该方法会想hadoop集群提交一个Map/Reduce任务， 用于执行天气分析的任务
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		if (ub == null) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else if (ub.getUserId().equals("admin")) {
			FileSystem fs = FileSystem.get(conf);
			Path out = new Path("/tomcat/experiment/weathercloud/results");
			if (fs.exists(out)) {
				fs.delete(out, true);
			}
			// 开始生成处理天气数据的Map/Reduce任务job信息，然后提交给hadoop集群开始执行.
			String jarpath = System.getProperty("user.home") + File.separator + "temp"+File.separator+"education.jar";
			conf.set("mapred.jar", jarpath);
			Job job = new Job(conf, "Parsing Meteorological Data");
			job.setJarByClass(WeatherParsingServlet.class);
			Path in = new Path("/tomcat/experiment/weathercloud/uploaddata");
			FileInputFormat.setInputPaths(job, in);
			FileOutputFormat.setOutputPath(job, out);

			job.setMapperClass(MeteorologicalMapper.class);
			job.setReducerClass(MeteorologicalReducer.class);

			job.setInputFormatClass(TextInputFormat.class);
			job.setOutputFormatClass(TextOutputFormat.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(MeteorologicalBean.class);

			job.setNumReduceTasks(1);
			// 生成job信息完成
			try {
				// 提交job给hadoop集群，然后hadoop集群开始执行.
				job.submit();
				request.getRequestDispatcher("/launchweather.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.getRequestDispatcher("/error.jsp?result=任务作业提交失败,请查看集群是否正常运行.").forward(request, response);
			}
		}
	}
	
	public static void main(String[] args) {
		String[] array = "2011-01-01 Temp(max:33.106℃/min:17.822℃);Humidity(99.196%);WSP(28.319m/s)".split(" ");
		if (array.length == 2) {
			String[] metes = array[1].split(";");
			if (metes.length == 3) {
				try {
					String[] temps = metes[0].substring(metes[0].indexOf("(") + 1, metes[0].indexOf(")")).split("/");
					MeteorologicalBean mb = new MeteorologicalBean();
					if (temps.length == 2) {
						mb.setMaxTemp(Float.parseFloat(temps[0].substring(temps[0].indexOf("max:") + "max:".length(), temps[0].indexOf("℃"))));
						mb.setMinTemp(Float.parseFloat(temps[1].substring(temps[1].indexOf("min:") + "min:".length(), temps[1].indexOf("℃"))));
					}
					String humidity = metes[1].substring(metes[1].indexOf("(") + 1, metes[1].indexOf(")"));
					mb.setHumidity(Float.parseFloat(humidity.substring(0, humidity.indexOf("%"))));
					String WSP = metes[2].substring(metes[2].indexOf("(") + 1, metes[2].indexOf(")"));
					mb.setWSP(Float.parseFloat(WSP.substring(0, WSP.indexOf("m/s"))));
					System.out.println("key:"+array[0].substring(0, array[0].lastIndexOf("-")));//[2011-01,mb]
					System.out.println("value:"+mb.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
