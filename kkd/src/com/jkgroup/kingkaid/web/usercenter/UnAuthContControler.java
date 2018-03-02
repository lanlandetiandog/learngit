package com.jkgroup.kingkaid.web.usercenter;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 *	
 *	@Title: ActivityControler.java
 *	@Description: 合同
 *	@Company : 北明软件有限公司
 *	@author zhengchengzhou@bmsoft.com.cn
 *	@version : 1.0, 2015-8-5 下午12:24:06
 */

@Controller
@RequestMapping(value=Constants.UN_AUTH+"/cont")
public class UnAuthContControler {

	/**
	 * 
	 * @param model
	 * @param risk_type
	 * @param cont_type
	 * @param proxy_type
	 * @param loanid
	 * @return
	 */
	@RequestMapping(value="loancontinfo.html")
	public String contPage(Model model,String risk_type,String cont_type,String proxy_type,String loanid){
		FormData formData = FormDataUtil.createInputForm("ContInfoConfirm");
		FormDataUtil.setProperty(formData,"risk_type", risk_type);
		FormDataUtil.setProperty(formData,"cont_type", cont_type);
		FormDataUtil.setProperty(formData,"proxy_type", proxy_type);
		FormDataUtil.setProperty(formData,"loanid", loanid);
		FormDataUtil.setProperty(formData,"custid", Utils.getCurrentUser().getCustId());
		FormData outData = ServiceClient.getResponseFormData(formData, "ContInfoConfirm");
		String respcode = String.valueOf(FormDataUtil.getProperty(outData, "respcode"));
		if(outData != null && respcode.equals(FormData.SUCCESS)){
			String riskinfo = String.valueOf(FormDataUtil.getProperty(outData, "riskinfo"));
			String loancontinfo = String.valueOf(FormDataUtil.getProperty(outData, "loancontinfo"));
			String proxycontinfo = String.valueOf(FormDataUtil.getProperty(outData, "proxycontinfo"));
			if(StringUtils.isNotBlank(riskinfo)){
				riskinfo = riskinfo.replaceAll("\\{\\d\\}", "");
				model.addAttribute("riskinfo", riskinfo);				
			}else{
				model.addAttribute("riskinfo", "");
			}
			if(StringUtils.isNotBlank(loancontinfo)){
				loancontinfo = loancontinfo.replaceAll("\\{\\d\\}", "");
				model.addAttribute("loancontinfo", loancontinfo);				
			}else{
				model.addAttribute("loancontinfo", "");
			}
			if(StringUtils.isNotBlank(proxycontinfo)){
				proxycontinfo = proxycontinfo.replaceAll("\\{\\d\\}", "");
				model.addAttribute("proxycontinfo", proxycontinfo);				
			}else{
				model.addAttribute("proxycontinfo", "");
			}
		}else{
			model.addAttribute("riskinfo", "");
			model.addAttribute("loancontinfo", "");
			model.addAttribute("proxycontinfo", "");
		}
		return "project/projectprotocol";
	}
}
