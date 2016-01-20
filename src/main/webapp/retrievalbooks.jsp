<%@ page language="java" import="java.util.*"
	import="com.education.experiment.commons.UserBean" import="com.education.experiment.cloudlibrary.Book"
	pageEncoding="UTF-8"%>
<%@ include file="/share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>云服务平台</title>
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<link href="css/base.css" rel="stylesheet" type="text/css" />
		<link href="css/boxSearch.css" rel="stylesheet" type="text/css" />
		<link href="css/reportOA.css" rel="stylesheet" type="text/css" />
		<link href="css/new-style.css" rel="stylesheet" type="text/css" />
		<script src="js/jquery-1.8.0.js"></script>
		<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
		<%
			String name = "";
			String author = "";
			String publishdate = "";
			String section = "";
			Book book = (Book) request.getAttribute("book");
			if (book != null) {
				name = book.getName();
				author = book.getAuthor();
				publishdate = book.getPublishDate();
				section = book.getSection();
			}
		%>
		
		<script type="text/javascript">
			function submitItem() {
				with (document.getElementById("bookform")) {
					method = "get";
					action = "retrievalbooks";
					submit();
				}
			}
			
			$(document).ready(function() {
				$("#btnCancel").click(function() {
					$("#text_box1").val("");
					$("#text_box2").val("");
				});
			});
			
			$(document).ready(function() {
				$("#name").val("<%=name%>");
				$("#author").val("<%=author%>");
				$("#publishdate").val("<%=publishdate%>");
				$("#section").val("<%=section%>");
			});
		
			$(document).ready(function() {
				$("#btnSave").click(
						function() {
							var name = $("input:text[name='name']").val();
							var author = $("input:text[name='author']").val();
							var publishdate = $("input:text[name='publishdate']").val();
							var section = $("input:text[name='section']").val();
							if (name == "" && author == ""&& publishdate == ""&& section == "") {
								alert("请输入查询条件!");
							} else {
								submitItem();
							}
						});
			});
		</script>
	</head>
	<body>
<div class="hd-main" style="min-width:1000px;">
	<div class="logo-main" xmlns="http://www.w3.org/1999/xhtml">
		<img src="images/book.png" /><span class="logo">图书馆图书管理系统</span>
	</div>
</div>
	<!--主体开始-->
	<div class="clearfix1 wrap">
	<div id="Container" style="float:left;width: 100%; height: 100%;min-width:790px;">
		<div class="fns">
			<div id="header-shaw" class="launchweather">
				<div class="title">
					<span class="title-left">图书馆图书管理系统 > 书籍检索</span>
				</div>
				<div class="panel mb15">
					<div class="panel-body">
						<form id="bookform">
						<table class="upload">
							<tr>
								<th>书名：</th>
								<td>
									<input class="input-text" name="name" id="name" type="text" value="" />
								</td>
							</tr>
							<tr>
								<th>作者：</th>
								<td>
									<input class="input-text" name="author" id="author" type="text" value="" />
								</td>
							</tr>
							<tr>
								<th>出版日期：</th>
								<td>
									<div class="input dateTime input-text">
										<input name="publishdate" id="publishdate" class="Wdate input"
											type="text" style="cursor: pointer;height: 18px;" value=""
											onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
									</div>
								</td>
							</tr>
							<tr>
								<th>内容：</th>
								<td>
									<input class="input-text" type="text" name="section" id="section" />（编码格式：UTF-8）
								</td>
							</tr>
							<tr>
								<th></th>
								<td>
									<input class="button button-blue" name="btnSave" id="btnSave" type="submit" value="查 询" /><input class="button button-default" name="" id="btnCancel" type="reset" value="重 置" />
								</td>
							</tr>
						</table>
					</div>
				</div>
				<%
					String result = (String) request.getAttribute("result");
					if (result != null) {
				%>
				<div class="module-history-list">
					<span class="history-list-tips" id="filecount">系统共为你检索出 ${totalPosts} 条结果:</span>
				</div>
				<c:forEach items="${entryList}" var="entry">
				<div class="panel mb15">
					<div class="panel-body">
						<table class="list">
							<thead>
								<tr>
									<th width="20%">书名:</th>
									<th class="last">《${entry.name}》</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>作者:</td>
									<td>${entry.author}</td>
								</tr>
								<tr>
									<td>发布日期:</td>
									<td>${entry.publishDate}</td>
								</tr>
								<tr>
									<td valign="top">章节摘要:</td>
									<td>${entry.section}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				</c:forEach>
				<dl class="list clearfix">
					<dt class="dt">
					</dt>
					<dd class="dd">
						<a href="retrievalbooks?pageNumber=1">首页</a>
						<c:if test="${pageNumber>1}">
							<a href="retrievalbooks?pageNumber=${pageNumber-1}&name=<%=name%>&author=<%=author%>&publishdate=<%=publishdate%>&section=<%=section%>">上一页</a>
						</c:if>
						跳转到第
						<select name="pageNumber" onchange="gotoSelectedPage();">
							<c:forEach begin="1" end="${totalPages}" step="1"
								var="pageIndex">
								<c:choose>
									<c:when test="${pageIndex eq pageNumber}">
										<option value="${pageIndex}" selected="selected">
											${pageIndex}
										</option>
									</c:when>
									<c:otherwise>
										<option value="${pageIndex}">
											${pageIndex}
										</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
						页
						<c:if test="${pageNumber<totalPages}">
							<a
								href="retrievalbooks?pageNumber=${pageNumber+1}&name=<%=name%>&author=<%=author%>&publishdate=<%=publishdate%>&section=<%=section%>">下一页</a>
						</c:if>
						<a
							href="retrievalbooks?pageNumber=${totalPages}&name=<%=name%>&author=<%=author%>&publishdate=<%=publishdate%>&section=<%=section%>">末页</a>
					</dd>
				</dl>
				<%
					}else{
				%>
				<div class="module-history-list">
					<span class="history-list-tips" id="filecount">没有搜索到相关内容查询结果!</span>
				</div>
				<%} %>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="/share/book-left.jsp"%>
	</div>
	<!--主体结束-->
	<!--尾部开始-->
	<%@ include file="/share/foot.jsp"%>
	<!--尾部结束-->
	</body>
</html>