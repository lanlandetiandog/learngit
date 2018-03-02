$(document).ready(function(){
    $("#usermenu_tz").addClass("user_menulist_href_hover");
    $("#menu_jkd").addClass("leftmenu_cur");
	
	var dsbj = Number($("#dsbj").val());//转让本金
	var vzrj = Number($("#txtzrj").val());//折让金
	
	var vfeeamtkind = $("#hidfeeamtkind").val();
	var vfeevalue = Number($("#hidfeevalue").val());
	
	fn_ComputeServiceFee();
	
	//服务费
	var vServiceFee = 0;
	if(vfeeamtkind=="1")
	{
		vServiceFee = vfeevalue;
	}
	if(vfeeamtkind=="2")
	{
		vServiceFee = dsbj * vfeevalue * 0.01;
		vServiceFee = vServiceFee.toFixed(2);
	}
	
	var lp = (dsbj - vzrj - vServiceFee);
	
	$("#lastprice").html(lp);
	
});

//给输入框注册事件
/*
var element = document.getElementById("txtzrj");
if(document.all) {
	alert("IE");
	element.onpropertychange = fn_ComputeTransferPrice;
}else{
	alert("Other");
	element.addEventListener("input",fn_ComputeTransferPrice,false);
}
*/

    //js

var maxNum=999;//最大库存
var minNum=1;//最小库存
     
function decreaseCarNum(inputid){
   
    var val=$("#"+inputid).val()*1;
    if(val>minNum){
            $("#"+inputid).val(val-1);
    }
    if(val<=minNum){
            $("#minus").addClass("disabled");
    }

}

//添加数量
function addCarNum(inputid){
        var val=$("#"+inputid).val()*1;
        if(val<maxNum){
            $("#"+inputid).val(val+1); 
            $("#minus").removeClass("disabled");
        }
        if(val>maxNum){
            $("#plus").addClass("disabled");
    }
}

function fn_ComputeTransferPrice()
{
	var reg1 = /^[0-9]{1,8}$/;
	var vzrj = Number($("#txtzrj").val());//折让金
	
	var vzdzrj = Number($("#hidzdzrj").val());//最大折让金
	var vbj = Number($("#hidconttotalamt").val());//原始投资
//	var vzrjg = Number($("#hidconttotalamt").val());//
	var dsbj = Number($("#dsbj").val());//转让本金
	
	if( !reg1.exec(vzrj))
	{
		alert('输入金额格式不正确！例如：100');
		return;
	}
	 
	if(vzrj>vzdzrj)
	{
		 alert('折让金不能大于最大允许折让金：' + $("#hidzdzrj").val());
		 return;
	}
	
	//转让价格
//	vzrjg = vbj + vzdzrj - vzrj;
	var vzrjgs = dsbj - vzrj;//转让价格
	 
	$("#spanTransferPrice").html(vzrjgs);
	
	fn_ComputeServiceFee();
	
	//服务费
	var vfeeamtkind = $("#hidfeeamtkind").val();
	var vfeevalue = Number($("#hidfeevalue").val());
	var vServiceFee = 0;
	if(vfeeamtkind=="1")
	{
		vServiceFee = vfeevalue;
	}
	if(vfeeamtkind=="2")
	{
		vServiceFee = dsbj * vfeevalue * 0.01;
		vServiceFee = vServiceFee.toFixed(2);
	}

	var lp = (vzrjgs - vServiceFee);
	
	$("#lastprice").html(lp);
	
	if(vzrjgs<0)
	{
		alert('转让金额不正确！');
		return;
	}
}

function fn_ComputeServiceFee()
{
	//var vzrjg = Number($("#spanTransferPrice").html());
	var dsbj = Number($("#dsbj").val());//转让本金
	//服务费
	var vfeeamtkind = $("#hidfeeamtkind").val();
	var vfeevalue = Number($("#hidfeevalue").val());
	var vServiceFee = 0;
	if(vfeeamtkind=="1")
	{
		vServiceFee = vfeevalue;
	}
	if(vfeeamtkind=="2")
	{
		vServiceFee = dsbj * vfeevalue * 0.01;
		var s = vServiceFee.toFixed(2);
		$("#spanservicefee").html(s);
	}
}

//提交转让申请

function fn_SubmitCreditTransfer()
{
	var reg1 = /^[0-9]{1,8}$/;
	var vzrj = Number($("#txtzrj").val());//折让金
	var vzdzrj = Number($("#hidzdzrj").val());//最大折让金
//	var vbj = Number($("#hidconttotalamt").val());//本金
//	var vzrjg = Number($("#hidconttotalamt").val());//转让价格
	var dsbj = Number($("#dsbj").val());//转让本金
	
	if( !reg1.exec(vzrj))
	{
		alert('输入金额格式不正确！例如：100');
		return;
	}
	 
	if(vzrj>vzdzrj)
	{
		 alert('折让金不能大于最大允许折让金：' + $("#hidzdzrj").val());
		 return;
	}
	 
	//转让价格
//	vzrjg = vbj + vzdzrj - vzrj;
	var vzrjgs = dsbj - vzrj;//转让价格
	 
	$("#spanTransferPrice").html(vzrjgs);
	//服务费
	fn_ComputeServiceFee();
	if(vzrjgs<0)
	{
		alert('转让金额不正确！');
		return;
	}
	
	if(!confirm("确定提交转让申请？"))
	{
		return;	
	}

	var loanid = $("#loanid").attr("value");
	var conttype = '2';//2:债权转让协议
	showContAgreement(loanid,conttype);
	
/*	var contno = $("#contno").val();
	var transfermoney = vzrjg;
	var fairvalue = $("#hidfairvalue").val();
	var creditormoney = $("#hidconttotalamt").val();
	 $.ajax({
		 url:ctx+'/auth/usercenter/credit_transfer_submit.html',// 跳转到 action    
		    data:{    
		 		contno : contno,   
		    	creditormoney :creditormoney,
		    	transfermoney : transfermoney,
		    	fairvalue : fairvalue
		    },    
		    type:'post',    
		    cache:false,    
		    dataType:'json',    
		    success:function(data) {  
		        if(data.status == true ){    
		            alert(data.message);    
		            window.location.href='my_invest_page.html';    
		        }else{
		            alert(data.message);    
		        }    
		     },    
		     error : function() {    
		          alert("异常！");    
		     }
	 });*/
}

/**
 * 同意协议书
 */
function contConfimOk(){
	var reg1 = /^[0-9]{1,8}$/;
	var vzrj = Number($("#txtzrj").val());//折让金
	var vzrjg = Number($("#dsbj").val());//待收本金
	
	if( !reg1.exec(vzrj))
	{
		alert('输入金额格式不正确！例如：100');
		return;
	}	 
	//转让价格
	var vzrjgsub = vzrjg  - vzrj;
	$("#spanTransferPrice").html(vzrjgsub);
	//服务费
	fn_ComputeServiceFee();
	if(vzrjgsub<0)
	{
		alert('转让金额不正确！');
		return;
	}
	var dslx = Number($("#dslx").val());//待收利息
	
	var contno = $("#contno").val();
	var transfermoney = vzrjgsub;
	var collectmoney= dslx;//待收利息
	
	var fairvalue = $("#hidfairvalue").val();
	var creditormoney = $("#hidconttotalamt").val();
	
//	alert(transfermoney);
//	return;
	 $.ajax({
		 url:ctx+'/auth/usercenter/credit_transfer_submit.html',// 跳转到 action    
		    data:{    
		 		contno : contno,   
		    	creditormoney :creditormoney,
		    	transfermoney : transfermoney,
		    	fairvalue : fairvalue,
		    	collectmoney : collectmoney
		    },    
		    type:'post',    
		    cache:false,    
		    dataType:'json',    
		    success:function(data) {  
		        if(data.status == true ){    
		            alert(data.message);    
		            window.location.href='my_invest_page.html';    
		        }else{
		            alert(data.message);    
		        }    
		     },    
		     error : function() {    
		          alert("异常！");    
		     }
	 });
}
