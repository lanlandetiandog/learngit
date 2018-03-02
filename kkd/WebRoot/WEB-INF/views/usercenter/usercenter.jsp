<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
   <%@ include file="/static/common/meta.jsp"%>
   <!-- 
   <script type="text/javascript" src="${ctx}/static/plugin/js/guide.js"></script>
    -->
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
        
        $("#xa_btnl").click(function(){
        	$(".xa_alert").css("display","none");
    		$("#big_bg").css("display","none");	
    		if($("#cashBal").val()>0.00){
    			$(".shade").css("display","block");
    			$(".xa_remind").css("display","block");
    		}
        });
        
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
          .jkd_bottom ul li{
              float:left;
              width:280px;
              height:44px;
              margin-right: -1px;
              border-right:1px solid #d3d5d6;
              text-align: center;
          }
    #big_bg {
	  position: absolute;
	  top:0px; 
	  left: 0px; 
	  width: 100%; 
	  height: 1224px; 
	  z-index: 100000;
	  background-color: #000;
	  opacity: 0.6;
	  filter: progid:DXImageTransform.Microsoft.Alpha(opacity=60)
	  }
	.xa_alert{
		width:655px;
		height: 406px;
/* 		border: 1px solid #dddddd; */
		margin: 0 auto;
		border-radius: 4px;
		overflow: hidden;
		position: absolute;
	    top: 140px;
	    right: 0;
	    left: 0;
	    background: #fff;
	    z-index: 100001;
	}
	.xa_hd{
		height: 42px;
		text-align: center;
		line-height: 42px;
		font-size: 18px;
		font-weight: 500;
		background:#4e76b1;
		color:#fff;
	}
	.xa_mid{
		height:170px;
		margin: 53px 30px;
		text-align: center;
		font-weight: 500;
		position: relative;
		font-size:16px;
	}
	.xa_apd{
	    letter-spacing:1px;
		line-height: 84px;
		font-size:16px;
		font-weight: 500;
	}
	.xa_btngr{
		hieght:45px;
		margin-top: 26px;
		padding: 0 34px;
	}
	.xa_btnl,.xa_btnr{
		height: 45px;
		line-height: 45px;
		width:290px;
		font-size: 16px;
		border-radius: 4px;
		cursor: pointer;
		color: #fff;
		text-align: center;
		background: #667489;
	}
	.xa_btnl:hover,.xa_btnr:hover{
		opacity:0.9;
	}
	.xa_btnl{
		float: left;
	}
	.xa_btnr{
		float:right;
		background: #5c88c9;
	}
	.warm{
	    position:absolute;
	    top:25px;
	    right:0px;
	   left:143px;
	}
	.userremind {
       width: 980px;
    }
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
                <div class="usercenter_content">
                    <div class="jkd_top" style="background: none">
                        <div class="jkd_userinfo">
                            <img style="float:left;" src="${ctx}/static/kingkaid/images/head_user.png" />
                            <div class="userinfo_list">
                                <div class="info_img_list">
                                    <span class="username">${membername}</span>
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
                                    		<a title="账户安全"><img onclick="javascript:window.location.href='${ctx}/auth/usercenter/safetycenter.html';" src="${ctx}/static/kingkaid/images/safe.png"/></a>
                                    	</c:when>
                                    	<c:otherwise>
                                    		<a title="账户不够安全，请完善资料"><img onclick="javascript:window.location.href='${ctx}/auth/usercenter/safetycenter.html';" src="${ctx}/static/kingkaid/images/safe_none.png"/></a>
                                    	</c:otherwise>
                                    </c:choose>
                                    <a title="站内信"><img onclick="javascript:window.location.href='${ctx}/auth/usercenter/mymessage.html';" src="${ctx}/static/kingkaid/images/mail_user.png"/></a>
                                    <c:if test="${outData.unreadmsg ne '0'}">
                                        <img style="margin:1px 0 0 -9px" src="${ctx}/static/kingkaid/images/message_warm.png"/>
                                    </c:if>
                                </div>
                                <div>
                                    <c:choose>
                                        <c:when test="${outData.vipstate eq '1'}">
                                            <img src="${ctx}/static/kingkaid/images/vip2.png"/>
                                            <a class="top-info-link" onclick="javascript:window.location.href='${ctx}/auth/usercenter/join_vip.html';">vip续期</a>
                                        </c:when>
                                        <c:otherwise>
                                		    <img src="${ctx}/static/kingkaid/images/vip_none.png"/>
                                    	    <a class="top-info-link" onclick="javascript:window.location.href='${ctx}/auth/usercenter/join_vip.html';">成为vip</a>
                                        </c:otherwise>
                                    </c:choose>
                                    <span>
                                        <span>金开币：</span>
                                        <a class="top-info-link" onclick="javascript:window.location.href='${ctx}/auth/usercenter/mycoin.html';">${outData.coinamount}</a>
                                    </span>
                                    <span>
                                        <span>优惠券：</span>
                                        <a class="top-info-link" onclick="javascript:window.location.href='${ctx}/auth/activity/my_lottery_page.html';">${outData.ratiocount}</a>
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
                            <c:choose>
							    <c:when test="${user_obj.memberState eq '4' || user_obj.memberState eq '5'}">
							        <a id="openblack" class="top_charge" style="margin-bottom:21px;padding: 0 11px;margin-top: 23px;" href="${ctx}/auth/cust/openpay_page.html" target="_blank">立即开通</a>
							    </c:when>
							   <%--  <c:when test="${user_obj.memberState eq '6' || user_obj.memberState eq '7'}">
							        <a id="signblack" class="top_charge" style="margin-bottom:21px;padding: 0 11px;margin-top: 23px;" href="${ctx}/auth/cust/openpay_page.html" target="_blank">立即激活</a>
							    </c:when> --%>
							    <c:otherwise>
							        <div class="top_charge" style="margin-bottom:21px;" onclick="javascript:window.location.href='${ctx}/auth/xabank/acct/deposit_page.html'">充值</div>
                                    <div class="draw_cash" onclick="javascript:window.location.href='${ctx}/auth/xabank/acct/withdraw_page.html'">提现</div>
							    </c:otherwise>
							</c:choose>
                        </div>
                         
                    </div>
                    
                    <c:if test="${cashBal.cashbal gt 0.00}">
                       <div class="huifu font16 font500 margt66">
							<div class="fl palr"><img src="${ctx}/static/kingkaid/images/image/huifu.png"/></div>
							<span>余额不可用，<a class="colblu" onclick="javascript:window.open('${ctx}/auth/usercenter/cashall.html','_self')">需提现</a></span>
						</div>
                    </c:if>
                    <div class="jkd_bottom">
							<div class="bottoml">
								<p class="xa_font16">
									<span class="xa_fontname">账户余额</span>
									<span class="clred">&yen; <span class="font24"><fmt:formatNumber value="${acctInfo.bal}" pattern="#,##0.00"/></span> </span> 元
								</p>
								<p class="xa_font16">
									<span class="xa_fontname">待收收益</span>
									<span class="clred">&yen; <fmt:formatNumber value="${acctInfo.dincome}" pattern="#,##0.00"/></span> 元
								</p>
							</div>
							<div class="bottomid"></div>
							<div class="bottomr">
								<p class="xa_font16 boderl">
									<span class="xa_fontname">冻结金额</span>
									<span class="clred">&yen; <span><fmt:formatNumber value="${acctInfo.fbal}" pattern="#,##0.00"/></span></span> 元
								</p>
								<p class="xa_font16 boderl">
									<span class="xa_fontname">待收本金</span>
									<span class="clred">&yen; <fmt:formatNumber value="${acctInfo.dcapi}" pattern="#,##0.00"/></span> 元
								</p>
							</div>
						</div>
                        <div class="jkdbotimg">
                        	<div class="xa_botxt">
								<p class="xa_font16">
									<span class="xa_fontname">累计收益</span>
									<span>&yen; <span class="font24"><fmt:formatNumber value="${acctInfo.lincome}" pattern="#,##0.00"/></span></span> 元
								</p>
                        	</div>
                        </div>

                    <%-- <div class="jkd_bottom" style="margin-top: 35px; margin-bottom: 30px">
                        <ul>
                            <li>
                                <div>账户余额</div>
                                <div class="jkd_money">&yen;<fmt:formatNumber value="${acctInfo.bal}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li style="border: none">
                                <div>冻结余额</div>
                                <div class="jkd_money">&yen;<fmt:formatNumber value="${acctInfo.fbal}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li>
                                <div>待收收益</div>
                                <div class="jkd_money">&yen;<fmt:formatNumber value="${acctInfo.dincome}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li style="border: none">
                                <div>待收本金</div>
                                <div class="jkd_money">&yen;<fmt:formatNumber value="${acctInfo.dcapi}" pattern="#,##0.00"/>元</div>
                            </li>

                        </ul>
                        <div class="jkd_total_money">
                            <div class="total_money_top">累计收益</div>
                            <div class="total_money_num">&yen;<fmt:formatNumber value="${acctInfo.lincome}" pattern="#,##0.00"/>元</div>
                        </div>
                    </div> --%>

                    <div class="jdk-middle">
                        <ul>
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
                                <div style="cursor: pointer;" onclick="javascript:window.location.href='${ctx}/auth/usercenter/my_invest_page.html';">
                                    <div style="cursor: pointer;">已投项目</div>
                                    <div class="middle_info_num">（<span>${outData.joinproj}个</span>）</div>
                                </div>
                            </li>
                            <li>
                                <div style="cursor: pointer;" onclick="javascript:window.location.href='${ctx}/auth/usercenter/my_borrow_page.html';">
                                    <div style="cursor: pointer;">借款项目</div>
                                    <div class="middle_info_num">（<span>${outData.loanapp}个</span>）</div>
                                </div>
                            </li>
                            <li>
                                <div style="cursor: pointer;" onclick="javascript:window.location.href='${ctx}/auth/usercenter/assign/mycredit_assign.html';">
                                    <div style="cursor: pointer;">债权转让</div>
                                    <div class="middle_info_num">（<span>${outData.projtrans}个</span>）</div>
                                </div>
                            </li>     
                        </ul>
                    </div>

	                <%@ include file="usercenterleftmenu.jsp"%>
                </div>
            </div>
            <div style="clear:both"></div>         
        </div> 
        <div style="clear:both"></div>       
    </div> 
    
   <%--  <c:if test="${user_obj.memberState eq '6' || user_obj.memberState eq '7'}">
       <div class="xa_alert">
			<div class="xa_hd"><span>电子账户激活</span></div>
			<div class="xa_mid">
			    <img class="warm" src="${ctx}/static/kingkaid/images/xatips.png" /><p style="font-size:19px;letter-spacing:2px;padding-top: 32px;">您的电子账户存在异常!</p>
				<p class="xa_apd" >为保证您的正常使用及资金安全请您立即进行转账激活电子账户</p>
			</div>
			<div class="xa_btngr">
				<div id="xa_btnl" class="xa_btnl">稍后再说</div>
				<div id="xa_btnr" class="xa_btnr"><a style="color:#fff;" href="${ctx}/auth/cust/openpay_page.html">立即激活</a></div>
			</div>
       </div>
       <div id="big_bg" ></div>
    </c:if> --%>
	    <div class="shade">
			<div  class="xa_remind">
				<div class="xa_guideimg"><img src="${ctx}/static/kingkaid/images/image/huifuback.png"/></div>
				<div class="xa_btnclosed"><img src="${ctx}/static/kingkaid/images/image/ikonwbtn.png"/></div>
			</div>
		</div> 
	<c:if test="${user_obj.memberState eq '4' || user_obj.memberState eq '5' || user_obj.memberState eq '8'}">
	   <c:if test="${cashBal.cashbal gt 0.00}">
	      <div class="shade" style="display:block;">
			<div  class="xa_remind" style="display:block;">
				<div class="xa_guideimg"><img src="${ctx}/static/kingkaid/images/image/huifuback.png"/></div>
				<div class="xa_btnclosed"><img src="${ctx}/static/kingkaid/images/image/ikonwbtn.png"/></div>
			</div>
		  </div> 
	   </c:if> 
    </c:if>
    <input id="cashBal" value="${cashBal.cashbal}" type="hidden"/>  
    <%@ include file="../common/footer.jsp"%>
</body>
</html>
