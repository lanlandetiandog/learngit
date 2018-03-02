 $(document).ready(function(){
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
    		 url : ctx+'/auth/usercenter/my_invest.html?date='+new Date().getTime(),
 	        items_per_page : 10,
 	        handle_data : function(dataObj) {
 		    	$("#poj_tb_list_1").empty();
                if (dataObj.body.result.length > 0) {
                    $('#download_in').show();
                } else {
                    $('#download_in').hide();
                }
 		    	var apprstate = $("#apprstate").attr("value");
 		    	var retukind = $("#retukind").attr("value");//还款方式
 		    	var loadstate = ["06","10","17","18","19","30"];//合同下载按钮的显示判断
 		    	var lookplanstate = ["10","19","30"];//还款计划书按钮显示判断
 		    	var relretukind = ["201","202","203","301","400"];//据还款方式显示债权转让按钮判断
 		    	var prodstate = ["06","10","19"];//根据项目状态显示（预期）期限
 		    	var appdate ='';
 		    	var wd = $("#workdate").val();
 		    	var swd = new Date(wd);
		    	var sn = new Date(swd.getTime()-14*3600*1000);
		    	var s = sn.getFullYear()+'/'+(sn.getMonth()+1)+'/'+sn.getDate();
		    	var snt  = new Date(s);
 		    	for(var i = 0; i < dataObj.body.result.length; i ++) {
 		    		var row = dataObj.body.result[i];
 		    		var content = '<li>';
 		    		content += '		<table class="poj_tb">';
 		    		content += '			<tr>';
 		    		content += '				<td style="width:97px;">';
 		    		if(jQuery.inArray(row.retukind,relretukind)>-1){
 		    		content += '			 		<div class="turn" title="支持债权转让"><img src="/static/kingkaid/images/mobile-iz.png"></img></div>';
 		    			}
 		    		content += '					<div><a class="loan_name" href="'+ctx+'/project/loan_detail_page.html?loanid='+row.loanid+'">'+row.projecttitle+'</a></div>';
// 		    		content += '					<div class="align_left">' + row.projecttitle + '</div>';
 		    		content += '				</td>';
 		    		content += '				<td style="width:80px;">';
 		    		content += '					 <div>' + row.conttotalamt + '</div>';
 		    		content += '				</td>';
 		    		content += '				<td style="width:112px;">';
 		    		content += '					<div>' + row.fee + '%';
 					/*if(row.inteaddrate!=''){
 						content += '+<span class="jkd_money">' + row.inteaddrate + '%</span></div>';
 						}else{
 							content += '</div>';
 						}*/
 					if(row.intadd==""  || row.intadd=='0'){
 						content += '</div>';
					}else{
						content += '+<span class="jkd_money">' + row.intadd + '%</span></div>';
					}
 					content += '				</td>';
 		    		content += '				<td style="width:60px;">';
 		    		if(row.prodid == '100401'){
 		    			if(jQuery.inArray(row.apprstate,prodstate)>-1){
 		    				content += '			<div>'+row.btdate+'天</div>';
 		    			}else{
 		    				content += '			<div>预期'+row.btdate+'天</div>';
 		    			}
 		    		}else{
 		    			content += '					<div>' + row.apptterm + '个月</div>';
 		    		}	    		
 		    		content += '				</td>';
 		    		content += '				<td style="width:95px;">';
 		    		content += '					<div>' + row.profitamt + '</div>';
 		    		content += '				</td>';
 		    		content += '				<td style="width:106px;">';
 		    		if(row.prodid == '100401'){
 		    			if(row.overplusdate<'0')
 		    				content += '			<div>'+0+'天</div>';
 		    			else
 		    			    content += '			<div>' + row.overplusdate + '天</div>';
 		    		}else{
 		    			if(row.overpluscount<'0')
 	 		    			content += '			<div>' + 0+'期</div>';
 	  		    		else
 	  		    			content += '			<div>' + row.overpluscount + '期</div>';
 		    		}
 		    		content += '				</td>';
 		    		content += '				<td style="width:92px;">';
 		    		if(row.apprstate=='19')
 		    			content += '					<div>' + '-'+ '</div>';
  		    		else
 		    		content += '					<div>' + formatDate(row.nextrepadate) + '</div>';
 		    		content += '				</td>';
 		    		content += '				<td style="width:70px;">';
 		    		content += '					<div>' + dataObj.annex.apprstatelabel[i] + '</div>';
 		    		content += '				</td>';
 		    		content += '			</tr>';
 		    		content += '			<tr>';
 		    		content += '				<td colspan="8">';
 		    		content += '					<div class="conpany_info">';
 		    		content += '						<div class="car-loan-icon">' + row.prodname + '</div>';
 		    		//content += '						<img style="float:left" src="'+ctx+'/static/kingkaid/images/anx.jpg">';
 		    		content += '						<div style="float:right">';
 		    		if(jQuery.inArray(row.apprstate,lookplanstate)>-1)
 		    		content += '							<div class="manage-loan-icon invest_right_btn" onclick="showReturnPlanBox(\' '+ row.projecttitle + '\',\'' + row.loanid + '\',\''+ row.contno + '\',\'' + appdate + '\')">回款计划书</div>';
 		    		
 		    		
 		    		if(getOffsetDays(snt.getTime(),new Date(formatDate(row.begindate)).getTime())>30){
 		    			
 		    			if(row.transflag=='1' && jQuery.inArray(row.retukind,relretukind)>-1){
 		    				 
 		    				if(comp(snt.getTime(),new Date(formatDate(row.nextrepadate)).getTime())!=0&&comp(snt.getTime(),(new Date(formatDate(row.nextrepadate)).getTime()-24*3600*1000))!=0&&comp(snt.getTime(),(new Date(formatDate(row.lastrepadate)).getTime()))!=0){
 		    					
 		    					content += '				<div class="car-loan-icon invest_right_btn"  onclick="toCreditHtml(\''+row.contno+'\')">转让债权</div>';
 		    				}
 		    			}
 		    		}
 		    			
 		    		
 		    		if(jQuery.inArray(row.apprstate,loadstate)>-1) 
 		    		content += '							<div class="turn-loan-icon invest_right_btn" onclick="showPromptWin(\''+row.apprstate+ '\',\'' + dataObj.annex.apprstatelabel[i] + '\',\'' + row.loanid+ '\',\'' + row.contno + '\',\'' + row.downloadurl + '\',\'' + row.filename +  '\')">下载合同</div>';
 		    		content += '						</div>';
 		    		content += '					</div>';
 		    		content += '				</td>';
 		    		content += '			</tr>';
 		    		content += '		</table>';
 		    		content += '</li>';
 		    		
 		    		$("#poj_tb_list_1").append(content);
 		    	}
 		    	return true;
 		    },
			    qcon_func : function() {
			    	return {
			    		apprstate : $("#apprstate").attr("value"),
			    		transferstate : $("#transferstate").attr("value")
			    	};
			    }
 		});
    	
    	$("#pager2").simplePagination({
    		 url : ctx+'/auth/usercenter/assign/mycredit_buy_data.html?date='+new Date().getTime(),
    	        items_per_page : 10,
    	        handle_data : function(dataObj) {
    		    	$("#poj_tb_list_2").empty();
                    if (dataObj.body.result.length > 0) {
                        $('#download_ct').show();
                    } else {
                        $('#download_ct').hide();
                    }
    		    	for(var i = 0; i < dataObj.body.result.length; i ++) {
    		    		var row = dataObj.body.result[i];
    		    		var content = '<li>';

    		    		content += '		<table class="poj_tb">';
    		    		content += '		    <tr>';
    	    			content += '		        <td style="width:200px;">';
    	    			content += '			<div><a class="loan_name" href="'+ctx+'/project/loan_detail_page.html?loanid='+row.loanid+'">'+row.projecttitle+'</a></div>';
//    	    			content += '		            <div class="align_left">'+row.projecttitle+'</div>';
        				content += '		        </td>';
        				//content += '		        <td style="width:100px;">';
        				//content += '		            <div class="align_left">'+row.projecttitle+'</div>';
        				//content += '		        </td>';
        				content += '		        <td style="width:100px;">';
        				content += '		            <div>'+row.transfermoney+'元</div>';
        				content += '		        </td>';
        				content += '		        <td style="width:85px;">';
        			//	content += '		            <div>'+row.interate+'%</div>';
        				if(row.intadd=='0' || row.intadd=="") 
     		    		content += '			<div>'+row.interate+'%</div>';
     		    		else
     		    		content += '			<div>'+row.interate+'%+'+row.intadd+'%</div>';
        				content += '		        </td>';
        				content += '		        <td style="width:90px;">';
        				if(row.proid == '100401'){
        					content += '		            <div>'+row.btdate+'天</div>';
        				}else{
        					content += '		            <div>'+row.tterm+'个月</div>';
        				}
        				content += '		        </td>';
        				content += '		        <td style="width:85px;">';
        				content += '		            <div>'+row.profitamt+'</div>';
        				content += '		        </td>';
        				content += '		        <td style="width:80px;">';
        				if(row.proid == '100401'){
        					content += '		            <div>'+row.overdate+'天</div>';
        				}else{
        					if(row.overpluscount<'0')
         		    			content += '					<div>' + 0+'期</div>';
          		    		else
          		    			content += '		            <div>'+row.overpluscount1+'期</div>';
        				}
        				content += '		        </td>';
        				content += '		        <td style="width:102px;">';
        				if(row.apprstate=='19')
     		    			content += '					<div>' + '-'+ '</div>';
      		    		else
        				content += '		            <div>'+formatDate(row.nextrepadate)+'</div>';
        				content += '		        </td>';
        				content += '		        <td style="width:60px;">';
         				if(row.apprstate=='10')
      		    			content += '        <div>还款中</div>';
      		    		if(row.apprstate=='19')
      		    			content += '        <div>已结清</div>'; 
      		    		
        				content += '		        </td>';
        				content += '		    </tr>';
        				content += '		    <tr>';
        				content += '		        <td colspan="8">';
        				content += '		        <div class="conpany_info">';
        				content += '		            <div class="return-icon">转</div>';
        				content += '		            <div style="float:right">';
        				content += '		                <div class="manage-loan-icon invest_right_btn" onclick="showReturnPlanBox(\' '+ row.projecttitle + '\',\'' + row.loanid + '\',\''+ row.bidid + '\',\'' + row.receivedate + '\')">回款计划书</div>';
        				content += '						<div class="turn-loan-icon invest_right_btn" onclick="showPromptWin_(\'10\',\'' + row.loanid + '\',\'' + row.bidid+ '\',\'' + row.downloadurl + '\',\'' + row.filename1 + '\',\'' + row.creditortransferid +  '\')">投资合同下载</div>';
        				content += '						<div class="turn-loan-icon invest_right_btn" onclick="showPromptWin_(\'11\',\'' + row.loanid + '\',\'' + row.bidid+ '\',\'' + row.downloadurl + '\',\'' + row.filename + '\',\'' + row.creditortransferid +  '\')">债权转让合同下载</div>';
//        				content += '		                <div class="turn-loan-icon invest_right_btn" onclick="showClaimantAgreement()">下载合同</div>';
        				content += '		            </div>';
        				content += '		        </div>';
                                    
        				content += '		        </td>';
        				content += '		    </tr>';
        				content += '		</table>';

    		    		content += '</li>';
    		    		
    		    		$("#poj_tb_list_2").append(content);
    		    	}
    		    	return true;
    		    },
			    qcon_func : function() {
			    	return {
			    		apprstate : $("#apprstate").attr("value"),
			    		transferstate : $("#transferstate").attr("value")
			    	};
			    }
    		});
   		$(".search_link").click(function(){
  			var apprstate = $(this).attr("value");		 
  			if('2A'==apprstate){
  				$("#apprstate").attr("value","10");
  				$("#transferstate").attr("value","0");
  			}else if('2B'==apprstate){
  				$("#apprstate").attr("value","10");
  				$("#transferstate").attr("value","1");
  			}else{
  				$("#apprstate").attr("value",apprstate);
  				$("#transferstate").attr("value","");
  			}
//            $(this).addClass("lottery_search_cur").parent().siblings().find(".lottery_search_type").removeClass("lottery_search_cur");
  			var cur_tab_id = $(".myinveat_tab").attr("id");		 
  			if('1'==tabtypeflag){
  				$("#pager").trigger("setPage", 0);	
  			}else if('2'==tabtypeflag){
  				$("#pager2").trigger("setPage", 0);
  			}
  		});

	 $('#download_in').click(function() {
         var form = $("<form>");
         form.attr("style", "display:none");
         form.attr("target", "");
         form.attr("method", "post");
         form.attr("action", "myInvest/downloadMyInvestList");
         var input1 = $("<input>");
         input1.attr("type", "hidden");
         input1.attr("name", "apprstate");
         input1.attr("value", $("#apprstate").attr("value"));
         var input2 = $("<input>");
         input2.attr("type", "hidden");
         input2.attr("name", "transferstate");
         input2.attr("value", $("#transferstate").attr("value"));
         form.append(input1);
         form.append(input2);
         $("body").append(form);
         form.submit();
     });

     $('#download_ct').click(function() {
         var form = $("<form>");
         form.attr("style", "display:none");
         form.attr("target", "");
         form.attr("method", "post");
         form.attr("action", "myInvest/downloadMyCreditTransList");
         var input1 = $("<input>");
         input1.attr("type", "hidden");
         input1.attr("name", "apprstate");
         input1.attr("value", $("#apprstate").attr("value"));
         form.append(input1);
         $("body").append(form);
         form.submit();
     });
});
 
 
 //相隔天数计算
 function getOffsetDays(time1,time2){
	 var offsetTime = Math.abs(time1-time2+8*3600*1000);
	 return Math.floor(offsetTime/(3600*24*1000));
 }
 
 //指定日期前一天
 function getYesterday(date){
	 var sdate = new Date(formatDate(date));
	 var sec = sdate.getTime();
	 var Yestime = sec-1000*3600*24;
//	 var YesDate = new Date();
//	 YesDate.setTime(Yestime);
//	 
//	 var Yesday = YesDate.getFullYear()+'-'+(YesDate.getMonth()+1)+'-'+YesDate.getDate();
	 return Yestime;
 }
  
 //指定日期后一天
 function getNextday(date){
	 var sdate = new Date(formatDate(date));
	 var sec = sdate.getTime();
	 var Nextime = sec+1000*3600*24;
//	 var NextDate = new Date();
//	 NextDate.setTime(Nextime);
//	 
//	 var Nextday = NextDate.getFullYear()+'-'+(NextDate.getMonth()+1)+'-'+NextDate.getDate();
	 return Nextime;
 }
 
 //计算指定日期毫秒数
function Seconds(date){
	 var tempdate = date.substring(0,10);
	 var sdate = new Date(tempdate);
	 var sec = sdate.getTime();
	 return sec;
 }
 
//比较日期
function comp(time1,time2){
	var ctime = Math.abs(time1-time2+8*3600*1000);
	return ctime;
}


 var formatDate = function (dates) {
	 	if(dates == "" || dates == null)
	 		return "-";
	 	var date = dates.substring(0,10);
//		   var date = new Date(dates)
//		    var y = date.getFullYear();  
//		    var m = date.getMonth() + 1;  
//		    m = m < 10 ? '0' + m : m;  
//		    var d = date.getDate();  
//		    d = d < 10 ? ('0' + d) : d;  
//		    return y + '-' + m + '-' + d; 
	 	return date;
	}; 

	//回计划书的下载
	function downloadreturnplan(title,loanid,bidno,receivedate){
		$("#loanid").attr("value",loanid);
		$("#bidno").attr("value",bidno);
		$("#receivedate").attr("value",receivedate);
		$("#ptitle").attr("value",title);
		$("#downloadreturnplans").submit();
	}
	

	/**
	 * 债权转让时判断用户是否已激活
	 */
	function toCreditHtml(contno){
		$.ajax({
	        type: "POST",
	        url: ctx+'/auth/activity/isVerti',
	        dataType: "json",
	        success: function(data) {
	        	var isVerti = data.isVerti;
	        	if(isVerti =='6'||isVerti =='7'){//未激活
	        		alert("您尚未激活银行存管电子账户，请您立即激活。");
	        		window.location = ctx+"/auth/usercenter/myjkd.html";
	        	}else{
	        		window.location = ctx+"/auth/usercenter/my_creditor_transfer_apply.html?contno="+$.trim(contno);
	        	}
	        }
	    });
	}
	
	
/**
 * 客户回款计划
 * @param loanid
 * @param bidno
 */
function showReturnPlanBox(title,loanid,bidno,receivedate) {
	 getReceiveData(loanid,bidno,receivedate);
     create_bg();
     var visual = document.createElement("div");
     visual.id = "new_dialogue";
     var planhtml = "";
     planhtml ='<div class="alert_box_big">'

              +'   <div class="window_top">'
              +'       <div class="window_top_l">'+title+'</div>'
              +'       <div class="window_top_r">'
              +'           <img style="margin-top:16px;cursor:pointer;" id="downloadimg" src="'+ctx+'/static/kingkaid/images/download_btn.png" />'
              +'       </div>'
              +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
              +'   </div>'
              +'   <div class="window_content" style="height:440px;">'
              +'       <table class="return_plan" id="return_plan_div">'
              +'        <tr> '                                                                  
              +'             <th class="plan_tb_first_column">应收本金</th>'
              +'             <th style="width:100px;">应收利息</th>'
              +'               <th style="width:95px;">应收本息</th>'
              +'               <th style="width:100px;">应收加息收益</th>'
              +'               <th style="width:100px;">还款期数</th>'
              +'               <th style="width:93px;">剩余本金</th>'
              +'               <th style="width:125px;">还款日</th>'
              +'               <th>状态</th>'
              +'           </tr>'
              +'       </table>'
              +'   </div>'
              +'</div>'

         visual.innerHTML = planhtml;
     document.body.appendChild(visual);
   //触发
	 $("#downloadimg").click(function(){
  	 	 downloadreturnplan(title,loanid,bidno,receivedate);
  	 });
 }

/**
 * 获取回款计划数据
 * @param loanid
 * @param bidno
 */
function getReceiveData(loanid,bidno,receivedate){
	if(receivedate!=null||receivedate!=''){
		var param = "?loanid="+loanid+"&bidno="+bidno+"&receivedate="+receivedate;
	}else{
		var param = "?loanid="+loanid+"&bidno="+bidno;
	}
	
	$.getJSON(ctx+"/acct/cust_receive_plan.html?date="+new Date().getTime()+param,function(data){
		$.each(data,function(i,item){
			var repaystat = '';
			if(data[i].repaystat == '1'){
				repaystat = '<img src="'+ctx+'/static/kingkaid/images/right_over.png"/>';
			}
			var content = '<tr class="plan_tr">';                                        
			content += '               <td class="plan_tb_first_column">'+changeTwoDecimal_f(data[i].scapi)+'</td>'
			content += '               <td>'+changeTwoDecimal_f(data[i].sinte)+'</td>'
			content += '               <td>'+changeTwoDecimal_f(data[i].summoney)+'</td>'
			content += '               <td>'+changeTwoDecimal_f(data[i].sinteadd)+'</td>'
			content += '               <td><div class="red_text">'+data[i].sterm+'</div></td>'
			content += '               <td>'+changeTwoDecimal_f(data[i].bal)+'</td>'
			content += '               <td>'+data[i].sdate+'</td>'
			content += '               <td>'+repaystat+'</td>'
			content += '           </tr>';
			$("#return_plan_div").append(content);
		});
		 $(".window_content").css("overflow","hidden");
		 $(".window_content").css("overflow-x","hidden");
		 $(".window_content").css("overflow-y","auto");
		 $(".window_content").height(500);
	});
}

/**
 * 投资合同下载
 * @param apprstate       当前业务状态
 * @param apprstatelabel  option中对应状态值
 * @param loanid          业务编号
 * @param contno          合同编号
 * @param downloadurl     合同下载路径
 * @param filename        合同名称
 */
function showPromptWin(apprstate,apprstatelabel,loanid,contno,downloadurl,filename) {
	if(apprstate != null && apprstate != ""){
		    create_bg();
		    var visual = document.createElement("div");
		    visual.id = "new_dialogue";
		    var activehtml = "";
		    
		    activehtml ='<div class="alert_box_big">'

	             +'   <div class="window_top">'
	             +'       <div class="window_top_l"><B>投资合同</B></div>'
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
	             +'       <div class="blue_btn" onclick="openUrl(\''+loanid+ '\',\''+apprstate+'\',\''+contno+'\',\''+downloadurl+'\',\''+filename+'\')">确 认</div>'
	             +'       <div class="gray_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">放 弃</div>'
	             +'     </div>'
	             +'   </div>'
	             +'</div>';

		    visual.innerHTML = activehtml;
		    document.body.appendChild(visual);
		    
		    $.ajax({
		        type: "POST",
		        url: ctx+'/auth/cont/continfo',
		        data: {"cont_type":"10","loanid":loanid},//cont_type:10 (投资合同浏览)
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
function openUrl(loanid,apprstate,contno,downloadurl,fileName){
	var loadstate = ["10","19","30"];//10：还款中，19：已结清，30：已代偿
	if(jQuery.inArray(apprstate,loadstate)>-1){//可以下载合同
		if($("#read_chkbox").attr('checked')){
			$("#filePath").attr("value",downloadurl+loanid+'/'+contno);
			$("#fileName").attr("value",fileName);
			$("#downloadform").submit();
		}else{
			alert("请阅读投资合同并确认已阅读！");
		}
    		
    }else{//只能浏览合同
    	alert("当前业务状态可浏览合同，但无法下载合同！");
    }
}

/**
 * 债权认购列表合同下载
 * 
 * @param cont_type      		 合同类型
 * @param loanid         		 业务编号
 * @param bidid          		 投标编号（合同编号）
 * @param downloadurl    		 合同下载路径
 * @param filename       		 合同名称
 * @param creditortransferid     债权转让编号
 */
function showPromptWin_(cont_type,loanid,bidid,downloadurl,filename,creditortransferid) {
		var contname = "";
		var keyno = "";
		if("10"==cont_type){
			contname="投资合同";
			keyno = loanid;
		}else if("11"==cont_type){
			contname="债权转让合同";
			keyno = creditortransferid;
		}else{
			alert("系统错误，请稍后再试！");
			return;
		}
		    create_bg();
		    var visual = document.createElement("div");
		    visual.id = "new_dialogue";
		    var activehtml = "";
		    
		    activehtml ='<div class="alert_box_big">'

	             +'   <div class="window_top">'
	             +'       <div class="window_top_l"><B>'+contname+'</B></div>'
	             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
	             +'   </div>'
	             +'   <div class="window_content">'
	             +'        <div class="agreement_box">'
	             +'        <div id="pro_textinfo1">'
	             +'   </div>' 
	             +'   </div>'
	             +'   <div class="window_bottom">'
	             +'     <div style="float:right">'
	             +'       <input id="read_chkbox" type="checkbox"/><label class="blue-text" style="margin-right:30px;">我已阅读</label>'
	             +'       <div class="blue_btn" onclick="openUrl_(\''+cont_type+ '\',\''+loanid+'\',\''+bidid+'\',\''+downloadurl+'\',\''+filename+'\',\''+creditortransferid+'\')">确 认</div>'
	             +'       <div class="gray_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">放 弃</div>'
	             +'     </div>'
	             +'   </div>'
	             +'</div>';

		    visual.innerHTML = activehtml;
		    document.body.appendChild(visual);
		    
		    $.ajax({
		        type: "POST",
		        url: ctx+'/auth/cont/continfo',
		        data: {"cont_type":cont_type,"loanid":keyno},//cont_type:10 (投资合同),cont_type:11 (债权转让合同)
		        dataType: "json",
		        success: function(data) {
		        	if(data.respcode && data.respcode == 'S') {//回调成功
		        		var continfo = data.continfo;
		        		$("#pro_textinfo1").empty();
		        		$("#pro_textinfo1").append(continfo);
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
function openUrl_(cont_type,loanid,bidid,downloadurl,fileName,creditortransferid){
	var filePath = "";
	if("10"==cont_type){
		filePath=downloadurl+loanid+'/'+bidid;
	}else if("11"==cont_type&&creditortransferid!=null&&creditortransferid!=""){
		filePath=downloadurl+loanid+'/'+bidid+'/'+creditortransferid;
	}else{
		alert("系统错误，请稍后再试！");
		return;
	}
		if($("#read_chkbox").attr('checked')){
			$("#filePath").attr("value",filePath);
			$("#fileName").attr("value",fileName);
			$("#downloadform").submit();
		}else{
			alert("请阅读合同内容并确认已阅读！");
		}
}
