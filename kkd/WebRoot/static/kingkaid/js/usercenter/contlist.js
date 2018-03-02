//合同浏览部分的公共的js

// 展示确认页面，根据审批状态判断是浏览合同还是下载合同
function showPromptWindow(apprstate,apprstatelabel,loanid,downloadurl,filename,prodid) {	
	if(apprstate != null && apprstate != ""){
		    create_bg();
		    var visual = document.createElement("div");
		    visual.id = "new_dialogue";
		    var activehtml = "";
		    
		    
		  /*  var content = '';
		    var loadstate = ["10","19","30"];//10：还款中，19：已结清，30：已代偿
		    var loadCont = '<a onclick="openUrl(\''+loanid+ '\',\''+downloadurl+'\',\''+filename+'\')" style="margin-left:20px;">合同下载</a>';
		    if(jQuery.inArray(apprstate,loadstate)>-1){//可以下载合同
		    	content = "该笔借款当前的状态为【" + apprstatelabel + "】，可点击【合同浏览】浏览合同，或者点击【合同下载】下载四方合同！";
		    		
		    }else{//只能浏览合同
		    	content = "该笔借款当前的状态为【" + apprstatelabel + "】，可点击【合同浏览】浏览合同，但无法下载合同！";
		    	loadCont = '';
		    }*/
		    if (prodid=='100301') {
		    	 activehtml ='<div class="alert_box_big">'
		             +'   <div class="window_top">'
	 	             +'       <div class="window_top_l">保理权益转让及回购协议</div>'
		             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
		             +'   </div>'
		             +'   <div class="window_content">'
		             +'        <div class="agreement_box">'
		             +'        <div id="pro_textinfo"></div>'
		             +'   	   </div>' 
		             +'   </div>'
		             +'   <div class="window_bottom">'
		             +'     <div style="float:right">'
		             +'       <input id="read_chkbox" type="checkbox"/><label class="blue-text" style="margin-right:30px;">我已阅读</label>'
		             +'       <div class="blue_btn" onclick="openUrl(\''+loanid+ '\',\''+apprstate+'\',\''+downloadurl+'\',\''+filename+'\')">确 认</div>'
		             +'       <div class="gray_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">放 弃</div>'
		             +'     </div>'
		             +'   </div>'
		             +'</div>';
			}else{
				 activehtml ='<div class="alert_box_big">'
		             +'   <div class="window_top">'
	 	             +'       <div class="window_top_l">借款合同</div>'	
		             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
		             +'   </div>'
		             +'   <div class="window_content">'
		             +'        <div class="agreement_box">'
		             +'        <div id="pro_textinfo"></div>'
		             +'   	   </div>' 
		             +'   </div>'
		             +'   <div class="window_bottom">'
		             +'     <div style="float:right">'
		             +'       <input id="read_chkbox" type="checkbox"/><label class="blue-text" style="margin-right:30px;">我已阅读</label>'
		             +'       <div class="blue_btn" onclick="openUrl(\''+loanid+ '\',\''+apprstate+'\',\''+downloadurl+'\',\''+filename+'\')">确 认</div>'
		             +'       <div class="gray_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">放 弃</div>'
		             +'     </div>'
		             +'   </div>'
		             +'</div>';
			}
		   

		    visual.innerHTML = activehtml;
		    document.body.appendChild(visual);
		    
		    $.ajax({
		        type: "POST",
		        url: ctx+'/auth/cont/continfo',
		        data: {"cont_type":"12","loanid":loanid},
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
		    
	}
}
//合同下载
function openUrl(loanid,apprstate,downloadurl,fileName){
	var loadstate = ["10","19","30"];//10：还款中，19：已结清，30：已代偿
	if(jQuery.inArray(apprstate,loadstate)>-1){//可以下载合同
		if($("#read_chkbox").attr('checked')){
			$("#filePath").attr("value",downloadurl+loanid);
			$("#fileName").attr("value",fileName);
			$("#downloadform").submit();
		}else{
			alert("请阅读借款合同并确认已阅读！");
		}
    		
    }else{//只能浏览合同
    	alert("当前业务状态可浏览合同，但无法下载合同！");
    }
}



