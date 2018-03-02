var now = 0;
var tempInterval = null;
$(document).ready(function () {
    $("#usermenu_db").addClass("user_menulist_href_hover");
    $("#menu_jkd").addClass("leftmenu_cur");

    $.ajax({
        type: "POST",
        url: ctx + '/auth/activity/isVerti',
        dataType: "json",
        success: function (data) {
            var isVerti = data.isVerti;
            if (isVerti == '4' || isVerti == '5') {//未认证
                alert("您还没有实名认证，请先完成实名认证！");
            }
        }
    });

    $("#pager").simplePagination({
        url: ctx + '/auth/usercenter/warrant/myWarrantList',
        items_per_page: 10,
        handle_data: function (data) {
            window.clearInterval(tempInterval);
            $("#warrant_list").empty();
            var loadstate = ["06", "10", "17", "18", "19", "30"];//合同下载按钮的显示判断
            var lookplanstate = ["10", "19", "30"];//还款计划书按钮显示判断
            combineOptionLabels(data);
            var items = '';
            for (var i = 0; i < data.body.result.length; i++) {
                var row = data.body.result[i];
                var item = '<li>';
                item += '    <table class="poj_tb">';
                item += '        <tr>';
                item += '            <td style="display:none">' + row.loanid + '</td>';
                item += '            <td style="width:120px;">';
                item += '                <div>' + row.projecttitle + '</div>';
                item += '            </td>';
                item += '            <td style="width:100px;">';
                item += '                <div>' + moneyFormat(row.apptcapi) + '</div>';
                item += '            </td>';
                item += '            <td style="width:122px;">';
                if (!isNaN(row.intadd) && Number(row.intadd) > 0) {
                    item += '                <div>' + row.pinterate + '%+<span class="jkd_money">' + moneyFormat(row.intadd) + '%</span></div>';
                } else {
                    item += '                <div>' + row.pinterate + '%</div>';
                }
                item += '            </td>';
                item += '            <td style="width:80px;">';
                item += '                <div>' + row.apptterm + '个月</div>';
                item += '            </td>';
                item += '            <td style="width:105px;">';
                item += '                <div>' + row.overpluscount + '期</div>';
                item += '            </td>';
                item += '            <td style="width:115px;">';
                item += '                <div class="red_text">' + row.nextrepadate + '</div>';
                item += '            </td>';
                item += '            <td style="width:80px;">';
                item += row.apprstate == '14'? '			         <div id=\'' + row.loanid + 'Div\'>' + formatPubDate(row.pubbiddate) + '</div>' : ('                <div>' + row.apprstatelabel + '</div>');
                item += '            </td>';
                item += '        </tr>';
                item += '        <tr>';
                item += '            <td colspan="8">';
                item += '            <div class="conpany_info">';
                item += '                <div style="float:left;overflow:hidden;">';
                item += '                    <div style="margin-bottom:10px;overflow:hidden">';
                item += '                        <img style="float:left" src="' + ctx + '/static/kingkaid/images/and.jpg"/>';
                item += '                        <div class="manage-loan-icon">担保贷</div>';
                if (row.projecttags.indexOf('2') > -1)
                    item += '                        <div class="mobile-icon">加息</div>';
                if (row.projecttags.indexOf('1') > -1)
                    item += '                        <div class="newcomer-icon">新手</div>';
                item += '                    </div>';
                item += '                </div>';
                item += '                <div style="float:right">';
                if (jQuery.inArray(row.apprstate, lookplanstate) > -1)
                    item += '                    <div class="manage-loan-icon invest_right_btn" onclick="returnPlan(\'' + row.loanid + '\',\'' + row.projecttitle + '\',\'' + row.apptcapi + '\',\'' + row.pinterate + '\')";>还款计划书</div>';
                if (row.apprstate == '09')
                    item += '                    <div class="car-loan-icon invest_right_btn" onclick="warrantConfirmBox(\'' + row.loanid + '\')";>确认担保</div>';
                if (row.apprstate == '17')
                    item += '                    <div class="car-loan-icon invest_right_btn" onclick="warrshowConfirmBox(\'' + row.loanid + '\',\'' + row.apprstate + '\',\'' + row.prodid + '\')";>合同签订</div>';
                if (jQuery.inArray(row.apprstate, loadstate) > -1)
                    item += '					<div class="turn-loan-icon invest_right_btn" onclick="showPromptWindow(\'' + row.apprstate + '\',\'' + row.apprstate + '\',\'' + row.loanid + '\',\'' + row.downloadurl + '\',\'' + row.filename + '\',\'' + row.prodid + '\')">下载合同</div>';
                if (row.flag == '1')
                    item += '					<div onclick="othMess(\'' + row.loanid + '\');" class="manage-loan-icon invest_right_btn">拆标信息</div>';
                item += '                </div>';
                item += '            </div>';
                item += '            </td>';
                item += '        </tr>';
                item += '    </table>';
                item += '</li>';
                items += item;
            }
            $("#warrant_list").append(items);
            // tempInterval = window.setInterval(function () {
            //     showSurplusTime(data.body.result);
            // }, 1000);
            return true;
        },
        qcon_func: function () {
            return {
                apprState: $(".search_link.cur_invest_search").attr("id"),
            }
        }
    });

    // 筛选条件
    $(".search_link").click(function () {
        $(this).addClass("cur_invest_search").parent().siblings().find(".search_link").removeClass("cur_invest_search");
        $("#pager").trigger("setPage", 0);
    });

});

function formatPubDate(pubBidDate) {
    var pubDate = new Date(pubBidDate.replace(/-/g, '/'));
    return addZeroIfNumberLT10(pubDate.getHours()) + ":" + addZeroIfNumberLT10(pubDate.getMinutes()) + "发布";
}

function showSurplusTime(investList) {
    now++;
    for (var i = 0; i < investList.length; i++) {
        var row = investList[i];
        var surplusSecond = row.surplussecond - now;
        if (row.apprstate === '14') {
            if (surplusSecond > 0) {
                var hour = Math.floor(surplusSecond / 3600);
                var minute = Math.floor((surplusSecond - hour * 3600) / 60);
                var second = Math.floor(surplusSecond - hour * 3600 - minute * 60);
                document.getElementById(row.loanid + 'Div').innerHTML = addZeroIfNumberLT10(hour) + ':' + addZeroIfNumberLT10(minute) + ':' + addZeroIfNumberLT10(second);
            } else {
                document.getElementById(row.loanid + 'Div').innerHTML = '自动投标中';
            }
        }
    }
}

function addZeroIfNumberLT10 (number) {
    return number < 10 ? '0' + number : number;
}

function downloadContract(loanid) {
    alert("合同下载" + loanid);
}

function confirmApp(loanid, apprstate) {
    if (apprstate == '25' && confirm('您确定要拒绝借款信息?') || apprstate == '12')
        confirmState(loanid, apprstate);
    window.location.href = ctx + '/auth/usercenter/mywarrant.html';
}
function confirmState(loanid, apprstate) {
    if ($('#new_dialogue').length !== 0) {
        closeDIV('new_dialogue');
    }
    $.ajax({
        type: 'POST',
        dataType: "json",
        url: ctx + '/auth/usercenter/warrant/setApprState',
        data: {
            "loanid": loanid,
            "apprstate": apprstate
        },
        success: function (data) {
            if (data.status) {
                $("#pager").trigger("currentPage");
                Window.location.reload();
            } else {
                alert(data.message);
            }
        }
    });
}

function returnPlan(loanid, projecttitle, apptcapi, pinterate) {
// 	create_bg();
//	$("body").append(warrantReturnPlanDlg(projecttitle));
    warrantReturnPlanDlg(projecttitle);
    $.ajax({
        type: 'POST',
        dataType: "json",
        url: ctx + '/auth/usercenter/warrant/returnPlan',
        data: {"loanid": loanid},
        success: function (data) {
            var items = '<tr>'
                + '               <th class="plan_tb_first_column">投资金额</th>'
                + '               <th style="width:100px;">年化利率</th>'
                + '               <th style="width:95px;">应收利息</th>'
                + '               <th style="width:100px;">应收本金</th>'
                + '               <th style="width:100px;">应收本息</th>'
                + '               <th style="width:93px;">还款期数</th>'
                + '               <th style="width:125px;">下期还款日</th>'
                + '               <th>状态</th>'
                + '           </tr>';
            for (var i = 0; i < data.length; i++) {
                var repaystat = '';
                if (data[i].repaystat == '1') {
                    repaystat = '<img src="' + ctx + '/static/kingkaid/images/right_over.png"/>';
                }
                var item = '<tr>'
                    + '    <td class="plan_tb_first_column">' + apptcapi + '</td>'
                    + '    <td>' + pinterate + '</td>'
                    + '    <td>' + data[i].sinte + '</td>'
                    + '    <td>' + data[i].scapi + '</td>'
                    + '    <td>'
                    + '        <div class="red_text">' + data[i].sumsi + '</div>'
                    + '    </td>'
                    + '    <td>' + data[i].sterm + '</td>'
                    + '    <td>' + data[i].sdate + '</td>'
                    + '    <td>' + repaystat + '</td>'
                    + '</tr>';
                items += item;
            }
            $("#new_dialogue tbody").html(items);
        }
    });
    //还款计划书下载
    $("#downloadimg").click(function () {
        downloadreturnplan(loanid, apptcapi, pinterate);
    });
}

function combineOptionLabels(data) {
    if (data && data.body && data.body.result && data.annex) {
        for (var i = 0; i < data.body.result.length; i++) {
            for (var labels in data.annex) {
                data.body.result[i][labels] = data.annex[labels][i];
            }
        }
        delete data.annex;
    }

}

//还款计划书的下载
function downloadreturnplan(loanid, apptcapi, pinterate) {
    $("#loanid").attr("value", loanid);
    $("#apptcapi").attr("value", apptcapi);
    $("#pinterate").attr("value", pinterate);
    $("#downloadreturnplanss").submit();
}

//显示还款计划书弹窗
function warrantReturnPlanDlg(projecttitle) {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var planhtml = "";
    planhtml = '<div class="alert_box_big">'
        + '        <div class="window_top">'
        + '            <div class="window_top_l">' + projecttitle + '还款计划</div>'
        + '            <div class="window_top_r">'
        + '                <img style="margin-top:16px;" id="downloadimg" src="' + ctx + '/static/kingkaid/images/download_btn.png" />'
        + '            </div>'
        + '            <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">'
        + '                <img src="' + ctx + '/static/kingkaid/images/window_close_bt.png" />'
        + '            </div>'
        + '        </div>'
        + '        <div class="window_content" style="height:440px;overflow-y:auto">'
        + '       <table class="return_plan" id="return_plan">'
        + '          	 <tr>'
        + '               	  <th class="plan_tb_first_column">投资金额</th>'
        + '                    <th style="width:100px;">年化利率</th>'
        + '                    <th style="width:95px;">应收利息</th>'
        + '                    <th style="width:100px;">应收本金</th>'
        + '                    <th style="width:100px;">应收本息</th>'
        + '                    <th style="width:93px;">还款期数</th>'
        + '                    <th style="width:125px;">下期还款日</th>'
        + '                    <th>状态</th>'
        + '                </tr>'
        + '            </table>'
        + '        </div>'
        + '    </div>';
    visual.innerHTML = planhtml;
    document.body.appendChild(visual);
}


//start for 数字证书 弹出确认框
function showConfirmBoxOfRA(loanid, apprstate, prodid) {
    LoadObject();
    GetVersion();
    var CertSubjectDN = SelectCertificate();
    $.ajax({
        url: ctx + '/auth/usercenter/warrant/ChoiceRANext.html',// 跳转到
        // action
        data: {
            loanid: loanid,
            CertSubjectDN: CertSubjectDN,
            prodid: prodid
        },
        type: 'post',
        cache: false,
        dataType: 'json',
        success: function (data) {
            var prodid = data.prodid;
            var flag = data.flag;
            var state = data.state;
            if (state == "3") {
                alert("数字证书已更新，请及时前往安全中心证书管理页，下载证书。");
                return false;
            }
            if (flag == "1") {
                alert("您的电子证书已过期，请您至我的金开贷->安全中心->证书管理栏目中点击“更新证书”按钮，待更新成功后，点击“下载证书”按钮并重新安装。");
                return false;
            }

            if (data.respcode == "S") {
                // 跳转到申请证书页面 1、证书验证成功，老逻辑继续
                showConfirmBoxOfRANext(loanid, apprstate, prodid, flag);
            } else if ((data.respcode == "N")) {
                // 无证书提示申请证书，
                alert("您还没有申请数字证书，请先申请数字证书!");
            } else {
                //验证失败，提示错误
                alert("证书验证失败，请从新验证！");
            }

        },
        error: function () {
            alert("异常！");
        }
    });
}


//合同签订
function warrshowConfirmBox(loanid, apprstate, prodid) {

    showConfirmBoxOfRA(loanid, apprstate, prodid);

}

function showConfirmBoxOfRANext(loanid, apprstate, prodid) {
    $("#loanidcont").attr("value", loanid);
    $("#apprstate").attr("value", apprstate);
    $("#dealtype").attr("value", "2");//1:担保公司合同签订
    $("#prodid").attr("value", prodid);
    $("#continfoconfirm").submit();
}

function warrantConfirmBox(loanid) {
    create_bg();
    var agree_click = ' onclick=\"confirmApp(\'' + loanid + '\',' + '\'12\');\"';
    var refuse_click = ' onclick=\"confirmApp(\'' + loanid + '\',' + '\'25\');\"';

    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var prompthtml = '<div class="alert_box_small">'
        + '   <div class="window_top">'
        + '       <div class="window_top_l">提示</div>'
        + '       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="' + ctx + '/static/kingkaid/images/window_close_bt.png"/></div>'
        + '   </div>'
        + '   <div class="window_content">'
        + '       <div class="operate_content">'
        + '             <div class="status_img">'
        + '                 <img src="' + ctx + '/static/kingkaid/images/warming_bt.png"/>'
        + '             </div>'
        + '             <div class="result_msg">'
        + '您要确认担保该笔借款吗？'
        + '             </div>'
        + '       </div>'
        + '   </div>'
        + '   <div class="small_window_bottom">'
        + '       <div class="blue_btn"' + agree_click + '>同  意</div>'
        + '       <div class="gray_btn btn_right" ' + refuse_click + '>拒  绝</div>'
        + '       <div class="gray_btn btn_right" onclick="closeDIV(&quot;new_dialogue&quot;)">取  消</div>'
        + '   </div>'
        + '</div>';
    visual.innerHTML = prompthtml;
    document.body.appendChild(visual);
}

function moneyFormat(money) {
    var findex = money.length;
    findex = money.indexOf('.') > -1 ? money.indexOf('.') : findex;
    var newMoney = money.substring(findex, money.length);
    var count = 0;
    for (var i = findex - 1; i >= 0; i--) {
        if (count == 2 && i != 0) {
            newMoney = ',' + money[i] + newMoney;
            count = 0;
        } else {
            newMoney = money[i] + newMoney;
            count++;
        }
    }
    return newMoney;
}

//数字证书 验签 start
var CryptoAgency = "";

function LoadObject() {
    try {
        var eDiv = document.createElement("div");
        if (navigator.appName.indexOf("Internet") >= 0 || navigator.appVersion.indexOf("Trident") >= 0) {
            if (window.navigator.cpuClass == "x86") {
                eDiv.innerHTML = "<object id=\"CryptoAgent\" codebase=\"CryptoKit.Standard.x86.cab\" classid=\"clsid:2F9BEB71-4164-4837-99EE-ED8065B58658\"></object>";
            }
            else {
                eDiv.innerHTML = "<object id=\"CryptoAgent\" codebase=\"CryptoKit.Standard.x64.cab\" classid=\"clsid:EC380EBA-922E-41F8-89FF-2FE4DCD200E3\"></object>";
            }
        }
        else {
            eDiv.innerHTML = "<embed id=\"CryptoAgent\" type=\"application/npCryptoKit.standard.x86\" style=\"height: 0px; width: 0px\">";
        }
        document.body.appendChild(eDiv);
    }
    catch (e) {
        alert(e);
    }
    CryptoAgency = document.getElementById("CryptoAgent");
}

function SelectCertificate() {
    try {
        var SubjectFilter = "";
        var IssueFilter = "";
        var CertSubjectDN = CryptoAgency.SelectSignCertificate(SubjectFilter, IssueFilter);
        return CertSubjectDN;
    }
    catch (e) {
        var LastErrorDesc = CryptoAgency.GetLastErrorDesc();
        alert(LastErrorDesc);
    }
}

function GetVersion() {
    var DownloadURL = window.location.host;
    try {
        CryptoAgency.GetVersion();
    }
    catch (e) {
        alert("签名控件未安装，请先安装签名控件");
        if (window.navigator.cpuClass == "x86") {
            DownloadURL = DownloadURL + "/ra/CryptoKit.Standard.x86.exe";
            window.open(DownloadURL);
        }
        else {
            DownloadURL = DownloadURL + "/ra/CryptoKit.Standard.x64.exe";
            window.open(DownloadURL);
        }
    }
}

//数字证书 验签 end	

//拆标信息
function othMess(loanid) {
    otherMess();
    $.ajax({
        type: 'POST',
        url: ctx + '/auth/usercenter/myborrows/setApprStates',
        data: {"loanid": loanid},
        dataType: 'json',
        success: function (data) {
            $(".plan_tr").remove();
            for (var i = 0; i < data.length; i++) {
                var row = data[i];
                var content = '<tr class="plan_tr">';
                content += '		<td></td>';
                content += '		<td><div class="red_text">' + row.splittitle + '</div></td>';
                content += '		<td><div class="red_text">' + row.amt + '</div></td>';
                content += '		<td><div class="red_text">' + row.splitnote + '</div></td>';
                content += '</tr>';
                $("#return_plan").append(content);
            }
            $(".window_content").css("overflow", "hidden");
            $(".window_content").css("overflow-x", "hidden");
            $(".window_content").css("overflow-y", "auto");
            $(".window_content").height(500);
        }

    });
}

//显示附加信息弹窗
function otherMess() {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var planhtml = '<div class="alert_box_big">'
        + '   <div class="window_top">'
        + '       <div class="window_top_l">拆标信息</div>'
        + '       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="' + ctx + '/static/kingkaid/images/window_close_bt.png"/></div>'
        + '   </div>'
        + '   <div class="window_content" style="height:440px;">'
        + '       <table class="return_plan" id="return_plan">'
        + '          <tr>'
        + '               <th style="width:20px;"></th>'
        + '               <th style="width:80px;">子标的名称</th>'
        + '               <th style="width:80px;">拆分金额</th>'
        + '               <th style="width:80px;">拆标说明</th>'
        + '           </tr>'
        + '       </table>'
        + '   </div>'
        + '</div>';

    visual.innerHTML = planhtml;
    document.body.appendChild(visual);
}
