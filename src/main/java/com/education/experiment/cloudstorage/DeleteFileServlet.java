package com.education.experiment.cloudstorage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.education.experiment.commons.BaseDao;
import com.education.experiment.commons.UserBean;

public class DeleteFileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Configuration conf = new Configuration();

	/**
	 * 处理用户提交的删除文件的请求,用户提交一个文件名给该方法，然后系统从HDFS上删除该名称的文件
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 找到用户所选定的文件
		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		PrintWriter out = response.getWriter();
		if (ub == null) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			// 获取用户提交的文件名
			String uuidname = new String(request.getParameter("filename").getBytes("ISO-8859-1"), "UTF-8");
			String dst = "/tomcat/users/" + ub.getUserId() + "/files/" + uuidname;
			// 开始删除文件
			FileSystem fs = FileSystem.get(conf);
			Path hdfsPath = new Path(dst);
			if (!fs.exists(hdfsPath)) {
				request.getRequestDispatcher("/error.jsp?result=刪除资源不存在!").forward(request, response);
			} else {
				FileStatus stat = fs.getFileStatus(hdfsPath);
				ub.setCloudSize(ub.getCloudSize() + stat.getLen());
				boolean status = fs.delete(hdfsPath, true);
				if(status){
					// 删除文件结束
					// 更新用户的sesion信息
					int result = BaseDao.updateUserStatus(ub);
					if(result > 0){
						out.write("true");
					}else{
						out.write("false");
					}
				}else{
					out.write("false");
				}
			}
		}
		if(out != null) out.close();
	}
}
