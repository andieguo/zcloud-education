<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>新建云日记</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/base.css" rel="stylesheet" type="text/css" />
<link href="css/boxSearch.css" rel="stylesheet" type="text/css" />
<link href="css/reportOA.css" rel="stylesheet" type="text/css" />
<link href="css/new-style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="hd-main" style="min-width:1000px;">
	<div class="logo-main" xmlns="http://www.w3.org/1999/xhtml">
		<img src="images/geren.png" /><span class="logo">个人私有存储系统</span>
	</div>
</div>
<div class="clearfix1 wrap">
	<div id="Container" style="float:left;width: 100%; height: 100%;min-width:790px;">
		<div class="fns">
			<div id="header-shaw">
			<form action="notecommit" method="post">
				<div class="doc-header">
					<span class="doc-header-title">日记标题</span>
					<input class="doc-header-body" type="text" id="noteTitle"  name="noteTitle"/>
				</div>
				<div class="eidtor">
					<script type="text/javascript" src="js/Eidtor/nicEdit.js"></script>
				    <script type="text/javascript">
				      //<![CDATA[
				      bkLib.onDomLoaded(function() {
				        new nicEditor({iconsPath : 'images/nicEditorIcons.gif'}).panelInstance('noteCountent');
				      });
				      //]]>
				    </script>
				    <textarea name="noteCountent" id="noteCountent" style="width: 100%; height: 400px;">
				    	日记内容
				    </textarea>
				</div>
				<div class="submit-reset">
					<span class="submit">
						<input name="" type="submit" value="上 传" />
					</span>
					<span class="reset">
						<input name="" id="btnCancel" type="reset" value="重 置" />
					</span>
				</div>
			</form>
			</div>
		</div>
	</div>
	<%@ include file="/share/storage-left.jsp"%>
</div>
<%@ include file="/share/foot.jsp"%>
</body>
</html>
