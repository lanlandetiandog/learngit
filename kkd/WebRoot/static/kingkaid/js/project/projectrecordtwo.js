 $(document).ready(function(){		 
		
    	 $("#pager").simplePagination({
    		url : ctx+'/project/projectrecordtwo.html',
 	        items_per_page : 10,
 	        handle_data : function(data) {
 		    	$("#invest_list_loan").empty();
 		    	dataObj = data.body;
 		    	for(var i = 0; i < dataObj.result.length; i ++) {
 		    		var row = dataObj.result[i];
 		    		var content = '<li>';
 		    		content += '<table class="invest-content-tb">';
 		    		content += '	<tr style="height:15px;">';
 		    		content += '		<td colspan="7">';
 		    		content += '			<div style="margin-top: 16px;">';
 		    		content += '				<div class="car-loan-icon">'+row.prodname+'</div>';
 		    		if(row.projecttags.indexOf('2')>-1)
 		    		{content += '				<div class="mobile-icon">加息</div>';}
 		    		if(row.projecttags.indexOf('1')>-1)
 		    		{content += '				<div class="newcomer-icon">新手</div>';}
 		    		content += '			</div>';
 		    		content += '		</td>';
 		    		content += '	</tr>';
 		    		content += '	<tr>';
 		    		content += '		<td style="width:315px;">';
 		    		content += '			<div><a class="loan_name" href="loan_detail_page.html?loanid='+row.loanid+'">'+row.projecttitle+'</a></div>';
 		    		content += ' 			<div class="conpany_info">';
 		    		content += '				<img src="'+ctx+'/static/kingkaid/images/and.jpg"/>';
// 		    		content += '				<a class="warrant">'+row.custname+'</a>';
 		    		content += '				<a class="warrant" href="'+ctx+'/website/cooperation_deatil_temp.html?partnerid='+row.custid+'" target="_blank">'+row.custname+'</a>';
 		    		content += '			</div>';
 		    		content += '		</td>';
 		    		content += '		<td style="width:80px;">';
 		    		content += '			<div style="color:#333;">金额</div>';
 		    		content += '			<div style="color:#eb493d;">'+row.tcapi/10000+'万</div>';
 		    		content += '		</td>';
 		    		content += '		<td style="width:100px;">';
 		    		content += '			<div style="color:#333;">利率</div>';
 		    		if(row.intadd=='0' || row.intadd=="") 
 		    		content += '			<div style="color:#eb493d;">'+row.pinterate+'%</div>';
 		    		else
 		    		content += '			<div style="color:#eb493d;">'+row.pinterate+'%+'+row.intadd+'%</div>';
 		    		content += '		</td>';
 		    		content += '		<td style="width:87px;">';
 		    		content += '			<div>周期</div>';
 		    		content += '			<div>'+row.tterm+'个月</div>';
 		    		content += '		</td>';
 		    		content += '		<td style="width:150px;">';
 		    		content += '			<div>还款方式</div>';
 		    		content += '			<div>'+row.retukindname+'</div>';
 		    		content += '		</td>';
 		    		content += '		<td style="width:90px;"> ';
 		    		content += '			 <div id="progress'+row.loanid+'" class="cerclebox" style="background-position: -' + row.bidprogress*100*54 + 'px 0px;">' + changebidprogress(row.bidprogress*100) + '%</div>';
 		    		content += '		</td>';
 		    		content += '		<td style="width:100px;">';
 		    		if(row.apprstate == '15')
 	 		    		content += '			<div class="tender-btn" onclick="tender(\''+row.loanid+'\');">投标中</div>';
 		    		else if(row.apprstate == '14')	
 	 		    		content += '			<div class="not-btn" onclick="tender(\''+row.loanid+'\');">即将发布</div>';
 		    		else if(row.apprstate == '1X')	
 	 		    		content += '			<div class="not-btn" onclick="tender(\''+row.loanid+'\');">自动投标中</div>';
 		    		else if(row.apprstate == '06' ||  row.apprstate == '16'|| row.apprstate == '17' || row.apprstate == '18')	
 	 		    		content += '			<div class="grey-btn">已满标</div>';
 		      		else	
 	 	 	    	content += '			<div class="grey-btn">'+data.annex.apprstatelabel[i]+'</div>';
 		    		content += '		</td>';
 		    		content += '	 </tr>';
 		    		content += '</table>';
 		    		content += '</li>';
 		    		
 		    		$("#invest_list_loan").append(content);
 		    	}
 		    	return true;
 		    },
	 	    qcon_func : function() {
		    	return {
		    	    s : $("#sapprstate").val()
		    	};
		    }
 		});

});
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
 
