package com.jkgroup.kingkaid.service.chinapnr.command;

import java.util.Map;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 解绑银行卡
 * @author pan
 *
 */
public class CommandUnBindCard implements HFCommand {

	@Override
	public FormData exec(Map<String, String> resultMap) {
		FormData formData = FormDataUtil.createInputForm("UnBindCard");
		FormDataUtil.setProperty(formData, "custacno", resultMap.get("CustId"));
		FormDataUtil.setProperty(formData, "bankcode", resultMap.get("BankId"));
		FormDataUtil.setProperty(formData, "bankacno", resultMap.get("CardId"));
		return ServiceClient.getResponseFormData(formData, "UnBindCard");
	}

}
