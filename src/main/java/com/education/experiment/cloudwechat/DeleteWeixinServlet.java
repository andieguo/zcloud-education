package com.education.experiment.cloudwechat;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.education.experiment.commons.BaseDao;
import com.education.experiment.commons.Constants;
import com.education.experiment.commons.HadoopConfiguration;
import com.education.experiment.commons.UserBean;

public class DeleteWeixinServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Configuration conf = HadoopConfiguration.getConfiguration();

	/**
	 * 处理用户提交的删除微信数据的请求，服务会根据用户提交的数据文件名称，然后从HDFS上的指定目录下删除该文件
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		int count = 0;
		if (ub == null) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			// 获取用户提交的文件名称
			String uuidnames[] = request.getParameterValues("filename");
			for (String uuid : uuidnames) {
				String uuidname = new String(uuid.getBytes("ISO-8859-1"), "UTF-8");
				String dst = Constants.HDFS_WEIXIN_UPLOADDATA + uuidname;
				// 开始删除用户提交的文件名称
				FileSystem fs = FileSystem.get(conf);
				Path hdfsPath = new Path(dst);
				if (!fs.exists(hdfsPath)) {
					request.getRequestDispatcher("/error.jsp?result=刪除资源不存在!").forward(request, response);
				} else {
					FileStatus stat = fs.getFileStatus(hdfsPath);
					ub.setCloudSize(ub.getCloudSize() + stat.getLen());
					boolean status = fs.delete(hdfsPath, true);
					if (status) {
						// 删除文件结束
						// 更新用户的sesion信息
						int result = BaseDao.updateUserStatus(ub);
						count += result;
					}
				}
			}
			if(uuidnames.length == count){
				response.sendRedirect("listweixin.jsp");
			}else{
				request.getRequestDispatcher("/error.jsp?result=刪除资源失败!").forward(request, response);
			}
		}
	}
}
