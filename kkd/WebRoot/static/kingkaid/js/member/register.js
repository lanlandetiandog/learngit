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
    
    $("#filter_membername").focus(function(e) {
    	register_form.element($(e.target));
    });
    
    $("#filter_mobilenumber").focus(function(e) {
    	register_form.element($(e.target));
    });

    // Form表单验证
    var register_form = $("#form_register").validate({
    	submitHandler: function(form) {
    		if(subFlag) {
    			subFlag = false;
    			form.submit();
    		}
    	},
        rules: {
        	filter_mobilenumber: {
        		required: true,
        		pattern: /^1\d{10}$/,
        		remote: {
        			url: ctx + "/member/checkMobile",
        			type: "POST",
        			data: {
        				memberMobile: function() {
        					return $("#filter_mobilenumber").val();
        				}
        			}
        		}
        	},
        	filter_loginpassword: {
        		required: true,
        		pattern: /^(?![a-zA-Z]+$)(?!\d+$)[A-Za-z0-9~!@#$%^&*()_+`\-={}:";'<>?,.\/]{8,20}$/
            },
            dpassword: {
            	required: true,
            	equalTo: "#filter_loginpassword"
            },
            filter_verifycode: "required",
            filter_invitecode: {
            	required: "#if_invitecode_chkbox:checked",
            	remote: {
        			url: ctx + "/member/checkICode",
        			type: "POST",
        			data: {
        				inviteCode: function() {
        					return $("#filter_invitecode").val();
        				}
        			}
        		}
            },
            if_read_chkbox: "required"
        },
        messages: {
        	filter_mobilenumber: {
        		required: "请输入手机号码",
        		pattern: "请输入正确的手机号码",
        		remote:'此手机号已经被使用'
        	},
        	filter_loginpassword: {
        		required: "请输入密码",
        		pattern: '密码必须是8-20位长的字母、数字和符号的组合',
        	},
            filter_verifycode: {
            	required: "请输入短信验证码"
            },
            filter_invitecode: {
            	required: "请输入邀请码",
            	remote: "邀请码不存在"
            },
            if_read_chkbox: "请阅读金开贷会员服务协议并同意"
        },
        errorElement: "em",
        errorPlacement: function(error,element) {
        	error.appendTo(element.parent());
        }
    });

    // 邀请码checkbox事件处理
    $("#if_invitecode_chkbox").click(function() {
    	$("#filter_invitecode").attr("disabled", !this.checked);
    	if(!this.checked) {
    		$("#filter_invitecode").val("");
    		$("#filter_invitecode-error").remove();
    	}
    });

    // 发送验证码
    $("#fetch-vcode").click(function() {
    	if (register_form.element($("#filter_mobilenumber")) 
    			&& register_form.element($("#filter_membername"))
    			&& register_form.element($("#filter_loginpassword"))
    			&& register_form.element($("#dpassword"))) {
    		showValCode();
    	}
    });

    $("input").keypress(function(e) {
		if(e.which == 13) {
		    e.preventDefault();
		    var tindex= $(e.target).attr("tabindex");
		    if(tindex > 7) {
		    	if(register_form.form()) {
		    		if(subFlag) {
		    			subFlag = false;
		    			$("#form_register")[0].submit();
		    		}
		    	}
		    } else {
		    	if(register_form.element($(e.target))) {
		    		var nindex = tindex + 1;
		    		var next = null;
		    		do {
		    			 next = $("[tabindex = " + nindex ++ + "]");
		    		} while (next && next.attr("disabled"));
		    		if(next) {
		    			next.focus();
		    		}
		    	}
		    }
		}
    });
    
    function showValCode() {
    	create_bg();
    	$("#dark_bg").css("z-index", "998");
    	$("#mvcodeDiv").show();
    	$("#reg_code_img").attr("src", ctx + "/member/getVCodeValidateCode?s=" + new Date().getTime());
    }
    
    function closeValCode() {
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
                    url: ctx + "/member/checkVCodeValCode",
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
    	closeValCode();
    });
    $("#mvd_close").click(function() {
    	closeValCode();
    });
    $("#mvd_ok").click(function() {
    	if(mcform.element($("#mvcode"))) {
    		var code = $("#mvcode").val();
    		closeValCode();
    		$('#fetch-vcode').attr("disabled", true);
    		$('#fetch-vcode').removeClass("catch_code");
    		$('#fetch-vcode').addClass("catch_code_gray");
            $.ajax({
                type: "POST",
                url: ctx + "/member/sendVCode",
                data: { "destination": $("#filter_mobilenumber").val(),
                		"option": 0,
                        "channel": 0,
                        "code": code
                      },
                dataType: "json",
                success: function(data) {
                    if(!data.status) {
                    	register_form.showErrors({
                    		"filter_mobilenumber" : data.message
                    	});
                    	$('#fetch-vcode').attr("disabled", false);
    					$('#fetch-vcode').removeClass("catch_code_gray");
    					$('#fetch-vcode').addClass("catch_code");
                    } else {
                    	$('#fetch-vcode').timer({
                            format: '已发送(%s)',
                            duration: '59s',
                            countdown: true,
                            callback: function() {
                                $('#fetch-vcode').timer('remove');
                                $('#fetch-vcode').attr("disabled", false);
                                $('#fetch-vcode').text("获取验证码");
    							$('#fetch-vcode').removeClass("catch_code_gray");
    							$('#fetch-vcode').addClass("catch_code");
                            }
                        });
                    }
                }
            });
    	}
    });
    
    $("#reg_code_img").click(function() {
    	$("#reg_code_img").attr("src", ctx + "/member/getVCodeValidateCode?s=" + new Date().getTime());
    });

});

