package com.jkgroup.kingkaid.service.pay;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.ui.Model;

import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.CommonServiceClient;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.MathUtil;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.PayConstant;
import com.jkgroup.kingkaid.utils.pay.PayUtil;
import com.jkgroup.kingkaid.utils.pay.PayUtil.PayChannel;
import com.jkgroup.kingkaid.utils.pay.PayUtil.PayOption;
import com.jkgroup.kingkaid.utils.pay.param.ChinaPnrPayParam;

/**
 * 支付相关接口操作
 * @author pan
 *
 */
public class PayServiceClient {

	/**
	 * 充值交易 - 新增单据
	 * @param map
	 * @return
	 */
	public static FormData depositInsert(Map<String,String> map){
		FormData deposit = FormDataUtil.createInputForm("DepositInsert");
		FormDataUtil.setProperty(deposit, "custid", map.get("custid"));
		FormDataUtil.setProperty(deposit, "listtype", PayConstant.LIST_TYPE_DEPOSIT);
		FormDataUtil.setProperty(deposit, "chanid", map.get("chanid"));
		FormDataUtil.setProperty(deposit, "busitype", PayConstant.BUSI_TYPE_DEPOSIT);
		FormDataUtil.setProperty(deposit, "custacno", map.get("usrCustId"));			
		FormDataUtil.setProperty(deposit, "custloginname", map.get("membername"));
		FormDataUtil.setProperty(deposit, "rechargeamt", map.get("balance"));
		FormDataUtil.setProperty(deposit, "acbankid", PayConstant.ACBANKID);
		if(StringUtils.isNotBlank(map.get("payfee"))){
			FormDataUtil.setProperty(deposit, "payfee", map.get("payfee"));
		}
		return ServiceClient.getResponseFormData(deposit, "DepositInsert");
	}
	
	/**
	 * 充值交易 - 记账
	 * @param map
	 * @return
	 */
	public static FormData depositCheck(Map<String,String> map){
		FormData deposit = FormDataUtil.createInputForm("DepositCheck");
		FormDataUtil.setProperty(deposit, "listid", map.get("listid"));
		FormDataUtil.setProperty(deposit, "listtype", PayConstant.LIST_TYPE_DEPOSIT);
		FormDataUtil.setProperty(deposit, "chanid", map.get("chanid"));
		FormDataUtil.setProperty(deposit, "busitype", PayConstant.BUSI_TYPE_DEPOSIT);
		FormDataUtil.setProperty(deposit, "otherlistno", map.get("otherlistno"));
		FormDataUtil.setProperty(deposit, "payfee", map.get("FeeAmt"));
		if(StringUtils.isNotBlank(map.get("tradeno"))){
			FormDataUtil.setProperty(deposit, "tradeno", map.get("tradeno"));			
		}
		if(StringUtils.isNotBlank(map.get("tradestat"))){
			FormDataUtil.setProperty(deposit, "tradestat", map.get("tradestat"));			
		}
		if(StringUtils.isNotBlank(map.get("respcode"))){
			FormDataUtil.setProperty(deposit, "respcode", map.get("respcode"));
		}
		if(StringUtils.isNotBlank(map.get("respdesc"))){
			FormDataUtil.setProperty(deposit, "respdesc", map.get("respdesc"));
		}
		if(StringUtils.isNotBlank(map.get("gatebusiid"))){
			FormDataUtil.setProperty(deposit, "gatebusiid", map.get("gatebusiid"));
		}
		if(StringUtils.isNotBlank(map.get("cardid"))){
			FormDataUtil.setProperty(deposit, "cardid", map.get("cardid"));
		}
		deposit = ServiceClient.getResponseFormData(deposit, "DepositCheck");
		return deposit;
	}
	
	public static FormData insertTender(Map<String,String> map){
		FormData formData = FormDataUtil.createInputForm("TenderInsert");
		FormDataUtil.setProperty(formData, "listtype", PayConstant.LIST_TYPE_TENDER);
		FormDataUtil.setProperty(formData, "busitype", PayConstant.BUSI_TYPE_TENDER);
		FormDataUtil.setProperty(formData, "chanid", PayConstant.CHAN_CHINAPNR);
		FormDataUtil.setProperty(formData, "custid", map.get("custid"));
		FormDataUtil.setProperty(formData, "custacno", map.get("custacno"));
		FormDataUtil.setProperty(formData, "custname", map.get("custname"));
		FormDataUtil.setProperty(formData, "loanid", map.get("loanid"));
		FormDataUtil.setProperty(formData, "tenderamt", map.get("tenderamt"));	
		if(StringUtils.isNotBlank(map.get("isUseRebate"))){
			FormDataUtil.setProperty(formData, "isUseRebate", map.get("isUseRebate"));			
		}
		if(StringUtils.isNotBlank(map.get("rebateamt"))){
			FormDataUtil.setProperty(formData, "rebateamt", map.get("rebateamt"));
		}
		FormDataUtil.setProperty(formData, "freezeOrdId", map.get("freezeOrdId"));
		return formData;
	}
	
	/**
	 * 个人用户开通第三方支付账户
	 * @param memberId
	 * @param memberName
	 * @return
	 */
	public static String registerUser(String memberId,String memberName,Model model){
		ChinaPnrPayParam pay = CommonServiceClient.getPayParams();
		Map<String,String> map = new HashMap<String,String>();
		map.put("Version", "10");
		map.put("CmdId", "UserRegister");
		map.put("MerCustId", pay.getMer_cust_id());
		map.put("BgRetUrl", pay.getBg_ret_url());
		map.put("RetUrl", pay.getRet_url());
		map.put("UsrId", memberName);
		
		StringBuilder chkSb = new StringBuilder();
		chkSb.append(map.get("Version")).append(map.get("CmdId")).append(map.get("MerCustId"))
			.append(map.get("BgRetUrl")).append(map.get("RetUrl")).append(map.get("UsrId"));
		
		fillUserRegisterParams(memberId, map, chkSb);
		map.put("MerPriv", memberId);
		chkSb.append(memberId);
		
		String chk = CommonServiceClient.chkValue(chkSb.toString());
		map.put("ChkValue", chk);
		String html = PayUtil.buildRequest(map,pay.getPay_uri());
		model.addAttribute("html", html);
		return PayConstant.FORWARD;
	}
	
	/**
	 * 从后端服务获取已存信息，填充个人用户注册报文
	 * @param memberId
	 * @param map
	 * @param chkSb
	 */
	private static void fillUserRegisterParams(String memberId, Map<String, String> map, StringBuilder chkSb) {
		// 从金开贷后台获取已有的客户信息
		FormData formData = FormDataUtil.createInputForm("AuthCustInfo");
		FormDataUtil.setProperty(formData, "memberid", memberId);
		FormData outData = ServiceClient.getResponseFormData(formData,"AuthCustInfo");
		// 后端接口调用出错，不填值
		if(!FormDataUtil.isSucceed(outData)) {
			return;
		}
		
		// 汇付天下汉字处理有问题，不再带过去，让用户在汇付天下页面填写
//		String custName = (String) FormDataUtil.getProperty(outData, "custname");
//		if (StringUtils.isNotEmpty(custName)) {
//			map.put("UsrName", custName);
//			chkSb.append(custName);
//		}
		
		String paperId = (String) FormDataUtil.getProperty(outData, "paperid");
		if(StringUtils.isNotEmpty(paperId)) {
			map.put("IdType", PayConstant.ID_TYPE);
			map.put("IdNo", paperId);
			chkSb.append(PayConstant.ID_TYPE);
			chkSb.append(paperId);
		}
		String mobilenumber = (String) FormDataUtil.getProperty(outData, "mobilenumber");
		if(StringUtils.isNotEmpty(mobilenumber)) {
			map.put("UsrMp", mobilenumber);
			chkSb.append(mobilenumber);
		}
		String email = (String) FormDataUtil.getProperty(outData, "email");
		if(StringUtils.isNotEmpty(email)) {
			map.put("UsrEmail", email);
			chkSb.append(email);
		}
	}
	
	/**
	 * 企业用户开通第三方支付账户
	 * @param memberId
	 * @param memberName
	 * @return
	 */
	public static String registerCorp(String memberId,String memberName,Model model,boolean isGuarantee){
		ChinaPnrPayParam pay = CommonServiceClient.getPayParams();
		Map<String,String> map = new HashMap<String,String>();
		map.put("Version", "10");
		map.put("CmdId", "CorpRegister");
		map.put("MerCustId", pay.getMer_cust_id());
		map.put("UsrId", memberName);
		
		StringBuilder chkSb = new StringBuilder();
		chkSb.append(map.get("Version")).append(map.get("CmdId")).append(map.get("MerCustId"))
			.append(map.get("UsrId"));
		
		fillCorpRegisterParams(memberId, map, chkSb);
		
		map.put("MerPriv", memberId);
		chkSb.append(memberId);
		// 担保类型
		String guarType = isGuarantee ? "Y" : "N";
		map.put("GuarType", guarType);
		chkSb.append(guarType);
		map.put("BgRetUrl", pay.getBg_ret_url());
		chkSb.append(map.get("BgRetUrl"));
		
		String chk = CommonServiceClient.chkValue(chkSb.toString());
		map.put("ChkValue", chk);
		String html = PayUtil.buildRequest(map,pay.getPay_uri());
		model.addAttribute("html", html);
		return PayConstant.FORWARD;
	}
	
	/**
	 * 从后端服务获取已存信息，填充企业用户注册报文
	 * @param memberId
	 * @param map
	 * @param chkSb
	 */
	private static void fillCorpRegisterParams(String memberId, Map<String, String> map, StringBuilder chkSb) {
		// 从金开贷后台获取已有的客户信息
		FormData formData = FormDataUtil.createInputForm("SelectCorpCustInfo");
		FormDataUtil.setProperty(formData, "memberid", memberId);
		FormData outData = ServiceClient.getResponseFormData(formData,"SelectCorpCustInfo");
		// 后端接口调用出错，不填值
		if(!FormDataUtil.isSucceed(outData)) {
			return;
		}
		// 企业名称
		// 汇付天下汉字处理有问题，不再带过去，让用户在汇付天下页面填写
//		String custName = (String) FormDataUtil.getProperty(outData, "custname");
//		if (StringUtils.isNotEmpty(custName)) {
//			map.put("UsrName", custName);
//			chkSb.append(custName);
//		}
		
		// 组织机构代码
		String liceId = (String) FormDataUtil.getProperty(outData, "liceid");
		if (StringUtils.isNotEmpty(liceId)) {
			// 后台组织机构代码可能会{8}-{1}格式
			liceId = liceId.replace("-", "");
			map.put("InstuCode", liceId);
			chkSb.append(liceId);
		}
		// 营业执照编号(必须，这个是必须传至汇付天下的项目)
		String orgaId = (String) FormDataUtil.getProperty(outData, "orgaid");
		if (StringUtils.isNotEmpty(orgaId)) {
			map.put("BusiCode", orgaId);
			chkSb.append(orgaId);
		}
		// 税务登记证号，因为不清楚汇付是国税还是地税，所以不带过去，由客户在汇付页面填写
//		String lreveId = (String) FormDataUtil.getProperty(outData, "lreveid");
//		if (StringUtils.isNotEmpty(lreveId)) {
//			map.put("TaxCode", lreveId);
//			chkSb.append(lreveId);
//		}
	}
	
	/**
	 * 主动投标
	 * @param loanid
	 * @param custId
	 * @param custAcNo
	 * @param custName
	 * @param amount
	 * @param isVocher
	 * @param vocherAmt
	 * @param coinamt
	 * @return
	 */
	public static String tender(String loanid,String custId,String custAcNo,String custName,Double amount,String inteaddno,Double inteaddrate,Double coinamt,String bidSource,Model model, SitePreference sitePreference){
		/*
		 * 新增投标单据
		 */
		FormData formData = FormDataUtil.createInputForm("TenderInsert");
		FormDataUtil.setProperty(formData, "listtype", PayConstant.LIST_TYPE_TENDER);
		FormDataUtil.setProperty(formData, "busitype", PayConstant.BUSI_TYPE_TENDER);
		FormDataUtil.setProperty(formData, "chanid", PayConstant.CHAN_CHINAPNR);
		FormDataUtil.setProperty(formData, "custid", custId);
		FormDataUtil.setProperty(formData, "custacno", custAcNo);
		FormDataUtil.setProperty(formData, "custname", custName);
		FormDataUtil.setProperty(formData, "loanid", loanid);
		FormDataUtil.setProperty(formData, "tenderamt", MathUtil.numFmt(amount));
		FormDataUtil.setProperty(formData, "bidsource", bidSource);
		if(StringUtils.isNotBlank(inteaddno)){
			FormDataUtil.setProperty(formData, "inteaddno", inteaddno);
			FormDataUtil.setProperty(formData, "inteaddrate", inteaddrate);
		}
		FormDataUtil.setProperty(formData, "coinamt", coinamt > 0 ? coinamt : 0);
		
		formData = ServiceClient.getResponseFormData(formData, "TenderInsert");
		String respCode = String.valueOf(FormDataUtil.getProperty(formData, "respcode"));
		String respDesc = String.valueOf(FormDataUtil.getProperty(formData, "respdesc"));
		
		if(respCode.equals(FormData.SUCCESS)){
			
			ChinaPnrPayParam pay = CommonServiceClient.getPayParams();
			String OrdId = String.valueOf(FormDataUtil.getProperty(formData, "otherlistno"));
			String listId = String.valueOf(FormDataUtil.getProperty(formData, "listid"));
			String OrdDate = DateUtils.getYMDDate((Date)FormDataUtil.getProperty(formData, "tenderdate"));
			String borrowCustId = String.valueOf(FormDataUtil.getProperty(formData, "borrowcustacno"));
			String freezeOrdId = String.valueOf(FormDataUtil.getProperty(formData, "freezeordid"));
			
			Map<String,String> map = new LinkedHashMap<String, String>();
			map.put("Version", "20");
			map.put("CmdId", "InitiativeTender");
			map.put("MerCustId", pay.getMer_cust_id());
			map.put("OrdId", OrdId);
			map.put("OrdDate", OrdDate);
			map.put("TransAmt", MathUtil.numFmt(amount));
			map.put("UsrCustId", custAcNo);
			map.put("MaxTenderRate", "0.10");
			map.put("BorrowerDetails", "[{\"BorrowerCustId\":\""+borrowCustId+"\",\"BorrowerAmt\":\""+MathUtil.numFmt(amount)+"\",\"BorrowerRate\":\"1.00\"}]");
			map.put("IsFreeze", "Y");
			map.put("FreezeOrdId", freezeOrdId);
			map.put("RetUrl", pay.getRet_url());
			map.put("BgRetUrl", pay.getBg_ret_url());
			map.put("MerPriv", listId);
			if(coinamt != 0){
				map.put("ReqExt", "{\"Vocher\":{\"AcctId\":\""+PayConstant.MDT000001+"\",\"VocherAmt\":\""+MathUtil.numFmt(coinamt)+"\"}}");
			}
			String ChkValue = PayUtil.chkValue(map);
			ChkValue = CommonServiceClient.chkValue(ChkValue);
			map.put("ChkValue", ChkValue);
			String html = PayUtil.buildRequest(map, pay.getPay_uri());
			model.addAttribute("html", html);
			return PayConstant.FORWARD;
		}else{
			return PayUtil.payError(respDesc, PayChannel.CHINAPNR, PayOption.INITIATIVETENDER, sitePreference, model);
		}
	}
	
	/**
	 * 用户绑卡
	 * @param custAcNo
	 * @param custId
	 * @return
	 */
	public static String bindcard(String custAcNo,String custId,Model model){
		ChinaPnrPayParam pay = CommonServiceClient.getPayParams();
		Map<String,String> map = new LinkedHashMap<String, String>();
		map.put("Version", "10");
		map.put("CmdId", "UserBindCard");
		map.put("MerCustId", pay.getMer_cust_id());
		map.put("UsrCustId", custAcNo);
		map.put("BgRetUrl", pay.getBg_ret_url());
		map.put("MerPriv", custId);
		
		String ChkValue = PayUtil.chkValue(map);
		ChkValue = CommonServiceClient.chkValue(ChkValue);
		map.put("ChkValue", ChkValue);
		String html = PayUtil.buildRequest(map, pay.getPay_uri());
		model.addAttribute("html", html);
		return PayConstant.FORWARD;
	}
	
	/**
	 * 处理提现
	 * @param custId
	 * @param custAcNo
	 * @param memberName
	 * @param amount
	 * @param servfee
	 * @param origfee
	 * @param cashchl
	 * @param isVIP
	 * @param coinamt
	 * @param cardId
	 * @param voucherno
	 * @param voucheramt
	 * @return
	 */
	public static String cash(String cashId,String custId,String custAcNo,String memberName,Double amount,Double servfee,Double origfee,String cashchl,String isVIP,Double coinamt,String cardId,String voucherno,double voucheramt,Model model, SitePreference sitePreference){
		ChinaPnrPayParam pay = CommonServiceClient.getPayParams();
		//新增单据
		FormData cashFormData = FormDataUtil.createInputForm("CashInsert");
		if(StringUtils.isNotBlank(cashId)){
			FormDataUtil.setProperty(cashFormData,"merpriv", cashId);
		}
		FormDataUtil.setProperty(cashFormData,"listtype", PayConstant.LIST_TYPE_WITHDRAW);
		FormDataUtil.setProperty(cashFormData,"chanid", PayConstant.CHAN_CHINAPNR);
		FormDataUtil.setProperty(cashFormData,"busitype", PayConstant.BUSI_TYPE_WITHDRAW);
		FormDataUtil.setProperty(cashFormData,"custid", custId);
		FormDataUtil.setProperty(cashFormData,"custloginname", memberName);
		FormDataUtil.setProperty(cashFormData,"custacno", custAcNo);
		FormDataUtil.setProperty(cashFormData,"acbankid", PayConstant.ACBANKID);
		FormDataUtil.setProperty(cashFormData,"withdrawamt", String.valueOf(amount));
		if(servfee > 0){
			FormDataUtil.setProperty(cashFormData,"servfee", String.valueOf(servfee));			
		}else{
			FormDataUtil.setProperty(cashFormData,"servfee", "0");	
		}
		if(origfee > 0){
			FormDataUtil.setProperty(cashFormData,"origfee", String.valueOf(origfee));			
		}else{
			FormDataUtil.setProperty(cashFormData,"origfee", "0");	
		}
		FormDataUtil.setProperty(cashFormData,"isvip", String.valueOf(isVIP));
		if(coinamt > 0){
			FormDataUtil.setProperty(cashFormData,"isusescore", "1");
			FormDataUtil.setProperty(cashFormData,"integral", String.valueOf(coinamt));
			FormDataUtil.setProperty(cashFormData,"scoreamt", String.valueOf(coinamt));
		}else{
			FormDataUtil.setProperty(cashFormData,"isusescore", "0");
			FormDataUtil.setProperty(cashFormData,"integral", String.valueOf(0));
			FormDataUtil.setProperty(cashFormData,"scoreamt", String.valueOf(0));
		}
		if(StringUtils.isNotBlank(cardId)){
			FormDataUtil.setProperty(cashFormData,"appopenacctid", String.valueOf(cardId));
		}
		//是否使用优惠卷
		if(StringUtils.isNotBlank(voucherno)){
			FormDataUtil.setProperty(cashFormData,"isvoucher", "1");
			FormDataUtil.setProperty(cashFormData,"voucherno", voucherno);
			FormDataUtil.setProperty(cashFormData,"voucheramt", voucheramt);
		}else{
			FormDataUtil.setProperty(cashFormData,"isvoucher", "0");
		}
		
		FormDataUtil.setProperty(cashFormData, "cashchanid", cashchl);
		cashFormData = ServiceClient.getResponseFormData(cashFormData, "CashInsert");
		String respCode = String.valueOf(FormDataUtil.getProperty(cashFormData, "respcode"));
		String respDesc = String.valueOf(FormDataUtil.getProperty(cashFormData, "respdesc"));
		if(respCode.equals(FormData.SUCCESS)){
			
			Map<String,String> map = new LinkedHashMap<String, String>();
			map.put("Version", "20");
			map.put("CmdId", "Cash");
			map.put("MerCustId", pay.getMer_cust_id());
			map.put("OrdId", String.valueOf(FormDataUtil.getProperty(cashFormData, "otherlistno")));
			map.put("UsrCustId", custAcNo);
			map.put("TransAmt", MathUtil.numFmt(amount));
			if(servfee > 0){
				map.put("ServFee", MathUtil.numFmt(servfee));
				map.put("ServFeeAcctId", PayConstant.MDT000001);
			}
			//是否包含银行账号
			if(StringUtils.isNotBlank(cardId)){
				map.put("OpenAcctId", cardId);
			}
			map.put("RetUrl", pay.getRet_url());
			map.put("BgRetUrl", pay.getBg_ret_url());
			map.put("MerPriv", String.valueOf(FormDataUtil.getProperty(cashFormData, "listid")));
			if(cashchl.equals(PayConstant.CHANCHL_GENERAL)){
				map.put("ReqExt", "[{\"FeeObjFlag\":\"M\",\"FeeAcctId\":\""+PayConstant.MDT000001+"\",\"CashChl\":\"GENERAL\"}]");			
			}else{
				map.put("ReqExt", "[{\"FeeObjFlag\":\"M\",\"FeeAcctId\":\""+PayConstant.MDT000001+"\",\"CashChl\":\"IMMEDIATE\"}]");
			}
			String ChkValue = PayUtil.chkValue(map);
			ChkValue = CommonServiceClient.chkValue(ChkValue);
			map.put("ChkValue", ChkValue);
			String html = PayUtil.buildRequest(map, pay.getPay_uri());
			model.addAttribute("html", html);
			return PayConstant.FORWARD;
		}else{
			return PayUtil.payError(respDesc, PayChannel.CHINAPNR, PayOption.CASH, sitePreference, model);
		}
	}
	
	/**
	 * 处理充值
	 * @param amount
	 * @param custAcNo
	 * @param custId
	 * @param memberName
	 * @param quick
	 * @param model
	 * @return
	 */
	public static String deposit(double amount,String custAcNo,String custId,String memberName,String quick,String bankId,String cardId,String certId,Model model, SitePreference sitePreference){
		if(StringUtils.isBlank(quick)){
			quick = "B2C";
		}
		/*
		 * 新增充值单据
		 */
		Map<String,String> param = new HashMap<String, String>();
		param.put("balance", MathUtil.numFmt(amount));
		param.put("usrCustId", custAcNo);
		param.put("chanid", PayConstant.CHAN_CHINAPNR);
		param.put("custid", custId);
		param.put("membername", memberName);
		FormData deposit = PayServiceClient.depositInsert(param);
		
		if(FormDataUtil.isSucceed(deposit)){
			ChinaPnrPayParam pay = CommonServiceClient.getPayParams();
			
			Date rechargedate = (Date)FormDataUtil.getProperty(deposit, "rechargedate");
			String ordDate = DateUtils.getYMDDate(rechargedate);
			String listid = String.valueOf(FormDataUtil.getProperty(deposit, "listid")); 
			
			Map<String,String> map = new LinkedHashMap<String, String>();
			map.put("Version", "10");
			map.put("CmdId", "NetSave");
			map.put("MerCustId", pay.getMer_cust_id());
			map.put("UsrCustId", String.valueOf(FormDataUtil.getProperty(deposit, "custacno")));
			map.put("OrdId", String.valueOf(FormDataUtil.getProperty(deposit, "otherlistno")));
			map.put("OrdDate", ordDate);
			map.put("GateBusiId", quick);
			if(StringUtils.isNotBlank(bankId)){
				map.put("OpenBankId", bankId);
			}
			map.put("DcFlag", "D");
			map.put("TransAmt", MathUtil.numFmt(amount));
			map.put("RetUrl", pay.getRet_url());
			map.put("BgRetUrl", pay.getBg_ret_url());
//			if(quick.equals("QP")){
//				map.put("OpenAcctId", cardId);
//				map.put("CertId", certId);
//			}
			map.put("MerPriv", listid);
			String ChkValue = PayUtil.chkValue(map);
			ChkValue = CommonServiceClient.chkValue(ChkValue);
			map.put("ChkValue", ChkValue);
			String html = PayUtil.buildRequest(map, pay.getPay_uri());
			model.addAttribute("html", html);
			return PayConstant.FORWARD;
		}else{
			return PayUtil.payError(FormDataUtil.getErrorMessage(deposit), PayChannel.CHINAPNR, PayOption.NETSAVE, sitePreference, model);
		}	
	}
	
	/**
	 * 汇付天下登录
	 * @param model
	 * @param user
	 * @return
	 */
	public static String login(Model model, User user) {
		ChinaPnrPayParam pay = CommonServiceClient.getPayParams();
		Map<String,String> map = new LinkedHashMap<String, String>();
		map.put("Version", "10");
		map.put("CmdId", "UserLogin");
		map.put("MerCustId", pay.getMer_cust_id());
		map.put("UsrCustId", user.getCustAcNo());
		String html = PayUtil.buildRequest(map, pay.getPay_uri());
		model.addAttribute("html", html);
		return PayConstant.FORWARD;
	}
	
}