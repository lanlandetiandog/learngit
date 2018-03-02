<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/static/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/static/common/meta.jsp" %>
    <link rel="stylesheet" href="${ctx}/static/kingkaid/css/simple.pagination.css"/>
    <link rel="stylesheet" href="${ctx}/static/kingkaid/css/autobidset.css"/>
    <script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
    <script type="text/javascript" src="${ctx}/static/kingkaid/js/simple.pagination.js"></script>
    <script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/myautobid.js?v=1470020108160"></script>
    <style type="text/css">
        input::-ms-clear {display: none}
    </style>
    <script type="text/javascript">
    $(document).ready(function(){
    	$("#closeblack").click(function(){
        	showRechargeOverBox('用户开户','开户完成前请不要关闭该窗口！');    	
        });
    });
    </script>
</head>

<body>
<%@ include file="../common/header.jsp" %>
<div style="clear:both"></div>
<div class="content">
    <div class="content_box">
        <div class="content_detail">
            <div class="usercenter_content">
                <div class="usercenter-title">
                    <div class="usertitle-text">自动投标简介</div>
                    <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png"/></div>
                </div>
                <div class="top_big_img"><img src="${ctx}/static/kingkaid/images/toubiao_top.jpg"/></div>
                <div class="usercenter-title" style="margin: 40px 0 0 0">
                    <div class="usertitle-text">自动投标</div>
                    <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png"/></div>
                    <div style="float:right;">
                            <c:choose>
                                <c:when test="${user_obj.memberState eq '4' || user_obj.memberState eq '5'}">
							        <div style="margin-right: 20px">
	                            	请先<a id="closeblack" class="quick_open_btn" style="margin-top: 10px" href="${ctx}/auth/cust/openpay_page.html"  target="_blank">开通银行存管账户</a>
	                        		</div>
							    </c:when>
							    <c:when test="${user_obj.memberState eq '6' || user_obj.memberState eq '7'}">
							        <div style="margin-right: 20px">
	                            	请先<a id="closeblack" class="quick_open_btn" style="margin-top: 10px" href="${ctx}/auth/cust/openpay_page.html"  target="_blank">激活银行存管账户</a>
	                        		</div>
							    </c:when>
							    <c:otherwise>
							        <input type="hidden" id="if_on" value="${setting.ifon}"/>
			                        <input type="hidden" id="if_third_on" value="${setting.ifthirdon}"/>
			                        <c:set var="ifOn" value="${setting.ifon eq '1'}" />
			                        <div class="switch">
			                            <img src="${ctx}/static/kingkaid/images/${ifOn ? 'onopen': 'offclose'}.png"/>
			                        </div>
							    </c:otherwise>
						     </c:choose>
                    </div>
                </div>
                <c:if test="${ifOn}">
                <div class="rank">
                    <div style="float: right; margin-right: 30px">
                        <div class="rank-ele">参考排名:</div>
                        <div class="rank-ele">${rank.rank}</div>
                        <div class="rank-ele">/</div>
                        <div class="rank-ele">${rank.total}</div>
                    </div>
                </div>
                </c:if>

                <div id="sx1">
                    <div class="inputmo modal" style="display: ${ifOn ? "none" : "block"}"></div>
                    <div class="produc-title">产品类别</div>
                    <p id="products">
                        <c:set var="careProduct" value="${setting.careproduct eq '1'}"></c:set>
                        <span id="p_off" ${!careProduct ? 'class="tab1"': ""}>不限</span>
                        <c:forEach items="${products}" var="prd">
                            <span id="p_${prd.prodid}" name="product_set" ${fn:contains(setting.productset, prd.prodid) and careProduct ? 'class="tab1"': ""}>${prd.prodname}</span>
                        </c:forEach>
                    </p>
                    <div class="produc-title">项目期限</div>
                    <p id="terms">
                        <c:set var="careTerm" value="${setting.careterm eq '1'}"></c:set>
                        <span id="t_off" ${!careTerm ? 'class="tab1"': ""}>不限</span>
                        <span id="t_3" name="term_set" ${fn:contains(setting.termset, '3') and careTerm ? 'class="tab1"': ""}>1-3个月</span>
                        <span id="t_6" name="term_set" ${fn:contains(setting.termset, '6') and careTerm ? 'class="tab1"': ""}>4-6个月</span>
                        <span id="t_9" name="term_set" ${fn:contains(setting.termset, '9') and careTerm ? 'class="tab1"': ""}>7-9个月</span>
                        <span id="t_12" name="term_set" ${fn:contains(setting.termset, '12') and careTerm ? 'class="tab1"': ""}>10-12个月</span>
                    </p>
                    <div class="produc-title">项目归属地</div>
                    <p id="banks">
                        <c:set var="careBank" value="${setting.carebank eq '1'}"></c:set>
                        <span id="b_off" ${!careBank ? 'class="tab1"': ""}>不限</span>
                        <c:forEach items="${banks}" var="bank">
                            <span id="b_${bank.bankid}" name="bank_set" ${fn:contains(setting.bankset, bank.bankid) and careBank ? 'class="tab1"': ""}>${bank.bankname}</span>
                        </c:forEach>
                    </p>
                    <div class="produc-title">单笔投资金额 (<span style="font-size:10px;">自动投标将投入该区间内的数字金额</span>)</div>
                    <p id="bidmax">
                        <c:set var="careBidMax" value="${setting.carebidmax eq '1'}"></c:set>
                        <span id="m_off" style="float:left;" ${!careBidMax ? 'class="tab1"': ""}>不限</span>
                        <span id="m_on_min" style="float:left; margin-left:4px; ${careBidMax ? '': 'background-color: #e1e1e1'}" ${careBidMax ? 'class="tab1"': ""}>
                            <input id="m_value_min" class="tenderselected" style="color:#666; height:36px; padding: 0; font-size: 14px; font-family: '微软雅黑'"
                                   placeholder="单笔最小投资金额" ${careBidMax ? '': 'readonly'} type="text" value="${careBidMax ? setting.bidminvalue: ''}"/></span>
                        <span style="border: none; color: #333; width: auto">元</span>
                        &nbsp;&nbsp;-&nbsp;&nbsp;
                        <span id="m_on_max" style="margin-left:4px; ${careBidMax ? '': 'background-color: #e1e1e1'}" ${careBidMax ? 'class="tab1"': ""}>
                            <input id="m_value_max" class="tenderselected" style="color:#666; height:36px; padding: 0; font-size: 14px; font-family: '微软雅黑'"
                                   placeholder="单笔最大投资金额" ${careBidMax ? '': 'readonly'} type="text" value="${careBidMax ? setting.bidmaxvalue: ''}"/></span>
                        <span style="border: none; color: #333; width: auto">元</span>
                        
                        <em id="m_error" class="error_text"></em>
                    </p>
                    <div class="produc-title">自动投标使用加息券</div>
                    <p id="coupon">
                        <c:set var="ifCoupon" value="${setting.ifcoupon eq '1'}"></c:set>
                        <span id="c_off" ${!ifCoupon ? 'class="tab1"': ""}>不使用</span>
                        <span id="c_on" ${ifCoupon ? 'class="tab1"': ""}>使用</span>
                    </p>
                    <div style="text-align: center; margin-bottom: 10px">
                        <button id="btn_save" class="blue-btn">保存</button>
                    </div>
                </div>
                <div style="background-color:#f6f6f6;padding:28px 60px;color:#666;overflow:hidden;line-height:30px;text-align:center;"></div>
            <%@ include file="usercenterleftmenu.jsp" %>
        </div>
    </div>
    <div style="clear:both"></div>
</div>
</div>
<div style="clear:both"></div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>
