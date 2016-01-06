<%@ page language="java" import="java.util.*"
	import="com.education.experiment.commons.UserBean" pageEncoding="UTF-8"%>
<%@ include file="/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>更新GPS信息</title>
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
	<%@ include file="/share/express-left.jsp"%>
</div>
<%@ include file="/share/foot.jsp"%>
</body>
</html>
