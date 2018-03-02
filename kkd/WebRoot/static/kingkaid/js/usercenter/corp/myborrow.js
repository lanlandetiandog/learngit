var now = 0;
var tempInterval = null;
$(document).ready(function () {
    $("#usermenu_jk").addClass("user_menulist_href_hover");
    $("#menu_jkd").addClass("leftmenu_cur");


    $("#pager").simplePagination({
        url: ctx + '/auth/usercenter/my_borrow.html?date=' + new Date(),
        items_per_page: 10,
        handle_data: function (data) {
            window.clearInterval(tempInterval);
            dataObj = data.body;
            $(".poj_tb_list").empty();
            var loadstate = ["06", "10", "17", "18", "19", "30"];//合同下载按钮的显示判断
            var lookplanstate = ["10", "19", "30"];//还款计划书按钮显示判断
            var proapprstate = ["20", "21", "22", "23", "24", "25", "26", "27"];//项目审批状态（各种拒绝）
            var prodstate = ["06", "10", "19"];//根据项目状态显示（预期）期限
            for (var i = 0; i < dataObj.result.length; i++) {
                var row = dataObj.result[i];
                var prodid = row.prodid;
                var prodicon = '';
                var prodtypeicon = '';
                if (prodid == '100101') {//担保贷，担
// 		    			prodicon = ' <div class="manage-loan-icon">担保贷</div> ';
                    prodtypeicon = 'and.jpg';
                } else if (prodid == '100102') {//车贷，担
// 		    			prodicon = ' <div class="car-loan-icon">车辆贷</div> ';
                    prodtypeicon = 'and.jpg';
                }

                var content = '<li>';
                content += '		<table class="poj_tb">';
                content += '			<tr>';
                content += '				<td style="width:97px;">';
                content += '					<div class="align_left">' + row.projecttitle + '</div>';
                content += '				</td>';
                content += '				<td style="width:80px;">';
                content += '					<div>' + MilliFormat(row.apptcapi) + '</div>';
                content += '				</td>';
                content += '				<td style="width:112px;">';
                if (row.intadd == '0' || row.intadd == "")
                    content += '			<div>' + row.pinterate + '%</div>';
                else
                    content += '			<div>' + row.pinterate + '%+' + row.intadd + '%</div>';
                content += '				</td>';
                content += '				<td style="width:80px;">';
                if (prodid == '100401') {
                    if (jQuery.inArray(row.apprstate, prodstate) > -1) {
                        content += '			<div>' + row.btdate + '天</div>';
                    } else {
                        content += '			<div>预期' + row.btdate + '天</div>';
                    }
                } else {
                    content += '					<div>' + row.apptterm + '个月</div>';
                }
                content += '				</td>';
                content += '				<td style="width:95px;">';
                content += '					<div>' + row.nextrepadate + '</div>';
                content += '				</td>';
                content += '				<td style="width:106px;">';
                content += '					<div>' + row.nextrepaamt + '</div>';
                content += '				</td>';
                content += '				<td style="width:92px;">';
                if (prodid == '100401') {
                    if (jQuery.inArray(row.apprstate, proapprstate) > -1) {
                        content += '					<div>' + row.btdate + '天</div>';
                    } else {
                        if (row.overplusdate < '0')
                            content += '			<div>' + 0 + '天</div>';
                        else
                            content += '			<div>' + row.overplusdate + '天</div>';
                    }
                } else {
                    content += '					<div>' + row.overpluscount + '期</div>';
                }
                content += '				</td>';
                content += '				<td style="width:70px;">';
                if (row.apprstate === '14')
                    content += '			<div id=\'' + row.loanid + 'Div\'>' + formatPubDate(row.pubbiddate) + '</div>';
                else
                    content += '					<div>' + data.annex.apprstatelabel[i] + '</div>';
                content += '				</td>';
                content += '			</tr>';
                content += '			<tr>';
                content += '				<td colspan="8">';
                content += '					<div class="conpany_info">';
                content += '						<div style="float:left;overflow:hidden;">';
                content += '							<div style="margin-bottom:10px;overflow:hidden">';
                content += prodicon;
                content += '							</div>';
                content += '				<div class="car-loan-icon">' + row.prodname + '</div>';
                if (row.projecttags.indexOf('2') > -1) {
                    content += '				<div class="mobile-icon">加息</div>';
                }
                if (row.projecttags.indexOf('1') > -1) {
                    content += '				<div class="newcomer-icon">新手</div>';
                }
                content += '						</div>';
                content += '						<div style="float:right">';
                if (row.apprstate == '10')
                    content += '							<div onclick="retuAheaInsert(\'' + row.loanid + '\',\'' + row.loanacno + '\')" class="car-loan-icon invest_right_btn">提前还款</div>';
                if (jQuery.inArray(row.apprstate, lookplanstate) > -1)
                    content += '							<div onclick="retuPlan(\'' + row.loanid + '\');" class="manage-loan-icon invest_right_btn">还款计划书</div>';
                if (row.apprstate == '08')
                    content += '                    <div onclick="showPromptBox(\'' + row.loanid + '\',\'' + row.apprstate + '\',\'' + row.prodid + '\')" class="car-loan-icon invest_right_btn">信息确认</div>';
                if (row.apprstate == '16')
                    content += '							<div onclick="showConfirmBox(\'' + row.loanid + '\',\'' + row.apprstate + '\')" class="car-loan-icon invest_right_btn">合同签订</div>';
                if (jQuery.inArray(row.apprstate, loadstate) > -1)
                    content += '							<div class="turn-loan-icon invest_right_btn" onclick="showPromptWindow(\'' + row.apprstate + '\',\'' + data.annex.apprstatelabel[i] + '\',\'' + row.loanid + '\',\'' + row.downloadurl + '\',\'' + row.filename + '\',\'' + row.prodid + '\')">下载合同</div>';
                if (row.flag == '1')
                    content += '					<div onclick="othMess(\'' + row.loanid + '\');" class="manage-loan-icon invest_right_btn">拆标信息</div>';
                content += '						 </div>';
                content += '					</div>';
                content += '				</td>';
                content += '			 </tr>';
                content += '		</table>';
                content += '</li>';

                $(".poj_tb_list").append(content);
            }
            // tempInterval = window.setInterval(function () {
                // showSurplusTime(dataObj.result);
            // }, 1000);
            return true;
        },
        qcon_func: function () {
            return {
                apprstate: $("#selectflag").attr("value")
            };
        }
    });


    $(".search_link").click(function () {
        var flag = $(this).attr("value");
        $("#selectflag").attr("value", flag);

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

function retuPlan(loanid) {
    returnPlan();
    $.getJSON(ctx + '/auth/plan?loanid=' + loanid + '&flag=1', function (dataObj) {
        $(".plan_tr").remove();
        for (var i = 0; i < dataObj.length; i++) {
            var row = dataObj[i];
            var repaystat = '';
            if (dataObj[i].repaystat == '1') {
                repaystat = '<img src="' + ctx + '/static/kingkaid/images/right_over.png"/>';
            }
            var content = '<tr class="plan_tr">';
            content += '		<td></td>';
            content += '		<td>' + row.sterm + '</td>';
            content += '		<td>' + row.sdate + '</td>';
            content += '		<td><div class="red_text">' + changeTwoDecimal_f(row.sumsi) + '</div></td>';
            content += '		<td><div class="red_text">' + changeTwoDecimal_f(row.scapi) + '</div></td>';
            content += '		<td><div class="red_text">' + changeTwoDecimal_f(row.sinte) + '</div></td>';
            content += '		<td><div class="red_text">' + changeTwoDecimal_f(row.bal) + '</div></td>';
            content += '		<td>' + repaystat + '</td>';
            content += '</tr>';
            $("#return_plan").append(content);
        }
        $(".window_content").css("overflow", "hidden");
        $(".window_content").css("overflow-x", "hidden");
        $(".window_content").css("overflow-y", "auto");
        $(".window_content").height(500);
    });

    $("#downloadimg").click(function () {
        downloadreturnplan(loanid);
    });
}

//公司客户还款计划书的下载
function downloadreturnplan(loanid) {
    $("#loanid").attr("value", loanid);
    $("#downloadreturnplanss").submit();
}

//显示还款计划书弹窗
function returnPlan() {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var planhtml = "";
    planhtml = '<div class="alert_box_big">'

        + '   <div class="window_top">'
        + '       <div class="window_top_l">还款计划书</div>'
        + '       <div class="window_top_r">'
        + '           <img style="margin-top:16px;" id="downloadimg" src="' + ctx + '/static/kingkaid/images/download_btn.png" />'
        + '       </div>'
        + '       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="' + ctx + '/static/kingkaid/images/window_close_bt.png"/></div>'
        + '   </div>'
        + '   <div class="window_content" style="height:440px;">'
        + '       <table class="return_plan" id="return_plan">'
        + '          <tr>'
        + '               <th style="width:20px;"></th>'
        + '               <th style="width:50px;">期次</th>'
        + '               <th style="width:80px;">还款日期</th>'
        + '               <th style="width:80px;">应还本息</th>'
        + '               <th style="width:80px;">应还本金</th>'
        + '               <th style="width:80px;">应还利息</th>'
        + '               <th style="width:80px;">贷款余额</th>'
        + '               <th style="width:65px;">还款状态</th>'
        + '           </tr>'
        + '       </table>'
        + '   </div>'
        + '</div>';

    visual.innerHTML = planhtml;
    document.body.appendChild(visual);
}

//确定借款信息（发标前）；flag:意见， 1-同意，0-拒绝
function confirmAjax(loanid, flag, apprstate, prodid) {
    $.ajax({
        url: ctx + '/auth/project/project_add.html',// 跳转到 action
        data: {
            loanid: loanid
        },
        type: 'post',
        cache: false,
        dataType: 'json',
        success: function (data) {
            if (data.code == 'f') {
                alert(data.msg);
            } else {
                $("#dark_bg").remove();
                $("#new_dialogue").remove();
                showRechargeOverBox('信息确认', '交易完成前请不要关闭该窗口！');
                window.open(data.url, '贷款确认');
            }
        },
        error: function () {
            alert("异常！");
        }
    });
    return;
}

//会员放款前确认;  flag:意见， 1-同意，0-拒绝
function ContConfirmAjax(loanid, flag) {
    $.ajax({
        url: ctx + '/auth/usercenter/my_borrow_loan_confirm.html',// 跳转到 action
        data: {
            loanid: loanid,
            flag: flag
        },
        type: 'post',
        cache: false,
        dataType: 'json',
        success: function (data) {
            if (data.status == true) {
                alert(data.message);
                window.location.reload();
            } else {
                alert(data.message);
            }
        },
        error: function () {
            alert("异常！");
        }
    });

}

//提前还款
function retuAheaInsert(loanid, loanacno) {
    if (!confirm("确认提前还款？")) {
        return;
    }


    $.ajax({
        url: ctx + '/auth/usercenter/my_borrow_loan_retuahea.html',// 跳转到 action
        data: {
            loanid: loanid,
            loanacno: loanacno
        },
        type: 'post',
        cache: false,
        dataType: 'json',
        success: function (data) {
            if (data.status) {
                $("#pager").trigger("currentPage");
                alert(data.message);
            } else {
                alert(data.message);
            }

        },
        error: function () {
            alert("异常！");
        }
    });

}


//千分位数据格式化
function MilliFormat(fSum) {
    //var re = new RegExp("^(-?\\d+)(\\.\\d+)?{1}"); //判断是否是浮点数
    var re = /^(-?[\d]+)|(-?[\d]+\.[\d]+)$/; //判断是否是浮点数
    var strSum;
    var behind = "00";
    if (re.test(fSum)) {
        strSum = new String(fSum);
    }
    else {
        return "";
    }

    if (strSum.indexOf(".") > -1) {
        behind = strSum.split(".")[1];
        strSum = strSum.split(".")[0];
    }
    var len;
    var result = "";
    var temp = "";
    len = strSum.length;
    while (len > 3) {
        temp = "," + strSum.substring(len - 3, len);
        strSum = strSum.substring(0, len - 3);
        result = temp + result;
        len = strSum.length;
    }
    if (len > 0) {
        result = strSum + result;
    }
    if (behind != "") {
        if (behind.length > 2) {
            result += "." + behind.substring(0, 2);
        }
        else {
            if (behind.length < 2) {
                result += "." + behind + "0";
            }
            else {
                result += "." + behind;
            }
        }
    }

    return result;
}

//确认提示
function confirmApp(loanid, apprstate, confirmText) {
    if (confirm('您确定要拒绝借款信息?')) {
        if ($('#new_dialogue').length !== 0)
            closeDIV('new_dialogue');
        $.ajax({
            type: 'POST',
            dataType: "json",
            url: ctx + '/auth/usercenter/myborrow/setApprState',
            data: {
                "loanid": loanid,
                "apprstate": apprstate
            },
            success: function (data) {
                if (data.status) {
                    $("#pager").trigger("currentPage");
                    alert('提交成功!');
                } else {
                    alert(data.message);
                }
            }
        });
    }
}

/*//会员放款前确认;
function showConfirmBox(loanid) {
    create_bg();
    var agree_click=' onclick=\"ContConfirmAjax(\''+loanid+'\','+ '\'1'+'\');\"';
    var refuse_click=' onclick=\"ContConfirmAjax(\''+loanid+'\','+ '\'0'+'\');\"';
    var confirmques = '您要同意该笔借款定标吗？';
    
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var prompthtml = "";
    prompthtml ='<div class="alert_box_small">'

             +'   <div class="window_top">'
             +'       <div class="window_top_l">提示</div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'       <div class="operate_content">'
             +'             <div class="status_img">'
             +'                 <img src="'+ctx+'/static/kingkaid/images/warming_bt.png"/>'
             +'             </div>'
             +'             <div class="result_msg">'
             +					confirmques
             +'             </div>'
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             //+'       <div class="blue_btn"></div>'
             +'       <div class="blue_btn"'+ agree_click +'>同  意</div>'
             +'       <div class="gray_btn btn_right" '+ refuse_click +'>拒  绝</div>'
             +'       <div class="gray_btn btn_right" onclick="closeDIV(&quot;new_dialogue&quot;)">取  消</div>'
             +'   </div>'
             +'</div>';

        visual.innerHTML = prompthtml;
    document.body.appendChild(visual);
}
*/

//证书验证
//start for 数字证书 弹出确认框
function showConfirmBoxOFTwo(loanid, apprstate, respcode, prodid, flag) {
    LoadObject();
    GetVersion();
    if (respcode == "E") {
        // 无证书提示申请证书，
        ContConfirmAlert(loanid);
    } else {
        if (flag == "1") {
            alert("您的电子证书已过期，请您至我的金开贷->安全中心->证书管理栏目中点击“更新证书”按钮，待更新成功后，点击“下载证书”按钮并重新安装。");
            return false;
        }
        var CertSubjectDN = SelectCertificate();
        $.ajax({
            url: ctx + '/auth/usercenter/ChoiceRANext.html',// 跳转到
            // action
            data: {
                loanid: loanid,
                CertSubjectDN: CertSubjectDN
            },
            type: 'post',
            cache: false,
            dataType: 'json',
            success: function (data) {
                if (data.respcode == "S") {
                    showConfirmBoxOfRANext(loanid, apprstate, prodid);
                } else if ((data.respcode == "F")) {
                    //验证失败，提示错误
                    alert("证书验证失败，请从新验证！");
                }
            },
            error: function () {
                alert("异常！");
            }
        });
    }
}

//first
function showConfirmBox(loanid, apprstate) {
    $.ajax({
        url: ctx + '/auth/usercenter/ChoiceHaveRA.html',// 跳转到
        data: {
            loanid: loanid,
        },
        type: 'post',
        cache: false,
        dataType: 'json',
        success: function (data) {
            var respcode = data.respcode;
            var prodid = data.prodid;
            var flag = data.flag;
            showConfirmBoxOFTwo(loanid, apprstate, respcode, prodid, flag);
        },
        error: function () {
            alert("异常！");
        }
    });
}

function showConfirmBoxOfRANext(loanid, apprstate, prodid) {

    $("#loanidcont").attr("value", loanid);
    $("#apprstate").attr("value", apprstate);
    $("#dealtype").attr("value", "1");//1:借款人合同签订
    $("#prodid").attr("value", prodid);
    $("#continfoconfirm").submit();

}

//会员信息确认;
function showPromptBox(loanid, apprstate, prodid) {
    create_bg();
    var agree_click = ' onclick=\"confirmAjax(\'' + loanid + '\',' + '\'1' + '\',\'' + apprstate + '\',\'' + prodid + '\');\"';
    var refuse_click = ' onclick=\"confirmApp(\'' + loanid + '\',' + '\'24' + '\',\'' + apprstate + '\',\'' + prodid + '\');\"';

    $.ajax({
        type: "POST",
        url: ctx + '/auth/usercenter/getBorrowCompanyDetailInfo',
        data: {
            loanid: loanid,
        },
        dataType: "json",
        success: function (data) {
            var visual = document.createElement("div");
            visual.id = "new_dialogue";
            var prompthtml = "";
            prompthtml = '<div class="alert_box_small" style="width:700px;top:8px;">';

            prompthtml += '   <div class="window_top">';
            if (data.prodid == '100301') {
                prompthtml += '       <div class="window_top_l">转让信息确认</div>';
            } else {
                prompthtml += '       <div class="window_top_l">借款信息确认</div>';
            }
            prompthtml += '       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="' + ctx + '/static/kingkaid/images/window_close_bt.png"/></div>';
            prompthtml += '   </div>';
            prompthtml += '   <div class="window_content" style="overflow:scroll;height:500px;">';
            prompthtml += '       <div class="operate_content_s" style="line-height:30px;width:600px;margin:0 auto;">';
            prompthtml += '             <div class="result_msg_s" style="width:600px;">';
            prompthtml += '                <ul>';
            prompthtml += '                 <li>';
            prompthtml += '                  <div style="font-size:16px;text-align:center;">申请企业基本情况</div>';
            prompthtml += '                  <div style="width:360px;float:left;height:300px;">';
            prompthtml += '                    <div>';
            if (data.prodid == '100301') {
                prompthtml += '                     <span>保理公司名称：' + data.custname + '</span>';
            } else {
                prompthtml += '                     <span>企业名称：' + data.custname + '</span>';
            }
            prompthtml += '                   </div>';
            prompthtml += '                   <div>';
            prompthtml += '                     <span>法人证件号码：' + data.leadcustpaperid + '</span>';
            prompthtml += '                   </div>';
            prompthtml += '                   <div>';
            prompthtml += '                     <span>企业证照号码：' + data.orgaid + '</span>';
            prompthtml += '                   </div>';
            prompthtml += '                   <div>';
            prompthtml += '                     <span>企业规模：' + data.corpsizesign + '</span>';
            prompthtml += '                   </div>';
            prompthtml += '                   <div>';
            prompthtml += '                     <span>经营范围：' + data.qyoperatescope + '</span>';
            prompthtml += '                   </div>';
            prompthtml += '                   <div>';
            prompthtml += '                     <span>公司地址：' + data.addr + '</span>';
            prompthtml += '                   </div>';
            prompthtml += '                  </div>';

            prompthtml += '                  <div style="width:240px;float:left;height:300px;">';
            prompthtml += '                    <div>';
            prompthtml += '                     <span>法人姓名：' + data.leadcustname + '</span>';
            prompthtml += '                   </div>';
            prompthtml += '                   <div>';
            prompthtml += '                     <span>公司行业：' + data.waykind + '</span>';
            prompthtml += '                   </div>';
            prompthtml += '                   <div>';
            prompthtml += '                     <span>注册资本：' + data.regifund + '万</span>';
            prompthtml += '                   </div>';
            prompthtml += '                   <div>';
            prompthtml += '                     <span>成立时间：' + data.comedate + '</span>';
            prompthtml += '                   </div>';
            prompthtml += '                   <div>';
            prompthtml += '                     <span>联系方式：' + data.corptel + '</span>';
            prompthtml += '                   </div>';
            prompthtml += '                  </div>';
            prompthtml += '                  </li>';
            prompthtml += '                   <li style="font-size:16px;text-align:center;">项目基本信息</li>';
            prompthtml += '                  <div style="width:360px;float:left;">';
            prompthtml += '                   <div>';
            prompthtml += '                     <span>项目名称：' + data.projecttitle + '</span>';
            prompthtml += '                   </div>';
            prompthtml += '                   <div>';
            if (data.prodid == '100301') {
                prompthtml += '                     <span>转让金额：' + (data.tcapi) / 10000 + '万</span>';
            } else {
                prompthtml += '                     <span>借款金额：' + (data.tcapi) / 10000 + '万</span>';
            }
            prompthtml += '                   </div>';
            prompthtml += '                   </div>';
            prompthtml += '                  <div style="width:240px;float:left;">';
            prompthtml += '                   <div>';
            if (data.intadd == 0 || data.intadd == '') {
                if (data.prodid == '100301') {
                    prompthtml += '                     <span>年溢价回购率：' + data.pinterate + '%</span>';
                } else {
                    prompthtml += '                     <span>借款利率：' + data.pinterate + '%</span>';
                }
            } else {
                if (data.prodid == '100301') {
                    prompthtml += '                     <span>年溢价回购率：' + (data.pinterate) + '%' + '+' + (data.intadd) + '%</span>';
                } else {
                    prompthtml += '                     <span>借款利率：' + (data.pinterate) + '%' + '+' + (data.intadd) + '%</span>';
                }
            }
            prompthtml += '                   </div>';
            prompthtml += '                   <div>';
            if (data.prodid == '100401') {
                prompthtml += '                     <span>约定还款日：' + data.enddate + '</span>';
            } else {
                prompthtml += '                     <span>项目期限：' + data.tterm + '个月</span>';
            }
            prompthtml += '                   </div>';
            prompthtml += '                   </div>';
            if (data.prodid == '100301') {
                prompthtml += '                   <div> </div>';
            } else {
                prompthtml += '                   <div>借款项目情况:</div>';
            }
            prompthtml += '                   <div>';
            prompthtml += '                     <span style="margin-left:28px;">' + data.detailnote + '</span>';
            prompthtml += '                   </div>';

            if (data.prodid != '100201' && data.prodid != '100301') {
                prompthtml += '                   <li style="font-size:16px;text-align:center;">借款项目还款来源</li>';
                prompthtml += '                   <div>';
                prompthtml += '                     <span style="margin-left:28px;">' + data.repafundsour + '</span>';
                prompthtml += '                   </div>';

                prompthtml += '                   <li style="font-size:16px;text-align:center;">企业征信情况</li>';
                prompthtml += '                   <div>';
                prompthtml += '                     <span style="margin-left:28px;">' + data.creditstate + '</span>';
                prompthtml += '                   </div>';
                if (data.prodid != '100401') {
                    prompthtml += '                   <li style="font-size:16px;text-align:center;">担保机构</li>';
                    prompthtml += '                   <div>';
                    prompthtml += '                     <span style="margin-left:28px;">' + data.dbcustname + '</span>';
                    prompthtml += '                   </div>';
                }
            }
            prompthtml += '                   <li style="text-align:center;font-size:16px;">申请企业声明</li>';
            prompthtml += '                   <div>本公司声明：</div>';
            prompthtml += '                   <div>';
            prompthtml += '                     <span>1、贵机构经办项目经理已就上述情况与本公司进行了面谈。</span>';
            prompthtml += '                   </div>';
            prompthtml += '                   <div>';
            prompthtml += '                     <span>2、申请表中填写内容、资料均属真实，如有隐瞒或虚构，本公司将承担一切法律和经济后果。本公司同时授权贵机构向有关方面查询、核实相关情况。</span>';
            prompthtml += '                   </div>';
            prompthtml += '                </ul>';
            prompthtml += '             </div>';
            prompthtml += '       </div>';
            prompthtml += '   </div>';
            prompthtml += '   <div class="small_window_bottom" style="padding:15px 0;">';
            prompthtml += '       <div class="blue_btn"' + agree_click + '>同  意</div>';
            prompthtml += '       <div class="gray_btn btn_right" ' + refuse_click + '>拒  绝</div>';
            prompthtml += '       <div class="gray_btn btn_right" onclick="closeDIV(&quot;new_dialogue&quot;)">取  消</div>';
            prompthtml += '   </div>';
            prompthtml += '</div>';

            visual.innerHTML = prompthtml;
            document.body.appendChild(visual);
        }
    });
}

//提示框方法，提示还没有申请证书
//按钮“申请”，跳转申请证书
function ContConfirmAlert(loanid) {
    create_bg();
    var agree_click = ' onclick=\"ContConfirmAjaxForRC1101(\'' + loanid + '\','
        + '\'1' + '\');\"';
    var down_click = ' onclick=\"ContConfirmAjaxForRC2101(\'' + loanid + '\',' + '\'1' + '\');\"';
    var operate_click = ' onclick=\"ContConfirmAjaxForRC2401(\'' + loanid + '\',' + '\'1' + '\');\"';
    var confirmques = '您还没有进行数字证书认证，请先申请并下载数字证书。';
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var prompthtml = "";
    prompthtml = '<div class="alert_box_small">'

        + '   <div class="window_top">'
        + '       <div class="window_top_l">提示</div>'
        + '       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'
        + ctx + '/static/kingkaid/images/window_close_bt.png"/></div>'
        + '   </div>' + '   <div class="window_content">'
        + '       <div class="operate_content">'
        + '             <div class="status_img">'
        + '                 <img src="' + ctx
        + '/static/kingkaid/images/warming_bt.png"/>'
        + '             </div>' + '             <div class="result_msg">'
        + confirmques + '             </div>' + '       </div>'
        + '   </div>' + '   <div class="small_window_bottom">'
        + '		  <div id="down_click" class="blue_btn"' + down_click + '>申 请</div>'
        + '		  <div id="operate_click" class="blue_btn"' + operate_click + '>下 载</div>'
        + '       <div id="agree_click" class="blue_btn"' + agree_click + '>申请并下载</div>'
        + '   </div>' + '</div>';
    +'   </div>' + '</div>';

    visual.innerHTML = prompthtml;
    document.body.appendChild(visual);

}

//2101申请证书
function ContConfirmAjaxForRC2101(loanid) {
    $('#down_click').attr("disabled", true);
    $.ajax({
        url: ctx + '/auth/usercenter/ContForRC2101.html',
        data: {
            loanid: loanid
        },
        type: 'post',
        cache: false,
        dataType: 'json',
        success: function (data) {
            if (data.respcode != "0000") {
                //申请证书失败
                alert(data.respdesc);
            } else {
                alert("单证书申请成功");
                window.location.reload();
                return;
            }
        },
        error: function () {
            alert("异常！");
        }
    });
}

//2401 制证
function ContConfirmAjaxForRC2401(loanid) {
    $('#operate_click').attr("disabled", true);
    OnLoad();
    GetVersion2();
    var p10 = PKCS10Requisition_SingleCert();

    $.ajax({
        url: ctx + '/auth/usercenter/ContForRC2401.html',
        data: {
            loanid: loanid,
            p10: p10
        },
        type: 'post',
        cache: false,
        dataType: 'json',
        success: function (data) {
            if (data.respcode != "0000") {
                //製作证书失败
                alert(data.respdesc);
                window.location.reload();
            } else {
                // 申请证书成功
                // 成功后执行生成证书
                //2.2.7
                var signCert = data.signaturecert;
                var contianerName = CryptoAgent2.CFCA_GetContainer();
                if (!contianerName) {
                    var errorDesc = CryptoAgent2.GetLastErrorDesc();
                    alert(errorDesc);
                    return;
                }
                //2.2.8
                var bResult = CryptoAgent2.CFCA_ImportSignCert(1, signCert, contianerName);
                if (!bResult) {
                    var errorDesc = CryptoAgent2.GetLastErrorDesc();
                    alert(errorDesc);
                    return;
                }
                alert("单证书安装成功");
                window.location.reload();
                return;
            }

        },
        error: function () {
            alert("异常！");
        }
    });
}

//数字证书申请调用三方操作。
function ContConfirmAjaxForRC1101(loanid) {
    $('#agree_click').attr("disabled", true);
    OnLoad();
    GetVersion2();
    var p10 = PKCS10Requisition_SingleCert();

    $.ajax({
        url: ctx + '/auth/usercenter/ContForRC1101.html',
        data: {
            loanid: loanid,
            p10: p10
        },
        type: 'post',
        cache: false,
        dataType: 'json',
        success: function (data) {
            if (data.respcode != "0000") {
                //申请证书失败，提示申请证书，
                alert(data.respdesc);
                window.location.reload();
            } else {
                // 申请证书成功
                // 成功后执行生成证书
                //2.2.7
                var signCert = data.varchar_14;
                var contianerName = CryptoAgent2.CFCA_GetContainer();
                if (!contianerName) {
                    var errorDesc = CryptoAgent2.GetLastErrorDesc();
                    alert(errorDesc);
                    return;
                }
                //2.2.8
                var bResult = CryptoAgent2.CFCA_ImportSignCert(1, signCert, contianerName);
                if (!bResult) {
                    var errorDesc = CryptoAgent2.GetLastErrorDesc();
                    alert(errorDesc);
                    return;
                }
                alert("单证书安装成功");
                window.location.reload();
                return;
            }

        },
        error: function () {
            alert("异常！");
        }
    });
}


//数字证书 集成p10 start
//若为32为操作系统
var CryptoAgent2 = "";

function OnLoad() {
    if (navigator.appName.indexOf("Internet") >= 0
        || navigator.appVersion.indexOf("Trident") >= 0) {
        if (window.navigator.cpuClass == "x86") {
            document.getElementById("FakeCryptoAgent").innerHTML = "<object id=\"CryptoAgent2\" codebase=\"CryptoKit.CertEnrollment.Pro.x86.cab\" classid=\"clsid:71BB5253-EF2B-4C5B-85FF-1FD6A03C29A6\" ></object>";

        } else {
            document.getElementById("FakeCryptoAgent").innerHTML = "<object id=\"CryptoAgent2\" codebase=\"CryptoKit.CertEnrollment.Pro.x64.cab\" classid=\"clsid:9E7B8F05-ADBE-4067-ABC6-28E0230A5C18\" ></object>";
        }
    } else {
        document.getElementById("FakeCryptoAgent").innerHTML = "<embed id=\"CryptoAgent2\" type=\"application/npCryptoKit.CertEnrollment.Pro.x86\" style=\"height: 0px; width: 0px\">";
    }
    var KenAlgorithm = "RSA";
    KenAlgorithm[0].selected = "selected";
    KenAlgorithm[1].selected = "";
    CryptoAgent2 = document.getElementById("CryptoAgent2");
}

function PKCS10Requisition_SingleCert() {
    try {
        var keyAlgorithm = "RSA";
        var selectValue = "SHA1";
        var keyLength = 2048;
        var cspName = "Microsoft Enhanced Cryptographic Provider v1.0";
//		var cspName = "CFCA FOR UKEY CSP v1.1.0";
        var strSubjectDN = "CN=certRequisition,O=CFCA TEST CA,C=CN";
        var res1 = CryptoAgent2.CFCA_SetCSPInfo(keyLength,
            cspName);
        if (!res1) {
            var errorDesc = CryptoAgent2.GetLastErrorDesc();
            alert(errorDesc);
            return;
        }
        var res2 = CryptoAgent2
            .CFCA_SetKeyAlgorithm(keyAlgorithm);
        if (!res2) {
            var errorDesc = CryptoAgent2.GetLastErrorDesc();
            alert(errorDesc);
            return;
        }

        var pkcs10Requisition = 0;
        if (keyAlgorithm == "RSA") {
            // RSA单证 sha1
            pkcs10Requisition = CryptoAgent2
                .CFCA_PKCS10CertRequisition(
                    strSubjectDN, 1, 0);
            // }
        } else {
            // SM2单证
            pkcs10Requisition = CryptoAgent2
                .CFCA_PKCS10CertRequisition(
                    strSubjectDN, 1, 0);
        }

        if (!pkcs10Requisition) {
            var errorDesc = CryptoAgent2.GetLastErrorDesc();
            alert(errorDesc);
            return;
        }
        //	alert("g176 p10==" + pkcs10Requisition);
        return pkcs10Requisition;
        // ...need to sent pkcs10 requisition to CA
    } catch (e) {
        var LastErrorDesc = CryptoAgent2.GetLastErrorDesc();
        alert(LastErrorDesc);
    }
}

//end for 数字证书 弹出确认框
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
    var DownloadURL = document.getElementById("ctlurlValue").value;
    try {
        CryptoAgency.GetVersion();
    }
    catch (e) {
        alert("签名控件未安装，请先安装签名控件");
        if (window.navigator.cpuClass == "x86") {
            DownloadURL = DownloadURL + "/CryptoKit.Standard.x86.exe";
            window.open(DownloadURL);
        } else {
            DownloadURL = DownloadURL + "/CryptoKit.Standard.x64.exe";
            window.open(DownloadURL);
        }
    }
}

function GetVersion2() {
//  var DownloadURL= window.location.host;   
    var DownloadURL = document.getElementById("ctlurlValue").value;
    try {
        CryptoAgent2.CFCA_HavePermissiontoGenerateKeyPair();
    }
    catch (e) {
        alert("下载控件未安装，请先安装下载控件");
        if (window.navigator.cpuClass == "x86") {
            DownloadURL = DownloadURL + "/CryptoKit.CertEnrollment.Pro.x86.exe";
            window.open(DownloadURL);
        } else {
            DownloadURL = DownloadURL + "/CryptoKit.CertEnrollment.Pro.x64.exe";
            window.open(DownloadURL);
        }
    }
}

//数字证书 验签 end

//附加信息
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
    var planhtml = "";
    planhtml = '<div class="alert_box_big">'
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