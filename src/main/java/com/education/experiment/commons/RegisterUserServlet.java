package com.education.experiment.commons;

import java.io.IOException;

import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RegisterUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// private static final SimulateMySQL sm = SimulateMySQL.getInstance();

	public RegisterUserServlet() {
		super();
	}
	/*
	 *处理用户注册，用于接收从registerUser.jsp页面发来的数据表单，然后把用户填写的注册信息写入数据库当中。
	 * */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//处理开始
		UserBean ub = new UserBean();
		ub.setUserId(request.getParameter("userId"));
		ub.setUserPassWd(request.getParameter("pwd"));
		ub.setUserName(new String(request.getParameter("userName").getBytes(
				"ISO-8859-1"), Charset.defaultCharset()));
		ub.setPhoneNumber(request.getParameter("phone"));
		ub.setRemark(new String(request.getParameter("remark").getBytes(
				"ISO-8859-1"), Charset.defaultCharset()));
		ub.setCloudSize(Integer.parseInt(request.getParameter("cloudSize")) * 1024L * 1024);
		PrintWriter out = response.getWriter();
		System.out.println(Charset.defaultCharset() + "\n" + ub);
		Connection conn;
		try {
			conn = BaseDao.getConnection();
			// Statement st = conn.createStatement();
			// st.execute("create table USER_INFO (ID VARCHAR(20) NOT NULL,PASSWD VARCHAR(50) NOT NULL,NAME VARCHAR(20) NOT NULL,CLOUDSIZE INT NOT NULL,PHONENUM VARCHAR(20),REMARK VARCHAR(200),primary key(ID))");//
			// // 建表
			// st.close();
			PreparedStatement ps = conn
					.prepareStatement("insert into USER_INFO(ID,PASSWD,NAME,CLOUDSIZE,PHONENUM,REMARK) values (?,?,?,?,?,?)");// 插入数据
			ps.setString(1, ub.getUserId());
			ps.setString(2, ub.getUserPassWd());
			ps.setString(3, ub.getUserName());
			ps.setLong(4, ub.getCloudSize());
			ps.setString(5, ub.getPhoneNumber());
			ps.setString(6, ub.getRemark());
			ps.executeUpdate();
			ps.close();
			conn.close();
			out.print("SUCCESS");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.print("usererror");
		}
		out.close();
		//处理结束
	}
}
