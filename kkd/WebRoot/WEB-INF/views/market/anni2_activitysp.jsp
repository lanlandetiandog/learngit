<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">var ctx = '${ctx}';</script>
<meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>金开贷</title>
<script type="text/javascript">
  $(document).ready(function(){
	  var infoPara;
		infoPara = {
			title:'金开贷精彩活动【金开贷3周年，有"礼"更精彩】,参与赢好礼，别怪我没告诉你！',
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
         <div class="container" ><img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/annispread/header.jpg"/>
         </div>
         
         <div class="container"><img  class="img-responsive"  src="${ctx}/static/kingkaid/images/market/annispread/mid1.jpg"/>
         </div>
         
         <div class="container"><img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/annispread/mid2.jpg"/>
         </div>
         
         <div class="container"><img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/annispread/mid3.jpg"/>
         </div>
         
         <div class="container"><img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/annispread/end.jpg"/>
         </div>
         
</body>
</html>
