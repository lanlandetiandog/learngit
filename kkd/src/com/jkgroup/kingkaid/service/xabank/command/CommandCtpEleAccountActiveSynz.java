package com.jkgroup.kingkaid.service.xabank.command;

import java.util.Map;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.service.chinapnr.command.HFCommand;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 客户开户后台异步通知
 * @author pan
 *
 */
public class CommandCtpEleAccountActiveSynz implements HFCommand{

	@Override
	public FormData exec(Map<String, String> resultMap) {
		FormData formData = FormDataUtil.createInputForm("OpenPayUpdate");
		FormDataUtil.setProperty(formData, "idtype", resultMap.get("ID_TYP"));
		FormDataUtil.setProperty(formData, "idno", resultMap.get("ID_NO"));
		FormDataUtil.setProperty(formData, "idname", resultMap.get("ID_NAME"));
		FormDataUtil.setProperty(formData, "eacctno", resultMap.get("ELE_ACCT_NO"));
		FormDataUtil.setProperty(formData, "isactive", resultMap.get("IS_ACTIVE"));
		FormDataUtil.setProperty(formData, "acctno", resultMap.get("ACCT_NO"));
		formData = ServiceClient.getResponseFormData(formData, "OpenPayUpdate");
		return formData;
	}

}
