<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/xabank/account/corp/withdraw.js"></script>
<script type="text/javascript">
$(document).ready(function(){

	$("span[id='fast']").hover(function(event){
		     
		     $("#pic").show();//mouseenter事件
		     
		   },function(event){
		     
		     $("#pic").hide();//mouseleaver事件
		});	
}) 

</script>
<style>

#fast{
   position:relative;
}

#pic{
   display:none;
   position:absolute;
   top:-58px;
   left:360px;
   height:290px;
   width:439px;
   background:url("${ctx}/static/kingkaid/images/bank/banklist.png") no-repeat top center;
   background-size:contain;
}
</style>

<style type="text/css">
.remind{
  color:red;
  font-size:13px;
  margin-left:108px;
  margin-top:5px;
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
                        <div class="recharge-title">提现 
                        <span style="font-size:14px; color:#ea6660; margin-left:10px; font-weight:normal;">
	                        <c:if test="${loancount != '0' && apprcount > 0}">
	                        	（<a href="#" onclick="apprList();" style="text-decoration:underline; color:#ea6660;">您有${apprcount}项提现申请已经通过，请查看</a>）
    	                    </c:if>
    	                </span>   
                        </div>
                        <div class="right_href">
                            <a href="${ctx}/auth/usercenter/myproperty.html">我的资产</a>
                        </div>
                    </div>
                    <form id="form" action="${ctx}/auth/pay/withdraw" method="post" target="_blank">
                    	<input type="hidden" id="balance" name="balance" value="${acctInfo.bal}"/>
                    	<input type="hidden" id="maxusecoin" name="maxusecoin" value="0"/>
                    	<input type="hidden" id="servfee" name="servfee" value="0"/>
                    	<input type="hidden" id="realcashamt" name="realcashamt" value="0"/>
                    	<input type="hidden" id="coinbalance" name="coinbalance" value="${acctInfo.coinbal}"/>
                    	<input type="hidden" id="voucherno" name="voucherno"/>
                    	<input type="hidden" id="voucheramt" name="voucheramt" value="0"/>
                    	<input type="hidden" id="cashchl" name="cashchl" value="IMMEDIATE"/>
	                    <ul class="recharge-info" id="menu">
	                        <li>
	                            <span class="cash_info_left">账户余额：</span>
	                            <span class="cash_info_right blue-text">${acctInfo.bal}</span>元
	                            <div style="clear:both;"></div>
	                        </li>
	                        <li>
	                            <span class="cash_info_left">提现金额：</span>
	                            <span class="cash_info_right">
	                                <input type="text" id="amount" name="amount" style="width:129px;height:26px;border:1px solid #dfdfdf;" />元
	                            </span>
	                            <div style="clear:both;"></div>
	                        </li>
	                        <!-- 
		                        <li>
		                            <p class="cash_info_left">提现周期：</p>
		                            <span class="cash_info_right">
		                                	
		                                <span class="blue-text"><input name="cashchl" value="GENERAL" checked="checked" type="radio"/>&nbsp普通提现（T+1）</span>
		                                
		                                <span class="blue-text" id="fast"><input name="cashchl" value="IMMEDIATE" style="margin-left:20px;" type="radio"/>&nbsp加急提现（T+0）</span>
		                                <span id="pic">
		                                    
		                                </span>	                                
		                            </span>
		                            <div style="clear:both;"></div>
		                        </li>
	                         -->
	                            <li>
	                            <span class="cash_info_left">手续费：</span>
	                                <div>
	                                    <span class="blue-text" id="servfee_show">0</span>元
	                                    <span style="font-size:12px;margin-left:15px;">
	                                        <span id="servfee1">
		                                        <span style="color:#eb493d;">费率</span>
		                                        <c:choose>
		                                        	<c:when test="${chanfee.servfeekind1 == '1'}">（每笔上限${chanfee.servfeevalue1}元）</c:when>
		                                        	<c:otherwise>（${chanfee.servfeevalue1}%每笔上限${chanfee.maxservfee1}元）</c:otherwise>
		                                        </c:choose>
	                                    	</span>
	                                    	<span id="servfee2" style="display:none;">
		                                        <span style="color:#eb493d;">费率</span>
		                                        <c:choose>
		                                        	<c:when test="${chanfee.servfeekind2 == '1'}">（每笔上限${chanfee.servfeevalue2}元）</c:when>
		                                        	<c:otherwise>（${chanfee.servfeevalue2}%）</c:otherwise>
		                                        </c:choose>
	                                    	</span>
	                                    </span>
	                                </div>
	                            <div style="clear:both;"></div>
	                        </li>
	                        <li>
	                            <span class="cash_info_left">手续费抵免：</span>
	                            <div class="cash_info_right">
	                                <div>
	                                	<!-- 
			                                <input type="checkbox" id="usevoucherno" name="usevoucherno"/>
	                                	 -->
		                   				<select class="cash_chosen_select" 
		                   					style="border:1px solid #bac0c4;color:#76818a;" 
		                   					id="raisinte" name="raisinte">
		                   					<option value="">请选择提现优惠券</option>
		                   					<c:forEach items="${raisintes}" var="raisinte">
			                                    <option value="${raisinte.seqorder_list},${raisinte.value}">提现优惠券：${raisinte.value}元（<fmt:formatDate value="${raisinte.effectenddate}" pattern="yyyy-MM-dd"/>到期）</option>
		                   					</c:forEach>
		                                </select>
	                                </div>
	                                <div style="font-size:12px;margin-top:5px;">
	                                    <input type="checkbox" id="usecoinamt" name="usecoinamt"/>
	                                    <span>使用金开币
	                                        <input style="width:40px;height:18px;border:1px solid #bac0c4;" type="text" id="coinamt" name="coinamt" value="0" disabled/> 元,
	                                        <span style="color:#808080;">（本次最多可使用：<label id="maxusecoin_show">${acctInfo.coinbal}</label> 个）</span>
	                                    </span>
	                                </div>
	                            </div>
	                            <div style="clear:both;"></div>
	                        </li>
	                        <li>
	                            <span class="cash_info_left">实际到账金额：</span>
	                            <span class="cash_info_right blue-text" id="realcashamt_show">0</span>元
	                            <div style="clear:both;"></div>
	                        </li>
	                        <li>
	                            <span class="cash_info_left">选择银行卡：</span>
	                            <div class="cash_info_right">
	                                <select class="cash_chosen_select" style="border:1px solid #bac0c4;color:#76818a;" id="cardId" name="cardId">
	                                	<option value="">请选择</option>
	                                	<c:if test="${not empty bankcard}">
	                                        <option value="${bankcard.bankacno}">${bankcard.bankname}(<kkd:bankCardFormat cardId="${bankcard.bankacno}"></kkd:bankCardFormat>)</option>
	                                    </c:if>
	                                </select>
	                            </div>
	                            <div style="clear:both;"></div>
	                        </li>
	                         <li>
	                        	<c:choose>
	                        		<c:when test="${loancount eq '0'}">
			                            <div style="cursor: pointer;" class="blue-btn" onclick="doCash();">下一步</div>
	                        		</c:when>
	                        		<c:otherwise>
	                        			<div style="cursor: pointer;" class="blue-btn" onclick="doCashAppr();">提交申请</div>
	                        			 <span style="margin-left:20px;">
	                                		您有${loancount}笔未结清的借款项目，请您填写提现信息后并提交后台项目经理审核!
	                                	</span>
	                        		</c:otherwise>
	                        	</c:choose>
	                        </li>
	                    </ul>
                    </form>
                    <div style="padding:30px 70px;line-height:25px;">
                        <div>温馨提示：</div>
                        <div style="color:#808080;line-height:25px;">
                            <div>提现成功后，您的实际提现金额将在两小时内到帐，</div>
                            <div>请您及时关注到帐情况（客服电话：400-1888-136）。</div>
                        </div>    
                    </div>
	     			<%@ include file="../../../usercenter/corp/usercenterleftmenu.jsp"%>
                </div>
            </div>
            <div style="clear:both"></div>         
        </div> 
        <div style="clear:both"></div>       
    </div>         
    <div id="cashsubmit">
    </div>
    <%@ include file="../../../common/footer.jsp"%>
</body>
</html>