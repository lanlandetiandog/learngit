<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/static/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/kingkaid/css/simple.pagination.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/plugin/js/radialIndicator.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/simple.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/website/cooperation_deatil_dan.js"></script> 

<title>金开贷</title> 
<script type="text/javascript">
    $(document).ready(function(){
        $("#sysmenu_hzhb").addClass("user_menulist_href_hover");
        $("#menu_guide").addClass("leftmenu_cur");
         //图片资料轮播
        $('#slider').slider({ speed: 500 });
        
    });
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
                     <div style="width:200px;float:left;padding:22px">
                        <div class="company_img" style="margin-left:15px;">
                           <img src="${fd.logoload}" />	 
                        </div>
                        <a class="company_link" href="${fd.filepath}" target="blank_">${fd.filepath}</a>
                    </div>
                    <div style="width:719px;float:left;padding-top:15px">
                        <table class="tender_info_tb">
		                    <tr>
		                        <td style="text-align:left"><span class="red_text">公司法人：${fd.leadcustname}</span></td>
		                        <td><span>投标中</span></td>
		                        <td><span>还款中</span></td>
		                        <td><span>已结清</span></td>
		                    </tr>
		                    <tr>
		                        <td></td>
		                        <td><span class="blue-text">${outDataing.bidamt}</span> 元</td>
		                        <td><span class="blue-text">${outDataing.retamt}</span> 元</td>
		                        <td><span class="blue-text">${outDataing.endamt}</span> 元</td>
		                    </tr>
		                    <tr>
		                        <td></td>
		                        <td><span class="blue-text">${outDataing.bidcnt}</span> 笔</td>
		                        <td><span class="blue-text">${outDataing.retcnt}</span> 笔</td>
		                        <td><span class="blue-text">${outDataing.endcnt}</span> 笔</td>
		                    </tr>
                        </table>
                        
                        <div class="company_resume_text">
                       		${fd.partnerinfo}
                        </div>
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
                             <script src="${ctx}/static/plugin/js/zoom.slider.min.js"></script>
                            <div style="clear:both;"></div>
                        </div>
                    </div>
                </div>
                   
                <!--担保项目列表 start -->
                <div class="project_list">
                    <div style="color:#5c88c9;font-size:20px;font-weight:bold;padding-left:29px;margin:15px 0;">担保记录</div>
                    <ul id="invest_list_loan" class="invest_list" style="border-top: 2px solid #e7f2f7;">
                      </ul> 
                <div id="pager" style="margin-left:15px; margin-bottom:10px"></div>
                </div>
          		<!--担保项目列表 end-->
            </div>
            <div style="clear:both"></div>       
        </div>
        <div style="clear:both"></div>        
    </div>          
   <input type="hidden" id="partnerid"  value="${fd.partnerid}"/>  
   <%@ include file="../common/footer.jsp"%> 
   
</body>
</html>
