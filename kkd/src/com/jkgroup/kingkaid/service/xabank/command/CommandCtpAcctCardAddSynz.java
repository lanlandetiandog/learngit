package com.jkgroup.kingkaid.service.xabank.command;

import java.util.Map;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.service.chinapnr.command.HFCommand;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 新增绑定银行卡通知
 * @author pan
 *
 */
public class CommandCtpAcctCardAddSynz implements HFCommand{

	@Override
	public FormData exec(Map<String, String> resultMap) {
		FormData formData = FormDataUtil.createInputForm("CtpBankCardInsert");
		FormDataUtil.setProperty(formData, "idno", resultMap.get("ID_NO"));
		FormDataUtil.setProperty(formData, "idtype", resultMap.get("ID_TYP"));
		FormDataUtil.setProperty(formData, "idname", resultMap.get("ID_NAME"));
		FormDataUtil.setProperty(formData, "isactive", resultMap.get("IS_ACTIVE"));
		FormDataUtil.setProperty(formData, "transtat", resultMap.get("TRAN_STAT"));
		FormDataUtil.setProperty(formData, "acctno", resultMap.get("ACCT_NO"));
		return ServiceClient.getResponseFormData(formData, "CtpBankCardInsert");
	}

}
