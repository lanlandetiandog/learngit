<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/static/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/kingkaid/css/simple.pagination.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/simple.pagination.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/plugin/js/My97DatePicker/skin/WdatePicker.css"/>
<script type="text/javascript" src="${ctx}/static/plugin/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/mycoin.js"></script>
</head>
<body>
	<jsp:include page="../common/header.jsp" flush="true">
		<jsp:param name="f" value="1" />
	</jsp:include>
	<div style="clear: both"></div>
	<div class="content">
		<div class="content_box">
			<div class="content_detail">
				<div class="usercenter_content">
					<div class="coin-top">
						<div class="top-text-left">
							<div style="float: left;">金开币余额：<span style="color: #e04d41;">${coinTotalAmt}</span></div>
							<div style="float: left; margin-left: 20px">年底到期金额：<span style="color: #e04d41;">${currExpiredAmt}</span></div>
						</div>
						<a class="coin_top_btn" href="#detail_list">金开币明细</a>
					</div>
					<div class="usercenter-title">
						<div class="usertitle-text">获得金开币</div>
						<div class="usertitle-img">
							<img src="${ctx}/static/kingkaid/images/label_right.png" />
						</div>
					</div>

					<div class="coin_top_box">
						<div class="unit_coin_type">
							<div class="coin_type_img">
								<img src="${ctx}/static/kingkaid/images/f1.png" />
							</div>
							<div class="coin_right_text">
								<div>开通银行存管电子账户并激活成功，可获得40个金开币。根据账户激活的时间，每个账户周年日可获得10个金开币。</div>
								<c:choose>
								    <c:when test="${user_obj.memberState eq '4' || user_obj.memberState eq '5'}">
								        <a class="coin_btn" href="${ctx}/auth/cust/openpay_page.html">开通银行存管账户</a>
								    </c:when>
								    <c:when test="${user_obj.memberState eq '6' || user_obj.memberState eq '7'}">
								        <a class="coin_btn" href="${ctx}/auth/cust/openpay_page.html">激活银行存管账户</a>
								    </c:when>
								    <c:otherwise>
								        <a class="coin_btn_finish">已激活</a>
								    </c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="unit_coin_type">
							<div class="coin_type_img">
								<img src="${ctx}/static/kingkaid/images/f2.png" />
							</div>
							<div class="coin_right_text">
								<div>完成首次充值可获得50个金开币。今后的每笔投资，所投项目成功定标后可获得所投金额年化值的0.5‰个金开币。</div>
								<c:choose>
								    <c:when test="${user_obj.memberState eq '4' || user_obj.memberState eq '5'}">
								        <a class="coin_btn" href="${ctx}/auth/cust/openpay_page.html">开通银行存管账户</a>
								    </c:when>
								    <c:when test="${user_obj.memberState eq '6' || user_obj.memberState eq '7'}">
								        <a class="coin_btn" href="${ctx}/auth/cust/openpay_page.html">激活银行存管账户</a>
								    </c:when>
								    <c:otherwise>
								        <a class="coin_btn" href="${ctx}/auth/xabank/acct/deposit_page.html">充值投标</a>
								    </c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					<div class="coin_bottom_box">
						<div class="unit_coin_type">
							<div class="coin_type_img">
								<img src="${ctx}/static/kingkaid/images/f3.png" />
							</div>
							<div class="coin_right_text">
								<div>分享个人专属聚财码给好友，好友使用您的聚财码成功完成注册后，您与好友将同时获得金开币奖励。使用您聚财码注册的好友越多，您获得的金开币也就越多。</div>
								<a class="coin_btn" href="${ctx}/auth/usercenter/myshare.html">邀请好友</a>
							</div>
						</div>
						<div class="unit_coin_type">
							<div class="coin_type_img">
								<img src="${ctx}/static/kingkaid/images/f4.png" />
							</div>
							<div class="coin_right_text">
								<div style="height:140px">完善个人账户资料，包括电子邮箱、物流地址、紧急联系人等，不仅可以提高账户安全系数，还可获得10个金开币。</div>
								<c:choose>
                                    <c:when test="${user_obj.fullfilled}">
                                        <a class="coin_btn_finish">已完善</a>
                                    </c:when>
                                    <c:otherwise>
								        <a class="coin_btn" href="${ctx}/auth/usercenter/safetycenter.html">完善资料</a>
                                    </c:otherwise>
                                </c:choose>
							</div>
						</div>
					</div>


					<div class="usercenter-title">
						<div class="usertitle-text">使用金开币</div>
						<div class="usertitle-img">
							<img src="${ctx}/static/kingkaid/images/label_right.png" />
						</div>
					</div>

					<div class="coin_top_box">
						<div class="unit_coin_type">
							<div class="coin_type_img">
								<img src="${ctx}/static/kingkaid/images/sf1.png" />
							</div>
							<div class="coin_right_text">
								<div>投标时，金开币可补充您的部分投标金额。使用金开币进行投标的金额不得超过投标金额的0.1%。例如您计划投资10万元，可使用100个金开币冲抵100元现金。</div>
								<a class="coin_btn" href="${ctx}/project/invest_list_page.html">投标金补给</a>
							</div>
						</div>
						<div class="unit_coin_type">
							<div class="coin_type_img">
								<img src="${ctx}/static/kingkaid/images/sf2.png" />
							</div>
							<div class="coin_right_text">
								<div style="height:140px">金开币可在您提现时冲抵提现手续费，冲抵比例为1:1，即1个金开币可冲抵1元人民币。</div>
								<c:choose>
								    <c:when test="${user_obj.memberState eq '4' || user_obj.memberState eq '5'}">
								        <a class="coin_btn" href="${ctx}/auth/cust/openpay_page.html">开通银行存管账户</a>
								    </c:when>
								    <c:when test="${user_obj.memberState eq '6' || user_obj.memberState eq '7'}">
								        <a class="coin_btn" href="${ctx}/auth/cust/openpay_page.html">激活银行存管账户</a>
								    </c:when>
								    <c:otherwise>
								        <a class="coin_btn" href="${ctx}/auth/xabank/acct/withdraw_page.html">提现费冲抵</a>
								    </c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					<div class="coin_bottom_box">
						<div class="unit_coin_type">
							<div class="coin_type_img">
								<img src="${ctx}/static/kingkaid/images/sf3.png" />
							</div>
							<div class="coin_right_text">
								<div style="height:84px">金开币可用于购买VIP权限。</div>
								<a class="coin_btn" href="${ctx}/auth/usercenter/join_vip.html">${user_obj.vip ? "续期" : "成为"}VIP</a>
							</div>
						</div>
						<div class="unit_coin_type">
							<div class="coin_type_img">
								<img src="${ctx}/static/kingkaid/images/sf4.png" />
							</div>
							<div class="coin_right_text">
								<div>金开币可用于金开贷礼品商城的礼品兑换，点击首页“礼品商城”进行支付兑换即可。</div>
 								<a class="coin_btn" href="${ctx}/gift/mall.html">立即兑换</a>
							</div>
						</div>
					</div>


					<div class="usercenter-title">
						<div class="usertitle-text"><a name="detail_list"></a>金开币明细</div>
						<div class="usertitle-img">
							<img src="${ctx}/static/kingkaid/images/label_right.png" />
						</div>

					</div>

					<div class="search-area"
						style="border-left: 1px solid #D8E1E5; border-right: 1px solid #D8E1E5; width: 805px;">

						<div style="float: left;">
							<span class="shelf_search_title">开始日期:</span> <input type="text"
								id="q_start_date"
								onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"
								class="shelf_search_input" style="float: left;" /> <span
								class="shelf_search_title">结束日期:</span> <input type="text"
								id="q_end_date"
								onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"
								class="shelf_search_input" style="float: left;" />
						</div>
						<div id="search_code_btn" class="search_code_btn">查询</div>

					</div>

					<table class="money_order_tb" id="coinhist_table"
						style="border-left: 1px solid #D8E1E5; border-right: 1px solid #D8E1E5; margin-top: 0;">
						<thead>
						  <tr>
							<th style="width: 180px;">发生时间</th>
							<th style="width: 138px;">获取/使用途径</th>
							<th style="width: 111px;">金额</th>
							<th style="width: 180px;">到期时间</th>
							<th>余额</th>
						  </tr>
						</thead>
						<tbody>
					    </tbody>
					</table>
					<%@ include file="usercenterleftmenu.jsp"%>
					<div id="pager" style="margin-left:15px; margin-bottom:10px"></div>
				</div>
			</div>
			<div style="clear: both"></div>
		</div>
		<div style="clear: both"></div>
	</div>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>
