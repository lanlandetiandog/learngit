<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background:#ffefdb;">
<head>
<title>金开贷(JKD.COM)官网-国有AAA级三级等保资质社会金融服务平台</title>
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?dbe3ee2c638351c63dd926bf9797405f";
  var s = document.getElementsByTagName("script")[0];
  s.parentNode.insertBefore(hm, s);
})();
</script>
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?7aec9043b6e6b40f2626448f19d8ae3b";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
<link type="image/x-icon" href="${ctx}/static/kingkaid/images/logo.ico" rel="shortcut icon"/>
<script type="text/javascript">var ctx = '${ctx}';</script>
<title>金开贷</title>
<style type="text/css">
	.top1{
		width:100%;
		height:902px;
		margin:0 auto;
		position:relative;
		background:url(${ctx}/static/kingkaid/images/anni/anniheader.jpg) no-repeat top center;
		}
	.top2{
		width:100%;
		height:1076px;
		margin:0 auto;
		background:url(${ctx}/static/kingkaid/images/anni/annimid1.jpg) no-repeat top center;
		}
	.top3{
		width:100%;
		height:598px;
		margin:0 auto;
		background:url(${ctx}/static/kingkaid/images/anni/annimid2.jpg) no-repeat top center;
		}
	.top6{
		width:100%;
		height:606px;
		margin:0 auto;
		background:url(${ctx}/static/kingkaid/images/anni/anniend.jpg) no-repeat top center;
		}
	.gamebtn{
		width:347px;
		height:80px;
		left:39%;
		position:absolute;
		background:url(${ctx}/static/kingkaid/images/anni/anniend.jpg) no-repeat top center;
		}
	.gamebtngray{
		width:268px;
		height:70px;
		left:39%;
		margin-top: 375px;
		position:absolute;
		background:url(${ctx}/static/kingkaid/images/anni/starting.png) no-repeat top center;
		}
	.gamebtngraytemp{
		width:268px;
		height:70px;
		left:39%;
		margin-top: 375px;
		position:absolute;
		background:url(${ctx}/static/kingkaid/images/anni/waiting.png) no-repeat top center;
		}
	.codebox{
	    position:fixed;
	    float:right;
	    bottom:8%;
	    right:4%;
	    cursor:pointer;
	    z-index:9999;
	}
	.back{
		width:208px;
		height:88px;
		position:absolute;
		top:0;
		left:12%;
		z-index:999;
	}
.nav{
  position:fixed;
  top:0;
  right:0;
  z-index:99999;
}

.subnav{
  transition:all 0.5s;
  transform:translate(74px,140px);
}

.nav:hover .subnav{
  transform:translate(0px,140px);
}
</style>
</head> 
<body>
    <div class="top1">
       <a href="${ctx}/index.html"><span class="back"></span></a>
       <div class="codebox">
         <div style="height:150px;"></div>
         <div class="code"><img src="${ctx}/static/kingkaid/images/anni/code1.png"/></div>
       </div>
    </div>
    <div class="top2"></div>
    <div class="top3">
      <jsp:useBean id="now" class="java.util.Date" scope="page"></jsp:useBean>
		<a class="gamebtngraytemp"></a>
     <%--     <div class="gamebtngray"></div>	--%>
    </div>
    <div class="top6"></div>
    
 
    
</body>
</html>
