package com.education.experiment.cloudexpress;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import com.education.experiment.commons.Constants;
import com.education.experiment.commons.HadoopConfiguration;
import com.education.experiment.commons.UserBean;

public class UploadExpressFileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Configuration conf = HadoopConfiguration.getConfiguration();

	public UploadExpressFileServlet() {
		super();
	}

	/**
	 * 处理客户端提交的上传快递数据文件的请求，客户端从本地读取数据示例文件，通过数据流的方式跟服务端产生通信 服务端接受到请求后，会读取该数据流，然后把数据流以文件的形式写入tomcat的临时目录下
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置request编码，主要是为了处理普通输入框中的中文问题
		request.setCharacterEncoding("utf-8");
		// 这里对request进行封装，RequestContext提供了对request多个访问方法
		RequestContext requestContext = new ServletRequestContext(request);
		// 判断表单是否是Multipart类型的。这里可以直接对request进行判断，不过已经以前的用法了
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		if (ub == null) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			if (FileUpload.isMultipartContent(requestContext)) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				// 设置文件的缓存路径
				factory.setRepository(new File(Constants.PROJECTPATH));
				ServletFileUpload upload = new ServletFileUpload(factory);
				// 设置上传文件大小的上限，-1表示无上限
				upload.setSizeMax(1024 * 1024 * 1024);
				List<FileItem> items = new ArrayList<FileItem>();
				try {
					// 上传文件，并解析出所有的表单字段，包括普通字段和文件字段
					items = upload.parseRequest(request);
				} catch (FileUploadException e1) {
					System.out.println("文件上传发生错误" + e1.getMessage());
				}
				// 下面对每个字段进行处理，分普通字段和文件字段
				Iterator<?> it = items.iterator();
				while (it.hasNext()) {
					FileItem fileItem = (FileItem) it.next();
					// 如果是普通字段
					if (fileItem.isFormField()) {
						System.out.println(fileItem.getFieldName() + "   " + fileItem.getName() + "   " + new String(fileItem.getString().getBytes("iso8859-1"), "gbk"));
					} else {
						// 保存文件，其实就是把缓存里的数据写到tomcat的临时目录下
						if (fileItem.getName() != null && fileItem.getSize() != 0) {
							String[] array = fileItem.getName().split("\\\\");
							File newFile = new File(Constants.PROJECTPATH + File.separator + array[array.length - 1]);
							try {
								fileItem.write(newFile);
							} catch (Exception e) {
								e.printStackTrace();
							}
							String dst = Constants.HDFS_EXPRESS_UPLOADDATA + newFile.getName();
							InputStream in = new BufferedInputStream(new FileInputStream(newFile));
							// 开始从tomcat的临时目录下读取数据文件，然后往HDFS上写入
							FileSystem fs = FileSystem.get(conf);
							Path path = new Path(dst);
							if (fs.exists(path)) {
								if (newFile.exists()) {
									newFile.delete();
								}
								request.getRequestDispatcher("/error.jsp?result=上传的文件已存在!").forward(request, response);
							} else {
								OutputStream out = fs.create(path, new Progressable() {
									public void progress() {
										// TODO Auto-generated method
										System.out.println("*");
									}
								});
								IOUtils.copyBytes(in, out, 4096, true);
								IOUtils.closeStream(in);
								IOUtils.closeStream(out);
								if (newFile.exists()) {
									newFile.delete();
								}
								if (ub.getUserId().equals("admin")) {
									request.getRequestDispatcher("/unlimit.jsp").forward(request, response);
								} else {
									request.getRequestDispatcher("/limited.jsp").forward(request, response);
								}
							}
						} else {
							System.out.println("path is null.");
						}
					}
				}
			}
		}
	}
}
