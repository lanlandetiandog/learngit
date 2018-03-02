var now = 0;
var tempInterval = null;
$(document).ready(function () {
    //百度统计 start
    var allurl = window.location.href;
    baiducont(allurl);
    loan('');
    //百度统计 end
    $(".newstab_normal").click(function () {
        var curid = $(this).attr("id");
        $(this).addClass("newtab_cur").parent().siblings().find(".newstab_normal").removeClass("newtab_cur");
        if (curid == "plat-tab") {
            $(".platform_detail").show();
            $(".newscenter_detail").hide();
        } else {
            $(".platform_detail").hide();
            $(".newscenter_detail").show();
        }
    });

    $("#menu_home").addClass("leftmenu_cur");

});

function baiducont(allurl) {
    $.ajax({
        url: ctx + '/getallurl',
        data: {
            allurl: allurl
        },
        type: 'post',
        cache: false,
        dataType: 'json',
        success: function (data) {
        },
        error: function () {
        }
    });
}

function tender(loanid) {
    window.location.href = ctx + '/project/loan_detail_page.html?loanid=' + loanid;
}

function loan(prodid) {
    $.ajax({
        type: 'post',
        url: ctx + '/maininvest',
        data: 'id=' + prodid,
        dataType: 'json',
        items_per_page: 10,
        success: function (data) {
            window.clearInterval(tempInterval);
            var dataObj = data.body;
            $("#invest_list_loan").empty();
            var retukind = $("#retukind").attr("value");//还款方式
            var relretukind = ["201", "202", "203", "301"];//据还款方式显转字
            var prodstate = ["06", "10", "19"];//根据项目状态显示（预期）期限
            for (var i = 0; i < dataObj.length; i++) {
                var row = dataObj[i];
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
                content += '			<div><a class="loan_name" href="project/loan_detail_page.html?loanid=' + row.loanid + '">' + row.projecttitle + '</a></div>';
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
                content += '				<a class="warrant">' + row.custname + '</a>';
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
                        content += '			<div>' + row.btdate + '天</div>';
                    } else {
                        content += '			<div>预期' + row.btdate + '天</div>';
                    }
                } else {
                    content += '			<div>' + row.tterm + '个月</div>';
                }
                content += '		</td>';
                content += '		<td style="width:150px;">';
                content += '			<div>还款方式</div>';
                content += '			<div title=' + row.retukindname + '>' + row.retukindname + '</div>';
                content += '		</td>';
                content += '		<td style="width:90px;"> ';
                content += '			 <div id="progress' + row.loanid + '" class="cerclebox" style="background-position: -' + row.bidprogress * 100 * 54 + 'px 0px;">' + changebidprogress(row.bidprogress * 100) + '%</div>';
                content += '		</td>';
                content += '		<td style="width:100px;">';
                if (row.apprstate == '15')
                    content += '			<div class="tender-btn" onclick="tender(\'' + row.loanid + '\');">投标中</div>';
                else if (row.apprstate == '14')
                    content += '			<div id=\'' + row.loanid + 'Div\' class="not-btn">' + formatPubDate(row.pubbiddate) + '</div>';
                else if (row.apprstate == '1X')
                    content += '			<div class="not-btn" onclick="tender(\'' + row.loanid + '\');">自动投标中</div>';
                else if (row.apprstate == '06' || row.apprstate == '16' || row.apprstate == '17' || row.apprstate == '18')
                    content += '			<div class="grey-btn" ">已满标</div>';
                else
                    content += '			<div class="grey-btn" ">' + data.annex.apprstatelabel[i] + '</div>';
                content += '		</td>';
                content += '	 </tr>';
                content += '</table>';
                content += '</li>';
                //将js对象转化为jquery对象
                $content = $(content);
                $("#invest_list_loan").append($content);
            }
            // tempInterval = window.setInterval(function () {
            //     showSurplusTime(dataObj);
            // }, 1000);
            return true;
        },
        error: function () {
            alert('fail');
        }
    });
}

function formatPubDate(pubBidDate) {
    var pubDate = new Date(pubBidDate);
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

function changebidprogress(x) {
    var f_x = parseFloat(x);
    if (isNaN(f_x)) {
        return 0;
    }
    if (f_x == 0) {
        return 0;
    }
    f_x = Math.round(f_x);
    return (f_x < 1 ? 1 : f_x).toFixed(0);
}

