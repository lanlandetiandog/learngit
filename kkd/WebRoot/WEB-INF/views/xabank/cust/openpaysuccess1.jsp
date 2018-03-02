<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/kingkaid/css/main.css" />
<script type="text/javascript" src="${ctx}/static/kingkaid/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/plugin/js/timer.jquery.js"></script>
<title>金开贷</title>
<script type="text/javascript">
	var ctx = "${ctx}";
</script>
<script type="text/javascript">
$(document).ready(function() {
	$('#jump').timer({
        format: '%s秒后自动进入我的金开贷',
        duration: '5s',
        countdown: true,
        callback: function() {
            $('#jump').timer('remove');
            $('#jump').hide();
        	location.href = ctx+ "/auth/usercenter/myjkd.html";
        }
    });
});
</script>
</head>
 
<body>
<div class="header">
    <div style="height:84px;width:1000px;margin:0 auto;">
        <a style="float:left;margin-top:20px;" href="${ctx}/index.html"><img src="${ctx}/static/kingkaid/images/logo.jpg" /></a>
        <div style="float:right;font-weight:bold;font-size:20px;margin-top:40px;">账户认证</div>
    </div>
</div>

<!--账户验证-->
<div class="bankwrap">
<!--验证说明-->
	<div class="open-process">
    	<div class="open-number">
        	<span class="num1">1</span>
        	<span class="num2" style="background:url(${ctx}/static/kingkaid/images/open-numblue.png) no-repeat top center;">2</span>
        	<span class="num3" style="background:url(${ctx}/static/kingkaid/images/open-numblue.png) no-repeat top center;">3</span>
        </div>
        <div class="open-instruction">
        	<div class="open-boximg">
                <span class="blue-cl">开通西安银行电子支付账户</span>
                <div class="openimg1"><img src="${ctx}/static/kingkaid/images/xianbankcard.png"/></div>
            </div>
        	<div class="open-boximg">
                <span class="blue-cl">激活电子账户</span>
                <div class="openimg2">
                    <div class="card1"><img src="${ctx}/static/kingkaid/images/otherbank.png"/></div>
                    <div class="pointtext">
                    	<span style="display:block;"><img src="${ctx}/static/kingkaid/images/pointtext.png"/></span>
                    	<span>充值验证</span>
                    </div>
                    <div class="card2"><img src="${ctx}/static/kingkaid/images/xianbankcard.png"/></div>
                </div>
            </div>
        	<div class="open-boximg">
                <span class="blue-cl">账户认证成功</span>
            </div>
        </div>
        <div style="clear:both;"></div>
        <p style="width:300px;color:#999;padding:40px 0 0 40px;">
        	<h4>温馨提示</h4>电子账户激活需要通过&nbsp;<em style="color:#ff8c01;">网银</em>或者&nbsp;<em style="color:#ff8c01;">ATM</em>&nbsp;进行打款认证。认证金额将会充值进入您的电子账户。
        </p>
    </div>
    

<!--账户验证信息-->
    <div class="accountinfo">
    		<div class="bankxian">
            	<div class="bankxianlogo"></div>
            </div>
            
          	<div class="ordernumwrap" style="width:280px;margin-top:110px;text-align:center;">
            	<div class="successnum"></div>
                <div style="clear:both;"></div>
                <span class="sucfailtext">恭喜您电子账户激活成功</br>祝您理财愉快</span>
            </div>
          	
            <div style="clear:both;"></div>          	
                <a class="open-activate" href="${ctx}/auth/usercenter/myjkd.html" id="jump"></a>
            <div style="clear:both;"></div>
            
    </div> 
        
    <div style="clear:both;"></div>
</div>
<!--账户验证end-->
         
          
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
