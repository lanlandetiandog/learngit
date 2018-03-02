<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link type="image/x-icon" href="${ctx}/static/kingkaid/images/favicon.ico" rel="shortcut icon"/>
<script type="text/javascript">var ctx = '${ctx}';</script>
<title>金开贷</title>
<style type="text/css">
	.top1{
		width:100%;
		height:1517px;
		margin:0 auto;
		position:relative;
		background:url(${ctx}/static/kingkaid/images/prize/top1.png) no-repeat top center;
		}
	.top2{
		width:100%;
		height:520px;
		margin:0 auto;
		background:url(${ctx}/static/kingkaid/images/prize/top2.png) no-repeat top center;
		}
	.top3{
		width:100%;
		height:868px;
		margin:0 auto;
		background:url(${ctx}/static/kingkaid/images/prize/top3.png) no-repeat top center;
		}
	.top4{
		position:relative;
		width:100%;
		height:474px;
		margin:0 auto;
		background:url(${ctx}/static/kingkaid/images/prize/top4.png) no-repeat top center;
	}
	.top5{
		position:relative;
		width:100%;
		height:930px;
		margin:0 auto;
		background:url(${ctx}/static/kingkaid/images/prize/top5.png) no-repeat top center;
		}
	.top6{
		position:relative;
		width:100%;
		height:100px;
		margin:0 auto;
		background:#7cd3b1;
		}
	.gamebtn{
		width:347px;
		height:80px;
		left:39%;
		position:absolute;
		background:url(${ctx}/static/kingkaid/images/prize/gamebtnred.png) no-repeat top center;
		}
	.gamebtngray{
		width:347px;
		height:80px;
		left:39%;
		position:absolute;
		background:url(${ctx}/static/kingkaid/images/prize/gamebtngray.png) no-repeat top center;
		}
	.codebox{
	    position:fixed;
	    float:right;
	    top:300px;
	    right:4%;
	    cursor:pointer;
	    z-index:999;
	}
	.back{
	width:234px;
	height:164px;
	position:absolute;
	top:0;
	left:18%;
	background:#;
	z-index:999;
	}
</style>
</head> 
<body>
    <div class="top1">
       <a href="${ctx}/index.html"><div class="back"></div></a>
       <div class="codebox">
         <div class="code"><img src="${ctx}/static/kingkaid/images/prize/code1.png"/></div>
         <div style="height:10px;"></div>
         <div class="code"><img src="${ctx}/static/kingkaid/images/prize/code3.png"/></div>
       </div>
    </div>
    <div class="top2"></div>
    <div class="top3"></div>
    <div class="top4"></div>
    <div class="top5"></div>
    <div class="top6">
        <c:choose>
           <c:when test="${tempflag}">
              <a class="gamebtn" href="${ctx}/auth/activity/anni_activity.html"></a>
           </c:when>
           <c:otherwise>
              <div class="gamebtngray"></div>
           </c:otherwise>
        </c:choose>        
    </div>
</body>
</html>
