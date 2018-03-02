<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<link type="text/css" rel="stylesheet" href="${ctx}/static/kingkaid/css/guidecss.css?v=20170327" />
<title>金开贷</title>
<style>
body{
	background: #e7f2f7;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	$(".refresh").click(function(){
		window.location.reload();
	})
	
	$("#phone_bank").click(function(){
		$(this).removeClass('opacity');
		$("#one").css('background','url(${ctx}/static/kingkaid/openpay/image/blueborder.png) no-repeat bottom center');
		$("#two,#three,#four").css('background','none');
		$("#phone_bank1").css('display','block');
		$("#cyber_bank2,#atm_bank3,#counter_bank4").css('display','none');
		$("#cyber_bank,#atm_bank,#counter_bank").addClass('opacity');
	    });
	  $("#cyber_bank").click(function(){
		$(this).removeClass('opacity');
		$("#two").css('background','url(${ctx}/static/kingkaid/openpay/image/blueborder.png) no-repeat bottom center');
		$("#one,#three,#four").css('background','none');
		$("#cyber_bank2").css('display','block');
		$("#phone_bank1,#atm_bank3,#counter_bank4").css('display','none');
		$("#phone_bank,#atm_bank,#counter_bank").addClass('opacity');
	    });
	  $("#counter_bank").click(function(){
		$(this).removeClass('opacity');
		$("#four").css('background','url(${ctx}/static/kingkaid/openpay/image/blueborder.png) no-repeat bottom center');
		$("#one,#three,#two").css('background','none');
		$("#counter_bank4").css('display','block');
		$("#cyber_bank2,#atm_bank3,#phone_bank1").css('display','none');
		$("#cyber_bank,#atm_bank,#phone_bank").addClass('opacity');
	    });
	  
	  $("#deskprint").click(function(){
			 var printData = document.getElementById("counter_bank4").innerHTML;
			 window.document.body.innerHTML = printData;
			 window.print(); 
			 window.location.reload();
		  });
});


function doSign(regamt,custacno,custname,bankcode,bankname){

	$.ajax({
		type:'POST',
		url:ctx + '/auth/cust/openpaysign',
		data:{TRAN_AMT:regamt,ELE_ACCT_NO:custacno,RCV_NAME:custname,RCV_ACCT:bankcode,RCV_BANK:bankname},
		dataType:'json',
		success:function(data){
			if(data.respcode=="S"){
				window.location.reload(true);
			}
		}
	});  
	
};

</script>
</head>
 
<body>
<div id="header">
	<div class="top_content">
	    <div class="top-left">
	        <div style="float:left;">客服电话：400-1888-136 , 400-6020-603</div>
	        <div style="color:#f35b64;margin-left:20px;float:left;">服务时间 : 09:00 - 17:00 (工作日) </div>
	        <div class="weixin">
	            <img src="${ctx}/static/kingkaid/images/wx.png"/>
	            <div class="winxin_hover"><img src="${ctx}/static/kingkaid/images/kingweixin.png" /></div>
	        </div>
	        <div class="webo">
	            <img src="${ctx}/static/kingkaid/images/wb.png"/>
	            <div class="webo_hover">
	                <div>点击关注新浪微博</div>
	                <div><a href="http://weibo.com/jinkaidai">@金开贷</a></div>
	            </div>
	        </div>
	        <div class="qq">
	            <img src="${ctx}/static/kingkaid/images/cqq.png"/>
	            <div class="qq_hover"><img src="${ctx}/static/kingkaid/images/kingqq.png" /></div>
	        </div>
	        <div style="float:left;margin:11px 15px 0 5px;">
	            <a href="${ctx}/website/mobdown.html" target="_blank"><img src="${ctx}/static/kingkaid/images/app-download.png"/></a>
	        </div>
			<div style="float:left;">建议您使用IE9以上版本浏览器</div>
	    </div>
	    <div class="top-right">
           <shiro:authenticated>
                <ul>
                    <li>你好，<shiro:principal property="mobileNumber"/></li>
                    <li><a href="${ctx}/logout">退出</a></li>
                </ul>
           </shiro:authenticated>
	        <shiro:guest>
		        <ul>
		            <li><a href="${ctx}/login.html">登录</a></li>
		            <li><a href="${ctx}/member/register.html">注册</a></li>
		        </ul>
	        </shiro:guest>
	    </div>
	    <div style="clear:both;"></div>
 	</div>
</div>    
<div style="clear:both"></div>


 <!--西安银行账户激活引导-->
 <div class="guidbox" id="guidbox">
  <!--1.步骤-->
	  <div class="guidTop">
			<span class="pdl30"><a href="${ctx}/"><img src="${ctx}/static/kingkaid/openpay/image/jkdnewlog-.png"/></a></span>
			<span class="accoungname">电子账户认证</span>
	  </div>
	  <div class="instruc">
	  <p class="open_tit" style="line-height:82px">
	  	<span class="ggray">开通电子账户</span>
	  	<img src="${ctx}/static/kingkaid/openpay/image/direc-.png"/>
	  	<span class="gblue open_position">激活电子账户<span class="blueborder"><img src="${ctx}/static/kingkaid/openpay/image/blueborder.png"/></span></span>
	  	<img src="${ctx}/static/kingkaid/openpay/image/direc-.png"/>
	  	<span class="ggray">完成认证</span>
	  </p>
	  </div>
	  <!--账户信息-->
	  <div class="gacount_info">
	  	<div class="gacount_in">
	  	    <p class="bor_bot">您的电子账户已设立，信息如下：</p>
	  	    <div class="per_info">
				<p>
					<span class="accwid">电子账号：</span>
					<span class="accountnum accred bor_bot">${formData.custacno}</span>
				</p>
				<p>
					<span class="accwid">开户名：</span>
					<span class="accountnum bor_bot">${formData.custname}</span>
				</p>
				<p>
					<span class="accwid">开户行：</span>
					<span class="accountnum bor_bot">西安银行南大街支行</span>
				</p>
	  		</div>
	  	</div>
	  </div>
	  
 <!--2.激活方式-->
 	  <div class="acc_tittle">
 	  	<div class="bot_border">
 	  		<p class="accbot_txt">通过以下<span class="accred" style="font-size:28px;padding:14px;">任一</span>方式<span class="accred" style="padding:10px;">转账</span>激活电子账户</p>
 	  	</div>
 	  </div>
 	  <!--选项-->
 	  <div class="accmode">
 	  		<ul class="tabmenu">
 	  			<li id="one" class="one">
 	  				<a id="phone_bank" class="na_font">
						<span class="bank_type">手机银行</span>
	  				 	<div><img src="${ctx}/static/kingkaid/openpay/image/phone.png"/></div>
	  				 	<span class="bank_intro">通过银行APP转账完成激活即时完成操作，安全快捷</span>
 	  				</a>
 	  			</li>
 	  			<li id="two" class="">
 	  				<a id="cyber_bank"	class="opacity">
 	  					<span class="bank_type">网上银行</span>
	  				 	<div><img src="${ctx}/static/kingkaid/openpay/image/webbank.png"/></div>
	  				 	<span class="bank_intro">通过电脑登录网上银行U盾或动态密码器保护，安全方便</span>
 	  				</a>
 	  			</li>
				<li id="four">
	  				<a id="counter_bank"	class="opacity"	>
		  				<span class="bank_type">银行柜台</span>
	  				 	<div><img src="${ctx}/static/kingkaid/openpay/image/BANK.png"/></div>
	  				 	<span class="bank_intro">绑定银行卡的银行柜台转账方便互联网操作不熟练用户</span>
	  				</a>
	  			</li>
 	  		</ul>
 	  		<div class="content">
				<div class="phone_bank1" id="phone_bank1">
					<div class="phoneguide"><p class="guidetxt">手机银行操作流程</p></div>
					<div class="x_img">
						<div class="x_img1">
							<div class="fl"><img src="${ctx}/static/kingkaid/openpay/image/x_img1.png"/></div>
							<div class="txtphone fr tgl">
								<p class=" padt30">1.打开 <span class="accred">${formData.bankname}APP</span> </p>
								<p class="txtin">登录您的绑定银行账户：<span class="accred">尾号${bankcode}</span> </p>
								<p class="txtin lih90"><span class="accred">重要提示：</span>您所使用的银行卡需开通手机银行功能才可完成操作</p>
							</div>
						</div>
						<div class="pointimg fr"><img src="${ctx}/static/kingkaid/openpay/image/point.png"/></div>
					</div>
					<div class="x_img">
						<div class="x_img1">
							<div class="fl x_img2"><img src="${ctx}/static/kingkaid/openpay/image/x_img2.png"/></div>
							<div class="txtphone fr tgl">
								<p class="padt30">2.选择 <span class="accred">转账</span>  功能  </p>
								<p class="txtin"> 选择  <span class="accred">跨行转账 </span> </p>
							</div>
						</div>
						<div class="pointimg fr"><img src="${ctx}/static/kingkaid/openpay/image/point.png"/></div>
					</div>
					<div class="x_img">
						<div class="x_img1">
							<div class="fl x_img2"><img src="${ctx}/static/kingkaid/openpay/image/x_img3.png"/></div>
							<div class="txtphone fr tgl">
								<p>3. 输入 <span class="accred">转账信息：</span></p>
								<p class="txtin"><p class="wid112">收款账号</p><span class="accweight lih34 pdl20">${formData.custacno}</span></p>
								<p class="txtin"><p class="wid112">收款方户名</p><span class="accweight lih34 pdl20">${formData.custname}</span> （您本人姓名）</p>		
								<p class="txtin"><p class="wid112">选择开户行</p><span class="accweight lih34 pdl20">西安银行南大街支行</span></p>
								<p class="txtin"><p class="wid112">转账金额</p><span class="accweight lih34 pdl20">任意</span></p>
								<p class="txtin">完成转账 </p>
								<p class="txtin"><span class="accred lih34">（所转金额将计入您的金开贷账户余额）</span></p>
							</div>
						</div>           
						<div class="pointimg fr padt30"><img src="${ctx}/static/kingkaid/openpay/image/point.png"/></div>
					</div>
					<div class="x_img">
						<div class="x_img1">
							<div class="fl x_img2"><img src="${ctx}/static/kingkaid/openpay/image/x_img4.png"/></div>
							<div class="txtphone fr tgl">
								<p class="txtin lih34">4.转账金额到账并验证成功</p>
								<p class="txtin lih34">收到 电子账户激活成功<span class="accred">  短信提示</span></p>
								<p class="txtin lih34"> <span class="accred">刷新</span>页面查看认证状态</p>
								<div style="clear: both;"></div>
								<div class="x_blbtn fl"><a style="color:#fff" class="refresh">我已收到激活成功短信，刷新查看状态</a></div>
							</div>
						</div>
						 
					</div>
					
					          

				</div>
				<div style="clear:both;"></div>
				
				<div class="cyber_bank2" id="cyber_bank2">
					<div class="phoneguide"><p class="guidetxt">网上银行操作流程</p></div>
					<div class="x_img">
						<div class="x_img1">
							<div class="fl"><img src="${ctx}/static/kingkaid/openpay/image/webbank1.png"/></div>
							<div class="txtphone fr tgl">
								<p class=" padt30">1.登录 ${formData.bankname}网站并登入 <span class="accred">尾号${bankcode}</span> 的个人网上银行</p>
								<p class="lih34 txtin">（部分银行通过插入并读取U盾即可登录个人网银）</p>
								<p class="txtin lih90"><span class="accred">重要提示：</span>您所使用的银行卡需开通网上银行功能才可完成操作</p>
							</div>
						</div>
						<div class="pointimg fr"><img src="${ctx}/static/kingkaid/openpay/image/point.png"/></div>
					</div>
					<div class="x_img">
						<div class="x_img1">
							<div class="fl"><img src="${ctx}/static/kingkaid/openpay/image/webbank2.png"/></div>
							<div class="txtphone fr tgl">
								<p class="padt30">2.选择 <span class="accred">转账</span>  功能  </p>
								<p class="txtin"> 选择  <span class="accred">跨行转账 </span> </p>
							</div>
						</div>
						<div class="pointimg fr"><img src="${ctx}/static/kingkaid/openpay/image/point.png"/></div>
					</div>
					<div class="x_img">
						<div class="x_img1">
							<div class="fl"><img src="${ctx}/static/kingkaid/openpay/image/webbank3.png"/></div>
							<div class="txtphone fr tgl">
								<p>3. 输入 <span class="accred">转账信息：</span></p>
								<p class="txtin"><p class="wid112">收款账号</p><span class="accweight lih34 pdl20">${formData.custacno}</span></p>
								<p class="txtin"><p class="wid112">收款方户名</p><span class="accweight lih34 pdl20">${formData.custname}</span> （您本人姓名）</p>		
								<p class="txtin"><p class="wid112">选择开户行</p><span class="accweight lih34 pdl20">西安银行南大街支行</span></p>
								<p class="txtin"><p class="wid112">转账金额</p><span class="accweight lih34 pdl20">任意</span></p>
								<p class="txtin"> 根据网银操作要求完成转账 </p>
								<p class="txtin"><span class="accred lih34">（所转金额将计入您的金开贷账户余额）</span></p>
							</div>
						</div>           
						<div class="pointimg fr padt30"><img src="${ctx}/static/kingkaid/openpay/image/point.png"/></div>
					</div>
					<div class="x_img">
						<div class="x_img1">
							<div class="fl"><img src="${ctx}/static/kingkaid/openpay/image/x_img4.png"/></div>
							<div class="txtphone fr tgl">
								<p class="txtin lih34">4.转账金额到账并验证成功</p>
								<p class="txtin lih34">收到 电子账户激活成功<span class="accred">  短信提示</span></p>
								<p class="txtin lih34"> <span class="accred">刷新</span>页面查看认证状态</p>
								<div style="clear: both;"></div>
								<div class="x_blbtn fl"><a style="color:#fff" class="refresh">我已收到激活成功短信，刷新查看状态</a></div>
							</div>
						</div>           
					</div>

				</div>
<%-- 				<div class="atm_bank3" id="atm_bank3">
					<div class="phoneguide"><p class="guidetxt">ATM机操作流程</p></div>
					<div class="x_img">
						<div class="x_img1">
							<div class="fl"><img src="${ctx}/static/kingkaid/openpay/image/atm1.png"/></div>
							<div class="txtphone fr tgl">
								<p>1. 记录您所需的 <span class="accred">转账信息：</span></p>
								<p class="txtin"><p class="wid112">收款账号</p><span class="accweight lih34 pdl20">${formData.custacno}</span></p>
								<p class="txtin"><p class="wid112">收款方户名</p><span class="accweight lih34 pdl20">${formData.custname}</span> （您本人姓名）</p>		
								<p class="txtin"><p class="wid112">选择开户行</p><span class="accweight lih34 pdl20">西安银行南大街支行</span></p>
								<p class="txtin"><p class="wid112">转账金额</p><span class="accweight lih34 pdl20">任意</span></p>
								<p class="txtin"><span class="accred lih34">（所转金额将计入您的金开贷账户余额）</span></p>
							</div>
						</div>
						<div class="pointimg fr"><img src="${ctx}/static/kingkaid/openpay/image/point.png"/></div>
					</div>
					<div class="x_img">
						<div class="x_img1">
							<div class="fl"><img src="${ctx}/static/kingkaid/openpay/image/atm2.png"/></div>
							<div class="txtphone fr tgl">
								<p class="padt30">2.选择 <span class="accred">${formData.bankname}</span>ATM机</p>
								<p class="txtin">  使用<span class="accred">尾号${bankcode}</span> 的银行卡进行转账操作 </p>
							</div> 
						</div>
						<div class="pointimg fr"><img src="${ctx}/static/kingkaid/openpay/image/point.png"/></div>
					</div>
					<div class="x_img">
						<div class="x_img1">
							<div class="fl"><img src="${ctx}/static/kingkaid/openpay/image/atm3.png"/></div>
							<div class="txtphone fr tgl">
								<p>3. 根据ATM机页面提示录入转账信息并完成转账</p>
								<p><span class="accred">（所转金额将计入您的金开贷账户余额）</span></p>
							</div>
						</div>           
						<div class="pointimg fr padt30"><img src="${ctx}/static/kingkaid/openpay/image/point.png"/></div>
					</div>
					<div class="x_img">
						<div class="x_img1">
							<div class="fl"><img src="${ctx}/static/kingkaid/openpay/image/x_img4.png"/></div>
							<div class="txtphone fr tgl">
								<p class="txtin lih34">4.24小时后转账金额到账并验证成功</p>
								<p class="txtin lih34">收到 电子账户激活成功<span class="accred">  短信提示</span></p>
								<p class="txtin lih34"> <span class="accred">重新登录 </span>金开贷账户查看认证状态</p>
								<div style="clear: both;"></div>
								<div id="atmprint" class="x_blbtn fl">打印转账信息</div>
							</div>
						</div>           
					</div>

				</div> --%>

				</div>
				<div class="counter_bank4" id="counter_bank4">
					<div class="phoneguide"><p class="guidetxt">柜台操作流程</p></div>
					<div class="x_img">
						<div class="x_img1">
							<div class="fl"><img src="${ctx}/static/kingkaid/openpay/image/atm1.png"/></div>
							<div class="txtphone fr tgl">
								<p>1. 记录您所需的 <span class="accred">转账信息：</span></p>
								<p class="txtin"><p class="wid112">收款账号</p><span class="accweight lih34 pdl20">${formData.custacno}</span></p>
								<p class="txtin"><p class="wid112">收款方户名</p><span class="accweight lih34 pdl20">${formData.custname}</span> （您本人姓名）</p>		
								<p class="txtin"><p class="wid112">选择开户行</p><span class="accweight lih34 pdl20">西安银行南大街支行</span></p>
								<p class="txtin"><p class="wid112">转账金额</p><span class="accweight lih34 pdl20">任意</span></p>
								<p class="txtin"><span class="accred lih34">（所转金额将计入您的金开贷账户余额）</span></p>
							</div>
						</div>
						<div class="pointimg fr"><img src="${ctx}/static/kingkaid/openpay/image/point.png"/></div>
					</div>
					<div class="x_img">
						<div class="x_img1">
							<div class="fl"><img src="${ctx}/static/kingkaid/openpay/image/cyber2.png"/></div>
							<div class="txtphone fr tgl">
								<p class="padt30">2.前往<span class="accred">${formData.bankname}</span></p>
								<p class="txtin">  使用<span class="accred">尾号${bankcode}</span> 的银行卡进行转账操作 </p>
							</div> 
						</div>
						<div class="pointimg fr"><img src="${ctx}/static/kingkaid/openpay/image/point.png"/></div>
					</div>
					<div class="x_img">
						<div class="x_img1">
							<div class="fl"><img src="${ctx}/static/kingkaid/openpay/image/cyber3.png"/></div>
							<div class="txtphone fr tgl">
								<p>3. 在银行柜员协助下，向上述收款账户进行转账</p>
								<p><span class="accred">（所转金额将计入您的金开贷账户余额）</span></p>
							</div>
						</div>           
						<div class="pointimg fr padt30"><img src="${ctx}/static/kingkaid/openpay/image/point.png"/></div>
					</div>
					<div class="x_img">
						<div class="x_img1">
							<div class="fl"><img src="${ctx}/static/kingkaid/openpay/image/x_img4.png"/></div>
							<div class="txtphone fr tgl">
								<p class="txtin lih34">4.24小时后转账金额到账并验证成功</p>
								<p class="txtin lih34">收到 电子账户激活成功<span class="accred">  短信提示</span></p>
								<p class="txtin lih34"> <span class="accred">重新登录 </span>金开贷账户查看认证状态</p>
								<div style="clear: both;"></div>
								<div id="deskprint" class="x_blbtn fl">打印转账信息</div>
							</div>
						</div>           
					</div>
				</div>
        	</div>
 	  </div>
 	  
</body>
</html>

