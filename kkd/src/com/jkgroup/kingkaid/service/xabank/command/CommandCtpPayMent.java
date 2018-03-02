package com.jkgroup.kingkaid.service.xabank.command;

import java.util.Map;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.service.chinapnr.command.HFCommand;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

public class CommandCtpPayMent implements HFCommand{

	@Override
	public FormData exec(Map<String, String> resultMap) {
		FormData formData = FormDataUtil.createInputForm("CtpByVipCheck");
		FormDataUtil.setProperty(formData, "listid", resultMap.get("MER_PRI"));
		FormDataUtil.setProperty(formData, "transstat", resultMap.get("TRAN_STAT"));
		formData = ServiceClient.getResponseFormData(formData, "CtpByVipCheck");
		
		String respcode = String.valueOf(FormDataUtil.getProperty(formData, "respcode"));
		if(respcode.equals(FormData.SUCCESS)){			
			User user = Utils.getCurrentUser();				
			if(user!= null){
				user.setVip(true);
			}		
		}
		return null;
	}

}
