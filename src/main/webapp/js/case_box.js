// JavaScript Document

/* 案件鼠标效果 */
$(document).ready(function() { 
	$("#caseBox").children(".list").children("ul").children("li").hover(
	function() {
		$(this).addClass("cur");
	},
	function() {
		$(this).removeClass("cur");
	});
});