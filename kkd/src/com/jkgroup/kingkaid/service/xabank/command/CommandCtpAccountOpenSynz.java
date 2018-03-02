package com.jkgroup.kingkaid.service.xabank.command;

import java.util.Map;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.service.chinapnr.command.HFCommand;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 立标结果后台通知
 * @author pan
 *
 */
public class CommandCtpAccountOpenSynz implements HFCommand{

	@Override
	public FormData exec(Map<String, String> resultMap) {
		FormData formData = FormDataUtil.createInputForm("OpenPayCorpCheck");
		FormDataUtil.setProperty(formData, "orgaid", resultMap.get("ID_NO"));
		FormDataUtil.setProperty(formData, "custname", resultMap.get("ID_NAME"));
		FormDataUtil.setProperty(formData, "custacno", resultMap.get("ELE_ACCT_NO"));
		FormDataUtil.setProperty(formData, "isactive", resultMap.get("IS_ACTIVE"));
		FormDataUtil.setProperty(formData, "acctno", resultMap.get("ACCT_NO"));
		FormDataUtil.setProperty(formData, "bankname", resultMap.get("BANK_NAME"));
		FormDataUtil.setProperty(formData, "issignmobile", resultMap.get("IS_SIGN_MOBILE"));
		FormDataUtil.setProperty(formData, "regamt", resultMap.get("REG_AMT"));
		return ServiceClient.getResponseFormData(formData, "OpenPayCorpCheck");
	}

}
