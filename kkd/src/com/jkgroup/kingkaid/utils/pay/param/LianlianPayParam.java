package com.jkgroup.kingkaid.utils.pay.param;

public class LianlianPayParam {
	
	/**
	 * 连连支付商户编号
	 */
	private String lianlian_oid_partner;
	
	/**
	 * 连连支付签名方式
	 */
	private String lianlian_sign_type;
	
	/**
	 * 连连支付签名方式
	 */
	private String lianlian_md5_key;
	
	/**
	 * 连连支付商户业务类型
	 */
	private String lianlian_busi_partner;
	
	/**
	 * 连连支付服务异步通知地址
	 */
	private String lianlian_notify_url;
	
	/**
	 * 连连支付同步通知地址
	 */
	private String lianlian_ret_url;
	
	/**
	 * 连连支付订单有效时间
	 */
	private int lianlian_valid_order;

	public String getLianlian_oid_partner() {
		return lianlian_oid_partner;
	}

	public void setLianlian_oid_partner(String lianlian_oid_partner) {
		this.lianlian_oid_partner = lianlian_oid_partner;
	}

	public String getLianlian_sign_type() {
		return lianlian_sign_type;
	}

	public void setLianlian_sign_type(String lianlian_sign_type) {
		this.lianlian_sign_type = lianlian_sign_type;
	}

	public String getLianlian_busi_partner() {
		return lianlian_busi_partner;
	}

	public void setLianlian_busi_partner(String lianlian_busi_partner) {
		this.lianlian_busi_partner = lianlian_busi_partner;
	}

	public String getLianlian_notify_url() {
		return lianlian_notify_url;
	}

	public void setLianlian_notify_url(String lianlian_notify_url) {
		this.lianlian_notify_url = lianlian_notify_url;
	}

	public String getLianlian_ret_url() {
		return lianlian_ret_url;
	}

	public void setLianlian_ret_url(String lianlian_ret_url) {
		this.lianlian_ret_url = lianlian_ret_url;
	}

	public int getLianlian_valid_order() {
		return lianlian_valid_order;
	}

	public void setLianlian_valid_order(int lianlian_valid_order) {
		this.lianlian_valid_order = lianlian_valid_order;
	}

	public String getLianlian_md5_key() {
		return lianlian_md5_key;
	}

	public void setLianlian_md5_key(String lianlian_md5_key) {
		this.lianlian_md5_key = lianlian_md5_key;
	}

}
