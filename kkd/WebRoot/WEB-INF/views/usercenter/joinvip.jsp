<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/static/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/joinvip.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $("#usermenu_vip").addClass("user_menulist_href_hover");
        $("#menu_jkd").addClass("leftmenu_cur");

    });
    
</script>
</head>
 
<body>
    <jsp:include page="../common/header.jsp" flush="true">
		<jsp:param name="f" value="1" />
	</jsp:include>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="usercenter_content">
                    <div class="recharge-top">
                        <div class="recharge-title">${vipInfo.vipstate eq '0' ? '成为VIP' : 'VIP续期'}</div>
                        <div class="right_href">
                            <a href="${ctx}/auth/usercenter/myvip.html">我的VIP</a>
                        </div>
                    </div>
                    <form action="${ctx}/auth/xabank/acct/buy_vip.html" method="post" id="form" target="_blank">
                    	<input type="hidden" id="amount" name="amount" value="0"/>
                    	<input type="hidden" id="realamt" name="realamt" value="0"/>
                    	<input type="hidden" id="coinbalance" name="coinbalance" value="${acctInfo.coinbal}"/>
	                    <ul class="recharge-info">
	                        <li>
	                            <span class="vip_info_left">VIP状态：</span>
	                            <span class="cash_info_right blue-text">
	                            	<c:if test="${vipInfo.vipstate eq '0'}">
		                                <img src="${ctx}/static/kingkaid/images/vip_false.png" title="未开通VIP会员"/>
	                            	</c:if>
	                            	<c:if test="${vipInfo.vipstate eq '1'}">
		                                <img src="${ctx}/static/kingkaid/images/vip_true.png" title="已开通VIP会员"/>
	                            	</c:if>
	                            </span>
	                            <div style="clear:both;"></div>
	                        </li>
	                        <c:if test="${vipInfo.vipstate eq '1'}">
		                        <li>
		                            <span class="vip_info_left">VIP到期时间：</span>
		                            <span id="old_vip_end_date" class="cash_info_right blue-text"><fmt:formatDate value="${vipInfo.vipenddate}" pattern="yyyy年MM月dd日"/></span>
		                            <div style="clear:both;"></div>
		                        </li>
	                        </c:if>
		                        <li>
		                        	<div>
			                        	<span class="vip_info_left">购买VIP期限：</span>
	                                    <select class="safety_select" style="width:150px;color:#333;" id="term" name="term">
	                                    	<option value="">请选择</option>
	                                    	<option value="1">1个月</option>
	                                    	<option value="2">2个月</option>
	                                    	<option value="3">3个月</option>
	                                    	<option value="4">4个月</option>
	                                    	<option value="5">5个月</option>
	                                    	<option value="6">6个月</option>
	                                    	<option value="7">7个月</option>
	                                    	<option value="8">8个月</option>
	                                    	<option value="9">9个月</option>
	                                    	<option value="10">10个月</option>
	                                    	<option value="11">11个月</option>
	                                    	<option value="12">12个月</option>
	                                    </select>
		                        	</div>
		                            <div style="clear:both;"></div>
		                        </li>
		                        <li>
		                            <span class="vip_info_left">购买后VIP到期时间：</span>
		                            <span class="cash_info_right blue-text" id="by_vip_end_date"></span>
		                            <div style="clear:both;"></div>
		                        </li>
		                        <li>
		                            <span class="vip_info_left">应付金额：</span>
		                            <div class="cash_info_right">
		                                <div>
		                                    <span class="blue-text"><label id="amount_show">0</label>元</span>
		                                    <span style="font-size:12px;margin-left:15px;">
		                                        		（账户可用余额：${acctInfo.bal}元）
		                                        <c:choose>
												    <c:when test="${user_obj.memberState eq '4' || user_obj.memberState eq '5'}">
												        <a class="red_link" href="${ctx}/auth/cust/openpay_page.html">立即开通</a>												        													        
												    </c:when>
												    <c:when test="${user_obj.memberState eq '6' || user_obj.memberState eq '7'}">
												        <a class="red_link" href="${ctx}/auth/cust/openpay_page.html">立即激活</a>												        													        
												    </c:when>
												    <c:otherwise>
												        <a class="red_link" href="${ctx}/auth/xabank/acct/deposit_page.html">充值</a>
												    </c:otherwise>
												</c:choose>		                                        
		                                    </span>
		                                </div>
		                                <div style="font-size:12px;margin-top:5px;">
		                                    <input type="checkbox" id="usecoin" name="usecoin"/>
		                                    <span>使用金开币
		                                        <input style="width:40px;height:18px;border:1px solid #bac0c4;" type="text" name="coinamt" id="coinamt" disabled/> 元,
		                                        <span style="color:#808080;">（账户可用金开币：${acctInfo.coinbal}元）</span>
		                                    </span>
		                                    
		                                </div>
		                            </div>
		                            <div style="clear:both;"></div>
		                        </li>
		                        <li>
		                            <span class="vip_info_left">实际支付金额：</span>
		                            <span class="cash_info_right" style="color:#eb493d;"><label id="realamt_show">0</label>元
		                                <span style="color:#808080;"></span>
		                            </span>
		                            <div style="clear:both;"></div>
		                        </li>
	                        <li>
	                        	<c:choose>
	                        		<c:when test="${user_obj.memberState eq '4'}">
	                        			<button class="blue-btn" style="background-color: #666;" disabled title="未开通第三方托管账户"> 购 买 </button>
	                        		</c:when>
	                        		<c:when test="${vipInfo.vipstate eq '1'}">
		                        		<div style="cursor: pointer;" class="blue-btn" onclick="toPay();">续期</div>
	                        		</c:when>
	                        		<c:otherwise>
			                           <div style="cursor: pointer;" class="blue-btn" onclick="toPay();">立即购买</div>
	                        		</c:otherwise>
	                        	</c:choose>
	                        </li>
	                    </ul>
                    </form>
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
