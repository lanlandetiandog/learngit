package com.jkgroup.kingkaid.web.comm;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.PayConstant;

/**
 * 用于处理公共组件请求的Controller
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年6月15日 上午9:30:16
 */

@Controller
public class ComponentController {

//	private final static Logger log = LoggerFactory.getLogger(ComponentController.class); 
	
	/**
	 * 收益计算
	 * @return
	 */
	@RequestMapping("counterBox")
	@ResponseBody
	public String counterBox(String amount,String term,String rate,String repayType){
		String json = "{}";
		
		try {
			Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			return json;
		}
		
		try {
			Integer.parseInt(term);
		} catch (NumberFormatException e) {
			return json;
		}
		
		try {
			Double.parseDouble(rate);
		} catch (NumberFormatException e) {
			return json;
		}
		
		if(StringUtils.isBlank(repayType)){
			return json;
		}
		
		if(repayType.equals("302") || repayType.equals("300") || repayType.equals("301") || repayType.equals("302") || repayType.equals("299")){

			FormData formData = FormDataUtil.createInputForm("Calculate");
			FormDataUtil.setProperty(formData, "amount", amount);
			FormDataUtil.setProperty(formData, "term", term);
			FormDataUtil.setProperty(formData, "rate", rate);
			FormDataUtil.setProperty(formData, "repaytype", repayType);
			
			List<FormData> formDatas = ServiceClient.getResponseFormDataList(formData, "Calculate");
			json = ServiceClient.parseObjToJson(formDatas, new SimpleDateFormat(DateUtils.SDF_DATETIME));
		}
		return json;
	}
	
	/**
	 * 收益计算
	 * @return
	 */
	@RequestMapping(value=Constants.AUTH+"/plan")
	@ResponseBody
	public String plan(String loanid,String loanacno,String type,String flag){
		User user = Utils.getCurrentUser();
		
		String json = "{}";
		
		if(StringUtils.isBlank(type))
			type = "1";
		
		if(StringUtils.endsWith(flag, "1")){
			//还款计划  对于借款人
			FormData formData = FormDataUtil.createInputForm("RetuplanQuery");
			FormDataUtil.setProperty(formData, "loanid", loanid);
			List<FormData> formDatas = ServiceClient.getResponseFormDataList(formData, "RetuplanQuery");
			json = ServiceClient.parseObjToJson(formDatas, new SimpleDateFormat(DateUtils.SDF_DATETIME));
		}else{
			//回款计划  对与投资人
			FormData formData = FormDataUtil.createInputForm("ReceivePlan");
			FormDataUtil.setProperty(formData, "memberid", user.getMemberId());
			FormDataUtil.setProperty(formData, "loanacno", loanacno);
			
			List<FormData> formDatas = ServiceClient.getResponseFormDataList(formData, "ReceivePlan");
			json = ServiceClient.parseObjToJson(formDatas, new SimpleDateFormat(DateUtils.SDF_DATETIME));
		}
		return json;
	}
	
	/**
	 * 查询会员的提现申请记录
	 * @return
	 */
	@RequestMapping(value="get_cash_appr_list.html")
	@ResponseBody
	public String getCashApprCount(){
		User user = Utils.getCurrentUser();
		if(user == null){
			return "{}";
		}
		
		FormData formData = FormDataUtil.createInputForm("CashApprList");
		FormDataUtil.setProperty(formData, "custid",StringUtils.isBlank(user.getCustId()) ? user.getMemberId() : user.getCustId());
		FormDataUtil.setProperty(formData, "chanid", PayConstant.CHAN_CHINAPNR);
		FormDataUtil.setProperty(formData, "cashapprstat", "2");
		FormDataUtil.setProperty(formData, "cashstat", "2");
		List<FormData> formDatas = ServiceClient.getResponseFormDataList(formData, "CashApprList");
		String json = ServiceClient.parseObjToJson(formDatas, new SimpleDateFormat(DateUtils.SDF_DATE));
		return json;
	}
}
