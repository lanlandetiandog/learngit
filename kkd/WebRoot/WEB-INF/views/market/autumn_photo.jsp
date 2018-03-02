<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">var ctx = '${ctx}';</script>
<meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/static/kingkaid/css/marketactivity.css" rel="stylesheet" type="text/css">
<title>最美秋景随手拍</title>
<script type="text/javascript">
  $(document).ready(function(){
	  var infoPara;
		infoPara = {
			title:'金开贷精彩活动【最美秋景随手拍】,参与赢好礼，别怪我没告诉你！',
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
.container{
	padding:0;
	margin:0 auto;
	background:#fff;
	}
</style>
</style>
</head> 
<body>
<div class="container">
		<img src="${ctx}/static/kingkaid/images/market/autumnphoto/autumn-1.jpg">
		<img src="${ctx}/static/kingkaid/images/market/autumnphoto/autumn-2.jpg">
		<img src="${ctx}/static/kingkaid/images/market/autumnphoto/autumn-3.jpg">
		<img src="${ctx}/static/kingkaid/images/market/autumnphoto/autumn-4.jpg">
		<img src="${ctx}/static/kingkaid/images/market/autumnphoto/autumn-5.jpg">
		<img src="${ctx}/static/kingkaid/images/market/autumnphoto/autumn-6.jpg">
		<img src="${ctx}/static/kingkaid/images/market/autumnphoto/autumn-7.jpg">
		<img src="${ctx}/static/kingkaid/images/market/autumnphoto/autumn-8.jpg">
		<img src="${ctx}/static/kingkaid/images/market/autumnphoto/autumn-9.jpg">
</div>
</body>
</html>
