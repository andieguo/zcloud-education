<%@ page language="java" import="com.education.experiment.commons.UserBean" pageEncoding="UTF-8"%>
<%
	UserBean ub = (UserBean) request.getSession().getAttribute("user");
%>
<div class="right-main">
	<%=ub.getUserId()%>，欢迎您！&nbsp;&nbsp;云盘容量：<%=ub.getCloudSizeString()%>&nbsp;&nbsp;<a href="loginout">[退出]</a>
</div>