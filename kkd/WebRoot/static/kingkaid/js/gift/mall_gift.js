 $(document).ready(function(){		 		 
	 	$("#usermenu_dzsc").addClass("user_menulist_href_hover");
		$("#menu_jkd").addClass("leftmenu_cur");
		 	  
		var goodsId=getQueryString();	 
	 	
		$.ajax({		
			type: 'POST',
			dataType: "json",
			url : ctx+'/gift/getMallGift',
			data: {"goodsId": goodsId},
			success: function(data) {	 	 
			
			var obj=eval(data); 
				obj=obj.biz_product_detail_query_response;			
			var content1='<div class="clubtittle1" style="margin:0 auto; color:#666; background:none;">'+obj.result.name+ '</div>';
			$(".clubtittle1").append(content1);

			var content2 = '<div class="product-img  lazyload-part">';
				content2 +='<img src="http://img20.360buyimg.com/n1/'+obj.result.imagePath+'">';
//				content2 +='<div class="product-share-wrap">'+obj.result.name+'</div>';
				content2 +='</div>';	
			$(".lazyload-part").append(content2);		

			var	content3 = '<div class="product-title">';
				content3 +='<h2 class="product-name">'+obj.result.name+'</h2>';
				content3 +='<h3 class="product-sub-name">'+obj.result.brandName+'</h3>';
				content3 +='</div></div>';
			$(".product-title").append(content3);
			
			var	content4 = obj.result.introduction; 
			$(".tab-content-wrap-account").append(content4);
			
			var	content5 = obj.result.param; 		
			$(".tab-content").append(content5);
			}
		});  
  
        $("#exchange-btn").click(function(){
        	var inputobj =$(".addbtn").siblings(".car_input_num").attr("value");	
        	$.ajax({
    			dataType: "json",
    			url : ctx+'/gift/getForOrders',
    			data: {"goodsId": goodsId},
        		success : function(data) {		
        			if (data.respcode != "S") {	
        				window.location.href=ctx+'/auth/usercenter/mall_gift_temp.html?goodsId='+goodsId;
        			} else {		
        				if (data.aclrate=="F") {
        					showPromptNewBox("您当前年化投资金额不足30万，继续努力，再来看看！ ");
//							alert("您当前年化投资金额不足100万，继续努力，再来看看！");
							return false;
						}else{
							window.location.href=ctx+'/auth/usercenter/mall_pay.html?inputnum='+inputobj;
						}
        			}
        		},
        		error : function() {
        			alert("异常！");
        		}
        	});
        });
 });

 
 function getQueryString(){
	 var strs;
	 var url = window.location.search;		 
     if(url.indexOf("?")!=-1){
    	 var str=url.substr(1);		 
    	 strs=str.split("=");		 
     }
     return strs[1];
 }
  
 