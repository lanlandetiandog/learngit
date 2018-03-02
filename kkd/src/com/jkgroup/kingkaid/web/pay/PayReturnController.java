package com.jkgroup.kingkaid.web.pay;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.PayMessage;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.MessageLogService;
import com.jkgroup.kingkaid.service.chinapnr.command.HFCommand;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.PayUtil;
import com.jkgroup.kingkaid.utils.pay.PayUtil.PayChannel;
import com.jkgroup.kingkaid.utils.pay.PayUtil.PayOption;

/**
 * 第三方支付同步返回入口
 * 
 * @author pan  
 *
 */
@Controller
@RequestMapping(value = Constants.UN_AUTH + "/pay/return")
public class PayReturnController {

	@Autowired
	private MessageLogService messageLogService;

	private static final Logger log = LoggerFactory.getLogger(PayReturnController.class);
	
	/**
	 * 统一处理同步返回应答转换为后台请求报文
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "")
	public String process(SitePreference sitePreference, HttpServletRequest request, Model model) {
		// 转换请求报文为Map格式
		Map<String, String> map = PayUtil.tranToMap(request.getParameterMap());
		// 日志保存，加入队列
		messageLogService.asyncLogMessage(new PayMessage(PayChannel.CHINAPNR, false, map));
		// 交易处理状态
		String respCode = StringUtils.trimToEmpty(map.get("RespCode"));
		// 交易类型
		String cmdId = StringUtils.trimToEmpty(map.get("CmdId"));
		// 交易处理状态描述
		String respDesc = map.get("RespDesc");

        log.info("Response:" + respCode + ", " + cmdId + ", " + respDesc);

		if("000".equals(respCode)){
			HFCommand command = PayUtil.cmdReturnMap.get(cmdId);
			if(null != command){
				FormData formData = command.exec(map);				
				FormDataUtil.print(formData);
				if(FormDataUtil.isSucceed(formData)) {
					if("UserRegister".equals(cmdId) && sitePreference.isNormal()) {
						return "redirect:/";
					} else {
						return PayUtil.paySuccess("操作成功", PayChannel.CHINAPNR, PayOption.parsePayOption(cmdId), sitePreference, model);
					}
				} else {
					return PayUtil.payError(FormDataUtil.getErrorMessage(formData), PayChannel.CHINAPNR, PayOption.parsePayOption(cmdId), sitePreference, model);
				}
			} else {
				return PayUtil.payError("暂不支持此操作", PayChannel.CHINAPNR, PayOption.UNSUPPORTED, sitePreference, model);
			}
		} else if ("999".equals(respCode) && "Cash".equalsIgnoreCase(cmdId)) {
		    HFCommand command = PayUtil.cmdReturnMap.get(cmdId);
		    if(null != command) {
    		    FormData formData = command.exec(map);                
                FormDataUtil.print(formData);
                if(FormDataUtil.isSucceed(formData)) {
                    return PayUtil.paySuccess("操作成功", PayChannel.CHINAPNR, PayOption.parsePayOption(cmdId), sitePreference, model);
                } else {
                    return PayUtil.payError(FormDataUtil.getErrorMessage(formData), PayChannel.CHINAPNR, PayOption.parsePayOption(cmdId), sitePreference, model);
                }
		    } else {
                return PayUtil.payError("暂不支持此操作", PayChannel.CHINAPNR, PayOption.UNSUPPORTED, sitePreference, model);
            }
	    } else {
			model.addAttribute("resultMap", map);
			return PayUtil.payError(map.get("RespDesc") + "。" + StringUtils.trimToEmpty(map.get("SecRespDesc")), PayChannel.CHINAPNR, PayOption.parsePayOption(cmdId), sitePreference, model);
		}
	}

}
