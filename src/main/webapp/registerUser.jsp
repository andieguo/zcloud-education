<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>云服务平台--用户信息注册</title>
		<link href="css/layout/register.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquery-1.8.0.js"></script>
		<!--ie6png图片透明补丁-->
		<!--[if IE 6]> 
<script type="text/javascript" src="js/DD_belatedPNG.js"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix("#header,.li.cur,.li .link .btn,#naviWebBank a,.statistics,.iconAbout,.iconSet,.btn.current input,.dateTime input,.dateTime2 input,.checkAll.more,#menu .li .box a,#submenu .li .box a,.tit,.dialogLayer,.li4 span b"); //放置css选择器
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
		<script type="text/javascript">
	$(document).ready(function() { //文档加载
		$("#header .li").hover(function() {
			$(this).addClass("current"); //鼠标经过添加hover样式
		}, function() {
			$(this).removeClass("current"); //鼠标离开移除hover样式
		});
	});
</script>
<!--头部导航下拉菜单效果-->
<script type="text/javascript">
	function isRightSize(num)
	{
		if(isNaN(num)){
			return false;
		}
		if (parseInt(num)<1025&&parseInt(num)>0)
		{
			return true;
		}else
		{
			return false;
		}
	}
	$(document).ready(function() {
			$("#btnSave").click(
				function() {
					var userId = $("input:text[name='tbUserId']").val();
					var phone = $("input:text[name='tbIDCard']").val();
					var userName = $("input:text[name='tbUserName']").val();
					var pwd = $("input:password[name='tbPwd']").val();
					var rePwd = $("input:password[name='tbRePwd']").val();
					var cloudsize = $("input:text[name='tbCloudSize']").val();
					var remark = $("#tbRemark").val();
					if (pwd != rePwd) {
						alert("重复密码错误!");
					} else if (userId == ""
							|| userName == ""
							|| pwd == "" || rePwd == ""||cloudsize=="") {
						alert("信息不完整!");
					}
					else if (!isRightSize(cloudsize)) {
						alert("申请空间大小格式不正确！");
						$("input:text[name='tbCloudSize']")
								.focus();
					} else {
						var url = "<%=basePath%>/registerUser?";
						url += "userId=" + userId;
						url += "&pwd=" + pwd;
						url += "&userName=" + userName;
						url += "&phone=" + phone;
						url += "&remark=" + remark;
						url += "&cloudSize=" + cloudsize;
						$.post(url,function(data) {
							if (data == "error") {
								alert("注册失败!");
							} else if (data == "usererror") {
								alert("用户ID重复,注册失败!");
							} else {
								alert("注册成功!");
							}
						});
					}
				});
		});
</script>
	</head>

	<body id="wrapper">
		<!--主体开始-->
		<div id="content" class="clearfix">
			<div id="title" class="clearfix">
				<h1>
					用户注册
				</h1>
				<div class="menu">
					<ul>
						<li>
							<a href="login.jsp">返回</a>
						</li>
					</ul>
				</div>
			</div>
			<!--基本信息开始-->
			<div class="userInfo">
				<div class="userTitle clearfix">
					<h2>
						基本资料
					</h2>
				</div>
				<div class="userList">
					<dl>
						<dt>
							<b>*</b>账号：
						</dt>
						<dd>
							<span class="input text"><input name="tbUserId"
									type="text" /> </span>
						</dd>
					</dl>
					<dl>
						<dt>
							<b>*</b>密码：
						</dt>
						<dd>
							<span class="input text"><input name="tbPwd"
									type="password" /> </span>
						</dd>
					</dl>
					<dl>
						<dt>
							<b>*</b>确认密码：
						</dt>
						<dd>
							<span class="input text"><input name="tbRePwd"
									type="password" /> </span>
						</dd>
					</dl>
					<dl>
						<dt>
							<b>*</b>真实姓名：
						</dt>
						<dd>
							<span class="input text"><input name="tbUserName"
									type="text" /> </span>
						</dd>
					</dl>
					<dl>
						<dt>
							<b>*</b>申请云空间大小：
						</dt>
						<dd>
							<span class="input text"><input name="tbCloudSize"
									type="text" /> </span>(单位：MB，不得大于1GB)
						</dd>
					</dl>
					<dl>
						<dt>
							联系电话：
						</dt>
						<dd>
							<span class="input text"><input name="tbIDCard"
									type="text" /> </span>
						</dd>
					</dl>
					<dl>
						<dt>
							备注：
						</dt>
						<dd>
							<span class="input textArea"><textarea name="tbRemark"
									id="tbRemark" cols="" rows=""></textarea> </span>
						</dd>
					</dl>
				</div>
			</div>
			<!--基本信息结束-->
			<div class="submitBtn">
				<span><input name="" type="submit" id="btnSave" value="注 册" />
				</span>
			</div>
			<div class="userInfo" />
		</div>
		<!--主体结束-->

		<!--尾部开始-->
		<div id="footer"></div>
		<!--尾部结束-->
		<!--返回顶部-->
		<script type="text/javascript" src="js/goto_top.js"></script>
		<!--返回顶部-->
	</body>
</html>
