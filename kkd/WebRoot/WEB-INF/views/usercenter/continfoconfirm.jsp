<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>

<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/continfoconfirm.js"></script>
<title>金开贷合同浏览</title>
</head>

<body>
	<div style="height: 84px; width: 1000px; margin: 0 auto;">
		<a style="float: left; margin-top: 20px;" href="${ctx}/index.html"><img
			src="${ctx}/static/kingkaid/images/logo.jpg" /></a>
		<div class="top-left"
			style="margin: 20px 0 0 30px; padding-left: 30px; line-height: 43px; border-left: 1px solid #dfdfdf;">
			<div style="float: left; color: #747474;">客服电话：400-1888-136</div>
			<div
				style="margin-left: 40px; float: left; color: #747474; border-right: 1px solid #dfdfdf; padding-right: 30px;">服务时间
				: 09:00 - 17:00 (工作日)</div>
			<div class="weixin">
				<img src="${ctx}/static/kingkaid/images/wx.png" />
				<div class="winxin_hover">
					<img src="${ctx}/static/kingkaid/images/kingweixin.png" />
				</div>
			</div>
			<div class="webo">
				<img src="${ctx}/static/kingkaid/images/wb.png" />
				<div class="webo_hover">
					<div>点击关注新浪微博</div>
					<div>
						<a>@金开贷</a>
					</div>
				</div>
			</div>
			<div style="float: left; margin: 11px 15px 0 5px;">
				<a><img src="${ctx}/static/kingkaid/images/app-download.png" /></a>
			</div>
		</div>
	</div>

	<div class="content" style="overflow: hidden;">
		<div class="protocal_content">
			
			<input type="hidden" id="loanidValue" value="${loanid}"/>
			<input type="hidden" id="cont_type" value="${cont_type}"/>
			<input type="hidden" id="apprstate" value="${apprstate}"/>
			<input type="hidden" id="prodid" value="${prodid}"/>
			<div style="padding-top:45px;text-align:center;color:#ea6660;font-size:20px;font-weight:bold;background:url(${ctx}/static/kingkaid/images/pro_top_bg.jpg);width:1026px;height:55px;">合同签订</div>
			<div class="pro_text" id="pro_textinfo">
				<div>共有三份协议需要您阅读并同意</div>
				<div style="padding-top:45px;text-align:center;color:#ea6660;font-size:20px;font-weight:bold;">风险提示书</div>
				<div class="agreement_box">
					<div id="riskinfo"></div>
				</div> 
					<c:choose>
		            	<c:when test="${prodid == '100301'}">
							<div style="padding-top:45px;text-align:center;color:#ea6660;font-size:20px;font-weight:bold;">保理权益转让及回购协议</div>
						</c:when>
					<c:otherwise>
		                 <div style="padding-top:45px;text-align:center;color:#ea6660;font-size:20px;font-weight:bold;">借款合同</div>
					</c:otherwise>
					</c:choose> 
				<div class="agreement_box">
					<div id="loancontinfo"></div>
				</div>
				<div style="padding-top:45px;text-align:center;color:#ea6660;font-size:20px;font-weight:bold;">催收委托书</div>
				<div class="agreement_box">
					<div id="proxyinfo"></div>
				</div>
			</div>
			<div class="small_window_bottom">
				<div class="blue_btn" onclick="ContConfirmAjax('${dealtype}','${loanid}','1')">同  意</div>
				<div class="gray_btn btn_right" onclick="ContConfirmAjax('${dealtype}','${loanid}','0');">拒  绝</div>
				<div class="gray_btn btn_right" onclick="history.back();">返  回</div>
			</div>
		</div>		
	</div>


	<div style="background-color: #4a545c; color: #fff; height: 87px;">
		<div
			style="height: 42px; width: 1000px; margin: 0 auto; line-height: 25px; font-family: 'SimSun'; padding-top: 20px;">
			<div style="float: left">
				<div>陕西金开贷金融服务有限公司 陕ICP备 13007681号</div>
				<div>@ 2014 金开贷 All rights reserved</div>
			</div>
			<div style="float: right">
				<a href="https://ss.knet.cn/verifyseal.dll?sn=e14050861010048655416p000000&amp;tp=icon3&amp;ct=df&amp;a=1&amp;pa=0.9496854659079472" target="blank_"> 
					<img style="margin-right: 30px;" src="${ctx}/static/kingkaid/images/gs1.jpg" />
				</a> 
				<a href="http://117.22.252.216:7001/wljgweb/bscx.do?method=hddoc&amp;id=61000000005374" target="blank_"> 
					<img src="${ctx}/static/kingkaid/images/gs2.jpg" />
				</a>
				<a href="https://trustsealinfo.verisign.com/splash?form_file=fdf/splash.fdf&amp;dn=www.kingkaid.com&amp;lang=zh_cn" target="blank_" style="margin-left: 18px;">
					<img src="${ctx}/static/kingkaid/images/vseal.jpg" />
				</a>
			</div>
		</div>
	</div>

</body>
</html>
