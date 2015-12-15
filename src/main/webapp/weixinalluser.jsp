<%@ page language="java" import="java.util.*"
	import="com.education.experiment.cloudwechat.WeixinUserBean" pageEncoding="UTF-8"%>
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
		<link href="css/master/appList.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquery-1.8.0.js"></script>
		<!--ie6png图片透明补丁-->
		<!--[if IE 6]>
    <script type="text/javascript" src="js/DD_belatedPNG.js"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('#header,.cur,.btn,.iconUser,.iconDepartment,.iconIdea,.iconKey,.iconQuit,.iconSet,.iconAbout,.iconTool,.recall,.case,.approve,.statistics,.https,.file,.compareForm,.webmaster,.diary,#down,#up'); //放置css选择器
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
		<!--头部导航下拉菜单效果-->
		<script type="text/javascript" src="js/top_navi.js"></script>
		<!--头部导航下拉菜单效果-->
		<!--导航条滚动效果-->
		<script type="text/javascript" src="js/navi002.js"></script>
		<!--导航条滚动效果-->
		<!--选择菜单-->
		<script type="text/javascript" src="js/selectbox.js"></script>
	</head>

	<body id="wrapper">
		<!--主体开始-->
		<div id="content" class="clearfix">
			<div id="title">
				<div class="menu">
					<ul>
						<li>
							<a href="uploadweixinsearcher.jsp">返回</a>
						</li>
					</ul>
				</div>
				<h1>
					微信用户信息表预览
				</h1>
			</div>
			<!--搜索结果列表开始-->
			<%
				List<WeixinUserBean> list = (List<WeixinUserBean>) request
						.getAttribute("allweixinuser");
			%>
			<div id="listSearch">
				<!--列表头部-->
				<div id="title">
					<h2>
						用户条数
					</h2>
					<div id="amount">
						<span class="result">当前找到 <%=list.size()%> 条结果</span>
					</div>
				</div>
				<!--列表头部-->
				<!--列表开始-->
				<div id="list" class="relative">
					<div class="title clearfix">
						<span class="li1">微信ID</span>
						<span class="li1">姓名</span>
						<span class="li1">年龄</span>
						<span class="li1">性别</span>
						<span class="li1">职业</span>
						<span class="li6">好友</span>
					</div>
					<%
						for (WeixinUserBean wub : list) {
					%>
					<ul>
						<!--列表项目-->
						<li class="li clearfix">
							<div class="li1">
								<h4 class="text_overflow" title="">
									<center>
										<%=wub.getId()%>
									</center>
								</h4>
							</div>
							<div class="li1">
								<h4 class="text_overflow" title="">
									<center>
										<%=wub.getName()%>
									</center>
								</h4>
							</div>
							<div class="li1">
								<h4 class="text_overflow" title="">
									<center>
										<%=wub.getAge()%>
									</center>
								</h4>
							</div>
							<div class="li1">
								<h4 class="text_overflow" title="">
									<center>
										<%=wub.getSex()%>
									</center>
								</h4>
							</div>
							<div class="li1">
								<h4 class="text_overflow" title="">
									<center>
										<%=wub.getVocation()%>
									</center>
								</h4>
							</div>
							<div class="li6">
								<h4 title="<%=wub.getFriends()%>">
									<%=wub.getSplitfriends()%>
								</h4>
							</div>
						</li>
						<!--列表项目-->
					</ul>
					<%
						}
					%>
					<br />
					<br />
				</div>
				<!--列表结束-->
			</div>
			<!--搜索结果列表结束-->
		</div>
		<!--主体结束-->
		<!--尾部开始-->
		<div id="footer"></div>
		<!--尾部结束-->
		<!--返回顶部-->
	</body>
</html>