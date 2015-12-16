package com.education.experiment.cloudstorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.education.experiment.commons.UserBean;

public class PreviewUserPropertyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String slaveone = null;

	public PreviewUserPropertyServlet() {
	}

	public void init() throws ServletException {
		if (slaveone == null) {
			slaveone = (String) this.getServletContext().getAttribute("slaveone");
		}
	}

	// 处理用户提交的查看请求,用户提交浏览云存储请求，服务端会读取HDFS上的文件信息，然后返回给客户端
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request.setCharacterEncoding(Charset.defaultCharset().toString());
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		if (ub != null) {
			request.setAttribute("cloudsize", ub.getCloudSize());
			String sign = request.getParameter("sign");
			if (sign.equals("0")) {
				String result = getHadoopContent("/tomcat/users/" + ub.getUserId());
				if (result.equals("")) {
					request.setAttribute("result", "无数据文件可浏览!");
				} else {
					request.setAttribute("result", convert(result));
				}
			} else {
				String dir = new String(request.getParameter("dir").getBytes("ISO-8859-1"), "UTF-8");
				// System.out.println(Charset.defaultCharset());
				// 开始从hadoop上读取文件的信息
				String result = getHadoopContent(dir);
				// 读取文件信息结束
				if (result.equals("")) {
					request.setAttribute("result", "无数据文件可浏览!");
				} else {
					request.setAttribute("result", convert(result));
				}
			}
			request.getRequestDispatcher("/viewfile.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

	// 读取hadoop文件信息的私有方法，不对外提供该功能
	private String getHadoopContent(String dir) {
		BufferedReader in = null;
		try {
			String url = "http://" + slaveone + ":50075/browseDirectory.jsp?namenodeInfoPort=50070&dir=" + URLEncoder.encode(dir, "UTF-8");
			URL urls = new URL(url);
			URLConnection conn = urls.openConnection();
			conn.connect();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			String result = "";
			while ((line = in.readLine()) != null) {
				result += line;
				result += System.getProperty("line.separator");
			}
			result = new String(result.getBytes(Charset.defaultCharset()), "UTF-8");
			Pattern pattern = Pattern.compile("<textarea[\\s\\S]*?</textarea>");
			Matcher matcher = pattern.matcher(result);
			if (matcher.find()) {
				return "当前文件内容：\n<br /><br />" + matcher.group();
			} else {
				pattern = Pattern.compile("<table.+</table>");
				matcher = pattern.matcher(result);
				if (matcher.find()) {
					return matcher.group();
				}
			}
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
		return "";
	}

	private String convert(String content) throws UnsupportedEncodingException {
		Pattern pattern = Pattern.compile("<a href=\"(.*?)\">");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			String href = matcher.group(1);
			String[] split = href.split("\\?");
			String url = "previewuser?sign=1&" + split[1].split("&")[0];
			int index = content.indexOf(href);
			content = content.substring(0, index) + url + content.substring(index + href.length());
		}
		return content;
	}
}
