package com.jkgroup.kingkaid.web.usercenter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.common.PropertiesUtil;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 *
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年6月23日 下午8:46:14
 */

@Controller
@RequestMapping(value=Constants.AUTH+"/usercenter")
public class ShareController {

	@RequestMapping("myshare.html")
	public String toMyShare(Model model) {
		String custId = Utils.getCurrentUser().getCustId();
		FormData fd = FormDataUtil.createInputForm("JCCodeShare");
		FormDataUtil.setProperty(fd, "custid", custId);
		FormData outData = ServiceClient.getResponseFormData(fd, "JCCodeShare");
		model.addAttribute("shareInfo", outData);		
		
		model.addAttribute("url", PropertiesUtil.getSHAREUri()+"/member/tomiddle.html?custId="+custId);
		return "usercenter/myshare";
	}
	
}
