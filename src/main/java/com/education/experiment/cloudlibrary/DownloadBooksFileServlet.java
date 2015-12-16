package com.education.experiment.cloudlibrary;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import com.education.experiment.commons.UserBean;

public class DownloadBooksFileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Configuration conf = new Configuration();

	/**
	 * 处理用户提交的下载课本的请求，服务端接受到该请求后，然后从HDFS上读取课本文件，先写入到tomcat的临时文件夹下，然后再回传给客户端.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		if (ub == null) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			// 获取客户端要下载的课本信息,根据课本信息从HDFS上读取指定的课本文件
			String name = new String(request.getParameter("name").getBytes("ISO-8859-1"), "UTF-8");
			String author = new String(request.getParameter("author").getBytes("ISO-8859-1"), "UTF-8");
			File f = new File("/hadoop/tomcat/temp/" + name);
			Path dst = new Path("/tomcat/experiment/librarycloud/books/" + author + "/" + name + ".book");
			// 开始从HDFS读取课本文件
			FileSystem fs = FileSystem.get(conf);
			if (!fs.exists(dst)) {
				request.getRequestDispatcher("/error.jsp?result=下载资源不存在!").forward(request, response);
			} else {
				InputStream hadopin = null;
				OutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
				try {
					hadopin = fs.open(dst);
					IOUtils.copyBytes(hadopin, bos, 4096, true);
				} finally {
					IOUtils.closeStream(hadopin);
					bos.close();
				}
				// 读取课本文件结束，此时课本文件被写入到tomcat的临时文件夹下
				String realname = name + ".book";
				// tomcat容器开始从临时文件夹下读取课本文件，然后通过文件流传输给客户端
				if (f.exists()) {
					// 设置应答的相应消息头
					response.setContentType("application/x-msdownload");
					String str = "attachment;filename=" + java.net.URLEncoder.encode(realname, "utf-8");
					response.setHeader("Content-Disposition", str);
					// 创建一 个输入流对象和指定的文件相关联
					FileInputStream in = new FileInputStream(f);
					// 从response对象中获取到输出流对象
					OutputStream out = response.getOutputStream();
					// 从输入流对象中读数据写入到输出流对象中
					byte[] buff = new byte[1024 * 1024];
					int len = 0;
					while ((len = in.read(buff)) > 0) {
						out.write(buff, 0, len);
					}
					f.delete();
					in.close();
					out.close();
				} else {
					request.getRequestDispatcher("/error.jsp?result=下载资源不存在!").forward(request, response);
				}
			}
		}
	}
}
