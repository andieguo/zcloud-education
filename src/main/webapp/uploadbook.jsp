<%@ page language="java" import="java.util.*,com.education.experiment.commons.UserBean" pageEncoding="UTF-8"%>
<%@ include file="/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
	UserBean ub = (UserBean) request.getSession().getAttribute("user");
%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>上传书籍</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/base.css" rel="stylesheet" type="text/css" />
<link href="css/boxSearch.css" rel="stylesheet" type="text/css" />
<link href="css/reportOA.css" rel="stylesheet" type="text/css" />
<link href="css/new-style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.0.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="hd-main" style="min-width:1000px;">
	<div class="logo-main" xmlns="http://www.w3.org/1999/xhtml">
		<img src="images/book.png" /><span class="logo">图书馆图书管理系统</span>
	</div>
	<%@ include file="/share/head-user.jsp"%>
</div>
<div class="clearfix1 wrap">
	<div id="Container" style="float:left;width: 100%; height: 100%;min-width:790px;">
		<div class="fns">
			<div id="header-shaw" class="launchweather">
				<div class="title">
					<span class="title-left">图书馆图书管理系统 > 书籍上传</span>
				</div>
				<div class="panel mb15">
					<div class="panel-title">书籍上传</div>
					<div class="panel-body">
						<form action="uploadbook" enctype="multipart/form-data" method="post">
						<table class="upload">
							<tr>
								<th>书名：</th>
								<td>
									<input class="input-text" name="name" type="text" value="" />
								</td>
							</tr>
							<tr>
								<th>作者：</th>
								<td>
									<input class="input-text" name="author" type="text" value="" />
								</td>
							</tr>
							<tr>
								<th>出版日期：</th>
								<td>
									<div class="input dateTime input-text">
										<input name="publishdate" id="publishdate" class="Wdate input"
											type="text" style="cursor: pointer;height: 18px;" value=""
											onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
									</div>
								</td>
							</tr>
							<tr>
								<th>书本文件：</th>
								<td>
									<div class="input-browse">
										<script type="text/javascript" src="js/file_uploader.js"></script>
										<input class="input-left" type="text" id="text_box1" /><span class="input-right">浏 览</span><input class="input-top" name="myfile" type="file" id="file_uploader1" />（编码格式：UTF-8）
									</div>
								</td>
							</tr>
							<tr>
								<th></th>
								<td>
									<input class="button button-blue" name="" type="submit" value="上 传" /><input class="button button-default" name="" id="btnCancel" type="reset" value="重 置" />
								</td>
							</tr>
						</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/share/book-left.jsp"%>
</div>
<%@ include file="/share/foot.jsp"%>
</body>
</html>
