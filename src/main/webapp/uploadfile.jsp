<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>云</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/base.css" rel="stylesheet" type="text/css" />
<link href="css/boxSearch.css" rel="stylesheet" type="text/css" />
<link href="css/reportOA.css" rel="stylesheet" type="text/css" />
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
			<div id="header-shaw">
				<form action="upload" enctype="multipart/form-data" method="post">
					<div id="selectSet">
						<!--默认选项开始-->
						<div id="normal">
							<dl class="list clearfix">
								<dt class="dt">
									文&nbsp;件&nbsp;一：
								</dt>
								<dd class="dd">
									<script type="text/javascript" src="js/file_uploader.js"></script>
									<span class="text input relative"> <input type="text"
											id="text_box1" class="text_box" /><a class="file_btn">浏
											览</a> <input name="myfile" type="file" id="file_uploader1"
											class="file_uploader" /> </span>
								</dd>
							</dl>
							<dl class="list clearfix">
								<dt class="dt">
									文&nbsp;件&nbsp;二：
								</dt>
								<dd class="dd">
									<script type="text/javascript" src="js/file_uploader.js"></script>
									<span class="text input relative"> <input type="text"
											id="text_box2" class="text_box" /><a class="file_btn">浏
											览</a> <input name="myfile" type="file" id="file_uploader2"
											class="file_uploader" /> </span>
								</dd>
							</dl>
							<br />
						</div>
						<!--默认选项结束-->

						<div class="foot">
							<span class="submit"><input name="" type="submit"
									value="上 传" /> </span>
							<span class="reset"><input name="" id="btnCancel"
									type="reset" value="重 置" /> </span>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="/share/storage-left.jsp"%>
</div>
<%@ include file="/share/foot.jsp"%>
</body>
</html>
