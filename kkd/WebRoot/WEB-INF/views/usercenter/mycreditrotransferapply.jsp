<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/kingkaid/css/simple.pagination.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/simple.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/mycreditrotransferapply.js"></script>
<script type="text/javascript"></script>

</head>
 
<body>
    <%@ include file="../common/header.jsp" %>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="usercenter_content">
                    <div class="usercenter-title">
                        <div class="usertitle-text">${investdetail.projecttitle}</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                        <div class="right_href" style="font-weight:bold;line-height:46px;margin-right:30px;">
                            <a href="my_invest_page.html">我的投资</a>
                        </div>
                    </div>
                    <div style="padding-bottom:30px;border-bottom:1px solid #eaeced;overflow:hidden;margin-bottom:30px;">
                        <ul class="borrow_list">
                            <li>
                                <div>项目金额</div>
                                <div class="jkd_money">${investdetail.apptcapi}元</div>
                                <input type="hidden" id="contno" value="${investdetail.contno}"/>
                                <input type="hidden" name="loanid" id="loanid" value="${investdetail.loanid}"/>
                            </li>
                            <li>
                                <div>项目期限</div>
                                <div class="jkd_money">${investdetail.apptterm}个月</div>
                            </li>
                            <li>
                                <div>项目利率</div>
                                <div class="jkd_money">${investdetail.fee}%</div>
                            </li>
                            <li style="border:none;">
                                <div>还款方式</div>
                                <div class="jkd_money">${investdetail.retukindname}</div>
                            </li>
                            <li>
                                <div>担保机构</div>
                                <div class="jkd_money">${investdetail.custname}</div>
                            </li>
                            <li>
                                <div>剩余期数</div>
                                <div class="jkd_money">${investdetail.overpluscount}个月</div>
                            </li>
                            <li>
                                <div>原始投资</div>
                                <div class="jkd_money">${investdetail.conttotalamt/10000}万</div>
                                <input id="hidconttotalamt" type="hidden" value="${investdetail.conttotalamt}"/>
                            </li>

                        </ul>
                    </div>
                   

                    <ul class="logistics-info">
                        <li>
                            <div class="list_info_left">
                                <span>转让本金</span>
                                <span class="quit">
                                    <img src="${ctx}/static/kingkaid/images/quit.png"/>
                                    <span class="quit_content">转让债权的本金金额</span>
                                </span>
                                <span>：</span>
                            </div>
                            <!--<span class="list_info_right">+  ${investdetail.profitamt + investdetail.withoutamt} 元（-->
                                <span class="list_info_right" style="margin-top:6px;"> ${investdetail.conttotalamt} 元
                                <input id="dsbj" type="hidden" value="<fmt:formatNumber value="${investdetail.conttotalamt}" pattern="##"/>" />
                               <!--  <span>
                                    <span>待收利息</span>
                                    <span class="quit">
                                        <img src="${ctx}/static/kingkaid/images/quit.png"/>
                                        <span class="quit_content">提示内容提示内容提示内容提示内容提示内容</span>
                                    </span>
                                    <span>：</span>
                                </span>
                                ${investdetail.withoutamt}元） -->
                            </span>
                            <div style="clear:both;"></div>
                        </li>
                        <li>
                            <div class="list_info_left">
                                <span>待收利息</span>
                                <span class="quit">
                                    <img src="${ctx}/static/kingkaid/images/quit.png"/>
                                    <span class="quit_content">所转让债权的所有未付利息</span>
                                </span>
                                <span>：</span>
                            </div>
                            <span class="list_info_right" style="margin-top:6px;">
                               <span><fmt:formatNumber value="${investdetail.withoutamt}" pattern="#,##0.00"/></span> 元 
                               <input id="dslx" type="hidden" value="${investdetail.withoutamt}" pattern="#,##0.00" />
                               <!--<span>
                                    <span>（待收利息</span>
                                    <span class="quit">
                                        <img src="${ctx}/static/kingkaid/images/quit.png"/>
                                        <span class="quit_content">所转让债权的所有未付利息</span>
                                    </span>
                                    <span>：</span> 
                                </span>
                                 <fmt:formatNumber value="${investdetail.withoutamt-investdetail.bal}" pattern="#,##0.00"/>元）
                               	 <fmt:formatNumber value="${investdetail.withoutamt}" pattern="#,##0.00"/>元）  -->
                            </span>
                            <div style="clear:both;"></div>
                        </li>
                        
                        <li>
                            <div class="list_info_left">
                                <span>&nbsp;&nbsp;&nbsp;折让金</span>
                                <span class="quit">
                                    <img src="${ctx}/static/kingkaid/images/quit.png"/>
                                    <span class="quit_content">转让债权时的让利金额，折让金越高，最终转让价格越低。折让金最小值为0，最大值为转让本金的5%。</span>
                                </span>
                                <span>：</span>
                            </div>
                            <span class="list_info_right">
                                -
                                <input id="txtzrj" type="text" onchange="javascript:fn_ComputeTransferPrice()" value="0" style="height:26px;border:1px solid #bac0c4;line-height:26px;width:80px;" />元(
                            	<span>
                                    <span>最大允许折让金：</span>
                                </span>
                                <input type="hidden" id="hidzdzrj" value="${investdetail.conttotalamt*0.05}" pattern="#,##0.00"/>
                                <fmt:formatNumber value="${investdetail.conttotalamt*0.05}" pattern="#,##0.00"/>元）
                            </span>
                            <div style="clear:both;"></div>
                        </li>
                        <li>
                            <div class="list_info_left">
                                <span>转让价格</span>
                                <span class="quit">
                                    <img src="${ctx}/static/kingkaid/images/quit.png"/>
                                    <span class="quit_content">转让价格=转让本金-折让金。</span>
                                </span>
                                <span>：</span>
                            </div>
                           <span class="list_info_right" style="margin-top:6px;">
                                = <span id="spanTransferPrice"><fmt:formatNumber value="${investdetail.conttotalamt}" pattern="##"/></span> 元 
                              <!--   <span>
                                    <span>（公允价值</span>
                                    <span class="quit">
                                        <img src="${ctx}/static/kingkaid/images/quit.png"/>
                                        <span class="quit_content">截至债权转让发起之日被转让债权的现金价值，包括债权的本金价值和应收利息。</span>
                                    </span>
                                    <span>：</span> 
                                </span>
                                <fmt:formatNumber value="${fairValues}" pattern="##"/>元）-->
                               	<input id="hidfairvalue" type="hidden" value="<fmt:formatNumber value="${fairValues}" pattern="##"/>" />
                            </span>
                            <div style="clear:both;"></div>
                        </li>
                        <li>
                            <div class="list_info_left">
                                <span>&nbsp;&nbsp;&nbsp;服务费</span>
                                <span class="quit">
                                    <img src="${ctx}/static/kingkaid/images/quit.png"/>
                                    <span class="quit_content">投资者成功转让债权，收取该笔债权转让本金0.5%的手续费，若不成功则免费。</span>
                                </span>
                                <span>：</span>
                            </div>
                            <span class="list_info_right" style="margin-top:6px;">
                            	- <span id="spanservicefee"></span> 元
                            	<c:if test="${feeamtkind==2}">（服务费率：${feevalue}%）</c:if>
                            	<c:if test="${feeamtkind==1}">（服务费：${feevalue}元）</c:if>
                            	<input type="hidden" value="${feevalue}" id="hidfeevalue"/>
                            	<input type="hidden" value="${feeamtkind}" id="hidfeeamtkind"/>
                            </span>
                            <div style="clear:both;"></div>
                        </li>
                        
                        <li>
                            <div class="list_info_left">
                                <span style="font-weight:bold">到账金额</span>
                                <span class="quit">
                                    <img src="${ctx}/static/kingkaid/images/quit.png"/>
                                    <span class="quit_content">到账金额=转让价格-服务费。</span>
                                </span>
                                <span>：</span>
                            </div>
                           <span class="list_info_right" style="margin-top:6px;">
                                = <span id="lastprice" value="0" style="color:#e04d41;font-weight:bold;" ></span> 元 
                            </span>
                            <div style="clear:both;"></div>
                        </li>
                       
                    </ul>

                    <div style="background-color:#f6f6f6;padding:28px 60px;color:#666;overflow:hidden;line-height:30px;text-align:center">
                        <button style="cursor:pointer" class="blue-btn" onclick="javascript:fn_SubmitCreditTransfer()">确认转让</button>
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
