package com.jkgroup.kingkaid.utils.bo.fee;

import java.util.Date;

/**
 * 财务核算参数
 * @author pan
 *
 */
public class AccInfo {

	/**
	 * 失效日期
	 */
	private Date effdate;
	
	/**
	 * 定标手续费率
	 */
	private String feerate;
	
	/**
	 * 定标预提费用比率
	 */
	private String prerate;
	
	/**
	 * VIP月费用
	 */
	private String vipfeem;
	
	/**
	 * VIP年费用
	 */
	private String vipfeey;
	
	/**
	 * 债权转让收费方式
	 */
	private String feeamtkind;
	
	/**
	 * 债权转让费率/值
	 */
	private String feevalue;
	
	/**
	 * 自动流标失效周期（天）
	 */
	private String biddays;
	
	/**
	 * 债权转让失效周期（小时）
	 */
	private String assignhour;

	public Date getEffdate() {
		return effdate;
	}

	public void setEffdate(Date effdate) {
		this.effdate = effdate;
	}

	public String getFeerate() {
		return feerate;
	}

	public void setFeerate(String feerate) {
		this.feerate = feerate;
	}

	public String getPrerate() {
		return prerate;
	}

	public void setPrerate(String prerate) {
		this.prerate = prerate;
	}

	public String getVipfeem() {
		return vipfeem;
	}

	public void setVipfeem(String vipfeem) {
		this.vipfeem = vipfeem;
	}

	public String getVipfeey() {
		return vipfeey;
	}

	public void setVipfeey(String vipfeey) {
		this.vipfeey = vipfeey;
	}

	public String getFeeamtkind() {
		return feeamtkind;
	}

	public void setFeeamtkind(String feeamtkind) {
		this.feeamtkind = feeamtkind;
	}

	public String getFeevalue() {
		return feevalue;
	}

	public void setFeevalue(String feevalue) {
		this.feevalue = feevalue;
	}

	public String getBiddays() {
		return biddays;
	}

	public void setBiddays(String biddays) {
		this.biddays = biddays;
	}

	public String getAssignhour() {
		return assignhour;
	}

	public void setAssignhour(String assignhour) {
		this.assignhour = assignhour;
	}
}
