<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/kingkaid/css/simple.pagination.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/simple.pagination.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	 $("#pager").simplePagination({
		url : ctx+'/auth/usercenter/acctdetail.html?date='+new Date().getTime(),
        items_per_page : 8,
        handle_data : function(dataObj) {
	    	$("#acctdetail_table tbody").empty();
	    	for(var i = 0; i < dataObj.body.result.length; i ++) {
	    		var row = dataObj.body.result[i];
	    		var rowContent = '<tr><td>'+row.subjtime+'</td><td>'
	    		    +row.projtitle+'</td><td>'
	    		    +dataObj.annex.listtypelabel[i]+'</td>'
	    		    +(row.ioflag === 'I' ? '<td style=color:red>':'<td style=color:green>')
	    		    +(row.ioflag === 'I' ? '+':'-')
	    		    +row.amount+'</td><td>'
	    		    +row.subjbal+'</td></tr>';
	            $("#acctdetail_table tbody").append(rowContent);
	    	}
	    	return true;
	    },
	    qcon_func : function() {
	    	return {
	    		startDate : $("#q_start_date").val(),
	    	    endDate : $("#q_end_date").val(),
	    	    chanid : '1'
	    	};
	    }
	});
	 
    $(".search_code_btn").click(function() {
    	$("#pager").trigger("setPage", 0);
    });
	
   $("#usermenu_zc").addClass("user_menulist_href_hover");
   $("#menu_jkd").addClass("leftmenu_cur");
   
});
</script>

</head>
 
<body>
    <%@ include file="../common/header.jsp" %>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="usercenter_content">
                  <div id="propertyinfo">
                    <%-- <div class="usercenter-title">
                        <div class="usertitle-text">汇付天下账户资产信息</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                    </div>
                    <div class="property_top" style=" margin:20px auto;position:relative;">
                        <ul style="margin-top:30px;">
                            <li>
                                <div>资金总额</div>
                                <div class="jkd_money">&yen;<fmt:formatNumber value="${acctInfo.bal+acctInfo.fbal}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li>
                                <div>可用资金</div>
                                <div class="jkd_money">&yen;<fmt:formatNumber value="${acctInfo.bal}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li>
                                <div>冻结资金</div>
                                <div class="jkd_money">&yen;<fmt:formatNumber value="${acctInfo.fbal}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li>
                                <div>待收本金</div>
                                <div class="jkd_money">&yen;<fmt:formatNumber value="${acctInfo.dcapi}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li style="border-right:none;">
                                <div>金开币余额</div>
                                <div class="jkd_money"><fmt:formatNumber value="${acctInfo.coinbal}" pattern="#,##0.00"/></div>
                            </li>
                        </ul>
                        <div style="position:absolute;right:0;bottom:0;"><img src="${ctx}/static/kingkaid/images/asset_table_right.png" /></div>
                    </div> --%>
					     
                    
                    <div class="usercenter-title">
                        <div class="usertitle-text">汇付天下账户资金记录查询</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                        <c:if test="${fn:contains('5678', user_obj.memberState)}">
	                        <div style="float:right;margin-right:10px;">
	                            <a class="jkb-order-search" style="cursor: pointer;" onclick="javascript:window.open('${ctx}/auth/usercenter/cashall.html','_self')">汇付天下账户余额提现</a>                          
	                        </div>
                        </c:if>
                    </div>

                    <div class="search-area">
                       
                        <div style="float:left;">
                            <span class="shelf_search_title">开始时间:</span>
                            <input type="text" id="q_start_date" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" class="shelf_search_input Wdate" style="float:left;" />
                            <span class="shelf_search_title">结束时间:</span>
                            <input type="text" id="q_end_date" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" class="shelf_search_input Wdate" style="float:left;"/>
                        </div>
                        <div class="search_code_btn">查询</div>
                    </div>

                    <table class="money_order_tb" id="acctdetail_table">
                    	<thead>
	                        <tr>                                                 
	                            <th style="width:179px;">时间</th>
	                            <th style="width:136px;">借款项目</th>
	                            <th style="width:118px;">摘要</th>
	                            <th style="width:124px;">出入(元)</th>
	                            <th>余额(元)</th>
	                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                    <div id="pager" style="margin-left:15px; margin-bottom:10px"></div>
                  </div>
                               
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
