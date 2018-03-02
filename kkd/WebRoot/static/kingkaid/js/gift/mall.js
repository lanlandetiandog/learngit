 $(document).ready(function(){		 		 
        $("#pager").simplePagination({	
        	url : ctx+'/gift/getMallInfo',
	        items_per_page : 8,
	        handle_data : function(dataObj) {				
	        	$("#mall_product_list").empty();				
	   		  	var rowContent ='<ul class="mall_product_list">';			 
	  		 	for(var i = 0; i < dataObj.list.length; i ++) {	 	
	  		 		rowContent +='<li>'
		  		 	rowContent +='<div class="product-imgauto">';
		  		 	rowContent +='<a href="'+ctx+'/gift/mall_gift.html?goodsId='+dataObj.list[i].sku+'" target="_blank">';
		  		 	rowContent +='<img src="http://img20.360buyimg.com/n2/'+dataObj.list[i].goodsdesc+'"></a>';
		  		 	/*rowContent +='<img src="'+dataObj.list[i].goodsDetail.biz_product_detail_query_response.imagePath+'" />';*/
		  		 	rowContent +='</div>';
		  		 	rowContent +='<div class="info_unit pdt_name" title="">';
		  		 	rowContent +='<span class="mall_product_data">'+dataObj.list[i].goodsname+'</span>';
		  		 	rowContent +='</div>';
		  		 	rowContent +='<div class="info_unit">';
		  		 	rowContent +='<span class="mall_product_title">价格：</span>';
		  		 	rowContent +='<span class="promotion_price"  style="margin-left:-30px;">'+dataObj.list[i].goodsamt+' 金开币</span>';
		  		 	rowContent +='</div></li>';
	  		 	}
	            rowContent=rowContent +  '</ul>';
	            $("#mall_product_list").append(rowContent);
		    	return true;
	  		   },
	  			
			});
 });
 
  
 