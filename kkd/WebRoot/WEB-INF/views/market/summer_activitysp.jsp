<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>金开贷</title>
<%@ include file="/static/common/meta.jsp"%>

<meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
  $(document).ready(function(){
	  var infoPara;
		infoPara = {
			title:'金开贷精彩活动【夏日清凉大作战】,参与赢好礼，别怪我没告诉你！',
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
	background:#f3d889;
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
span,p{margin:0; padding:0;background:#f3d889;}
.container{
	max-width:640px;
	text-align:center;
	background:#f3d889;
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
.thermometer{
		float:left;
		padding-top:5em;
		background:#f3d889;
		}
.tab-content{
	width:100%;
	margin:0 auto;
	text-align:center;
	}
.list-table{
	margin:0 auto;
	float:left;
	}
.tab-content {
    min-height: 100px;
	width:80%;
	float:left;
}
.list-table {
	font-size:1.6em;
    width: 100%;
    table-layout: fixed;
    text-align: center;
    border: 1px solid #;
    margin: 0 auto;
}
.list-table td{
	color:#333;
	}
.list-table tr {
    height:1.5em;
	font-weight:600;
    border-bottom: 1px solid #CCC;
}
.list-table thead th {
    height:1.5em;
	font-weight:400px;
}
.mname{
	font-size:1.2em;
	line-height:2.4em;
	font-weight:bold;
	color:#00a1e9;
	text-align:center;
	}
.btn1,.btn2{
	float:left;
	width:50%;
	text-align:center;
	}
.code1{
	position:absolute;
	left:8em;
	bottom:18em;
	}
</style>
</head> 
<body>
         <div class="container"><img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/sharecode/mpic1.jpg"></div>
         <div class="container" style="position:relative;">
         	<img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/sharecode/mpic2.jpg">
            <div class="code1"><img src="${ctx}/static/kingkaid/images/market/sharecode/code1.png"></div>
         </div>
         <div class="container"><img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/sharecode/mpic3.jpg"></div>
         <div class="container"><img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/sharecode/mpic4.jpg"></div>
         <div class="container">
         	<div class="mname">实时降温排名</div>
         	<div class="thermometer"><img src="${ctx}/static/kingkaid/images/market/sharecode/thermometer.png"></div>
            <div class="tab-content" id="exchangeListTemplate_div">
            	<table class="list-table">
                	<thead>
                    	<tr>
                        	<th>名次</th>
                            <th>用户名</th>
                            <th>目前降温度数</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach begin="0" end="7" var="info" items="${shareinfo.result}" varStatus="status">
                        <tr>
                        	<td>${status.count}</td>
                            <td>${kkd:showPrivacy(info.membername, 8)}</td>
                            <td>${info.sharecount}</td>
                        </tr>
                    </c:forEach> 
                    </tbody>
                </table>
            </div>
         </div>
         </div>
         <div class="container"><img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/sharecode/mpic5.jpg"></div>
         <div class="container"><img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/sharecode/mpic6.jpg"></div>
         <div class="container">
            	<a class="btn1" href="${ctx}/login.html"><img src="${ctx}/static/kingkaid/images/market/sharecode/btn1.png"></a>
            	<a class="btn2" href="${ctx}/index.html"><img src="${ctx}/static/kingkaid/images/market/sharecode/btn2.png"></a>
         </div>
         <div class="container"><img  class="img-responsive" src="${ctx}/static/kingkaid/images/market/sharecode/mpic7.jpg"></div>
</body>
</html>
