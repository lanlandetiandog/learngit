package com.jkgroup.kingkaid.web.pay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.common.PayValidateException;
import com.jkgroup.kingkaid.service.CommonServiceClient;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.service.ValidateService;
import com.jkgroup.kingkaid.service.pay.PayServiceClient;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.MathUtil;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.FeeRuleUtil;
import com.jkgroup.kingkaid.utils.pay.PayConstant;
import com.jkgroup.kingkaid.utils.pay.PayUtil;
import com.jkgroup.kingkaid.utils.pay.PayUtil.PayChannel;
import com.jkgroup.kingkaid.utils.pay.PayUtil.PayOption;
import com.jkgroup.kingkaid.utils.pay.param.ChinaPnrPayParam;

/**
 * 跳转第三方入口
 * @author pan
 * @createDate 2015-04-20
 */
@Controller
@RequestMapping(value=Constants.AUTH+"/pay")
public class PayController {

	@Autowired
	private ValidateService validateService;


	
	private static final Logger logger = LoggerFactory.getLogger(PayController.class);
	
	/**
	 * 开通第三方支付
	 * @return
	 */
	@RequestMapping(value="openpay")
	public String openpay(Model model,HttpServletRequest request){
		User user = Utils.getCurrentUser();
		if(user.isCorp()) {
			return PayServiceClient.registerCorp(user.getMemberId(), user.getMemberName(), model, user.isGuarantee());
		} else {
			return PayServiceClient.registerUser(user.getMemberId(), user.getMemberName(), model);
		}
	}
	
	/**
	 * 充值
	 * @return
	 */
	@RequestMapping(value="deposit")
	public String deposit(Model model,HttpServletRequest request, SitePreference sitePreference){
		User user = Utils.getCurrentUser();
		
		double balance = 0;
		try {
			validateService.hasCustAcNo(user);
			balance = validateService.isRightMoneyAmt(request.getParameter("amount"), PayOption.NETSAVE.getDesc());
		} catch (PayValidateException e) {
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.NETSAVE, sitePreference, model);
		}
		
		//是否快捷支付 
		String quick = request.getParameter("quick");
		String bankId = request.getParameter("bankid");
		String cardId = request.getParameter("cardId");
		String certId = "";
		if(user.getMemberType().equals("0")){	//个人会员 充值 必须传参数 quick bankid
			if(StringUtils.isBlank(quick)){
				return PayUtil.payError("请选择正确的充值渠道", PayChannel.CHINAPNR, PayOption.NETSAVE, sitePreference, model);
			}
			
			if(StringUtils.isBlank(bankId)){
				logger.info("充值-bank id: " + bankId);
//				return PayUtil.payError("请选择正确的充值银行", PayChannel.CHINAPNR, PayOption.NETSAVE, sitePreference, model);
			}
		}
		
		return PayServiceClient.deposit(balance, user.getCustAcNo(), user.getCustId(), user.getMemberName(), quick,bankId, cardId, certId, model, sitePreference);
	}
	
	/**
	 * 提现
	 * @return
	 */
	@RequestMapping(value="withdraw")
	public String withdraw(Model model,HttpServletRequest request, SitePreference sitePreference){
		User user = Utils.getCurrentUser();
		String isVIP = user.isVip() ? "1" : "0";
		
		String voucherno = request.getParameter("voucherno");
		String cashchl = request.getParameter("cashchl");
		if(StringUtils.isBlank(cashchl)){
			cashchl = "GENERAL";
		}
		
		if(cashchl.equals("FAST")){
			cashchl = PayConstant.CHANCHL_FAST;
		}
		
		String cardId = request.getParameter("cardId");

		double coinamt = 0d;
		double balance = 0d;
		double servfee = 0d;
		double voucheramt = 0d;
		try {
			validateService.hasCustAcNo(user);
			validateService.isNotBlank(cardId, "银行卡不能为空");
			coinamt = validateService.isRightCoinAmt(request.getParameter("coinamt"));
			balance = validateService.isRightMoneyAmt(request.getParameter("amount"), PayOption.CASH.getDesc());
			servfee = validateService.isDouble(request.getParameter("servfee"), "服务费金额计算错误");
			voucheramt = validateService.isRightCouponAmt(voucherno, request.getParameter("voucheramt"), "提现优惠券金额有误");
			//二次计算服务费 与 jsp页面对比
			double servfee1 = FeeRuleUtil.cashServfee(cashchl, PayConstant.CHAN_CHINAPNR, String.valueOf(balance), isVIP);
			servfee1 = FeeRuleUtil.calCashServfee(servfee1, coinamt, voucheramt);
			validateService.isEqual(servfee, servfee1, "服务费计算不一致");
		} catch (PayValidateException e) {
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.CASH, sitePreference, model);
		}
		// 原始费用金额
		double origfee = FeeRuleUtil.calOriginalFee(cashchl, PayConstant.CHAN_CHINAPNR, String.valueOf(balance));
		balance = MathUtil.sub(balance, servfee);
		return PayServiceClient.cash(null, user.getCustId(), user.getCustAcNo(), user.getMemberName(), balance, servfee, origfee, cashchl, isVIP, coinamt, cardId, voucherno, voucheramt, model, sitePreference);
	}
	
	/**
	 * 汇付余额提现
	 * @return
	 */
	@RequestMapping(value="withdrawAll")
	public String withdrawAll(Model model,HttpServletRequest request, SitePreference sitePreference){
		User user = Utils.getCurrentUser();
		String isVIP = user.isVip() ? "1" : "0";		
		String cashchl = "GENERAL";
		String cardId = request.getParameter("cardId");
		String amount = request.getParameter("amount");
		double balance = 0d;
		
		// 汇付天下用户信息查询
		FormData MemberInfo = FormDataUtil.createInputForm("ChinapnrMemberInfo");
		FormDataUtil.setProperty(MemberInfo, "memberid", user.getMemberId());
		MemberInfo = ServiceClient.getResponseFormData(MemberInfo, "ChinapnrMemberInfo");
		
		String custacno = (String) FormDataUtil.getProperty(MemberInfo, "custacno") ;
		
		try {
			if (StringUtils.isBlank(custacno)) {
				throw new PayValidateException("您尚未开通第三方支付托管账户");
			}
			validateService.isNotBlank(cardId, "银行卡不能为空");
			validateService.isNotBlank(amount, "可提现金额不能为空");
			balance = validateService.isRightMoneyAmt(amount, PayOption.CASH.getDesc());
			
		} catch (PayValidateException e) {
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.CASH, sitePreference, model);
		}
		return PayServiceClient.cash(null, user.getCustId(), custacno, user.getMemberName(), balance, 0d, 0d, cashchl, isVIP, 0d, cardId, null, 0d, model, sitePreference);
	}
	
	/**
	 * 通过审批列表发起提现
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/withdraw_appr")
	public String withdrawByAppr(Model model,HttpServletRequest request, SitePreference sitePreference){
		User user = Utils.getCurrentUser();
		String cashId = request.getParameter("cashId");

		try {
			validateService.hasCustAcNo(user);
			validateService.isNotBlank(cashId, "提现审批编号不正确");
		} catch (PayValidateException e) {
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.CASH, sitePreference, model);
		}
		
		FormData formData = FormDataUtil.createInputForm("CashApprSelect");
		FormDataUtil.setProperty(formData, "cashid", cashId);
		formData = ServiceClient.getResponseFormData(formData, "CashApprSelect");
		String respCode = String.valueOf(FormDataUtil.getProperty(formData, "respcode"));
		String respDesc = String.valueOf(FormDataUtil.getProperty(formData, "respdesc"));
		
		if(!respCode.equals(FormData.SUCCESS)){
			return PayUtil.payError(respDesc, PayChannel.CHINAPNR, PayOption.CASH, sitePreference, model);
		}
		
		double amount = Double.parseDouble(String.valueOf(FormDataUtil.getProperty(formData, "appramt")));
		double servfee = Double.parseDouble(String.valueOf(FormDataUtil.getProperty(formData, "servfee")));
		double coinamt = Double.parseDouble(String.valueOf(FormDataUtil.getProperty(formData, "scoreamt")));
		String cashchl = String.valueOf(FormDataUtil.getProperty(formData, "chalchl"));
		if(StringUtils.isBlank(cashchl)){
			cashchl = "GENERAL";
		}
		if(cashchl.equals("FAST")){
			cashchl = PayConstant.CHANCHL_FAST;
		}
		String cardId = String.valueOf(FormDataUtil.getProperty(formData, "openacctid"));
		String voucherno = String.valueOf(FormDataUtil.getProperty(formData, "vecherid"));
		String voucheramt = String.valueOf(FormDataUtil.getProperty(formData, "vecheramt"));
		String isVip = String.valueOf(FormDataUtil.getProperty(formData, "isvip"));
		// 原始费用金额
		double origfee = FeeRuleUtil.calOriginalFee(cashchl, PayConstant.CHAN_CHINAPNR, String.valueOf(amount));
		amount = amount - servfee;
		return PayServiceClient.cash(cashId,user.getCustId(), user.getCustAcNo(), user.getMemberName(), amount, servfee, origfee, cashchl, isVip, coinamt, cardId, voucherno, Double.parseDouble(voucheramt), model, sitePreference);
	}
	
	/**
	 * 绑定银行卡
	 * @return
	 */
	@RequestMapping(value="bindcard")
	public String bindcard(Model model,HttpServletRequest request, SitePreference sitePreference){
		User user = Utils.getCurrentUser();
		try {
			validateService.hasCustAcNo(user);
		}catch (PayValidateException e) {
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.USERBINDCARD, sitePreference, model);
		}
		return PayServiceClient.bindcard(user.getCustAcNo(), user.getCustId(), model);
	}
	
	/**
	 * 会员主动投标
	 * @return
	 */
	@RequestMapping(value="tender")
	public String tender(Model model,HttpServletRequest request, SitePreference sitePreference){
		User user = Utils.getCurrentUser();
		//业务申请编号
		String loanid = request.getParameter("loanid");
		//加息卷编号
		String inteaddno = request.getParameter("inteaddno");
		
		if(user.getMemberType().equals("1")){
			return PayUtil.payError("企业会员不允许投资", PayChannel.CHINAPNR, PayOption.INITIATIVETENDER, sitePreference, model);
		}
		
		double inteaddrate = 0d;
		double amount = 0d;
		double coinamt = 0d;
		try {
			validateService.hasCustAcNo(user);
			validateService.isNotBlank(loanid, "借款编号不正确");
			inteaddrate = validateService.isRightCouponAmt(inteaddno, request.getParameter("inteaddrate"), "加息利率错误");
			amount = validateService.isRightMoneyAmt(request.getParameter("amount"), PayOption.INITIATIVETENDER.getDesc());
			coinamt = validateService.isRightCoinAmt(StringUtils.isBlank(request.getParameter("coinamt"))?"0":request.getParameter("coinamt"));
		} catch (PayValidateException e) {
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.INITIATIVETENDER, sitePreference, model);
		}
		// 网页
		String bidSource = "2";
		return PayServiceClient.tender(loanid, user.getCustId(), user.getCustAcNo(), user.getCustName(), amount, inteaddno, inteaddrate, coinamt,bidSource,model,sitePreference);
	}
	
	/**
	 * 汇付天下登录接口
	 * @return
	 */
	@RequestMapping(value="chinapnr")
	public String chinapnr(Model model,HttpServletRequest request, SitePreference sitePreference){
		User user = Utils.getCurrentUser();
		try {
			validateService.hasCustAcNo(user);
		} catch (PayValidateException e) {
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.USERLOGIN, sitePreference, model);
		}
		return PayServiceClient.login(model, user);
	}

	/**
	 * 会员支付VIP会员费接口
	 * @return
	 */
	@RequestMapping(value="pay_vip_fee")
	public String vipfee(Model model,HttpServletRequest request, SitePreference sitePreference){
		User user = Utils.getCurrentUser();
		String term = request.getParameter("term");
		
		//VIP金额
		double amount = 0d;
		double coinamt = 0d;
		double realamt = 0d;
		try {
			validateService.hasCustAcNo(user);
			amount = validateService.isRightMoneyAmt(request.getParameter("amount"), PayOption.USRACCTPAY.getDesc());
			validateService.isInteger(term, "购买VIP期限不正确");
			
			Object[] calFee = FeeRuleUtil.calVipfee(Integer.parseInt(term), null);
			validateService.isEqual(amount, ((Double)calFee[0]).doubleValue(), "VIP支付金额有误");
			
			coinamt = validateService.isRightCoinAmt(request.getParameter("coinamt"));
			realamt = validateService.isRightMoneyAmt(request.getParameter("realamt"), PayOption.USRACCTPAY.getDesc());
			validateService.isEqual(realamt + coinamt, amount, "VIP支付金额有误");
			validateService.isLessThan(realamt, 1d, "实际支付金额不能小于1元");
		} catch (PayValidateException e) {
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.USRACCTPAY, sitePreference, model);
		}
		
		FormData formData = FormDataUtil.createInputForm("VipFeeInsert");
		FormDataUtil.setProperty(formData, "listtype", PayConstant.LIST_TYPE_VIPFEE);
		FormDataUtil.setProperty(formData, "busitype", PayConstant.BUSI_TYPE_VIPFEE);
		FormDataUtil.setProperty(formData, "chanid", PayConstant.CHAN_CHINAPNR);
		FormDataUtil.setProperty(formData, "custid", user.getCustId());
		FormDataUtil.setProperty(formData, "custloginname", user.getMemberName());
		FormDataUtil.setProperty(formData, "custacno", user.getCustAcNo());
		FormDataUtil.setProperty(formData, "vipfee", realamt);
		FormDataUtil.setProperty(formData, "paymonth", term);
		FormDataUtil.setProperty(formData, "acbankid", PayConstant.ACBANKID);
		if(coinamt > 0){
			FormDataUtil.setProperty(formData, "coinamt", coinamt);			
		}else{
			FormDataUtil.setProperty(formData, "coinamt", 0);
		}
		formData = ServiceClient.getResponseFormData(formData, "VipFeeInsert");
		if(FormDataUtil.isSucceed(formData)){
			String ordId = String.valueOf(FormDataUtil.getProperty(formData, "otherlistno"));
			String listId = String.valueOf(FormDataUtil.getProperty(formData, "listid"));
			
			//使用用户账户支付接口
			ChinaPnrPayParam pay = CommonServiceClient.getPayParams();
			
			Map<String,String> map = new LinkedHashMap<String, String>();
			map.put("Version", "10");
			map.put("CmdId", "UsrAcctPay");
			map.put("OrdId", ordId);
			map.put("UsrCustId", user.getCustAcNo());
			map.put("MerCustId", pay.getMer_cust_id());
			map.put("TransAmt", MathUtil.numFmt(realamt));
			map.put("InAcctId", PayConstant.MDT000001);
			map.put("InAcctType", PayConstant.ACCTTYPE);
			map.put("RetUrl", pay.getRet_url());
			map.put("BgRetUrl", pay.getBg_ret_url());
			map.put("MerPriv", listId);
			String ChkValue = PayUtil.chkValue(map);
			ChkValue = CommonServiceClient.chkValue(ChkValue);
			map.put("ChkValue", ChkValue);
			String html = PayUtil.buildRequest(map, pay.getPay_uri());
			model.addAttribute("html", html);
			return PayConstant.FORWARD;
		}else{
			return PayUtil.payError(FormDataUtil.getErrorMessage(formData), PayChannel.CHINAPNR, PayOption.USRACCTPAY, sitePreference, model);
		}
	}
	
	/**
	 * 生利宝购买/赎回接口
	 * @param model
	 * @param request
	 * @return
	 */
	/*@RequestMapping(value="fsstran")
	public String fssTran(Model model,HttpServletRequest request, SitePreference sitePreference){
		User user = Utils.getCurrentUser();
		
		try {
			validateService.hasCustAcNo(user);
		} catch (PayValidateException e) {
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.FSSTRANS, sitePreference, model);
		}
		
		FormData formData = FormDataUtil.createInputForm("FssTranInsert");
		FormDataUtil.setProperty(formData, "listtype", PayConstant.LIST_TYPE_FSSTRAN);
		FormDataUtil.setProperty(formData, "chanid", PayConstant.CHAN_CHINAPNR);
		FormDataUtil.setProperty(formData, "custid", user.getCustId());
		FormDataUtil.setProperty(formData, "custname", user.getCustName());
		FormDataUtil.setProperty(formData, "custacno", user.getCustAcNo());
		FormDataUtil.setProperty(formData, "acbankid", PayConstant.ACBANKID);
		formData = ServiceClient.getResponseFormData(formData, "FssTranInsert");
		if(FormDataUtil.isSucceed(formData)){
			String ordId = String.valueOf(FormDataUtil.getProperty(formData, "otherlistno"));
			String ordDate = DateUtils.getYMDDate((Date) FormDataUtil.getProperty(formData, "transdate")); 
			String listId = String.valueOf(FormDataUtil.getProperty(formData, "listid"));
			
			ChinaPnrPayParam pay = CommonServiceClient.getPayParams();
			Map<String,String> map = new LinkedHashMap<String, String>();
			map.put("Version", "10");
			map.put("CmdId", "FssTrans");
			map.put("MerCustId", pay.getMer_cust_id());
			map.put("UsrCustId", user.getCustAcNo());
			map.put("OrdId", ordId);
			map.put("OrdDate", ordDate);
			map.put("RetUrl", pay.getRet_url());
			map.put("BgRetUrl", pay.getBg_ret_url());
			map.put("MerPriv", listId);
			String ChkValue = PayUtil.chkValue(map);
			ChkValue = CommonServiceClient.chkValue(ChkValue);
			map.put("ChkValue", ChkValue);
			String html = PayUtil.buildRequest(map, pay.getPay_uri());
			model.addAttribute("html", html);
			return PayConstant.FORWARD;
		}else{
			return PayUtil.payError(FormDataUtil.getErrorMessage(formData), PayChannel.CHINAPNR, PayOption.FSSTRANS, sitePreference, model);
		}
		
	}*/
	
	/**
	 * 汇付天下明细账查询
	 * @return
	 */
	@RequestMapping(value="chinapnraccount")
	public String chinapnraccount(Model model,HttpServletRequest request){
		return PayConstant.FORWARD;
	}
	
	/**
	 * 自动投标计划开启
	 * @return
	 */
	@RequestMapping(value="autotenderopen")
	public String autotenderplan(Model model,HttpServletRequest request, SitePreference sitePreference){
		User user = Utils.getCurrentUser();
		
		try {
			validateService.hasCustAcNo(user);
		} catch (PayValidateException e) {
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.AUTOTENDERPLAN, sitePreference, model);
		}
		
		ChinaPnrPayParam pay = CommonServiceClient.getPayParams();
		Map<String,String> map = new LinkedHashMap<String, String>();
		map.put("Version", "10");
		map.put("CmdId", "AutoTenderPlan");
		map.put("MerCustId", pay.getMer_cust_id());
		map.put("UsrCustId", user.getCustAcNo());
		map.put("TenderPlanType", "W");
		map.put("RetUrl", pay.getRet_url());
		map.put("MerPriv", user.getCustId());
		String ChkValue = PayUtil.chkValue(map);
		ChkValue = CommonServiceClient.chkValue(ChkValue);
		map.put("ChkValue", ChkValue);
		String html = PayUtil.buildRequest(map, pay.getPay_uri());
		model.addAttribute("html", html);
		logger.info(html);
		return PayConstant.FORWARD;
	}
	
	/**
	 * 自动投标计划关闭
	 * @return
	 */
	@RequestMapping(value="autotenderclose")
	public String autotenderclose(Model model,HttpServletRequest request, SitePreference sitePreference){
		User user = Utils.getCurrentUser();
		
		try {
			validateService.hasCustAcNo(user);
		} catch (PayValidateException e) {
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.AUTOTENDERPLANCLOSE, sitePreference, model);
		}
		
		ChinaPnrPayParam pay = CommonServiceClient.getPayParams();
		Map<String,String> map = new LinkedHashMap<String, String>();
		map.put("Version", "10");
		map.put("CmdId", "AutoTenderPlanClose");
		map.put("MerCustId", pay.getMer_cust_id());
		map.put("UsrCustId", user.getCustAcNo());
		map.put("RetUrl", pay.getRet_url());
		map.put("MerPriv", user.getCustId());
		String ChkValue = PayUtil.chkValue(map);
		ChkValue = CommonServiceClient.chkValue(ChkValue);
		map.put("ChkValue", ChkValue);
		String html = PayUtil.buildRequest(map, pay.getPay_uri());
		model.addAttribute("html", html);
		return PayConstant.FORWARD;
	}
	
	/**
	 * 债权转让
	 * @param model
	 * @param request
	 * @return
	 * @throws 
	 */
	@RequestMapping(value="creditassign")
	public String creditAssign(Model model,HttpServletRequest request, SitePreference sitePreference) throws Exception{
		User user = Utils.getCurrentUser();
		//债权转让编号
		String ctfId = request.getParameter("ctfId");
		
		try {
			validateService.hasCustAcNo(user);
			validateService.isNotBlank(ctfId, "债权转让编号不能为空");
		} catch (PayValidateException e) {
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.CREDITASSIGN, sitePreference, model);
		}
		
		FormData creditformData = FormDataUtil.createInputForm("CreditDetail");
		FormDataUtil.setProperty(creditformData, "creditortransferid", ctfId);
		creditformData = ServiceClient.getResponseFormData(creditformData, "CreditDetail");
		FormDataUtil.print(creditformData);
			
		//借款申请编号
		String loanid = String.valueOf(FormDataUtil.getProperty(creditformData, "loanid"));
		//分户账编号
		String LoanAcNo = String.valueOf(FormDataUtil.getProperty(creditformData, "LoanAcNo"));
		
		//转让客户信息
		String TransferCustId = String.valueOf(FormDataUtil.getProperty(creditformData, "TransferCustId"));
		String TransferCustName = String.valueOf(FormDataUtil.getProperty(creditformData, "TransferCustName"));
		String TransferCustAcNo = String.valueOf(FormDataUtil.getProperty(creditformData, "TransferCustAcNo"));
		
		//投资编号
		String BidId = String.valueOf(FormDataUtil.getProperty(creditformData, "BidId"));
		//投标订单号
		String BidOrderNo = String.valueOf(FormDataUtil.getProperty(creditformData, "BidOrderNo"));
		//投标订单日期
		Date bidorderdate = (Date) FormDataUtil.getProperty(creditformData, "BidOrderDate");
		String BidOrderDate = DateUtils.getYMDDate(bidorderdate);
		//产品编号
		String prodid = String.valueOf(FormDataUtil.getProperty(creditformData, "prodid"));
		//转让金额
		double TransferMoney = Double.parseDouble(String.valueOf(FormDataUtil.getProperty(creditformData, "TransferMoney")));
		//服务费
		double ServiceFee = Double.parseDouble(String.valueOf(FormDataUtil.getProperty(creditformData, "ServiceFee")));
		//已还款金额
		double RepayAmt = Double.parseDouble(String.valueOf(FormDataUtil.getProperty(creditformData, "repayamt")));
		//转让状态
		String TransferState = String.valueOf(FormDataUtil.getProperty(creditformData, "TransferState"));
		/*
		 * 债权转让过期判断
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String disabledate = sdf.format(FormDataUtil.getProperty(creditformData, "disabledate"));
  		Date disdate=sdf.parse(disabledate);
		
		Date nowtime=new Date();
		try {
			if(disdate.getTime() >= nowtime.getTime()){
				validateService.isEqual(TransferState, "0", "该债权已过期,不能申购");
			}else if(disdate.getTime() < nowtime.getTime()){
				validateService.isEqual(TransferState, "0", "该债权已成交或已经取消,不能申购");
			}
			//判断债权转让状态是否可以购买债权产品
		//	validateService.isEqual(TransferState, "0", "该债权已成交或已经取消,不能申购");
			//不能承接自己的债权
			validateService.isUnequal(user.getCustId(), TransferCustId, "债权转让人与转出人不能是同一个人");
		} catch (PayValidateException e) {
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.CREDITASSIGN, sitePreference, model);
		}
		
		
		
		//新增债权转让单据
		FormData formData = FormDataUtil.createInputForm("CreditAssignInsert");
		FormDataUtil.setProperty(formData, "listtype", PayConstant.LIST_TYPE_CREDIT);
		FormDataUtil.setProperty(formData, "busitype", PayConstant.BUSI_TYPE_CREDIT);
		FormDataUtil.setProperty(formData, "chanid", PayConstant.CHAN_CHINAPNR);
		FormDataUtil.setProperty(formData, "acbankid", PayConstant.ACBANKID);
		FormDataUtil.setProperty(formData, "custid", TransferCustId);
		FormDataUtil.setProperty(formData, "custacno", TransferCustAcNo);
		FormDataUtil.setProperty(formData, "custname", TransferCustName);
		FormDataUtil.setProperty(formData, "buycustid", user.getCustId());
		FormDataUtil.setProperty(formData, "buycustacno", user.getCustAcNo());
		FormDataUtil.setProperty(formData, "buycustname", user.getCustName());
		FormDataUtil.setProperty(formData, "loanid", loanid);
		FormDataUtil.setProperty(formData, "loanacno", LoanAcNo);
		FormDataUtil.setProperty(formData, "bidno", BidId);
		FormDataUtil.setProperty(formData, "prodid", prodid);
		FormDataUtil.setProperty(formData, "tenderordid", BidOrderNo);
		FormDataUtil.setProperty(formData, "tenderdate", bidorderdate);
		FormDataUtil.setProperty(formData, "creditamt", TransferMoney);
		FormDataUtil.setProperty(formData, "creditdealamt", TransferMoney);
		FormDataUtil.setProperty(formData, "fee", ServiceFee);
		FormDataUtil.setProperty(formData, "printamt", RepayAmt);
		FormDataUtil.setProperty(formData, "creditortransferid", ctfId);
		formData = ServiceClient.getResponseFormData(formData, "CreditAssignInsert");
		if(!FormDataUtil.isSucceed(formData)){
			return PayUtil.payError(FormDataUtil.getErrorMessage(formData), PayChannel.CHINAPNR, PayOption.CREDITASSIGN, sitePreference, model);
		}
		String listid = String.valueOf(FormDataUtil.getProperty(formData, "listid"));
		String loancustacno = String.valueOf(FormDataUtil.getProperty(formData, "loancustacno"));
		String otherlistno = String.valueOf(FormDataUtil.getProperty(formData, "otherlistno"));
		String transdate = DateUtils.getYMDDate((Date) FormDataUtil.getProperty(formData, "transdate"));
		
		ChinaPnrPayParam pay = CommonServiceClient.getPayParams();
		Map<String,String> map = new LinkedHashMap<String, String>();
		map.put("Version", "10");
		map.put("CmdId", "CreditAssign");
		map.put("MerCustId", pay.getMer_cust_id());
		map.put("SellCustId", TransferCustAcNo);
		map.put("CreditAmt", MathUtil.numFmt(TransferMoney));
		map.put("CreditDealAmt", MathUtil.numFmt(TransferMoney));
		String bidDetails = "{\"BidDetails\":[{\"BidOrdId\":\""+BidOrderNo+"\",\"BidOrdDate\":\""+BidOrderDate+"\",\"BidCreditAmt\":\""+MathUtil.numFmt(TransferMoney)+"\",\"BorrowerDetails\":[{\"BorrowerCustId\":\""+loancustacno+"\",\"BorrowerCreditAmt\":\""+MathUtil.numFmt(TransferMoney)+"\",\"PrinAmt\":\""+MathUtil.numFmt(RepayAmt)+"\"}]}]}";
		map.put("BidDetails", bidDetails);
		map.put("Fee", MathUtil.numFmt(ServiceFee));
		String divDetails = "[{\"DivAcctId\":\""+PayConstant.MDT000001+"\",\"DivAmt\":\""+MathUtil.numFmt(ServiceFee)+"\"}]";
		map.put("DivDetails", divDetails);
		map.put("BuyCustId", user.getCustAcNo());
		map.put("OrdId", otherlistno);
		map.put("OrdDate", transdate);
		map.put("RetUrl", pay.getRet_url());
		map.put("BgRetUrl", pay.getBg_ret_url());
		map.put("MerPriv", listid);
		String ChkValue = PayUtil.chkValue(map);
		ChkValue = CommonServiceClient.chkValue(ChkValue);
		map.put("ChkValue", ChkValue);
		String html = PayUtil.buildRequest(map, pay.getPay_uri());
		model.addAttribute("html", html);
		return PayConstant.FORWARD;
	}
	
	/**
	 * 
	 * @param cardid
	 */
	@RequestMapping(value="/delete_bankcard.html",produces = "text/html; charset=utf-8")
	public @ResponseBody String deleteCard(String keyno){
		FormData formData = FormDataUtil.createInputForm("DeleteBankCard");
		FormDataUtil.setProperty(formData, "keyno", keyno);
		formData = ServiceClient.getResponseFormData(formData, "DeleteBankCard");
		return ServiceClient.parseObjToJson(formData);
	}
}
