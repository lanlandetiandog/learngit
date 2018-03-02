<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<title>金开贷</title>
<script type="text/javascript">
    $(document).ready(function(){
        $("#sysmenu_hzhb").addClass("user_menulist_href_hover");
        $("#menu_guide").addClass("leftmenu_cur");

    })
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
                        <div class="usertitle-text">合作伙伴</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                        
                    </div>
                    <div class="title_box" style="margin-top:40px;">
                        <div class="sys_cooperation_title">合作公司	 </div>
                    </div>  
                   <ul class="company_list">
                    	<c:forEach var="teamworklist" items="${teamworklist}" >
			                 <li>
	                            <div class="company_img">
	                                <a href="cooperation_deatil2.html?id=${teamworklist.id}&partnerid=${teamworklist.partnerid}" target="_blank"><img src="${teamworklist.logoload}" /></a>
	                            </div>
	                            <div class="company_name">${teamworklist.partnername}</div>
	                       	 </li>
                        </c:forEach> 
                    </ul>
		  		 
				 <c:forEach var="teamwork" items="${teamwork}" varStatus="sta">
					 	<div class="title_box">
                        	<div class="sys_cooperation_title">${teamwork.areakindname}担保机构 </div>
                    	</div> 
			               <ul class="company_list">
			                    	<c:forEach var="fd" items="${totallist[sta.index]}" varStatus="status">
						                 <li>
				                            <div class="company_img">
				                                 <a href="cooperation_deatil_temp.html?id=${fd.id}&partnerid=${fd.partnerid}" target="_blank"><img src="${fd.logoload}" /></a>  
					                        </div>  
					                        <div class="company_name">${fd.partnername}</div> 
				                       	 </li>
			                        </c:forEach> 
			                    </ul>
					</c:forEach> 
              
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
