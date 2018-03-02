package com.jkgroup.kingkaid.web.xabank.project;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.common.PayValidateException;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.service.ValidateService;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.PayUtil.PayOption;

@Controller
@RequestMapping(value=Constants.AUTH+"/project")
public class XaBankProjectController {

	private static final Logger logger = LoggerFactory.getLogger(XaBankProjectController.class);
	
	@Autowired
	private ValidateService validateService;
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/project_add.html" , produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String addProject(String loanid){
		String json = "{}";
		FormData formData = FormDataUtil.createInputForm("ProjectAdd");
		FormDataUtil.setProperty(formData, "loanid", loanid);
		formData = ServiceClient.getResponseFormData(formData, "ProjectAdd");
		String respCode = String.valueOf(FormDataUtil.getProperty(formData, "respcode"));
		String desc = String.valueOf(FormDataUtil.getProperty(formData, "respdesc"));
		if(!respCode.equals(FormData.SUCCESS)){
			json = "{\"code\":\"f\",\"msg\":\""+desc+"\"}";
		}else{
			String transurl = String.valueOf(FormDataUtil.getProperty(formData, "transurl"));
			json = "{\"code\":\"s\",\"msg\":\"成功\",\"url\":\""+transurl+"\"}";
		}
		logger.debug("json : {} ",json);
		return json;
	}
	
	/**
	 * 会员投标
	 * @param loanid
	 * @param bidamt
	 * @param coinamt
	 * @param inteaddno
	 * @param inteaddrate
	 * @return
	 */
	@RequestMapping(value="tender.html",produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String tender(String loanid,String bidamt,String coinamt,String inteaddno,String inteaddrate,String amount,String moneypicket){
		
		//加息利率，投标金额，金开币金额，加息券编号
		double inteaddrates = 0d;
		double amounts = 0d;
		double coinamts = 0d;
		
		
		User user = Utils.getCurrentUser();
		String json = "{}";
		
		if(user.getMemberState().equals("4")||user.getMemberState().equals("5")){
			return json = "{\"code\":\"f\",\"msg\":\"您还未开通银行存管电子账户，请先开通！\"}";
		}
		
		if(user.getMemberState().equals("6")||user.getMemberState().equals("7")){
			return json = "{\"code\":\"f\",\"msg\":\"您还未激活银行存管电子账户，请先激活！\"}";
		}
		
		if(user.getMemberType().equals("1")){
			return json = "{\"code\":\"f\",\"msg\":\"企业会员不允许投资\"}";
		}
		
		try {
			validateService.hasCustAcNo(user);
			validateService.isNotBlank(loanid, "借款编号不正确");
			inteaddrates = validateService.isRightCouponAmt(inteaddno, inteaddrate, "加息利率错误");
			amounts = validateService.isRightMoneyAmt(bidamt, PayOption.INITIATIVETENDER.getDesc());
			coinamts = validateService.isRightCoinAmt(StringUtils.isBlank(coinamt)?"0":coinamt);
		} catch (PayValidateException e) {
			return json = "{\"code\":\"f\",\"msg\":\""+e.getMessage()+"\"}";
		}

		FormData formData = FormDataUtil.createInputForm("CtpTenderInsert");
		FormDataUtil.setProperty(formData, "custid", user.getCustId());
		FormDataUtil.setProperty(formData, "loanid", loanid);
		FormDataUtil.setProperty(formData, "tenderamt", amount);
		FormDataUtil.setProperty(formData, "coinamt", coinamts);
		FormDataUtil.setProperty(formData, "inteaddno", inteaddno);
		FormDataUtil.setProperty(formData, "inteaddrate", inteaddrates);
		FormDataUtil.setProperty(formData, "bidsource", "2");
		FormDataUtil.setProperty(formData, "transamt", bidamt);
		
		if(StringUtils.isNotBlank(moneypicket)) {
			String moneypickets[] = moneypicket.split(",");
			
			FormDataUtil.setProperty(formData, "moneycoupno", moneypickets[0]);
			FormDataUtil.setProperty(formData, "moneycoupamt", moneypickets[1]);
		}
		
		//验证
		
		formData = ServiceClient.getResponseFormData(formData, "CtpTenderInsert");
		String respCode = String.valueOf(FormDataUtil.getProperty(formData, "respcode"));
		String desc = String.valueOf(FormDataUtil.getProperty(formData, "respdesc"));
		if(!respCode.equals(FormData.SUCCESS)){
			json = "{\"code\":\"f\",\"msg\":\""+desc+"\"}";
		}else{			
			String transurl = String.valueOf(FormDataUtil.getProperty(formData, "transurl"));
			json = "{\"code\":\"s\",\"msg\":\"成功\",\"url\":\""+transurl+"\"}";
		}
		logger.debug("json : {} ",json);
		return json;
	}
	
	/**
	 * 债权转让接口
	 * @param ctfId
	 * @return
	 */
	@RequestMapping(value="credit_assgin.html",produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String crditAssign(String ctfId){
				
		User user = Utils.getCurrentUser();
		String json = "{}";
		
		if(user.getMemberState().equals("4")||user.getMemberState().equals("5")){
			return json = "{\"code\":\"f\",\"msg\":\"您还未开通银行存管电子账户，请先开通！\"}";
		}
		
		if(user.getMemberState().equals("6")||user.getMemberState().equals("7")){
			return json = "{\"code\":\"f\",\"msg\":\"您还未激活银行存管电子账户，请先激活！\"}";
		}
		
		FormData formData = FormDataUtil.createInputForm("CtpCreditAssignInsert");
		FormDataUtil.setProperty(formData, "creditortransferid", ctfId);
		FormDataUtil.setProperty(formData, "memberid", user.getMemberId());
		
		//验证
		
		formData = ServiceClient.getResponseFormData(formData, "CtpCreditAssignInsert");
		String respCode = String.valueOf(FormDataUtil.getProperty(formData, "respcode"));
		String desc = String.valueOf(FormDataUtil.getProperty(formData, "respdesc"));
		if(!respCode.equals(FormData.SUCCESS)){
			json = "{\"code\":\"f\",\"msg\":\""+desc+"\"}";
		}else{
			String transurl = String.valueOf(FormDataUtil.getProperty(formData, "transurl"));
			json = "{\"code\":\"s\",\"msg\":\"成功\",\"url\":\""+transurl+"\"}";
		}
		logger.debug("json : {} ",json);
		return json;
	}
	
	/**
	 * 开启关闭自动投标
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="open_autobidset.html")
	@ResponseBody
	public String openAutoBidSet() throws UnsupportedEncodingException{
		User user = Utils.getCurrentUser();
		String json = "{}";
		FormData formData = FormDataUtil.createInputForm("CtpAutoBidSet");
		String opertype="S";
		FormDataUtil.setProperty(formData, "opertype", opertype);
		FormDataUtil.setProperty(formData, "memberid", user.getMemberId());
		formData = ServiceClient.getResponseFormData(formData, "CtpAutoBidSet");
		String respCode = String.valueOf(FormDataUtil.getProperty(formData, "respcode"));
		String desc = String.valueOf(FormDataUtil.getProperty(formData, "respdesc"));
		desc =URLEncoder.encode(desc,"UTF-8");	
		if(!respCode.equals(FormData.SUCCESS)){
			json = "{\"code\":\"f\",\"msg\":\""+desc+"\"}"; 
		}else{			
			String transurl = String.valueOf(FormDataUtil.getProperty(formData, "transurl"));
			json = "{\"code\":\"s\",\"msg\":\"成功\",\"url\":\""+transurl+"\"}";
		}
		logger.debug("json : {} ",json);
		return json;
	}
}
