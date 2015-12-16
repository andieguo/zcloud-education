package com.education.experiment.commons;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginId = new String(request.getParameter("loginId").getBytes("ISO-8859-1"), Charset.defaultCharset());
		String loginPwd = request.getParameter("loginPwd");
		PrintWriter out = response.getWriter();
		Connection conn;
		try {
			conn = BaseDao.getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from USER_INFO WHERE ID = ? AND PASSWD = ?");
			ps.setString(1, loginId);
			ps.setString(2, loginPwd);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				out.print("1");
				UserBean ub = new UserBean();
				ub.setUserId(rs.getString(1));
				ub.setUserPassWd(rs.getString(2));
				ub.setUserName(rs.getString(3));
				ub.setCloudSize(rs.getLong(4));
				ub.setPhoneNumber(rs.getString(5));
				ub.setRemark(rs.getString(6));
				System.out.println(ub);
				request.getSession().setAttribute("user", ub);
				request.getSession().setAttribute("username", ub.getUserId());
			} else {
				out.print("2");
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.print("2");
		}
		out.close();
	}
}
