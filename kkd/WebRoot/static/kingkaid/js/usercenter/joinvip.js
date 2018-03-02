var vipfee_form ;
$(document).ready(function(){
	
	 $.validator.addMethod("dmaxrange", function(value, element, param) {
			return this.optional(element) || (value >= param[0] && value <= Math.min($(param[1]).val(), $(param[2]).val()));
		}, '您输入金开币金额不正确');
	
	 $.validator.addMethod("countamtcheck", function(value, element, param) {
	    	var check = true;
	    	//支付金额
	    	var amount = $("#amount").val() == "" ? 0 : $("#amount").val();
	    	
	    	//金开币使用金额
	    	var coinamt = $("#coinamt").val() == "" ? 0 : $("#coinamt").val();
	    	
	    	var realamt = changeTwoDecimal_f(amount - coinamt);
	    	if(realamt < 1){
	    		check = false;
	    	}
	    	
			return this.optional(element) || check;
		}, '实际支付金额不能小于1元');
	 
	// Form表单验证
    vipfee_form = $("#form").validate({
        rules: {
        	term: {
        		required: true
            },
            coinamt : {
            	required: "#usecoin:checked",
            	pattern: /^[1-9]+[0-9]*]*$/,
            	number:true,
            	dmaxrange:[0,"#coinbalance", "#amount"],
            	countamtcheck:[],
            }
        },
        messages: {
        	term: {
        		required: 'VIP购买期限不能为空'
        	},
        	coinamt : {
        		required:'金开币金额不能为空',
        		pattern:'金开币金额必须为正整数',
        		number:'您输入的金开币金额不正确',
        		dmaxrange:'您输入的金开币金额不正确'
        	}
        },
        errorElement: "em",
        errorPlacement: function(error,element) {
        	error.appendTo(element.parent());
        }
    });
    
    $("#usecoin").click(function() {
    	$("#coinamt").attr("disabled", !this.checked);
    	if(!this.checked) {
    		$("#coinamt").val("");
    		$("#coinamt-error").remove();
    		setVipFee();
    	}
    });
    
    $("#term").change(function(){
    	if($(this).val() != ''){
    		calVipfee($(this).val(), $("#old_vip_end_date").text());    		
    	}
    });
    
    $('#coinamt').bind('input propertychange', function() {
    	setVipFee();
	});
});

function calVipfee(term, oldDate){
	$.ajax({
        type: 'POST',
        dataType: "json",
        url: ctx + '/fee/rule/cal_vip_fee',
        data: {'term':term,
        	   'oldDate':oldDate},
        success: function(data) {
            if(data.status=='s') {
            	$("#amount").val(data.value);
    			$("#by_vip_end_date").html(data.enddate);
    			setVipFee();
            } else {
            	alert(data.value);
            }
        }
    });
}

function setVipFee(){
	//支付金额
	var amount = $("#amount").val() == "" ? 0 : $("#amount").val();
	
	//金开币使用金额
	var coinamt = $("#coinamt").val() == "" ? 0 : $("#coinamt").val();
	
	var realamt = changeTwoDecimal_f(amount - coinamt);
	if(realamt < 0){
		realamt = 0;
	}
	
	$("#amount_show").html(amount);
	$("#realamt").val(realamt);
	$("#realamt_show").html(realamt);
}

function toPay(){
	if(vipfee_form.form()) {
		showRechargeOverBox('购买VIP特权','支付完成前请不要关闭该窗口！');
		$.ajax({
			type:'post',
			url:ctx+'/auth/xabank/acct/buy_vip.html',
			data:{"amount":$("#amount").val(),"realamt":$("#realamt").val(),"coinamt":$("#coinamt").val(),"term":$("#term").val()},
			dataType:'json',
			success:function(data){
				if(data.code == 's'){
					window.open(data.url,'支付vip费用');	
				}else{
					alert(data.msg);
				}
			},
			error:function(){
				alert('error');
			}
		});
	}
} 

/**
 * 【四舍五入】保留2位小数
 * @param x
 * @returns
 */
function changeTwoDecimal_f(x) {
    var f_x = parseFloat(x);
    if (isNaN(f_x)) {
        alert('请求参数不合法');
        return false;
    }
    var f_x = Math.round(x * 100) / 100;
    var s_x = f_x.toString();
    var pos_decimal = s_x.indexOf('.');
    if (pos_decimal < 0) {
        pos_decimal = s_x.length;
        s_x += '.';
    }
    while (s_x.length <= pos_decimal + 2) {
        s_x += '0';
    }
    return s_x;
}