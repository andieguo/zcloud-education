// JavaScript Document
/* 协议鼠标经过适应当前窗口位置 */
$(document).ready(function() {
	$(".protocolBtn").live({
		'mouseover':function(){
			positionProListDiv($(this));
		},
		'mouseout':function(){
			hideProListDiv();
		}
	});
});
//隐藏div
function hideProListDiv(){
	$("#id_proList_dialog").hide();
	$("#id_proList_dialog").hover(
	function() {
		$(this).show();
	},
	function() {
		$(this).hide();
	});		
}
//计算div的位置
function positionProListDiv($e){
	var X = $e.offset().left; //btn水平位置
	var Y = $e.offset().top; //btn距页面顶距离
	var btnH = $e.height(); //标签自身高度
	var scrollT = $(window).scrollTop(); //滚动轴距顶距离
	var windowH = $(window).height(); //当前窗口高度
	var bodyH = document.body.clientHeight; //body高度
	var btnY = $e.offset().top - 37; //btn距顶距离-页面头部(top)的高度
	var divH = $("#id_proList_dialog").height(); //div自身高度
	var btn_H = btnY - scrollT; //计算btn距当前窗口顶部距离离
	var btn_DD = bodyH - btnY - btnH; //标签底端距页面底部的距离
	if (btn_H > windowH / 2) {
		if (divH > btnY) {
			down(X, Y);
		} else {
			up(divH, X, Y);
		}
	} else {
		if (divH <= btn_DD) {
			down(X, Y);
		} else {
			if (divH > btnY) {
				down(X, Y);
			} else {
				up(divH, X, Y);
			}
		}
	}	
}

//向上显示
function up(divH, x, y) {
	$("#id_proList_dialog").show().css({
		left: x - 680,
		top: y - divH //- 4
	});
}
//向下显示
function down(x, y) {
	$("#id_proList_dialog").show().css({
		left: x - 680,
		top: y + 25-4
	});
}