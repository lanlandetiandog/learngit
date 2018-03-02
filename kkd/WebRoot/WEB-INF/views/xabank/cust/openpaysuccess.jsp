<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<link type="text/css" rel="stylesheet" href="${ctx}/static/kingkaid/css/guidecss.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/timer.jquery.js"></script>
<title>金开贷</title>
<script type="text/javascript">
$(document).ready(function() {
	$('#jump').timer({
        format: '%s秒后自动进入我的金开贷...',
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

 <div class="guidbox">
  <!--1.步骤-->
	  <div class="guidTop">
			<span class="pdl30"><a href="${ctx}/"><img src="${ctx}/static/kingkaid/openpay/image/jkdnewlog-.png"/></a></span>
			<span class="accoungname">电子账户认证</span>
	  </div>
	  <div class="instruc">
	  <p class="open_tit" style="line-height:82px">
	  	<span class="ggray">开通电子账户</span>
  			  	<img src="${ctx}/static/kingkaid/openpay/image/direc-.png"/>

	  		  	<span class="ggray">激活电子账户</span>
	  	<img src="${ctx}/static/kingkaid/openpay/image/direc-.png"/>
	  	<span class="gblue open_position">完成认证<span class="blueborder3"><img src="${ctx}/static/kingkaid/openpay/image/blueborder.png"/></span></span>
	  </p>
	  </div>
	  <div class="xa_end">
	  		<p class="xa_endtxt"><span class="xa_endimg"><img src="${ctx}/static/kingkaid/openpay/image/xa_end.png"/></span>恭喜您已完成电子银行账户认证，立即<a class="xa_re" href="${ctx}/auth/xabank/acct/deposit_page.html">充值</a>开启金开贷理财！</p>
	  </div>
	  <div class="xa_end">
	        <p id="jump" class="xa_endtxt"></p>
      </div>
</div>


   <div style="clear: both;"></div>

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
