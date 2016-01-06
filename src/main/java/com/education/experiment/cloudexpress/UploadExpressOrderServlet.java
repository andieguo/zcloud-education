package com.education.experiment.cloudexpress;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

public class UploadExpressOrderServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Configuration conf = HadoopConfiguration.getConfiguration();

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public UploadExpressOrderServlet() {
		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置request编码，主要是为了处理普通输入框中的中文问题
		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		// System.out.println(content);
		if (ub != null) {
			String result = null;
			float longitude = 0.0f;
			float latitude = 0.0f;
			try {
				longitude = Float.parseFloat(request.getParameter("longitude"));
				latitude = Float.parseFloat(request.getParameter("latitude"));
			} catch (Exception e) {
				e.printStackTrace();
				result = "经纬度数据格式错误";
			}
			if (result != null) {
				request.getRequestDispatcher("/error.jsp?result=" + result + "!").forward(request, response);
			} else {
				StringBuffer sb = new StringBuffer();
				Date current = new Date();
				sb.append(sdf.format(current) + "\t");
				String address = request.getParameter("address");
				if (address != null && !"".equals(address)) {
					sb.append(address + "\t");
				} else {
					sb.append("null" + "\t");
				}
				sb.append("经纬度:" + longitude + "," + latitude + "\t");
				String remark = request.getParameter("remark");
				if (remark != null && !"".equals(remark)) {
					sb.append("备注:" + remark);
				} else {
					sb.append("备注:null");
				}
				FileSystem hdfs = FileSystem.get(conf);
				Path path = new Path(Constants.HDFS_EXPRESS_UPLOADDATA + current.getTime() + ".txt");
				if (hdfs.exists(path)) {
					request.getRequestDispatcher("/error.jsp?result=上传的文件已存在!").forward(request, response);
				} else {
					FSDataOutputStream hdfsOut = hdfs.create(path);
					hdfsOut.write(sb.toString().getBytes(request.getCharacterEncoding()));
					hdfsOut.close();
					if (ub.getUserId().equals("admin")) {
						response.sendRedirect("unlimit.jsp");
					} else {
						response.sendRedirect("limited.jsp");
					}
				}
			}
		} else {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
}
