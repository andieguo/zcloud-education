package com.education.experiment.cloudwechat;

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

import com.education.experiment.commons.Constants;
import com.education.experiment.commons.UserBean;

public class DownloadWeixinServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Configuration conf = new Configuration();

	/*
	 * 处理用户提交的下载微信示例数据文件请求，服务端会从指定目录里下载该文件到tomcat的临时文件夹下，然后再传输给客户端该文件.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 找到用户所选定的文件
		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		if (ub == null) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			// 获取用户提交的文件名称
			String uuidname = new String(request.getParameter("filename").getBytes("ISO-8859-1"), "UTF-8");
			System.out.println("uuidname:" + uuidname);
			File f = new File(Constants.LOCAL_WEIXIN_PATH + File.separator + uuidname);
			if (!f.exists()) {
				String dst = Constants.HDFS_WEIXIN_UPLOADDATA + uuidname;
				// 开始从hadoop的HDFS上读取示例文件--并存储到本地的临时文件中
				FileSystem fs = FileSystem.get(conf);
				InputStream hadopin = null;
				OutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
				Path hdfsPath = new Path(dst);
				if (!fs.exists(hdfsPath)) {
					request.getRequestDispatcher("/error.jsp?result=下载资源不存在!").forward(request, response);
				} else {
					try {
						hadopin = fs.open(hdfsPath);
						IOUtils.copyBytes(hadopin, bos, 4096, true);
					} finally {
						IOUtils.closeStream(hadopin);
						IOUtils.closeStream(bos);
					}
				}
			} 
			// 开始从tomcat的临时文件夹里读取文件，然后通过数据流传送给客户端
			// 设置应答的相应消息头
			response.setContentType("application/x-msdownload");
			String str = "attachment;filename=" + java.net.URLEncoder.encode(uuidname, "utf-8");
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
			in.close();
			out.close();
		}
	}
}
