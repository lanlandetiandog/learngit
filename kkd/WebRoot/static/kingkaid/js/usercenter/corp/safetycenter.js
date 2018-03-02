$(document).ready(function(){
    $("#usermenu_aqzx").addClass("user_menulist_href_hover");
    $("#menu_jkd").addClass("leftmenu_cur");

    $(".operate_btn").click(function(){
        $(this).parent().parent().siblings(".unit_safety_detail").toggle();

        var cur_text = $(this).text();

        if(cur_text=="[收起]"){
            if($(this).attr("id")=="info_view"){
                $(this).text("[查看]");
            }else{
                $(this).text("[修改]");
            }
            $(this).parent().parent().removeClass("unit_list_top_down"); 
        }
        else{
            $(this).text("[收起]");
            $(this).parent().parent().addClass("unit_list_top_down"); 
        }
    });

    // 更换所有下拉框为chosen组件(除了省市区联动组件外) 
    $("select").chosen({ width: "250px", disable_search_threshold: 10 });    
    // 更换密码表单验证
    var change_pwd_form = $("#change_pwd_form").validate({
    	rules: {
    		old_password: "required",
    		new_password: {
    			required: true,
    			pattern: /^(?![a-zA-Z]+$)(?!\d+$)[A-Za-z0-9~!@#$%^&*()_+`\-={}:";'<>?,.\/]{8,20}$/
    		},
    		new_password_d: {
    			required: true,
    			equalTo: "#new_password"
    		}
    	},
    	messages: {
    		old_password: {
    			required: "请输入当前密码"
    		},
            new_password: {
                required: "请输入新密码",
                pattern: "必须是8-20位字母、数字或符号的组合"
            },
            new_password_d: {
            	required: "请再输入一遍新密码",
                equalTo: "两遍密码须保持一致"
            }
    	},
    	errorElement: "em",
    	errorClass: "sc-error",
    	errorPlacement: function(error, element) {
    		error.appendTo(element.next());
    	}
    });

    // 更换密码-确定
    $("#change_pwd_btn").click(function() {
    	if(change_pwd_form.form()) {
            $.ajax({
                type: 'POST',
                dataType: "json",
                url: ctx + '/auth/usercenter/updatePwd',
                data: {"oldPassword": $("#old_password").val(),
                       "newPassword": $("#new_password").val()},
                success: function(data) {
                    if(data.status) {
                    	alert(data.message);
                        location.href = ctx + "/auth/usercenter/safetycenter.html";
                    } else {
                    	change_pwd_form.resetForm();
                    	$('#change_pwd_msg').timer({
                            format: data.message,
                            duration: '3s',
                            callback: function() {
                                $('#change_pwd_msg').timer('remove');
                            }
                        });
                    }
                }
            });
    	}
    });
    
 // 绑定手机表单验证
    var bind_mobile_form = $("#bind_mobile_form").validate({
        rules: {
            mobile: {
            	required: true,
                pattern: /^1\d{10}$/,
            },
            m_vcode: "required"
        },
        messages: {
        	mobile: {
        		required: "请输入手机号码",
                pattern: "请输入正确的手机号码",
            },
            m_vcode: {
            	required: "请输入验证码"
            }
        },
        errorElement: "em",
        errorClass: "sc-error",
        errorPlacement: function(error, element) {
            error.appendTo(element.parent());
        }
    });
    
    // 绑定手机-发送验证码
    $("#m_send_btn").click(function() {
    	if(bind_mobile_form.element($("#mobile"))) {
    		showBMValCode();
    	}
    });
    // 绑定手机
    $("#bind_mobile_btn").click(function() {
    	if(bind_mobile_form.form()) {
            $.ajax({
                type: 'POST',
                dataType: 'json',
                url: ctx + '/auth/usercenter/bindContactWay',
                data: {"channel": 0,
                       "verifyCode": $("#m_vcode").val(),
                       "destination": $("#mobile").val()},
                success: function(data) {
                	if(data.status) {
                        location.href = ctx + "/auth/usercenter/safetycenter.html";
                	} else {
                		bind_mobile_form.resetForm();
                        $('#bind_mobile_msg').timer({
                            format: data.message,
                            duration: '3s',
                            callback: function() {
                                $('#bind_mobile_msg').timer('remove');
                            }
                        });
                	}
                }
            });
    	}
    });
    
    // 变更手机表单1验证
    var update_mobile_form1 = $("#update_mobile_form1").validate({
        rules: {
        	m_old_vcode: "required"
        },
        messages: {
        	m_old_vcode: {
                required: "请输入验证码"
            }
        },
        errorElement: "em",
        errorClass: "sc-error",
        errorPlacement: function(error, element) {
            error.appendTo(element.parent());
        }
    });
    
    // 改变手机号码-发送号码到旧手机
    $("#m_send_old_btn").click(function() {
    	$('#m_send_old_btn').attr("disabled", true);
    	$('#m_send_old_btn').removeClass("catch_code");
		$('#m_send_old_btn').addClass("catch_code_gray");
    	$.ajax({
            type: 'POST',
            dataType: 'json',
            url: ctx + '/auth/usercenter/sendmVCode',
            data: {"channel": 0,
            	   "option": 2},
            success: function(data) {
                if(data.status) {
                	$('#m_send_old_btn').timer({
						format: '已发送(%s)',
						duration: '59s',
						countdown: true,
						callback: function() {
							$('#m_send_old_btn').timer('remove');
							$('#m_send_old_btn').attr("disabled", false);
							$('#m_send_old_btn').text("发送验证码");
							$('#m_send_old_btn').removeClass("catch_code_gray");
							$('#m_send_old_btn').addClass("catch_code");
						}
					});
            	} else {
            		 $('#update_mobile_msg1').timer({
                         format: data.message,
                         duration: '3s',
                         callback: function() {
                             $('#update_mobile_msg1').timer('remove');
                         }
                     });
            		 $('#m_send_old_btn').attr("disabled", false);
            		 $('#m_send_old_btn').removeClass("catch_code_gray");
            		 $('#m_send_old_btn').addClass("catch_code");
            	}
            }
        });
    });
    // 改变手机号码-下一步
    $("#m_change_btn_next").click(function() {
    	if(update_mobile_form1.form()) {
        	$.ajax({
                type: 'POST',
                dataType: "json",
                url: ctx + '/auth/usercenter/verifyForUpdate',
                data: { "channel": 0,
                	    "verifyCode": $("#m_old_vcode").val()},
                success: function(data) {
                	if (data.status) {
                        $("#change_mobile_1").hide("slow");
                		$("#change_mobile_2").show("slow");
                	} else {
                		 $('#update_mobile_msg1').timer({
                                format: data.message,
                                duration: '3s',
                                callback: function() {
                                    $('#update_mobile_msg1').timer('remove');
                                }
                         });
                	}
                }
            });
    	}
    });
    
    // 变更手机表单2验证
    var update_mobile_form2 = $("#update_mobile_form2").validate({
        rules: {
        	new_mobile: {
                required: true,
                pattern: /^1\d{10}$/,
            },
            m_new_vcode: "required"
        },
        messages: {
        	new_mobile: {
                required: "请输入手机号码",
                pattern: "请输入正确的手机号码",
            },
            m_new_vcode: {
                required: "请输入验证码"
            }
        },
        errorElement: "em",
        errorClass: "sc-error",
        errorPlacement: function(error, element) {
            error.appendTo(element.parent());
        }
    });
    // 给新手机发送验证码
    $("#m_send_new_btn").click(function() {
    	if(update_mobile_form2.element($("#new_mobile"))) {
    		showUMValCode();
    	}
    });
    // 更新手机
    $("#update_mobile_btn").click(function() {
    	if(update_mobile_form2.form()) {
        	$.ajax({
                type: 'POST',
                dataType: 'json',
                url: ctx + '/auth/usercenter/updateContactWay',
                data: {"channel": 0,
                	   "verifyCode": $("#m_new_vcode").val(),
                       "destination": $("#new_mobile").val()},
                success: function(data) {
                	if(data.status) {
                        location.href = ctx + "/auth/usercenter/safetycenter.html";
                	} else {
                		update_mobile_form2.resetForm();
                        $('#update_mobile_msg2').timer({
                            format: data.message,
                            duration: '3s',
                            callback: function() {
                                $('#update_mobile_msg2').timer('remove');
                            }
                        });
                	}
                }
            });
    	}
    });

    // 绑定邮箱表单验证
    var bind_email_form = $("#bind_email_form").validate({
        rules: {
            email: {
            	required: true,
            	email: true,
            },
            e_vcode: "required"
        },
        messages: {
        	email: {
        		required: "请输入邮箱地址",
        		email: "请输入正确的邮箱地址",
            },
            e_vcode: {
            	required: "请输入验证码"
            }
        },
        errorElement: "em",
        errorClass: "sc-error",
        errorPlacement: function(error, element) {
            error.appendTo(element.parent());
        }
    });

    // 绑定邮箱-发送验证码
    $("#e_send_btn").click(function() {
    	if(bind_email_form.element($("#email"))) {
    		showBEValCode();
    	}
    });

    // 绑定邮箱
    $("#bind_email_btn").click(function() {
    	if(bind_email_form.form()) {
	        $.ajax({
	            type: 'POST',
	            dataType: 'json',
	            url: ctx + '/auth/usercenter/bindContactWay',
	            data: {"channel": 1,
	                   "verifyCode": $("#e_vcode").val(),
	                   "destination": $("#email").val()},
	            success: function(data) {
	            	if(data.status) {
                        location.href = ctx + "/auth/usercenter/safetycenter.html";
                	} else {
                		bind_email_form.resetForm();
                        $('#bind_email_msg').timer({
                            format: data.message,
                            duration: '3s',
                            callback: function() {
                                $('#bind_email_msg').timer('remove');
                            }
                        });
                	}
	            }
	        });
    	}
    });

    // 变更邮箱表单1验证
    var update_email_form1 = $("#update_email_form1").validate({
        rules: {
        	e_old_vcode: "required"
        },
        messages: {
        	e_old_vcode: {
                required: "请输入验证码"
            }
        },
        errorElement: "em",
        errorClass: "sc-error",
        errorPlacement: function(error, element) {
            error.appendTo(element.parent());
        }
    });

    // 改变邮箱-发送验证码到旧邮箱
    $("#e_send_old_btn").click(function() {
    	$('#e_send_old_btn').attr("disabled", true);
    	$('#e_send_old_btn').removeClass("catch_code");
		$('#e_send_old_btn').addClass("catch_code_gray");
        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: ctx + '/auth/usercenter/sendmVCode',
            data: {"channel": 1,
            	   "option": 2},
            success: function(data) {
            	if(data.status) {
            		$('#e_send_old_btn').timer({
                        format: '已发送(%s)',
                        duration: '59s',
                        countdown: true,
                        callback: function() {
                            $('#e_send_old_btn').timer('remove');
                            $('#e_send_old_btn').attr("disabled", false);
                            $('#e_send_old_btn').text("发送验证码");
							$('#e_send_old_btn').removeClass("catch_code_gray");
							$('#e_send_old_btn').addClass("catch_code");
                        }
                    });
            	} else {
            		 $('#update_email_msg1').timer({
                         format: data.message,
                         duration: '3s',
                         callback: function() {
                             $('#update_email_msg1').timer('remove');
                         }
                     });
            		 $('#e_send_old_btn').attr("disabled", false);
            		 $('#e_send_old_btn').removeClass("catch_code_gray");
					 $('#e_send_old_btn').addClass("catch_code");
            	}
            }
        });
    });
    // 改变电子邮箱-下一步
    $("#e_change_btn_next").click(function() {
    	if(update_email_form1.form()) {
	        $.ajax({
	            type: 'POST',
	            dataType: "json",
	            url: ctx + '/auth/usercenter/verifyForUpdate',
	            data: { "channel": 1,
	                    "verifyCode": $("#e_old_vcode").val()},
	            success: function(data) {
	            	if (data.status) {
                        $("#change_email_1").hide("slow");
                		$("#change_email_2").show("slow");
                	} else {
                		 $('#update_email_msg1').timer({
                                format: data.message,
                                duration: '3s',
                                callback: function() {
                                    $('#update_email_msg1').timer('remove');
                                }
                         });
                	}
	            }
	        });
    	}
    });

    // 变更邮箱表单2验证
    var update_email_form2 = $("#update_email_form2").validate({
        rules: {
        	new_email: {
                required: true,
                email: true,
            },
            e_new_vcode: "required"
        },
        messages: {
        	new_email: {
                required: "请输入邮箱地址",
                email: "请输入正确的邮箱地址",
            },
            e_new_vcode: {
                required: "请输入验证码"
            }
        },
        errorElement: "em",
        errorClass: "sc-error",
        errorPlacement: function(error, element) {
            error.appendTo(element.parent());
        }
    });

    // 给新邮箱发送验证码
    $("#e_send_new_btn").click(function() {
    	if(update_email_form2.element($("#new_email"))) {
    		showUEValCode();
    	}
    });

    // 更新邮箱
    $("#update_email_btn").click(function() {
    	if(update_email_form2.form()) {
	        $.ajax({
	            type: 'POST',
	            dataType: 'json',
	            url: ctx + '/auth/usercenter/updateContactWay',
	            data: {"channel": 1,
	                   "verifyCode": $("#e_new_vcode").val(),
	                   "destination": $("#new_email").val()},
	            success: function(data) {
	            	if(data.status) {
                        location.href = ctx + "/auth/usercenter/safetycenter.html";
                	} else {
                		update_email_form2.resetForm();
                        $('#update_email_msg2').timer({
                            format: data.message,
                            duration: '3s',
                            callback: function() {
                                $('#update_email_msg2').timer('remove');
                            }
                        });
                	}
	            }
	        });
    	}
    });
    
    // 紧急联系人校验
    var update_urgentper_form = $("#update_urgentper_form").validate({
        rules: {
        	ungent_name: {
                required: true,
            },
            ungent_mobile: {
            	required: true,
                pattern: /^1\d{10}$/
            },
            ungent_tel: {
            	pattern: /^0\d{2,3}-\d{6,8}$/
            }
        },
        messages: {
        	ungent_name: {
                required: "请输入联系人姓名",
            },
            ungent_mobile: {
                required: "请输入手机号码",
                pattern: "请输入正确的手机号码",
            },
            ungent_tel: {
            	pattern: "请输入正确的电话如：029-12345678"
            }
        },
        errorElement: "em",
        errorClass: "sc-error",
        errorPlacement: function(error, element) {
            error.appendTo(element.parent());
        }
    });
    
    // 更新紧急联系人
    $("#update_urgentper_btn").click(function() {
    	if(update_urgentper_form.form()) {
	    	$.ajax({
	            type: 'POST',
	            dataType: 'json',
	            url: ctx + '/auth/usercenter/updateCorpSCContacter',
	            data: {"channel": 1,
	                   "filter_connpepole": $("#ungent_name").val(),
	                   "filter_mobile": $("#ungent_mobile").val(),
	                   "filter_tel": $("#ungent_tel").val()},
	            success: function(data) {
	            	if(data.status) {
                        location.href = ctx + "/auth/usercenter/safetycenter.html";
                	} else {
                		update_urgentper_form.resetForm();
                        $('#update_urgentper_msg').timer({
                            format: data.message,
                            duration: '3s',
                            callback: function() {
                                $('#update_urgentper_msg').timer('remove');
                            }
                        });
                	}
	            }
	        });
    	}
    });
    
    // 初始化企业信息中的地址选择组件
    $("#corp_addr_area").areaSelection({level:3, value:$("#corp_addr_code").val(), item_linkage: function (v) {
    	$("#corp_addr_code").val(v);
    	corp_info_form.element($("#corp_addr_code"));
    }});
    
    // 添加下拉校验事件
    $("#corp_orgakind").chosen().change(function() {
    	corp_info_form.element($("#corp_orgakind"));
    });
    $("#corp_waykind").chosen().change(function() {
    	corp_info_form.element($("#corp_waykind"));
    });
    
    // 验证企业信息
    var corp_info_form = $("#corp_info_form").validate({
    	rules: {
    		corp_orgakind: "required",
    		corp_waykind: "required",
    		corp_tel: {
    			required: true,
    			pattern: /^0\d{2,3}-\d{6,8}$/
    		},
    		corp_connpepole: "required",
    		corp_mobile: {
    			required: true,
    			pattern: /^1\d{10}$/
    		},
    		corp_email: {
    			required: true,
    			email: true
    		},
    		corp_addr_code: {
    			required: true,
        		pattern: /^(?!\d{4}00)\d{6}$/
    		}, 
    		corp_addr_block: "required",
    		corp_leadernum: {
    			digits: true,
    			pattern: /^(?!0\d+)\d*$/
    		},
    		corp_leadcustname:{
    			required: true
    		},
    		corp_regifund:{
    			required: true
    		},
    		corp_comedate:{
    			required: true
    		},
    		corp_operatescope:{
    			required: true
    		}
    	},
    	messages: {
    		corp_orgakind: "请选择公司类型",
    		corp_waykind: "请选择公司行业",
    		corp_tel: {
    			required: "请输入公司电话",
    			pattern: "请输入正确的电话如：029-12345678"
    		},
    		corp_connpepole: "请填写公司联系人",
    		corp_mobile: {
    			required: "请输入联系人手机",
    			pattern: "请输入正确的手机号码"
    		},
    		corp_email: {
    			required: "请输入公司邮箱",
    			email: "请输入正确的邮箱地址"
    		},
    		corp_addr_code: "请选择所在的省、市和区域",
    		corp_addr_block: "请输入街道地址",
    		corp_leadernum: {
    			digits: '请输入整数',
    			pattern: '请输入正确的数值'
    		},
    		corp_leadcustname: {
    			required: "请输入法人代表"
    		},
    		corp_regifund: {
    			required: "请输入注册资本"
    		},
    		corp_comedate: {
    			required: "请输入成立日期"
    		},
    		corp_operatescope: {
    			required: "请输入经营范围"
    		},
    	},
    	ignore: "",
    	errorElement: "em",
        errorClass: "error",
        errorPlacement: function(error, element) {
        	if(element.is("select")) {
        		error.appendTo(element.next().next());
        	} else {
        		error.appendTo(element.next());
        	}
        }
    });
    
    // 更新企业信息
    $("#update_corpinfo_btn").click(function() {
    	if(corp_info_form.form()) {
	    	$.ajax({
	            type: 'POST',
	            dataType: 'json',
	            url: ctx + '/auth/usercenter/updateCorpSCInfo',
	            data: {"filter_orgakind": $('#corp_orgakind').val(),
	                   "filter_waykind": $('#corp_waykind').val(),
	                   "filter_corpsizesign": $('#corp_sizesign').val(),
	                   "filter_leadernum": $('#corp_leadernum').val(),
		               "filter_tel": $('#corp_tel').val(),
		               "filter_connpepole": $('#corp_connpepole').val(),
		               "filter_mobile": $('#corp_mobile').val(),
		               "filter_email": $('#corp_email').val(),
		               "filter_leadcustname": $('#corp_leadcustname').val(),
		               "filter_regifund": $('#corp_regifund').val(),
		               "comedate": $('#corp_comedate').val(),
		               "filter_operatescope": $('#corp_operatescope').val(),
		               "filter_addr": area_selection_text("corp_addr_area") + $("#corp_addr_block").val() },
	            success: function(data) {
	            	if(data.status) {
	                    location.href = ctx + "/auth/usercenter/safetycenter.html";
	            	} else {
	            		update_corpinfo_form.resetForm();
	                    $('#update_corpinfo_msg').timer({
	                        format: data.message,
	                        duration: '3s',
	                        callback: function() {
	                            $('#update_corpinfo_msg').timer('remove');
	                        }
	                    });
	            	}
	            }
	        });
    	}
    });
    
    function showBMValCode() {
    	create_bg();
    	$("#dark_bg").css("z-index", "1998");
    	$("#bmvcodeDiv").show();
    	$("#bmvcode_img").attr("src", ctx + "/member/getVCodeValidateCode?s=" + new Date().getTime());
    }
    
    function closeBMValCode() {
        $("#dark_bg").remove();
        $("#bmvcodeDiv").hide();
        bmcform.resetForm();
    }
    
    var bmcform = $("#bmcform").validate({
    	rules: {
    		bmvcode: {
    			required: true,
    			rangelength: [4,4],
    			remote: {
                    url: ctx + "/member/checkVCodeValCode",
                    type: "POST",
                    cache: false,
                    data: {
                        code: function() { 
                            return $("#bmvcode").val();
                        }
                    }
                }
    		}
    	},
    	messages: {
    		bmvcode: {
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

    $("#bmvcode").focus(function(e) {
    	bmcform.element($(e.target));
    });
    
    $("#bmvd_cancel").click(function() {
    	closeBMValCode();
    });
    $("#bmvd_close").click(function() {
    	closeBMValCode();
    });
    $("#bmvd_ok").click(function() {
    	if(bmcform.element($("#bmvcode"))) {
    		var code = $("#bmvcode").val();
    		closeBMValCode();
    		$('#m_send_btn').attr("disabled", true);
			$('#m_send_btn').removeClass("catch_code");
			$('#m_send_btn').addClass("catch_code_gray");
        	$.ajax({
                type: 'POST',
                dataType: 'json',
                url: ctx + '/member/sendVCode',
                data: {"channel": 0,
                	   "option": 2,
                       "destination": $("#mobile").val(),
                       "code": code
                       },
                success: function(data) {
                	if(data.status) {
						$('#m_send_btn').timer({
	                        format: '已发送(%s)',
	                        duration: '59s',
	                        countdown: true,
	                        callback: function() {
	                            $('#m_send_btn').timer('remove');
	                            $('#m_send_btn').attr("disabled", false);
	                            $('#m_send_btn').text("发送验证码");
								$('#m_send_btn').removeClass("catch_code_gray");
								$('#m_send_btn').addClass("catch_code");
	                        }
	                    });
                	} else {
                		bind_mobile_form.showErrors({"mobile" : data.message});
                		$('#m_send_btn').attr("disabled", false);
						$('#m_send_btn').removeClass("catch_code_gray");
						$('#m_send_btn').addClass("catch_code");
                	}
                	$("#bmvcode_img").attr("src", ctx + "/member/getVCodeValidateCode?s=" + new Date().getTime());
                }
            });
    	}
    });
    
    $("#bmvcode_img").click(function() {
    	$("#bmvcode_img").attr("src", ctx + "/member/getVCodeValidateCode?s=" + new Date().getTime());
    });
    
    function showUMValCode() {
    	create_bg();
    	$("#dark_bg").css("z-index", "1998");
    	$("#umvcodeDiv").show();
    	$("#umvcode_img").attr("src", ctx + "/member/getVCodeValidateCode?s=" + new Date().getTime());
    }
    
    function closeUMValCode() {
        $("#dark_bg").remove();
        $("#umvcodeDiv").hide();
        umcform.resetForm();
    }
    
    var umcform = $("#umcform").validate({
    	rules: {
    		umvcode: {
    			required: true,
    			rangelength: [4,4],
    			remote: {
                    url: ctx + "/member/checkVCodeValCode",
                    type: "POST",
                    cache: false,
                    data: {
                        code: function() { 
                            return $("#umvcode").val();
                        }
                    }
                }
    		}
    	},
    	messages: {
    		umvcode: {
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

    $("#umvcode").focus(function(e) {
    	umcform.element($(e.target));
    });
    
    $("#umvd_cancel").click(function() {
    	closeUMValCode();
    });
    $("#umvd_close").click(function() {
    	closeUMValCode();
    });
    $("#umvd_ok").click(function() {
    	if(umcform.element($("#umvcode"))) {
    		var code = $("#umvcode").val();
    		closeUMValCode();
    		$('#m_send_new_btn').attr("disabled", true);
        	$('#m_send_new_btn').removeClass("catch_code");
    		$('#m_send_new_btn').addClass("catch_code_gray");
            $.ajax({
                type: 'POST',
                dataType: 'json',
                url: ctx + '/member/sendVCode',
                data: {"channel": 0,
                	   "option": 2,
                	   "destination": $("#new_mobile").val(),
                	   "code": code
                	  },
                success: function(data) {
                	if(data.status) {
						$('#m_send_new_btn').timer({
	                        format: '已发送(%s)',
	                        duration: '59s',
	                        countdown: true,
	                        callback: function() {
	                            $('#m_send_new_btn').timer('remove');
	                            $('#m_send_new_btn').attr("disabled", false);
	                            $('#m_send_new_btn').text("获取验证码");
								$('#m_send_new_btn').removeClass("catch_code_gray");
								$('#m_send_new_btn').addClass("catch_code");
	                        }
	                    });
                	} else {
                		update_mobile_form2.showErrors({"new_mobile" : data.message});
                		$('#m_send_new_btn').attr("disabled", false);
						$('#m_send_new_btn').removeClass("catch_code_gray");
						$('#m_send_new_btn').addClass("catch_code");
                	}
                	$("#umvcode_img").attr("src", ctx + "/member/getVCodeValidateCode?s=" + new Date().getTime());
                }
            });
    	}
    });
    
    $("#umvcode_img").click(function() {
    	$("#umvcode_img").attr("src", ctx + "/member/getVCodeValidateCode?s=" + new Date().getTime());
    });
    
    function showBEValCode() {
    	create_bg();
    	$("#dark_bg").css("z-index", "1998");
    	$("#bevcodeDiv").show();
    	$("#bevcode_img").attr("src", ctx + "/member/getVCodeValidateCode?s=" + new Date().getTime());
    }
    
    function closeBEValCode() {
        $("#dark_bg").remove();
        $("#bevcodeDiv").hide();
        becform.resetForm();
    }
    
    var becform = $("#becform").validate({
    	rules: {
    		bevcode: {
    			required: true,
    			rangelength: [4,4],
    			remote: {
                    url: ctx + "/member/checkVCodeValCode",
                    type: "POST",
                    cache: false,
                    data: {
                        code: function() { 
                            return $("#bevcode").val();
                        }
                    }
                }
    		}
    	},
    	messages: {
    		bevcode: {
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

    $("#bevcode").focus(function(e) {
    	becform.element($(e.target));
    });
    
    $("#bevd_cancel").click(function() {
    	closeBEValCode();
    });
    $("#bevd_close").click(function() {
    	closeBEValCode();
    });
    $("#bevd_ok").click(function() {
    	if(becform.element($("#bevcode"))) {
    		var code = $("#bevcode").val();
    		closeBEValCode();
    		$('#e_send_btn').attr("disabled", true);
    		$('#e_send_btn').removeClass("catch_code");
    		$('#e_send_btn').addClass("catch_code_gray");
	        $.ajax({
	            type: 'POST',
	            dataType: 'json',
	            url: ctx + '/member/sendVCode',
	            data: {"channel": 1,
	            	   "option": 2,
	                   "destination": $("#email").val(),
                       "code": code
	                  },
	            success: function(data) {
                	if(data.status) {    
                		$('#e_send_btn').timer({
	                        format: '已发送(%s)',
	                        duration: '59s',
	                        countdown: true,
	                        callback: function() {
	                            $('#e_send_btn').timer('remove');
	                            $('#e_send_btn').attr("disabled", false);
	                            $('#e_send_btn').text("发送验证码");
								$('#e_send_btn').removeClass("catch_code_gray");
								$('#e_send_btn').addClass("catch_code");
	                        }
	                    });
                	} else {
                		bind_email_form.showErrors({"email" : data.message});
                		$('#e_send_btn').attr("disabled", false);
                		$('#e_send_btn').removeClass("catch_code_gray");
                		$('#e_send_btn').addClass("catch_code");
                	}
                	$("#bevcode_img").attr("src", ctx + "/member/getVCodeValidateCode?s=" + new Date().getTime());
	            }
	        });
    	}
    });
    
    $("#bevcode_img").click(function() {
    	$("#bevcode_img").attr("src", ctx + "/member/getVCodeValidateCode?s=" + new Date().getTime());
    });
    
    function showUEValCode() {
    	create_bg();
    	$("#dark_bg").css("z-index", "1998");
    	$("#uevcodeDiv").show();
    	$("#uevcode_img").attr("src", ctx + "/member/getVCodeValidateCode?s=" + new Date().getTime());
    }
    
    function closeUEValCode() {
        $("#dark_bg").remove();
        $("#uevcodeDiv").hide();
        uecform.resetForm();
    }
    
    var uecform = $("#uecform").validate({
    	rules: {
    		uevcode: {
    			required: true,
    			rangelength: [4,4],
    			remote: {
                    url: ctx + "/member/checkVCodeValCode",
                    type: "POST",
                    cache: false,
                    data: {
                        code: function() { 
                            return $("#uevcode").val();
                        }
                    }
                }
    		}
    	},
    	messages: {
    		uevcode: {
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

    $("#uevcode").focus(function(e) {
    	uecform.element($(e.target));
    });
    
    $("#uevd_cancel").click(function() {
    	closeUEValCode();
    });
    $("#uevd_close").click(function() {
    	closeUEValCode();
    });
    $("#uevd_ok").click(function() {
    	if(uecform.element($("#uevcode"))) {
    		var code = $("#uevcode").val();
    		closeUEValCode();
    		$('#e_send_new_btn').attr("disabled", true);
    		$('#e_send_new_btn').removeClass("catch_code");
    		$('#e_send_new_btn').addClass("catch_code_gray");
	        $.ajax({
	            type: 'POST',
	            dataType: 'json',
	            url: ctx + '/member/sendVCode',
	            data: {"channel": 1,
	            	   "option": 2,
	                   "destination": $("#new_email").val(),
	                   "code": code
	                  },
	            success: function(data) {
	                if(data.status) {
	                	$('#e_send_new_btn').timer({
	                        format: '已发送(%s)',
	                        duration: '59s',
	                        countdown: true,
	                        callback: function() {
	                            $('#e_send_new_btn').timer('remove');
	                            $('#e_send_new_btn').attr("disabled", false);
	                            $('#e_send_new_btn').text("发送验证码");
								$('#e_send_new_btn').removeClass("catch_code_gray");
								$('#e_send_new_btn').addClass("catch_code");
	                        }
	                    });
	                	
                	} else {
                		update_email_form2.showErrors({"new_email" : data.message});
                		$('#e_send_new_btn').attr("disabled", false);
                		$('#e_send_new_btn').removeClass("catch_code_gray");
						$('#e_send_new_btn').addClass("catch_code");
                	}
	                $("#uevcode_img").attr("src", ctx + "/member/getVCodeValidateCode?s=" + new Date().getTime());
	            }
	        });
    	}
    });
    
    $("#uevcode_img").click(function() {
    	$("#uevcode_img").attr("src", ctx + "/member/getVCodeValidateCode?s=" + new Date().getTime());
    });
    
    $("input").keypress(function(e) {
		if(e.which == 13) { 
			return false;
		}
    });
    
    var reset_pwd_form = $("#reset_pwd_form").validate({
    	rules: {   		
    		ctp_chkcode: "required"
    	},
    	messages: {
            ctp_chkcode:  {
            	required: "请输入短信验证码"
            }
    	},
    	 errorElement: "em",
         errorClass: "sc-error",
         errorPlacement: function(error, element) {
             error.appendTo(element.parent());
         }
    });
    
    
    $("#setpwd_send_msg").click(function() {
    	if (validPwd(pgeditor) && isSamePwd(pgeditor, pgeditorCopy)) {   		
    		$('#setpwd_send_msg').attr("disabled", true);
        	$('#setpwd_send_msg').removeClass("catch_code");
    		$('#setpwd_send_msg').addClass("catch_code_gray");
    		$.ajax({
                type: 'POST',
                dataType: 'json',
                url: ctx + '/auth/cust/sendmsg.html',
                data: {"type": 2},
                success: function(data) {
    				if(data.code=='s') {
    					$('#setpwd_send_msg').timer({
    						format: '已发送(%s)',
    						duration: '59s',
    						countdown: true,
    						callback: function() {
    							$('#setpwd_send_msg').timer('remove');
    							$('#setpwd_send_msg').attr("disabled", false);
    							$('#setpwd_send_msg').text("发送验证码");
    							$('#setpwd_send_msg').removeClass("catch_code_gray");
    							$('#setpwd_send_msg').addClass("catch_code");
    						}
    					});
                	} else {
                		 $('#setpwd_send_msg').timer({
                             format: data.message,
                             duration: '3s',
                             callback: function() {
                                 $('#setpwd_send_msg').timer('remove');
                             }
                         });
    					 $('#setpwd_send_msg').attr("disabled", false);
    					 $('#setpwd_send_msg').removeClass("catch_code_gray");
    					 $('#setpwd_send_msg').addClass("catch_code");
                	}
                }
            });
    	}
    });
    
    $("#ctp_reset_pwd_btn").click(function() {
    	if (reset_pwd_form.form() && validPwd(pgeditor) && isSamePwd(pgeditor, pgeditorCopy)) {   
	    	$.ajax({
				type: 'post',
				dataType: 'json',
				url: ctx + '/auth/xabank/getrk',
				success: function(data) {
					if (data.status == true) {
	    				$.ajax({
	    	    			type:'post',
	    	    			url:ctx+'/auth/cust/reset_pwd.html',
	    	    			data: {
	    		            	"chkcode": $("#ctp_chkcode").val(),
	    		            	"newpwd": function() {
		    		            		pgeditor.pwdSetSk(data.body.rcode);
		    		            		var pwd = pgeditor.pwdSmResult(data.body.kx, data.body.ky);
		    		            		return encodeURIComponent(pwd);
	    		            		}()
	    		            	},
	    	    			dataType:'json',
	    	    			success:function(data){
	    	    				if(data.status == true){
	    	    					$('#ctp_reset_pwd_btn').attr("disabled", false);
	    	    					alert(data.message);
	    	    					window.location.reload(true);
	    	    				}else{
	    	    					pgeditor.pwdclear();
	    	    					pgeditorCopy.pwdclear();
	    	    					$('#ctp_reset_pwd_btn').attr("disabled", false);
	    	    					alert(data.message);
	    	    				}  				   				
	    	    			},
	    	    			error: function() {
		    					pgeditor.pwdclear();
		    					pgeditorCopy.pwdclear();
		    					$('#ctp_reset_pwd_btn').attr("disabled", false);
	    	    				alert("系统繁忙，请稍候再试");
	    	    			}
	    	    		});
					} else {
						pgeditor.pwdclear();
						pgeditorCopy.pwdclear();
						$('#ctp_reset_pwd_btn').attr("disabled", false);
						alert(data.message);
					}
				},
				error: function() {
					pgeditor.pwdclear();
					pgeditorCopy.pwdclear();
					$('#ctp_reset_pwd_btn').attr("disabled", false);
					alert("系统繁忙，请稍候再试");
				}
			});
    	}
    });
    
    
    var modify_pwd_form = $("#modify_pwd_form").validate({
    	rules: {
    		ctp_uppwd_chkcode: "required"
    	},
    	messages: {
            ctp_uppwd_chkcode:  {
            	required: "请输入短信验证码"
            }
    	},
    	errorElement: "em",
    	errorClass: "sc-error",
    	errorPlacement: function(error, element) {
    		error.appendTo(element.parent());
    	}
    });
    
    $("#uppwd_send_msg").click(function() {
    	if (validPwd(pgeditorOld) && validPwd(pgeditorNew) && isSamePwd(pgeditorNew, pgeditorNewCopy)) {
    		$('#uppwd_send_msg').attr("disabled", true);
        	$('#uppwd_send_msg').removeClass("catch_code");
    		$('#uppwd_send_msg').addClass("catch_code_gray");
	    	$.ajax({
	            type: 'POST',
	            dataType: 'json',
	            url: ctx + '/auth/cust/sendmsg.html',
	            data: {"type": 1},
	            success: function(data) {
	            	if(data.code=='s') {
    					$('#uppwd_send_msg').timer({
    						format: '已发送(%s)',
    						duration: '59s',
    						countdown: true,
    						callback: function() {
    							$('#uppwd_send_msg').timer('remove');
    							$('#uppwd_send_msg').attr("disabled", false);
    							$('#uppwd_send_msg').text("发送验证码");
    							$('#uppwd_send_msg').removeClass("catch_code_gray");
    							$('#uppwd_send_msg').addClass("catch_code");
    						}
    					});
                	} else {
                		 $('#uppwd_send_msg').timer({
                             format: data.message,
                             duration: '3s',
                             callback: function() {
                                 $('#uppwd_send_msg').timer('remove');
                             }
                         });
    					 $('#uppwd_send_msg').attr("disabled", false);
    					 $('#uppwd_send_msg').removeClass("catch_code_gray");
    					 $('#uppwd_send_msg').addClass("catch_code");
                	}
	            }
	        });
    	}
    });
    
    //确认修改支付密码
    $("#ctp_update_pwd_btn").click(function(){
    	if (modify_pwd_form.form() && validPwd(pgeditorOld) && validPwd(pgeditorNew) && isSamePwd(pgeditorNew, pgeditorNewCopy)) {
	    	$.ajax({
				type: 'post',
				dataType: 'json',
				url: ctx + '/auth/xabank/getrk',
				success: function(data) {
					if (data.status == true) {
	    				$.ajax({
	    	    			type:'post',
	    	    			url:ctx+'/auth/cust/update_pwd.html',
	    	    			data: {
	    	    				"chkcode": $("#ctp_uppwd_chkcode").val(),
	    	    				"oldpwd": function() {
		    	    					pgeditorOld.pwdSetSk(data.body.rcode);
		    		            		var pwd = pgeditorOld.pwdSmResult(data.body.kx, data.body.ky);
		    		            		return encodeURIComponent(pwd);
	    	    					}(),
	    		            	"newpwd": function() {
		    		            		pgeditorNew.pwdSetSk(data.body.rcode);
		    		            		var pwd = pgeditorNew.pwdSmResult(data.body.kx, data.body.ky);
		    		            		return encodeURIComponent(pwd);
	    		            		}()
	    		            	},
	    	    			dataType:'json',
	    	    			success:function(data){
	    	    				if(data.status == true){
	    	    					$('#ctp_update_pwd_btn').attr("disabled", false);
	    	    					alert(data.message);
	    	    					window.location.reload(true);
	    	    				}else{
	    	    					pgeditorNew.pwdclear();
	    	    					pgeditorNewCopy.pwdclear();
	    	    					$('#ctp_update_pwd_btn').attr("disabled", false);
	    	    					alert(data.message);
	    	    				}  				   				
	    	    			},
	    	    			error: function() {
    	    					pgeditorNew.pwdclear();
    	    					pgeditorNewCopy.pwdclear();
    	    					$('#ctp_update_pwd_btn').attr("disabled", false);
	    	    				alert("系统繁忙，请稍候再试");
	    	    			}
	    	    		});
					} else {
    					pgeditorNew.pwdclear();
    					pgeditorNewCopy.pwdclear();
    					$('#ctp_update_pwd_btn').attr("disabled", false);
						alert(data.message);
					}
				},
				error: function() {
					pgeditorNew.pwdclear();
					pgeditorNewCopy.pwdclear();
					$('#ctp_update_pwd_btn').attr("disabled", false);
					alert("系统繁忙，请稍候再试");
				}
			});
    	}
    });

    function isSamePwd(pge, pgec) {
    	var pgeId = pgec.settings.pgeId;
    	$('#' + pgeId + "_str").parent().find('em.sc-error').remove();
    	pge.pwdSetSk('');
    	pgec.pwdSetSk('');
        if (pge.pwdSm4Result() === pgec.pwdSm4Result()) {
            return true;
        } else {
            var error = $('<em>');
            error.text('两次密码输入不一致');
            error.addClass("sc-error");
            error.appendTo($('#' + pgeId + "_str").parent());
            return false;
        }
    }
    
    function validPwd(pge) {
        var pgeId = pge.settings.pgeId;
    	$('#' + pgeId + "_str").parent().find('em.sc-error').remove();
        if (pge.pwdLength() == 0) {
            var error = $('<em>');
            error.text('请输入6位数字密码');
            error.addClass("sc-error");
            error.appendTo($('#' + pgeId + "_str").parent());
            return false;
        }
        if (pge.pwdValid() == 1) {
            var error = $('<em>');
            error.text('密码不符合要求，请输入6位数字密码');
            error.addClass("sc-error");
            error.appendTo($('#' + pgeId + "_str").parent());
            return false;
        }
        return true;
    }
    
    
  //证书start
    //2101申请证书
    $("#down_click").click(function() { 
    	$.ajax({
			url : ctx + '/auth/usercenter/ChoiceRAInfo.html',// 跳转到
			data : {
 	 		},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.respcode == "0000") {	
					alert("单证书申请成功"); 
	                window.location.reload();
		            return;   
				}else {		
					//申请证书失败 
					alert(data.respdesc);
				}
			},
			error : function() {
				alert("异常！");
			}
		});
    });
    //补发证书  
    $("#rechange_click").click(function() { 
    	$.ajax({
			url : ctx + '/auth/usercenter/RechangeRAInfo.html',
			data : {
 	 		},
			type : 'post',
			cache : false,
			dataType : 'json',
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			success : function(data) {		 	 
				if (data.respcode == "0000") {	
					//申请证书失败 
				    alert("单证书补发成功"); 
	                window.location.reload();
		            return;   
				}else if(data.respcode == "3322"){
					alert("单证书补发前,请先点击下载证书按钮"); 
					return; 
				}else {		
					alert(data.respdesc);    
				}
			},
			error : function() {
				alert("异常！");
			}
		});
    });
    
    //更新 
    $("#update_click").click(function() { 
    	$.ajax({
			url : ctx + '/auth/usercenter/UpdateRAInfo.html',
			data : {
 	 		},
			type : 'post',
			cache : false,
			dataType : 'json',
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			success : function(data) {		 	 
				if (data.respcode == "0000") {	
					//申请证书失败 
				    alert("单证书更新成功"); 
	                window.location.reload();
		            return;   
				}else if(data.respcode == "3322"){
					alert("单证书更新前,请先点击下载证书按钮"); 
					return; 
				}else {		
					alert(data.respdesc);    
				}
			},
			error : function() {
				alert("异常！");
			}
		});
    }); 
    //更新 
    $("#updateDB_click").click(function() { 
    	$.ajax({
			url : ctx + '/auth/usercenter/UpdateDBRAInfo.html',
			data : {
 	 		},
			type : 'post',
			cache : false,
			dataType : 'json',
			contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			success : function(data) {		 	 
				if (data.respcode == "0000") {	
					//申请证书失败 
				    alert("单证书更新成功"); 
	                window.location.reload();
		            return;   
				}else if(data.respcode == "3322"){
					alert("单证书更新前,请先点击下载证书按钮"); 
					return; 
				}else {		
					alert(data.respdesc);    
				}
			},
			error : function() {
				alert("异常！");
			}
		});
    });   
  //证书下载  
    $("#operate_click").click(function() { 
    	OnLoad();	
    	GetVersion2();
    	var options="1";
    	var p10=PKCS10Requisition_SingleCert(options);	
    	$.ajax({
    		url : ctx + '/auth/usercenter/ContForRA2401.html',
    		data : {
     			p10:p10
    		},
    		type : 'post',
    		cache : false,
    		dataType : 'json',
    		success : function(data) {		
    			if (data.respcode != "0000") {	
    				//製作证书失败
    				alert(data.respdesc);		
//    				window.location.reload();
    			} else {		
    				// 申请证书成功
    				// 成功后执行生成证书
    				//2.2.7
    				var signCert=data.signaturecert;				
    				var contianerName = CryptoAgent2.CFCA_GetContainer();					 
    				if (!contianerName) {
    				    var errorDesc = CryptoAgent2.GetLastErrorDesc();
    				    alert(errorDesc);				 
    				    return;
    				}	 
    				//2.2.8
    			     var bResult = CryptoAgent2.CFCA_ImportSignCert(1, signCert, contianerName);
    	                if (!bResult) {
    	                    var errorDesc = CryptoAgent2.GetLastErrorDesc();
    	                    alert(errorDesc);			  
    	                    return;
    	                }    
    	                alert("单证书安装成功"); 
    	                window.location.reload();
    	            return;     
    			}

    		},
    		error : function() {
    			alert("异常！");
    		}
    	});
    });
    
    $("#operateDB_click").click(function() { 	
     	$.ajax({
    		url : ctx + '/auth/usercenter/ContForRA2401CHECK.html',			
    		data : {
     		},
    		type : 'post',
    		cache : false,
    		dataType : 'json',
    		success : function(data) {		
    			if (data.respcode != "0000") {	
    				//製作证书失败
    				alert(data.respdesc);		
    			} else {		
    				operateDB_racert();
    			}	
    		},
    		error : function() {
    			alert("异常！");
    		}
    	});
    });
    
function   operateDB_racert() { 	
    	OnLoad();								
    	GetVersion2();							
    	var options="0";							
    	var p10=PKCS10Requisition_SingleCert(options);	
     	$.ajax({
    		url : ctx + '/auth/usercenter/ContForRA2401.html',			
    		data : {
     			p10:p10
    		},
    		type : 'post',
    		cache : false,
    		dataType : 'json',
    		success : function(data) {		
    			if (data.respcode != "0000") {	
    				//製作证书失败
    				alert(data.respdesc);		
//    				window.location.reload();
    			} else {		
    				// 申请证书成功
    				// 成功后执行生成证书
    				//2.2.7
    				var signCert=data.signaturecert;				
    				var contianerName = CryptoAgent2.CFCA_GetContainer();					 
    				if (!contianerName) {
    				    var errorDesc = CryptoAgent2.GetLastErrorDesc();
    				    alert(errorDesc);				 
    				    return;
    				}	 
    				//2.2.8
    			     var bResult = CryptoAgent2.CFCA_ImportSignCert(1, signCert, contianerName);
    	                if (!bResult) {
    	                    var errorDesc = CryptoAgent2.GetLastErrorDesc();
    	                    alert(errorDesc);			  
    	                    return;
    	                }    
    	                alert("单证书安装成功"); 
    	                window.location.reload();
    	            return;     
    			}

    		},
    		error : function() {
    			alert("异常！");
    		}
    	});
    }
  //数字证书 集成p10 start
  //若为32为操作系统
  var CryptoAgent2 = "";
  function OnLoad() {	 
  	if (navigator.appName.indexOf("Internet") >= 0
  			|| navigator.appVersion.indexOf("Trident") >= 0) {
  		if (window.navigator.cpuClass == "x86") {
  			document.getElementById("FakeCryptoAgent").innerHTML = "<object id=\"CryptoAgent2\" codebase=\"CryptoKit.CertEnrollment.Pro.x86.cab\" classid=\"clsid:71BB5253-EF2B-4C5B-85FF-1FD6A03C29A6\" ></object>";

  		} else {
  			document.getElementById("FakeCryptoAgent").innerHTML = "<object id=\"CryptoAgent2\" codebase=\"CryptoKit.CertEnrollment.Pro.x64.cab\" classid=\"clsid:9E7B8F05-ADBE-4067-ABC6-28E0230A5C18\" ></object>";
  		}
  	} else {
  		document.getElementById("FakeCryptoAgent").innerHTML = "<embed id=\"CryptoAgent2\" type=\"application/npCryptoKit.CertEnrollment.Pro.x86\" style=\"height: 0px; width: 0px\">";
  	}
  	var KenAlgorithm = "RSA";
  	KenAlgorithm[0].selected = "selected";
  	KenAlgorithm[1].selected = "";
  	CryptoAgent2 = document.getElementById("CryptoAgent2");		 
  }   
    
  function GetVersion2() {		
	  //  var DownloadURL= window.location.host;   
	    var DownloadURL = document.getElementById("ctlurlValue").value; 		
	 	   try {			    
	            CryptoAgent2.CFCA_HavePermissiontoGenerateKeyPair();		 
	        }
	        catch (e) {	 
	 	        alert("下载控件未安装，请先安装下载控件");
	 	        if (window.navigator.cpuClass == "x86"){		
	 	        	DownloadURL=DownloadURL+"/CryptoKit.CertEnrollment.Pro.x86.exe";						 
	 	        	window.open(DownloadURL);			
	 	        }else{
	 	        	DownloadURL=DownloadURL+"/CryptoKit.CertEnrollment.Pro.x64.exe";					 
	 	        	window.open(DownloadURL);
	 	        } 
	 	    }
	  }
  
  function PKCS10Requisition_SingleCert(options) {	
		try {
			var keyAlgorithm = "RSA";
			var selectValue = "SHA1";
			var keyLength = 2048;
			var cspName;
			if (options=="1") {
				cspName = "Microsoft Enhanced Cryptographic Provider v1.0";
			}else{
				cspName = "CFCA FOR UKEY CSP v1.1.0";
			}
			var strSubjectDN = "CN=certRequisition,O=CFCA TEST CA,C=CN";
			var res1 = CryptoAgent2.CFCA_SetCSPInfo(keyLength,
					cspName);
			if (!res1) {
				var errorDesc = CryptoAgent2.GetLastErrorDesc();
				alert(errorDesc);
				return;
			}
			var res2 = CryptoAgent2
					.CFCA_SetKeyAlgorithm(keyAlgorithm);
			if (!res2) {
				var errorDesc = CryptoAgent2.GetLastErrorDesc();
				alert(errorDesc);
				return;
			}

			var pkcs10Requisition = 0;
			if (keyAlgorithm == "RSA") {
				// RSA单证 sha1
				pkcs10Requisition = CryptoAgent2
						.CFCA_PKCS10CertRequisition(
								strSubjectDN, 1, 0);
				// }
			} else {
				// SM2单证
				pkcs10Requisition = CryptoAgent2
						.CFCA_PKCS10CertRequisition(
								strSubjectDN, 1, 0);
			}

			if (!pkcs10Requisition) {
				var errorDesc = CryptoAgent2.GetLastErrorDesc();
				alert(errorDesc);
				return;
			}
						//	alert("g176 p10==" + pkcs10Requisition);
			return pkcs10Requisition;
			// ...need to sent pkcs10 requisition to CA
		} catch (e) {
			var LastErrorDesc = CryptoAgent2.GetLastErrorDesc();
			alert(LastErrorDesc);
		}
	}
  
  
  /*修改西安银行手机号码start*/
  // 变更手机表单1验证
  var ctp_update_mobile_form1 = $("#ctp_update_mobile_form1").validate({
      rules: {
      	ctp_m_old_vcode: "required"
      },
      messages: {
      	ctp_m_old_vcode: {
              required: "请输入验证码"
          }
      },
      errorElement: "em",
      errorClass: "sc-error",
      errorPlacement: function(error, element) {
          error.appendTo(element.parent());
      }
  });

  // 改变手机号码-发送号码到旧手机
  $("#ctp_m_send_old_btn").click(function() {
  	$('#ctp_m_send_old_btn').attr("disabled", true);
  	$('#ctp_m_send_old_btn').removeClass("catch_code");
		$('#ctp_m_send_old_btn').addClass("catch_code_gray");
  	$.ajax({
          type: 'POST',
          dataType: 'json',
          url: ctx + '/auth/usercenter/sendmVCodeToCtp',
          data: {"channel": 0,
          	   "option": 2,
          	   "mobile": $("#ctp_mobilenumber").val()},
          success: function(data) {
              if(data.status) {
					$('#ctp_m_send_old_btn').timer({
						format: '已发送(%s)',
						duration: '59s',
						countdown: true,
						callback: function() {
							$('#ctp_m_send_old_btn').timer('remove');
							$('#ctp_m_send_old_btn').attr("disabled", false);
							$('#ctp_m_send_old_btn').text("发送验证码");
							$('#ctp_m_send_old_btn').removeClass("catch_code_gray");
							$('#ctp_m_send_old_btn').addClass("catch_code");
						}
					});
          	} else {
          		 $('#ctp_update_mobile_msg1').timer({
                       format: data.message,
                       duration: '3s',
                       callback: function() {
                           $('#ctp_update_mobile_msg1').timer('remove');
                       }
                   });
					 $('#ctp_m_send_old_btn').attr("disabled", false);
					 $('#ctp_m_send_old_btn').removeClass("catch_code_gray");
					 $('#ctp_m_send_old_btn').addClass("catch_code");
          	}
          }
      });
  });
  // 改变手机号码-下一步
  $("#ctp_m_change_btn_next").click(function() {
  	if(ctp_update_mobile_form1.form()) {
      	$.ajax({
              type: 'POST',
              dataType: "json",
              url: ctx + '/auth/usercenter/verifyForUpdate',
              data: { "channel": 0,
              	    "verifyCode": $("#ctp_m_old_vcode").val()},
              success: function(data) {
              	if (data.status) {
                      $("#ctp_change_mobile_1").hide("slow");
              		$("#ctp_change_mobile_2").show("slow");
              	} else {
              		 $('#ctp_update_mobile_msg1').timer({
                              format: data.message,
                              duration: '3s',
                              callback: function() {
                                  $('#ctp_update_mobile_msg1').timer('remove');
                              }
                       });
              	}
              }
          });
  	}
  });

  // 变更手机表单2验证
  var ctp_update_mobile_form2 = $("#ctp_update_mobile_form2").validate({
      rules: {
      	ctp_new_mobile: {
              required: true,
              pattern: /^1\d{10}$/,
          },
          ctp_m_new_vcode: "required"
      },
      messages: {
      	ctp_new_mobile: {
              required: "请输入手机号码",
              pattern: "请输入正确的手机号码",
          },
          ctp_m_new_vcode: {
              required: "请输入验证码"
          }
      },
      errorElement: "em",
      errorClass: "sc-error",
      errorPlacement: function(error, element) {
          error.appendTo(element.parent());
      }
  });
  // 给新手机发送验证码
  $("#ctp_m_send_new_btn").click(function() {
  	if(ctp_update_mobile_form2.element($("#ctp_new_mobile"))) {
  		$('#ctp_m_send_new_btn').attr("disabled", true);
      	$('#ctp_m_send_new_btn').removeClass("catch_code");
  		$('#ctp_m_send_new_btn').addClass("catch_code_gray");
          $.ajax({
              type: 'POST',
              dataType: 'json',
              url: ctx + '/member/CtpsendVCode',
              data: {"channel": 0,
              	   "option": 2,
              	   "destination": $("#ctp_new_mobile").val()
              	  },
              success: function(data) {
              	if(data.status) {
						$('#ctp_m_send_new_btn').timer({
	                        format: '已发送(%s)',
	                        duration: '59s',
	                        countdown: true,
	                        callback: function() {
	                            $('#ctp_m_send_new_btn').timer('remove');
	                            $('#ctp_m_send_new_btn').attr("disabled", false);
	                            $('#ctp_m_send_new_btn').text("获取验证码");
								$('#ctp_m_send_new_btn').removeClass("catch_code_gray");
								$('#ctp_m_send_new_btn').addClass("catch_code");
	                        }
	                    });
              	} else {
              		ctp_update_mobile_form2.showErrors({"ctp_new_mobile" : data.message});
              		$('#ctp_m_send_new_btn').attr("disabled", false);
						$('#ctp_m_send_new_btn').removeClass("catch_code_gray");
						$('#ctp_m_send_new_btn').addClass("catch_code");
              	}
              }
          });
  	}
  });
  // 更新手机
  $("#ctp_update_mobile_btn").click(function() {
  	if(ctp_update_mobile_form2.form()) {
  		$('#ctp_update_mobile_btn').attr("disabled", true);
  		$('#ctp_update_mobile_btn').removeClass("blue_btn");
  		$('#ctp_update_mobile_btn').addClass("code_gray");
      	$.ajax({
              type: 'POST',
              dataType: 'json',
              url: ctx + '/auth/cust/reset_mobile.html',
              data: {"chkcode": $("#ctp_m_new_vcode").val(),
                     "newmobile": $("#ctp_new_mobile").val()},
              success: function(data) {
              	$('#ctp_update_mobile_btn').attr("disabled", false);
              	$('#ctp_update_mobile_btn').removeClass("code_gray");
          		$('#ctp_update_mobile_btn').addClass("blue_btn");
              	if(data.code=="s") {
              		if(data.state=="4"){
              			alert(data.msg);
              			window.location.reload();
              		}else{
              			alert(data.msg);
                  		window.location.href = ctx+"/auth/cust/openpay_page.html";
              		}
              	} else {
              		ctp_update_mobile_form2.resetForm();
                      $('#ctp_update_mobile_msg2').timer({
                          format: data.msg,
                          duration: '3s',
                          callback: function() {
                              $('#ctp_update_mobile_msg2').timer('remove');
                          }
                      });
              	}
              },
              error: function() {
  				$('#ctp_update_mobile_btn').attr("disabled", false);
              	$('#ctp_update_mobile_btn').removeClass("code_gray");
          		$('#ctp_update_mobile_btn').addClass("blue_btn");
          		ctp_update_mobile_form2.resetForm();
                  $('#ctp_update_mobile_msg2').timer({
                      format: "系统繁忙，请稍候再试",
                      duration: '3s',
                      callback: function() {
                          $('#ctp_update_mobile_msg2').timer('remove');
                      }
                  });
  			}
          });
  	}
  });        
  /*修改西安银行手机号码end*/
  
  
  $.validator.addMethod("checkBank", function(value, element, param) {
      if ( this.optional( element ) ) {
          return "dependency-mismatch";
      }

      var previous = this.previousValue( element ),
          validator, data;

      if (!this.settings.messages[ element.name ] ) {
          this.settings.messages[ element.name ] = {};
      }
      previous.originalMessage = this.settings.messages[ element.name ].checkBank;
      this.settings.messages[ element.name ].checkBank = previous.message;

      param = typeof param === "string" && { url: param } || param;

      if ( previous.old === value ) {
          return previous.valid;
      }

      previous.old = value;
      validator = this;
      this.startRequest( element );
      data = {};
      data[ element.name ] = value;

      $("#bank_name_div").show();
      $(".bankname").html("正在获取中...");

      $.ajax( $.extend( true, {
          url: param,
          mode: "abort",
          port: "validate" + element.name,
          dataType: "json",
          data: data,
          context: validator.currentForm,
          cache: false,
          success: function( response ) {
              $(".bankname").html('');
              $("#bank_name_div").hide();

              var valid = response.status === true || response.status === "true",
                  errors, message, submitted;

              validator.settings.messages[ element.name ].checkBank = previous.originalMessage;
              if ( valid ) {
                  submitted = validator.formSubmitted;
                  validator.prepareElement( element );
                  validator.formSubmitted = submitted;
                  validator.successList.push( element );
                  delete validator.invalid[ element.name ];
                  validator.showErrors();

                  $("#bank_name_div").show();
                  $(".bankname").html(response.body);
              } else {
                  errors = {};
                  message = response.message || validator.defaultMessage( element, "checkBank" );
                  errors[ element.name ] = previous.message = $.isFunction( message ) ? message( value ) : message;
                  validator.invalid[ element.name ] = true;
                  validator.showErrors( errors );
              }
              previous.valid = valid;
              validator.stopRequest( element, valid );
          }
      }, param ) );
      return "pending";
  }, '暂不支持该卡，请更换！');

  $.validator.addMethod("requiredX", function(value, element, param) {
      if ( !this.depend( param, element ) ) {
          return "dependency-mismatch";
      }
      var previous = this.previousValue( element );

      if ( element.nodeName.toLowerCase() === "select" ) {
          var val = $( element ).val();
          return val && val.length > 0;
      }
      if ( this.checkable( element ) ) {
          return this.getLength( value, element ) > 0;
      }

      //return $.trim( value ).length > 0;
      if ($.trim( value ).length > 0) {
          return true;
      } else {
          $(".bankname").html('');
          $("#bank_name_div").hide();
          previous.old = value;
          return false;
      }
  }, "请输入您名下银行卡的卡号");

  $.validator.addMethod("patternX", function(value, element, param) {
      var previous = this.previousValue( element );

      if (this.optional(element)) {
          return true;
      }
      if (typeof param === "string") {
          param = new RegExp("^(?:" + param + ")$");
      }

      //return param.test(value);
      if (param.test(value)) {
          return true;
      } else {
          $(".bankname").html('');
          $("#bank_name_div").hide();
          previous.old = value;
          return false;
      }
  }, "请输入正确的银行卡号");
  
  
// 绑定银行卡表单验证
  var update_card_form2 = $("#update_card_form2").validate({
      rules: {
    	  ACCT_NO: {
               requiredX: true,
               patternX: /^\d{16,19}$/,
               checkBank: ctx + '/auth/cust/bank_certify.html'
           }
      },      
      errorElement: "em",
      errorClass: "sc-error",
      errorPlacement: function(error, element) {
          error.appendTo(element.parent());
      }
  });
  
  
  
    
});

function clearError(pgeId) {
	$('#' + pgeId + "_str").parent().find('em.sc-error').remove();
}



function validPwd(pge) {
    var pgeId = pge.settings.pgeId;
	$('#' + pgeId + "_str").parent().find('em.sc-error').remove();
    if (pge.pwdLength() == 0) {
        var error = $('<em>');
        error.text('请输入6位数字密码');
        error.addClass("sc-error");
        error.appendTo($('#' + pgeId + "_str").parent());
        return false;
    }
    if (pge.pwdValid() == 1) {
        var error = $('<em>');
        error.text('密码不符合要求，请输入6位数字密码');
        error.addClass("sc-error");
        error.appendTo($('#' + pgeId + "_str").parent());
        return false;
    }
    return true;
}



function deleteCorpCard(){
	if(validPwd(pgeditorOld)){
	    if(confirm("解绑银行卡后暂时无法完成充值、提现操作,是否确认解绑?")){
	    	$("#delete_sub").attr("disabled", true);
	    	$('#delete_sub').addClass("code_gray");
 			$('#delete_sub').removeClass("blue_btn");
	    	$.ajax({
				type: 'post',
				dataType: 'json',
				url: ctx + '/auth/xabank/getrk',
				success: function(data) {
					if (data.status == true) {
	    				$.ajax({
	    	    			type:'post',
	    	    			url:ctx+'/auth/cust/del_card.html',
	    	    			data: { 		    		            	
	    		            	"transpwd": function() {
	    		            		    pgeditorOld.pwdSetSk(data.body.rcode);
	    		            		var pwd = pgeditorOld.pwdSmResult(data.body.kx, data.body.ky);
	    		            		return encodeURIComponent(pwd);
	    		            		}()
	    		            	},
	    	    			dataType:'json',
	    	    			success:function(data){
	    	 					if (data.code=='s') {
	    	 						alert("您已成功解绑银行卡,为了您更便捷的体验,请您立即绑定新卡！");
	    	 						window.location.reload();
	    	 						$('#delete_sub').addClass("blue_btn");
	    			 			    $('#delete_sub').removeClass("code_gray");
	    						}else {
	    							$('#delete_sub').attr("disabled", false);
	    							$('#delete_sub').addClass("blue_btn");
		    			 			$('#delete_sub').removeClass("code_gray");
		    			 			pgeditorOld.pwdclear();
									alert(data.msg);
								}				   				
	    	    			},
	    	    			error: function() {
	    	    				pgeditorOld.pwdclear();
		    					$('#delete_sub').attr("disabled", false);
		    					$('#delete_sub').addClass("blue_btn");
		    					$('#delete_sub').removeClass("code_gray");
	    	    				alert("系统繁忙，请稍候再试");
	    	    			}
	    	    		});
					} else {
						pgeditorOld.pwdclear();
						$('#delete_sub').attr("disabled", false);
						$('#delete_sub').addClass("blue_btn");
						$('#delete_sub').removeClass("code_gray");
						alert(data.msg);						
					}
				},
				error: function() {
					pgeditorOld.pwdclear();
					$('#delete_sub').attr("disabled", false);
					$('#delete_sub').addClass("blue_btn");
	 			    $('#delete_sub').removeClass("code_gray");
					alert("系统繁忙，请稍候再试");
				}
			});
		}
	}
}


//绑定银行卡
	
function ctp_bind_xabank(){
	if(validPwd(pgeditorBind)&&update_card_form2.form()){
			$('#bind_sub').attr("disabled",true);
			$('#bind_sub').addClass("code_gray");
			$('#bind_sub').removeClass("blue_btn");
			$.ajax({
			type: 'post',
			dataType: 'json',
			url: ctx + '/auth/xabank/getrk',
			success: function(data) {
				if (data.status == true) {
    				$.ajax({
    					type:'post',
    					url:ctx+'/auth/cust/add_card.html',
    	    			data: { 	
    	    				"acctno": $("#ACCT_NO").val(),
    		            	"transpwd": function() {
    		            		pgeditorBind.pwdSetSk(data.body.rcode);
    		            		var pwd = pgeditorBind.pwdSmResult(data.body.kx, data.body.ky);
    		            		return encodeURIComponent(pwd);
    		            		}()
    		            	},
    	    			dataType:'json',
    	    			success:function(data){
    	    				if(data.code == "s"){
    	    					if(data.isactive == '0'){
    	    						window.location.href = ctx+'/auth/cust/openpay_page.html';
    	    					}
    	    				}else {
    							$('#bind_sub').attr("disabled", false);
    							$('#bind_sub').addClass("blue_btn");
	    			 			$('#bind_sub').removeClass("code_gray");
    							pgeditorBind.pwdclear();
								alert(data.msg);
							}				   				
    	    			},
    	    			error: function() {
    	    				pgeditorBind.pwdclear();
	    					$('#bind_sub').attr("disabled", false);
	    					$('#bind_sub').addClass("blue_btn");
    			 			$('#bind_sub').removeClass("code_gray");
    	    				alert("系统繁忙，请稍候再试");
    	    			}
    	    		});
				} else {
					pgeditorBind.pwdclear();
					$('#bind_sub').attr("disabled", false);
					$('#bind_sub').addClass("blue_btn");
		 			$('#bind_sub').removeClass("code_gray");
					alert(data.msg);						
				}
			},
			error: function() {
				pgeditorBind.pwdclear();
				$('#bind_sub').attr("disabled", false);
				$('#bind_sub').addClass("blue_btn");
	 			$('#bind_sub').removeClass("code_gray");
				alert("系统繁忙，请稍候再试");
			}
	   });
	}
}


