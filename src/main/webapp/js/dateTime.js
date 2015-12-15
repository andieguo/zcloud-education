    // JavaScript Document
    /*
	* 日期选择
	* 使用方法：
	* (1)只选择日期   <input type="text" name="date"   readOnly onClick="setDay(this);">
	* (2)选择日期和小时 <input type="text" name="dateh" readOnly onClick="setDayH(this);">
	* (3)选择日期和小时及分钟 <input type="text" name="datehm" readOnly onClick="setDayHM(this);">
	* 设置参数的方法
	* (1)设置日期分隔符    setDateSplit(strSplit);默认为"-"
	* (2)设置日期与时间之间的分隔符 setDateTimeSplit(strSplit);默认为" "
	* (3)设置时间分隔符    setTimeSplit(strSplit);默认为":"
	* (4)设置(1),(2),(3)中的分隔符 setSplit(strDateSplit,strDateTimeSplit,strTimeSplit);
	* (5)设置开始和结束年份    setYearPeriod(intDateBeg,intDateEnd)
	* 说明：
	* 默认返回的日期时间格式类似：2005-02-02 08:08
	*/

    //------------------ 样式定义 ---------------------------//

    //功能按钮同样样式
    var time_turn_base = "height:16px;font-size:9pt;color:white;border:0 solid #CCCCCC;cursor:hand;background-color:#2650A6;";
    //翻年、月等的按钮
    var time_turn = "width:28px;" + time_turn_base;
    //关闭、清空等按钮样式
    var time_turn2 = "width:22px;" + time_turn_base;
    //年选择下拉框
    var time_select = "width:64px;display:none;";
    //月、时、分选择下拉框
    var time_select2 = "width:46px;display:none;";
    //日期选择控件体的样式
    var time_body = "display:none;";
    var prevYear = "prevYear"
    var prevMonth = "prevMonth"
    var nextMonth = "nextMonth"
    var nextYear = "nextYear"
    //显示周的的样式
    var time_week = "week clearfix";
    //显示日的的样式
    var time_day = "day clearfix";
    //字体样式
    var time_font = "color:#FFCC00;font-size:9pt;cursor:hand;";
    //链接的样式
    var time_link = "text-decoration:none;font-size:9pt;color:#2650A6;";
    //横线
    var time_line = "border-bottom:1 solid #6699CC";
    //------------------ 变量定义 ---------------------------//
    var sssYearSt = 1950; //可选择的开始年份
    var sssYearEnd = 2050; //可选择的结束年份
    var sssDateNow = new Date();
    var sssYear = sssDateNow.getFullYear(); //定义年的变量的初始值
    var sssMonth = sssDateNow.getMonth() + 1; //定义月的变量的初始值
    var sssDay = sssDateNow.getDate();
    var sssHour = 8; //sssDateNow.getHours();
    var sssMinute = 0; //sssDateNow.getMinutes();
    var sssArrDay = new Array(42); //定义写日期的数组
    var sssDateSplit = "-"; //日期的分隔符号
    var sssDateTimeSplit = " "; //日期与时间之间的分隔符
    var sssTimeSplit = ":"; //时间的分隔符号
    var sssOutObject; //接收日期时间的对象
    var arrsssHide = new Array(); //被强制隐藏的标签
    var m_bolShowHour = false; //是否显示小时
    var m_bolShowMinute = false; //是否显示分钟
    var m_aMonHead = new Array(12); //定义阳历中每个月的最大天数
    m_aMonHead[0] = 31;
    m_aMonHead[1] = 28;
    m_aMonHead[2] = 31;
    m_aMonHead[3] = 30;
    m_aMonHead[4] = 31;
    m_aMonHead[5] = 30;
    m_aMonHead[6] = 31;
    m_aMonHead[7] = 31;
    m_aMonHead[8] = 30;
    m_aMonHead[9] = 31;
    m_aMonHead[10] = 30;
    m_aMonHead[11] = 31;
    // ---------------------- 用户可调用的函数 -----------------------------//
    //用户主调函数－只选择日期
    function setDay(obj) {
        sssOutObject = obj;
        //如果标签中有值，则将日期初始化为当前值
        var strValue = sssTrim(sssOutObject.value);
        if (strValue != "") {
            sssInitDate(strValue);
        }
        sssPopCalendar();
    }
    //用户主调函数－选择日期和小时
    function setDayH(obj) {
        sssOutObject = obj;
        m_bolShowHour = true;
        //如果标签中有值，则将日期和小时初始化为当前值
        var strValue = sssTrim(sssOutObject.value);
        if (strValue != "") {
            sssInitDate(strValue.substring(0, 10));
            var hour = strValue.substring(11, 13);
            if (hour < 10) sssHour = hour.substring(1, 2);
        }
        sssPopCalendar();
    }
    //用户主调函数－选择日期和小时及分钟
    function setDayHM(obj) {
        sssOutObject = obj;
        m_bolShowHour = true;
        m_bolShowMinute = true;
        //如果标签中有值，则将日期和小时及分钟初始化为当前值
        var strValue = sssTrim(sssOutObject.value);
        if (strValue != "") {
            sssInitDate(strValue.substring(0, 10));
            var time = strValue.substring(11, 16);
            var arr = time.split(sssTimeSplit);
            sssHour = arr[0];
            sssMinute = arr[1];
            if (sssHour < 10) sssHour = sssHour.substring(1, 2);
            if (sssMinute < 10) sssMinute = sssMinute.substring(1, 2);
        }
        sssPopCalendar();
    }
    //设置开始日期和结束日期
    function setYearPeriod(intDateBeg, intDateEnd) {
        sssYearSt = intDateBeg;
        sssYearEnd = intDateEnd;
    }
    //设置日期分隔符。默认为"-"
    function setDateSplit(strDateSplit) {
        sssDateSplit = strDateSplit;
    }
    //设置日期与时间之间的分隔符。默认为" "
    function setDateTimeSplit(strDateTimeSplit) {
        sssDateTimeSplit = strDateTimeSplit;
    }
    //设置时间分隔符。默认为":"
    function setTimeSplit(strTimeSplit) {
        sssTimeSplit = strTimeSplit;
    }
    //设置分隔符
    function setSplit(strDateSplit, strDateTimeSplit, strTimeSplit) {
        sssDateSplit(strDateSplit);
        sssDateTimeSplit(strDateTimeSplit);
        sssTimeSplit(strTimeSplit);
    }
    //设置默认的日期。格式为：YYYY-MM-DD
    function setDefaultDate(strDate) {
        sssYear = strDate.substring(0, 4);
        sssMonth = strDate.substring(5, 7);
        sssDay = strDate.substring(8, 10);
    }
    //设置默认的时间。格式为：HH24:MI
    function setDefaultTime(strTime) {
        sssHour = strTime.substring(0, 2);
        sssMinute = strTime.substring(3, 5);
    }
    // ---------------------- end 用户可调用的函数 -----------------------------//
    //------------------ begin 页面显示部分 ---------------------------//
    var weekName = new Array("日", "一", "二", "三", "四", "五", "六");
    document.write('<div id="boxDate" style="' + time_body + '">');
    document.write('<div id="boxDateText" class="clearfix" isControled="true">');

    document.write('<input type="button" class="' + nextYear + '" value="" title="下一年" onClick="sssNextYear();">');
    document.write('<input type="button" class="' + nextMonth + '" value="" title="下一月" onClick="sssNextMonth();">');

    document.write('<input type="button" class="' + prevYear + '" value="" title="上一年" onClick="sssPrevYear();">');
    document.write('<input type="button" class="' + prevMonth + '" value="" title="上一月" onClick="sssPrevMonth();">');

    document.write('<span class="datebox">');
    document.write('<span id="sssYearHead" isControled="true" class="' + time_font + '" ' + ' onclick="spanYearCEvent();">年</span>');
    document.write('<select id="selTianYear" class="' + time_select + '" isControled="true" ' + ' onChange="sssYear=this.value;sssSetDay(sssYear,sssMonth);document.all.sssYearHead.style.display=\'\';' + 'this.style.display=\'none\';">');
    for (var i = sssYearSt; i <= sssYearEnd; i++) {
        document.writeln('<option value="' + i + '">' + i + '</option>');
    }
    document.write('</select>');
    document.write('<span id="sssMonthHead" isControled="true" class="' + time_font + '" ' + 'onclick="spanMonthCEvent();">月</span>');
    document.write('<select id="selTianMonth" class="' + time_select2 + '" isControled="true" ' + 'onChange="sssMonth=this.value;sssSetDay(sssYear,sssMonth);document.all.sssMonthHead.style.display=\'\';' + 'this.style.display=\'none\';">');
    for (var i = 1; i <= 12; i++) {
        document.writeln('<option value="' + i + '">' + i + '</option>');
    }
    document.write('</select>');
    document.write('</span>');

    document.write('</div>');

    document.write('<div class="' + time_week + '" onselectstart="return false">');
    document.write(' <ul isControled="true">');
    for (var i = 0; i < weekName.length; i++) {
        //输出星期
        document.write('<li isControled="true">' + weekName[i] + '</li>');
    }
    document.write(' </ul>');
    document.write('</div>');
    //输出天的选择
    document.write('<div class="' + time_day + '" onselectstart="return false">');
    var n = 0;
    for (var i = 0; i < 5; i++) {
        document.write(' <ul id="trsssDay' + i + '" >');
        for (var j = 0; j < 7; j++) {
            document.write('<li id="tdsssDay' + n + '" ' + 'onClick="sssDay=this.innerText;sssSetValue(true);"></li>');
            n++;
        }
        document.write(' </ul>');
    }
    document.write(' <ul id="trsssDay5" >');
    document.write('<li id="tdsssDay35" onClick="sssDay=this.innerText;sssSetValue(true);"></li>');
    document.write('<li id="tdsssDay36" onClick="sssDay=this.innerText;sssSetValue(true);"></li>');
    //    document.write('<li align="right" colspan="5"><a href="javascript:sssClear();" class="' + time_link + '">清空</a>' + '&nbsp;<a href="javascript:sssHideControl();" class="' + time_link + '">关闭</a>' + '&nbsp;<a href="javascript:sssSetValue(true);" class="' + time_link + '">确定</a>&nbsp;' + '</li>');
    document.write(' </ul>');
    document.write('</div>');

    document.write('<div class="timeFoot">');
    document.write('<span class="li"><a href="javascript:sssToday();" class="' + time_link + '">今天</a>' + '</span>');
    document.write('<span class="li"><a href="javascript:sssClear();" class="' + time_link + '">清空</a>' + '</span>');
    document.write('<span class="libox">');
    document.write('<span id="sssHourHead" isControled="true" class="' + time_font + 'display:none;" ' + 'onclick="spanHourCEvent();">时</span>');
    document.write('<select id="selTianHour" class="' + time_select2 + 'display:none;" isControled="true" ' + ' onChange="sssHour=this.value;sssWriteHead();document.all.sssHourHead.style.display=\'\';' + 'this.style.display=\'none\';">');
    for (var i = 0; i <= 23; i++) {
        document.writeln('<option value="' + i + '">' + i + '</option>');
    }
    document.write('</select>');
    document.write('<span id="sssMinuteHead" isControled="true" class="' + time_font + 'display:none;" ' + 'onclick="spanMinuteCEvent();">分</span>');
    document.write('<select id="selTianMinute" class="' + time_select2 + 'display:none;" isControled="true" ' + ' onChange="sssMinute=this.value;sssWriteHead();document.all.sssMinuteHead.style.display=\'\';' + 'this.style.display=\'none\';">');
    for (var i = 0; i <= 59; i++) {
        document.writeln('<option value="' + i + '">' + i + '</option>');
    }
    document.write('</select>');
    document.write('</span>');
    document.write('</div>');

    document.write('</div>');
    //------------------ end 页面显示部分 ---------------------------//
    //------------------ 显示日期时间的span标签响应事件 ---------------------------//
    //单击年份span标签响应
    function spanYearCEvent() {
        hideElementsById(new Array("selTianYear", "sssMonthHead"), false);
        if (m_bolShowHour) hideElementsById(new Array("sssHourHead"), false);
        if (m_bolShowMinute) hideElementsById(new Array("sssMinuteHead"), false);
        hideElementsById(new Array("sssYearHead", "selTianMonth", "selTianHour", "selTianMinute"), true);
    }
    //单击月份span标签响应
    function spanMonthCEvent() {
        hideElementsById(new Array("selTianMonth", "sssYearHead"), false);
        if (m_bolShowHour) hideElementsById(new Array("sssHourHead"), false);
        if (m_bolShowMinute) hideElementsById(new Array("sssMinuteHead"), false);
        hideElementsById(new Array("sssMonthHead", "selTianYear", "selTianHour", "selTianMinute"), true);
    }
    //单击小时span标签响应
    function spanHourCEvent() {
        hideElementsById(new Array("sssYearHead", "sssMonthHead"), false);
        if (m_bolShowHour) hideElementsById(new Array("selTianHour"), false);
        if (m_bolShowMinute) hideElementsById(new Array("sssMinuteHead"), false);
        hideElementsById(new Array("sssHourHead", "selTianYear", "selTianMonth", "selTianMinute"), true);
    }
    //单击分钟span标签响应
    function spanMinuteCEvent() {
        hideElementsById(new Array("sssYearHead", "sssMonthHead"), false);
        if (m_bolShowHour) hideElementsById(new Array("sssHourHead"), false);
        if (m_bolShowMinute) hideElementsById(new Array("selTianMinute"), false);
        hideElementsById(new Array("sssMinuteHead", "selTianYear", "selTianMonth", "selTianHour"), true);
    }
    //根据标签id隐藏或显示标签
    function hideElementsById(arrId, bolHide) {
        var strDisplay = "";
        if (bolHide) strDisplay = "none";
        for (var i = 0; i < arrId.length; i++) {
            var obj = document.getElementById(arrId[i]);
            obj.style.display = strDisplay;
        }
    }
    //------------------ end 显示日期时间的span标签响应事件 ---------------------------//
    //判断某年是否为闰年
    function isPinYear(year) {
        var bolRet = false;
        if (0 == year % 4 && ((year % 100 != 0) || (year % 400 == 0))) {
            bolRet = true;
        }
        return bolRet;
    }
    //得到一个月的天数，闰年为29天
    function getMonthCount(year, month) {
        var c = m_aMonHead[month - 1];
        if ((month == 2) && isPinYear(year)) c++;
        return c;
    }
    //重新设置当前的日。主要是防止在翻年、翻月时，当前日大于当月的最大日
    function setRealDayCount() {
        if (sssDay > getMonthCount(sssYear, sssMonth)) {
            //如果当前的日大于当月的最大日，则取当月最大日
            sssDay = getMonthCount(sssYear, sssMonth);
        }
    }
    //在个位数前加零
    function addZero(value) {
        if (value < 10) {
            value = "0" + value;
        }
        return value;
    }
    //取出空格
    function sssTrim(str) {
        return str.replace(/(^\s*)|(\s*$)/g, "");
    }
    //为select创建一个option
    function createOption(objSelect, value, text) {
        var option = document.createElement("OPTION");
        option.value = value;
        option.text = text;
        objSelect.options.add(option);
    }
    //往前翻 Year
    function sssPrevYear() {
        if (sssYear > 999 && sssYear < 10000) {
            sssYear--;
        }
        else {
            alert("年份超出范围（1000-9999）！");
        }
        sssSetDay(sssYear, sssMonth);
        //如果年份小于允许的最小年份，则创建对应的option
        if (sssYear < sssYearSt) {
            sssYearSt = sssYear;
            createOption(document.all.selTianYear, sssYear, sssYear + "年");
        }
        checkSelect(document.all.selTianYear, sssYear);
        sssWriteHead();
    }

    //往后翻 Year
    function sssNextYear() {
        if (sssYear > 999 && sssYear < 10000) {
            sssYear++;
        }
        else {
            alert("年份超出范围（1000-9999）！");
            return;
        }
        sssSetDay(sssYear, sssMonth);
        //如果年份超过允许的最大年份，则创建对应的option
        if (sssYear > sssYearEnd) {
            sssYearEnd = sssYear;
            createOption(document.all.selTianYear, sssYear, sssYear + "年");
        }
        checkSelect(document.all.selTianYear, sssYear);
        sssWriteHead();
    }

    //选择今天
    function sssToday() {
        sssYear = sssDateNow.getFullYear();
        sssMonth = sssDateNow.getMonth() + 1;
        sssDay = sssDateNow.getDate();
        sssSetValue(true);
        //sssSetDay(sssYear,sssMonth);
        //selectObject();
    }
    //往前翻月份
    function sssPrevMonth() {
        if (sssMonth > 1) {
            sssMonth--
        } else {
            sssYear--;
            sssMonth = 12;
        }
        sssSetDay(sssYear, sssMonth);
        checkSelect(document.all.selTianMonth, sssMonth);
        sssWriteHead();
    }
    //往后翻月份
    function sssNextMonth() {
        if (sssMonth == 12) {
            sssYear++;
            sssMonth = 1
        } else {
            sssMonth++
        }
        sssSetDay(sssYear, sssMonth);
        checkSelect(document.all.selTianMonth, sssMonth);
        sssWriteHead();
    }
    //向span标签中写入年、月、时、分等数据
    function sssWriteHead() {
        document.all.sssYearHead.innerText = sssYear + "年";
        document.all.sssMonthHead.innerText = sssMonth + "月";
        if (m_bolShowHour) document.all.sssHourHead.innerText = " " + sssHour + "时";
        if (m_bolShowMinute) document.all.sssMinuteHead.innerText = sssMinute + "分";
        sssSetValue(false); //给文本框赋值，但不隐藏本控件
    }
    //设置显示天
    function sssSetDay(yy, mm) {

        setRealDayCount(); //设置当月真实的日
        sssWriteHead();
        var strDateFont1 = "",
        strDateFont2 = "" //处理日期显示的风格
        for (var i = 0; i < 37; i++) {
            sssArrDay[i] = ""
        }; //将显示框的内容全部清空
        var day1 = 1;
        var firstday = new Date(yy, mm - 1, 1).getDay(); //某月第一天的星期几
        for (var i = firstday; day1 < getMonthCount(yy, mm) + 1; i++) {
            sssArrDay[i] = day1;
            day1++;
        }
        //如果用于显示日的最后一行的第一个单元格的值为空，则隐藏整行。
        //if(sssArrDay[35] == ""){
        // document.all.trsssDay5.style.display = "none";
        //} else {
        // document.all.trsssDay5.style.display = "";
        //}
        for (var i = 0; i < 37; i++) {
            var da = eval("document.all.tdsssDay" + i) //书写新的一个月的日期星期排列
            if (sssArrDay[i] != "") {
                //判断是否为周末，如果是周末，则改为红色字体
                if (i % 7 == 0 || (i + 1) % 7 == 0) {
                    strDateFont1 = "<font color=#ff0000>"
                    strDateFont2 = "</font>"
                } else {
                    strDateFont1 = "";
                    strDateFont2 = ""
                }
                da.innerHTML = strDateFont1 + sssArrDay[i] + strDateFont2;
                //如果是当前选择的天，则改变颜色
                if (sssArrDay[i] == sssDay) {
                    da.style.backgroundColor = "#08c";
                    da.style.color = "#fff";
                } else {
                    da.style.backgroundColor = "#fff";
                    da.style.color = "#444";
                }
                da.style.cursor = "hand"
            } else {
                da.innerHTML = "";
                da.style.backgroundColor = "";
                da.style.cursor = "default"
            }
        } //end for
        sssSetValue(false); //给文本框赋值，但不隐藏本控件
    } //end function sssSetDay
    //根据option的值选中option
    function checkSelect(objSelect, selectValue) {
        var count = parseInt(objSelect.length);
        if (selectValue < 10 && selectValue.toString().length == 2) {
            selectValue = selectValue.substring(1, 2);
        }
        for (var i = 0; i < count; i++) {
            if (objSelect.options[i].value == selectValue) {
                objSelect.selectedIndex = i;
                break;
            }
        } //for
    }
    //选中年、月、时、分等下拉框
    function selectObject() {
        //如果年份小于允许的最小年份，则创建对应的option
        if (sssYear < sssYearSt) {
            for (var i = sssYear; i < sssYearSt; i++) {
                createOption(document.all.selTianYear, i, i + "年");
            }
            sssYearSt = sssYear;
        }
        //如果年份超过允许的最大年份，则创建对应的option
        if (sssYear > sssYearEnd) {
            for (var i = sssYearEnd + 1; i <= sssYear; i++) {
                createOption(document.all.selTianYear, i, i + "年");
            }
            sssYearEnd = sssYear;
        }
        checkSelect(document.all.selTianYear, sssYear);
        checkSelect(document.all.selTianMonth, sssMonth);
        if (m_bolShowHour) checkSelect(document.all.selTianHour, sssHour);
        if (m_bolShowMinute) checkSelect(document.all.selTianMinute, sssMinute);
    }
    //给接受日期时间的控件赋值
    //参数bolHideControl - 是否隐藏控件
    function sssSetValue(bolHideControl) {
        var value = "";
        if (!sssDay || sssDay == "") {
            sssOutObject.value = value;
            return;
        }
        var mm = sssMonth;
        var day = sssDay;
        if (mm < 10 && mm.toString().length == 1) mm = "0" + mm;
        if (day < 10 && day.toString().length == 1) day = "0" + day;
        value = sssYear + sssDateSplit + mm + sssDateSplit + day;
        if (m_bolShowHour) {
            var hour = sssHour;
            if (hour < 10 && hour.toString().length == 1) hour = "0" + hour;
            value += sssDateTimeSplit + hour;
        }
        if (m_bolShowMinute) {
            var minute = sssMinute;
            if (minute < 10 && minute.toString().length == 1) minute = "0" + minute;
            value += sssTimeSplit + minute;
        }
        sssOutObject.value = value;
        //document.all.boxDate.style.display = "none";
        if (bolHideControl) {
            sssHideControl();
        }
    }
    //是否显示时间
    function showTime() {
        if (!m_bolShowHour && m_bolShowMinute) {
            alert("如果要选择分钟，则必须可以选择小时！");
            return;
        }
        hideElementsById(new Array("sssHourHead", "selTianHour", "sssMinuteHead", "selTianMinute"), true);
        if (m_bolShowHour) {
            //显示小时
            hideElementsById(new Array("sssHourHead"), false);
        }
        if (m_bolShowMinute) {
            //显示分钟
            hideElementsById(new Array("sssMinuteHead"), false);
        }
    }
    //弹出显示日历选择控件，以让用户选择
    function sssPopCalendar() {
        //隐藏下拉框，显示相对应的head
        hideElementsById(new Array("selTianYear", "selTianMonth", "selTianHour", "selTianMinute"), true);
        hideElementsById(new Array("sssYearHead", "sssMonthHead", "sssHourHead", "sssMinuteHead"), false);
        sssSetDay(sssYear, sssMonth);
        sssWriteHead();
        showTime();
        var dads = document.all.boxDate.style;
        var iX, iY;

        var h = document.all.boxDate.offsetHeight;
        var w = document.all.boxDate.offsetWidth;
        //计算left
        if (window.event.x + h > document.body.offsetWidth - 10) iX = window.event.x - h - 5;
        else iX = window.event.x + 5;
        if (iX < 0) iX = 0;
        //计算top
        iY = window.event.y;
        if (window.event.y + w > document.body.offsetHeight - 10) iY = document.body.scrollTop + document.body.offsetHeight - w - 5;
        else iY = document.body.scrollTop + window.event.y + 5;
        if (iY < 0) iY = 0;
        dads.left = iX;
        dads.top = iY;
        sssShowControl();
        selectObject();
    }
    //隐藏日历控件(同时显示被强制隐藏的标签)
    function sssHideControl() {
        document.all.boxDate.style.display = "none";
        sssShowObject();
        arrsssHide = new Array(); //将被隐藏的标签对象清空
    }
    //显示日历控件(同时隐藏会遮挡的标签)
    function sssShowControl() {
        document.all.boxDate.style.display = "";
        sssHideObject("SELECT");
        sssHideObject("OBJECT");
    }
    //根据标签名称隐藏标签。如会遮住控件的select，object
    function sssHideObject(strTagName) {

        x = document.all.boxDate.offsetLeft;
        y = document.all.boxDate.offsetTop;
        h = document.all.boxDate.offsetHeight;
        w = document.all.boxDate.offsetWidth;

        for (var i = 0; i < document.all.tags(strTagName).length; i++) {

            var obj = document.all.tags(strTagName)[i];
            if (!obj || !obj.offsetParent) continue;
            // 获取元素对于BODY标记的相对坐标
            var objLeft = obj.offsetLeft;
            var objTop = obj.offsetTop;
            var objHeight = obj.offsetHeight;
            var objWidth = obj.offsetWidth;
            var objParent = obj.offsetParent;

            while (objParent != null && objParent != undefined && objParent.tagName.toUpperCase() != "BODY") {
                objLeft += objParent.offsetLeft;
                objTop += objParent.offsetTop;
                objParent = objParent.offsetParent;
            }
            //alert("控件左端:" + x + "select左端" + (objLeft + objWidth) + "控件底部:" + (y+h) + "select高:" + objTop);
            var bolHide = true;
            if (obj.style.display == "none" || obj.style.visibility == "hidden" || obj.getAttribute("isControled") == "true") {
                //如果标签本身就是隐藏的，则不需要再隐藏。如果是控件中的下拉框，也不用隐藏。
                bolHide = false;
            }
            if (((objLeft + objWidth) > x && (y + h + 20) > objTop && (objTop + objHeight) > y && objLeft < (x + w)) && bolHide) {
                //arrsssHide.push(obj);//记录被隐藏的标签对象
                arrsssHide[arrsssHide.length] = obj;
                obj.style.visibility = "hidden";
            }

        }
    }
    //显示被隐藏的标签
    function sssShowObject() {
        for (var i = 0; i < arrsssHide.length; i++) {
            //alert(arrsssHide[i]);
            arrsssHide[i].style.visibility = "";
        }
    }
    //初始化日期。
    function sssInitDate(strDate) {
        var arr = strDate.split(sssDateSplit);
        sssYear = arr[0];
        sssMonth = arr[1];
        sssDay = arr[2];
    }
    //清空
    function sssClear() {
        sssOutObject.value = "";
        sssHideControl();
    };

    //任意点击时关闭该控件
    document.onclick = function() {
        with(window.event.srcElement) {
            if (tagName != "INPUT" && getAttribute("IsControl") != "true" && getAttribute("isControled") != "true") sssHideControl();
        }
    }
    //按ESC键关闭该控件
    document.onkeypress = function() {
        if (event.keyCode == 27) {
            sssHideControl();
        }
    }
    $(document).ready(function() {
        $(".dateTime").click(function() {
            x = $(".dateTime").offset();
            $("#boxDate").attr({
                style: "top:" + x.top + "px; left:" + x.left + "px; display:block;"
            });
        });
        $(".dateTime2").click(function() {
            x = $(".dateTime2").offset();
            $("#boxDate").attr({
                style: "top:" + x.top + "px; left:" + x.left + "px; display:block;"
            });
        });
    });