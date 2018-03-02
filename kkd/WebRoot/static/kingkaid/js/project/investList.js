var now = 0;
var tempInterval = null;
$(document).ready(function () {
    $("#pager").simplePagination({
        url: ctx + '/project/invest_list.html',
        items_per_page: 10,
        handle_data: function (data) {
            window.clearInterval(tempInterval);
            $("#invest_list_loan").empty();
            var relretukind = ["201", "202", "203", "301"];//据还款方式显转字
            var prodstate = ["06", "10", "19"];//根据项目状态显示（预期）期限
            dataObj = data.body;
            for (var i = 0; i < dataObj.result.length; i++) {
                var row = dataObj.result[i];
                var content = '<li>';
                content += '<table class="invest-content-tb">';
                content += '	<tr style="height:15px;">';
                content += '		<td colspan="7">';
                content += '			<div style="margin-top: 16px;">';
                content += '				<div class="car-loan-icon">' + row.prodname + '</div>';
                if (row.projecttags.indexOf('2') > -1) {
                    content += '				<div class="mobile-icon">加息</div>';
                }
                if (row.projecttags.indexOf('1') > -1) {
                    content += '				<div class="newcomer-icon">新手</div>';
                }
                content += '			</div>';
                content += '		</td>';
                content += '	</tr>';
                content += '	<tr>';
                content += '		<td style="width:315px;">';
                if (jQuery.inArray(row.retukind, relretukind) > -1) {
                    content += ' 			<div class="turn" title="支持债权转让">';
                    content += '				<img src="' + ctx + '/static/kingkaid/images/mobile-iz.png"/>';
                    content += '			</div>';
                }
                content += '			<div><a class="loan_name" href="loan_detail_page.html?loanid=' + row.loanid + '">' + row.projecttitle + '</a></div>';
                content += ' 			<div class="conpany_info">';
                if (row.prodid == '100201') {
                    content += '				<img src="' + ctx + '/static/kingkaid/images/andcredit.png"/>';
                } else if (row.prodid == '100301') {
                    content += '				<img src="' + ctx + '/static/kingkaid/images/andfactoring.png"/>';
                } else if (row.prodid == '100401') {
                    content += '				<img src="' + ctx + '/static/kingkaid/images/andticket.png"/>';
                } else {
                    content += '				<img src="' + ctx + '/static/kingkaid/images/and.jpg"/>';
                }
                //	content += '				<span class="warrant">'+row.custname+'</span>';
                content += '				<a class="warrant" href="' + ctx + '/website/cooperation_deatil_temp.html?partnerid=' + row.custid + '" target="_blank">' + row.custname + '</a>';
                content += '			</div>';
                content += '		</td>';
                content += '		<td style="width:80px;">';
                content += '			<div style="color:#333;">金额</div>';
                content += '			<div style="color:#eb493d;">' + row.tcapi / 10000 + '万</div>';
                content += '		</td>';
                content += '		<td style="width:100px;">';
                content += '			<div style="color:#333;">利率</div>';
                if (row.intadd == '0' || row.intadd == "")
                    content += '			<div style="color:#eb493d;">' + row.pinterate + '%</div>';
                else
                    content += '			<div style="color:#eb493d;">' + row.pinterate + '%+' + row.intadd + '%</div>';
                content += '		</td>';
                content += '		<td style="width:87px;">';
                content += '			<div>周期</div>';
                if (row.prodid == '100401') {
                    if (jQuery.inArray(row.apprstate, prodstate) > -1) {
                        if (row.btdate < '0') {
                            content += '			<div>' + 0 + '天</div>';
                        } else {
                            content += '			<div>' + row.btdate + '天</div>';
                        }
                    } else {
                        if (row.btdate < '0') {
                            content += '			<div>预期' + 0 + '天</div>';
                        } else {
                            content += '			<div>预期' + row.btdate + '天</div>';
                        }
                    }
                } else {
                    content += '			<div>' + row.tterm + '个月</div>';
                }
                content += '		</td>';
                content += '		<td style="width:150px;">';
                content += '			<div>还款方式</div>';
                //	content += '			<div title='+row.retukindname+'>'+substr(row.retukindname,3)+'</div>';
                content += '			<div title=' + row.retukindname + '>' + row.retukindname + '</div>';
                content += '		</td>';
                content += '		<td style="width:90px;"> ';
                content += '			 <div id="progress' + row.loanid + '" class="cerclebox" style="background-position:-' + row.bidprogress * 100 * 54 + 'px 0px;">' + changebidprogress(row.bidprogress * 100) + '%</div>';
                content += '		</td>';
                content += '		<td style="width:100px;">';
                if (row.apprstate == '15')
                    content += '			<div class="tender-btn" onclick="tender(\'' + row.loanid + '\');">投标中</div>';
                else if (row.apprstate == '14')
                    content += '			<div id=\'' + row.loanid + 'Div\' class="not-btn">' + formatPubDate(row.pubbiddate) + '</div>';
                else if (row.apprstate == '1X')
                    content += '			<div class="not-btn" onclick="tender(\'' + row.loanid + '\');">自动投标中</div>';
                else if (row.apprstate == '06' || row.apprstate == '16' || row.apprstate == '17' || row.apprstate == '18')
                    content += '			<div class="grey-btn">已满标</div>';
                else
                    content += '			<div class="grey-btn">' + data.annex.apprstatelabel[i] + '</div>';
                content += '		</td>';
                content += '	 </tr>';
                content += '</table>';
                content += '</li>';
                $("#invest_list_loan").append(content);
            }
            // tempInterval = window.setInterval(function () {
            //     showSurplusTime(dataObj.result);
            // }, 1000);
            return true;
        },
        qcon_func: function () {
            return {
                p: $("#sprodid").val(),
                t: $("#stterm").val(),
                b: $("#sbankid").val(),
                s: $("#sapprstate").val()
            };
        }
    });

    $("#pager1").simplePagination({
        url: ctx + '/project/credit_transfer_list.html',
        items_per_page: 10,
        handle_data: function (dataObj) {
            $("#invest_list_credit").empty();
            for (var i = 0; i < dataObj.result.length; i++) {
                var row = dataObj.result[i];
                var content = '<li>';
                content += '<table class="invest-content-tb">';
                content += '<tr>';
                content += '    <td style="width:315px;">';
                if (row.transferstate == '0' && row.disabledate >= row.workdate) {
                    content += '        <div>';
                    content += '            <a class="transfer_name">';
                    content += '            <span>(转)</span>';
                    content += '            ' + row.projecttitle + '</a>';
                    content += '        </div>';
                } else {
                    content += '        <div>';
                    content += '            <a class="transfer_name" href="\credit_detail_page.html?ctid=' + row.creditortransferid + '">';
                    content += '            <span>(转)</span>';
                    content += '            ' + row.projecttitle + '</a>';
                    content += '        </div>';
                }
                content += '        <div class="conpany_info">';
                if (row.prodid == '100201') {
                    content += '				<img src="' + ctx + '/static/kingkaid/images/andcredit.png"/>';
                } else if (row.prodid == '100301') {
                    content += '				<img src="' + ctx + '/static/kingkaid/images/andfactoring.png"/>';
                } else if (row.prodid == '100401') {
                    content += '				<img src="' + ctx + '/static/kingkaid/images/andticket.png"/>';
                } else {
                    content += '				<img src="' + ctx + '/static/kingkaid/images/and.jpg"/>';
                }
                content += '				<a class="warrant" href="' + ctx + '/website/cooperation_deatil_temp.html?partnerid=' + row.dbcustid + '" target="_blank">' + row.dbcustname + '</a>';
                //	content += '            <span>'+row.dbcustname+'</span>';
                content += '        </div>';
                content += '    </td>';
                content += '    <td style="width:130px;">';
                content += '        <div style="color:#333;">转让价格</div>';
                content += '        <div style="color:#eb493d;">' + row.transfermoney + '</div>';
                content += '    </td>';
                content += '    <td style="width:110px;">';
                content += '        <div style="color:#333;">利率</div>';
                if (row.intadd == '0' || row.intadd == "")
                    content += '        <div style="color:#eb493d;">' + row.interate + '%</div>';
                else
                    content += '			<div style="color:#eb493d;">' + row.interate + '%+' + row.intadd + '%</div>';
                content += '    </td>';
                content += '    <td style="width:117px;">';
                content += '        <div>剩余期限</div>';
                if (row.overdate < '0')
                    content += '        <div>' + 0 + '天</div>';
                else
                    content += '        <div>' + row.overdate + '天</div>';
                content += '    </td>';
                content += '    <td style="width:150px;">';
                content += '        <div>转让人</div>';
                content += '        <div>' + fn_dealmembername(row.membername) + '</div>';
                content += '    </td>';
                content += '    <td style="width:100px;">';
                if (row.transferstate == '1')
                    content += '        <div class="not-btn">已转让</div>';
                if (row.transferstate == '0' && row.flag == '0')
                    content += '        <div class="tender-btn" onclick="javascript:window.location.href=\'credit_detail_page.html?ctid=' + row.creditortransferid + '\';">认购</div>';
                if (row.transferstate == '0' && row.flag == '1')
                    content += '        <div class="tender-btn" style="background-color:#666;">已失效</div>';
                if (row.transferstate == '2')
                    content += '        <div class="tender-btn" style="background-color:#666;">已失效</div>';
                if (row.transferstate == '3')
                    content += '        <div class="tender-btn" style="background-color:#666;">已取消</div>';
//  		    		alert(row.workdate+'workdate');
//   		    		alert(row.disabledate+'disabledate');
                content += '    </td>';
                content += '</tr>';
                content += '</table>';

                content += '</li>';

                $("#invest_list_credit").append(content);
            }
            return true;
        },
        qcon_func: function () {
            return {
                t: $("#stterm1").val(),
                b: $("#sbankid1").val(),
                s: $("#sapprstate1").val()
            };
        }
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

function substr(str, len) {
    if (str.length <= len) {
        return str;
    }
    return str.substring(0, len) + "...";
}

function tender(loanid) {
    window.location.href = ctx + '/project/loan_detail_page.html?loanid=' + loanid;
}

/**
 * 【处理用户名秘密】保留前后各2位，中间用3个*代替
 * @param x
 * @returns
 */
function fn_dealmembername(membername) {

    var vnamelength = membername.length;
    var vname1 = membername.substring(0, 2);
    var vname2 = '***';
    var vname3 = membername.substring(vnamelength - 2);

    return (vname1 + vname2 + vname3);
}

function counter() {
    var amount = $("#c_amount").val();
    var term = $("#c_term").val();
    var rate = $("#c_rate").val();
    var repayType = $("#c_repaytype").val();
    $.getJSON(ctx + '/counterBox?amount=' + amount + '&term=' + term + '&rate=' + rate + '&repayType=' + repayType, function (dataObj) {
        $("#counter_return_plan").show();
        $(".counter_tr").remove();
        for (var i = 0; i < dataObj.length; i++) {
            var row = dataObj[i];
            var content = '<tr class="counter_tr">';
            content += '		<td>' + row.term + '</td>';
            content += '		<td>' + row.amt + '</td>';
            content += '		<td>' + row.capi + '</td>';
            content += '		<td>' + row.inte + '</td>';
            content += '</tr>';
            $("#counter_return_plan").append(content);
        }
    });
}


function changebidprogress(x) {
    var f_x = parseFloat(x);
    if (isNaN(f_x)) {
        return 0;
    }
    if (f_x == 0) {
        return 0;
    }
    f_x = Math.round(f_x);
    return f_x < 1 ? 1 : f_x;
}