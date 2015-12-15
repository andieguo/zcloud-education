package com.education.experiment.commons;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * 处理JDBC基本的操作
 * @author administrator
 *
 */
public class BaseDao {
	
	//private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	//private static final String URL = "jdbc:sqlserver://localhost:1433;DataBaseName=bbs";
	private static String DRIVER = null;
	private static String URL = null;
	private static String USER = null;
	private static String PASSWORD = null;
	
	static{
		DRIVER = "com.mysql.jdbc.Driver";//PropertiesHelper.getProperty("DRIVER");
		URL = "jdbc:mysql://localhost:3306/db_education?useUnicode=true&amp;characterEncoding=utf-8";//PropertiesHelper.getProperty("URL");
		USER = "root";//PropertiesHelper.getProperty("USER");
		PASSWORD = "root";//PropertiesHelper.getProperty("PASSWORD");
	}
	
	/**
	 * 只适应用于没有引用关系的类
	 * @param clazz
	 * @param sql
	 * @param params
	 * @return
	 */
	public <T> List<T> executeQuery(Class<T> clazz,String sql,Object... params){
		List<T> objects = new ArrayList<T>();
		T obj = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			for(int i=0;i<params.length;i++){
				pstmt.setObject(i+1, params[i]);
			}
			// 执行查询
			rs = pstmt.executeQuery();
			// 获取结果
			while (rs.next()) {
				rsmd = rs.getMetaData();
				int count = rsmd.getColumnCount();
				String[] colName = new String[count];
				for (int i = 0; i < count; i++)
					colName[i] = rsmd.getColumnLabel(i + 1);
				obj = clazz.newInstance();//实例化clazz类
				Method[] ms = clazz.getMethods();//获取到clazz类的所有方法
				for (Method m : ms)
					for (int i = 0; i < colName.length; i++)
						if (m.getName().equalsIgnoreCase("set" + colName[i])) 
							m.invoke(obj, rs.getObject(colName[i]));
				objects.add(obj);
			}
			return objects;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return objects;
	}


	/**
	 * 获取连接对象
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			//加载驱动
			Class.forName(DRIVER);
			//获取连接对象
			conn = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		return conn;
	}
	
	
	/**
	 * 通用的执行增，删，改的方法
	 * 
	 * @param sql 预编译的语句
	 * @param params 参数
	 * @return 受影响的行数
	 */
	public int executeNonQuery(String sql,Object...params) { 
		int rows = -1;
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i+1, params[i]);
			}
			rows = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally{
			closeAll(conn, pstmt, null);
		}
		return rows;
	}
	
	/**
	 * 关闭释放资源
	 * @param conn 连接对象
	 * @param pstmt 预处理语句
	 * @param res 结果集
	 */
	public void closeAll(Connection conn,PreparedStatement pstmt,ResultSet res) {
		try {
			if(res!=null) {
				res.close();
				res = null;
			}
			if(pstmt!=null) {
				pstmt.close();
				pstmt = null;
			}
			if(conn!=null && !conn.isClosed()) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}
	
	public static void updateUserStatus(UserBean ub) {
		try {
			Connection conn = BaseDao.getConnection();
			PreparedStatement ps = conn.prepareStatement("UPDATE USER_INFO SET CLOUDSIZE = ? WHERE ID = ?");
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
	
	public static void main(String[] args) {
		
		BaseDao baseDao = new BaseDao();
		System.out.println("connection:"+BaseDao.getConnection());
//		baseDao.executeNonQuery("insert into USER_INFO(ID,PASSWD,NAME,CLOUDSIZE,PHONENUM,REMARK) values (?,?,?,?,?,?)", new Object[]{"admin","admin","管理员",10240L * 1024 * 1024,"13888888888","我是管理员"});
//		baseDao.executeNonQuery("insert into WEIXIN_INFO(ID,NAME,AGE,SEX,VOCATION,RELATION) values (?,?,?,?,?,?)", new Object[]{1,"andy",20,0,"jack","jack"});
		List<UserBean> users = baseDao.executeQuery(UserBean.class, "select * from user_info");
		for(UserBean u : users){
			System.out.println(u);
		}
	}
	
}
