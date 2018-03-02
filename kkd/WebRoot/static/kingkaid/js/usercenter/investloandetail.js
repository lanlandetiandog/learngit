$(document).ready(function(){
	$(".cash_chosen_select").chosen({width: "270px",disable_search_threshold: 10});

    $("#usermenu_tz").addClass("user_menulist_href_hover");
    $("#menu_jkd").addClass("leftmenu_cur");

    
     
  
    //环形进度条加载
    loadCercleData();				
    fn_LoadProgress();
    
    $(".drop_down").click(function(){
        $(this).parent().siblings(".unit_loan_prj_detail").toggle();
        $(this).parent().toggleClass("unit_list_top_down");
    });

    //图片资料轮播
    $('#slider').slider({ speed: 500 });

    //展开查看更多
    $("#more_button").click(function(){
        var cur_btn_text=$(this).text();

        if(cur_btn_text=="展开查看更多"){
            $(".hideTender").show();
            $(this).text("隐藏投标信息");
        }else{
            $(".hideTender").hide();
            $(this).text("展开查看更多");
        }
        
    });
    
    $.validator.addMethod("drange", function(value, element, param) {
		return this.optional(element) || (value >= param[0] && value <= Math.ceil($(param[1]).val()));
	}, '您输入金开币金额不正确');
    
    //检查余额是否足够
    $.validator.addMethod("balchecker", function(value, element, param) {
    	var check = true;
    	//投资金额
    	var amount = ($("#amount").val() == "" || $("#amount").val() == undefined ) ? 0 : $("#amount").val();
    	//金开币金额
    	var coinamt = ($("#coinamt").val() == "" || $("#coinamt").val() == undefined ) ? 0 : $("#coinamt").val();
    	
    	var balance = $("#balance").val();
    	
    	var amt = changeTwoDecimal_f(parseFloat(coinamt)+parseFloat(amount));
    	
    	if(parseFloat(amt) > (parseFloat(balance) + parseFloat(coinamt))){
    		check = false;
    	}
    	
    	if(check){
    		$("#amount-error").remove();
    		$("#coinamt-error").remove();
    	}
    	
		return this.optional(element) || check;
	}, '总投资金额不大于账户余额+金开币使用金额');
    
    //检查投资标准
    $.validator.addMethod("minbidcheck", function(value, element, param) {
    	var check = true;
    	//投资金额
    	var amount = ($("#amount").val() == "" || $("#amount").val() == undefined ) ? 0 : $("#amount").val();
    	//金开币金额
    	var coinamt = ($("#coinamt").val() == "" || $("#coinamt").val() == undefined ) ? 0 : $("#coinamt").val();
    	var amt = parseFloat(amount);
    	
    	//最低投资金额
    	var minbidamt = ($("#minbidamt").val() == "" || $("#minbidamt").val() == undefined ) ? 0 : $("#minbidamt").val();
    	//投资金额倍数
    	var bidamtmult = ($("#bidamtmult").val() == "" || $("#bidamtmult").val() == undefined ) ? 0 : $("#bidamtmult").val();
    	//剩余投资金额
    	var balamt = ($("#balamt").val() =="" || $("#balamt").val() == undefined) ? 0 :$("#balamt").val();
    	
    	if( parseFloat(amt) < parseFloat(minbidamt) && parseFloat(balamt) > parseFloat(minbidamt)){
    		check = false;
    	}
    	
    	if(parseFloat(amt) % parseFloat(bidamtmult) != 0){
    		check = false;
    	}
    	
    	if(check){
    		$("#amount-error").remove();
    		$("#coinamt-error").remove();
    	}
    	
		return this.optional(element) || check;
	}, '总投标金额必须大于等于'+$("#minbidamt").val()+'且为'+$("#bidamtmult").val()+'的倍数');
    
    //投资金额不能大于剩余投资金额
    $.validator.addMethod("countamtcheck", function(value, element, param) {
    	var check = true;
    	//投资金额
    	var amount = ($("#amount").val() == "" || $("#amount").val() == undefined ) ? 0 : $("#amount").val();
    	//金开币金额
    	var coinamt = ($("#coinamt").val() == "" || $("#coinamt").val() == undefined ) ? 0 : $("#coinamt").val();
    	var amt = parseFloat(amount);
    	
    	//最低投资金额
    	var minbidamt = ($("#minbidamt").val() == "" || $("#minbidamt").val() == undefined ) ? 0 : $("#minbidamt").val();
    	//剩余投资金额
    	var balamt = ($("#balamt").val() =="" || $("#balamt").val() == undefined) ? 0 :$("#balamt").val();

    	if(parseFloat(balamt) > parseFloat(minbidamt)){
    		if(parseFloat(amt) > parseFloat(balamt)){
    			check = false;
    		}
    	}
    	
    	if(check){
    		$("#amount-error").remove();
    		$("#coinamt-error").remove();
    	}
    	
		return this.optional(element) || check;
	}, '投资金额必须小于剩余投资金额');

    
    //剩余投资额必须一次投完
    $.validator.addMethod("countamtcheck1", function(value, element, param) {
    	var check = true;
    	//投资金额
    	var amount = ($("#amount").val() == "" || $("#amount").val() == undefined ) ? 0 : $("#amount").val();
    	//金开币金额
    	var coinamt = ($("#coinamt").val() == "" || $("#coinamt").val() == undefined ) ? 0 : $("#coinamt").val();
    	var amt = parseFloat(amount);
    	
    	//最低投资金额
    	var minbidamt = ($("#minbidamt").val() == "" || $("#minbidamt").val() == undefined ) ? 0 : $("#minbidamt").val();
    	//剩余投资金额
    	var balamt = ($("#balamt").val() =="" || $("#balamt").val() == undefined) ? 0 :$("#balamt").val();
    	
    	if(parseFloat(balamt) <= parseFloat(minbidamt)){
    		if(parseFloat(amt) != parseFloat(balamt)){
    			check = false;
    		}
    	}
    	
    	if(check){
    		$("#amount-error").remove();
    		$("#coinamt-error").remove();
    	}
    	
		return this.optional(element) || check;
	}, '剩余投资金额必须一次投完');
    
    // Form表单验证
    var tender_form = $("#form").validate({
        rules: {
        	amount: {
        		required: true,
        		number:true,
        		pattern: /^[1-9]+[0-9]*]*$/,
        		balchecker :[],
        		minbidcheck :[],
        		countamtcheck:[],
        		countamtcheck1:[]
            },
            coinamt : {
            	required: "#usecoinamt:checked",
            	number:true,
            	pattern: /^[1-9]+[0-9]*]*$/,
            	drange:[0,"#maxcoinamt"]
            }
        },
        messages: {
        	amount: {
        		required: '投标金额不能为空',
        		number:'投标金额不正确',
        		pattern: '投资金额必须为正整数'
        	},
        	coinamt : {
        		required:'金开币金额不能为空',
        		number:'您输入的金开币金额不正确',
        		pattern:'金开币金额必须为正整数'
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
    		$("#coinamt-error").remove();
    		$("#coinamt").val("");
    		calTenderAmt();
    	}
    });
    
    $("#tender_btn").click(function(){
    	if(tender_form.form()) {
			showRechargeOverBox('用户投标','请在投标完成前请不要关闭该窗口！');
			$('#form').submit();
		}
    });
    
    $('#amount').bind('input propertychange', function() {
    	var amount = $(this).val();
    	var f_x = parseFloat(amount);
        if (isNaN(f_x)) {
            return false;
        }
        calTenderAmt();
	});
    
    $('#coinamt').bind('input propertychange', function() {
    	var coinamt = $(this).val();
    	var f_x = parseInt(coinamt);
        if (isNaN(f_x)) {
            return false;
        }
    	calTenderAmt();
    });
    
    $("#raisinte").change(function(){
    	var optionvalue = $(this).val();
    	
    	if(optionvalue == ''){
    		$("#inteaddno").val("");
        	$("#inteaddrate").val(0);
    		return;
    	}
    	
    	var val = optionvalue.split(",")[0];
    	var voucheramt = optionvalue.split(",")[1];
    	
    	$("#inteaddno").val(val);
    	$("#inteaddrate").val(voucheramt);
    	
    });
}); 

/**
 * 计算投标总金额(实际支付金额)
 */
function calTenderAmt(){
	var amount = $("#amount").val() == "" ? 0 : $("#amount").val();
	var coinamt = ($("#coinamt").val() == "" || $("#coinamt").val() == undefined) ? 0 : $("#coinamt").val();
	
	var tenderAmt = changeTwoDecimal_f(parseFloat(amount)-parseFloat(coinamt));
	
	$("#tenderamt").val(tenderAmt);
	$("#tenderamt_show").html(tenderAmt);
}

function load_retu_plan(loanid){
	 $.getJSON(ctx+'/project/loan_detail.html?loanid='+loanid,function(dataObj){
		 for(var i = 0; i < dataObj.length; i ++) {
			 	var row = dataObj[i];
	    		var content = '<tr>';
	    		content += '		<td>'+row.sdate+'</td>';
	    		content += '		<td>'+row.sumsi+'</td>';
	    		content += '		<td>'+row.scapi+'</td>';
	    		content += '		<td>'+row.sinte+'</td>';
	    		content += '		<td>'+row.bal+'</td>';
	    		content += '</tr>';
	    		$("#return_plan").append(content);
	    	}
	 });
 }
 

function load_bid_list(loanid){		
	 $.getJSON(ctx+'/project/bid_list.html?loanid='+loanid,function(dataObj){
		 for(var i = 0; i < dataObj.length; i ++) {			
	 
			 	var row = dataObj[i];		 	
	    		var content = '<tr>';
	    		if(i>4) content = '<tr class="hideTender">';
	    		content += '		<td>'+fn_dealmembername(row.membername)+'</td>';
	    		content += '		<td>'+row.fee+'%</td>';
	    		if(row.collectchannel=='3'||row.collectchannel=='4')//1自动，2网页， 3安卓，4IOS，5其他
	    			content += '		<td><span>'+row.conttotalamt+'</span><img src="/static/kingkaid/images/mobile.png" title="手机端投标"></td>';
	    		else
	    			content += '		<td>'+row.conttotalamt+'</td>';
	    		content += '		<td>'+row.conttime+'</td>';
	    		content += '</tr>';
	    		$("#tender_detail").append(content);
	    		
	    	}
	 });
 }

/**
 * 【处理用户名秘密】保留前后各2位，中间用3个*代替
 * @param x
 * @returns
 */
function fn_dealmembername(membername){
	
	var vnamelength = membername.length;
	var vname1 = membername.substring(0,2);
	var vname2 = '***';
	var vname3 = membername.substring(vnamelength-2);
	
	return (vname1 + vname2 + vname3);
}
 
 function counter(){
	 var amount = $("#c_amount").val();
	 var term = $("#c_term").val();
	 var rate = $("#c_rate").val();
	 var repayType = $("#c_repaytype").val();
	 $.getJSON(ctx+'/counterBox?amount='+amount+'&term='+term+'&rate='+rate+'&repayType='+repayType,function(dataObj){
		 $("#counter_return_plan").show();
		 $(".counter_tr").remove();
		 for(var i = 0; i < dataObj.length; i ++) {
			 	var row = dataObj[i];
	    		var content = '<tr class="counter_tr">';
	    		content += '		<td>'+row.term+'</td>';
	    		content += '		<td>'+row.amt+'</td>';
	    		content += '		<td>'+row.capi+'</td>';
	    		content += '		<td>'+row.inte+'</td>';
	    		content += '</tr>';
	    		$("#counter_return_plan").append(content);
	    	}
	 });
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
 
function loadCercleData()
{		 
		$('.cerclebox001').radialIndicator({
		    barColor: '#1b619c',
		    barWidth: 3,
		    initValue: 0,
		    roundCorner: true,
		    percentage: true,
		    radius: 20
		});
}

function fn_LoadProgress()
{
	 $(".cerclebox001").each(function(index, element) {
		    var div=$(this);
		    var divdata = div.attr('data-value');
		    
		    var radObj = div.data('radialIndicator');
		    
		    radObj.animate(divdata);
	 });	 
}		 