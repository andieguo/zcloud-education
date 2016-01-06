package com.education.experiment.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
				f.delete();
			}
		}
		out.flush();
		out.close();
	}
	
	public static String readInputStream(InputStream input){
		BufferedReader reader = null;
		try {
			//使用本地环境中的默认字符集，例如在中文环境中将使用 GBK编码
			reader = new BufferedReader(new InputStreamReader(input,"UTF-8"));
			String line = null;
			StringBuffer buffer = new StringBuffer("");
			// 一次读一行，读入null时文件结束
			while ((line = reader.readLine()) != null) {
				// 把当前行号显示出来
				buffer.append(line);
				buffer.append("\n");
			}
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return "";
	}
	
	public static void main(String[] args) throws Exception {
		//测试工具zipUtil
		File file1 = new File("C:\\Users\\andieguo\\temp\\dsafdddddd.html");
		File file2 = new File("C:\\Users\\andieguo\\temp\\asdf.html");
		List<File> listFile = new ArrayList<File>();
		listFile.add(file2);
		listFile.add(file1);
		FileUtil.zipUtil(listFile, new FileOutputStream(new File("C:\\Users\\andieguo\\temp\\dd.zip")));
		//测试工具readInputStream
		System.out.println(readInputStream(new FileInputStream("C:\\Users\\andieguo\\temp\\asdf.html")));
	}
	

}
