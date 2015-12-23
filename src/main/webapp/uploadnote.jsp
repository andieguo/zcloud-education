<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>云</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/base.css" rel="stylesheet" type="text/css" />
<link href="css/boxSearch.css" rel="stylesheet" type="text/css" />
<link href="css/reportOA.css" rel="stylesheet" type="text/css" />
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
					<div id="selectSet">
						<!--默认选项开始-->
						<div id="normal">
							<dl class="list clearfix">
								<dt class="dt">
									日&nbsp;记&nbsp;标&nbsp;题：
								</dt>
								<dd class="dd">
									<span class="input text"><input name="noteTitle"
											type="text" value="" /> </span>
								</dd>
							</dl>
							<dl class="list clearfix">
								<dt class="dt">
									日&nbsp;记&nbsp;内&nbsp;容：
								</dt>
								<dd>
									<span><textarea name="noteCountent" id="noteCountent"
											style="width: 560px; height: 250px;" cols="" rows=""></textarea>
									</span>
								</dd>
							</dl>
						</div>
						<!--默认选项结束-->
						<br/>
						<div class="foot" style="margin-top:5px;">
							<span class="submit"><input name="" type="submit"
									id="btnSave" value="上 传" /> </span>
							<span class="reset"><input name="" type="reset"
									value="重 置" /> </span>
						</div>
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
