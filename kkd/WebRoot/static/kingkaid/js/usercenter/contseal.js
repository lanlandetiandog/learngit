$(document).ready(function(){
	
	
});

/**
 * 处理
 * 
 * @param dealtype  1：借款人合同签订，2：担保公司合同签订
 * @param loanid     业务编号
 * @return
 */
function ContConfirmContSeal(dealtype, loanid){
		
		if("1"==dealtype){
			loanContConfirmContSeal(loanid);
		}else if("2"==dealtype){
			warrConfirmContSeal(loanid);
		}
}


/**
 * 借款人合同签订
 * 
 * @param loanid
 * @return
 */
function loanContConfirmContSeal(loanid){
	
	$.ajax({
		 url:ctx+'/auth/usercenter/my_borrow_loan_confirm.html',  
		    data:{    
		    	loanid : loanid , 
				flag : '1'
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
function warrConfirmContSeal(loanid) {
	var warrapprstate = "18";	//担保公司合同已签订	
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