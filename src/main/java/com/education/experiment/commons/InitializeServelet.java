package com.education.experiment.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitializeServelet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		String url = "http://192.168.100.141:50070/dfsnodelist.jsp?whatNodes=LIVE";
		URL urls = null;
		BufferedReader in = null;
		try {
			urls = new URL(url);
			// 打开与url之间的连接
			URLConnection conn = urls.openConnection();
			// 建立显示的连接
			conn.connect();
			// BufferedReader输入流来读取URL的相应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			String result = "";
			while ((line = in.readLine()) != null) {
				result += line;
				result += System.getProperty("line.separator");
			}
			Pattern pattern = Pattern.compile("<a title.*?>(.*?)</a>");
			Matcher matcher = pattern.matcher(result);
			String slaveOne = null;
			if (matcher.find()) {
				slaveOne = matcher.group(1);
			}
			// System.out.println(slaveOne);
			this.getServletContext().setAttribute("slaveone", slaveOne);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// this.getServletContext().setAttribute("slaveone", "slave11");
	}

	public static void main(String[] args) throws ServletException {
		InitializeServelet is = new InitializeServelet();
		is.init();
	}
}
