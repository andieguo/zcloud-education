package com.education.experiment.commons;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.education.experiment.util.JarUtil;

public class InitializeServelet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		//将/zcloud-education/WEB-INF/classes下的class打包到用户家目录temp下，提交job时需要这个jar文件
		try {
			String javaClassPath = InitializeServelet.class.getClassLoader().getResource("").toString();
			javaClassPath = javaClassPath.substring(javaClassPath.indexOf("/")+1);
			File temp = new File(System.getProperty("user.home") + File.separator + "temp");
			if (!temp.exists()) temp.mkdir();
			String targetPath = temp+File.separator+"education.jar";
			System.out.println(targetPath);
			File targetFile = new File(targetPath);
			if (targetFile.exists()) {
				targetFile.delete();
			}
			JarUtil jarUtil = new JarUtil(javaClassPath, targetPath);
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
