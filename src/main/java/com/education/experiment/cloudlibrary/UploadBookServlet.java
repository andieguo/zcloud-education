package com.education.experiment.cloudlibrary;

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

import com.education.experiment.commons.HadoopConfiguration;
import com.education.experiment.commons.UserBean;

public class UploadBookServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Configuration conf = HadoopConfiguration.getConfiguration();

	public UploadBookServlet() {
		super();
	}

	/**
	 * 处理用户提交的上传书本的请求，服务端根据客户提交的书本名称，作者，出版日期等，然后把这些以文件的形式写入到HDFS中， 写入完成后，并开始对书本建立索引。
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
			// 定义一个书本的对象，接受客户端发送的书本信息
			Book book = new Book();
			if (FileUpload.isMultipartContent(requestContext)) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				// 设置文件的缓存路径,首先把文件写入到tomcat的临时文件夹下
				File temp = new File(System.getProperty("user.home") + File.separator + "temp");
				if (!temp.exists())
					temp.mkdir();
				factory.setRepository(temp);
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
						if (fileItem.getFieldName().equals("name")) {
							book.setName(new String(fileItem.getString().getBytes("iso8859-1"), "utf-8"));
						} else if (fileItem.getFieldName().equals("author")) {
							book.setAuthor(new String(fileItem.getString().getBytes("iso8859-1"), "utf-8"));
						} else if (fileItem.getFieldName().equals("publishdate")) {
							book.setPublishDate(new String(fileItem.getString().getBytes("iso8859-1"), "utf-8"));
						}
					} else {
						if (book.getName() == null || "".equals(book.getName()) || book.getAuthor() == null || "".equals(book.getAuthor())) {
							request.getRequestDispatcher("/error.jsp?result=作者或用户名不能为空!").forward(request, response);
						} else {
							System.out.println(fileItem.getFieldName() + "   " + fileItem.getName() + "   " + fileItem.isInMemory() + "    " + fileItem.getContentType() + "   " + fileItem.getSize());
							// 保存文件，其实就是把缓存里的数据写到目标路径HDFS下
							if (fileItem.getName() != null && fileItem.getSize() != 0) {
								String[] array = fileItem.getName().split("\\\\");
								File newFile = new File(temp.getPath() + File.separator + array[array.length - 1]);
								try {
									fileItem.write(newFile);
								} catch (Exception e) {
									e.printStackTrace();
								}
								String dst = "/tomcat/experiment/librarycloud/uploaddata/" + book.getAuthor() + "-" + book.getName() + ".book";
								InputStream in = new BufferedInputStream(new FileInputStream(newFile));
								// 开始往HDFS上写入书本文件信息
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
											// TODO Auto-generated
											System.out.println("*");
										}
									});
									IOUtils.copyBytes(in, out, 4096, true);
									IOUtils.closeStream(in);
									IOUtils.closeStream(out);
									// 往HDFS上写入文件完成
									if (newFile.exists()) {
										newFile.delete();
									}
									response.sendRedirect("listbook.jsp");
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
}
