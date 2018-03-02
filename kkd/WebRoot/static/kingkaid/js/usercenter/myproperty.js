$(document).ready(function(){
	$("#checkReason").tooltip({
		show: null,
		position: {
			my: "left top",
			at: "left bottom"
		},
		open: function( event, ui ) {
			ui.tooltip.animate({ top: ui.tooltip.position().top + 10 }, "fast" );
		}
	});
	 
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
	  
    	 $("#pager").simplePagination({
    		url : ctx+'/auth/usercenter/acctdetail.html?date='+new Date().getTime(),
 	        items_per_page : 8,
 	        handle_data : function(dataObj) {
 		    	$("#acctdetail_table tbody").empty();
 		    	for(var i = 0; i < dataObj.body.result.length; i ++) {
 		    		var row = dataObj.body.result[i];
 		    		var rowContent = '<tr><td>'+row.subjtime+'</td><td>'
 		    		    +row.projtitle+'</td><td>'
 		    		    +(row.amtsign === 'E' || row.amtsign === 'H' ? dataObj.annex.amtsignlabel[i] : dataObj.annex.listtypelabel[i] )+'</td>'
 		    		    +(row.ioflag === 'I' ? '<td style=color:red>':'<td style=color:green>')
 		    		    +(row.ioflag === 'I' ? '+':'-')
 		    		    +row.amount+'</td><td>'
 		    		    +row.subjbal+'</td></tr>';
 		            $("#acctdetail_table tbody").append(rowContent);
 		    	}
 		    	return true;
 		    },
 		    qcon_func : function() {
 		    	return {
 		    		startDate : $("#q_start_date").val(),
 		    	    endDate : $("#q_end_date").val(),
 		    	    chanid : $("#prochanel").val()
 		    	};
 		    }
 		});
    	 
 	    $(".search_code_btn").click(function() {
 	    	$("#pager").trigger("setPage", 0);
 	    });
    	
        $("#usermenu_zc").addClass("user_menulist_href_hover");
        $("#menu_jkd").addClass("leftmenu_cur");
        
        
        $("#delete_cancel").click(function(){
        	$("#deleteCard").css("display","none");
        	$("#propertyinfo").css("display","block");
        });
        
        $("#bind_cancel").click(function(){
        	$("#bindCard").css("display","none");
        	$("#propertyinfo").css("display","block");
        });
});
 
 
 	function getCourseName(key){
 		if(key=='10'){
 			return '会员充值';
 		}else if(key=='11'){
 			return '银行卡提现';
 		}else if(key=='71'){
 			return 'VIP会员费';
 		}else{
 			return '其他';
 		}
 		return '会员其他';
 	}
 	
 	/*function showDeleteCard(){
 		create_bg();
 	     var visual = document.createElement("div");
 	     visual.id = "new_dialogue";
 	     var InfoLookehtml = "";
 	     InfoLookehtml ='<div class="alert_box_small" style="width:400px;margin-left:-230px;">'
 	              +'   <div class="window_top">'
 	              +'       <div class="window_top_l">解绑银行卡</div>'
 	              +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
 	              +'   </div>'
 	              +'   <div class="window_content">'
 	              +'       <div class="operate_content" style="width:auto;">'
 	              +'         <div style="overflow:hidden;margin-bottom:20px;padding:0 0 5px 5px;">'
 	              +'           <div style="float:left;width:400px;"><span style="display:block;width:100px;position:absolute;text-align:right;font-size:16px;">银行卡号：</span><input type="text" value="'+trim($("#bankacno_str").html())+'" class="window_input" style="margin-left:100px;width:240px;height:30px;line-height:30px;font-weight:bold;font-size:16px;color:#669;padding-left:10px;" readonly/></div>'
 	              +'         </div>'
 	              +'         <div style="overflow:hidden;margin-bottom:20px;padding:0 0 5px 5px;">'
 	              +'           <div style="float:left;width:400px;"><span style="display:block;width:100px;position:absolute;text-align:right;font-size:16px;">银行名称：</span><input type="text" value="'+trim($("#bankname_str").html())+'" class="window_input" style="margin-left:100px;width:240px;height:30px;line-height:30px;font-weight:bold;font-size:16px;color:#669;padding-left:10px;" readonly/></div>'
 	              +'         </div>'
 	             +'         <div style="overflow:hidden;margin-bottom:20px;padding:0 0 5px 5px;">'
	              +'           <div style="float:left;width:400px;"><span style="display:block;width:100px;position:absolute;text-align:right;font-size:16px;">交易密码：</span><input type="password" name="transpwd_delcard" id="transpwd_delcard" value="" class="window_input" style="margin-left:100px;width:240px;height:30px;line-height:30px;font-weight:bold;font-size:16px;color:#669;padding-left:10px;"/></div>'
	              +'         </div>'
 	              +'       </div>'
 	              +'   </div>'
 	              +'   <div class="small_window_bottom" style="padding-top:10px;">'
 	              +'       <div class="blue_btn" style="float:left;margin-left:30px;width:260px;" onclick="delCard()">解绑该银行卡</div>'
 	              +'   </div>'
 	              +'</div>';
 	         visual.innerHTML = InfoLookehtml;
 	     document.body.appendChild(visual);
 	}*/
 	function showDeleteCard(){
 		$("#propertyinfo").css("display","none");
 		$("#deleteCard").css("display","block");
 		document.getElementById("deleteno").value=trim($("#bankacno_str").html());
 		document.getElementById("deletename").value=trim($("#bankname_str").html());
 	}
 	
 	
 	function trim(str){
 		return str.replace(/(^\s+)|(\s+$)/g,"");
 	}
 	
 	function delCard(){
 		if(validatePwd(pgeditor)){
 		    if(confirm("解绑银行卡后暂时无法完成充值、提现操作,是否确认解绑?")){
 		    	$("#delete_sub").attr("disabled", true);
 		    	$('#delete_sub').addClass("code_gray");
 	 			$('#delete_sub').removeClass("xa_sub");
 		    	$.ajax({
 					type: 'post',
 					dataType: 'json',
 					url: ctx + '/auth/xabank/getrk',
 					success: function(data) {
 						if (data.status == true) {
 		    				$.ajax({
 		    	    			type:'post',
 		    	    			url:ctx+'/auth/cust/del_card.html',
 		    	    			data: { 		    		            	
 		    		            	"transpwd": function() {
	 		    		            		pgeditor.pwdSetSk(data.body.rcode);
			    		            		var pwd = pgeditor.pwdSmResult(data.body.kx, data.body.ky);
			    		            		return encodeURIComponent(pwd);
 		    		            		}()
 		    		            	},
 		    	    			dataType:'json',
 		    	    			success:function(data){
 		    	 					if (data.code=='s') {
 		    	 						alert("您已成功解绑银行卡,为了您更便捷的体验,请您立即绑定新卡！");
 		    	 						window.location.reload();
 		    	 						$('#delete_sub').addClass("xa_sub");
	    	    			 			$('#delete_sub').removeClass("code_gray");
 		    						}else {
 		    							$('#delete_sub').attr("disabled", false);
 		    							$('#delete_sub').addClass("xa_sub");
	    	    			 			$('#delete_sub').removeClass("code_gray");
 										pgeditor.pwdclear();
 										alert(data.msg);
 									}				   				
 		    	    			},
 		    	    			error: function() {
 			    					pgeditor.pwdclear();
 			    					$('#delete_sub').attr("disabled", false);
 			    					$('#delete_sub').addClass("xa_sub");
    	    			 			$('#delete_sub').removeClass("code_gray");
 		    	    				alert("系统繁忙，请稍候再试");
 		    	    			}
 		    	    		});
 						} else {
 							pgeditor.pwdclear();
 							$('#delete_sub').attr("disabled", false);
 							$('#delete_sub').addClass("xa_sub");
    			 			$('#delete_sub').removeClass("code_gray");
 							alert(data.msg);						
 						}
 					},
 					error: function() {
 						pgeditor.pwdclear();
 						$('#delete_sub').attr("disabled", false);
 						$('#delete_sub').addClass("xa_sub");
			 			$('#delete_sub').removeClass("code_gray");
 						alert("系统繁忙，请稍候再试");
 					}
 				});
 			}
 		}
 	}
 	
 	//绑定银行卡
 	/*function ctp_addbankcard_up() {
 	     create_bg();
 	     var visual = document.createElement("div");
 	     visual.id = "new_dialogue";
 	     var InfoLookehtml = "";
 	     InfoLookehtml ='<div class="alert_box_small" style="width:900px;margin-left:-430px;">'
 	              +'   <div class="window_top">'
 	              +'       <div class="window_top_l">绑定银行卡</div>'
 	              +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
 	              +'   </div>'
 	              +'   <div class="window_content">'
 	              +'       <div class="operate_content" style="width:auto;">'
 	              +'         <div style="overflow:hidden;margin-bottom:20px;padding:0 0 5px 5px;">'
 	              +'           <div style="float:left;width:400px;"><span style="display:block;width:100px;position:absolute;text-align:right;font-size:16px;">银行卡号：</span><input type="text" name="bankcode_up" id="bankcode_up" value="" class="window_input" style="margin-left:100px;width:240px;height:30px;font-weight:bold;font-size:16px;color:#669;padding-left:10px;line-height:30px;" onchange="ctp_cardcertify_up();"/></div>'
 	              +'           <div style="float:left;color:#abc;"><span>输入您要绑定银行卡的卡号</span></div>'
 	              +'         </div>'
 	              +'         <div id="ctp_bank_name_up" style="overflow:hidden;margin-bottom:20px;padding:0 0 5px 5px;display:none;">'
 	              +'           <div style="float:left;width:400px;"><span style="display:block;width:100px;position:absolute;text-align:right;font-size:16px;">银行名称：</span><input type="text" name="bankname_up" id="bankname_up" value="" class="window_input" readonly style="margin-left:100px;width:240px;height:30px;font-weight:bold;font-size:16px;color:#669;padding-left:10px;border:none;line-height:30px;"/></div>'
 	              +'           <div style="float:left;color:#abc;"><span></span></div>'
 	              +'         </div>'
 	              +'         <div style="overflow:hidden;margin-bottom:20px;padding:0 0 5px 5px;">'
 	              +'           <div style="float:left;width:400px;"><span style="display:block;width:100px;position:absolute;text-align:right;font-size:16px;">交易密码：</span><input type="password" name="transpwd" id="transpwd" value="" class="window_input" style="margin-left:100px;width:240px;height:30px;font-weight:bold;font-size:16px;color:#669;padding-left:10px;line-height:30px;"/></div>'
 	              +'           <div style="float:left;color:#abc;"><span>请设置您的资金交易密码 *</span></div>'
 	              +'         </div>'
 	              +'       </div>'
 	              +'   </div>'
 	              +'   <div class="small_window_bottom" style="padding-top:10px;">'
 	              +'       <div id="addcard" class="blue_btn" style="float:left;margin-left:30px;width:260px;" onclick="ctp_openPay_by_xabank()">绑定银行卡</div>'
 	              +'   </div>'
 	              +'</div>';
 	         visual.innerHTML = InfoLookehtml;
 	     document.body.appendChild(visual);
 	}*/
 	
 	//绑定银行卡
 	function ctp_addbankcard_up() {
 		$("#propertyinfo").css("display","none");
 		$("#bindCard").css("display","block");
 	}
 	
 	function ctp_cardcertify_up(){
 		if($("#bankcode_up").val()!=""){
 			$.ajax({
 				type:'post',
 				url:ctx+'/auth/cust/bank_certify.html',
 				data:{ACCT_NO:$("#bankcode_up").val()},
 				dataType:'json',
 				success:function(data){
 					if(data.status === true || data.status === "true"){
 						$("#bankname_up").val(data.body);
 					}else{
 						alert(data.message);
 					}
 				}
 			});
 		}
 	}
 	
 	function ctp_openPay_by_xabank(){
 		var bankcode_up=$("#bankcode_up").val();
 		if(bankcode_up==null || bankcode_up==""){
 			 alert("银行卡号不能为空！");
 			 return false;
 		}
 		var bankname_up=$("#bankname_up").val();
 		if(bankname_up==null || bankname_up==""){
 			 alert("银行名称不能为空！");
 			 return false;
 		}
 		if(validatePwd(pgeditorBind)){
 			$('#bind_sub').attr("disabled",true);
 			$('#bind_sub').addClass("code_gray");
 			$('#bind_sub').removeClass("xa_sub");
 			$.ajax({
				type: 'post',
				dataType: 'json',
				url: ctx + '/auth/xabank/getrk',
				success: function(data) {
					if (data.status == true) {
	    				$.ajax({
	    					type:'post',
	    					url:ctx+'/auth/cust/add_card.html',
	    	    			data: { 	
	    	    				"acctno": $("#bankcode_up").val(),
	    		            	"transpwd": function() {
	    		            		pgeditorBind.pwdSetSk(data.body.rcode);
	    		            		var pwd = pgeditorBind.pwdSmResult(data.body.kx, data.body.ky);
	    		            		return encodeURIComponent(pwd);
	    		            		}()
	    		            	},
	    	    			dataType:'json',
	    	    			success:function(data){
	    	    				if(data.code == "s"){
	    	    					if(data.isactive == '0'){
	    	    						window.location.href = ctx+'/auth/cust/openpay_page.html';
	    	    					}
	    	    				}else {
	    							$('#bind_sub').attr("disabled", false);
	    							$('#bind_sub').addClass("xa_sub");
    	    			 			$('#bind_sub').removeClass("code_gray");
	    							pgeditorBind.pwdclear();
									alert(data.msg);
								}				   				
	    	    			},
	    	    			error: function() {
	    	    				pgeditorBind.pwdclear();
		    					$('#bind_sub').attr("disabled", false);
		    					$('#bind_sub').addClass("xa_sub");
	    			 			$('#bind_sub').removeClass("code_gray");
	    	    				alert("系统繁忙，请稍候再试");
	    	    			}
	    	    		});
					} else {
						pgeditorBind.pwdclear();
						$('#bind_sub').attr("disabled", false);
						$('#bind_sub').addClass("xa_sub");
			 			$('#bind_sub').removeClass("code_gray");
						alert(data.msg);						
					}
				},
				error: function() {
					pgeditorBind.pwdclear();
					$('#bind_sub').attr("disabled", false);
					$('#bind_sub').addClass("xa_sub");
		 			$('#bind_sub').removeClass("code_gray");
					alert("系统繁忙，请稍候再试");
				}
		   });
 		}
 	}
 	
 	
 	
 	function validatePwd(pge) {
        var pgeId = pge.settings.pgeId;
        $('#' + pgeId + "_str").find('em.error').remove();
        if (pge.pwdLength() == 0) {
            var error = $('<em>');
            error.text('请输入6位数字密码');
            error.addClass("error");
            error.appendTo($('#' + pgeId + "_str"));
            return false;
        }
        if (pge.pwdValid() == 1) {
            var error = $('<em>');
            error.text('密码不符合要求，请输入6位数字密码');
            error.addClass("error");
            error.appendTo($('#' + pgeId + "_str"));
            return false;
        }
        return true;
	}
 	
 	
 	function clearError(pgeId) {
 	    $('#' + pgeId + "_str").find('em.error').remove();
 	}
 	
 	function showPopover() {
 		$('').show();
 	}