// JavaScript Document

/* 导航菜单滚动效果 */
    $(document).ready(function() {
        $("#down").click(
        function() {
            $(".box_2").show();
            $(".box_1").hide();
            $("#down").hide();
            $("#up").show();
        });
        $("#up").click(
        function() {
            $(".box_1").show();
            $(".box_2").hide();
            $("#up").hide();
            $("#down").show();
        });
    });

