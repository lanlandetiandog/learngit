package com.jkgroup.kingkaid.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.common.PayValidateException;

/**
 * 验证数据方法
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年9月28日 上午9:49:56
 */

@Service
public class ValidateService {

	private final String ERROR_NO_CUSTACNO = "您尚未开通第三方支付托管账户";
	private final String ERROR_WRONG_AMOUNT = "金额不正确";
	private final String ERROR_WRONG_COIN_AMOUNT = "金开币兑换金额有误";

	/**
	 * 是否开通第三方账户
	 * 未开通则抛出异常
	 * @param user
	 */
	public void hasCustAcNo(User user) {
		if (StringUtils.isBlank(user.getCustAcNo())) {
			throw new PayValidateException(ERROR_NO_CUSTACNO);
		}
	}

	/**
	 * 钱的金额是否正确，a判断是否是double类型 b是否大于等于0
	 * 不正确则抛出异常，正确则返回double数值
	 * @param amount
	 * @param optionName 操作名称，作为异常名称的前缀：充值(optionName)金额不正确
	 * @return
	 */
	public double isRightMoneyAmt(String amount, String optionName) {
		double balance;
		try {
			balance = Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			throw new PayValidateException(optionName + ERROR_WRONG_AMOUNT);
		}
		if (balance <= 0) {
			throw new PayValidateException(optionName + ERROR_WRONG_AMOUNT);
		}
		return balance;
	}
	
	/**
	 * 金开币的金额是否正确，a判断是否是double类型 b是否大于0
	 * 不正确则抛出异常，正确则返回double数值
	 * 
	 * @param amount
	 * @return
	 */
	public double isRightCoinAmt(String amount) {
		Double coinAmt = 0d;
		if(StringUtils.isNotBlank(amount)){
			try {
				coinAmt = Double.parseDouble(amount);
			} catch (NumberFormatException e1) {
				throw new PayValidateException(ERROR_WRONG_COIN_AMOUNT);
			}			
		}
		if(coinAmt < 0){
			throw new PayValidateException(ERROR_WRONG_COIN_AMOUNT);
		}
		return coinAmt;
	}
	
	/**
	 * 判断所给属性是否为空
	 * @param attr
	 */
	public void isNotBlank(String attr, String msg) {
		if(StringUtils.isBlank(attr)){
			throw new PayValidateException(msg);
		}
	}
	
	/**
	 * 是否是double
	 * 不是则抛出异常，是则返回double数值
	 * 
	 * @param amtStr
	 * @return
	 */
	public double isDouble(String amtStr, String msg) {
		double amount;
		try{
			amount = Double.parseDouble(amtStr);
		} catch (NumberFormatException e) {
			throw new PayValidateException(msg);
		}
		return amount;
	}
	
//	/**
//	 * 是否是double
//	 * 不是则抛出异常，是则返回double数值
//	 * 
//	 * @param amtStr
//	 * @return
//	 */
//	public double isDoubleOrBlank(String amtStr, String msg) {
//		double amount = 0d;
//		if(StringUtils.isNotBlank(amtStr)){
//			try {
//				amount = Double.parseDouble(amtStr);
//			} catch (NumberFormatException e) {
//				throw new PayValidateException(msg);
//			}
//		}
//		return amount;
//	}
	
	/**
	 * 是否是int
	 * 不是则抛出异常，是则返回int数值
	 * 
	 * @param amtStr
	 * @return
	 */
	public int isInteger(String amtStr, String msg) {
		int amount;
		try{
			amount = Integer.parseInt(amtStr);
		} catch (NumberFormatException e) {
			throw new PayValidateException(msg);
		}
		return amount;
	}
	
	/**
	 * 优惠券优惠的金额是否正确
	 * 不正确则抛出异常，正确则返回double数值
	 * 
	 * @param couponNo 优惠券号
	 * @param couponAmt 金额
	 * @return
	 */
	public double isRightCouponAmt(String couponNo, String couponAmt, String msg) {
		double amount = 0d;
		if(StringUtils.isNotBlank(couponNo)){
			try {
				amount = Double.parseDouble(couponAmt);
			} catch (NumberFormatException e) {
				throw new PayValidateException(msg);
			}
		}
		return amount;
	}
	
	public void isEqual(double d1, double d2, String msg) {
		if(d1 != d2) {
			throw new PayValidateException(msg);
		}
	}
	
	public void isEqual(String s1, String s2, String msg) {
		if(!s1.equals(s2)) {
			throw new PayValidateException(msg);
		}
	}
	
	public void isUnequal(String s1, String s2, String msg) {
		if(s1.equals(s2)) {
			throw new PayValidateException(msg);
		}
	}
	
	public void isLessThan(double d1, double d2, String msg) {
	    if(d1 < d2) {
            throw new PayValidateException(msg);
        }
	}
	
}
