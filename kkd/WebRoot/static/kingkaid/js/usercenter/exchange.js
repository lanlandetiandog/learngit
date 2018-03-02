$(document).ready(function(){
        $("#usermenu_dzsc").addClass("user_menulist_href_hover");
        $("#menu_jkd").addClass("leftmenu_cur");
        if(coin<0){
        	 $("#coinmessge").show();
        	}
       
        $(".addbtn").click(function() {
            var inputobj = $(this).siblings(".car_input_num").attr("id");
            if(coin<0){
            	$("#plus").addClass("disabled");
            	$("#coinmessge").show();
            	alert('金开币余额不足！');
            	return false;
            }
            addCarNum(inputobj);
        });
        $(".decreasebtn").click(function() {
            var inputobj = $(this).siblings(".car_input_num").attr("id");
            decreaseCarNum(inputobj);
        });
        $("#exchangesub").click(function() {
        	var giftnum = $("#text").val();
        	
//        	if(custname == '' || coinamount == '' || mobilenumber == '' || addr == ''|| post == '' ){
//        		alert('物流信息不完善！');
//        		return;
//        	}
        	if(giftname == '' || discotamt == '' || coinamount == '' ){
        		alert('商品信息不正确！');
        		return;
        	}
    		var giftnum_ =parseInt(giftnum);
    		var gifttcount_ =parseInt(gifttcount);
        	if(giftnum_>gifttcount_){
        		alert('商品库存不足！');
        		return;
        	}
        	if(coin<0){
        		alert('金开币余额不足！');
        		return;
        	}
        	  $.ajax({
      	        type: "POST",
      	        url: ctx+'/auth/usercenter/exchangesub',
      	        data: {"giftno":giftno,"giftnum":giftnum},
      	        dataType: "json",
      	        success: function(data) {
      	        	if(data.respcode && data.respcode == 'S') {//回调成功
      	        		showSuccess(data.giftname,data.orderid);
    	        	}else{
    	        		if(data.message != ''){
    	        			alert("错误提示："+data.message);
    	        		}else{
    	        			alert("系统错误，请稍后再试！");
    	        		}
    	        	}
      	        }
      	    });
        	});
    })


var maxNum=9999999;//最大库存
var minNum=1;//最小库存


    

function decreaseCarNum(inputid){
   
    var val=$("#"+inputid).val()*1;
    if(val>minNum){
            $("#"+inputid).val(val-1);
    }
    if(val<=minNum){
            $("#minus").addClass("disabled");
    }
    var num=$("#"+inputid).val()*1;
//    $("#price").val(num*discotamt);
    $("#price").html(num*discotamt);
    coin = coinamount-num*discotamt;
    $("#coin").html(coin);
    if(coin>=0){
    	$("#coinmessge").hide();
    	 $("#plus").removeClass("disabled");
    }
}
//添加数量
function addCarNum(inputid){
        var val=$("#"+inputid).val()*1;
        if(val<maxNum){
            $("#"+inputid).val(val+1); 
            $("#minus").removeClass("disabled");
        }
        if(val>maxNum){
            $("#plus").addClass("disabled");
            alert('商品库存不足！');
        }
        var num=$("#"+inputid).val()*1;
//        $("#price").val(num*discotamt);
        $("#price").html(num*discotamt);
        coin = coinamount-num*discotamt;
        $("#coin").html(coin);
        if(coin<0){
        	$("#plus").addClass("disabled");
        	$("#coinmessge").show();
        	alert('金开币余额不足！');
        }
}


//操作成功状态
function showSuccess(giftname, orderid ) {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var successhtml = "";
    successhtml ='<div class="alert_box_small">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l">操作成功</div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'       <div class="operate_content">'
             +'             <div class="status_img">'
             +'                 <img src="'+ctx+'/static/kingkaid/images/right_bt.png"/>'
             +'             </div>'
             +'             <div class="result_msg">'
             +'                 您已成功兑换商品'+giftname+',订单号为'+orderid+' ,我们将在7个工作日内为您寄出，请您耐心等待。'
             +'             </div>'
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             +'       <div class="blue_btn" onclick="return_()">我知道了</div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = successhtml;
    document.body.appendChild(visual);
}

function return_(){
	window.location = ctx+"/auth/usercenter/mall.html";
}


