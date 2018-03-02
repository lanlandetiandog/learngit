
//操作提示状态
function showConfirmBox(content,func,param) {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var prompthtml = "";
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
             +'                 '+content
             +'             </div>'
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             +'       <div class="blue_btn" onclick="'+func+'('+param+');">确 认</div>'
             +'       <div class="gray_btn btn_right" onclick="closeDIV(&quot;new_dialogue&quot;)">取 消</div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = prompthtml;
    document.body.appendChild(visual);
}

function closeBox(){
	closeDIV('new_dialogue');
}

function showAlertBox(content) {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var buyViphtml = "";
    buyViphtml ='<div class="alert_box_small">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l">提示</div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'       <div class="operate_content" style="text-align:center;line-height:36px;">'

             +'            <img style="margin:0 10px -12px 0;" src="'+ctx+'/static/kingkaid/images/warming_bt.png"/>'

             +'             <span style="font-size:20px;display:inline-block">'
             +'                '+content
             +'             </span>'
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             +'       <div class="blue_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">确定</div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = buyViphtml;
    document.body.appendChild(visual);
}

function showRechargeOverBox(title,content,url) {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var Rechargehtml = "";
    Rechargehtml ='<div class="alert_box_small">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l">'+title+'</div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'       <div class="operate_content" style="text-align:center;line-height:36px;">'

             +'            <img style="margin:0 10px -12px 0;" src="'+ctx+'/static/kingkaid/images/warming_bt.png"/>'

             +'             <span style="font-size:20px;display:inline-block">'
             +'                '+content
             +'             </span>'
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom" style="height:auto">'
             +'       <div class="blue_btn" onclick="payfroward('+url+');" style="background-color:red">已完成操作</div>'
             +'       <div class="gray_btn btn_right" onclick="closeDIV(&quot;new_dialogue&quot;)" style="background-color:#5a87bc;"><a href="'+ctx+'/website/xa_faqdetail.html" target="_blank" style="color:#FFFFFF;">操作遇到问题</a></div>'
             +'       <div class="pay_bottom_link"style=""><a onclick="closeDIV(&quot;new_dialogue&quot;)">返回重新进行操作</a></div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = Rechargehtml;
    document.body.appendChild(visual);
}

function payfroward(url){
	
	if(url == undefined){
		window.location.reload(true);
		closeBox();
		return;
	}
	
	window.location.href = url;
}