<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="${ctx}/static/kingkaid/css/jkdinfo_css/main.css" />
<title>金开贷</title>
<style type="text/css">
    .banner2{
        height:578px;
        width: 100%;
        margin: 0 auto;
        text-align: center;
        background: linear-gradient(0deg,#348ac7,#7474bf);
        background-size: contain;
    }
    
    .banner2{ 
        background: #348ac7; 
        background: -moz-linear-gradient(top, #7474bf 0%,#348ac7  100%); 
        background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#7474bf), color-stop(100%,#348ac7)); 
        background: -webkit-linear-gradient(top, #7474bf 0%,#348ac7 100%); 
        background: -o-linear-gradient(top, #7474bf 0%,#348ac7 100%); 
        background: -ms-linear-gradient(top, #7474bf 0%,#348ac7 100%); 
        background: linear-gradient(to bottom, #7474bf 0%,#348ac7 100%); 
        filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#7474bf', endColorstr='#348ac7',GradientType=0 ); 
    } 
    :root ..banner2{filter:none;}
    .bannertwo{
        height: 300px;
        width: 960px;
        margin: 0 auto;
        margin-top: 40px;
        margin-bottom: 20px;
        text-align: center;
        background: url(${ctx}/static/kingkaid/images/borrow/twobanner.png) top center no-repeat;
        background-size: contain;

    }
    .banner2in{
        height:578px;
        width: 100%;
        margin: 0 auto;
        background: url(${ctx}/static/kingkaid/images/borrow/banner2.png) bottom center no-repeat;
    }
    .inputbox{
        width: 913px;
        height: 468px;
        margin: 0 auto;
    }
    .float-r{
        width: 365px;
        height: 504px;
        padding: 20px 0 20px 30px;
        margin-top:18px;
        border-radius:6px;
        float: right;
        font-size: 18px;
        text-align: left;
        background: #fff;
    }
    .phg{
        line-height:20px;
        margin-bottom:32px;
    }
    .inputboxb,.inputboxc,.inputboxd{
        width: 360px;
        height: 36px;
        margin-bottom: 21px;
        margin-top: 8px;
        text-align: left;
    }
    .inputname{
        width:88px;
        height: 36px;
        font-size: 16px;
        line-height:36px;
        float:left;
    }
    .input_focus{
        width: 200px;
        height: 36px;
        float: left;
    }
    .input_focus input{
        height:30px;
        width: 180px;
        padding-left: 14px;
        border:1px #e2e2e2 solid; 
        border-radius: 4px;
    }
    .rmb{
        line-height:28px;
        font-size: 14px;
    }
    .telinput{
        height:30px;
        width: 82px;
        padding-left: 8px;
        float: left;
        border:1px #e2e2e2 solid; 
        border-radius: 4px;
    }
    .input_button{
        width: 197px;
        margin-top: 14px;
        float: left;
        padding-left: 88px;
    }
    .input_buttond{
        width: 284px;
        height: 36px;
    }
    .submit_apply{
        height:34px;
        width: 92px;
        line-height: 34px;
        text-align: center;
        border-radius: 4px;
        color: #fff;
        font-size: 14px;
        background: #5d89c9;
        float: right;
        margin-top: 12px;
    }
     .telbtn{
        height:34px;
        width: 92px;
        line-height: 34px;
        text-align: center;
        border-radius: 4px;
        color: #fff;
        font-size: 14px;
        background: #5d89c9;
        margin-left: 21px;
    }
    .form-control,.form-controlb{
        height:34px;
        width: 92px;
        padding-left: 14px;
        float: left;
        border:1px #e2e2e2 solid; 
        border-radius: 4px;
    }
    .form-controlb{
        float: right;
    }
    .submit_apply:hover,.telbtn:hover{
        cursor: pointer;
        background:#537bb5;
    }
    .inputboxd{
        margin-top:20px;
        margin-bottom:0px;
    }
    .submit_apply{
        width:195px;
        height: 34px;
        margin-top: 33px;
        margin-right: 82px;
    }
    .subtips{
        font-size: 14px;
        margin-top: 15px;
        color: #333;
    }
    .loan_tbn{
        float:right;
        font-size:20px;
        margin-top:40px;
        margin-right: 18px;
    }
    .loan_tbn a{
        color:#333;
    }
    .loan_login,.loan_register{
        width: 45px;
        padding:0 20px;
        height: 20px;
        line-height:20px;
        float: left;
    }
    .loan_register{
        border-left: 1px solid #373737;
    }
    .catch_code_gray{    	   
		height: 34px;
	    width: 92px;
	    line-height: 34px;
	    text-align: center;
	    border-radius: 4px;
	    color: #fff;
	    font-size: 14px;
	    background: #99a7ba;
	    float: right;
	    margin-left: 21px;
    }
    .codeinput{
        width:50px;
        height: 30px;
	    padding-left: 14px;
	    border: 1px #e2e2e2 solid;
	    border-radius: 4px;
    }
</style>
</head>
 
<body>
<div style="background:#fff;width:100%;height:84px;background:#fff;">
    <div style="height:84px;width:1000px;margin:0 auto;">
        <a style="float:left;margin-top:20px;" href="${ctx}/"><img src="${ctx}/static/kingkaid/images/borrow/logo.png" /></a>
        <div class="top-right"  style="margin-top: 30px;">
            <ul>
                <li><a href="${ctx}/login.html">登录</a></li>
                <li><a href="${ctx}/member/register.html">注册</a></li>
            </ul>
        </div>
    </div>
</div>  


<!--预借款-->
    <div class="banner2">
        <div class="banner2in">
            <div class="inputbox">
                <div class="float-r">
                    <p class="phg"><img src="${ctx}/static/kingkaid/images/borrow/safe.png"/>   基本信息填写</p>
                     <form id="form_borrow" action="" method="post" style="margin-top: -18px;">
                    <div class="inputboxb">
                        <div class="inputname">借款金额:</div>
                        <div class="input_focus"><input type="text" name="borrowamt" id="borrowamt" value=""/></div>
                        <span class="rmb">元</span>
                    </div>
                    <div class="inputboxb">
                        <div class="inputname">公司名称:</div>
                        <div class="input_focus"><input type="text" name="companyname" id="companyname" value=""/></div>
                        <span class="rmb">（选填）</span>
                    </div>
                    <div class="inputboxb">
                        <div class="inputname">联系人:</div>
                        <div class="input_focus"><input type="text" name="custname" id="custname" value=""/></div>
                    </div>
                    <div class="inputboxc">
                        <div class="inputname">手机号码:</div>
                        <div class="input_focus">
                        <input type="text" name="tel" id="tel" class="borrow_input" value=""/>
                        <em class="error" id="sp5"></em>
                        </div>
                    </div>
                     <div class="inputboxc">
                         <div class="inputname">验证码:</div>
                         <div style="height: 36px;float:left;">
	                         <input class="codeinput" name="bmvcode"  id="bmvcode" type="text" value=""/>
	                         <button class="telbtn" type="button"  id="ctp_m_send_new_btn">获取验证码</button>
	                         <div></div>
                         </div>
                     </div>
                     
                    <div class="inputboxd">
                        <div class="inputname">所在地区:</div>
                        <div class="input_buttond">
                           <div style="display:inline-block;">
                                            <span id="job_work_city_area"></span>
                                            <input type="hidden" id="workcity_code" name="address" value="" />
                                 <div></div>
                          </div>
                        </div>
                    </div>
                    <p style="font-size:12px;color:#333333;">（目前仅支持西安、武汉、银川、珠海用户在线申请。）</p>
                    <button class="submit_apply"  type="submit">提交申请</button>
                    </form>
                    <!--提交成功后——alert成功提示信息-->
                    <!--提交成功，项目经理会在24小时内与您取得联系，请您耐心等待！-->
                    <div style="clear: both"></div>
                    <p class="subtips">温馨提示：<br/>请填写真实有效的联系信息，客户经理会在您提交借款<br/>申请后的24小时内与您取得联系！</p>
                </div>
            </div><br/>
        </div>
    </div>
    <div class="bannertwo"></div>
    
<!--预借款end-->

    <div style="background-color:#4a545c;color:#fff;height:100px">
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
   </div>
   
   
   
    <div id="umvcodeDiv" style="display:none; z-index:1999">
        <div class="alert_box_small" style="width:400px; top:200px">
           <div class="window_top">
               <div class="window_top_l">请输入验证码用以发送短信</div>
               <div class="window_close_btn" id="umvd_close"><img src="${ctx}/static/kingkaid/images/window_close_bt.png"/></div>
           </div>
           <div class="window_content">
               <div class="operate_content" style="width:400px">
                   <form id="umcform" style="width:325px;height:55px">
                       <input id="umvcode" name="umvcode" type="text" class="login-input" style="width:146px;margin-left:80px" placeholder ="请输入验证码" />
                       <img id="umvcode_img" style="float:right;cursor:pointer;margin-top:5px" title="点击更换验证码" alt="验证码" src="${ctx}/member/getVCodeValidateCode?s=<%=new java.util.Date().getTime()%>" />
                       <div style="margin-left:80px;line-height:20px"></div>
                   </form>
               </div>
           </div>
           <div class="small_window_bottom" style="padding:20px 0 32px 0">
               <div id="umvd_ok" class="blue_btn">确定</div>
               <div id="umvd_cancel" class="blue_btn btn_right">取消</div>
           </div>
        </div>
    </div>
</body>
</html>
