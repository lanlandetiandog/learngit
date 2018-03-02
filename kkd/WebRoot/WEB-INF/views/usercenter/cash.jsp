<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<script type="text/javascript">
var cash_form;
$(document).ready(function(){
	
	$("#usermenu_zc").addClass("user_menulist_href_hover");
    //对页面内下拉选框执行chosen插件。
    $(".cash_chosen_select").chosen({width: "270px",disable_search_threshold: 10});
	
    cashServFee($("input[name='cashchl']:checked").val(),'',$("#amount").val());
    
}) 

function doCash(){
	showRechargeOverBox('银行卡提现','提现完成前请不要关闭该窗口！');
	$('#form').submit();
}

/**
 * 计算提现服务费
 */
function cashServFee(chanchl,chanid,amt){
	if(chanchl && amt){
		var param = "?chanchl="+chanchl+"&chanid="+chanid+"&amount="+amt;
		$.ajax({
			type:'post',
			url:ctx+"/fee/rule/cash_serv_fee.html"+param,
			async: false,
			success:function(data){
				var json = jQuery.parseJSON(data);
				if(json.status=='s'){
					$("#servfee_show").html(json.value);
				}
			}
		});
	}
}

</script>
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
    <%@ include file="../common/header.jsp" %>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="usercenter_content">
                    <div class="recharge-top">
                        <div class="recharge-title">提现</div>
                        <div class="right_href">
                            <a href="${ctx}/auth/usercenter/myproperty.html">我的资产</a>
                        </div>
                    </div>
                    <form id="form" action="${ctx}/auth/pay/withdrawAll" method="post" target="_blank">     
                    <input type="hidden" id="amount" name="amount" value="${cashBal.cashbal}"/>                               
	                    <ul class="recharge-info" id="menu">
	                        <li>
	                            <span class="cash_info_left">账户余额：</span>
	                            <span class="cash_info_right blue-text">${cashBal.cashbal}</span>元	<span style="padding-left: 9px;color:red;font-size:10px;">温馨提示：账户余额=原汇付天下账户余额+生利宝余额</span>	                                                       
	                            <div style="clear:both;"></div>
	                        </li>  
	                        <li>
	                            <span class="cash_info_left">提现金额：</span>
	                            <span class="cash_info_right blue-text" id="realcashamt_show">${cashBal.cashbal}</span>元
	                            <div style="clear:both;"></div>
	                        </li>      
	                        <li>
	                            <p class="cash_info_left">提现周期：</p>
	                            <span class="cash_info_right">
	                                	
	                                <span class="blue-text"><input name="cashchl" value="GENERAL" checked="checked" type="radio"/>&nbsp普通提现（T+1）</span>	                                                                
	                            </span>
	                            <div style="clear:both;"></div>
	                        </li>
	                        <li>
	                            <span class="cash_info_left">选择银行卡：</span>
	                            <div class="cash_info_right">
	                                <select class="cash_chosen_select" style="border:1px solid #bac0c4;color:#76818a;" id="cardId" name="cardId">
	                                	<option value="">请选择</option>
	                                    <c:forEach items="${bankcards}" var="bankcard">
											<option value="${bankcard.bankacno}">
												<kkd:bankCardFormat cardId="${bankcard.bankacno}"></kkd:bankCardFormat>
											</option>
										</c:forEach>
	                                </select>
	                                <span style="margin-left:20px;">
	                                	<c:if test="${fn:length(bankcards) == 0}">
	                                		未找到可用银行卡，请您进行<a class="red-href" onclick="showRechargeOverBox('添加银行卡','操作完成前请不要关闭该窗口！');" href="${ctx}/auth/usercenter/bindcard">绑卡</a>操作
	                                	</c:if>
	                                </span>
	                            </div>
	                            <div style="clear:both;"></div>
	                        </li>
	                        <div class="remind">提示：已为您减免提现手续费<span class="blue-text" id="servfee_show">0</span>元</div>
	                        <li>
	                        	<div style="cursor: pointer;" class="blue-btn" onclick="doCash();">下一步</div>
	                        </li>
	                    </ul>
                    </form>
                    <div style="padding:30px 70px;line-height:25px;">
                        <div>温馨提示：</div>
                        <div style="color:#808080;line-height:25px;">
                            <div>提现成功后，您的实际到账金额将按照您所选定的提现周期打入您选择的银行卡，</div>
                            <div>请注意查收（汇付天下服务电话：4008202819）。</div>
                        </div>    
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
