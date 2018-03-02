$(document).ready(function(){
	var loanid = $("#loanidValue").attr("value");
	
	//获取并加载合同内容
	$.ajax({
        type: "POST",
        url: ctx+'/auth/cont/continfo',
        data: {"cont_type":"1","loanid":loanid},
        dataType: "json",
        success: function(data) {
        	if(data.respcode && data.respcode == 'S') {//回调成功
        		var continfo = data.continfo;
        		$("#pro_textinfo").empty();
        		$("#pro_textinfo").append(continfo);
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