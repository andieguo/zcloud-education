<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>云</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/base.css" rel="stylesheet" type="text/css" />
<link href="css/boxSearch.css" rel="stylesheet" type="text/css" />
<link href="css/reportOA.css" rel="stylesheet" type="text/css" />
<link href="css/new-style.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.8.0.js"></script>
<script type="text/javascript">

function getFileSystem(type,parentDir){
	  $("#parentDirText").val(parentDir);//改变文本框的值:
	  if(type == 'FILE'){
		 $.ajax({//调用JQuery提供的Ajax方法 
			type : "GET",
			url : "filesystem",
			data : {command:'OPEN',parentDir:parentDir},
			dataType : "text",
			success : function(data){//回调函数 
				console.log('data：',data);
				$("#tab_filesystem").html("<textarea style='margin: 0px; height: 206px; width: 760px;'>"+data+"</textarea>");
			},
			error : function() {
				alert("系统出现问题");
			}
		});
	  }else if(type == 'DIRECTORY'){
		 $.ajax({//调用JQuery提供的Ajax方法 
			type : "GET",
			url : "filesystem",
			data : {command:'LISTSTATUS',parentDir:parentDir},
			dataType : "json",
			success : function(data){//回调函数 
				console.log('data：',data);
				printFileSystem(parentDir,data.FileStatuses.FileStatus);
			},
			error : function() {
				alert("系统出现问题");
			}
		});
	  }
	  
}

function printFileSystem(parentDir,data){
	   $("#tab_filesystem").html("");
	   for (var i=0; i<data.length; i++) {
	      var tr1 = "<dd class='list-view-item'>"; 
			tr1 += "<input class='checkbox' type='checkbox' />";
			tr1 += "<span class='fileicon'></span>";
				tr1 += "<div class='file-name' style='width:62%'>";
					tr1 += "<div class='text'>";
						tr1 += "<a onClick=getFileSystem($(this).parent().next().html(),$('#parentDirText').val()+$(this).html()+'/') title='"+data[i].pathSuffix+"'>"+data[i].pathSuffix+"</a>";
					tr1 += "</div>";
					tr1 += "<div class='operate'>";
						tr1 += "<a class='icon icon-download-blue' href='downloadfile?filename=sensors.xml' title='下载'></a>";
						tr1 += "<a class='icon icon-delete-blue' onClick=deletefile('"+data[i].pathSuffix+"') title='删除'></a>";
					tr1 += "</div>";
				tr1 += "</div>";
				tr1 += "<div class='file-size' style='width:16%'>"+data[i].blockSize+"</div>";
				tr1 += "<div class='ctime' style='width:22%'>"+data[i].modificationTime+"</div>";
			tr1 += "</dd>";
       $("#tab_filesystem").append(tr1);                          
	}   
 }
 
 function deletefile(filename){
	 alert("确定要删除么？");
	 console.log(filename);
	  $.ajax({//调用JQuery提供的Ajax方法 
			type : "GET",
			url : "deletenote",
			data : {filename:filename},
			//dataType : "json",
			success : function(data){//回调函数 
				console.log('data：',data);
				if (data == "true") {
					getFileSystem('DIRECTORY','/tomcat/users/admin/notes');
				}else{
					alert("删除失败！");
				}
			},
			error : function() {
				alert("系统出现问题");
			}
		});
 }
 
 function goToParentDir(){
	   var parentDir = $('#parentDirText').val();// eg. /user/hadoop/zcloud/
	   parentDir = parentDir.substring(0,parentDir.length-1); // eg. /user/hadoop/zcloud
	   var dir = parentDir.substring(0,parentDir.lastIndexOf('/')+1);// eg. /user/hadoop/
	   //console.log("dir:"+dir);
	   getFileSystem('DIRECTORY',dir);
 }

$(function(){
     getFileSystem('DIRECTORY','/tomcat/users/admin/notes');
	});
	
</script>
</head>
<body>
<div class="hd-main" style="min-width:1000px;">
	<div class="logo-main" xmlns="http://www.w3.org/1999/xhtml">
		<img src="images/geren.png" /><span class="logo">个人私有存储系统</span>
	</div>
	<ul xmlns="http://www.w3.org/1999/xhtml" class="b-list-1 options fMainBlue top_menu">
		<li style="_width:90px;max-width:116px" class="b-list-item list-li haspulldown">
		<span class="top-username"><a title="ae12580" target="_blank" href="http://passport.baidu.com/center">ae12580</a></span>
		</li>
		<li class="b-list-item list-li"><a target="_blank" href="http://pan.baidu.com/download" class="b-no-ln">客户端下载</a></li>
	</ul>
</div>
<div class="clearfix1 wrap">
	<div id="Container" style="float:left;width: 100%; height: 100%;min-width:790px;">
		<div class="fns">
			<div id="header-shaw" style="background-color: #fff;height: 542px;">
				<div class="module-history-list">
					<span class="history-list-dir">全部文件</span>
					<span class="history-list-tips">已全部加载，共14个</span>
				</div>
				<div class="list-view-header">
					<ul class="list-cols">
						<li class="col first-col" style="width: 60%;">
							<input class="check" type="checkbox" />
							<span class="text">文件名</span>
							<span class="order-icon"></span>
							<a class="g-button" href="#">
								<span class="g-button-right">
									<em class="icon icon-download-gray" title="下载"></em>
									<span class="text">下载</span>
								</span>
							</a>
							<a class="g-button" href="#">
								<span class="g-button-right">
									<em class="icon icon-delete" title="删除"></em>
									<span class="text">删除</span>
								</span>
							</a>
						</li>
						<li class="col" style="width: 16%;">
							<span class="text">大小</span>
							<span class="order-icon"></span>
						</li>
						<li class="col last-col" style="width: 22%;">
							<span class="text">修改日期</span>
							<span class="order-icon"></span>
						</li>
					</ul>
				</div>
				<div id="tab_filesystem" class="list-view-container">
					
				</div>
			</div>
		</div>
	</div>
	<div class="aside main" style="width: 210px; float:left;">
		<div style="height: 533px;" class="b-view genere jfk-scrollbar" id="genere">
			<div class="minheight-forfooter">
				<ul class="b-list-3" id="aside-menu">
				<li class="b-list-item"><a href="privatestoragecloud.jsp" class="sprite2 b-no-ln" hidefocus="true" id="tab-home" unselectable="on"><span class="text1"><span class="img-ico aside-disk"></span>新建云日记</span></a></li>
				<li class="b-list-item"><a href="deletenote.jsp" class="sprite2 b-no-ln" hidefocus="true" id="tab-recyle" unselectable="on"><span class="text1"><span class="img-ico aside-recycle"></span>云日记删除</span></a></li>
				<li class="b-list-item separator-1"></li>
				<li class="b-list-item"><a href="uploadfile.jsp" class="type-a-oth type-a-app" hidefocus="true"><span class="text1"><span class="img-ico aside-mapp"></span>云文件上传</span></a></li>
				<li class="b-list-item"><a href="previewuser.jsp" class="sprite2 on" hidefocus="true" id="tab-share" unselectable="on"><span class="text1"><span class="img-ico aside-share"></span>云文件预览</span></a></li>
				</ul>			
			</div>
		</div>
	</div>
</div>
</div>
<div class="banquan">
	版权所有&copy; 2013斑步志伟科技公司
</div>
</body>
</html>
