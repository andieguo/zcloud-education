<%@ page language="java" import="java.util.*"
	import="com.education.experiment.cloudwechat.WeixinResultBean"
	import="com.education.experiment.commons.UserBean" pageEncoding="UTF-8"%>
<%@ include file="/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>微信分析结果查看</title>
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
				<!--列表开始-->
				<%
					String result = (String) request.getAttribute("result");
					if (result != null) {
						List<WeixinResultBean> list = (ArrayList<WeixinResultBean>) request.getAttribute("list");
				%>
				<div class="title">
					<span class="title-left">微信关系分析系统 > 微信结果查看</span>
					<span class="title-right">系统为您分析出 <%=list.size()%> 条结果</span>
				</div>
				<%
					for (WeixinResultBean wrb : list) {
				%>
				<div class="panel mb15">
					<div class="panel-body">
						<table class="list">
							<thead>
								<tr>
									<th width="20%">通信人</th>
									<th class="last"><%=wrb.getLinkman()%></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>开始时间</td>
									<td><%=wrb.getBegintime()%></td>
								</tr>
								<tr>
									<td>结束时间</td>
									<td><%=wrb.getEndtime()%></td>
								</tr>
								<tr>
									<td>通信地点</td>
									<td><%=wrb.getPlace()%></td>
								</tr>
								<tr>
									<td valign="top">通信内容</td>
									<td><%=wrb.getLinkcontent()%></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<%
					}
				%>
				<%
					} else {
				%>
				当前无分析数据结果可供浏览，可能分析数据任务正在执行当中。
				<%
					}
				%>
			</div>
		</div>
	<%@ include file="/share/weixin-left.jsp"%>
	</div>
</div>
<%@ include file="/share/foot.jsp"%>
</body>
</html>
