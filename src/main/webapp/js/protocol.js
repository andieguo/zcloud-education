// JavaScript Document

/* 协议鼠标效果 */
$(document).ready(function() { 
	$(".protocol").children("dl").children("dd").children("ul").children("li").hover(
	function() {
		$(this).addClass("cur");
	},
	function() {
		$(this).removeClass("cur");
	});
});