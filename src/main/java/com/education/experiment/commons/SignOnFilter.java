package com.education.experiment.commons;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignOnFilter implements Filter {
	FilterConfig fc;

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest hreq = (HttpServletRequest) request;
		HttpServletResponse hres = (HttpServletResponse) response;
		HttpSession session = hreq.getSession();
		if (session.getAttribute("user") == null) {
			if (hreq.getRequestURI().equals(hreq.getContextPath() + "/login.jsp")) {
				chain.doFilter(request, response);
			} else if (hreq.getRequestURI().equals(hreq.getContextPath() + "/registerUser.jsp")) {
				chain.doFilter(request, response);
			} else {
				hres.sendRedirect(hreq.getContextPath() + "/login.jsp");
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fc) throws ServletException {
		// TODO Auto-generated method stub
		this.fc = fc;
	}

}