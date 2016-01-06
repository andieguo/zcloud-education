<%@ page language="java" import="java.util.*"
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
</div>
<div class="clearfix1 wrap">
	<div id="Container" style="float:left;width: 100%; height: 100%;min-width:790px;">
		<div class="fns">
			<div id="header-shaw">
						<span>全年气象数据平均值：</span>
						最高温度：<span class="input text" id="maxTempText"></span>℃
						<br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						最低温度：<span class="input text" id="minTempText"></span>℃
						<br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						湿度：<span class="input text" id="humidityText"></span>%
						<br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						风速：<span class="input text" id="wspText"></span>m/s
					<div id="container01" style="width: 100%; height: 400px; margin: 0 auto"></div>
			</div>
		</div>
	</div>
	<%@ include file="/share/weather-left.jsp"%>
</div>
</div>
<%@ include file="/share/foot.jsp"%>
</body>
</html>
