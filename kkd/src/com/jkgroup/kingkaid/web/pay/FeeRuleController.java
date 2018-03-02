package com.jkgroup.kingkaid.web.pay;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.FeeRuleUtil;
import com.jkgroup.kingkaid.utils.pay.PayConstant;
import com.jkgroup.kingkaid.web.usercenter.UsrCenterProjectController;

/**
 * 费用计算
 * @author pan
 *
 */
@Controller
@RequestMapping(value="fee/rule")
public class FeeRuleController {
	
	private static final Logger log = LoggerFactory.getLogger(UsrCenterProjectController.class);
	
	/**
	 * 计算提现服务费(汇付天下)
	 * @param chanid
	 * @param amount
	 * @return
	 */
	@RequestMapping(value="/cash_serv_fee.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String cashServFee(String chanchl,String chanid,String amount){
		User user = Utils.getCurrentUser();
		String json = "{\"status\":\"f\",\"value\":\"参数不正确\"}";
		if(user == null){
			json = "{\"status\":\"f\",\"value\":\"该会员登录超时\"}";
			return json;
		}
		
		
		if(StringUtils.isBlank(chanid)){
			chanid = PayConstant.CHAN_CHINAPNR;
		}
		
		if(StringUtils.isBlank(chanchl)){
			json = "{\"status\":\"f\",\"value\":\"提现周期不正确\"}";
			return json;
		}
		
		
		double amt = 0d;
		try {
			amt = Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			json = "{\"status\":\"f\",\"value\":\"提现金额不正确\"}";
			return json;
		}
		
		amt = FeeRuleUtil.cashServfee(chanchl, chanid, amount,user.isVip() ? "1" : "0");
		
		if(amt == -1){
			json = "{\"status\":\"f\",\"value\":\"提现周期不正确\"}";
		}else{
			json = "{\"status\":\"s\",\"value\":\""+amt+"\"}";
		}
		return json;
	}
	
	/**
	 * 计算提现服务费(西安银行)
	 * @param chanid
	 * @param amount
	 * @return
	 */
	@RequestMapping(value="/xabank_cash_serv_fee.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String xaCashServFee(String chanchl,String chanid,String amount){
		User user = Utils.getCurrentUser();
		String json = "{\"status\":\"f\",\"value\":\"参数不正确\"}";
		if(user == null){
			json = "{\"status\":\"f\",\"value\":\"该会员登录超时\"}";
			return json;
		}
		
		
		if(StringUtils.isBlank(chanid)){
			chanid = PayConstant.CHAN_XABANK;
		}
		
		if(StringUtils.isBlank(chanchl)){
			json = "{\"status\":\"f\",\"value\":\"提现周期不正确\"}";
			return json;
		}
		
		
		double amt = 0d;
		try {
			amt = Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			json = "{\"status\":\"f\",\"value\":\"提现金额不正确\"}";
			return json;
		}
		
		amt = FeeRuleUtil.cashServfee(chanchl, chanid, amount,user.isVip() ? "1" : "0");
		
		if(amt == -1){
			json = "{\"status\":\"f\",\"value\":\"提现周期不正确\"}";
		}else{
			json = "{\"status\":\"s\",\"value\":\""+amt+"\"}";
		}
		return json;
	}
	
	
	/**
	 * 计算VIP会员费
	 * @param term
	 * @return
	 */
	@RequestMapping(value="cal_vip_fee", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String calVipfee(String term, String oldDate){
		try {
			Integer.parseInt(term);
		} catch (NumberFormatException e) {
			return "{\"status\":\"f\",\"value\":\"购买期限不正确\"}";
		}
		Date oDate = null;
		try {
			oldDate = URLDecoder.decode(oldDate, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "{\"status\":\"f\",\"value\":\"当前到期时间不正确\"}";
		}
		if(StringUtils.isNotBlank(oldDate)) {
			oDate = DateUtils.getVIPDateFromStr(StringUtils.trim(oldDate));
			if(oDate == null) {
				return "{\"status\":\"f\",\"value\":\"当前到期时间不正确\"}";
			}
		}
		
		Object[] obj = FeeRuleUtil.calVipfee(Integer.parseInt(term), oDate);
		return "{\"status\":\"s\",\"value\":\""+obj[0]+"\",\"enddate\":\""+DateUtils.getStrFromVIPDate((Date) obj[1])+"\"}";
		
	}
	
	
	/**
	 * 计算提现服务费(APP)
	 * @param chanid
	 * @param amount
	 * @return
	 */
	@RequestMapping(value="/cash_serv_fee_app.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String cashServFeeApp(String chanchl,String chanid,String amount,String isVip){
		String json = "{\"status\":\"f\",\"value\":\"参数不正确\"}";
		
		
		if(StringUtils.isBlank(chanid)){
			chanid = PayConstant.CHAN_CHINAPNR;
		}
		
		if(StringUtils.isBlank(chanchl)){
			json = "{\"status\":\"f\",\"value\":\"提现周期不正确\"}";
			return json;
		}
		
		
		double amt = 0d;
		try {
			amt = Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			json = "{\"status\":\"f\",\"value\":\"提现金额不正确\"}";
			return json;
		}
		
		amt = FeeRuleUtil.cashServfee(chanchl, chanid, amount, isVip);
		
		if(amt == -1){
			json = "{\"status\":\"f\",\"value\":\"提现周期不正确\"}";
		}else{
			json = "{\"status\":\"s\",\"value\":\""+amt+"\"}";
		}
		return json;
	}
	
	/**
     * 计算公允价值接口（app）
     */
    @RequestMapping(value="/getfairvalues.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String getfairvalues(String basicValue,String yearRate,String LastRepayDate){
		String json = "{}";
		 
		BigDecimal fairValue = getFairValue(basicValue,yearRate,String2Date(LastRepayDate));
		
		BigDecimal conttotalamt_ = new BigDecimal(basicValue);
		BigDecimal fairValues = conttotalamt_.add(fairValue);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fairValues", fairValues);
		
		json = ServiceClient.parseObjToJson(map);
		
	    return json;
    }
	
  //String转Date
  	public static Date String2Date(String dateString)
  	{
  		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  		Date date = null;
  		try {
  			date = sdf.parse(dateString);
  		} catch (ParseException e) {
  			e.printStackTrace();
  		}
  		return date;
  	}
  	
  //计算相差天数
  	public static int daysOfTwo(Date fDate, Date oDate) {
  	       Calendar aCalendar = Calendar.getInstance();
  	       aCalendar.setTime(fDate);
  	       int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
  	       aCalendar.setTime(oDate);
  	       int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
  	       return day2 - day1;
  	}
  	
  	/*
	 * 计算公允价值(本金未计入|按日计算利息)
	 * */
	protected BigDecimal getFairValue(String basicValue, String yearRate, Date LastRepayDate)
	{
		BigDecimal bValue = null;
		Date now = new Date();
		
		BigDecimal bdbasicValue = new BigDecimal(basicValue);
		BigDecimal bdyearRate = new BigDecimal(yearRate);
		BigDecimal bddayRate = bdyearRate.divide(new BigDecimal(360), 5, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), 5, BigDecimal.ROUND_HALF_UP);
		
//		if("".equals(LastRepayDate)||LastRepayDate==null) LastRepayDate = "2015-07-15";//为了测试//临时赋值
//		BigDecimal bdDays = new BigDecimal(daysOfTwo(String2Date(LastRepayDate.toString()), now));
		BigDecimal bdDays = new BigDecimal(daysOfTwo(LastRepayDate, now));
		
		bValue = bdbasicValue.multiply(bdDays).multiply(bddayRate);

		log.info("bdbasicValue:["+bdbasicValue+"]--");
		log.info("bdDays:["+bdDays+"]--");
		log.info("bdyearRate:["+bdyearRate+"]--");
		log.info("bddayRate:["+bddayRate+"]--");
		
		return bValue;
	}
	
	
	/**
	 * 计算VIP会员费(app)
	 * @param term
	 * @return
	 */
	@RequestMapping(value="cal_vip_fee_app", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String calVipfeeApp(String term, String oldDate){
		
		
		// 获取系统时间 调用新增加接口9006 start
		FormData getworkdate = FormDataUtil.createInputForm("WebGetWorkDate");
		FormData output = ServiceClient.getResponseFormData(getworkdate, "WebGetWorkDate");
		Date workDate = (Date) FormDataUtil.getProperty(output, "workdate");
		
		try {
			Integer.parseInt(term);
		} catch (NumberFormatException e) {
			return "{\"status\":\"f\",\"value\":\"购买期限不正确\"}";
		}
		
		try {
			oldDate = URLDecoder.decode(oldDate, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "{\"status\":\"f\",\"value\":\"当前到期时间不正确\"}";
		}
		
		Date oDate = null;
		if(StringUtils.isNotBlank(oldDate)) {
			oDate = DateUtils.getVIPDateFromStr(StringUtils.trim(oldDate));
			if(oDate == null) {
				return "{\"status\":\"f\",\"value\":\"当前到期时间不正确\"}";
			}
			if(workDate.before(oDate)){
				log.debug("workDate.before(oDate)------{}", workDate.before(oDate));
			}else{
				oDate = null;
			}
		}		

		Object[] obj = FeeRuleUtil.calVipfee(Integer.parseInt(term), oDate);
		return "{\"status\":\"s\",\"value\":\""+obj[0]+"\",\"enddate\":\""+DateUtils.getStrFromVIPDate((Date) obj[1])+"\"}";
		
	}
}
