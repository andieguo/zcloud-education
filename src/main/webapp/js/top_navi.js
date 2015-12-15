// JavaScript Document

/* 头部导航菜单 */
$(document).ready(function() { //文档加载
	$("#header .li").hover(
	function() {
		$(this).addClass("current"); //鼠标经过添加hover样式
	},
	function() {
		$(this).removeClass("current"); //鼠标离开移除hover样式
	});
});