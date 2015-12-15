<%@ page language="java" import="java.util.*"
	import="com.education.experiment.commons.UserBean" pageEncoding="UTF-8"%>
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
		<img src="images/zhineng.png" /><span class="logo">智能EMS速递云系统</span>
	</div>
</div>
<div class="clearfix1 wrap">
	<div id="Container" style="float:left;width: 100%; height: 100%;min-width:790px;">
		<div class="fns">
			<div id="header-shaw">
				<form action="updategprs" method="post">
					<div id="content" class="clearfix">
						<!--列表开始-->
						<div id="boxSearch">
							<div id="selectSet">
								<!--默认选项开始-->
								<div id="normal">
									<dl class="list clearfix">
										<dt class="dt">
											当前经度：
										</dt>
										<dd class="dd">
											<span class="input text"><input name="longitude"
													type="text" value="" /> </span>
										</dd>
										（例如：116.33533）
									</dl>
									<dl class="list clearfix">
										<dt class="dt">
											当前纬度：
										</dt>
										<dd class="dd">
											<span class="input text"><input name="latitude"
													type="text" value="" /> </span>
										</dd>
										（例如：39.20567）
									</dl>
								</div>
								<!--默认选项结束-->
								<div class="foot">
									<span class="submit"><input name="" type="submit"
											value="更 新" /> </span>
									<span class="reset"><input name="" id="btnCancel"
											type="reset" value="重 置" /> </span>
								</div>
							</div>
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
				<li class="b-list-item"><a  href="downloadexpress.jsp" class="on"><span class="text1"><span class="img-ico aside-moth"></span>示例文件下载</span></a></li>
				<li class="b-list-item"><a href="uploadexpress.jsp"><span class="text1"><span class="img-ico aside-mapp"></span>气象数据上传</span></a></li>
				<li class="b-list-item"><a href="launchexpressparsing.jsp"><span class="text1"><span class="img-ico aside-start"></span>启动智能分析</span></a></li>
				<li class="b-list-item"><a href="suspendexpressparsing.jsp"><span class="text1"><span class="img-ico aside-stop"></span>停止智能分析</span></a></li>
				<li class="b-list-item"><a href="gprsupdate.jsp"><span class="text1"><span class="img-ico aside-gprs"></span>我的GPRS</span></a></li>	
				<li class="b-list-item"><a href="#"><span class="text1"><span class="img-ico aside-box"></span>我的快递</span></a></li>						
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
