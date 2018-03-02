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
            ACCT_NO: {
                requiredX: true,
                patternX: /^\d{16,19}$/,
                checkBank: ctx + '/auth/cust/bank_certify.html'
            },
            agree_box: "required"
        },
        messages: {
            agree_box: "请阅读西安银行资金存管三方协议并同意"
        },
        errorElement: "span",
        errorPlacement: function(error,element) {
        	error.addClass("info1");
        	error.appendTo(element.parent());
        }
    });
     
    // 提交开户
    $("#doSubmit").click(function() {
    	if (openpayform.form()) {
    		$("#dotting").addClass("dotting");
			$('#doSubmit').attr("disabled", true);
			$('#doSubmit').addClass("code_gray");
			$('#doSubmit').removeClass("buttonstyle");   	
			doSub();
    	}
    });
    
    $("#ACCT_NO").focus(function(e) {
        openpayform.element(e.target);
    });
    // 进入页面后银行卡号输入框获取焦点
    $("#ACCT_NO").focus();
    
    function doSub(){
		$.ajax({
			type: 'post',
			dataType: 'json',
			url: ctx + '/auth/cust/openpay3f',
			data:{
				ACCT_NO:$("#ACCT_NO").val(),
				MOBILE:$("#MOBILE").val()
				},
			success: function(data) {
				if (data.status == true) {
					window.location.href = data.body;
				} else {
					failedBox(data.message);
					$("#dotting").removeClass("dotting");
		    		$('#doSubmit').attr("disabled", false);
		    		$('#doSubmit').removeClass("code_gray");
		    		$('#doSubmit').addClass("buttonstyle");
				}
			},
			error: function() {
				failedBox("系统繁忙，请稍候再试");
				$("#dotting").removeClass("dotting");
	    		$('#doSubmit').attr("disabled", false);
	    		$('#doSubmit').removeClass("code_gray");
	    		$('#doSubmit').addClass("buttonstyle");
			}
		});
    }
    
    function failedBox(msg) {
        create_bg();
        var visual = document.createElement("div");
        visual.id = "new_dialogue";
        var errorthtml = '<div class="alert_box_small">'
            + '   <div class="window_top">'
            + '       <div class="window_top_l">提示</div>'
            + '       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="' + ctx + '/static/kingkaid/images/window_close_bt.png"/></div>'
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
            + '       <div class="blue_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">确定</div>'
            + '   </div>'
            + '</div>';
        visual.innerHTML = errorthtml;
        document.body.appendChild(visual);
    }
    
});
