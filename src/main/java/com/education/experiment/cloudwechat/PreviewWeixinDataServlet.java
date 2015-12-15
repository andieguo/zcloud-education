package com.education.experiment.cloudwechat;

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

public class PreviewWeixinDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String slaveone = null;
	private static int fileCount = 0;

	public PreviewWeixinDataServlet() {
	}

	public void init() throws ServletException {
		if (slaveone == null) {
			slaveone = (String) this.getServletContext().getAttribute(
					"slaveone");
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// request.setCharacterEncoding(Charset.defaultCharset().toString());
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		fileCount = 0;
		if (ub != null) {
			String sign = request.getParameter("sign");
			if (sign.equals("0")) {
				String result = getHadoopContent("/tomcat/experiment/weixincloud/uploadparsing");
				if (result.equals("")) {
					request.setAttribute("result", "无数据文件可浏览!");
				} else {
					request.setAttribute("result", convert(result));
				}
			} else {
				String dir = new String(request.getParameter("dir").getBytes(
						"ISO-8859-1"), "UTF-8");
				// System.out.println(Charset.defaultCharset());
				String result = getHadoopContent(dir);
				if (result.equals("")) {
					request.setAttribute("result", "无数据文件可浏览!");
				} else {
					request.setAttribute("result", convert(result));
				}
			}
			request.setAttribute("datacount", fileCount);
			request.getRequestDispatcher("/viewweixindata.jsp").forward(request,
					response);
		} else {
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
		}
	}

	private String getHadoopContent(String dir) {
		BufferedReader in = null;
		try {
			String url = "http://" + slaveone
					+ ":50075/browseDirectory.jsp?namenodeInfoPort=50070&dir="
					+ URLEncoder.encode(dir, "UTF-8");
			URL urls = new URL(url);
			URLConnection conn = urls.openConnection();
			conn.connect();
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line = null;
			String result = "";
			while ((line = in.readLine()) != null) {
				result += line;
				result += System.getProperty("line.separator");
			}
			result = new String(result.getBytes(Charset.defaultCharset()),
					"UTF-8");
			Pattern pattern = Pattern.compile("<textarea[\\s\\S]*?</textarea>");
			Matcher matcher = pattern.matcher(result);
			if (matcher.find()) {
				return matcher.group();
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
			fileCount++;
			String href = matcher.group(1);
			String[] split = href.split("\\?");
			String url = "previewweixindata?sign=1&" + split[1].split("&")[0];
			int index = content.indexOf(href);
			content = content.substring(0, index) + url
					+ content.substring(index + href.length());
		}
		return content;
	}
}
