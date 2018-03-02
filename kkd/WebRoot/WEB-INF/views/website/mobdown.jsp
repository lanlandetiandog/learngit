<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/static/kingkaid/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/qrcode.js"></script> 
<title>金开贷</title>
<script type="text/javascript">
    $(document).ready(function(){	
    	
    	var temp=checkMobile();		
	    if(temp=="I"){		 	
	   	 	window.location.href=document.getElementById("iosurl").value;
 	    }else if(temp=="A"){		 
	   		window.location.href=document.getElementById("androidurl").value;		 
	    }else{	
		   	var str=window.location.href;	
	    //	var str="http://192.168.100.15:8080/kkd/website/mobdown.html";  	
	    }
	    
	 	
    	
     
    });
   
	function checkMobile() {
		var browser = {
			versions : function() {
				var u = navigator.userAgent, app = navigator.appVersion;
				return {//移动终端浏览器版本信息  
					ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端 
					iPhone : u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器 
					iPad : u.indexOf('iPad') > -1, //是否iPad 
					android : u.indexOf('Android') > -1
							|| u.indexOf('Linux') > -1, //android终端或者uc浏览器 
				};
			}(),
			language : (navigator.browserLanguage || navigator.language)
					.toLowerCase()
		};

		if (browser.versions.ios || browser.versions.iPhone
				|| browser.versions.iPad) {
			return "I";
		} else if (browser.versions.android) {
			return "A";
		}
	}
	

</script>
<style>
    .download-banner01{
        background: url(${ctx}/static/kingkaid/images/downloadapp01.jpg) no-repeat 50% 0;
        height: 665px;
        width: 100%;
    }
    .banner01-content{
        height: 100%;
        width: 1000px;
        margin: 0 auto;
        overflow: hidden;
    }
    .ewm-img{
        float: left;
        margin:402px 0px 0 0;
        text-align: center;
    }
    .download-banner02{
        background-color: #fff;
        height: 555px;
        width: 100%;
    }
    .banner02-content{
        background: url(${ctx}/static/kingkaid/images/downloadapp02.png);
        height: 100%;
        width: 1000px;
        margin: 0 auto;
        overflow: hidden;
    }
    
    .download-banner03{
        background-color: #edf7ff;
        height: 555px;
        width: 100%;
    }
    .banner03-content{
        background: url(${ctx}/static/kingkaid/images/downloadapp03.png);
        height: 100%;
        width: 1000px;
        margin: 0 auto;
        overflow: hidden;
    }
    .download-banner04{
        background-color: #fff;
        height: 555px;
        width: 100%;
        
    }
    .banner04-content{
        background: url(${ctx}/static/kingkaid/images/downloadapp04.png);
        height: 100%;
        width: 1000px;
        margin: 0 auto;
        overflow: hidden;
    }
    

    .download-banner05{
        background-color: #edf7ff;
        height: 235px;
        width: 100%;
    }
    .banner05-content{
        background: url(${ctx}/static/kingkaid/images/downloadapp05.png);
        height: 100%;
        width: 1000px;
        margin: 0 auto;
        overflow: hidden;
    }
</style>
</head>

<body>
 <div style="height:84px;width:1000px;margin:0 auto;padding-top:">
        <a style="float:left;margin-top:20px;" href="${ctx}/index.html"><img src="${ctx}/static/kingkaid/images/logo.jpg" /></a>
        <div style="float:right;font-weight:bold;font-size:20px;margin-top:40px;">APP下载</div>
    </div>
    
    <div class="downloadapp-content">
        <div class="download-banner01">
            <div class="banner01-content">
                <div style="float:left;margin:402px 20px 0 456px">
                    <div style="margin-bottom: 25px;">
                    	<c:choose>
                    		<c:when test="${tempflag}">
                    			<a href="${outData[0].imageload}"><img src="${ctx}/static/kingkaid/images/android-download-btn.png" /></a>
                    		</c:when>
                    		<c:otherwise>
                    			<a href="${ctx}/website/androidwait_page.html"><img src="${ctx}/static/kingkaid/images/android-download-btn.png" /></a>
                    		</c:otherwise>
                    	</c:choose>
                    </div>
                    <div>
                        <a href="${outData[0].filepath}" target="blank_"><img src="${ctx}/static/kingkaid/images/ios-downloadapp-btn.png" /></a>
                    </div>
                </div>
           
           			<div><img style="float:left;margin-top:395px" width="150" height="150" src="${ctx}/static/kingkaid/images/qrcode/code.png" /></div>
           			<div style="color: #303030;font-size: 18px;margin-top:550px;">扫描二维码下载客户端</div>
             
            </div>
        </div>
        <div class="download-banner02">
            <div class="banner02-content">
                <div style="font-size: 50px;margin:204px 0 15px 0;">
                    <span style="color: #ff5761;">触手可及</span>
                    <span style="color: #434343">的距离</span>
                </div>
                <div style="color: #757575;font-size:26px;">不分时间，不分地点；随时查看，随时操作</div>
            </div>
        </div>
        <div class="download-banner03">
            <div class="banner03-content" style="text-align:right;">
                <div style="font-size: 50px;margin:204px 0 15px 0;">
                    <span style="color:#529be6;">一目了然</span>
                    <span style="color: #434343">的信息</span>
                </div>
                <div style="color: #757575;font-size:26px;">借款详情随意看，还款计划提前知</div>
            </div>
        </div>
        <div class="download-banner04">
            <div class="banner04-content">
                <div style="font-size: 50px;margin:204px 0 15px 0;">
                    <span style="color: #ff5761;">了如指掌</span>
                    <span style="color: #434343">的资产</span>
                </div>
                <div style="color: #757575;font-size:26px;">账户信息全面掌握，充值提现特别方便</div>
            </div>
        </div> 
        <div class="download-banner05">
            <div class="banner05-content">
                <div style="float:left;text-align: center;margin:45px 93px 0 243px;">
                
                <img style="float:center;margin-top:-20px" width="150" height="150" src="${ctx}/static/kingkaid/images/qrcode/code.png"/>
                     <div style="color: #3f3734;font-size: 18px;margin-top:10px;">扫描二维码下载客户端</div>
                </div>
                <div style="float:left; margin-top: 45px;">
                    <div style="margin-bottom: 20px;">
                        <c:choose>
                    		<c:when test="${tempflag}">
                        		<a href="${outData[0].imageload}"><img src="${ctx}/static/kingkaid/images/android-download-btn.png" /></a>
                    		</c:when>
                    		<c:otherwise>
                    			<a href="${ctx}/website/androidwait_page.html"><img src="${ctx}/static/kingkaid/images/android-download-btn.png" /></a>
                    		</c:otherwise>
                    	</c:choose>
                    </div>
                    <div>
                        <a href="${outDataTemp[0].filepath}" target="blank_"><img src="${ctx}/static/kingkaid/images/ios-downloadapp-btn.png" /></a>
                    </div>
                </div>
                
            </div>
        </div>          
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
    <input type="hidden" id="androidurl"  value="${outData[0].imageload}"/>
    <input type="hidden" id="iosurl"  value="${outDataTemp[0].filepath}"/>  
 </body>
</html>
