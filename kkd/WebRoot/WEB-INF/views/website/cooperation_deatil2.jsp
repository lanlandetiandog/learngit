<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="../css/main.css" />
<link type="text/css" rel="stylesheet"  href="../css/zoom.css"/>
<script type="text/javascript" src="../lib/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="../js/main.js"></script>
<script type="text/javascript" src="../js/small_slider.js" ></script>   
<title>金开贷</title>
<script type="text/javascript">
    $(document).ready(function(){
        $("#sysmenu_hzhb").addClass("user_menulist_href_hover");
        $("#menu_guide").addClass("leftmenu_cur");
         //图片资料轮播
        $('#slider').slider({ speed: 500 });
        
    })
</script>
</head>
 
<body>
   <%@ include file="../common/header.jsp"%>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail_right">
                <div class="recharge-top" style="padding-left:29px;">
                    <div class="recharge-title">${fd.partnername}</div>
                    <div class="right_href" style="margin-top:5px;">
                        <a style="font-size:14px;font-weight:bold;" href="system_cooperation.html">返回合作伙伴列表</a>
                    </div>
                </div>
                <div style="overflow:hidden;">
                    <div style="width:183px;float:left;padding:22px">
                        <div class="company_img" style="margin-left:15px;">
                            <img src="${fd.logoload}"/>
                        </div>
                        <a class="company_link" href="${fd.filepath}" target="_blank">${fd.filepath}</a>
                    </div>
                    <div style="width:719px;float:left;padding-top:15px">
                        <div class="company_resume_text">
                       		${fd.partnerinfo}
                        </div>
						<c:if test="${not empty fds}">
	                        <div style="margin:30px 0" class="cooperation_img_silder">
	                            <div id="slider" class="cooperation_slider gallery">
	                          	<c:forEach var="fd" items="${fds}" >
		                             <div class="unit_img">
	                                    <a href="${fd.imageload}">
	                                        <img src="${fd.imageload}"/>
	                                    </a>
	                                </div>
	                     	    </c:forEach>  
	                             </div>
	                            <script type="text/javascript" src="${ctx}/static/plugin/js/zoom.slider.min.js"></script>
	                            <div style="clear:both;"></div>
	                        </div>
                       </c:if>
                     </div>
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
