<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${ctx}/static/kingkaid/css/simple.pagination.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/simple.pagination.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/plugin/js/My97DatePicker/skin/WdatePicker.css"/>
<script type="text/javascript" src="${ctx}/static/plugin/js/My97DatePicker/WdatePicker.js"></script> 
<title>金开贷</title>
<script type="text/javascript">			 
	$(document).ready(function() {			 
	    $("#pager").simplePagination({	
	        url : '${ctx}/website/getPlatnews',
	        items_per_page : 10,
	        handle_data : function(dataObj,pageno) {
		    	$("#newlist_table").empty();				
	   		  	var rowContent ='<ul class="news_list">';		
 	   			for(var i = 0; i < dataObj.result.length; i ++) {
		        	rowContent=rowContent +'<li> <div class="list_style"></div>'+'<div class="list_name"></div>'	
		        	              	 	+'<a href="notice_detail.html?platid='+dataObj.result[i].platid+'">' + dataObj.result[i].platname 
 		        	              	 	+ '<div class="list_time">'+dataObj.result[i].optime+'</div>'		
		        	              	 	+'</a></li>';
		    	}
	            rowContent=rowContent +  '</ul>';
	            $("#newlist_table").append(rowContent);
		    	return true;
		    },
		    qcon_func : function() {
		    	return {
		    		startDate : $("#q_start_date").val(),
		    	    endDate : $("#q_end_date").val()
		    	};
		    }
		});
	    $("#search_code_btn").click(function() {
	    	$("#pager").trigger("setPage", 0);
	    });
	    
		$("#usermenu_jkb").addClass("user_menulist_href_hover");
		$("#menu_jkd").addClass("leftmenu_cur");
	});
</script>
</head>
 	
<body>
  <%@ include file="../common/header.jsp"%>    
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail_right">
               <div class="usercenter-title">
                  <div class="usertitle-text">平台公告</div>
                  <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
               </div>
	    		<ul class="news_list" id="newlist_table">
	    		</ul>	
               <div id="pager" style="padding:15px 0 15px 30px;"></div>
            </div>
            <div style="clear:both"></div>       
        </div>
        <div style="clear:both"></div>        
    </div>          
<%@ include file="../common/footer.jsp"%>    
</body>
</html>
