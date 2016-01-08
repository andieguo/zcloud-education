<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>云文件上传</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/base.css" rel="stylesheet" type="text/css" />
<link href="css/boxSearch.css" rel="stylesheet" type="text/css" />
<link href="css/reportOA.css" rel="stylesheet" type="text/css" />
<link href="css/new-style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="hd-main" style="min-width:1000px;">
	<div class="logo-main" xmlns="http://www.w3.org/1999/xhtml">
		<img src="images/geren.png" /><span class="logo">个人私有存储系统</span>
	</div>
</div>
<div class="clearfix1 wrap">
	<div id="Container" style="float:left;width: 100%; height: 100%;min-width:790px;">
		<div class="fns">
			<div id="header-shaw" class="launchweather">
				<div class="title">
					<span class="title-left">个人私有存储系统 > 云文件上传</span>
				</div>
				<div class="panel mb15">
					<div class="panel-body">
						<form action="upload" enctype="multipart/form-data" method="post">
						<table class="upload">
							<tr>
								<th>文件一：</th>
								<td>
									<div class="input-browse">
										<script type="text/javascript" src="js/file_uploader.js"></script>
										<input class="input-left" type="text" id="text_box1" /><span class="input-right">浏 览</span><input class="input-top" name="myfile" type="file" id="file_uploader1" />
									</div>
								</td>
							</tr>
							<tr>
								<th>文件二：</th>
								<td>
									<div class="input-browse">
										<script type="text/javascript" src="js/file_uploader.js"></script>
										<input class="input-left" type="text" id="text_box2" /><span class="input-right">浏 览</span><input class="input-top" name="myfile" type="file" id="file_uploader2" />
									</div>
								</td>
							</tr>
							<tr>
								<th></th>
								<td>
									<input class="button button-blue" name="" type="submit" value="上 传" /><input class="button button-default" name="" id="btnCancel" type="reset" value="重 置" />
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/share/storage-left.jsp"%>
</div>
<%@ include file="/share/foot.jsp"%>
</body>
</html>
