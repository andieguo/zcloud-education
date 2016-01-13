<%@ page language="java" import="java.util.*"
	import="com.education.experiment.commons.UserBean" pageEncoding="UTF-8"%>
<%@ include file="/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>我的快递</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/base.css" rel="stylesheet" type="text/css" />
<link href="css/boxSearch.css" rel="stylesheet" type="text/css" />
<link href="css/reportOA.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="hd-main" style="min-width:1000px;">
	<div class="logo-main" xmlns="http://www.w3.org/1999/xhtml">
		<img src="images/zhineng.png" /><span class="logo">智能EMS速递云系统</span>
	</div>
</div>
<div class="clearfix1 wrap">
	<div id="Container" style="float:left;width: 100%; height: 100%;min-width:790px;">
		<div class="fns">
			<div id="selectSet">
					<%
						String result = (String) request.getAttribute("result");
						if (result != null) {
							List<String> list = (ArrayList<String>) request.getAttribute("list");
					%>
					<dl class="list clearfix">
						<span>系统就近为你分析出 <%=list.size()%> 个订单：</span>
						<br />
						<%
							int count = 1;
								for (String order : list) {
						%>
						<span class="input text">订单<%=count%>:</span>&nbsp;&nbsp;<%=order%>
						<br>
						<%
							count++;
								}
						%>

					</dl>
					<br />
					<%
						} else {
					%>
					无订单信息可领取。
					<br>
					<br>
					<br>
					<%
						}
					%>
					<form action="previewexpress" method="get">
						<div class="foot">
							<span class="submit"><input name="" type="submit"
									value="提取快件" /> <input type="hidden" id="sign" name="sign"
									value="1" /> </span>
						</div>
					</form>
				</div>
		</div>
	</div>
	<%@ include file="/share/express-left.jsp"%>
</div>
<%@ include file="/share/foot.jsp"%>
</body>
</html>
