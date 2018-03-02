<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">var ctx = '${ctx}';</script>
<meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>金开贷</title>
<script type="text/javascript">
  $(document).ready(function(){
	  var infoPara;
		infoPara = {
			title:'金开贷精彩活动【金彩纷呈，开怀畅赢】,参与赢好礼，别怪我没告诉你！',
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
p{margin:0; padding:0}
.container{
	max-width:640px;
	text-align:center;
	background:#7cd3b1;
	}
.img-responsive,
 img {
  display: block;
  max-width: 100%;
  height: auto;
  text-align:center;
  margin:0 auto;
}
</style>
</head> 
<body>
         <div class="container" style="background:#f15b3a;"><img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/prize/top1.png">
         </div>
         
         <div class="container" style="background:#fff;padding:3.4em 0 2em 0;"><img  class="img-responsive"  src="${ctx}/static/kingkaid/images/market/prize/top2.png">
         </div>
         
         <div style="background:#ffc500; padding:2.13em 0 3em 0"><img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/prize/top3.png">
         </div>
         
         <div class="container" style="background:#dcf3e1;padding-bottom:6em;"><img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/prize/top4.png">
         </div>
         
         <div class="container" style="background:#81cef1;padding:3.14em 0 3.14em 0;"><img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/prize/top5.png">
         </div>
         
         <div class="container" style="background:#7cd3b1;padding:3em 0 3em 0;"><img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/prize/top6.png">
         </div>
         
         <div class="container"><img class="img-responsive" src="${ctx}/static/kingkaid/images/market/prize/top7.png"></div>
         <div class="container" style="background:#7cd3b1;padding-top:1.25em;"><img class="img-responsive" src="${ctx}/static/kingkaid/images/market/prize/prizecode.png"></div>
         
         <div class="container" style="background:#7cd3b1;"><p style="font-weight:bold; background:#7cd3b1;line-height:1.25me;">扫一扫关注官方微信</br>参与活动</br>微信号：jinkaidai</p></div>
</body>
</html>
