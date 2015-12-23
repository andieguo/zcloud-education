package com.education.experiment.cloudstorage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.education.experiment.commons.BaseDao;
import com.education.experiment.commons.HadoopConfiguration;
import com.education.experiment.commons.UserBean;

public class NoteCommitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Configuration conf = HadoopConfiguration.getConfiguration();

	public NoteCommitServlet() {
		super();
	}

	/*
	 * 处理用户提交的日记信息，把用户填写的日志信息写入hadoop的HDFS文件系统当中去，一个日记写入一个文件
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// 获取用户提交的信息
		String noteName = request.getParameter("noteTitle");
		String content = request.getParameter("noteCountent");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		System.out.println("content:" + content);
		System.out.println("noteName:" + noteName);
		FileSystem fs = FileSystem.get(conf);
		if (ub != null) {
			Path path = new Path("/tomcat/users/" + ub.getUserId() + "/notes/" + noteName + ".html");
			System.out.println("path:" + path);
			if (fs.exists(path)) {
				request.getRequestDispatcher("/error.jsp?result=文件已经存在!").forward(request, response);
			} else {
				// 开始往hdfs上上传文件
				FSDataOutputStream hdfsOut = fs.create(path);
				StringBuffer buffer = new StringBuffer("<html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'><title>");
				buffer.append(noteName).append("</title></head><body><h1>").append(noteName).append("</h1>").append(content).append("</body></html>");
				hdfsOut.write(buffer.toString().getBytes(request.getCharacterEncoding()));
				hdfsOut.close();
				// 上传结束
				FileStatus stat = fs.getFileStatus(path);
				// 更新用户的sesion信息。
				ub.setCloudSize(ub.getCloudSize() - stat.getLen());
				BaseDao.updateUserStatus(ub);
				if (ub.getUserId().equals("admin")) {
					response.sendRedirect("unlimit.jsp");
				} else {
					response.sendRedirect("limited.jsp");
				}
			}
		} else {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		Path path = new Path("/tomcat/users/admin/notes/hello.note");
		Configuration conf = HadoopConfiguration.getConfiguration();
		FileSystem hdfs = FileSystem.get(conf);
		FSDataOutputStream hdfsOut = hdfs.create(path);
		hdfsOut.write("hello,world".getBytes("UTF-8"));
		hdfsOut.close();
	}
}
