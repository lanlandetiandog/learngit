package com.jkgroup.kingkaid.service.xabank.command;

import java.util.Map;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.service.chinapnr.command.HFCommand;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 手动投标通知
 * @author pan
 *
 */
public class CommandCtpP2pManualBidResultSynz implements HFCommand{

	@Override
	public FormData exec(Map<String, String> resultMap) {
		FormData formData = FormDataUtil.createInputForm("CtpTenderCheck");
		FormDataUtil.setProperty(formData, "listid", resultMap.get("MER_PRI"));
		FormDataUtil.setProperty(formData, "transtat", resultMap.get("TRAN_STAT"));	//1成功  2失败
		return ServiceClient.getResponseFormData(formData, "CtpTenderCheck");
	}

}
