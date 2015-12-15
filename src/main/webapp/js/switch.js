// JavaScript

// 开关按钮
    $(document).ready(function() {

        $('.iphone-style').live('click',
        function() {

            checkboxID = '#' + $(this).attr('rel');

            if ($(checkboxID)[0].checked == false) {

                $(this).animate({
                    backgroundPosition: '0% 100%'
                });

                $(checkboxID)[0].checked = true;
                $(this).removeClass('off').addClass('on');

            } else {

                $(this).animate({
                    backgroundPosition: '100% 0%'
                });

                $(checkboxID)[0].checked = false;
                $(this).removeClass('on').addClass('off');

            }
        });
        $('.iphone-style-checkbox').each(function() {

            thisID = $(this).attr('id');
            thisClass = $(this).attr('class');

            switch (thisClass) {
            case "iphone-style-checkbox":
                setClass = "iphone-style";
                break;
            }

            $(this).addClass('none');

            if ($(this)[0].checked == true) $(this).after('<div class="' + setClass + ' on" rel="' + thisID + '">&nbsp;</div>');
            else $(this).after('<div class="' + setClass + ' off" rel="' + thisID + '">&nbsp;</div>');
        });
    });
