<%@ page language="java" import="java.util.*,com.education.experiment.commons.UserBean"
	import="com.education.experiment.commons.UserBean" pageEncoding="UTF-8"%>
<%@ include file="/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
	UserBean ub = (UserBean) request.getSession().getAttribute("user");
%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>分析条件上传</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/base.css" rel="stylesheet" type="text/css" />
<link href="css/boxSearch.css" rel="stylesheet" type="text/css" />
<link href="css/reportOA.css" rel="stylesheet" type="text/css" />
<link href="css/new-style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="hd-main" style="min-width: 1000px;">
	<div class="logo-main" xmlns="http://www.w3.org/1999/xhtml">
		<img src="images/weixin.png" /><span class="logo">微信关系分析系统</span>
	</div>
	<%@ include file="/share/head-user.jsp"%>
</div>
<div class="clearfix1 wrap">
	<div id="Container" style="float: left; width: 100%; height: 100%; min-width: 790px;">
		<div class="fns">
			<div id="header-shaw" class="launchweather">
				<div class="title">
					<span class="title-left">微信关系分析系统 > 分析条件上传</span>
				</div>
				<div class="panel mb15">
					<div class="panel-title">分析条件上传</div>
					<div class="panel-body">
						<form action="uploadweixinparsing" method="post">
						<table class="upload">
							<tr>
								<th>通信时间点：</th>
								<td>
									<input class="input-text" name="datePoint" id="datePoint" type="text" value="" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />（注：大于等于为符合条件）
								</td>
							</tr>
							<tr>
								<th>通信时长：</th>
								<td>
									<input class="input-text" name="minute" type="text" value="" />分钟 （注：大于等于为符合条件）
								</td>
							</tr>
							<tr>
								<th>性别选择：</th>
								<td>
									<label><input class="checkbox" name="sex" type="radio" value="男男" checked="checked" />男男</label>
									<label><input class="checkbox" name="sex" type="radio" value="女女" />女女</label>
									<label><input class="checkbox" name="sex" type="radio" value="异性" />异性</label>
									<label><input class="checkbox" name="sex" type="radio" value="ALL" />全部</label>
								</td>
							</tr>
							<tr>
								<th>是否好友关系：</th>
								<td>
									<label><input class="checkbox" name="isfriend" type="radio" value="是" checked="checked" />是</label>
									<label><input class="checkbox" name="isfriend" type="radio" value="否" />否</label>
									<label><input class="checkbox" name="isfriend" type="radio" value="ALL" />两者都包含</label>
								</td>
							</tr>
							<tr>
								<th>年龄范围：</th>
								<td>
									<input class="input-text" name="agespan" type="text" />（格式：35至38）
								</td>
							</tr>
							<tr>
								<th>职业：</th>
								<td>
									<label><input class="checkbox" name="vocation" type="checkbox" value="餐饮" />餐饮</label>
									<label><input class="checkbox" name="vocation" type="checkbox" value="教育" />教育</label>
									<label><input class="checkbox" name="vocation" type="checkbox" value="金融" />金融</label>
									<label><input class="checkbox" name="vocation" type="checkbox" value="律师" />律师</label>
									<label><input class="checkbox" name="vocation" type="checkbox" value="娱乐" />娱乐</label>
									<label><input class="checkbox" name="vocation" type="checkbox" value="军人" />军人</label>
									<label><input class="checkbox" name="vocation" type="checkbox" value="体育" />体育</label>
									<label><input class="checkbox" name="vocation" type="checkbox" value="建筑" />建筑</label>
									<label><input class="checkbox" name="vocation" type="checkbox" value="无业" />无业</label>
								</td>
							</tr>
							<tr>
								<th>通信地点：</th>
								<td>
									<input class="input-text" name="places" type="text" />（多个通信地点用英文逗号隔开）
								</td>
							</tr>
							<tr>
								<th>通信内容关键词：</th>
								<td>
									<input class="input-text" name="keywords" type="text" />（多个关键词用英文逗号隔开）
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
	<%@ include file="/share/weixin-left.jsp"%>
</div>
<%@ include file="/share/foot.jsp"%>
</body>
</html>
