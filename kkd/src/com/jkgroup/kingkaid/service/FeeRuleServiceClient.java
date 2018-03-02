package com.jkgroup.kingkaid.service;

import java.util.Date;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.utils.bo.fee.AccInfo;
import com.jkgroup.kingkaid.utils.bo.fee.ChanFee;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 费用规则相关方法
 * @author pan
 *
 */
public class FeeRuleServiceClient {

	/**
	 * 获取渠道费规则
	 * @param chanid
	 * @return
	 */
	public static ChanFee getChanFee(String chanid){
		FormData fd = FormDataUtil.createInputForm("ChanFee");
		FormDataUtil.setProperty(fd, "chanid", chanid);
		FormData formData = ServiceClient.getResponseFormData(fd, "ChanFee");
		ChanFee chanFee = new ChanFee();
		if(formData != null){
			chanFee.setChanid((String) FormDataUtil.getProperty(formData, "chanid"));
			chanFee.setChanname((String) FormDataUtil.getProperty(formData, "channame"));
			chanFee.setPayfeekind1((String) FormDataUtil.getProperty(formData, "payfeekind1"));
			chanFee.setPayfeevalue1((String) FormDataUtil.getProperty(formData, "payfeevalue1"));
			chanFee.setPayfeekind2((String) FormDataUtil.getProperty(formData, "payfeekind2"));
			chanFee.setPayfeevalue2((String) FormDataUtil.getProperty(formData, "payfeevalue2"));
			chanFee.setServfeekind1((String) FormDataUtil.getProperty(formData, "servfeekind1"));
			chanFee.setServfeevalue1((String) FormDataUtil.getProperty(formData, "servfeevalue1"));
			chanFee.setMaxservfee1((String) FormDataUtil.getProperty(formData, "maxservfee1"));
			chanFee.setServfeekind2((String) FormDataUtil.getProperty(formData, "servfeekind2"));
			chanFee.setServfeevalue2((String) FormDataUtil.getProperty(formData, "servfeevalue2"));
			chanFee.setMaxservfee2((String) FormDataUtil.getProperty(formData, "maxservfee2"));
			chanFee.setViprate((String) FormDataUtil.getProperty(formData, "viprate"));
		}
		return chanFee;
	}
	
	/**
	 * 获取财务核算参数费用配置
	 * @return
	 */
	public static AccInfo getAccInfo(){
		FormData fd = FormDataUtil.createInputForm("FeeRule");
		FormData formData = ServiceClient.getResponseFormData(fd, "FeeRule");
		AccInfo accInfo = new AccInfo();
		if(formData != null){
			accInfo.setEffdate((Date) FormDataUtil.getProperty(formData, "effdate"));
			accInfo.setAssignhour((String) FormDataUtil.getProperty(formData, "assignhour"));
			accInfo.setBiddays((String) FormDataUtil.getProperty(formData, "biddays"));
			accInfo.setFeeamtkind((String) FormDataUtil.getProperty(formData, "feeamtkind"));
			accInfo.setFeerate((String) FormDataUtil.getProperty(formData, "feerate"));
			accInfo.setFeevalue((String) FormDataUtil.getProperty(formData, "feevalue"));
			accInfo.setPrerate((String) FormDataUtil.getProperty(formData, "prerate"));
			accInfo.setVipfeem((String) FormDataUtil.getProperty(formData, "vipfeem"));
			accInfo.setVipfeey((String) FormDataUtil.getProperty(formData, "vipfeey"));
		}
		return accInfo;
	}
}
