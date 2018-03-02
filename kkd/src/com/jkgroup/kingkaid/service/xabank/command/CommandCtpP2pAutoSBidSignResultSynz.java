package com.jkgroup.kingkaid.service.xabank.command;

import java.util.Map;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.service.chinapnr.command.HFCommand;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 自动投标开启/关闭 通知
 * @author pan
 *
 */
public class CommandCtpP2pAutoSBidSignResultSynz implements HFCommand{

	@Override
	public FormData exec(Map<String, String> resultMap) {
		FormData formData = FormDataUtil.createInputForm("CtpAutoBidSetNotify");
		FormDataUtil.setProperty(formData, "idno", resultMap.get("ID_NO"));
		FormDataUtil.setProperty(formData, "idname", resultMap.get("ID_NAME"));
		FormDataUtil.setProperty(formData, "opertype", resultMap.get("OPER_TYPE"));
		FormDataUtil.setProperty(formData, "transtat", resultMap.get("TRAN_STAT"));
		return ServiceClient.getResponseFormData(formData, "CtpAutoBidSetNotify");
	}

}
