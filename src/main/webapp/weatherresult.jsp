<%@ page language="java" import="java.util.*" import="com.education.experiment.cloudweather.MonthBean"
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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>云服务平台</title>
		<link href="css/base.css" rel="stylesheet" type="text/css" />
		<link href="css/module/header.css" rel="stylesheet" type="text/css" />
		<link href="css/module/title.css" rel="stylesheet" type="text/css" />
		<link href="css/module/pageSkin.css" rel="stylesheet" type="text/css" />
		<link href="css/module/navi002.css" rel="stylesheet" type="text/css" />
		<link href="css/module/reportOA.css" rel="stylesheet" type="text/css" />
		<link href="css/module/boxSearch.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquery-1.8.0.js"></script>
		<script type="text/javascript">
	$(function() {
		var chart;
		$(document).ready(
				function() {
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
		<!--ie6png图片透明补丁-->
		<!--[if IE 6]>
    <script type="text/javascript" src="js/DD_belatedPNG.js"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('#header,.cur,.btn,.iconUser,.iconDepartment,.iconIdea,.iconKey,.iconQuit,.iconSet,.iconAbout,.iconTool,.recall,.case,.approve,.statistics,.https,.file,.compareForm,.webmaster,.diary,#up,#down,.setKeywords a,.icon,.select,.iconMore,.barSearch'); //放置css选择器
    </script>
    <script type="text/javascript">
        //防止抖动
        // <![CDATA[
        if ((window.navigator.appName.toUpperCase().indexOf("MICROSOFT") >= 0) && (document.execCommand)) try {
            document.execCommand("BackgroundImageCache", false, true);
        }
        catch(e) {}
        // ]]>
    </script>
<![endif]-->
		<!--ie6png图片透明补丁-->
	</head>

	<body id="wrapper">
		<script src="js/highcharts.js"></script>
		<!--主体开始-->
		<div id="content" class="clearfix">
			<div id="title">
				<h1>
					气象数据分析云
				</h1>
			</div>

			<!--列表开始-->
			<div id="boxSearch">
				<div class="subNavi">
					<ul>
						<li>
							<a href='downloadweather.jsp'>示例文件下载</a>
						</li>
						<li>
							<a href='uploadweather.jsp'>气象数据上传</a>
						</li>
						<%
							UserBean user = (UserBean) session.getAttribute("user");
							if (user.getUserId().equals("admin")) {
						%>
						<li>
							<a href='deleteweather.jsp'>气象数据删除</a>
						</li>
						<li>
							<a href='previewweatherdata?sign=0'>气象数据云计算</a>
						</li>
						<%
							}
						%>
						<li class="current">
							<a href='previewweatherresult'>气象结果查看</a>
						</li>
					</ul>
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
		<div id="footer"></div>
	</body>
</html>