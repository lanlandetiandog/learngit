package com.jkgroup.kingkaid.utils.bo.fee;

/**
 * 渠道费
 * @author pan
 *
 */
public class ChanFee {
	
	/**
	 * 渠道编号
	 */
	private String chanid;
	
	/**
	 * 渠道名称
	 */
	private String channame;
	
	/**
	 * 充值费用收取方式
	 */
	private String payfeekind1;
	
	/**
	 * 充值费用比率/值
	 */
	private String payfeevalue1;
	
	/**
	 * 提现费用收取方式
	 */
	private String payfeekind2;
	
	/**
	 * 提现费用比率/值
	 */
	private String payfeevalue2;
	
	/**
	 * 普通取现服务费收取方式
	 */
	private String servfeekind1;
	
	/**
	 * 普通取现服务费比率/值
	 */
	private String servfeevalue1;
	
	/**
	 * 普通取现服务费最高上限
	 */
	private String maxservfee1;
	
	/**
	 * 即时取现服务费收取方式
	 */
	private String servfeekind2;
	
	/**
	 * 即时取现服务费比率/值
	 */
	private String servfeevalue2;
	
	/**
	 * 即时取现服务费最高上限
	 */
	private String maxservfee2;
	
	private String viprate;

	public String getChanid() {
		return chanid;
	}

	public void setChanid(String chanid) {
		this.chanid = chanid;
	}

	public String getChanname() {
		return channame;
	}

	public void setChanname(String channame) {
		this.channame = channame;
	}

	public String getPayfeekind1() {
		return payfeekind1;
	}

	public void setPayfeekind1(String payfeekind1) {
		this.payfeekind1 = payfeekind1;
	}

	public String getPayfeevalue1() {
		return payfeevalue1;
	}

	public void setPayfeevalue1(String payfeevalue1) {
		this.payfeevalue1 = payfeevalue1;
	}

	public String getPayfeekind2() {
		return payfeekind2;
	}

	public void setPayfeekind2(String payfeekind2) {
		this.payfeekind2 = payfeekind2;
	}

	public String getPayfeevalue2() {
		return payfeevalue2;
	}

	public void setPayfeevalue2(String payfeevalue2) {
		this.payfeevalue2 = payfeevalue2;
	}

	public String getServfeekind1() {
		return servfeekind1;
	}

	public void setServfeekind1(String servfeekind1) {
		this.servfeekind1 = servfeekind1;
	}

	public String getServfeevalue1() {
		return servfeevalue1;
	}

	public void setServfeevalue1(String servfeevalue1) {
		this.servfeevalue1 = servfeevalue1;
	}

	public String getMaxservfee1() {
		return maxservfee1;
	}

	public void setMaxservfee1(String maxservfee1) {
		this.maxservfee1 = maxservfee1;
	}

	public String getServfeekind2() {
		return servfeekind2;
	}

	public void setServfeekind2(String servfeekind2) {
		this.servfeekind2 = servfeekind2;
	}

	public String getServfeevalue2() {
		return servfeevalue2;
	}

	public void setServfeevalue2(String servfeevalue2) {
		this.servfeevalue2 = servfeevalue2;
	}

	public String getMaxservfee2() {
		return maxservfee2;
	}

	public void setMaxservfee2(String maxservfee2) {
		this.maxservfee2 = maxservfee2;
	}

	public String getViprate() {
		return viprate;
	}

	public void setViprate(String viprate) {
		this.viprate = viprate;
	}
}
