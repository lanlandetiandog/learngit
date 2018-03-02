$(document).ready(function(){
	$("#menu_jkd").addClass("leftmenu_cur");
	$("#usermenu_zc").addClass("user_menulist_href_hover");
	
 // Form表单验证
	chinapnr_form = $("#form").validate({
        rules: {
        	amount: {
        		required: true,
        		number:true,
        		pattern: /^\d+(\.\d{1,2})?$/,
        		range:[0.01,10000000]
            }
        },
        messages: {
        	amount: {
        		required: '充值金额不能为空',
        		number:'充值金额不正确',
        		pattern:'充值金额不正确',
        		range:'充值金额必须大于等于0.01且小于等于10,000,000'
        	}
        },
        errorElement: "em",
        errorPlacement: function(error,element) {
        	error.appendTo(element.parent());
        }
    });
	
	

	$('#butsub').click(function() {
		var bad_req_msg = '充值失败!请稍后再试';
		if (chinapnr_form.form() && validatePwd(pgeditor)) {
			showloading("#butsub");
			$.ajax({
				type : 'post',
				url : ctx + '/auth/xabank/acct/createOrder',
				data : {
					transamt : $("#amount").val(),
					acctno : $("#acctno").val()
				},
				dataType: 'json',
				success : function(data) {
					if (data.status) {
						$.ajax({
							type : 'post',
							url : ctx + '/auth/xabank/acct/deposit',
							data : {
								transpwd : function(){
									pgeditor.pwdSetSk(data.body.rcode);
									var pwd = pgeditor.pwdSmResult(data.body.kx, data.body.ky);
									return encodeURIComponent(pwd);
								}()
							},
							dataType: 'json',
							success : function(data) {
								if (data.status) {
									hideloading();
									alert(data.message);
									window.location.reload(true);
								} else {
									pgeditor.pwdclear();
									hideloading();
									alert(data.message);
								}
							},
							error : function() {
								pgeditor.pwdclear();
								hideloading();
								alert(bad_req_msg);
							}
						});
					} else {
						pgeditor.pwdclear();
						hideloading();
						alert(data.message);
					}
				},
				error : function() {
					pgeditor.pwdclear();
					hideloading();
					alert(bad_req_msg);
				}
			});
		}
	});
	
	function validatePwd(pge) {
        var pgeId = pge.settings.pgeId;
        $('#' + pgeId + "_str").find('em.error').remove();
        if (pge.pwdLength() == 0) {
            var error = $('<em>');
            error.text('请输入6位数字密码');
            error.addClass("error");
            error.appendTo($('#' + pgeId + "_str"));
            return false;
        }
        if (pge.pwdValid() == 1) {
            var error = $('<em>');
            error.text('密码不符合要求，请输入6位数字密码');
            error.addClass("error");
            error.appendTo($('#' + pgeId + "_str"));
            return false;
        }
        return true;
	}
	
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
    var spinner = new Spinner(opts).spin(target);
    //return spinner;
  };
  function hideloading() {
    $('#ajax_spin').remove();
    //new Spinner(opts).spin(target)
    //spinner.stop();
  };
