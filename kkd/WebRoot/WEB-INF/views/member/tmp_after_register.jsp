<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/static/plugin/js/timer.jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/member/tmp_after_register.js"></script>
</head>
<body>
	<div style="height: 84px; width: 1000px; margin: 0 auto;">
		<a style="float: left; margin-top: 20px;" href="${ctx}/"><img src="${ctx}/static/kingkaid/images/logo.jpg" /></a>
		<div class="top-left" style="margin: 20px 0 0 30px; padding-left: 30px; line-height: 43px; border-left: 1px solid #dfdfdf;">
			<div style="float: left; color: #747474;">客服电话：400-1888-136</div>
			<div style="margin-left: 40px; float: left; color: #747474; border-right: 1px solid #dfdfdf; padding-right: 30px;">服务时间 : 09:00 - 17:00 (工作日)</div>
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
				<a href="${ctx}/website/mobdown.html"><img src="${ctx}/static/kingkaid/images/app-download.png" /></a>
			</div>
		</div>
	</div>
	<div>
		<div class="content" style="overflow: hidden;">
			<div class="result_content">
				<div style="text-align: center; margin-top: 70px;">
					<img src="${ctx}/static/kingkaid/images/big_success_icon.jpg" />
				</div>

				<div style="line-height: 35px; color: #666; font-size: 18px; margin: 55px 200px 0 200px;">
					<div>恭喜您注册成功!</div>
					<div>
						您的登陆账号为${user_obj.memberName}，开通银行存管电子账户，开始理财！
					</div>
					<div><a class="quick_open_btn" href="${ctx}/auth/cust/openpay_page.html">立即开通</a></div>
				</div>
				<div style="margin:10px 200px; line-height: 25px; color: #666; font-size: 15px;" id="jump"></div>
			</div>
			<div style="clear: both"></div>
		</div>
		
	</div>


	<div style="background-color: #4a545c; color: #fff; height: 87px;">
		<div style="height: 42px; width: 1000px; margin: 0 auto; line-height: 25px; font-family: 'SimSun'; padding-top: 20px;">
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