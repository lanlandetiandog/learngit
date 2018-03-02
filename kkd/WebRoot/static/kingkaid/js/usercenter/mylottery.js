 $(document).ready(function(){
	    $("#usermenu_jq").addClass("user_menulist_href_hover");
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
       	
  		$("#pager").simplePagination({
  		     	url : ctx+'/auth/activity/my_lottery.html',
  		     	items_per_page : 5,
  		     	handle_data : function(dataObj) {
//  		     	var dataObj = data.body;
  		     		var flag = $("#selectflag").attr("value");
  		     		emptyLottery(flag);
	     			var lotery_unit = '&nbsp;';
	     			var lottery_name = '&nbsp;';
	     			var snd_title = '&nbsp;';
	     			var yxq = '有效期至：';
	     			var buttonstyle = '';
	     			
	     			var html = '';
	     			var exchange = '';
		     		for(var i = 0; i < dataObj.result.length; i ++) {
		     			var row = dataObj.result[i];
		     			var acttype = row.acttype;
		     			var memberid = row.memberid;
		     			yxq = '有效期至：';
		     			var img = '';
		     			var detail = '';
		     			var button = '';
		     			var keyno = row.keyno;
		     			var seqorder_list = row.seqorder_list;
		     			var jqxq_click =  ' onclick=\"getTickDetail(\''+seqorder_list+'\','+ '\''+keyno+'\','+'\''+acttype+'\',' + '\''+memberid+'\');\"';
		     			var button_click = ' onclick=\"ticketUse(\''+seqorder_list+'\','+ '\''+keyno +'\',' + '\'' +acttype+'\');\"'; 
		     			
		     			if(acttype == '9') {
		     				button_click = ' onclick=\"confirmBox(\''+seqorder_list+'\','+ '\''+keyno +'\',' + '\'' +acttype+'\',' + '\'' +row.value+'\');\"';
		     			}
		     			
		     			if(acttype == '1'){//金开币
	     					lotery_unit = 'lotery_unit jkb_lot';
	     					lottery_name = '金开币';
	     					snd_title = row.value+'元';
	     					yxq += formatDate(row.effectenddate);
	     					buttonstyle = ' style="color:#ea9663;"';
	     				}else if(acttype == '2'){//加息券
	     					lotery_unit = 'lotery_unit jiaxi';
	     					lottery_name = '加息券';
	     					snd_title = row.value+'%';
	     					yxq += formatDate(row.effectenddate);
	     					buttonstyle = ' style="color:#ea6963;"';
	     				}else if(acttype == '3'){//会员升级
	     					lotery_unit = 'lotery_unit menber';
	     					lottery_name = '会员升级';
	     					snd_title = row.value+'个月';
	     					yxq += formatDate(row.effectenddate);
	     					buttonstyle = ' style="color:#c09542;"';
	     				}else if(acttype == '4'){//活动入场券
	     					lotery_unit = 'lotery_unit rcq';
	     					lottery_name = '活动入场券';
	     					snd_title = row.value;
	     					yxq += formatDate(row.effectenddate);
	     					buttonstyle = ' style="color:#96ba48;"';
	     				}else if(acttype == '5'){//第三方优惠券
	     					lotery_unit = 'lotery_unit other';
	     					lottery_name = '第三方优惠券';
	     					snd_title = row.value+'元';
	     					yxq += formatDate(row.effectenddate);
	     					buttonstyle = ' style="color:#53a79e;"';
	     				}else if(acttype == '6'){//实体奖品
	     					lotery_unit = 'lotery_unit gift';
	     					lottery_name = '实体奖品';
	     					snd_title = row.value;
	     					yxq += formatDate(row.effectenddate);
	     					buttonstyle = ' style="color:#53a79e;"';
	     				}else if(acttype == '7'){//提现优惠券
	     					lotery_unit = 'lotery_unit recharge-lot';
	     					lottery_name = '提现优惠券';
	     					snd_title = row.value+'元';
	     					yxq += formatDate(row.effectenddate);
	     					buttonstyle = ' style="color:#5e88b2;"';
	     				}else if(acttype == '8'){//现金券
	     					lotery_unit = 'lotery_unit recharge-lot';
	     					lottery_name = '现金券';
	     					snd_title = row.value+'元';
	     					yxq += formatDate(row.effectenddate);
	     					buttonstyle = ' style="color:#5e88b2;"';
	     				}else if(acttype == '9'){//现金红包
	     					lotery_unit = 'lotery_unit recharge-lot';
	     					lottery_name = '现金红包';
	     					snd_title = row.value+'元';
	     					yxq += formatDate(row.effectenddate);
	     					buttonstyle = ' style="color:#5e88b2;"';
	     				}else{
	     					
	     				}
		     			
		     			if(flag=="0"){//全部
		     				$("#all").addClass("lottery_search_cur");
		     				if(row.bstyle=="0"){//已过期标志
		     					lotery_unit = 'lotery_unit used_lot';
			     				buttonstyle = "";
			     				img = '<div class="over_img"><img src="'+ctx+'/static/kingkaid/images/yiguoqi.png"/></div>';
			     				detail = '			<a class="jqxq" value="'+keyno+'">奖券详情</a>';
			     				button = '			<button'+buttonstyle+'>使用</button>'; 
		     			    }else if(row.bstyle=="1"){//已使用标志
	     						lotery_unit = 'lotery_unit used_lot';
			     				buttonstyle = "";
			     				img = '<div class="over_img"><img src="'+ctx+'/static/kingkaid/images/yishiyong.png"/></div>';
			     				detail = '			<a class="jqxq" value="'+keyno+'">奖券详情</a>'
			     				button = '			<button'+buttonstyle+'>使用</button>'; 
		     				}else{
		     					detail = '			<a class="jqxq"'+jqxq_click+' value="'+keyno+'">奖券详情</a>';
		     					if(acttype == "9"){
			     					button = '			<button'+buttonstyle+button_click+'>领取红包</button>';
			     				}else{
			     					button = '			<button'+buttonstyle+button_click+'>使用</button>';
			     				}
		     				}
		     			}else if(flag=="1"){//可使用
		     				detail = '			<a class="jqxq"'+jqxq_click+' value="'+keyno+'">奖券详情</a>';
		     				if(acttype == "9"){
		     					button = '			<button'+buttonstyle+button_click+'>领取红包</button>';
		     				}else{
		     					button = '			<button'+buttonstyle+button_click+'>使用</button>';		     					
		     				}
		     			}else if(flag=="2"){//已使用
		     				lotery_unit = 'lotery_unit used_lot';
		     				buttonstyle = "";
		     				img = '<div class="over_img"><img src="'+ctx+'/static/kingkaid/images/yishiyong.png"/></div>';
		     				detail = '			<a class="jqxq" value="'+keyno+'">奖券详情</a>';
		     				button = '			<button'+buttonstyle+'>使用</button>'; 
		     			}else if(flag=="3"){//已过期
		     				lotery_unit = 'lotery_unit used_lot';
		     				buttonstyle = "";
		     				img = '<div class="over_img"><img src="'+ctx+'/static/kingkaid/images/yiguoqi.png"/></div>';
		     				detail = '			<a class="jqxq" value="'+keyno+'">奖券详情</a>';
		     				button = '			<button'+buttonstyle+'>使用</button>'; 
		     			}
		     				
		     				
			     			html = '';
			     			html = '<div class="'+lotery_unit+'">';
			     			html += '		<div>';
			     			html += '			<span class="lottery_name">'+lottery_name+'</span>';
			     			html += '			<span class="snd_title">'+snd_title+'</span>';
			     			html += '		</div>';
			     			html += '		<div class="lottory_activity_info">';
			     			html += '			<div>&nbsp;</div>';
			     			html += '			<div class="yxq">'+yxq+'</div>';
			     			html += '		</div>';
			     			html += '		<div class="lottory_bottom">';
			     			html += detail;
			     			html += button;
			     			html += '		</div>';
			     			html += img;
			     			html += '</div>';

	  		     			$("#lottery_content").append(html);
	  		     			
	  		     	}
		     		
  		     		return true;
  		     	},
  				    qcon_func : function() {
  				    	return {
  				    		flag : $("#selectflag").attr("value")
  				    	};
  				    }
  		     });
       		 
  		
  		$(".lottery_search_type").click(function(){
  			var flag = $(this).attr("value");
  			if(flag=="0"){//全部奖券
  				$("#selectflag").attr("value",0);
  			}else if(flag=="1"){//可使用
  				$("#lottery_content").addClass("card_area can_use");
  				$("#selectflag").attr("value",1);
  			}else if(flag=="2"){//已用奖券
  				$("#lottery_content").addClass("card_area used-lottery");
// 				$("#lottery_content").removeClass("card_area can_use");
  				$("#selectflag").attr("value",2);
  			}else if(flag=="3"){//过期奖券
  				$("#lottery_content").addClass("card_area overtime");
// 				$("#lottery_content").removeClass("card_area can_use");
  				$("#selectflag").attr("value",3);
  			}
  			
            $(this).addClass("lottery_search_cur").parent().siblings().find(".lottery_search_type").removeClass("lottery_search_cur");
            $("#pager").trigger("setPage", 0);	
  		});

//  		$("#sure_join_btn").click(function(){
//  			$("#ticket_close").click();
//  		});
        
});

 //格式化时间
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

function emptyLottery(flag){
	$("#lottery_content").empty();
	if(flag=="1" || flag=="0"){//加上“兑换”
		exchange = '<div class="exchange" >';
		exchange += '   <a onclick=\"javascript:showExchangeBox();\"><img src="'+ctx+'/static/kingkaid/images/add-icon.jpg" /></a>';
		exchange += '   <div><a onclick=\"javascript:showExchangeBox();\">兑换</a></div>';
		exchange += '</div>';
		$("#lottery_content").append(exchange);
	}
}	
	
function ticketUseRedPicket(seqorder_list,keyno,acttype,redpicket){
	$.ajax({
        type: "POST",
        url: ctx+'/auth/activity/ticketcash.html',
        data: {"seqorder_list":seqorder_list},
        dataType: "json",
        success: function(data) {
        	closeDIV("new_dialogue");
        	if(data.respcode && data.respcode == 'S') {//回调成功
        		retulink = "/auth/usercenter/myproperty.html";
        		showRedPicketSuccessBox(redpicket);
        	}else{
        		if(data.respdesc != ''){
        			alert("错误提示："+data.respdesc);
        		}else{
        			alert("系统错误，请稍后再试！");
        		}
        	}
        }
    });
}
	
function ticketUseAjax(seqorder_list,keyno,acttype){
		$.ajax({
	        type: "POST",
	        url: ctx+'/auth/activity/ticketUse',
	        data: {"seqorder_list":seqorder_list,"keyno":keyno,"acttype":acttype},
	        dataType: "json",
	        success: function(data) {
	        	if(data.respcode && data.respcode == 'S') {//回调成功
	        		//加载成功页面，给出成功提示
	        		var retulink = null;
	        		if(acttype == "1"){//金开币
	        			retulink = "/auth/usercenter/mycoin.html";
	        			showSuccessBox(acttype,retulink);
	        		}else if(acttype == "2"){//加息券
	        			
	        		}else if(acttype == "3"){//会员升级
	        			retulink = "/auth/usercenter/myvip.html";
	        			showSuccessBox(acttype,retulink);
	        		}else if(acttype == "4" || acttype == "5"){//活动入场券、第三方优惠券
	        			retulink = "/auth/activity/my_lottery_page.html";
	        			showSuccessBox(acttype,retulink);
	        		}else if(acttype == "6"){//实体奖品
	        			retulink = "/auth/activity/my_lottery_page.html";
	        			showSuccessBox(acttype,retulink);
	        		}else if(acttype == "7"){//体现优惠券
	        			
	        		}
	        		
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
 
function ticketUse(seqorder_list,keyno,acttype){
	if(acttype == "1" || acttype == "3" || acttype == "5"){//金开币,会员升级，第三方优惠券
		ticketUseAjax(seqorder_list,keyno,acttype);
	}else if(acttype == "2"){//加息券
		retulink = "/project/invest_list_page.html";
		showSuccessBox(acttype,retulink);
	}else if(acttype == "7"){//提现优惠券
		retulink = "/auth/xabank/acct/withdraw_page.html";
		showSuccessBox(acttype,retulink);
	}else if(acttype == "4"){//活动入场券
		$.ajax({
	        type: "POST",
	        url: ctx+'/auth/activity/actpassselect',
	        data: {"keyno":keyno},
	        dataType: "json",
	        success: function(data) {
	        	if(data.respcode && data.respcode == 'S') {//回调成功
	        		//加载活动报名页
	        		showJoinCardBox(data,seqorder_list,keyno,acttype);
	        	}else{
	        		if(data.message != ''){
	        			alert("错误提示："+data.message);
	        		}else{
	        			alert("系统错误，请稍后再试！");
	        		}
	        		
	        	}
	        }
	    });
	}else if(acttype == "6"){//实体奖品
		$.ajax({
	        type: "POST",
	        url: ctx+'/auth/activity/relationsselect',
	        dataType: "json",
	        success: function(data) {
	        	if(data.respcode && data.respcode == 'S') {//回调成功
	        		showRelationsBox(data,seqorder_list,keyno,acttype);
	        	}else{
	        		if(data.message != ''){
	        			alert("错误提示："+data.message);
	        		}else{
	        			alert("系统错误，请稍后再试！");
	        		}
	        		
	        	}
	        }
	    });
	}else if(acttype == "8"){//现金券
		retulink = "/project/invest_list_page.html";
		showSuccessBox(acttype,retulink);
	}
}
 
function exchange(){
	showExchangeBox();
}

function addTicket(){	
	var djm_input = $("#djm_input").val();
	if(djm_input == '' || djm_input==undefined){
		alert("优惠券兑奖码不能为空！");
		return false;
	}else{
		$.ajax({
	        type: "POST",
	        url: ctx+'/auth/activity/awaToCust',
	        data: {"awaid":djm_input},
	        dataType: "json",
	        success: function(data) {
	        	if(data.respcode && data.respcode == 'S') {//回调成功
	        		alert("兑奖码绑定成功，奖券已发放到您的账户！"); 
	        		$("#close_btn").click();
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
 
function getTickDetail(seqorder_list,keyno,acttype,memberid){
    $.ajax({
        type: "POST",
        url: ctx+'/auth/activity/ticketDetail',
        data: {"seqorder_list":seqorder_list,"keyno":keyno,"acttype":acttype,"memberid":memberid},
        dataType: "json",
        success: function(data) {
        	if(data.respcode && data.respcode == 'S') {//回调成功
        		var content = '';
        		var left='<li><span class=\"window-list-left\">';
        		var right='</span></li>';

        		if(acttype == '1'){//金开币
        			content = left + '金额：</span><span>			'+data.amt + '元' + right
            				+ left + '兑换有效期：</span><span>		' +data.enddate + right;
 				}else if(acttype == '2'){//加息券
 					content = left + '加息比：</span><span>			'+ data.value + '%' + right
 							+ left + '兑换有效期：</span><span>		' +data.effectenddate + right
 							+ '<b>' + left + '使用范围</span><span>		' + right + '</b>'
 							+ left + '担保机构性质：</span><span>		' + ((data.assuorgkindname=='')?'无限制':data.assuorgkindname) + right
 							+ left + '担保机构类型：</span><span>		' + ((data.assuorgtypename=='')?'无限制':data.assuorgtypename) + right
 							+ left + '担保机构：</span><span>		' +  ((data.custname=='')?'无限制':data.custname) + right
 							+ left + '项目周期（月）：</span><span>		' + ((data.properiod=='')?'无限制':data.properiod) + right
 							+ left + '项目归属地：</span><span>		' + ((data.proaddrname=='')?'无限制':data.proaddrname) + right
 							+ left + '还款方式：</span><span>		' + ((data.retukindname=='')?'无限制':data.retukindname) + right
 							+ left + '投标金额：</span><span>		' + ((data.bidamt=='')?'无限制':data.bidamt) + right;
 				}else if(acttype == '3'){//会员升级
 					content = left + 'VIP期限：</span><span>			'+data.vipterm + '月' + right
							+ left + '兑换有效期：</span><span>		' +data.enddate + right;
 				}else if(acttype == '4'){//活动入场券
 					content = left + '活动名称：</span><span>			'+data.actname + right
 							+ left + '活动地点：	</span><span>		' +data.actarea + right
 							+ left + '活动时间：	</span><span>		' +data.actdate + right
 							+ left + '入场券有效期：	</span><span>		' +data.enddate + right
 							+ left + '活动简介：	</span><span>		' +data.actintro + right;
 				}else if(acttype == '5'){//第三方优惠券
 					content = left + '商家：</span><span>			'+data.seller + right
							+ left + '金额：	</span><span>		' +data.amt + right
							+ left + '优惠券有效期：	</span><span>		' +data.enddate + right
							+ left + '使用规则：	</span><span>		' +data.userule + right;
 				}else if(acttype == '6'){//实体奖品
 					content = left + '优惠券有效期：	</span><span>		' +data.enddate + right
 							+ left + '奖品：	</span><span>		' +data.prize + right;
 				}else if(acttype == '7'){//提现优惠券
 					content = left + '提现大于：</span><span>			'+ ((data.mindrawamt=='')?'无限制':data.mindrawamt) + '元' + right
							+ left + '提现小于：	</span><span>		' + ((data.maxdrawamt=='')?'无限制':data.maxdrawamt) + '元' + right
							+ left + '优惠金额：	</span><span>		' +data.amt + right
							+ left + '优惠券有效期：	</span><span>		' +data.enddate + right;
 				}else if(acttype == '8'){//现金劵
 					content = left + '活动名称：</span><span>			'+data.actname + right 
 					+ left + '现金券面值：</span><span>			'+data.value + '元' + right
    				+ left + '现金券有效期：</span><span>		' +data.effectenddate + right
    				+'</span><span><label style="display:block;line-height:50px;height:50px;border-bottom:1px solid #d8e1e5;">使用规则：本券仅可在投资时抵扣投资金额，投资' + (data.value*100) +'元即可使用。</label>';
 				}else if(acttype == '9'){//现金红包
 					content = left + '活动名称：</span><span>			'+data.actname + right
 					+ left + '现金红包面值：</span><span>			'+data.value + '元' + right
    				+ left + '现金红包有效期：</span><span>		' +data.effectenddate + right
    				+'<div style="height:45px;width:480px;border-bottom:1px solid #d8e1e5;margin-top:10px;padding-bottom:10px;"><label style="">使用规则：点击\"领取红包\"即为成功使用，使用后将体现在账户余额中，请至\"我的资产\"查看。</label></div>';
 				}else{
 					
 				}
        	    showUseBox(content);
        	    
        	}
        }
    });
}

function prizeUse(flag,seqorder_list,keyno,acttype){
	if(flag && flag == "true"){
		ticketUseAjax(seqorder_list,keyno,acttype);
	}else{//物流信息不完整
		alert("您在平台预留的物流信息不完整，请点击“去修改”按钮去安全中心修改物流信息！");
	}
}

//加载联系方式确认页面
function showRelationsBox(data,seqorder_list,keyno,acttype){
	create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var prompthtml = "";
    
    var content = '';
	var left='<li><span>';
	var right='</span></li>';
	content = left + '会员姓名：</span><span>			'+data.custname  + right
			+ left + '物流地址：</span><span>			'+data.addr + right
			+ left + '联系电话：</span><span>			'+data.mobilenumber + right
			+ left + '邮政编码：</span><span>			'+data.post + right;
	var flag = true;
	if(data.custname == '' || data.addr == '' || data.mobilenumber == '' || data.post == '' ){
		flag = false;
	}
	
	var button_click = 
			' onclick=\"prizeUse(\''+flag+'\',\''+seqorder_list+'\','+ '\''+keyno +'\',' + '\'' +acttype+'\');\"';
	
    prompthtml ='<div class="alert_box_small">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l">提示</div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'       <div class="operate_content">'
             +'             <div class="status_img">'
             +'                 <img src="'+ctx+'/static/kingkaid/images/warming_bt.png"/>'
             +'             </div>'
             +'             <div class="result_msg">'
             +'                 您在平台预留的物流信息如下：'
             +'       <ul>'
             +			   content
             +'       </ul>'
             +'             </div>'
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             //+'       <div class="blue_btn">确 定</div>'
             +'       <div class="blue_btn"' + button_click+ '>确 认</div>'
             +'       <div class="gray_btn btn_right"><a style="color:#fff" href="' + ctx +'/auth/usercenter/safetycenter.html">去修改</a></div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = prompthtml;
    document.body.appendChild(visual);
}

//加载活动报名页
function showJoinCardBox(data,seqorder_list,keyno,acttype) {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var JoinCardhtml = "";
    var joinbt_click=' onclick=\"ticketUseAjax(\''+seqorder_list+'\','+ '\''+keyno +'\',' + '\'' +acttype+'\');\"';
    
    JoinCardhtml ='<div class="alert_box_small" style="top:20px;">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l">入场券兑换</div>'
             +'       <div class="window_close_btn" id="ticket_close" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'       <div class="operate_content" style="text-align:center;line-height:36px;">'
             +'          <div class="JoinCard_content">'
             +'             <ul class="joincard_list">'
             +'                <li>'
             +'                   <span class="window-list-left">活动名称：</span>'
             +'                   <span class="window-list-right">'+ data.actname +'</span>'
             +'                </li>'
             +'                <li>'
             +'                   <span class="window-list-left">活动时间：</span>'
             +'                   <span class="window-list-right">'+ data.actdate +'</span>'
             +'                </li>'
             +'                <li>'
             +'                   <span class="window-list-left">活动地点：</span>'
             +'                   <span class="window-list-right">'+ data.actarea +'</span>'
             +'                </li>'
             +'                <li>'
             +'                   <span class="window-list-left">入场券有效期：</span>'
             +'                   <span class="window-list-right">'+ data.enddate +'</span>'
             +'                </li>'
             +'                <li>'
             +'                   <span class="window-list-left">姓名：</span>'
             +'                   <span class="window-list-right">'+ data.username +'</span>'
             +'                </li>'
             +'                <li>'
             +'                   <span class="window-list-left">联系电话：</span>'
             +'                   <span class="window-list-right">'+ data.tel +'</span>'
             +'                </li>'
             +'                <li>'
             +'                   <span class="window-list-left">出席人数：</span>'
             +'                   <span class="window-list-right">'+ data.num +'人</span>'
             +'                </li>'
             +'             </ul>'
             +'          </div>'
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom" style="padding:20px 0;">'
             +'       <div id="sure_join_btn" class="blue_btn"'+ joinbt_click +'>确认参加</div>'
            +'       <div class="gray_btn btn_right" onclick="closeDIV(&quot;new_dialogue&quot;)">下次再来</div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = JoinCardhtml;
    document.body.appendChild(visual);
}

function showRedPicketSuccessBox(redpicketamt){
	create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    retulink = "/auth/usercenter/myproperty.html";
    var topmessage = "提示";
	var result_msg = "恭喜您,"+redpicketamt+"元已经进入您的账户,请即刻开启您的金开贷赚钱之旅吧!";
    
    var successhtml ='<div class="alert_box_small">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l">'+topmessage+'</div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'       <div class="operate_content">'
             +'             <div class="status_img">'
             +'                 <img src="'+ctx+'/static/kingkaid/images/right_bt.png"/>'
             +'             </div>'
             +'             <div class="result_msg">'
             +					result_msg
             +'             </div>'
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             +'       <div class="blue_btn"><a href="'+ctx+retulink+'">我知道了</a></div>'
//             +'       <div class="blue_btn"><a href="' + ctx + retulink +'">确 认</a></div>'
//             +'       <div class="gray_btn btn_right" onclick="closeDIV(&quot;new_dialogue&quot;);location.reload(true);">取 消</div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = successhtml;
    document.body.appendChild(visual);
}

//操作成功状态
function showSuccessBox(acttype,retulink) {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var successhtml = "";
    var result_msg = "";
    var topmessage = "操作成功";
    if(acttype=="1"){//金开币
    	result_msg = "您已成功兑换，请前往“我的金开币”查看明细。";
    }else if(acttype=="2"){//加息券
    	topmessage = "提示";
    	result_msg = "是否跳转到投资列表使用加息券？";
    }else if(acttype=="3"){//会员升级
    	result_msg = "您已成功兑换，请前往“我的VIP”查看明细。";
    }else if(acttype=="7"){//提现优惠券
    	topmessage = "提示";
    	result_msg = "是否跳转到提现页面使用优惠券？";
    }else if(acttype=="4"){//活动入场券
    	result_msg = "请准时前往活动地点参与活动！";
    }else if(acttype=="5"){//第三方优惠券
    	result_msg = "您可直接到该指定商家根据使用规则享受优惠！";
    }else if(acttype=="6"){//实体奖品
    	result_msg = "您已成功兑换奖品，我们将在10个工作日内寄往您预留的物流地址，请您耐心等待！";
    }else if(acttype=="8"){// 现金券
    	topmessage = "提示";
    	result_msg = "是否跳转到投资列表使用现金券？";
    }else if(acttype=="9"){// 现金红包
    	topmessage = "提示";
    	result_msg = "您已成功领取现金红包，请前往“我的资产”查看明细。";
    }
    
    successhtml ='<div class="alert_box_small">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l">'+topmessage+'</div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'       <div class="operate_content">'
             +'             <div class="status_img">'
             +'                 <img src="'+ctx+'/static/kingkaid/images/right_bt.png"/>'
             +'             </div>'
             +'             <div class="result_msg">'
             +					result_msg
             +'             </div>'
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             //+'       <div class="blue_btn">我知道了</div>'
             +'       <div class="blue_btn"><a href="' + ctx + retulink +'">确 认</a></div>'
             +'       <div class="gray_btn btn_right" onclick="closeDIV(&quot;new_dialogue&quot;);location.reload(true);">取 消</div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = successhtml;
    document.body.appendChild(visual);
}

//添加奖券
function showExchangeBox() {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var InfoLookehtml = "";
    InfoLookehtml ='<div class="alert_box_small">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l">添加奖券</div>'
             +'       <div class="window_close_btn" id="close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'       <div class="operate_content" style="text-align:center">'
             +'           <div style="margin-bottom:20px;"><span>优惠券兑奖码：</span><input type="text" class="window_input" id="djm_input"></div>'
             +'           <div>提示：请输入您线下获取的活动兑奖码，点击添加按钮添加活动下的奖券！</div>'
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             +'       <div class="blue_btn" onclick="addTicket()">添    加</div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = InfoLookehtml;
    document.body.appendChild(visual);
}

//使用条件弹窗方法
function showUseBox(content) {
     create_bg();
     var visual = document.createElement("div");
 visual.id = "new_dialogue";
 var useBoxhtml = "";
 useBoxhtml ='<div class="alert_box_small">'

          +'   <div class="window_top">'
          +'       <div class="window_top_l">奖券详情</div>'
          +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
          +'   </div>'
          +'   <div class="window_content">'
          +'       <ul class="window_use_list" >'
          +			   content
          +'       </ul>'
          +'   </div>'
          +'   <div class="small_window_bottom">'
          +'       <div class="blue_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">关 闭</div>'
          +'   </div>'
          +'</div>';

         visual.innerHTML = useBoxhtml;
     document.body.appendChild(visual);
 }


//使用条件弹窗方法
function confirmBox(seqorder_list,keyno,acttype,redpicket) {
     create_bg();
     var visual = document.createElement("div");
 visual.id = "new_dialogue";
 var useBoxhtml = "";
 useBoxhtml ='<div class="alert_box_small">'

          +'   <div class="window_top">'
          +'       <div class="window_top_l">提示</div>'
          +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
          +'   </div>'
          +'   <div class="window_content">'
          +'       <ul class="window_use_list" >'
          +			   '你确定要立即领取现金红包吗?'
          +'       </ul>'
          +'   </div>'
          +'   <div class="small_window_bottom">'
          +'       <div class="blue_btn" onclick=\"ticketUseRedPicket(\''+seqorder_list+'\','+ '\''+keyno +'\',' + '\'' +acttype+'\',' + '\'' +redpicket+'\');\">立即领取</div>'
          +'       <div class="gray_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">取 消</div>'
          +'   </div>'
          +'</div>';

         visual.innerHTML = useBoxhtml;
     document.body.appendChild(visual);
 }

	
 