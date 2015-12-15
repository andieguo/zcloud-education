// JavaScript Document

// 注册用户输入框点击消失提示文本效果
    $(function() {
        //show('请填写登录账号', 'show');
        show2('请填写登录密码', 'passText','pass');
		show2('请填写登录密码', 'sec_passText','sec_pass');
		$('.inputText').focus(function(){
			var title = $(this).attr('tip');
			var val = $(this).val();
			if(val==title){
				$(this).val('').addClass('textInput');
			}
		}).blur(function(){
			var title = $(this).attr('tip');
			var val = $(this).val();
			if(val==''){
				$(this).val(title).removeClass('textInput');
			}
		});
        function show2(title, id1,id2) {
            $('#' + id1).focus(function() {
				$('#' + id1).hide();
                $('#'+id2).show().addClass("textInput");
                $('#'+id2).focus();
                
            });
            $('#'+id2).blur(function() {
                if ($('#'+id2).val() == '') {
                    $('#'+id2).hide();
                    $('#' + id1).show().removeClass('textInput');
                }
            });
        }
    });