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
	<%if(((String) request.getAttribute("result"))!=null){%>
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
						// Define the data points. All series have a dummy year
						// of 2007/71 in order to be compared on the same x axis. Note
						// that in JavaScript, months start at 0 for January, 1 for February etc.
						data : [ [ Date.UTC(2012, 1), <%=((MonthBean) request.getAttribute("01")).getMinTemp()%> ],
								[ Date.UTC(2012, 2), <%=((MonthBean) request.getAttribute("02")).getMinTemp()%> ],
								[ Date.UTC(2012, 3), <%=((MonthBean) request.getAttribute("03")).getMinTemp()%> ],
								[ Date.UTC(2012, 4), <%=((MonthBean) request.getAttribute("04")).getMinTemp()%> ],
								[ Date.UTC(2012, 5), <%=((MonthBean) request.getAttribute("05")).getMinTemp()%> ],
								[ Date.UTC(2012, 6), <%=((MonthBean) request.getAttribute("06")).getMinTemp()%> ],
								[ Date.UTC(2012, 7), <%=((MonthBean) request.getAttribute("07")).getMinTemp()%> ],
								[ Date.UTC(2012, 8), <%=((MonthBean) request.getAttribute("08")).getMinTemp()%> ],
								[ Date.UTC(2012, 9), <%=((MonthBean) request.getAttribute("09")).getMinTemp()%> ],
								[ Date.UTC(2012, 10), <%=((MonthBean) request.getAttribute("10")).getMinTemp()%> ],
								[ Date.UTC(2012, 11), <%=((MonthBean) request.getAttribute("11")).getMinTemp()%> ],
								[ Date.UTC(2012, 12), <%=((MonthBean) request.getAttribute("12")).getMinTemp()%> ] ]
					},
					{
						name : 'Meteorological:MaxTemp(℃)',
						// Define the data points. All series have a dummy year
						// of 2007/71 in order to be compared on the same x axis. Note
						// that in JavaScript, months start at 0 for January, 1 for February etc.
						data : [ [ Date.UTC(2012, 1), <%=((MonthBean) request.getAttribute("01")).getMaxTemp()%> ],
								[ Date.UTC(2012, 2), <%=((MonthBean) request.getAttribute("02")).getMaxTemp()%> ],
								[ Date.UTC(2012, 3), <%=((MonthBean) request.getAttribute("03")).getMaxTemp()%> ],
								[ Date.UTC(2012, 4), <%=((MonthBean) request.getAttribute("04")).getMaxTemp()%> ],
								[ Date.UTC(2012, 5), <%=((MonthBean) request.getAttribute("05")).getMaxTemp()%> ],
								[ Date.UTC(2012, 6), <%=((MonthBean) request.getAttribute("06")).getMaxTemp()%> ],
								[ Date.UTC(2012, 7), <%=((MonthBean) request.getAttribute("07")).getMaxTemp()%> ],
								[ Date.UTC(2012, 8), <%=((MonthBean) request.getAttribute("08")).getMaxTemp()%> ],
								[ Date.UTC(2012, 9), <%=((MonthBean) request.getAttribute("09")).getMaxTemp()%> ],
								[ Date.UTC(2012, 10), <%=((MonthBean) request.getAttribute("10")).getMaxTemp()%> ],
								[ Date.UTC(2012, 11), <%=((MonthBean) request.getAttribute("11")).getMaxTemp()%> ],
								[ Date.UTC(2012, 12), <%=((MonthBean) request.getAttribute("12")).getMaxTemp()%> ] ]
					},
					{
						name : 'Meteorological：Humidity(%)',
						data : [ [ Date.UTC(2012, 1), <%=((MonthBean) request.getAttribute("01")).getHumidity()%> ],
								[ Date.UTC(2012, 2), <%=((MonthBean) request.getAttribute("02")).getHumidity()%> ],
								[ Date.UTC(2012, 3), <%=((MonthBean) request.getAttribute("03")).getHumidity()%> ],
								[ Date.UTC(2012, 4), <%=((MonthBean) request.getAttribute("04")).getHumidity()%> ],
								[ Date.UTC(2012, 5), <%=((MonthBean) request.getAttribute("05")).getHumidity()%> ],
								[ Date.UTC(2012, 6), <%=((MonthBean) request.getAttribute("06")).getHumidity()%> ],
								[ Date.UTC(2012, 7), <%=((MonthBean) request.getAttribute("07")).getHumidity()%> ],
								[ Date.UTC(2012, 8), <%=((MonthBean) request.getAttribute("08")).getHumidity()%> ],
								[ Date.UTC(2012, 9), <%=((MonthBean) request.getAttribute("09")).getHumidity()%> ],
								[ Date.UTC(2012, 10), <%=((MonthBean) request.getAttribute("10")).getHumidity()%> ],
								[ Date.UTC(2012, 11), <%=((MonthBean) request.getAttribute("11")).getHumidity()%> ],
								[ Date.UTC(2012, 12), <%=((MonthBean) request.getAttribute("12")).getHumidity()%> ] ]
					},
					{
						name : 'Meteorological：WSP(m/s)',
						data : [ [ Date.UTC(2012, 1), <%=((MonthBean) request.getAttribute("01")).getWSP()%> ],
								[ Date.UTC(2012, 2), <%=((MonthBean) request.getAttribute("02")).getWSP()%> ],
								[ Date.UTC(2012, 3), <%=((MonthBean) request.getAttribute("03")).getWSP()%> ],
								[ Date.UTC(2012, 4), <%=((MonthBean) request.getAttribute("04")).getWSP()%> ],
								[ Date.UTC(2012, 5), <%=((MonthBean) request.getAttribute("05")).getWSP()%> ],
								[ Date.UTC(2012, 6), <%=((MonthBean) request.getAttribute("06")).getWSP()%> ],
								[ Date.UTC(2012, 7), <%=((MonthBean) request.getAttribute("07")).getWSP()%> ],
								[ Date.UTC(2012, 8), <%=((MonthBean) request.getAttribute("08")).getWSP()%> ],
								[ Date.UTC(2012, 9), <%=((MonthBean) request.getAttribute("09")).getWSP()%> ],
								[ Date.UTC(2012, 10), <%=((MonthBean) request.getAttribute("10")).getWSP()%> ],
								[ Date.UTC(2012, 11), <%=((MonthBean) request.getAttribute("11")).getWSP()%> ],
								[ Date.UTC(2012, 12), <%=((MonthBean) request.getAttribute("12")).getWSP()%> ] ]
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
