$(document).ready(function(){
	var subFlag = true;
	
    $(".right-top").click(function(){
        $(".login-box").hide();
        $(".login-two-code").show();
    });
    $(".right-top-view").click(function(){
        $(".login-box").show();
        $(".login-two-code").hide();
    });
    
    $("#val_code").focus(function(e) {
    	login_from.element($(e.target));
    });
    
    var login_from = $("#form_login").validate({
    	submitHandler: function(form) {
    		if(subFlag) {
    			subFlag = false;
    			form.submit();
    		}
    	},
    	rules: {
    		username: "required",
    		password: "required",
    		val_code: {
    			required: true,
    			remote: {
                    url: ctx + "/checkValCode",
                    type: "POST",
                    cache: false,
                    data: {
                        code: function() { 
                            return $("#val_code").val();
                        }
                    }
                }
    		}
    	},
    	messages: {
    		username: {
    			required: '请输入用户名'
    		},
    		password: {
    			required: '请输入密码'
    		},
    		val_code: {
    			required: '请输入验证码',
    			remote: '验证码错误'
    		}
    	},
    	errorElement: "em",
    	errorPlacement: function(error,element) {
            error.appendTo(element.parent().parent());
        }
    });
    
    $("input").keypress(function(e) {
		if(e.which == 13) {
		    e.preventDefault();
		    var tindex= $(e.target).attr("tabindex");
		    if(tindex > 2) {
		    	if(login_from.form()) {
		    		if(subFlag) {
		    			subFlag = false;
		    			$("#form_login")[0].submit();
		    		}
		    	}
		    } else {
		    	if(login_from.element($(e.target))) {
		    		$("[tabindex = " + (tindex + 1) + "]").focus();
		    	}
		    }
		}
    });
    
    $("#login_code_img").click(function() {
    	$("#login_code_img").attr("src", ctx + "/getValidateCode?s=" + new Date().getTime());
    });
    
});