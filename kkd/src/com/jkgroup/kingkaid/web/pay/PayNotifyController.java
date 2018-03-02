package com.jkgroup.kingkaid.web.pay;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.PayMessage;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.MessageLogService;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.service.chinapnr.command.HFCommand;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.PayConstant;
import com.jkgroup.kingkaid.utils.pay.PayUtil;
import com.jkgroup.kingkaid.utils.pay.PayUtil.PayChannel;

/**
 * 第三方支付异步通知返回入口
 * @author pan
 *
 */
@Controller
@RequestMapping(value=Constants.UN_AUTH+"/pay/notify")
public class PayNotifyController {

	@Autowired
	private MessageLogService messageLogService;

	private Logger log = LoggerFactory.getLogger(PayNotifyController.class);
	
	/**
	 * 统一处理通知转换为后台请求报文
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="")
	@ResponseBody
	public String process(HttpServletRequest request,Model model){
		// 转换请求报文为Map格式
		Map<String, String> map = PayUtil.tranToMap(request.getParameterMap());
		// 日志保存，加入队列
		messageLogService.asyncLogMessage(new PayMessage(PayChannel.CHINAPNR, true, map));
		// 交易处理状态
		String respCode = StringUtils.trimToEmpty(map.get("RespCode"));
		// 交易类型
		String cmdId = StringUtils.trimToEmpty(map.get("CmdId"));
		// 交易处理状态描述
		String respDesc = map.get("RespDesc");

		log.info("Notify Response:" + respCode + ", " + cmdId + ", " + respDesc);

		if("000".equals(respCode)) { // 只处理成功报文
			HFCommand command = PayUtil.cmdNotifyMap.get(cmdId);
			if(null != command){
				command.exec(map);
			}
			return PayUtil.getNotifyRetu(map);				
		} else if("217".equals(respCode) && "CorpRegister".equals(cmdId)) { // 企业认证审核失败 
			HFCommand command = PayUtil.cmdNotifyMap.get(cmdId);
			if(null != command){
				command.exec(map);
			}
			return PayUtil.getNotifyRetu(map);	
		} else if("400".equals(respCode)) {
			String respType = String.valueOf(map.get("RespType"));
			String merpriv = String.valueOf(map.get("MerPriv"));
			if("Cash".equals(respType) && StringUtils.isNotBlank(merpriv)){
				//提现退回处理
				FormData formData = FormDataUtil.createInputForm("WithDrawCancel");
				FormDataUtil.setProperty(formData, "listid", merpriv);
				formData = ServiceClient.getResponseFormData(formData, "WithDrawCancel");
			}
			return PayUtil.getNotifyRetu(map);	
		} else if ("999".equals(respCode) && "Cash".equalsIgnoreCase(cmdId)) { 
		    HFCommand command = PayUtil.cmdNotifyMap.get(cmdId);
            if(null != command){


                command.exec(map);
            }
            return PayUtil.getNotifyRetu(map);
		} else {
			return PayConstant.CHINAPNR_NOTIFY_PREFIX;
		}
	}
}
