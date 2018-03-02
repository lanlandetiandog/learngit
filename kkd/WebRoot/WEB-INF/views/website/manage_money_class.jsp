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
 
<title>金开贷</title>
<style> 
 	.video_img img{width:246px; height:160px} 
</style> 
<script type="text/javascript">			 	
	$(document).ready(function() {			 	
	    $("#pager").simplePagination({	
	        url : '${ctx}/website/getManagerMoney',
	        items_per_page : 9,
	        handle_data : function(dataObj,pageno) {			 
		    	$("#newlist_table").empty();				
	   		  	var rowContent ='<ul class="news_list">';	
	   			rowContent=rowContent +'<li>'; 
	   			 for(var i = 0; i < dataObj.result.length; i ++) {
 	   		      	rowContent=rowContent +'<div class="video_unit" style="margin: 0 0 30px 20px;"><div class="video_img">'
					  +'<a href="manage_money_detail.html?seqno='+dataObj.result[i].seqno+'" target="_blank"><img src="'+dataObj.result[i].imageload +'"/></a></div>'
 					  +'<a class="video_title" href="manage_money_detail.html?seqno='+dataObj.result[i].seqno+'" target="_blank">'
					  +'<img src="${ctx}/static/kingkaid/images/arrow_right.jpg"/>'
             	 	  +'<span>' + dataObj.result[i].coursename 
              	 	  +'</span>'		
             	 	  +'</a></div>';
	   		 	}
	   			rowContent=rowContent +'</li>'; 
	            rowContent=rowContent +'</ul>';
	            $("#newlist_table").append(rowContent);
		    	return true;
		    },
		
		});
	    $("#search_code_btn").click(function() {
	    	$("#pager").trigger("setPage", 0);
	    });
	    
		$("#sysmenu_class").addClass("user_menulist_href_hover");
		$("#menu_guide").addClass("leftmenu_cur");
	});
</script>
</head>
<body>
    <%@ include file="../common/header.jsp"%> 
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="system_guide_content">
                    <div class="usercenter-title">
                        <div class="usertitle-text">理财课堂</div>
                        <div class="usertitle-img">
                       		<img src="${ctx}/static/kingkaid/images/label_right.png"/>
                        </div>
                    </div> 
                    <div><img style="width: 100%;" src="${ctx}/static/kingkaid/images/licai_top.jpg"/></div>
                    <div style="margin:35px auto;">
                    </div>
                       <ul class="news_list" id="newlist_table">
	    			   </ul>
                    <div id="pager" style="padding:15px 0 15px 30px;"></div> 
                   <%@ include file="../website/system_leftmenu.jsp"%>
                </div>
            </div>
           <div style="clear:both"></div>         
        </div> 
      <div style="clear:both"></div>       
    </div>          
  <%@ include file="../common/footer.jsp"%> 
</body>
</html>
