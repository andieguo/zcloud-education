package com.education.experiment.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {
	
	public static void zipUtil(List<File> fileList,OutputStream output) throws Exception{
		ZipOutputStream out = new ZipOutputStream(output);
		for (File f : fileList) {
			if (f.exists()) {
				// 设置应答的相应消息头
				// 创建一 个输入流对象和指定的文件相关联
				FileInputStream in = new FileInputStream(f);
				// 从response对象中获取到输出流对象
				out.putNextEntry(new ZipEntry(f.getName()));
				byte[] buffer = new byte[1024];
				int len;
				// 读入需要下载的文件的内容，打包到zip文件
				while ((len = in.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				out.closeEntry();
				in.close();
			}
		}
		out.flush();
		out.close();
	}
	
	public static void main(String[] args) throws Exception {
		File file1 = new File("C:\\Users\\andieguo\\temp\\dsafdddddd.html");
		File file2 = new File("C:\\Users\\andieguo\\temp\\asdf.html");
		List<File> listFile = new ArrayList<File>();
		listFile.add(file2);
		listFile.add(file1);
		FileUtil.zipUtil(listFile, new FileOutputStream(new File("C:\\Users\\andieguo\\temp\\dd.zip")));
		
	}

}
