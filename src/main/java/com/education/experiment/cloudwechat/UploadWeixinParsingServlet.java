package com.education.experiment.cloudwechat;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.education.experiment.commons.Constants;
import com.education.experiment.commons.HadoopConfiguration;
import com.education.experiment.commons.UserBean;

public class UploadWeixinParsingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Configuration conf = HadoopConfiguration.getConfiguration();

	public UploadWeixinParsingServlet() {
		super();
	}

	/**
	 * 处理用户提交的分析条件信息，用户提交的条件表单给服务端，服务端会校验这些信息，校验通过的话，会以文件的信息把这些信息写入到HDFS当中去
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置request编码，主要是为了处理普通输入框中的中文问题
		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		// System.out.println(content);
		if (ub != null) {
			String result = null;
			StringBuffer sb = new StringBuffer();
			String datePoint = request.getParameter("datePoint");
			// 开始校验客户端提交过来的分析条件信息
			if (datePoint != null && !"".equals(datePoint)) {
				sb.append("timePoint:" + datePoint.trim() + "\t");
			} else {
				sb.append("timePoint:" + "null" + "\t");
			}
			String minute = request.getParameter("minute");
			try {
				if (minute != null && !minute.equals("")) {
					sb.append("durationTime:" + Integer.parseInt(minute) + "\t");
				} else {
					sb.append("durationTime:" + "null" + "\t");
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = "通信时长格式错误";
			}
			String sex = request.getParameter("sex");
			if (sex != null) {
				sb.append("gender:" + sex + "\t");
			} else {
				sb.append("gender:" + "null" + "\t");
			}
			String isFriend = request.getParameter("isfriend");
			if (isFriend != null) {
				sb.append("isFriend:" + isFriend + "\t");
			} else {
				sb.append("isFriend:" + "null" + "\t");
			}
			String ageSpan = request.getParameter("agespan");
			if (ageSpan != null && !"".equals(ageSpan)) {
				if (ageSpan.contains("至")) {
					sb.append("ageSpan:" + ageSpan + "\t");
				} else {
					result = "年龄范围格式错误";
				}
			} else {
				sb.append("ageSpan:" + "null" + "\t");
			}
			String[] vocations = request.getParameterValues("vocation");
			if (vocations != null && vocations.length > 0) {
				sb.append("vocation:" + vocations[0]);
				for (int index = 1; index < vocations.length; index++) {
					sb.append("," + vocations[index]);
				}
				sb.append("\t");
			} else {
				sb.append("vocation:" + "null" + "\t");
			}
			String communicationPlace = request.getParameter("places");
			if (communicationPlace != null && !"".equals(communicationPlace)) {
				sb.append("communicationPlace:" + communicationPlace + "\t");
			} else {
				sb.append("communicationPlace:" + "null" + "\t");
			}
			String keywords = request.getParameter("keywords");
			if (keywords != null && !"".equals(keywords)) {
				sb.append("keywords:" + keywords + "\t");
			} else {
				sb.append("keywords:" + "null" + "\t");
			}
			// 开始往hadoop的HDFS上写入分析条件的文件
			FileSystem hdfs = FileSystem.get(conf);
			Path path = new Path(Constants.HDFS_WEIXIN_UPLOADPARSING + ub.getUserId() + ".pars");
			if (result != null) {
				request.getRequestDispatcher("/error.jsp?result=" + result + "!").forward(request, response);
			} else {
				if (hdfs.exists(path)) {
					request.getRequestDispatcher("/error.jsp?result=上传的文件已存在!").forward(request, response);
				} else {
					FSDataOutputStream hdfsOut = hdfs.create(path);
					hdfsOut.write(sb.toString().getBytes(request.getCharacterEncoding()));
					hdfsOut.close();
					// 写入完成
					response.sendRedirect("listparsing.jsp");
				}
			}
		} else {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
}
