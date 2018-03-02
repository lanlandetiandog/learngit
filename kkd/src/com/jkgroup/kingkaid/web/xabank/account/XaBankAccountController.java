package com.jkgroup.kingkaid.web.xabank.account;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.MessageWrapper;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.common.PropertiesUtil;
import com.jkgroup.kingkaid.service.CommonServiceClient;
import com.jkgroup.kingkaid.service.FeeRuleServiceClient;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.Base64Util;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.FileUtil;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.bo.fee.ChanFee;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.FeeRuleUtil;
import com.jkgroup.kingkaid.utils.pay.PayConstant;

@Controller
@RequestMapping(value=Constants.AUTH+"/xabank/acct/")
public class XaBankAccountController {

	private static final String DEPOSIT_LIST_ID = "DepositListId";
	public static final Logger logger = LoggerFactory.getLogger(XaBankAccountController.class);
	
	/**
	 * 转到充值页面
	 * @return
	 */
	@RequestMapping(value="deposit_page.html")
	public String depositPage(Model model){
		User user = Utils.getCurrentUser();
		//余额查询
		FormData acctInfo = CommonServiceClient.getNewBalance();
		model.addAttribute("acctInfo", acctInfo);
		
		FormData formData = FormDataUtil.createInputForm("BankCardList");
		FormDataUtil.setProperty(formData, "memberid", user.getMemberId());
		FormDataUtil.setProperty(formData, "chanid", PayConstant.CHAN_XABANK);
		List<FormData> formDatas = ServiceClient.getResponseFormDataList(formData, "BankCardList");
		if(formDatas != null && formDatas.size() > 0){
			model.addAttribute("bankcard", formDatas.get(0));
		}
		return "xabank/"+ (user.isCorp()? "account/corp/": "account/") +"deposit";
	}

	/**
	 * 转到更换银行卡界面
	 * @return
	 */
	@RequestMapping(value="change_card_page.html")
	public String changeCardPage(Model model){
		User user = Utils.getCurrentUser();
		//余额查询
		FormData acctInfo = CommonServiceClient.getBalance();
		model.addAttribute("acctInfo", acctInfo);
		
		FormData formData = FormDataUtil.createInputForm("BankCardList");
		FormDataUtil.setProperty(formData, "memberid", user.getMemberId());
		FormDataUtil.setProperty(formData, "chanid", PayConstant.CHAN_XABANK);
		List<FormData> formDatas = ServiceClient.getResponseFormDataList(formData, "BankCardList");
		if(formDatas != null && formDatas.size() > 0)
			model.addAttribute("bankcard", formDatas.get(0));
		model.addAttribute("user", user);
//		if(acctInfo != null && accountSettled(acctInfo)) 
//			return "xabank/account/unbundling";
//		else 
		return "xabank/account/changeCard";
	}
	
	public boolean accountSettled(FormData acctInfo) {
		return Double.parseDouble(FormDataUtil.getProperty(acctInfo, "bal").toString()) == 0
				&& Double.parseDouble(FormDataUtil.getProperty(acctInfo, "fbal").toString()) == 0
				&& Double.parseDouble(FormDataUtil.getProperty(acctInfo, "dincome").toString()) == 0
				&& Double.parseDouble(FormDataUtil.getProperty(acctInfo, "dcapi").toString()) == 0;
	}
	
	/**
	 * 跳转至绑定银行卡
	 * @return
	 */
	@RequestMapping(value="bunding_card_page.html")
	public String bundingCardPage(Model model){
		User user = Utils.getCurrentUser();
		
		FormData formData = FormDataUtil.createInputForm("BankCardList");
		FormDataUtil.setProperty(formData, "memberid", user.getMemberId());
		FormDataUtil.setProperty(formData, "chanid", PayConstant.CHAN_XABANK);
		List<FormData> formDatas = ServiceClient.getResponseFormDataList(formData, "BankCardList");
		model.addAttribute("user", user);
		if(formDatas != null && formDatas.size() > 0) {
			model.addAttribute("bankcard", formDatas.get(0));
			return "forward:/auth/usercenter/myproperty.html";
		}
		else return "xabank/account/bundling";
	}
	
	/**
	 * 转到提现页面
	 * @return
	 */
	@RequestMapping(value="withdraw_page.html")
	public String withDrawPage(Model model){
		User user = Utils.getCurrentUser();
		
		FormData loancount = FormDataUtil.createInputForm("LoanCountSelect");
		FormDataUtil.setProperty(loancount, "memberid", user.getMemberId());
		loancount = ServiceClient.getResponseFormData(loancount, "LoanCountSelect");
		model.addAttribute("loancount", String.valueOf(FormDataUtil.getProperty(loancount, "loancount")));
		model.addAttribute("apprcount", String.valueOf(FormDataUtil.getProperty(loancount, "apprcount")));
		

		//手续费用收取规则
		ChanFee chanFee = FeeRuleServiceClient.getChanFee(PayConstant.CHAN_XABANK);
		//余额查询
		FormData acctInfo = CommonServiceClient.getNewBalance();
		
		//提现优惠卷查询
		List<FormData> raisintes = CommonServiceClient.getRaisintList(user.getMemberId(), "2");
		
		
		model.addAttribute("raisintes", raisintes);
		model.addAttribute("chanfee", chanFee);
		model.addAttribute("acctInfo", acctInfo);
		
		//银行卡
		FormData formData = FormDataUtil.createInputForm("BankCardList");
		FormDataUtil.setProperty(formData, "memberid", user.getMemberId());
		FormDataUtil.setProperty(formData, "chanid", PayConstant.CHAN_XABANK);
		List<FormData> formDatas = ServiceClient.getResponseFormDataList(formData, "BankCardList");
		if(formDatas != null && formDatas.size() > 0){
			model.addAttribute("bankcard", formDatas.get(0));
		}
		return "xabank/"+(user.isCorp()? "account/corp/": "account/")+"/withdraw";
	}
	
	/**
	 * 提现新增交易
	 * @param transamt
	 * @param acctno
	 * @return
	 */
	@RequestMapping(value="withdraw.html",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String withdraw(String transamt,String acctno,String custfee,String coinamt,String voucherno,String voucheramt){
		User user = Utils.getCurrentUser();
		String json = "";
		
		String isVIP = user.isVip() ? "1" : "0";
		if (StringUtils.isBlank(user.getCustAcNo())){
			return json = "{\"code\":\"f\",\"msg\":\"您尚未开通银行存管电子账户，请您立即开通。\"}";
		}
		if (StringUtils.isBlank(acctno)){
			return json = "{\"code\":\"f\",\"msg\":\"银行卡不能为空\"}";
		}
		
		//transamt:提现金额；acctno：银行卡；transpwd：交易密码；coinamt：使用金开币;voucherno:加息券编号;custfee:服务费;voucheramt:提现优惠券金额
		double amounts = 0d;
		double coinamts = 0d;
		double servfee = 0d;
		double voucheramts = 0d;
		
		//提现使用金开币数额的校验
		if(StringUtils.isNotBlank(coinamt)){
			try {
				coinamts = Double.parseDouble(coinamt);
			} catch (NumberFormatException e1) {
				return json = "{\"code\":\"f\",\"msg\":\"金开币兑换金额有误\"}";
			}			
		}
		if(coinamts < 0){
			return json = "{\"code\":\"f\",\"msg\":\"金开币兑换金额有误\"}";
		}
		
		//提现金额校验
		try {
			amounts = Double.parseDouble(transamt);
		} catch (NumberFormatException e) {
			return json = "{\"code\":\"f\",\"msg\":\"提现金额不正确\"}";
		}
		if (amounts <= 0) {
			return json = "{\"code\":\"f\",\"msg\":\"提现金额不正确\"}";
		}		
		
		//服务费金额格式校验
		try{
			servfee = Double.parseDouble(custfee);
		} catch (NumberFormatException e) {
			return json = "{\"code\":\"f\",\"msg\":\"服务费金额计算错误\"}";
		}
		
		//提现优惠券校验
		if(StringUtils.isNotBlank(voucherno)){
			try {
				voucheramts = Double.parseDouble(voucheramt);
			} catch (NumberFormatException e) {
				return json = "{\"code\":\"f\",\"msg\":\"提现优惠券金额有误\"}";
			}
		}
		
		//二次计算服务费 与 jsp页面对比
		double servfee1 = FeeRuleUtil.cashServfee("IMMEDIATE", PayConstant.CHAN_XABANK, String.valueOf(amounts), isVIP);
		servfee1 = FeeRuleUtil.calCashServfee(servfee1, coinamts, voucheramts);
		if(servfee != servfee1) {
			return json = "{\"code\":\"f\",\"msg\":\"服务费计算不一致\"}";
		}
		
		//amounts = MathUtil.sub(amounts, servfee);
		
		FormData formData = FormDataUtil.createInputForm("WithDrawListInsert");
		FormDataUtil.setProperty(formData, "memberid", user.getMemberId());
		FormDataUtil.setProperty(formData, "transamt", amounts);
		FormDataUtil.setProperty(formData, "acctno", acctno);
		FormDataUtil.setProperty(formData, "coinamt", coinamts);
		FormDataUtil.setProperty(formData, "custfee", servfee);
		FormDataUtil.setProperty(formData, "applyid", "");
		if(StringUtils.isNotBlank(voucherno)){
			FormDataUtil.setProperty(formData, "voucherno", voucherno);
			FormDataUtil.setProperty(formData, "voucheramt", voucheramt);
		}else{
			FormDataUtil.setProperty(formData, "voucheramt", "0");
		}
		
		formData = ServiceClient.getResponseFormData(formData, "WithDrawListInsert");
		String respcode = String.valueOf(FormDataUtil.getProperty(formData, "respcode"));
		String respdesc = String.valueOf(FormDataUtil.getProperty(formData, "respdesc"));
		if(!respcode.equals(FormData.SUCCESS)){
			json = "{\"code\":\"f\",\"msg\":\""+respdesc+"\"}";
		}else{			
			String transurl = String.valueOf(FormDataUtil.getProperty(formData, "transurl"));
			json = "{\"code\":\"s\",\"msg\":\"成功\",\"url\":\""+transurl+"\"}";
		}
		return json;
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
		double servfee1 = FeeRuleUtil.cashServfee(cashchl, PayConstant.CHAN_XABANK, String.valueOf(balance), user.isVip() ? "1" : "0");
		servfee1 = FeeRuleUtil.calCashServfee(servfee1, coinamt, voucheramt);
		
		if(servfee != servfee1){
			return "{\"respcode\":\"f\",\"respdesc\":\"服务费计算有误\"}";
		}
		
		servfee = servfee1;
		
		FormData formData = FormDataUtil.createInputForm("CashApprInsert");
		FormDataUtil.setProperty(formData, "custid", user.getCustId());
		FormDataUtil.setProperty(formData, "custname", user.getCustName());
		FormDataUtil.setProperty(formData, "custacno", user.getCustAcNo());
		FormDataUtil.setProperty(formData, "chanid", PayConstant.CHAN_XABANK);
		FormDataUtil.setProperty(formData, "memberid", user.getMemberId());
		FormDataUtil.setProperty(formData, "chalchl", cashchl);
		FormDataUtil.setProperty(formData, "cashapprstat", "0");
		FormDataUtil.setProperty(formData, "cashstat", "2");
		FormDataUtil.setProperty(formData, "appramt", balance);
		FormDataUtil.setProperty(formData, "servfee", servfee);
		FormDataUtil.setProperty(formData, "openacctid", cardId);
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
		FormDataUtil.setProperty(formData, "chanid", PayConstant.CHAN_XABANK);
		FormDataUtil.setProperty(formData, "cashapprstat", "2");
		FormDataUtil.setProperty(formData, "cashstat", "2");
		List<FormData> formDatas = ServiceClient.getResponseFormDataList(formData, "CashApprList");
		String json = ServiceClient.parseObjToJson(formDatas, new SimpleDateFormat(DateUtils.SDF_DATE));
		return json;
	}
	
	/**
	 * 通过审批列表发起提现
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/withdraw_appr")
	@ResponseBody
	public String withdrawByAppr(Model model,HttpServletRequest request, SitePreference sitePreference){
		User user = Utils.getCurrentUser();
		String cashId = request.getParameter("cashId");
		String json = "";
		
		if (StringUtils.isBlank(user.getCustAcNo())){
			return json = "{\"code\":\"f\",\"msg\":\"您尚未开通银行存管电子账户，请您立即开通。\"}";
		}
		if (StringUtils.isBlank(cashId)){
			return json = "{\"code\":\"f\",\"msg\":\"银行卡不能为空\"}";
		}
		
		FormData formData = FormDataUtil.createInputForm("CashApprSelect");
		FormDataUtil.setProperty(formData, "cashid", cashId);
		formData = ServiceClient.getResponseFormData(formData, "CashApprSelect");
		String respCode = String.valueOf(FormDataUtil.getProperty(formData, "respcode"));
		String respDesc = String.valueOf(FormDataUtil.getProperty(formData, "respdesc"));
		
		if(!respCode.equals(FormData.SUCCESS)){
			return json = "{\"code\":\"f\",\"msg\":\""+respDesc+"\"}";
		}
		
		
		//amount:提现金额；cardId：银行卡；coinamt：使用金开币;voucherno:加息券编号;servfee:服务费;voucheramt:提现优惠券金额
		double amount = Double.parseDouble(String.valueOf(FormDataUtil.getProperty(formData, "appramt")));
		double servfee = Double.parseDouble(String.valueOf(FormDataUtil.getProperty(formData, "servfee")));
		double coinamt = Double.parseDouble(String.valueOf(FormDataUtil.getProperty(formData, "scoreamt")));
		String cashchl = String.valueOf(FormDataUtil.getProperty(formData, "chalchl"));
		String cardId = String.valueOf(FormDataUtil.getProperty(formData, "openacctid"));
		String vecherid = String.valueOf(FormDataUtil.getProperty(formData, "vecherid"));
		double voucheramt = Double.parseDouble(String.valueOf(FormDataUtil.getProperty(formData, "vecheramt")));
		String isVip = String.valueOf(FormDataUtil.getProperty(formData, "isvip"));
		
		
		//二次计算服务费 与 jsp页面对比
		double servfee1 = FeeRuleUtil.cashServfee(cashchl, PayConstant.CHAN_XABANK, String.valueOf(amount), isVip);
		servfee1 = FeeRuleUtil.calCashServfee(servfee1, coinamt, voucheramt);
		if(servfee != servfee1) {
			return json = "{\"code\":\"f\",\"msg\":\"服务费计算不一致\"}";
		}
		
		
		FormData formDatas = FormDataUtil.createInputForm("WithDrawListInsert");
		FormDataUtil.setProperty(formDatas, "memberid", user.getMemberId());
		FormDataUtil.setProperty(formDatas, "transamt", amount);
		FormDataUtil.setProperty(formDatas, "acctno", cardId);
		FormDataUtil.setProperty(formDatas, "coinamt", coinamt);
		FormDataUtil.setProperty(formDatas, "custfee", servfee);
		FormDataUtil.setProperty(formDatas, "applyid", cashId);
		if(StringUtils.isNotBlank(vecherid)){
			FormDataUtil.setProperty(formData, "voucherno", vecherid);
			FormDataUtil.setProperty(formData, "voucheramt", voucheramt);
		}else{
			FormDataUtil.setProperty(formData, "voucheramt", "0");
		}
		formDatas = ServiceClient.getResponseFormData(formDatas, "WithDrawListInsert");
		String respcode = String.valueOf(FormDataUtil.getProperty(formDatas, "respcode"));
		String respdesc = String.valueOf(FormDataUtil.getProperty(formDatas, "respdesc"));
		if(!respcode.equals(FormData.SUCCESS)){
			json = "{\"code\":\"f\",\"msg\":\""+respdesc+"\"}";
		}else{			
			String transurl = String.valueOf(FormDataUtil.getProperty(formDatas, "transurl"));
			json = "{\"code\":\"s\",\"msg\":\"成功\",\"url\":\""+transurl+"\"}";
		}
		
		return json;
		
	}
	
	/**
	 * 购买VIP
	 * @param coinamt
	 * @param term
	 * @return
	 */
	@RequestMapping(value="buy_vip.html",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String buyVip(String amount,String realamt,String coinamt,String term){
		User user = Utils.getCurrentUser();
		String json = "";
		
		//拿到前台传到的购买期数
		//VIP应付金额
		//金开币金额
		//实际支付金额
		
		if(user.getMemberState().equals("4")||user.getMemberState().equals("5")){
			return json = "{\"code\":\"f\",\"msg\":\"您还未开通银行存管电子账户，请先开通！\"}";
		}
		
		if(user.getMemberState().equals("6")||user.getMemberState().equals("7")){
			return json = "{\"code\":\"f\",\"msg\":\"您还未激活银行存管电子账户，请先激活！\"}";
		}
		
		double amounts = 0d;
		double coinamts = 0d;
		double realamts = 0d;
		
		//购买VIP应付金额校验
		try {
			amounts = Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			return json = "{\"code\":\"f\",\"msg\":\"VIP购买金额不正确\"}";
		}
		if (amounts <= 0) {
			return json = "{\"code\":\"f\",\"msg\":\"VIP购买金额不正确\"}";
		}
		
		//通过所传购买期数进行费用计算并与前台所传数额进行对比校验
		Object[] calFee = FeeRuleUtil.calVipfee(Integer.parseInt(term), null);
		
		if(amounts != ((Double)calFee[0]).doubleValue()) {
			return json = "{\"code\":\"f\",\"msg\":\"VIP支付金额有误\"}";
		}
		
		//购买vip使用金开币数额的校验
		if(StringUtils.isNotBlank(coinamt)){
			try {
				coinamts = Double.parseDouble(coinamt);
			} catch (NumberFormatException e1) {
				return json = "{\"code\":\"f\",\"msg\":\"金开币兑换金额有误\"}";
			}			
		}
		if(coinamts < 0){
			return json = "{\"code\":\"f\",\"msg\":\"金开币兑换金额有误\"}";
		}
		
		//实际支付金额校验
		try {
			realamts = Double.parseDouble(realamt);
		} catch (NumberFormatException e) {
			return json = "{\"code\":\"f\",\"msg\":\"VIP购买金额不正确\"}";
		}
		if (realamts <= 0) {
			return json = "{\"code\":\"f\",\"msg\":\"VIP购买金额不正确\"}";
		}
		
		if((realamts+coinamts)!=amounts){
			return json = "{\"code\":\"f\",\"msg\":\"VIP支付金额有误\"}";
		}
		
		if(realamts < 1d) {
			return json = "{\"code\":\"f\",\"msg\":\"实际支付金额不能小于1元\"}";
	    }
		
		
		FormData formData = FormDataUtil.createInputForm("CtpByVipInsert");
		FormDataUtil.setProperty(formData, "memberid", user.getMemberId());
		FormDataUtil.setProperty(formData, "transamt", realamts);
		if(coinamts > 0){
			FormDataUtil.setProperty(formData, "coinamt", coinamts);			
		}else{
			FormDataUtil.setProperty(formData, "coinamt", 0);
		}
		FormDataUtil.setProperty(formData, "paymonth", term);
		formData = ServiceClient.getResponseFormData(formData, "CtpByVipInsert");
		String respcode = String.valueOf(FormDataUtil.getProperty(formData, "respcode"));
		String respdesc = String.valueOf(FormDataUtil.getProperty(formData, "respdesc"));
		if(!respcode.equals(FormData.SUCCESS)){
			json = "{\"code\":\"f\",\"msg\":\""+respdesc+"\"}";
		}else{			
			String transurl = String.valueOf(FormDataUtil.getProperty(formData, "transurl"));
			json = "{\"code\":\"s\",\"msg\":\"成功\",\"url\":\""+transurl+"\"}";
		}
		return json;
	}
	
	/**
	 * 创建充值订单并获取随机因子
	 * @return
	 */
	@RequestMapping("createOrder")
	@ResponseBody
	public String createOrder(String transamt, String acctno) {
		User user = Utils.getCurrentUser();

		if (StringUtils.isBlank(user.getCustAcNo())) {
			return ServiceClient.parseObjToJson(new MessageWrapper(false, "您尚未开通银行存管电子账户，请您立即开通。"));
		}
		
		if(user.getMemberType().equals("1")){
			return ServiceClient.parseObjToJson(new MessageWrapper(false, "企业户充值请通过线下或项目经理处"));
		}

		// 充值金额校验
		double dTransamt = 0;
		try {
			dTransamt = Double.parseDouble(transamt);
		} catch (NumberFormatException e) {
			return ServiceClient.parseObjToJson(new MessageWrapper(false, "充值金额不正确"));
		}
		if (dTransamt <= 0) {
			return ServiceClient.parseObjToJson(new MessageWrapper(false, "充值金额不正确"));
		}

		if (StringUtils.isBlank(acctno)) {
			logger.debug("充值 acctno: " + acctno);
			// TODO: 银行卡是不是应该存在session中？
			return ServiceClient.parseObjToJson(new MessageWrapper(false, "请选择正确的充值银行卡"));
		}

		FormData inData = FormDataUtil.createInputForm("DepositListInsertCombo");
		FormDataUtil.setProperty(inData, "memberid", user.getMemberId());
		FormDataUtil.setProperty(inData, "transamt", dTransamt);
		FormDataUtil.setProperty(inData, "acctno", acctno);
		FormData outData = ServiceClient.getResponseFormData(inData, "DepositListInsertCombo");
		if (!FormDataUtil.isSucceed(outData)) {
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData));
		} else {
			Session session = SecurityUtils.getSubject().getSession();
			session.setAttribute(DEPOSIT_LIST_ID, (String) FormDataUtil.getProperty(outData, "listid"));
			Map<String, String> map = new HashMap<String, String>();
			map.put("kx", (String) FormDataUtil.getProperty(outData, "kx"));
			map.put("ky", (String) FormDataUtil.getProperty(outData, "ky"));
			map.put("rcode", (String) FormDataUtil.getProperty(outData, "rcode"));
			MessageWrapper wrapper = new MessageWrapper(true);
			wrapper.setBody(map);
			return ServiceClient.parseObjToJson(wrapper);
		}
	}
	
	/**
	 * 充值的check方法
	 * @param transpwd
	 * @return
	 */
	@RequestMapping("deposit")
	@ResponseBody
	public String doDeposit(String transpwd) {
		
		Session session = SecurityUtils.getSubject().getSession();
		String listId = (String) session.getAttribute(DEPOSIT_LIST_ID);
		FormData inData = FormDataUtil.createInputForm("DepositListCheck");
		FormDataUtil.setProperty(inData, "listid", listId);
		try {
			transpwd = URLDecoder.decode(transpwd, "UTF-8");
			transpwd = Base64.encodeBase64String(transpwd.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("个人开户密码URLDecode或Base64编码时异常", e);
		}
		FormDataUtil.setProperty(inData, "transpwd", transpwd);
		inData = ServiceClient.getResponseFormData(inData, "DepositListCheck");
		if (!FormDataUtil.isSucceed(inData)) {
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(inData));
		} else {
			return ServiceClient.parseObjToJson(new MessageWrapper(true, "充值成功"));
		}
	}
	
	/**
	 * 更换银行卡
	 */
	@RequestMapping("changeCard")
	@ResponseBody
	public String changeCard(HttpServletRequest request) throws IllegalStateException {
		String changeCardPath = "ChangeCardApply";
		
		User user = Utils.getCurrentUser();
		
		String newBankCard = request.getParameter("ACCT_NO");
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		String reason = request.getParameter("reason");
		String memberId = Utils.getCurrentUser().getMemberId();
		String basePath = PropertiesUtil.getFileUri() + File.separator + changeCardPath + File.separator + id + File.separator;
		FileUtil.createDir(basePath);
		String zipPath = basePath + user.getCustName() + user.getMobileNumber() + ".zip";
		
		FormData changeCardData = FormDataUtil.createInputForm("ChangeCardInsert");
		FormDataUtil.setProperty(changeCardData, "id", id);
		FormDataUtil.setProperty(changeCardData, "memberid", memberId);
		FormDataUtil.setProperty(changeCardData, "newcardnum", newBankCard);
		FormDataUtil.setProperty(changeCardData, "reason", Base64Util.encodeBase64(reason));
		FormDataUtil.setProperty(changeCardData, "zipurl", zipPath);
		
		List<File> fileList = new ArrayList<File>();
		File zip = new File(zipPath);
		
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
			Iterator<String> fileNames = multipartRequest.getFileNames();
			
			while(fileNames.hasNext()) {
				MultipartFile file = multipartRequest.getFile(fileNames.next());
				
				if(StringUtils.isEmpty(file.getOriginalFilename()))
					continue;

				if(!isPicture(file.getOriginalFilename())) 
					return ServiceClient.parseObjToJson(new MessageWrapper(false, "wrongFileFormat"));

				if(fileIsOverSize(file)) 
					return ServiceClient.parseObjToJson(new MessageWrapper(false, "fileOverSize-" + file.getName()));
				
				String fullPath = basePath + generateFileName(file);
				FormDataUtil.setProperty(changeCardData, file.getName(), fullPath);
				File originFile = new File(fullPath);
				try {
					file.transferTo(originFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				fileList.add(originFile);
			}
			FormData outData = ServiceClient.getResponseFormData(changeCardData, "ChangeCardInsert");
			
			try {
				FileUtil.ZipFiles(zip, "zip", fileList);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (!FormDataUtil.isSucceed(outData))
				return ServiceClient.parseObjToJson(new MessageWrapper(false, (String) FormDataUtil.getProperty(outData, "respcode")));
			else
				return ServiceClient.parseObjToJson(new MessageWrapper(true, ""));
		} else
			return ServiceClient.parseObjToJson(new MessageWrapper(false, ""));
	}
	
	private static boolean isPicture(String fileName) {
		fileName = fileName.toLowerCase();
		return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif") || fileName.endsWith(".bmp");
	}

	private static String generateFileName(MultipartFile file) {
		String originName = file.getOriginalFilename();
		return file.getName() + originName.substring(originName.lastIndexOf("."));
	}
	
	private static boolean fileIsOverSize(MultipartFile file) {
		return file.getSize() > 1024 * 1024 * 2;
	}
	
}
