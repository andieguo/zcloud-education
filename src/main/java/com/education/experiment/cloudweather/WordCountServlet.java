package com.education.experiment.cloudweather;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

import com.education.experiment.commons.HadoopConfiguration;

public class WordCountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Configuration conf = HadoopConfiguration
			.getConfiguration();

	public WordCountServlet() {
		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置request编码，主要是为了处理普通输入框中的中文问题
		String[] args = new String[5];
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 这里对request进行封装，RequestContext提供了对request多个访问方法
		RequestContext requestContext = new ServletRequestContext(request);

		// 判断表单是否是Multipart类型的。这里可以直接对request进行判断，不过已经以前的用法了
		if (FileUpload.isMultipartContent(requestContext)) {

			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置文件的缓存路径
			factory.setRepository(new File("/hadoop/tomcat/temp/"));
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 设置上传文件大小的上限，-1表示无上限
			upload.setSizeMax(1024 * 1024 * 1024);
			args[1] = "/tomcat/wordcount/input";
			args[2] = "/tomcat/wordcount/output";
			args[3] = "-skip";
			args[4] = "/tomcat/wordcount/filter/patterns.txt";
			args[0] = "-Dwordcount.case.sensitive=false";
		}
		// -Dwordcount.case.sensitive=false /tomcat/input /tomcat/output -skip
		// /tomcat/filter/patterns.txt
		try {
			ToolRunner.run(conf, new WordCount(), args);
			request.getRequestDispatcher("/unlimit.jsp").forward(request,
					response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.getRequestDispatcher(
					"/error.jsp?result=任务执行失败,请查看集群是否正常运行." + e.getMessage())
					.forward(request, response);
		}
	}
}
