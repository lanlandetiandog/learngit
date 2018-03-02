$(document).ready(function() {
	var openpayForm = $("#openpayForm").validate({
		rules: {
			filter_custname: "required",
			filter_inducate: "required",
			filter_regamt: {
				required: true,
				pattern: /^\d+(\.\d{1,2})?$/
			},
			filter_leadcustname: "required",
			filter_leadphone:  {
				required: true,
				pattern: /^\d+$/
			},
			filter_leadcustpaperid: {
				required: true,
				pattern: /^\d{17}[0-9Xx]$/
			},
			filter_threeinone: "required",
			filter_orgaid: "required",
			filter_creveid: "required",
			filter_lreveid: "required",
			filter_instcredit: "required",
			filter_instcreditenddate: "required",
			filter_instcreditdate: "required",
			filter_comporg:"required",
			filter_isinner: "required",
			filter_appno: "required"
		},
		messages: {
			filter_custname: "请输入企业名称",
			filter_inducate: "请选择行业类别",
			filter_regamt: {
				required: "请输入企业注册资金",
				pattern: "请输入正确的注册资金(小数点后最多两位)"
			},
			filter_leadcustname: "请输入法人姓名",
			filter_leadphone: {
				required: "请输入法人联系电话",
				pattern: "电话号码只能由数字组成"
			},
			filter_leadcustpaperid: {
				required: "请输入法人身份证号码",
				pattern: "请输入正确的身份证号码"
			},
			filter_threeinone: "请确定企业是否三证合一",
			filter_orgaid: "请输入企业营业执照号",
			filter_creveid: "请输入国税税务登记号",
			filter_lreveid: "请输入地税税务登记号",
			filter_instcredit: "请输入机构信用代码证号",
			filter_instcreditenddate: "请输入机构信用代码证到期日",
			filter_instcreditdate: "请输入机构信用代码证发证日期",
			filter_comporg:"请输入组织机构代码证",
			filter_isinner: "请确定是否有西安银行账户",
			filter_appno: "请输入基本存款账户开户许可证核准号"
		},
		errorElement: "span",
    	errorPlacement: function(error, element) {
    		error.addClass("info1");
    		error.appendTo(element.parent());
    	}
	});
	
	$("#openpayForm").submit(function() {
		if (openpayForm.form()) {
			$("#dotting").addClass("dotting");
			$('#doSubmit').attr("disabled", true);
			$('#doSubmit').addClass("code_gray");
			$('#doSubmit').removeClass("subInfo");
			return true;
		} else {
			return false;
		}
	});
	
	$("#doCancel").click(function() {
		$('#cancelForm').submit();
	});
	
	$("#doNothing").click(function() {
        window.opener = null;
        window.open('', '_self');
        window.close();
	});
	
});