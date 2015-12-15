// JavaScript Document

/* 网管导航菜单 */
$(document).ready(function() { 
	$(".webmasterNavi li.li").hover(
	function() {
		$(this).addClass("current");
	},
	function() {
		$(this).removeClass("current");
	});
});