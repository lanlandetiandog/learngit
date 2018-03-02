$(document).ready(function(){
    $(".right-top").click(function(){
        $(".login-box").hide();
        $(".login-two-code").show();
    });
    $(".right-top-view").click(function(){
        $(".login-box").show();
        $(".login-two-code").hide();
    });

    $(".recharge-type").click(function(){
        var curid=$(this).attr("id");
        $(this).addClass("recharge-type-cur").siblings().removeClass("recharge-type-cur");
        if(curid=="mobile_found"){
            $(".mobile_found").show();
            $(".mail_found").hide();
        }
        else{
            $(".mail_found").show();
            $(".mobile_found").hide();
        }
    });

    var mobile_form = $("#mobile_form").validate({
        rules: {
        	mobile_number: {
                required: true,
                pattern: /^1\d{10}$/,
            },
            m_verify_code: "required"
        },
        messages: {
        	mobile_number: {
                required: "请输入手机号码",
                pattern: "请输入正确的手机号码",
            },
            m_verify_code: {
                required: "请输入验证码"
            }
        },
        errorElement: "em",
        errorPlacement: function(error,element) {
            error.appendTo(element.parent());
        }
    });

    var mail_form = $("#mail_form").validate({
        rules: {
        	email: {
        		required: true,
        		email: true,
            }
        },
        messages: {
        	email: {
        		required: "请输入邮箱地址",
        		email: "请输入正确的邮箱地址"
            }
        },
        errorElement: "em",
        errorPlacement: function(error,element) {
            error.appendTo(element.parent());
        }
    });

    $("#m_send_code").click(function() {
    	if(mobile_form.element($("#mobile_number"))) {
    		showMValCode();
    	}
    });

    $("#m_next_btn").click(function() {
    	if(mobile_form.form()) {
        	$.ajax({
        		type: "POST",
        		dataType: "json",
        		url: ctx + '/member/verifyForResetPwd',
        		data: {'destination' : $("#mobile_number").val(),
        			    'verifyCode' : $("#m_verify_code").val()},
        	    success: function(data) {
        	    	 if(data.status) {
        	             location.href = ctx + '/member/resetpwd.html'
                     } else {
                    	 mobile_form.showErrors({
                             "m_verify_code" : data.message
                         });
                     }
        	    }
        	});
    	}
    });

    $("#e_send_code").click(function() {
    	if(mail_form.form()) {
    		showEValCode();
    	}
    });

    $("#to_emailbox").click(function() {
        window.open("https://" + $("#email").val().replace(/^.+@/g, 'mail.'));
    });
    
    
    function showMValCode() {
    	create_bg();
    	$("#dark_bg").css("z-index", "998");
    	$("#mvcodeDiv").show();
    	$("#mvcode_img").attr("src", ctx + "/member/getVCodeMValidateCode?s=" + new Date().getTime());
    }
    
    function closeMValCode() {
        $("#dark_bg").remove();
        $("#mvcodeDiv").hide();
        mcform.resetForm();
    }
    
    var mcform = $("#mcform").validate({
    	rules: {
    		mvcode: {
    			required: true,
    			rangelength: [4,4],
    			remote: {
                    url: ctx + "/member/checkVCodeMValCode",
                    type: "POST",
                    cache: false,
                    data: {
                        code: function() { 
                            return $("#mvcode").val();
                        }
                    }
                }
    		}
    	},
    	messages: {
    		mvcode: {
    			required: '请输入验证码',
    			rangelength: '请输入4位验证码',
    			remote: '验证码错误'
    		}
    	},
    	errorElement: "em",
        errorPlacement: function(error,element) {
        	error.appendTo(element.next().next());
        }
    });

    $("#mvcode").focus(function(e) {
    	mcform.element($(e.target));
    });
    
    $("#mvd_cancel").click(function() {
    	closeMValCode();
    });
    $("#mvd_close").click(function() {
    	closeMValCode();
    });
    $("#mvd_ok").click(function() {
    	if(mcform.element($("#mvcode"))) {
    		var code = $("#mvcode").val();
    		closeMValCode();
            $('#m_send_code').attr("disabled", true);
            $('#m_send_code').removeClass("catch_code");
            $('#m_send_code').addClass("catch_code_gray");
          	$.ajax({
          		type: "POST",
          		dataType: "json",
          		url : ctx + '/member/sendmVCode',
          		data : {'destination' : $("#mobile_number").val(),
            			    'channel' : 0,
            			    'option': 1,
            			    'code': code
            			   },
          	    success: function(data) {
            	    if(data.status) {
                        $('#m_send_code').timer({
                              format: '已发送(%s)',
                              duration: '59s',
                              countdown: true,
                              callback: function() {
                                  $('#m_send_code').timer('remove');
                                  $('#m_send_code').attr("disabled", false);
                                  $('#m_send_code').text("获取验证码");
                                  $('#m_send_code').removeClass("catch_code_gray");
                                  $('#m_send_code').addClass("catch_code");
                              }
                        });
                    } else {
                      	mobile_form.showErrors({
                              "mobile_number" : data.message
                        });
                        $('#m_send_code').attr("disabled", false);
                        $('#m_send_code').removeClass("catch_code_gray");
                        $('#m_send_code').addClass("catch_code");
                    }
            	    $("#mvcode_img").attr("src", ctx + "/member/getVCodeMValidateCode?s=" + new Date().getTime());
          	    }
          	});
    	}
    });
    
    $("#mvcode_img").click(function() {
    	$("#mvcode_img").attr("src", ctx + "/member/getVCodeMValidateCode?s=" + new Date().getTime());
    });
    
    function showEValCode() {
    	create_bg();
    	$("#dark_bg").css("z-index", "998");
    	$("#evcodeDiv").show();
    	$("#evcode_img").attr("src", ctx + "/member/getVCodeMValidateCode?s=" + new Date().getTime());
    }
    
    function closeEValCode() {
        $("#dark_bg").remove();
        $("#evcodeDiv").hide();
        ecform.resetForm();
    }
    
    var ecform = $("#ecform").validate({
    	rules: {
    		evcode: {
    			required: true,
    			rangelength: [4,4],
    			remote: {
                    url: ctx + "/member/checkVCodeMValCode",
                    type: "POST",
                    cache: false,
                    data: {
                        code: function() { 
                            return $("#evcode").val();
                        }
                    }
                }
    		}
    	},
    	messages: {
    		evcode: {
    			required: '请输入验证码',
    			rangelength: '请输入4位验证码',
    			remote: '验证码错误'
    		}
    	},
    	errorElement: "em",
        errorPlacement: function(error,element) {
        	error.appendTo(element.next().next());
        }
    });

    $("#evcode").focus(function(e) {
    	ecform.element($(e.target));
    });
    
    $("#evd_cancel").click(function() {
    	closeEValCode();
    });
    $("#evd_close").click(function() {
    	closeEValCode();
    });
    $("#evd_ok").click(function() {
    	if(ecform.element($("#evcode"))) {
    		var code = $("#evcode").val();
    		closeEValCode();
    		$.ajax({
                type: "POST",
                dataType: "json",
                url : ctx + '/member/sendmVCode',
                data : {'destination' : $("#email").val(),
                	    'channel' : 2,
                	    'option': 1,
                	    'code': code
                	    },
           	    success: function(data) {
                    if(data.status) {
                        $("#e_send_code").hide();
                        $("#to_mailbox_li").show();
                    } else {
                    	mail_form.showErrors({
                            "email" : data.message
                        });
                    }
                    $("#evcode_img").attr("src", ctx + "/member/getVCodeMValidateCode?s=" + new Date().getTime());
                }
            });
    	}
    });
    
    $("#evcode_img").click(function() {
    	$("#evcode_img").attr("src", ctx + "/member/getVCodeMValidateCode?s=" + new Date().getTime());
    });
    
    $("input").keypress(function(e) {
		if(e.which == 13) { 
			return false;
		}
    });
    
});
