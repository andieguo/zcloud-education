<%@ page language="java" import="java.util.*"
	import="com.education.experiment.commons.UserBean" pageEncoding="UTF-8"%>
<%@ include file="/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>上传快递信息</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/base.css" rel="stylesheet" type="text/css" />
<link href="css/boxSearch.css" rel="stylesheet" type="text/css" />
<link href="css/reportOA.css" rel="stylesheet" type="text/css" />
<link href="css/new-style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.0.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="hd-main" style="min-width:1000px;">
	<div class="logo-main" xmlns="http://www.w3.org/1999/xhtml">
		<img src="images/zhineng.png" /><span class="logo">智能EMS速递云系统</span>
	</div>
</div>
<div class="clearfix1 wrap">
	<div id="Container" style="float:left;width: 100%; height: 100%;min-width:790px;">
		<div class="fns">
			<div id="header-shaw" class="launchweather">
				<div class="title">
					<span class="title-left">智能EMS速递云系统 > 快递文件上传</span>
				</div>
				<div class="panel mb15">
					<div class="panel-title">快递文件上传</div>
					<div class="panel-body">
						<form action="uploadexpressinfo" method="post">
						<table class="upload">
							<tr>
								<th>经度：</th>
								<td>
									<input class="input-text" name="longitude" type="text" value="" />
								</td>
							</tr>
							<tr>
								<th>纬度：</th>
								<td>
									<input class="input-text" name="latitude" type="text" value="" />
								</td>
							</tr>
							<tr>
								<th>地址：</th>
								<td>
									<input class="input-text" name="address" type="text" value="" />
								</td>
							</tr>
							<tr>
								<th>备注：</th>
								<td>
									<textarea class="input-text" rows="5" name="remark"></textarea>
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
	</div>
	<%@ include file="/share/express-left.jsp"%>
</div>
<%@ include file="/share/foot.jsp"%>
</body>
</html>
