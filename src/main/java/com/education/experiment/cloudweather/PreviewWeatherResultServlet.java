package com.education.experiment.cloudweather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.json.JSONObject;

import com.education.experiment.commons.HadoopConfiguration;
import com.education.experiment.commons.UserBean;

public class PreviewWeatherResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Configuration conf = HadoopConfiguration.getConfiguration();

	public PreviewWeatherResultServlet() {
	}

	/*
	 * 处理用户提交的浏览云计算提天气数据的结果，服务端读取mapreduce计算的生成的结果文件，然后把信息返回给客户端
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request.setCharacterEncoding(Charset.defaultCharset().toString());
		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		Map<Long, MonthBean> maps = new TreeMap<Long, MonthBean>();
		JSONObject resultJSON = new JSONObject();
		JSONArray mintempSeries = new JSONArray();
		JSONArray humiditySeries = new JSONArray();
		JSONArray maxtempSeries = new JSONArray();
		JSONArray wspSeries = new JSONArray();
		if (ub != null) {
			// 开始读取结果文件
			FileSystem fs = FileSystem.get(conf);
			Path _success = new Path("/tomcat/experiment/weathercloud/results/_SUCCESS");
			Path reduceRusult = new Path("/tomcat/experiment/weathercloud/results/part-r-00000");
			if (fs.exists(_success) && fs.exists(reduceRusult)) {
				FSDataInputStream fsdis = fs.open(reduceRusult);
				BufferedReader br = new BufferedReader(new InputStreamReader(fsdis, "UTF-8"));
				String line = null;
				int count = 0;
				float maxTempTotal = 0.0f, minTempTotal = 0.0f, humidityTotal = 0.0f, WSPTotal = 0.0f;
				// 2012-01
				// AVG{Temp(max:21.754515℃/min:12.678706℃);Humidity(46.5716%);WSP(19.337095m/s)}
				// 开始汇总读取到的每一行数据文件，主要总的操作是把所有结果信息汇总，然后返回给客户端.
				while ((line = br.readLine()) != null) {
					String[] array = line.split("\t");
					if (array.length == 2) {
						MonthBean monBean = new MonthBean();
						String value = array[1].substring(array[1].indexOf("{") + 1, array[1].indexOf("}"));
						String[] metes = value.split(";");
						if (metes.length == 3) {
							String[] temps = metes[0].substring(metes[0].indexOf("(") + 1, metes[0].indexOf(")"))
									.split("/");
							if (temps.length == 2) {
								double maxtemp = Double.parseDouble(temps[0]
										.substring(temps[0].indexOf("max:") + "max:".length(), temps[0].indexOf("℃")));
								monBean.setMaxTemp(decimal(maxtemp));
								maxTempTotal += monBean.getMaxTemp();
								double mintemp = Double.parseDouble(temps[1]
										.substring(temps[1].indexOf("min:") + "min:".length(), temps[1].indexOf("℃")));
								monBean.setMinTemp(decimal(mintemp));
								minTempTotal += monBean.getMinTemp();
							}
							String humidity = metes[1].substring(metes[1].indexOf("(") + 1, metes[1].indexOf(")"));
							monBean.setHumidity(
									decimal(Double.parseDouble(humidity.substring(0, humidity.indexOf("%")))));
							humidityTotal += monBean.getHumidity();
							String WSP = metes[2].substring(metes[2].indexOf("(") + 1, metes[2].indexOf(")"));
							monBean.setWSP(decimal(Double.parseDouble(WSP.substring(0, WSP.indexOf("m/s")))));
							WSPTotal += monBean.getWSP();
							count++;
						}
						Calendar calendar = Calendar.getInstance(java.util.Locale.CHINA);
						String[] d = array[0].split("-");
						calendar.set(Integer.valueOf(d[0]), Integer.valueOf(d[1]) - 1, 1);
						maps.put(calendar.getTimeInMillis(), monBean);
					}
				}
				try {
					for (Long key : maps.keySet()) {
						JSONArray mintemp = new JSONArray();
						JSONArray maxtemp = new JSONArray();
						JSONArray wsp = new JSONArray();
						JSONArray humidity = new JSONArray();
						MonthBean monBean = maps.get(key);
						mintemp.put(key);
						mintemp.put(monBean.getMinTemp());
						maxtemp.put(key);
						maxtemp.put(monBean.getMaxTemp());
						humidity.put(key);
						humidity.put(monBean.getHumidity());
						wsp.put(key);
						wsp.put(monBean.getWSP());
						mintempSeries.put(mintemp);
						maxtempSeries.put(maxtemp);
						humiditySeries.put(humidity);
						wspSeries.put(wsp);
					}
					resultJSON.put("mintempSeries", mintempSeries);
					resultJSON.put("maxtempSeries", maxtempSeries);
					resultJSON.put("wspSeries", wspSeries);
					resultJSON.put("humiditySeries", humiditySeries);
					resultJSON.put("maxTemp", decimal(maxTempTotal / count));
					resultJSON.put("minTemp", decimal(minTempTotal / count));
					resultJSON.put("humidity", decimal(humidityTotal / count));
					resultJSON.put("WSP", decimal(WSPTotal / count));
					// 返回JSON格式数据
					response.setCharacterEncoding("utf-8");
					response.setContentType("application/x-json");
					PrintWriter writer = response.getWriter();
					writer.write(resultJSON.toString());
					writer.flush();
					writer.close();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {// 结果页面不存在
				request.getRequestDispatcher("/error.jsp?result=天气云计算结果不存在!").forward(request, response);
			}
		} else {// 用户未登录
			response.sendRedirect("login.jsp");
		}
	}

	public static double decimal(double num) {
		return (double) (Math.round(num * 100)) / 100;
	}

	public static void main(String[] args) {
		JSONObject jsonObject = new JSONObject();
		double num = 26.149999618530273;
		jsonObject.put("key", decimal(num));
		System.out.println(jsonObject.get("key"));
	}
}
