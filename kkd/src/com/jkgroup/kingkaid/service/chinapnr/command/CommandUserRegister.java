package com.jkgroup.kingkaid.service.chinapnr.command;

import java.util.Map;

import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 *
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年6月4日 上午11:00:16
 */

public class CommandUserRegister implements HFCommand {
	@Override
	public FormData exec(Map<String, String> resultMap) {
		FormData inputFormData = FormDataUtil.createInputForm("MemberAuth");
		FormDataUtil.setProperty(inputFormData, "memberid", resultMap.get("MerPriv"));
		FormDataUtil.setProperty(inputFormData, "custname", resultMap.get("UsrName"));
		FormDataUtil.setProperty(inputFormData, "paperkind", resultMap.get("IdType"));
		FormDataUtil.setProperty(inputFormData, "paperid", resultMap.get("IdNo"));
		FormDataUtil.setProperty(inputFormData, "mobilenumber", resultMap.get("UsrMp"));
		FormDataUtil.setProperty(inputFormData, "email", resultMap.get("UsrEmail"));
		FormDataUtil.setProperty(inputFormData, "custacno", resultMap.get("UsrCustId"));
		FormData outData = ServiceClient.getResponseFormData(inputFormData, "MemberAuth");
		if(FormDataUtil.isSucceed(outData)) {
			User user = Utils.getCurrentUser();
			if(user != null) {
				user.setCustId((String) FormDataUtil.getProperty(outData, "custid"));
				user.setCustAcNo((String) FormDataUtil.getProperty(outData, "custacno"));
				user.setCustName((String) FormDataUtil.getProperty(outData, "custname"));
				user.setPaperKind((String) FormDataUtil.getProperty(outData, "paperkind"));
				user.setPaperId((String) FormDataUtil.getProperty(outData, "paperid"));
				// 5-已认证
				user.setMemberState("5");
			}
		}
		return outData;
	}
}
