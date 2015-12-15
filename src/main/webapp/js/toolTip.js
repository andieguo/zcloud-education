// JavaScript Document

/* 鼠标选词工具 */
$(document).ready(function() {
    $(function() {
        //插入图标备用
        $('<img src="../../../images/toolTip.gif" id="toolBtn" style="position:absolute;z-index:1000;display:none"/>').appendTo($("body"));
        //选择文字函数
        function selctiTxt() {
            if (document.selection) { //IE
                return document.selection.createRange().text;
            } else { //标准
                return window.getSelection().toString();
            }
        }

        //选择问题显示分享图标
        $(document).bind("mouseup",
        function(e) {
            var e = $.event.fix(e);
            var iX = e.pageX;
            var iY = e.pageY;
            if (selctiTxt().length > 0) {
                setTimeout(function() {
                    $("#toolBtn").css({
                        "left": iX + "px",
                        "top": iY + "px"
                    }).fadeIn(500);
                },
                100);
            } else {
                $("#toolBtn").hide();
            }
        });
        //点击别处隐藏，并且点击自身不隐藏
        $(document).click(function() {
            $("#toolBtn").fadeOut(1000);
            $("#toolBox").fadeOut(200);
            $("#toolList").fadeOut(200);
        });
        $("#toolBox").click(function() {
            event.stopPropagation();
        });
        $("#toolList").click(function() {
            event.stopPropagation();
        });
        //鼠标经过工具按钮淡入工具列表，移出淡出效果
        $("#toolBtn").hover(
        function() {
            $("#toolList").fadeIn();
			var x = $("#toolBtn").offset();
            $("#toolList").attr({
                style: "top:" + x.top + "px; left:" + x.left + "px; display:block;"
            });
        },
        function() {
            $("#toolBtn").fadeOut(1000);
        });
        //鼠标经过功能列表，显示转化编辑框
        $(".codeExchange").hover(
        function() {
			$(".codeExchange").addClass("current")
            $("#toolBox").fadeIn();
			var x = $(".codeExchange").offset();
            $("#toolBox").attr({
                style: "top:" + x.top + "px; left:" + x.left + "px; display:block;"
            });
        },
        function() {
			$(".codeExchange").removeClass("current")
        });
        //鼠标经过转化编辑框，隐藏功能列表
        $("#toolBox").hover(
        function() {
            $("#toolList").fadeOut(1000);
        });
		
		
		
    });
});








