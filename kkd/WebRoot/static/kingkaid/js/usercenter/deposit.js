$(document).ready(function(){
    $("#usermenu_zc").addClass("user_menulist_href_hover");
    $(".web_qp").hide();
    $(".alipay").hide();
    $(".recharge-type").click(function(){
        var curid=$(this).attr("id")
        $(this).addClass("recharge-type-cur").siblings().removeClass("recharge-type-cur");
        if(curid=="web_bank"){
           $("#quick").val("B2C"); 
           $("#form").attr("action",ctx+"/auth/pay/deposit");
           $("#banklist").show();
           $("#bankid").val("");
        }else if(curid=="alipay") {
        	$("#form").attr("action",ctx+"/auth/pay/alipay/deposit.html");
        	$("#banklist").hide();
        }else if(curid=="web_qp") {
        	$("#quick").val("QP");
        	$("#form").attr("action",ctx+"/auth/pay/deposit");
        	$("#banklist").hide();
        	$("#bankid").val("");
        }
    });
    
    
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
});

function selectbank(bankid){
	$("#bankid").val(bankid);
};

function chinaPnrSubmit(){
	if(chinapnr_form.form()){
		if($("#bankid").val()=='' && $("#quick").val() == 'B2C'){
			alert("请先选择正确的充值银行");
			return;
		}
		showRechargeOverBox('充值','支付完成前请不要关闭该窗口！');
		$('#form').submit();
	}
}