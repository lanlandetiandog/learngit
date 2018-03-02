// 获取areaSelection组件的文本内容
function area_selection_text (container_id) {
	var area_text = '';
	$("#"+ container_id + " [data-bind] :selected")
	.each(function(){
		if($(this).val() != "-1") {
			area_text += $(this).text();
		}
	});
	return area_text;
}
// 获取areaSelection组件中某一级别的值
function area_selection_value (container_id, level) {
	return $("#" + container_id + " [data-bind='" + level + "']").val();
}
// reset form
$.fn.resetForm = function() {
    return this.each(function() {
        // guard against an input with the name of 'reset'
        // note that IE reports the reset function as an 'object'
        if (typeof this.reset == 'function' || (typeof this.reset == 'object' && !this.reset.nodeType)) {
            this.reset();
        }
    });
};

/**
 * 【四舍五入】保留2位小数
 * @param x
 * @returns
 */
function changeTwoDecimal_f(x) {
    var f_x = parseFloat(x);
    if (isNaN(f_x)) {
        alert('请求参数不合法');
        return false;
    }
    var f_x = Math.round(x * 100) / 100;
    var s_x = f_x.toString();
    var pos_decimal = s_x.indexOf('.');
    if (pos_decimal < 0) {
        pos_decimal = s_x.length;
        s_x += '.';
    }
    while (s_x.length <= pos_decimal + 2) {
        s_x += '0';
    }
    return s_x;
}
// 统一处理ajax操作未登录请求
/*
$.ajaxSetup({
	cache: false,
	statusCode: {
        401: function() {
        	window.location.reload();
        },
        500: function() {
        	uncaughtErrorDlg();
        }
    }
});
*/

$.ajaxSetup({
	cache: false
});

//操作失败弹窗
function uncaughtErrorDlg() {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var errorthtml ='<div class="alert_box_small">'
             +'   <div class="window_top">'
             +'       <div class="window_top_l">提示</div>'
             +'       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="'+ctx+'/static/kingkaid/images/window_close_bt.png"/></div>'
             +'   </div>'
             +'   <div class="window_content">'
             +'       <div class="operate_content">'
             +'             <div class="status_img">'
             +'                 <img src="'+ctx+'/static/kingkaid/images/wrong_bt.png"/>'
             +'             </div>'
             +'             <div class="result_msg" style="line-height:36px">'
             +'                不好意思，操作失败啦。'
             +'             </div>'
             +'       </div>'
             +'   </div>'
             +'   <div class="small_window_bottom">'
             +'       <div class="blue_btn" onclick="javascript:window.location.href=\''+ctx+'\'">返回首页</div>'
             +'       <div class="blue_btn btn_right" onclick="javascript:window.location.reload()">刷新当前页面</div>'
             +'       <div class="blue_btn btn_right" onclick="closeDIV(&quot;new_dialogue&quot;)">留在本页</div>'
             +'   </div>'
             +'</div>';
        visual.innerHTML = errorthtml;
    document.body.appendChild(visual);
}
