$(document).ready(function() {
		$("#usermenu_zqzr").addClass("user_menulist_href_hover");
		$("#menu_jkd").addClass("leftmenu_cur");
	
		$.ajax({
	        type: "POST",
	        url: ctx+'/auth/activity/isVerti',
	        dataType: "json",
	        success: function(data) {
	        	var isVerti = data.isVerti;
	        	if(isVerti =='4'||isVerti =='5'){//未认证
	        		alert("您尚未开通银行存管电子账户，请您立即开通。");
	        		window.location = ctx+"/auth/usercenter/myjkd.html";
	        	}
	        }
	    });
		
		$(".search_link").click(
			function() {
				$(this).addClass("cur_invest_search").parent()
						.siblings().find(".search_link").removeClass(
								"cur_invest_search");
		});

   	 $("#pager").simplePagination({
		 url : ctx+'/auth/usercenter/assign/mycredit_assign_data.html',
	        items_per_page : 10,
	        handle_data : function(dataObj) {
		    	$("#poj_tb_list_1").empty();
		    	for(var i = 0; i < dataObj.result.length; i ++) {
		    		var row = dataObj.result[i];
		    		var content = '<li>';

		    		content += '		<table class="poj_tb">';
		    		content += '		    <tr>';
	    			content += '		        <td style="width:200px;">';
	    			content += '		            <div class="align_left">'+row.projecttitle+'</div>';
    				content += '		        </td>';
    				//content += '		        <td style="width:100px;">';
    				//content += '		            <div class="align_left">'+row.projecttitle+'</div>';
    				//content += '		        </td>';
    				content += '		        <td style="width:100px;">';
    				content += '		            <div>'+row.transfermoney+'元</div>';
    				content += '		        </td>';
    				content += '		        <td style="width:90px;">';
    				content += '		            <div>'+row.collectmoney+'元</div>';
    				content += '		        </td>';
    				content += '		        <td style="width:80px;">';
    				content += '		            <div>'+row.overpluscount+'</div>';
    				content += '		        </td>';
    				content += '		        <td style="width:85px;">';
    				if(row.intadd=='0' || row.intadd=="") 
 		    		content += '			<div>'+row.interate+'%</div>';
 		    		else
 		    		content += '			<div>'+row.interate+'%+'+row.intadd+'%</div>';
    				content += '		        </td>';
    				content += '		        <td style="width:102px;">';
    				content += '		            <div>'+row.applydate+'</div>';
    				content += '		        </td>';
    				content += '		        <td style="width:60px;">';
    				content += '		            <div>'+row.transferstatename+'</div>';
    				content += '		        </td>';
    				content += '		    </tr>';
    				content += '		    <tr>';
    				content += '		        <td colspan="9">';
    				content += '		        <div class="conpany_info">';
    				content += '		            <div class="return-icon">转</div>';
    				content += '		            <div style="float:right">';
    				if(row.transferstate=='0')
    				content += '		                <div class="car-loan-icon invest_right_btn" onclick="javascript:fn_CreditTransferCancel(\''+row.creditortransferid+'\')" >取消转让</div>';
    				if(row.transferstate=='1')
 		    		content += '							<div class="turn-loan-icon invest_right_btn" onclick="showPromptWin(\''+row.transferstate+ '\',\'' + row.loanid+ '\',\'' + row.bidid+ '\',\'' + row.creditortransferid + '\',\'' + row.downloadurl + '\',\'' + row.filename +  '\')">下载合同</div>';
//    				content += '		                <div class="turn-loan-icon invest_right_btn">下载合同</div>';
    				content += '		            </div>';
    				content += '		        </div>';
                                
    				content += '		        </td>';
    				content += '		    </tr>';
    				content += '		</table>';

		    		content += '</li>';
		    		
		    		$("#poj_tb_list_1").append(content);
		    	}
		    	return true;
		    },
	 	    qcon_func : function() {
		    	return {
		    	    s : $("#sstate").val()
		    	};
		    }
		});
		
		
});

function fn_SelectSearchType(type, value)
{
	$("#"+type).val(value);
	$("#pager").trigger("setPage", 0);
}

function fn_CreditTransferCancel(ctid)
{

	 if(!confirm("确定取消此债权转让申请？"))
	 {
		 return;
	 }

	 
	 $.ajax({
		 url:ctx+'/auth/usercenter/credit_transfer_cancel.html',// 跳转到 action    
		    data:{    
		    	ctid : ctid   
		    },    
		    type:'post',    
		    cache:false,    
		    dataType:'json',    
		    success:function(data) {  
		        if(data.status == true ){    
		            alert(data.message);    
		            window.location.reload();    
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
 * 债权转让合同下载
 * @param transferstate          	 转让状态
 * @param loanid          			 业务编号
 * @param bidid         			 投标编号(合同编号)
 * @param creditortransferid         债权转让编号
 * @param downloadurl     			 合同下载路径
 * @param filename       			 合同名称
 */
function showPromptWin(transferstate,loanid,bidid,creditortransferid,downloadurl,filename) {
		    create_bg();
		    var visual = document.createElement("div");
		    visual.id = "new_dialogue";
		    var activehtml = "";
		    
		    activehtml ='<div class="alert_box_big">'

	             +'   <div class="window_top">'
	             +'       <div class="window_top_l"><B>债权转让合同</B></div>'
	             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
	             +'   </div>'
	             +'   <div class="window_content">'
	             +'        <div class="agreement_box">'
	             +'        <div id="pro_textinfo">'
	             +'   </div>' 
	             +'   </div>'
	             +'   <div class="window_bottom">'
	             +'     <div style="float:right">'
	             +'       <input id="read_chkbox" type="checkbox"/><label class="blue-text" style="margin-right:30px;">我已阅读</label>'
	             +'       <div class="blue_btn" onclick="openUrl(\''+loanid+ '\',\''+transferstate+'\',\''+bidid+'\',\''+creditortransferid+'\',\''+downloadurl+'\',\''+filename+'\')">确 认</div>'
	             +'       <div class="gray_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">放 弃</div>'
	             +'     </div>'
	             +'   </div>'
	             +'</div>';

		    visual.innerHTML = activehtml;
		    document.body.appendChild(visual);
		    
		    $.ajax({
		        type: "POST",
		        url: ctx+'/auth/cont/continfo',
		        data: {"cont_type":"11","loanid":creditortransferid},//cont_type:11 (债权转让协议浏览)
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
//合同下载
function openUrl(loanid,transferstate,contno,creditortransferid,downloadurl,fileName){
	//transferstate 0：发布，1：成交，2：过期，3：取消
	if(transferstate=='1'){//可以下载合同
		if($("#read_chkbox").attr('checked')){
			$("#filePath").attr("value",downloadurl+loanid+'/'+contno+'/'+creditortransferid);
			$("#fileName").attr("value",fileName);
			$("#downloadform").submit();
		}else{
			alert("请阅读债权转让合同并确认已阅读！");
		}
    		
    }else{//只能浏览合同
    	alert("当前业务状态无法下载合同！");
    }
}
