package com.education.experiment.cloudexpress;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.education.experiment.commons.UserBean;

public class LaunchParsingServlet extends HttpServlet {

	/**
	 * 该类负责启动和停止快递分析云计算，一旦启动了分析的云计算， hadoop会定时扫描数据文件，一旦发现数据文件，就会开始分析计算
	 */
	private static final long serialVersionUID = 1L;
	private static ExpressParsingThread ept = null;

	public LaunchParsingServlet() {
		super();
	}

	// 处理用户提交的启动和停止请求，服务端会根据用户的请求对Hadoop集群进行相应的指令操作
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		if (ub == null) {
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
		} else if (ub.getUserId().equals("admin")) {
			String sign = request.getParameter("sign");
			if (sign.equals("1")) {
				if (ept != null && ept.isAlive()) {
					request.getRequestDispatcher("/error.jsp?result=分析任务已经启动!")
							.forward(request, response);
				} else {
					ept = new ExpressParsingThread();
					ept.start();
					request.getRequestDispatcher(
							"/mrlink.jsp?result=已经成功发送启动命令").forward(request,
							response);
				}
			} else {
				ept.termination();
				request.getRequestDispatcher("/mrlink.jsp?result=已经成功发送终止命令")
						.forward(request, response);
			}
		}
	}
}
