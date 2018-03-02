<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="../css/main.css" />
<script type="text/javascript" src="../lib/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="../js/main.js"></script> 
<script type="text/javascript" src="../js/jquery.circliful.js"></script>  
<title>金开贷</title>
<script type="text/javascript">
    $(document).ready(function(){
        
    })
</script>
</head>
 
<body>
    <%@ include file="../common/header.jsp"%> 
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail_right">
                <div class="usercenter-title">
                    <div class="usertitle-text">理财课堂</div>
                    <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                    <a href="manage_money_class.html" style="float:right;margin:13px 30px 0 0;font-size:14px;color:#ea6660;">   
                        返回列表&gt;&gt;
                    </a>
                </div>
                <div class="activity_detail">
                    <div class="act_detail_title">${courseselect.coursename}</div>
                    <div class="act_detail_info">
                         <span>发布时间：<fmt:formatDate value="${courseselect.optime}" pattern="yyyy年MM月dd日" /></span>
                    </div>
                    <div>${courseselect.courseinfo}</div> 
                </div>
                <div class="act_detail_bottom">
                    <a href="manage_money_detail.html?seqno=${courseselect.seqno_two}">
                        <span class="red_text">上一篇：</span>
                        <span>${courseselect.coursename_two}</span> 
                    </a>
                    <a href="manage_money_detail.html?seqno=${courseselect.seqno_one}">
                        <span class="red_text">下一篇：</span>
                        <span>${courseselect.coursename_one}</span>
                    </a>
                </div>
                
            </div>
            <div style="clear:both"></div>       
        </div>
        <div style="clear:both"></div>        
    </div>          
   <%@ include file="../common/footer.jsp"%> 
   
</body>
</html>
