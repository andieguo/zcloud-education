    // JavaScript Document
    /*
	* ����ѡ��
	* ʹ�÷�����
	* (1)ֻѡ������   <input type="text" name="date"   readOnly onClick="setDay(this);">
	* (2)ѡ�����ں�Сʱ <input type="text" name="dateh" readOnly onClick="setDayH(this);">
	* (3)ѡ�����ں�Сʱ������ <input type="text" name="datehm" readOnly onClick="setDayHM(this);">
	* ���ò����ķ���
	* (1)�������ڷָ���    setDateSplit(strSplit);Ĭ��Ϊ"-"
	* (2)����������ʱ��֮��ķָ��� setDateTimeSplit(strSplit);Ĭ��Ϊ" "
	* (3)����ʱ��ָ���    setTimeSplit(strSplit);Ĭ��Ϊ":"
	* (4)����(1),(2),(3)�еķָ��� setSplit(strDateSplit,strDateTimeSplit,strTimeSplit);
	* (5)���ÿ�ʼ�ͽ������    setYearPeriod(intDateBeg,intDateEnd)
	* ˵����
	* Ĭ�Ϸ��ص�����ʱ���ʽ���ƣ�2005-02-02 08:08
	*/

    //------------------ ��ʽ���� ---------------------------//

    //���ܰ�ťͬ����ʽ
    var time_turn_base = "height:16px;font-size:9pt;color:white;border:0 solid #CCCCCC;cursor:hand;background-color:#2650A6;";
    //���ꡢ�µȵİ�ť
    var time_turn = "width:28px;" + time_turn_base;
    //�رա���յȰ�ť��ʽ
    var time_turn2 = "width:22px;" + time_turn_base;
    //��ѡ��������
    var time_select = "width:64px;display:none;";
    //�¡�ʱ����ѡ��������
    var time_select2 = "width:46px;display:none;";
    //����ѡ��ؼ������ʽ
    var time_body = "display:none;";
    var prevYear = "prevYear"
    var prevMonth = "prevMonth"
    var nextMonth = "nextMonth"
    var nextYear = "nextYear"
    //��ʾ�ܵĵ���ʽ
    var time_week = "week clearfix";
    //��ʾ�յĵ���ʽ
    var time_day = "day clearfix";
    //������ʽ
    var time_font = "color:#FFCC00;font-size:9pt;cursor:hand;";
    //���ӵ���ʽ
    var time_link = "text-decoration:none;font-size:9pt;color:#2650A6;";
    //����
    var time_line = "border-bottom:1 solid #6699CC";
    //------------------ �������� ---------------------------//
    var sssYearSt = 1950; //��ѡ��Ŀ�ʼ���
    var sssYearEnd = 2050; //��ѡ��Ľ������
    var sssDateNow = new Date();
    var sssYear = sssDateNow.getFullYear(); //������ı����ĳ�ʼֵ
    var sssMonth = sssDateNow.getMonth() + 1; //�����µı����ĳ�ʼֵ
    var sssDay = sssDateNow.getDate();
    var sssHour = 8; //sssDateNow.getHours();
    var sssMinute = 0; //sssDateNow.getMinutes();
    var sssArrDay = new Array(42); //����д���ڵ�����
    var sssDateSplit = "-"; //���ڵķָ�����
    var sssDateTimeSplit = " "; //������ʱ��֮��ķָ���
    var sssTimeSplit = ":"; //ʱ��ķָ�����
    var sssOutObject; //��������ʱ��Ķ���
    var arrsssHide = new Array(); //��ǿ�����صı�ǩ
    var m_bolShowHour = false; //�Ƿ���ʾСʱ
    var m_bolShowMinute = false; //�Ƿ���ʾ����
    var m_aMonHead = new Array(12); //����������ÿ���µ��������
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
    // ---------------------- �û��ɵ��õĺ��� -----------------------------//
    //�û�����������ֻѡ������
    function setDay(obj) {
        sssOutObject = obj;
        //�����ǩ����ֵ�������ڳ�ʼ��Ϊ��ǰֵ
        var strValue = sssTrim(sssOutObject.value);
        if (strValue != "") {
            sssInitDate(strValue);
        }
        sssPopCalendar();
    }
    //�û�����������ѡ�����ں�Сʱ
    function setDayH(obj) {
        sssOutObject = obj;
        m_bolShowHour = true;
        //�����ǩ����ֵ�������ں�Сʱ��ʼ��Ϊ��ǰֵ
        var strValue = sssTrim(sssOutObject.value);
        if (strValue != "") {
            sssInitDate(strValue.substring(0, 10));
            var hour = strValue.substring(11, 13);
            if (hour < 10) sssHour = hour.substring(1, 2);
        }
        sssPopCalendar();
    }
    //�û�����������ѡ�����ں�Сʱ������
    function setDayHM(obj) {
        sssOutObject = obj;
        m_bolShowHour = true;
        m_bolShowMinute = true;
        //�����ǩ����ֵ�������ں�Сʱ�����ӳ�ʼ��Ϊ��ǰֵ
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
    //���ÿ�ʼ���ںͽ�������
    function setYearPeriod(intDateBeg, intDateEnd) {
        sssYearSt = intDateBeg;
        sssYearEnd = intDateEnd;
    }
    //�������ڷָ�����Ĭ��Ϊ"-"
    function setDateSplit(strDateSplit) {
        sssDateSplit = strDateSplit;
    }
    //����������ʱ��֮��ķָ�����Ĭ��Ϊ" "
    function setDateTimeSplit(strDateTimeSplit) {
        sssDateTimeSplit = strDateTimeSplit;
    }
    //����ʱ��ָ�����Ĭ��Ϊ":"
    function setTimeSplit(strTimeSplit) {
        sssTimeSplit = strTimeSplit;
    }
    //���÷ָ���
    function setSplit(strDateSplit, strDateTimeSplit, strTimeSplit) {
        sssDateSplit(strDateSplit);
        sssDateTimeSplit(strDateTimeSplit);
        sssTimeSplit(strTimeSplit);
    }
    //����Ĭ�ϵ����ڡ���ʽΪ��YYYY-MM-DD
    function setDefaultDate(strDate) {
        sssYear = strDate.substring(0, 4);
        sssMonth = strDate.substring(5, 7);
        sssDay = strDate.substring(8, 10);
    }
    //����Ĭ�ϵ�ʱ�䡣��ʽΪ��HH24:MI
    function setDefaultTime(strTime) {
        sssHour = strTime.substring(0, 2);
        sssMinute = strTime.substring(3, 5);
    }
    // ---------------------- end �û��ɵ��õĺ��� -----------------------------//
    //------------------ begin ҳ����ʾ���� ---------------------------//
    var weekName = new Array("��", "һ", "��", "��", "��", "��", "��");
    document.write('<div id="boxDate" style="' + time_body + '">');
    document.write('<div id="boxDateText" class="clearfix" isControled="true">');

    document.write('<input type="button" class="' + nextYear + '" value="" title="��һ��" onClick="sssNextYear();">');
    document.write('<input type="button" class="' + nextMonth + '" value="" title="��һ��" onClick="sssNextMonth();">');

    document.write('<input type="button" class="' + prevYear + '" value="" title="��һ��" onClick="sssPrevYear();">');
    document.write('<input type="button" class="' + prevMonth + '" value="" title="��һ��" onClick="sssPrevMonth();">');

    document.write('<span class="datebox">');
    document.write('<span id="sssYearHead" isControled="true" class="' + time_font + '" ' + ' onclick="spanYearCEvent();">��</span>');
    document.write('<select id="selTianYear" class="' + time_select + '" isControled="true" ' + ' onChange="sssYear=this.value;sssSetDay(sssYear,sssMonth);document.all.sssYearHead.style.display=\'\';' + 'this.style.display=\'none\';">');
    for (var i = sssYearSt; i <= sssYearEnd; i++) {
        document.writeln('<option value="' + i + '">' + i + '</option>');
    }
    document.write('</select>');
    document.write('<span id="sssMonthHead" isControled="true" class="' + time_font + '" ' + 'onclick="spanMonthCEvent();">��</span>');
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
        //�������
        document.write('<li isControled="true">' + weekName[i] + '</li>');
    }
    document.write(' </ul>');
    document.write('</div>');
    //������ѡ��
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
    //    document.write('<li align="right" colspan="5"><a href="javascript:sssClear();" class="' + time_link + '">���</a>' + '&nbsp;<a href="javascript:sssHideControl();" class="' + time_link + '">�ر�</a>' + '&nbsp;<a href="javascript:sssSetValue(true);" class="' + time_link + '">ȷ��</a>&nbsp;' + '</li>');
    document.write(' </ul>');
    document.write('</div>');

    document.write('<div class="timeFoot">');
    document.write('<span class="li"><a href="javascript:sssToday();" class="' + time_link + '">����</a>' + '</span>');
    document.write('<span class="li"><a href="javascript:sssClear();" class="' + time_link + '">���</a>' + '</span>');
    document.write('<span class="libox">');
    document.write('<span id="sssHourHead" isControled="true" class="' + time_font + 'display:none;" ' + 'onclick="spanHourCEvent();">ʱ</span>');
    document.write('<select id="selTianHour" class="' + time_select2 + 'display:none;" isControled="true" ' + ' onChange="sssHour=this.value;sssWriteHead();document.all.sssHourHead.style.display=\'\';' + 'this.style.display=\'none\';">');
    for (var i = 0; i <= 23; i++) {
        document.writeln('<option value="' + i + '">' + i + '</option>');
    }
    document.write('</select>');
    document.write('<span id="sssMinuteHead" isControled="true" class="' + time_font + 'display:none;" ' + 'onclick="spanMinuteCEvent();">��</span>');
    document.write('<select id="selTianMinute" class="' + time_select2 + 'display:none;" isControled="true" ' + ' onChange="sssMinute=this.value;sssWriteHead();document.all.sssMinuteHead.style.display=\'\';' + 'this.style.display=\'none\';">');
    for (var i = 0; i <= 59; i++) {
        document.writeln('<option value="' + i + '">' + i + '</option>');
    }
    document.write('</select>');
    document.write('</span>');
    document.write('</div>');

    document.write('</div>');
    //------------------ end ҳ����ʾ���� ---------------------------//
    //------------------ ��ʾ����ʱ���span��ǩ��Ӧ�¼� ---------------------------//
    //�������span��ǩ��Ӧ
    function spanYearCEvent() {
        hideElementsById(new Array("selTianYear", "sssMonthHead"), false);
        if (m_bolShowHour) hideElementsById(new Array("sssHourHead"), false);
        if (m_bolShowMinute) hideElementsById(new Array("sssMinuteHead"), false);
        hideElementsById(new Array("sssYearHead", "selTianMonth", "selTianHour", "selTianMinute"), true);
    }
    //�����·�span��ǩ��Ӧ
    function spanMonthCEvent() {
        hideElementsById(new Array("selTianMonth", "sssYearHead"), false);
        if (m_bolShowHour) hideElementsById(new Array("sssHourHead"), false);
        if (m_bolShowMinute) hideElementsById(new Array("sssMinuteHead"), false);
        hideElementsById(new Array("sssMonthHead", "selTianYear", "selTianHour", "selTianMinute"), true);
    }
    //����Сʱspan��ǩ��Ӧ
    function spanHourCEvent() {
        hideElementsById(new Array("sssYearHead", "sssMonthHead"), false);
        if (m_bolShowHour) hideElementsById(new Array("selTianHour"), false);
        if (m_bolShowMinute) hideElementsById(new Array("sssMinuteHead"), false);
        hideElementsById(new Array("sssHourHead", "selTianYear", "selTianMonth", "selTianMinute"), true);
    }
    //��������span��ǩ��Ӧ
    function spanMinuteCEvent() {
        hideElementsById(new Array("sssYearHead", "sssMonthHead"), false);
        if (m_bolShowHour) hideElementsById(new Array("sssHourHead"), false);
        if (m_bolShowMinute) hideElementsById(new Array("selTianMinute"), false);
        hideElementsById(new Array("sssMinuteHead", "selTianYear", "selTianMonth", "selTianHour"), true);
    }
    //���ݱ�ǩid���ػ���ʾ��ǩ
    function hideElementsById(arrId, bolHide) {
        var strDisplay = "";
        if (bolHide) strDisplay = "none";
        for (var i = 0; i < arrId.length; i++) {
            var obj = document.getElementById(arrId[i]);
            obj.style.display = strDisplay;
        }
    }
    //------------------ end ��ʾ����ʱ���span��ǩ��Ӧ�¼� ---------------------------//
    //�ж�ĳ���Ƿ�Ϊ����
    function isPinYear(year) {
        var bolRet = false;
        if (0 == year % 4 && ((year % 100 != 0) || (year % 400 == 0))) {
            bolRet = true;
        }
        return bolRet;
    }
    //�õ�һ���µ�����������Ϊ29��
    function getMonthCount(year, month) {
        var c = m_aMonHead[month - 1];
        if ((month == 2) && isPinYear(year)) c++;
        return c;
    }
    //�������õ�ǰ���ա���Ҫ�Ƿ�ֹ�ڷ��ꡢ����ʱ����ǰ�մ��ڵ��µ������
    function setRealDayCount() {
        if (sssDay > getMonthCount(sssYear, sssMonth)) {
            //�����ǰ���մ��ڵ��µ�����գ���ȡ���������
            sssDay = getMonthCount(sssYear, sssMonth);
        }
    }
    //�ڸ�λ��ǰ����
    function addZero(value) {
        if (value < 10) {
            value = "0" + value;
        }
        return value;
    }
    //ȡ���ո�
    function sssTrim(str) {
        return str.replace(/(^\s*)|(\s*$)/g, "");
    }
    //Ϊselect����һ��option
    function createOption(objSelect, value, text) {
        var option = document.createElement("OPTION");
        option.value = value;
        option.text = text;
        objSelect.options.add(option);
    }
    //��ǰ�� Year
    function sssPrevYear() {
        if (sssYear > 999 && sssYear < 10000) {
            sssYear--;
        }
        else {
            alert("��ݳ�����Χ��1000-9999����");
        }
        sssSetDay(sssYear, sssMonth);
        //������С���������С��ݣ��򴴽���Ӧ��option
        if (sssYear < sssYearSt) {
            sssYearSt = sssYear;
            createOption(document.all.selTianYear, sssYear, sssYear + "��");
        }
        checkSelect(document.all.selTianYear, sssYear);
        sssWriteHead();
    }

    //���� Year
    function sssNextYear() {
        if (sssYear > 999 && sssYear < 10000) {
            sssYear++;
        }
        else {
            alert("��ݳ�����Χ��1000-9999����");
            return;
        }
        sssSetDay(sssYear, sssMonth);
        //�����ݳ�������������ݣ��򴴽���Ӧ��option
        if (sssYear > sssYearEnd) {
            sssYearEnd = sssYear;
            createOption(document.all.selTianYear, sssYear, sssYear + "��");
        }
        checkSelect(document.all.selTianYear, sssYear);
        sssWriteHead();
    }

    //ѡ�����
    function sssToday() {
        sssYear = sssDateNow.getFullYear();
        sssMonth = sssDateNow.getMonth() + 1;
        sssDay = sssDateNow.getDate();
        sssSetValue(true);
        //sssSetDay(sssYear,sssMonth);
        //selectObject();
    }
    //��ǰ���·�
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
    //�����·�
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
    //��span��ǩ��д���ꡢ�¡�ʱ���ֵ�����
    function sssWriteHead() {
        document.all.sssYearHead.innerText = sssYear + "��";
        document.all.sssMonthHead.innerText = sssMonth + "��";
        if (m_bolShowHour) document.all.sssHourHead.innerText = " " + sssHour + "ʱ";
        if (m_bolShowMinute) document.all.sssMinuteHead.innerText = sssMinute + "��";
        sssSetValue(false); //���ı���ֵ���������ر��ؼ�
    }
    //������ʾ��
    function sssSetDay(yy, mm) {

        setRealDayCount(); //���õ�����ʵ����
        sssWriteHead();
        var strDateFont1 = "",
        strDateFont2 = "" //����������ʾ�ķ��
        for (var i = 0; i < 37; i++) {
            sssArrDay[i] = ""
        }; //����ʾ�������ȫ�����
        var day1 = 1;
        var firstday = new Date(yy, mm - 1, 1).getDay(); //ĳ�µ�һ������ڼ�
        for (var i = firstday; day1 < getMonthCount(yy, mm) + 1; i++) {
            sssArrDay[i] = day1;
            day1++;
        }
        //���������ʾ�յ����һ�еĵ�һ����Ԫ���ֵΪ�գ����������С�
        //if(sssArrDay[35] == ""){
        // document.all.trsssDay5.style.display = "none";
        //} else {
        // document.all.trsssDay5.style.display = "";
        //}
        for (var i = 0; i < 37; i++) {
            var da = eval("document.all.tdsssDay" + i) //��д�µ�һ���µ�������������
            if (sssArrDay[i] != "") {
                //�ж��Ƿ�Ϊ��ĩ���������ĩ�����Ϊ��ɫ����
                if (i % 7 == 0 || (i + 1) % 7 == 0) {
                    strDateFont1 = "<font color=#ff0000>"
                    strDateFont2 = "</font>"
                } else {
                    strDateFont1 = "";
                    strDateFont2 = ""
                }
                da.innerHTML = strDateFont1 + sssArrDay[i] + strDateFont2;
                //����ǵ�ǰѡ����죬��ı���ɫ
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
        sssSetValue(false); //���ı���ֵ���������ر��ؼ�
    } //end function sssSetDay
    //����option��ֵѡ��option
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
    //ѡ���ꡢ�¡�ʱ���ֵ�������
    function selectObject() {
        //������С���������С��ݣ��򴴽���Ӧ��option
        if (sssYear < sssYearSt) {
            for (var i = sssYear; i < sssYearSt; i++) {
                createOption(document.all.selTianYear, i, i + "��");
            }
            sssYearSt = sssYear;
        }
        //�����ݳ�������������ݣ��򴴽���Ӧ��option
        if (sssYear > sssYearEnd) {
            for (var i = sssYearEnd + 1; i <= sssYear; i++) {
                createOption(document.all.selTianYear, i, i + "��");
            }
            sssYearEnd = sssYear;
        }
        checkSelect(document.all.selTianYear, sssYear);
        checkSelect(document.all.selTianMonth, sssMonth);
        if (m_bolShowHour) checkSelect(document.all.selTianHour, sssHour);
        if (m_bolShowMinute) checkSelect(document.all.selTianMinute, sssMinute);
    }
    //����������ʱ��Ŀؼ���ֵ
    //����bolHideControl - �Ƿ����ؿؼ�
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
    //�Ƿ���ʾʱ��
    function showTime() {
        if (!m_bolShowHour && m_bolShowMinute) {
            alert("���Ҫѡ����ӣ���������ѡ��Сʱ��");
            return;
        }
        hideElementsById(new Array("sssHourHead", "selTianHour", "sssMinuteHead", "selTianMinute"), true);
        if (m_bolShowHour) {
            //��ʾСʱ
            hideElementsById(new Array("sssHourHead"), false);
        }
        if (m_bolShowMinute) {
            //��ʾ����
            hideElementsById(new Array("sssMinuteHead"), false);
        }
    }
    //������ʾ����ѡ��ؼ��������û�ѡ��
    function sssPopCalendar() {
        //������������ʾ���Ӧ��head
        hideElementsById(new Array("selTianYear", "selTianMonth", "selTianHour", "selTianMinute"), true);
        hideElementsById(new Array("sssYearHead", "sssMonthHead", "sssHourHead", "sssMinuteHead"), false);
        sssSetDay(sssYear, sssMonth);
        sssWriteHead();
        showTime();
        var dads = document.all.boxDate.style;
        var iX, iY;

        var h = document.all.boxDate.offsetHeight;
        var w = document.all.boxDate.offsetWidth;
        //����left
        if (window.event.x + h > document.body.offsetWidth - 10) iX = window.event.x - h - 5;
        else iX = window.event.x + 5;
        if (iX < 0) iX = 0;
        //����top
        iY = window.event.y;
        if (window.event.y + w > document.body.offsetHeight - 10) iY = document.body.scrollTop + document.body.offsetHeight - w - 5;
        else iY = document.body.scrollTop + window.event.y + 5;
        if (iY < 0) iY = 0;
        dads.left = iX;
        dads.top = iY;
        sssShowControl();
        selectObject();
    }
    //���������ؼ�(ͬʱ��ʾ��ǿ�����صı�ǩ)
    function sssHideControl() {
        document.all.boxDate.style.display = "none";
        sssShowObject();
        arrsssHide = new Array(); //�������صı�ǩ�������
    }
    //��ʾ�����ؼ�(ͬʱ���ػ��ڵ��ı�ǩ)
    function sssShowControl() {
        document.all.boxDate.style.display = "";
        sssHideObject("SELECT");
        sssHideObject("OBJECT");
    }
    //���ݱ�ǩ�������ر�ǩ�������ס�ؼ���select��object
    function sssHideObject(strTagName) {

        x = document.all.boxDate.offsetLeft;
        y = document.all.boxDate.offsetTop;
        h = document.all.boxDate.offsetHeight;
        w = document.all.boxDate.offsetWidth;

        for (var i = 0; i < document.all.tags(strTagName).length; i++) {

            var obj = document.all.tags(strTagName)[i];
            if (!obj || !obj.offsetParent) continue;
            // ��ȡԪ�ض���BODY��ǵ��������
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
            //alert("�ؼ����:" + x + "select���" + (objLeft + objWidth) + "�ؼ��ײ�:" + (y+h) + "select��:" + objTop);
            var bolHide = true;
            if (obj.style.display == "none" || obj.style.visibility == "hidden" || obj.getAttribute("isControled") == "true") {
                //�����ǩ����������صģ�����Ҫ�����ء�����ǿؼ��е�������Ҳ�������ء�
                bolHide = false;
            }
            if (((objLeft + objWidth) > x && (y + h + 20) > objTop && (objTop + objHeight) > y && objLeft < (x + w)) && bolHide) {
                //arrsssHide.push(obj);//��¼�����صı�ǩ����
                arrsssHide[arrsssHide.length] = obj;
                obj.style.visibility = "hidden";
            }

        }
    }
    //��ʾ�����صı�ǩ
    function sssShowObject() {
        for (var i = 0; i < arrsssHide.length; i++) {
            //alert(arrsssHide[i]);
            arrsssHide[i].style.visibility = "";
        }
    }
    //��ʼ�����ڡ�
    function sssInitDate(strDate) {
        var arr = strDate.split(sssDateSplit);
        sssYear = arr[0];
        sssMonth = arr[1];
        sssDay = arr[2];
    }
    //���
    function sssClear() {
        sssOutObject.value = "";
        sssHideControl();
    };

    //������ʱ�رոÿؼ�
    document.onclick = function() {
        with(window.event.srcElement) {
            if (tagName != "INPUT" && getAttribute("IsControl") != "true" && getAttribute("isControled") != "true") sssHideControl();
        }
    }
    //��ESC���رոÿؼ�
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