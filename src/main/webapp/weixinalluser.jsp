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
			<div id="header-shaw" class="launchweather">
			<%
				List<WeixinUserBean> list = (List<WeixinUserBean>) request.getAttribute("allweixinuser");
			%>
			<form action="" method="get">
				<div class="title">
					<span class="title-left">微信关系分析系统 > 微信用户信息</span>
					<span class="title-right" id="filecount">当前找到 <%=list.size()%> 条结果</span>
				</div>
				<div class="panel mb15">
					<!-- <div class="panel-title">正在进行的任务</div> -->
					<div class="panel-body">
						<table class="list" id='tab_running'>
							<thead>
								<tr>
									<th>微信ID</th>
									<th>姓名</th>
									<th>年龄</th>
									<th>性别</th>
									<th>职业</th>
									<th width="42%" class="last">好友</th>
								</tr>
							</thead>
							<tbody>
								<%
									for (WeixinUserBean wub : list) {
								%>
								<tr>
									<td><%=wub.getId()%></td>
									<td><%=wub.getName()%></td>
									<td><%=wub.getAge()%></td>
									<td><%=wub.getSex()%></td>
									<td><%=wub.getVocation()%></td>
									<td><%=wub.getSplitfriends()%></td>
								</tr>
								<%
									}
								%>
							</tbody>
						</table>
					</div>
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
