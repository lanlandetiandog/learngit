package com.jkgroup.kingkaid.web.project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.MessageWrapper;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.CommonServiceClient;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

@Controller
@RequestMapping(value=Constants.AUTH+"/loan")
public class LoanConterller {

	/**
	 * 我的金开贷主页
	 * @return
	 */
	@RequestMapping(value="appr_loan.html")
	public String apprLoan(Model model){
		User user = Utils.getCurrentUser();
		FormData acctInfo = CommonServiceClient.getBalance();
		model.addAttribute("acctInfo", acctInfo);
		model.addAttribute("usertype", user.isCorp() ? "1" : "2");
		if("1".equals(user.getMemberType())){
			FormData fd = FormDataUtil.createInputForm("SelectCorpCustInfo");
			FormDataUtil.setProperty(fd, "memberid", user.getMemberId());
			FormData outData = ServiceClient.getResponseFormData(fd, "SelectCorpCustInfo");
			model.addAttribute("orgaid",FormDataUtil.getProperty(outData, "orgaid"));
		}else{
			model.addAttribute("orgaid","");
		}
		//User user = Utils.getCurrentUser();
		//FormData acctInfo = CommonServiceClient.getBalance();
		//model.addAttribute("acctInfo", acctInfo);
		//return USERCENTER + (user.isCorp() ? CORP : StringUtils.EMPTY) + "usercenter";
		return "project/apprloan";
	}
	
	/**
	 * 查询借款利率
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="appr_loan_rate")
	@ResponseBody
	public String apprLoanRate(HttpServletRequest request,HttpServletResponse response,Model model){
		String prodid = request.getParameter("prodid");
		String tterm = request.getParameter("tterm");
		FormData formData = FormDataUtil.createInputForm("RateQuery");
		FormDataUtil.setProperty(formData, "prodid", prodid);
		FormDataUtil.setProperty(formData, "tterm", tterm);
		List<FormData> list = ServiceClient.getResponseFormDataList(formData, "RateQuery");
		String json = ServiceClient.parseObjToJson(list, new SimpleDateFormat(DateUtils.SDF_DATETIME));
		return json;
	}
	
	/**
	 * 提交借款申请
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="appr_loan_submit")
	@ResponseBody
	public String apprLoanSubmit(HttpServletRequest request,HttpServletResponse response,Model model){
		
		User user = Utils.getCurrentUser();
		
		String prodid = request.getParameter("prodid");//产品ID
		String apptterm = request.getParameter("apptterm");//借款期限
		String apptcapi = request.getParameter("apptcapi");//借款金额
		String borrowuse = request.getParameter("borrowuse");//借款金额
		String enddateStr = request.getParameter("enddate");
	
		String custid = user.getCustId();//客户编号
		String custname = user.getCustName();//客户名称
		String custtype = user.isCorp()?"1":"0";
		
		FormData formData = FormDataUtil.createInputForm("ApprLoanSubmit");
		FormDataUtil.setProperty(formData, "prodid", prodid);
		FormDataUtil.setProperty(formData, "apptterm", apptterm);
		FormDataUtil.setProperty(formData, "apptcapi", apptcapi);
		FormDataUtil.setProperty(formData, "borrowuse", borrowuse);
		FormDataUtil.setProperty(formData, "custid", custid);
		FormDataUtil.setProperty(formData, "custname", custname);
		FormDataUtil.setProperty(formData, "custtype", custtype);
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date enddate = null;
		if(!"".equals(enddateStr) && enddateStr != null){
			try {
				enddate = fmt.parse(enddateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			FormDataUtil.setProperty(formData, "enddate", enddate);
		}
		FormData fd = ServiceClient.getResponseFormData(formData, "ApprLoanSubmit");
		
//		String custappid = FormDataUtil.getProperty(fd, "custappid").toString();

		MessageWrapper mw = new MessageWrapper();
				
		if(FormDataUtil.getProperty(fd, "respcode") == null||FormDataUtil.getProperty(fd, "respcode").equals("")||FormDataUtil.getProperty(fd, "respcode").equals("S"))
		{
			mw.setStatus(true);
			mw.setMessage("提交成功！");
		}
		else
		{
			mw.setStatus(false);
			if("CF".equals(FormDataUtil.getProperty(fd, "respcode"))){
				//CF：政策检测不通过
				mw.setMessage((String)FormDataUtil.getProperty(fd, "checkresult"));
			}else{
				mw.setMessage(FormDataUtil.getErrorMessage(fd));
			}
		}
		
		return ServiceClient.parseObjToJson(mw);
	}
	

}
