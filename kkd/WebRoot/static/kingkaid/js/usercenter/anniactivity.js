 $(document).ready(function(){	 
	 
	   $.ajax({
         type:"POST",
         url:ctx+'/auth/activity/isVerti',
         dataType:"json",
         success:function(data){
             var state = data.isVerti;
             if(state =='4'){
               alert("您还没有实名认证，请前往安全中心完成实名认证！");
               window.location.href = ctx+"/auth/usercenter/safetycenter.html";
             }else{
            	 prizereload();
            	 timesreload();             	   
             }            
         }
       });
	   

       $("#play").click(function(){
    	  $("#play").attr("disabled",true);
    	  vidplay();
    	  $(".lotin").delay(5000).show(1000);
    	  $("#play").css("cursor","default");    	
    	  setTimeout("replace()",5000);
       });
	 
     //关闭灰色 jQuery 遮罩
		$("#close").click(function(){
			$("#fullbg").hide();
			$("#dialog").hide();
			$(".lotin").hide();	
			$("#play").css("background","url("+ctx+"/static/kingkaid/images/prize/start2.png) no-repeat top center");
	    	$("#play").css("cursor","pointer");
			prizereload();
		});
 });
 
function replace(){
	$("#play").css("background","url("+ctx+"/static/kingkaid/images/prize/start1.png) no-repeat top center");	
}
 
function timesreload(){
	 $.ajax({
		 type:"POST",
	     url:ctx+'/auth/activity/timereload',
	     dataType:"json",
	     success:function(data){
	    	 if(data.status){
	    		 var times = data.body.times;
		    	 $(".footertext").empty();
		    	 var content = '';
		    	 content += '<p>你今天还有'+times+'次抽奖机会</p>';
		    	 $(".footertext").append(content);
		    	 if(times!='0'&&times!=''){
		    		 $("#play").attr("disabled", false);
		    	 }
	    	 }else{
	 	    	 $("#play").css("cursor","default");
	    	 } 
	     }
	 });
};

function prizereload(){
	 $.ajax({
		 type:"POST",
	     url:ctx+'/auth/activity/prizereload',
	     dataType:"json",
	     success:function(data){
	    	 $(".addressline").empty();
	    	 for(var i=0;i<data.length;i++){
	    		 var content = '';
		    	 content += '<div>';
		    	 content += '<span>'+data[i].awardname+'</span>';
		    	 content += '</div>';  
		    	 $(".addressline").append(content);
	    	 }
	     }
	 });
};



function vidplay() {
   var video = document.getElementById("lotmo");
   if (video.paused) {
      video.play();
   }
};    


function showBg() {	
	  $("#loading").show();
	  $("#close").empty();
      var content ='<div id="fullbg"></div>';
      content += '<div id="dialog">';
      content += '<div class="doorlight"><img src='+ctx+'/static/kingkaid/images/prize/000000.png></div>';
      content += '<div style="color:#fff;font-size:18px;">很遗憾您未中奖！</div>';
      content += '</div>';
      $("#close").append(content);
      $.ajax({
	 	   type:"POST",
	 	   url: ctx+'/auth/activity/lottery',
	 	   dataType:"json",
	 	   success:function(data){
	 		   if(data.status){
	 		   $("#loading").hide();
	 		   var awardid = data.body.awardid;
	  		   var contactaddr = data.body.contactaddr;
	  		   var isvirtual = data.body.isvirtual;
	  		   var awardArr = ['','10元金开币','3‰加息券一张','平台VIP1个月','晴雨伞一把','自拍杆一个','充电宝一个','微单相机一台','iPhon6s一部','Apple macbook'];
	  		   $("#close").empty();
	  		   var content ='<div id="fullbg"></div>';
	  		   content += '<div id="dialog">';
	  		   if(awardid==''){
	  			   content += '<div class="doorlight"><img src='+ctx+'/static/kingkaid/images/prize/000000.png></div>';
	  			   content += '<div style="color:#fff;font-size:18px;">很遗憾您未中奖！</div>';
	  		   }else{
	  			   var awardname = '';
	      		   var num = awardid.charAt(5);
	      		   awardname = awardArr[num]; 
	  			   content += '<div class="doorlight"><img src='+ctx+'/static/kingkaid/images/prize/'+awardid+'.png></div>';
	    		       content += '<div style="color:#fff;font-size:18px;">恭喜您获得'+awardname+'</div>';
	    		   if(isvirtual=='1'){
	    			   content += '<div style="color:#fff;font-size:12px;"><a href='+ctx+'/auth/activity/my_lottery_page.html target="_blank">奖品已发送至您的帐户，请在我的奖券查看！</a></div>'; 
	    		   }else{
	    			   if(contactaddr==''){
	       			   content += '<div style="color:#fff;font-size:15px;"><a href='+ctx+'/auth/usercenter/safetycenter.html target="_blank">系统检测到您还未完善联系地址，为保证奖品正常发送，请先前往安全中心联系地址栏完善您的邮寄地址！</a></div>';
	       		   }else{          			   
	        		       content += '<div style="color:#fff;font-size:13px;">当前邮寄地址为:'+contactaddr+'</div>';
	        		       content += '<div style="color:#fff;font-size:13px;margin-top:30px;"><a href='+ctx+'/auth/usercenter/safetycenter.html target="_blank">更换地址？</a></div>';
	       		   } 
	    		   }
	  		   }
	  		     content += '</div>';
	  		     $("#close").append(content);    
		  		 var bh = $("body").height();
		  	     var bw = $("body").width();
		  	     $("#fullbg").css({
		  	     height:bh,
		  	     width:bw,
		  	     display:"block"
		  	     });
		  	     $("#dialog").show();
		  	     timesreload();
	 		   }else{
	 			   alert(data.message);
	 		   }       		   
	 	   }
	   })
};

