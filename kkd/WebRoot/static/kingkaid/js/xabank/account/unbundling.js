$(document).ready(function(){
	$('#butsub').click(function() {
		if (validatePwd(pgeditor)) {
			showloading("#butsub");
			$.ajax({
				type: 'post',
				dataType: 'json',
				url: ctx + '/auth/xabank/getrk',
				success: function(data) {
					if (data.status) {
	    				$.ajax({
	    	    			type:'post',
	    	    			url:ctx + '/auth/cust/del_card.html',
	    	    			data: {	    		            	
	    		            	"transpwd": function() {
	    		            		    pgeditor.pwdSetSk(data.body.rcode);
		    		            		var pwd = pgeditor.pwdSmResult(data.body.kx, data.body.ky);
		    		            		return encodeURIComponent(pwd);
    		            			}()
	    		            	},
	    	    			dataType:'json',
	    	    			success:function(data) {
	    	 					if (data.code=='s') {
	    	 						hideloading();
	    	 						alert("您已成功解绑银行卡,为了您更便捷的体验,请您立即绑定新卡！");
	    	 						window.location.href = ctx + "/auth/usercenter/myproperty.html";
	    						} else {
	    							pgeditor.pwdclear();
	    							hideloading();
	    							alert(data.msg);
								}				   				
	    	    			},
	    	    			error: function() {
	    	    				pgeditor.pwdclear();
	    	    				hideloading();
	    	    				alert("系统繁忙，请稍候再试");
	    	    			}
	    	    		});
					} else {
						pgeditor.pwdclear();
						hideloading();
						alert(data.msg);						
					}
				},
				error: function() {
					pgeditor.pwdclear();
					hideloading();
					alert("系统繁忙，请稍候再试");
				}
			});
		}
	});
	
	function validatePwd(pge) {
        var pgeId = pge.settings.pgeId;
        $('#' + pgeId + "_str").find('em.error').remove();
        if (pge.pwdLength() == 0) {
            var error = $('<em>');
            error.text('请输入6位数字密码');
            error.addClass("error");
            error.appendTo($('#' + pgeId + "_str"));
            return false;
        }
        if (pge.pwdValid() == 1) {
            var error = $('<em>');
            error.text('密码不符合要求，请输入6位数字密码');
            error.addClass("error");
            error.appendTo($('#' + pgeId + "_str"));
            return false;
        }
        return true;
	}
	
});

function clearError(pgeId) {
    $('#' + pgeId + "_str").find('em.error').remove();
}

function showloading(btn) {
	if (btn == null || btn == undefined || $(btn).length == 0) return;
 	var left = $(btn).offset().left;
	var top = $(btn).offset().top;
 	var width = $(btn).outerWidth();
	var height = $(btn).height();
	var opts = {
		lines: 7, // The number of lines to draw
		length: 0, // The length of each line
		width: 9, // The line thickness
		radius: 8, // The radius of the inner circle
		corners: 1, // Corner roundness (0..1)
		rotate: 0, // The rotation offset
		direction: 1, // 1: clockwise, -1: counterclockwise
		color: '#000', // #rgb or #rrggbb or array of colors
		speed: 1, // Rounds per second
		trail: 81, // Afterglow percentage
		shadow: false, // Whether to render a shadow
		hwaccel: false, // Whether to use hardware acceleration
		className: 'spinner', // The CSS class to assign to the spinner
		zIndex: 2e9, // The z-index (defaults to 2000000000)
		top: '32%', // Top position relative to parent
		left: '50%' // Left position relative to parent
    };
    $('#ajax_spin').remove();
    $('body').append('<div id="ajax_spin" style="position:absolute;background:#FFF;filter:alpha(opacity=30);opacity:0.3"><div id="ajax_spin_inner" style="position:relative;height:50px;"></div></div>');
    $('#ajax_spin').css({
    	'top': top,
    	'left': left,
		'width': width,
		'height': height
    });
    var target = document.getElementById('ajax_spin_inner');
    new Spinner(opts).spin(target);
};

function hideloading() {
	$('#ajax_spin').remove();
};
