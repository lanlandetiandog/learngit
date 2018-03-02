package com.jkgroup.kingkaid.service.xabank.command;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.service.chinapnr.command.HFCommand;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

public class CommandCtpAuditResultSynz implements HFCommand {

	@Override
	public FormData exec(Map<String, String> resultMap) {
		FormData inData = FormDataUtil.createInputForm("OpenPayCorpAudit");
		FormDataUtil.setProperty(inData, "orgaid", resultMap.get("ID_NO"));
		FormDataUtil.setProperty(inData, "custname", resultMap.get("ID_NAME"));
		FormDataUtil.setProperty(inData, "unino", resultMap.get("UNI_NO"));
		String transUrl = resultMap.get("TRANS_URL");
		if (StringUtils.isNotEmpty(transUrl)) {
			try {
				FormDataUtil.setProperty(inData, "transurl", URLEncoder.encode(transUrl, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		FormDataUtil.setProperty(inData, "unistat", resultMap.get("UNI_STAT"));
		FormDataUtil.setProperty(inData, "auditopinion", resultMap.get("AUDIT_OPINION"));
		return ServiceClient.getResponseFormData(inData, "OpenPayCorpAudit");
	}

}
