<%@ page language="java" import="java.util.*"
	import="com.education.experiment.commons.UserBean" import="com.education.experiment.cloudlibrary.Book"
	pageEncoding="UTF-8"%>
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
	$(document).ready(function() {
		$("#btnCancel").click(function() {
			$("#text_box1").val("");
			$("#text_box2").val("");
		});
	});
</script>
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
		$(document).ready(function() {
		$("#name").val("<%=name%>");
		$("#author").val("<%=author%>");
		$("#publishdate").val("<%=publishdate%>");
		$("#section").val("<%=section%>");
	});
</script>
		<script type="text/javascript">
	function submitItem() {
		with (document.getElementById("bookform")) {
			method = "get";
			action = "retrievalbooks";
			submit();
		}
	}

	$(document)
			.ready(
					function() {
						$("#btnSave")
								.click(
										function() {
											var name = $(
													"input:text[name='name']")
													.val();
											var author = $(
													"input:text[name='author']")
													.val();
											var publishdate = $(
													"input:text[name='publishdate']")
													.val();
											var section = $(
													"input:text[name='section']")
													.val();
											if (name == "" && author == ""
													&& publishdate == ""
													&& section == "") {
												alert("请输入查询条件!");
											} else {
												submitItem();
											}
										});
					});
</script>
		<script language="javascript" type="text/javascript"
			src="My97DatePicker/WdatePicker.js"></script>
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
		<!--主体开始-->
		<div id="content" class="clearfix">
			<div id="title">
				<h1>
					图书馆图书管理云
				</h1>
			</div>

			<!--列表开始-->
			<div id="boxSearch">
				<div class="subNavi">
					<ul>
						<li>
							<a href='uploadbooks.jsp'>书籍上传和索引</a>
						</li>
						<li>
							<a href='downloadbooks.jsp'>书籍下载</a>
						</li>
						<li class="current">
							<a href='retrievalbooks.jsp'>书籍检索</a>
						</li>
					</ul>
				</div>

				<form id="bookform">
					<div id="selectSet">
						<!--默认选项开始-->
						<div id="normal">
							<dl class="list clearfix">
								<dt class="dt">
									书名：
								</dt>
								<dd class="dd">
									<span class="input text"><input name="name" id="name"
											type="text" value="" /> </span>
								</dd>
							</dl>
							<dl class="list clearfix">
								<dt class="dt">
									作者：
								</dt>
								<dd class="dd">
									<span class="input text"><input name="author"
											id="author" type="text" value="" /> </span>
								</dd>
							</dl>
							<dl class="list clearfix">
								<dt class="dt">
									出版日期：
								</dt>
								<dd class="dd">
									<div class="input dateTime">
										<input name="publishdate" id="publishdate" class="Wdate input"
											type="text" style="cursor: pointer;" value=""
											onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
									</div>
								</dd>
							</dl>
							<dl class="list clearfix">
								<dt class="dt">
									内容：
								</dt>
								<dd class="dd">
									<span class="input text"><input name="section"
											id=section " 
											type="text" value="" /> </span>
								</dd>
							</dl>
							<br />
						</div>
						<!--默认选项结束-->
						<%
							String result = (String) request.getAttribute("result");
							if (result != null) {
						%>
						系统共为你检索出 ${totalPosts} 条结果:
						<br />
						<br />
						<c:forEach items="${entryList}" var="entry">
							<tr align="center">
								<td>
									<span class="input text">书名:</span>&nbsp;&nbsp;${entry.name}
								</td>
								<br />
								<td>
									<span class="input text">作者:</span>&nbsp;&nbsp;${entry.author}
								</td>
								<br />
								<td>
									<span class="input text">发布日期:</span>&nbsp;&nbsp;${entry.publishDate}
								</td>
								<br />
								<td>
									<span class="input text">章节摘要:</span>&nbsp;&nbsp;${entry.section}
								</td>
							</tr>
							<br /><br />
						</c:forEach>
						<br />
						<br />
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
						没有搜索到相关内容查询结果!
						<%} %>
						<div class="foot">
							<span class="submit"><input name="" type="button"
									id="btnSave" value="查 询" /> </span>
							<span class="reset"><input name="" id="btnCancel"
									type="reset" value="重 置" /> </span>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!--主体结束-->
		<!--尾部开始-->
		<div id="footer"></div>
		<!--尾部结束-->
	</body>
</html>