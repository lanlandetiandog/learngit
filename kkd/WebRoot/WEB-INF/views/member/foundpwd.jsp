<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/member/foundpwd.js"></script>
<script type="text/javascript" src="${ctx}/static/plugin/js/timer.jquery.js"></script>
</head>
 
<body>
    <div style="height:84px;width:1000px;margin:0 auto;">
        <a style="float:left;margin-top:20px;" href="${ctx}/"><img src="${ctx}/static/kingkaid/images/logo.jpg" /></a>
        <div class="top-left" style="margin:20px 0 0 30px;padding-left:30px;line-height:43px;border-left:1px solid #dfdfdf;">
            <div style="float:left;color:#747474;">客服电话：400-1888-136</div>
            <div style="margin-left:40px;float:left;color:#747474;border-right:1px solid #dfdfdf;padding-right:30px;">服务时间 : 09:00 - 17:00 (工作日) </div>
            <div class="weixin">
                <img src="${ctx}/static/kingkaid/images/wx.png"/>
                <div class="winxin_hover"><img src="${ctx}/static/kingkaid/images/kingweixin.png" /></div>
            </div>
            <div class="webo">
                <img src="${ctx}/static/kingkaid/images/wb.png"/>
                <div class="webo_hover">
                    <div>点击关注新浪微博</div>
                    <div><a>@金开贷</a></div>
                </div>
            </div>
            <div style="float:left;margin:11px 15px 0 5px;">
                <a  href="${ctx}/website/mobdown.html"><img src="${ctx}/static/kingkaid/images/app-download.png"/></a>
            </div>
        </div>
    </div>
    
    <div class="content">
        <div class="psd_content">
            <ul class="recharge-tab" style="width:560px;margin:20px auto 0 auto;font-size:16px;border:0">
                <li class="recharge-type recharge-type-cur" id="mobile_found" style="border-right:1px solid #dbddde;">
                    <div>使用手机找回</div>
                </li>
                <li class="recharge-type" id="mail_found">
                    <div>使用邮箱找回</div>
                </li>	 	
            </ul>
            <form id="mobile_form">
            <ul class="mobile_found" >
                <li class="pwd_input-unit">
                    <div class="psd_input_title">手机号码：</div>
                    <div class="psd_input">
                        <input id="mobile_number" name="mobile_number" type="text" class="register-input" style="width:160px;" />
                        <span id="m_send_code" class="catch_code">获取验证码</span>
                        <div></div>
                    </div>
                </li>
                <li class="pwd_input-unit">
                    <div class="psd_input_title">短信验证码：</div>
                    <div class="psd_input">
                        <input id="m_verify_code" name="m_verify_code" type="text" class="register-input" style="width:160px;"/>
                        <div></div>
                    </div>
                </li>
                <li>
                    <button id="m_next_btn" type="button" class="blue-btn" style="margin-left:90px;">下一步</button>
                </li>
            </ul>
            </form>
            <form id="mail_form">
            <ul class="mail_found" style="display:none">
                <li class="pwd_input-unit">
                    <div class="psd_input_title">注册邮箱：</div>
                    <div class="psd_input">
                        <input id="email" name="email" type="text" class="register-input" style="width:160px;" />
                        <span id="e_send_code" class="catch_code">发送验证邮件</span>
                        <div></div>
                    </div>
                </li>
                <li id="to_mailbox_li" style="display:none">
                    <button id="to_emailbox" type="button" class="blue-btn" style="margin-left:90px;cursor:pointer">进入邮箱验证</button>
                </li>
            </ul>
            </form>
        </div>
    </div>
           
    <div style="background-color:#4a545c;color:#fff;height:87px;">
        <div style="height:42px;width:1000px;margin:0 auto;line-height:25px;font-family:'SimSun';padding-top:20px;">
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
                       <img id="mvcode_img" style="float:right;cursor:pointer;margin-top:5px" title="点击更换验证码" alt="验证码" src="${ctx}/member/getVCodeMValidateCode?s=<%=new java.util.Date().getTime()%>" />
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
    
    <div id="evcodeDiv" style="display:none; z-index:999">
        <div class="alert_box_small" style="width:400px; top:200px">
           <div class="window_top">
               <div class="window_top_l">请输入验证码用以发送邮件</div>
               <div class="window_close_btn" id="evd_close"><img src="${ctx}/static/kingkaid/images/window_close_bt.png"/></div>
           </div>
           <div class="window_content">
               <div class="operate_content" style="width:400px">
                   <form id="ecform" style="width:325px;height:55px">
                       <input id="evcode" name="evcode" type="text" class="login-input" style="width:146px;margin-left:80px" placeholder="请输入验证码" />
                       <img id="evcode_img" style="float:right;cursor:pointer;margin-top:5px" title="点击更换验证码" alt="验证码" src="${ctx}/member/getVCodeMValidateCode?s=<%=new java.util.Date().getTime()%>" />
                       <div style="margin-left:80px;line-height:20px"></div>
                   </form>
               </div>
           </div>
           <div class="small_window_bottom" style="padding:20px 0 32px 0">
               <div id="evd_ok" class="blue_btn">确定</div>
               <div id="evd_cancel" class="blue_btn btn_right">取消</div>
           </div>
        </div>
    </div>
   
</body>
</html>
