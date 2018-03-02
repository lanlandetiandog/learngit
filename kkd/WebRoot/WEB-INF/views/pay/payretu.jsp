<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/static/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/static/common/meta.jsp" %>
    <script type="text/javascript">
        function closeDlg() {
            window.opener = null;
            window.open('', '_self');
            window.close();
        }
    </script>
</head>
<body>
<div style="height:84px;width:1000px;margin:0 auto;">
    <a style="float:left;margin-top:20px;" href="${ctx}/index.html"><img src="${ctx}/static/kingkaid/images/logo.png"/></a>
    <div class="top-left"
         style="margin:20px 0 0 30px;padding-left:30px;line-height:43px;border-left:1px solid #dfdfdf;">
        <div style="float:left;color:#747474;">客服电话：400-1888-136</div>
        <div style="margin-left:40px;float:left;color:#747474;border-right:1px solid #dfdfdf;padding-right:30px;">服务时间 :
            09:00 - 17:00 (工作日)
        </div>
        <div class="weixin">
            <img src="${ctx}/static/kingkaid/images/wx.png"/>
            <div class="winxin_hover"><img src="${ctx}/static/kingkaid/images/kingweixin.png"/></div>
        </div>
        <div class="webo">
            <img src="${ctx}/static/kingkaid/images/wb.png"/>
            <div class="webo_hover">
                <div>点击关注新浪微博</div>
                <div><a>@金开贷</a></div>
            </div>
        </div>
        <div style="float:left;margin:11px 15px 0 5px;">
            <a><img src="${ctx}/static/kingkaid/images/app-download.png"/></a>
        </div>
    </div>
</div>

<div class="content" style="overflow:hidden;">
    <div class="result_content">
        <div class="chargebox">
            <div class="chargeboxtop" style="height:62px;">
                <div style="float:left; margin:10px 8px 0;">
                    <c:choose>
                        <c:when test="${respCode eq 'S'}">
                            <img src="${ctx}/static/kingkaid/images/chargebig_success_icon.png"/>
                        </c:when>
                        <c:otherwise>
                            <img src="${ctx}/static/kingkaid/images/wrong_warning.png"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <span>
                    <p style="line-height:62px;font-size:32px;color:#666;float:left;display:block;">${payOption.desc}${respCode eq 'S' ? "成功" : "失败" }</p>
                </span>
                <div style="clear:both;"></div>
                <ul class="chargeboxul">
                    <c:choose>
                        <c:when test="${respCode eq 'S'}">
                            <c:forEach var="s" items="${payOption.suggs}">
                                <li><p>${s}</p></li>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <li><p>非常抱歉，操作失败</p></li>
                            <li><p>失败原因：${respDesc}</p></li>
                            <li><p>如有疑问，请拨打客服电话400-1888-136</p></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
        <div style="clear:both"></div>
        <div class="retunbtn" onclick="closeDlg()">关闭当前页面</div>
    </div>
</div>
<div style="clear:both"></div>
<div style="background-color:#4a545c;color:#fff;height:100px;">
    <div style="height:42px;width:1000px;margin:0 auto;line-height:25px;font-family:'SimSun';padding-top:20px;">
        <div style="float:left">
            <div>陕西金开贷金融服务有限公司 陕ICP备 13007681号</div>
            <div>@ 2014 金开贷 All rights reserved</div>
        </div>
        <div style="float: right">
            <a href="https://ss.knet.cn/verifyseal.dll?sn=e14050861010048655416p000000&amp;tp=icon3&amp;ct=df&amp;a=1&amp;pa=0.9496854659079472"
               target="blank_">
                <img style="margin-right: 30px;" src="${ctx}/static/kingkaid/images/gs1.jpg"/>
            </a>
            <a href="http://117.22.252.216:7001/wljgweb/bscx.do?method=hddoc&amp;id=61000000005374" target="blank_">
                <img src="${ctx}/static/kingkaid/images/gs2.jpg"/>
            </a>
            <a href="https://trustsealinfo.verisign.com/splash?form_file=fdf/splash.fdf&amp;dn=www.kingkaid.com&amp;lang=zh_cn"
               target="blank_" style="margin-left: 18px;">
                <img src="${ctx}/static/kingkaid/images/vseal.jpg"/>
            </a>
        </div>
    </div>
</div>

</body>
</html>