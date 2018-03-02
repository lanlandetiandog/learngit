<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/static/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/myvip.js"></script>
</head>

<body>
	<%@ include file="../common/header.jsp" %>
	<div style="clear: both"></div>
	<div class="content">
		<div class="content_box">
			<div class="content_detail">
				<div class="usercenter_content">
					<div style="background:url(${ctx}/static/kingkaid/images/vip_m_bk.png);width:100%;height:614px;">
						<div style="width: 100%; overflow: hidden; margin-top: 80px;">
							<div style="width:100%;height:4px;background:url(${ctx}/static/kingkaid/images/vip_info_top.png) repeat"></div>
							<div style="width: 100%; overflow: hidden; margin: 37px 0;">
								<div class="vip_img">
									<img src="${ctx}/static/kingkaid/images/vip_b_${vipInfo.vipstate eq '1'? 'true' : 'false'}.png" />
								</div>
								<div style="float: left; width: 484px; padding-left: 43px; border-left: 1px solid #e5e5e5;">
									<div class="vip_right_title">我的VIP</div>
									<ul class="my_viplist">
										<li>
										  <span class="myvip_info_title">状态：</span> 
										  <span class="cash_info_right blue-text"> 
										      <img src="${ctx}/static/kingkaid/images/vip_${vipInfo.vipstate eq '1'? 'true' : 'false'}.png" title="${vipInfo.vipstate eq '1'? '已' : '未'}开通VIP会员"/>
                                            <c:if test="${vipInfo.vipstate ne '1'}">
										      <span class="vip_red_btn" style="margin-left: 30px;" onclick="javascript:window.location.href='${ctx}/auth/usercenter/join_vip.html';">成为VIP</span>
										    </c:if>
										  </span>
									      <div style="clear: both;"></div>
									    </li>
									    <c:if test="${vipInfo.vipstate eq '1'}">
										  <li>
										    <span class="myvip_info_title">有效期：</span> 
										    <span class="cash_info_right blue-text"><fmt:formatDate value="${vipInfo.vipenddate}" pattern="yyyy年MM月dd日" />
											    <span class="vip_red_btn" onclick="javascript:window.location.href='${ctx}/auth/usercenter/join_vip.html';">续期</span>
										    </span>
										    <div style="clear: both;"></div>
									      </li>
									    </c:if>
										<li><span class="myvip_info_title">特权：</span>
											<div class="cash_info_right">				
												<div class="privilege">													
													<c:choose>
													    <c:when test="${user_obj.memberState eq '4' || user_obj.memberState eq '5'}">
													        <span>1. 提现手续费5折优惠</span><a href="${ctx}/auth/cust/openpay_page.html"><span class="vip_btn">立即开通</span></a>													        
													    </c:when>
													    <c:when test="${user_obj.memberState eq '6' || user_obj.memberState eq '7'}">
													        <span>1. 提现手续费5折优惠</span><a href="${ctx}/auth/cust/openpay_page.html"><span class="vip_btn">立即激活</span></a>													        
													    </c:when>
													    <c:otherwise>
													        <span>1. 提现手续费5折优惠</span><a href="${ctx}/auth/xabank/acct/withdraw_page.html"><span class="vip_btn">提现</span></a>
													    </c:otherwise>
													</c:choose>
												</div>
											</div>
											<div style="clear: both;"></div></li>
									</ul>
								</div>
							</div>
							<div
								style="width:100%;height:4px;background:url(${ctx}/static/kingkaid/images/vip_info_down.png) repeat"></div>
						</div>
					</div>
					<%@ include file="usercenterleftmenu.jsp"%>
				</div>

			</div>
			<div style="clear: both"></div>
		</div>
		<div style="clear: both"></div>
	</div>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>
