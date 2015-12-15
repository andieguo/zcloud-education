// JavaScript Document

/* 筛选条 */
$(document).ready(function() {
	//据顶部200px时开始滚动
    $(function() {
        $(window).scroll(function() {
            if ($(window).scrollTop() < 200) {
                $("#sift .btnSift").removeClass("roll")
                $("#sift .barSearch").removeClass("roll")
            }
            else {
                $("#sift .btnSift").addClass("roll");
                $("#sift .barSearch").addClass("roll");
            }
        });
		//小按钮鼠标经过效果
        $(document).ready(function() {
            $("div.btnSift").mouseover(function() {
                $("div.btnSift").addClass("current");
            });
            $("div.btnSift").mouseout(function() {
                $("div.btnSift").removeClass("current");
            });
			//小按钮与筛选条之间的切换效果
            $(function() {
                $(document).click(function() {
                    $("#sift .barSearch").hide();
                    $(".btnSift").show();
                });
                $("#sift .barSearch").click(function(event) {
                    event.stopPropagation();
                });
                $(".btnSift").click(function(event) {
                    event.stopPropagation();
                    $("#sift .barSearch").show();
                    $(".btnSift").hide();
                });
            });
        });
		
    });
});