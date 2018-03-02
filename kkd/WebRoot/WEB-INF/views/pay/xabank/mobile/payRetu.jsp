<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/static/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<meta content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0" name="viewport" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<title>金开贷</title>
<link href="${ctx}/static/kingkaid/css/msj_m.css" rel="stylesheet"/>
<link href="${ctx}/static/kingkaid/css/ss_base2.css" rel="stylesheet"/>
<script type="text/javascript">var ctx = '${ctx}';</script>
</head>

<body>
<div class="m_wrap" id="m_wrap">

    <div class="m_content_main" id="m_content_main">
        <article>
            <div class="m_con_topimg">
                <div class="chargebox" style="margin:0 auto;text-align:center;">
                    <div class="chargeboxtop" style="height:44px;">
                        <div class="chargeboxtopimg">
                            <c:choose>
                                <c:when test="${transtat eq '0'}">
                                    <img src="${ctx}/static/kingkaid/images/mright_bt.png"/>
                                </c:when>
                                <c:when test="${transtat eq '1'}">
                                    <img src="${ctx}/static/kingkaid/images/mwait.png"/>
                                </c:when>
                                <c:otherwise>
                                    <img src="${ctx}/static/kingkaid/images/mwrong_warning.png"/>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <span>
                            <c:choose>
                                <c:when test="${transtat eq '0'}">
                                    <p style="font-size:32px;color:#666;display:inline-block;line-height:44px;">成功</p>
                                </c:when>
                                <c:when test="${transtat eq '1'}">
                                    <p style="font-size:32px;color:#666;display:inline-block;line-height:44px;">处理中</p>
                                </c:when>
                                <c:otherwise>
                                    <p style="font-size:32px;color:#666;display:inline-block;line-height:44px;">失败</p>
                                </c:otherwise>
                            </c:choose>
                        </span>
                        <div style="clear:both;"></div>
                    </div>
                </div>
            </div>
        </article>
    </div>
</div>
</body>
</html>
