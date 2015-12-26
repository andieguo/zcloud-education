package com.education.experiment.cloudstorage;

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

import com.education.experiment.commons.HadoopConfiguration;
import com.education.experiment.commons.NoteBean;
import com.education.experiment.commons.UserBean;
import com.education.experiment.util.FileUtil;
import com.education.experiment.util.HtmlParseUtil;

public class DetailFileServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Configuration conf = HadoopConfiguration.getConfiguration();

	/*
	 * 处理用户下载的文件请求，用户提交一个文件名称，系统从HDFS读取该文件，然后传输给用户
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 找到用户所选定的文件
		request.setCharacterEncoding("utf-8");
		String command = request.getParameter("command");
		System.out.println("command:" + command);
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		if (ub == null) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			// 获取用户提交的文件名称
			String uuidname = new String(request.getParameter("filename").getBytes("ISO-8859-1"), "UTF-8");
			System.out.println("uuidname:" + uuidname);
			File temp = new File(System.getProperty("user.home") + File.separator + "temp");
			if (!temp.exists()) temp.mkdir();
			File f = new File(temp.getPath() + File.separator + uuidname);// 创建临时文件，读取HDFS上的文件存储在本地临时文件中，再文件f的内容返回给response
			String dst = "/tomcat/users/" + ub.getUserId() + "/"+command+ "/" + uuidname;
			// 开始从HDFS上读取文件
			FileSystem fs = FileSystem.get(conf);
			InputStream hadopin = null;
			OutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
			System.out.println("dst:" + dst);
			Path hdfsPath = new Path(dst);
			if (!fs.exists(hdfsPath)) {// 服务器端的内容不存在
				request.getRequestDispatcher("/error.jsp?result=下载资源不存在!").forward(request, response);
			} else {
				try {
					hadopin = fs.open(hdfsPath);
					IOUtils.copyBytes(hadopin, bos, 4096, true);
					// 读取文件结束,将文件f的内容返回给response
					if (f.exists()) {
						// 创建一 个输入流对象和指定的文件相关联
						FileInputStream input = new FileInputStream(f);
						if(command.equals("notes")){
							NoteBean noteBean = HtmlParseUtil.parse(input);
							request.setAttribute("noteBean", noteBean);
							request.getRequestDispatcher("/detailnote.jsp").forward(request, response);
						}else{
							String content = FileUtil.readInputStream(input);
							request.setAttribute("content", content);
							request.getRequestDispatcher("/detailfile.jsp").forward(request, response);
						}
					} else {
						request.getRequestDispatcher("/error.jsp?result=下载资源不存在!").forward(request, response);
					}
				} finally {
					IOUtils.closeStream(hadopin);
					bos.close();
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Configuration conf = HadoopConfiguration.getConfiguration();
		FileSystem fs = FileSystem.get(conf);
		String dst = "/tomcat/users/13888888888/files/file0.txt";
		Path hdfsPath = new Path(dst);
		System.out.println("status:" + fs.exists(hdfsPath));
	}
}
