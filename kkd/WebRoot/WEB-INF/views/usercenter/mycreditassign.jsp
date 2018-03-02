<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/kingkaid/css/simple.pagination.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/simple.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/mycreditassign.js"></script>
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="usercenter_content">
                    <div class="usercenter-title">
                        <div class="usertitle-text">债权转让简介</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>

                    </div>
                    
                    <div class="top_big_img"><img src="${ctx}/static/kingkaid/images/zq_top.jpg" /></div>
                    <div class="invest_top">
                        <div>债权转让是平台提供给您的一项投资退出服务，投资人可将其持有的、符合一定条件的债权转让给其他投资人，实现获取现金的目的。您所转让的债权若在您发布项目后的24小时内无人认购，则您将继续持有该债权，若需转让，您需再次进行债权转让操作。</div>
                    </div>                    

                    <div class="return_total">
                        <div style="width:50%;float:left;text-align:right">
                            <span>转让中笔数</span>
                            <span style="margin:0 60px 0 20px;color:#ea6660">${rowcount}</span>
                            <span style="color:#a1a6aa">|</span>
                        </div>
                        <div style="width:50%;float:left;">               
                            <span style="margin:0 20px 0 60px;">已转让笔数</span>
                            <span style="color:#ea6660">${count}</span>
                        </div>
                    </div>
                    
                    <div class="usercenter-title">
                        <div class="usertitle-text">债权转让信息</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                        <div class="usertitle-right">请前往“<a class="usertitle-right_link" href="${ctx}/auth/usercenter/my_invest_page.html">我的投资</a>”转让现在持有的债权</div>
                    </div>
                    
                    <div class="invest_tb_list">
                    	<input type="hidden" id="sstate" value=""/>
                        <ul class="invest_search">
                            <li>
                                <a class="search_link cur_invest_search" onclick="fn_SelectSearchType('sstate','')">全部</a>
                                <i></i>
                            </li>
                             <li>
                                <a class="search_link" onclick="fn_SelectSearchType('sstate','0')">转让中</a>
                                <i></i>
                            </li>
                            <li>
                                <a class="search_link" onclick="fn_SelectSearchType('sstate','1')">已转让</a>
                                <i></i>
                            </li>
                            <li>
                                <a class="search_link" onclick="fn_SelectSearchType('sstate','2')">已过期</a>
                                <i></i>
                            </li>
                            <li>
                                <a class="search_link" onclick="fn_SelectSearchType('sstate','3')">已取消</a>
                            </li>
                        </ul>
                        <div style="clear:both"></div>
                        <table class="poj_tb">
                            <tr>                                                   
                                <th style="width:220px;">项目名称</th>
                                <th style="width:110px;">转让金额</th>
                                <th style="width:100px;">待收利息</th>
                                <th style="width:90px;">剩余期数</th>
                                <th style="width:95px;">年利率</th>
                                <th style="width:112px;">转让时间</th>
                                <th style="width:70px;">状态</th>
                            </tr>
                        </table>
                        <ul id="poj_tb_list_1" class="poj_tb_list">

                        </ul>
                        <div id="pager" style="margin-left:15px; margin-bottom:10px"></div>
                    </div>
                    <%@ include file="usercenterleftmenu.jsp"%>  
                </div>
            </div>
            <div style="clear:both"></div>         
        </div> 
        <div style="clear:both"></div>       
    </div>          
    <form action="${ctx}/auth/cont/download" target="_blank" method="post" id="downloadform">
    	<input type="hidden" id="filePath" name="filePath" value=""/>
    	<input type="hidden" id="fileName" name="fileName" value=""/>
    </form>
    <%@ include file="../common/footer.jsp"%>
</body>
</html>
