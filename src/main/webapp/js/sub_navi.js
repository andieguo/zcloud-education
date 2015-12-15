// JavaScript Document

/* 子导航菜单 */
    $(document).ready(function() {
        $(".subNavi .link_box").hover(
        function() {
            $(this).children(".box").show();
        },
        function() {
            $(this).children(".box").hide();
        });
    });