// JavaScript Document
$(function() {
    // Start 窗口的拖动
    var _move = false; //移动标记 
    var _x, _y; //鼠标离控件左上角的相对位置 
    $(".title").mousedown(function(e) {
        _move = true;
        px = $("#count > .box").css("left") == 'auto' ? 568 : parseInt($("#count > .box").css("left"));
        py = $("#count > .box").css("top") == 'auto' ? 66 : parseInt($("#count > .box").css("top"));
        _x = e.pageX - px;
        _y = e.pageY - py;
        $("#count > .box").fadeTo(20, 0.5); //点击后开始拖动并透明显示 
    });
    $(document).mousemove(function(e) {
        if (_move) {
            var x = e.pageX - _x; //移动时根据鼠标位置计算控件左上角的绝对位置 
            var y = e.pageY - _y;
            $("#count > .box").css({
                top: y,
                left: x
            }); //控件新位置 
        }
    }).mouseup(function() {
        _move = false;
        $("#count > .box").fadeTo("fast", 1); //松开鼠标后停止移动并恢复成不透明 
    });
    // End 窗口的拖动
});