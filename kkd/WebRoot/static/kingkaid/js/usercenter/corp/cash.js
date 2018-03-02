var cash_form;
$(document).ready(function(){
	$("#usermenu_zc").addClass("user_menulist_href_hover");

    //对页面内下拉选框执行chosen插件。
    $(".cash_chosen_select").chosen({width: "270px",disable_search_threshold: 10});
    
    $("input[name='cashchl']").change(function(){
    	cashServFee($(this).val(),'',$("#amount").val());
    	var val = $(this).val();
    	if(val == 'GENERAL'){
    		$("#servfee1").show();
    		$("#servfee2").hide();
    	}else{
    		$("#servfee1").hide();
    		$("#servfee2").show();
    	}
    });
    
    $.validator.addMethod("drange", function(value, element, param) {
    	cashServFee($("input[name='cashchl']:checked").val(),'',$("#amount").val());
		return this.optional(element) || (value >= param[0] && value <= $(param[1]).val());
	}, '您输入金开币金额不正确');
    
    $("#cardId").chosen().change(function() {
    	cash_form.element($("#cardId"));
    });

	// Form表单验证
	cash_form = $("#form").validate({
        rules: {
        	amount: {
        		required: true,
        		number:true,
        		pattern: /^\d+(\.\d{1,2})?$/,
        		range:[0.01,$("#balance").val()]
            },
            cardId : {
            	required : true
            },
            coinamt : {
            	required: "#usecoinamt:checked",
            	number:true,
            	drange:[0,"#maxusecoin"]
            },
        },
        messages: {
        	amount: {
        		required: '提现金额不能为空',
        		number:'提现金额不正确',
        		pattern:'提现金额不正确',
        		range:'提现金额必须大于等于0.01且小于等于'+$("#balance").val()
        	},
        	cardId : {
        		required : '请选择提现银行卡'
        	},
        	coinamt : {
        		required:'金开币金额不能为空',
        		number:'您输入的金开币金额不正确'
        	}
        },
        errorElement: "em",
        errorPlacement: function(error,element) {
        	error.appendTo(element.parent());
        }
    });
    
    $("#usecoinamt").click(function(){
    	$("#coinamt").attr("disabled", !this.checked);
    	if(!this.checked) {
    		$("#coinamt").val("");
    		$("#coinamt-error").remove();
    		$("#coinamt").val(0);
    		cashServFee($("input[name='cashchl']:checked").val(),'',$("#amount").val());
    	}
    });
    
    $("#amount").blur(function(){
    	cashServFee($("input[name='cashchl']:checked").val(),'',$(this).val());
    });

    $("#raisinte").change(function(){
    	var optionvalue = $(this).val();
    	if(optionvalue == ''){
    		$("#voucherno").val("");
        	$("#voucheramt").val(0);
        	
        	cashServFee($("input[name='cashchl']:checked").val(),'',$("#amount").val());
    		return;
    	}
    	
    	var val = optionvalue.split(",")[0];
    	var voucheramt = optionvalue.split(",")[1];
    	
    	$("#voucherno").val(val);
    	$("#voucheramt").val(voucheramt);
    	
    	if($("#servfee").val() == "0"){
    		return;
    	}
    	
    	updateAmt();
    });
    
    $("#coinamt").blur(function(){
    	//cashServFee($("input[name='cashchl']:checked").val(),'',$("#amount").val());
    });

});

/**
 * 计算提现服务费
 */
function cashServFee(chanchl,chanid,amt){
	if(chanchl && amt){
		var param = "?chanchl="+chanchl+"&chanid="+chanid+"&amount="+amt;
		$.ajax({
			type:'post',
			url:ctx+"/fee/rule/cash_serv_fee.html"+param,
			async: false,
			success:function(data){
				var json = jQuery.parseJSON(data);
				if(json.status=='s'){
					$("#servfee").val(json.value);
					updateAmt();
				}
			}
		});
	}
}

function updateAmt(){
//	var realcashamt = $("#realcashamt").val();

	//可用余额
	var balance = $("#balance").val();
	//金开币可用余额
	var coinbalance = $("#coinbalance").val();
	
	//服务费
	var servfee = $("#servfee").val();
	//优惠卷金额
	var voucheramt = $("#voucheramt").val();
	
	//提现金额
	var amount = $("#amount").val();
	//金开币金额
	var coinamt = $("#coinamt").val() == '' ? 0 : Math.ceil($("#coinamt").val());
	
	//优惠价金额大于服务费金额
	if(voucheramt - servfee >= 0){
		servfee = 0;
	}else{
		servfee = servfee - voucheramt;
	}
	
	//计算最多可用金开币数量
	if(Math.ceil(servfee) > coinbalance){
		$("#maxusecoin").val(coinbalance);
	}else{
		$("#maxusecoin").val(Math.ceil(servfee));
	}
	
	$("#maxusecoin_show").html($("#maxusecoin").val());
	
	//计算金开币抵用之后服务费金额
	if(servfee > 0 ){
		servfee = servfee - coinamt;
		if(servfee < 0 )
			servfee = 0;
	}
	
	var realcashamt = changeTwoDecimal_f(amount - servfee) ;
	$("#servfee").val(changeTwoDecimal_f(servfee));
	$("#servfee_show").html(changeTwoDecimal_f(servfee));
	
	$("#realcashamt_show").html(realcashamt);
}

function doCash(){
	if(cash_form.form()) {
		cashServFee($("input[name='cashchl']:checked").val(),'',$("#amount").val());
		showRechargeOverBox('银行卡提现','提现完成前请不要关闭该窗口！');
		$('#form').submit();
	}
}

function doCashAppr(){
	if(cash_form.form()) {
		cashServFee($("input[name='cashchl']:checked").val(),'',$("#amount").val());
		$.ajax({
			type:'post',
			url:ctx+'/auth/usercenter/sub_cash_appr.html',
			data:$("#form").serialize(),
			success:function(data){
				var json = jQuery.parseJSON(data);
				if(json.respcode != 'S'){
					showAlertBox(json.respdesc);
				}else{
					showAlertBox("您的提现申请成功,请等待客户经理审核.");
					window.location.reload(true);
				}
			}
		});
	}
}

//申请列表
function apprList() {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var planhtml = "";
    planhtml ='<div class="alert_box_big">'
             +'   <div class="window_top">'
             +'       <div class="window_top_l">我的提现申请</div>'
             +'       <div class="window_top_r">'
             +'       </div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content" style="height:440px;">'
             +'       <table class="return_plan" id="appr_list">'
             +'          <tr>'                                      
             +'               <th style="width:20px;"></th>'
             +'               <th style="width:80px;">申请日期</th>'
             +'               <th style="width:80px;">是否VIP</th>'
             +'               <th style="width:80px;">提现金额</th>'
             +'               <th style="width:80px;">服务费</th>'
             +'               <th style="width:80px;">优惠卷金额</th>'
             +'               <th style="width:80px;">提现渠道</th>'
             +'               <th style="width:80px;">金开币金额</th>'
             +'               <th style="width:80px;">审批状态</th>'
             +'               <th style="width:65px;">提现状态</th>'
             +'               <th style="width:65px;">操作</th>'
             +'           </tr>'
             +'       </table>'
             +'   </div>'
             +'</div>'

        visual.innerHTML = planhtml;
    document.body.appendChild(visual);
    getApprListdata();
}

function getApprListdata(){
	$.getJSON(ctx+"/get_cash_appr_list.html",function(data){
		$.each(data,function(i,item){
			var isvip = "否";
			var chalchl = "普通取现";
			if(data[i].isvip == '1'){
				isvip = "是";
			}
			if(data[i].chalchl == 'FAST'){
				isvip = "即时取现";
			}
			var cashapprstat = "同意提现";
			if(data[i].cashapprstat =='1'){
				cashapprstat = "拒绝提现";
			}
			var cashstat = "已提现";
			if(data[i].cashstat =='2'){
				cashstat = "待提现";
			}
			var html = '	<tr>'                                  
	             +'               <td></th>'
	             +'               <td>'+data[i].apprdate+'</th>'
	             +'               <td>'+isvip+'</th>'
	             +'               <td>'+changeTwoDecimal_f(data[i].appramt)+'</th>'
	             +'               <td>'+changeTwoDecimal_f(data[i].servfee)+'</th>'
	             +'               <td>'+changeTwoDecimal_f(data[i].vecheramt)+'</th>'
	    	     +'               <td>'+chalchl+'</th>'
	             +'               <td>'+changeTwoDecimal_f(data[i].scoreamt)+'</th>'
	             +'               <td>'+cashapprstat+'</th>'
	             +'               <td>'+cashstat+'</th>'
	             +'               <td><a href="#" onclick="doApprCash(\''+data[i].cashid+'\');">马上提现</a></th>'            
	             +'         </tr>';
	        $("#appr_list").append(html);     
		});
	});
}

function doApprCash(cashId){
	closeBox();
	showRechargeOverBox('银行卡提现','提现完成前请不要关闭该窗口！');
	window.open(ctx+'/auth/pay/withdraw_appr?cashId='+cashId,'会员提现',null,false);
}