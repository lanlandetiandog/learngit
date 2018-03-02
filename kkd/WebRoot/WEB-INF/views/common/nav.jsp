<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>金开贷</title>
<script type="text/javascript">		 	 
$(document).ready(function(){	
	    $.ajax({   				
 	        url : '${ctx}/website/getPlatnewslist',
	        type:"POST",           
       		dataType:"json",   
	        success:function(data){    
	        	$("#newlist_tables").empty();					   		
	          	var rowContent ;
	          	rowContent=	'<ul class="important-notice">';
				for(var i = 0; i < data.length; i ++) {		
	 				rowContent=rowContent + '<li><a href="${ctx}/website/notice_detail.html?platid='+data[i].platid+'" target="_blank">'
	 								      + data[i].platname +'</a></li>';
					}		 
	            rowContent=rowContent +  '</ul>';	
            $("#newlist_tables").append(rowContent);	
	    	return true;
	        }
	    });
	 
});

</script>
</head>

<body>
 <div class="leftmenu">
 	<div class="myleftmenu_top"><a href="${ctx}/"><img src="${ctx}/static/kingkaid/images/logo.png" /></a></div>
    <ul class="menulist">
        <li>
            <a class="menulist_href" id="menu_home" href="${ctx}/">首   页</a>
        </li>
        <li>
            <a class="menulist_href" id="menu_jkd" href="${ctx}/auth/usercenter/myjkd.html">我的金开贷</a>
        </li>
        <li>
            <a class="menulist_href" id="menu_lc" href="${ctx}/project/invest_list_page.html">我要理财</a>
        </li>
        <li>
            <%-- <a class="menulist_href" id="menu_loan" href="${ctx}/auth/loan/appr_loan.html">我要借款</a> --%>
            <a class="menulist_href" id="menu_loan" href="${ctx}/project/appr_loan.html">我要借款</a>
        </li>
        <li>
            <a class="menulist_href" id="menu_guide" href="${ctx}/website/company_introduce.html">关于我们</a>
        </li>
	<li>
            <a class="menulist_href" id="menu_gift" href="${ctx}/gift/mall.html">礼品商城</a>
        </li>
        <li>
            <a class="menulist_href" id="menu_publishinfo" href="${ctx}/website/info_publish.html">信息披露</a>
        </li>
    </ul>
    <div style="padding:16px 0 100% 15px;width:178px;">
        <div style="background:url(${ctx}/static/kingkaid/images/notice-bg.png);width:77px;height:19px;color:#fff;line-height:19px;padding-left:10px;">重要公告：</div>	
        <ul class="important-notice"  id="newlist_tables">
	   	</ul>	
     </div>
 </div>
 </body>
</html>