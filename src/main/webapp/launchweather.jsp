<%@ page language="java" import="java.util.*"
	import="com.education.experiment.commons.UserBean" pageEncoding="UTF-8"%>
<%@ include file="/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>启动气象数据分析</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/base.css" rel="stylesheet" type="text/css" />
<link href="css/boxSearch.css" rel="stylesheet" type="text/css" />
<link href="css/reportOA.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.8.0.js"></script>
<script src="js/common/jobtracker.js"></script>
<script type="text/javascript">

$(function(){
	getRuningJob();
	getOtherJob();
});
	
</script>
</head>
<body>
<div class="hd-main" style="min-width:1000px;">
	<div class="logo-main" xmlns="http://www.w3.org/1999/xhtml">
		<img src="images/qixiang.png" /><span class="logo">气象数据分析系统</span>
	</div>
</div>
<div class="clearfix1 wrap">
	<div id="Container" style="float:left;width: 100%; height: 100%;min-width:790px;">
		<div class="fns">
			<div id="header-shaw">
				<form action="weatherparsing" method="get">
					<div id="selectSet">
						<div class="foot noneBorder">
							<B>点击启动快件分析作业：</B>
							<input name="" type="submit" value="启动" />
							<input type="hidden" id="sign" name="sign" value="1" />
						</div>
						<br />
						<br />
						<div class="foot">
						</div>
					</div>
							<div class="fade active" id="zj">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">hadoop > Reduce</h3>
				</div>
				<div class="panel-heading">
					<h3 class="panel-title">正在进行的任务</h3>
				</div>
				<div class="panel-body">
					<table id='tab_running'
						class="table table-bordered table-condensed table-hover">
						<thead>
							<tr>
								<th width="15%">ID</th>
								<th width="15%">名称</th>
								<th width="15%">所属用户</th>
								<th width="15%">开始时间</th>
								<th width="20%" colspan="2">map进度</th>
								<th width="20%" colspan="2">reduce进度</th>
							</tr>
						</thead>
						<tbody>

						</tbody>
					</table>
				</div>
			</div>

			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">已完成任务</h3>
				</div>
				<div class="panel-body">
					<table id='tab_completed'
						class="table table-bordered table-condensed table-hover">
						<thead>
							<tr>
								<th>ID</th>
								<th>名称</th>
								<th>所属用户</th>
								<th>开始时间</th>

							</tr>
						</thead>
						<tbody>

						</tbody>
					</table>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">失败任务</h3>
				</div>
				<div class="panel-body">
					<table id='tab_failed'
						class="table table-bordered table-condensed table-hover">
						<thead>
							<tr>
								<th width="25px">ID</th>
								<th width="15%">名称</th>
								<th width="15%">所属用户</th>
								<th width="15%">开始时间</th>
								<th width="20%" colspan="2">map进度</th>
								<th width="20%" colspan="2">reduce进度</th>
							</tr>
						</thead>
						<tbody>

						</tbody>
					</table>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">已杀死任务</h3>
				</div>
				<div class="panel-body">
					<table id='tab_killed'
						class="table table-bordered table-condensed table-hover">
						<thead>
							<tr>
								<th>ID</th>
								<th>名称</th>
								<th>所属用户</th>
								<th>开始时间</th>
							</tr>
						</thead>
						<tbody>

						</tbody>
					</table>
				</div>
			</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="/share/weather-left.jsp"%>
</div>
<%@ include file="/share/foot.jsp"%>
</body>
</html>
