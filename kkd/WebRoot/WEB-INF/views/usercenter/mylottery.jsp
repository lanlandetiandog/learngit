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
<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/mylottery.js"></script>
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
                    <div class="usercenter-title">
                        <div class="usertitle-text">我的奖券</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                    </div>

					<input type="hidden" id="selectflag" value="0"/>
                    <ul class="lottery_search" >
                        <li>
                            <a class="lottery_search_type" id="all" value="0">全部</a>
                        </li>
                        <li>
                            <a class="lottery_search_type" id="can_use" value="1">可使用</a>
                        </li>
                        <li>
                            <a class="lottery_search_type" id="used-lottery" value="2">已使用</a>
                        </li>
                        <li>
                            <a class="lottery_search_type" id="overtime" value="3">已过期</a>
                        </li>
                    </ul>
                    
                    <div class="card_area all" id="lottery_content">
                                        
                    </div>
                    <div id="pager" style="margin-left:15px; margin-bottom:10px"></div>

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
