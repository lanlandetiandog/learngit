<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/static/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
    <%@ include file="/static/common/meta.jsp" %>
    <script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
    <script type="text/javascript" src="${ctx}/static/kingkaid/js/project/loandetail.js"></script>
	<script type="text/javascript" src="${ctx}/static/plugin/js/small_slider.js"></script>
    <script type="text/javascript">
        load_retu_plan('${loanid}');
        load_bid_list('${loanid}');
    </script>
</head>

<body>
<jsp:include page="../common/header.jsp" flush="true">
    <jsp:param name="f" value="1"/>
</jsp:include>
<div style="clear:both"></div>
<div class="content">
    <div class="content_box">
        <div class="content_detail">
            <div style="margin-top:108px;" class="user_top_alert">
                <a class="top_return_btn" href="javascript:history.go(-1);">返回项目列表</a>
                <div style="float:right;margin:5px 10px 0 0;font-size:16px;" onclick="counterBox();">
                    <a><img style="margin-bottom:-10px;" src="${ctx}/static/kingkaid/images/counter.jpg"/></a>
                    <span>收益计算器</span>
                </div>
                <div style="clear:both"></div>
            </div>
            <div class="prj_detail">
                <div class="prj_detail_left">
                    <div class="detail_left_top">
                        <div class="prj_detail_name">${projectdetail.projecttitle}</div>
                        <div class="manage-loan-icon" style="margin:6px 0 0 10px;">${projectdetail.prodname}</div>
                        <c:choose>
                            <c:when test="${fn:containsIgnoreCase(projectdetail.projecttags, 2)}">
                                <div style="margin:6px 0 0 10px;" class="mobile-icon">加息标</div>
                            </c:when>
                            <c:otherwise></c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${fn:containsIgnoreCase(projectdetail.projecttags, 1)}">
                                <div style="margin:6px 0 0 10px;" class="newcomer-icon">新手标</div>
                            </c:when>
                            <c:otherwise></c:otherwise>
                        </c:choose>
                    </div>
                    <div class="prj_detail_info">
                        <div id="progress1${projectdetail.loanid}" class="cerclebox"
                             style="float:left;margin:0 20px; background-position: -${projectdetail.bidprogress*100*54}px 0px;">
                            <fmt:formatNumber type="number" value="${projectdetail.bidprogress*100}" pattern="##"/>%
                        </div>
                        <span>项目金额：<span class="red_text">${projectdetail.tcapi/10000}万</span></span>
                        <span class="v_line">|</span>
                        <c:choose>
                            <c:when test="${projectdetail.prodid == '100401'}">
                                <c:choose>
                                    <c:when test="${projectdetail.apprstate!='06' && projectdetail.apprstate!='10' && projectdetail.apprstate!='19'}">
                                        <c:choose>
                                            <c:when test="${projectdetail.btdate < 0}">
                                                <span>预期项目期限：<span class="red_text">0天</span></span>
                                            </c:when>
                                            <c:otherwise>
                                                <c:choose>
                                                    <c:when test="${projectdetail.btdate < 0}">
                                                        <span>预期项目期限：<span class="red_text">0天</span></span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span>预期项目期限：<span
                                                                class="red_text">${projectdetail.btdate}天</span></span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <span>项目期限：<span class="red_text">${projectdetail.btdate}天</span></span>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <span>项目期限：<span class="red_text">${projectdetail.tterm}个月</span></span>
                            </c:otherwise>
                        </c:choose>
                        <span class="v_line">|</span>
                        <c:choose>
                            <c:when test="${projectdetail.intadd eq '0'}">
                                <span>年利率：<span class="red_text">${projectdetail.pinterate}%</span></span>
                            </c:when>
                            <c:when test="${projectdetail.intadd eq ''}">
                                <span>年利率：<span class="red_text">${projectdetail.pinterate}%</span></span>
                            </c:when>
                            <c:otherwise>
                                <span>年利率：<span
                                        class="red_text">${projectdetail.pinterate}%+${projectdetail.intadd}%</span></span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <ul class="loan_info_list">
                        <li>
                            <c:choose>
                                <c:when test="${projectdetail.prodid == '100301'}">
                                    <span>转让方性质：<c:if test="${projectdetail.custtype == '0'}">个人</c:if><c:if
                                            test="${projectdetail.custtype == '1'}">企业</c:if></span>
                                    <span style="margin-left:50px;">回购方式： ${projectdetail.retukindname}</span>
                                </c:when>
                                <c:otherwise>
                                    <span>借款方性质：<c:if test="${projectdetail.custtype == '0'}">个人</c:if><c:if
                                            test="${projectdetail.custtype == '1'}">企业</c:if></span>
                                    <span style="margin-left:50px;">还款方式： ${projectdetail.retukindname}</span>
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <c:if test="${projectdetail.prodid != '100201' and  projectdetail.prodid != '100301' and  projectdetail.prodid != '100401'}">
                            <li>
                                <img src="${ctx}/static/kingkaid/images/and.jpg" style="margin:0 5px -3px 0"/>
                                <span>担保机构：${projectdetail.dbcustname}</span>
                            </li>
                        </c:if>


                        <li>
                            <c:choose>
                                <c:when test="${'201'==projectdetail.retukind||'202'==projectdetail.retukind||'203'==projectdetail.retukind||'301'==projectdetail.retukind}">
                                    <div>是否可转让： 可转让</div>
                                </c:when>
                                <c:otherwise>
                                    <div>是否可转让：不可转让</div>
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <li>
                            <img style="margin:0 2px -1px 0" src="${ctx}/static/kingkaid/images/time_icon1.jpg"/>
                            <span>项目发布时间：<fmt:formatDate value="${projectdetail.pubbiddate}"
                                                         pattern="yyyy年MM月dd日"/></span>

                            <img style="margin:0 2px -1px 30px" src="${ctx}/static/kingkaid/images/time_icon2.jpg"/>
                            <span>投标结束时间：<fmt:formatDate value="${projectdetail.bidenddate}"
                                                         pattern="yyyy年MM月dd日"/></span>
                        </li>
                    </ul>

                    <ul class="detail_list">
                        <li style="border-bottom: 0px solid #eee;">
                            <div class="detail_list_top detail_list_top_down">
                                <c:choose>
                                    <c:when test="${projectdetail.prodid != '100301'}">
                                        <div class="loan_detail_title">借款人信息</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="loan_detail_title">转让人信息</div>
                                    </c:otherwise>
                                </c:choose>
                                <a class="drop_down"></a>
                            </div>
                            <c:if test="${projectdetail.custtype == '0'}">
                                <div class="unit_loan_prj_detail">
                                    <div style="width:330px;float:left;">
                                        <div class="content_info_li" style="overflow:hidden;">
                                            <span class="prolist_info_title">项目经理：</span>
                                            <span class="prolist_info_detail">${kkd:showPrivacy(projectdetail.tapeopername, 0)}</span>
                                            <a href="project_record_page.html" target="_blank"><span
                                                    class="small_blue_btn">项目记录</span></a>
                                        </div>
                                        <div class="content_info_li" style="overflow:hidden;">
                                            <span class="prolist_info_title">借款人：</span>
                                            <span class="prolist_info_detail">${kkd:showPrivacy(projectdetail.custname, 0)}</span>
                                            <a href="project_record_page_one.html" target="_blank"><span
                                                    class="small_blue_btn">借款记录</span></a>
                                        </div>
                                        <div class="content_info_li" style="overflow:hidden;">
                                            <span class="prolist_info_title">年龄：</span>
                                            <span class="prolist_info_detail">${age.age}岁</span>
                                        </div>
                                        <div class="content_info_li" style="overflow:hidden;">
                                            <span class="prolist_info_title">性别：</span>
                                            <span class="prolist_info_detail">${projectdetail.sexsign}</span>
                                        </div>
                                        <div class="content_info_li" style="overflow:hidden;">
                                            <span class="prolist_info_title">工作城市：</span>
                                            <span class="prolist_info_detail">${projectdetail.workcity}</span>
                                        </div>
                                        <div class="content_info_li" style="overflow:hidden;">
                                            <span class="prolist_info_title">学历：</span>
                                            <span class="prolist_info_detail">${age.educsignname}</span>
                                        </div>
                                        <div class="content_info_li" style="overflow:hidden;">
                                            <span class="prolist_info_title">公司类别：</span>
                                            <span class="prolist_info_detail">${age.corpcategoryname}</span>
                                        </div>
                                    </div>
                                    <div style="width:270px;float:left;">
                                        <div class="content_info_li" style="overflow:hidden;">
                                            <span class="prolist_info_title">工作年限：</span>
                                            <span class="prolist_info_detail">${age.workyearmonth}年</span>
                                        </div>
                                        <div class="content_info_li" style="overflow:hidden;">
                                            <span class="prolist_info_title">是否结婚：</span>
                                            <span class="prolist_info_detail">${age.marrsignname}</span>
                                        </div>
                                        <div class="content_info_li" style="overflow:hidden;">
                                            <span class="prolist_info_title">有无购房：</span>
                                            <span class="prolist_info_detail">${projectdetail.varchar_11}</span>
                                        </div>
                                        <div class="content_info_li" style="overflow:hidden;">
                                            <span class="prolist_info_title">有无购车：</span>
                                            <span class="prolist_info_detail">${projectdetail.varchar_13}</span>
                                        </div>
                                        <div class="content_info_li" style="overflow:hidden;">
                                            <span class="prolist_info_title">有无车贷：</span>
                                            <span class="prolist_info_detail">${projectdetail.varchar_14} </span>
                                        </div>
                                        <div class="content_info_li" style="overflow:hidden;">
                                            <span class="prolist_info_title">有无房贷：</span>
                                            <span class="prolist_info_detail">${projectdetail.varchar_12}</span>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${projectdetail.custtype == '1'}">
                                <div class="unit_loan_prj_detail">
                                    <div style="width:330px;float:left;">
                                        <div class="content_info_li" style="overflow:hidden;">
                                            <span class="prolist_info_title">企业名称：</span>
                                            <span class="prolist_info_detail">${kkd:showPrivacy(projectdetail.custname, 7)}</span>
                                            <!-- <a href="project_record_page.html" target="_blank"><span class="small_blue_btn">项目记录</span></a> -->
                                            <a href="project_record_page_one.html" target="_blank"><span
                                                    class="small_blue_btn">借款记录</span></a>
                                        </div>
                                        <div class="content_info_li" style="overflow:hidden;">
                                            <span class="prolist_info_title">企业证照号：</span>
                                            <span class="prolist_info_detail">${kkd:showPrivacy(projectdetail.orgaid, 7)}</span>
                                        </div>
                                        <c:if test="${ projectdetail.prodid != '100301'}">
                                            <div class="content_info_li" style="overflow:hidden;">
                                                <span class="prolist_info_title">法人代表：</span>
                                                <span class="prolist_info_detail">${kkd:showPrivacy(projectdetail.leadcustname, 0)}</span>
                                            </div>
                                        </c:if>
                                    </div>
                                    <div style="width:270px;float:left;">
                                        <div class="content_info_li" style="overflow:hidden;">
                                            <span class="prolist_info_title">注册资本：</span>
                                            <span class="prolist_info_detail">${projectdetail.regifund}万</span>
                                        </div>
                                        <div class="content_info_li" style="overflow:hidden;">
                                            <span class="prolist_info_title">企业规模：</span>
                                            <span class="prolist_info_detail">${age.corpsizesign}</span>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </li>
                        <c:if test="${projectdetail.custtype == '1'}">
                            <li>
                                <div class="" style="overflow:hidden;padding: 0px 0px 20px 16px;">
                                    <span class="prolist_info_title">企业经营范围：</span>
                                    <span class="prolist_info_detail">${age.qyoperatescope}</span>
                                </div>
                            </li>
                        </c:if>
                        <li>
                            <div class="detail_list_top detail_list_top_down">
                                <div class="loan_detail_title">项目详情介绍</div>
                                <a class="drop_down"></a>
                            </div>
                            <div class="unit_loan_prj_detail">
                                <div class="content_info_li" style="overflow:hidden;">
                                    <span class="prolist_info_title">基本情况：</span>
                                    <span class="prolist_info_detail">${projectdetail.detailnote}</span>
                                </div>
                                <c:if test="${projectdetail.prodid == '100401'}">
                                    <div class="content_info_li" style="overflow:hidden;">
                                        <span class="prolist_info_title">约定还款日：</span>
                                        <span class="prolist_info_detail"><fmt:formatDate
                                                value="${projectdetail.enddate}" pattern="yyyy年MM月dd日"/></span>
                                    </div>
                                </c:if>
                                <c:if test="${projectdetail.prodid != '100301'}">
                                    <div class="content_info_li" style="overflow:hidden;">
                                        <span class="prolist_info_title">借款用途：</span>
                                        <span class="prolist_info_detail">${age.loanuse}</span>
                                    </div>
                                    <div class="content_info_li" style="overflow:hidden;">
                                        <span class="prolist_info_title">还款来源：</span>
                                        <span class="prolist_info_detail">${projectdetail.repafundsour}</span>
                                    </div>
                                    <div class="content_info_li" style="overflow:hidden;">
                                        <span class="prolist_info_title">征信情况：</span>
                                        <span class="prolist_info_detail">${age.creditstate}</span>
                                    </div>
                                    <c:if test="${projectdetail.prodid != '100201' and projectdetail.prodid != '100401'}">
                                        <div class="content_info_li" style="overflow:hidden;">
                                            <span class="prolist_info_title">担保方式：</span>
                                            <span class="prolist_info_detail">${age.assukind}</span>
                                        </div>
                                    </c:if>
                                    <div class="content_info_li" style="overflow:hidden;">
                                        <span class="prolist_info_title">备注：</span>
                                        <span class="prolist_info_detail">${projectdetail.loanusenote}</span>
                                    </div>
                                </c:if>
                            </div>
                        </li>
                        <li>
                            <div class="detail_list_top detail_list_top_down">
                                <div class="loan_detail_title">还款计划</div>
                                <a class="drop_down"></a>
                            </div>
                            <div class="unit_loan_prj_detail">
                                <table class="return_plan_tb" id="return_plan">
                                    <tr>
                                        <th style="width:100px;">
                                            <div class="rightline_th">预期还款时间</div>
                                        </th>
                                        <th style="width:108px;">
                                            <div class="rightline_th">应付本息（元）</div>
                                        </th>
                                        <th style="width:120px;">
                                            <div class="rightline_th">应付本金（元）</div>
                                        </th>
                                        <th style="width:120px;">
                                            <div class="rightline_th">应付利息（元）</div>
                                        </th>
                                        <th>
                                            <div>剩余本金（元）</div>
                                        </th>
                                    </tr>
                                </table>
                            </div>
                        </li>
                        <c:if test="${fn:length(outDataing) > 0}">
                            <li style="overflow:visible">
                                <div class="detail_list_top detail_list_top_down">
                                    <div class="loan_detail_title">图片资料</div>
                                    <a class="drop_down"></a>
                                </div>
                                <div class="unit_loan_prj_detail" style="padding:0;">
                                    <div id="slider" class="gallery">
                                        <c:forEach var="out" items="${outDataing}">
                                            <div class="spic unit_img">
                                                <a class="top_img" href="${out.fileurl}">
                                                    <img src="${out.fileurl}"/>
                                                </a>
                                                <div class="pic_name">${out.imagetypename}</div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                    <script src="${ctx}/static/plugin/js/zoom.slider.min.js"></script>
                                </div>
                                <div style="clear:both"></div>
                            </li>
                        </c:if>
                        <c:if test="${projectdetail.prodid != '100201' and projectdetail.prodid != '100301' and projectdetail.prodid != '100401'}">
                            <li>
                                <div class="detail_list_top detail_list_top_down">
                                    <div class="loan_detail_title">担保机构信息</div>
                                    <a class="drop_down"></a>
                                </div>
                                <div class="unit_loan_prj_detail">
                                    <div class="content_info_li" style="overflow:hidden;">
                                        <span class="prolist_info_title">公司名称：</span>
                                        <span class="prolist_info_detail">${projectdetail.dbcustname} </span>
                                        <a href="${ctx}/website/cooperation_deatil_temp.html?partnerid=${projectdetail.dbcustid}"
                                           target="_blank"><span class="small_blue_btn">担保记录</span></a>
                                    </div>
                                    <!--                                     <div class="content_info_li" style="overflow:hidden;"> -->
                                    <!--                                         <span class="prolist_info_title">经济性质：</span> -->
                                    <!--                                         <span class="prolist_info_detail">${age.posskind}</span> -->
                                    <!--                                     </div> -->
                                    <div class="content_info_li" style="overflow:hidden;">
                                        <span class="prolist_info_title">组织类别：</span>
                                        <span class="prolist_info_detail">${age.orgakind}</span>
                                    </div>
                                    <div class="content_info_li" style="overflow:hidden;">
                                        <span class="prolist_info_title">经营范围：</span>
                                        <span class="prolist_info_detail">${projectdetail.operatescope}</span>
                                    </div>
                                    <div class="content_info_li" style="overflow:hidden;">
                                        <span class="prolist_info_title">兼营：</span>
                                        <span class="prolist_info_detail">${projectdetail.operatesideline}</span>
                                    </div>
                                    <div class="content_info_li" style="overflow:hidden;">
                                        <span class="prolist_info_title">成立日期：</span>
                                        <span class="prolist_info_detail"><fmt:formatDate
                                                value="${projectdetail.comedate}" pattern="yyyy年MM月dd日"/></span>
                                    </div>
                                    <div class="content_info_li" style="overflow:hidden;">
                                        <span class="prolist_info_title">所在地：</span>
                                        <span class="prolist_info_detail">${projectdetail.localarea}</span>
                                    </div>
                                </div>
                                <div style="clear:both"></div>
                            </li>
                        </c:if>
                        <li>
                            <div class="detail_list_top detail_list_top_down">
                                <div class="loan_detail_title">投标详情</div>
                                <a class="drop_down"></a>
                            </div>
                            <div class="unit_loan_prj_detail">
                                <div style="color:#4c4c4c;margin-bottom:10px;">
                                    <span>目前投标总额：<span class="red_text">￥${projectdetail.bidamt}</span></span>
                                    <span style="margin-left:40px;">剩余投标金额：<span
                                            class="red_text">￥${projectdetail.tcapi - projectdetail.bidamt}</span></span>
                                </div>
                                <table class="tender_detail_tb" id="tender_detail">
                                    <tr>
                                        <th style="width:130px;">
                                            投标人
                                        </th>
                                        <th style="width:130px;">
                                            年利率
                                        </th>
                                        <th style="width:130px;">
                                            投标金额
                                        </th>
                                        <th>
                                            投标时间
                                        </th>
                                    </tr>
                                </table>
                                <div style="text-align:center;margin-top:10px;">
                                    <div class="blue_btn" id="more_button">展开查看更多</div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="prj_detail_right">
                    <form id="form" action="${ctx}/auth/pay/tender" method="post" target="_blank">
                        <input type="hidden" name="hidprodid" id="hidprodid" value="${projectdetail.prodid}"/>
                        <input type="hidden" name="loanid" id="loanid" value="${loanid}"/>
                        <input type="hidden" name="balance" id="balance" value="${acctInfo.bal}"/>
                        <input type="hidden" name="tenderamt" id="tenderamt" value="0"/>
                        <input type="hidden" name="maxcoinamt" id="maxcoinamt" value="${acctInfo.coinbal}"/>
                        <input type="hidden" name="coinamt" id="coinamt" value="0"/>
                        <input type="hidden" name="bcmaxcoinamt" id="bcmaxcoinamt"/>
                        <input type="hidden" name="inteaddno" id="inteaddno"/>
                        <input type="hidden" name="inteaddrate" id="inteaddrate" value="0"/>
                        <input type="hidden" name="minbidamt" id="minbidamt" value="${projectdetail.minbidamt}"/>
                        <input type="hidden" name="bidamtmult" id="bidamtmult" value="${projectdetail.bidamtmult}"/>
                        <input type="hidden" name="balamt" id="balamt"
                               value="${projectdetail.tcapi-projectdetail.bidamt}"/>
                        <input type="hidden" name="cruleamt" id="cruleamt" value="${coinRule.jkdamt}"/>
                        <input type="hidden" name="cruletype" id="cruletype" value="${coinRule.amttype}"/>
                        <input type="hidden" name="moneypicket" id="moneypicket" value=""/>
                        <shiro:authenticated>
                            <div class="mini_tender">
                                <div style="font-size:14px;margin:10px 0 10px 0;">
                                    项目剩余投资金额：<span class="red_text">${projectdetail.tcapi - projectdetail.bidamt}</span>元
                                </div>
                                <div>
                                    <input type="text" class="tender-money-input" id="amount" name="amount"
                                           placeholder="最低投资金额${projectdetail.minbidamt}"/>
                                    <div></div>
                                </div>
                                <div style="font-size:12px;margin:10px 0;">
                                    <span>账户可用余额：<label id="balance_show">${acctInfo.bal}</label> 元</span>
                                    <c:choose>
                                        <c:when test="${user_obj.memberState eq '4' || user_obj.memberState eq '5'}">
                                            <a class="blue_link" href="${ctx}/auth/cust/openpay_page.html">开通银行存管账户</a>
                                        </c:when>
                                        <c:when test="${user_obj.memberState eq '6' || user_obj.memberState eq '7'}">
                                            <a class="blue_link" href="${ctx}/auth/cust/openpay_page.html">激活银行存管账户</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a class="blue_link"
                                               href="${ctx}/auth/xabank/acct/deposit_page.html">[充值]</a>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <select style="font-size:14px;border:1px solid #bac0c4;color:#76818a;width:233px;height:30px;line-height:30px;"
                                        id="raisinte" name="raisinte">
                                    <option value="">请选择使用奖券</option>
                                    <option acttype='A' value='0'>金开币 - 0元</option>
                                    <c:forEach items="${raisintes}" var="raisinte">
                                        <option acttype=${raisinte.acttype} value="${raisinte.seqorder_list},${raisinte.value}"
                                                title="<c:if test="${raisinte.acttype eq '8'}">投资金额大于 ${raisinte.value*100}使用</c:if>">
                                            <c:if test="${raisinte.acttype eq '2'}">加息券 - ${raisinte.value}%</c:if>
                                            <c:if test="${raisinte.acttype eq '8'}">现金券 - ${raisinte.value}元</c:if>
                                            （<fmt:formatDate value="${raisinte.effectenddate}" pattern="yyyy-MM-dd"/>到期）
                                        </option>
                                    </c:forEach>
                                </select>
                                <div style="font-size:20px;margin:30px 0 10px 0;">
                                    实际支付金额：<span class="red_text" id="tenderamt_show">0</span>元
                                </div>
                                <div>您的实际支付金额等于投资金额减去所使用金开币抵扣的金额。投资金额需符合平台<a class="blue_link" target="_blank"
                                                                             href="${ctx}/website/common_qs.html">投资标准</a>
                                </div>
                            </div>
                            <div class="mini_tender_btm">
                                <c:choose>
                                    <c:when test="${projectdetail.apprstate eq '14'}">
                                        <a class="red_round_btn" id="${projectdetail.loanid}Div" href="#"></a>
                                    </c:when>
                                    <c:when test="${projectdetail.apprstate eq '1X'}">
                                        <a class="red_round_btn">自动投标中</a>
                                    </c:when>
                                    <c:when test="${projectdetail.apprstate eq '16' || projectdetail.apprstate eq '17' || projectdetail.apprstate eq '18'}">
                                        <a class="red_round_btn" style="background-color:#666;">已满标</a>
                                    </c:when>
                                    <c:when test="${projectdetail.apprstate eq '23'}">
                                        <a class="red_round_btn" style="background-color:#666;">已流标</a>
                                    </c:when>
                                    <c:when test="${projectdetail.apprstate eq '06' || projectdetail.apprstate eq '10'}">
                                        <a class="red_round_btn" style="background-color:#666;">还款中</a>
                                    </c:when>
                                    <c:when test="${projectdetail.apprstate eq '19'}">
                                        <a class="red_round_btn" style="background-color:#666;">已结清</a>
                                    </c:when>
                                    <c:when test="${user_obj.custId eq projectdetail.custid}">
                                        <a class="red_round_btn" style="background-color:#666;">确认投标</a>
                                    </c:when>
                                    <c:when test="${(projectdetail.tcapi - projectdetail.bidamt) eq '0'}">
                                        <a class="red_round_btn" style="background-color:#666;">已满标</a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${user_obj.memberState eq '4' || user_obj.memberState eq '5'}">
                                                <a class="red_round_btn" style="background-color:#666;"
                                                   href="${ctx}/auth/cust/openpay_page.html">开通银行存管账户</a>
                                            </c:when>
                                            <c:when test="${user_obj.memberState eq '6' || user_obj.memberState eq '7'}">
                                                <a class="red_round_btn" style="background-color:#666;"
                                                   href="${ctx}/auth/cust/openpay_page.html">激活银行存管账户</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a class="red_round_btn" id="tender_btn">确认投标</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </shiro:authenticated>
                        <shiro:guest>
                            <div class="login_tender" style="">
                                <div style="margin:40px 0 20px 0">
                                    还需要的金额：${projectdetail.tcapi - projectdetail.bidamt}元
                                </div>
                                <div class="red_round_btn" style="cursor: pointer;"
                                     onclick="javascript:window.location.href='${ctx}/login.html';">登录并投资
                                </div>
                            </div>
                        </shiro:guest>
                    </form>
                </div>
            </div>

        </div>
        <div style="clear:both"></div>
    </div>
    <div style="clear:both"></div>
</div>
<%@ include file="../common/footer.jsp" %>
</body>
<script type="text/javascript">
    var pubDate = new Date(${projectdetail.pubbiddate.getTime()});
    document.getElementById('${projectdetail.loanid}Div').innerHTML = addZeroIfNumberLT10(pubDate.getHours()) + ":" + addZeroIfNumberLT10(pubDate.getMinutes()) + "发布";
    function addZeroIfNumberLT10 (number) {
        return number < 10 ? '0' + number : number;
    }
</script>
</html>
