<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
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
	    
	 	/* $(".androidupbtn").hover(function(){
	 	    $(".andplay").css("display","block");
	 	},function(){
	 	    $(".andplay").css("display","none");
	 	}); */
    	
     
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
 <style type="text/css">
.appload{
		background:url(${ctx}/static/kingkaid/images/appdown/bg10.png) no-repeat top center;
		height:606px;
		overflow:hidden;
		}
.appbg1{
	width:860px;
	height:606px;
	margin:0 auto;
	}
.mg0{
	margin:0 auto;
	}
.bg1phone{
	width:260px;
	float:left;
	margin:150px 66px 0 0;
	}
.bg1open{
	width:480px;
	padding-top:95px;
	float:left;
	height:604px;
	}
.bg1open1{
	width:480px;
	padding-top:60px;
	float:left;
	height:604px;
	}
.loadbgbtn{
	width:500px;
	height:240px;
	}
.androidupbtn{
	height:58px;
	margin-top:30px;
	display:block;
	}
.androidbtn{
	height:58px;
	margin-top:42px;
	display:block;
	}
.iosbtn{
	height:58px;
	margin-top:42px;
	display:block;
	}
.app2code{
	margin:40px 0 0 20px;
	float:left;
	}
.app2code p{
	line-height:30px;
	}
.appdisplay{
	height:1950px;
	background:url(${ctx}/static/kingkaid/images/appdown/bg2.png) no-repeat top center;
	}
.appdpone{
	margin-top:30px;
	height:750px;
	width:860px;
	}
.appdptwo{
	margin-top:90px;
	height:750px;
	width:860px;
	}
.appdptwobg{
	height:560px;
	overflow:hidden;
	background:#ecf7ff;
	}
.appdpthree{
	width:860px;
	height:640px;
	}
.apploadfoot{
	height:240px;
	text-align:center;
	background:#ecf7ff;
	}
.loadfootbox{
	width:740px;
	height:196px;
	}
.footldbtn{
	float:left;
	width:330px;
	}
	
.andplay{
   display:none;
}		
</style>
</head>

<body>
 <div style="height:84px;width:1000px;margin:0 auto;padding-top:">
        <a style="float:left;margin-top:20px;" href="${ctx}/index.html"><img src="${ctx}/static/kingkaid/images/logo.jpg" /></a>
        <div style="float:right;font-weight:bold;font-size:20px;margin-top:40px;">APP下载</div>
    </div>
    
    
    <!--app2.0下载页面-->
	<div class="appload">
    	<div class="appbg1">
        	<div class="bg1phone"></div>
        	
        	     <div class="bg1open1">
	            	<div style="width:480px;height:304px"></div>
	                <div style="clear:both"></div>
	                <div class="loadbgbtn">
	                	<div style="float:left;width:260px;">
	                	    <a href="${outDataTemp[0].filepath}" target="blank_" class="iosbtn"><img src="${ctx}/static/kingkaid/images/appdown/osbtnw.png"/></a>
	                        <a href="${outData[0].imageload}" class="androidupbtn"><img src="${ctx}/static/kingkaid/images/appdown/adgbtn.png"/></a>
	                        <div class="andplay" style="height:20px;text-align:center;padding-top:10px;font-size:16px;" >
	                          <span style="color:#fff;">即将发布 </span><span style="color:#333;">敬请期待...</span>     
	                        </div>
	                    </div>
	                    <div class="app2code">
	                    	<img src="${ctx}/static/kingkaid/images/appdown/app2code.png"/>
	                        <p>扫描二维码，下载客户端</p>
	                    </div>
	                </div>
                 </div>  
        </div>
    </div>
    <div class="appdisplay">
    	<div class="appdpone mg0"><img src="${ctx}/static/kingkaid/images/appdown/bg2phone.png"/></div>
    	<div class="appdptwobg">
        	<div class="appdptwo mg0"><img src="${ctx}/static/kingkaid/images/appdown/bg3phone.png"/></div>
        </div>
        <div class="appdpthree mg0"><img src="${ctx}/static/kingkaid/images/appdown/bg4.png"/></div>
    </div>
    
    <div class="apploadfoot">
       <div style="width:800px;position:relative;margin:0 auto;">
           <div class="loadfootbox mg0" style="width:510px;">
                    <div class="app2code">
                    	<img src="${ctx}/static/kingkaid/images/appdown/app2code.png"/>
                        <p>扫描二维码，下载客户端</p>
                    </div>
                    <div class="footldbtn">
                        <a href="${outDataTemp[0].filepath}" target="blank_" class="iosbtn"><img src="${ctx}/static/kingkaid/images/appdown/iosredbtn.png"/></a>
                        <a href="${outData[0].imageload}" class="androidbtn"><img src="${ctx}/static/kingkaid/images/appdown/adbtnred.png"/></a>    
                    </div>
           </div> 
           
          <!--  <span style="height:20px;text-align:center;padding-top:10px;font-size:18px;position:absolute;bottom:18px;right:15px;">
	             <span style="color:#f04531;font-weight:500;">即将发布 </span><span style="color:#333;">敬请期待...</span>     
	       </span> -->
       </div>         
    </div>
<!--app2.0下载页面end-->
          
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
