// JavaScript Document

/* 登录信息 */
(function(jQuery) {

    //this.version = '@1.3';
    this.title = '';//'登录信息提示';
    this.time = 4000;
    this.anims = {
        'type': 'slide',
		'speed':600
    };
    this.timer1 = null;
    this.inits = function(title, text) {
        if ($("#messageLogin").is("div")) {
            return;
        }
        $(document.body).prepend('<div id="messageLogin" class="input">\
		<div id="messageLogin_content">' + text + '</div>\
		<div class="title clearfix"><span id="messageLogin_close">知道了</span><h1>' + title + '</h1></div>\
		</div>');
        $("#messageLogin_close").click(function() {
            setTimeout('this.close()', 1);
        });
        $("#messageLogin").hover(function() {
            clearTimeout(timer1);
            timer1 = null;
        },
        function() {
			close();
            //timer1 = setTimeout('this.close()', 1);
        });
    };

    this.show = function(title, text, time) {
        if (!$("#messageLogin").is("div")) {
            if (title == 0 || !title) title = this.title;
	        this.inits(title, text);
        }
        if (time >= 0) this.time = time;
        switch (this.anims.type) {
        case 'slide':
            $("#messageLogin").slideDown(this.anims.speed);
            break;
        case 'fade':
            $("#messageLogin").fadeIn(this.anims.speed);
            break;
        case 'show':
            $("#messageLogin").show(this.anims.speed);
            break;
        default:
            $("#messageLogin").slideDown(this.anims.speed);
            break;
        }
        if ($.browser.is == 'chrome') {
            setTimeout(function() {
                $("#messageLogin").remove();
                this.inits(title, text);
                $("#messageLogin").css("display", "block");
            },
            this.anims.speed - (this.anims.speed / 5));
        }
        this.rmmessage(this.time);
    };

    this.anim = function(type, speed) {
        if ($("#messageLogin").is("div")) {
            return;
        }
        if (type != 0 && type) this.anims.type = type;
        if (speed != 0 && speed) {
            switch (speed) {
            case 'slow':
                break;
            case 'fast':
                this.anims.speed = 200;
                break;
            case 'normal':
                this.anims.speed = 400;
                break;
            default:
                this.anims.speed = speed;
            }
        }
    }

    this.rmmessage = function(time) {
        if (time > 0) {
            timer1 = setTimeout('this.close()', time);
        }
    };
    this.close = function() {
        switch (this.anims.type) {
        case 'slide':
            $("#messageLogin").slideUp(this.anims.speed);
            break;
        case 'fade':
            $("#messageLogin").fadeOut(this.anims.speed);
            break;
        case 'show':
            $("#messageLogin").hide(this.anims.speed);
            break;
        default:
            $("#messageLogin").slideUp(this.anims.speed);
            break;
        };
        setTimeout('$("#messageLogin").remove();', this.anims.speed);
        //this.original();
    }

    /*this.original = function() {
        this.title = '登录信息提示';
        this.time = 4000;
        this.anims = {
            'type': 'slide',
            'speed': 600
        };
    };*/
    jQuery.messager = this;
    return jQuery;
})(jQuery);