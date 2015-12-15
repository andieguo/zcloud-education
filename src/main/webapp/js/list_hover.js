// JavaScript Document

/* 列表鼠标经过效果 */
    $(document).ready(function() {
        $(".normal").mouseover(function() {
            $(this).addClass("cur");
        });
        $(".normal").mouseout(function() {
            $(this).removeClass("cur");
        });
        //简介与详情的切换效果
        $(".normal").click(function() {
			if($(this).next(".details").is(':visible')){
				$(this).next(".details").hide();
			}else{
				$(".normal").each(function(){
					$(this).next(".details").hide();
				});
				$(this).next(".details").show();
			}
			$(".normal").removeClass('current');
			$(this).addClass('current');
        });
    });