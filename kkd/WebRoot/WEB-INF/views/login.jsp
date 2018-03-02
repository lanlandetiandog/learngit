<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/login.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/qrcode.js"></script>

<style>
#vcode_img:hover {
	cursor: pointer;
}
</style>
</head>

<body>
	<div style="height:84px;width:1000px;margin:0 auto;padding-top:">
		<a style="float:left;margin-top:20px;" href="${ctx}/"><img
			src="${ctx}/static/kingkaid/images/logo.jpg" /></a>
		<div
			style="float:right;font-weight:bold;font-size:20px;margin-top:40px;">账户登录</div>
	</div>

	<%-- <div class="login-content">
		<div class="login_banner">
			<div class="login-box">
				<div class="right-top"></div>
				<form id="form_login" class="login_area" action="${ctx}/login"
					method="post">
					<div style="height:53px">
						<div class="login_title" style="margin-bottom:8px">登录</div>
						<div id="errorMsg" style="color: #E7222B; font-size: 12px;">${errorMsg}</div>
					</div>

					<shiro:authenticated>
						<div class="login_area" style="width:260px;">
							<c:set var="thirdPartyName" value="${user_obj.memberName}" />
							<div class="login_title"
								style="font-weight:normal; margin-top:-30px;">${kkd:showPrivacy(thirdPartyName, 6)}，您的账号已登录！</div>
							<div class="login-bottom">
								<a href="${ctx}/logout" style="color:#3874cc">退出此账号</a> <a
									href="${ctx}/">继续浏览</a>
							</div>
						</div>
					</shiro:authenticated>

					<shiro:guest>
						<div class="input-unit">
							<div style="width:290px;">
								<label class="name-label"> </label> <input id="username"
									name="username" type="text" class="login-input"
									placeholder="用户名/ 邮箱/ 手机" tabindex='1' />
							</div>
						</div>
						<div class="input-unit">
							<div style="width:290px;">
								<label class="psw-label"> </label> <input id="password"
									name="password" type="password" class="login-input"
									placeholder="密码" tabindex='2' />
							</div>
						</div>
						<div class="input-unit">
							<div>
								<input id="val_code" name="val_code" type="text"
									class="login-input" style="width:260px;" placeholder="验证码"
									tabindex='3' />
								<div class="code_img">
									<img id="vcode_img" style="float:right;margin-top:5px"
										title="点击更换验证码" alt="验证码"
										src="${ctx}/getValidateCode?s=<%=new java.util.Date().getTime()%>" />
								</div>
								<div class="error-msg"></div>
							</div>
						</div>
						<input type="submit" id="login_btn" class="login_btn" value="登录"></input>
						<div class="login-bottom">
							<a href="${ctx}/member/register.html">注册账号</a> <a
								href="${ctx}/member/foundpwd.html">忘记密码</a>
						</div>
					</shiro:guest>
				</form>
			</div>

			<div class="login-two-code" style="display:none;">
				<div class="right-top-view"></div>
				<div class="code_area">
					<div class="login_title">扫描下载手机APP</div>
					<div class="two-code-img">
						<img style="float:center;margin-top:-10px" width="200"
							height="200" src="${ctx}/static/kingkaid/images/qrcode/code.png" />
					</div>
				</div>
			</div>

		</div>
		<div style="clear:both"></div>
	</div>

	<div style="background-color:#4a545c;color:#fff;height:100px;">
		<div
			style="height:42px;width:1000px;margin:0 auto;line-height:25px;font-family:'SimSun';padding-top:20px;">
			<div style="float:left">
				<div>陕西金开贷金融服务有限公司 陕ICP备 13007681号</div>
				<div>@ 2014 金开贷 All rights reserved</div>
			</div>
			<div style="float: right">
				<script
					src="https://kxlogo.knet.cn/seallogo.dll?sn=e14050861010048655416p000000&size=3"></script>
				<a
					href="http://117.22.252.216:7001/wljgweb/bscx.do?method=hddoc&amp;id=61000000005374"
					target="blank_" style="margin-left:19px;"> <img
					src="${ctx}/static/kingkaid/images/gs2.jpg" />
				</a>
				<div id='symantecSeal'
					style='text-align:center; width: 92px;display:inline;'
					title='单击即可验证 - 该站点选择 SymantecSSL 实现安全的电子商务和机密通信'>
					<a
						href="https://trustsealinfo.verisign.com/splash?form_file=fdf/splash.fdf&amp;dn=www.jkd.com&amp;lang=zh_cn"
						target="blank_" style="margin-left: 18px;"> <img
						src="${ctx}/static/kingkaid/images/vseal.jpg" />
					</a>
				</div>
			</div>
		</div>
	</div> --%>

</body>
</html>
