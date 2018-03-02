<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/static/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/kingkaid/css/simple.pagination.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/simple.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/jAlert.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/mall.js"></script>
<script type="text/javascript">
	
</script>
</head>
<body>
	<jsp:include page="../common/header.jsp" flush="true">
		<jsp:param name="f" value="1" />
	</jsp:include>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="usercenter_content">
                    <div class="recharge-top">
                        <div class="recharge-title">电子商城</div>
                    <!--<div class="right_href">
                            	本平台商品由百礼汇提供，相关事宜请拨打<span style="color:#eb493d;"> 400-132-1232 </span>咨询
                        </div>	-->
                    </div>

                    <ui class="mall_product_list" id="mall_gift_list">
                                        
                    </ui>
                    <div id="pager"  style="margin-left:15px; margin-bottom:10px"></div>
                    <form action="${ctx}/auth/usercenter/exchange.html" target="_self" method="post" id="exchangeform">
    					<input type="hidden" id="giftno" name="giftno" value="-1"/>
   					 </form>

                <%@ include file="usercenterleftmenu.jsp"%>
                </div>

            </div>
            <div style="clear:both"></div>         
        </div> 
        <div style="clear:both"></div>       
    </div>          
	
	<%@ include file="../common/footer.jsp"%>
</body>
</html>
