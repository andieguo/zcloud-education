package com.education.experiment.cloudstorage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.education.experiment.commons.BaseDao;
import com.education.experiment.commons.HadoopConfiguration;
import com.education.experiment.commons.UserBean;

public class DeleteNoteServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Configuration conf = HadoopConfiguration.getConfiguration();

	/*
	 * 删除用户提交的文件名称
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 找到用户所选定的文件
		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		if (ub == null) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			String uuidname = new String(request.getParameter("filename").getBytes("ISO-8859-1"), "UTF-8");
			// 获取用户提交的文件名
			String dst = "/tomcat/users/" + ub.getUserId() + "/notes/" + uuidname + ".note";
			// 开始删除文件
			FileSystem fs = FileSystem.get(conf);
			Path hdfsPath = new Path(dst);
			if (!fs.exists(hdfsPath)) {
				request.getRequestDispatcher("/error.jsp?result=刪除资源不存在!").forward(request, response);
			} else {
				FileStatus stat = fs.getFileStatus(hdfsPath);
				ub.setCloudSize(ub.getCloudSize() + stat.getLen());
				fs.delete(hdfsPath, true);// 删除结束
				// 更新用户的sesion信息
				BaseDao.updateUserStatus(ub);
				if (ub.getUserId().equals("admin")) {
					response.sendRedirect("unlimit.jsp");
				} else {
					response.sendRedirect("limited.jsp");
				}
			}
		}
	}
}
