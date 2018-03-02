$(document).ready(function(){
	$("#menu_jkd").addClass("leftmenu_cur");
	$("#usermenu_zc").addClass("user_menulist_href_hover");
	
	if(isIE()) {
		showInputFile(true);
		showFileBtn(false);
	} else {
		showInputFile(false);
		showFileBtn(true);
	}
	
 // Form表单验证
    var chinapnr_form = $("#changeCardForm").validate({
        rules: {
        	ACCT_NO: {
        		requiredX: true,
        		checkBank: ctx + '/auth/cust/bank_certify.html',
                patternX: /^\d{16,19}$/
            },
            reason: {
        		required: true,
                rangelength: [0, 200]
            }
        },
        messages: {
        	reason: {
        		required: '请输入换卡原因',
                rangelength:'请输入不多于200个字'
        	}
        },
        errorElement: "em",
        errorPlacement: function(error,element) {
        	error.appendTo(element.parent());
        }
    });
	
	$("input[type='file']").each(function() {
		$(this).change(function() {
			if(($(this).val() != '' && !isPicture(getInputType($(this).val()))) || ($(this).val() == '' && $(this).attr("id") != 'oldCard')) {
				$('#' + $(this).attr('id') + 'Div').hide();
				$('#' + $(this).attr('id') + 'Prompt').show();
			} else {
				$('#' + $(this).attr('id') + 'Div').show();
				$('#' + $(this).attr('id') + 'Prompt').hide();
			}
		});
	});
	
	$('#butsub').click(function() {
		if (chinapnr_form.form() && allFileSelect()) {
			showloading("#butsub");
			$('#changeCardForm').ajaxSubmit({
				type : 'post',
				url : ctx + '/auth/xabank/acct/changeCard',
				dataType: 'json',
				async: true,
				success : function(data) {
					if (data.status) {
						window.location.href = ctx + "/auth/usercenter/myproperty.html";
					} else {
						hideloading();
						alertPrompts(data.message);
					}
				},
				error : function(XMLHttpRequeset, textStatus, errorThrown) {
					hideloading();
					alert("申请异常，请稍候再试");
				}
			});
		}
	});
	
	function alertPrompts(message) {
		if(message == 'wrongFileFormat') {
			alert('文件格式错误!');
		} else if(message.indexOf('fileOverSize') >= 0) {
			var name = message.substring(message.indexOf('-') + 1);
			alert($('#' + name + 'Span').html() + '大小超过2M');
		} else if(message == '.newCardHasBeenUsed001') {
			alert('银行卡已被使用');
		} else {
			alert('申请异常，请稍候再试');
		}
	}
	
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

                    $(".bankname").html(response.body);
                } else {
                    errors = {};
                    errors[ element.name ] = '请输入正确的银行卡号';
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

        if ($.trim( value ).length > 0) {
            return true;
        } else {
            $(".bankname").html('');
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
            previous.old = value;
            return false;
        }
    }, "请输入正确的银行卡号");
});

function clearError(pgeId) {
    $('#' + pgeId + "_str").find('em.error').remove();
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
  
function selectBtnClick(fileInputName) {
	$('#' + fileInputName).click();
}

function isIE() {
	return navigator.appName == "Microsoft Internet Explorer";
}

function photoSelect(fileInput) {
	var photoImg = document.getElementById(fileInput.id + 'Img');
	var photoDiv = document.getElementById(fileInput.id + 'Div');
	photoImg.style.display = 'block';
	photoImg.style.width = '200px';
	photoImg.style.height = '150px';
	if(!isIE()) {
		var reader = new FileReader();
		reader.onload = function(e) {
			photoImg.src = e.target.result;
		};
		if(fileInput.files && fileInput.files[0]) 
			reader.readAsDataURL(fileInput.files[0]);
	} else {
		//IE下 使用滤镜
		fileInput.select();
		fileInput.blur();
		var imgSrc = document.selection.createRange().text;
		photoImg.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
		photoImg.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
		
	}
	photoDiv.style.paddingLeft = '20px';
	photoDiv.style.paddingTop = '20px';
} 

function isPicture(fileType) {
	fileType = fileType.toLowerCase();
	return fileType == '.jpg' || fileType == '.jpeg' || fileType == '.png' || fileType == '.gif' || fileType == '.bmp';
}

function getInputType(fileName) {
	return fileName.substring(fileName.lastIndexOf("."));
}

function allFileSelect() {
	var result = true;
	$("input[type='file']").each(function() {
		if(($(this).val() != '' && !isPicture(getInputType($(this).val()))) || ($(this).val() == '' && $(this).attr("id") != 'oldCard')) {
			$("#" + $(this).attr('id') + "Prompt").show();
			result = false;
		}
	});
	return result;
}

function showInputFile(state) {
	$("input[type='file']").each(function() {
		state ? $(this).show() : $(this).hide();
	});
}

function showFileBtn(state) {
	$("input[type='file']").each(function() {
		state ? $("#" + $(this).attr('id') + "Select").show() : $("#" + $(this).attr('id') + "Select").hide();
	});
}