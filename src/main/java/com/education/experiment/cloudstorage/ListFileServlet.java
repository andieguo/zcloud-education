package com.education.experiment.cloudstorage;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListFileServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 获取文件的路径
		String savePath = this.getServletContext().getRealPath("WEB-INF/upload");
		// 定义map映射类，来存储uuidname和realname
		Map<String, String> map = new HashMap<String, String>();
		// 去调用这个方法来得到文件名的路径
		listFiles(new File(savePath), map);
		// 存到作用域中，让jap页面去访问，并输出
		request.setAttribute("map", map);
		request.getRequestDispatcher("/listfiles.jsp").forward(request, response);
	}

	// 递归方法遍历该文件夹下的所有文件及子文件夹下的文件
	private void listFiles(File file, Map<String, String> map) {
		if (file.isFile()) {
			// 如果是文件
			String uuidname = file.getName();// 获取的是uuid的文件名
			String realname = uuidname.substring(uuidname.indexOf("_") + 1);
			map.put(uuidname, realname);
		} else {
			// 不是文件，而是文件夹
			File[] files = file.listFiles();// 得到某一个文件夹里的所有东西
			for (File f : files) {
				listFiles(f, map);
			}
		}
	}
}
