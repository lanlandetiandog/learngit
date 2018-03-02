<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/project/creditdetail.js"></script>
<script type="text/javascript" src="${ctx}/static/plugin/js/timer.jquery.js"></script>
<style>
    .red_round_btn{cursor:pointer;}
    .tender-money-input{font-size:16px;}
</style>
</head>
 
<body>
    <jsp:include page="../common/header.jsp" flush="true">
		<jsp:param name="f" value="1"/>
	</jsp:include>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div style="margin-top:108px;" class="user_top_alert">
                    <a class="top_return_btn" href="invest_list_page.html">返回项目列表</a>
                    <div style="float:right;margin:5px 10px 0 0;font-size:16px;" onclick="counterBox();">
                        <a><img style="margin-bottom:-10px;" src="${ctx}/static/kingkaid/images/counter.jpg" /></a>
                        <span>收益计算器</span>
                    </div>
                    <div style="clear:both"></div>
                </div>
                <div class="prj_detail">
                    <div class="prj_detail_left">
                        <div class="detail_left_top">
                       		<input type="hidden" name="loanid" id="loanid" value="${creditdetail.loanid}"/>
                            <div class="prj_detail_name" style="color:#4e89cb">${creditdetail.projecttitle}</div>
                            <div class="gray_small_btn" style="margin:3px 0 0 10px;">转让债权</div>
                            <div style="float:right;margin-top:3px;">原项目名称：<a class="blue_link" href="loan_detail_page.html?loanid=${creditdetail.loanid}">${creditdetail.projecttitle}</a></div>
                        </div>
                        <div class="prj_detail_info"> 
                            <span>债权金额：<span class="red_text"><fmt:formatNumber pattern="0.#####" value="${creditdetail.creditormoney/10000}"/>万</span></span>
                            
                            <span style="margin:0 55px">待收利息：<span class="red_text">${creditdetail.collectmoney}元 </span></span>
                            
                            <span>转让价格：<span class="red_text">${creditdetail.transfermoney}元</span></span>
                        </div>
                        <div style="border-bottom:1px solid #e5e5e5;padding-bottom:40px;">
                            <div style="color:#666;font-weight:bold;line-height:25px;margin:20px 0">
                                <c:if test="${creditdetail.overdate < 0}" >
                            		 <span>剩余期限：0天</span>  
                            	</c:if>  
                            	<c:if test="${creditdetail.overdate > 0}" >
                            		 <span>剩余期限：${creditdetail.overdate}天</span>  
                            	</c:if> 
                            	            
                                <span style="margin-left:50px;">项目利率：${creditdetail.interate}%</span>
                                <span style="margin-left:50px;">还款方式：${creditdetail.retukindname}</span> 
                            </div>
                            
                            <div>
                                <img style="margin:0 2px -1px 0" src="${ctx}/static/kingkaid/images/time_icon1.jpg" />
                                <span>债权转让的剩余时间：<span id="spanSurplusTime" class="blue-text">0天00小时00分00秒</span></span>
                                <input type="hidden" value="${restSeconds}" id="restSeconds" />
                                <input type="hidden" value="${creditdetail.transferstate}" id="transferState" />
                                <img style="margin:0 2px -1px 50px" src="${ctx}/static/kingkaid/images/time_icon2.jpg" />
                                <span>下期还款日：<fmt:formatDate value="${creditdetail.sdate}" pattern="yyyy-MM-dd"/></span>
                            </div>
                        </div>
                    </div>
                    <div class="prj_detail_right">
                    	<form id="form" action="" method="post" target="_blank">
                    		<input id="ctfId" type="hidden" name="ctfId" value="${creditdetail.creditortransferid}"/>
	                    	<shiro:authenticated>
		                        <div class="mini_tender">
		                            <div><input type="text" class="tender-money-input" value="${creditdetail.transfermoney}" readonly="readonly"/>元</div>
		                            <div style="font-size:12px;margin:10px 0;">
		                                <span>账户可用余额：<label id="balance_show">${acctInfo.bal}</label> 元</span>
		                                <a class="blue_link" href="${ctx}/auth/xabank/acct/deposit_page.html">[充值]</a>
		                            </div>
		                            <div style="font-size:20px;margin:30px 0 10px 0;">
		                                	购买总额：<span class="red_text">${creditdetail.transfermoney}</span>元
		                            </div>
		                            <div></div>
		                        </div>
		                        <div class="mini_tender_btm" style="margin-bottom:60px;">
		                        	<c:choose>
		                        		<c:when test="${user_obj.memberState eq '4'||user_obj.memberState eq '5'}">
	                       					<div class="red_round_btn" style="background-color:#666;" title="未开通银行存管账户"><a href="${ctx}/auth/cust/openpay_page.html">开通银行存管账户</a></div>
	                       				</c:when>
	                       				<c:when test="${user_obj.memberState eq '6'||user_obj.memberState eq '7'}">
	                       					<div class="red_round_btn" style="background-color:#666;" title="未激活银行存管账户"><a href="${ctx}/auth/cust/openpay_page.html">激活银行存管账户</a></div>
	                       				</c:when>
		                        		<c:when test="${creditdetail.transferstate eq '1'}">
				                            <div class="red_round_btn" style="background-color:#666;">已转让</div>
		                        		</c:when>
		                        		<c:when test="${creditdetail.transferstate eq '3'}">
				                            <div class="red_round_btn" style="background-color:#666;">已取消</div>
		                        		</c:when>
		                        		<c:when test="${creditdetail.transferstate eq '2'}">
				                            <div class="red_round_btn" style="background-color:#666;">已失效</div>
		                        		</c:when>
		                        		<c:when test="${creditdetail.transferstate eq '0'}">
		                        		  <c:if test="${!flag}">
		                        		     <div class="red_round_btn" style="background-color:#666;">已失效</div>
		                        		  </c:if>
		                        		  <c:if test="${flag}">
		                        		    <c:if test="${(acctInfo.bal-creditdetail.transfermoney) < 0}">
		                        		       <div class="red_round_btn" style="background-color:#666;">确认认购</div>
		                        		    </c:if>
		                        		    <c:if test="${(acctInfo.bal-creditdetail.transfermoney) > 0}">
		                        		       <div class="red_round_btn" id="credit_btn">确认认购</div>
		                        		    </c:if>
		                        		  </c:if>
		                        		</c:when>
		                        		<c:otherwise>
		                        			<div class="red_round_btn" id="credit_btn">确认认购</div>
		                        		</c:otherwise>
		                        	</c:choose>
		                        </div>
	                    	</shiro:authenticated>
                    	</form>
                    	<shiro:guest>
	                        <div class="login_tender" style="margin-bottom:60px;">
	                            <div class="red_round_btn" style="margin:70px 0 20px 0" onclick="javascript:window.location.href='${ctx}/login.html';">登录并认购</div>
	                        </div>
                    	</shiro:guest>
                    </div>
                </div>
                 
            </div>
            <div style="clear:both"></div>       
        </div>
        <div style="clear:both"></div>        
    </div>      
    <div id="creditsubmit">
    
    </div> 
    <%@ include file="../common/footer.jsp"%>
</body>
</html>
