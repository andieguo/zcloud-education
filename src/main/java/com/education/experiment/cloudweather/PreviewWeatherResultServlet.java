package com.education.experiment.cloudweather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.education.experiment.commons.HadoopConfiguration;
import com.education.experiment.commons.UserBean;

public class PreviewWeatherResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Configuration conf = HadoopConfiguration
			.getConfiguration();

	public PreviewWeatherResultServlet() {
	}

	/*
	 * 处理用户提交的浏览云计算提天气数据的结果，服务端读取mapreduce计算的生成的结果文件，然后把信息返回给客户端
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// request.setCharacterEncoding(Charset.defaultCharset().toString());
		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		if (ub == null) {
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
		} else {
			// 开始读取结果文件
			FileSystem fs = FileSystem.get(conf);
			Path _success = new Path(
					"/tomcat/experiment/weathercloud/results/_SUCCESS");
			if (!fs.exists(_success)) {
				request.setAttribute("result", null);
				request.getRequestDispatcher("/weatherresult.jsp").forward(
						request, response);
			} else {
				request.setAttribute("result", "");
				Path reduceRusult = new Path(
						"/tomcat/experiment/weathercloud/results/part-r-00000");
				if (fs.exists(reduceRusult)) {
					FSDataInputStream fsdis = fs.open(reduceRusult);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(fsdis, "UTF-8"));
					String line = null;
					int count = 0;
					float maxTempTotal = 0.0f;
					float minTempTotal = 0.0f;
					float humidityTotal = 0.0f;
					float WSPTotal = 0.0f;
					// 2012-01
					// AVG{Temp(max:21.754515℃/min:12.678706℃);Humidity(46.5716%);WSP(19.337095m/s)}
					// 开始汇总读取到的每一行数据文件，主要总的操作是把所有结果信息汇总，然后返回给客户端.
					while ((line = br.readLine()) != null) {
						String[] array = line.split("\t");
						if (array.length == 2) {
							MonthBean monBean = new MonthBean();
							String value = array[1].substring(
									array[1].indexOf("{") + 1,
									array[1].indexOf("}"));
							String[] metes = value.split(";");
							if (metes.length == 3) {
								try {
									String[] temps = metes[0].substring(
											metes[0].indexOf("(") + 1,
											metes[0].indexOf(")")).split("/");
									if (temps.length == 2) {
										monBean.setMaxTemp(Float.parseFloat(temps[0].substring(
												temps[0].indexOf("max:")
														+ "max:".length(),
												temps[0].indexOf("℃"))));
										maxTempTotal += monBean.getMaxTemp();
										monBean.setMinTemp(Float.parseFloat(temps[1].substring(
												temps[1].indexOf("min:")
														+ "min:".length(),
												temps[1].indexOf("℃"))));
										minTempTotal += monBean.getMinTemp();
									}
									String humidity = metes[1].substring(
											metes[1].indexOf("(") + 1,
											metes[1].indexOf(")"));
									monBean.setHumidity(Float
											.parseFloat(humidity.substring(0,
													humidity.indexOf("%"))));
									humidityTotal += monBean.getHumidity();
									String WSP = metes[2].substring(
											metes[2].indexOf("(") + 1,
											metes[2].indexOf(")"));
									monBean.setWSP(Float.parseFloat(WSP
											.substring(0, WSP.indexOf("m/s"))));
									WSPTotal += monBean.getWSP();
									count++;
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							System.out.println(array[0].split("-")[1]);
							request.setAttribute(array[0].split("-")[1],
									monBean);
						}
					}
					// 读取结果文件完成
					// 2012-12
					// AVG{Temp(max:21.760002℃/min:13.001612℃);Humidity(51.97549%);WSP(21.388714m/s)}
					request.setAttribute("maxTemp", maxTempTotal / count);
					request.setAttribute("minTemp", minTempTotal / count);
					request.setAttribute("humidity", humidityTotal / count);
					request.setAttribute("WSP", WSPTotal / count);
					request.getRequestDispatcher("/weatherresult.jsp").forward(
							request, response);
				} else {
					request.setAttribute("result", null);
				}

			}
		}
	}
}
