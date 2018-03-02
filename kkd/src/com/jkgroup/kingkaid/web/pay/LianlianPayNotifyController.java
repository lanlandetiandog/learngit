package com.jkgroup.kingkaid.web.pay;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jkgroup.kingkaid.bo.PayMessage;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.MessageLogService;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.PayConstant;
import com.jkgroup.kingkaid.utils.pay.PayUtil;
import com.jkgroup.kingkaid.utils.pay.PayUtil.PayChannel;

/**
 * 连连支付异步通知
 * @author pan
 *
 */
@Controller
@RequestMapping(value="/lianlian/process")
public class LianlianPayNotifyController {
	
	private static final Logger logger = LoggerFactory.getLogger(LianlianPayNotifyController.class);
	
	//支付成功标志
	private static final String RESULT_SUCCESS = "SUCCESS";
	
	@Autowired
	private MessageLogService messageLogService;
	
	/**
	 * 处理连连支付异步通知入口
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="notify_url.html")
	public void notify(HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		String data = PayUtil.readReqStr(request);
		logger.info("notify data : {}",data);
		
		
		try {
			if(StringUtils.isNotBlank(data)){
				String signData = URLEncoder.encode(data, "utf-8");
				
				FormData inData = FormDataUtil.createInputForm("LianlianChkSign");
				FormDataUtil.setProperty(inData, "chk_value", signData);
				inData = ServiceClient.getResponseFormData(inData, "LianlianChkSign");
				//验证签名返回码
				String code = String.valueOf(FormDataUtil.getProperty(inData, "respcode"));
				
				Map<String,String> map = new ObjectMapper().readValue(data, Map.class);

				// 日志保存，加入队列
				messageLogService.asyncLogMessage(new PayMessage(PayChannel.LIANLIAN, true, map));
				
				if(code.equals(FormData.SUCCESS)){		//验证签名成功
					
					//订单号
					String no_order = map.get("no_order");
					//返回结果
					String result_pay = map.get("result_pay");
					//订单金额
					String money_order = map.get("money_order");
					
					if(result_pay.equals(RESULT_SUCCESS)){			
						FormData deposit = FormDataUtil.createInputForm("DepositCheck");
						FormDataUtil.setProperty(deposit, "listid", no_order);
						FormDataUtil.setProperty(deposit, "listtype", PayConstant.LIST_TYPE_DEPOSIT);
						FormDataUtil.setProperty(deposit, "busitype", PayConstant.BUSI_TYPE_DEPOSIT);
						FormDataUtil.setProperty(deposit, "chanid", PayConstant.CHAN_LIANLIANPAY);
						FormDataUtil.setProperty(deposit, "rechargeamt", money_order);
						FormDataUtil.setProperty(deposit, "respcode", "000");
						deposit = ServiceClient.getResponseFormData(deposit, "DepositCheck");
						String respcode = String.valueOf(FormDataUtil.getProperty(deposit, "respcode"));
						if(respcode.equals(FormData.SUCCESS)){
							try {
								response.getWriter().print("{\"ret_code\":\"0000\",\"ret_msg\":\"交易成功\"}");
							} catch (IOException e) {
								logger.error(" response error : {} ",e.getMessage());
							}
						}
					}
				}else{
					logger.debug(" 验证签名失败 ：{} ",data);
				}
				
			}
		} catch (JsonParseException e) {
			logger.error(" error : {} ",e.getMessage());
		} catch (JsonMappingException e) {
			logger.error(" error : {} ",e.getMessage());
		} catch (IOException e) {
			logger.error(" error : {} ",e.getMessage());
		}
	}
}
