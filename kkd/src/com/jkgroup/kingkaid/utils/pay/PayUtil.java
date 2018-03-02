package com.jkgroup.kingkaid.utils.pay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.ui.Model;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.CommonServiceClient;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.service.chinapnr.command.CommandAutoTenderPlan;
import com.jkgroup.kingkaid.service.chinapnr.command.CommandAutoTenderPlanClose;
import com.jkgroup.kingkaid.service.chinapnr.command.CommandBandCard;
import com.jkgroup.kingkaid.service.chinapnr.command.CommandCash;
import com.jkgroup.kingkaid.service.chinapnr.command.CommandCorpRegister;
import com.jkgroup.kingkaid.service.chinapnr.command.CommandCreditAssign;
import com.jkgroup.kingkaid.service.chinapnr.command.CommandFssTrans;
import com.jkgroup.kingkaid.service.chinapnr.command.CommandNetSave;
import com.jkgroup.kingkaid.service.chinapnr.command.CommandTender;
import com.jkgroup.kingkaid.service.chinapnr.command.CommandUnBindCard;
import com.jkgroup.kingkaid.service.chinapnr.command.CommandUserRegister;
import com.jkgroup.kingkaid.service.chinapnr.command.CommandVIPFee;
import com.jkgroup.kingkaid.service.chinapnr.command.HFCommand;
import com.jkgroup.kingkaid.service.xabank.command.CommandCtpAccountOpenSynz;
import com.jkgroup.kingkaid.service.xabank.command.CommandCtpAcctNotifyCheckSynz;
import com.jkgroup.kingkaid.service.xabank.command.CommandCtpAcctNotifyGetNoSynz;
import com.jkgroup.kingkaid.service.xabank.command.CommandCtpAuditResultSynz;
import com.jkgroup.kingkaid.service.xabank.command.CommandCtpEleAccountActiveSynz;
import com.jkgroup.kingkaid.service.xabank.command.CommandCtpMobileNoReset;
import com.jkgroup.kingkaid.service.xabank.command.CommandCtpP2pAddResultSynz;
import com.jkgroup.kingkaid.service.xabank.command.CommandCtpP2pAutoSBidSignResultSynz;
import com.jkgroup.kingkaid.service.xabank.command.CommandCtpP2pManualBidResultSynz;
import com.jkgroup.kingkaid.service.xabank.command.CommandCtpP2pProjectAssiSynz;
import com.jkgroup.kingkaid.service.xabank.command.CommandCtpPayMent;
import com.jkgroup.kingkaid.service.xabank.command.CommandCtpTransOutSynz;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.param.ChinaPnrPayParam;

/**
 * 第三方支付辅助接口
 * @author pan
 *
 */
public class PayUtil {
	
	private static final String RETU_PAGE = "pay/payretu";
	private static final String MOBIL_RETU_PAGE = "mobile/payretu";
	private static final Logger logger = LoggerFactory.getLogger(PayUtil.class);
	
	public final static Map<String, HFCommand> cmdReturnMap = new HashMap<String, HFCommand>() {
		private static final long serialVersionUID = -8713715580938336895L;
		{
			put("UserRegister", new CommandUserRegister());
			put("NetSave", new CommandNetSave());
			put("Cash", new CommandCash());
			put("UsrAcctPay", new CommandVIPFee());
			put("FssTrans", new CommandFssTrans());
			put("InitiativeTender", new CommandTender());
			put("CreditAssign",new CommandCreditAssign());
			put("AutoTenderPlan",new CommandAutoTenderPlan());
			put("AutoTenderPlanClose",new CommandAutoTenderPlanClose());
		}
	};
	
	public final static Map<String, HFCommand> cmdNotifyMap = new HashMap<String, HFCommand>() {
		private static final long serialVersionUID = -8713715580938336895L;
		{
			put("UserRegister", new CommandUserRegister());
			put("CorpRegister", new CommandCorpRegister());
			put("NetSave", new CommandNetSave());
			put("Cash", new CommandCash());
			put("UsrAcctPay", new CommandVIPFee());
			put("FssTrans", new CommandFssTrans());
			put("CreditAssign",new CommandCreditAssign());
			put("UserBindCard", new CommandBandCard());
			put("PnrUsrUnBindExpressCard", new CommandUnBindCard());
			put("InitiativeTender", new CommandTender());
		}
	};
	
	public final static Map<String, HFCommand> cmdXabankNotifyMap = new HashMap<String, HFCommand>() {
		private static final long serialVersionUID = -8713715580938336895L;
		{
//			put("ctpAccountOpenSynz", new CommandCtpAccountOpenSynz());
//			put("ctpPerAccountOpenSynz", new CommandCtpAccountOpenSynz());
			put("ctpEleAccountActiveSynz", new CommandCtpEleAccountActiveSynz());
			//提现通知
			put("ctpTransOutSynz", new CommandCtpTransOutSynz());
			//项目新增通知接口
			put("ctpP2pAddResultSynz", new CommandCtpP2pAddResultSynz());
			//手动投标通知接口
			put("ctpP2pManualBidResultSynz", new CommandCtpP2pManualBidResultSynz());
			//债权转让通知接口
			put("ctpP2pProjectAssiSynz", new CommandCtpP2pProjectAssiSynz());
			//自动投标开启通知接口
			put("ctpP2pAutoSBidSignResultSynz", new CommandCtpP2pAutoSBidSignResultSynz());
			//购买VIP通知接口
			put("ctpPayMent", new CommandCtpPayMent());
			// 企业开户审核结果异步通知接口
			put("ctpAuditResultSynz", new CommandCtpAuditResultSynz());
			// 企业开户成功异步通知接口
			put("ctpAccountOpenSynz", new CommandCtpAccountOpenSynz());
			put("ctpPayMentSynz",new CommandCtpPayMent());
			//获取入账流水号
			put("ctpAcctNotifyGetNoSynz", new CommandCtpAcctNotifyGetNoSynz());
			//账务通知入账
			put("ctpAcctNotifyCheckSynz", new CommandCtpAcctNotifyCheckSynz());
			// 重置手机号后的通知
			put("ctpMobileNoReset",new CommandCtpMobileNoReset());
		}
	};
	
	public final static Map<String, String> returnTranTypes = new HashMap<String, String>() {
		private static final long serialVersionUID = -8713715580938336895L;
		{
			//提现通知
			put("ctpTransOutSynz", "TX");
			//项目新增通知接口
			put("ctpP2pAddResultSynz", "LB");
			//手动投标通知接口
			put("ctpP2pManualBidResultSynz", "TB");
			//债权转让通知接口
			put("ctpP2pProjectAssiSynz", "ZR");
			//自动投标开启通知接口
			put("ctpP2pAutoSBidSignResultSynz", "ZDTBQJY");
			// 缴费
			put("ctpPayMent", "JF");
			put("ctpPayMentSynz","JF");
			//企业开户
			put("ctpAccountOpenSynz","QYKH");
			//修改手机号码
			put("ctpMobileNoReset","CHMB");
		}
	};
	
	/**
	 * 转换第三支付参数
	 * @param requestParams
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String,String> tranToMap(Map requestParams) {
		Map<String,String> params = new HashMap<String,String>();
		try {
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
					                                                        : valueStr + values[i] + ",";
				}
				params.put(name, new String(URLCodec.decodeUrl(valueStr.getBytes("UTF-8")), "UTF-8"));
				logger.info(" Resp : {}\t{}", name, new String(URLCodec.decodeUrl(valueStr.getBytes("UTF-8")), "UTF-8"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		return params;
	}
	
	/**
	 * 构造签名字符串
	 * @param ParaMap
	 * @return
	 */
	public static String chkValue(Map<String,String> ParaMap){
		StringBuffer buffer = new StringBuffer();
		for (Entry<String, String> entry : ParaMap.entrySet()) {
			if(!entry.getKey().equals("CharSet")){
				String value = StringUtils.trimToEmpty(entry.getValue());
				buffer.append(value);
			}
		}
		return buffer.toString();
	}
	
    /**
     * 建立请求，以表单HTML形式构造（默认）
     * @param sPara 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     */
    public static String buildRequest(Map<String, String> sPara) {
    	ChinaPnrPayParam param = CommonServiceClient.getPayParams();
    	String url = param.getPay_uri();
    	//待请求参数数组
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuilder sbHtml = new StringBuilder();

        sbHtml.append("<form id=\"chinapnrsubmit\" name=\"chinapnrsubmit\" action=\""+url+"\" method=\"POST\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = keys.get(i);
            String value = sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value='" + value + "'/>");
        }

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"submit\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['chinapnrsubmit'].submit();</script>");
        return sbHtml.toString();
    }
    
    /**
     * 建立请求，以表单HTML形式构造（默认）
     * @param sPara 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     */
    public static String buildRequest(Map<String, String> sPara,String uri) {
    	//待请求参数数组
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuilder sbHtml = new StringBuilder();

        sbHtml.append("<form id=\"chinapnrsubmit\" name=\"chinapnrsubmit\" action=\""+uri+"\" method=\"POST\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = keys.get(i);
            String value = sPara.get(name);
            logger.debug(" name : {}  value : {}",name,value);
            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value='" + value + "'/>");
        }

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"submit\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['chinapnrsubmit'].submit();</script>");
        return sbHtml.toString();
    }
    
    /**
     * 对报文做加签处理
     * @param field[] 输入报文
     * @return
     */
    public static String chkValue(String[] field){
    	FormData formData = ServiceClient.getResponseFormData(field, null);
    	String chkValue = String.valueOf(FormDataUtil.getProperty(formData, "chkvalue"));
    	return chkValue;
    }
    
    /**
     * 对报文做验签处理
     * @param field[] 输入报文
     * @return
     */
    public static boolean sign(String[] field,String checkVal){
    	FormData formData = ServiceClient.getResponseFormData(field, null);
    	String chkValue = String.valueOf(FormDataUtil.getProperty(formData, "chkvalue"));
    	return checkVal.equals(chkValue);
    }
    
    /**
     * 返回支付结果页面
     * @param respCode
     * @param respDesc
     * @param payChannel
     * @param payOption
     * @param sitePreference
     * @param model
     * @return
     */
    private static String payReturn(String respCode, String respDesc, PayChannel payChannel, PayOption payOption, SitePreference sitePreference, Model model) {
    	model.addAttribute(PayConstant.RESPCODE, respCode);
    	model.addAttribute(PayConstant.RESPDESC, respDesc);
    	model.addAttribute(PayConstant.PAYCHANNEL, payChannel);
    	model.addAttribute(PayConstant.PAYOPTION, payOption);
    	String page = sitePreference.isNormal() ? RETU_PAGE : MOBIL_RETU_PAGE;
    	return page;
    }
    
    /**
     * 返回支付成功结果页面
     * @param cmdId
     * @param respCode
     * @param respDesc
     * @return
     */
    public static String paySuccess(String respDesc, PayChannel payChannel, PayOption payOption, SitePreference sitePreference, Model model) {
    	return payReturn(FormData.SUCCESS, respDesc, payChannel, payOption, sitePreference, model);
    }
    
    /**
     * 返回支付成功结果页面
     * @param cmdId
     * @param respCode
     * @param respDesc
     * @return
     */
    public static String payError(String respDesc, PayChannel payChannel, PayOption payOption, SitePreference sitePreference, Model model){
    	return payReturn(FormData.FAILURE, respDesc, payChannel, payOption, sitePreference, model);
    }
    
    
    /**
     * 读取request流
     * @param req
     * @return
     * @author guoyx
     */
    public static String readReqStr(HttpServletRequest request)
    {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try
        {
            reader = new BufferedReader(new InputStreamReader(request
                    .getInputStream(), "utf-8"));
            String line;

            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (null != reader)
                {
                    reader.close();
                }
            } catch (IOException e)
            {

            }
        }
        return sb.toString();
    }
    
    /**
     * 汇付天下 异步 打印结果
     * @param map
     * @return
     */
    public static String getNotifyRetu(Map<String,String> map){
    	String cmdId = map.get("CmdId");
    	String ret = PayConstant.CHINAPNR_NOTIFY_PREFIX;
    	if(StringUtils.isBlank(cmdId)){
    		return PayConstant.CHINAPNR_NOTIFY_PREFIX;
    	}else{
    		if(cmdId.equals("UserRegister") || cmdId.equals("NetSave") || cmdId.equals("CorpRegister")){
    			ret += map.get("TrxId");
    		}else if(cmdId.equals("BatchRepayment")){
    			ret += map.get("BatchId");
    		}else{
    			ret += map.get("OrdId");
    		}
    	}
    	
    	logger.debug(" pay notify return message : {}",ret);
    	
    	return ret;
    }
    
    /**
     * 三方支付操作类型
     */
    public enum PayOption {
    	USERREGISTER("会员实名认证", "PA"),
    	CORPREGISTER("会员实名认证", "CA"),
    	NETSAVE("充值", "10", new String[] {"您的充值金额将在30分钟内到账", "如果超过30分钟仍未成功", "请拨打客服电话400-1888-136", "您可以进入我的金开贷查看充值信息"}),
    	CASH("提现", "11", new String[] {"您的汇付天下账户余额已清空，汇付天下账户","将自动注销。请您使用银行存管电子账户继续","金开贷理财。感谢您的支持！"}),
    	USRACCTPAY("VIP购买", "14", new String[] {"恭喜您成为金开贷VIP用户，我们将竭诚为您服务"}),
    	FSSTRANS("生利宝操作", "43"),
    	USERBINDCARD("绑定银行卡", "BC"),
    	INITIATIVETENDER("投标", "40", new String[] {"您的投标请求已经成功受理"}),
    	CREDITASSIGN("债权转让", "24", new String[] {"您已经成功购买债权转让"}),
    	AUTOTENDERPLAN("自动投标开启", "TO", new String[] {"恭喜您的自动投标功能开启成功", "请享受您的无忧理财之旅吧！"}),
    	AUTOTENDERPLANCLOSE("自动投标关闭", "TC", new String[] {"你的自动投标功能已经关闭"}),
    	USERLOGIN("第三方账户登录", "UL"),
    	UNSUPPORTED("不支持的操作", "NO"),
    	//----------------------------------------------
    	USRUNFREEZE("资金解冻", "UF"),
    	LOANS("自动扣款(放款)", "LO"),
    	AUTOTENDER("自动投标", "AT"),
    	BATCHREPAYMENT("批量还款", "BR");
    	
    	private String desc;
    	// 与后台option中的listtype单据类型相对应
    	private String listType;
    	private String[] suggs;
    	
		public String getDesc() {
			return desc;
		}

		public String[] getSuggs() {
			return suggs;
		}

		public String getListType() {
			return listType;
		}

		PayOption(String desc, String listType) {
			this.desc = desc;
			this.listType = listType;
		}

		PayOption(String desc, String listType, String[] suggs) {
			this.desc = desc;
			this.listType = listType;
			this.suggs = suggs;
		}
    	
		/**
		 * 根据CmdId解析操作类型
		 * @param cmdId
		 * @return
		 */
		public static PayOption parsePayOption(String cmdId) {
			try {
				return valueOf(cmdId.toUpperCase());
			} catch(Exception e) {
				return  UNSUPPORTED;
			}
		}
		
    }
    
    /**
     * 三方支付渠道
     */
    public enum PayChannel {
    	
    	CHINAPNR(PayConstant.CHAN_CHINAPNR), ALIPAY(PayConstant.CHAN_ALIPAY), LIANLIAN(PayConstant.CHAN_LIANLIANPAY);    	

    	PayChannel(String seqNo) {
    		this.seqNo = seqNo;
    	}
    	
    	// 和后台对应的代码
    	private String seqNo;

		public String getSeqNo() {
			return seqNo;
		}
    	
    }
    
    public static Map<String,String> parseJsonToMap(String json){
    	Map<String,String> map = new HashMap<String,String>();
		try {
			JSONObject dataJson = new JSONObject(json);
			if(dataJson != null){
				for(Iterator it = dataJson.keys(); it.hasNext() ;){
					String key = String.valueOf(it.next());
					String value = StringUtils.trimToEmpty(dataJson.getString(key));
					map.put(key, value);
				}
			}
		} catch (JSONException e) {
			logger.debug(" JSONException..." +
					" ",e.getMessage());
		}
		return map;
    }
    
    public static String outMessage(Map<String,String> map){
    	StringBuffer sb = new StringBuffer();
    	sb.append("{");
    	sb.append("\"PLAIN\":").append("{");
    	sb.append("\"H_RSP_CODE\":").append("\"0000\",");
    	sb.append("\"H_RSP_MSG\":").append("\"\",");
    	sb.append("\"VERSION\":").append("\""+map.get("VERSION")+"\",");
    	sb.append("\"TRANS_CODE\":").append("\""+map.get("TRANS_CODE")+"\",");
    	sb.append("\"CTP_ID\":").append("\""+map.get("CTP_ID")+"\",");
    	sb.append("\"ENCODE\":").append("\""+map.get("ENCODE")+"\",");
    	sb.append("\"MER_PRI\":").append("\""+map.get("MER_PRI")+"\",");
    	sb.append("\"RES_BODY\":").append("{}");
    	sb.append("},");
    	sb.append("\"SIGN\":").append("\"abc\"");
    	sb.append("}");
		return sb.toString();
    }
    
    public static String outMessage(Map<String,String> map,FormData formData,Map<String,String> retVal){
    	String respCode = "";
    	String respDesc = "";
    	String respBody = "";
    	if(formData != null){
    		respCode = (String) FormDataUtil.getProperty(formData, "respcode");
    		respDesc = (String) FormDataUtil.getProperty(formData, "respdesc");
    		if(respDesc.length()>14)
    			respDesc = respDesc.substring(0, 14);
    	}
    	
    	if(respCode.equals(FormData.SUCCESS)){
    		respCode = "0000";
    		respDesc = "";
    	}
    	
    	if(retVal != null && retVal.size() > 0){
    		for(Entry<String, String> entry : retVal.entrySet()){
    			String value = (String) FormDataUtil.getProperty(formData, entry.getValue());
    			if(StringUtils.isNotBlank(value)){
    				respBody += "\""+entry.getKey()+"\":"+"\""+value+"\",";    				
    			}
    		}
    		if(respBody.endsWith(","))
    			respBody = respBody.substring(0, respBody.length()-1);
    	}
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("{");
    	sb.append("\"PLAIN\":").append("{");
    	sb.append("\"H_RSP_CODE\":").append("\""+respCode+"\",");
    	sb.append("\"H_RSP_MSG\":").append("\""+respDesc+"\",");
    	sb.append("\"VERSION\":").append("\""+map.get("VERSION")+"\",");
    	sb.append("\"TRANS_CODE\":").append("\""+map.get("TRANS_CODE")+"\",");
    	sb.append("\"CTP_ID\":").append("\""+map.get("CTP_ID")+"\",");
    	sb.append("\"ENCODE\":").append("\""+map.get("ENCODE")+"\",");
    	sb.append("\"MER_PRI\":").append("\""+map.get("MER_PRI")+"\",");
    	sb.append("\"RES_BODY\":").append("{"+respBody+"}");
    	sb.append("},");
    	sb.append("\"SIGN\":").append("\"abc\"");
    	sb.append("}");
		return sb.toString();
    }
}
