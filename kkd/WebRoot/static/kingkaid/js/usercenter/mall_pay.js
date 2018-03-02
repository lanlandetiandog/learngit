 $(document).ready(function(){		 		 
	 // 初始化联系地址中的地址选择组件
/*	    $("#contact_addr_area").areaSelection({level:3, value:$("#contact_addr_code").val(), item_linkage: function (v) {
	    	$("#contact_addr_code").val(v);
	    	contact_addr_form.element($("#contact_addr_code"));
	    }});*/

	//省变化联动市区变化   
    $("#province").change(function(){
    	$("#city").html("");
    	$("#county").html("");
    	$("#town").html("");
    	var province= $("#province").val();	 	
       	$.ajax({
	        type: "POST",
	        url: ctx+'/auth/usercenter/getCityInfo',
	        data : {
    			province : province
    		},
	        dataType: "json",
	        success: function(data) {		 
        	   var optcontent="";
        	   optcontent +="<option></option>"; 
	             for ( var i = 0; i < data.length; i++) {	
//	            	 optcontent +="<option value="">null</option>";
	            	 optcontent +="<option value="+data[i].city+">"+data[i].cityname+"</option>";
	             	}
	             $("#city").append(optcontent);
	        }
	    });    
    });
	   
  //市变化联动县变化   
    $("#city").change(function(){
    	$("#county").html("");
    	$("#town").html("");
    	var city= $("#city").val();	 	
       	$.ajax({
	        type: "POST",
	        url: ctx+'/auth/usercenter/getCountyInfo',
	        data : {
	        	city : city
    		},
	        dataType: "json",
	        success: function(data) { 
        	   var optcontent="";
        	   optcontent +="<option></option>"; 
	             for ( var i = 0; i < data.length; i++) {	
 	            	 optcontent +="<option value="+data[i].county+">"+data[i].countyname+"</option>";
	             	}			 
	             $("#county").append(optcontent);
	        }
	    });    
    });
  
    //市变化联动县变化   
    $("#county").change(function(){			
    	$("#town").html("");
    	var county= $("#county").val();	 		 
       	$.ajax({
	        type: "POST",
	        url: ctx+'/auth/usercenter/getTownInfo',
	        data : {
	        	county : county
    		},
	        dataType: "json",
	        success: function(data) { 		 	
	        var optcontent="";
		        if (data.length=="0") {		 
 		        	$("#town").css("display","none");
		        	$("#town_name").css("display","none");
 				} else {
 				 	$("#town").show();
 			    	$("#town_name").show();
					// optcontent +="<option></option>"; 
		             for ( var i = 0; i < data.length; i++) {			 
		            	 optcontent +="<option value="+data[i].town+">"+data[i].townname+"</option>";
		             	}
		             $("#town").append(optcontent);
				}
		        
	        }
	    });    
    });
    
    $("#mobile").focus(function(){
   	 $(".oderphone").css("display","inline-block");
    });
     $("#mobile").blur(function(){
   	 $(".oderphone").css("display","none");
    });
 });

 function saveAddress(){	
	 var province= $("#province").val();	
	 var city= $("#city").val();	
	 var county= $("#county").val();	
	 var town= $("#town").val();
	 var detailaddress= $("#address").val();
	 var name= $("#name").val();
	 var mobile= $("#mobile").val();
	 var zip= $("#zip").val();
	 if(isOrNotEmpty(province)){
		 alert("省份不能为空！");
		 return false;
	 }
	 if(isOrNotEmpty(city)){
		 alert("城市不能为空！");
		 return false;
	 }
	 if(isOrNotEmpty(county)){
		 alert("县镇地址不能为空！");
		 return false;
	 }
	 if(isOrNotEmpty(detailaddress)){
		 alert("详细地址不能为空！");
		 return false;
	 }
	 if(isOrNotEmpty(name)){
		 alert("收件人姓名不能为空！");
		 return false;
	 }
	 if(isOrNotEmpty(mobile)){
		 alert("手机号码不能为空！");
		 return false;
	 }	 
	 if (mobile.length !="11") {
		 alert("请输入正确的11位手机号码！");
		 return false;
	}
	 if(isOrNotEmpty(zip)){
		 alert("邮编不能为空！");
		 return false;
	 }
	
	 ConfirmAlertForNext(); 
}
 
 function ContConfirmForSaveAddr(){		 
	 var province= $("#province").val();	
	 var city= $("#city").val();	
	 var county= $("#county").val();	
	 var town= $("#town").val();
	 var detailaddress= $("#address").val();
	 var name= $("#name").val();
	 var mobile= $("#mobile").val();
	 var zip= $("#zip").val();
	   	$.ajax({
	        type: "POST",
	        url: ctx+'/auth/usercenter/SaveAddressInfo',
	        data : {
	        	province : province,
	        	city : city,
	        	county : county,
	        	town : town,
	        	detailaddress : detailaddress,
	        	name : name,
	        	mobile : mobile,
	        	zip : zip
    		},
	        dataType: "json",
	        success: function(data) {		 
	        	var responsecode= data.responsecode;		 
	        	if (responsecode=="S") {	
	        		window.location.reload();	 
	        		window.location.href='#saveorder';
//	        		showPromptNewBox("地址保存成功，请确认输入的地址正确无误！ ");
 				} else{
					alert("地址保存失败，请重新保存地址！ ");
				} 
	        }
	    });
 }
 
 function ConfirmAlertForNext() {
		create_bg();
		var agree_click = ' onclick=\"ContConfirmForSaveAddr();\"';
	 
		var confirmques = '请确认输入的地址信息正确！';
		var visual = document.createElement("div");
		visual.id = "new_dialogue";
		var prompthtml = "";
		prompthtml = '<div class="alert_box_small">'

				+ '   <div class="window_top">'
				+ '       <div class="window_top_l">温馨提示</div>'
				+ '       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'
				+ ctx + '/static/kingkaid/images/window_close_bt.png"/></div>'
				+ '   </div>' + '   <div class="window_content">'
				+ '       <div class="operate_content">'
				+ '             <div class="status_img">'
				+ '                 <img src="' + ctx
				+ '/static/kingkaid/images/warming_bt.png"/>'
				+ '             </div>' + '             <div class="result_msg">'
				+ confirmques + '             </div>' + '       </div>'
				+ '   </div>' + '   <div class="small_window_bottom">'
				+ '		  <div id="down_click" class="blue_btn"' + agree_click + '>确 认</div>'
				+'       <div class="gray_btn btn_right" onclick="closeDIV(&quot;new_dialogue&quot;)">取 消</div>'
				+ '   </div>' + '</div>';

		visual.innerHTML = prompthtml;
		document.body.appendChild(visual);

	}
 
 
 
 
// 提交订单方法	
 function saveOrderInfo(){	
	 var inputnum=getQueryString();			 
	 var goodsamts= $("#goodsamts").text();		 
	 if (parseInt(goodsamts)==0) {
//		 alert("下单页面金额必须大于等于零！");
		 showPromptNewBox("下单页面金额必须大于等于零！ ");
		 return false;
	 }
	 var haveamt=$("#have_amt").text();			 
	 if (parseInt(goodsamts)-parseInt(haveamt) > 0) {
//		 alert("金开币余额不足，无法进行兑换！");
		 showPromptNewBox("金开币余额不足，无法进行兑换！ ");
		 return false;
	 }
	 var province= $("#province").val();	
	 var city= $("#city").val();	
	 var county= $("#county").val();	
	 var town= $("#town").val();
	 var detailaddress= $("#address").val();
	 var name= $("#name").val();
	 var mobile= $("#mobile").val();
	 var zip= $("#zip").val();
	 if(isOrNotEmpty(province)){
		 alert("省份不能为空！");
		 return false;
	 }
	 if(isOrNotEmpty(city)){
		 alert("城市不能为空！");
		 return false;
	 }
	 if(isOrNotEmpty(county)){
		 alert("县镇地址不能为空！");
		 return false;
	 }
	 if(isOrNotEmpty(detailaddress)){
		 alert("详细地址不能为空！");
		 return false;
	 }
	 if(isOrNotEmpty(name)){
		 alert("收件人姓名不能为空！");
		 return false;
	 }
	 if(isOrNotEmpty(mobile)){
		 alert("手机号码不能为空！");
		 return false;
	 }	 
	 if(isOrNotEmpty(zip)){
		 alert("邮编不能为空！");
		 return false;
	 }
	 
	  	$.ajax({
	        type: "POST",
	        url: ctx+'/auth/usercenter/saveOrderInfos',
	        data : { 
	        	inputnum:inputnum
    		},
	        dataType: "json",
	        success: function(data) {	
	        	var king_respcode= data.king_respcode;			 
	        	var jd_respcode=data.jd_respcode;	
	        	var thirdorder=data.thirdorder;		 
	        	if (king_respcode=="S"&& jd_respcode=="true") {
					window.location.href=ctx+'/auth/usercenter/mall_success.html?thirdid='+thirdorder;
/*	    		}else if (king_respcode=="S"&& jd_respcode=="false"){
					alert(data.jd_respdesc);
				} else if(king_respcode=="F" && jd_respcode=="true"){		
					alert(data.king_respdesc);
				} else if(king_respcode=="F"){		
					alert(data.king_respdesc);*/
				}else if (king_respcode=="N") {
					alert("暂不支持企业户进行兑换！");
				}else {
					window.location.href=ctx+'/auth/usercenter/mall_fail.html?thirdid='+thirdorder;
				}
	        }
	    });
 }
 function isOrNotEmpty(name){
	 var flag=false;
	 if(name==null || name==""){			 
		 flag=true;
	 }else{					 
		 flag= false;
	 }
	 return flag;
 }
 
 function getQueryString(){
	 var strs;
	 var url = window.location.search;		 
     if(url.indexOf("?")!=-1){
    	 var str=url.substr(1);		 
    	 strs=str.split("=");		 
     }
     return strs[1];
 }
	
