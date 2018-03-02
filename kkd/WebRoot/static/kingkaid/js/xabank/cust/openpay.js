$(function(){
	
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
	
	// Form表单验证
    var openpayform = $("#openpayform").validate({   	
        rules: { 
        	ID_NAME: {
        		required: true,
        		pattern:/^[\u4e00-\u9fa5]{2,100}$/
            },
            ID_NO: {
        		required: true,
        		pattern: /^\d{17}[0-9Xx]$/,
        		remote: {
        			url: ctx + "/member/checkPaperID",
        			type: "POST",
        			data: {
        				paperid: function() {
        					return $("#ID_NO").val();
        				}
        			}
        		}
            },
            ACCT_NO: {
                requiredX: true,
                patternX: /^\d{16,19}$/,
                checkBank: ctx + '/auth/cust/bank_certify.html'
            },
            MOBILE: {
            	required: true,
            	pattern: /^\d+$/
            },
            CHK_CODE: {
                required: true
            },
            agree_box: "required"
        },
        messages: {
        	ID_NAME: {
        		required: '请输入您的真实姓名',
        		pattern:'请输入至少两个汉字'
        	},
        	ID_NO: {
        		required: "请输入您的真实身份证号码",
        		pattern: "请输入正确的身份证号码",
        		remote:'此身份证号码已经被使用'
        	},
            MOBILE: {
            	required: "请输入您的11位手机号码",
            	pattern: "电话号码只能由数字组成"
            },
            CHK_CODE:  {
            	required: "请输入您手机收到的6位数验证码"
            },
            agree_box: "请阅读西安银行资金存管三方协议并同意"
        },
        errorElement: "span",
        errorPlacement: function(error,element) {
        	error.addClass("info1");
        	error.appendTo(element.parent());
        },
        ignore : '.ignore'
    });
     
 // 提交开户
    $("#doSubmit").click(function() {
    	if (openpayform.form()
	    		&& validPwd(pgeditor)
	    		&& isSamePwd(pgeditor, pgeditorCopy)) {	
    	 	$("#Bankname").text($(".bankname").text()+" "+$("#ACCT_NO").val()); 
		    $("#memberphone").text($("#MOBILE").val());
			$(".xa_alert").css("display","block");
			$("#big_bg").css("display","block");
	   // 	hidectr();
            $("#_ocx_password_str").css("visibility","hidden");
            $("#_ocx_password_copy_str").css("visibility","hidden");
			
    	}
    });
    
    $(".xa_btnr").click(function(){
    	$("#dotting").addClass("dotting");
    	$('#xa_btnl').attr("disabled", true);
		$('#xa_btnr').attr("disabled", true);
		$('#xa_btnr').addClass("xa_btnr_gray");
		$('#xa_btnr').removeClass("xa_btnr "); 
    	doSub();
    });
    
    
 // 发送验证码
    $("#achievenum").click(function() {
    	if (openpayform.element($("#ID_NAME"))
    			&& openpayform.element($("#ID_NO"))
    			&& openpayform.element($("#ACCT_NO"))
    			&& openpayform.element($("#MOBILE"))
    			&& validPwd(pgeditor)
    			&& isSamePwd(pgeditor, pgeditorCopy)) {
    		sendmsg();
    	}
    });
    
    $("#ACCT_NO").focus(function(e) {
        openpayform.element(e.target);
    });
    
    function doSub(){
		$.ajax({
			type: 'post',
			dataType: 'json',
			url: ctx + '/auth/xabank/getrk',
			success: function(data) {
				if (data.status == true) {
    				$.ajax({
    	    			type:'post',
    	    			url:ctx+'/auth/cust/openpay.html',
    	    			data:{
    	    				ID_NAME:$("#ID_NAME").val(),
    	    				ID_NO:$("#ID_NO").val(),
    	    				ACCT_NO:$("#ACCT_NO").val(),
    	    				CHK_CODE:$("#CHK_CODE").val(),
    	    				MOBILE:$("#MOBILE").val(),
    	    				TRANS_PWD: function() {
	    	    					pgeditor.pwdSetSk(data.body.rcode);
	    		    				var pwd = pgeditor.pwdSmResult(data.body.kx, data.body.ky);
	    		    				return encodeURIComponent(pwd);
    	    					}()
    	    				},
    	    			dataType:'json',
    	    			success:function(data){
    	    				if(data.status == true){
    	    					window.location.reload(true);
    	    					$(".xa_alert").css("display","none");
    	    			    	$("#big_bg").css("display","none");
    	    					$("#dotting").removeClass("dotting");
    	    		    		$('#xa_btnr').attr("disabled", false);
    	    		    		$('#xa_btnr').removeClass("xa_btnr_gray");
    	    		    		$('#xa_btnr').addClass("xa_btnr");
    	    		    		$('#xa_btnl').attr("disabled", false);
    	    				}else{
    	    					pgeditor.pwdclear();
    	    					pgeditorCopy.pwdclear();
    	    					failedBox(data.message);
    	    					$(".xa_alert").css("display","none");
    	    			    	$("#big_bg").css("display","none");
    	    					$("#dotting").removeClass("dotting");
    	    		    		$('#xa_btnr').attr("disabled", false);
    	    		    		$('#xa_btnr').removeClass("xa_btnr_gray");
    	    		    		$('#xa_btnr').addClass("xa_btnr");
    	    		    		$('#xa_btnl').attr("disabled", false);
    	    				}  				   				
    	    			},
    	    			error: function() {
	    					pgeditor.pwdclear();
	    					pgeditorCopy.pwdclear();
	    					failedBox("系统繁忙，请稍候再试");
	    					$(".xa_alert").css("display","none");
	    			    	$("#big_bg").css("display","none");
    	    				$("#dotting").removeClass("dotting");
	    		    		$('#xa_btnr').attr("disabled", false);
	    		    		$('#xa_btnr').removeClass("xa_btnr_gray");
	    		    		$('#xa_btnr').addClass("xa_btnr");
	    		    		$('#xa_btnl').attr("disabled", false);
    	    			}
    	    		});
				} else {
					pgeditor.pwdclear();
					pgeditorCopy.pwdclear();
					failedBox(data.message);
					$(".xa_alert").css("display","none");
			    	$("#big_bg").css("display","none");
					$("#dotting").removeClass("dotting");
		    		$('#xa_btnr').attr("disabled", false);
		    		$('#xa_btnr').removeClass("xa_btnr_gray");
		    		$('#xa_btnr').addClass("xa_btnr");
		    		$('#xa_btnl').attr("disabled", false);
				}
			},
			error: function() {
				pgeditor.pwdclear();
				pgeditorCopy.pwdclear();
				failedBox("系统繁忙，请稍候再试");
				$(".xa_alert").css("display","none");
		    	$("#big_bg").css("display","none");
				$("#dotting").removeClass("dotting");
	    		$('#xa_btnr').attr("disabled", false);
	    		$('#xa_btnr').removeClass("xa_btnr_gray");
	    		$('#xa_btnr').addClass("xa_btnr");
	    		$('#xa_btnl').attr("disabled", false);
			}
		});
    }

    function sendmsg(){   	
		if($("#MOBILE").val()!=""){	
			$('#achievenum').attr("disabled", true);
	    	$('#achievenum').removeClass("catch_code");
			$('#achievenum').addClass("catch_code_gray");
			$.ajax({
				type:'post',
				url:ctx+'/auth/cust/sendopenmsg.html',
				dataType:'json',
				data:{MOBILE:$("#MOBILE").val()},
				success:function(data){
					if(data.code == "s"){
						$('#achievenum').timer({
							format: '已发送(%s)',
							duration: '59s',
							countdown: true,
							callback: function() {
								$('#achievenum').timer('remove');
								$('#achievenum').attr("disabled", false);
								$('#achievenum').text("获取验证码");
								$('#achievenum').removeClass("catch_code_gray");
								$('#achievenum').addClass("catch_code");
							}
						});
					}else{
					    $('#achievenum').attr("disabled", false);
						$('#achievenum').removeClass("catch_code_gray");
						$('#achievenum').addClass("catch_code");
						alert(data.msg);
					}
				}
			});
		}
    }
    
    function validPwd(pge) {
        var pgeId = pge.settings.pgeId;
        var jPge = $('#' + pgeId + "_str");
        jPge.parent().find('span.info1').remove();
        if (pge.pwdLength() == 0) {
            var error = $('<span>');
            error.text('请输入6位数字作为您在西安银行的交易密码');
            error.addClass("info1");
            error.appendTo(jPge.parent());
            return false;
        }
        if (pge.pwdValid() == 1) {
            var error = $('<span>');
            error.text('密码不符合要求，请输入6位数字密码');
            error.addClass("info1");
            error.appendTo(jPge.parent());
            return false;
        }
        return true;
    }

    function isSamePwd(pge, pgec) {
        var pgeId = pgec.settings.pgeId;
        var jPge = $('#' + pgeId + "_str");
        jPge.parent().find('span.info1').remove();
        pge.pwdSetSk('');
        pgec.pwdSetSk('');
        if (pge.pwdSm4Result() === pgec.pwdSm4Result()) {
            return true;
        } else {
            var error = $('<span>');
            error.text('两次密码输入不一致');
            error.addClass("info1");
            error.appendTo(jPge.parent());
            return false;
        }
    }
    
    function failedBox(msg) {
        create_bg();
        hidectr();
        var visual = document.createElement("div");
        visual.id = "new_dialogue";
        var errorthtml = '<div class="alert_box_small">'
            + '   <div class="window_top">'
            + '       <div class="window_top_l">提示</div>'
            + '       <div class="window_close_btn" onclick="badClose(&quot;new_dialogue&quot;)"><img src="' + ctx + '/static/kingkaid/images/window_close_bt.png"/></div>'
            + '   </div>'
            + '   <div class="window_content">'
            + '       <div class="operate_content">'
            + '             <div class="status_img">'
            + '                 <img src="' + ctx + '/static/kingkaid/images/wrong_bt.png"/>'
            + '             </div>'
            + '             <div class="result_msg">'
            + msg
            + '             </div>'
            + '       </div>'
            + '   </div>'
            + '   <div class="small_window_bottom">'
            + '       <div class="blue_btn" onclick="badClose(&quot;new_dialogue&quot;)">确定</div>'
            + '   </div>'
            + '</div>';
        visual.innerHTML = errorthtml;
        document.body.appendChild(visual);
    }

    if (!pgeditor.checkInstall()) {
        $("#download_ctr").show();
        //$("#ctr_help").show();
    }

    $("#download_ctr").click(function() {
        location.href = pgeditor.getDownHref();
    });
    
});

function badClose(boxid) {
    closeDIV(boxid);
    showctr();
}

function clearError(pgeId) {
	$('#' + pgeId + "_str").find('span.info1').remove();
}

function clearShow(pgeId) {
    $('#' + pgeId + "_str").parent().find('span.info1').remove();
    var error = $('<span>');
    error.text('请输入6位数字作为您在西安银行的交易密码');
    error.addClass("info1");
    error.appendTo($('#' + pgeId + "_str").parent());
}

function hidectr() {
    $("#_ocx_password_str").hide();
    $("#_ocx_password_copy_str").hide();
    $("#fake_pwd").show();
    $("#fake_pwd_copy").show();
}

function showctr() {
    $("#_ocx_password_str").show();
    $("#_ocx_password_copy_str").show();
    $("#fake_pwd").hide();
    $("#fake_pwd_copy").hide();
}
