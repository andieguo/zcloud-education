	function getFileSystem(user,command){
		var file = '/tomcat/users/'+user+'/'+command;
		 $.ajax({//调用JQuery提供的Ajax方法 
			type : "GET",
			url : "filesystem",
			data : {command:'LISTSTATUS',parentDir:file},
			dataType : "json",
			success : function(data){//回调函数 
				console.log('data：',data);
				printFileSystem(user,command,data.FileStatuses.FileStatus);
			},
			error : function() {
				alert("系统出现问题");
			}
		});
	}
	
	function printFileSystem(user,command,data){
		   $("#tab_filesystem").html("");
		   $("#filecount").html("已全部加载，共"+data.length+"个");
		   for (var i=0; i<data.length; i++) {
		      var tr1 = "<dd class='list-view-item'>"; 
				tr1 += "<input class='checkbox' name='keyIds' type='checkbox' value='"+data[i].pathSuffix+"' onclick=ChkSonClick('keyIds','chkAll') />";
				tr1 += "<span class='fileicon'></span>";
					tr1 += "<div class='file-name' style='width:62%'>";
						tr1 += "<div class='text'>";
							tr1 += "<a href='#' title='"+data[i].pathSuffix+"'>"+data[i].pathSuffix+"</a>";
						tr1 += "</div>";
						tr1 += "<div class='operate'>";
						tr1 += "<a class='icon icon-download-blue' href='downloadfile?command="+command+"&filename="+data[i].pathSuffix+"'title='下载'></a>";
						tr1 += "<a class='icon icon-delete-blue' onClick=deletefile('"+user+"','"+command+"','"+data[i].pathSuffix+"') title='删除'></a>";						
						tr1 += "</div>";
					tr1 += "</div>";
					tr1 += "<div class='file-size' style='width:16%'>"+formatsize(data[i].length)+"</div>";
					tr1 += "<div class='ctime' style='width:22%'>"+formatdate(data[i].modificationTime)+"</div>";
				tr1 += "</dd>";
	       $("#tab_filesystem").append(tr1);                          
		}   
	 }

	function formatsize(size){
		if(size > 1024){
			var num = new Number(size/1024);
			return  num.toFixed(2)+"KB";
		}else if(size > 1024*1024){
			var num = new Number(size/1024/1024);
			return num.toFixed(2)+"MB";
		}else if(size > 1024*1024*1024){
			var num = new Number(size/1024/1024/1024);
			return num.toFixed(2)+"GB";
		}else{
			return size+"B";
		}
	}
	
	function add0(m) {
		return m < 10 ? '0' + m : m
	}
	
	function formatdate(timestap) {
		//shijianchuo是整数，否则要parseInt转换
		var time = new Date(timestap);
		var y = time.getFullYear();
		var m = time.getMonth() + 1;
		var d = time.getDate();
		var h = time.getHours();
		var mm = time.getMinutes();
		var s = time.getSeconds();
		return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':'
				+ add0(mm) + ':' + add0(s);
	}

	function deletefile(user,command,filename) {
		alert("确定要删除么？");
		console.log(filename);
		$.ajax({//调用JQuery提供的Ajax方法 
			type : "GET",
			url : "deletefile",
			traditional: true,
			data : {
				filename : filename,
				command : command
			},
			//dataType : "json",
			success : function(data) {//回调函数 
				console.log('data：', data);
				if (data == "true") {
					getFileSystem(user,command);
				} else {
					alert("删除失败！");
				}
			},
			error : function() {
				alert("系统出现问题");
			}
		});
	}
	
	function deleteAllAction(user,command){
		var value=0;
		var filenames = [];
		$("input[name='keyIds']").each(function () {
			if(this.checked){
				value=1;
				filenames.push(this.value);
			}
		});
		if(!value){
			alert("请选择删除项！");
		}else{
			deletefile(user,command,filenames);
		};
		
	}
	
	