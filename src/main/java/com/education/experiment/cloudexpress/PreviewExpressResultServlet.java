package com.education.experiment.cloudexpress;

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
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.education.experiment.commons.HadoopConfiguration;
import com.education.experiment.commons.UserBean;

public class PreviewExpressResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Configuration conf = HadoopConfiguration.getConfiguration();

	public PreviewExpressResultServlet() {
	}

	/**
	 * 处理用户提交的查看自己的快递信息的请求，服务端接受到该请求后，会根据用户的ID信息去hdfs上读取相应的文件， 最后把文件的内容返回给客户端.
	 * */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request.setCharacterEncoding(Charset.defaultCharset().toString());
		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		if (ub == null) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			// 开始读取属于客户的快递信息文件
			FileSystem fs = FileSystem.get(conf);
			Path path = new Path("/tomcat/experiment/expresscloud/results/" + ub.getUserId());
			if (!fs.exists(path)) {
				request.setAttribute("result", null);
				request.getRequestDispatcher("/expressresult.jsp").forward(request, response);
			} else {
				FileStatus[] files = fs.listStatus(path);
				if (files.length > 0) {
					String sign = request.getParameter("sign");
					if (sign.equals("1")) {
						for (FileStatus file : files) {
							fs.delete(file.getPath(), false);
						}
						if (ub.getUserId().equals("admin")) {
							request.getRequestDispatcher("/unlimit.jsp?result=提取成功!").forward(request, response);
						} else {
							request.getRequestDispatcher("/limited.jsp?result=提取成功!").forward(request, response);
						}
					} else {
						List<String> list = new ArrayList<String>();
						for (FileStatus file : files) {
							request.setAttribute("result", "");
							FSDataInputStream fsdis = fs.open(file.getPath());
							BufferedReader br = new BufferedReader(new InputStreamReader(fsdis, "UTF-8"));
							String line = null;
							while ((line = br.readLine()) != null) {
								list.add(line);
							}
							br.close();
							fsdis.close();
						}
						// 读取快递文件结束，然后把快递信息封装到一个对象里返回给客户端
						request.setAttribute("list", list);
						request.getRequestDispatcher("/expressresult.jsp").forward(request, response);
					}
				} else {
					request.setAttribute("result", null);
					request.getRequestDispatcher("/expressresult.jsp").forward(request, response);
				}
			}
			// 2012-12
			// AVG{Temp(max:21.760002℃/min:13.001612℃);Humidity(51.97549%);WSP(21.388714m/s)}

		}
	}
}
