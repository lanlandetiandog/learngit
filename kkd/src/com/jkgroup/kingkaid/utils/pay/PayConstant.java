package com.jkgroup.kingkaid.utils.pay;

/**
 * @author Chris
 *
 */
public class PayConstant {
	
	/**
	 * 统一跳转支付路径
	 */
	public static final String FORWARD = "pay/forward";
	
	/**
	 * 财务核算操作、充值、提现、生利宝、vip费用 财务机构统一用_90001_陕西金开贷
	 */
	public static final String ACBANKID = "90001";
	
	/**
	 * 商户子账户号、所有账务都记录到该账户下
	 */
	public static final String MDT000001 = "MDT000001";
	
	/**
	 * 商户子账户类型
	 */
	public static final String ACCTTYPE = "MERDT";
	
	/**
	 * 支付渠道 chanid = 1 汇付天下支付渠道
	 */
	public static final String CHAN_CHINAPNR = "1";
	
	/**
	 * 支付渠道 chanid = 2 支付宝渠道
	 */
	public static final String CHAN_ALIPAY = "2";
	
	/**
	 * 支付渠道 chanid = 3 连连支付渠道
	 */
	public static final String CHAN_LIANLIANPAY = "3";
	
	/**
	 * 支付渠道 chanid = 4 西安银行支付渠道
	 */
	public static final String CHAN_XABANK = "4";
	
	
	//--------------------------------------------------------------------
	
	/**
	 * listtype 单据类型 ：充值
	 */
	public static final String LIST_TYPE_DEPOSIT = "10";
	
	/**
	 * listtype 单据类型 ：提现
	 */
	public static final String LIST_TYPE_WITHDRAW = "11";
	
	/**
	 * listtype 单据类型 ：投标
	 */
	public static final String LIST_TYPE_TENDER = "40";
	
	/**
	 * listtype 单据类型 ：VIP会员费
	 */
	public static final String LIST_TYPE_VIPFEE = "14";
	
	/**
	 * listtype 单据类型 ：生利宝申购、赎回
	 */
	public static final String LIST_TYPE_FSSTRAN = "43";
	
	/**
	 * listtype 单据类型 ：债权转让
	 */
	public static final String LIST_TYPE_CREDIT = "24";
	//-------------------------------------------------------------------------
	
	/**
	 * busitype 业务类型 ：充值
	 */
	public static final String BUSI_TYPE_DEPOSIT = "13";
	
	/**
	 * busitype 业务类型 ：提现
	 */
	public static final String BUSI_TYPE_WITHDRAW = "14";
	
	/**
	 * busitype 业务类型 ：投标
	 */
	public static final String BUSI_TYPE_TENDER = "40";
	
	/**
	 * busitype 业务类型 ：VIP会员费
	 */
	public static final String BUSI_TYPE_VIPFEE = "17";
	
	/**
	 * busitype 业务类型 ：生利宝申购、赎回
	 */
	public static final String BUSI_TYPE_FSSTRAN = "42";
	
	/**
	 * busitype 业务类型 ：债权转让
	 */
	public static final String BUSI_TYPE_CREDIT = "24";
	
	/*----------------------------------------------------------------------*/
	
	/**
	 * 加息卷
	 */
	public static final String RAISINTE_JXJ = "1";
	
	/**
	 * 提现优惠卷
	 */
	public static final String RAISINTE_TXYHJ = "2";
	
	/*------------------------------------------------------------------------*/
	
	/**
	 * 普通取现
	 */
	public static final String CHANCHL_GENERAL = "GENERAL";
	
	/**
	 * 即时取现
	 */
	public static final String CHANCHL_FAST = "IMMEDIATE";
	
	/*------------------------------------------------------------------------*/
	
	/**
	 * 定额
	 */
	public static final String FEE_KIND_1 = "1";
	
	/**
	 * 定比
	 */
	public static final String FEE_KIND_2 = "2";
	
	/*------------------------------------------------------------------------*/
	
	/**
	 * 接口返回标示
	 */
	public static final String RESPCMDID = "cmdId";
	
	/**
	 * 接口返回值编码
	 */
	public static final String RESPCODE = "respCode";
	
	/**
	 * 接口返回值描述
	 */
	public static final String RESPDESC = "respDesc";
	
	/**
	 * 接口所属平台
	 */
	public static final String PAYCHANNEL = "payChannel";
	
	/**
	 * 接口操作类型
	 */
	public static final String PAYOPTION = "payOption";
	
	/*------------------------------------------------------------------------*/
	
	// 00-汇付天下身份证代码，目前仅支持身份证，如果以后支持其它再使用后端-汇付天下映射表
	public static final String ID_TYPE = "00";
	
	
	/*------------------------------------------------------------------------*/
	
	public static final String LOAN_COUNT = "loan_count_";
	
	/*------------------------------------------------------------------------*/
	
	/**
	 * 汇付天下异步 返回 信息前缀
	 */
	public static final String CHINAPNR_NOTIFY_PREFIX = "RECV_ORD_ID_";
}
