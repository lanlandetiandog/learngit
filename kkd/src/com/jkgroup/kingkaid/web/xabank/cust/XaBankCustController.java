package com.jkgroup.kingkaid.web.xabank.cust;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.MessageWrapper;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.CommonServiceClient;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.FilterExt;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.XaBankConstant;
import com.jkgroup.kingkaid.web.xabank.account.XaBankAccountController;

/**
 * 西安银行客户类接口处理
 * 
 * @author pan
 * 
 */
@Controller
@RequestMapping(value = Constants.AUTH + "/cust")
public class XaBankCustController {

	public static final Logger logger = LoggerFactory
			.getLogger(XaBankCustController.class);

	/**
	 * 西安银行开户
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/openpay_page.html", "openpay_page.html" }, produces = "text/html; charset=utf-8")
	public String openPayPage(Model model) {
		User user = Utils.getCurrentUser();
		String interfaceName = user.isCorp() ? "OpenPayCorpSelect"
				: "OpenPaySelect";
		FormData data = FormDataUtil.createInputForm(interfaceName);
		FormDataUtil.setProperty(data, "memberid", user.getMemberId());
		data = ServiceClient.getResponseFormData(data, interfaceName);
		String isactive = String.valueOf(FormDataUtil.getProperty(data,
				"isactive"));
		model.addAttribute("formData", data);

		if ("0".equals(isactive)) {
			String bankcode = String.valueOf(FormDataUtil.getProperty(data,
					"bankcode"));
			model.addAttribute("bankcode", bankcode.substring(
					bankcode.length() - 4, bankcode.length()));

			return "xabank/cust/openpaysign";
		} else if ("1".equals(isactive)) {
			return "xabank/cust/openpaysuccess";
		} else {
			// 开户时将用户（若是已经认证过的用户则查出其真实姓名、银行卡号、身份证号等信息）通过session传值，防止恶意修改开户号码，保证交付给西安银行电话号码与网站注册号码保持一致
			Session session = SecurityUtils.getSubject().getSession();
			if (!user.isCorp()) {
				session.setAttribute("currcustname", String
						.valueOf(FormDataUtil.getProperty(data, "custname")));
				session.setAttribute("currpaperid", String.valueOf(FormDataUtil
						.getProperty(data, "paperid")));
			}

			return "xabank/cust/" + (user.isCorp() ? "corp/" : "") + "openpay";
		}

	}

	/**
	 * 西安银行开户
	 * 
	 * @return
	 */
	@RequestMapping(value = "openpay_modify.html", produces = "text/html; charset=utf-8")
	public String openPayModifyPage(Model model) {
		User user = Utils.getCurrentUser();
		if (!user.isCorp()) {
			return "redirect:xabank/cust/openpay";
		} else {
			FormData data = FormDataUtil.createInputForm("OpenPayCorpSelect");
			FormDataUtil.setProperty(data, "memberid", user.getMemberId());
			data = ServiceClient.getResponseFormData(data, "OpenPayCorpSelect");
			model.addAttribute("formData", data);
			return "xabank/cust/corp/openpay_modify";
		}
	}

	/**
	 * 西安银行开户协议
	 * 
	 * @return
	 */
	@RequestMapping(value = "xaprotocol.html", produces = "text/html; charset=utf-8")
	public String xaProTocol(Model model) {

		return "xabank/cust/xaprotocol";
	}

	/**
	 * 西安银行开户
	 * 
	 * @return
	 */
	@RequestMapping(value = "/cust_info.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String custInfo(Model model) {
		User user = Utils.getCurrentUser();
		FormData data = FormDataUtil.createInputForm("OpenPaySelect");
		FormDataUtil.setProperty(data, "memberid", user.getMemberId());

		data = ServiceClient.getResponseFormData(data, "OpenPaySelect");
		String json = ServiceClient.parseObjToJson(data);
		logger.info("select custOpenPayInfo :", json);
		return json;
	}

	/**
	 * 银行卡支持鉴权查询
	 * 
	 * @param acctno
	 * @return
	 */
	@RequestMapping(value = "/bank_certify.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String bankcertify(String ACCT_NO) {
		User user = Utils.getCurrentUser();
		FormData outData = FormDataUtil.createInputForm("BankCardCertify");
		FormDataUtil.setProperty(outData, "memberid", user.getMemberId());
		FormDataUtil.setProperty(outData, "acctno", ACCT_NO);
		outData = ServiceClient.getResponseFormData(outData, "BankCardCertify");

		String[] bankNames = { "建设银行", "广发银行", "邮政储蓄银行", "招商银行", "工商银行",
				"中国农业银行", "中国银行", "交通银行", "光大银行", "平安银行", "中信银行", "浦发银行",
				"西安银行", "民生银行" };

		Set<String> set = new HashSet<String>(Arrays.asList(bankNames));

		if (FormDataUtil.isSucceed(outData)) {
			String bankName = String.valueOf(FormDataUtil.getProperty(outData,
					"bankname"));
			if (StringUtils.isEmpty(bankName) || !set.contains(bankName)) {
				return ServiceClient.parseObjToJson(new MessageWrapper(false,
						"暂不支持该卡，请更换！"));
			} else {
				MessageWrapper wrapper = new MessageWrapper(true);
				wrapper.setBody(bankName);
				return ServiceClient.parseObjToJson(wrapper);
			}
		} else {
			return ServiceClient.parseObjToJson(FormDataUtil
					.buildFailedMsgWrapper(outData));
		}
	}

	/**
	 * 发送短信验证码（开户）
	 * 
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/sendopenmsg.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String sendOpenMsg(Model model, HttpServletRequest request) {

		String mobile = request.getParameter("MOBILE");
		;

		User user = Utils.getCurrentUser();
		String json = "";
		if (StringUtils.isBlank(mobile) || mobile.length() != 11) {
			return json = "{\"code\":\"f\",\"msg\":\"电话号码格式不正确\"}";
		}
		FormData formData = FormDataUtil.createInputForm("OpenPaySendMsg");
		FormDataUtil.setProperty(formData, "mobile", mobile);
		FormDataUtil.setProperty(formData, "memberid", user.getMemberId());
		formData = ServiceClient
				.getResponseFormData(formData, "OpenPaySendMsg");
		String respCode = String.valueOf(FormDataUtil.getProperty(formData,
				"respcode"));
		String desc = String.valueOf(FormDataUtil.getProperty(formData,
				"respdesc"));
		if (!respCode.equals(FormData.SUCCESS)) {
			json = "{\"code\":\"f\",\"msg\":\"" + desc + "\"}";
		} else {
			String chkcode = String.valueOf(FormDataUtil.getProperty(formData,
					"chkcode"));
			String serialno = String.valueOf(FormDataUtil.getProperty(formData,
					"serialno"));
			Session session = SecurityUtils.getSubject().getSession();
			session.setAttribute(user.getMemberId() + XaBankConstant.SERIAL,
					serialno);
			session.setAttribute(user.getMemberId() + XaBankConstant.CHKCODE,
					chkcode);
			json = "{\"code\":\"s\",\"msg\":\"发送成功\"}";
		}

		logger.debug("json : {}", json);
		return json;
	}

	/**
	 * 发送短信验证码（重置密码，修改手机号码）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sendmsg.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String sendMsg(String type) {
		String json = "{}";
		User user = Utils.getCurrentUser();
		FormData formData = FormDataUtil.createInputForm("CtpMsgSend");
		FormDataUtil.setProperty(formData, "memberid", user.getMemberId());
		FormDataUtil.setProperty(formData, "trantype", type);
		formData = ServiceClient.getResponseFormData(formData, "CtpMsgSend");
		String respCode = String.valueOf(FormDataUtil.getProperty(formData,
				"respcode"));
		String desc = String.valueOf(FormDataUtil.getProperty(formData,
				"respdesc"));
		if (!respCode.equals(FormData.SUCCESS)) {
			json = "{\"code\":\"f\",\"msg\":\"" + desc + "\"}";
		} else {
			json = "{\"code\":\"s\",\"msg\":\"发送成功\"}";
		}
		logger.debug("json : {}", json);
		return json;
	}

	/**
	 * 查询客户信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/get_custinfo.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String getCustInfo() {
		User user = Utils.getCurrentUser();
		FormData data = FormDataUtil.createInputForm("OpenPaySelect");
		FormDataUtil.setProperty(data, "memberid", user.getMemberId());
		data = ServiceClient.getResponseFormData(data, "OpenPaySelect");
		String json = ServiceClient.parseObjToJson(data);
		logger.debug("json:{}", json);
		return json;
	}

	/**
	 * 调用后台开户联机交易
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openpay.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String openPay(HttpServletRequest request) {
		User user = Utils.getCurrentUser();
		Session session = SecurityUtils.getSubject().getSession();

		// 先对通过session拿到后台查到的用户姓名、身份证号、银行卡号的信息进行校验，
		// 若不为空，则说明是开户人是已实名认证过的老用户，直接取session所传的值；相反则说明开户人为新用户开户，直接取前台页面所传的值
		String custname = (String) session.getAttribute("currcustname");
		if (StringUtils.isEmpty(custname)) {
			custname = request.getParameter("ID_NAME");
		}
		String paperid = (String) session.getAttribute("currpaperid");
		if (StringUtils.isEmpty(paperid)) {
			paperid = request.getParameter("ID_NO");
		}
		String acctno = request.getParameter("ACCT_NO");

		String transpwd = request.getParameter("TRANS_PWD");
		String chkcode = request.getParameter("CHK_CODE");
		String mobile = request.getParameter("MOBILE");
		String serialno = String.valueOf(session.getAttribute(user
				.getMemberId() + XaBankConstant.SERIAL));

		FormData data = FormDataUtil.createInputForm("OpenPayInsert");
		FormDataUtil.setProperty(data, "transeq",
				(String) session.getAttribute(XaBankConstant.CURR_TRAN_SEQ));
		FormDataUtil.setProperty(data, "memberid", user.getMemberId());
		FormDataUtil.setProperty(data, "custname", custname);
		FormDataUtil.setProperty(data, "paperid", paperid);
		FormDataUtil.setProperty(data, "mobilenumber", mobile);
		FormDataUtil.setProperty(data, "acctno", acctno);
		try {
			transpwd = URLDecoder.decode(transpwd, "UTF-8");
			transpwd = Base64.encodeBase64String(transpwd.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("个人开户密码URLDecode或Base64编码时异常", e);
		}
		FormDataUtil.setProperty(data, "transpwd", transpwd);
		FormDataUtil.setProperty(data, "chkcode", chkcode);
		FormDataUtil.setProperty(data, "serialno", serialno);
		data = ServiceClient.getResponseFormData(data, "OpenPayInsert");
		if (FormDataUtil.isSucceed(data)) {
			String isactive = String.valueOf(FormDataUtil.getProperty(data,
					"isactive"));
			if ("1".equals(isactive)) {
				user.setMemberState(XaBankConstant.MEMBER_STATE_ACTIVE);
			} else {
				user.setMemberState(XaBankConstant.MEMBER_STATE_UNACTIVE);
			}
			return ServiceClient.parseObjToJson(new MessageWrapper(true));
		} else {
			return ServiceClient.parseObjToJson(FormDataUtil
					.buildFailedMsgWrapper(data));
		}
	}

	/**
	 * 修改交易密码
	 * 
	 * @param oldpwd
	 * @param newpwd
	 * @param chkcode
	 * @return
	 */
	@RequestMapping(value = "update_pwd.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String updatepwd(String oldpwd, String newpwd, String chkcode) {
		User user = Utils.getCurrentUser();
		Session session = SecurityUtils.getSubject().getSession();
		FormData data = FormDataUtil.createInputForm("PwdUpdate");
		FormDataUtil.setProperty(data, "memberid", user.getMemberId());
		try {
			oldpwd = URLDecoder.decode(oldpwd, "UTF-8");
			oldpwd = Base64.encodeBase64String(oldpwd.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("旧密码URLDecode或Base64编码时异常", e);
		}
		try {
			newpwd = URLDecoder.decode(newpwd, "UTF-8");
			newpwd = Base64.encodeBase64String(newpwd.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("新密码URLDecode或Base64编码时异常", e);
		}
		FormDataUtil.setProperty(data, "oldpwd", oldpwd);
		FormDataUtil.setProperty(data, "newpwd", newpwd);
		FormDataUtil.setProperty(data, "chkcode", chkcode);
		FormDataUtil.setProperty(data, "transeq",
				(String) session.getAttribute(XaBankConstant.CURR_TRAN_SEQ));
		// 验证
		data = ServiceClient.getResponseFormData(data, "PwdUpdate");
		if (FormDataUtil.isSucceed(data)) {
			return ServiceClient.parseObjToJson(new MessageWrapper(true,
					"修改交易密码成功"));
		} else {
			return ServiceClient.parseObjToJson(FormDataUtil
					.buildFailedMsgWrapper(data));
		}
	}

	/**
	 * 重置交易密码
	 * 
	 * @param chkcode
	 * @return
	 */
	@RequestMapping(value = "reset_pwd.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String resetpwd(String chkcode, String newpwd) {
		User user = Utils.getCurrentUser();
		Session session = SecurityUtils.getSubject().getSession();
		FormData data = FormDataUtil.createInputForm("PwdReset");
		FormDataUtil.setProperty(data, "memberid", user.getMemberId());
		FormDataUtil.setProperty(data, "chkcode", chkcode);
		try {
			newpwd = URLDecoder.decode(newpwd, "UTF-8");
			newpwd = Base64.encodeBase64String(newpwd.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("新密码URLDecode或Base64编码时异常", e);
		}
		FormDataUtil.setProperty(data, "newpwd", newpwd);
		FormDataUtil.setProperty(data, "transeq",
				(String) session.getAttribute(XaBankConstant.CURR_TRAN_SEQ));
		// 验证
		data = ServiceClient.getResponseFormData(data, "PwdReset");
		if (FormDataUtil.isSucceed(data)) {
			return ServiceClient.parseObjToJson(new MessageWrapper(true,
					"重置交易密码成功"));
		} else {
			return ServiceClient.parseObjToJson(FormDataUtil
					.buildFailedMsgWrapper(data));
		}
	}

	/**
	 * 重置手机号码
	 * 
	 * @param newmobile
	 * @param chkcode
	 * @return
	 */
	@RequestMapping(value = "reset_mobile.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String resetmobile(String newmobile, String chkcode, String ctpname,
			String ctppaperid) {
		User user = Utils.getCurrentUser();
		FormData data = FormDataUtil.createInputForm("MobileReset");
		FormDataUtil.setProperty(data, "memberid", user.getMemberId());
		FormDataUtil.setProperty(data, "newmobile", newmobile);
		FormDataUtil.setProperty(data, "ctpname", ctpname);
		FormDataUtil.setProperty(data, "ctppaperid", ctppaperid);
		FormDataUtil.setProperty(data, "chkcode", chkcode);
		// 验证
		data = ServiceClient.getResponseFormData(data, "MobileReset");
		String respCode = String.valueOf(FormDataUtil.getProperty(data,
				"respcode"));
		String desc = String
				.valueOf(FormDataUtil.getProperty(data, "respdesc"));
		String json = "{}";
		if (!respCode.equals(FormData.SUCCESS)) {
			json = "{\"code\":\"f\",\"msg\":\"" + desc + "\"}";
		} else {
			String transurl = String.valueOf(FormDataUtil.getProperty(data,
					"transurl"));
			json = "{\"code\":\"s\",\"msg\":\"成功\",\"url\":\"" + transurl
					+ "\"}";
		}
		logger.debug("json : {}", json);
		return json;
	}

	/**
	 * 绑定银行卡
	 * 
	 * @param acctno
	 * @param transpwd
	 * @return
	 */
	@RequestMapping(value = "add_card.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String addCard(String acctno, String transpwd) {
		User user = Utils.getCurrentUser();
		Session session = SecurityUtils.getSubject().getSession();
		try {
			transpwd = URLDecoder.decode(transpwd, "UTF-8");
			transpwd = Base64.encodeBase64String(transpwd.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("解绑银行卡个人密码URLDecode或Base64编码时异常", e);
		}

		FormData data = FormDataUtil.createInputForm("CtpBankCardInsert");
		FormDataUtil.setProperty(data, "memberid", user.getMemberId());
		FormDataUtil.setProperty(data, "acctno", acctno);
		FormDataUtil.setProperty(data, "transpwd", transpwd);
		FormDataUtil.setProperty(data, "transeq",
				(String) session.getAttribute(XaBankConstant.CURR_TRAN_SEQ));

		data = ServiceClient.getResponseFormData(data, "CtpBankCardInsert");
		String respCode = String.valueOf(FormDataUtil.getProperty(data,
				"respcode"));
		String desc = String
				.valueOf(FormDataUtil.getProperty(data, "respdesc"));
		String json = "{}";
		if (!respCode.equals(FormData.SUCCESS)) {
			json = "{\"code\":\"f\",\"msg\":\"" + desc + "\"}";
		} else {
			String regamt = String.valueOf(FormDataUtil.getProperty(data,
					"regamt"));
			String isactive = String.valueOf(FormDataUtil.getProperty(data,
					"isactive"));
			json = "{\"code\":\"s\",\"msg\":\"成功\",\"isactive\":\"" + isactive
					+ "\",\"regamt\":\"" + regamt + "\"}";
		}
		logger.debug("json : {}", json);
		return json;
	}

	/**
	 * 绑定银行卡删除
	 * 
	 * @param acctno
	 * @param transpwd
	 * @return
	 */
	@RequestMapping(value = "del_card.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String delCard(String transpwd) {
		Session session = SecurityUtils.getSubject().getSession();
		try {
			transpwd = URLDecoder.decode(transpwd, "UTF-8");
			transpwd = Base64.encodeBase64String(transpwd.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("解绑银行卡个人密码URLDecode或Base64编码时异常", e);
		}
		
		//余额查询
		FormData acctInfo = CommonServiceClient.getBalance();
		if(!(new XaBankAccountController().accountSettled(acctInfo))) {
			return "{\"code\":\"f\",\"msg\":\"您的账户未结清\"}";
		}

		User user = Utils.getCurrentUser();
		FormData data = FormDataUtil.createInputForm("CtpBankCardDelete");
		FormDataUtil.setProperty(data, "memberid", user.getMemberId());
		FormDataUtil.setProperty(data, "transpwd", transpwd);

		FormDataUtil.setProperty(data, "transeq", (String) session.getAttribute(XaBankConstant.CURR_TRAN_SEQ));

		data = ServiceClient.getResponseFormData(data, "CtpBankCardDelete");
		String respCode = String.valueOf(FormDataUtil.getProperty(data, "respcode"));
		String desc = String.valueOf(FormDataUtil.getProperty(data, "respdesc"));
		String json = "{}";
		if (!respCode.equals(FormData.SUCCESS)) {
			json = "{\"code\":\"f\",\"msg\":\"" + desc + "\"}";
		} else {
			json = "{\"code\":\"s\",\"msg\":\"成功\"}";
		}
		logger.debug("json : {}", json);
		return json;
	}

	/**
	 * 企业用户调用后台开户联机交易
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openpaycorp.html", method = RequestMethod.POST)
	public String openPayCorp(HttpServletRequest request, Model model) {
		User user = Utils.getCurrentUser();
		List<FilterExt> fList = FilterExt.buildFromHttpRequest(request);
		FormData inData = FormDataUtil.buildFormDataByFilterExt(fList,
				"OpenPayCorpInsert");
		FormDataUtil.setProperty(inData, "memberid", user.getMemberId());
		FormDataUtil
				.setProperty(inData, "mobilenumber", user.getMobileNumber());
		FormData outData = ServiceClient.getResponseFormData(inData,
				"OpenPayCorpInsert");
		String respcode = String.valueOf(FormDataUtil.getProperty(outData,
				"respcode"));
		model.addAttribute("respcode", respcode);
		model.addAttribute("respdesc",
				String.valueOf(FormDataUtil.getProperty(outData, "respdesc")));
		if ("S".equals(respcode)) {
			user.setAuthState("Z");
		}
		return "xabank/cust/corp/openpaysuccess";
	}

	/**
	 * 企业开户修改资料或者撤销
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openpaycorp_modify.html", method = RequestMethod.POST)
	public String openPayCorpModify(HttpServletRequest request,
			String operateType, Model model) {
		User user = Utils.getCurrentUser();
		List<FilterExt> fList = FilterExt.buildFromHttpRequest(request);
		FormData inData = FormDataUtil.buildFormDataByFilterExt(fList,
				"OpenPayCorpUpdateCancel");
		FormDataUtil.setProperty(inData, "memberid", user.getMemberId());
		FormDataUtil
				.setProperty(inData, "mobilenumber", user.getMobileNumber());
		FormDataUtil.setProperty(inData, "opttype", operateType);
		FormData outData = ServiceClient.getResponseFormData(inData,
				"OpenPayCorpUpdateCancel");
		String respcode = String.valueOf(FormDataUtil.getProperty(outData,
				"respcode"));
		String respDesc = String.valueOf(FormDataUtil.getErrorMessage(outData));
		model.addAttribute("respcode", respcode);
		if ("S".equals(respcode)) {
			if ("2".equals(operateType)) {
				user.setAuthState("Z");
			} else {
				user.setAuthState("0");
			}
		}
		model.addAttribute("operateType", operateType);
		model.addAttribute("respcode", respcode);
		model.addAttribute("respdesc", respDesc);
		return "xabank/cust/corp/openpay_modifysuccess";
	}

	/**
	 * 模拟打款激活
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openpaysign", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String openPaySign(HttpServletRequest request, String TRAN_AMT,
			String ELE_ACCT_NO, String RCV_NAME, String RCV_ACCT,
			String RCV_BANK) {
		User user = Utils.getCurrentUser();

		FormData data = FormDataUtil.createInputForm("OpenPayActive");
		FormDataUtil.setProperty(data, "memberid", user.getMemberId());
		FormDataUtil.setProperty(data, "rcv_name", RCV_NAME);
		FormDataUtil.setProperty(data, "tran_amt",
				StringUtils.isEmpty(TRAN_AMT) ? "100" : TRAN_AMT);
		FormDataUtil.setProperty(data, "ele_acct_no", ELE_ACCT_NO);
		FormDataUtil.setProperty(data, "rcv_acct", RCV_ACCT);
		FormDataUtil.setProperty(data, "rcv_bank", RCV_BANK);
		data = ServiceClient.getResponseFormData(data, "OpenPayActive");
		String respcode = String.valueOf(FormDataUtil.getProperty(data,
				"respcode"));
		if (respcode.equals("0000")) {
			String isactive = String.valueOf(FormDataUtil.getProperty(data,
					"isactive"));
			if ("1".equals(isactive)) {
				user.setMemberState(XaBankConstant.MEMBER_STATE_ACTIVE);
			}
		}
		String json = ServiceClient.parseObjToJson(data);
		logger.debug("json:{}", json);
		return json;
	}

	/**
	 * 三要素开户页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/openpay3f_page.html", produces = "text/html; charset=utf-8")
	public String openPay3FactorsPage(Model model) {
		User user = Utils.getCurrentUser();
		if (user.isCorp()) {
			return "xabank/cust/corp/openpay";
		}
		FormData data = FormDataUtil.createInputForm("OpenPaySelect");
		FormDataUtil.setProperty(data, "memberid", user.getMemberId());
		data = ServiceClient.getResponseFormData(data, "OpenPaySelect");
		String isactive = String.valueOf(FormDataUtil.getProperty(data,
				"isactive"));
		model.addAttribute("formData", data);
		if ("0".equals(isactive)) {
			String bankcode = String.valueOf(FormDataUtil.getProperty(data,
					"bankcode"));
			model.addAttribute("bankcode", bankcode.substring(
					bankcode.length() - 4, bankcode.length()));
			return "xabank/cust/openpaysign";
		} else if ("1".equals(isactive)) {
			return "xabank/cust/openpaysuccess";
		} else {
			return "xabank/cust/openpay3f";
		}
	}

	/**
	 * 三要素开户
	 * 
	 * @return
	 */
	@RequestMapping(value = "openpay3f", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String openPay3Factors(String ACCT_NO, String MOBILE) {
		User user = Utils.getCurrentUser();
		FormData outData = FormDataUtil.createInputForm("OpenPay3Factors");
		FormDataUtil.setProperty(outData, "memberid", user.getMemberId());
		FormDataUtil.setProperty(outData, "acctno", ACCT_NO);
		FormDataUtil.setProperty(outData, "mobile", MOBILE);
		outData = ServiceClient.getResponseFormData(outData, "OpenPay3Factors");
		if (FormDataUtil.isSucceed(outData)) {
			MessageWrapper mw = new MessageWrapper(true);
			mw.setBody(FormDataUtil.getProperty(outData, "transurl"));
			return ServiceClient.parseObjToJson(mw);
		} else {
			return ServiceClient.parseObjToJson(FormDataUtil
					.buildFailedMsgWrapper(outData));
		}
	}

}
