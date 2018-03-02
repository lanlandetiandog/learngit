<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/static/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/static/plugin/js/jquery.zclip.min.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/myshare.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/qrcode.js"></script> 
</head>

<body>
    <%@ include file="../common/header.jsp" %>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="usercenter_content">
                    <div class="usercenter-title">
                        <div class="usertitle-text">邀请好友 </div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                    </div>
                    <div>
                        <div class="shear_left">    
                            <div class="share_title">分享聚财码给好友</div>
                            <div style="width:548px;height:380px;background:url(${ctx}/static/kingkaid/images/friend1.jpg);margin:0 auto;overflow:hidden;">
                                <div id="share_code" class="share_code">${shareInfo.invitecode}</div>
                                <div id="copy_btn" class="copy_btn">复 制</div>
                            </div>

                            <div class="share_title">邀请好友使用聚财码注册并投资</div>
                            <div style="width:548px;height:368px;background:url(${ctx}/static/kingkaid/images/friend2.jpg);margin:0 auto;"></div>

                            <div class="share_title">与好友一起多多赚钱</div>
                            <div style="width:548px;height:252px;background:url(${ctx}/static/kingkaid/images/friend3.jpg);margin:0 auto;">
                            
                            </div>
                        </div>
                        <div class="shear_right">
                            <div class="share_total">聚财码使用次数： ${shareInfo.usetotal}次</div>
                            <div class="share_total">返利总额： ${shareInfo.earntotal}元</div>
                            <div style="color:#dd635f;font-size:18px;padding-left:18px;margin:50px 0 30px 0;">分享的好处：</div>
                            <ul class="share_list">
                                <li>好友使用您的聚财码注册将获得额外的金开币奖励、一个月VIP权限以及千分之三的加息券奖励；</li>
                                <li>您邀请的好友成功实名注册您将获得金开币奖励；</li>
                                <li>您邀请的好友6个月内每笔成功投资您都将获得平台返利奖励。</li>
                            </ul>
                            <div style="font-size:18px;font-weight:bold;margin:40px 0 20px 20px;">分享至社交平台</div>
                            <!-- JiaThis Button BEGIN -->
                            <div style="margin:15px 0 15px 20px;text-align:center;overflow:hidden;">
                                <div class="jiathis_style">
                                    <a class="jiathis_button_qzone"></a>
                                    <a class="jiathis_button_tsina"></a>
                                    <a class="jiathis_button_tqq"></a>
                                    <a class="jiathis_button_weixin"></a>
                                    <a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
                                    <a class="jiathis_counter_style"></a>
                                </div>
                                <script>
                                var jiathis_config = {
                                	url: '${shareInfo.shareurl}',
                                    title: '${shareInfo.sharecontent}'
                                };
                                </script>
                                <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
                            </div>    
                            <!-- JiaThis Button END -->
                            <div style="margin: 50px 0 20px 20px;">	
                             <img style="float:left;margin-top:-50px"src="${ctx}/getCodeZXing?width=200&height=200&url=${url}" />
                         <!--   <div id="codebody" style="background-color: white; height: 140px; width: 140px;">
		                   		<div id="code" style="line-height:50px; padding:10px 10px;"></div>
		                   	</div> 
                             <a href="please_mobile_page.html"><img src="${ctx}/static/kingkaid/images/erweima.jpg"/></a>  
                                <div style="margin:20px 0;">微信扫描二维码分享给好友</div>-->   
                            </div>
                        </div>
                    </div>
                    <%@ include file="usercenterleftmenu.jsp"%>
                </div>
            </div>
             <input type="hidden" id="shareurl"  value="${shareInfo.shareurl}"/>
            <div style="clear:both"></div>         
        </div> 
        <div style="clear:both"></div>       
    </div>
   <%@ include file="../common/footer.jsp"%>
</body>
</html>
