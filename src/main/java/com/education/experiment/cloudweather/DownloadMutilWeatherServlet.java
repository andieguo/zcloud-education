package com.education.experiment.cloudweather;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import com.education.experiment.commons.Constants;
import com.education.experiment.commons.HadoopConfiguration;
import com.education.experiment.commons.UserBean;
import com.education.experiment.util.FileUtil;

public class DownloadMutilWeatherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Configuration conf = HadoopConfiguration.getConfiguration();

	/*
	 * 处理用户下载的文件请求，用户提交一个文件名称，系统从HDFS读取该文件，然后传输给用户
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 找到用户所选定的文件
		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		InputStream hadopin = null;
		OutputStream bos =null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		List<File> fileList = new ArrayList<File>();
		if (ub == null) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			try {
				// 获取用户提交的文件名称
				String uuidnames[] = request.getParameterValues("filename");
				// 读取HDFS上的文件存储在本地临时文件中
				// 开始从HDFS上读取文件
				FileSystem fs = FileSystem.get(conf);
				for (String uuid : uuidnames) {
					String uuidname = new String(uuid.getBytes("ISO-8859-1"), "UTF-8");
					System.out.println("uuidname:" + uuidname);
					//创建临时文件，读取HDFS上的文件存储在本地临时文件中，再文件f的内容返回给response
					File f = new File(Constants.LOCAL_WEATHER_PATH + File.separator + uuidname);
					if(!f.exists()){
						String dst = Constants.HDFS_WEATHER_UPLOADDATA + uuidname;
						bos = new BufferedOutputStream(new FileOutputStream(f));
						System.out.println("dst:" + dst);
						Path hdfsPath = new Path(dst);
						if (fs.exists(hdfsPath)) {// 服务器端的内容存在
							hadopin = fs.open(hdfsPath);
							// 将HDFS上的文件拷贝到临时文件f中
							IOUtils.copyBytes(hadopin, bos, 4096, true);
						}
					}
					fileList.add(f);
				}
				//读取文件结束,将文件f的内容返回给response,开始给客户端传送文件。
				response.setContentType("application/x-msdownload");
				String zipFileName = "weather-"+simpleDateFormat.format(new Date())+".zip";
				//设置content-disposition响应头控制浏览器以下载的形式打开文件
				response.setHeader("Content-Disposition", "attachment;filename=" + zipFileName);
				// 从输入流对象中读数据写入到输出流对象中
				FileUtil.zipUtil(fileList, response.getOutputStream());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				IOUtils.closeStream(hadopin);
				bos.close();
			}
		}
	}

}
