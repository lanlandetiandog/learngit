package com.jkgroup.kingkaid.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 核算相关 方法
 * @author pan
 *
 */
public class AccountServiceClient {
	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

	/**
	 * 获取客户回款计划
	 * @param loanid
	 * @param bidno
	 * @return
	 */
	public static List<FormData> getCustReceivePlan(String loanid,String bidno,String receivedate){
		FormData formData = FormDataUtil.createInputForm("CustReceivePlan");
		FormDataUtil.setProperty(formData, "loanid", loanid);
		FormDataUtil.setProperty(formData, "bidno", bidno);
		if(!IsEmpty(receivedate)){
			FormDataUtil.setProperty(formData, "receivedate", strToDate(receivedate,"yyyy-MM-dd"));
		}
		List<FormData> formDatas = ServiceClient.getResponseFormDataList(formData, "CustReceivePlan");
		return formDatas;
	}
	
	public static boolean IsEmpty(String s){
		return (s==null || s.trim().length() == 0);
	}
	
	/**
	 * 字符串转Date对象方法
	 * @param str
	 * @param dateformat
	 * @return
	 */
	private static Date strToDate(String str, String dateformat) {
		if(StringUtils.isEmpty(str)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			logger.error("错误的日期格式:{}，应为:{}", str, dateformat);
			return null;
		}
	}
	
	/**
	 * 获取客户还款计划
	 * @param loanid
	 * @return
	 */
	public static List<FormData> getCustRetuPlan(String loanid){
		FormData formData = FormDataUtil.createInputForm("RetuplanQuery");
		FormDataUtil.setProperty(formData, "loanid", loanid);
		List<FormData> formDatas = ServiceClient.getResponseFormDataList(formData, "RetuplanQuery");
		return formDatas;
	}
	
	/**
	 * 投资统计
	 * @param memberId
	 * @return
	 */
	public static FormData getBidCount(String memberId){
		FormData formData = FormDataUtil.createInputForm("BidCount");
		FormDataUtil.setProperty(formData, "memberid", memberId);
		return ServiceClient.getResponseFormData(formData, "BidCount");
	}
	
	/**
	 * 借款统计
	 * @param memberId
	 * @return
	 */
	public static FormData getLoanCount(String memberId){
		FormData formData = FormDataUtil.createInputForm("LoanCount");
		FormDataUtil.setProperty(formData, "memberid", memberId);
		return ServiceClient.getResponseFormData(formData, "LoanCount");
	}
}
