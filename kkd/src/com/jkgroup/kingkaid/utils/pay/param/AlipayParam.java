package com.jkgroup.kingkaid.utils.pay.param;

/**
 * 支付宝参数
 * @author pan
 *
 */
public class AlipayParam{

	/**
	 * 支付宝商户邮箱
	 */
	private String alipay_sellmail;
	
	/**
	 * 支付宝商户名称
	 */
	private String alipay_acct_name;
	
	/**
	 * 支付宝合作者ID
	 */
	private String alipay_partner;
	
	/**
	 * 支付宝商户私钥
	 */
	private String alipay_key;
	
	/**
	 * 支付宝网关地址
	 */
	private String alipay_gateway;
	
	/**
	 * 支付宝同步返回地址
	 */
	private String alipay_ret_url;
	
	/**
	 * 支付宝异步通知地址
	 */
	private String alipay_notify_url;
	
	/**
	 * 编码方式
	 */
	private String alipay_chartset;

	public String getAlipay_sellmail() {
		return alipay_sellmail;
	}

	public void setAlipay_sellmail(String alipay_sellmail) {
		this.alipay_sellmail = alipay_sellmail;
	}

	public String getAlipay_acct_name() {
		return alipay_acct_name;
	}

	public void setAlipay_acct_name(String alipay_acct_name) {
		this.alipay_acct_name = alipay_acct_name;
	}

	public String getAlipay_partner() {
		return alipay_partner;
	}

	public void setAlipay_partner(String alipay_partner) {
		this.alipay_partner = alipay_partner;
	}

	public String getAlipay_key() {
		return alipay_key;
	}

	public void setAlipay_key(String alipay_key) {
		this.alipay_key = alipay_key;
	}

	public String getAlipay_gateway() {
		return alipay_gateway;
	}

	public void setAlipay_gateway(String alipay_gateway) {
		this.alipay_gateway = alipay_gateway;
	}

	public String getAlipay_ret_url() {
		return alipay_ret_url;
	}

	public void setAlipay_ret_url(String alipay_ret_url) {
		this.alipay_ret_url = alipay_ret_url;
	}

	public String getAlipay_notify_url() {
		return alipay_notify_url;
	}

	public void setAlipay_notify_url(String alipay_notify_url) {
		this.alipay_notify_url = alipay_notify_url;
	}

	public String getAlipay_chartset() {
		return alipay_chartset;
	}

	public void setAlipay_chartset(String alipay_chartset) {
		this.alipay_chartset = alipay_chartset;
	}
}
