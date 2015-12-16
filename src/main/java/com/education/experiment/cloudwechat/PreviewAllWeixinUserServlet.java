package com.education.experiment.cloudwechat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.education.experiment.commons.BaseDao;
import com.education.experiment.commons.UserBean;

public class PreviewAllWeixinUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PreviewAllWeixinUserServlet() {
	}

	/*
	 * 处理用户提交的浏览所有微信用户信息的请求,服务端会读取数据信息，然后把这些信息返回给客户端
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request.setCharacterEncoding(Charset.defaultCharset().toString());
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		if (ub != null) {
			try {
				// 开始连接数据库
				Connection conn = BaseDao.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("select * from WEIXIN_INFO");// 读取插入的数据
				List<WeixinUserBean> list = new ArrayList<WeixinUserBean>();
				while (rs.next()) {
					WeixinUserBean wub = new WeixinUserBean();
					wub.setId(rs.getInt(1));
					wub.setName(rs.getString(2));
					wub.setAge(rs.getInt(3));
					if (rs.getInt(4) == 1) {
						wub.setSex("男");
					} else {
						wub.setSex("女");
					}
					wub.setVocation(rs.getString(5));
					wub.setFriends(rs.getString(6));
					if (wub.getFriends().length() > 70) {
						wub.setSplitfriends(wub.getFriends().substring(0, 70) + "....");
					} else {
						wub.setSplitfriends(wub.getFriends());
					}
					list.add(wub);
				}
				request.setAttribute("allweixinuser", list);
				st.close();
				rs.close();
				conn.close();
				// 读取数据信息完成，然后返回给客户端
				request.getRequestDispatcher("/weixinalluser.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				request.getRequestDispatcher("/error.jsp?result=查询数据出现异常,请联系管理员!").forward(request, response);
			}
		} else {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
}
