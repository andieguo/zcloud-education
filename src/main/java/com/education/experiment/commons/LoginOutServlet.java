package com.education.experiment.commons;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginOutServlet extends HttpServlet {

	/** 
     * @category 退出登录的Servlet,注销 
     * @author Bird 
     */  
    private static final long serialVersionUID = 1L;  
  
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        HttpSession session = request.getSession(false);//防止创建Session  
        if(session == null){  
            response.sendRedirect("login.jsp"); 
            return;  
        }  
          
        session.removeAttribute("user");  
        response.sendRedirect("login.jsp");  
    }  
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
  
    }  
}
