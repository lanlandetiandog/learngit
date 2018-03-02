<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/kingkaid/css/main.css" />
<script type="text/javascript" src="${ctx}/static/kingkaid/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/qrcode.js"></script> 
<style type="text/css">
.iosapp{
    margin-left:auto;
    margin-right:auto;
    min-width: 1200px;  
	width: 100%;
	height:630px;
	font-family: "微软雅黑";
	font-size: 14px;
	background:url(${ctx}/static/kingkaid/images/downloadapp01_04%20Android.png) no-repeat top center;
    background-color:#e7f2f7;
}

</style>
<title>金开贷</title>
</head>
 
<body>
    <div style="height:84px;width:1000px;margin:0 auto;">
        <a style="float:left;margin-top:20px;" href="${ctx}/index.html"><img src="${ctx}/static/kingkaid/images/logo.png" /></a>
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
                    <div><a href="http://weibo.com/jinkaidai">@金开贷</a></div>
                </div>
            </div>
            <div style="float:left;margin:11px 15px 0 5px;">
                <a><img src="${ctx}/static/kingkaid/images/app-download.png"/></a>
            </div>
        </div>
    </div>
    
    <div class="content" style="overflow:hidden;">
    
    	<div class="iosapp"></div>
        <div style="clear:both"></div>
    </div>
           
 	<div style="background-color:#4a545c;color:#fff;height:100px;">
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
   
</body>
</html>




