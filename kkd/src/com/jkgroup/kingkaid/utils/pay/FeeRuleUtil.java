package com.jkgroup.kingkaid.utils.pay;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.jkgroup.kingkaid.service.FeeRuleServiceClient;
import com.jkgroup.kingkaid.utils.MathUtil;
import com.jkgroup.kingkaid.utils.bo.fee.AccInfo;
import com.jkgroup.kingkaid.utils.bo.fee.ChanFee;

/**
 * 费用规则计算
 * @author pan
 *
 */
public class FeeRuleUtil {

	/**
	 * 原始服务费计算
	 * @param chanchl
	 * @param chanid
	 * @param amount
	 * @return
	 */
	public static double calOriginalFee(String chanchl, String chanid, String amount) {
		
		double amt = Double.parseDouble(amount);
		
		ChanFee chanFee = FeeRuleServiceClient.getChanFee(chanid);
		
		if(chanchl.equals(PayConstant.CHANCHL_GENERAL)){	//普通取现
			String servfeekind = chanFee.getServfeekind1();
			double servfeevalue = Double.parseDouble(chanFee.getServfeevalue1());
			double maxservfee = Double.parseDouble(chanFee.getMaxservfee1());
			if(servfeekind.equals(PayConstant.FEE_KIND_1)){	//定额
				amt = servfeevalue;
			}else{	//定比
				amt = MathUtil.round(MathUtil.mul(amt, MathUtil.div(servfeevalue,100)),2);
				if(amt > maxservfee){
					amt = maxservfee;
				}
			}
			return amt;
			
		}else if(chanchl.equals(PayConstant.CHANCHL_FAST)){	//即时取现
			String servfeekind = chanFee.getServfeekind2();
			double servfeevalue = Double.parseDouble(chanFee.getServfeevalue2());
			double maxservfee = Double.parseDouble(chanFee.getMaxservfee2());
			
			if(servfeekind.equals(PayConstant.FEE_KIND_1)){	//定额
				amt = servfeevalue;
			}else{	//定比
				amt = MathUtil.round(MathUtil.mul(amt, MathUtil.div(servfeevalue,100)),2);
				if(amt > maxservfee){
					amt = maxservfee;
				}
			}
			
			return amt;
		}else{
			return -1;
		}
	}
	
	/**
	 * 服务费计算
	 * @return
	 */
	public static double cashServfee(String chanchl,String chanid,String amount,String isVip){
		
		if(StringUtils.isBlank(isVip)){
			isVip = "0";
		}
		
		double amt = Double.parseDouble(amount);
		
		ChanFee chanFee = FeeRuleServiceClient.getChanFee(chanid);
		double servfeerate = 0.5;
		
		if(StringUtils.isNotBlank(chanFee.getViprate())){
			servfeerate = Double.parseDouble(chanFee.getViprate());
		}
		
		if(chanchl.equals(PayConstant.CHANCHL_GENERAL)){	//普通取现
			String servfeekind = chanFee.getServfeekind1();
			double servfeevalue = Double.parseDouble(chanFee.getServfeevalue1());
			double maxservfee = Double.parseDouble(chanFee.getMaxservfee1());
			if(servfeekind.equals(PayConstant.FEE_KIND_1)){	//定额
				amt = servfeevalue;
			}else{	//定比
				amt = MathUtil.mul(amt, MathUtil.div(servfeevalue,100));
				if(amt > maxservfee){
					amt = maxservfee;
				}
			}
			
			if(isVip.equals("1")){
				amt = MathUtil.mul(amt, servfeerate);
			}
			
			return MathUtil.round(amt,2);
			
		}else if(chanchl.equals(PayConstant.CHANCHL_FAST)){	//即时取现
			String servfeekind = chanFee.getServfeekind2();
			double servfeevalue = Double.parseDouble(chanFee.getServfeevalue2());
			double maxservfee = Double.parseDouble(chanFee.getMaxservfee2());
			
			if(servfeekind.equals(PayConstant.FEE_KIND_1)){	//定额
				amt = servfeevalue;
			}else{	//定比
				amt = MathUtil.mul(amt, MathUtil.div(servfeevalue,100));
				if(amt > maxservfee){
					amt = maxservfee;
				}
			}
			
			if(isVip.equals("1")){
				amt = MathUtil.mul(amt, servfeerate);
			}
			
			return MathUtil.round(amt,2);
		}else{
			return -1;
		}
	}
	
	/**
	 * 计算减去优惠卷抵用金额 or 金开币金额 之后的服务费
	 * @param servfee
	 * @param coinamt
	 * @param vecheramt
	 * @return
	 */
	public static double calCashServfee(double servfee,double coinamt,double voucheramt){
		//减去提现优惠卷
		if(servfee - voucheramt > 0){
			servfee = MathUtil.round(MathUtil.sub(servfee, voucheramt),2);
		}else{
			servfee = 0;
		}
		
		//减去金开币抵扣
		if(servfee > 0){
			servfee = MathUtil.round(MathUtil.sub(servfee, coinamt),2);
			if(servfee < 0 )
				servfee = 0;
		}
		
		return servfee;
	}

	/**
	 * 计算vip会员费
	 * @param term
	 * @return
	 */
	public static Object[] calVipfee(int term, Date oldDate){
		double amt = 0d;
		Date date = oldDate == null ? new Date() : oldDate;
		
		AccInfo accInfo = FeeRuleServiceClient.getAccInfo();
		//购买期限小于12个月  按月计算    期限 * vipfeem
		Double vipfeem = Double.parseDouble(accInfo.getVipfeem());
		//购买期限等于12个与 按年费用计算 vipfeey
		Double vipfeey = Double.parseDouble(accInfo.getVipfeey());
		
		if(term < 12){
			amt = vipfeem * term;
		}else{
			amt = vipfeey;
		}
		
		date = DateUtils.addMonths(date, term);
		return new Object[]{amt,date};
	}
}
