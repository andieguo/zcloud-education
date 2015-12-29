//天气数据图表显示
function drawChart(mintempSeries,maxtempSeries,humiditySeries,wspSeries) {

	 $('#container01').highcharts({
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
		xAxis: {
            type:"datetime",
            dateTimeLabelFormats:{
              month: '%Y-%m'
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
				return '<b>' + this.series.name + '</b><br/>'
						+ Highcharts.dateFormat('%Y年%b', this.x) + ': '
						+ this.y;
			}
		},
		plotOptions: {
			series: {
				stickyTracking: false
			},
			turboThreshold:0 //不限制数据点个数
		},
		series : [ {
			name : 'Meteorological：MinTemp(℃)',
			data : mintempSeries
		}, {
			name : 'Meteorological:MaxTemp(℃)',
			data : maxtempSeries
		}, {
			name : 'Meteorological：Humidity(%)',
			data : humiditySeries
		}, {
			name : 'Meteorological：WSP(m/s)',
			data : wspSeries
		} ]
	});
}
