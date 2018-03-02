package com.jkgroup.kingkaid.utils.pay.param;

/**
 * 汇付天下支付参数
 * @author pan
 *
 */
public class ChinaPnrPayParam{

	public String getPay_uri() {
		return pay_uri;
	}

	public void setPay_uri(String pay_uri) {
		this.pay_uri = pay_uri;
	}

	public String getMer_cust_id() {
		return mer_cust_id;
	}

	public void setMer_cust_id(String mer_cust_id) {
		this.mer_cust_id = mer_cust_id;
	}

	public String getMer_id() {
		return mer_id;
	}

	public void setMer_id(String mer_id) {
		this.mer_id = mer_id;
	}

	public String getMer_key_file() {
		return mer_key_file;
	}

	public void setMer_key_file(String mer_key_file) {
		this.mer_key_file = mer_key_file;
	}

	public String getPg_key_file() {
		return pg_key_file;
	}

	public void setPg_key_file(String pg_key_file) {
		this.pg_key_file = pg_key_file;
	}

	public String getBg_ret_url() {
		return bg_ret_url;
	}

	public void setBg_ret_url(String bg_ret_url) {
		this.bg_ret_url = bg_ret_url;
	}

	public String getRet_url() {
		return ret_url;
	}

	public void setRet_url(String ret_url) {
		this.ret_url = ret_url;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * 第三方支付交易地址
	 */
	private String pay_uri;
	
	/**
	 * 商户客户号
	 */
	private String mer_cust_id;
	
	/**
	 * 商户号
	 */
	private String mer_id;
	
	/**
	 * 公钥文件
	 */
	private String mer_key_file;
	
	/**
	 * 私钥文件
	 */
	private String pg_key_file;
	
	/**
	 * 异步通知地址
	 */
	private String bg_ret_url;
	
	/**
	 * 同步返回地址
	 */
	private String ret_url;
	
	/**
	 * 编码方式
	 */
	private String charset;
}
