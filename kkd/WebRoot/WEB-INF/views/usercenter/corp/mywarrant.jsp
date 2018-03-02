<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/static/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/kingkaid/css/simple.pagination.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/simple.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/contlist.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/corp/mywarrant.js"></script>
</head>
 
<body>
    <jsp:include page="../../common/header.jsp" flush="true">
        <jsp:param name="f" value="1" />
    </jsp:include>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="usercenter_content">
                    <div class="usercenter-title">
                        <div class="usertitle-text">我的担保</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                    </div>               

                    <div class="jkd_bottom">
                        <ul id="my_warrant_info">
                            <li>
                                <div>授信总额</div>
                                <div class="jkd_money"><fmt:formatNumber value="${guaranteeInfo.assuamt / 10000}" pattern="0.00" />万元</div>
                            </li>
                            <li>
                                <div>在担总额</div>
                                <div class="jkd_money"><fmt:formatNumber value="${guaranteeInfo.isum / 10000}" pattern="0.00" />万元</div>
                            </li>
                            <li>
                                <div>结清总额</div>
                                <div class="jkd_money"><fmt:formatNumber value="${guaranteeInfo.fsum / 10000}" pattern="0.00" />万元</div>
                            </li>
                            <li>
                                <div></div>
                                <div class="jkd_money"></div>
                            </li>
                            <li>
                                <div></div>
                                <div class="jkd_money"></div>
                            </li>
                            <li>
                                <div>在担笔数</div>
                                <div class="jkd_money">${guaranteeInfo.icount}</div>
                            </li>
                            <li>
                                <div>结清笔数</div>
                                <div class="jkd_money">${guaranteeInfo.fcount}</div>
                            </li>
                        </ul>
                        <div class="jkd_total_money">
                            <div class="total_money_top">可用担保额度</div>
                            <div class="total_money_num"><fmt:formatNumber value="${(guaranteeInfo.assuamt - guaranteeInfo.isum) / 10000}" pattern="0.00" />万元</div>
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
                        <ul class="invest_search">
                            <li>
                                <a class="search_link cur_invest_search" id="all">全部</a>
                                <i></i>
                            </li>
                            <li>                                                           
                                <a class="search_link" id="bidding">投标中</a>
                                <i></i>
                            </li>
                            <li>
                                <a class="search_link" id="returning">还款中</a>
                                <i></i>
                            </li>
                            <li>
                                <a class="search_link" id="auditing">审核中</a>
                                <i></i>
                            </li>
                             <li>
                                <a class="search_link" id="failed">未通过</a>
                                <i></i>
                            </li>
                            <li>
                                <a class="search_link" id="finished">已结清</a>
                                <i></i>
                            </li>
                            <li>
                                <a class="search_link" id="unfounded">未成立</a>
                                <i></i>
                            </li>
                            <li>
                                <a class="search_link" id="syspay">已代偿</a>
                            </li>
                        </ul>
                        <div style="clear:both"></div>
                        <table class="poj_tb">
                            <tr>  
                                <th style="display:none"></th>                                                
                                <th style="width:130px;">项目名称</th>
                                <th style="width:110px;">投资金额</th>
                                <th style="width:132px;">利率</th>
                                <th style="width:90px;">期限</th>
                                <th style="width:115px;">剩余期数</th>
                                <th style="width:125px;" class="red_text">下期还款日</th>
                                <th style="width:90px;">状态</th>
                            </tr>
                        </table>
                        <ul class="poj_tb_list" id="warrant_list">
                        </ul>
                        <div id="pager" style="margin-bottom:10px"></div>
                    </div>
                     <%@ include file="usercenterleftmenu.jsp"%>
                </div>
            </div>
            <div style="clear:both"></div>         
        </div> 
        <div style="clear:both"></div>       
    </div>          
    <div id="FakeCryptoAgent"></div>  
    <div id="footer"></div>
   	<form action="${ctx}/auth/cont/download" target="_blank" method="post" id="downloadform">
    	<input type="hidden" id="filePath" name="filePath" value=""/>
    	<input type="hidden" id="fileName" name="fileName" value=""/>
    </form>
    <form action="${ctx}/auth/cont/continfoconfirm_page.html" method="post" id="continfoconfirm">
    	<input type="hidden" id="loanidcont" name="loanidcont" value=""/>
    	<input type="hidden" id="apprstate" name="apprstate" value=""/>
    	<input type="hidden" id="dealtype" name="dealtype" value=""/>
    	<input type="hidden" id="prodid" name="prodid" value=""/>
    </form>
    <form action="${ctx}/auth/usercenter/returnplandownload1.html" target="_blank" method="post" id="downloadreturnplanss">
    	<input type="hidden" id="loanid" name="loanid" value=""/>
    	<input type="hidden" id="apptcapi" name="apptcapi" value=""/>
        <input type="hidden" id="pinterate" name="pinterate" value=""/>
    </form> 
    <%@ include file="../../common/footer.jsp"%>
</body>
</html>
