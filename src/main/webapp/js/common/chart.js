//天气数据图表显示
function drawChart(mintempSeries,maxtempSeries,humiditySeries,wspSeries) {

	 $('#container01').highcharts({
		chart : {
			renderTo : 'container',
			type : 'spline',
			margin: [50, 20, 40, 80]
		},
        legend: {
            align: 'center',
            verticalAlign: 'top',
        },
		credits: { 
			enabled: false //不显示LOGO 
		},
		title : {
			text : null
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
            tickPixelInterval: 40,
			gridLineDashStyle: 'longdash',
    		lineColor: '#C0D0E0',
    		lineWidth: 1,
    		tickWidth: 1,
    		tickColor: '#C0D0E0'
		},
		tooltip : {
			formatter : function() {
				return '<b>' + this.series.name + '</b><br/>'
						+ Highcharts.dateFormat('%Y年%b', this.x) + ': '
						+ this.y;
			}
		},
		plotOptions: {
	          spline: {
	              lineWidth: 2,
	              states: {
	                  hover: {
	                      lineWidth: 4
	                  }
	              },
	              marker: {
	                  enabled: false
	              }
	          },
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
