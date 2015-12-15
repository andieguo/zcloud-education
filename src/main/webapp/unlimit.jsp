<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<link href="css/master/error.css" rel="stylesheet" type="text/css" />
		<!--ie6png图片透明补丁-->
		<!--[if IE 6]>
    <script type="text/javascript" src="js/DD_belatedPNG.js"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('#error'); //放置css选择器
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
<![endif]–->
<!--ie6png图片透明补丁-->
		<script language="Javascript">
	//屏蔽鼠标右键、Ctrl+R、shift+F10、F5刷新、退格键  

	/** 
	 * 屏蔽按键刷新 
	 */
	function KeyDown() {
		//alert(22);  
		if ((window.event.altKey) && ((window.event.keyCode == 37) || //屏蔽 Alt+ 方向键 ←  
		(window.event.keyCode == 39))) { //屏蔽 Alt+ 方向键 → 
			event.returnValue = false;
		}
		if (event.keyCode == 116) { //屏蔽 F5 刷新键  
			event.keyCode = 0;
			event.returnValue = false;
		}
		if ((event.ctrlKey) && (event.keyCode == 82)) { //屏蔽 Ctrl+R  
			event.returnValue = false;
		}
		if ((event.shiftKey) && (event.keyCode == 121)) { //屏蔽 shift+F10  
			event.returnValue = false;
		}
	}
</script>
	</head>

	<body id="wrapper" onkeydown="KeyDown()"
		oncontextmenu="event.returnValue=false">
		<!--提示层开始-->
		<div id="error">
			<h1>
				操作成功！
			</h1>
			<%
				String result = request.getParameter("result");
				if (result != null) {
			%>
			<%=result%>
			<%
				} else {
			%>
			<p>
				点击下面链接浏览HDFS集群文件
			</p>
			<p>
				<a href='http://master:50070/nn_browsedfscontent.jsp'>hdfs://master:9100</a>
			</p>
			返回上一层页面
			<%
				}
			%>
			<p>
				<span class="submit"><input name="" type="button" value="返 回"
						onclick="history.go(-1)" /> </span>
			</p>
		</div>
		<!--提示层结束-->
	</body>
</html>
