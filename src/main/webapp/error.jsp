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

	<body id="wrapper">
		<!--提示层开始-->
		<div id="error" onkeydown="KeyDown()"
			oncontextmenu="event.returnValue=false">
			<h1>
				操作失败！
			</h1>
			<p>
				<h3>
					错误信息：
				</h3>
			</p>
			<p><%=new String(request.getParameter("result").getBytes(
					"UTF-8"), "UTF-8")%>
			</p>
			<p>
				<span class="submit"><input name="" type="button" value="返 回"
						onclick="history.go(-1)" /> </span>
			</p>
		</div>
		<!--提示层结束-->
		<div id="footer">
		</div>
	</body>
</html>
