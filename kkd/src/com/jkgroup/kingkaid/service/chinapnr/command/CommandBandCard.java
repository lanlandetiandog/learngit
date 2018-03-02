package com.jkgroup.kingkaid.service.chinapnr.command;

import java.util.Map;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

public class CommandBandCard implements HFCommand{

	@Override
	public FormData exec(Map<String, String> resultMap) {
		FormData formData = FormDataUtil.createInputForm("BankCardInsert");
		FormDataUtil.setProperty(formData, "custid", resultMap.get("MerPriv"));
		FormDataUtil.setProperty(formData, "custacno", resultMap.get("UsrCustId"));
		FormDataUtil.setProperty(formData, "bankcode", resultMap.get("OpenAcctId"));
		FormDataUtil.setProperty(formData, "bankacno", resultMap.get("OpenBankId"));
		ServiceClient.getResponseFormData(formData, "BankCardInsert");
		return null;
	}

}
