<%@ page language="java" import="java.util.*"
	import="com.education.experiment.commons.UserBean,com.education.experiment.cloudwechat.WeixinUserBean" pageEncoding="UTF-8"%>
<%@ include file="/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>用户数据列表</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/base.css" rel="stylesheet" type="text/css" />
<link href="css/boxSearch.css" rel="stylesheet" type="text/css" />
<link href="css/reportOA.css" rel="stylesheet" type="text/css" />
<link href="css/new-style.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.8.0.js"></script>

</head>
<body>
<div class="hd-main" style="min-width:1000px;">
	<div class="logo-main" xmlns="http://www.w3.org/1999/xhtml">
		<img src="images/weixin.png" /><span class="logo">微信关系分析系统</span>
	</div>
</div>
<div class="clearfix1 wrap">
	<div id="Container" style="float:left;width: 100%; height: 100%;min-width:790px;">
		<div class="fns">
			<div id="header-shaw" style="background-color: #fff;height: 542px;">
			<%
				List<WeixinUserBean> list = (List<WeixinUserBean>) request.getAttribute("allweixinuser");
			%>
			<form action="" method="get">
				<div class="module-history-list">
					<span class="history-list-dir">全部文件</span>
					<span class="history-list-tips" id="filecount">当前找到 <%=list.size()%> 条结果</span>
				</div>
				<div class="list-view-header">
					<ul class="list-cols">
						<li class="col" style="width: 10%;">
							<span class="text">微信ID</span>
							<span class="order-icon"></span>
						</li>
						<li class="col" style="width: 10%;">
							<span class="text">姓名</span>
							<span class="order-icon"></span>
						</li>
						<li class="col" style="width: 10%;">
							<span class="text">年龄</span>
							<span class="order-icon"></span>
						</li>
						<li class="col" style="width: 10%;">
							<span class="text">性别</span>
							<span class="order-icon"></span>
						</li>
						<li class="col" style="width: 10%;">
							<span class="text">职业</span>
							<span class="order-icon"></span>
						</li>
						<li class="col" style="width: 50%;">
							<span class="text">好友</span>
							<span class="order-icon"></span>
						</li>
					</ul>
				</div>
				<div id="tab_filesystem" class="list-view-container">
					<%
						for (WeixinUserBean wub : list) {
					%>
					<dd class="list-view-item">
						<div class="file-name" style="width:10%">
							<div class="text"><%=wub.getId()%></div>
						</div>
						<div class="ctime" style="width:10%"><div class="text"><%=wub.getName()%></div></div>
						<div class="ctime" style="width:10%"><div class="text"><%=wub.getAge()%></div></div>
						<div class="ctime" style="width:10%"><div class="text"><%=wub.getSex()%></div></div>
						<div class="ctime" style="width:10%"><div class="text"><%=wub.getVocation()%></div></div>
						<div class="ctime" style="width:50%"><div class="text"><%=wub.getSplitfriends()%></div></div>
					</dd>
					<%
						}
					%>
				</div>
			</form>
			</div>
		</div>
	</div>
	<%@ include file="/share/weixin-left.jsp"%>
</div>
</div>
<%@ include file="/share/foot.jsp"%>
</body>
</html>
