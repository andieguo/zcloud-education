// JavaScript Document

/* 开关按钮 */
(function($) {
    $.fn.extend({
        EMSwitchBox: function(options) {
            var defaults = {
                onLabel: 'On',
                offLabel: 'Off'
            };
            var options = $.extend({}, defaults, options)
            return this.each(function() {
				var styleVal = 'left:111px;';
				if($(this).is(':checked')){ styleVal='left:0px;'; }
                var $markup = $('<div class="switch"><span class="green">' + options.onLabel + '</span><span class="red">' + options.offLabel + '</span><div class="thumb" style="'+styleVal+'"></div></div>');
                $markup.insertAfter($(this));
                $(this).hide();

                $('div.switch').click(function() {
					var v1= '110px';
					var v2= '0';
					var v3= true;
					if( $(this).prev('input').is(':checked')){
						v1 = '0px';
						v2='111';
						v3=false;
					}
                    if ((navigator.userAgent.match(/iPhone/i)) || (navigator.userAgent.match(/iPod/i)) || (navigator.userAgent.match(/iPad/i)) || (navigator.userAgent.match(/Android/i))) {
                        $(this).children('div.thumb').css({
                            '-webkit-transition-duration': '300ms',
                            '-webkit-transform': 'translate3d('+v1+',0,0)'
                        });
                    }else {
                        $(this).children('div.thumb').animate({left: v2}, 300);
                    }
                    $(this).prev('input').attr('checked', v3);
                });
            });
        }
    });
})(jQuery);