package com.jkgroup.kingkaid.service.chinapnr.command;

import java.util.Map;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.PayConstant;

/**
 * 债权转让记账
 * @author pan
 *
 */
public class CommandCreditAssign implements HFCommand{

	@Override
	public FormData exec(Map<String, String> resultMap) {
		FormData formData = FormDataUtil.createInputForm("CreditAssignCheck");
		FormDataUtil.setProperty(formData, "listtype", PayConstant.LIST_TYPE_CREDIT);
		FormDataUtil.setProperty(formData, "busitype", PayConstant.BUSI_TYPE_CREDIT);
		FormDataUtil.setProperty(formData, "chanid", PayConstant.CHAN_CHINAPNR);
		FormDataUtil.setProperty(formData, "listid", resultMap.get("MerPriv"));
		FormDataUtil.setProperty(formData, "ordid", resultMap.get("OrdId"));
		FormDataUtil.setProperty(formData, "orddate", DateUtils.getDateFromStr(resultMap.get("OrdDate")));
		FormDataUtil.setProperty(formData, "respcode", resultMap.get("RespCode"));
		FormDataUtil.setProperty(formData, "respdesc", resultMap.get("RescDesc"));
		return ServiceClient.getResponseFormData(formData, "CreditAssignCheck");
	}

}
