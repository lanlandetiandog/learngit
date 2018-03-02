package com.jkgroup.kingkaid.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.param.AlipayParam;
import com.jkgroup.kingkaid.utils.pay.param.ChinaPnrPayParam;
import com.jkgroup.kingkaid.utils.pay.param.LianlianPayParam;

/**
 * 公共参数,公共接口
 * @author pan
 *
 */
public class CommonServiceClient {
	
	private static ChinaPnrPayParam chinaPnrPay = null;
	
	private static AlipayParam alipay = null;
	
	private static LianlianPayParam lianlianPay = null;
	
	/**
	 * 加签处理
	 * @param msgData
	 * @return
	 */
	public static String chkValue(String msgData){
		FormData formData = ServiceClient.getResponseFormData(new String[]{"1",msgData,""}, "ChkValue");
		String chkvalue = String.valueOf(FormDataUtil.getProperty(formData, "chkvalue"));
		return StringUtils.trimToEmpty(chkvalue);
	}
	
	/**
	 * 验签处理
	 * @param msgData
	 * @param chkValue
	 * @return
	 */
	public static int sign(String msgData,String chkValue){
		FormData formData = ServiceClient.getResponseFormData(new String[]{"2",msgData,chkValue}, "ChkValue");
		String chkvalue = String.valueOf(FormDataUtil.getProperty(formData, "chkvalue"));
		return Integer.parseInt(chkvalue);
	}
	
	/**
	 * 获取汇付参数
	 * @return
	 */
	public static ChinaPnrPayParam getPayParams(){
		if(chinaPnrPay!=null){
			return chinaPnrPay;			
		}
		
		FormData formData = ServiceClient.getResponseFormData(new String[0], "PaySelect");
		ChinaPnrPayParam param = new ChinaPnrPayParam();
		param.setPay_uri(String.valueOf(FormDataUtil.getProperty(formData, "pay_uri")));
		param.setMer_cust_id(String.valueOf(FormDataUtil.getProperty(formData, "mer_cust_id")));
		param.setMer_id(String.valueOf(FormDataUtil.getProperty(formData, "mer_id")));
		param.setMer_key_file(String.valueOf(FormDataUtil.getProperty(formData, "mer_key_file")));
		param.setPg_key_file(String.valueOf(FormDataUtil.getProperty(formData, "pg_key_file")));
		param.setBg_ret_url(String.valueOf(FormDataUtil.getProperty(formData, "bg_ret_url")));
		param.setRet_url(String.valueOf(FormDataUtil.getProperty(formData, "ret_url")));
		param.setCharset(String.valueOf(FormDataUtil.getProperty(formData, "charset")));
		
		chinaPnrPay = param;
		return param;
	}
	
	/**
	 * 获取支付宝参数
	 * @return
	 */
	public static AlipayParam getAliPayParams(){
		if(alipay != null){
			return alipay;
		}
		
		FormData formData = ServiceClient.getResponseFormData(new String[0], "AliPaySelect");
		AlipayParam param = new AlipayParam();
		param.setAlipay_sellmail(String.valueOf(FormDataUtil.getProperty(formData, "alipay_sellmail")));
		param.setAlipay_acct_name(String.valueOf(FormDataUtil.getProperty(formData, "alipay_acct_name")));
		param.setAlipay_partner(String.valueOf(FormDataUtil.getProperty(formData, "alipay_partner")));
		param.setAlipay_key(String.valueOf(FormDataUtil.getProperty(formData, "alipay_key")));
		param.setAlipay_gateway(String.valueOf(FormDataUtil.getProperty(formData, "alipay_gateway")));
		param.setAlipay_ret_url(String.valueOf(FormDataUtil.getProperty(formData, "alipay_ret_url")));
		param.setAlipay_notify_url(String.valueOf(FormDataUtil.getProperty(formData, "alipay_notify_url")));
		param.setAlipay_chartset(String.valueOf(FormDataUtil.getProperty(formData, "alipay_chartset")));
		
		alipay = param;
		return param;
	}
	
	/**
	 * 获取连连支付参数
	 * @return
	 */
	public static LianlianPayParam getLianlianPayParams(){
		if(lianlianPay != null){
			return lianlianPay;
		}
		
		FormData formData = ServiceClient.getResponseFormData(new String[0], "LLPaySelect");
		LianlianPayParam param = new LianlianPayParam();
		param.setLianlian_oid_partner(String.valueOf(FormDataUtil.getProperty(formData, "lianlian_oid_partner")));
		param.setLianlian_sign_type(String.valueOf(FormDataUtil.getProperty(formData, "lianlian_sign_type")));
		param.setLianlian_md5_key(String.valueOf(FormDataUtil.getProperty(formData, "lianlian_md5_key")));
		param.setLianlian_busi_partner(String.valueOf(FormDataUtil.getProperty(formData, "lianlian_busi_partner")));
		param.setLianlian_notify_url(String.valueOf(FormDataUtil.getProperty(formData, "lianlian_notify_url")));
		param.setLianlian_ret_url(String.valueOf(FormDataUtil.getProperty(formData, "lianlian_ret_url")));
		String valid_order = String.valueOf(FormDataUtil.getProperty(formData, "lianlian_valid_order"));
		try {
			param.setLianlian_valid_order(StringUtils.isBlank(valid_order) ? 1440 :Integer.parseInt(valid_order));
		} catch (NumberFormatException e) {
			param.setLianlian_valid_order(1440);
		}
		
		lianlianPay = param;
		return param;
	}
	
	/**
	 * 获取当前登录会员的账户余额信息
	 * @return
	 */
	public static FormData getBalance(){
		User user = Utils.getCurrentUser();
		FormData acctInfo = FormDataUtil.createInputForm("BalanceQuery");
		FormDataUtil.setProperty(acctInfo, "memberid", user.getMemberId());
		acctInfo = ServiceClient.getResponseFormData(acctInfo, "BalanceQuery");
		return acctInfo;
	}
	
	/**
	 * 获取当前登录会员的账户余额信息
	 * @return
	 */
	public static FormData getNewBalance(){
		User user = Utils.getCurrentUser();
		FormData acctInfo = FormDataUtil.createInputForm("QueryBalance");
		FormDataUtil.setProperty(acctInfo, "memberid", user.getMemberId());
		acctInfo = ServiceClient.getResponseFormData(acctInfo, "QueryBalance");
		return acctInfo;
	}
	
	/**
	 * 发送验证码，手机或Email未绑定会员
	 * @param destination
	 * @param channel
	 * @param option
	 * @return
	 */
	public static FormData sendVCode(String destination, int channel, int option) {
		FormData inputFormData = FormDataUtil.createInputForm("VCodeSend");
		FormDataUtil.setProperty(inputFormData, "sendtype", channel);
		FormDataUtil.setProperty(inputFormData, "senddestination", destination);
		FormDataUtil.setProperty(inputFormData, "optiontype", option);
		return ServiceClient.getResponseFormData(inputFormData, "VCodeSend");
	}
	
	/**
	 * 发送验证码(该手机/Email必须与某一会员绑定)
	 * @param destination
	 * @param channel
	 * @param option
	 * @return
	 */
	public static FormData sendmVCode(String destination, int channel, int option) {
		FormData inputFormData = FormDataUtil.createInputForm("VCodeSendM");
		FormDataUtil.setProperty(inputFormData, "sendtype", channel);
		FormDataUtil.setProperty(inputFormData, "senddestination", destination);
		FormDataUtil.setProperty(inputFormData, "optiontype", option);
		return ServiceClient.getResponseFormData(inputFormData, "VCodeSendM");
	}
	
	/**
	 * 检查验证码是否正确
	 * @param destination
	 * @param verifyCode
	 * @return
	 */
	public static FormData checkVcode(String destination, String verifyCode) {
		FormData inputFormData = FormDataUtil.createInputForm("VCodeCheck");
		FormDataUtil.setProperty(inputFormData, "senddestination", destination);
		FormDataUtil.setProperty(inputFormData, "verifycode", verifyCode);
		return ServiceClient.getResponseFormData(inputFormData, "VCodeCheck");
	}
	
	/**
	 * 获取Code Library信息
	 * @return
	 */
	public static List<FormData> queryCodeLibByNode(String nodeId, boolean recursive) {
		FormData inputFormData = FormDataUtil.createInputForm("QueryCodeLib");
		FormDataUtil.setProperty(inputFormData, "nodeid", nodeId);
		FormDataUtil.setProperty(inputFormData, "recursive", recursive? "1" : "0");
		return ServiceClient.getResponseFormDataList(inputFormData, "QueryCodeLib");
	}
	
	/**
	 * 获取活动优惠卷信息
	 */
	public static List<FormData> getRaisintList(String memberid,String flag){
		FormData inputFormData = FormDataUtil.createInputForm("RaisintList");
		FormDataUtil.setProperty(inputFormData, "memberid", memberid);
		FormDataUtil.setProperty(inputFormData, "flag", flag);
		return ServiceClient.getResponseFormDataList(inputFormData, "RaisintList");
	}
	
	/**
	 * 设置项目状态信息
	 * @param loanId
	 * @param apprState
	 * @return
	 */
	public static FormData setProjectApprState(String loanId, String apprState) {
		FormData inputData = FormDataUtil.createInputForm("ProjectApprstateSet");
		FormDataUtil.setProperty(inputData, "loanid", loanId);
		FormDataUtil.setProperty(inputData, "apprstate", apprState);
		return ServiceClient.getResponseFormData(inputData, "ProjectApprstateSet");
	}
	
	/**
	 * 获取随机因子和下个交易号
	 * @return
	 */
	public static FormData getRandomKey(String memberId) {
		FormData inData = FormDataUtil.createInputForm("GetRandomKey");
		FormDataUtil.setProperty(inData, "memberid", memberId);
		return ServiceClient.getResponseFormData(inData, "GetRandomKey");
	}
	
}
