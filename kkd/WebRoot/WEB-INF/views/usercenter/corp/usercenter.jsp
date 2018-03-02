<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/static/common/meta.jsp"%>
<link type="text/css" rel="stylesheet" href="${ctx}/static/kingkaid/css/xa_webchange20170308.css" />
<script type="text/javascript">
    $(document).ready(function(){
        $(".jdk-middle ul li").hover(
            function(){
                $(this).addClass("jdk-middle-li-hover");
                $(this).find(".middle_info_num").addClass("middle_info_num_hover");
            },
            function(){
                $(this).removeClass("jdk-middle-li-hover");
                $(this).find(".middle_info_num").removeClass("middle_info_num_hover");
            }
        );

        $("#menu_jkd").addClass("leftmenu_cur");
        $("#usermenu_jkd").addClass("user_menulist_href_hover");
        
        $(".xa_btnclosed").click(function(){
    		  $(".shade").css("display","none");
    	});
        
        $("#openblack").click(function(){
        	showRechargeOverBox('用户开户','开户完成前请不要关闭该窗口！');    	
        });
        
        $("#signblack").click(function(){
        	showRechargeOverBox('用户激活','激活完成前请不要关闭该窗口！');    	
        });
        
    });
</script>
<style type="text/css"> 
.userremind {
    width: 980px;
}
</style>
</head>
 
<body>
    <jsp:include page="../../common/header.jsp" flush="true">
        <jsp:param name="f" value="1"/>
    </jsp:include>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="usercenter_content">
                    <div class="jkd_top">
                        <div class="jkd_userinfo">
                            <img style="float:left;" src="${ctx}/static/kingkaid/images/head_user.png" />
                            <div class="userinfo_list">
                                <div class="info_img_list">

                                    <span class="username">${user.memberName}</span>
                                    <span style="margin:0 10px;color:#d8e1e5;float:left;">|</span>
                                    <c:choose>
                                        <c:when test="${outData.memberstate eq '8'}">
                                            <a title="实名认证"><img src="${ctx}/static/kingkaid/images/message_user.png"/></a>
                                        </c:when>
                                        <c:otherwise>
                                            <a title="未实名认证"><img src="${ctx}/static/kingkaid/images/message_user_none.png"/></a>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                    	<c:when test="${outData.authpercent eq '1'}">
                                    		<a title="账户安全"><img src="${ctx}/static/kingkaid/images/safe.png"/></a>
                                    	</c:when>
                                    	<c:otherwise>
                                    		<a title="账户安全"><img src="${ctx}/static/kingkaid/images/safe_none.png"/></a>
                                    	</c:otherwise>
                                    </c:choose>

                                    <a title="站内信"><img onclick="javascript:window.location.href='${ctx}/auth/usercenter/corp/corpmessage.html';" src="${ctx}/static/kingkaid/images/mail_user.png"/></a>
                                    <c:if test="${outData.unreadmsg ne '0'}">
                                        <img style="margin:1px 0 0 -9px" src="${ctx}/static/kingkaid/images/message_warm.png"/>
                                    </c:if>
                                    
                                </div>
                                <div>
                                    <span>
                                        <span>金开币：</span>
                                        <a class="top-info-link">${outData.coinamount}</a>
                                    </span>
                                </div>
                            </div>
                           
                        </div>
                         
                          <!--css文件*9.22修改*-->
                         <div class="bankopen">
                            <div class="bankxiancard4">
                            	<div class="smlogo"><img src="${ctx}/static/kingkaid/images/smallbankofxianlogo.png"/></div>
                            	<span class="bankopen-name">电子账户</span>
                                <div style="clear:both"></div>                               
                            	<span class="bankopen-num">
                            	   <c:choose>
	                            		<c:when test="${empty user.custAcNo}">您尚未开通银行存管电子账户</c:when>
	                            		<c:otherwise>${user.custAcNo}</c:otherwise>
                            		</c:choose>
                            	</span>
                            </div>
                           <%--  <c:choose>
							    <c:when test="${user_obj.memberState eq '4' || user_obj.memberState eq '5'}">
							        <a id="openblack" class="top_charge" style="margin-bottom:21px;padding: 0 11px;margin-top: 23px;" href="${ctx}/auth/cust/openpay_page.html" target="_blank">立即开通</a>
							    </c:when>
							    <c:when test="${user_obj.memberState eq '6' || user_obj.memberState eq '7'}">
							        <a id="signblack" class="top_charge" style="margin-bottom:21px;padding: 0 11px;margin-top: 23px;" href="${ctx}/auth/cust/openpay_page.html" target="_blank">立即激活</a>
							    </c:when>
							    <c:otherwise>
							        <div class="top_charge" style="margin-bottom:21px;" onclick="javascript:window.location.href='${ctx}/auth/xabank/acct/deposit_page.html'">充值</div>
                                    <div class="draw_cash" onclick="javascript:window.location.href='${ctx}/auth/xabank/acct/withdraw_page.html'">提现</div>
							    </c:otherwise>
							</c:choose>  --%>                         
                         </div>

                    </div>
                    
                    <c:if test="${cashBal.cashbal gt 0.00}">
                       <div class="huifu font16 font500 margt66">
							<div class="fl palr"><img src="${ctx}/static/kingkaid/images/image/huifu.png"/></div>
							<span>余额不可用，<a class="colblu" onclick="javascript:window.open('${ctx}/auth/usercenter/cashall.html','_self')">需提现</a></span>
						</div>
                    </c:if>
                  
                    <div class="jdk-middle">
                        <ul style="margin-left:150px;">
                            <li>
                               <c:choose>
							    <c:when test="${user_obj.memberState eq '4' || user_obj.memberState eq '5'}">
							        <div style="cursor: pointer;">
	                                    <div>银行卡</div>
	                                    <div>暂未绑定</div>
	                                </div>
							    </c:when>
							    <c:otherwise>
								    <div style="cursor: pointer;" onclick="javascript:window.location.href='${ctx}/auth/usercenter/myproperty.html';">
	                                    <div>银行卡</div>
	                                    <div class="middle_info_num">（尾号<span>${bankcode}</span>）</div>
	                                </div>
							    </c:otherwise>
							   </c:choose> 
                            </li>
                            <li>
                                <div>借款项目</div>
                                <div class="middle_info_num">（<span>${outData.loanapp}个</span>）</div>
                            </li>
                            <li>
                           		<div style="cursor: pointer;" onclick="javascript:window.location.href='${ctx}/auth/usercenter/mywarrant.html';">
                                	<div>担保项目</div>
                                	<div class="middle_info_num">（<span>${outData.securedproj}个</span>）</div>
                                </div>
                            </li>
                        </ul>
                    </div>
                    
                    <div class="jkd_bottom" style="width:826px;height:149px;margin:0 auto;margin-bottom:70px;padding-top:0px;">
                        <ul>
                            <li>
                                <div>账户余额</div>
                                <div class="jkd_money">&yen;<fmt:formatNumber value="${acctInfo.bal}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li>
                                <div>冻结余额</div>
                                <div class="jkd_money">&yen;<fmt:formatNumber value="${acctInfo.fbal}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li style="border:none;">
                                <div>本期待付</div>
                                <div class="jkd_money">&yen;<fmt:formatNumber value="${acctInfo.dincome}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li>
                                <div>待还本金</div>
                                <div class="jkd_money">&yen;<fmt:formatNumber value="${acctInfo.debt}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li>
                                <div>代付利息</div>
                                <div class="jkd_money">&yen;<fmt:formatNumber value="${acctInfo.dcapi}" pattern="#,##0.00"/>元</div>
                            </li>

                        </ul>
                        <div class="jkd_total_money">
                            <div class="total_money_top">当前总负债</div>
                            <div class="total_money_num">&yen;<fmt:formatNumber value="${acctInfo.lincome}" pattern="#,##0.00"/>元</div>
                        </div>
                    </div>
                    <%@ include file="usercenterleftmenu.jsp"%>
                </div>
            </div>
            <div style="clear:both"></div>         
        </div> 
        <div style="clear:both"></div>       
    </div>          
    <div id="footer"></div>
    <c:if test="${cashBal.cashbal gt 0.00}">
	    <div class="shade">
			<div  class="xa_remind">
				<div class="xa_guideimg"><img src="${ctx}/static/kingkaid/images/image/huifuback.png"/></div>
				<div class="xa_btnclosed"></div>
			</div>
		</div>
    </c:if>   
    <%@ include file="../../common/footer.jsp"%>
</body>
</html>
