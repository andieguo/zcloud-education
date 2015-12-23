package com.education.experiment.commons;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.education.experiment.util.PropertiesUtil;
import com.education.experiment.util.Rest;

public class FileSystemServlet extends HttpServlet {

	private static final long serialVersionUID = 5009026145243904234L;
	private Logger logger =  Logger.getLogger(FileSystemServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Properties properties = PropertiesUtil.loadFromInputStream(this.getClass().getResourceAsStream("/config.properties"));
		String hostname = properties.getProperty("fs.default.name.hostname");//192.168.100.141
		String port = properties.getProperty("hadoop.namenode.port");//50070
		String command = request.getParameter("command");//LISTSTATUS
		String parentDir = request.getParameter("parentDir");//LISTSTATUS
		String url = null;
		logger.info("hostname:"+hostname);
		logger.info("port:"+port);
		logger.info("command:"+command);
		logger.info("parentDir:"+parentDir);
		try {
			if(command.equals("LISTSTATUS")){
				url = MessageFormat.format("http://{0}:{1}/webhdfs/v1{2}?op={3}", hostname,port,parentDir,command);
				logger.info("url:"+url);
			}else if(command.equals("OPEN")){
				url = MessageFormat.format("http://{0}:{1}/webhdfs/v1{2}?op={3}", hostname,port,parentDir,command);
				logger.info("url:"+url);
			}
			String resultJSON = Rest.doRest("GET",url,null);
			//这句话的意思，是让浏览器用utf8来解析返回的数据  
			response.setHeader("Content-type", "text/html;charset=UTF-8");  
			//这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859  
			response.setCharacterEncoding("UTF-8");//response输出，按照utf-8输出。
			PrintWriter out = response.getWriter();
			System.out.println(resultJSON);
			out.println(resultJSON);// 向客户端输出JSONObject字符串
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			String url2 = "http://192.168.100.141:50070/webhdfs/v1/tomcat/users/admin/notes?op=LISTSTATUS";
			String resultJSON = Rest.doRest("GET",url2,null);//输出的是中文
			System.out.println(resultJSON);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
