// JavaScript Document

/* 返回头部 */
var lastScrollY = 0;
function heartBeat() {
    var diffY;
    if (document.documentElement && document.documentElement.scrollTop) diffY = document.documentElement.scrollTop;
    else if (document.body) diffY = document.body.scrollTop
    else {
        /*Netscape stuff*/
    }
    percent = .1 * (diffY - lastScrollY);
    if (percent > 0) percent = Math.ceil(percent);
    else percent = Math.floor(percent);
    document.getElementById("full").style.top = parseInt(document.getElementById("full").style.top) + percent + "px";
    lastScrollY = lastScrollY + percent;
    if (lastScrollY < 200) {
        document.getElementById("full").style.display = "none";
    }
    else {
        document.getElementById("full").style.display = "block";
    }
}
var gWidth = document.body.clientWidth;
var ks = (gWidth - 940) / 2 - 30;
suspendcode = "<div class=\"goto_top\" id=\"full\" style='right:" + ks + "px;position:absolute;top:560px;z-index:100'><a onclick='window.scrollTo(0,0);' href='#'></a></div>"
document.write(suspendcode);
window.setInterval("heartBeat()", 1);