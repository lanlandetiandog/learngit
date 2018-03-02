package com.jkgroup.kingkaid.web.pay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.common.PayValidateException;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.FeeRuleUtil;
import com.jkgroup.kingkaid.utils.pay.PayConstant;

@Controller
@RequestMapping(value="/app/resource/xac")
public class AppPayXacController {
	
	private static final Logger log = LoggerFactory.getLogger(AppPayXacController.class);
	
	
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
	@RequestMapping(value="tender.html",produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String tender(HttpServletRequest request, Model model, SitePreference sitePreference) {
		//会员编号
		String memberId = request.getParameter("memberid");
		//交易金额
		String transamt = request.getParameter("transamt");
		//加息卷编号
		String inteaddno = request.getParameter("inteaddno");
		//加息卷额度
		String inteaddrate = request.getParameter("inteaddrate");
		//使用金开币金额
		String coinamt = request.getParameter("coinamt");
		//借款编号
		String loanid = request.getParameter("loanid");
		//请求令牌
		String requestToken = request.getParameter("requesttoken");
		//投标金额
		String bidamt = request.getParameter("tenderamt");
		// 现金券编号
		String moneycoupno = request.getParameter("moneycoupno");
		// 现金券金额
		String moneycoupamt = request.getParameter("moneycoupamt");
		
		log.debug("会员编号----memberId:{}", memberId);
		log.debug("投标金额----tenderamt:{}", bidamt);
		log.debug("加息卷编号----inteaddno:{}", inteaddno);
		log.debug("使用金开币金额----coinamt:{}", coinamt);
		log.debug("借款编号----loanid:{}", loanid);
		log.debug("请求令牌----requestToken:{}", requestToken);
		
		double bidamts = 0d;
		double transamts = 0d;
		double coinamts = 0d;
		double inteaddrates = 0d;
		FormData outData = null;
		String json = "{}";
		
		if (StringUtils.isBlank(requestToken)){
			return json = "{\"code\":\"f\",\"msg\":\"请求令牌不能为空\"}";
		}
		
		if (StringUtils.isBlank(memberId)){
			return json = "{\"code\":\"f\",\"msg\":\"会员编号不能为空\"}";
		}
		
		if (StringUtils.isBlank(loanid)){
			return json = "{\"code\":\"f\",\"msg\":\"借款编号不能为空\"}";
		}
		
		
		//提现优惠券校验
		if(StringUtils.isNotBlank(inteaddno)){
			try {
				inteaddrates = Double.parseDouble(inteaddrate);
			} catch (NumberFormatException e) {
				return json = "{\"code\":\"f\",\"msg\":\"加息利率错误\"}";
			}
		}
		
		//投标金额校验
		try {
			bidamts = Double.parseDouble(bidamt);
		} catch (NumberFormatException e) {
			return json = "{\"code\":\"f\",\"msg\":\"投标金额错误\"}";
		}
		
		//交易金额校验
		try {
			transamts = Double.parseDouble(transamt);
		} catch (NumberFormatException e) {
			return json = "{\"code\":\"f\",\"msg\":\"交易金额错误\"}";
		}
		
		//金开币金额校验
		if(StringUtils.isBlank(coinamt)){
			coinamt = "0";
		}
		
		try {
			coinamts = Double.parseDouble(coinamt);
		} catch (NumberFormatException e) {
			return json = "{\"code\":\"f\",\"msg\":\"金开币金额错误\"}";
		}
		
		outData = checkMember(memberId, requestToken);
		
		String custId = String.valueOf(FormDataUtil.getProperty(outData, "custid"));
		
		String bidSource = request.getParameter("bidsource");
        
		FormData formData = FormDataUtil.createInputForm("CtpTenderInsert");
		FormDataUtil.setProperty(formData, "custid", custId);
		FormDataUtil.setProperty(formData, "loanid", loanid);
		FormDataUtil.setProperty(formData, "tenderamt", bidamts);
		FormDataUtil.setProperty(formData, "coinamt", coinamts);
		FormDataUtil.setProperty(formData, "inteaddno", inteaddno);
		FormDataUtil.setProperty(formData, "inteaddrate", inteaddrates);
		FormDataUtil.setProperty(formData, "bidsource", bidSource);
		FormDataUtil.setProperty(formData, "transamt", transamts);
		
		if(StringUtils.isNotBlank(moneycoupno)) {
			FormDataUtil.setProperty(formData, "moneycoupno", moneycoupno);
			FormDataUtil.setProperty(formData, "moneycoupamt", moneycoupamt);			
		}
		
		formData = ServiceClient.getResponseFormData(formData, "CtpTenderInsert");
		String respCode = String.valueOf(FormDataUtil.getProperty(formData, "respcode"));
		String desc = String.valueOf(FormDataUtil.getProperty(formData, "respdesc"));
		if(!respCode.equals(FormData.SUCCESS)){
			json = "{\"code\":\"f\",\"msg\":\""+desc+"\"}";
		}else{			
			String transurl = String.valueOf(FormDataUtil.getProperty(formData, "transurl"));
			json = "{\"code\":\"s\",\"msg\":\"成功\",\"url\":\""+transurl+"\"}";
		}
		return json;
	}
	
	/**
	 * 绑定银行卡
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="bind_card.html",produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String bindCard(HttpServletRequest request, Model model, SitePreference sitePreference){
		//会员编号
		String memberId = request.getParameter("memberid");
		//请求令牌
		String requestToken = request.getParameter("requesttoken");
		//银行卡号
		String acctno = request.getParameter("acctno");
		//交易密码
		String transpwd = request.getParameter("transpwd");
		//客户端
		String clienttype = request.getParameter("clienttype");
		
		FormData outData = null;
		String json = "{}";
		if (StringUtils.isBlank(requestToken)){
			return json = "{\"code\":\"f\",\"msg\":\"请求令牌不能为空\"}";
		}
		
		if (StringUtils.isBlank(memberId)){
			return json = "{\"code\":\"f\",\"msg\":\"会员编号不能为空\"}";
		}

		outData = checkMember(memberId, requestToken);

		String custId = String.valueOf(FormDataUtil.getProperty(outData, "custid"));
		
		FormData data = FormDataUtil.createInputForm("CtpBankCardInsert");
		FormDataUtil.setProperty(data, "memberid", custId);
		FormDataUtil.setProperty(data, "acctno", acctno);
		
		FormDataUtil.setProperty(data, "transpwd", transpwd);
		FormDataUtil.setProperty(data, "clienttype", clienttype);
		
		data = ServiceClient.getResponseFormData(data, "CtpBankCardInsert");
		String respCode = String.valueOf(FormDataUtil.getProperty(data, "respcode"));
		String desc = String.valueOf(FormDataUtil.getProperty(data, "respdesc"));
		if(!respCode.equals(FormData.SUCCESS)){
			json = "{\"code\":\"f\",\"msg\":\""+desc+"\"}";
		}else{
			String regamt = String.valueOf(FormDataUtil.getProperty(data, "regamt"));
			String isactive = String.valueOf(FormDataUtil.getProperty(data, "isactive"));
			json = "{\"code\":\"s\",\"msg\":\"您的银行电子账户尚未激活，请先打款激活！\",\"isactive\":\""+isactive+"\",\"regamt\":\""+regamt+"\"}";
		}
		return json;
		
	}
	
	
	/**
	 * 解绑银行卡
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="del_card.html",produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String DelCard(HttpServletRequest request, Model model, SitePreference sitePreference){
		//会员编号
		String memberId = request.getParameter("memberid");
		//请求令牌
		String requestToken = request.getParameter("requesttoken");
		//交易密码
		String transpwd = request.getParameter("transpwd");
		//客户端
		String clienttype = request.getParameter("clienttype");
		
		FormData outData = null;
		String json = "{}";
		
		if (StringUtils.isBlank(requestToken)){
			return json = "{\"code\":\"f\",\"msg\":\"请求令牌不能为空\"}";
		}
		
		if (StringUtils.isBlank(memberId)){
			return json = "{\"code\":\"f\",\"msg\":\"会员编号不能为空\"}";
		}

		outData = checkMember(memberId, requestToken);

		String custId = String.valueOf(FormDataUtil.getProperty(outData, "custid"));
		
		FormData data = FormDataUtil.createInputForm("CtpBankCardDelete");
		FormDataUtil.setProperty(data, "memberid", custId);
		
		FormDataUtil.setProperty(data, "transpwd", transpwd);
		FormDataUtil.setProperty(data, "clienttype", clienttype);
		//验证
		
		data = ServiceClient.getResponseFormData(data, "CtpBankCardDelete");
		String respCode = String.valueOf(FormDataUtil.getProperty(data, "respcode"));
		String desc = String.valueOf(FormDataUtil.getProperty(data, "respdesc"));
		if(!respCode.equals(FormData.SUCCESS)){
			json = "{\"code\":\"f\",\"msg\":\""+desc+"\"}";
		}else{
			json = "{\"code\":\"s\",\"msg\":\"成功\"}";
		}
		return json;
	}
	
	
	/**
	 * 会员提现
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="cash.html",produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String cash(HttpServletRequest request,Model model, SitePreference sitePreference){
		//会员编号
		String memberId = request.getParameter("memberid");
		//提现优惠卷编号
		String voucherno = request.getParameter("voucherno");
		//请求令牌
		String requestToken = request.getParameter("requesttoken");
		//交易金额
		String transamt = request.getParameter("transamt");
		//手续费
		String custfee = request.getParameter("custfee");
		//银行卡号
		String acctno = request.getParameter("acctno");
		//使用金开币金额
		String coinamt = request.getParameter("coinamt");
		//提现优惠券金额
		String voucheramt = request.getParameter("voucheramt");
		
		FormData outData = null;
		String json = "";
		
		if (StringUtils.isBlank(requestToken)){
			return json = "{\"code\":\"f\",\"msg\":\"请求令牌不能为空\"}";
		}
		
		if (StringUtils.isBlank(memberId)){
			return json = "{\"code\":\"f\",\"msg\":\"会员编号不能为空\"}";
		}
		
		outData = checkMember(memberId, requestToken);

		
		String vipstate = String.valueOf(FormDataUtil.getProperty(outData, "vipstate"));
		String custId = String.valueOf(FormDataUtil.getProperty(outData, "custid"));
		String custAcNo = String.valueOf(FormDataUtil.getProperty(outData, "custacno"));

      //transamt:提现金额；acctno：银行卡；coinamt：使用金开币;voucherno:加息券编号;servfee:服务费;voucheramt:提现优惠券金额
  		double amounts = 0d;
  		double coinamts = 0d;
  		double servfee = 0d;
  		double voucheramts = 0d;
		
		if (StringUtils.isBlank(custAcNo)){
			return json = "{\"code\":\"f\",\"msg\":\"您尚未开通银行存管电子账户，请您立即开通。\"}";
		}
		
		FormData loancount = FormDataUtil.createInputForm("LoanCountSelect");
		FormDataUtil.setProperty(loancount, "memberid", custId);
		loancount = ServiceClient.getResponseFormData(loancount, "LoanCountSelect");
		String loancounts = String.valueOf(FormDataUtil.getProperty(loancount, "loancount"));
		
		if(Integer.parseInt(loancounts)>0){
			return json = "{\"code\":\"f\",\"msg\":\"您有未结清的借款项目，请前往网站发起提现申请。\"}";
		}
		
		
		if (StringUtils.isBlank(acctno)){
			return json = "{\"code\":\"f\",\"msg\":\"银行卡不能为空\"}";
		}
		
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
		double servfee1 = FeeRuleUtil.cashServfee("IMMEDIATE", PayConstant.CHAN_XABANK, String.valueOf(amounts), vipstate);
		servfee1 = FeeRuleUtil.calCashServfee(servfee1, coinamts, voucheramts);
		if(servfee != servfee1) {
			return json = "{\"code\":\"f\",\"msg\":\"服务费计算不一致\"}";
		}
		
		
		FormData formData = FormDataUtil.createInputForm("WithDrawListInsert");
		FormDataUtil.setProperty(formData, "memberid", custId);
		FormDataUtil.setProperty(formData, "transamt", amounts);
		FormDataUtil.setProperty(formData, "acctno", acctno);
		FormDataUtil.setProperty(formData, "coinamt", coinamts);
		FormDataUtil.setProperty(formData, "custfee", servfee);
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
	 * 快捷充值 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/fast_deposit.html",produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String fastDeposit(HttpServletRequest request, Model model, SitePreference sitePreference){
		//请求令牌
		String requestToken = request.getParameter("requesttoken");
		//会员编号
		String memberId = request.getParameter("memberid");
		//充值金额
		String transamt = request.getParameter("transamt");
		//银行卡
		String acctno = request.getParameter("acctno");
		//交易密码
		String transpwd = request.getParameter("transpwd");
		
		FormData outData = null;
		String json = "";
		
		if (StringUtils.isBlank(requestToken)){
			return json = "{\"code\":\"f\",\"msg\":\"请求令牌不能为空\"}";
		}
		
		if (StringUtils.isBlank(memberId)){
			return json = "{\"code\":\"f\",\"msg\":\"会员编号不能为空\"}";
		}

		outData = checkMember(memberId, requestToken);
		String custId = String.valueOf(FormDataUtil.getProperty(outData, "custid"));
		String custAcNo = String.valueOf(FormDataUtil.getProperty(outData, "custacno"));
				
		//transamt:充值金额；acctno：银行卡；transpwd：交易密码
		
		double amout = 0d;
		if (StringUtils.isBlank(custAcNo)){
			return json = "{\"code\":\"f\",\"msg\":\"您尚未开通银行存管电子账户，请您立即开通。\"}";
		}
		
		//充值金额校验
		double balance = 0;
		try {
			balance = Double.parseDouble(transamt);
		} catch (NumberFormatException e) {
			return json = "{\"code\":\"f\",\"msg\":\"充值金额不正确\"}";
		}
		if (balance <= 0) {
			return json = "{\"code\":\"f\",\"msg\":\"充值金额不正确\"}";
		}
		
		amout = balance;
		
		if(StringUtils.isBlank(acctno)){
			log.info("充值-bank id: " + acctno);
			return json = "{\"code\":\"f\",\"msg\":\"请选择正确的充值银行卡\"}";
		}
		
		FormData formData = FormDataUtil.createInputForm("DepositListInsert");
		FormDataUtil.setProperty(formData, "memberid", custId);
		FormDataUtil.setProperty(formData, "transamt", amout);
		FormDataUtil.setProperty(formData, "acctno", acctno);
		formData = ServiceClient.getResponseFormData(formData, "DepositListInsert");
		String respcode = String.valueOf(FormDataUtil.getProperty(formData, "respcode"));
		String respdesc = String.valueOf(FormDataUtil.getProperty(formData, "respdesc"));
		if(!respcode.equals(FormData.SUCCESS)){
			json = "{\"code\":\"f\",\"msg\":\""+respdesc+"\"}";
		}else{
			String listid = String.valueOf(FormDataUtil.getProperty(formData, "listid"));
			formData = FormDataUtil.createInputForm("DepositListCheck");
			FormDataUtil.setProperty(formData, "listid", listid);
			FormDataUtil.setProperty(formData, "transpwd", transpwd);
			FormDataUtil.setProperty(formData, "clienttype", "4");
			formData = ServiceClient.getResponseFormData(formData, "DepositListCheck");
			respcode = String.valueOf(FormDataUtil.getProperty(formData, "respcode"));
			respdesc = String.valueOf(FormDataUtil.getProperty(formData, "respdesc"));
			if(!respcode.equals(FormData.SUCCESS)){
				json = "{\"code\":\"f\",\"msg\":\""+respdesc+"\"}";
			}else{
				json = "{\"code\":\"s\",\"msg\":\"充值成功\"}";				
			}
		}
		return json;
		
	}
	
	/**
	 * 债权转让
	 * @param model
	 * @param request
	 * @return
	 * @throws 
	 */
	@RequestMapping(value="creditassign",produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String creditAssign(Model model,HttpServletRequest request, SitePreference sitePreference) throws Exception{
		//会员编号
		String memberId = request.getParameter("memberid");		
		//请求令牌
		String requestToken = request.getParameter("requesttoken");
		//债权转让编号
		String ctfId = request.getParameter("ctfid");
		
		FormData outData = null;
		String json = "{}";
		
		if (StringUtils.isBlank(requestToken)){
			return json = "{\"code\":\"f\",\"msg\":\"请求令牌不能为空\"}";
		}
		
		if (StringUtils.isBlank(memberId)){
			return json = "{\"code\":\"f\",\"msg\":\"会员编号不能为空\"}";
		}
		
		if (StringUtils.isBlank(ctfId)){
			return json = "{\"code\":\"f\",\"msg\":\"会员编号不能为空\"}";
		}

		outData = checkMember(memberId, requestToken);
		String custId = String.valueOf(FormDataUtil.getProperty(outData, "custid"));

		
		FormData formData = FormDataUtil.createInputForm("CtpCreditAssignInsert");
		FormDataUtil.setProperty(formData, "creditortransferid", ctfId);
		FormDataUtil.setProperty(formData, "memberid", custId);
		
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
		log.debug("json : {} ",json);
		return json;
		
	}
	
	
	/**
	 * 会员支付VIP会员费接口(app)
	 * @return
	 */
	@RequestMapping(value="pay_vip_fee",produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String vipfee(Model model,HttpServletRequest request, SitePreference sitePreference){
		
		//会员编号
		String memberId = request.getParameter("memberid");		
		//请求令牌
		String requestToken = request.getParameter("requesttoken");
		//购买vip应付金额
		String amount = request.getParameter("amount");
		//购买vip期限
		String term = request.getParameter("term");
		//购买vip实际支付金额
	    String realamt = request.getParameter("realamt");
	    //购买vip金开币金额
	    String coinamt = request.getParameter("coinamt");
		
		FormData outData = null;
		
		String json = "";
		
		// 验证用户，获取用户信息，验证token
		outData = checkMember(memberId, requestToken);
		String custacno = String.valueOf(FormDataUtil.getProperty(outData, "custacno"));

		if (StringUtils.isBlank(requestToken)){
			return json = "{\"code\":\"f\",\"msg\":\"请求令牌不能为空\"}";
		}
		
		if (StringUtils.isBlank(memberId)){
			return json = "{\"code\":\"f\",\"msg\":\"会员编号不能为空\"}";
		}		
		
		if (StringUtils.isBlank(custacno)){
			return json = "{\"code\":\"f\",\"msg\":\"您尚未开通银行存管电子账户，请您立即开通。\"}";
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
		FormDataUtil.setProperty(formData, "memberid", memberId);
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
	 * 自动投标计划开启
	 * @return
	 */
	@RequestMapping(value="autotenderopen",produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String autotenderplan(Model model,HttpServletRequest request, SitePreference sitePreference){
		//会员编号
		String memberId = request.getParameter("memberid");		
		//请求令牌
		String requestToken = request.getParameter("requesttoken");
		
		FormData outData = null;
		String json = "{}";
		
		if (StringUtils.isBlank(requestToken)){
			return json = "{\"code\":\"f\",\"msg\":\"请求令牌不能为空\"}";
		}
		
		if (StringUtils.isBlank(memberId)){
			return json = "{\"code\":\"f\",\"msg\":\"会员编号不能为空\"}";
		}	
		
		// 验证用户，获取用户信息，验证token
		outData = checkMember(memberId, requestToken);
		
		String custacno = String.valueOf(FormDataUtil.getProperty(outData, "custacno"));
		String custId = String.valueOf(FormDataUtil.getProperty(outData, "custid"));
			
		if (StringUtils.isBlank(custacno)){
			return json = "{\"code\":\"f\",\"msg\":\"您尚未开通银行存管电子账户，请您立即开通。\"}";
		}
		
		FormData formData = FormDataUtil.createInputForm("CtpAutoBidSet");
		String opertype="S";
		FormDataUtil.setProperty(formData, "opertype", opertype);
		FormDataUtil.setProperty(formData, "memberid", custId);
		formData = ServiceClient.getResponseFormData(formData, "CtpAutoBidSet");
		String respCode = String.valueOf(FormDataUtil.getProperty(formData, "respcode"));
		String desc = String.valueOf(FormDataUtil.getProperty(formData, "respdesc"));
		try {
			desc =URLEncoder.encode(desc,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
		if(!respCode.equals(FormData.SUCCESS)){
			json = "{\"code\":\"f\",\"msg\":\""+desc+"\"}"; 
		}else{			
			String transurl = String.valueOf(FormDataUtil.getProperty(formData, "transurl"));
			json = "{\"code\":\"s\",\"msg\":\"成功\",\"url\":\""+transurl+"\"}";
		}
		log.debug("json : {} ",json);
		return json;
		
	}
	
	/**
	 * 三要素开户
	 * @return
	 */
	@RequestMapping(value="openpay3f", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String openPay3Factors(String memberid, String acctno,String mobile, String requesttoken) {
		if (StringUtils.isBlank(memberid) || StringUtils.isBlank(acctno)) {
			return "{\"code\":\"f\",\"msg\":\"不合法的输入\"}";
		}
		try {
			checkMember(memberid, requesttoken);
		} catch (PayValidateException e) {
			return "{\"code\":\"f\",\"msg\":\"" + e.getMessage() + "\"}";
		}
		FormData outData = FormDataUtil.createInputForm("OpenPay3Factors");
		FormDataUtil.setProperty(outData, "memberid", memberid);
		FormDataUtil.setProperty(outData, "acctno", acctno);
		FormDataUtil.setProperty(outData, "mobile", mobile);
		outData = ServiceClient.getResponseFormData(outData, "OpenPay3Factors");
		String json = "{}";
		if (FormDataUtil.isSucceed(outData)) {
			String transurl = String.valueOf(FormDataUtil.getProperty(outData, "transurl"));
			json = "{\"code\":\"s\",\"msg\":\"成功\",\"url\":\"" + transurl + "\"}";
		} else {
			json = "{\"code\":\"f\",\"msg\":\"" + FormDataUtil.getErrorMessage(outData) + "\"}";
		}
		return json;
	}
	
}
