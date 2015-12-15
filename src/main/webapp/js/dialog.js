// JavaScript Document

//异步调用，充填对话框内容
//调用直接调用showDialog方法

/* 对话框 */
//显示对话框
function showDialog(){
	$(".dialog").show();
	DialogTL();
	$(window)
	.scroll(function(){ DialogTL() })
	.resize(function(){ DialogTL() });
	$('.dialog .clas_close').click(function(){ $(".dialog").hide(); });
}
//初始对话框位置
function DialogTL(){
	var scrollT = $(window).scrollTop();
	var scrollL = $(window).scrollLeft();
	var windowW = $(window).width()+scrollL;
	var windowH = $(window).height()+scrollT;
	var divh = $('.dialogLayer').height();
	var divw = $('.dialogBox').width();
	var divH = ($(window).height()-divh)/2+scrollT;
	var divW = (windowW-divw)/2;
	divH = (divH<0)?0:divH;
	divW = (divW<0)?0:divW;
	$(".backgroundDialog").css({
	"width": windowW,
	"height": windowH
	});
	$(".dialogLayer").css({
	"left": divW,
	"top": divH
	});
}
