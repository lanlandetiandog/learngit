<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/static/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/kingkaid/css/simple.pagination.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/simple.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/project/projectrecord.js"></script>
<script type="text/javascript" src="../lib/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
 
function fn_SelectSearchType(type, value)
{
	$("#"+type).val(value);
	$("#pager").trigger("setPage", 0);
}

</script>
</head>
<body>
	<jsp:include page="../common/header.jsp" flush="true">
		<jsp:param name="f" value="1"/>
	</jsp:include>
   <div style="clear:both"></div>
   <div class="content">
        <div class="content_box">
            <div class="content_detail_right user_top_alert" style="min-height:600px;">
            <div class="recharge-top" style="padding:20px 34px">
                    <div class="recharge-title">项目记录</div>
                    <a style="float:right;color:#ea6660;font-weight:bold;line-height:28px;" href="loan_detail_page.html?loanid=${map.loanid}">返回</a>   
                </div>
                <table class="borrow-record-table">
                 <tr>
                        <td style="text-align:left"><span class="red_text">项目经理：${kkd:showPrivacy(map.tapeopername, 0)}</span></td>
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
                    <ul id="invest_list_loan" class="invest_list" style="border-top: 2px solid #e7f2f7;margin-top:50px;">
                    </ul>
                    <div id="pager" style="margin-left:15px; margin-bottom:10px"></div>
                
            <div style="clear:both"></div>       
        </div>
        <div style="clear:both"></div>        
    </div>
    </div>
    <%@ include file="../common/footer.jsp"%>
</body>
</html>
