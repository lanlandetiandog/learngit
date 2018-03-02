<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/main.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/login_new.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/qrcode.js"></script>
<script type="text/javascript" src="${ctx}/static/plugin/js/timer.jquery.js"></script>
<style>
#login_code_img:hover {
	cursor: pointer;
}
#reg_code_img:hover {
	cursor: pointer;
}
</style>
<title>金开贷</title>
</head>
<body>
	<div style="height:84px;width:1000px;margin:0 auto;">
		<a style="float:left;margin-top:20px;" href="${ctx}/"> 
			<img src="${ctx}/static/kingkaid/images/logo.jpg" />
		</a>
		<div style="float:right;font-weight:bold;font-size:20px;margin-top:40px;"></div>
	</div>

	<div class="login-content">
		<div class="login_banner">
			<div class="login-box">
				<div class="right-top"></div>
				<div class="login_area">
					<div class="nlogin_title">
						<div class="nlogin" onclick="nloginfun()" id="nlogin" style="color: #999; background: url('${ctx}/static/kingkaid/images/log_bot.png') no-repeat bottom center;">登录</div>
						<div class="login-line"></div>
						<div class="nregis" onclick="nregisfun()" id="nregis">注册</div>
					</div>
					<div style="clear: both;"></div>
					<form id="form_login" action="${ctx}/login" method="post">
						<shiro:authenticated>
							<div class="login_area" style="width:270px;">
								<c:set var="thirdPartyName" value="${user_obj.memberName}" />
								<div class="login_title"
									style="font-weight:normal; margin-top:-30px;">${kkd:showPrivacy(thirdPartyName, 6)}，您的账号已登录！</div>
								<div class="login-bottom">
									<a href="${ctx}/logout" style="color:#3874cc">退出此账号</a> 
									<a href="${ctx}/">继续浏览</a>
								</div>
							</div>
						</shiro:authenticated>
						<shiro:guest>
							<div class="login-btn" id="login-btn">
								<!--登录-->
								<div id="errorMsg" style="color: #E7222B; font-size: 12px;">${errorMsg}</div>
								<div class="input-unit">
									<div style="width:290px;">
										<label class="name-label"> </label> <input id="username"
											name="username" type="text" class="login-input"
											placeholder="用户名/手机号码" tabindex='1' />
									</div>
									<div class="error-msg"></div>
								</div>
								<div class="input-unit">
									<div style="width:290px;">
										<label class="psw-label"> </label> <input id="password"
											name="password" type="password" class="login-input"
											placeholder="密码" tabindex='2' />
									</div>
									<div class="error-msg"></div>
								</div>
								<div class="input-unit nlogin-unit-input">
									<div>
										<input id="val_code" name="val_code" type="text"
											class="login-input" style="width:260px;"
											placeholder="请输入红色验证码" tabindex='3' />
										<div class="code_img">
											<img id="login_code_img" style="float:left; margin-top:2px"
												title="点击更换验证码" alt="验证码"
												src="${ctx}/getValidateCode?s=<%=new java.util.Date().getTime()%>" />
										</div>
										<div class="error-msg"></div>
									</div>
								</div>
								<input type="submit" id="login_btn" class="login_btn" value="登录"
									tabindex='4'></input>
								<div class="login-bottom">
									<a href="${ctx}/member/foundpwd.html">忘记密码</a>
								</div>
							</div>
						</shiro:guest>
					</form>

					<!--注册-->
					<form id="form_register" action="${ctx}/member/register"
						method="post">
						<div class="register-btn" id="register-btn">
							<div id="regErrorMsg" style="color: #E7222B; font-size: 12px;">${regErrorMsg}</div>
							<div class="input-unit">
								<div style="width: 285px;">
									<label class="psw-label-phone"> </label> 
									<input type="text"
										id="filter_mobilenumber" name="filter_mobilenumber"
										tabindex="1" class="login-input" placeholder="手机号码" />
								</div>
								<div class="error-msg"></div>
							</div>
							<div class="input-unit">
								<div style="width: 285px;">
									<label class="psw-label"> </label> <input type="password"
										id="filter_loginpassword" name="filter_loginpassword"
										tabindex="2" class="login-input" placeholder="请设置密码" />
								</div>
								<div class="error-msg"></div>
							</div>

							<div id="regis_numimg" class="input-unit nlogin-unit-input">
								<input type="text" id="mvcode" name="mvcode" class="login-input"
									style="width:260px;" tabindex="3" placeholder="请输入红色验证码" />
								<div class="code_img">
									<img id="reg_code_img" style="float:left; margin-top:2px"
										title="点击更换验证码" alt="验证码"
										src="${ctx}/member/getVCodeValidateCode?s=<%=new java.util.Date().getTime()%>" />
								</div>
								<div class="error-msg"></div>
							</div>

							<div class="input-unit">
								<div style="width: 285px;">
									<input id="filter_verifycode"
										name="filter_verifycode" class="login-input" tabindex="4"
										style="width:180px;" placeholder="短信验证码" />
									<button type="button" id="fetch-vcode" class="catch_code">获取验证码</button>
								</div>
								<div class="error-msg"></div>
							</div>

							<div class="nregis_input">
								<input id="filter_invitecode"
									name="filter_invitecode" class="nregis_in"
									placeholder="聚财码（选填）" tabindex="5" />
								<div class="error-msg"></div>
							</div>
							<div style="clear: both;"></div>

							<div class="logtxt">
								<input type="checkbox" id="if_read_chkbox" name="if_read_chkbox"
									tabindex="6" /> <span>我同意</span> <a class="blue_link"
									href="member/protocol.html" target="_blank">金开贷会员服务协议</a>
								<div class="error-msg"></div>
							</div>
							<input type="submit" id="btn_register" class="login_btn"
								style='width:100%; margin-bottom:40px; font-family: "微软雅黑","宋体",STHeiti,Verdana,Arial,Helvetica,sans-serif;'
								tabindex="7" value="注 册" />
						</div>
					</form>
				</div>
			</div>
			<div class="login-two-code"
				style="display:none; color: #ea6660; font-size: 20px;">
				<div class="right-top-view"></div>
				<div class="ncode_area">
					<div class="login_title">扫描下载手机APP</div>
					<img style="float:center;margin-top:-10px" width="200" height="200"
						src="${ctx}/static/kingkaid/images/qrcode/code.png" />
				</div>
			</div>
		</div>
		<div style="clear:both"></div>
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
	</div>

</body>
</html>