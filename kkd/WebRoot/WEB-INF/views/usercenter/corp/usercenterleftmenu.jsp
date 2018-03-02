<%@ page contentType="text/html;charset=UTF-8"%>
<div class="user_leftmenu">
    
    <ul class="user_menulist">
        <li>
            <a class="user_menulist_href" id="usermenu_jkd" href="${ctx}/auth/usercenter/myjkd.html">我的金开贷</a>
        </li>
        <li>
            <a class="user_menulist_href" id="usermenu_jk" href="${ctx}/auth/usercenter/my_borrow_page.html">我的借款</a>
        </li>
        <li>
            <a class="user_menulist_href" id="usermenu_zc" href="${ctx}/auth/usercenter/myproperty.html">我的资产</a>
        </li>
        <c:if test="${isGuarantee}">
        <li>
            <a class="user_menulist_href" id="usermenu_db" href="${ctx}/auth/usercenter/mywarrant.html">我的担保</a>
        </li>
        </c:if>
        <li>
            <a class="user_menulist_href" id="usermenu_notes" href="${ctx}/auth/usercenter/corp/corpmessage.html">我的消息</a>
        </li>
        <li>
            <a class="user_menulist_href" id="usermenu_aqzx" href="${ctx}/auth/usercenter/safetycenter.html">安全中心</a>
        </li>
        <li>
            <a class="user_menulist_href" id="usermenu_xz" href="${ctx}/auth/usercenter/corp/corpdownload_center.html">下载中心</a>
        </li>
    </ul>
    
</div>