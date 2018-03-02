<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<%@ include file="/static/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/kingkaid/css/simple.pagination.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/simple.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/contlist.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/myborrow.js"></script>

</head>
 
<body>
    <%@ include file="../common/header.jsp" %>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="usercenter_content">
                    <div class="usercenter-title">
                        <div class="usertitle-text">我的借款</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                    </div>               

                    <div class="jkd_bottom">
                        <ul>
                            <li>
                                <div>待还本金</div>
                                <div class="jkd_money" id="scapi">&yen;<fmt:formatNumber value="${accInfo.scapi+0}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li>
                                <div>待还本息</div>
                                <div class="jkd_money" id="scapinte">&yen;<fmt:formatNumber value="${accInfo.scapi + accInfo.sinte}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li style="border:none;">
                                <div>逾期金额</div>
                                <div class="jkd_money" id="soveramt">&yen;<fmt:formatNumber value="${accInfo.soveramt+0}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li>
                                <div>待还笔数</div>
                                <div class="jkd_money" id="overcount">${accInfo.overcount+0}笔</div>
                            </li>
                            <li>
                                <div>结清笔数</div>
                                <div class="jkd_money" id="payoffcount">${accInfo.payoffcount+0}笔</div>
                            </li>              
							 <li>
                                <div>应付代偿款</div>
                                <div class="jkd_money" id="compcapi">&yen;<fmt:formatNumber value="${accInfo.compcapi+0}" pattern="#,##0.00"/>元</div>
                            </li>   
                        </ul>
                        <div class="jkd_total_money">
                            <div class="total_money_top">本期待还</div>
                            <div class="total_money_num" id="samt">&yen;<fmt:formatNumber value="${accInfo.samt+0}" pattern="#,##0.00"/>元</div>
                        </div>
                    </div>
                    
                
                    <div class="usercenter-title">
                        <div class="usertitle-text">借款信息</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                        <div style="float:right;margin:13px 30px 0 0;font-size:14px;">
                            <span style="color:#ea6660;">"下期还款日"</span>
                            <span>为借款人最后还款期限,投资人请于次日查询账户明细</span>
                        </div>
                    </div>  
                    <div class="invest_tb_list invest_loan">
                    
                    <input type="hidden" id="selectflag" value=""/>

                        <ul class="invest_search">
                            <li>
                                <a class="search_link cur_invest_search" value="">全部</a>
                                <i></i>
                            </li>
                            <li>                                                           
                                <a class="search_link" value="15,1X">投标中</a>
                                <i></i>
                            </li>
                            <li>
                                <a class="search_link" value="10">还款中</a>
                                <i></i>
                            </li>
                            <li>
                                <a class="search_link" value="01,02,03,04,05,07,08,09,11,12,13,14">审核中</a>
                                <i></i>
                            </li>
                             <li>
                                <a class="search_link" value="20,21,22,23,24,25">未通过</a>
                                <i></i>
                            </li>
                            <li>
                                <a class="search_link" value="19">已结清</a>
                                <i></i>
                            </li>
                            <li>
                                <a class="search_link" value="00">未成立</a>
                            </li>
                        </ul>
                        <div style="clear:both"></div>
                        <table class="poj_tb">
                            <tr>                                                  
                                <th style="width:107px;">项目名称</th>
                                <th style="width:90px;">借款金额</th>
                                <th style="width:122px;">年化利率</th>
                                <th style="width:70px;">借款期限</th>
                                <th style="width:105px;">下期还款日</th>
                                <th style="width:116px;">本期待还</th>
                                <th style="width:102px;">剩余期数/天数</th>
                                <th style="width:80px;">状态</th>
                            </tr>
                        </table>
                        <ul class="poj_tb_list">
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
    <div id="FakeCryptoAgent"></div> 
     <form action="${ctx}/auth/cont/download" target="_blank" method="post" id="downloadform">
    	<input type="hidden" id="filePath" name="filePath" value=""/>
    	<input type="hidden" id="fileName" name="fileName" value=""/>
    </form>            
    <form action="${ctx}/auth/usercenter/returnplandownload.html" target="_blank" method="post" id="downloadreturnplans">
    	<input type="hidden" id="loanid" name="loanid" value=""/>
    </form>  
    <form action="${ctx}/auth/cont/continfoconfirm_page.html" method="post" id="continfoconfirm">
    	<input type="hidden" id="loanidcont" name="loanidcont" value=""/>
    	<input type="hidden" id="apprstate" name="apprstate" value=""/>
    	<input type="hidden" id="dealtype" name="dealtype" value=""/>
    	<input type="hidden" id="prodid" name="prodid" value=""/>
    </form>
    <%@ include file="../common/footer.jsp"%>
    <input type="hidden" id="ctlurlValue" value="${ctlurl}"/>
</body>
</html>
