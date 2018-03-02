<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
<link type="image/x-icon" href="${ctx}/static/kingkaid/images/favicon.ico" rel="shortcut icon"/>
<title>金开贷</title>
<style type="text/css">
.bpic1,.bpic2,.bpic3,.bpic4,.bpic5,.bpic6{
		height:600px;
		text-align:center;
		background-size:contain;
		margin:0 auto;
		}
.bpic4{
		height:500px;
		text-align:center;
		background-size:contain;
		margin:0 auto;
		}
	.bpic1{
		background: url(${ctx}/static/kingkaid/images/sharecode/bpic1.jpg) no-repeat top center;
		}
	.bpic2{
		background:url(${ctx}/static/kingkaid/images/sharecode/bpic2.jpg) no-repeat top center;
		}
	.bpic3{
		background:url(${ctx}/static/kingkaid/images/sharecode/bpic3.jpg) no-repeat top center;
		}
	.bpic4{
		background:url(${ctx}/static/kingkaid/images/sharecode/bpic4.jpg) no-repeat top center;
		}
	.bpic5{
		background:url(${ctx}/static/kingkaid/images/sharecode/bpic5.jpg) no-repeat top center;
		}
	.bpic6{
		height:665px;
		text-align:center;
		background-size:contain;
		margin:0 auto;
		background:url(${ctx}/static/kingkaid/images/sharecode/bpic6.jpg) no-repeat top center;
		}
.tab-content{
	width:270px;
	margin:0 auto;
	text-align:center;
	}
.list-table{
	margin:0 auto;
	}
.tab-content {
    min-height: 100px;
}
.list-table {
	padding-top:30px;
    width: 380px;
    table-layout: fixed;
    text-align: center;
    border: 1px solid #;
    margin: 0 auto;
}
.list-table td{
	color:#333;
	}
.list-table tr {
    height:20px;
	font-weight:600;
    border-bottom: 1px solid #CCC;
}
.list-table thead th {
    height:20px;
	font-weight:400px;
}
.tab-content-wrap-account{
	display:block;
	width:778px;
	margin-bottom:40px;
	float:left;
	}

</style>
</head> 
<body>
    <div class="bpic1"></div>
    <div class="bpic2"></div>
    <div class="bpic3"></div>
    <div class="bpic4"></div>
    <div class="bpic5">
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
    <div class="bpic6"></div>
</body>
</html>
