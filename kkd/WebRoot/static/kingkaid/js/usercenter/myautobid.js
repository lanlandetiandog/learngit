$(document).ready(function () {
    $("#usermenu_zdtb").addClass("user_menulist_href_hover");
    $("#menu_jkd").addClass("leftmenu_cur");
    $(".search_link").click(function () {
        $(this).addClass("cur_invest_search").parent().siblings().find(".search_link").removeClass("cur_invest_search");
    });

    $("#p_off").click(function () {
        if (!$(this).hasClass("tab1")) {
            $(this).addClass("tab1");
            $("[name='product_set'].tab1").removeClass("tab1");
        }
    });

    $("[name='product_set']").click(function () {
        if (!$(this).hasClass("tab1")) {
            $(this).addClass("tab1");
            $("#p_off.tab1").removeClass("tab1");
        } else {
            $(this).removeClass("tab1");
            if ($("[name='product_set'].tab1").length === 0) {
                $("#p_off").addClass("tab1");
            }
        }
    });

    $("#t_off").click(function () {
        if (!$(this).hasClass("tab1")) {
            $(this).addClass("tab1");
            $("[name='term_set'].tab1").removeClass("tab1");
        }
    });

    $("[name='term_set']").click(function () {
        if (!$(this).hasClass("tab1")) {
            $(this).addClass("tab1");
            $("#t_off.tab1").removeClass("tab1");
        } else {
            $(this).removeClass("tab1");
            if ($("[name='term_set'].tab1").length === 0) {
                $("#t_off").addClass("tab1");
            }
        }
    });

    $("#b_off").click(function () {
        if (!$(this).hasClass("tab1")) {
            $(this).addClass("tab1");
            $("[name='bank_set'].tab1").removeClass("tab1");
        }
    });

    $("[name='bank_set']").click(function () {
        if (!$(this).hasClass("tab1")) {
            $(this).addClass("tab1");
            $("#b_off.tab1").removeClass("tab1");
        } else {
            $(this).removeClass("tab1");
            if ($("[name='bank_set'].tab1").length === 0) {
                $("#b_off").addClass("tab1");
            }
        }
    });

    $("#m_off").click(function () {
        if (!$(this).hasClass("tab1")) {
            $(this).addClass("tab1");
            $("#m_on_min").removeClass("tab1");
            $("#m_on_min").css("backgroundColor", "#e1e1e1");
            
            $("#m_on_max").removeClass("tab1");
            $("#m_on_max").css("backgroundColor", "#e1e1e1");
            
            $("#m_value_min").attr("readonly", true);
            $("#m_value_min").val(null);
            
            $("#m_value_max").attr("readonly", true);
            $("#m_value_max").val(null);
        }
    });

    $("#m_on_min").click(function () {
        if (!$(this).hasClass("tab1")) {
            $(this).addClass("tab1");
            $("#m_off").removeClass("tab1");
            $("#m_on_min").css("backgroundColor", "inherit");
            $("#m_value_min").attr("readonly", false);
            $("#m_on_min")[0].focus();
            $("#m_value_min")[0].focus();
        }
        
        if (!$("#m_on_max").hasClass("tab1")) {
            $("#m_on_max").addClass("tab1");
            $("#m_off").removeClass("tab1");
            $("#m_on_max").css("backgroundColor", "inherit");
            $("#m_value_max").attr("readonly", false);
        }
        
    });
    
    $("#m_on_max").click(function () {
        if (!$(this).hasClass("tab1")) {
            $(this).addClass("tab1");
            $("#m_off").removeClass("tab1");
            $("#m_on_max").css("backgroundColor", "inherit");
            $("#m_value_max").attr("readonly", false);
            $("#m_on_max")[0].focus();
            $("#m_value_max")[0].focus();
        }
        
        if (!$("#m_on_min").hasClass("tab1")) {
            $("#m_on_min").addClass("tab1");
            $("#m_off").removeClass("tab1");
            $("#m_on_min").css("backgroundColor", "inherit");
            $("#m_value_min").attr("readonly", false);
        }
    });

    $("#c_off").click(function () {
        if (!$(this).hasClass("tab1")) {
            $(this).addClass("tab1");
            $("#c_on").removeClass("tab1");
        }
    });

    $("#c_on").click(function () {
        if (!$(this).hasClass("tab1")) {
            $(this).addClass("tab1");
            $("#c_off").removeClass("tab1");
        }
    });

    $(".switch").click(function () {
        // turn off
        if ("1" === $("#if_on").val()) {
            closeAutoBidBox();
        } else {
            // turn on
            showTenderAgreement();
        }
    });

    $("#btn_save").click(function () {
        if ($("#m_on_min").hasClass("tab1")) {
            if (!/^\d+$/.test($("#m_value_min").val())) {
                $("#m_error").text("请填写单笔最低投资金额(正整数)");
                return;
            }
        }
        
        if ($("#m_on_max").hasClass("tab1")) {
            if (!/^\d+$/.test($("#m_value_max").val())) {
                $("#m_error").text("请填写单笔最大投资金额(正整数)");
                return;
            }
        }
        
        var maxval = $("#m_value_max").val();
        var minval = $("#m_value_min").val();
        
        if(parseFloat(minval) > parseFloat(maxval)) {
        	$("#m_error").text("单笔最低投资金额不能大于单笔最大投资金额");
        	return;
        }
        
        $.ajax({
            type: 'POST',
            dataType: "json",
            url: ctx + '/auth/usercenter/autobid/saveSetting',
            data: gatherData(),
            success: function (data) {
                if (data.status) {
                    successBox("自动投标设置保存成功！");
                } else {
                    errorBox(data.message);
                }
            }
        });
    });

    function gatherData() {
        var data = {
            "filter_careproduct": $("#p_off").hasClass("tab1") ? "0" : "1",
            "filter_careterm": $("#t_off").hasClass("tab1") ? "0" : "1",
            "filter_carebank": $("#b_off").hasClass("tab1") ? "0" : "1",
            "filter_ifcoupon": $("#c_off").hasClass("tab1") ? "0" : "1",
            "filter_carebidmax": $("#m_off").hasClass("tab1") ? "0" : "1",
        };
        if (data.filter_careproduct === "1") {
            data.filter_productset = $("#products .tab1").map(function () {
                return this.id.split("_")[1];
            }).get().join(",");
        }
        if (data.filter_carebank === "1") {
            data.filter_bankset = $("#banks .tab1").map(function () {
                return this.id.split("_")[1];
            }).get().join(",");
        }
        if (data.filter_careterm === "1") {
            data.filter_termset = $("#terms .tab1").map(function () {
                return this.id.split("_")[1];
            }).get().join(",");
        }
        if (data.filter_carebidmax === "1") {
            data.filter_bidminvalue = $("#m_value_min").val();
            data.filter_bidmaxvalue = $("#m_value_max").val();
        }
        return data;
    }

    function showTenderAgreement() {
        create_bg();
        var visual = document.createElement("div");
        visual.id = "new_dialogue";
        var planHtml = '<div class="alert_box_big">'
            + '   <div class="window_top">'
            + '       <div class="window_top_l">自动投标协议</div>'
            + '       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="' + ctx + '/static/kingkaid/images/window_close_bt.png"/></div>'
            + '   </div>'
            + '   <div class="window_content">'
            + '        <div class="agreement_box">'
            + '            <div class="agreement_title">自动投标功能说明及风险揭示</div>'
            + '            <p>'
            + '                   <div>一、自动投标功能说明</div>'
            + '                   <div>金开贷自动投标功能是为了方便一部分出借人及时完成投标，提高资金使用效率而设置的特殊功能。该功能具体操作规则为：</div>'
            + '                   <div>（一）出借人以金开贷会员账号及密码登录金开贷系统；</div>'
            + '                   <div>（二）选择并进入自动投标功能页面，在自动投标页面自主设定或选定预定投标的下列条件：</div>'
            + '                   <p>1.	单笔最大投资限额：设定允许的单笔最大投资金额。</p>'
            + '                   <p>2.	项目期限：选择借款项目的借款期限或可接受的借款期限区间。</p>'
            + '                   <p>3.	产品类型：借款项目的产品类型，担保贷、车贷、企业信用贷、商票贷等。</p>'
            + '                   <p>4.	项目归属地：借款项目的归属地。</p>'
            + '                   <p>5.	自动匹配加息券：是否自动投标过程中匹配使用出借人账户中已有的加息券。</p>'
            + '                   <div>（三）在借款人发布符合出借人条件的借款申请时，金开贷系统将自动按照出借人设定的自动投标条件参与投标。投标成功的，金开贷系统将自动将投标对应的款项，冻结在出借人的金开贷账户。此时，出借人的自动投标行为将被视为签署交易合同（即会员通过金开贷网络平台与金开贷以及其他会员就有关交易事项达成的相关协议或条款）的缔约行为。</div>'
            + '                   <div>（四）在定标时，出借人被冻结在其金开贷账户内的资金作为出借资金划转至借款人金开贷账户，供借款人支取。</div>'
            + '                   <div>（五）如因借款人的借款融资项目始终未满标，或因其他原因导致出借人自动投标不成功的，金开贷系统将会将冻结在出借人金开贷账户内的资金解冻，出借人无权要求金开贷就此承担任何法律及经济责任。</div>'
            + '                   <div>二、自动投标功能风险揭示</div>'
            + '                   <div>1．作为系统自动功能，自动投标功能由系统自动按照投标条件参与投标。出借人作为系统自动投标最终结果的交易合同的当事人（债权人）存在无法选择借款人（债务人），以及无法确定自动投标功能设置中存在投标条件区间设置的具体事项的风险。</div>'
            + '                   <div>2．投标条件系出借人自主设定，金开贷不会作任何修改，或在系统设定外替出借人做出任何意思表示，故出借人须独立承担相关的法律及经济风险。在出借人出借款项涉及的借款债权出现政策及法律风险、借款人及担保人违约风险，流动性风险等其他风险的情况下，出借人无权要求金开贷承担任何法律及经济责任。</div>'
            + '            </p>'
            + '            <div class="agreement_title">自动投标声明</div>'
            + '            <p>本人已经阅读《自动投标功能说明及风险揭示》，知晓、理解并掌握金开贷自动投标功能的功能设置，知晓并理解自动投标行为基于该功能设置所包含的所有法律、经济、操作风险。本人特此声明，本人自愿接受上述风险，本人开启该功能，并自动投标的行为，视为本人通过投标出借款项的真实意思表示，该意思表示在交易达成的情况下是为本人作为出借人签署交易合同的自愿行为。</p>'
            + '        </div>'
            + '   </div>'
            + '   <div class="window_bottom">'
            + '     <div style="float:right">'
            + '       <input id="read_chkbox" type="checkbox"/><label class="blue-text" style="margin-right:30px;">我已阅读</label>'
            + '       <div class="blue_btn" onclick="readchkboxsubmit()">确 认</div>'
            + '       <div class="gray_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">放 弃</div>'
            + '     </div>'
            + '   </div>'
            + '</div>';

        visual.innerHTML = planHtml;
        document.body.appendChild(visual);
    }

});

//开启自动投标确认
function readchkboxsubmit(){
	if($("#read_chkbox").attr('checked')){
		closeBox();
		if ('0' === $("#if_third_on").val() || '' === $("#if_third_on").val()  ) {
			showRechargeOverBox('自动投标计划-开启', '确认操作完成前请不要关闭该窗口！');
			$.ajax({
				url : ctx + '/auth/project/open_autobidset.html',// 跳转到
				type : 'post',
				cache : false,
				dataType : 'json',
				data : {
					'opertype' : 'S'
				},
				success : function(data) {
					if (data.code == 's' ) {
						window.open(data.url, '自动投标签约');
//					} else if (data.code == 's') {
//						alert("自动投标开启成功！");
//						window.location.reload();
					} else {
						alert(decodeURIComponent(data.msg));
						window.location.reload();
					}
				}
			});
		} else {
			 $.ajax({
			        type: 'POST',
			        dataType: "json",
			        url: ctx + '/auth/usercenter/autobid/toggle',
			        data: {
			            "ifOn": "1" ,
			            "ifThirdOn":"1"
			        },
			        success: function (data) {
			            if (data.status) {
			                successBox("自动投标功能开启成功！");
			            } else {
			                errorBox(data.message);
			            }
			        }
			 });
		}
	} else {
		alert("请阅读自动投标协议并同意！");
	}
}

function successBox(msg) {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var successhtml = '<div class="alert_box_small">'
        + '   <div class="window_top">'
        + '       <div class="window_top_l">操作成功</div>'
        + '   </div>'
        + '   <div class="window_content">'
        + '       <div class="operate_content">'
        + '             <div class="status_img">'
        + '                 <img src="' + ctx + '/static/kingkaid/images/right_bt.png"/>'
        + '             </div>'
        + '             <div class="result_msg">'
        + msg
        + '             </div>'
        + '       </div>'
        + '   </div>'
        + '   <div class="small_window_bottom">'
        + '       <div class="blue_btn" onclick="closeAndRefresh(&quot;new_dialogue&quot;)">确定</div>'
        + '   </div>'
        + '</div>';
    visual.innerHTML = successhtml;
    document.body.appendChild(visual);
}

function closeAutoBidBox() {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var prompthtml = '<div class="alert_box_small">'
        + '   <div class="window_top">'
        + '       <div class="window_top_l">提示</div>'
        + '       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="' + ctx + '/static/kingkaid/images/window_close_bt.png"/></div>'
        + '   </div>'
        + '   <div class="window_content">'
        + '       <div class="operate_content">'
        + '             <div class="status_img">'
        + '                 <img src="' + ctx + '/static/kingkaid/images/warming_bt.png"/>'
        + '             </div>'
        + '             <div class="result_msg">'
        + '                 你确定要关闭自动投标功能么？'
        + '             </div>'
        + '       </div>'
        + '   </div>'
        + '   <div class="small_window_bottom">'
        + '       <div class="blue_btn" onclick="sendCloseAutoBid(&quot;new_dialogue&quot;)">关闭自动投标</div>'
        + '       <div class="gray_btn btn_right" onclick="closeDIV(&quot;new_dialogue&quot;)">取 消</div>'
        + '   </div>'
        + '</div>';
    visual.innerHTML = prompthtml;
    document.body.appendChild(visual);
}

function errorBox(msg) {
    create_bg();
    var visual = document.createElement("div");
    visual.id = "new_dialogue";
    var errorthtml = '<div class="alert_box_small">'
        + '   <div class="window_top">'
        + '       <div class="window_top_l">提示</div>'
        + '       <div class="window_close_btn" onclick="closeDIV(&quot;new_dialogue&quot;)"><img src="' + ctx + '/static/kingkaid/images/window_close_bt.png"/></div>'
        + '   </div>'
        + '   <div class="window_content">'
        + '       <div class="operate_content">'
        + '             <div class="status_img">'
        + '                 <img src="' + ctx + '/static/kingkaid/images/wrong_bt.png"/>'
        + '             </div>'
        + '             <div class="result_msg">'
        + msg
        + '             </div>'
        + '       </div>'
        + '   </div>'
        + '   <div class="small_window_bottom">'
        + '       <div class="blue_btn" onclick="closeDIV(&quot;new_dialogue&quot;)">确定</div>'
        + '   </div>'
        + '</div>';
    visual.innerHTML = errorthtml;
    document.body.appendChild(visual);
}

function closeAndRefresh(boxid) {
    closeDIV(boxid);
    window.location.reload();
}

function sendCloseAutoBid(boxid) {
    closeDIV(boxid); 
    $.ajax({
        type: 'POST',
        dataType: "json",
        url: ctx + '/auth/usercenter/autobid/toggle',
        data: {
            "ifOn": "0",
            "ifThirdOn":"1" 
        },
        success: function (data) {
            if (data.status) {
                successBox("自动投标功能关闭成功！");
            } else {
                errorBox(data.message);
            }
        }
    });
    
}