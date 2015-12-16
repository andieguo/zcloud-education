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

public class DeleteParsingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Configuration conf = new Configuration();

	/**
	 * 处理用户提交的删除分析条件的请求,服务端会根据客户端提交的文件名称，到HDFS上的指定文件目录下删除该文件
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 找到用户所选定的文件
		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		if (ub == null) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			// 获取用户要删除的文件名称
			String userid = new String(request.getParameter("userid").getBytes("ISO-8859-1"), "UTF-8");
			String dst = "/tomcat/experiment/weixincloud/uploadparsing/" + userid + ".pars";
			// 开始删除文件，获取HDFS链接
			FileSystem fs = FileSystem.get(conf);
			Path hdfsPath = new Path(dst);
			if (!fs.exists(hdfsPath)) {
				request.getRequestDispatcher("/error.jsp?result=刪除资源不存在!").forward(request, response);
			} else {
				fs.delete(hdfsPath, true);
				// 删除文件结束
				if (ub.getUserId().equals("admin")) {
					response.sendRedirect("unlimit.jsp");
				} else {
					response.sendRedirect("limited.jsp");
				}
			}
		}
	}
}
