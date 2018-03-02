<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/taglib.jsp"%>
<%@ include file="/static/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/static/plugin/js/timer.jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/member/register.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/jquery.qrcode.min.js"></script>
</head>
 
<body>
    <div style="height:84px;width:1000px;margin:0 auto;padding-top:">
        <a style="float:left;margin-top:20px;"href="${ctx}/index.html"><img src="${ctx}/static/kingkaid/images/logo.jpg" /></a>
        <div style="float:right;font-weight:bold;font-size:20px;margin-top:40px;">用户注册</div>
    </div>
    
    <div class="register-content">
        <div class="register_banner">
            <div class="register-box">
                <div class="right-top"></div>
                <div class="reqister-m">
                <div class="register_label">
                    <div class="login_title">注册</div>
                    <label>用户名:</label>
                    <label>登录密码:</label>
                    <label>重复密码:</label>
					<label>手机号码:</label>
					<label>短信验证码:</label>
					<label>聚财码:</label>
                </div>
                <form id="form_register" class="register_area" style="margin-top:0" action="${ctx}/member/register" method="post">
                    <div id="errorMsg" style="color: #E7222B; font-size: 12px; height:20px">${errorMsg}</div>
                    <div class="input-unit">
                        <input type="text" id="filter_membername" name="filter_membername" tabindex="1" class="register-input" />
                        <div></div>
                    </div>
                    <div class="input-unit">
                        <input type="password" id="filter_loginpassword" name="filter_loginpassword" tabindex="2" class="register-input" />
                        <div></div>
                    </div>
                    <div class="input-unit">
                        <input type="password" id="dpassword" name="dpassword" tabindex="3" class="register-input" />
                        <div></div>
                    </div>
                    <div class="input-unit">
                        <input type="text" id="filter_mobilenumber" name="filter_mobilenumber" tabindex="4" class="register-input" />
                        <div></div>
                    </div>
                    <div class="input-unit">
                        <input type="text" id="filter_verifycode" name="filter_verifycode" tabindex="5" class="register-input" />
                        <button type="button" id="fetch-vcode" class="catch_code">获取验证码</button>
                        <div></div>
                    </div>
                    <div class="input-unit">
                        <input type="checkbox"  id="if_invitecode_chkbox" name="if_invitecode_chkbox" tabindex="7" checked="checked" <c:if test="${not empty inviteCode}">disabled</c:if> />
                        <input type="text" id="filter_invitecode" name="filter_invitecode" class="register-input" tabindex="8" style="width:156px;" <c:if test="${not empty inviteCode}">value='${inviteCode}' readonly="true"</c:if> />
                        <div></div>
                    </div>
                    <div style="margin-bottom:15px; height:33px">
                        <input type="checkbox" id="if_read_chkbox" name="if_read_chkbox" tabindex="8"/>
                        <span>我同意</span><a class="blue_link" href="protocol.html" target="_blank">金开贷会员服务协议</a>
                        <div></div>
                    </div>
                    <input type="submit" id="btn_register" class="login_btn" style='width:200px; margin-top:10px; font-family: "微软雅黑","宋体",STHeiti,Verdana,Arial,Helvetica,sans-serif;' tabindex="9" value="注 册"/> 
                    <div class="login-bottom" style="margin-top:10px;">
                        <a href="${ctx}/login.html" style="margin-right:80px;">已有账号，立即登录</a>
                    </div>
                </form>
                </div>
            </div>

            <div class="register-two-code" style="display:none;height:390px;text-align:center;">
                <div class="right-top-view"></div>
                <div style="margin:80px auto 0 auto;">
                    <div class="login_title">扫描下载手机APP</div>
					<img style="float:center;margin-top:-10px" width="200" height="200" src="${ctx}/static/kingkaid/images/qrcode/code.png"/>
             <!--      <div class="two-code-img">
                        <img src="${ctx}/static/kingkaid/images/erweima.jpg" />
                    </div>		 -->  
                </div>  
            </div>

        </div>
        <div style="clear:both"></div>         
    </div>       
           
    <div style="background-color:#4a545c;color:#fff;height:100px;">
        <div style="height:42px;width:1000px;margin:0 auto;line-height:25px;font-family:'SimSun';padding-top:20px;">
                <div style="float:left">
                    <div>陕西金开贷金融服务有限公司 陕ICP备 13007681号</div>
                    <div>@ 2014 金开贷 All rights reserved</div>
                </div>
                <div style="float: right">
						<script src="https://kxlogo.knet.cn/seallogo.dll?sn=e14050861010048655416p000000&size=3"></script>
						<a href="http://117.22.252.216:7001/wljgweb/bscx.do?method=hddoc&amp;id=61000000005374" target="blank_"  style="margin-left:19px;"> 
							<img src="${ctx}/static/kingkaid/images/gs2.jpg" />
						</a>
						<div id='symantecSeal' style='text-align:center; width: 92px;display:inline;'  title='单击即可验证 - 该站点选择 SymantecSSL 实现安全的电子商务和机密通信' >
						<a href="https://trustsealinfo.verisign.com/splash?form_file=fdf/splash.fdf&amp;dn=www.jkd.com&amp;lang=zh_cn" target="blank_" style="margin-left: 18px;">
							<img src="${ctx}/static/kingkaid/images/vseal.jpg" />
						</a>
				</div>
            </div>
    </div>

	<div id="mvcodeDiv" style="display:none; z-index:999">
	    <div class="alert_box_small" style="width:400px; top:200px">
	       <div class="window_top">
	           <div class="window_top_l">请输入验证码用以发送短信</div>
	           <div class="window_close_btn" id="mvd_close"><img src="${ctx}/static/kingkaid/images/window_close_bt.png"/></div>
	       </div>
	       <div class="window_content">
	           <div class="operate_content" style="width:400px">
	               <form id="mcform" style="width:325px;height:55px">
	                   <input id="mvcode" name="mvcode" type="text" class="login-input" style="width:146px;margin-left:80px" placeholder="请输入验证码" />
	                   <img id="vcode_img" style="float:right;cursor:pointer;margin-top:5px" title="点击更换验证码" alt="验证码" src="${ctx}/member/getVCodeValidateCode?s=<%=new java.util.Date().getTime()%>" />
	                   <div style="margin-left:80px;line-height:20px"></div>
	               </form>
	           </div>
	       </div>
	       <div class="small_window_bottom" style="padding:20px 0 32px 0">
	           <div id="mvd_ok" class="blue_btn">确定</div>
	           <div id="mvd_cancel" class="blue_btn btn_right">取消</div>
	       </div>
	    </div>
	</div>
	
</body>
</html>
