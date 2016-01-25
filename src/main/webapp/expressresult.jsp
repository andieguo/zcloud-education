<%@ page language="java" import="java.util.*,com.education.experiment.commons.UserBean"
	import="com.education.experiment.commons.UserBean" pageEncoding="UTF-8"%>
<%@ include file="/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
	UserBean ub = (UserBean) request.getSession().getAttribute("user");
%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>我的快递</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/base.css" rel="stylesheet" type="text/css" />
<link href="css/boxSearch.css" rel="stylesheet" type="text/css" />
<link href="css/reportOA.css" rel="stylesheet" type="text/css" />
<link href="css/new-style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="hd-main" style="min-width:1000px;">
	<div class="logo-main" xmlns="http://www.w3.org/1999/xhtml">
		<img src="images/zhineng.png" /><span class="logo">智能EMS速递云系统</span>
	</div>
	<%@ include file="/share/head-user.jsp"%>
</div>
<div class="clearfix1 wrap">
	<div id="Container" style="float:left;width: 100%; height: 100%;min-width:790px;">
		<div class="fns">
			<div id="header-shaw" class="launchweather">
				<%
					String result = (String) request.getAttribute("result");
					if (result != null) {
						List<String> list = (ArrayList<String>) request.getAttribute("list");
				%>
				<div class="title">
					<span class="title-left">智能EMS速递云系统 > 智能分析结果</span>
					<span class="title-right">
					<form action="previewexpress" method="get">
						系统就近为你分析出 <%=list.size()%> 个订单&nbsp;&nbsp;
						<input class="button button-blue" name="" type="submit" value="提取快件" />
						<input type="hidden" id="sign" name="sign" value="1" />
					</form>
					</span>
				</div>
				<div class="panel mb15">
					<div class="panel-body">
						<table class="list" id='tab_running'>
							<thead>
								<tr>
									<th width="15%" >订单号</th>
									<th class="last">地址/备注</th>
								</tr>
							</thead>
							<tbody>
								<%
									int count = 1;
										for (String order : list) {
								%>
								<tr>
									<td>订单<%=count%></td>
									<td><%=order%></td>
								</tr>
								<%
									count++;
										}
								%>
							</tbody>
						</table>
					</div>
				</div>
				<%
					} else {
				%>
				<div class="title">
					<span class="title-left">智能EMS速递云系统 > 智能分析结果</span>
				</div>
				<div class="panel mb15">
					<div class="panel-body">
						<table class="list" id='tab_running'>
							<thead>
								<tr>
									<th width="15%" >订单号</th>
									<th class="last">地址/备注</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td colspan="2">无订单信息可领取。</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<%
					}
				%>
			</div>
		</div>
	</div>
	<%@ include file="/share/express-left.jsp"%>
</div>
<%@ include file="/share/foot.jsp"%>
</body>
</html>
