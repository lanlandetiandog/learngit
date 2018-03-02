package com.jkgroup.kingkaid.web.usercenter;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.MessageWrapper;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.common.PayValidateException;
import com.jkgroup.kingkaid.service.CommonServiceClient;
import com.jkgroup.kingkaid.service.FeeRuleServiceClient;
import com.jkgroup.kingkaid.service.MessageHelpService;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.service.pay.PayServiceClient;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.bo.Page;
import com.jkgroup.kingkaid.utils.bo.fee.ChanFee;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.FeeRuleUtil;
import com.jkgroup.kingkaid.utils.pay.PayConstant;
import com.jkgroup.kingkaid.utils.pay.PayUtil;
import com.jkgroup.kingkaid.utils.pay.PayUtil.PayChannel;
import com.jkgroup.kingkaid.utils.pay.PayUtil.PayOption;

@Controller
@RequestMapping(value=Constants.AUTH+"/usercenter")
public class UserCenterController {

	private static final String USERCENTER = "usercenter/";
	private static final String CORP = "corp/";
	
	@Autowired
	private MessageHelpService messageHelpService;
	
	/**
	 * 我的金开贷主页
	 * @return
	 */
	@RequestMapping(value="myjkd.html")
	public String myJkd(Model model){
		User user = Utils.getCurrentUser();
		//余额查询
		FormData acctInfo = CommonServiceClient.getBalance();
		//我的金开贷显示信息查询
		FormData formData=FormDataUtil.createInputForm("MyKingkaid");
		FormDataUtil.setProperty(formData,"memberid",user.getMemberId());
		FormData outData=ServiceClient.getResponseFormData(formData, "MyKingkaid");
		
		FormData fd = FormDataUtil.createInputForm("BankCardList");
		FormDataUtil.setProperty(fd, "memberid", user.getMemberId());
		List<FormData> bankcards = ServiceClient.getResponseFormDataList(fd, "BankCardList");
		
		if(bankcards.size()>0){
			String bankcode = (String) FormDataUtil.getProperty(bankcards.get(0), "bankacno");
			model.addAttribute("bankcode", bankcode.substring(bankcode.length()-4, bankcode.length()));
		}
		// 汇付天下开户用户的可提现余额查询
		FormData cashBal = FormDataUtil.createInputForm("CashBalQuery");
		FormDataUtil.setProperty(cashBal, "custid", user.getMemberId());
		cashBal = ServiceClient.getResponseFormData(cashBal, "CashBalQuery");
		model.addAttribute("cashBal", cashBal);
		
		model.addAttribute("acctInfo", acctInfo);
		model.addAttribute("user", user);
		model.addAttribute("outData", outData);
		return USERCENTER + (user.isCorp() ? CORP : StringUtils.EMPTY) + "usercenter";
	}

	/**
	 * 我的资产页面
	 * @return
	 */
	@RequestMapping(value="myproperty.html")
	public String myproperty(Model model){
		User user = Utils.getCurrentUser();
		//余额查询
		FormData acctInfo = CommonServiceClient.getNewBalance();
		//银行卡查询
		FormData fd = FormDataUtil.createInputForm("BankCardList");
		FormDataUtil.setProperty(fd, "memberid", user.getMemberId());
		List<FormData> bankcards = ServiceClient.getResponseFormDataList(fd, "BankCardList");
		
		// 汇付天下开户用户的可提现余额查询
		FormData cashBal = FormDataUtil.createInputForm("CashBalQuery");
		FormDataUtil.setProperty(cashBal, "custid", user.getMemberId());
		cashBal = ServiceClient.getResponseFormData(cashBal, "CashBalQuery");
		model.addAttribute("cashBal", cashBal);
		
		// 最后一次换卡申请记录
		FormData changeCardRecord = FormDataUtil.createInputForm("ChangeCardRecord");
		FormDataUtil.setProperty(changeCardRecord, "memberid", user.getMemberId());
		changeCardRecord = ServiceClient.getResponseFormData(changeCardRecord, "ChangeCardRecord");
		model.addAttribute("changeCardRecord", changeCardRecord);
		
		if(bankcards.size()>0){
			String bankcode = (String) FormDataUtil.getProperty(bankcards.get(0), "bankacno");
			String bankname = (String) FormDataUtil.getProperty(bankcards.get(0), "bankname");
			model.addAttribute("bankcode", bankcode);
			model.addAttribute("bankname", bankname);
		}

		model.addAttribute("acctInfo", acctInfo);
		model.addAttribute("user", user);
		return USERCENTER + (user.isCorp() ? CORP : StringUtils.EMPTY) + "myproperty";
	}
	
	/**
	 * 充值页面
	 * @return
	 */
	@RequestMapping(value="pre_deposit.html")
	public String preDeposit(Model model){
		User user = Utils.getCurrentUser();
		//余额查询
		FormData acctInfo = CommonServiceClient.getNewBalance();
		model.addAttribute("acctInfo", acctInfo);
		return USERCENTER + (user.isCorp() ? CORP : StringUtils.EMPTY) + "deposit";
	}
	
	/**
	 * 提现页面
	 * @return
	 */
	@RequestMapping(value="pre_cash.html")
	public String preCash(Model model){
		User user = Utils.getCurrentUser();
		
		FormData loancount = FormDataUtil.createInputForm("LoanCountSelect");
		FormDataUtil.setProperty(loancount, "memberid", user.getMemberId());
		loancount = ServiceClient.getResponseFormData(loancount, "LoanCountSelect");
		model.addAttribute("loancount", String.valueOf(FormDataUtil.getProperty(loancount, "loancount")));
		model.addAttribute("apprcount", String.valueOf(FormDataUtil.getProperty(loancount, "apprcount")));
		
		//银行卡
		FormData fd = FormDataUtil.createInputForm("BankCardList");
		FormDataUtil.setProperty(fd, "memberid", user.getMemberId());
		List<FormData> bankcards = ServiceClient.getResponseFormDataList(fd, "BankCardList");
		
		//手续费用收取规则
		ChanFee chanFee = FeeRuleServiceClient.getChanFee(PayConstant.CHAN_CHINAPNR);
		//余额查询
		FormData acctInfo = CommonServiceClient.getNewBalance();
		
		//提现优惠卷查询
		List<FormData> raisintes = CommonServiceClient.getRaisintList(user.getMemberId(), "2");
		
		// 个人用户的可提现余额查询
		// 企业用户不查询
		if (!user.isCorp()) {
			FormData cashBal = FormDataUtil.createInputForm("CashBalQuery");
			FormDataUtil.setProperty(cashBal, "custid", user.getMemberId());
			cashBal = ServiceClient.getResponseFormData(cashBal, "CashBalQuery");
			model.addAttribute("cashBal", cashBal);
		}

		model.addAttribute("raisintes", raisintes);
		model.addAttribute("chanfee", chanFee);
		model.addAttribute("acctInfo", acctInfo);
		model.addAttribute("bankcards", bankcards);
		return USERCENTER + (user.isCorp() ? CORP : StringUtils.EMPTY) + "cash";
	}
	
	/**
	 * 汇付天下余额提现页面
	 * @return
	 */
	@RequestMapping(value="cashall.html")
	public String CashAll(Model model){
		User user = Utils.getCurrentUser();
		
		//银行卡
		FormData fd = FormDataUtil.createInputForm("ChinapnrMemberBankInfo");
		FormDataUtil.setProperty(fd, "memberid", user.getMemberId());
		List<FormData> bankcards = ServiceClient.getResponseFormDataList(fd, "ChinapnrMemberBankInfo");
				
		// 用户的可提现余额查询
		FormData cashBal = FormDataUtil.createInputForm("CashBalQuery");
		FormDataUtil.setProperty(cashBal, "custid", user.getMemberId());
		cashBal = ServiceClient.getResponseFormData(cashBal, "CashBalQuery");
		model.addAttribute("cashBal", cashBal);

		model.addAttribute("bankcards", bankcards);
		return "usercenter/cash";
	}
	
	/**
	 * 绑定银行卡
	 * @return
	 */
	@RequestMapping(value="bindcard")
	public String bindcard(Model model,HttpServletRequest request, SitePreference sitePreference){
		User user = Utils.getCurrentUser();
		
		// 汇付天下用户信息查询
		FormData MemberInfo = FormDataUtil.createInputForm("ChinapnrMemberInfo");
		FormDataUtil.setProperty(MemberInfo, "memberid", user.getMemberId());
		MemberInfo = ServiceClient.getResponseFormData(MemberInfo, "ChinapnrMemberInfo");
		
		String custacno = (String) FormDataUtil.getProperty(MemberInfo, "custacno") ;		
		try {
			if (StringUtils.isBlank(custacno)) {
				throw new PayValidateException("您尚未开通第三方支付托管账户");
			}
		}catch (PayValidateException e) {
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.USERBINDCARD, sitePreference, model);
		}
		return PayServiceClient.bindcard(custacno, user.getCustId(), model);
	}
	
	/**
	 * 会员汇付天下账户明细账查询
	 * @return
	 */
	@RequestMapping(value="pnraccdetail.html")
	public String PnrAccDetail(Model model){
		
		return "usercenter/pnraccdetail";
	}

	
	/**
	 * 提交提现申请【借款人】
	 * @return
	 */
	@RequestMapping(value="sub_cash_appr.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String subCashAppr(HttpServletRequest request){
		User user = Utils.getCurrentUser();
		
		String coinamt_str = request.getParameter("coinamt");
		String voucherno = request.getParameter("voucherno");
		String voucheramt_str = request.getParameter("voucheramt");
		
		String cashchl = request.getParameter("cashchl");
		if(StringUtils.isBlank(cashchl)){
			cashchl = "GENERAL";
		}
		String cardId = request.getParameter("cardId");
		if(StringUtils.isBlank(cardId)){
			//提现银行卡不能为空
			return "{\"respcode\":\"f\",\"respdesc\":\"提现银行卡不能为空\"}";
		}
		//使用金开币
		
		Double coinamt = 0d;
		if(StringUtils.isNotBlank(coinamt_str)){
			try {
				coinamt = Double.parseDouble(coinamt_str);
			} catch (NumberFormatException e1) {
				//金开币金额错误
				return "{\"respcode\":\"f\",\"respdesc\":\"金开币金额错误\"}";
			}			
		}
		
		if(coinamt < 0){
			//金开币金额不正确
			return "{\"respcode\":\"f\",\"respdesc\":\"金开币金额错误\"}";
		}
		
		
		double balance = 0d;
		try {
			balance = Double.parseDouble(request.getParameter("amount"));
		} catch (NumberFormatException e) {
			// 金额错误
			return "{\"respcode\":\"f\",\"respdesc\":\"提现金额错误\"}";
		}
		
		if(balance <= 0){
			//金额不正确处理
			return "{\"respcode\":\"f\",\"respdesc\":\"提现金额错误\"}";
		}
		
		//服务费金额
		double servfee = 0d;
		try{
			servfee = Double.parseDouble(request.getParameter("servfee"));
		} catch (NumberFormatException e) {
			// 服务费金额类型错误
			return "{\"respcode\":\"f\",\"respdesc\":\"服务费金额错误\"}";
		}
		
		double voucheramt = 0d;
		if(StringUtils.isNotBlank(voucherno)){
			try {
				voucheramt = Double.parseDouble(voucheramt_str);
			} catch (NumberFormatException e) {
				return "{\"respcode\":\"f\",\"respdesc\":\"服务费金额错误\"}";
			}
		}
		
		//二次计算服务费 与 jsp页面对比
		double servfee1 = FeeRuleUtil.cashServfee(cashchl, PayConstant.CHAN_CHINAPNR, String.valueOf(balance), user.isVip() ? "1" : "0");
		servfee1 = FeeRuleUtil.calCashServfee(servfee1, coinamt, voucheramt);
		
		if(servfee != servfee1){
			return "{\"respcode\":\"f\",\"respdesc\":\"服务费计算有误\"}";
		}
		
		servfee = servfee1;
		
		FormData formData = FormDataUtil.createInputForm("CashApprInsert");
		FormDataUtil.setProperty(formData, "custid", user.getCustId());
		FormDataUtil.setProperty(formData, "custname", user.getCustName());
		FormDataUtil.setProperty(formData, "custacno", user.getCustAcNo());
		FormDataUtil.setProperty(formData, "chanid", PayConstant.CHAN_CHINAPNR);
		FormDataUtil.setProperty(formData, "memberid", user.getMemberId());
		FormDataUtil.setProperty(formData, "chalchl", cashchl);
		FormDataUtil.setProperty(formData, "cashapprstat", "0");
		FormDataUtil.setProperty(formData, "cashstat", "2");
		FormDataUtil.setProperty(formData, "appramt", balance);
		FormDataUtil.setProperty(formData, "servfee", servfee);
		FormDataUtil.setProperty(formData, "openbankid", cardId);
		FormDataUtil.setProperty(formData, "scoreamt", coinamt);
		FormDataUtil.setProperty(formData, "scroefee", coinamt);
		if(StringUtils.isNotBlank(voucherno)){
			FormDataUtil.setProperty(formData, "vecherid", voucherno);
			FormDataUtil.setProperty(formData, "vecheramt", voucheramt);
		}else{
			FormDataUtil.setProperty(formData, "vecheramt", "0");
		}
		FormDataUtil.setProperty(formData, "isvip", user.isVip() ? "1" : "0");
		formData = ServiceClient.getResponseFormData(formData, "CashApprInsert");
		return ServiceClient.parseObjToJson(formData, new SimpleDateFormat(DateUtils.SDF_DATETIME));
	}
	
	/**
	 * 会员发生额明细
	 * @param pageSize
	 * @param pageNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "acctdetail.html",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String accDetail(int pageSize, int pageNo, String startDate, String endDate,String chanid) {
		String memberId = Utils.getCurrentUser().getMemberId();
		FormData fd = FormDataUtil.createInputForm("AcctDetailQuery");
		FormDataUtil.setProperty(fd, "chanid", chanid);
		if(!StringUtils.isEmpty(startDate)) {
			FormDataUtil.setProperty(fd, "begindate_", startDate);
		}
		if(!StringUtils.isEmpty(endDate)) {
			FormDataUtil.setProperty(fd, "enddate_", endDate);
		}
		FormDataUtil.setProperty(fd, "memberid", memberId);
		Page<FormData> inPage = Page.buildPageFromRequest(pageSize, pageNo);
		Page<FormData> outData = ServiceClient.getResponseFormDataPage(fd, "AcctDetailQuery", inPage);
		MessageWrapper wrapper = messageHelpService.buildMessageWrapperWithOption(outData, "listtype","amtsign");
		String json = ServiceClient.parseObjToJson(wrapper, new SimpleDateFormat(DateUtils.SDF_DATETIME));
		return json;
	}
}
