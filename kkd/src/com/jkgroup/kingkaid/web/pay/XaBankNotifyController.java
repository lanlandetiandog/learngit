package com.jkgroup.kingkaid.web.pay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 西安银行回调接口
 * @author pan
 *
 */
@Controller
@RequestMapping(value="/pay/xabank")
public class XaBankNotifyController {

	private static final Logger logger = LoggerFactory.getLogger(XaBankNotifyController.class);
	
//	@Autowired
//	private MessageLogServices messageLogServices;
	
	/**
	 * 
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/notify_url.html" , produces = "text/html; charset=utf-8")
	@ResponseBody
	public String execute(HttpServletRequest request,HttpServletResponse response){
//		logger.debug(".............接收西安银行通知信息 .............");
//		response.setCharacterEncoding("UTF-8");
//		String data = PayUtil.readReqStr(request);
//		
//		logger.debug("notify data : {}",data);
//		
//		Map<String,String> resMap = PayUtil.parseJsonToMap(data);
//		String sign = resMap.get("SIGN");
//		String plain = resMap.get("PLAIN");
//		
//		logger.debug("sign : {} plain : {}",sign,plain);
//		
//		//验证签名
//		
//		resMap = PayUtil.parseJsonToMap(plain);
//		
//		ObjectMapper mapp = new ObjectMapper();
//		try {
//			JsonNode node = mapp.readTree(plain);
//			String CTP_ID = node.get("CTP_ID").asText();
//			String MER_PRI = node.get("MER_PRI").asText();
//			String TRANS_CODE = node.get("TRANS_CODE").asText();
//			String TRANS_DATE = node.get("TRANS_DATE").asText();
//			// SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd hh:mm:ss");
//			// Date trandate=sdf.parse(TRANS_DATE);
//
//			resMap.put("CTP_ID", CTP_ID);
//			resMap.put("MER_PRI", MER_PRI);
//			resMap.put("TRANS_CODE", TRANS_CODE);
//			resMap.put("TRANSTIME", TRANS_DATE);
//			resMap.put("CONTENT", data);
//			resMap.put("TRANTYPE", "ASYNC");
//		} catch (JsonProcessingException e1) {
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		
//		if(resMap !=null){
//			// 日志保存，加入队列
//			messageLogServices.asyncLogMessage(resMap);
//			FormData fromData = null;
//			String tranCode = "";
//			if(resMap != null){
//				
//				for(Entry<String, String> entry : resMap.entrySet()){
//					logger.debug("key :{} \t value : {}",entry.getKey(),entry.getValue());
//				}
//
//				tranCode = resMap.get("TRANS_CODE") == null ? "" : StringUtils.trim(resMap.get("TRANS_CODE"));
//				String reqBody  = resMap.get("REQ_BODY")   == null ? "" : StringUtils.trim(resMap.get("REQ_BODY"));
//				
//				Map<String,String> map = PayUtil.parseJsonToMap(reqBody);
//				map.put("MER_PRI", resMap.get("MER_PRI"));
//				
//				if(map != null && map.size() >0){
//					try {
//						fromData = PayUtil.cmdXabankNotifyMap.get(tranCode).exec(map);					
//					} catch (Exception e) {
//						e.printStackTrace();
//						logger.error(" error ... {}",e.getMessage());
//					}
//				}
//			}
//			String respdata = "";
//			if(tranCode!=null && tranCode.equals("ctpAcctNotifyGetNoSynz")){
//				Map<String,String> retVal = new HashMap<String,String>();
//				retVal.put("LIST_ID", "listid");
//				respdata = PayUtil.outMessage(resMap,fromData,retVal);
//			}else{
//				respdata = PayUtil.outMessage(resMap,fromData,null);
//			}
//			logger.debug(" response data : {}",respdata);
//			return respdata;
//		}
//		return plain;
		return "";
	}
}
