// JavaScript Document

/* 选择节点 */
$(document).ready(function() {
    $(".pointList").children().children().children("span").hover(
    function() {
        $(this).addClass("current");
    },
    function() {
        $(this).removeClass("current");
    });
});