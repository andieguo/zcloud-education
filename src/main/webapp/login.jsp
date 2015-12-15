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
<title>云服务平台--用户登录</title>
<link href="css/master/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.0.js"></script>
<!--ie6png图片透明补丁-->
<!--[if IE 6]>
    <script type="text/javascript" src="js/DD_belatedPNG.js"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('#login,.selectbox,.submit'); //放置css选择器
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
<!--选择菜单-->
<script type="text/javascript" src="js/selectbox.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#mySle').selectbox();
	});
</script>
<!--选择菜单-->
<!--点击输入框更换样式效果-->
<script type="text/javascript">
	$(function() {
		show('账号', 'show1');
		show2('密码', 'show');
		function show(title, id) {
			$('#' + id).focus(function() {
				if ($(this).val() == title) {
					$(this).val('').addClass("textInput");
				}
			}).blur(function() {
				if ($(this).val() == '') {
					$(this).val(title).removeClass("textInput");
				}
			});
		}
		function show2(title, id) {
			$('#' + id).focus(function() {
				$('#' + id).hide();
				$('#hide').show().addClass("textInput");
				$('#hide').focus();
			});
			$('#hide').blur(function() {
				if ($('#hide').val() == '') {
					$('#hide').hide();
					$('#' + id).show().removeClass("textInput");
				}
			});
		}
	});
</script>
<script type="text/javascript">
	$(document).ready(
			function() {
				$("#btnLogin").click(
						function() {
							var loginId = $("#show1").val();
							var loginPwd = $("#hide").val();
							var userListStr = "";
							if ($.trim(loginId) == "") {
								alert("用户名不能为空，请输入！");
								$("#show1").focus();
							} else if ($.trim(loginPwd) == "") {
								alert("密码不能为空，请输入！");
								$("#hide").focus();
							} else {
								$.post("<%=basePath%>/login?loginId="+ loginId + "&loginPwd=" + loginPwd,
										function(data) {
											if (data == "1") {
												//location.href="jsp/search.jsp";
												//	location.href="common/countDataList.jsp";
												location.href = "index.jsp";
											} else if (data == "2") {
												alert("用户名密码错误，请重新输入！");
												$("#hide").val("");
												$("#show1").select();
											} else if (data == "0"|| data == "error"|| data == "") {
												alert("系统连接数据库有异常,请联系管理员！");
												$("#hide").val("");
												$("#show1").select();
											}
										});
							}
						});
				$("#btnCancel").click(function() {
					$("#show1").val("");
					$("#hide").val("");
					$("#show1").focus();
				});
			});

	function EnterforPass() {
		if (window.event.keyCode == 13) {
			$("#show1").click();
		}
	}

	if (top.location != self.location) {
		top.location = self.location;
	}
</script>
<!--点击输入框更换样式效果-->
</head>

<body id="wrapper">
	<!--登录开始-->

	<div id="login">
		<div class="input text">
			<input id="show1" type="text" value="账号" /> <input id="hide1"
				class="none" type="text" value="" />
		</div>
		<div class="error"></div>
		<div class="input password">
			<input id="show" type="text" value="密码" /> <input id="hide"
				class="none" type="password" value="" />
		</div>
		<div class="error"></div>
		<div class="input select relative">
			<select style="display: none;" name="mySle" id="mySle">
				<option selected="selected" value="1">普通身份</option>
				<option value="2">管理员身份</option>
			</select>
		</div>
		<div class="submit">
			<input class="pointer" id="btnLogin" type="submit" value="登 录" />
		</div>
		<br /> <a href='registerUser.jsp'
			style='float: right; color: #FFFF6E; margin-right: 20px;'>注册账号</a>
	</div>
	<!--登录结束-->
</body>
</html>
