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
			var load = new Loading();
			load.init({
				target : this
			});
			load.start();
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
									load.stop();
									alert(data.message);
									window.location.reload(true);
								} else {
									pgeditor.pwdclear();
									load.stop();
									alert(data.message);
								}
							},
							error : function() {
								pgeditor.pwdclear();
								load.stop();
								alert(bad_req_msg);
							}
						});
					} else {
						pgeditor.pwdclear();
						load.stop();
						alert(data.message);
					}
				},
				error : function() {
					pgeditor.pwdclear();
					load.stop();
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
