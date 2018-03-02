<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/static/plugin/js/PassGuardCtrl.js"></script>
<script type="text/javascript" src="${ctx}/static/plugin/js/timer.jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/xabank/cust/openpay.js"></script>
<title>金开贷</title>
<style type="text/css">
.ocx_style {
		width: 280px;
		height: 45px;
		line-height: 45px;
		padding: 0 0 0 20px;
		outline: 0;
		box-sizing: border-box;
		border: 1px solid #e4e4e4;
		text-indent: 10px;
}
.code_gray{
	width:280px;;
	border-radius: 4px;
	float:right;
	background-color: #99a7ba;
	height:42px;
	line-height: 42px;
	text-align: center;
	margin:0 auto;
	margin:25px 60px 0  0;
	font-size: 16px;
	color: #fff;
	cursor: pointer;
}

.buttonstyle{
    width:280px;;
	border-radius: 4px;
	float:right;
	background-color: #5d89c9;
	height:42px;
	line-height: 42px;
	text-align: center;
	margin:0 auto;
	margin:25px 60px 0  0;
	font-size: 16px;
	color: #fff;
	cursor: pointer;
}

.dotting {
    display: inline-block; width: 10px; min-height: 2px;
    padding-right: 2px;
    border-left: 2px solid currentColor; border-right: 2px solid currentColor;
    background-color: currentColor; background-clip: content-box;
    box-sizing: border-box;
    -webkit-animation: dot 4s infinite step-start both;
    animation: dot 4s infinite step-start both;
    *zoom: expression(this.innerHTML = '...'); /* IE7 */
}
.dotting:before { content: '...'; } /* IE8 */
.dotting::before { content: ''; }
:root .dotting { margin-left: 2px; padding-left: 2px; } /* IE9+ */

@-webkit-keyframes dot {
    25% { border-color: transparent; background-color: transparent; }
    50% { border-right-color: transparent; background-color: transparent; }
    75% { border-right-color: transparent; }
}
@keyframes dot {
    25% { border-color: transparent; background-color: transparent; }
    50% { border-right-color: transparent; background-color: transparent; }
    75% { border-right-color: transparent; }
}
.open_png_btn {
    float: right;
    width: 30px;
    height: 45px;
    cursor: pointer;
}

.xa_protocol {
    width: 280px;
    height: 20px;
    line-height: 20px;
    outline: 0;
    box-sizing: border-box;
    text-indent: 10px;
    font-size: 14px;
}

.xa_agree {
    width: 0px;
    height: 0px;
    line-height: 30px;
    outline: 0;
    box-sizing: border-box;
    text-indent: 10px;
    font-size: 14px;
}
</style>
<script type="text/javascript">
	var pgeditor = new $.pge({
		pgePath: ctx + "/static/xabank/ocx/",
		pgeId: "_ocx_password",
		pgeEdittype: 0,
		pgeEreg1: "[0-9]*",
		pgeEreg2: "[0-9]{6}",
		pgeMaxlength: 6,
		pgeTabindex: 4,
		pgeClass: "ocx_style",
		pgeInstallClass: "ocx_style",
		pgeOnfocus: "clearShow('_ocx_password')"
	});
	
	var pgeditorCopy = new $.pge({
		pgePath: ctx + "/static/xabank/ocx/",
		pgeId: "_ocx_password_copy",
		pgeEdittype: 0,
		pgeEreg1: "[0-9]*",
		pgeEreg2: "[0-9]{6}",
		pgeMaxlength: 6,
		pgeTabindex: 5,
		pgeClass: "ocx_style",
		pgeInstallClass: "ocx_style",
		pgeOnfocus: "clearError('_ocx_password_copy')"
	});
	
    window.onload = function() {
        pgeditor.pgInitialize();
        pgeditorCopy.pgInitialize();
    };
	
</script>
</head>
 
<body>
<div class="header">
    <div style="height:84px;width:1000px;margin:0 auto;">
        <a style="float:left;margin-top:20px;" href="${ctx}/index.html"><img src="${ctx}/static/kingkaid/images/logo.jpg" /></a>
        <div style="float:right;font-weight:bold;font-size:20px;margin-top:40px;">账户认证</div>
    </div>
</div>

<!--账户验证-->
<div class="bankwrap">
<!--验证说明-->
	<div class="open-process">
    	<div class="open-number">
        	<span class="num1">1</span>
        	<span class="num2">2</span>
        	<span class="num3">3</span>
        </div>
        <div class="open-instruction">
        	<div class="open-boximg">
                <span class="blue-cl">开通西安银行电子支付账户</span>
                <div class="openimg1"><img src="${ctx}/static/kingkaid/images/xianbankcard.png"/></div>
            </div>
        	<div class="open-boximg">
                <span class="black-cl">激活电子账户</span>
                <div class="openimg2">
                    <div class="card1"><img src="${ctx}/static/kingkaid/images/otherbank.png"/></div>
                    <div class="pointtext">
                    	<span style="display:block;"><img src="${ctx}/static/kingkaid/images/pointtext.png"/></span>
                    	<span>充值验证</span>
                    </div>
                    <div class="card2"><img src="${ctx}/static/kingkaid/images/xianbankcard.png"/></div>
                </div>
            </div>
        	<div class="open-boximg">
                <span class="black-cl">账户认证成功</span>
            </div>
        </div>
        <div style="clear:both;"></div>
        <p style="width:300px;color:#999;padding:40px 0 0 40px;"><h4>温馨提示</h4>电子账户是通过电子渠道实现开户，无物理介质，主要依托个人网上银行或手机银行进行投资理财、资金转出等业务处理的个人账户，必须本人实名开立。</p>
    </div>
<!--账户验证信息-->
    <div class="accountinfo">
    		<div class="bankxian">
            	<div class="bankxianlogo"></div>
                <h2>开通西安银行电子支付账户</h2>
            </div>
<!--realname1-->
        <form id="openpayform">
            <div class="inputwrap">
                <span class="title-realname">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</span>            
                <input type="text" id="ID_NAME" name="ID_NAME" maxlength="10" class="realname" placeholder="真实姓名" value="${formData.custname}" />
                <div style="clear:both;"></div>                        
            </div> 
    <div style="clear:both;"></div>
<!--idnum2-->
            <div class="inputwrap">
                <span class="title-realname">证件号码</span>            
                <input id="ID_NO" name="ID_NO" type="text" maxlength="18" class="idnum" placeholder="证件号码" value="${formData.paperid}" />
                <div style="clear:both;"></div>
            </div>
<!--cardnum3-->
            <div class="inputwrap">
                <span class="title-realname">银行卡号</span>            
                <input type="text" id="ACCT_NO" name="ACCT_NO" maxlength="20" class="cardnum" placeholder="银行卡号" value="${formData.bankcode}" onkeyup="if(/\D/.test(this.value)){this.value='';}" />
                <div style="clear:both;"></div>               
                <div id="bank_name_div" class="banknamebox" style="color:#666;display:none;">当前银行为: <span class="bankname"></span></div>
            </div> 
<!--password4-->
            <div class="inputwrap">
                <span class="title-realname">交易密码</span>            
                <span id="_ocx_password_str">
	                <script>
						pgeditor.generate();
					</script>
				</span>
				<input id="fake_pwd" type="password" value="******" style="display: none"/>
                <div class="open_png_btn" id="download_ctr" style="display: none" title="点击下载控件"><img style="padding: 13px 0 0 10px" src="${ctx}/static/kingkaid/images/xaopen_loading.png"/></div>
                <div style="clear:both;"></div>                         
            </div> 
<!-- password5 -->
            <div class="inputwrap">
                <span class="title-realname">确认密码</span>      
                <span id="_ocx_password_copy_str">
	                <script>
	                	pgeditorCopy.generate();
					</script>
				</span>
				<input id="fake_pwd_copy" type="password" value="******" style="display: none"/>
                <div class="open_png_btn" id="ctr_help" style="display: none" title="如果控件不能使用，请点击查看浏览器设置帮助"><img style="padding: 13px 0 0 10px" src="${ctx}/static/kingkaid/images/xaopen_help.png"/></div>      
                <div style="clear:both;"></div>                         
            </div>
<!--cardnum6-->
            <div class="inputwrap">
                <span class="title-realname">手机号码</span>            
                <input type="text" id="MOBILE" maxlength="11" name="MOBILE" class="phonenum" placeholder="手机号码" value="${formData.mobilenumber}" readonly="readonly" style="background: #e1e1e1"/>
                <a class="open_png_btn" title="去安全中心修改您在平台绑定的手机号码" href="${ctx}/auth/usercenter/safetycenter.html?updateMobile"><img style="padding: 13px 0 0 10px" src="${ctx}/static/kingkaid/images/xaopen_update.png"/></a>
                <div style="clear:both;"></div>                
            </div> 
<!--cardnum7-->
            <div class="inputwrap" style="padding: 20px 60px 0 0;width: 280px;">
                <span class="title-realname">验证码</span>            
                <input type="text" id="CHK_CODE" name="CHK_CODE" maxlength="6" class="testnum" placeholder="短信验证码" onkeyup="if(/\D/.test(this.value)){this.value='';}" style="width:130px;float:left;" />
                <button type="button" id="achievenum" class="catch_code" style="width:136px;float:right;height:45px;text-align:center;padding:0;">获取验证码</button>
                <div style="clear:both;"></div>               
            </div> 
		    <div style="clear:both;"></div>
		    <div class="inputwrap" style="padding: 20px 60px 0 0;width: 280px;">
                 <span class="title-realname"></span>
                 <input id="agree_box" name="agree_box" type="checkbox"/><span class="xa_protocol">我同意<a class="blue_link" href="${ctx}/auth/cust/xaprotocol.html" target="_blank">西安银行资金存管三方协议</a></span>
                 <div></div>
            </div>
            <div style="clear:both;"></div>
		    <button type="button" id="doSubmit" class="buttonstyle">确认开户<span id="dotting"></span></button>
		    <div style="clear:both;"></div>
    
    </form>
    </div> 
        
    <div style="clear:both;"></div>
</div>
<!--账户验证end-->
         
          
    <div style="background-color:#4a545c;color:#fff;height:60px;">
        <div style="height:42px;width:1000px;margin:0 auto;line-height:25px;font-family:'SimSun';padding-top:20px;">
                <div style="float:left">
                    <div>陕西金开贷金融服务有限公司 陕ICP备 13007681号</div>
                </div>
                <div style="float:right">
                    <div>@ 2014 金开贷 All rights reserved</div>
                </div>
            </div>
    <div style="clear:both;"></div>
    </div>
   
</body>
</html>

