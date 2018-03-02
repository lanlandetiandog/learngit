<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>活动专区</title>
<link type="image/x-icon" href="${ctx}/static/kingkaid/images/logo.ico" rel="shortcut icon"/>
<meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi"/>
<script type="text/javascript">
  $(document).ready(function(){
	  var infoPara;
		infoPara = {
			title:'金开贷精彩活动【活动专区】,参与赢好礼，别怪我没告诉你！',
			imgUrl:'http://m.jkd.com/static/kingkaid/images/icon2.0-87.png',
			desc:'平台活动',
			link:window.location.toString(),
		};
		jkdWeChat(infoPara);
  });
</script>
<style type="text/css">
body{
	font-family: 'Yanone Kaffeesatz', sans-serif;
	background:#f0f0f0;
}
body{
	font-size:14px;
	max-width:640px;
	margin:0 auto;
	transition:0.5s all;
	-webkit-transition:0.5s all;
	-moz-transition:0.5s all;
	-o-transition:0.5s all;
	-ms-transition:0.5s all;
}
span,p,a{margin:0; padding:0;background:#f0f0f0;outline:none;text-decoration:none;}

.container{
	max-width:640px;
	background:#fff;
	margin:12px 0;
	padding:0 12px;
	}
.img-responsive,
 img {
  display: block;
  max-width: 100%;
  height: auto;
  text-align:center;
  margin:0 auto;
  border:0;text-decoration:none;
}
.activname,.activin,.activend{
	display:inline-block;
	height:1.8em;
	line-height:1.8em;
	font-size:1.8em;
	text-align:left;
	color:#333;
	background:#fff;
	}
.activapp2{
	line-height:1.8em;
	}
.appcircle{
	width:2px;
	height:2px;
	margin-bottom:0.8em;
	border-radius:1px;
	vertical-align:middle;
	display:inline-block;
	background:#000;
	}
.appline{
	display:inline-block;
	width:0.1em;
	height:1.8em;
	margin:0.6em 1.2em 0 0;
	float:right;
	background:#666;
	}
.activin,.activend{
	color:#ee4639;
	float:right;
	text-align:right;
	}
.activend{
	color:#999;
	}
</style>
</head>

<body>
      <%-- <c:forEach var="activPage" items="${outData}">
        <div class="container">
             <a href="${activPage.activename}">
                <img  class="img-responsive" src="${activPage.imageload}">
                <div class="activapp2">
                	<div class="appcircle"></div>
                    <span class="activname">${activPage.activeid}</span>
                    <c:choose>
                      <c:when test="${activPage.isornotflag eq '1'}">
                         <span class="activin">正在进行</span>
                      </c:when>
                      <c:otherwise>
                         <span class="activend">已结束</span>
                      </c:otherwise>
                    </c:choose>                   
                    <div class="appline"></div>
                </div>
             </a>
         </div> 
       </c:forEach>   --%>
   
       <div class="container">
             <a href="${ctx}/market/activity/firstloan.html">
                <img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/appimg7.jpg"/>
                <div class="activapp2">
                	<div class="appcircle"></div>
                    <span class="activname">首投加息</span>
                    <span class="activin">正在进行</span>
                    <div class="appline"></div>
                </div>
             </a>
         </div>
         <div class="container">
             <a href="${ctx}/market/activity/anni2_activitysp.html">
                <img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/annispread/annispread.jpg"/>
                <div class="activapp2">
                	<div class="appcircle"></div>
                    <span class="activname">金开贷3周年，有"礼"更精彩</span>
                    <span class="activend">已结束</span>
                    <div class="appline"></div>
                </div>
             </a>
         </div>
         <div class="container">
             <a href="${ctx}/market/activity/newred.html">
                <img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/appimg8.jpg"/>
                <div class="activapp2">
                	<div class="appcircle"></div>
                    <span class="activname">最高88元惊喜红包等您来拿</span>
                    <span class="activend">已结束</span>
                    <div class="appline"></div>
                </div>
             </a>
         </div>
         <div class="container">
             <a href="${ctx}/market/activity/autumnphoto.html">
                <img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/index0.jpg"/>
                <div class="activapp2">
                	<div class="appcircle"></div>
                    <span class="activname">最美秋景随手拍</span>
                    <span class="activend">已结束</span>
                    <div class="appline"></div>
                </div>
             </a>
         </div>
        <div class="container">
             <a href="${ctx}/market/activity/summer_activitysp.html">
                <img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/appimg1.jpg"/>
                <div class="activapp2">
                	<div class="appcircle"></div>
                    <span class="activname">夏日清凉大作战</span>
                    <span class="activend">已结束</span>
                    <div class="appline"></div>
                </div>
             </a>
         </div>
         <div class="container">
             <a href="${ctx}/market/activity/redpackets.html">
                <img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/appimg2.jpg"/>
                <div class="activapp2">
                	<div class="appcircle"></div>
                    <span class="activname">我为金狂，任性开抢</span>
                    <span class="activend">已结束</span>
                    <div class="appline"></div>
                </div>
             </a>
         </div>
         <div class="container">
             <a href="${ctx}/market/activity/anni_activitysp.html">
                <img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/appimg3.jpg"/>
                <div class="activapp2">
                	<div class="appcircle"></div>
                    <span class="activname">金彩纷呈，开怀畅赢</span>
                    <span class="activend">已结束</span>
                    <div class="appline"></div>
                </div>
             </a>
         </div>
         <div class="container">
             <a href="${ctx}/market/activity/loangift.html">
                <img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/appimg4.jpg"/>
                <div class="activapp2">
                	<div class="appcircle"></div>
                    <span class="activname">关心收益，更关心您的健康</span>
                    <span class="activend">已结束</span>
                    <div class="appline"></div>
                </div>
             </a>
         </div>
         <div class="container">
             <a href="${ctx}/market/activity/InternetLoan.html">
                <img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/appimg5.gif"/>
                <div class="activapp2">
                	<div class="appcircle"></div>
                    <span class="activname">518互联网金融网贷节</span>
                    <span class="activend">已结束</span>
                    <div class="appline"></div>
                </div>
             </a>
         </div>
         <div class="container">
             <a href="${ctx}/market/activity/Invitefriends.html">
                <img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/appimg6.jpg"/>
                <div class="activapp2">
                	<div class="appcircle"></div>
                    <span class="activname">邀请好友祝您星想事成</span>
                    <span class="activend">已结束</span>
                    <div class="appline"></div>
                </div>
             </a>
         </div>                  
</body>
</html>
