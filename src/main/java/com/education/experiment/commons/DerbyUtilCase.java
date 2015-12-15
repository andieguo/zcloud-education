package com.education.experiment.commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DerbyUtilCase {
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 加载驱动
	}

	public static final Connection getDerbyConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:derby:education;create=true");// 连接数据库
	}

	public static void updateUserStatus(UserBean ub) {
		try {
			Connection conn = DerbyUtilCase.getDerbyConnection();
			PreparedStatement ps = conn
					.prepareStatement("UPDATE USER_INFO SET CLOUDSIZE = ? WHERE ID = ?");
			ps.setLong(1, ub.getCloudSize());
			ps.setString(2, ub.getUserId());
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 public static void main(String[] args) throws SQLException {
		 Connection conn = DerbyUtilCase.getDerbyConnection();
		 Statement st = conn.createStatement();
		 st.execute("create table USER_INFO (ID VARCHAR(20) NOT NULL,PASSWD VARCHAR(50) NOT NULL,NAME VARCHAR(20) NOT NULL,CLOUDSIZE BIGINT NOT NULL,PHONENUM VARCHAR(20),REMARK VARCHAR(200),primary key(ID))");//
		 // 建表
		 PreparedStatement ps = conn
		 .prepareStatement("insert into USER_INFO(ID,PASSWD,NAME,CLOUDSIZE,PHONENUM,REMARK) values (?,?,?,?,?,?)");//
		 // 插入数据
		 ps.setString(1, "admin");
		 ps.setString(2, "admin");
		 ps.setString(3, "管理员");
		 ps.setLong(4, 10240L * 1024 * 1024);
		 ps.setString(5, "13888888888");
		 ps.setString(6, "我是管理员。");
		 ps.executeUpdate();
		 ps = conn.prepareStatement("UPDATE USER_INFO SET CLOUDSIZE = 256 WHERE ID = ?");
		 ps.setString(1, "xiaoshang2");
		 ps.executeUpdate();
		 ResultSet rs = st.executeQuery("select * from USER_INFO");// 读取刚插入的数据
		 while (rs.next()) {
			 System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|"
			 + rs.getString(3) + "|" + rs.getLong(4) + "|"
			 + rs.getString(5) + "|" + rs.getString(6));
		 }
		 st.close();
		 ps.close();
		 rs.close();
		 conn.close();
	 }
	
//	public static void main(String[] args) throws SQLException, IOException {
//		Connection conn = DerbyUtilCase.getDerbyConnection();
//		Statement st = conn.createStatement();
//		//st.execute("create table WEIXIN_INFO (ID INT NOT NULL,NAME VARCHAR(50) NOT NULL,AGE INT NOT NULL,SEX INT NOT NULL,VOCATION VARCHAR(20),RELATION VARCHAR(200),primary key(ID))");//
//		// 建表
////		PreparedStatement ps = conn
////				.prepareStatement("insert into WEIXIN_INFO(ID,NAME,AGE,SEX,VOCATION,RELATION) values (?,?,?,?,?,?)");//
////		// 插入数据
////		BufferedReader reader = new BufferedReader(new InputStreamReader(
////				new FileInputStream(new File("E:\\weixindata.txt"))));
////		String line = null;
////		while ((line = reader.readLine()) != null) {
////			String[] array = line.split("\t");
////			ps.setInt(1, Integer.parseInt(array[0]));
////			ps.setString(2, array[1]);
////			ps.setInt(3, Integer.parseInt(array[2]));
////			ps.setInt(4, Integer.parseInt(array[3]));
////			ps.setString(5, array[4]);
////			ps.setString(6, array[5]);
////			ps.executeUpdate();
////		}
//
//		//ps = conn
//		//		.prepareStatement("UPDATE USER_INFO SET CLOUDSIZE = 256 WHERE ID = ?");
//		// ps.setString(1, "xiaoshang2");
//		// ps.executeUpdate();
//		ResultSet rs = st.executeQuery("select * from WEIXIN_INFO");// 读取刚插入的数据
//		while (rs.next()) {
//			System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|"
//					+ rs.getInt(3) + "|" + rs.getInt(4) + "|" + rs.getString(5)
//					+ "|" + rs.getString(6));
//		}
//		st.close();
//		//ps.close();
//		rs.close();
//		conn.close();
//	}
}
