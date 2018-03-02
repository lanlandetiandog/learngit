package com.jkgroup.kingkaid.web.usercenter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.CommonServiceClient;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 *
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年6月4日 上午11:32:26
 */
@Controller
@RequestMapping(value=Constants.AUTH+"/usercenter")
public class VipController {

	@RequestMapping("myvip.html")
	public String toMyVip(Model model) {
		String memberId = Utils.getCurrentUser().getMemberId();
		FormData fd = FormDataUtil.createInputForm("VIPSummary");
		FormDataUtil.setProperty(fd, "memberid", memberId);
		FormData outData = ServiceClient.getResponseFormData(fd, "VIPSummary");
//		if(FormDataUtil.getProperty(outData, "respcode").equals("S")) {
//			model.addAttribute("vipInfo", outData);
//		} else {
//			FormDataUtil.setProperty(outData, "vipstate", "0");
//			model.addAttribute("vipInfo", outData);
//		}
		model.addAttribute("vipInfo", outData);
		return "usercenter/myvip";
	}
	
	/**
	 * 加入VIP
	 * @param model
	 * @return
	 */
	@RequestMapping(value="join_vip.html")
	public String joinVip(Model model){
		String memberId = Utils.getCurrentUser().getMemberId();
		
		//vip状态查询
		FormData fd = FormDataUtil.createInputForm("VIPSummary");
		FormDataUtil.setProperty(fd, "memberid", memberId);
		FormData outData = ServiceClient.getResponseFormData(fd, "VIPSummary");
		
		//余额查询
		FormData acctInfo = CommonServiceClient.getBalance();
		model.addAttribute("acctInfo", acctInfo);
		model.addAttribute("vipInfo", outData);
		return "usercenter/joinvip";
	}
}
