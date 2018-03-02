<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/static/plugin/js/PassGuardCtrl.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/xabank/account/corp/deposit.js"></script>
<script src="${ctx}/static/kingkaid/js/loading.js"></script>
<script type="text/javascript">
	var pgeditor = new $.pge({
		pgePath: ctx + "/static/xabank/ocx/",
		pgeId: "_ocx_password",
		pgeEdittype: 0,
		pgeEreg1: "[0-9]*",
		pgeEreg2: "[0-9]{6}",
		pgeMaxlength: 6,
		pgeClass: "ocx_style",
		pgeInstallClass: "ocx_style",
		pgeOnfocus: "clearError('_ocx_password')"
	});
	
	pgeditor.pgInitialize();
	
</script>
<style>
	.ocx_style {
		border: 1px solid #7F9DB9;
		width: 133px;
		height: 30px;
	}
	
	.ui-loading{
		position:absolute;
		left:0;
		top:0;
		z-index: 9999;
	}
	.ui-loading .ui-loading-mask{ 
		position:absolute;
		top:0;
		left:0;
		background-color: #000;
		opacity: .3;
		z-index: 1;
	}
	.ui-loading i{
		height: 90px;
		width:90px; 
		display: block;
		background: url("${ctx}/static/kingkaid/images/bank/loading.gif") no-repeat center center;
		background-size:100% 100%;
		position: absolute;
		z-index: 2;
	}
</style>
</head>

<body>
	<%@ include file="../../../common/header.jsp" %>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="usercenter_content">
                    <div class="recharge-top">
                        <div class="recharge-title">充值</div>
                        <div class="right_href"><a href="${ctx}/auth/usercenter/myproperty.html">我的资产</a></div>
                    </div>
                    <ul class="recharge-tab">
                        <li class="recharge-type recharge-type-cur">
                            <div>快捷充值</div>
                        </li>
                    </ul>
                    <span id="pic">
	                    
	                </span>
                    <form id="form" action="${ctx}/auth/pay/deposit" target="_blank" method="post">
                    	<input type="hidden" name="quick" id="quick" value="B2C"/>
                    	<input type="hidden" name="bankid" id="bankid" value=""/>
	                    <ul class="recharge-info web_bank">
	                        <li>
	                            <span>账户余额：</span>
	                            <span>&yen;<fmt:formatNumber value="${acctInfo.bal}" pattern="#,##0.00"/>元</span>
	                        </li>
	                        <li>
	                            <span>充值金额：</span>
	                            <input type="text" id="amount" name="amount" style="width:129px;height:26px;border:1px solid #dfdfdf;"/>
	                            <span>元</span>
	                        </li>
	                        <li>
	                        	<span>充值银行卡：</span>
	                            <span>
	                            	<c:if test="${not empty bankcard}">
		                            	<input type="hidden" name="acctno" id="acctno" value="${bankcard.bankacno}"/>
		                            	<label>${bankcard.bankname}(<kkd:bankCardFormat cardId="${bankcard.bankacno}"></kkd:bankCardFormat>)</label>
	                            	</c:if>
	                            </span>
	                        </li>
	                        <li>
	                            <span>交易密码：</span>
	                            <span id="_ocx_password_str">
	                            	<script>
										pgeditor.generate();
									</script>
								</span>
								<span style="padding-left: 9px;color:red;font-size:14px;">温馨提示：请输入正确的6位交易密码</span>
	                        </li>
	                        <li>
	                            <input id="butsub" class="blue-btn" type="button" value="下一步"></input>
	                        </li>
	                    </ul>
                    </form>
                    <div style="padding:0px 70px;line-height:35px;">
                        <div><strong>温馨提示：</strong></div>
                        <div style="color:#808080;line-height:35px;"><b>1. </b>充值完成后存在平台到账时间会延迟1~2分钟情况，给您带来不便请您谅解，若您充值金额等待30分钟后没有到账，请联系官方客服。</div>
                        <div style="color:#808080;line-height:35px;"><b>2. </b>为了您的资金安全,您银行存管电子账户绑定的银行卡将是您在平台唯一充值、提现卡。</div>
                        <div>&nbsp;</div>
                    </div>
                    
                   <%@ include file="../../../usercenter/corp/usercenterleftmenu.jsp"%>
                </div>

            </div>
            <div style="clear:both"></div>
        </div>
        <div style="clear:both"></div>
    </div>
    <%@ include file="../../../common/footer.jsp"%>
   
</body>
</html>
