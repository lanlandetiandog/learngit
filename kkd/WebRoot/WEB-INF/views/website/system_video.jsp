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
    $(document).ready(function(){
        $("#pager").simplePagination({	
	        url : '${ctx}/website/getSystemVideo',
	        items_per_page : 6,
	        handle_data : function(dataObj,pageno) {			 
		    	$("#newlist_table").empty();				
	   		  	var rowContent ='<ul class="news_list">';	
	   			rowContent=rowContent +'<li style="padding: 22px 0px;margin-left: -11px;">'; 
	   			 for(var i = 0; i < dataObj.result.length; i ++) {
 	   		      	rowContent=rowContent +'<div class="video_unit"><div class="video_img">'
					  +'<a href="'+dataObj.result[i].filepath+'"  target="_blank"><img src="'+dataObj.result[i].imageload +'"/></div>'
					  +'<a class="video_title" href="'+dataObj.result[i].filepath+'">'
					  +'<img src="${ctx}/static/kingkaid/images/arrow_right.jpg"/>'
             	 	  + dataObj.result[i].videoname 
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
	    
 
        $("#sysmenu_video").addClass("user_menulist_href_hover");
        $("#menu_guide").addClass("leftmenu_cur");

        var media1 = '<a class="media" href="../js/video/1.flv"></a>';
       
        $('.video_img').bind("click",function(){
            $('#myModal').prepend(media1);
            $('.media').media({ width:600, height:450});
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
                    <div class="usercenter-title" style="margin-bottom:0;">
                        <div class="usertitle-text">媒体报道</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                    </div>  
                    <div class="video_area" style="width: 100%;">
                 
                          <ul class="news_list" id="newlist_table">
	    			   </ul>
	    			   <div id="pager" style="padding:15px 0 15px 30px;"></div> 
                        <div id="myModal" class="reveal-modal">
                           <a class="close-reveal-modal">&#215;</a>
                        </div>
                        <%@ include file="../website/system_leftmenu.jsp"%> 
                    </div>
				 </div>
                </div>
            </div>
            <div style="clear:both"></div>         
        </div> 
        <div style="clear:both"></div>       
              
   <%@ include file="../common/footer.jsp"%>
    
</body>
</html>
