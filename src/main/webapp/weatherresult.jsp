<%@ page language="java" import="java.util.*,com.education.experiment.commons.UserBean"
	import="com.education.experiment.cloudweather.MonthBean" pageEncoding="UTF-8"%>
<%@ include file="/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>气象数据列表</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/base.css" rel="stylesheet" type="text/css" />
<link href="css/boxSearch.css" rel="stylesheet" type="text/css" />
<link href="css/reportOA.css" rel="stylesheet" type="text/css" />
<link href="css/new-style.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.8.0.js"></script>
<script src="js/common/checkbox.js"></script>
<script src="js/highcharts.js"></script>
<script src="js/common/chart.js"></script>
<script type="text/javascript">
$(function() {//页面加载时调用该方法
	$.ajax({//调用JQuery提供的Ajax方法 
		type : "GET",
		url : "previewweatherresult",
		data : {startyear:'2010',endyear:'2012'},
		dataType : "json",
		success : function(data){//回调函数 
			console.log('data：',data);
			var mintempSeries= JSON.parse(data.mintempSeries);
			var maxtempSeries= JSON.parse(data.maxtempSeries);
			var humiditySeries= JSON.parse(data.humiditySeries);
			var wspSeries= JSON.parse(data.wspSeries);
			$("#maxTempText").text(data.maxTemp);
			$("#minTempText").text(data.minTemp);
			$("#humidityText").text(data.humidity);
			$("#wspText").text(data.WSP);
			drawChart(mintempSeries,maxtempSeries,humiditySeries,wspSeries);
		},
		error : function() {
			alert("系统出现问题");
		}
	});
});
</script>
</head>
<body>
<div class="hd-main" style="min-width:1000px;">
	<div class="logo-main" xmlns="http://www.w3.org/1999/xhtml">
		<img src="images/qixiang.png" /><span class="logo">气象数据分析系统</span>
	</div>
	<%@ include file="/share/head-user.jsp"%>
</div>
<div class="clearfix1 wrap">
	<div id="Container" style="float:left;width: 100%; height: 100%;min-width:790px;">
		<div class="fns">
			<div id="header-shaw" class="launchweather">
				<div class="title">
					<span class="title-left">气象数据分析系统 > 气象数据查看</span>
				</div>
				<div class="panel mb15">
					<div class="panel-title">全年气象数据平均值</div>
					<div class="panel-body">
						<table class="list" id='tab_running'>
							<thead>
								<tr>
									<th>最高温度</th>
									<th>最低温度</th>
									<th>湿度</th>
									<th class="last">风速</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><span id="maxTempText"></span>℃</td>
									<td><span id="minTempText"></span>℃</td>
									<td><span id="wspText"></span>m/s</td>
									<td><span id="wspText">24.6</span>m/s</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="panel mb15">
					<div class="panel-title">气象数据统计</div>
					<div class="panel-body">
						<div id="container01" style="width: 100%; height: 300px; margin: 0 auto"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/share/weather-left.jsp"%>
</div>
</div>
<%@ include file="/share/foot.jsp"%>
</body>
</html>
