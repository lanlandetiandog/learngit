$(document).ready(function(){
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

	var bundingForm = $("#bundingForm").validate({
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

	$('#butsub').click(function() {
		if (validPwd(pgeditor) && bundingForm.form()) {
			showloading("#butsub");
			$.ajax({
				type: 'post',
				dataType: 'json',
				url: ctx + '/auth/xabank/getrk',
				success: function(data) {
					if (data.status) {
	    				$.ajax({
	    					type:'post',
	    					url:ctx+'/auth/cust/add_card.html',
	    	    			data: { 	
	    	    				"acctno": $("#ACCT_NO").val(),
	    		            	"transpwd": function() {
		    		            		pgeditor.pwdSetSk(data.body.rcode);
		    		            		var pwd = pgeditor.pwdSmResult(data.body.kx, data.body.ky);
		    		            		return encodeURIComponent(pwd);
	    		            		}()
	    		            	},
	    	    			dataType:'json',
	    	    			success:function(data){
	    	    				if(data.code == "s"){
	    	    					if(data.isactive == '0'){
	    	    						window.location.href = ctx + "/auth/usercenter/myproperty.html";
	    	    					}
	    	    				} else {
	    	    					hideloading();
	    	    					pgeditor.pwdclear();
									alert(data.msg);
								}				   				
	    	    			},
	    	    			error: function() {
	    	    				pgeditor.pwdclear();
	    	    				hideloading();
	    	    				alert("系统繁忙，请稍候再试");
	    	    			}
	    	    		});
					} else {
						pgeditor.pwdclear();
						hideloading();
						alert(data.msg);						
					}
				},
				error: function() {
					pgeditor.pwdclear();
					hideloading();
					alert("系统繁忙，请稍候再试");
				}
		   });
		}
	});
});

function clearError(pgeId) {
    $('#' + pgeId + "_str").find('em.error').remove();
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

function showloading(btn) {
	if (btn == null || btn == undefined || $(btn).length == 0) return;
 	var left = $(btn).offset().left;
	var top = $(btn).offset().top;
 	var width = $(btn).outerWidth();
	var height = $(btn).height();
	var opts = {
		lines: 7, // The number of lines to draw
		length: 0, // The length of each line
		width: 9, // The line thickness
		radius: 8, // The radius of the inner circle
		corners: 1, // Corner roundness (0..1)
		rotate: 0, // The rotation offset
		direction: 1, // 1: clockwise, -1: counterclockwise
		color: '#000', // #rgb or #rrggbb or array of colors
		speed: 1, // Rounds per second
		trail: 81, // Afterglow percentage
		shadow: false, // Whether to render a shadow
		hwaccel: false, // Whether to use hardware acceleration
		className: 'spinner', // The CSS class to assign to the spinner
		zIndex: 2e9, // The z-index (defaults to 2000000000)
		top: '32%', // Top position relative to parent
		left: '50%' // Left position relative to parent
    };
    $('#ajax_spin').remove();
    $('body').append('<div id="ajax_spin" style="position:absolute;background:#FFF;filter:alpha(opacity=30);opacity:0.3"><div id="ajax_spin_inner" style="position:relative;height:50px;"></div></div>');
    $('#ajax_spin').css({
    	'top': top,
    	'left': left,
		'width': width,
		'height': height
    });
    var target = document.getElementById('ajax_spin_inner');
    new Spinner(opts).spin(target);
};

function hideloading() {
	$('#ajax_spin').remove();
};