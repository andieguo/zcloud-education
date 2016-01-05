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
			<!--列表开始-->
			<div id="boxSearch">
				<div class="subNavi">
					<ul>
						<li>
							<a href='downloadweixin.jsp'>示例文件下载</a>
						</li>
						<li>
							<a href='uploadweixin.jsp'>模拟数据上传</a>
						</li>
						<li>
							<a href='uploadweixinsearcher.jsp'>分析条件上传</a>
						</li>
						<%
							UserBean user = (UserBean) session.getAttribute("user");
							if (user.getUserId().equals("admin")) {
						%>
						<li>
							<a href='deleteweixin.jsp'>模拟数据删除</a>
						</li>
						<li>
							<a href='deleteparsing.jsp'>分析条件删除</a>
						</li>
						<li>
							<a href='previewweixindata?sign=0'>分析数据云计算</a>
						</li>
						<%
							}
						%>
						<li class="current">
							<a href='previewweixinresult'>分析结果查看</a>
						</li>
					</ul>
				</div>
				<div id="selectSet">
					<%
						String result = (String) request.getAttribute("result");
						if (result != null) {
							List<WeixinResultBean> list = (ArrayList<WeixinResultBean>) request.getAttribute("list");
					%>
					<dl class="list clearfix">
						<span>系统为您分析出 <%=list.size()%> 条结果：</span>
						<br />
						<%
							for (WeixinResultBean wrb : list) {
						%>
						<span class="input text">通信人:</span>&nbsp;&nbsp;<%=wrb.getLinkman()%>
						<br>
						<span class="input text"></>开始时间:</span>&nbsp;&nbsp;<%=wrb.getBegintime()%>
						<br>
							<span class="input text">结束时间:</span>&nbsp;&nbsp;<%=wrb.getEndtime()%>
						<br>
						<span class="input text">通信地点:</span>&nbsp;&nbsp;<%=wrb.getPlace()%>
						<br>
						<span class="input text">通信内容:</span>&nbsp;&nbsp;<%=wrb.getLinkcontent()%>
						<br>
						-----------------------------------------<br>
									<%
										}
									%>
								
					</dl>
					<br />
					<%
						} else {
					%>
					当前无分析数据结果可供浏览，可能分析数据任务正在执行当中。
					<br>
						<br>
							<br>
								<%
									}
								%>
							
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/share/weixin-left.jsp"%>
</div>
</div>
<%@ include file="/share/foot.jsp"%>
</body>
</html>
