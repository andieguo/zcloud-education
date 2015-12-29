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
<script src="js/common/weatherlist.js"></script>
<script src="js/common/checkbox.js"></script>
<script type="text/javascript">
$(function() {
	var chart;
	$(document).ready(function() {
	<%
		Map<String,MonthBean> maps = (Map<String,MonthBean>)request.getAttribute("result");
		if( maps != null){
	%>
		var mintempSeries = '[';
		var maxtempSeries = '[';
		var humiditySeries = '[';
		var wspSeries = '[';
		<%
			for(String key : maps.keySet()){
				MonthBean monthBean = maps.get(key);%>
				mintempSeries += '['+key+','+ <%= monthBean.getMinTemp() %>+'],';
				maxtempSeries += '['+key+','+ <%= monthBean.getMaxTemp() %>+'],';
				humiditySeries += '['+key+','+ <%= monthBean.getHumidity() %>+'],';
				wspSeries += '['+key+','+ <%= monthBean.getWSP() %>+'],';
		<%	
		}
		%>
		mintempSeries = mintempSeries.substring(0, mintempSeries.length);
		mintempSeries += ']';
		maxtempSeries = maxtempSeries.substring(0, maxtempSeries.length);
		maxtempSeries += ']';
		wspSeries = wspSeries.substring(0, wspSeries.length);
		wspSeries += ']';
		humiditySeries = humiditySeries.substring(0, humiditySeries.length);
		humiditySeries += ']';
		wspSeries = wspSeries.substring(0, wspSeries.length);
		wspSeries += ']';
		
		chart = new Highcharts.Chart({
			chart : {
				renderTo : 'container',
				type : 'spline'
			},
			title : {
				text : '气象数据统计'
			},
			subtitle : {
				text : '2012-2013年'
			},
			xAxis : {
				type : 'datetime',
				dateTimeLabelFormats : { // don't display the dummy year
					month : '%Y年%b',
					year : '%b'
				}
			},
			yAxis : {
				title : {
					text : '单位：摄氏度,百分比,米每秒'
				},
				min : -20
			},
			tooltip : {
				formatter : function() {
					return '<b>'
							+ this.series.name
							+ '</b><br/>'
							+ Highcharts.dateFormat('%Y年%b',
									this.x) + ': ' + this.y ;
				}
			},

			series : [
					{
						name : 'Meteorological：MinTemp(℃)',
						data : mintempSeries
					},
					{
						name : 'Meteorological:MaxTemp(℃)',
						data : maxtempSeries
					},
					{
						name : 'Meteorological：Humidity(%)',
						data : humiditySeries
					},
					{
						name : 'Meteorological：WSP(m/s)',
						data : wspSeries
					} ]
		});
	});
	<%}%>
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
		<script src="js/highcharts.js"></script>
		<!--主体开始-->
		<div id="content" class="clearfix">
			<div id="title">
				<h1>
					气象数据分析云
				</h1>
			</div>

				<div id="selectSet">
					<%
						String result = (String) request.getAttribute("result");
						if (result != null) {
						float maxTemp = (Float) request.getAttribute("maxTemp");
						float minTemp = (Float) request.getAttribute("minTemp");
						float humidity = (Float) request.getAttribute("humidity");
						float WSP = (Float) request.getAttribute("WSP");
					%>
					<dl class="list clearfix">
						<span>全年气象数据平均值：</span>
						<span class="input text">最高温度：<%=maxTemp%>℃</span>
						<br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span class="input text"></>最低温度：<%=minTemp%>℃</span>
						<br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span class="input text">湿&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：<%=minTemp%>%</span>
						<br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span class="input text">风&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;速：<%=minTemp%>m/s</span>
					</dl>
					<br />
					<div id="container"
						style="min-width: 400px; height: 400px; margin: 0 auto"></div>
					<%
						} else {
					%>
					当前无气象数据结果可供浏览，可能分析数据任务正在执行当中。
					<br>
						<br>
							<br>
							<%
								}
							%>
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
