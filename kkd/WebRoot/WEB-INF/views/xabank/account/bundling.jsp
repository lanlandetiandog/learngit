<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/static/plugin/js/PassGuardCtrl.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/xabank/account/spin.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/xabank/account/bundling.js"></script>
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
		height: 25px;
}
</style>
</head>

<body>
	<%@ include file="../../common/header.jsp" %>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="usercenter_content">
                    <div class="recharge-top">
                        <div class="recharge-title">绑定银行卡</div>
                    </div>
                    <form id="bundingForm">
                    	<input type="hidden" name="bankid" id="bankid" value=""/>
	                    <ul class="recharge-info web_bank">
	                        <li>
	                        	<span class="safe_info_title">新银行卡号：</span>
								<input id="ACCT_NO" name="ACCT_NO" class="safety_input"/>
								<div id="bank_name_div" class="banknamebox" style="margin-left:108px;width:154px;color:#666;display:none;">
								<span class="bankname" style="width: 80px;height: 20px;"></span></div>
	                        </li>
	                        <li>
	                            <span class="safe_info_title">交易密码：</span>
	                            <span id="_ocx_password_str">
	                            	<script>
										pgeditor.generate();
									</script>
								</span>
								<span style="padding-left: 9px;color:red;font-size:14px;">温馨提示：请输入正确的6位交易密码</span>
	                        </li>
	                        <li>
	                            <input id="butsub" class="blue-btn" type="button" value="绑定银行卡"></input>
	                        </li>
	                    </ul>
                    </form>
                   <%@ include file="../../usercenter/usercenterleftmenu.jsp"%>
                </div>

            </div>
            <div style="clear:both"></div>
        </div>
        <div style="clear:both"></div>
    </div>
    <%@ include file="../../common/footer.jsp"%>
   
</body>
</html>
