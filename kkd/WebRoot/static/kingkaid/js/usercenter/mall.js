 $(document).ready(function(){
	 	$("#usermenu_dzsc").addClass("user_menulist_href_hover");
	 	$("#menu_jkd").addClass("leftmenu_cur");
        
       	$.ajax({
	        type: "POST",
	        url: ctx+'/auth/usercenter/isVerti',
	        dataType: "json",
	        success: function(data) {
	        	var isVerti = data.isVerti;
	        	if(isVerti =='4'){//未认证
	        		alert("您还没有实名认证，请在安全中心完成实名认证！");
	        		window.location = ctx+"/auth/usercenter/safetycenter.html";
	        	}
	        }
	    });    
       	
  		$("#pager").simplePagination({
		     	url : ctx+'/auth/usercenter/mall_gift.html',
		     	items_per_page : 8,
		     	handle_data : function(dataObj) {
		     	$("#mall_gift_list").empty();
     			var html = '';
	     		for(var i = 0; i < dataObj.result.length; i ++) {
	     			var row = dataObj.result[i];
	     			var img = '';
		     			html = '<li>';
		     			html += '<div>';
		     			html += ' <img src="'+row.giftpic+'" style="width:178px;height:178px;" />';
		     			html += '</div>';
		     			html += '<div class="info_unit pdt_name">';
		     			html += '<span class="mall_product_title">商品名称：</span>';
		     			html += '<span class="mall_product_data">'+row.giftname+'</span>';
		     			html += '</div>';
		     			html += ' <div class="info_unit">';
		     			html += '<span class="mall_product_title">商品规格：</span>';
		     			html += '<span class="mall_product_data">'+row.giftsize+'</span>';
		     			html += '</div>';
		     			html += '<div class="info_unit">';
		     			html += '<span class="mall_product_title">价格：</span>';
		     			html += '<span class="mall_product_data standard_price">'+row.giftdelprice+'金开币</span>';
		     			html += '</div>';
		     			html += '<div class="info_unit">';
		     			html += '<span class="mall_product_title">优惠价：</span>';
		     			html += '<span class="mall_product_data promotion_price">'+row.discotamt+'金开币</span>';
		     			html += '</div>';
		     			html += '<div class="info_unit">';
		     			html += '<span class="mall_product_title">来源： </span>';
		     			html += '<span class="mall_product_data">'+row.giftfrom+'</span>';
		     			html += '</div>';
//		     			html += '<div class="exchange_btn" onclick="openExchange(\''+row.giftname+'\',\''+row.giftsize+'\',\''+row.discotamt+'\');">';
		     			html += '<div class="exchange_btn" onclick="openExchange(\''+row.giftno+'\');">';
		     			html += '兑 换</div>';
		     			html += '</li>';
  
  		     			$("#mall_gift_list").append(html);
  		     			
  		     			
  		     		}
	     			$("#mall_gift_list").append("<hr/>");
		     		return true;
		     	},
		     });
  		
});
 function openExchange(giftno){
	 $("#giftno").attr("value",giftno);
	 $("#exchangeform").submit();
//	 	window.location = ctx+'/auth/usercenter/exchange.html?giftno='+giftno;
	}


	
 