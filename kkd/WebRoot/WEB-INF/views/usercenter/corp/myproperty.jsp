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
<link type="text/css" rel="stylesheet" href="${ctx}/static/kingkaid/css/xa_webchange20170308.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/corp/myproperty.js"></script>
<script type="text/javascript" src="${ctx}/static/plugin/js/PassGuardCtrl.js"></script>
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

var pgeditorBind = new $.pge({
	pgePath: ctx + "/static/xabank/ocx/",
	pgeId: "_ocx_password_bind",
	pgeEdittype: 0,
	pgeEreg1: "[0-9]*",
	pgeEreg2: "[0-9]{6}",
	pgeMaxlength: 6,
	pgeClass: "ocx_style",
	pgeInstallClass: "ocx_style",
	pgeOnfocus: "clearError('_ocx_password_bind')"
});

pgeditorBind.pgInitialize();
</script>

<style type="text/css">
.ocx_style {
	width: 234px;
	height: 32px;
	line-height: 32px;
	border-radius: 4px;
	padding: 0 14px;
	color: #000;
	border: 1px #d4d4d4 solid;
}
.bdg_card{
	width: 750px;
	height: 450px;
	padding: 0 24px;
	margin-top: 34px;
	font-size: 14px;
	overflow: hidden;
}
.xa_bdgtitle{
	height: 54px;
	line-height: 54px;
	font-size: 18px;
	display: block;
	color: #000;
}
.xa_input{
	height: 60px;
	line-height: 60px;
	margin-left: 74px;
}
.fir_name{
	width: 84px;
	margin-right: 24px;
	display:inline-block;
}
.xa_input>input,.xa_sub,.xa_cancel{
	width: 234px;
	height: 32px;
	line-height: 32px;
	border-radius: 4px;
	padding: 0 14px;
	color: #000;
	border: 1px #d4d4d4 solid;
}

.xa_subprom,.xa_numprom{
	color: #7c7c7c;
}
.xa_btngroup{
	width:400px;
	height: 84px;
	line-height: 84px;
	margin-left: 185px;
	padding-top: 30px;
}
.xablue-btn{
	background: #5c88c9;
	color: #fff;
	margin-top: 30px;
	display: block;
}
.xa_sub{
	background: #5c88c9;
	text-align: center;
	cursor: pointer;
	float: left;
	color: #fff;
}
.xa_sub:hover{
	background: #4771af;
}
.xa_cancel{
	width: 90px;
	float:right;
	text-align: center;
	cursor: pointer;
	background: #f4f4f4;
	color:#b2b2b2;
}
.xa_cancel:hover{
	background: #edecec;
}
.bdg_card input{
	color:#000;
}

.disin{
	background: #f5f5f5;
}/*input禁止输入时样式*/

      /*解除银行卡时*/
/*金额不为0时*/
.xa_subinput p{
	height: 24px;
	padding-left: 114px;
	line-height: 24px;
	color: #ff5761;
}
#_ocx_password_str{
    position: absolute;
    left: 112px;
    top:5px;
}
#_ocx_password_bind_str{
    position: absolute;
    left: 112px;
    top:5px;
}
.error{
    position: absolute;
    top:-13px;
    right: -109px;
}
.code_gray{	
	background: #99a7ba;
	text-align: center;
	cursor: pointer;
	float: left;
	color: #fff;
	width: 234px;
	height: 32px;
	line-height: 32px;
	border-radius: 4px;
	padding: 0 14px;
	border: 1px #d4d4d4 solid;
}
.userremind {
    width: 980px;
}
</style>
</head>
<body>
    <jsp:include page="../../common/header.jsp" flush="true">
        <jsp:param name="f" value="1" />
    </jsp:include>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="usercenter_content">
                  <div id="propertyinfo">
                    <div class="usercenter-title">
                        <div class="usertitle-text">我的资产</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                    </div>
                    
                    <c:if test="${cashBal.cashbal gt 0.00}">
	                    <div class="huifu font16 font500">
	                    	<div class="fl palr"><img src="${ctx}/static/kingkaid/images/image/huifu.png"/></div>
	                    	<span>余额不可用，<a class="colblu" onclick="javascript:window.open('${ctx}/auth/usercenter/cashall.html','_self')">需提现</a></span>
	                    </div>
                    </c:if>
                    
                    <div class="property_top" style=" margin:20px auto;position:relative;">
                        <ul style="margin-top:30px;">
                            <li>
                                <div>资金总额</div>
                                <div class="jkd_money">&yen;<fmt:formatNumber value="${acctInfo.bal+acctInfo.fbal}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li>
                                <div>可用资金</div>
                                <div class="jkd_money">&yen;<fmt:formatNumber value="${acctInfo.bal}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li>
                                <div>冻结资金</div>
                                <div class="jkd_money">&yen;<fmt:formatNumber value="${acctInfo.fbal}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li>
                                <div>应付代偿款</div>
                                <div class="jkd_money">&yen;<fmt:formatNumber value="${acctInfo.compcapi}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li style="border-right:none;">
                                <div>金开币余额</div>
                                <div class="jkd_money"><fmt:formatNumber value="${acctInfo.coinbal}" pattern="#,##0.00"/></div>
                            </li>

                        </ul>
                        <div style="position:absolute;right:0;bottom:0;"><img src="${ctx}/static/kingkaid/images/asset_table_right.png" /></div>
                    </div>

                   <%--  <div class="card_area">
                       <form id="form" action="${ctx}/auth/pay/bindcard" method="post" target="_blank">
                            <div style="float:left;width:710px;">
                                <c:set var="qp" value="0"></c:set>
	                        	<c:forEach items="${bankcards}" var="bankcard">
		                            <div class="card-unit" id="${bankcard.keyno}"> 
		                                <div class="bank-card">
		                                    <div style="height:49px;">
		                                        <span style="float:left;" id="bankname_str">${bankcard.bankname}</span>
		                                        <span style="float:right;"></span>
		                                    </div>
		                                    <div style="line-height:24px;" id="bankacno_str">
		                                    	<kkd:bankCardFormat cardId="${bankcard.bankacno}"></kkd:bankCardFormat>(快捷)
		                                    </div>
		                                </div>
			                            <div class="del-card" title="解绑快捷银行卡"><img onclick="showDeleteCard()" src="${ctx}/static/kingkaid/images/asset_del.png"/></div>
		                            </div>
	                        	</c:forEach>
                                
                                
                                <c:if test="${fn:length(bankcards) == 0}">
		                            <div class="add_card">
		                            	<c:choose>		                            		
		                            		<c:when test="${user_obj.memberState eq '8' || user_obj.memberState eq '6' || user_obj.memberState eq '7'}">
		                            			<a style="" onclick="ctp_addbankcard_up();"><img src="${ctx}/static/kingkaid/images/add-icon.jpg" /></a>
				                                <div style="line-height:24px;">绑定银行卡</div>
		                            		</c:when>
		                            		<c:otherwise>
		                            			<div style="line-height:24px;" title="您尚未开通银行存管电子账户">
		                            				您尚未开通银行存管电子账户
		                            			</div>
		                            		</c:otherwise>
		                            	</c:choose>
		                            </div>
								</c:if>
                            </div>    
                        </form>
                        <div style="float:right;">
                            <div style="margin-bottom:5px;overflow:hidden;"><a href="${ctx}/auth/xabank/acct/deposit_page.html" class="top_charge">充值</a></div>
                            <div style="overflow:hidden;"><a href="${ctx}/auth/xabank/acct/withdraw_page.html" class="draw_cash">提现</a></div>
                        </div>

                    </div> --%>
                    
                    <div class="xa_cardarea mg20">
                       <div class="xa_telcard fl">
                       		<p class="lin24"><span class="wid67">电子账号：</span><span class="wid200 clred undline">${user.custAcNo}</span></p>
                       		<p class="lin24"><span class="wid67">开户名：</span><span class="wid200 undline">${user.custName}</span></p>
                       		<p class="lin24"><span class="wid67">开户行：</span><span class="wid200 undline">西安银行</span></p>
                       		<!--未激活时按钮文字为激活，激活之后激活显示为充值，提现按钮显示
                       		<a class="xa_outcash xa_numactivbl fr bgbl bderbl">提现</a>
                       		-->
                       		<c:choose>
							    <c:when test="${user_obj.memberState eq '4' || user_obj.memberState eq '5'}">
							        <a class="xa_sense xa_numactiv fr"  href="${ctx}/auth/cust/openpay_page.html" target="_blank">立即开通</a>
							    </c:when>
							  <%--   <c:when test="${user_obj.memberState eq '6' || user_obj.memberState eq '7'}">
							        <a class="xa_sense xa_numactiv fr"  href="${ctx}/auth/cust/openpay_page.html" target="_blank">激活</a>
							    </c:when> --%>
							    <c:otherwise>
							        <a href="${ctx}/auth/xabank/acct/deposit_page.html" class="xa_sense xa_numactiv fr">充值</a>
                                    <a href="${ctx}/auth/xabank/acct/withdraw_page.html" class="xa_outcash xa_numactivbl fr bgbl bderbl">提现</a>
							    </c:otherwise>
							</c:choose>         
                       </div> 
					   <div class="xalink fl"><img src="${ctx}/static/kingkaid/images/image/xalink.png"/></div>
                   		<!--绑定银行卡-->
                   		<div class="xa_bindcard fl">
                   			<c:if test="${not empty bankcode}">
	                   			<p>绑定银行：</p>
	                   			<p><kkd:bankCardFormat cardId="${bankcode}"></kkd:bankCardFormat> ${bankname}</p>
	                   			<c:if test="${changeCardRecord.state eq '3'}">
		                   			申请拒绝，<a id="checkReason" title="${changeCardRecord.remark}" style="color: red;">查看原因</a>
	                   			</c:if>
	                   			<c:if test="${changeCardRecord.state ne '1'}">
	                            	<a href="${ctx}/auth/xabank/acct/change_card_page.html" class="xa_outcash xa_numactivbl fr bgbl bderbl" style="width: 86px;">更换银行卡</a>
	                           	</c:if>
	                           	<c:if test="${changeCardRecord.state eq '1'}">
	                            	<a href="#" class="xa_outcash xa_numactivbl fr bgbl bderbl" style="width: 86px;">审批中</a>
	                           	</c:if>
                   			</c:if>
                   		</div>
                    </div>
                    
                    
                    <div class="usercenter-title">
                        <div class="usertitle-text">资金记录查询</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>                                           
                        <div style="float:right;margin-right:10px;">
	                        <%-- <a class="jkb-order-search" style="cursor: pointer;" onclick="javascript:window.open('${ctx}/auth/usercenter/pnraccdetail.html','_self')">汇付天下账户明细账查询</a>  --%> 
	                        <a class="jkb-order-search" style="cursor: pointer;" onclick="javascript:window.location.href='${ctx}/auth/usercenter/mycoin.html#detail_list'">金开币记录查询</a>                          
                            <c:if test="${cashBal.cashbal gt 0.00}"> 
                            	<a class="pay-account-search" style="cursor: pointer;padding: 0 20px;" onclick="javascript:window.open('${ctx}/auth/usercenter/cashall.html','_self')">汇付天下账户提现</a>                          
                            </c:if>
                        </div>                        
                    </div>

                    <div class="search-area">
                        <div style="float:left;">
                            <span class="shelf_search_title">资金通道</span>
                            <select id="prochanel" class="shelf_search_input" style="float:left;height:25px;border: #999 1px solid;color: #666;">
                               <option value="4">西安银行</option>
                               <option value="1">汇付天下</option>
                            </select>
                            <span class="shelf_search_title">开始时间:</span>
                            <input type="text" id="q_start_date" onFocus="WdatePicker({startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy年M月d日 HH:mm:ss',alwaysUseStartDate:true})" class="shelf_search_input" style="float:left;" />
                            <span class="shelf_search_title">结束时间:</span>
                            <input type="text" id="q_end_date" onFocus="WdatePicker({startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy年M月d日 HH:mm:ss',alwaysUseStartDate:true})" class="shelf_search_input" style="float:left;"/>
                        </div>
                        <div class="search_code_btn">查询</div>
                    </div>
                    <table class="money_order_tb" id="acctdetail_table">
                        <thead>
	                        <tr>                                                 
	                            <th style="width:179px;">时间</th>
	                            <th style="width:136px;">借款项目</th>
	                            <th style="width:118px;">摘要</th>
	                            <th style="width:124px;">出入(元)</th>
	                            <th>余额(元)</th>
	                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                    <div id="pager" style="margin-left:15px; margin-bottom:10px"></div>
                  </div>
                  <div  id="deleteCard" style="display:none;">
	                <div class="usercenter-title">
	                        <div class="usertitle-text">我的资产</div>
	                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
	                </div>
                   <!--解除绑定银行卡-->
                    <div id="delete_card" class="bdg_card">
						<span class="xa_bdgtitle"><img style="margin: 2px 4px 0 0;" src="${ctx}/static/kingkaid/images/xasmalltips.png"/>解绑银行卡</span>
                   		
						<div class="xa_input">
							<span class="fir_name">持卡人：</span>
							<span class="card_name">${custacname}</span>
						</div>
						<div class="xa_input">
							<span class="fir_name">银行卡号：</span>
							<input id="deleteno"  type="text" class="disin" checked="checked" value="" disabled="disabled"/>
						</div>
						<div class="xa_input" style="margin-bottom:7px;">
							<span class="fir_name">银行：</span>
							<input id="deletename" class="xacard_name disin" disabled="disabled" value="" />
						</div>
						<div class="xa_input xa_subinput" style="position:relative;">
							<span class="fir_name" style="position:absolute;top:-10px;left:0">支付密码：</span>
							<span id="_ocx_password_str">
							   <script>
								  pgeditor.generate();
							   </script>
							</span>							
						</div>
						<div class="xa_btngroup">
							<div id="delete_sub" class="xa_sub" onclick="delCard()">确认解绑</div>
							<div id="delete_cancel" class="xa_cancel">取消</div>
						</div>
                   
                    </div>
                    <div style="padding:0px 70px;line-height:35px;">
                        <div><strong>温馨提示：</strong></div>
                        <div style="color:#808080;line-height:35px;"><b>1. </b>解绑银行卡后暂时无法完成充值、提现操作。</div>
                        <div style="color:#808080;line-height:35px;"><b>2. </b>账户内有余额无法解绑银行卡，请您提现并确认账户余额为0后，再进行解绑。</div>
                        <div>&nbsp;</div>
                    </div>
					<!--解除end-->
                </div>              
                <div id="bindCard" style="display:none;">
	                 <div class="usercenter-title">
	                        <div class="usertitle-text">我的资产</div>
	                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
	                 </div>
                   <!--添加银行卡-->
                    <div id="bind_card" class="bdg_card">
						<span class="xa_bdgtitle"><img style="margin: 2px 4px 0 0;" src="${ctx}/static/kingkaid/images/xasmalltips.png"/>绑定银行卡</span>
                   		
						<div class="xa_input">
							<span class="fir_name">持卡人：</span>
							<span class="card_name">${custacname}</span>
						</div>
						<div class="xa_input">
							<span class="fir_name">银行卡号：</span>
							<input id="bankcode_up"  type="text" checked="checked" value="" style="color:#999;" onchange="ctp_cardcertify_up();"/>
							<span class="xa_numprom">请输入本人名下的银行卡</span>
						</div>
						<div class="xa_input" style="margin-bottom:7px;">
							<span class="fir_name">银行：</span>
							<input id="bankname_up" class="xacard_name disin" disabled="disabled" value="" />
						</div>
						<div class="xa_input" style="position:relative;">
							<span class="fir_name" style="position:absolute;top:-10px;left:0">支付密码：</span>
							<span id="_ocx_password_bind_str">
							  <script>
								pgeditorBind.generate();
							  </script>
							</span>	
						</div>
						<div class="xa_btngroup">
							<div id="bind_sub" class="xa_sub" onclick="ctp_openPay_by_xabank()">绑定银行卡</div>
							<div id="bind_cancel" class="xa_cancel">取消</div>
						</div>
                    </div>
					<!--添加end-->
                </div>  
                    <%@ include file="usercenterleftmenu.jsp"%>
                </div>
            </div>
            <div style="clear:both"></div>         
        </div> 
        <div style="clear:both"></div>       
    </div>
    <%@ include file="../../common/footer.jsp"%>
</body>
</html>

