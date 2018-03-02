package com.jkgroup.kingkaid.web.pay;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibm.icu.text.SimpleDateFormat;
import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.CommonServiceClient;
import com.jkgroup.kingkaid.service.pay.PayServiceClient;
import com.jkgroup.kingkaid.utils.MathUtil;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.PayConstant;
import com.jkgroup.kingkaid.utils.pay.alipay.util.AlipaySubmit;
import com.jkgroup.kingkaid.utils.pay.param.AlipayParam;

/**
 * 支付宝充值跳转入口
 * @author pan
 *
 */
@Controller
@RequestMapping(value=Constants.AUTH+"/pay/alipay")
public class AlipayController {

	private static final String FORWARD = "pay/forward";
	
	/**
	 * 支付宝充值
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="deposit.html")
	public String deposit(HttpServletRequest request,Model model){
		User user = Utils.getCurrentUser();
		if(user == null){
			
		}
		//防钓鱼时间戳
		String anti_phishing_key = "";
		try {
			anti_phishing_key = AlipaySubmit.query_timestamp();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//若要使用请调用类文件submit中的query_timestamp函数

		//客户端的IP地址
		String exter_invoke_ip = "";
		//非局域网的外网IP地址，如：221.0.0.1
		double balance = Double.parseDouble(request.getParameter("amount"));
		
		
		try {
			balance = Double.parseDouble(request.getParameter("amount"));
		} catch (NumberFormatException e) {
			//金额判断
		}
		
		if(balance<=0){
			//金额不能小于0
		}
		
		double payfee = MathUtil.round(MathUtil.mul(balance,0.002),2);
		
		AlipayParam pay = CommonServiceClient.getAliPayParams();
		/*
		 * 新增充值单据
		 */
		Map<String,String> param = new HashMap<String, String>();
		param.put("balance", MathUtil.numFmt(balance));
		param.put("usrCustId", user.getCustAcNo());
		param.put("chanid", PayConstant.CHAN_ALIPAY);
		param.put("custid", user.getCustId());
		param.put("membername", user.getMemberName());
		if(payfee > 0){
			param.put("payfee", String.valueOf(payfee));
		}
		FormData deposit = PayServiceClient.depositInsert(param);
		
		/**
		 * 生成充值单据信息 渠道为 2：支付宝
		 */
		String out_trade_no = String.valueOf(FormDataUtil.getProperty(deposit, "otherlistno"));
		
		//////////////////////////////////////////////////////////////////////////////////
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String,String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", pay.getAlipay_partner());
        sParaTemp.put("_input_charset", pay.getAlipay_chartset());
		sParaTemp.put("payment_type", "1");
		sParaTemp.put("paymethod", "expressGatewayDebit");
		sParaTemp.put("notify_url", pay.getAlipay_notify_url());
		sParaTemp.put("seller_email", pay.getAlipay_sellmail());
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", "金开贷充值");
		sParaTemp.put("total_fee", MathUtil.numFmt(balance));
		//传递单据号
		sParaTemp.put("extra_common_param", "");
		sParaTemp.put("anti_phishing_key", anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
		sParaTemp.put("return_url", pay.getAlipay_ret_url());
		//sParaTemp.put("body", body);
		//sParaTemp.put("show_url", show_url);
		//directPay cartoon bankPay cash 
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
		model.addAttribute("html", sHtmlText);
		return FORWARD;
	}
}
