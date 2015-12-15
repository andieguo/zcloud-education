// JavaScript Document

/* 对话框 */
    $(document).ready(function() {
        $(".editBtn").click(function() {
            $(".dialog").show();
            $(".backgroundDialog").css({
                "width": $(document).width(),
                "height": $(document).height()
            });
            var divWidth = $(".dialogLayer").width() / 2;
            $(".dialogLayer").css({
                "left": +$(document).width() / 2 + "px",
                "margin-left": "-" + divWidth + "px"
            });
        });
        $(".close").click(function() {
            $(".dialog").hide();
        });
        $(function() {
            // Start 窗口的拖动
            var _move = false; //移动标记 
            var _x, _y; //鼠标离控件左上角的相对位置 
            $(".dialog .title").mousedown(function(e) {
                _move = true;
                px = $(".dialogLayer").css("left") == 'auto' ? 0 : parseInt($(".dialogLayer").css("left"));
                py = $(".dialogLayer").css("top") == 'auto' ? 0 : parseInt($(".dialogLayer").css("top"));
                _x = e.pageX - px;
                _y = e.pageY - py;
                $(".dialogLayer").fadeTo(20, 0.5); //点击后开始拖动并透明显示 
            });
            $(document).mousemove(function(e) {
                if (_move) {
                    var x = e.pageX - _x; //移动时根据鼠标位置计算控件左上角的绝对位置 
                    var y = e.pageY - _y;
                    $(".dialogLayer").css({
                        top: y,
                        left: x
                    }); //控件新位置 
                }
            }).mouseup(function() {
                _move = false;
                $(".dialogLayer").fadeTo("fast", 1); //松开鼠标后停止移动并恢复成不透明 
            });
            // End 窗口的拖动
        });
    });