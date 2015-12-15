// JavaScript Document

/* 文件上传 */
$(document).ready(function() {
	$("#file_uploader1").change(function() {
		var $text_box = $("#text_box1"); // 模拟的上传控件的文本框
		var $txt = $(this).val(); // 默认上传控件的值
		$("#text_box1").val($txt); // 将上传控件的值赋给.text_box文本控件
	});
	$("#file_uploader2").change(function() {
		var $text_box = $("#text_box2"); // 模拟的上传控件的文本框
		var $txt = $(this).val(); // 默认上传控件的值
		$("#text_box2").val($txt); // 将上传控件的值赋给.text_box文本控件
	});
	$("#file_uploader").change(function() {
		var $text_box = $("#text_box"); // 模拟的上传控件的文本框
		var $txt = $(this).val(); // 默认上传控件的值
		$("#text_box").val($txt); // 将上传控件的值赋给.text_box文本控件
	});
})