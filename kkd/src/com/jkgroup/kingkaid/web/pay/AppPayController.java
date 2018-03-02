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

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.common.PayValidateException;
import com.jkgroup.kingkaid.service.CommonServiceClient;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.service.ValidateService;
import com.jkgroup.kingkaid.service.pay.PayServiceClient;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.MathUtil;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.FeeRuleUtil;
import com.jkgroup.kingkaid.utils.pay.PayConstant;
import com.jkgroup.kingkaid.utils.pay.PayUtil;
import com.jkgroup.kingkaid.utils.pay.PayUtil.PayChannel;
import com.jkgroup.kingkaid.utils.pay.PayUtil.PayOption;
import com.jkgroup.kingkaid.utils.pay.param.ChinaPnrPayParam;

/**
 * 手机App端提现、绑卡、投标、购买VIPfee、开启自动投标连接汇付接口
 * @author pan
 *
 */
@Controller
@RequestMapping(value="/app/resource")
public class AppPayController {

	private static final Logger log = LoggerFactory.getLogger(AppPayController.class);
	
	@Autowired
	private ValidateService validateService;
	
	/**
	 * 用户第三方注册
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="register.html")
	public String register(HttpServletRequest request,Model model, SitePreference sitePreference){
		//会员编号
		String memberId = request.getParameter("memberid");
		//请求令牌
		String requestToken = request.getParameter("requesttoken");
		FormData outData = null;
		
		try {
			validateService.isNotBlank(requestToken, "请求令牌不能为空");
			validateService.isNotBlank(memberId, "会员编号不能为空");
			// 验证用户，获取用户信息，验证token
			outData = checkMember(memberId, requestToken);
		} catch (PayValidateException e) {
			log.error("APP实名认证异常", e);
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.USERREGISTER, sitePreference, model);
		}
		
		String memberName = String.valueOf(FormDataUtil.getProperty(outData, "membername")); 
		return PayServiceClient.registerUser(memberId, memberName, model);
	}

	/**
	 * 验证用户，获取用户信息，验证token
	 * @param model
	 * @param sitePreference
	 * @param memberId
	 * @param requestToken
	 * @return
	 */
	private FormData checkMember(String memberId, String requestToken) {
		FormData formData = FormDataUtil.createInputForm("MemberInfo");
		FormDataUtil.setProperty(formData, "memberid", memberId);
		FormData outData = ServiceClient.getResponseFormData(formData,"MemberInfo");
		if(!FormDataUtil.isSucceed(outData)) {
			// 该会员不存在
			throw new PayValidateException("会员不存在");
		}
		String memberType = String.valueOf(FormDataUtil.getProperty(outData, "membertype"));
		if(memberType.equals("1")){
			throw new PayValidateException("企业会员不允许投资");
		}
		String apiToken = String.valueOf(FormDataUtil.getProperty(outData, "apitoken"));
		if(!apiToken.equals(requestToken)){
			//请求令牌错误
			throw new PayValidateException("请求令牌有误");
		}
		return outData;
	}
	
	/**
	 * 主动投标
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="tender.html")
	public String tender(HttpServletRequest request, Model model, SitePreference sitePreference) {
		//会员编号
		String memberId = request.getParameter("memberid");
		//投标金额
		String transamt = request.getParameter("tenderamt");
		//加息卷编号
		String inteaddno = request.getParameter("inteaddno");
		//使用金开币金额
		String coinamtstr = request.getParameter("coinamt");
		//借款编号
		String loanid = request.getParameter("loanid");
		//请求令牌
		String requestToken = request.getParameter("requesttoken");
		
		log.debug("会员编号----memberId:{}", memberId);
		log.debug("投标金额----transamt:{}", transamt);
		log.debug("加息卷编号----inteaddno:{}", inteaddno);
		log.debug("使用金开币金额----coinamtstr:{}", coinamtstr);
		log.debug("借款编号----loanid:{}", loanid);
		log.debug("请求令牌----requestToken:{}", requestToken);
		
		
		double amount = 0d;
		double coinamt = 0d;
		double inteaddrate = 0d;
		FormData outData = null;
		
		try {
			validateService.isNotBlank(requestToken, "请求令牌不存在");
			validateService.isNotBlank(memberId, "会员编号不能为空");
			validateService.isNotBlank(loanid, "借款编号不能为空");
			amount = validateService.isRightMoneyAmt(transamt, PayOption.INITIATIVETENDER.getDesc());
			coinamt = validateService.isRightCoinAmt(coinamtstr);
			inteaddrate = validateService.isRightCouponAmt(inteaddno, request.getParameter("inteaddrate"), "加息利率错误");
			outData = checkMember(memberId, requestToken);
		} catch (PayValidateException e) {
			log.error("APP投标异常", e);
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.INITIATIVETENDER, sitePreference, model);
		}
		
		String custId = String.valueOf(FormDataUtil.getProperty(outData, "custid"));
		String custAcNo = String.valueOf(FormDataUtil.getProperty(outData, "custacno"));
		String custName = String.valueOf(FormDataUtil.getProperty(outData, "custname"));
		
		String bidSource = request.getParameter("bidsource");
		
		//执行投标单据新增并组织汇付表单数据
		return PayServiceClient.tender(loanid, custId, custAcNo, custName, amount, inteaddno, inteaddrate, coinamt, bidSource, model, sitePreference);
	}
	
	/**
	 * 绑定银行卡
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="bind_card.html")
	public String bindCard(HttpServletRequest request, Model model, SitePreference sitePreference){
		//会员编号
		String memberId = request.getParameter("memberid");
		//请求令牌
		String requestToken = request.getParameter("requesttoken");
		
		FormData outData = null;
		try {
			validateService.isNotBlank(requestToken, "请求令牌不存在");
			validateService.isNotBlank(memberId, "会员编号不能为空");
			outData = checkMember(memberId, requestToken);
		} catch (PayValidateException e) {
			log.error("APP绑卡异常", e);
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.USERBINDCARD, sitePreference, model);
		}

		String custId = String.valueOf(FormDataUtil.getProperty(outData, "custid"));
		String custAcNo = String.valueOf(FormDataUtil.getProperty(outData, "custacno"));
		
		return PayServiceClient.bindcard(custAcNo, custId, model);
	}
	
	/**
	 * 会员提现
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="cash.html")
	public String cash(HttpServletRequest request,Model model, SitePreference sitePreference){
		//会员编号
		String memberId = request.getParameter("memberid");
		//提现优惠卷编号
		String voucherno = request.getParameter("voucherno");
		//请求令牌
		String requestToken = request.getParameter("requesttoken");
		//提现类型
		String cashchl = request.getParameter("cashchl");
		if(StringUtils.isBlank(cashchl)){
			cashchl = "GENERAL";
		}
		if(cashchl.equals("FAST")){
			cashchl = PayConstant.CHANCHL_FAST;
		}
		//卡号
		String cardId = request.getParameter("cardId");
		
		double amount = 0d;
		double coinamt = 0d;
		double voucheramt = 0d;
		double servfee = 0d;
		FormData outData = null;
		
		try {
			validateService.isNotBlank(cashchl, "提现类型不能为空");
			if(!cashchl.equals(PayConstant.CHANCHL_FAST) && !cashchl.equals(PayConstant.CHANCHL_GENERAL)){
				throw new PayValidateException("提现类型不存在");
			}
			validateService.isNotBlank(requestToken, "请求令牌不能为空");
			validateService.isNotBlank(memberId, "会员编号不能为空");
			amount = validateService.isRightMoneyAmt(request.getParameter("amount"), PayOption.CASH.getDesc());
			coinamt = validateService.isRightCoinAmt(request.getParameter("coinamt"));
			voucheramt = validateService.isRightCouponAmt(voucherno, request.getParameter("voucheramt"), "提现优惠价金额不正确");
			servfee = validateService.isDouble(request.getParameter("servfee"), "服务费金额不正确");
			outData = checkMember(memberId, requestToken);
		} catch (PayValidateException e) {
			log.error("APP提现异常", e);
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.CASH, sitePreference, model);
		}
		
		String vipstate = String.valueOf(FormDataUtil.getProperty(outData, "vipstate"));
		//二次计算服务费 与 jsp页面对比
		double servfee1 = FeeRuleUtil.cashServfee(cashchl, PayConstant.CHAN_CHINAPNR, String.valueOf(amount), vipstate);
		servfee1 = FeeRuleUtil.calCashServfee(servfee1, coinamt, voucheramt);
		if(servfee != servfee1){
			log.error("APP FEE: " + servfee + ", SERVER FEE: " + servfee1 + ", AMOUNT: " + amount + ", COINAMT: " + coinamt);
			return PayUtil.payError("服务费计算不一致", PayChannel.CHINAPNR, PayOption.CASH, sitePreference, model);
		}
		// 原始费用金额
		double origfee = FeeRuleUtil.calOriginalFee(cashchl, PayConstant.CHAN_CHINAPNR, String.valueOf(amount));
		amount = MathUtil.sub(amount, servfee);
		
		String custId = String.valueOf(FormDataUtil.getProperty(outData, "custid"));
		String custAcNo = String.valueOf(FormDataUtil.getProperty(outData, "custacno"));
		String memberName = String.valueOf(FormDataUtil.getProperty(outData, "membername"));
		

		return PayServiceClient.cash(null, custId, custAcNo, memberName, amount, servfee, origfee, cashchl, vipstate, coinamt, cardId, voucherno, voucheramt, model, sitePreference);
	}
	
	/**
	 * 快捷充值 - 手机端用
	 * 实际是手机端绑定银行卡，对于汇付天下
	 * 手机端快捷走的是连连支付
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/fast_deposit.html")
	public String fastDeposit(HttpServletRequest request, Model model, SitePreference sitePreference){
		//请求令牌
		String requestToken = request.getParameter("requesttoken");
		//会员编号
		String memberId = request.getParameter("memberid");
		
		double amount = 0d;
		FormData outData = null;
		
		try {
			validateService.isNotBlank(requestToken, "请求令牌不能为空");
			validateService.isNotBlank(memberId, "会员编号不能为空");
			amount = validateService.isRightMoneyAmt(request.getParameter("amount"), PayOption.NETSAVE.getDesc());
			outData = checkMember(memberId, requestToken);
		} catch (PayValidateException e) {
			log.error("APP快捷充值", e);
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.NETSAVE, sitePreference, model);
		}
		
		String custId = String.valueOf(FormDataUtil.getProperty(outData, "custid"));
		String custAcNo = String.valueOf(FormDataUtil.getProperty(outData, "custacno"));
		String memberName = String.valueOf(FormDataUtil.getProperty(outData, "membername"));
		
		//强制使用快捷支付
		String quick = "QP";
		String bankId = request.getParameter("bankId");
		String cardId = request.getParameter("cardId");
		String certId = request.getParameter("certId");
		
//		if(StringUtils.isBlank(bankId)){
//			return PayUtil.payError("", "", "银行代号不能为空", model);
//		}
//		
//		if(StringUtils.isBlank(cardId)){
//			return PayUtil.payError("", "", "卡号不能为空", model);
//		}
//		
//		if(StringUtils.isBlank(certId)){
//			return PayUtil.payError("", "", "身份证号不能为空", model);
//		}
		
		return PayServiceClient.deposit(amount, custAcNo, custId, memberName, quick, bankId, cardId, certId,model,sitePreference);
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
		//会员编号
		String memberId = request.getParameter("memberid");		
		//请求令牌
		String requestToken = request.getParameter("requesttoken");
		//债权转让编号
		String ctfId = request.getParameter("ctfid");
		
		FormData outData = null;
		
		try {
			validateService.isNotBlank(requestToken, "请求令牌不存在");
			validateService.isNotBlank(memberId, "会员编号不能为空");
			validateService.isNotBlank(ctfId, "债权转让编号不能为空");
			outData = checkMember(memberId, requestToken);
		} catch (PayValidateException e) {
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.CREDITASSIGN, sitePreference, model);
		}
		
		
		String custId = String.valueOf(FormDataUtil.getProperty(outData, "custid"));
		String custAcNo = String.valueOf(FormDataUtil.getProperty(outData, "custacno"));
		String custName = String.valueOf(FormDataUtil.getProperty(outData, "custname"));
		
		
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
			validateService.isUnequal(custId, TransferCustId, "债权转让人与转出人不能是同一个人");
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
		FormDataUtil.setProperty(formData, "buycustid", custId);
		FormDataUtil.setProperty(formData, "buycustacno", custAcNo);
		FormDataUtil.setProperty(formData, "buycustname", custName);
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
		map.put("BuyCustId", custAcNo);
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
	 * 会员支付VIP会员费接口(app)
	 * @return
	 */
	@RequestMapping(value="pay_vip_fee")
	public String vipfee(Model model,HttpServletRequest request, SitePreference sitePreference){
		
		//会员编号
		String memberId = request.getParameter("memberid");		
		//请求令牌
		String requestToken = request.getParameter("requesttoken");
		//购买vip期限
		String term = request.getParameter("term");
		
		FormData outData = null;
		
		//VIP金额
		double amount = 0d;
		double coinamt = 0d;
		double realamt = 0d;
		String custacno = null;
				
		try {
			validateService.isNotBlank(requestToken, "请求令牌不能为空");
			validateService.isNotBlank(memberId, "会员编号不能为空");
			// 验证用户，获取用户信息，验证token
			outData = checkMember(memberId, requestToken);
			
			custacno = String.valueOf(FormDataUtil.getProperty(outData, "custacno"));
			
			if (StringUtils.isBlank(custacno)) {
				throw new PayValidateException("您尚未开通第三方支付托管账户");
			}
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
		
		
		String custid = String.valueOf(FormDataUtil.getProperty(outData, "custid"));
		String membername = String.valueOf(FormDataUtil.getProperty(outData, "membername"));
		
		FormData formData = FormDataUtil.createInputForm("VipFeeInsert");
		FormDataUtil.setProperty(formData, "listtype", PayConstant.LIST_TYPE_VIPFEE);
		FormDataUtil.setProperty(formData, "busitype", PayConstant.BUSI_TYPE_VIPFEE);
		FormDataUtil.setProperty(formData, "chanid", PayConstant.CHAN_CHINAPNR);
		FormDataUtil.setProperty(formData, "custid", custid);
		FormDataUtil.setProperty(formData, "custloginname", membername);
		FormDataUtil.setProperty(formData, "custacno", custacno);
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
			map.put("UsrCustId", custacno);
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
	 * 自动投标计划开启
	 * @return
	 */
	@RequestMapping(value="autotenderopen")
	public String autotenderplan(Model model,HttpServletRequest request, SitePreference sitePreference){
		
		//会员编号
		String memberId = request.getParameter("memberid");		
		//请求令牌
		String requestToken = request.getParameter("requesttoken");
		
		FormData outData = null;
		String custacno = null;
		String custId = null;
		
		try {
			
			validateService.isNotBlank(requestToken, "请求令牌不能为空");
			validateService.isNotBlank(memberId, "会员编号不能为空");
			// 验证用户，获取用户信息，验证token
			outData = checkMember(memberId, requestToken);
			
			custacno = String.valueOf(FormDataUtil.getProperty(outData, "custacno"));
			
			custId = String.valueOf(FormDataUtil.getProperty(outData, "custid"));
			
			if (StringUtils.isBlank(custacno)) {
				throw new PayValidateException("您尚未开通第三方支付托管账户");
			}
			
		} catch (PayValidateException e) {
			return PayUtil.payError(e.getMessage(), PayChannel.CHINAPNR, PayOption.AUTOTENDERPLAN, sitePreference, model);
		}
		
		ChinaPnrPayParam pay = CommonServiceClient.getPayParams();
		Map<String,String> map = new LinkedHashMap<String, String>();
		map.put("Version", "10");
		map.put("CmdId", "AutoTenderPlan");
		map.put("MerCustId", pay.getMer_cust_id());
		map.put("UsrCustId", custacno);
		map.put("TenderPlanType", "W");
		map.put("RetUrl", pay.getRet_url());
		map.put("MerPriv", custId);
		String ChkValue = PayUtil.chkValue(map);
		ChkValue = CommonServiceClient.chkValue(ChkValue);
		map.put("ChkValue", ChkValue);
		String html = PayUtil.buildRequest(map, pay.getPay_uri());
		model.addAttribute("html", html);
		log.info(html);
		return PayConstant.FORWARD;
	}
	
}
