package com.jkgroup.kingkaid.service.xabank.command;

import java.util.Map;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.service.chinapnr.command.HFCommand;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 接收西安银行账务通知入账
 * @author pan
 *
 */
public class CommandCtpAcctNotifyCheckSynz implements HFCommand{

	@Override
	public FormData exec(Map<String, String> resultMap) {
		FormData formData = FormDataUtil.createInputForm("CtpAcctNotifyCheck");
		FormDataUtil.setProperty(formData, "listkind", resultMap.get("LIST_KIND"));
		String listkind = resultMap.get("LIST_KIND");
		if(listkind.equals("A1") || listkind.equals("B1")){
			FormDataUtil.setProperty(formData, "listid", resultMap.get("LIST_ID"));			
		}else{
			FormDataUtil.setProperty(formData, "filecontent", resultMap.get("FILE_CONTENT"));
		}
		return ServiceClient.getResponseFormData(formData, "CtpAcctNotifyCheck");
	}

}
