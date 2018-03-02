package com.jkgroup.kingkaid.service.xabank.command;

import java.util.Map;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.service.chinapnr.command.HFCommand;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 获取入账流水号
 * @author pan
 *
 */
public class CommandCtpAcctNotifyGetNoSynz implements HFCommand{

	@Override
	public FormData exec(Map<String, String> resultMap) {
		FormData formData = FormDataUtil.createInputForm("CtpAcctNotifyGetNo");
		FormDataUtil.setProperty(formData, "listkind", resultMap.get("LIST_KIND"));
		FormDataUtil.setProperty(formData, "transamt", resultMap.get("TRANS_AMT"));
		FormDataUtil.setProperty(formData, "idtyp", resultMap.get("ID_TYP"));
		FormDataUtil.setProperty(formData, "idname", resultMap.get("ID_NAME"));
		FormDataUtil.setProperty(formData, "idno", resultMap.get("ID_NO"));
		return ServiceClient.getResponseFormData(formData, "CtpAcctNotifyGetNo");
	}

}
