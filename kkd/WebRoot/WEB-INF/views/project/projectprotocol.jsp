<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
</head>

<body>
	<div style="height:84px;width:1000px;margin:0 auto;">
		<a style="float:left;margin-top:20px;" href="${ctx}/"><img
			src="${ctx}/static/kingkaid/images/logo.jpg" /></a>
		<div class="top-left"
			style="margin:20px 0 0 30px;padding-left:30px;line-height:43px;border-left:1px solid #dfdfdf;">
			<div style="float:left;color:#747474;">客服电话：400-1888-136</div>
			<div
				style="margin-left:40px;float:left;color:#747474;border-right:1px solid #dfdfdf;padding-right:30px;">服务时间
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
			<div style="float:left;margin:11px 15px 0 5px;">
				<a><img src="${ctx}/static/kingkaid/images/app-download.png" /></a>
			</div>
		</div>
	</div>

	<div class="content" style="overflow:hidden;">
		<div class="protocal_content">
			<div class="pro_name1">风险提示书</div>
			<div style="height:240px; overflow-y:scroll;">
				${riskinfo}
			</div>
			<div class="pro_name2">借款合同</div>
			<div style="height:240px; overflow-y:scroll;">
				${loancontinfo }
			</div>
			<div class="pro_name2">催收授权委托书</div>
			<div style="height:240px; overflow-y:scroll;">
				${proxycontinfo}
			</div>
			<div class="pro_text2" style="height:150px; margin-top:10px;">
				<p style=" margin-top:20px; margin-right:25px;">

					<input name="" type="checkbox" value="" />金开贷”会员服务协议双方为陕西金开贷金融服务有限公司与http://www.kingkaid.com/网站（以下简称“”会员服务协议双方为陕西金开贷金融服务有限公司与http://www.kingkaid.com/网站（以下简
				</p>
				<div id="more_button" class="blue_btn" style="margin-top:15px;">下一步</div>
				<div></div>
			</div>
		</div>
		<div style="clear:both"></div>
		<div style="background-color:#4a545c;color:#fff;height:100px;">
			<div
				style="height:42px;width:1000px;margin:0 auto;line-height:25px;font-family:'SimSun';padding-top:20px;">
				<div style="float:left">
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

		<input type="hidden" name="loanidValue" id="loanidValue"
			value="D9000100011" />
</body>
</html>
