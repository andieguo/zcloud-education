package com.education.experiment.commons;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.JobClient;

import com.education.experiment.util.JarUtil;
import com.education.experiment.util.PropertiesUtil;

public class InitializeServelet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JobClient jobClient;
	private Properties properties;
	private Configuration conf;
	private ServletContext servletContext;

	public void init() throws ServletException {
		properties = PropertiesUtil.loadFromInputStream(this.getClass().getResourceAsStream("/config.properties"));
		String hostname = properties.getProperty("fs.default.name.hostname");//192.168.100.141
		String jobPort = properties.getProperty("mapred.job.tracker.port");//9001
		String jobtrackerPort = properties.getProperty("hadoop.jobtracker.port");//50030
		conf = HadoopConfiguration.getConfiguration();
		servletContext = getServletContext();
		try {
			//获取jobClient
			jobClient = new JobClient(new InetSocketAddress(hostname,Integer.valueOf(jobPort)), conf);
			servletContext.setAttribute("jobClient", jobClient);
			servletContext.setAttribute("jobtrackerUrl", "http://"+hostname+":"+jobtrackerPort);
			//将/zcloud-education/WEB-INF/classes下的class打包到用户家目录temp下，提交job时需要这个jar文件
			String javaClassPath = InitializeServelet.class.getClassLoader().getResource("").toString();
			javaClassPath = javaClassPath.substring(javaClassPath.indexOf("/")+1);
			File targetFile = new File(Constants.JAR_HOME);
			if (targetFile.exists()) {
				targetFile.delete();
			}
			JarUtil jarUtil = new JarUtil(javaClassPath, Constants.JAR_HOME);
			jarUtil.generateJar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws ServletException {
		String javaClassPath = "file:/C:/Users/andieguo/workspace_mars/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/zcloud-education/WEB-INF/classes/";
		javaClassPath = javaClassPath.substring(javaClassPath.indexOf("/")+1);
		System.out.println(javaClassPath);
		File temp = new File(System.getProperty("user.home") + File.separator + "temp");
		if (!temp.exists()) temp.mkdir();
		String targetPath = temp.getPath() + File.separator +"education.jar";
		System.out.println(targetPath);
	}
}
