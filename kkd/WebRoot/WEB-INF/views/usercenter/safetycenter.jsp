<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/static/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/static/common/meta.jsp" %>
    <script type="text/javascript" src="${ctx}/static/plugin/js/PassGuardCtrl.js"></script>
    <script type="text/javascript" src="${ctx}/static/plugin/js/timer.jquery.js"></script>
    <script type="text/javascript" src="${ctx}/static/kingkaid/js/areacode.js"></script>
    <script type="text/javascript" src="${ctx}/static/kingkaid/js/chosen.area.selection.js"></script>
    <script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/safetycenter.js"></script>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/kingkaid/css/xa_webchange20170308.css"/>
    <style type="text/css">
        .ocx_style {
            width: 150px;
            height: 23px;
            border: 1px solid #d5d5d5;
            vertical-align: middle;
        }

        .code_gray {
            padding: 0 30px;
            background-color: #99a7ba;
            color: #fff;
            height: 36px;
            line-height: 36px;
            border-radius: 4px;
            display: inline-block;
            cursor: pointer;
        }
    </style>
    <script type="text/javascript">
        var pgeditor = new $.pge({
            pgePath: ctx + "/static/xabank/ocx/",
            pgeId: "_ocx_password",
            pgeEdittype: 0,
            pgeEreg1: "[0-9]*",
            pgeEreg2: "[0-9]{6}",
            pgeMaxlength: 6,
            pgeClass: "ocx_style",
            pgeInstallClass: "ocx_style",
            pgeOnfocus: "clearError('_ocx_password')"
        });

        pgeditor.pgInitialize();

        var pgeditorCopy = new $.pge({
            pgePath: ctx + "/static/xabank/ocx/",
            pgeId: "_ocx_password_copy",
            pgeEdittype: 0,
            pgeEreg1: "[0-9]*",
            pgeEreg2: "[0-9]{6}",
            pgeMaxlength: 6,
            pgeClass: "ocx_style",
            pgeInstallClass: "ocx_style",
            pgeOnfocus: "clearError('_ocx_password_copy')"
        });

        pgeditorCopy.pgInitialize();

        var pgeditorOld = new $.pge({
            pgePath: ctx + "/static/xabank/ocx/",
            pgeId: "_ocx_password_old",
            pgeEdittype: 0,
            pgeEreg1: "[0-9]*",
            pgeEreg2: "[0-9]{6}",
            pgeMaxlength: 6,
            pgeClass: "ocx_style",
            pgeInstallClass: "ocx_style",
            pgeOnfocus: "clearError('_ocx_password_old')"
        });

        pgeditorOld.pgInitialize();

        var pgeditorNew = new $.pge({
            pgePath: ctx + "/static/xabank/ocx/",
            pgeId: "_ocx_password_new",
            pgeEdittype: 0,
            pgeEreg1: "[0-9]*",
            pgeEreg2: "[0-9]{6}",
            pgeMaxlength: 6,
            pgeClass: "ocx_style",
            pgeInstallClass: "ocx_style",
            pgeOnfocus: "clearError('_ocx_password_new')"
        });

        pgeditorNew.pgInitialize();

        var pgeditorNewCopy = new $.pge({
            pgePath: ctx + "/static/xabank/ocx/",
            pgeId: "_ocx_password_new_copy",
            pgeEdittype: 0,
            pgeEreg1: "[0-9]*",
            pgeEreg2: "[0-9]{6}",
            pgeMaxlength: 6,
            pgeClass: "ocx_style",
            pgeInstallClass: "ocx_style",
            pgeOnfocus: "clearError('_ocx_password_new_copy')"
        });

        pgeditorNewCopy.pgInitialize();


        /* var pgeditor = new $.pge({
            pgePath: ctx + "/static/xabank/ocx/",
            pgeId: "_ocx_password",
            pgeEdittype: 0,
            pgeEreg1: "[0-9]*",
            pgeEreg2: "[0-9]{6}",
            pgeMaxlength: 6,
            pgeClass: "ocx_style",
            pgeInstallClass: "ocx_style",
            pgeOnfocus: "clearError('_ocx_password')"
        });

        pgeditor.pgInitialize(); */

        var pgeditorBind = new $.pge({
            pgePath: ctx + "/static/xabank/ocx/",
            pgeId: "_ocx_password_bind",
            pgeEdittype: 0,
            pgeEreg1: "[0-9]*",
            pgeEreg2: "[0-9]{6}",
            pgeMaxlength: 6,
            pgeClass: "ocx_style",
            pgeInstallClass: "ocx_style",
            pgeOnfocus: "clearError('_ocx_password_bind')"
        });

        pgeditorBind.pgInitialize();


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
                    <div class="usertitle-text">安全中心</div>
                    <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png"/></div>
                </div>

                <div class="xa_retitle">
                    <div class="jkdsmlogo"><img src="${ctx}/static/kingkaid/images/image/smlogo.png"/></div>
                    <p>金开贷-账户信息</p>
                </div>
                <div style="clear: both;"></div>


                <div style="padding:20px;margin-bottom:15px;">
                    <span style="color:#1f1f1f;font-size:16px;font-weight:bold;float:left;">账号安全系数：</span>
                    <div style="float:left;margin:5px 5px 0 10px;">
                        <div class="process_bar">
                            <span style='width:<fmt:formatNumber value="${formdata.authpercent}" type="percent"/>'></span>
                        </div>
                    </div>
                    <span class="process_bar_text"><fmt:formatNumber value="${formdata.authpercent}"
                                                                     type="percent"/></span>
                    <span style="color:#b8b8b8;font-size:12px;float:left;margin-top:3px;">完善信息，提高账号安全。</span>
                </div>
                <ul class="safety_list">
                    <!-- 修改用户名start -->
					<li>
                        <div class="unit_list_top">
                            <div class="safety_top_content">
                                <div class="left-img">
                                	<img src="${ctx}/static/kingkaid/images/${formdata.nameupdate eq '0' ? 'un' : ''}validated.png"/>
                                </div>
                                <div class="li_title">用户名</div>
                                <div class="li_title_info">${formdata.membername}</div>
                                <div class="li_title_notice">${formdata.nameupdate eq '0' ? '随机用户名可修改一次，修改后不能变更' : ''}</div>
                                <a class="operate_btn" style="display:inline;"
                                   id="update_userName">${formdata.nameupdate eq '0' ? '[修改]' : ''}</a>
                            </div>
                        </div>
                        <div class="unit_safety_detail">
                            <form id="change_name_form">
                                <div class="content_info_li">
                                    <span class="safe_info_title">随机用户名：</span>
                                    <span class="safe_info_detail">${formdata.membername}</span>
                                </div>
                                <div class="content_info_li">
                                    <span class="safe_info_title">新用户名：</span>
                                    <input id="new_name" name="new_name" type="text" class="safety_input"/>
                                    <div></div>
                                </div>
                                <div class="content_info_li">
                                    <span class="safe_info_title">密码：</span>
                                    <input id="password" name="password" type="password" class="safety_input"/>
                                    <div></div>
                                </div>
                                <div id="change_name_btn" class="blue_btn">确定</div>
                                <span id="change_name_msg" class="error_text"></span>
                            </form>
                            <div class="clear"></div>
                        </div>
                    </li>
                    <!-- 修改用户名end -->
                    <!-- 修改账户登录密码start -->
                    <li>
                        <div class="unit_list_top">
                            <div class="safety_top_content">
                                <div class="left-img"><img src="${ctx}/static/kingkaid/images/validated.png"/></div>
                                <div class="li_title">登录密码</div>
                                <a class="operate_btn">[修改]</a>
                            </div>
                        </div>
                        <div class="unit_safety_detail">
                            <form id="change_pwd_form">
                                <div class="content_info_li">
                                    <span class="safe_info_title">当前密码：</span>
                                    <input id="old_password" name="old_password" type="password" class="safety_input"/>
                                    <div></div>
                                </div>
                                <div class="content_info_li">
                                    <span class="safe_info_title">设置新密码：</span>
                                    <input id="new_password" name="new_password" type="password" class="safety_input"/>
                                    <div></div>
                                </div>
                                <div class="content_info_li">
                                    <span class="safe_info_title">确认新密码:</span>
                                    <input id="new_password_d" name="new_password_d" type="password"
                                           class="safety_input"/>
                                    <div></div>
                                </div>
                                <div id="change_pwd_btn" class="blue_btn">确定</div>
                                <span id="change_pwd_msg" class="error_text"></span>
                            </form>
                            <div class="clear"></div>
                        </div>
                    </li>
                    <!-- 修改账户登录密码end -->
                    <!-- 修改手机号码 start -->
                    <li>
                        <c:set var="hasAuth" value="${!fn:contains('45', user_obj.memberState)}"/>
                        <div class="unit_list_top">
                            <div class="safety_top_content">
                                <c:set var="hasMobile" value="${fn:substring(formdata.authflag, 2, 3) eq '1'}"/>
                                <div class="left-img"><img
                                        src="${ctx}/static/kingkaid/images/${hasMobile ? '' : 'un'}validated.png"/>
                                </div>
                                <div class="li_title">手机号码</div>
                                <c:choose>
                                    <c:when test="${hasMobile}">
                                        <div class="li_title_info"><c:set var="pri_mobilenumber"
                                                                          value="${kkd:showPrivacy(formdata.mobilenumber, 1)}"/>${pri_mobilenumber}</div>
                                        <div class="li_title_notice">若已丢失或停用，请立即更换，避免账户被盗</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="li_title_notice">接收通知，找回账户信息的重要途径</div>
                                    </c:otherwise>
                                </c:choose>
                                <a class="operate_btn" style="display:inline;" id="update_mobile">[修改]</a>
                            </div>
                        </div>
                        <div class="unit_safety_detail">
                            <c:choose>
                                <c:when test="${hasMobile}">
                                    <div id="change_mobile_1">
                                        <form id="update_mobile_form1">
                                            <div class="content_info_li">
                                                <span class="safe_info_title">联系电话：</span>
                                                <span class="safe_info_detail">${pri_mobilenumber}</span>
                                            </div>
                                            <div class="content_info_li">
                                                <span class="safe_info_title">验证码：</span>
                                                <input id="m_old_vcode" name="m_old_vcode" type="text"
                                                       class="safety_input"/>
                                                <button type="button" id="m_send_old_btn" class="catch_code">发送验证码
                                                </button>
                                                <div></div>
                                            </div>
                                            <div style="overflow:hidden;">
                                                <div id="m_change_btn_next" class="blue_btn">下一步</div>
                                                <!-- 	                                               <a class="unresive">验证码无法接收？</a>-->
                                                <span class="error_text" id="update_mobile_msg1"></span>
                                            </div>
                                        </form>
                                    </div>
                                    <div id="change_mobile_2" style="display:none">
                                        <form id="update_mobile_form2">
                                            <div class="content_info_li">
                                                <span class="safe_info_title">新手机号：</span>
                                                <input id="new_mobile" name="new_mobile" type="text"
                                                       class="safety_input"/>
                                                <button id="m_send_new_btn" type="button" class="catch_code">发送验证码
                                                </button>
                                                <div></div>
                                            </div>
                                            <div class="content_info_li">
                                                <span class="safe_info_title">验证码：</span>
                                                <input id="m_new_vcode" name="m_new_vcode" type="text"
                                                       class="safety_input"/>
                                                <div></div>
                                            </div>
                                            <div style="overflow:hidden;">
                                                <div id="update_mobile_btn" class="blue_btn">确定</div>
                                                <!--                                                <a class="unresive">验证码无法接收？</a> -->
                                                <span class="error_text" id="update_mobile_msg2"></span>
                                            </div>
                                        </form>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div>
                                        <form id="bind_mobile_form">
                                            <div class="content_info_li">
                                                <span class="safe_info_title">手机号码：</span>
                                                <input id="mobile" name="mobile" type="text" class="safety_input"/>
                                                <span style="color:#bfbfbf;font-size:12px;">请输入您的常用手机号码</span>
                                                <div></div>
                                            </div>
                                            <div class="content_info_li">
                                                <span class="safe_info_title">验证码：</span>
                                                <input id="m_vcode" name="m_vcode" type="text" class="safety_input"/>
                                                <button type="button" id="m_send_btn" class="catch_code">发送验证码</button>
                                                <div></div>
                                            </div>
                                            <div id="bind_mobile_btn" class="blue_btn" style="float:left">确定</div>
                                            <span class="error_text" id="bind_mobile_msg"></span>
                                        </form>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="clear"></div>
                        </div>
                    </li>
                    <!-- 修改手机号码 end -->
                    <!-- 修改邮箱 start -->
                    <li>
                        <div class="unit_list_top">
                            <div class="safety_top_content">
                                <c:set var="hasEmail" value="${fn:substring(formdata.authflag, 3, 4) eq '1'}"/>
                                <div class="left-img"><img
                                        src="${ctx}/static/kingkaid/images/${hasEmail ? '' : 'un'}validated.png"/></div>
                                <div class="li_title">电子邮箱</div>
                                <c:choose>
                                    <c:when test="${hasEmail}">
                                        <div class="li_title_info"><c:set var="pri_email"
                                                                          value="${kkd:showPrivacy(formdata.email, 3)}"/>${pri_email}</div>
                                        <div class="li_title_notice">若已丢失或停用，请立即更换，避免账户被盗</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="li_title_notice">接收通知，找回账户信息的重要途径</div>
                                    </c:otherwise>
                                </c:choose>
                                <a class="operate_btn">[修改]</a>
                            </div>
                        </div>
                        <div class="unit_safety_detail">
                            <c:choose>
                                <c:when test="${hasEmail}">
                                    <div id="change_email_1">
                                        <form id="update_email_form1">
                                            <div class="content_info_li">
                                                <span class="safe_info_title">电子邮箱：</span>
                                                <span class="safe_info_detail">${pri_email}</span>
                                            </div>
                                            <div class="content_info_li">
                                                <span class="safe_info_title">验证码：</span>
                                                <input id="e_old_vcode" name="e_old_vcode" type="text"
                                                       class="safety_input"/>
                                                <button type="button" id="e_send_old_btn" class="catch_code">向旧邮箱发送验证码
                                                </button>
                                                <div></div>
                                            </div>
                                            <div style="overflow:hidden;">
                                                <div id="e_change_btn_next" class="blue_btn">下一步</div>
                                                <!--                                         <a class="unresive">没收到邮件？</a> -->
                                                <span class="error_text" id="update_email_msg1"></span>
                                            </div>
                                        </form>
                                    </div>
                                    <div id="change_email_2" style="display:none">
                                        <form id="update_email_form2">
                                            <div class="content_info_li">
                                                <span class="safe_info_title">新电子邮箱：</span>
                                                <input id="new_email" name="new_email" type="text"
                                                       class="safety_input"/>
                                                <button type="button" id="e_send_new_btn" class="catch_code">向新邮箱发送验证码
                                                </button>
                                                <div></div>
                                            </div>
                                            <div class="content_info_li">
                                                <span class="safe_info_title">验证码：</span>
                                                <input id="e_new_vcode" name="e_new_vcode" type="text"
                                                       class="safety_input"/>
                                                <div></div>
                                            </div>
                                            <div style="overflow:hidden;">
                                                <div id="update_email_btn" class="blue_btn" style="float:left">确定</div>
                                                <!--                                         <a class="unresive">验证码无法接收？</a> -->
                                                <span class="error_text" id="update_email_msg2"></span>
                                            </div>
                                        </form>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div>
                                        <form id="bind_email_form">
                                            <div class="content_info_li">
                                                <span class="safe_info_title">电子邮箱：</span>
                                                <input id="email" name="email" type="text" class="safety_input"/>
                                                <button type="button" id="e_send_btn" class="catch_code">向邮箱发送验证码
                                                </button>
                                                <div></div>
                                            </div>
                                            <div class="content_info_li">
                                                <span class="safe_info_title">验证码：</span>
                                                <input id="e_vcode" name="e_vcode" type="text" class="safety_input"/>
                                                <div></div>
                                            </div>
                                            <div id="bind_email_btn" class="blue_btn">确定</div>
                                            <div class="error_text" id="bind_email_msg"></div>
                                        </form>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="clear"></div>
                        </div>
                    </li>
                    <!-- 修改邮箱 end -->
                    <!-- 修改联系地址start -->
                    <li>
                        <div class="unit_list_top">
                            <div class="safety_top_content">
                                <c:set var="hasContactAddr" value="${fn:substring(formdata.authflag, 5, 6) eq '1'}"/>
                                <div class="left-img"><img
                                        src="${ctx}/static/kingkaid/images/${hasContactAddr ? '' : 'un'}validated.png"/>
                                </div>
                                <div class="li_title">联系地址</div>
                                <c:choose>
                                    <c:when test="${hasContactAddr}">
                                        <div class="li_title_notice">${kkd:showPrivacy(formdata.contactaddr, 5)}</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="li_title_notice">用于邮寄资料或礼品</div>
                                    </c:otherwise>
                                </c:choose>
                                <a class="operate_btn" style="display:${hasAuth ? 'inline': 'none'}">[修改]</a>
                            </div>
                        </div>
                        <div class="unit_safety_detail">
                            <form id="contact_addr_form">
                                <div class="content_info_li">
                                    <span class="safe_info_title">地址：</span>
                                    <div style="display:inline-block;width:270px;">
                                        <span id="contact_addr_area"></span>
                                        <input id="contact_addr_code" name="contact_addr_code" type="hidden"
                                               value="${contactFAddr.code}"/>
                                        <div style="margin-left: -105px"></div>
                                        <input id="contact_addr_block" name="contact_addr_block" type="text"
                                               class="safety_input" style="width:267px;margin-top:15px;"
                                               value="${contactFAddr.block}"/>
                                        <div style="margin-left: -105px"></div>
                                    </div>
                                </div>
                                <div class="content_info_li">
                                    <span class="safe_info_title">邮编：</span>
                                    <input id="contact_addr_post" name="contact_addr_post" type="text"
                                           class="safety_input" value="${formdata.post}"/>
                                    <div></div>
                                </div>
                                <div id="upate_contact_addr_btn" class="blue_btn">确定</div>
                                <div class="error_text" id="update_urgentper_msg"></div>
                            </form>
                            <div class="clear"></div>
                        </div>
                    </li>
                    <!-- 修改联系地址end -->
                </ul>
                <!-- 未开户或未打款激活不显示 -->
                <c:if test="${!fn:contains('45', user_obj.memberState)}">
                    <div class="xa_retitle">
                        <div class="jkdsmlogo"><img src="${ctx}/static/kingkaid/images/image/xasmlogo.png"/></div>
                        <p>西安银行-电子账户信息</p>
                    </div>
                    <div style="clear: both;"></div>
                    <ul class="safety_list">

                        <li>
                            <div class="unit_list_top">
                                <div class="safety_top_content">

                                    <div class="left-img"><img src="${ctx}/static/kingkaid/images/validated.png"/></div>
                                    <div class="li_title">电子账号</div>
                                    <div class="li_title_info">${kkd:showPrivacy(user.custAcNo, 2)}</div>
                                    <div class="li_title_notice">与西安银行合作资金存管，保障用户资金安全</div>
                                </div>
                            </div>
                        </li>

                        <c:if test="${!fn:contains('67', user_obj.memberState)}">
                            <!-- 重置支付密码 begin -->
                            <li>
                                <div class="unit_list_top">
                                    <div class="safety_top_content">
                                        <div class="left-img"><img src="${ctx}/static/kingkaid/images/validated.png"/>
                                        </div>
                                        <div class="li_title">重置交易密码</div>
                                        <a class="operate_btn">[修改]</a>
                                    </div>
                                </div>
                                <div class="unit_safety_detail">
                                    <form id="reset_pwd_form">
                                        <div class="content_info_li">
                                            <span class="safe_info_title">设置新密码：</span>
                                            <span id="_ocx_password_str">
								            <script>
												pgeditor.generate();
											</script>
										</span>
                                            <div></div>
                                        </div>
                                        <div class="content_info_li">
                                            <span class="safe_info_title">确认新密码:</span>
                                            <span id="_ocx_password_copy_str">
							                <script>
							                	pgeditorCopy.generate();
											</script>
										</span>
                                            <div></div>
                                        </div>
                                        <div class="content_info_li">
                                            <span class="safe_info_title">联系电话：</span>
                                            <c:choose>
                                                <c:when test="${not empty mobilenumber}">
                                                    <span class="safe_info_detail">${kkd:showPrivacy(mobilenumber, 1)}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="safe_info_detail">${kkd:showPrivacy(formdata.mobilenumber, 1)}</span>
                                                </c:otherwise>
                                            </c:choose>
                                            <input type="hidden" name="mobile" value="${pri_mobilenumber}"/>
                                        </div>
                                        <div class="content_info_li">
                                            <span class="safe_info_title">验证码：</span>
                                            <input id="ctp_chkcode" name="ctp_chkcode" type="text"
                                                   class="safety_input"/>
                                            <button type="button" id="setpwd_send_msg" class="catch_code">发送验证码</button>
                                            <div></div>
                                        </div>
                                        <div id="ctp_reset_pwd_btn" class="blue_btn">确定</div>
                                        <span id="ctp_reset_pwd_btn" class="error_text"></span>
                                    </form>
                                    <div class="clear"></div>
                                </div>
                            </li>

                            <!-- 修改手机号码 start -->
                            <li>
                                <div class="unit_list_top">
                                    <div class="safety_top_content">
                                        <c:set var="hasMobile" value="${fn:substring(formdata.authflag, 2, 3) eq '1'}"/>
                                        <div class="left-img"><img
                                                src="${ctx}/static/kingkaid/images/${hasMobile ? '' : 'un'}validated.png"/>
                                        </div>
                                        <div class="li_title">手机号码</div>
                                        <c:choose>
                                            <c:when test="${hasMobile}">
                                                <c:choose>
                                                    <c:when test="${not empty mobilenumber}">
                                                        <c:set var="ctp_mobilenumber"
                                                               value="${kkd:showPrivacy(mobilenumber, 1)}"/>
                                                        <input id="ctp_mobilenumber" class="safe_info_detail"
                                                               value="${mobilenumber}" style="display:none;"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="ctp_mobilenumber"
                                                               value="${kkd:showPrivacy(formdata.mobilenumber, 1)}"/>
                                                        <input id="ctp_mobilenumber" class="safe_info_detail"
                                                               value="${formdata.mobilenumber}" style="display:none;"/>
                                                    </c:otherwise>
                                                </c:choose>
                                                <div class="li_title_info">${ctp_mobilenumber}</div>
                                                <div class="li_title_notice">若已丢失或停用，请立即更换，避免账户被盗</div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="li_title_notice">接收通知，找回账户信息的重要途径</div>
                                            </c:otherwise>
                                        </c:choose>
                                        <a class="operate_btn" style="display:inline;" id="update_mobile">[修改]</a>
                                    </div>
                                </div>
                                <div class="unit_safety_detail">
                                    <c:if test="${ playmoneyflag eq '1'}">
                                        <div id="" style="margin-bottom: 23px;font-size: 14px;color: #e04d41;">
                                            您已办理更换预留手机号码的业务，请通过绑定银行卡（尾号${bankcodesub}）向您尾号${custacnosub}的电子账户转账${regamt}元完成办理，也可放弃转账重新申请。
                                        </div>                                    
                                    </c:if>
                                    <div id="ctp_change_mobile_2">
                                        <form id="ctp_update_mobile_form2">
                                            <div class="content_info_li">
                                                <span class="safe_info_title">姓名：</span>
                                                <input id="ctp_name" name="ctp_name" type="text" class="safety_input"/>
                                                <div></div>
                                            </div>
                                            <div class="content_info_li">
                                                <span class="safe_info_title">身份证号：</span>
                                                <input id="ctp_paperid" name="ctp_paperid" type="text"
                                                       class="safety_input"/>
                                                <div></div>
                                            </div>
                                            <div class="content_info_li">
                                                <span class="safe_info_title">新手机号：</span>
                                                <input id="ctp_new_mobile" name="ctp_new_mobile" type="text"
                                                       class="safety_input"/>
                                                <button id="ctp_m_send_new_btn" type="button" class="catch_code">发送验证码
                                                </button>
                                                <div></div>
                                            </div>
                                            <div class="content_info_li">
                                                <span class="safe_info_title">验证码：</span>
                                                <input id="ctp_m_new_vcode" name="ctp_m_new_vcode" type="text"
                                                       class="safety_input"/>
                                                <div></div>
                                            </div>
                                            <div style="overflow:hidden;">
                                                <div id="ctp_update_mobile_btn" class="blue_btn">确定</div>
                                                <!--                                                <a class="unresive">验证码无法接收？</a> -->
                                                <span class="error_text" id="ctp_update_mobile_msg2"></span>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </li>
                            <!-- 修改手机号码 end -->
                            <!-- 修改绑定银行卡 start -->
                            <li>
                                <div class="unit_list_top">
                                    <div class="safety_top_content">
                                        <c:set var="hasCard" value="${not empty bankcode}"/>
                                        <div class="left-img"><img
                                                src="${ctx}/static/kingkaid/images/${hasCard ? '' : 'un'}validated.png"/>
                                        </div>
                                        <div class="li_title">绑定银行卡</div>
                                        <div class="li_title_info"><c:set var="pri_bankcode"
                                                                          value="${kkd:showPrivacy(bankcode, 2)}"/>${pri_bankcode}
                                            &nbsp;&nbsp;${bankname}
                                        </div>
                                        <div class="li_title_notice">用于电子账户充值与提现的唯一指定银行卡</div>
                                        <a class="operate_btn" style="display:inline;" id="update_mobile">[修改]</a>
                                    </div>
                                </div>
                                <div class="unit_safety_detail">
                                    <c:choose>
                                        <c:when test="${hasCard}">
                                            <div id="update_card_form1">
                                                <div class="content_info_li">
                                                    <span class="safe_info_title">银行卡号：</span>
                                                    <span class="safe_info_detail">${pri_bankcode}</span>
                                                </div>
                                                <div style="overflow:hidden;">
                                                    <c:if test="${changeCardRecord.state eq '3'}">
                                                        申请拒绝，<a id="checkReason" title="${changeCardRecord.remark}" style="color: red;">查看原因</a>
                                                    </c:if>
                                                    <c:if test="${changeCardRecord.state ne '1'}">
                                                        <button class="blue_btn" onclick="javascript: window.location.href = '${ctx}/auth/xabank/acct/change_card_page.html'">更换银行卡</button>
                                                    </c:if>
                                                    <c:if test="${changeCardRecord.state eq '1'}">
                                                        <button class="gray_btn">审批中</button>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <form id="update_card_form2">
                                                <div style="overflow:hidden;">
                                                    <a href="${ctx}/auth/xabank/acct/bunding_card_page.html" class="xa_outcash xa_numactivbl fr bgbl bderbl" style="width: 86px;">绑定银行卡</a>
                                                </div>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="clear"></div>
                                </div>
                            </li>
                            <!-- 修改绑定银行卡 end -->
                        </c:if>
                    </ul>

                </c:if>

                <div class="xa_retitle">
                    <div class="jkdsmlogo"><img src="${ctx}/static/kingkaid/images/image/smper.png"/></div>
                    <p>个人信息</p>
                </div>
                <div style="clear: both;"></div>

                <ul class="safety_list">
                    <li>
                        <div class="unit_list_top">
                            <div class="safety_top_content">
                                <c:set var="hasUrgentPer" value="${fn:substring(formdata.authflag, 4, 5) eq '1'}"/>
                                <div class="left-img"><img
                                        src="${ctx}/static/kingkaid/images/${hasUrgentPer ? '' : 'un'}validated.png"/>
                                </div>
                                <div class="li_title">真实姓名</div>
                                <div class="li_title_info"><c:set var="pri_name"
                                                                  value="${kkd:showPrivacy(formdata.custname, 0)}"/>${pri_name}</div>
                                <a class="operate_btn" id="info_view"
                                   style="display:${hasAuth ? 'inline': 'none'}">[查看]</a>
                            </div>
                        </div>
                        <div class="unit_safety_detail">
                            <div class="content_info_li">
                                <span class="safe_info_title">姓名：</span>
                                <span class="safe_info_detail">${pri_name}</span>
                            </div>
                            <div class="content_info_li">
                                <span class="safe_info_title">身份证号：</span>
                                <span class="safe_info_detail">${kkd:showPrivacy(formdata.paperid, 2)}</span>
                            </div>
                            <div class="clear"></div>
                        </div>
                    </li>

                    <li>
                        <div class="unit_list_top">
                            <div class="safety_top_content">
                                <c:set var="hasUrgentPer" value="${fn:substring(formdata.authflag, 4, 5) eq '1'}"/>
                                <div class="left-img"><img
                                        src="${ctx}/static/kingkaid/images/${hasUrgentPer ? '' : 'un'}validated.png"/>
                                </div>
                                <div class="li_title">紧急联系人</div>
                                <c:choose>
                                    <c:when test="${hasUrgentPer}">
                                        <div class="li_title_info">${kkd:showPrivacy(formdata.familycustname, 0)}</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="li_title_notice">在紧急情况下能够被联系到的与您相关的人</div>
                                    </c:otherwise>
                                </c:choose>
                                <a class="operate_btn" style="display:${hasAuth ? 'inline': 'none'}">[修改]</a>
                            </div>
                        </div>
                        <div class="unit_safety_detail">
                            <form id="update_urgentper_form">
                                <div class="content_info_li">
                                    <span class="safe_info_title">姓名：</span>
                                    <input id="ungent_name" name="ungent_name" type="text" class="safety_input"
                                           value="${formdata.familycustname}"/>
                                    <div></div>
                                </div>
                                <div class="content_info_li">
                                    <span class="safe_info_title">手机：</span>
                                    <input id="ungent_mobile" name="ungent_mobile" type="text" class="safety_input"
                                           value="${formdata.mobile}"/>
                                    <div></div>
                                </div>
                                <div id="update_urgentper_btn" class="blue_btn">确定</div>
                                <div class="error_text" id="update_urgentper_msg"></div>
                            </form>
                            <div class="clear"></div>
                        </div>
                    </li>

                    <li>
                        <div class="unit_list_top">
                            <div class="safety_top_content">
                                <c:set var="hasPersInfo" value="${fn:substring(formdata.authflag, 6, 7) eq '1'}"/>
                                <div class="left-img"><img
                                        src="${ctx}/static/kingkaid/images/${hasPersInfo ? '' : 'un'}validated.png"/>
                                </div>
                                <div class="li_title">个人信息</div>
                                <c:if test="${!hasPersInfo}">
                                    <div class="li_title_info">完善有助于提高您的账户安全</div>
                                </c:if>
                                <a class="operate_btn" style="display:${hasAuth ? 'inline': 'none'}">[修改]</a>
                            </div>
                        </div>
                        <div class="unit_safety_detail">
                            <form id="pers_info_form">
                                <div style="width:390px;float:left;">
                                    <div class="content_info_li">
                                        <span class="safe_info_title">出生日期：</span>
                                        <input type="text" id="pers_birth_date" class="safety_input"
                                               value='<fmt:formatDate value="${formdata.birtdate}" pattern="yyyy-MM-dd"/>'
                                               disabled="disabled"/>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">性别：</span>
                                        <span style="display:inline-block;">
                                            <select data-placeholder="请选择" id="pers_sexsign" class="safety_select"
                                                    disabled="disabled">
                                            <option value=""></option>
                                            <c:forEach items="${sexsign.options}" var="option">
                                                <option value="${option.value}" ${formdata.sexsign eq option.value ? 'selected' : ''}>${option.text}</option>
                                            </c:forEach>
                                            </select>
                                        </span>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">*最高学历：</span>
                                        <div style="display:inline-block;">
                                            <select data-placeholder="请选择" id="pers_educsign" name="pers_educsign"
                                                    class="safety_select">
                                                <option value=""></option>
                                                <c:forEach items="${educsign.options}" var="option">
                                                    <option value="${option.value}" ${formdata.educsign eq option.value ? 'selected' : ''}>${option.text}</option>
                                                </c:forEach>
                                            </select>
                                            <div></div>
                                        </div>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">入学年份：</span>
                                        <span style="display:inline-block;">
                                            <select data-placeholder="请选择" id="pers_enterschoolyear"
                                                    class="safety_select">
                                            <option value=""></option>
                                             <c:forEach items="${years}" var="year">
                                                 <option value="${year}" ${formdata.enterschoolyear eq year ? 'selected' : ''}>${year}</option>
                                             </c:forEach>
                                            </select>
                                        </span>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">毕业院校：</span>
                                        <input id="pers_grandschool" type="text" style="width:264px;"
                                               class="safety_input" value="${formdata.gradschool}"/>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">*婚姻状况：</span>
                                        <div style="display:inline-block;">
                                            <select data-placeholder="请选择" id="pers_marrsign" name="pers_marrsign"
                                                    class="safety_select">
                                                <option value=""></option>
                                                <c:forEach items="${marrsign.options}" var="option">
                                                    <option value="${option.value}" ${formdata.marrsign eq option.value ? 'selected' : ''}>${option.text}</option>
                                                </c:forEach>
                                            </select>
                                            <div></div>
                                        </div>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">*有无子女：</span>
                                        <div style="display:inline-block;">
                                            <select data-placeholder="请选择" id="pers_childsign" name="pers_childsign"
                                                    class="safety_select">
                                                <option value=""></option>
                                                <c:forEach items="${isornotflag.options}" var="option">
                                                    <option value="${option.value}" ${formdata.childsign eq option.value ? 'selected' : ''}>${option.text}</option>
                                                </c:forEach>
                                            </select>
                                            <div></div>
                                        </div>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">是否有房：</span>
                                        <span style="display:inline-block;">
                                            <select data-placeholder="请选择" id="pers_has_house" class="safety_select">
                                            <option value=""></option>
                                            <c:forEach items="${isornotflag.options}" var="option">
                                                <option value="${option.value}" ${formdata.varchar_11 eq option.value ? 'selected' : ''}>${option.text}</option>
                                            </c:forEach>
                                            </select>
                                        </span>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">有无房贷：</span>
                                        <span style="display:inline-block;">
                                            <select data-placeholder="请选择" id="pers_has_house_loan"
                                                    class="safety_select">
                                            <option value=""></option>
                                            <c:forEach items="${isornotflag.options}" var="option">
                                                <option value="${option.value}" ${formdata.varchar_12 eq option.value ? 'selected' : ''}>${option.text}</option>
                                            </c:forEach>
                                            </select>
                                        </span>
                                    </div>
                                    <div id="update_pers_info_btn" class="blue_btn">确定</div>
                                </div>
                                <div style="width:380px;float:left;margin-left:10px">
                                    <div class="content_info_li">
                                        <span class="safe_info_title">是否有车：</span>
                                        <span style="display:inline-block;">
                                            <select data-placeholder="请选择" id="pers_has_car" class="safety_select">
                                            <option value=""></option>
                                            <c:forEach items="${isornotflag.options}" var="option">
                                                <option value="${option.value}" ${formdata.varchar_13 eq option.value ? 'selected' : ''}>${option.text}</option>
                                            </c:forEach>
                                            </select>
                                        </span>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">有无车贷：</span>
                                        <span style="display:inline-block;">
                                            <select data-placeholder="请选择" id="pers_has_car_loan" class="safety_select">
                                            <option value=""></option>
                                            <c:forEach items="${isornotflag.options}" var="option">
                                                <option value="${option.value}" ${formdata.varchar_14 eq option.value ? 'selected' : ''}>${option.text}</option>
                                            </c:forEach>
                                            </select>
                                        </span>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">买车年份：</span>
                                        <span style="display:inline-block;">
                                            <select data-placeholder="请选择" id="pers_carbuyyear" class="safety_select">
                                                <option value=""></option>
                                                <c:forEach items="${years}" var="year">
                                                    <option value="${year}" ${formdata.carbuyyear eq year ? 'selected' : ''}>${year}</option>
                                                </c:forEach>
                                            </select>
                                        </span>
                                    </div>

                                    <div class="content_info_li">
                                        <span class="safe_info_title">汽车品牌：</span>
                                        <input id="pers_carbrand" type="text" style="width:264px;" class="safety_input"
                                               value="${formdata.carbrand}"/>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">*籍贯：</span>
                                        <div style="display:inline-block;">
                                            <span id="pers_native_area"></span>
                                            <input id="nativeplace_code" name="nativeplace_code" type="hidden"
                                                   value="${formdata.nativeplace}"/>
                                            <div></div>
                                        </div>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">*户口所在地：</span>
                                        <div style="display:inline-block;">
                                            <span id="pers_regis_area"></span>
                                            <input type="hidden" id="regisarea_code" name="regisarea_code"
                                                   value="${formdata.regisarea}"/>
                                            <div></div>
                                        </div>
                                    </div>

                                    <div class="content_info_li">
                                        <span class="safe_info_title">*居住地址：</span>
                                        <div style="display:inline-block;width:270px;">
                                            <span id="pers_live_area"></span>
                                            <input type="hidden" id="live_addr_code" name="live_addr_code"
                                                   value="${liveFAddr.code}"/>
                                            <div></div>
                                        </div>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title"></span>
                                        <div style="display:inline-block;width:270px;">
                                            <span id="pers_live_area"></span>
                                            <input id="pers_live_block" type="text" class="safety_input_spec"
                                                   style="width:264px;margin-top:-10px" value="${liveFAddr.block}"/>
                                        </div>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">居住地电话：</span>
                                        <input id="pers_tel" name="pers_tel" type="text" style="width:150px"
                                               class="safety_input" value="${formdata.tel}"/>
                                        <div></div>
                                    </div>
                                </div>
                            </form>
                            <div class="clear"></div>
                        </div>
                    </li>
                    <li>
                        <div class="unit_list_top">
                            <div class="safety_top_content">
                                <c:set var="hasJobInfo" value="${fn:substring(formdata.authflag, 7, 8) eq '1'}"/>
                                <div class="left-img"><img
                                        src="${ctx}/static/kingkaid/images/${hasJobInfo ? '' : 'un'}validated.png"/>
                                </div>
                                <div class="li_title">工作信息</div>
                                <c:if test="${!hasJobInfo}">
                                    <div class="li_title_info">完善有助于提高您的账户安全</div>
                                </c:if>
                                <a class="operate_btn" style="display:${hasAuth ? 'inline': 'none'}">[修改]</a>
                            </div>
                        </div>
                        <div class="unit_safety_detail">
                            <form id="job_info_form">
                                <div style="width:390px;float:left;">
                                    <div class="content_info_li">
                                        <span class="safe_info_title">公司名称：</span>
                                        <input id="job_workcorp" type="text" style="width:264px;" class="safety_input"
                                               value="${formdata.workcorp}"/>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">*工作城市：</span>
                                        <div style="display:inline-block;">
                                            <span id="job_work_city_area"></span>
                                            <input type="hidden" id="workcity_code" name="workcity_code"
                                                   value="${formdata.workcity}"/>
                                            <div></div>
                                        </div>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">*公司行业：</span>
                                        <div style="display:inline-block;">
                                            <select data-placeholder="请选择" id="job_waykind" name="job_waykind"
                                                    class="safety_select">
                                                <option value=""></option>
                                                <c:forEach items="${waykind.options}" var="option">
                                                    <option value="${option.value}" ${formdata.waykind eq option.value ? 'selected' : ''}>${option.text}</option>
                                                </c:forEach>
                                            </select>
                                            <div></div>
                                        </div>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">职位：</span>
                                        <span style="display:inline-block;">
                                            <select data-placeholder="请选择" id="job_workkind" class="safety_select">
                                                <option value=""></option>
                                                <c:forEach items="${workkind.options}" var="option">
                                                    <option value="${option.value}" ${formdata.workkind eq option.value ? 'selected' : ''}>${option.text}</option>
                                                </c:forEach>
                                            </select>
                                        </span>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">入职年份：</span>
                                        <span style="display:inline-block;">
                                        <select data-placeholder="请选择" id="job_workyear" class="safety_select">
                                            <option value=""></option>
                                            <c:forEach items="${years}" var="year">
                                                <option value="${year}" ${formdata.workyearmonth eq year ? 'selected' : ''}>${year}</option>
                                            </c:forEach>
                                        </select>
                                        </span>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">工作邮箱：</span>
                                        <div style="display:inline-block;">
                                            <input id="job_workemail" name="job_workemail" type="text"
                                                   style="width:264px;" class="safety_input"
                                                   value="${formdata.workemail}"/>
                                            <div></div>
                                        </div>
                                    </div>

                                    <div id="update_job_info_btn" class="blue_btn">确定</div>
                                </div>
                                <div style="width:380px;float:left;margin-left:10px">
                                    <div class="content_info_li">
                                        <span class="safe_info_title">*职业：</span>
                                        <div style="display:inline-block;">
                                            <select data-placeholder="请选择" id="job_vocasign" name="job_vocasign"
                                                    class="safety_select">
                                                <option value=""></option>
                                                <c:forEach items="${vocasign.options}" var="option">
                                                    <option value="${option.value}" ${formdata.vocasign eq option.value ? 'selected' : ''}>${option.text}</option>
                                                </c:forEach>
                                            </select>
                                            <div></div>
                                        </div>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">公司类别：</span>
                                        <span style="display:inline-block;">
                                            <select data-placeholder="请选择" id="job_corpcategory" class="safety_select">
                                                <option value=""></option>
                                                <c:forEach items="${corpcategory.options}" var="option">
                                                    <option value="${option.value}" ${formdata.corpcategory eq option.value ? 'selected' : ''}>${option.text}</option>
                                                </c:forEach>
                                            </select>
                                        </span>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">公司规模：</span>
                                        <span style="display:inline-block;">
                                        <select data-placeholder="请选择" id="job_corpsizesign" class="safety_select">
                                            <option value=""></option>
                                            <c:forEach items="${corpsizesign.options}" var="option">
                                                <option value="${option.value}" ${formdata.corpsizesign eq option.value ? 'selected' : ''}>${option.text}</option>
                                            </c:forEach>
                                        </select>
                                        </span>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">*月收入：</span>
                                        <div style="display:inline-block;">
                                            <input id="job_income" name="job_income" type="text" style="width:85px;"
                                                   class="safety_input" value="${formdata.monthincome}"/> 元
                                            <div></div>
                                        </div>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">公司电话：</span>
                                        <div style="display:inline-block;">
                                            <input id="job_corptel" name="job_corptel" type="text" style="width:264px;"
                                                   class="safety_input" value="${formdata.corptel}"/>
                                            <div></div>
                                        </div>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title">公司地址：</span>
                                        <div style="display:inline-block;width:270px;">
                                            <span id="job_corp_addr_area"></span>
                                            <input type="hidden" id="corp_addr_code" name="corp_addr_code"
                                                   value="${corpFAddr.code}"/>
                                            <div></div>
                                        </div>
                                    </div>
                                    <div class="content_info_li">
                                        <span class="safe_info_title"></span>
                                        <div style="display:inline-block;width:270px;">
                                            <span id="job_corp_addr_area"></span>
                                            <input id="job_corp_addr_block" type="text"
                                                   style="width:264px;margin-top:-10px" class="safety_input_spec"
                                                   value="${corpFAddr.block}"/>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <div class="clear"></div>
                        </div>
                    </li>
                    <!-- add 证书管理 start -->
                    <c:set var="hasRaInfos" value="${rafordend.loanapp eq '0'}"/>
                    <c:choose>
                        <c:when test="${hasRaInfos}">
                        </c:when>
                        <c:otherwise>
                            <li>
                                <div class="unit_list_top">
                                    <div class="safety_top_content">
                                        <c:set var="hasRaInfo" value="${fordend.id eq ''}"/>
                                        <div class="left-img">
                                            <img
                                                    src="${ctx}/static/kingkaid/images/${hasRaInfo ? 'un' : ''}validated.png"/>
                                        </div>
                                        <div class="li_title">证书管理</div>
                                        <a class="operate_btn" id="infooper">[管理]</a>
                                    </div>
                                </div>
                                <div style="display: none;" class="unit_safety_detail">
                                    <div style="width: 390px; float: left;">

                                        <div style="display: block;" class="unit_safety_detail">
                                            <div class="content_info_li">
                                                <span class="safe_info_title">是否申请：</span> <span
                                                    class="safe_info_detail">${fordend.statename}</span>
                                            </div>
                                            <div class="content_info_li">
                                                <span class="safe_info_title">姓名：</span> <span
                                                    class="safe_info_detail">${fordend.custname}</span>
                                            </div>
                                            <div class="content_info_li">
                                                <span class="safe_info_title">序列号：</span> <span
                                                    class="safe_info_detail">${fordend.serialno}</span>
                                            </div>
                                            <div class="content_info_li">
                                                <span class="safe_info_title">CN：</span> <span
                                                    class="safe_info_detail">${fordend.cn}</span>
                                            </div>
                                            <div class="content_info_li">
                                                <span class="safe_info_title">有效期：</span> <span
                                                    class="safe_info_detail">${fn:substring(fordend.starttime,0,8)}-${fn:substring(fordend.endtime,0,8)}</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="right_detail">
                                        <div id="down_click" class="blbtnvisited">申请证书</div>

                                        <div id="rechange_click" class="blbtn">补发证书</div>
                                        <c:choose>
                                            <c:when test="${fordend.flag eq '1'}">
                                                <div id="update_click" class="blbtn">更新证书</div>
                                            </c:when>
                                            <c:otherwise>
                                                <div id="no_update_click" class="blbtnvisited">更新证书</div>
                                            </c:otherwise>
                                        </c:choose>
                                        <div id="operate_click" class="blbtn">下载证书</div>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </li>
                        </c:otherwise>
                    </c:choose>
                    <!-- add 证书管理 end  -->

                </ul>
                <%@ include file="usercenterleftmenu.jsp" %>
            </div>
        </div>
        <div style="clear: both"></div>
    </div>
    <div style="clear: both"></div>
</div>
<div id="FakeCryptoAgent"></div>
<input type="hidden" id="ctlurlValue" value="${ctlurl}"/>
<%@ include file="../common/footer.jsp" %>
<div id="bmvcodeDiv" style="display:none; z-index:1999">
    <div class="alert_box_small" style="width:400px; top:200px">
        <div class="window_top">
            <div class="window_top_l">请输入验证码用以发送短信</div>
            <div class="window_close_btn" id="bmvd_close"><img src="${ctx}/static/kingkaid/images/window_close_bt.png"/>
            </div>
        </div>
        <div class="window_content">
            <div class="operate_content" style="width:400px">
                <form id="bmcform" style="width:325px;height:55px">
                    <input id="bmvcode" name="bmvcode" type="text" class="login-input"
                           style="width:146px;margin-left:80px" placeholder="请输入验证码"/>
                    <img id="bmvcode_img" style="float:right;cursor:pointer;margin-top:5px" title="点击更换验证码" alt="验证码"
                         src="${ctx}/member/getVCodeValidateCode?s=<%=new java.util.Date().getTime()%>"/>
                    <div style="margin-left:80px;line-height:20px"></div>
                </form>
            </div>
        </div>
        <div class="small_window_bottom" style="padding:20px 0 32px 0">
            <div id="bmvd_ok" class="blue_btn">确定</div>
            <div id="bmvd_cancel" class="blue_btn btn_right">取消</div>
        </div>
    </div>
</div>

<div id="umvcodeDiv" style="display:none; z-index:1999">
    <div class="alert_box_small" style="width:400px; top:200px">
        <div class="window_top">
            <div class="window_top_l">请输入验证码用以发送短信</div>
            <div class="window_close_btn" id="umvd_close"><img src="${ctx}/static/kingkaid/images/window_close_bt.png"/>
            </div>
        </div>
        <div class="window_content">
            <div class="operate_content" style="width:400px">
                <form id="umcform" style="width:325px;height:55px">
                    <input id="umvcode" name="umvcode" type="text" class="login-input"
                           style="width:146px;margin-left:80px" placeholder="请输入验证码"/>
                    <img id="umvcode_img" style="float:right;cursor:pointer;margin-top:5px" title="点击更换验证码" alt="验证码"
                         src="${ctx}/member/getVCodeValidateCode?s=<%=new java.util.Date().getTime()%>"/>
                    <div style="margin-left:80px;line-height:20px"></div>
                </form>
            </div>
        </div>
        <div class="small_window_bottom" style="padding:20px 0 32px 0">
            <div id="umvd_ok" class="blue_btn">确定</div>
            <div id="umvd_cancel" class="blue_btn btn_right">取消</div>
        </div>
    </div>
</div>

<div id="bevcodeDiv" style="display:none; z-index:1999">
    <div class="alert_box_small" style="width:400px; top:200px">
        <div class="window_top">
            <div class="window_top_l">请输入验证码用以发送邮件</div>
            <div class="window_close_btn" id="bevd_close"><img src="${ctx}/static/kingkaid/images/window_close_bt.png"/>
            </div>
        </div>
        <div class="window_content">
            <div class="operate_content" style="width:400px">
                <form id="becform" style="width:325px;height:55px">
                    <input id="bevcode" name="bevcode" type="text" class="login-input"
                           style="width:146px;margin-left:80px" placeholder="请输入验证码"/>
                    <img id="bevcode_img" style="float:right;cursor:pointer;margin-top:5px" title="点击更换验证码" alt="验证码"
                         src="${ctx}/member/getVCodeValidateCode?s=<%=new java.util.Date().getTime()%>"/>
                    <div style="margin-left:80px;line-height:20px"></div>
                </form>
            </div>
        </div>
        <div class="small_window_bottom" style="padding:20px 0 32px 0">
            <div id="bevd_ok" class="blue_btn">确定</div>
            <div id="bevd_cancel" class="blue_btn btn_right">取消</div>
        </div>
    </div>
</div>

<div id="uevcodeDiv" style="display:none; z-index:1999">
    <div class="alert_box_small" style="width:400px; top:200px">
        <div class="window_top">
            <div class="window_top_l">请输入验证码用以发送邮件</div>
            <div class="window_close_btn" id="uevd_close"><img src="${ctx}/static/kingkaid/images/window_close_bt.png"/>
            </div>
        </div>
        <div class="window_content">
            <div class="operate_content" style="width:400px">
                <form id="uecform" style="width:325px;height:55px">
                    <input id="uevcode" name="uevcode" type="text" class="login-input"
                           style="width:146px;margin-left:80px" placeholder="请输入验证码"/>
                    <img id="uevcode_img" style="float:right;cursor:pointer;margin-top:5px" title="点击更换验证码" alt="验证码"
                         src="${ctx}/member/getVCodeValidateCode?s=<%=new java.util.Date().getTime()%>"/>
                    <div style="margin-left:80px;line-height:20px"></div>
                </form>
            </div>
        </div>
        <div class="small_window_bottom" style="padding:20px 0 32px 0">
            <div id="uevd_ok" class="blue_btn">确定</div>
            <div id="uevd_cancel" class="blue_btn btn_right">取消</div>
        </div>
    </div>
</div>

<div id="resetsubmit">

</div>

</body>
</html>
