$(document).ready(function(){		 	 
    $("#usermenu_yqhy").addClass("user_menulist_href_hover");
    $("#menu_jkd").addClass("leftmenu_cur");  
    
    $.ajax({
        type: "POST",
        url: ctx+'/auth/activity/isVerti',
        dataType: "json",
        success: function(data) {
        	var isVerti = data.isVerti;
        	if(isVerti =='4'||isVerti =='5'){//未认证
        		alert("您还没有开通西安银行电子账户，请先开通！");
        		window.location = ctx+"/auth/usercenter/myjkd.html";
        	}
        }
    });
    
    
    
    $("#copy_btn").zclip({
    	path: ctx + '/static/plugin/js/ZeroClipboard.swf',
    	copy: $('#share_code').text(),
    	afterCopy: function() { alert("您已将邀请码复制至剪切板"); }
    });
    
});
 