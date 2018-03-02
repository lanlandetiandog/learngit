<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
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
                    <li>你好，${mobileNumber}</li>
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
	    <%@ include file="nav.jsp" %>
 	</div>
</div>