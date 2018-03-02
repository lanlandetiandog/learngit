<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${ctx}/static/kingkaid/css/simple.pagination.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/simple.pagination.js"></script> 
<title>金开贷</title>
<script type="text/javascript">
$(document).ready(function() {		
	$("#sysmenu_zzry").addClass("user_menulist_href_hover");
    $("#menu_guide").addClass("leftmenu_cur");
     
    $("#pager").simplePagination({	
        url : '${ctx}/website/getSystemHonour',
        items_per_page : 8,
        handle_data : function(dataObj,pageno) {		 
	    	$("#newlist_table").empty();				
   		  	var rowContent ='<ul class="news_list">';
	   			for(var i = 0; i < dataObj.result.length; i ++) {
	        	rowContent=rowContent +'<div class="unit_img unit_honour"> '
	        			 			  +'	<a class="honour_img" href="'+dataObj.result[i].imageload+'" target="_blank">'
	        	              	  	  +'		<img src="'+dataObj.result[i].imageload+'" />'
	        	              	 	  +'	</a>'
	        	              	 	  +'<div class="honour_name">'+dataObj.result[i].honourname+'</div>'
	        	              	 	  +'</div>';
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
                        <div class="usertitle-text">资质荣誉</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                    </div>  
             		<div class="honour_list gallery">
		                 <ul class="news_list" id="newlist_table">
	    			   </ul>
	    			   <div style="clear:both"></div>       
	    			   <div id="pager" style="padding:15px 0 15px 30px;"></div>  
                        <%@ include file="../website/system_leftmenu.jsp"%> 
				</div> 
            </div>
        </div> 
        <div style="clear:both"></div>       
    </div>          
   <%@ include file="../common/footer.jsp"%>
   </div>  
</body>
</html>
