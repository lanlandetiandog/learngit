package com.jkgroup.kingkaid.web.usercenter;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jkgroup.kingkaid.utils.ProjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.MessageWrapper;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.Array;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.AccountServiceClient;
import com.jkgroup.kingkaid.service.CommonServiceClient;
import com.jkgroup.kingkaid.service.MessageHelpService;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.bo.Page;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 *
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年8月1日 下午5:12:49
 */

@Controller
@RequestMapping(value=Constants.AUTH+"/usercenter")
public class WarrantController {
	private static Logger logger =LoggerFactory.getLogger(WarrantController.class);
	private static final String USERCENTER = "usercenter/";
	private static final String CORP = "corp/";
	private final Map<String, String[]> stateMap = new HashMap<String, String[]>() {
		private static final long serialVersionUID = -4820126322138271794L;
		{
			put("all", null);
			put("bidding", new String[]{"15,1X"});
			put("returning", new String[]{"10"});
			put("auditing", new String[]{"01,02,03,04,05,07,08,09,11,12,13,14"});
			put("failed", new String[]{"20,21,22,23,24,25"});
			put("finished", new String[]{"19"});
			put("unfounded", new String[]{"00"});
			put("syspay", new String[]{"30"});
		}
	};
	
	@Autowired
	private MessageHelpService messageHelpService;
	
	@RequestMapping("mywarrant.html")
	public String toMyWarrant(Model model) {
		User user = Utils.getCurrentUser();
		if(!user.isGuarantee()) {
			return "redirect:myjkd.html";
		}
		String custid = user.getCustId();
		FormData inData = FormDataUtil.createInputForm("GuaranteeInfo");
		FormDataUtil.setProperty(inData, "custid", custid);
		FormData outData = ServiceClient.getResponseFormData(inData, "GuaranteeInfo");
		model.addAttribute("guaranteeInfo", outData);
		return USERCENTER + CORP + "mywarrant";
	}
	
	/**
	 * 担保项目列表
	 * @param pageSize
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("warrant/myWarrantList")
	@ResponseBody
	public String myWarrantList(int pageSize, int pageNo, String apprState) throws Exception {
		String[] realState = stateMap.get(apprState);
		User user = Utils.getCurrentUser();
		String custid = user.getCustId();
		Page<FormData> page = Page.buildPageFromRequest(pageSize, pageNo);
		FormData inData = FormDataUtil.createInputForm("MyBorrowList2");
		if(null != realState) {
			Array array = new Array();
			for(String stateCode : realState) {
				array.add(stateCode);
			}
			FormDataUtil.setProperty(inData, "apprstates", array);
		}
		FormDataUtil.setProperty(inData, "dbcustid", custid);

		Page<FormData> outData = ServiceClient.getResponseFormDataPage(inData, "MyBorrowList2", page);

		outData.setResult(ProjectUtil.projectsAddSurplusSecond(outData.getResult()));

		MessageWrapper wrapper = messageHelpService.buildMessageWrapperWithOption(outData, "apprstate");

		return ServiceClient.parseObjToJson(wrapper, new SimpleDateFormat(DateUtils.SDF_DATETIME));
	}
	
	/**
	 * 显示还款计划
	 * @param loanid
	 * @return
	 */
	@RequestMapping("warrant/returnPlan")
	@ResponseBody
	public String returnPlan(String loanid) {
		 List<FormData> list = AccountServiceClient.getCustRetuPlan(loanid);
		 return ServiceClient.parseObjToJson(list, new SimpleDateFormat(DateUtils.SDF_DATETIME));
		 
		 
	}
	
	/**
	 * 设置项目状态--确认担保、确认定标
	 * @param loanid
	 * @param apprstate
	 * @return
	 */
	@RequestMapping("warrant/setApprState")
	@ResponseBody
	public String setApprState(String loanid, String apprstate) {
		//请求时间戳
		FormData tsaRequest = null;
		if("18".equals(apprstate)){//担保公司合同已签订
			try {
				tsaRequest = FormDataUtil.createInputForm("TsaRequest");
				FormDataUtil.setProperty(tsaRequest, "loanid", loanid);
				FormDataUtil.setProperty(tsaRequest, "tsarequesttype", "2");
				FormDataUtil.setProperty(tsaRequest, "custid", Utils.getCurrentUser().getCustId());
				tsaRequest = ServiceClient.getResponseFormData(tsaRequest, "TsaRequest");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		FormData outData = CommonServiceClient.setProjectApprState(loanid, apprstate);
		if(FormDataUtil.isSucceed(outData) && FormDataUtil.isSucceed(tsaRequest)) {
			return ServiceClient.parseObjToJson(new MessageWrapper(true));
		} else {
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData));
		}
	}

	// 数字证书，start 验证
	@RequestMapping(value = "warrant/ChoiceRANext.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String ChoiceRANext(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 获取到操作的签章信息
		String loanid = request.getParameter("loanid");
		String CN = request.getParameter("CertSubjectDN"); // CN=JKD@du3333@Z222354@1
		if (CN.indexOf("=") != -1 && CN.indexOf(",") != -1) {
			CN = CN.substring(CN.indexOf("CN=") + 3, CN.indexOf(","));
			logger.debug("CN", CN);
		}
		// 根据loanid去查询签章要的字段
		FormData fd = FormDataUtil.createInputForm("RASelectDBAcct");
		FormDataUtil.setProperty(fd, "loanid", loanid);
		FormData outData = ServiceClient.getResponseFormData(fd, "RASelectDBAcct");

		String custid = FormDataUtil.getProperty(outData, "custid").toString();
		String flag = FormDataUtil.getProperty(outData, "flag").toString();
		String state = FormDataUtil.getProperty(outData, "state").toString();
		FormDataUtil.setProperty(outData, "flag", flag);
		if (custid.isEmpty()) {
			FormDataUtil.setProperty(outData, "respcode", "N");
		} else {
			String cn_after = FormDataUtil.getProperty(outData, "dn").toString();
			if (cn_after.indexOf("=") != -1 && cn_after.indexOf(",") != -1) {
				cn_after = cn_after.substring(cn_after.indexOf("CN=") + 3, cn_after.indexOf(","));
			}
			logger.debug("DN:【{}】,cn_after:【{}】", CN,cn_after);
			if (CN.equals(cn_after)) {
				FormDataUtil.setProperty(outData, "respcode", "S");
			} else {
				FormDataUtil.setProperty(outData, "respcode", "F");
			}
		}
		String prodid = request.getParameter("prodid");
		FormDataUtil.setProperty(outData, "prodid", prodid);
		FormDataUtil.setProperty(outData, "state", state);
		return ServiceClient.parseObjToJson(outData);

	}

	// 数字证书，end 验证
}
