$(document).ready(function(){
  	var subFlag = true;
    // Form表单验证
    var register_form = $("#form_register").validate({
        submitHandler: function(form) {
      		if(subFlag) {
      			subFlag = false;
      			form.submit();
      		}
      	},
        rules: {
        	filter_membername: {
        		required: true,
        		pattern: /^[a-zA-Z]\w{5,19}$/,
        		remote: {
        			url: ctx + "/member/checkName",
        			type: "POST",
        			data: {
        				memberName: function() {
        					return $("#filter_membername").val();
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
            filter_mobilenumber: {
            	required: true,
            	pattern: /^1\d{10}$/,
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
        	filter_membername: {
        		required: '请输入用户名',
        		pattern: '必须是6-20位字母开头，字母或数字组成',
        		remote: '此用户名已经被使用'
        	},
        	filter_loginpassword: {
        		required: "请输入密码",
        		pattern: '密码必须是8-20位长的字母、数字和符号的组合',
        	},
        	dpassword: {
        		required: "请再输入一遍密码",
        		equalTo: "两个密码须保持一致"
        	},
        	filter_mobilenumber: {
                required: "请输入手机号码",
                pattern: "请输入正确的手机号码",
            },
            filter_verifycode: {
            	required: "请输入验证码"
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

    // 发送邀请码
    $("#fetch-vcode").click(function() {
    	if(register_form.element($("#filter_mobilenumber"))) {
        $('#fetch-vcode').attr("disabled", true);
        $('#fetch-vcode').removeClass("m-catch_code");
        $('#fetch-vcode').addClass("m-catch_code_gray");
        $.ajax({
            type: "POST",
            url: ctx + "/member/sendVCode",
            data: { "destination": $("#filter_mobilenumber").val(),
                    "channel": 0,
                    "option": 0
                  },
            dataType: "json",
            success: function(data) {
                if(!data.status) {
                	register_form.showErrors({
                		"filter_mobilenumber" : data.message
                	});
                  $('#fetch-vcode').attr("disabled", false);
                  $('#fetch-vcode').removeClass("m-catch_code_gray");
                  $('#fetch-vcode').addClass("m-catch_code");
                } else {
                	$('#fetch-vcode').timer({
                        format: '已发送(%s)',
                        duration: '59s',
                        countdown: true,
                        callback: function() {
                            $('#fetch-vcode').timer('remove');
                            $('#fetch-vcode').attr("disabled", false);
                            $('#fetch-vcode').text("获取验证码");
														$('#fetch-vcode').removeClass("m-catch_code_gray");
														$('#fetch-vcode').addClass("m-catch_code");
                        }
                    });
                }
            }
        });
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

});
