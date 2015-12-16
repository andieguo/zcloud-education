package com.education.experiment.cloudexpress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.education.experiment.commons.HadoopConfiguration;
import com.education.experiment.commons.UserBean;

public class UpdateGPRSServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Configuration conf = HadoopConfiguration.getConfiguration();

	// private static final SimpleDateFormat sdf = new SimpleDateFormat(
	// "yyyy-MM-dd HH:mm:ss");

	public UpdateGPRSServlet() {
		super();
	}

	/**
	 * 更新用户提交的GPRS信息操作，服务端接受客户端提交的GPRS信息，然后把这些信息更新到HDFS上，用于后期的数据处理
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置request编码，主要是为了处理普通输入框中的中文问题
		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		// System.out.println(content);
		if (ub != null) {
			String result = null;
			float longitude = 0.0f;
			float latitude = 0.0f;
			// 获取客户端提交的GPRS信息
			try {
				longitude = Float.parseFloat(request.getParameter("longitude"));
				latitude = Float.parseFloat(request.getParameter("latitude"));
			} catch (Exception e) {
				e.printStackTrace();
				result = "通信时长格式错误";
			}
			if (result != null) {
				request.getRequestDispatcher("/error.jsp?result=" + result + "!").forward(request, response);
			} else {
				// 更新GPRS信息到HDFS上
				FileSystem hdfs = FileSystem.get(conf);
				Path path = new Path("/tomcat/experiment/expresscloud/courier/courier.txt");
				if (hdfs.exists(path)) {
					FSDataInputStream fsdis = hdfs.open(path);
					BufferedReader br = new BufferedReader(new InputStreamReader(fsdis, "UTF-8"));
					String line = null;
					StringBuffer sb = new StringBuffer();
					boolean sign = false;
					while ((line = br.readLine()) != null) {
						String[] array = line.split("\t");
						if (!sign && array[0].equals(ub.getUserId())) {
							sb.append(array[0] + "\t" + longitude + "," + latitude + "\n");
							sign = true;
						} else {
							sb.append(line + "\n");
						}
					}
					if (!sign) {
						sb.append(ub.getUserId() + "\t" + longitude + "," + latitude + "\n");
					}
					fsdis.close();
					hdfs.delete(path, true);
					FSDataOutputStream hdfsOut = hdfs.create(path);
					hdfsOut.write(sb.toString().getBytes(request.getCharacterEncoding()));
					hdfsOut.close();
				} else {
					String line = ub.getUserId() + "\t" + longitude + "," + latitude + "\n";
					FSDataOutputStream hdfsOut = hdfs.create(path);
					hdfsOut.write(line.getBytes(request.getCharacterEncoding()));
					hdfsOut.close();
				}
				if (ub.getUserId().equals("admin")) {
					response.sendRedirect("unlimit.jsp");
				} else {
					response.sendRedirect("limited.jsp");
				}
			}
		} else {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
}
