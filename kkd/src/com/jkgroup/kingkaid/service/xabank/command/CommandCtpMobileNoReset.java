package com.jkgroup.kingkaid.service.xabank.command;

import java.util.Map;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.service.chinapnr.command.HFCommand;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 接收西安银行账务通知入账
 * @author pan
 *
 */
public class CommandCtpMobileNoReset implements HFCommand{

	@Override
	public FormData exec(Map<String, String> resultMap) {
		
		FormData formData = FormDataUtil.createInputForm("CtpAcctMobileNotify");
		FormDataUtil.setProperty(formData, "idno", resultMap.get("ID_NO"));
		FormDataUtil.setProperty(formData, "idname", resultMap.get("ID_NAME"));
		FormDataUtil.setProperty(formData, "idtype", resultMap.get("ID_TYP"));
		FormDataUtil.setProperty(formData, "newmobile", resultMap.get("NEW_MOBILE"));
		FormDataUtil.setProperty(formData, "transstat", resultMap.get("TRAN_STAT"));
		FormDataUtil.setProperty(formData, "regamt", resultMap.get("REG_AMT"));
		return ServiceClient.getResponseFormData(formData, "CtpAcctMobileNotify");
	}

}
