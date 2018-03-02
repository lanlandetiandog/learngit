<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link type="image/x-icon" href="${ctx}/static/kingkaid/images/favicon.ico" rel="shortcut icon"/>
<script type="text/javascript">var ctx = '${ctx}';</script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/jquery-1.7.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/anniactivity.js"></script>  
<script type="text/javascript" src="${ctx}/static/kingkaid/js/kxbdSuperMarquee.js"></script>
<link href="${ctx}/static/kingkaid/css/lottery.css" rel="stylesheet" type="text/css">
<title>金开贷</title>
</head>
 
<body>
<!--headertips-->
<div class="headertips"><img src="${ctx}/static/kingkaid/images/prize/top.png"></div>
<div class="maintips">
	<div class="lotpeople">
    	 <div class="marquee">
			 <ul>
			    <c:if test="${not empty userinfolist}">
				     <c:forEach items="${userinfolist}" var="userinfo">
				       <span>${kkd:showPrivacy(userinfo.membername,6)}获得${userinfo.awardname}</span>
				     </c:forEach>
			    </c:if>
			</ul>
	   </div>
	   <div class="lotpeople-r">中奖用户名单</div>
        <div id="loading" style="display:none;">
	         <div class="spinner">
				  <div class="spinner-container container1">
				    <div class="circle1"></div>
				    <div class="circle2"></div>
				    <div class="circle3"></div>
				    <div class="circle4"></div>
				  </div>
				  <div class="spinner-container container2">
				    <div class="circle1"></div>
				    <div class="circle2"></div>
				    <div class="circle3"></div>
				    <div class="circle4"></div>
				  </div>
				  <div class="spinner-container container3">
				    <div class="circle1"></div>
				    <div class="circle2"></div>
				    <div class="circle3"></div>
				    <div class="circle4"></div>
				  </div>
			</div>
		</div>	   
	   
    </div>

        <div  class="leftimg">
        	<div><img src="${ctx}/static/kingkaid/images/prize/tips.png"></div>
            <div class="address">
            	<div><img src="${ctx}/static/kingkaid/images/prize/mylot.png"></div>
                	<div class="addressline">
                    	
                    </div>
            </div>
        </div>
        <!--lottery-->

          <video id="lotmo"  poster="${ctx}/static/kingkaid/images/prize/doorclose1.png" preload="auto">
                 <source src="${ctx}/static/kingkaid/images/prize/lotmo.mp4" type="video/mp4"/>
          </video>
                     
        <div class="footertext"></div>
      
        <button type="button" id="play" disabled="disabled"></button>
        
        <div style="clear:both;"></div>
<!--12点击热区-->
        <div class="lotin">
        	<a href="javascript:showBg();"></a>
        	<a href="javascript:showBg();"></a>
        	<a href="javascript:showBg();"></a>
        	<a href="javascript:showBg();"></a>
        	<a href="javascript:showBg();"></a>
        	<a href="javascript:showBg();"></a>
            <div style="clear:both;"></div>
        	<a href="javascript:showBg();"></a>
        	<a href="javascript:showBg();"></a>
        	<a href="javascript:showBg();"></a>
        	<a href="javascript:showBg();"></a>
        	<a href="javascript:showBg();"></a>
        	<a href="javascript:showBg();"></a>
        </div>
<!--12点击热区end-->

  
<!--奖品弹出层-->     
</div>

<div id="close">
        
</div>
<!--遮罩层-->

<!--奖品弹出层-->  

<!--中奖人员名单滚动出现  -->
<script type="text/javascript">
		
		$(function(){
			//Marquee
			$('.marquee').kxbdSuperMarquee({
				isMarquee:true,
				isEqual:false,
				scrollDelay:20,
				direction:'left',
			});
		});

</script>
<div class="">
   <a class="opentb" href="${ctx}/website/anni_activitysp.html">
    <img src="${ctx}/static/kingkaid/images/prize/back.png" />
   </a>
</div>
</body>
</html>
