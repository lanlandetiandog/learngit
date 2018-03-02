<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/kingkaid/css/simple.pagination.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/simple.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/myinvest.js"></script>
<script type="text/javascript">
var tabtypeflag='1';
    $(document).ready(function(){
        $("#usermenu_tz").addClass("user_menulist_href_hover");
        $("#menu_jkd").addClass("leftmenu_cur");

        $(".search_link").click(function(){
            $(this).addClass("cur_invest_search").parent().siblings().find(".search_link").removeClass("cur_invest_search");
        });

        //tab切换
        $(".myinveat_tab").click(function(){
            $(this).addClass("inveat_tab_cur").siblings().removeClass("inveat_tab_cur");
            $(".invest_tb_list").hide();

            var cur_tab_id = $(this).attr("id");
            if('invest_loan'==cur_tab_id){
            	tabtypeflag='1';	
  			}else if('return_loan'==cur_tab_id){
  				tabtypeflag='2';	
  			}
            $("."+cur_tab_id).show();
        });

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
                    <div class="usercenter-title">
                        <div class="usertitle-text">我的投资</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                    </div>           

                    <div class="jkd_bottom">
                        <ul>
                            <li>
                                <div>待收本金</div>
                                <div class="jkd_money" id="rcapi">&yen;<fmt:formatNumber value="${accInfo.rcapi+0}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li>
                                <div>累计收益</div>
                                <div class="jkd_money" id="sinte">&yen;<fmt:formatNumber value="${accInfo.sinte+0}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li style="border:none;">
                                <div>累计投资</div>
                                <div class="jkd_money" id="bidamt">&yen;<fmt:formatNumber value="${accInfo.bidamt+0}" pattern="#,##0.00"/>元</div>
                            </li>
                            <li>
                                <div>累计投资笔数</div>
                                <div style="color:#808080;font-size:12px;">（投资+债权认购）</div>
                                <div class="jkd_money" id="bidcount"><fmt:formatNumber value="${accInfo.bidcount + accInfo.assigncount}" pattern="#,##0"/>笔</div>
                            </li>
                            <li>
                                <div>已转让笔数</div>
                                <div class="jkd_money" style="margin-top:16px;" id="transcount"><fmt:formatNumber value="${accInfo.transcount}" pattern="#,##0"/>笔</div>
                            </li>
                            <li style="border:none;">
                                <div>已完成项目</div>
                                <div style="color:#808080;font-size:12px;">（已转让+已结清）</div>
                                <div class="jkd_money" id="endbidcount"><fmt:formatNumber value="${accInfo.endbidcount+accInfo.transcount}" pattern="#,##0"/>笔</div>
                            </li>

                        </ul>
                        <div class="jkd_total_money">
                            <div class="total_money_top">待收本息</div>
                            <div class="total_money_num" id="ramt">&yen;<fmt:formatNumber value="${accInfo.rcapi + accInfo.rinte}" pattern="#,##0.00"/>元</div>
                        </div>
                    </div>
                    <input type="hidden" value="${workDate}" id="workdate" />
                    <div style="margin-top:58px;width:826px;margin:0 auto;">
                        <div class="myinveat_tab inveat_tab_cur" id="invest_loan">
                            投资项目列表
                        </div>
                        <div class="myinveat_tab" id="return_loan">
                            债权认购项目列表
                        </div>
                        <div style="float:right;margin:13px 0px 0 0;font-size:14px;">
                            <span style="color:#ea6660;">"下期还款日"</span>
                            <span>为借款人最后还款期限,投资人请于次日查询账户明细</span>
                        </div>
                        <div style="clear:both"></div>
                    </div>
                    <div class="invest_tb_list invest_loan">
                        <input type="hidden" id="apprstate" value=""/>
                        <input type="hidden" id="transferstate" value=""/>
                        <ul class="invest_search">
                            <li>
                                <a class="search_link cur_invest_search" value="">全部</a>
                                <i></i>
                            </li>
                            <li>                                                           
                                <a class="search_link" value="15">投标中</a>
                                <i></i>
                            </li>
                            <li>
                                <a class="search_link" value="10">还款中</a>
                                <i></i>
                            </li>
                       <!-- 
                            <li>
                                <a class="search_link" value="1">认购中</a>
                                <i></i>
                            </li>
			     
                             <li>
                                <a class="search_link" value="2A">转让中</a>
                                <i></i>
                            </li>
                            <li>
                                <a class="search_link" value="2B">已转让</a>
                                <i></i>
                            </li>
                       -->       
                            <li>
                                <a class="search_link" value="19">已结清</a>
                                <i></i>
                            </li>
                           <%-- <li>
                                <a class="search_link" value="10">未成立</a>
                            </li> --%>
                            <li style="float:right">
                                <div id="download_in" class="turn-loan-icon invest_right_btn" style="height: 18px; line-height: 18px; cursor: pointer; background-color: #5f8aca;">导出至Excel</div>
                            </li>
                       </ul>
                        <div style="clear:both"></div>
                        <table class="poj_tb">
                            <tr>                                                   
                                <th style="width:107px;">项目</th>
                                <th style="width:90px;">投资金额</th>
                                <th style="width:122px;">利率</th>
                                <th style="width:70px;">期限</th>
                                <th style="width:105px;">已得收益</th>
                                <th style="width:116px;">剩余期数/天数</th>
                                <th style="width:102px;">下期还款日</th>
                                <th style="width:80px;">状态</th>
                            </tr>
                        </table>
                        <ul class="poj_tb_list" id="poj_tb_list_1">
                           
                        </ul>
                        <div id="pager" style="margin-left:15px; margin-bottom:10px"></div>
                    </div>

                    <div class="invest_tb_list return_loan"  style="display:none">
                        <input type="hidden" id="apprstate" value=""/>
                        <ul class="invest_search">
                            <li>
                                <a class="search_link cur_invest_search" value="">全部</a>
                                <i></i>
                            </li>
                          <!--    <li>                                                           
                                <a class="search_link">投标中</a>
                                <i></i>
                            </li> -->
                            <li>
                                <a class="search_link" value="10">还款中</a>
                                <i></i>
                            </li>
                            <!--<li>
                                <a class="search_link">认购中</a>
                                <i></i>
                            </li>
                             <li>
                                <a class="search_link">转让中</a>
                                <i></i>
                            </li>
                            <li>
                                <a class="search_link">已转让</a>
                                <i></i>
                            </li>-->
                            <li>
                                <a class="search_link" value="19">已结清</a>
                                <i></i>
                            </li>
                            <!-- <li>
                                <a class="search_link" value="30">已代偿</a>
                                <i></i>
                            </li> -->
                           <%-- <li>
                                <a class="search_link">未成立</a>
                            </li> --%>
                            <li style="float:right">
                                <div id="download_ct" class="turn-loan-icon invest_right_btn" style="height: 18px; line-height: 18px; cursor: pointer; background-color: #5f8aca;">导出至Excel</div>
                            </li>
                        </ul>
                        <div style="clear:both"></div>
                        <table class="poj_tb">
                            <tr>                                                   
                                <th style="width:180px;">项目</th>
                                <!--<th style="width:107px;">原项目名称</th>-->
                                <th style="width:104px;">投资金额</th>
                                <th style="width:80px;">利率</th>
                                <th style="width:90px;">期限</th>
                                <th style="width:95px;">已得收益</th>
                                <th style="width:90px;">剩余期数/天数</th>
                                <th style="width:102px;">下期还款日</th>
                                <th style="width:70px;">状态</th>
                            </tr>
                        </table>
                        <ul class="poj_tb_list" id="poj_tb_list_2">
                            <li>
                                <table class="poj_tb">
                                    <tr>                                               
                                        <td style="width:97px;">
                                            <div class="align_left">SSCGL流动资金借款</div>
                                        </td>
                                        <td style="width:97px;">
                                            <div class="align_left">SSCGL流动资金借款第一期</div>
                                        </td>
                                        <td style="width:80px;">
                                            <div>1000000</div>
                                        </td>
                                        <td style="width:70px;">
                                            <div>8.64%</div>
                                        </td>
                                        <td style="width:60px;">
                                            <div>6个月</div>
                                        </td>
                                        <td style="width:85px;">
                                            <div>1212.02</div>
                                        </td>
                                        <td style="width:66px;">
                                            <div>6期</div>
                                        </td>
                                        <td style="width:92px;">
                                            <div>2020-03-20</div>
                                        </td>
                                        <td style="width:60px;">
                                            <div>还款中</div>
                                        </td>
                                    </tr>
                                    <tr>                                           
                                        <td colspan="9">
                                        <div class="conpany_info">
                                            <div class="return-icon">转</div>
                                            <div style="float:right">
                                                <div class="manage-loan-icon invest_right_btn" onclick="showReturnPlan()">还款计划书</div>
                                                <div class="turn-loan-icon invest_right_btn" onclick="showClaimantAgreement()">下载合同</div>
                                            </div>
                                        </div>
                                            
                                        </td>
                                    </tr>
                                </table>
                            </li>
                            <li>
                                <table class="poj_tb">
                                    <tr>                                               
                                        <td style="width:97px;">
                                            <div class="align_left">SSCGL流动资金借款</div>
                                        </td>
                                        <td style="width:97px;">
                                            <div class="align_left">SSCGL流动资金借款第一期</div>
                                        </td>
                                        <td style="width:80px;">
                                            <div>1000000</div>
                                        </td>
                                        <td style="width:70px;">
                                            <div>8.64%</div>
                                        </td>
                                        <td style="width:60px;">
                                            <div>6个月</div>
                                        </td>
                                        <td style="width:85px;">
                                            <div>1212.02</div>
                                        </td>
                                        <td style="width:66px;">
                                            <div>6期</div>
                                        </td>
                                        <td style="width:92px;">
                                            <div>2020-03-20</div>
                                        </td>
                                        <td style="width:60px;">
                                            <div>还款中</div>
                                        </td>
                                    </tr>
                                    <tr>                                           
                                        <td colspan="9">
                                        <div class="conpany_info">
                                            <div class="return-icon">转</div>
                                            <div style="float:right">
                                                <div class="manage-loan-icon invest_right_btn" onclick="showReturnPlan()">还款计划书</div>
                                                <div class="turn-loan-icon invest_right_btn" onclick="showClaimantAgreement()">下载合同</div>
                                            </div>
                                        </div>
                                            
                                        </td>
                                    </tr>
                                </table>
                            </li>
                            <li>
                                <table class="poj_tb">
                                    <tr>                                               
                                        <td style="width:97px;">
                                            <div class="align_left">SSCGL流动资金借款</div>
                                        </td>
                                        <td style="width:97px;">
                                            <div class="align_left">SSCGL流动资金借款第一期</div>
                                        </td>
                                        <td style="width:80px;">
                                            <div>1000000</div>
                                        </td>
                                        <td style="width:70px;">
                                            <div>8.64%</div>
                                        </td>
                                        <td style="width:60px;">
                                            <div>6个月</div>
                                        </td>
                                        <td style="width:85px;">
                                            <div>1212.02</div>
                                        </td>
                                        <td style="width:66px;">
                                            <div>6期</div>
                                        </td>
                                        <td style="width:92px;">
                                            <div>2020-03-20</div>
                                        </td>
                                        <td style="width:60px;">
                                            <div>还款中</div>
                                        </td>
                                    </tr>
                                    <tr>                                           
                                        <td colspan="9">
                                        <div class="conpany_info">
                                            <div class="return-icon">转</div>
                                            <div style="float:right">
                                                <div class="manage-loan-icon invest_right_btn" onclick="showReturnPlan()">还款计划书</div>                                   
                                                <div class="turn-loan-icon invest_right_btn" onclick="showClaimantAgreement()">下载合同</div>
                                            </div>
                                        </div>
                                        </td>
                                    </tr>
                                </table>
                            </li>
                        </ul>
                        <div id="pager2" style="margin-left:15px; margin-bottom:10px"></div>
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
    <form action="${ctx}/auth/usercenter/returnplandownload2.html" target="_blank" method="post" id="downloadreturnplans">
    	<input type="hidden" id="loanid" name="loanid" value=""/>
    	<input type="hidden" id="bidno" name="bidno" value=""/>
    	<input type="hidden" id="receivedate" name="receivedate" value=""/>
    	<input type="hidden" id="ptitle" name="ptitle" value=""/>
    </form>
    <%@ include file="../common/footer.jsp"%>
    
</body>
</html>
