// JavaScript Document

/* 展开收起高级选项 */
    $(document).ready(function() {
        $("#status").click(showFile);
    })
    function showFile() {
        if ($("#super").css("display") == "none") {
            $("#super").css("display", "inline");
            $("#status").text("查询条件 ↑");
        } else {
            $("#super").css("display", "none");
            $("#status").text("查询条件 ↓");
        }
    }