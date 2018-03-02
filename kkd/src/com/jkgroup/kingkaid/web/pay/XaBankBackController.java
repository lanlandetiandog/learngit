package com.jkgroup.kingkaid.web.pay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.MessageLogServices;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.PayUtil;

/**
 * 接收西安银行返回页面
 * @author pan
 *
 */
@Controller
@RequestMapping(value="/pay/xabank")
public class XaBankBackController {

	private static final Logger logger = LoggerFactory.getLogger(XaBankBackController.class);
	@Autowired private MessageLogServices messageLogServices;
	
	/**
	 * 
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/ret_url.html")
	public String execute(HttpServletRequest request,HttpServletResponse response,SitePreference sitePreference){
		logger.debug(".............接收西安银行返回信息 .............");
		String ctpseq = request.getParameter("CTP_SEQ");
		String transcode = request.getParameter("TRANS_CODE");
		String transtat = request.getParameter("TRAN_STAT");
		
		logger.debug(" CTP_SEQ : "+ctpseq+" TRANS_CODE : {} TRAN_STAT : {}",transcode,transtat);

		Map<String,String> resMap=new HashMap<String, String>();
		resMap.put("CTP_SEQ", ctpseq);
		resMap.put("TRANS_CODE", transcode);
		resMap.put("TRAN_STAT", transtat);
		resMap.put("TRANTYPE","REAL");
		messageLogServices.asyncLogMessage(resMap);
		
		if("ctpPerCustOpenActivationReqSynz".equals(transcode)) {
			User user = Utils.getCurrentUser();
			if (user == null) {
				if ("0".equals(transtat) || "1".equals(transtat)) {
					FormData formData = FormDataUtil.createInputForm("OpenPay3FSelect");
					FormDataUtil.setProperty(formData, "ctpseq", ctpseq);
					formData = ServiceClient.getResponseFormData(formData, "OpenPay3FSelect");
					
					request.setAttribute("transtat", transtat);
					request.setAttribute("transcode", transcode);
					String page = sitePreference.isNormal() ? "pay/xabank/payRetu" : "pay/xabank/mobile/payRetu";
					return page;
				}
			} else {
				if ("0".equals(transtat) || "1".equals(transtat)) {
					
					FormData formData = FormDataUtil.createInputForm("OpenPay3FSelect");
					FormDataUtil.setProperty(formData, "ctpseq", ctpseq);
					FormDataUtil.setProperty(formData, "memberid", user.getMemberId());
					formData = ServiceClient.getResponseFormData(formData, "OpenPay3FSelect");
					
					if(FormDataUtil.isSucceed(formData)){
						return "redirect:/auth/cust/openpay_page.html";
					}else{
						return "redirect:/auth/cust/openpay3f_page.html";
					}
				}
			}
		}
		
		if("0".equals(transtat) && StringUtils.isNotBlank(ctpseq)){
			FormData formData = FormDataUtil.createInputForm("CtpTransStatSelect");
			FormDataUtil.setProperty(formData, "transseq", ctpseq);
			FormDataUtil.setProperty(formData, "trantype", PayUtil.returnTranTypes.get(transcode));
			List<FormData> list = ServiceClient.getResponseFormDataList(formData, "CtpTransStatSelect");
			if(list != null && list.size()>0){
				formData = list.get(0); 
				
				transtat = String.valueOf(FormDataUtil.getProperty(formData, "transtat"));
				
				FormData formDataLog = FormDataUtil.createInputForm("CtpMessageLog");
				FormDataUtil.setProperty(formDataLog, "transseq", ctpseq);
				formDataLog = ServiceClient.getResponseFormData(formDataLog, "CtpMessageLog");
				
				if(StringUtils.isNotBlank(transtat) && transtat.equals("0")){
									
					String reqData = String.valueOf(FormDataUtil.getProperty(formDataLog, "reqbody"));
					String listid = String.valueOf(FormDataUtil.getProperty(formDataLog, "listid"));
					
					logger.debug("reqBody : {}",reqData);
					logger.debug("listid : {}",listid);
					
					Map<String,String> reqMap = PayUtil.parseJsonToMap(reqData);
					
					Map<String,String> params = new HashMap<String,String>();
					
					if(transcode.equals("ctpPayMent") 
							|| transcode.equals("ctpPayMentSynz")
							|| transcode.equals("ctpP2pManualBidResultSynz")
							|| transcode.equals("ctpTransOutSynz")
							|| transcode.equals("ctpP2pProjectAssiSynz")){	//购买VIP || 手动投标  || 提现 || 债权转让	
						params.put("MER_PRI", listid);
						params.put("TRAN_STAT", transtat);
						PayUtil.cmdXabankNotifyMap.get(transcode).exec(params);
					}else if(transcode.equals("ctpP2pAddResultSynz")){	//项目添加
						params.put("PRO_ID", reqMap.get("PRO_ID"));
						params.put("STAT", transtat);
						PayUtil.cmdXabankNotifyMap.get(transcode).exec(params);
					}else if(transcode.equals("ctpP2pAutoSBidSignResultSynz")){	//自动投标授权
						params.put("ID_NO", reqMap.get("ID_NO"));
						params.put("ID_NAME", reqMap.get("ID_NAME"));
						params.put("OPER_TYPE", reqMap.get("OPER_TYPE"));
						params.put("TRAN_STAT", transtat);
						PayUtil.cmdXabankNotifyMap.get(transcode).exec(params);
					}else if(transcode.equals("ctpMobileNoReset")){//修改西安银行手机号码
						params.put("NEW_MOBILE", reqMap.get("NEW_MOBILE"));
						params.put("ID_NO", reqMap.get("ID_NO"));
						params.put("ID_NAME", reqMap.get("ID_NAME"));
						params.put("ID_TYP", reqMap.get("ID_TYP"));
						params.put("TRAN_STAT", transtat);
						params.put("REG_AMT", reqMap.get("REG_AMT"));
						PayUtil.cmdXabankNotifyMap.get(transcode).exec(params);
					}
				}else if("1".equals(transtat) && StringUtils.isNotBlank(ctpseq)){
					transtat = "1";
					if(transcode.equals("ctpPayMent") 
							|| transcode.equals("ctpP2pManualBidResultSynz")
							|| transcode.equals("ctpP2pProjectAssiSynz")){
						transtat = "2";
					}else if(transcode.equals("ctpTransOutSynz")){
						String listid = String.valueOf(FormDataUtil.getProperty(formDataLog, "listid"));
						Map<String,String> params = new HashMap<String,String>();
						params.put("MER_PRI", listid);
						params.put("TRAN_STAT", transtat);
						PayUtil.cmdXabankNotifyMap.get(transcode).exec(params);
					}
				}else{
					transtat = "2";
				}
			}else{
				transtat = "2";
			}
		}
		
		logger.debug("本次交易状态为transtat : {}",transtat);
		
		request.setAttribute("transtat", transtat);
		request.setAttribute("transcode", transcode);
		
		String page = sitePreference.isNormal() ? "pay/xabank/payRetu" : "pay/xabank/mobile/payRetu";
		return page;
	}
}
