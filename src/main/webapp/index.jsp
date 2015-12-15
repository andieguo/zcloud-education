<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>云计算实训</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
</head>
<style>
body {
background:#f7f7f7;
}
</style>
<body>
<div class="hd-main" style="min-width:1000px;">
	<div class="logo-main" xmlns="http://www.w3.org/1999/xhtml">
	<img width="50" height="50"src="images/logo.png" /><span class="logo">云计算实训项目</span>
	</div>
</div>
<div class="clearfix1 wrap">
	<div class="service-list">
		<br /><br />
		<ul>
			<li>
			<a target="_blank" href="privatestoragecloud.jsp" hidefocus="true" class="inote">个人私有存储</a>
			</li>
			<li>
			<a target="_blank" href="uploadbooks.jsp" hidefocus="true" class="icontacts">图书馆图书管理</a>
			</li>
			<li>
			<a target="_blank" href="downloadweather.jsp" hidefocus="true" class="ialbum">气象数据分析</a>
			</li>
			<li>
			<a target="_blank" href="downloadexpress.jsp" hidefocus="true" class="ipan">智能EMS速递云</a>
			</li>
			<li>
			<a target="_blank" href="downloadweixin.jsp" hidefocus="true" class="imsg">微信关系分析</a>
		</ul>
	</div>
</div>
<div class="footer">
<p>
	版权所有© 2013 北京斑步志伟科技公司
</p>
</div>
</body>
</html>
