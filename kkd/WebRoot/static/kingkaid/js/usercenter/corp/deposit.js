$(document).ready(function(){
    $("#usermenu_zc").addClass("user_menulist_href_hover");
    // Form表单验证
	chinapnr_form = $("#form1").validate({
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
});
	
function chinaPnrSubmit(){
	if(chinapnr_form.form()){
		showRechargeOverBox('支付宝充值','支付完成前请不要关闭该窗口！');
		$('#form1').submit();
	}
}
