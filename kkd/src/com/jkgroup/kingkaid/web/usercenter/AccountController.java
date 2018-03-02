package com.jkgroup.kingkaid.web.usercenter;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.bo.MessageWrapper;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.AccountServiceClient;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.Utils;

@Controller
@RequestMapping(value = "/acct")
public class AccountController {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	/**
	 * 客户回款计划
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/cust_receive_plan.html")
	@ResponseBody
	public String getCustReceivePlan(HttpServletRequest request,Model model){
		String loanid = request.getParameter("loanid");
		String bidno = request.getParameter("bidno");
		String receivedate = request.getParameter("receivedate");
		
		List<FormData> formDatas = AccountServiceClient.getCustReceivePlan(loanid, bidno,receivedate);
		String json = ServiceClient.parseObjToJson(formDatas, new SimpleDateFormat(DateUtils.SDF_DATE));
		logger.debug("客户回款计划 : {}",json);
		return json;
	}
	
	/**
	 * 客户还款计划
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/cust_retu_plan.html")
	@ResponseBody
	public String getCustRetuPlan(HttpServletRequest request,Model model){
		String loanid = request.getParameter("loanid");
		List<FormData> formDatas = AccountServiceClient.getCustRetuPlan(loanid);
		String json = ServiceClient.parseObjToJson(formDatas, new SimpleDateFormat(DateUtils.SDF_DATE));
		logger.debug("客户还款款计划 : {}",json);
		return json;
	}
	
	/**
	 * 投资统计
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/bid_count.html")
	@ResponseBody
	public String getBidCount(HttpServletRequest request,Model model){
		User user = Utils.getCurrentUser();
		if(user == null){
			return ServiceClient.parseObjToJson(new MessageWrapper(true, "登录超时"));
		}
		FormData formData = AccountServiceClient.getBidCount(user.getMemberId());
		String json = ServiceClient.parseObjToJson(formData);
		logger.debug("投资统计 : {}",json);
		return json;
	}
	
	/**
	 * 借款统计
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/loan_count.html")
	@ResponseBody
	public String getLoanCount(HttpServletRequest request,Model model){
		User user = Utils.getCurrentUser();
		if(user == null){
			return ServiceClient.parseObjToJson(new MessageWrapper(true, "登录超时"));
		}
		
		FormData formData = AccountServiceClient.getLoanCount(user.getMemberId());
		String json = ServiceClient.parseObjToJson(formData);
		logger.debug("借款统计 : {}",json);
		return json;
	}
}
