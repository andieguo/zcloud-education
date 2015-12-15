<%@ page language="java" import="java.util.*"
	import="com.education.experiment.commons.UserBean" pageEncoding="UTF-8"%>
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
</head>
<body>
<div class="hd-main" style="min-width:1000px;">
	<div class="logo-main" xmlns="http://www.w3.org/1999/xhtml">
		<img src="images/weixin.png" /><span class="logo">微信关系分析系统</span>
	</div>
</div>
<div class="clearfix1 wrap">
	<div id="Container" style="float:left;width: 100%; height: 100%;min-width:790px;">
		<div class="fns">
			<div id="header-shaw" style="padding-top:0px; padding-bottom:60px;">
				<form action="uploadweixinparsing" method="post">
					<div id="selectSet"style="padding-top:0px; padding-bottom:0px;" >
						<!--默认选项开始-->
						<div id="normal">
							<dl class="list clearfix">
								<dt class="dt">
									通信时间点：
								</dt>
								<dd class="dd">
									<div class="input dateTime">
										<input name="datePoint" id="datePoint" class="Wdate input"
											type="text" style="cursor: pointer;" value=""
											onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
									</div>
								</dd>
								（注：大于等于为符合条件）
							</dl>
							<dl class="list clearfix">
								<dt class="dt">
									通信时长：
								</dt>
								<dd class="dd">
									<span class="input text"><input name="minute"
											type="text" value="" /> </span>分钟
								</dd>
								（注：大于等于为符合条件）
							</dl>
							<dl class="list clearfix">
								<dt class="dt">
									性别选择：
								</dt>
								<dd class="dd">
									<span><input name="sex" type="radio" value="男男"
											checked="checked" /> <label for="allPoint">
											男男
										</label> </span>
									<span><input name="sex" type="radio" value="女女" /> <label
											for="selectPoint">
											女女
										</label> </span>
									<span><input name="sex" type="radio" value="异性" /> <label
											for="selectPoint">
											异性
										</label> </span>
									<span><input name="sex" type="radio" value="ALL" /> <label
											for="selectPoint">
											全部
										</label> </span>
								</dd>
							</dl>
							<dl class="list clearfix">
								<dt class="dt">
									是否好友关系：
								</dt>
								<dd class="dd">
									<span><input name="isfriend" type="radio" value="是"
											checked="checked" /> <label for="allPoint">
											是
										</label> </span>
									<span><input name="isfriend" type="radio" value="否" />
										<label for="selectPoint">
											否
										</label> </span>
									<span><input name="isfriend" type="radio" value="ALL" />
										<label for="selectPoint">
											两者都包含
										</label> </span>
								</dd>
							</dl>
							<dl class="list clearfix">
								<dt class="dt">
									年龄范围：
								</dt>
								<dd class="dd">
									<span class="text input"><input name="agespan"
											type="text" /> </span>
								</dd>
								（格式：35至38）
							</dl>
							<dl class="list clearfix">
								<dt class="dt">
									职业：
								</dt>
								<dd class="dd">
									<p>
										<span><label>
												<input class="checkbox" name="vocation" type="checkbox"
													value="餐饮" />
												餐饮
											</label> </span>
										<span><label>
												<input class="checkbox" name="vocation" type="checkbox"
													value="教育" />
												教育
											</label> </span>
										<span><label>
												<input class="checkbox" name="vocation" type="checkbox"
													value="金融" />
												金融
											</label> </span>
										<span><label>
												<input class="checkbox" name="vocation" type="checkbox"
													value="律师" />
												律师
											</label> </span>
										<span><label>
												<input class="checkbox" name="vocation" type="checkbox"
													value="娱乐" />
												娱乐
											</label> </span>
									</p>
									<p>
										<span><label>
												<input class="checkbox" name="vocation" type="checkbox"
													value="军人" />
												军人
											</label> </span>
										<span><label>
												<input class="checkbox" name="vocation" type="checkbox"
													value="体育" />
												体育
											</label> </span>
										<span><label>
												<input class="checkbox" name="vocation" type="checkbox"
													value="建筑" />
												建筑
											</label> </span>
										<span><label>
												<input class="checkbox" name="vocation" type="checkbox"
													value="无业" />
												无业
											</label> </span>
									</p>
								</dd>
							</dl>
							<dl class="list clearfix">
								<dt class="dt">
									通信地点：
								</dt>
								<dd class="dd">
									<span class="text input"><input name="places"
											type="text" /> </span>
								</dd>
								（多个通信地点用英文逗号隔开）
							</dl>
							<dl class="list clearfix">
								<dt class="dt">
									通信内容关键词：
								</dt>
								<dd class="dd">
									<span class="text input"><input name="keywords"
											type="text" /> </span>
								</dd>
								（多个关键词用英文逗号隔开）
							</dl>
						</div>
						<!--默认选项结束-->
						<div class="foot">
							<span class="submit"><input name="" type="submit"
									value="上 传" /> </span>
							<span class="reset"><input name="" id="btnCancel"
									type="reset" value="重 置" /> </span>
						</div>
					</div>
				</div>
		</form>
			</div>
		</div>
	</div>
	<div class="aside main" style="width: 210px; float:left;">
		<div style="height: 533px;" class="b-view genere jfk-scrollbar" id="genere">
			<div class="minheight-forfooter">
				<ul class="b-list-3" id="aside-menu">
					<li class="b-list-item"><a  href="downloadweixin.jsp" class="sprite2" hidefocus="true"><span class="text1"><span class="img-ico aside-moth"></span>示例文件下载</span></a></li><li class="b-list-item separator-1"></li>
					<li class="b-list-item"><a href="#"><span class="text1"><span class="img-ico aside-mapp"></span>数据上传</span></a></li>				
						<ul class="contact-list cls part">					
							<li class="icon mou-evt">
								<a href="uploadweixin.jsp" >
									<div title="模拟数据上传" class="list-wrap" style="width: auto;">
										<span class="name">模拟数据上传</span>
									</div>
								</a>
							</li>					
							<li class="icon mou-evt on">
								<a href="uploadweixinsearcher.jsp" >
									<div title="分析条件上传" class="list-wrap" style="width: auto;">
										<span class="name">分析条件上传</span>
									</div>
								</a>
							</li>
						</ul>
					<li class="b-list-item separator-1"></li>
					<li class="b-list-item"><a href="#"><span class="text1"><span class="img-ico aside-recycle"></span>数据删除</span></a>					
						<ul class="contact-list cls part">						
							<li class="icon mou-evt">
								<a href="deleteweixin.jsp" >
									<div title="模拟数据删除" class="list-wrap" style="width: auto;">
										<span class="name">模拟数据删除</span>
									</div>	
								</a>
							</li>								
							<li class="icon mou-evt">
								<a href="deleteparsing.jsp" >
									<div title="分析条件删除" class="list-wrap" style="width: auto;">
										<span class="name">分析条件删除</span>
									</div>
								</a>
							</li>
							
						</ul>
					</li>
					<li class="b-list-item separator-1"></li>
					<li class="b-list-item"><a href="#" class="sprite2 b-no-ln" hidefocus="true" id="tab-recyle" unselectable="on"><span class="text1"><span class="img-ico aside-js"></span>分析数据计算</span></a></li>				
					<li class="b-list-item"><a href="#" class="sprite2 b-no-ln" hidefocus="true" id="tab-share" unselectable="on"><span class="text1"><span class="img-ico aside-share"></span>分析结果查看</span></a></li>			
				</ul>
			</div>
		</div>
	</div>
</div>
</div>
<div class="banquan">
	版权所有© 2013 北京斑步志伟科技公司
</div>
</body>
</html>
