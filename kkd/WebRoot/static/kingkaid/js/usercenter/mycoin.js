$(document).ready(function() {
    $("#pager").simplePagination({
        url : ctx + '/auth/usercenter/getCoinHist',
        items_per_page : 10,
        handle_data : function(dataObj) {
	    	$("#coinhist_table tbody").empty();
	    	for(var i = 0; i < dataObj.result.length; i ++) {
	    		var rowContent = '<tr><td>'+dataObj.result[i].trandate+'</td><td>'
	    		    +dataObj.result[i].paraname+'</td><td>'
	    		    +(dataObj.result[i].operationtype === '0' ? '':'-')
	    		    +dataObj.result[i].coinamount+'</td><td>'
	    		    +dataObj.result[i].expirationdate + '</td><td>'
	    		    +dataObj.result[i].balance+'</td></tr>';
	            $("#coinhist_table tbody").append(rowContent);
	    	}
	    	return true;
	    },
	    qcon_func : function() {
	    	return {
	    		startDate : $("#q_start_date").val(),
	    	    endDate : $("#q_end_date").val()
	    	};
	    }
	});
    $("#search_code_btn").click(function() {
    	$("#pager").trigger("setPage", 0);
    });
    
	$("#usermenu_jkb").addClass("user_menulist_href_hover");
	$("#menu_jkd").addClass("leftmenu_cur");
});