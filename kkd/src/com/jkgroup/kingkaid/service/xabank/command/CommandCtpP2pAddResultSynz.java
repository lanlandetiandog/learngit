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
public class CommandCtpP2pAddResultSynz implements HFCommand{

	@Override
	public FormData exec(Map<String, String> resultMap) {
		FormData formData = FormDataUtil.createInputForm("ProjectAddNotify");
		FormDataUtil.setProperty(formData, "loanid", resultMap.get("PRO_ID"));
		FormDataUtil.setProperty(formData, "stat", resultMap.get("STAT"));
		return ServiceClient.getResponseFormData(formData, "ProjectAddNotify");
	}

}
