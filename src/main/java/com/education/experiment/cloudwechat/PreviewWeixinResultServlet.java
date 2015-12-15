package com.education.experiment.cloudwechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

public class PreviewWeixinResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Configuration conf = HadoopConfiguration
			.getConfiguration();

	public PreviewWeixinResultServlet() {
	}

	/*
	 * 处理用户提交的浏览微信分析结果文件的请求，服务端接受到请求以后，从HDF S读取MapReduce产生的结果数据文件，然后把信息返回给客户端
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
			// 开始读取HDFS上的结果数据文件
			FileSystem fs = FileSystem.get(conf);
			Path _success = new Path(
					"/tomcat/experiment/weathercloud/results/_SUCCESS");
			if (!fs.exists(_success)) {
				request.setAttribute("result", null);
				request.getRequestDispatcher("/weixinresult.jsp").forward(
						request, response);
			} else {
				request.setAttribute("result", "");
				Path reduceRusult = new Path(
						"/tomcat/experiment/weixincloud/results/"
								+ ub.getUserId() + ".result");
				if (fs.exists(reduceRusult)) {
					FSDataInputStream fsdis = fs.open(reduceRusult);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(fsdis, "UTF-8"));
					String line = null;
					// {10094,10099} 2013-03-05 10:11:24 2013-03-05 12:42:40
					// 北京市西八家房
					// 　　“你自己说，你跟谁走？”南夜爵神色笃定，用了和上次相同的方法。
					List<WeixinResultBean> list = new ArrayList<WeixinResultBean>();
					// 读取一行数据文件，分析一行数据文件,并把结果放入到一个集合当中去
					while ((line = br.readLine()) != null) {
						String[] array = line.split("\t");
						if (array.length == 5) {
							WeixinResultBean wrb = new WeixinResultBean();
							wrb.setBegintime(array[1]);
							wrb.setEndtime(array[2]);
							wrb.setLinkcontent(array[4]);
							wrb.setLinkman(array[0]);
							wrb.setPlace(array[3]);
							list.add(wrb);
						}
					}
					// 读取结果文件完成，把读取到的结果返回给客户端
					request.setAttribute("list", list);
				} else {
					request.setAttribute("result", null);
				}
				request.getRequestDispatcher("/weixinresult.jsp").forward(
						request, response);
			}
			// 2012-12
			// AVG{Temp(max:21.760002℃/min:13.001612℃);Humidity(51.97549%);WSP(21.388714m/s)}
		}
	}
}
