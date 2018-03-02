package com.jkgroup.kingkaid.web.xabank;


import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.MessageWrapper;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.CommonServiceClient;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.XaBankConstant;

/**
 * 处理西安银行接口
 * @author pan
 *
 */
@Controller
@RequestMapping(value=Constants.AUTH+"/xabank")
public class XaBankController {

	/**
	 * 获取当前交易的随机因子与当前交易号
	 * @return
	 */
	@RequestMapping(value="/getrk")
	@ResponseBody
	public String getRandomKey() {
		User user = Utils.getCurrentUser();
		FormData outData = CommonServiceClient.getRandomKey(user.getMemberId());
		if (FormDataUtil.isSucceed(outData)) {
			Map<String, String> map = new HashMap<String, String>();
			Session session = SecurityUtils.getSubject().getSession();
			session.setAttribute(XaBankConstant.CURR_TRAN_SEQ, (String) FormDataUtil.getProperty(outData, "transeq"));
			map.put("kx", (String) FormDataUtil.getProperty(outData, "kx"));
			map.put("ky", (String) FormDataUtil.getProperty(outData, "ky"));
			map.put("rcode", (String) FormDataUtil.getProperty(outData, "rcode"));
			MessageWrapper wrapper = new MessageWrapper(true);
			wrapper.setBody(map);
			return ServiceClient.parseObjToJson(wrapper);
		} else {
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData));
		}
	}
}
