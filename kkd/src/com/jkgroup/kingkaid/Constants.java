package com.jkgroup.kingkaid;

public class Constants {

	/**
	 * 登录会员 
	 */
	public static final String CURRENT_USER = "user_obj";

	/**
	 * 匿名会员 
	 */
	public static final String CURRENT_ANON = "anon";
	
	/**
	 * 需登录操作的过滤路径
	 */
	public static final String AUTH = "auth";
	
	/**
	 * 无需登录认证的过滤路径
	 */
	public static final String UN_AUTH = "unauth";
	
	/**
	 * 第三方支付请求地址
	 */
	public static final String PAY_URI = "payuri";
	
	/**
	 * 第三方支付商户客户号
	 */
	public static final String MER_CUST_ID = "mercustid";
	
	/**
	 * 第三方支付交互编码方式
	 */
	public static final String CHARSET = "charset";
	
	/**
	 * 第三方支付报文1.0接口属性
	 */
	public static final String VERSION_10 = "10";
	
	/**
	 * 第三方支付报文2.0接口属性
	 */
	public static final String VERSION_20 = "20";

}
