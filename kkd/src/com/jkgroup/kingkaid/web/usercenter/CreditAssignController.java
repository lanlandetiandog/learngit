package com.jkgroup.kingkaid.web.usercenter;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.MessageWrapper;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.MessageHelpService;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.bo.Page;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 债权转让
 * @author pan
 *
 */
@Controller
@RequestMapping(value=Constants.AUTH+"/usercenter/assign")
public class CreditAssignController {
	
	@Autowired
	private MessageHelpService mhs;
	/**
	 * 我的债权转让
	 * @param model
	 * @return
	 */
	@RequestMapping(value="mycredit_assign.html")
	public String mycreditassign(Model model){

		User user = Utils.getCurrentUser();
		String custid = user.getCustId();
		//查询转让中的笔数、已转让的笔数
		FormData formData=FormDataUtil.createInputForm("CreditTransferCount") ;
		FormDataUtil.setProperty(formData, "TransferCustId", custid);
		FormData outData = ServiceClient.getResponseFormData(formData, "CreditTransferCount");
//		String respcode = (String)FormDataUtil.getProperty(outData, "respcode");
		if (FormDataUtil.getProperty(outData, "respcode") == null||FormDataUtil.getProperty(outData, "respcode").equals("")||FormDataUtil.getProperty(outData, "respcode").equals("S") ){
			model.addAttribute("rowcount", FormDataUtil.getProperty(outData, "rowcount"));
			model.addAttribute("count", FormDataUtil.getProperty(outData, "count"));
		}else{
			model.addAttribute("rowcount", 0);
			model.addAttribute("count", 0);
		}
		
		return "usercenter/mycreditassign";
	}
	
	/**
	 * 我的债权转让列表
	 * @param request
	 * @param model
	 * @return 
	 */
	@RequestMapping(value="mycredit_assign_data.html", produces = "text/html; charset=utf-8")
	@ResponseBody 
	public String assignList(int pageSize, int pageNo, HttpServletRequest request, Model model) {
		Page page = Page.buildPageFromRequest(pageSize, pageNo);
		FormData formData = FormDataUtil.createInputForm("CreditTransferList");

		User user = Utils.getCurrentUser();
		FormDataUtil.setProperty(formData, "transfercustid", user.getCustId());
		FormDataUtil.setProperty(formData, "receivecustid", "");
		FormDataUtil.setProperty(formData, "transferstate", "");
		FormDataUtil.setProperty(formData, "tterm1", "");
		FormDataUtil.setProperty(formData, "tterm2", "");
		FormDataUtil.setProperty(formData, "bankid", "");
		if(request.getParameter("s") != null && !"".equals(request.getParameter("s")))
		{
			FormDataUtil.setProperty(formData, "transferstate", request.getParameter("s"));
		}
		
		Page<FormData> outData = ServiceClient.getResponseFormDataPage(formData,"CreditTransferList",page);
		for(FormData f : outData.getResult()){
			FormDataUtil.print(f);
		}
		
		String json = ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATETIME));
		
		return json;
	}
	
	/**
	 * 我的债权购买列表
	 * @param request
	 * @param model
	 * @return 
	 */
	@RequestMapping(value="mycredit_buy_data.html", produces = "text/html; charset=utf-8")
	@ResponseBody 
	public String buyList(int pageSize, int pageNo, HttpServletRequest request, Model model) {
		Page page = Page.buildPageFromRequest(pageSize, pageNo);
		FormData formData = FormDataUtil.createInputForm("CreditTransferPageList");

		User user = Utils.getCurrentUser();
//		FormDataUtil.setProperty(formData, "transfercustid", "");
		FormDataUtil.setProperty(formData, "receivecustid", user.getCustId());
		FormDataUtil.setProperty(formData, "transferstate", "1");
		FormDataUtil.setProperty(formData, "tterm1", "");
		FormDataUtil.setProperty(formData, "tterm2", "");
		FormDataUtil.setProperty(formData, "bankid", "");		
		 
		if (!IsEmpty(request.getParameter("apprstate"))){
			FormDataUtil.setProperty(formData, "apprstate", request.getParameter("apprstate"));
		}
		
		Page<FormData> outData = ServiceClient.getResponseFormDataPage(formData,"CreditTransferPageList",page);
		for(FormData f : outData.getResult()){
			FormDataUtil.print(f);
		}
		
//		String json = ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATETIME));
		
		MessageWrapper mw = mhs.buildMessageWrapperWithOption(outData, "apprstate");
		
		String json = ServiceClient.parseObjToJson(mw, new SimpleDateFormat(DateUtils.SDF_DATETIME));
		return json;
	}
	public boolean IsEmpty(String s){
		return (s==null || s.trim().length() == 0);
	}

}
