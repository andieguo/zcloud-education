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
				<form action="deletefile" method="get">
					<div id="selectSet">
						<!--默认选项开始-->
						<div id="normal">
							<dl class="list clearfix">
								<dt class="dt">
									文&nbsp;件&nbsp;名：
								</dt>
								<dd class="dd">
									<span class="input text"><input id="text_box"
											name="filename" type="text" style="width: 230px;" /> </span>
								</dd>
							</dl>
						</div>
						<!--<div id="normal">
							<dl class="list clearfix">
								<dt class="dt">
									文&nbsp;件&nbsp;名：
								</dt>
								<dd class="dd">
									<select>
										<option selected="selected" value="1">
											普通身份
										</option>
										<option value="2">
											管理员身份
										</option>
									</select>
								</dd>
							</dl>
						</div>-->
						<!--默认选项结束-->
						<br />
						<div class="foot">
							<span class="submit"><input name="" type="submit"
									value="刪 除" /> </span>
							<span class="reset"><input name="" id="btnCancel"
									type="reset" value="重 置" /> </span>
						</div>

					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="aside main" style="width: 210px; float:left;">
		<div style="height: 533px;" class="b-view genere jfk-scrollbar" id="genere">
			<div class="minheight-forfooter">
				<ul class="b-list-3" id="aside-menu">
				<li class="b-list-item"><a href="privatestoragecloud.jsp" class="sprite2 on" hidefocus="true" id="tab-home" unselectable="on"><span class="text1"><span class="img-ico aside-disk"></span>新建云日记</span></a></li>
				<li class="b-list-item"><a href="deletenote.jsp" class="sprite2 b-no-ln" hidefocus="true" id="tab-recyle" unselectable="on"><span class="text1"><span class="img-ico aside-recycle"></span>云日记删除</span></a></li>
				<li class="b-list-item separator-1"></li>
				<li class="b-list-item"><a href="uploadfile.jsp" class="type-a-oth type-a-app" hidefocus="true"><span class="text1"><span class="img-ico aside-mapp"></span>云文件上传</span></a></li>
				<li class="b-list-item"><a  href="downloadfile.jsp" class="sprite2 b-no-ln" hidefocus="true"><span class="text1"><span class="img-ico aside-moth"></span>云文件下载</span></a></li>
				<li class="b-list-item"><a href="previewuser.jsp" class="sprite2 b-no-ln" hidefocus="true" id="tab-share" unselectable="on"><span class="text1"><span class="img-ico aside-share"></span>云文件预览</span></a></li>
				
				<li class="b-list-item"><a href="deletefile.jsp" class="sprite2 b-no-ln" hidefocus="true" id="tab-recyle" unselectable="on"><span class="text1"><span class="img-ico aside-recycle"></span>云文件删除</span></a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
</div>
<div class="banquan">
	版权所有© 2013 北京斑步志伟科技公司
</div>
</body>
</html>
