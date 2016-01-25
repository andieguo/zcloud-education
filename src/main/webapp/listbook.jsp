<%@ page language="java" import="java.util.*,com.education.experiment.commons.UserBean" pageEncoding="UTF-8"%>
<%@ include file="/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
	UserBean ub = (UserBean) request.getSession().getAttribute("user");
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>书籍列表</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/base.css" rel="stylesheet" type="text/css" />
<link href="css/boxSearch.css" rel="stylesheet" type="text/css" />
<link href="css/reportOA.css" rel="stylesheet" type="text/css" />
<link href="css/new-style.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.8.0.js"></script>
<script src="js/common/checkbox.js"></script>
<script src="js/common/drawtable.js"></script>
<script type="text/javascript">

var drawBookTable;

$(function(){
	var homeDir = "/tomcat/experiment/librarycloud/uploaddata";
    drawBookTable = drawTable(homeDir,"book");
    drawBookTable.getFileSystem();
});

</script>
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
			<div id="header-shaw" style="background-color: #fff;height: 542px;">
			<form action="" method="get">
				<div class="module-history-list">
					<span class="history-list-dir">图书馆图书管理系统 > 书籍列表</span>
					<span class="history-list-tips" id="filecount"></span>
				</div>
				<div class="list-view-header">
					<ul class="list-cols">
						<li class="col first-col" style="width: 60%;">
							<input class="check" name="chkAll" id="chkAll" onClick="ChkAllClick('filename','chkAll')" type="checkbox"/>
							<span class="text">文件名</span>
							<span class="order-icon"></span>
							<a class="g-button" onClick="drawBookTable.downloadAllAction()">
								<span class="g-button-right">
									<em class="icon icon-download-gray" title="下载"></em>
									<span class="text">下载</span>
								</span>
							</a>
							<a class="g-button" onClick="drawBookTable.deleteAllAction()">
								<span class="g-button-right">
									<em class="icon icon-delete" title="删除"></em>
									<span class="text">删除</span>
								</span>
							</a>
						</li>
						<li class="col" style="width: 16%;">
							<span class="text">大小</span>
							<span class="order-icon"></span>
						</li>
						<li class="col last-col" style="width: 22%;">
							<span class="text">修改日期</span>
							<span class="order-icon"></span>
						</li>
					</ul>
				</div>
				<div id="tab_filesystem" class="list-view-container">
					
				</div>
			</form>
			</div>
		</div>
	</div>
	<%@ include file="/share/book-left.jsp"%>
</div>
</div>
<%@ include file="/share/foot.jsp"%>
</body>
</html>
