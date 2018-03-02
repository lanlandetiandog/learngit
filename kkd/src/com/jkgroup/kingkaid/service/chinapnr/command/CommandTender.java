package com.jkgroup.kingkaid.service.chinapnr.command;

import java.util.Map;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

public class CommandTender implements HFCommand{

	@Override
	public FormData exec(Map<String, String> resultMap) {
		FormData formData = FormDataUtil.createInputForm("TenderCheck");
		FormDataUtil.setProperty(formData, "listid", resultMap.get("MerPriv"));
		FormDataUtil.setProperty(formData, "freezetrxid", resultMap.get("FreezeTrxId"));
		FormDataUtil.setProperty(formData, "otherlistno", resultMap.get("OrdId"));
		FormDataUtil.setProperty(formData, "tendertrxid", resultMap.get("TrxId"));
		FormDataUtil.setProperty(formData, "respcode", resultMap.get("RespCode"));
		FormDataUtil.setProperty(formData, "respdesc", resultMap.get("RespDesc"));
		return ServiceClient.getResponseFormData(formData, "TenderCheck");
	}

}
