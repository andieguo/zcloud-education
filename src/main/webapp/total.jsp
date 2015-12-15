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

		<!--主体开始-->
		<form action="wordcount" enctype="multipart/form-data" method="post">
			<div id="content" class="clearfix">
				<div id="title">
					<h1>
						Hadoop云测试系统
					</h1>
				</div>

				<!--列表开始-->
				<div id="boxSearch">
					<div class="subNavi">
						<ul>
							<li class="current">
								<a>Map/Reduce计算</a>
							</li>
							<li>
								<a href='upload.jsp'>HDFS文件上传</a>
							</li>
							<li>
								<a href='download.jsp'>HDFS文件下载</a>
							</li>
						</ul>
					</div>
					<div id="selectSet">
						<!--默认选项开始-->
						<div id="normal">
							<dl class="list clearfix">
								<dt class="dt">
									单&nbsp;词&nbsp;文&nbsp;件&nbsp;：
								</dt>
								<dd class="dd">
									<script type="text/javascript" src="js/file_uploader.js"></script>
									<span class="text input relative"> <input type="text"
											id="text_box1" class="text_box" /><a class="file_btn">浏
											览</a> <input name="words" type="file" id="file_uploader1"
											class="file_uploader" /> </span>
								</dd>
							</dl>
							<dl class="list clearfix">
								<dt class="dt">
									过&nbsp;滤&nbsp;文&nbsp;件&nbsp;：
								</dt>
								<dd class="dd">
									<script type="text/javascript" src="js/file_uploader.js"></script>
									<span class="text input relative"> <input type="text"
											id="text_box2" class="text_box" /><a class="file_btn">浏
											览</a> <input name="filter" type="file" id="file_uploader2"
											class="file_uploader" /> </span>
								</dd>
							</dl>
							<dl class="list clearfix">
								<dt class="dt">
									区分大小写：
								</dt>
								<dd class="dd">
									<span class="text input relative"> 
									<select name="sensitive">
											<option value="true">
												是
											</option>
											<option value="false">
												否
											</option>
										</select> </span>
								</dd>
							</dl>
						</div>
						<!--默认选项结束-->

						<div class="foot">
							<span class="submit"><input name="" type="submit"
									value="开始计算" /> </span>
							<span class="reset"><input name="" type="reset"
									value="重 置" /> </span>
						</div>

					</div>

				</div>

				<!--列表结束-->
				<!--列表尾部开始-->

				<!--列表尾部结束-->
			</div>
		</form>
		<!--主体结束-->
		<!--尾部开始-->
		<div id="footer"></div>
		<!--尾部结束-->
	</body>
</html>
