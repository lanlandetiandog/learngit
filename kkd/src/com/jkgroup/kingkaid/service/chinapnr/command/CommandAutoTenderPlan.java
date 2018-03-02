package com.jkgroup.kingkaid.service.chinapnr.command;

import java.util.Map;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 自动投标计划开启
 * @author pan
 *
 */
public class CommandAutoTenderPlan implements HFCommand{

	@Override
	public FormData exec(Map<String, String> resultMap) {
		FormData formData = FormDataUtil.createInputForm("AutoBidSetToggle");
		String custid = resultMap.get("MerPriv");
		FormDataUtil.setProperty(formData, "custid", custid);
		FormDataUtil.setProperty(formData, "ifon", "1");
		FormDataUtil.setProperty(formData, "ifthirdon", "1");
		return ServiceClient.getResponseFormData(formData, "AutoBidSetToggle");
	}

}
