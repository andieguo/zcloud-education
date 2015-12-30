package com.education.experiment.cloudstorage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		String title = request.getParameter("noteTitle");
		String content = request.getParameter("noteCountent");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		System.out.println("content:" + content);
		System.out.println("noteName:" + title);
		FileSystem fs = FileSystem.get(conf);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (ub != null) {
			Path path = new Path("/tomcat/users/" + ub.getUserId() + "/notes/" + title + ".html");
			System.out.println("path:" + path);
			if (fs.exists(path)) {
				request.getRequestDispatcher("/error.jsp?result=文件已经存在!").forward(request, response);
			} else {
				// 开始往hdfs上上传文件
				FSDataOutputStream hdfsOut = fs.create(path);
				StringBuffer buffer = new StringBuffer("");
				buffer.append("<html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'><style type='text/css'>.details {overflow: scroll;overflow-x: auto; }.details h1 {display: inline-block;margin-top: 20px;padding: 10px 15px;border-left: 6px solid #58595B;font-size: 1.8em;font-weight: bold; }.details h1 span {margin-left: 20px;font-size: 0.6em;font-weight: 100; }</style>");
				buffer.append("<title>").append(title).append("</title></head><body><div id='header-shaw' class='details' style='padding-left: 30px;padding-right:30px;background-color: #fff;height: 542px;'>");
				buffer.append("<h1>").append(title).append("<span>发布时间：").append(simpleDateFormat.format(new Date())).append("</span>");
				buffer.append("<span>发布者：").append(ub.getUserName()).append("</span></h1>");
				buffer.append("<div>").append(content).append("</div></div></body></html>");
				hdfsOut.write(buffer.toString().getBytes(request.getCharacterEncoding()));
				hdfsOut.close();
				// 上传结束
				FileStatus stat = fs.getFileStatus(path);
				// 更新用户的sesion信息。
				ub.setCloudSize(ub.getCloudSize() - stat.getLen());
				BaseDao.updateUserStatus(ub);
				response.sendRedirect("listnote.jsp");
			}
		} else {
			response.sendRedirect("login.jsp");
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
