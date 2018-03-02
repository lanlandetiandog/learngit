<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/static/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <style>
        .divcss5 img {
            width: 177px;
            height: 92px
        }
    </style>
    <%@ include file="/static/common/meta.jsp" %>
    <meta name="baidu-site-verification" content="FRgBprBmQg"/>
    <meta name="baidu-site-verification" content="UtJiE4Ua6n"/>
    <meta name="360-site-verification" content="7102fe09ba0e85f91a5e5b8f0ec67040"/>
    <script type="text/javascript" src="${ctx}/static/kingkaid/js/project/index.js"></script>
</head>
<body>
<jsp:include page="common/header.jsp" flush="true">
    <jsp:param name="f" value="1"/>
</jsp:include>
<div style="clear:both"></div>
<div class="content">
    <div id="full-screen-slider">
        <ul id="slides">
            <c:forEach begin="0" end="4" var="indeximage" items="${indeximagelistinfo}">
                <c:choose>
                    <c:when test="${not empty indeximage.imageurl}">
                        <li style="background:url('${indeximage.filename}') no-repeat center top;cursor:pointer;" onclick="javascript:window.open('${indeximage.imageurl}','_blank')">
                    </c:when>
                    <c:otherwise>
                        <li style="background:url('${indeximage.filename}') no-repeat center top;">
                    </c:otherwise>
                </c:choose>
                <div class="banner-bottom">
                    <div class="banner-news">${indeximage.imagename}</div>
                </div>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div class="content_box">
        <div class="content_detail">
            <div class="total-count">
                <div class="total-money">
                    <img style="float:left;" src="${ctx}/static/kingkaid/images/money.png"/>
                    <div style="float:right;text-align:right;">
                        <div style="line-height:16px;">平台累计交易额</div>
                        <div class="money-num"><fmt:formatNumber pattern="#,#00.##" value="${outData.alldeal}"/></div>
                    </div>
                </div>
                <div class="payoff-money">
                    <img style="float:left;" src="${ctx}/static/kingkaid/images/money.png"/>
                    <div style="float:right;text-align:right;">
                        <div style="line-height:16px;">累计还本付息</div>
                        <div class="money-num"><fmt:formatNumber pattern="#,#00.##"
                                                                 value="${outData.allinterest}"/></div>
                    </div>
                </div>
                <shiro:authenticated>
                    <div class="login-register-auth">
                        <div style="line-height:16px;float:right;margin:4px 16px 0 0;">注册人数</div>
                        <div style="clear:both;"></div>
                        <div class="money-num"
                             style="color:#fdcfcd;font-size:16px;float:right;margin: 1px 16px 0 6px; font-size: 14px">
                            <span style='color:#fff;font-size:22px; font-family: Times New Roman'>${outData.alltoatl}</span><span
                                style='color:#fff;font-size:18px; font-family: Times New Roman'>人</span></div>
                    </div>
                    "
                </shiro:authenticated>
                <shiro:guest>
                    <div class="login-register">
                        <div style="color:#fdcfcd;font-size:14px;margin:5px 0 0px 6px;">注册人数 <span
                                style='color:#fff; font-size:16px; font-family: Times New Roman'> ${outData.alltoatl}</span>人
                        </div>
                        <div style="margin:0 10px 0 6px;">
                            <a style="float:left" href="${ctx}/member/register.html"><img
                                    src="${ctx}/static/kingkaid/images/freeregister.png"/></a>
                            <a style="float:right" href="${ctx}/login.html"><img
                                    src="${ctx}/static/kingkaid/images/login.png"/></a>
                        </div>
                    </div>
                </shiro:guest>
            </div>
            <div class="list-area">
                <div class="area-top">
                    <div class="top-content-bar">
                        <div class="unit-title">投资列表</div>
                        <div style="float:right;margin-right:30px;">
                            <!--  <a class="return-loan">周转贷</a>-->
                            <input type="hidden" id="prodid" value=""/>
                            <a class="jyd" onclick="loan('100101')">担保贷</a>
                            <a class="car-loan" onclick="loan('100102')">汽车贷</a>
                            <a class="credit-loan" onclick="loan('100201')">信用贷</a>
                            <!-- <a class="factoring-loan" onclick="loan('100301')">金保理</a> -->
                            <a class="ticket-loan" onclick="loan('100401')">商票贷</a>
                        </div>
                    </div>
                    <div style=" brder-bottom:2px solid #d8e1e5;"></div>
                </div>
                <ul class="invest_list" id="invest_list_loan">
                </ul>
                <div style="padding:15px 0;text-align:center;"><a class="more_link" href="project/invest_list_page.html">更多...</a></div>
            </div>

            <div class="list-area">
                <div class="area-top">
                    <div class="top-content-bar">
                        <div class="unit-title">新 闻</div>
                    </div>
                </div>
                <div style="padding:0 30px 30px 30px;overflow:hidden;">
                    <div class="news_center">
                        <ul class="newstab">
                            <li>
                                <div class="newstab_normal newtab_cur" id="plat-tab">平台公告</div>
                            </li>
                            <li>
                                <div class="newstab_normal" id="news_tab">新闻资讯</div>
                            </li>
                        </ul>

                        <div style="clear:both"></div>
                        <!-- 平台公告 -->
                        <div class="platform_detail">
                            <a href="${ctx}/website/notice_list.html" style="float:right;margin-top:-46px;"
                               target="_blank">
                                <img src="${ctx}/static/kingkaid/images/more_news.png"/>
                            </a>
                            <table class="platform_content_tb">
                                <c:forEach begin="0" end="4" var="fct" items="${formdatainfo}">
                                    <tr>
                                        <td style="width:420px;text-align:left">
                                            <a href="website/notice_detail.html?platid=${fct.platid}"
                                               target="_blank">${fct.platname }</a>
                                        </td>
                                        <td style="width:190px;text-align:right;color:#808080">
                                            <fmt:formatDate value="${fct.optime}" type="date" dateStyle="default"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <!-- 新闻资讯 -->
                        <div class="newscenter_detail" style="display:none;">
                            <a href="${ctx}/website/news_list.html" style="float:right;margin-top:-46px;"
                               target="_blank">
                                <img src="${ctx}/static/kingkaid/images/more_news.png"/>
                            </a>
                            <table class="platform_content_tb">
                                <c:forEach begin="0" end="4" var="fctb" items="${medialistinfo}">
                                    <tr>
                                        <td style="width:420px;text-align:left">
                                            <a href="website/news_detail.html?id=${fctb.id}"
                                               target="_blank">${fctb.mediaid }</a>
                                        </td>
                                        <td style="width:190px;text-align:right;color:#808080">
                                            <fmt:formatDate value="${fctb.optime}" type="date" dateStyle="default"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                    <div style="width:292px;float:right;">
                        <div style="overflow:hidden;margin-bottom:20px;">
                            <div class="activity_title">平台活动</div>
                            <a href="${ctx}/website/activity_list.html" target="_blank"
                               style="float:right;margin-top:8px;">
                                <img src="${ctx}/static/kingkaid/images/more_news.png"/>
                            </a>
                        </div>
                        <div>
                            <a href="website/activity_detail.html?id=${activelistinfo[0].id}" target="_blank">
                                <img src="${activelistinfo[0].imageload}" width="292" height="127"/>
                            </a>
                            <ul class="activity_list">
                                <c:forEach begin="0" end="2" var="fdd" items="${activelistinfo}">
                                    <a href="website/activity_detail.html?id=${fdd.id}"
                                       target="_blank">${fdd.activeid}</a>
                                    <br/>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <div class="list-area">
                <div class="area-top">
                    <div class="top-content-bar">
                        <div class="unit-title">合作伙伴</div>
                        <div style="float:right;margin:19px 21px 0 0;"><a href="${ctx}/website/system_cooperation.html"
                                                                          target="_blank"><img
                                src="${ctx}/static/kingkaid/images/more_news.png"/></a></div>
                    </div>
                </div>
                <div class="cooperation">
                    <ul class="cooperate-list">
                        <c:forEach begin="0" end="9" var="cl" items="${teamworklistinfo}">

                            <li>
                                <div class="divcss5">
                                    <a href="${cl.filepath}" target="_blank">
                                        <img src="${cl.logoload}"/>
                                    </a></div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

            </div>

        </div>
        <div style="clear:both"></div>
    </div>
    <div style="clear:both"></div>
</div>
<%@ include file="common/footer.jsp" %>
</body>
</html>
