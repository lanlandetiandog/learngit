<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<link type="text/css" rel="stylesheet" href="${ctx}/static/kingkaid/css/guidecss.css" />
<script type="text/javascript" src="${ctx}/static/kingkaid/js/xabank/cust/xian_guide.js"></script>
<script type="text/javascript" src="${ctx}/static/plugin/js/timer.jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/xabank/cust/openpay3f.js"></script>
<title>金开贷</title>
<script type="text/javascript">
$(document).ready(function(){
	$(".openallbak").click(function(){
		  $(".allbak_shade").css("display","block");
		}); 
		
		$(".xa_closebtn").click(function(){
		  $(".allbak_shade").css("display","none");
			$("body").css("overflow-y","auto");
		}); 
});
</script>
<style type="text/css">
body {
    background: #e7f2f7;
}
.ocx_style {
		width: 280px;
		height: 45px;
		line-height: 45px;
		padding: 3px 0 0 2px;
	 	outline: 0; 
		box-sizing: border-box;
	 	border: 1px solid #e4e4e4; 
	 	text-indent: 10px; 
/* 		background:url(${ctx}/static/kingkaid/openpay/image/loadpro.png) no-repeat ; */
		cursor:pointer;
}
.ocx_style a{
  width:10px;
}
.code_gray{
	width:280px;;
	border-radius: 4px;
	float:right;
	background-color: #99a7ba;
	height:42px;
	line-height: 42px;
	text-align: center;
	margin:0 auto;
	margin:25px 60px 0  0;
	font-size: 16px;
	color: #fff;
	cursor: pointer;
}

.buttonstyle{
    width:280px;;
	border-radius: 4px;
	float:right;
	background-color: #5d89c9;
	height:42px;
	line-height: 42px;
	text-align: center;
	margin:0 auto;
	margin:25px 60px 0  0;
	font-size: 16px;
	color: #fff;
	cursor: pointer;
}

.dotting {
    display: inline-block; width: 10px; min-height: 2px;
    padding-right: 2px;
    border-left: 2px solid currentColor; border-right: 2px solid currentColor;
    background-color: currentColor; background-clip: content-box;
    box-sizing: border-box;
    -webkit-animation: dot 4s infinite step-start both;
    animation: dot 4s infinite step-start both;
    *zoom: expression(this.innerHTML = '...'); /* IE7 */
}
.dotting:before { content: '...'; } /* IE8 */
.dotting::before { content: ''; }
:root .dotting { margin-left: 2px; padding-left: 2px; } /* IE9+ */

@-webkit-keyframes dot {
    25% { border-color: transparent; background-color: transparent; }
    50% { border-right-color: transparent; background-color: transparent; }
    75% { border-right-color: transparent; }
}
@keyframes dot {
    25% { border-color: transparent; background-color: transparent; }
    50% { border-right-color: transparent; background-color: transparent; }
    75% { border-right-color: transparent; }
}
.open_png_btn {
    top: 21px;
    cursor: pointer;
    position: absolute;
    right: 7px;
}

.xa_protocol {
    width: 280px;
    height: 20px;
    line-height: 20px;
    outline: 0;
    box-sizing: border-box;
    text-indent: 10px;
    font-size: 14px;
}

.xa_agree {
    width: 0px;
    height: 0px;
    line-height: 30px;
    outline: 0;
    box-sizing: border-box;
    text-indent: 10px;
    font-size: 14px;
}
.allbak_shade{
    display:none;
    width:auto;
    height:900px;
    color:#fff;
    text-align:center;
    position:absolute;
    z-index:999999999;
    top:0;
    bottom:0;
    right:0;
    left:0;
	position: fixed;
    background: rgba(0,0,0,0.6);
}
.xa_alertjpg,.xa_alertjpgin{
	width: 930px;
	height: 465px;
    margin:0 auto;
    vertical-align:central;
    opacity:1;
    display:block;
    position:relative;
    z-index:9999999889;
    top:0;
    bottom:0;
    right:0;
    left:0;
}
.xa_alertjpg{
	margin-top: 13%;
	padding-left:42px;
}
.xa_alertjpgin{
	background: url(${ctx}/static/kingkaid/openpay/images/allbank.png) top center no-repeat;
	height: 545px;
	top:-100px;
}
.xa_closebtn{
    width:63px;
    height:63px;
	position:absolute;
	top: -100px;
    right: 127px;
	cursor: pointer;
	z-index: 999999999999;
}
.xa_closebtn:hover{
	opacity: 0.9;
}
.openallbak{
    cursor:pointer;
    text-indent:10px;
}
</style>
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

<div class="bankwrap">
<!--账户验证信息-->
 <!--西安银行账户激活引导-->
 <div class="guidbox" style="height:220px;border: none;">
  <!--1.步骤-->
 	 <div class="guidTop">
			<span class="pdl30"><img src="${ctx}/static/kingkaid/openpay/image/jkdnewlog-.png"/></span>
			<span class="accoungname">电子账户认证</span>
	  </div>

  <div style="clear: both"></div>
	  <div class="instruc">
		  <p style="line-height:82px">
			<span class="gblue open_position">开通电子账户<span class="blueborder"><img src="${ctx}/static/kingkaid/openpay/image/blueborder.png"/></span></span>
			<img src="${ctx}/static/kingkaid/openpay/image/direc-.png"/>
			<span class="ggray">激活电子账户</span>
			<img src="${ctx}/static/kingkaid/openpay/image/direc-.png"/>
			<span class="ggray">完成认证</span>
	  	</p>
	  </div>
	  <div style="text-align: center;">
			<img src="${ctx}/static/kingkaid/openpay/image/xa_logo.png"/>
	  </div>
	  
	  <div style="padding-left: 363px;margin-top:10px;color:red;font-size:20px;">
			<span >银行存管已上线，需完善以下信息才能正常使用</span>
	  </div>

</div>
    <div class="accountinfos" style="margin-top:10px;">
            <form id="openpayform">
            <div class="inputwrap" style="margin-top:20px;">
                <span class="title-realname">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</span>            
                <input type="text" id="ID_NAME" name="ID_NAME" value="${formData.custname}" placeholder="真实姓名" style="background-color: #e0e0e0" readonly="readonly"/>
                <div style="clear:both;"></div>
            </div> 
    		<div style="clear:both;"></div>
    		
            <div class="inputwrap" style="margin-top:20px;">
                <span class="title-realname">证件号码</span>            
                <input id="ID_NO" name="ID_NO" type="text" value="${formData.paperid}" placeholder="证件号码" style="background-color: #e0e0e0" readonly="readonly"/>
                <div style="clear:both;"></div>              
            </div>
            
            <div class="inputwrap" style="margin-top:20px;">
                <span class="title-realname">手机号码</span>            
                <input type="text" id="MOBILE" value="${formData.mobilenumber}" name="MOBILE" class="phonenum" value="${formData.mobilenumber}"/>            
                <div style="clear:both;"></div> 
                <span class="pointout-affirm">
                    <span>
                        <span id="password_error" class="">请务必输入您绑定银行卡的预留手机号码！</span>
                    </span>
                    <div class="rectangle"></div>
                </span>
            </div> 
		    <div style="clear:both;"></div>
            
            <div class="inputwrap" style="margin-top:20px;">
                <span class="title-realname">银行卡号</span>            
                <input type="text" id="ACCT_NO" name="ACCT_NO" value="${formData.bankcode}" maxlength="20" placeholder="银行卡号"  onkeyup="if(/\D/.test(this.value)){this.value='';}" />
                <div style="clear:both;"></div>
                 <div id="bank_name_div" class="banknamebox" style="color:#666;display:none;"><span class="bankname"></span></div>
                <span class="pointout-cardnum" style="height:105px;">
                    <span>
                        <span id="card-error" >该银行卡将成为您在金开贷的唯一默认充值、提现银行卡，一经绑定，不可更换！&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        <span id="card-error" class="openallbak">(点击查看当前支持银行卡>>)</span>
                        <div class="rectangle"></div>
                    </span>
                </span>               
            </div> 
            
		    <div class="inputwrap" style="padding: 26px 15px 0 0;width: 300px;">
		       <span class="title-realname"></span>
		       <input id="agree_box" name="agree_box" type="checkbox"/><span class="xa_protocol"><a class="blue_link" href="${ctx}/auth/cust/xaprotocol.html" target="_blank">西安银行互联网交易资金存管三方协议</a></span>
		    </div>

		    <button type="button" id="doSubmit" class="buttonstyle">确认开户<span id="dotting"></span></button>
		    <div style="clear:both;"></div>
    
    </form>
  </div> 
  
    <div style="clear:both;"></div>
    
    <div class="allbak_shade">
		<div  class="xa_alertjpg">
			<div class="xa_alertjpgin">
			</div>
			<div class="xa_closebtn"></div>
		</div>
	</div>
    
</div>
<!--账户验证end-->
  
  <div id="big_bg" style="position: absolute; top: 0px; left: 0px; width: 1453px; height: 1224px; display:none;z-index: 100000;position: absolute;background-color: #000;opacity: 0.6;"></div>
  
    <div style="background-color:#4a545c;color:#fff;height:60px;">
        <div style="height:42px;width:1000px;margin:0 auto;line-height:25px;font-family:'SimSun';padding-top:20px;">
                <div style="float:left">
                    <div>陕西金开贷金融服务有限公司 陕ICP备 13007681号</div>
                </div>
                <div style="float:right">
                    <div>@ 2014 金开贷 All rights reserved</div>
                </div>
        </div>
    <div style="clear:both;"></div>
    </div>
</body>
</html>

