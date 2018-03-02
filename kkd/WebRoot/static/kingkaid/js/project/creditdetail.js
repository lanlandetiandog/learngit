$(document).ready(function(){
	$(".cash_chosen_select").chosen({width: "270px",disable_search_threshold: 10});

    $("#menu_lc").addClass("leftmenu_cur");
    
    $("#credit_btn").click(function(){
    	var loanid = $("#loanid").attr("value");
    	var conttype = '2';//2:债权转让协议
    	showContAgreement(loanid,conttype);
    });

	countdownTimer();
}); 

function countdownTimer() {
	var rests = Number($('#restSeconds').val()) || 0;	 
	var tsfState = $('#transferState').val() || '0';	
	if (rests === 0) {
		if (tsfState === '1'){
			$('#spanSurplusTime').html("已转让");
		} else if (tsfState === '3'){
			$('#spanSurplusTime').html("已取消");
		} else {
			$('#spanSurplusTime').html("已失效");
		}
		if ($('#credit_btn').length > 0) {
			$('#credit_btn').unbind();
			$('#credit_btn').val("已失效");
			$('#credit_btn').css("background-color", "#666");
		}
	} else {			 
		if (tsfState === '0') {
			$('#spanSurplusTime').timer({
				format: '%d天%H小时%M分%S秒',
				secDuration: rests,
				countdown: true,
				callback: function () {
					$('#spanSurplusTime').timer('remove');
					$('#spanSurplusTime').html("已失效");
					if ($('#credit_btn').length > 0) {
						$('#credit_btn').unbind();
						$('#credit_btn').val("已失效");
						$('#credit_btn').css("background-color", "#666");
					}
				}
			});
		} else if (tsfState === '1'){
			$('#spanSurplusTime').html("已转让");
		} else if (tsfState === '3'){
			$('#spanSurplusTime').html("已取消");
		} else {
			$('#spanSurplusTime').html("已失效");
		}
	}
}

/**
 * 同意协议书
 */
function contConfimOk(){
	showRechargeOverBox('债权申购','请在支付完成前请不要关闭该窗口！');
	$.ajax({
		type:'post',
		url:ctx+'/auth/project/credit_assgin.html', 
		data:{"ctfId":$("#ctfId").val()},
		dataType:'json',
		success:function(data){
			if(data.code == 's'){						
				var content = '';
				content += '<form id="xabanksubmit" name="xabanksubmit" action="'+data.url+'" method="post" target="_blank" display="none">';
				content += '<input type="submit" value="submit" >';
				content +=	'</form>';							
				$("#creditsubmit").append(content);
				document.forms['xabanksubmit'].submit();
				$("#creditsubmit").remove();	
			}else{
				alert(data.msg);
				return false;
			}
		},
		error:function(){
			alert('error');
		}
	});	
}

/**
 * 计算投标总金额
 */
function calTenderAmt(){
	var amount = $("#amount").val() == "" ? 0 : $("#amount").val();
	var coinamt = ($("#coinamt").val() == "" || $("#coinamt").val() == undefined) ? 0 : $("#coinamt").val();
	
	var tenderAmt = changeTwoDecimal_f(parseFloat(amount)+parseFloat(coinamt));
	
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
