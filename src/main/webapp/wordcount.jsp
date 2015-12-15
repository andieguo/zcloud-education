<%@ page language="java" import="java.util.*"
	import="com.education.bean.UserBean" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<body>
		<form action="wordcount" enctype="multipart/form-data" method="post">
			<input type="file" name="words" />
			上传单词文件
			<br />
			<input type="file" name="filter" />
			上传过滤文件
			<br />
			是否区分大小写：
			<select name="word">
				<option value="true">
					是
				</option>
				<option value="false">
					否
				</option>
			</select>
			<br />
			<br>
			<input type="submit" value="开始计算" />
		</form>
	</body>
</html>