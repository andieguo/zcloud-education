window.onload = function() {
	gaoliang();
	contentHeight();
};
//左导航高亮
function gaoliang() {
    //获取div下面所有的a标签（返回节点对象）
    var myNav = document.getElementById("nav").getElementsByTagName("a");
    //获取当前窗口的url
    var myURL = document.location.href;
    //循环div下面所有的链接，
    for (var i = 0; i < myNav.length; i++) {
        //获取每一个a标签的herf属性
        var links = myNav[i].getAttribute("href");
        var myURL = document.location.href;
        //查看div下的链接是否包含当前窗口，如果存在，则给其添加样式
        if (myURL.indexOf(links) != -1) {
            myNav[i].className = "on";
        } else {
            myNav[i].className = "";
        }
    }
};
//高度自适应
function contentHeight() {
    var browserHeight = document.documentElement.clientHeight;
    var leftHeight =  browserHeight - 72 + "px";
    var rightHeight =  browserHeight - 92 + "px";
    var tabHeight =  browserHeight - 192 + "px";
    document.getElementById("genere").style.height = leftHeight;
    document.getElementById("header-shaw").style.height = rightHeight;
    document.getElementById("tab_filesystem").style.height = tabHeight;
}
