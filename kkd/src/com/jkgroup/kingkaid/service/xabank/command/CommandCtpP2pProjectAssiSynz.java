package com.jkgroup.kingkaid.service.xabank.command;

import java.util.Map;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.service.chinapnr.command.HFCommand;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 债权转让通知
 * @author pan
 *
 */
public class CommandCtpP2pProjectAssiSynz implements HFCommand{

	@Override
	public FormData exec(Map<String, String> resultMap) {
		FormData formData = FormDataUtil.createInputForm("CtpCreditAssignCheck");
		FormDataUtil.setProperty(formData, "listid", resultMap.get("MER_PRI"));
		FormDataUtil.setProperty(formData, "stat", resultMap.get("TRAN_STAT"));	//0 处理中 1 成功 2 失败
		return ServiceClient.getResponseFormData(formData, "CtpCreditAssignCheck");
	}

}
