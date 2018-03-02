var now = 0;
var tempInterval = null;
$(document).ready(function () {
    $("#pager").simplePagination({
        url: ctx + '/website/cooperationdeatildan.html',
        items_per_page: 10,
        handle_data: function (data) {
            window.clearInterval(tempInterval);
            $("#invest_list_loan").empty();
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
                content += '			<div><a class="loan_name" href="' + ctx + '/project/loan_detail_page.html?loanid=' + row.loanid + '">' + row.projecttitle + '</a></div>';
                content += ' 			<div class="conpany_info">';
                content += '				<img src="' + ctx + '/static/kingkaid/images/and.jpg"/>';
                content += '				<a class="warrant">' + row.custname + '</a>';
                content += '			</div>';
                content += '		</td>';
                content += '		<td style="width:100px;">';
                content += '			<div style="color:#333;">金额</div>';
                content += '			<div style="color:#eb493d;">' + row.tcapi / 10000 + '万</div>';
                content += '		</td>';
                content += '		<td style="width:80px;">';
                content += '			<div style="color:#333;">利率</div>';
                content += '			<div style="color:#eb493d;">' + row.pinterate + '%</div>';
                content += '		</td>';
                content += '		<td style="width:87px;">';
                content += '			<div>周期</div>';
                content += '			<div>' + row.tterm + '个月</div>';
                content += '		</td>';
                content += '		<td style="width:150px;">';
                content += '			<div>还款方式</div>';
                content += '			<div>' + row.retukindname + '</div>';
                content += '		</td>';
                content += '		<td style="width:90px;"> ';
//		    		content += '			 <div id="progress'+row.loanid+'" class="cerclebox" style="background-position:' +-row.bidprogress*100*54+'px 0px;+">'+(changebidprogress(row.bidprogress)*100)+'%</div>';
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
            //     // showSurplusTime(dataObj.result);
            // }, 1000);
            return true;
        },
        qcon_func: function () {
            return {
                s: $("#sapprstate").val(),
                gid: $("#partnerid").val()
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

function loadCercleData1() {
    $('.cerclebox001').radialIndicator({
        barColor: '#1b619c',
        barWidth: 3,
        initValue: 0,
        roundCorner: true,
        percentage: true,
        radius: 20
    });
}

function fn_LoadProgress() {
    $(".cerclebox001").each(function (index, element) {
        var div = $(this);
        var divdata = div.attr('data-value');

        var radObj = div.data('radialIndicator');

        radObj.animate(divdata);
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
 
 
