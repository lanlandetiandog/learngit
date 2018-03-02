package com.jkgroup.kingkaid.utils.pay;

/**
 * 
 * @author pan
 *
 */
public class XaBankConstant {

	/**
	 * 发送短信流水后缀
	 */
	public static final String SERIAL = "_OP_SERIAL";
	
	/**
	 * 发送短信验证码后缀
	 */
	public static final String CHKCODE = "_OP_CHKCODE";
	
	/**
	 * 发送短信验证码-重置交易密码
	 */
	public static final String RESETPWD = "_RS_PWD";
	
	/**
	 * 发送短信验证码-修改交易密码
	 */
	public static final String UPDATEPWD = "_UP_PWD";
	
	/**
	 * 用户已经激活 
	 */
	public static final String MEMBER_STATE_ACTIVE = "8";
	
	/**
	 * 用户开户未激活 
	 */
	public static final String MEMBER_STATE_UNACTIVE = "6";
	
	/**
	 * 用户重置手机号码未激活 
	 */
	public static final String MEMBER_STATE_UNACTIVE_UPMOBILE = "7";
	
	public static final String CURR_TRAN_SEQ = "CURR_TRAN_SEQ";
}
