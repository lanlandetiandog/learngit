package com.jkgroup.kingkaid.service.xabank.command;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.service.chinapnr.command.HFCommand;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.web.pay.XaBankNotifyController;

/**
 * 提现请求回调
 * @author pan
 *
 */
public class CommandCtpTransOutSynz implements HFCommand{

	private static final Logger logger = LoggerFactory.getLogger(XaBankNotifyController.class);
	
	@Override
	public FormData exec(Map<String, String> resultMap) {
		
		logger.debug(".............提现同步报文信息打印.............");
		logger.debug("MER_PRI:{}",resultMap.get("MER_PRI"));
		logger.debug("ORDER_NO:{}",resultMap.get("ORDER_NO"));
		logger.debug("TRAN_STAT:{}",resultMap.get("TRAN_STAT"));
		logger.debug("TRAN_STAT_DESC:{}",resultMap.get("TRAN_STAT_DESC"));
		
		FormData formData = FormDataUtil.createInputForm("WithDrawListCheck");
		
		FormDataUtil.setProperty(formData, "listid", resultMap.get("MER_PRI"));
		FormDataUtil.setProperty(formData, "orderno", resultMap.get("ORDER_NO"));
		FormDataUtil.setProperty(formData, "transtate", resultMap.get("TRAN_STAT"));
		FormDataUtil.setProperty(formData, "trabstatedesc", resultMap.get("TRAN_STAT_DESC"));
		formData = ServiceClient.getResponseFormData(formData, "WithDrawListCheck");
		return formData;
	}

}
