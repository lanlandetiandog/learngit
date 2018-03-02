$(document).ready(function(){
	$.ajax({
        type: "POST",
        url: ctx+'/member/continfo',
        dataType: "json",
        success: function(data) {
        	if(data.respcode && data.respcode == 'S') {//回调成功
        		var continfo = data.continfo;
        		$("#pro_textinfo").empty();
        		$("#pro_textinfo").append(continfo);
        	}
        }
    });
	
});