<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/deposit.js"></script>
<script type="text/javascript">
$(document).ready(function(){

	$("li[id='web_qp']").hover(function(event){
		     
		     $("#pic").show();//mouseenter事件
		     
		   },function(event){
		     
		     $("#pic").hide();//mouseleaver事件
		});	
}) 

</script>
<style>

#web_qp{
   position:relative;
}

#pic{
   display:none;
   position:absolute;
   top:-35px;
   left:405px;
   height:360px;
   width:484px;
   background:url("${ctx}/static/kingkaid/images/bank/bankrelist.png") no-repeat top center;
   background-size:contain;
}
</style>
</head>

<body>
	<%@ include file="../common/header.jsp" %>
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
                        <li class="recharge-type recharge-type-cur" id="web_bank" style="border-right:1px solid #dbddde;">
                            <div>网银充值</div>
                        </li>
                        <li class="recharge-type" id="web_qp" style="border-right:1px solid #dbddde;">
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
	                            <input type="text" name="amount" style="width:129px;height:26px;border:1px solid #dfdfdf;"/>
	                            <span>元</span>
	                        </li>
	                        <li id="banklist">
	                        	<table width="100%" cellpadding="0" cellspacing="0">
	                        		<tr style="height:70px;">
	                        			<td><label class="banklab" for="BOC" style="cursor: pointer;" onclick="selectbank('BOC')"><input type="radio" name="openbankid" id="BOC" style="height:35px;"/><img id="BOCIMG" src="${ctx}/static/kingkaid/images/bank/b2c/BOC.png" style="border:1px solid #eee;width:150px;height:38px;"/></label></td>
	                        			<td><label class="banklab" for="BOS" style="cursor: pointer;" onclick="selectbank('BOS')"><input type="radio" name="openbankid" id="BOS" style="height:35px;"/><img id="BOSIMG" src="${ctx}/static/kingkaid/images/bank/b2c/BOS.png" style="border:1px solid #eee;width:150px;height:38px;"/></label></td>
	                        			<td><label class="banklab" for="CCB" style="cursor: pointer;" onclick="selectbank('CCB')"><input type="radio" name="openbankid" id="CCB" style="height:35px;"/><img id="CCBIMG" src="${ctx}/static/kingkaid/images/bank/b2c/CCB.png" style="border:1px solid #eee;width:150px;height:38px;"/></label></td>
	                        			<td><label class="banklab" for="CEB" style="cursor: pointer;" onclick="selectbank('CEB')"><input type="radio" name="openbankid" id="CEB" style="height:35px;"/><img id="CEBIMG" src="${ctx}/static/kingkaid/images/bank/b2c/CEB.png" style="border:1px solid #eee;width:150px;height:38px;"/></label></td>
	                        		</tr>
	                        		<tr style="height:70px;">
	                        			<td><label class="banklab" for="CIB" style="cursor: pointer;" onclick="selectbank('CIB')"><input type="radio" name="openbankid" id="CIB" style="height:35px;"/><img id="CIBIMG" src="${ctx}/static/kingkaid/images/bank/b2c/CIB.png" style="border:1px solid #eee;width:150px;height:38px;"/></label></td>
	                        			<td><label class="banklab" for="CITIC" style="cursor: pointer;" onclick="selectbank('CITIC')"><input type="radio" name="openbankid" id="CITIC" style="height:35px;"/><img id="CITICIMG" src="${ctx}/static/kingkaid/images/bank/b2c/CITIC.png" style="border:1px solid #eee;width:150px;height:38px;"/></label></td>
	                        			<td><label class="banklab" for="CMB" style="cursor: pointer;" onclick="selectbank('CMB')"><input type="radio" name="openbankid" id="CMB" style="height:35px;"/><img id="CMBIMG" src="${ctx}/static/kingkaid/images/bank/b2c/CMB.png" style="border:1px solid #eee;width:150px;height:38px;"/></label></td>
	                        			<td><label class="banklab" for="CMBC" style="cursor: pointer;" onclick="selectbank('CMBC')"><input type="radio" name="openbankid" id="CMBC" style="height:35px;"/><img id="CMBCIMG" src="${ctx}/static/kingkaid/images/bank/b2c/CMBC.png" style="border:1px solid #eee;width:150px;height:38px;"/></label></td>
	                        		</tr>
	                        		<tr style="height:70px;">
	                        			<td><label class="banklab" for="ICBC" style="cursor: pointer;" onclick="selectbank('ICBC')"><input type="radio" name="openbankid" id="ICBC" style="height:35px;"/><img id="ICBCIMG" src="${ctx}/static/kingkaid/images/bank/b2c/ICBC.png" style="border:1px solid #eee;width:150px;height:38px;"/></label></td>
	                        			<td><label class="banklab" for="PSBC" style="cursor: pointer;" onclick="selectbank('PSBC')"><input type="radio" name="openbankid" id="PSBC" style="height:35px;"/><img id="PSBCIMG" src="${ctx}/static/kingkaid/images/bank/b2c/PSBC.png" style="border:1px solid #eee;width:150px;height:38px;"/></label></td>
	                        			<td><label class="banklab" for="SPDB" style="cursor: pointer;" onclick="selectbank('SPDB')"><input type="radio" name="openbankid" id="SPDB" style="height:35px;"/><img id="SPDBIMG" src="${ctx}/static/kingkaid/images/bank/b2c/SPDB.png" style="border:1px solid #eee;width:150px;height:38px;"/></label></td>
	                        			<td><label class="banklab" for="SRCB" style="cursor: pointer;" onclick="selectbank('SRCB')"><input type="radio" name="openbankid" id="SRCB" style="height:35px;"/><img id="SRCBIMG" src="${ctx}/static/kingkaid/images/bank/b2c/SRCB.png" style="border:1px solid #eee;width:150px;height:38px;"/></label></td>
	                        		</tr>
	                        	</table>
	                        </li>
	                        <li>
	                            <button class="blue-btn" onclick="chinaPnrSubmit();" type="button">下一步</button>
	                        </li>
	                    </ul>
                    </form>
                    <div style="padding:0px 70px;line-height:35px;">
                        <div><strong>温馨提示：</strong></div>
                        <div style="color:#808080;line-height:35px;"><b>1. </b>为了您的账户安全,您的当日个人网银充值金额需要到第二个工作日起可取现。</div>
                        <div style="color:#808080;line-height:35px;"><b>2. </b>在支付页面完成充值后，点击完成充值，充值后有时存在平台到账时间会延迟1~2分钟情况,给 您带来的不便，请您谅解。若您充值金额等待30分钟后没有到账，请联系系统客服。</div>
                        <div style="color:#808080;line-height:35px;"><b>3. </b>为了您的资金安全,绑定的快捷支付卡将同时被绑定为唯一的取现卡。如需解绑此卡，请致电汇付天下官网-P2P账户查询系统进行解绑。</div>
                        <div>&nbsp;</div>
                    </div>
                   <%@ include file="usercenterleftmenu.jsp"%>
                </div>

            </div>
            <div style="clear:both"></div>
        </div>
        <div style="clear:both"></div>
    </div>
    <%@ include file="../common/footer.jsp"%>
   
</body>
</html>
