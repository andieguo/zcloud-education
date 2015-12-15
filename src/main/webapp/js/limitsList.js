// JavaScript Document

/* 权限选择鼠标效果 */
$(document).ready(function() { 
	$(".userLimitsList").children(".li").children("ul").children("li").hover(
	function() {
		$(this).addClass("hover");
	},
	function() {
		$(this).removeClass("hover");
	});
	$(".userLimitsList").children(".li").children(".subBox").children("ul").children("li").hover(
	function() {
		$(this).addClass("hover");
	},
	function() {
		$(this).removeClass("hover");
	});
});