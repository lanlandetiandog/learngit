<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/static/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/kingkaid/css/simple.pagination.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/kingkaid/css/malloder.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/simple.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/gift/mall.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
	  	$("#usermenu_dzsc").addClass("user_menulist_href_hover");
        $("#menu_gift").addClass("leftmenu_cur");
		$(".user_leftmenu").css("display","none");
	})
</script>
<style type="text/css">
.user_leftmenu{
	display:none;
	}
.usercenter_content{
	width:980px;
	}
.mall_product_list li {
    width: 165px;
    margin: 0 0 30px 14px;
    padding:10px 30px;
}
.userremind {
   margin-left: 0px;
   width: 978px;
}
</style>
</head>
<body>
   	<%@ include file="../common/header.jsp"%> 
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="usercenter_content">
                    <div class="recharge-top">
                        <div class="recharge-title">礼品商城</div>
                        <div style="float:bottom;margin: 6px 0px 0px 100px; font-size: 15px; color:#ea6660;">温馨提示：所有商品由京东商城提供销售配送和售后服务
                         <a href="${ctx}/auth/usercenter/mallorder.html" class="right_href">
                            <p style="color:#5c88c9;line-height:26px;">我的订单>></p>
                        </a></div>
                    </div>
                    <ul class="mall_product_list" id="mall_product_list">
	    			</ul>
	    			<div style="clear:both;"></div>
                    <div id="pager" style="padding:15px 0 45px 30px;"></div> 
 			 	<%-- 	<%@ include file="usercenterleftmenu.jsp"%> --%>
                </div>
            </div>
            <div style="clear:both"></div>         
        </div> 
        <div style="clear:both"></div>       
    </div>     
    <form action="${ctx}/auth/usercenter/exchange.html" target="_self" method="post" id="exchangeform">
    					<input type="hidden" id="giftno" name="giftno" value="-1"/>
   	</form>     
	<%@ include file="../common/footer.jsp"%>
</body>
</html>
