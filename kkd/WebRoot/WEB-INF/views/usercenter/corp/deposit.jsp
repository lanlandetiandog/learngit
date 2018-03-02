<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/corp/deposit.js"></script>
</head>
<body>
	<%@ include file="../../common/header.jsp" %>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="usercenter_content">
                    <div class="recharge-top">
                        <div class="recharge-title">充值</div>
                        <div class="right_href"><a href="${ctx}/auth/usercenter/myproperty.html">我的资产</a></div>
                    </div>
                    <form id="form1" action="${ctx}/auth/pay/deposit" target="_blank" method="post">
	                    <ul class="recharge-info web_bank">
	                        <li>
	                            <span>账户余额：</span>
	                            <span>&yen;<fmt:formatNumber value="${acctInfo.bal}" pattern="#,##0.00"/>元</span>
	                        </li>
	                        <li>
	                            <span>充值金额：</span>
	                            <input type="text" name="amount" style="width:129px;height:26px;border:1px solid #dfdfdf;"/>
	                            <span>元</span>
	                        </li>
	                        <li>
	                            <button class="blue-btn" onclick="chinaPnrSubmit();" type="button">下一步</button>
	                        </li>
	                    </ul>
                    </form>
                    <div style="padding:0px 70px;line-height:25px;">
                        <div>温馨提示：</div>
                        <div style="color:#808080;line-height:25px;">在支付页面完成充值后，点击完成充值，充值后有时存在平台到账时间会延迟1~2分钟情况,给 您带来的不便，请您谅解。若您充值金额等待30分钟后没有到账，请联系系统客服。</div>
                    </div>
                   <%@ include file="usercenterleftmenu.jsp"%>
                </div>

            </div>
            <div style="clear:both"></div>
        </div>
        <div style="clear:both"></div>
    </div>
    <%@ include file="../../common/footer.jsp"%>
   
</body>
</html>
