$(document).ready(function() {
	$('#jump').timer({
        format: '%s秒后自动进入我的金开贷',
        duration: '5s',
        countdown: true,
        callback: function() {
            $('#jump').timer('remove');
        	location.href = ctx+ "/auth/usercenter/myjkd.html";
        }
    });
});