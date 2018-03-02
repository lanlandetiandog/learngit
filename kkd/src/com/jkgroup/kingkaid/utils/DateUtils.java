package com.jkgroup.kingkaid.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年5月24日 上午11:39:07
 */

public class DateUtils {

	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	
	// 通常使用以及小贷后台精确到日的日期类型
	public final static String SDF_DATE = "yyyy-MM-dd";
	// 通常使用精确到秒的日期类型
	public final static String SDF_DATETIME = "yyyy-MM-dd HH:mm:ss";
	// 小贷后台精确到秒的日期类型
	public final static String MSDF_DATETIME = "yyyy-MM-dd'T'HH:mm:ss";
	// 小贷后台精确到月的日期类型
	public final static String MSDF_DATEMONTH = "yyyy-MM";
	// 小贷后台精确到月的日期类型_yyyyMMdd
	public final static String SDF_DATE_YMD = "yyyyMMdd";
	// VIP日期类型
	public final static String VIP_SDF_DATE = "yyyy年MM月dd日";
	
	/**
	 * 字符串转化Date对象(date)方法
	 * @param dateStr
	 * 			格式为yyyy-MM-dd
	 * @return
	 */
	public static Date getDateFromStr(String dateStr) {
		return strToDate(dateStr, SDF_DATE);
	}
	
	/**
	 * Date对象转格式为yyyy-MM-dd字符串方法
	 * @param date
	 * @return
	 */
	public static String getStrFromDate(Date date) {
		return dateToStr(date, SDF_DATE);
	}
	
	/**
	 * 字符串转化Date对象(datetime)方法
	 * @param dateStr
	 * 			格式为yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date getDatetimeFromStr(String dateStr) {
		return strToDate(dateStr, SDF_DATETIME);
	}
	
	/**
	 * Date对象转格式为yyyy-MM-dd HH:mm:ss字符串方法
	 * @param date
	 * @return
	 */
	public static String getStrFromDatetime(Date date) {
		return dateToStr(date, SDF_DATETIME);
	}
	
	/**
	 * 字符串转化Date对象(datetime)方法
	 * @param dateStr 
	 * 			小贷专用格式字符串 yyyy-MM-dd'T'HH:mm:ss
	 * 			如：2015-06-04T16:04:16
	 * @return
	 */
	public static Date getMDatetimeFromStr(String dateStr) {
		return strToDate(dateStr, MSDF_DATETIME);
	}
	
	/**
	 * Date对象转格式为yyyy-MM-dd HH:mm:ss字符串方法
	 * @param date
	 * @return
	 */
	public static String getStrFromMDatetime(Date date) {
		return dateToStr(date, MSDF_DATETIME);
	}
	
	/**
	 * Date对象转格式为yyyyMMdd字符串方法
	 * @param date
	 * @return
	 */
	public static String getYMDDate(Date date) {
		return dateToStr(date, SDF_DATE_YMD);
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
	
	private static String dateToStr(Date date, String dateformat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		return sdf.format(date);
	}
	
	/**
	 * 根据小贷系统的返回的字符串创建Date对象
	 * @param dateStr
	 * @return
	 */
	public static Date parseDateFromBmStr(String dateStr) {
		Pattern p = Pattern.compile("\\d{4}-\\d{1,2}(-\\d{1,2}(T\\d{1,2}:\\d{1,2}:\\d{1,2})?)?");
		Matcher m = p.matcher(dateStr);
		if(m.find()) {
			if(m.group(1) == null) {
				return strToDate(dateStr, MSDF_DATEMONTH);
			}
			if(m.group(2) == null) {
				return strToDate(dateStr, SDF_DATE);
			}
			return strToDate(dateStr, MSDF_DATETIME);
		}
		return null;
	}
	
	/**
	 * 获取最后一天
	 * @param sDate1
	 * @return
	 */
	public static Date getLastDayOfMonth(Date sDate1) {
        Calendar cDay1 = Calendar.getInstance();  
        cDay1.setTime(sDate1);  
        final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);  
        cDay1.set(Calendar.DATE, lastDay);
        Date lastDate = cDay1.getTime();  
        return   lastDate;  
	}
	
	/**
	 * Date对象转格式为yyyy年MM月dd日字符串方法
	 * @param date
	 * @return
	 */
	public static String getStrFromVIPDate(Date date) {
		return dateToStr(date, VIP_SDF_DATE);
	}
	
	/**
	 * 字符串转化Date对象(date)方法
	 * @param dateStr
	 * 			格式为yyyy年MM月dd日
	 * @return
	 */
	public static Date getVIPDateFromStr(String dateStr) {
		return strToDate(dateStr, VIP_SDF_DATE);
	}

	/**
	 * 计算两个Date对象之间相差的秒数
	 *
	 * @param date1 被减数
	 * @param date2 减数
     * @return
     */
	public static long subsDates(Date date1, Date date2) {
		if(null == date1 || null == date2) {
			return 0;
		}
		return (date1.getTime() - date2.getTime()) / 1000;
	}
	
	public static boolean canDownload() {
		Date now=new Date();
		Boolean tempflag=false;
		try {
			Date when = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse("20160411 14:00:00");
			tempflag = !now.before(when); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return tempflag;
	}

	public static boolean canStart() {
		Date now=new Date();
		Boolean tempflag=false;
		try {
			Date when = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse("20160523 00:00:00");
			tempflag = !now.before(when); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return tempflag;
	}
}
