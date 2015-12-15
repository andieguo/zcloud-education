package com.education.experiment.cloudwechat;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.education.experiment.commons.UserBean;

public class DeleteWeixinServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Configuration conf = new Configuration();

	/**
	 * 处理用户提交的删除微信数据的请求，服务会根据用户提交的数据文件名称，然后从HDFS上的指定目录下删除该文件
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		if (ub == null) {
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
		} else {
			// 获取到用户提交的文件名称
			String uuidname = new String(request.getParameter("filename")
					.getBytes("ISO-8859-1"), "UTF-8");
			String dst = "/tomcat/experiment/weixincloud/uploaddata/"
					+ uuidname;
			// 开始从HDFS上删除文件
			FileSystem fs = FileSystem.get(conf);
			Path hdfsPath = new Path(dst);
			if (!fs.exists(hdfsPath)) {
				request.getRequestDispatcher("/error.jsp?result=刪除资源不存在!")
						.forward(request, response);
			} else {
				fs.delete(hdfsPath, true);
				// 删除文件结束.
				if (ub.getUserId().equals("admin")) {
					response.sendRedirect("unlimit.jsp");
				} else {
					response.sendRedirect("limited.jsp");
				}
			}
		}
	}
}
