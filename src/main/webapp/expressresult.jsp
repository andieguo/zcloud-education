<%@ page language="java" import="java.util.*"
	import="com.education.experiment.commons.UserBean" pageEncoding="UTF-8"%>
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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>云服务平台</title>
		<link href="css/base.css" rel="stylesheet" type="text/css" />
		<link href="css/module/header.css" rel="stylesheet" type="text/css" />
		<link href="css/module/title.css" rel="stylesheet" type="text/css" />
		<link href="css/module/pageSkin.css" rel="stylesheet" type="text/css" />
		<link href="css/module/navi002.css" rel="stylesheet" type="text/css" />
		<link href="css/module/reportOA.css" rel="stylesheet" type="text/css" />
		<link href="css/module/boxSearch.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquery-1.8.0.js"></script>
		<!--ie6png图片透明补丁-->
		<!--[if IE 6]>
    <script type="text/javascript" src="js/DD_belatedPNG.js"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('#header,.cur,.btn,.iconUser,.iconDepartment,.iconIdea,.iconKey,.iconQuit,.iconSet,.iconAbout,.iconTool,.recall,.case,.approve,.statistics,.https,.file,.compareForm,.webmaster,.diary,#up,#down,.setKeywords a,.icon,.select,.iconMore,.barSearch'); //放置css选择器
    </script>
    <script type="text/javascript">
        //防止抖动
        // <![CDATA[
        if ((window.navigator.appName.toUpperCase().indexOf("MICROSOFT") >= 0) && (document.execCommand)) try {
            document.execCommand("BackgroundImageCache", false, true);
        }
        catch(e) {}
        // ]]>
    </script>
<![endif]-->
		<!--ie6png图片透明补丁-->
	</head>

	<body id="wrapper">
		<script src="js/highcharts.js"></script>
		<!--主体开始-->
		<div id="content" class="clearfix">
			<div id="title">
				<h1>
					微信人物关系分析云
				</h1>
			</div>

			<!--列表开始-->
			<div id="boxSearch">
				<div class="subNavi">
					<ul>
						<li>
							<a href='downloadexpress.jsp'>示例文件下载</a>
						</li>
						<%
							UserBean user = (UserBean) session.getAttribute("user");
							if (user.getUserId().equals("admin")) {
						%>
						<li>
							<a href='launchexpressparsing.jsp'>启动智能分析</a>
						</li>
						<li>
							<a href='suspendexpressparsing.jsp'>停止智能分析</a>
						</li>
						<%
							}
						%>
						<li>
							<a href='uploadexpress.jsp'>快递上传</a>
						</li>
						<li>
							<a href='gprsupdate.jsp'>我的GPRS</a>
						</li>
						<li class="current">
							<a href='previewexpress?sign=0'>我的快递</a>
						</li>
					</ul>
				</div>
				<div id="selectSet">
					<%
						String result = (String) request.getAttribute("result");
						if (result != null) {
							List<String> list = (ArrayList<String>) request
									.getAttribute("list");
					%>
					<dl class="list clearfix">
						<span>系统就近为你分析出 <%=list.size()%> 个订单：</span>
						<br />
						<%
							int count = 1;
								for (String order : list) {
						%>
						<span class="input text">订单<%=count%>:</span>&nbsp;&nbsp;<%=order%>
						<br>
						<%
							count++;
								}
						%>

					</dl>
					<br />
					<%
						} else {
					%>
					无订单信息可领取。
					<br>
					<br>
					<br>
					<%
						}
					%>
					<form action="previewexpress" method="get">
						<div class="foot">
							<span class="submit"><input name="" type="submit"
									value="提取快件" /> <input type="hidden" id="sign" name="sign"
									value="1" /> </span>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div id="footer"></div>
	</body>
</html>