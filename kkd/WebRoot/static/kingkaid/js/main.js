$(document).ready(function(){
    //loadHeader();
    //loadLeftMenu();
    //loadUserLeftMenu();
    //loadSystemLeftMenu();
    //loadTopUserRemind()
    //loadFooter();
    goTopEx();

    $('.qq').hover(
            function(){
                $(".qq_hover").show();
            },
            function(){
                 $(".qq_hover").hide();
            });
    
    $('.weixin').hover(
        function(){
            $(".winxin_hover").show();
        },
        function(){
             $(".winxin_hover").hide();
        });

    $('.webo').hover(
        function(){
            $(".webo_hover").show();
        },
        function(){
             $(".webo_hover").hide();
        });

    $('.user_menulist_href').hover(
        function(){
            $(this).addClass("user_menulist_href_select");
        },
        function(){
            $(this).removeClass("user_menulist_href_select");
        });

    //问号弹出框提示
    $(".quit").hover(
            function(){
                $(this).find(".quit_content").fadeIn();
            },
            function(){
                $(this).find(".quit_content").fadeOut();
            }
        );
    
    $("#openxapay").click(function(){
    	showRechargeOverBox('用户开户','开户完成前请不要关闭该窗口！');    	
    });
    
    $("#corpopenpay").click(function(){
    	showRechargeOverBox('企业用户开户','开户完成前请不要关闭该窗口！');    	
    });

});



function loadHeader(){
    $.ajax({
        url: "header.html",
        cache: false,
        global: false,
        type: "GET",
        dataType: "html",
        async:false,
        success: function(html){
            $("#header").html(html);
        }
    })

}

function loadFooter(){
    $.ajax({
        url: "footer.html",
        cache: false,
        global: false,
        type: "GET",
        dataType: "html",
        async:false,
        success: function(html){
            $("#footer").html(html);
        }
    })
}

function loadLeftMenu(){
    $.ajax({
        url: "leftmenu.html",
        cache: false,
        global: false,
        type: "GET",
        dataType: "html",
        async:false,
        success: function(html){
            $(".top_content").append(html);
        }
    })
}

function loadUserLeftMenu(){
    $.ajax({
        url: "usercenter_leftmenu.html",
        cache: false,
        global: false,
        type: "GET",
        dataType: "html",
        async:false,
        success: function(html){
            $(".usercenter_content").append(html);
        }
    })
}

function loadSystemLeftMenu(){
    $.ajax({
        url: "system_leftmenu.html",
        cache: false,
        global: false,
        type: "GET",
        dataType: "html",
        async:false,
        success: function(html){
            $(".system_guide_content").append(html);
        }
    })
}

//加载用户中心顶部提示
function loadTopUserRemind(uname, astate, msg, mstate){
	var contentTopRemind = '<div class="userremind">'
		+ '<div class="biglogo"><img src="'+ctx+'/static/kingkaid/images/bigbankofxianlogo.png"></div>';
	var btn_open = '<a id="openxapay" class="openbtn" href="' + ctx + '/auth/cust/openpay_page.html" target="_blank">立即开通</a>';
	var btn_hit = '<a class="openbtn" href="' + ctx + '/auth/cust/openpay_page.html" target="_blank">立即激活</a>';
	var btn_bind = '<a id="corpopenpay" class="openbtn" href="' + msg + '" target="_blank">绑定银行卡</a>';
	var btn_update = '<a id="openxapay" class="openbtn" href="' + ctx + '/auth/cust/openpay_modify.html" target="_blank">修改开户资料</a>';
	if (mstate === '6') {
		contentTopRemind += '<p>' + uname+'，您的电子账户尚未激活。</p>' + btn_hit;
	} else if (mstate === '7') {
		contentTopRemind += '<p>' + uname+'，您变更了绑定的手机号码，请向电子账户转账任意金额以重新激活您的账户，详情见打款指引。</p>' + btn_hit;
	} else {
		switch(astate)
		{
		case '0':
			contentTopRemind += '<p>' + uname+'，您尚未开通银行存管电子账户，请您立即开通。</p>' + btn_open;
			break;
		case 'Z':
			contentTopRemind += '<p>' + uname+'，您的电子账户开户资料已经发送至西安银行，请将相关证件照片提交至您的客户经理处。</p>';
			break;
		case '1':
			contentTopRemind += '<p>' + uname+'，您的银行存管电子账户的开通申请正在被人工审核中，请耐心等待1-2个工作日。</p>' + btn_update;
			break;
		case '2':
			contentTopRemind += '<p>' + uname+'，您的银行存管电子账户的开通申请已经通过审核，请绑定银行卡以完成开户。</p>' + btn_bind;
			break;
		case '3':
			contentTopRemind += '<p>' + uname+'，您西安银行电子账户的开通申请未能通过审核，原因是：' + msg + '。</p>' + btn_open;
			break;
		default:
		}
	}
	contentTopRemind += '</div>';
    $(".usercenter_content").append(contentTopRemind);
    var msgP = $(".userremind p");
    var mul = Math.ceil(msgP.text().length / 50);
    mul = mul > 3 ? mul - 1 : mul;
    if (mul > 1) {
    	msgP.css({"width": "720px", "line-height": Math.ceil(50 / mul) + "px"});
    }
}

//function loadTopUserRemind(uname){
//    var contentTopRemind='<div style="font-size:16px;position: absolute;top:-60px;left: 0;">'+uname+'，您还未开通第三方支付账户，请<span class="quick_open_btn" style="cursor:pointer;" onclick="javascript:window.location.href=\''+ctx+'/app/resource/register.html?memberid=JKD0000000000001&requesttoken=123\';">立即开通</span>以确保您的正常使用和资金安全。</div>'
//    $(".usercenter_content").append(contentTopRemind);
//    $(".system_guide_content").append(contentTopRemind);
//    $(".user_top_alert").append(contentTopRemind);
//}

//环形进度条加载
function loadImg (data,id){
    var i = 0;
    var object = document.getElementById(id);
    setInterval(function(){
        i++
        if(i>data){
            i=data
        }
        var imgLeft = -(i*44+(i*10))+'px'
        object.style.backgroundPosition = imgLeft+'\t'+'0px'
        object.innerHTML = i+'%';
    },1)

}

function loadCercleData(){
    //环形进度条
    var cupCountArrea ={
        cupCount1:10,
        cupCount2:20,
        cupCount3:90,
        cupCount4:40,
        cupCount5:50,
        cupCount6:80,
        cupCount7:100
    };//进度条显示当前百分比

    var cercleCount = ($(".cerclebox").length)+1
    for(i=1;i<cercleCount;i++){
        loadImg(cupCountArrea["cupCount"+i],"progress"+i)
    }
}

//返回顶部
function goTopEx(){
    var backToTop = '<div style="display:none" id=goTopBtn><a href="#"><img border=0 src="'+ctx+'/static/kingkaid/images/back-top.jpg"/></a></div>';  
    $("body").append(backToTop);
	
$(window).scroll(function() {
	var scrollY = $(document).scrollTop();// 获取垂直滚动的距离，即滚动了多少
	if (scrollY > 100){ //如果滚动距离大于100px则显示，
		$('#goTopBtn').show();
	} 
	else {
		$('#goTopBtn').hide();//否则隐藏
	};	
	
});

$('#goTopBtn').click(function () {
$('html,body').animate({
scrollTop : '0px'
}, 200);//返回顶部所用的时间 
});	
	
};
	
	
	
	
	

function isInteger(str){
    var reg1 =  /^\d+$/;
    return reg1.test(str);
}


// 生成弹框半透明背景
function create_bg() {
    // 建立一个div的节点
    bg = document.createElement("div");
    bg.id = "dark_bg";
    with (bg.style) {
        position = "absolute";
        top = "0";
        left = "0";
        width = document.documentElement.scrollWidth + "px";
        if (document.documentElement.scrollHeight < document.documentElement.clientHeight) {
            height = document.documentElement.clientHeight + "px";
        } else {
            height = document.documentElement.scrollHeight + "px";
        }

    }
    document.body.appendChild(bg);
}

//close DIV
function closeDIV(boxid) {
    var dark_bg = document.getElementById("dark_bg");
        dark_bg.parentNode.removeChild(dark_bg);

        if(boxid=="new_dialogue"){

            var new_dialogue = document.getElementById("new_dialogue");
                new_dialogue.parentNode.removeChild(new_dialogue);
        }
        else{

            $("#"+boxid).css("display","none");
        }
        

}




//显示还款计划书弹窗
function showReturnPlan() {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var planhtml = "";
    planhtml ='<div class="alert_box_big">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l">SSCGL流动资金借款第一期</div>'
             +'       <div class="window_top_r">'
             +'           <img style="margin-top:16px;" src="'+ctx+'/static/kingkaid/images/download_btn.png" />'
             +'       </div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content" style="height:440px;">'
             +'       <table class="return_plan">'
             +'        <tr> '                                                                  
             +'             <th class="plan_tb_first_column">投资金额</th>'
             +'             <th style="width:100px;">年化利率</th>'
             +'               <th style="width:95px;">应收利息</th>'
             +'               <th style="width:100px;">应收本金</th>'
             +'               <th style="width:100px;">应收本息</th>'
             +'               <th style="width:93px;">还款期数</th>'
             +'               <th style="width:125px;">下期还款日</th>'
             +'               <th>状态</th>'
             +'           </tr>'
             +'           <tr>'                                                            
             +'               <td class="plan_tb_first_column">1000000</td>'
             +'               <td>8.64%</td>'
             +'               <td>52110.0 </td>'
             +'               <td>24242.00</td>'
             +'               <td><div class="red_text">24242.00</div></td>'
             +'               <td>6</td>'
             +'               <td>2020-03-20</td>'
             +'               <td><img src="'+ctx+'/static/kingkaid/images/right_over.png"/></td>'
             +'           </tr>'
             +'           <tr>'                                                            
             +'               <td class="plan_tb_first_column">1000000</td>'
             +'               <td>8.64%</td>'
             +'               <td>52110.0 </td>'
             +'               <td>24242.00</td>'
             +'               <td><div class="red_text">24242.00</div></td>'
             +'               <td>6</td>'
             +'               <td>2020-03-20</td>'
             +'               <td><img src="'+ctx+'/static/kingkaid/images/right_over.png"/></td>'
             +'           </tr>'
             +'           <tr>'                                                            
             +'               <td class="plan_tb_first_column">1000000</td>'
             +'               <td>8.64%</td>'
             +'               <td>52110.0 </td>'
             +'               <td>24242.00</td>'
             +'               <td><div class="red_text">24242.00</div></td>'
             +'               <td>6</td>'
             +'               <td>2020-03-20</td>'
             +'               <td><img src="'+ctx+'/static/kingkaid/images/right_over.png"/></td>'
             +'           </tr>'
              +'           <tr>'                                                            
             +'               <td class="plan_tb_first_column">1000000</td>'
             +'               <td>8.64%</td>'
             +'               <td>52110.0 </td>'
             +'               <td>24242.00</td>'
             +'               <td><div class="red_text">24242.00</div></td>'
             +'               <td>6</td>'
             +'               <td>2020-03-20</td>'
             +'               <td><img src="'+ctx+'/static/kingkaid/images/right_over.png"/></td>'
             +'           </tr>'
             +'           <tr>'                                                            
             +'               <td class="plan_tb_first_column">1000000</td>'
             +'               <td>8.64%</td>'
             +'               <td>52110.0 </td>'
             +'               <td>24242.00</td>'
             +'               <td><div class="red_text">24242.00</div></td>'
             +'               <td>6</td>'
             +'               <td>2020-03-20</td>'
             +'               <td><img src="'+ctx+'/static/kingkaid/images/right_over.png"/></td>'
             +'           </tr>'
             +'       </table>'
             +'   </div>'
             +'</div>'

        visual.innerHTML = planhtml;
    document.body.appendChild(visual);
}

//显示债权转让合同
function showClaimantAgreement() {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var planhtml = "";
    planhtml ='<div class="alert_box_big">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l"><B>债权转让合同</B></div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'        <div class="agreement_box">'
             +'            <div class="agreement_title">债权转让合同</div>'
             +'            <p>支付宝服务（以下简称本服务）是由支付宝（中国）网络技术有限公司（以下简称本公司）向支付宝用户提供的支付宝软件系统（以下简称本系统）及(或)附随的货款代收代付的中介服务。 本协议由您和本公司签订。</p>'
             +'            <p>'
             +'                   <div>一、声明与承诺</div>'
             +'                   <div>（一）本协议已对与您的权益有或可能具有重大关系的条款，及对本公司具有或可能具有免责或限制责任的条款用粗体字予以标注，请您注意。您确认，在您注册成为支付宝用户以接受本服 务，或您以其他本公司允许的方式实际使用本服务前，您已充分阅读、理解并接受本协议的全部内容，一旦您使用本服务，即表示您同意遵循本协议之所有约定。</div>'
             +'                  <div>  （二）您同意，本公司有权随时对本协议内容进行单方面的变更，并以在www.alipay.com网站公告的方式予以公布，无需另行单独通知您；若您在本协议内容公告变更后继续使用本服务的，表示您已充分 阅读、理解并接受修改后的协议内容，也将遵循修改后的协议内容使用本服务；若您不同意修改后的协议内容，您应停止使用本服务。</div>'
             +'                  <div>  （二）您同意，本公司有权随时对本协议内容进行单方面的变更，并以在www.alipay.com网站公告的方式予以公布，无需另行单独通知您；若您在本协议内容公告变更后继续使用本服务的，表示您已充分 阅读、理解并接受修改后的协议内容，也将遵循修改后的协议内容使用本服务；若您不同意修改后的协议内容，您应停止使用本服务。</div>'
             +'                  <div>  （二）您同意，本公司有权随时对本协议内容进行单方面的变更，并以在www.alipay.com网站公告的方式予以公布，无需另行单独通知您；若您在本协议内容公告变更后继续使用本服务的，表示您已充分 阅读、理解并接受修改后的协议内容，也将遵循修改后的协议内容使用本服务；若您不同意修改后的协议内容，您应停止使用本服务。</div>'
             +'                  <div>  （二）您同意，本公司有权随时对本协议内容进行单方面的变更，并以在www.alipay.com网站公告的方式予以公布，无需另行单独通知您；若您在本协议内容公告变更后继续使用本服务的，表示您已充分 阅读、理解并接受修改后的协议内容，也将遵循修改后的协议内容使用本服务；若您不同意修改后的协议内容，您应停止使用本服务。</div>'

              +'              </p>'    

             +'        </div>'
             +'   </div>'
             +'   <div class="window_bottom">'
             +'     <div style="float:right">'
             +'       <input type="checkbox"/><label class="blue-text" style="margin-right:30px;">我已阅读</label>'
             +'       <div class="blue_btn">确 认</div>'
             +'       <div class="gray_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">放 弃</div>'
             +'     </div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = planhtml;
    document.body.appendChild(visual);
}

//显示自动投标协议
/*function showTenderAgreement(vipEndTime) {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var planhtml = "";
    planhtml ='<div class="alert_box_big">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l">自动投标协议</div>'
             +'       <div class="window_top_r">VIP有效日期至'+vipEndTime+'</div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'        <div class="agreement_box">'
             +'            <div class="agreement_title">自动投标协议</div>'
             +'            <p>支付宝服务（以下简称本服务）是由支付宝（中国）网络技术有限公司（以下简称本公司）向支付宝用户提供的支付宝软件系统（以下简称本系统）及(或)附随的货款代收代付的中介服务。 本协议由您和本公司签订。</p>'
             +'            <p>'
             +'                   <div>一、声明与承诺</div>'
             +'                   <div>（一）本协议已对与您的权益有或可能具有重大关系的条款，及对本公司具有或可能具有免责或限制责任的条款用粗体字予以标注，请您注意。您确认，在您注册成为支付宝用户以接受本服 务，或您以其他本公司允许的方式实际使用本服务前，您已充分阅读、理解并接受本协议的全部内容，一旦您使用本服务，即表示您同意遵循本协议之所有约定。</div>'
             +'                  <div>  （二）您同意，本公司有权随时对本协议内容进行单方面的变更，并以在www.alipay.com网站公告的方式予以公布，无需另行单独通知您；若您在本协议内容公告变更后继续使用本服务的，表示您已充分 阅读、理解并接受修改后的协议内容，也将遵循修改后的协议内容使用本服务；若您不同意修改后的协议内容，您应停止使用本服务。</div>'
             +'                  <div>  （二）您同意，本公司有权随时对本协议内容进行单方面的变更，并以在www.alipay.com网站公告的方式予以公布，无需另行单独通知您；若您在本协议内容公告变更后继续使用本服务的，表示您已充分 阅读、理解并接受修改后的协议内容，也将遵循修改后的协议内容使用本服务；若您不同意修改后的协议内容，您应停止使用本服务。</div>'
             +'                  <div>  （二）您同意，本公司有权随时对本协议内容进行单方面的变更，并以在www.alipay.com网站公告的方式予以公布，无需另行单独通知您；若您在本协议内容公告变更后继续使用本服务的，表示您已充分 阅读、理解并接受修改后的协议内容，也将遵循修改后的协议内容使用本服务；若您不同意修改后的协议内容，您应停止使用本服务。</div>'
             +'                  <div>  （二）您同意，本公司有权随时对本协议内容进行单方面的变更，并以在www.alipay.com网站公告的方式予以公布，无需另行单独通知您；若您在本协议内容公告变更后继续使用本服务的，表示您已充分 阅读、理解并接受修改后的协议内容，也将遵循修改后的协议内容使用本服务；若您不同意修改后的协议内容，您应停止使用本服务。</div>'

              +'              </p>'    

             +'        </div>'
             +'   </div>'
             +'   <div class="window_bottom">'
             +'     <div style="float:right">'
             +'       <input id="read_chkbox" type="checkbox"/><label class="blue-text" style="margin-right:30px;">我已阅读</label>'
             +'       <div class="blue_btn" onclick="submit()">确 认</div>'
             +'       <div class="gray_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">放 弃</div>'
             +'     </div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = planhtml;
    document.body.appendChild(visual);
}*/

//显示信息确认框
function showInfoConfirmBox() {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var planhtml = "";
    planhtml ='<div class="alert_box_big">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l">担保确认信息</div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'     <div class="confirm_info_content">'
             +'        <table class="confirm_info_tb">'
             +'            <tr>'
             +'                <td class="confirm_left_title"><div>操作人：</div></td>'
             +'                <td>张三</td>'
             +'            </tr>'
             +'            <tr>'
             +'                <td class="confirm_left_title"><div>真实姓名：</div></td>'
             +'                <td>李四</td>'
             +'            </tr>'
             +'            <tr>'
             +'                <td class="confirm_left_title"><div>身份证号：</div></td>'
              +'                <td>612*****22222</td>'
             +'            </tr>'
             +'            <tr>'
             +'                <td class="confirm_left_title"><div>借款金额：</div></td>'
              +'                <td>600万</td>'
             +'            </tr>'
             +'            <tr>'
             +'                <td class="confirm_left_title"><div>借款期限：</div></td>'
              +'                <td>6个月</td>'
             +'            </tr>'
             +'            <tr>'
             +'                <td class="confirm_left_title"><div>借款利率：</div></td>'
              +'                <td>10%</td>'
             +'            </tr>'
             +'            <tr>'
             +'                <td class="confirm_left_title"><div>管理费率：</div></td>'
              +'                <td>1%</td>'
             +'            </tr>'
             +'            <tr>'
             +'                <td class="confirm_left_title"><div>借款用途：</div></td>'
              +'                <td>购房</td>'
             +'            </tr>'
             +'            <tr>'
             +'                <td class="confirm_left_title"><div>还款方式：</div></td>'
              +'                <td>按月还息</td>'
             +'            </tr>'
             +'            <tr>'
             +'                <td class="confirm_left_title"><div>担保机构：</div></td>'
              +'                <td>西安某某担保有限公司</td>'
             +'            </tr>'
             +'            <tr>'
             +'                <td class="confirm_left_title"><div>借款详情：</div></td>'
              +'                <td>借款详情</td>'
             +'            </tr>'

             +'        </table>'
             +'     </div>'
             +'   </div>'
             +'   <div class="window_bottom">'
             +'     <div style="float:right">'
             +'       <div class="blue_btn">确 认</div>'
             +'       <div class="gray_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">取 消</div>'
             +'     </div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = planhtml;
    document.body.appendChild(visual);
}

//计算器弹窗
function counterBox() {
     create_bg();
     var visual = document.createElement("div");
     visual.id = "new_dialogue";
     var InfoLookehtml = "";
     InfoLookehtml ='<div class="alert_box_small">'
              +'   <div class="window_top">'
              +'       <div class="window_top_l">收益计算</div>'
              +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
              +'   </div>'
              +'   <div class="window_content">'
              +'       <div class="operate_content" style="width:auto;">'
              +'         <div style="border-bottom:1px solid #e1e1e1;overflow:hidden;margin-bottom:20px;padding:0 0 20px 15px;">'
              +'           <div style="float:left"><span>投资金额：</span><input type="text" name="c_amount" id="c_amount" value="10000" class="window_input"/> 元</div>'
              +'           <div style="float:left;margin-left:30px;"><span>借款利率：</span><input type="text" name="c_rate" id="c_rate" value="9.36" class="window_input"/> %</div>'
              +'         </div>'
              +'         <div style="border-bottom:1px solid #e1e1e1;overflow:hidden;margin-bottom:20px;padding:0 0 20px 15px;">'
              +'           <div style="float:left"><span>投资期限：</span><select name="c_term" id="c_term" class="window_input" style="height:27px"><option value="1">1个月</option>'
              +'				<option value="2">2个月</option><option value="3">3个月</option><option value="4">4个月</option><option value="5">5个月</option>'
              +'				<option value="6">6个月</option><option value="7">7个月</option><option value="8">8个月</option><option value="9">9个月</option>'
              +'				<option value="10">10个月</option><option value="11">11个月</option><option value="12">12个月</option></select></div>'
              +'           <div style="float:left;margin-left:52px;"><span>还款方式：</span><select name="c_repaytype" id="c_repaytype" class="window_input" style="height:27px"><option value="301">按月付息到期还本</option><option value="300">等额本息</option><option value="302">等额本金</option><option value="299">一次性还本付息</option><option value="501">按日还款</option></select></div>'
              +'         </div>'
              +'       </div>'
              +'   </div>'
              +'    <div style="width:100%;height:100px;overflow-y:auto;">'
              +'	<table class="return_plan_tb" id="counter_return_plan" style="display:none;">'
              +'		<tr>'
              +'			<th style="width:100px;">'
              +'				<div class="rightline_th">还款期数</div>'
              +'			</th>'
              +'			<th style="width:100px;">'
              +'				<div class="rightline_th">应付本息（元）</div>'
              +'			</th>'
              +'			<th style="width:100px;">'
              +'				<div class="rightline_th">应付本金（元）</div>'
              +'			</th>'
              +'			<th style="width:100px;">'
              +'				<div class="rightline_th">应付利息（元）</div>'
              +'			</th>'
              +'		</tr>'
              +'	</table>'
              +'    </div>'
              +'   <div class="small_window_bottom" style="padding-top:10px;">'
              +'       <div style="float:left;margin-left:30px;"></div>'
              +'       <div class="blue_btn" style="float:right;margin-right:30px;" onclick="counter()">点击计算</div>'
              +'   </div>'
              +'</div>';
         visual.innerHTML = InfoLookehtml;
     document.body.appendChild(visual);
}


//操作成功状态
function showSuccessBox() {
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
             +'                 您已成功兑换商品，×××，我们将在7个工作日内为您寄出，请您耐心等待。'
             +'             </div>'
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             //+'       <div class="blue_btn">我知道了</div>'
             +'       <div class="blue_btn">确 认</div>'
             +'       <div class="gray_btn btn_right" onclick="closeDIV(&quot;new_dialogue&quot;)">取 消</div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = successhtml;
    document.body.appendChild(visual);
}


//操作提示状态
function showPromptBox() {
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
             +'                 您正在进行债权转让项目取消操作，确认取消后，您的债权转让项目将不再发布，您将继续持有该债权。'
             +'             </div>'
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             //+'       <div class="blue_btn">确 定</div>'
             +'       <div class="blue_btn">确 认</div>'
             +'       <div class="gray_btn btn_right" onclick="closeDIV(&quot;new_dialogue&quot;)">取 消</div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = prompthtml;
    document.body.appendChild(visual);
}

//操作失败弹窗
function showErrorBox() {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var errorthtml = "";
    errorthtml ='<div class="alert_box_small">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l">提示</div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'       <div class="operate_content">'
             +'             <div class="status_img">'
             +'                 <img src="'+ctx+'/static/kingkaid/images/wrong_bt.png"/>'
             +'             </div>'
             +'             <div class="result_msg">'
             +'                数据提交失败，请检查您的网络。数据提交失败，请检查您的网络，数据提交失败，请检查您的网络。'
             +'             </div>'
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             //+'       <div class="blue_btn">返 回</div>'
             +'       <div class="blue_btn">返 回</div>'
             +'       <div class="gray_btn btn_right" onclick="closeDIV(&quot;new_dialogue&quot;)">取 消</div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = errorthtml;
    document.body.appendChild(visual);
}

//系统消息弹窗
function showSystemMsgBox(messagecontents) {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var errorthtml = "";
    errorthtml ='<div class="alert_box_small">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l"><img style="float:left;margin:19px 10px 0 0;" src="'+ctx+'/static/kingkaid/images/broadcast.jpg"/>系统消息</div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'       <div class="operate_content">'
           
             + 	 messagecontents
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             +'       <div class="blue_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">已 阅</div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = errorthtml;
    document.body.appendChild(visual);
}
 
//项目消息弹窗
function showProjectMsgBox(messagecontents) {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var errorthtml = "";
    errorthtml ='<div class="alert_box_small">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l"><img style="float:left;margin:19px 10px 0 0;" src="'+ctx+'/static/kingkaid/images/broadcast.jpg"/>项目消息</div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'       <div class="operate_content">'
         
             + 	 messagecontents
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             +'       <div class="blue_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">已 阅</div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = errorthtml;
    document.body.appendChild(visual);
}

//活动消息弹窗
function showActiveMsgBox(messagecontents) {	
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var activehtml = "";
    activehtml ='<div class="alert_box_small">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l"><img style="float:left;margin:19px 10px 0 0;" src="'+ctx+'/static/kingkaid/images/broadcast.jpg"/>活动消息</div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'       <div class="operate_content">' 
             + 	 messagecontents
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             +'       <div class="blue_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">已 阅</div>'
             +'       <div class="window_bottom_link"><a href="myshare.html">邀请好友</a><a style="margin-left:20px;" href="'+ctx+'/auth/activity/my_lottery_page.html">我的奖券</a></div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = activehtml;
    document.body.appendChild(visual);
}

//充值提现
function showRechargeBox(messagecontents) {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var Rechargehtml = "";
    Rechargehtml ='<div class="alert_box_small">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l"><img style="float:left;margin:19px 10px 0 0;" src="'+ctx+'/static/kingkaid/images/broadcast.jpg"/>充值提现</div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'       <div class="operate_content" style="text-align:center">'
            // +'           账户余额变动（与项目不相关的）'
             + 	 messagecontents
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             +'       <div class="blue_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">已 阅</div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = Rechargehtml;
    document.body.appendChild(visual);
}


//信息识别
function showInfoLookBox() {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var InfoLookehtml = "";
    InfoLookehtml ='<div class="alert_box_small">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l">信息识别</div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'       <div class="operate_content" style="text-align:center">'
             +'           <div style="margin-bottom:20px;"><span>工号：</span><input type="text" class="window_input"/></div>'
             +'           <div style="margin-bottom:20px;"><span>密码：</span><input type="password" class="window_input"/></div>'
             +'           <div>提示：登录后请立即插入U-Key完成身份识别</div>'
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             +'       <div class="blue_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">登 录</div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = InfoLookehtml;
    document.body.appendChild(visual);
}



//充值提示
function showRechargeOverBox() {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var Rechargehtml = "";
    Rechargehtml ='<div class="alert_box_small">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l">提示</div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'       <div class="operate_content" style="text-align:center;line-height:36px;">'

             +'            <img style="margin:0 10px -12px 0;" src="'+ctx+'/static/kingkaid/images/warming_bt.png"/>'

             +'             <span style="font-size:20px;display:inline-block">'
             +'                完成充值前请不要关闭此窗口!'
             +'             </span>'
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom" style="height:auto">'
             +'       <div class="blue_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">已完成网银操作</div>'
             +'       <div class="gray_btn btn_right">遇到问题</div>'
             +'       <div class="pay_bottom_link"style=""><a>返回重新选t择充值方式</a></div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = Rechargehtml;
    document.body.appendChild(visual);
}

//完成购买VIP
function showCompleteVipBox() {
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
             +'                完成充值前请不要关闭此窗口!'
             +'             </span>'
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             +'       <div class="blue_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">已完成购买VIP操作</div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = buyViphtml;
    document.body.appendChild(visual);
}




//可使用奖券
function showCanUseCard() {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var Rechargehtml = "";
    Rechargehtml ='<div class="alert_box_small">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l">可使用奖券</div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'       <div class="operate_content" style="text-align:center">'
             +'             <select class="prompt_long_select" style="padding: 5px 0;">'
             +'                 <option>加息券：投一万加息0.3%</option>'
             +'             </select>'
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             +'       <div class="blue_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">下一步</div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = Rechargehtml;
    document.body.appendChild(visual);
}


//收藏本站代码
jQuery.fn.addFavorite = function(l, h) {
    return this.click(function() {
        var t = jQuery(this);
        if(jQuery.browser.msie) {
            window.external.addFavorite(h, l);
        } else if (jQuery.browser.mozilla || jQuery.browser.opera) {
            t.attr("rel", "sidebar");
            t.attr("title", l);
            t.attr("href", h);
        } else {
            alert("您的浏览器不支持自动加入收藏，请使用Ctrl+D将本页加入收藏夹！");
        }
    });
};
$(function(){
    $('#fav').addFavorite('金开贷',location.href);
});

function showContAgreement(loanid,conttype) {
	create_bg();
	var visual = document.createElement("div");
	visual.id = "new_dialogue";
	var contagrname = '';
	var contname = '';
	var cont_type = '';
	if("1"==conttype){//1:投资协议，2：债权转让协议
		contagrname = '投资协议';
		contname = '出借合同';
		cont_type = "10";
		
	}else if("2"==conttype){
		contagrname = '债权转让协议';
		contname = '债权转让合同';
		cont_type = '9';
	}
	var planhtml = "";
	planhtml ='<div class="alert_box_big_cont">'

			+'   <div class="window_top">'
			+'       <div class="window_top_l">'+contagrname+'</div>'
			+'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
			+'   </div>'
			+'   <div class="window_content">'
			+'        <div class="agreement_box_cont">'
			+'				<div class="agreement_title"><B>风险提示书</B></div>'
			+'				<div class="agreement_box_cont_in">'
			+'					<div id="riskinfo"></div>'
			+'				</div><br/>'
			+'				<div class="agreement_title"><B>'+contname+'</B></div>'
			+'				<div class="agreement_box_cont_in">'
			+'					<div id="loancontinfo"></div>'
			+'				</div><br/>'
			+'				<div class="agreement_title"><B>催款授权委托书</B></div>'
			+'				<div class="agreement_box_cont_in">'
			+'					<div id="proxyinfo"></div>'
			+'				</div>'
			+'        </div>'
			+'   </div>'
			+'   <div class="window_bottom">'
			+'     <div style="float:right">'
			+'       <input id="cont_read_chkbox" type="checkbox"/><label class="blue-text" style="margin-right:30px;">我已阅读并同意签署《风险提示书》、《'+contname+'》、《催款授权委托书》投资协议</label>'
			+'       <div class="blue_btn" onclick="contAgreementOk()">确 认</div>'
			+'       <div class="gray_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">放 弃</div>'
			+'     </div>'
			+'   </div>'
			+'</div>';
	
		visual.innerHTML = planhtml;
		document.body.appendChild(visual);

//获取并加载合同内容  risk_type：5（风险提示书）， cont_type：10（投资协议）11（债券转让协议）， proxy_type：13（催收授权委托书）{取值参考：cont_type}

		$.ajax({
			type: "POST",
			url: ctx+'/auth/cont/continfoconfirm',
			data: {"risk_type":"5","cont_type":cont_type,"proxy_type":"13","loanid":loanid},
			dataType: "json",
			success: function(data) {
				if(data.respcode && data.respcode == 'S') {//回调成功
					var riskinfo = data.riskinfo;
					var loancontinfo = data.loancontinfo;
					var proxycontinfo = data.proxycontinfo;
					$("#riskinfo").empty();
					$("#riskinfo").append(riskinfo);
					$("#loancontinfo").empty();
					$("#loancontinfo").append(loancontinfo);
					$("#proxyinfo").empty();
					$("#proxyinfo").append(proxycontinfo);
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

function contAgreementOk(){
	if($("#cont_read_chkbox").attr('checked')){
		closeBox();
		contConfimOk();
	}else{
		alert("请阅读协议并确认已阅读！");
	}
}

//计算器弹窗
function ctpOpenPay() {
	create_bg();
	$.ajax({
		type:'post',
		url:ctx+'/auth/cust/cust_info.html',
		dataType:'json',
		success:function(data){
			if(data.isactive != '1' && data.memberstate != '8'){	//未激活
				var visual = document.createElement("div");
			     visual.id = "new_dialogue";
			     var InfoLookehtml = "";
			     InfoLookehtml ='<div class="alert_box_small" style="width:900px;margin-left:-430px;">'
			              +'   <div class="window_top">'
			              +'       <div class="window_top_l">开通西安银行电子支付账户</div>'
			              +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
			              +'   </div>'
			              +'   <div class="window_content">'
			              +'       <div class="operate_content" style="width:auto;">'
			              +'         <div style="overflow:hidden;margin-bottom:20px;padding:0 0 5px 5px;">'
			              +'           <div style="float:left;width:400px;"><span style="display:block;width:100px;position:absolute;text-align:right;font-size:16px;">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span><input type="text" name="ID_NAME" id="ID_NAME" value="'+data.custname+'" class="window_input" style="margin-left:100px;width:240px;height:30px;font-weight:bold;font-size:16px;color:#669;padding-left:10px;"/></div>'
			              +'           <div style="float:left;color:#abc;"><span>请输入您的真实姓名 *</span></div>'
			              +'         </div>'
			              +'         <div style="overflow:hidden;margin-bottom:20px;padding:0 0 5px 5px;">'
			              +'           <div style="float:left;width:400px;"><span style="display:block;width:100px;position:absolute;text-align:right;font-size:16px;">证件号码：</span><input type="text" name="ID_NO" id="ID_NO" value="'+data.paperid+'" class="window_input" style="margin-left:100px;width:240px;height:30px;font-weight:bold;font-size:16px;color:#669;padding-left:10px;"/></div>'
			              +'           <div style="float:left;color:#abc;"><span>请输入您的真实身份证号码 *</span></div>'
			              +'         </div>'
			              +'         <div style="overflow:hidden;margin-bottom:20px;padding:0 0 5px 5px;">'
			              +'           <div style="float:left;width:400px;"><span style="display:block;width:100px;position:absolute;text-align:right;font-size:16px;">银行卡号：</span><input type="text" name="ACCT_NO" id="ACCT_NO" value="" class="window_input" style="margin-left:100px;width:240px;height:30px;font-weight:bold;font-size:16px;color:#669;padding-left:10px;" onchange="ctp_cardcertify();"/></div>'
			              +'           <div style="float:left;color:#abc;"><span>开通电子账号必须绑定一张银行卡*</span></div>'
			              +'         </div>'
			              +'         <div id="ctp_bank_name" style="overflow:hidden;margin-bottom:20px;padding:0 0 5px 5px;display:none;">'
			              +'           <div style="float:left;width:400px;"><span style="display:block;width:100px;position:absolute;text-align:right;font-size:16px;">银行名称：</span><input type="text" name="ctp_bankname" id="ctp_bankname" value="" class="window_input" readonly style="margin-left:100px;width:240px;height:30px;font-weight:bold;font-size:16px;color:#669;padding-left:10px;border:none;"/></div>'
			              +'           <div style="float:left;color:#abc;"><span></span></div>'
			              +'         </div>'
			              +'         <div style="overflow:hidden;margin-bottom:20px;padding:0 0 5px 5px;">'
			              +'           <div style="float:left;width:400px;"><span style="display:block;width:100px;position:absolute;text-align:right;font-size:16px;">支付密码：</span><input type="password" name="TRANS_PWD" id="TRANS_PWD" value="" class="window_input" style="margin-left:100px;width:240px;height:30px;font-weight:bold;font-size:16px;color:#669;padding-left:10px;"/></div>'
			              +'           <div style="float:left;color:#abc;"><span>请设置您的资金交易密码 *</span></div>'
			              +'         </div>'
			              +'         <div style="overflow:hidden;margin-bottom:20px;padding:0 0 5px 5px;">'
			              +'           <div style="float:left;width:400px;"><span style="display:block;width:100px;position:absolute;text-align:right;font-size:16px;">确认密码：</span><input type="password" name="TRANS_PWD_P" id="TRANS_PWD_P" value="" class="window_input" style="margin-left:100px;width:240px;height:30px;font-weight:bold;font-size:16px;color:#669;padding-left:10px;"/></div>'
			              +'           <div style="float:left;color:#abc;"><span>请再次输入您的资金交易密码*</span></div>'
			              +'         </div>'              
			              +'         <div style="overflow:hidden;margin-bottom:20px;padding:0 0 5px 5px;">'
			              +'           <div style="float:left;width:400px;"><span style="display:block;width:100px;position:absolute;text-align:right;font-size:16px;">手机号码：</span><input type="text" name="MOBILE" id="MOBILE" value="'+data.mobilenumber+'" class="window_input" style="margin-left:100px;width:240px;height:30px;font-weight:bold;font-size:16px;color:#669;padding-left:10px;"/></div>'
			              +'           <div style="float:left;color:#abc;"><span>请输入您的的手机号码*</span></div>'
			              +'         </div>'
			              +'         <div style="overflow:hidden;margin-bottom:20px;padding:0 0 5px 5px;border-bottom:1px solid #ccc;">'
			              +'           <div style="float:left;width:400px;"><span style="display:block;width:100px;position:absolute;text-align:right;font-size:16px;">验证码：</span><input type="text" name="CHK_CODE" id="CHK_CODE" value="" class="window_input" style="margin-left:100px;width:140px;height:30px;font-weight:bold;font-size:16px;color:#669;padding-left:10px;"/><div class="blue_btn" style="margin-left:10px;padding:0 10px;" onclick="ctp_sendopenpay_msg()">点击发送</div></div>'
			              +'           <div style="float:left;color:#abc;"><span></span></div>'
			              +'         </div>'
			              +'       </div>'
			              +'   </div>'
			              +'   <div class="small_window_bottom" style="padding-top:10px;">'
			              +'       <div class="blue_btn" style="float:left;margin-left:30px;width:260px;" onclick="ctp_openPay_by_xabank()">确认开户</div>'
			              +'   </div>'
			              +'</div>';
			         visual.innerHTML = InfoLookehtml;
			         document.body.appendChild(visual);
			}else if(data.isactive == '1' && (data.memberstate == '6' || data.memberstate == '4' || data.memberstate == '5')){
				var visual = document.createElement("div");
			     visual.id = "new_dialogue";
			     var InfoLookehtml = "";
			     InfoLookehtml ='<div class="alert_box_small" style="width:900px;margin-left:-430px;">'
			              +'   <div class="window_top">'
			              +'       <div class="window_top_l">开通西安银行电子支付账户</div>'
			              +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
			              +'   </div>'
			              +'   <div class="window_content">'
			              +'       <div class="operate_content" style="width:auto;">'
			              +'         <div style="overflow:hidden;margin-bottom:20px;padding:0 0 5px 5px;">'
			              +'           <div style="margin-left:30px;color:#789;margin-bottom:30px;"><span style="font-size:28px;color:#369;">您的电子银行账号注册成功！</span><br/></div>'
			              +'           <div style="margin-left:30px;color:#789;"><span>您在西安银行的电子银行账号已开通,电子账号为  ： <font style="font-size:18px;color:#346;">'+data.custacno+'</font>。</span></div>'
			              +'           <div style="margin-left:30px;color:#789;"><span>请你通过您注册时绑定的银行卡 <font style="font-size:18px;color:#346;">'+data.bankcode+'</font> 向您的西安银行电子银行账号中打款不少于0.01元用于激活您的电子支付账户。你打款的金额会同步更新到您的电子支付账户余额。</span></div>'
			              +'         </div>'
			              +'   </div>'
			              +'   <div class="small_window_bottom" style="padding-top:10px;">'
			              +'       <div class="blue_btn" style="float:left;margin-left:30px;width:260px;font-size:20px;" onclick="closeDIV(&quot;new_dialogue&quot;)">关&nbsp;&nbsp;&nbsp;&nbsp;闭</div>'
			              +'   </div>'
			              +'</div>';
			         visual.innerHTML = InfoLookehtml;
			         document.body.appendChild(visual);
			}else{
				closeDIV("new_dialogue");
			}
		}
	}); 
}

function ctp_custinfo(){
	$.ajax({
		type:'post',
		url:ctx+'/auth/cust/cust_info.html',
		dataType:'json',
		success:function(data){
			
		}
	});
}

function ctp_sendopenpay_msg(){
	if($("#MOBILE").val()!=""){
		$.ajax({
			type:'post',
			url:ctx+'/auth/cust/sendopenmsg.html',
			data:{mobile:$("#MOBILE").val()},
			dataType:'json',
			success:function(data){
				alert(data.msg);
			}
		});
	}
}

function ctp_cardcertify(){
	if($("#ACCT_NO").val()!=""){
		$.ajax({
			type:'post',
			url:ctx+'/auth/cust/bank_certify.html',
			data:{acctno:$("#ACCT_NO").val()},
			dataType:'json',
			success:function(data){
				if(data.code == "s"){
					$("#ctp_bank_name").show();
					$("#ctp_bankname").val(data.bankname);
				}else{
					alert(data.msg);
				}
			}
		});
	}
}

function ctp_openPay_by_xabank(){
	$.ajax({
		type:'post',
		url:ctx+'/auth/cust/openpay.html',
		data:{ID_NAME:$("#ID_NAME").val(),ID_NO:$("#ID_NO").val(),ACCT_NO:$("#ACCT_NO").val(),CHK_CODE:$("#CHK_CODE").val(),TRANS_PWD:$("#TRANS_PWD").val()},
		dataType:'json',
		success:function(data){
			if(data.respcode == 'S'){
				window.location.reload(true);				
			}else{
				alert(data.respdesc);
				ctpOpenPay();
			}
		}
	});
}
//操作温馨提示
function showPromptNewBox(row) {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var prompthtml = "";
    prompthtml ='<div class="alert_box_small">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l">温馨提示</div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'       <div class="operate_content">'
             +'             <div class="status_img">'
             +'                 <img src="'+ctx+'/static/kingkaid/images/warming_bt.png"/>'
             +'             </div>'
             +'             <div class="result_msg">'
             +row
             +'             </div>'
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             +'       <div class="blue_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">确 认</div>'
//             +'       <div class="gray_btn btn_right" onclick="closeDIV(&quot;new_dialogue&quot;)">取 消</div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = prompthtml;
    document.body.appendChild(visual);
}

function nloginfun(){
    document.getElementById("login-btn").style.display="block";
    document.getElementById("nlogin").style.color="#999";
    document.getElementById("nregis").style.color="#5d89c9";
    document.getElementById("register-btn").style.display="none";
    document.getElementById("nlogin").style.background="url(" + ctx + "/static/kingkaid/images/log_bot.png) no-repeat bottom center";
    document.getElementById("nregis").style.background="none";
}

function nregisfun(){
    document.getElementById("login-btn").style.display="none";
    document.getElementById("nregis").style.color="#999";
    document.getElementById("nlogin").style.color="#5d89c9";
    document.getElementById("register-btn").style.display="block";
    document.getElementById("nregis").style.background="url(" + ctx + "/static/kingkaid/images/log_bot.png) no-repeat bottom center";
    document.getElementById("nlogin").style.background="none";
}