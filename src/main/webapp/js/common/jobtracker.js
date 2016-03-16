 	function printRunJobTable(url,data,tab){
			tab.html('<thead><tr><th>ID</th><th>名称</th><th>所属用户</th><th>开始时间</th><th colspan="2" width="20%">map进度</th><th colspan="2" class="last" width="20%">reduce进度</th></tr><thead>');
			for (var i=0; i<data.length; i++) {
			   var mapProgress = (data[i].mapProgress).toFixed(2)*100;
			   var reduceProgress = (data[i].reduceProgress).toFixed(2)*100;
			   console.log("mapProgress",mapProgress);
				var tr1 = "<tbody><tr>"; 
				tr1 += "<td><a href='"+url+"/jobdetails.jsp?jobid="+data[i].jobId+"'>"+data[i].jobId+"</a></td>";
				tr1 += "<td>"+data[i].jobName+"</td>";
				tr1 += "<td>"+data[i].userName+"</td>";
				tr1 += "<td>"+data[i].starttime+"</td>"; 
				tr1 += '<td width="15%">';
				tr1 +=     '<div class="progress">';
				tr1 +=           '<div class="progress-bar" role="progressbar" aria-valuenow="'+mapProgress+'"';
				tr1 +=                             'aria-valuemin="0" aria-valuemax="100" style="width: '+mapProgress+'%;">';
				tr1 +=                            '<span class="sr-only">'+mapProgress+'%</span>';
				tr1 +=    '</div></div></td>';
				tr1 += '<td>'+mapProgress+'%</td>';
				tr1 += '<td width="15%">';
				tr1 +=     '<div class="progress">';
				tr1 +=           '<div class="progress-bar" role="progressbar" aria-valuenow="'+reduceProgress+'"';
				tr1 +=                             'aria-valuemin="0" aria-valuemax="100" style="width: '+reduceProgress+'%;">';
				tr1 +=                            '<span class="sr-only">'+reduceProgress+'%</span>';
				tr1 +=    '</div></div></td>';
				tr1 += '<td>'+reduceProgress+'%</td></tr></tbody>';
				tab.append(tr1);  
		  }
          }
        
       function printJobTable(url,data,tab){
    	   for (var i=0; i<data.length; i++) {
     	      var tr1 = "<tr>"; 
			 tr1 += "<td><a href='"+url+"/jobdetails.jsp?jobid="+data[i].jobId+"'>"+data[i].jobId+"</a></td>";
	         tr1 += "<td>"+data[i].jobName+"</td>";
	         tr1 += "<td>"+data[i].userName+"</td>";
	         tr1 += "<td>"+data[i].starttime+"</td>"; 
	         tr1 += '</tr>';    
	         tab.append(tr1);                          
     		}   
         }
        
      function getRuningJob(url,jobname){
    	  $.ajax({//调用JQuery提供的Ajax方法 
				type : "GET",
				url : "jobtracker?jobname="+jobname,
				dataType : "json",
				success : function(data){//回调函数 
					console.log(data);
					if(data.running.length > 0){
						console.log("执行了getRuningJob方法");
						window.setTimeout(getRuningJob(url,jobname),1000);//休息1S后执行getRuningJob方法
						printRunJobTable(url,data.running,$("#tab_running"));
					}
				},
				error : function() {
					//alert("访问hadoop集群失败!");
				}
			});
         }
      
      function getOtherJob(url,jobname){
    	  $.ajax({//调用JQuery提供的Ajax方法 
				type : "GET",
				url : "jobtracker?jobname="+jobname,
				dataType : "json",
				success : function(data){//回调函数 
					//console.log(data);
					printRunJobTable(url,data.failed,$("#tab_failed"));
		        	printJobTable(url,data.completed,$("#tab_completed"));
		        	printJobTable(url,data.killed,$("#tab_killed"));
				},
				error : function() {
					//alert("访问hadoop集群失败!");
				}
			});
         }
      