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
			<div id="header-shaw">
				<form action="uploadexpressinfo" method="post">
					<div id="selectSet">
						<!--默认选项开始-->
						<div id="normal">
							<dl class="list clearfix">
								<dt class="dt">
									经度：
								</dt>
								<dd class="dd">
									<span class="input text"><input name="longitude" type="text"
											value="" /> </span>
								</dd>
							</dl>
							<dl class="list clearfix">
								<dt class="dt">
									纬度：
								</dt>
								<dd class="dd">
									<span class="input text"><input name="latitude"
											type="text" value="" /> </span>
								</dd>
							</dl>
							<dl class="list clearfix">
								<dt class="dt">
									地址：
								</dt>
								<dd class="dd">
									<span class="input text"><input name="address"
											type="text" value="" /> </span>
								</dd>
							</dl>
							<dl class="list clearfix">
								<dt class="dt">
									备注：
								</dt>
								<dd class="dd">
									<span class="input text"><textarea rows="5" cols="25" name="remark"></textarea> </span>
								</dd>
							</dl>
							<br />
						</div>
						<!--默认选项结束-->
						<div class="foot clearfix">
							<span class="submit"><input name="" type="submit"
									value="上传" /> </span>
							<span class="reset"><input name="" id="btnCancel"
									type="reset" value="重 置" /> </span>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="/share/express-left.jsp"%>
</div>
<%@ include file="/share/foot.jsp"%>
</body>
</html>
