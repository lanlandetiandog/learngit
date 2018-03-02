$(document).ready(function(){
	var loanid = $("#loanidValue").attr("value");
	var cont_type = $("#cont_type").attr("value");//合同类型
	//获取并加载合同内容
	$.ajax({
        type: "POST",
        url: ctx+'/auth/cont/continfoconfirm',
        data: {"risk_type":"5","cont_type":"1","proxy_type":"6","loanid":loanid},
        dataType: "json",
        success: function(data) {
        	if(data.respcode && data.respcode == 'S') {//回调成功
        		var riskinfo = data.riskinfo;
        		var loancontinfo = data.loancontinfo;
        		var proxycontinfo = data.proxycontinfo;
        		$("#riskinfo").empty();
        		$("#riskinfo").append(riskinfo);
        		$("#loancontinfo").empty();
        		$("#loancontinfo").append(loancontinfo);
        		$("#proxyinfo").empty();
        		$("#proxyinfo").append(proxycontinfo);
        	}else{
        		if(data.message != ''){
        			alert("错误提示："+data.message);
        		}else{
        			alert("系统错误，请稍后再试！");
        		}
        		
        	}
        }
    });
	
});

/**
 * 处理
 * 
 * @param dealtype  1：借款人合同签订，2：担保公司合同签订
 * @param loanid     业务编号
 * @param flag      flag:意见， 1-同意，0-拒绝
 * @return
 */
function ContConfirmAjax(dealtype, loanid, flag){
	//借款人或担保公司拒绝时，无需打开合同签章页面
	if("0"==flag){
		if("1"==dealtype){
			loanContConfirmAjax(loanid,flag);
		}else if("2"==dealtype){
			warrconfirmApp(loanid,flag);
		}
	}else{		
		//打开电子签章控件页面，加盖签章，上传并保存 
		window.open(ctx+'/auth/cont/contseal?dealtype='+dealtype +'&loanid='+loanid+'&flag='+flag);	
	}
}

/**
 * 借款人合同签订
 * 
 * @param loanid
 * @param flag
 * @return
 */
function loanContConfirmAjax(loanid,flag){
	if("0"==flag){
		if(!confirm("确定拒绝？")){
			 return;
		 }
	}
	
	$.ajax({
		 url:ctx+'/auth/usercenter/my_borrow_loan_confirm.html',// 跳转到 action    
		    data:{    
		    	loanid : loanid , 
				flag : flag
		    },    
		    type:'post',    
		    cache:false,    
		    dataType:'json',    
		    success:function(data) {  
		        if(data.status == true ){    
		            alert(data.message);    
		            history.back();    
		        }else{
		            alert(data.message);    
		        }    
		     },    
		     error : function() {    
		          alert("异常！");    
		     }
	 });
}
/**
 * 担保公司合同签订
 * 
 * @param loanid
 * @param flag
 * 
 * @return
 */
function warrconfirmApp(loanid,flag) {

	/**	业务状态，
	flag=1时同意担保warrapprstate=18 “担保公司合同已签订”，
	flag=0时同意担保warrapprstate=27 “拒绝签订合同”
	**/
	var warrapprstate = "";
	if("0"==flag){
		warrapprstate = "27";
		if(!confirm("确定拒绝？")){
			 return;
		 }
	}else if("1"==flag){
		warrapprstate = "18";
	}
	
	$.ajax({
		type: 'POST',
		dataType: "json",
		url: ctx + '/auth/usercenter/warrant/setApprState',
		data: {"loanid": loanid,
			"apprstate": warrapprstate},
			success: function(data) {
				if(data.status) {
					 alert(data.message);    
			         history.back(); 
				} else {
					alert(data.message);
				}
			}
	});
}