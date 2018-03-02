package com.jkgroup.kingkaid.service.chinapnr.command;

import java.util.Map;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 生利宝 账务处理 接口调用
 * @author pan
 *
 */
public class CommandFssTrans implements HFCommand{

	@Override
	public FormData exec(Map<String, String> resultMap) {
		FormData formData = FormDataUtil.createInputForm("FssTranCheck");
		FormDataUtil.setProperty(formData, "respcode", resultMap.get("RespCode"));
		FormDataUtil.setProperty(formData, "respdesc", resultMap.get("RespDesc"));
		FormDataUtil.setProperty(formData, "listid", resultMap.get("MerPriv"));
		FormDataUtil.setProperty(formData, "type", resultMap.get("TransType"));
		FormDataUtil.setProperty(formData, "transamt", resultMap.get("TransAmt"));
		return ServiceClient.getResponseFormData(formData, "FssTranCheck");
	}

}
