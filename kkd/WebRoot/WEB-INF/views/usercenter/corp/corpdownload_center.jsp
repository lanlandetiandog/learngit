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
<link type="text/css" rel="stylesheet" href="../css/main.css" />
<script type="text/javascript" src="../lib/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="../js/main.js"></script> 
<title>金开贷</title>
<script type="text/javascript">
    $(document).ready(function(){
        $("#usermenu_xz").addClass("user_menulist_href_hover");
        $("#menu_jkd").addClass("leftmenu_cur");
        
        //分页
        $("#pager").simplePagination({	
	        url : '${ctx}/auth/usercenter/getCorpDownloadcenter',
	        items_per_page : 8,
	        handle_data : function(dataObj,pageno) {	 
		    	$("#download_table").empty();				
	   		  	var rowContent ='<ul class="download_list">';		
	  		 	for(var i = 0; i < dataObj.result.length; i ++) {		 
		        	rowContent=rowContent  +'<li class="download_list_li"><div class="doc_name">'
		        						   + dataObj.result[i].imageloadname+'</div>'
		        						   +'<div class="download_li_right"><span class="doc_time">'	
		        						   + dataObj.result[i].optime+'</span>'
		        						   +'<a  href="'+dataObj.result[i].imageload +'" class="download_btn">'
		        						   +'</a></div></li>';
		    	}		 
	            rowContent=rowContent +  '</ul>';
	            $("#download_table").append(rowContent);
		            $(".download_list_li").hover(
		                    function(){
		                        $(this).addClass("download_list_li_cur").find(".download_btn").addClass("download_btn_hover");
		                    },
		                    function(){
		                        $(this).removeClass("download_list_li_cur").find(".download_btn").removeClass("download_btn_hover");
		                    }
		                );
		    	return true;
		    },
		    qcon_func : function() {
		    	return {
		    		startDate : $("#q_start_date").val(),
		    	    endDate : $("#q_end_date").val()
		    	};
		    }
		});
      //分页
    });
</script>
</head>
 
<body>
    <%@ include file="../../common/header.jsp"%>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="usercenter_content">
                    
                    <div class="usercenter-title">
                        <div class="usertitle-text">下载列表</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                    </div> 
            <!--         <ul class="download_list">
                       	<c:forEach  begin="0" end="9" var="odata" items="${outDataing}">
							<li class="download_list_li">
								<div class="doc_name">${odata.imageloadname}</div>
                            	<div class="download_li_right">
                                <span class="doc_time"><fmt:formatDate value="${odata.optime}"  type="date" dateStyle="default" /></span>
                               		<a href="${odata.imageload}" class="download_btn"></a>
                            	</div> 
							</li>
						</c:forEach>  
                    </ul>	 -->
                   <ul class="download_list" id="download_table">
                    </ul>	
                    <div id="pager" style="margin-left:15px; margin-bottom:10px"></div>
                   <%@ include file="../../usercenter/corp/usercenterleftmenu.jsp"%>
                </div>
            </div>
            <div style="clear:both"></div>         
        </div> 
        <div style="clear:both"></div>       
    </div>          
  <%@ include file="../../common/footer.jsp"%>
</body>
</html>
