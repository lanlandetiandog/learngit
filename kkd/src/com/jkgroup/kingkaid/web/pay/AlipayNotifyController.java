package com.jkgroup.kingkaid.web.pay;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jkgroup.kingkaid.bo.PayMessage;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.MessageLogService;
import com.jkgroup.kingkaid.service.pay.PayServiceClient;
import com.jkgroup.kingkaid.utils.MathUtil;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.PayUtil.PayChannel;
import com.jkgroup.kingkaid.utils.pay.alipay.util.AlipayNotify;

/**
 * 支付宝同步、异步回调
 * @author pan
 *
 */
@Controller
@RequestMapping(value="/alipay/process")
public class AlipayNotifyController {

	private static final Logger logger = LoggerFactory.getLogger(AlipayNotifyController.class);
	
	@Autowired
	private MessageLogService messageLogService;
	
	/**
	 * 支付宝同步入口
	 * @return
	 */
	@RequestMapping(value="/return_url.html")
	public String returnProcess(HttpServletRequest request,Model model){
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
			logger.debug("key : {}  value : ",name,valueStr);
			
		}

		// 日志保存，加入队列
		messageLogService.asyncLogMessage(new PayMessage(PayChannel.ALIPAY, true, params));
		
		try {
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			//验证成功
//			if(AlipayNotify.verify(params)){
				if(trade_status.equals("TRADE_FINISHED")){
				} else if (trade_status.equals("TRADE_SUCCESS")){
					Map<String,String> map = new HashMap<String, String>();
					map.put("otherlistno", out_trade_no);
					map.put("tradeno", trade_no);
					map.put("tradestat", trade_status);
					map.put("listid", request.getParameter("extra_common_param"));
					map.put("rechargeamt", MathUtil.numFmt(Double.parseDouble(request.getParameter("total_fee"))));
					FormData formData = PayServiceClient.depositCheck(map);
					String respCode = String.valueOf(FormDataUtil.getProperty(formData, "respcode"));
					String respDesc = String.valueOf(FormDataUtil.getProperty(formData, "respdesc"));
					if(respCode.equals("S")){
						
					}else{
						
					}
//				//对应单据表中的listId
//				String common_id = params.get("extra_common_param");
//				String total_fee = params.get("total_fee");
//				//处理记账业务
				}
				
//			}else{//验证失败
//				
//			}
		} catch (UnsupportedEncodingException e) {
			
		}
		
		return "";
	}
	
	/**
	 * 支付宝同步入口
	 * @return
	 */
	@RequestMapping(value="/notif_url.html")
	public void notifyProcess(HttpServletRequest request,HttpServletResponse response){
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			String value = "";
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
				
			}
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			//验证成功
			if(AlipayNotify.verify(params)){
				if(trade_status.equals("TRADE_FINISHED")){
				} else if (trade_status.equals("TRADE_SUCCESS")){
					Map<String,String> map = new HashMap<String, String>();
					map.put("otherlistno", out_trade_no);
					map.put("tradeno", trade_no);
					map.put("trade_status", trade_status);
					map.put("listid", map.get("extra_common_param"));
					map.put("rechargeamt", MathUtil.numFmt(Double.parseDouble(map.get("total_fee"))));
					FormData formData = PayServiceClient.depositCheck(map);
					String respCode = String.valueOf(FormDataUtil.getProperty(formData, "respcode"));
					String respDesc = String.valueOf(FormDataUtil.getProperty(formData, "rescdesc"));
					if(respCode.equals("S")){
						pw.println("success");
					}else{
						pw.println("fail");
					}
//					//对应单据表中的listId
//					String common_id = params.get("extra_common_param");
//					String total_fee = params.get("total_fee");
//					//处理记账业务
				}
				pw.println("success");	//请不要修改或删除
			}else{//验证失败
				pw.println("fail");
			}
		} catch (UnsupportedEncodingException e) {
			pw.print("fail");
		} catch (IOException e) {
			pw.print("fail");
		}finally{
			pw.close();
		}
	}
}
