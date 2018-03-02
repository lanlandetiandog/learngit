package com.jkgroup.kingkaid.utils;

import java.util.Map;

import com.jkgroup.kingkaid.bo.PayMessage;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.PayUtil.PayOption;

/**
 * 报文记录辅助工具类
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年10月13日 上午11:10:44
 */

public class PayMessageUtil {

	/**
	 * 解析并填充报文至FormData
	 * @param <T>
	 * @param message
	 * @param fd
	 */
	public static <T> void fillFormData(PayMessage message, FormData fd) {
		parseMessage(message);
		FormDataUtil.setProperty(fd, "channel", message.getChannel().getSeqNo());
		FormDataUtil.setProperty(fd, "payoption", message.getOption().getListType());
		FormDataUtil.setProperty(fd, "listId", message.getListId());
		FormDataUtil.setProperty(fd, "status", message.isStatus() ? "1" : "0");
		FormDataUtil.setProperty(fd, "async", message.isAsync() ? "1" : "0");
		FormDataUtil.setProperty(fd, "optiontime", message.getOptionTime());
		FormDataUtil.setProperty(fd, "content", ServiceClient.parseObjToJson(message.getRawData()));
	}

	/**
	 * 解析原始报文键值对，填充维度数据
	 * @param message
	 */
	private static void parseMessage(PayMessage message) {
		Map<String, String> rawData =  message.getRawData();
		switch (message.getChannel()) {
		case CHINAPNR:
			message.setOption(PayOption.parsePayOption(rawData.get("CmdId")));
			message.setListId(rawData.get("MerPriv"));
			message.setStatus(rawData.get("RespCode").equals("000"));
			break;
		case ALIPAY:
			try {
				message.setOption(PayOption.NETSAVE);
			} catch (Exception e) {
			}
			message.setListId(rawData.get("extra_common_param"));
			message.setStatus(rawData.get("trade_status").equals("TRADE_SUCCESS"));
			break;
		case LIANLIAN:
			try {
				message.setOption(PayOption.NETSAVE);
			} catch (Exception e) {
			}
			message.setListId(rawData.get("no_order"));
			message.setStatus(rawData.get("result_pay").equals("SUCCESS"));
			break;
		default:
			break;
		}
	}

}
