// JavaScript Document

/* չ������߼�ѡ�� */
    $(document).ready(function() {
        $("#status").click(showFile);
    })
    function showFile() {
        if ($("#super").css("display") == "none") {
            $("#super").css("display", "inline");
            $("#status").text("��ѯ���� ��");
        } else {
            $("#super").css("display", "none");
            $("#status").text("��ѯ���� ��");
        }
    }