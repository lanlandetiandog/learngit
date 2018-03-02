package com.jkgroup.kingkaid.web.usercenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cfca.ra.common.vo.response.CertServiceResponseVO;
import cfca.ra.toolkit.exception.RATKException;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.FrontAddr;
import com.jkgroup.kingkaid.bo.MessageWrapper;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.AddressCodeService;
import com.jkgroup.kingkaid.service.CommonServiceClient;
import com.jkgroup.kingkaid.service.OptionService;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.CipherUtil;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.FilterExt;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.ra.inter.Inter2101Other;
import com.ra.inter.Inter2401;
import com.ra.inter.Inter2702;
import com.ra.inter.Inter2703;

/**
 * 安全中心相关请求处理类
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年6月9日 上午10:17:08
 */

@Controller
@RequestMapping(value=Constants.AUTH+"/usercenter")
public class SafeCenterController {
	
	private static final String USERCENTER = "usercenter/";
	private static final String CORP = "corp/";
	
	@Autowired
	private AddressCodeService codeLibraryService;
	@Autowired
	private OptionService optionService;
	
	@RequestMapping("safetycenter.html")
	public String toSafeCenter(Model model) {
		//duxt start for ra
		//调用后台交易获取PDF签章控件下载地址
		FormData formData1 = FormDataUtil.createInputForm("GetControlDownLoadURL");
		FormDataUtil.setProperty(formData1,"ctltype", "2"); //下载控件类型，1：合同PDF签章控件
		FormData outData1 = ServiceClient.getResponseFormData(formData1, "GetControlDownLoadURL");
		if(FormDataUtil.isSucceed(outData1)){
			String ctlurl = (String)FormDataUtil.getProperty(outData1, "ctlurl");
			model.addAttribute("ctlurl", ctlurl);
		}
		//duxt start for ra
		User user = Utils.getCurrentUser();
		String urlPart = StringUtils.EMPTY;
		if(user.isCorp()) {
			organizeCorpSCData(model, user.getMemberId(), user);
			urlPart = CORP;
		} else {
			organizePersSCData(model, user.getMemberId(), user);
		}
		
		FormData fd = FormDataUtil.createInputForm("MemberPhoneSelect");
		FormDataUtil.setProperty(fd, "memberid", user.getMemberId());
		FormData ctp_mobile = ServiceClient.getResponseFormData(fd, "MemberPhoneSelect");
		String mobilenumber = (String) FormDataUtil.getProperty(ctp_mobile, "ctp_mobile");
		String playmoneyflag = (String) FormDataUtil.getProperty(ctp_mobile, "playmoneyflag");
		model.addAttribute("mobilenumber", mobilenumber);
		model.addAttribute("playmoneyflag", playmoneyflag);
		model.addAttribute("user", user);
		
		if("68".indexOf(user.getMemberState()) > 0){
			
			String interfaceName = user.isCorp() ? "OpenPayCorpSelect" : "OpenPaySelect";
			FormData data = FormDataUtil.createInputForm(interfaceName);
			FormDataUtil.setProperty(data, "memberid", user.getMemberId());
			data = ServiceClient.getResponseFormData(data, interfaceName);
			String regamt = String.valueOf(FormDataUtil.getProperty(data, "regamt"));
			String bankcodesub = String.valueOf(FormDataUtil.getProperty(data, "bankcode"));
			model.addAttribute("bankcodesub", bankcodesub.substring(bankcodesub.length()-4, bankcodesub.length()));
			model.addAttribute("regamt", regamt);
			model.addAttribute("custacnosub", user.getCustAcNo().substring(user.getCustAcNo().length()-4, user.getCustAcNo().length()));
			model.addAttribute("formData", data);
		}

		// 最后一次换卡申请记录
		FormData changeCardRecord = FormDataUtil.createInputForm("ChangeCardRecord");
		FormDataUtil.setProperty(changeCardRecord, "memberid", user.getMemberId());
		changeCardRecord = ServiceClient.getResponseFormData(changeCardRecord, "ChangeCardRecord");
		model.addAttribute("changeCardRecord", changeCardRecord);
		
		FormData fds = FormDataUtil.createInputForm("BankCardList");
		FormDataUtil.setProperty(fds, "memberid", user.getMemberId());
		List<FormData> bankcards = ServiceClient.getResponseFormDataList(fds, "BankCardList");
		
		
		if(bankcards.size()>0){
			String bankcode = (String) FormDataUtil.getProperty(bankcards.get(0), "bankacno");
			String bankname = (String) FormDataUtil.getProperty(bankcards.get(0), "bankname");
			model.addAttribute("bankcode", bankcode);
			model.addAttribute("bankname", bankname);
		}
		
		
		return USERCENTER + urlPart + "safetycenter";
	}

	// 组织企业会员安全中心数据
	private void organizeCorpSCData(Model model, String memberId, User user) {
		FormData fd = FormDataUtil.createInputForm("CorpSafeCenterInfo");
		FormDataUtil.setProperty(fd, "memberid", memberId);
		FormData outData = ServiceClient.getResponseFormData(fd, "CorpSafeCenterInfo");
		if(FormDataUtil.isSucceed(outData)) {
			// 为了变更手机号和Email使用，因为前台保存和显示的是部分值
			// 同时也为了bindDestination，就是初次绑定手机作为验证，防止有人恶意操作
			Session session = SecurityUtils.getSubject().getSession();
			session.setAttribute("currMobile", FormDataUtil.getProperty(outData, "mobilenumber"));
			session.setAttribute("currEmail", FormDataUtil.getProperty(outData, "email"));
			session.setAttribute("membername", FormDataUtil.getProperty(outData, "membername"));
			session.setAttribute("nameupdate", FormDataUtil.getProperty(outData, "nameupdate"));
			// 公司地址
			String addr = (String) FormDataUtil.getProperty(outData, "addr");
			FrontAddr fAddr = codeLibraryService.buildFrontAddr(addr);
			model.addAttribute("fAddr", fAddr);
			// 各种option
			model.addAttribute("orgakind", optionService.getOptions("orgakind"));
			model.addAttribute("waykind", optionService.getOptions("waykind"));
			model.addAttribute("corpsizesign", optionService.getOptions("corpsizesign"));
			// 更新内存中的authflag状态
            user.setAuthFlag((String) FormDataUtil.getProperty(outData, "authflag"));
		}
		SimpleDateFormat fmt1 = new SimpleDateFormat("yyyy-MM-dd");
		if("".equals(FormDataUtil.getProperty(outData, "comedate")) || null == FormDataUtil.getProperty(outData, "comedate")){
			model.addAttribute("comedate","");
		}else{
			model.addAttribute("comedate",fmt1.format(FormDataUtil.getProperty(outData, "comedate")));
		}
		
		//证书信息-是否是借款人
		FormData fds = FormDataUtil.createInputForm("MyKingkaid");
		FormDataUtil.setProperty(fds, "memberid", memberId);
		FormData rafordend = ServiceClient.getResponseFormData(fds, "MyKingkaid");
		model.addAttribute("rafordend", rafordend);
		//证书信息-是否有证书信息
		FormData ford = FormDataUtil.createInputForm("RASelAcct");
		FormDataUtil.setProperty(ford, "custid", memberId);
		FormData fordend = ServiceClient.getResponseFormData(ford, "RASelAcct"); 
		model.addAttribute("fordend", fordend);
		model.addAttribute("formdata", outData);
	}

	// 组织个人会员安全中心数据
	private void organizePersSCData(Model model, String memberId, User user) {
		FormData fd = FormDataUtil.createInputForm("SafeCenterInfo");
		FormDataUtil.setProperty(fd, "memberid", memberId);
		FormData outData = ServiceClient.getResponseFormData(fd, "SafeCenterInfo");
		if(FormDataUtil.isSucceed(outData)) {
			// 为了变更手机号和Email使用，因为前台保存和显示的是部分值
			// 同时也为了bindDestination，就是初次绑定手机作为验证，防止有人恶意操作
			Session session = SecurityUtils.getSubject().getSession();
			session.setAttribute("currMobile", FormDataUtil.getProperty(outData, "mobilenumber"));
			session.setAttribute("currEmail", FormDataUtil.getProperty(outData, "email"));
			session.setAttribute("membername", FormDataUtil.getProperty(outData, "membername"));
			session.setAttribute("nameupdate", FormDataUtil.getProperty(outData, "nameupdate"));
			// 联系地址
			String contactaddr = (String) FormDataUtil.getProperty(outData, "contactaddr");
			FrontAddr contactFAddr = codeLibraryService.buildFrontAddr(contactaddr);
			model.addAttribute("contactFAddr", contactFAddr);
			// 居住地址
			String liveaddr = (String) FormDataUtil.getProperty(outData, "addr");
			FrontAddr liveFAddr = codeLibraryService.buildFrontAddr(liveaddr);
			model.addAttribute("liveFAddr", liveFAddr);
			// 公司地址
			String corpaddr = (String) FormDataUtil.getProperty(outData, "corpaddr");
			FrontAddr corpFAddr = codeLibraryService.buildFrontAddr(corpaddr);
			model.addAttribute("corpFAddr", corpFAddr);
			// 各种option
			model.addAttribute("isornotflag", optionService.getOptions("isornotflag"));
			model.addAttribute("educsign", optionService.getOptions("educsign"));
			model.addAttribute("sexsign", optionService.getOptions("sexsign"));		
			model.addAttribute("marrsign", optionService.getOptions("marrsign"));
			model.addAttribute("waykind", optionService.getOptions("waykind"));
			model.addAttribute("workkind", optionService.getOptions("workkind"));
			model.addAttribute("corpcategory", optionService.getOptions("corpcategory"));
			model.addAttribute("corpsizesign", optionService.getOptions("corpsizesign"));
			model.addAttribute("vocasign", optionService.getOptions("vocasign"));
			// 年份列表
			model.addAttribute("years", optionService.getYears());
			System.out.println("authflag:"+FormDataUtil.getProperty(outData, "authflag"));
			// 更新内存中的authflag状态
			user.setAuthFlag((String) FormDataUtil.getProperty(outData, "authflag"));
			
			//证书信息-是否是借款人
			FormData fds = FormDataUtil.createInputForm("MyKingkaid");
			FormDataUtil.setProperty(fds, "memberid", memberId);
			FormData rafordend = ServiceClient.getResponseFormData(fds, "MyKingkaid");
			model.addAttribute("rafordend", rafordend);
			//证书信息-是否有证书信息
			FormData ford = FormDataUtil.createInputForm("RASelAcct");
			FormDataUtil.setProperty(ford, "custid", memberId);
			FormData fordend = ServiceClient.getResponseFormData(ford, "RASelAcct"); 
			model.addAttribute("fordend", fordend);
		}
		model.addAttribute("formdata", outData);
	}
	
	/**
	 * 修改用户名
	 * @param model
	 * @param newName
	 * @param oldPassword
	 * @return
	 */
	@RequestMapping("updateName")
	@ResponseBody
	public String updateName(Model model, String newName, String oldPassword) {
		String memberId = Utils.getCurrentUser().getMemberId();
		String memberName = Utils.getCurrentUser().getMemberName();
		String salt = Utils.getCurrentUser().getSalt();
		MessageWrapper wrapper = null;
		if(StringUtils.isEmpty(memberName) || !memberName.matches("[a-zA-Z]\\w{5,19}")) {
			wrapper = new MessageWrapper(false, "用户名必须是6-20位字母开头,字母或数字组成");
		} else {
			FormData fd = FormDataUtil.createInputForm("UpdateName");
			FormDataUtil.setProperty(fd, "memberid", memberId);
			FormDataUtil.setProperty(fd, "membername", newName);
			FormDataUtil.setProperty(fd, "salt", salt);
			FormDataUtil.setProperty(fd, "oldpassword", oldPassword);
			FormData outData = ServiceClient.getResponseFormData(fd, "UpdateName");
			if(FormDataUtil.isSucceed(outData)) {
				User user = Utils.getCurrentUser();
				user.setMemberName(newName);
				user.setSalt(CipherUtil.getSalt(newName));
				user.setLoginPassword(CipherUtil.generatePassword(oldPassword, newName));
				wrapper = new MessageWrapper(true);
			} else {
				wrapper = FormDataUtil.buildFailedMsgWrapper(outData);
			}
		}
		return ServiceClient.parseObjToJson(wrapper);
	}
	
	@RequestMapping("updatePwd")
	@ResponseBody
	public String updatePassword(String oldPassword, String newPassword) {
		String memberId = Utils.getCurrentUser().getMemberId();
		String memberName = Utils.getCurrentUser().getMemberName();
		String salt = Utils.getCurrentUser().getSalt();
		FormData fd = FormDataUtil.createInputForm("UpdatePassword");
		FormDataUtil.setProperty(fd, "memberid", memberId);
		FormDataUtil.setProperty(fd, "oldpassword", getPassword(salt, memberName, oldPassword));
		FormDataUtil.setProperty(fd, "newpassword", getPassword(salt, memberName, newPassword));
		FormData outData = ServiceClient.getResponseFormData(fd, "UpdatePassword");
		MessageWrapper wrapper = null;
		if(FormDataUtil.isSucceed(outData)) {
			wrapper = new MessageWrapper(true, "修改登录密码成功");
		} else {
			wrapper = FormDataUtil.buildFailedMsgWrapper(outData);
		}
		return ServiceClient.parseObjToJson(wrapper);
	}
	
	private String getPassword(String salt, String memberName, String pwd) {
		if(StringUtils.isEmpty(salt))
			return CipherUtil.generatePassword(pwd, memberName);
		else
			return CipherUtil.encodePassword(pwd, salt);
	}
	
	/**
	 * 发送验证码(该手机/Email必须与某一会员绑定)
	 * @param destination
	 * @param channel
	 * @return
	 */
	@RequestMapping(value="sendmVCode", method=RequestMethod.POST)
	@ResponseBody
	public String sendmVCode(int channel, int option) {
		String destination = null;
		Session session = SecurityUtils.getSubject().getSession();
		if (channel == 0) {
			destination = (String) session.getAttribute("currMobile");
		} else {
			destination = (String) session.getAttribute("currEmail");
		}
		FormData outData = CommonServiceClient.sendmVCode(destination, channel, option);
		if(FormDataUtil.isSucceed(outData)) {
			return ServiceClient.parseObjToJson(new MessageWrapper(true, "发送成功"));
		} else {
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData));
		}
	}
	
	/**
	 * 修改西安银行手机发送验证码(该手机不必存在于会员表中)
	 * @param destination
	 * @param channel
	 * @return
	 */
	@RequestMapping(value="sendmVCodeToCtp", method=RequestMethod.POST)
	@ResponseBody
	public String sendmVCodeToCtp(int channel, int option,String mobile) {

		String result = "";
		
	    FormData outData = CommonServiceClient.sendVCode(mobile, channel, option);
	    if(FormDataUtil.isSucceed(outData)) {
	        result = ServiceClient.parseObjToJson(new MessageWrapper(true, "发送成功"));
	    } else {
	        result = ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData));
	    }
		return result;
	}
	
	/**
	 * 验证码校验 - 验证发给旧手机/Email的验证码 - 修改手机/Email
	 * @param destination
	 * @param verifyCode
	 * @return
	 */
	@RequestMapping(value="verifyCodeForUpdate", method=RequestMethod.POST)
	@ResponseBody
	public String verifyCodeForUpdate(String verifyCode,String mobile) {
		
		FormData outData = CommonServiceClient.checkVcode(mobile, verifyCode);
		if(FormDataUtil.isSucceed(outData)) {
			return ServiceClient.parseObjToJson(new MessageWrapper(true));
		} else {
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData));
		}
	}
	
	/**
	 * 验证码校验 - 验证发给旧手机/Email的验证码 - 修改手机/Email
	 * @param destination
	 * @param verifyCode
	 * @return
	 */
	@RequestMapping(value="verifyForUpdate", method=RequestMethod.POST)
	@ResponseBody
	public String verifyForUpdate(int channel, String verifyCode) {
		Session session = SecurityUtils.getSubject().getSession();
		String destination = channel == 0 ? (String) session.getAttribute("currMobile") 
				: (String) session.getAttribute("currEmail");
		FormData outData = CommonServiceClient.checkVcode(destination, verifyCode);
		if(FormDataUtil.isSucceed(outData)) {
			session.setAttribute("verifyContactWay", channel + "");
			return ServiceClient.parseObjToJson(new MessageWrapper(true));
		} else {
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData));
		}
	}
	
	/**
	 * 修改手机/邮箱
	 * @return
	 */
	@RequestMapping(value="updateContactWay", method=RequestMethod.POST)
	@ResponseBody
	public String updateContactWay(int channel, String verifyCode, String destination) {
		Session session = SecurityUtils.getSubject().getSession();
		String schannel = (String) session.getAttribute("verifyContactWay");
		// 检查页面进入是否合法--即是否验证过旧手机/Email
		if(!schannel.endsWith(""+channel)) {
			return ServiceClient.parseObjToJson(new MessageWrapper(false, "非法操作！"));
		}
		// 更新新手机/Email
		String memberId = Utils.getCurrentUser().getMemberId();
		FormData inputFormData = FormDataUtil.createInputForm("BindContactWay");
		FormDataUtil.setProperty(inputFormData, "memberid", memberId);
		FormDataUtil.setProperty(inputFormData, "channel", channel);
		FormDataUtil.setProperty(inputFormData, "destination", destination);
		FormDataUtil.setProperty(inputFormData, "verifycode", verifyCode);
		FormData outData = ServiceClient.getResponseFormData(inputFormData, "BindContactWay");
		if(FormDataUtil.isSucceed(outData)) {
			User user = Utils.getCurrentUser();
			if (channel == 0) {
				user.setMobileNumber(destination);
			} else {
				user.setEmail(destination);
			}
			return ServiceClient.parseObjToJson(new MessageWrapper(true));
		} else {
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData));
		}
	}
	
	/**
	 * 绑定手机/邮箱
	 * @return
	 */
	@RequestMapping(value="bindContactWay", method=RequestMethod.POST)
	@ResponseBody
	public String bindContactWay(int channel, String verifyCode, String destination) {
		// 检查是否非法操作
		Session session = SecurityUtils.getSubject().getSession();
		String checkDestination = channel == 0 ? (String) session.getAttribute("currMobile") 
				: (String) session.getAttribute("currEmail");
		// 不为空说明非法，应该走更新不是绑定
		if(StringUtils.isNotEmpty(checkDestination)) {
			return ServiceClient.parseObjToJson(new MessageWrapper(false, "非法操作！"));
		}
		// 绑定手机/Email
		String memberId = Utils.getCurrentUser().getMemberId();
		FormData inputFormData = FormDataUtil.createInputForm("BindContactWay");
		FormDataUtil.setProperty(inputFormData, "memberid", memberId);
		FormDataUtil.setProperty(inputFormData, "channel", channel);
		FormDataUtil.setProperty(inputFormData, "destination", destination);
		FormDataUtil.setProperty(inputFormData, "verifycode", verifyCode);
		FormData outData = ServiceClient.getResponseFormData(inputFormData, "BindContactWay");
		if(FormDataUtil.isSucceed(outData)) {
		    User user = Utils.getCurrentUser();
		    if (channel == 0) {
		        user.setMobileNumber(destination);
            } else {
                user.setEmail(destination);
            }
			return ServiceClient.parseObjToJson(new MessageWrapper(true));
		} else {
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData));
		}
	}
	
	/**
	 * 更新紧急联系人
	 * @return
	 */
	@RequestMapping(value="updateSCContacter", method=RequestMethod.POST)
	@ResponseBody
	public String updateSCContacter(HttpServletRequest request) {
		List<FilterExt> list = FilterExt.buildFromHttpRequest(request);
		FormData fd = FormDataUtil.buildFormDataByFilterExt(list, "UpdateSCContacter");
		String custId = Utils.getCurrentUser().getCustId();
		FormDataUtil.setProperty(fd, "custid", custId);
		FormData outData = ServiceClient.getResponseFormData(fd, "UpdateSCContacter");
		if(FormDataUtil.isSucceed(outData)) {
			return ServiceClient.parseObjToJson(new MessageWrapper(true));
		} else {
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData));
		}
	}
	
	/**
	 * 更新联系地址
	 * @return
	 */
	@RequestMapping(value="UpdateSCAddress", method=RequestMethod.POST)
	@ResponseBody
	public String updateSCAddress(HttpServletRequest request) {
		List<FilterExt> list = FilterExt.buildFromHttpRequest(request);
		FormData fd = FormDataUtil.buildFormDataByFilterExt(list, "UpdateSCAddress");
		String custId = Utils.getCurrentUser().getCustId();
		FormDataUtil.setProperty(fd, "custid", custId);
		FormData outData = ServiceClient.getResponseFormData(fd, "UpdateSCAddress");
		if(FormDataUtil.isSucceed(outData)) {
			return ServiceClient.parseObjToJson(new MessageWrapper(true));
		} else {
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData));
		}
	}
	
	/**
	 * 更新个人信息
	 * @return
	 */
	@RequestMapping(value="UpdateSCPersInfo", method=RequestMethod.POST)
	@ResponseBody
	public String updateSCPersInfo(HttpServletRequest request, String birtdate) {
		List<FilterExt> list = FilterExt.buildFromHttpRequest(request);
		FormData fd = FormDataUtil.buildFormDataByFilterExt(list, "UpdateSCPersInfo");
		String custId = Utils.getCurrentUser().getCustId();
		FormDataUtil.setProperty(fd, "custid", custId);
		Date birthDate = DateUtils.getDateFromStr(birtdate);
		if(birthDate != null) {
			FormDataUtil.setProperty(fd, "birtdate", birthDate);
		}
		FormData outData = ServiceClient.getResponseFormData(fd, "UpdateSCPersInfo");
		if(FormDataUtil.isSucceed(outData)) {
			return ServiceClient.parseObjToJson(new MessageWrapper(true));
		} else {
			return "";
		}
	}
	
	/**
	 * 更新工作信息
	 * @return
	 */
	@RequestMapping(value="UpdateSCJobInfo", method=RequestMethod.POST)
	@ResponseBody
	public String updateSCJobInfo(HttpServletRequest request) {
		List<FilterExt> list = FilterExt.buildFromHttpRequest(request);
		FormData fd = FormDataUtil.buildFormDataByFilterExt(list, "UpdateSCJobInfo");
		String custId = Utils.getCurrentUser().getCustId();
		FormDataUtil.setProperty(fd, "custid", custId);
		FormData outData = ServiceClient.getResponseFormData(fd, "UpdateSCJobInfo");
		if(FormDataUtil.isSucceed(outData)) {
			return ServiceClient.parseObjToJson(new MessageWrapper(true));
		} else {
			return "";
		}
	}
	
	
	/**
	 * 更新企业紧急联系人
	 * @return
	 */
	@RequestMapping(value="updateCorpSCContacter", method=RequestMethod.POST)
	@ResponseBody
	public String updateCorpSCContacter(HttpServletRequest request) {
		List<FilterExt> list = FilterExt.buildFromHttpRequest(request);
		FormData fd = FormDataUtil.buildFormDataByFilterExt(list, "UpdateCorpSCContacter");
		String custId = Utils.getCurrentUser().getCustId();
		FormDataUtil.setProperty(fd, "custid", custId);
		// 8-紧急联系人
		FormDataUtil.setProperty(fd, "addrtype", "8");
		FormData outData = ServiceClient.getResponseFormData(fd, "UpdateCorpSCContacter");
		if(FormDataUtil.isSucceed(outData)) {
			return ServiceClient.parseObjToJson(new MessageWrapper(true));
		} else {
			return "";
		}
	}
	
	/**
	 * 更新企业信息
	 * @return
	 */
	@RequestMapping(value="updateCorpSCInfo", method=RequestMethod.POST)
	@ResponseBody
	public String updateCorpSCInfo(HttpServletRequest request, String comedate) {
		List<FilterExt> list = FilterExt.buildFromHttpRequest(request);
		FormData fd = FormDataUtil.buildFormDataByFilterExt(list, "UpdateCorpSCInfo");
		FormDataUtil.setProperty(fd, "comedate", DateUtils.getDateFromStr(comedate));
		String custId = Utils.getCurrentUser().getCustId();
		FormDataUtil.setProperty(fd, "custid", custId);
		FormDataUtil.setProperty(fd, "addrtype", "2");
		FormData outData = ServiceClient.getResponseFormData(fd, "UpdateCorpSCInfo");
		if(FormDataUtil.isSucceed(outData)) {
			return ServiceClient.parseObjToJson(new MessageWrapper(true));
		} else {
			return "";
		}
	}
	
	 //证书管理 -申请证书=ContForRC2101
	@RequestMapping(value="ChoiceRAInfo", produces = "text/html; charset=utf-8")
	@ResponseBody 
	public String ChoiceRAInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = Utils.getCurrentUser();
		String custid = user.getCustId();	
		FormData fds = FormDataUtil.createInputForm("RASelAcct");
		FormDataUtil.setProperty(fds, "custid", custid);
		FormData outData= ServiceClient.getResponseFormData(fds, "RASelAcct");
		String flag = FormDataUtil.getProperty(outData, "id").toString();
		if (flag.isEmpty()) {
			// 根据custid去查询签章要的字段
			String custtype = user.getMemberType();
			String custname = user.getCustName();
			String paperkind=user.getPaperKind(), paperid=user.getPaperId();
			Map<String,String> map=new HashMap<String, String>();
			map.put("custtype", custtype);
			map.put("custname", custname);
			map.put("paperkind", paperkind);
 			map.put("paperid", paperid);
			// 调用签章的接口方法。
			try {
				CertServiceResponseVO cr = Inter2101Other.toinprocess(map, outData);
				FormDataUtil.setProperty(outData, "respdesc", cr.getResultMessage());
				FormDataUtil.setProperty(outData, "respcode", cr.getResultCode());
				// 记录申请下载到系统
				FormData raacct = FormDataUtil.createInputForm("RAInertAcct");
				FormDataUtil.setProperty(raacct, "txcode", cr.getTxCode());
				FormDataUtil.setProperty(raacct, "txtime", cr.getTxTime());
				FormDataUtil.setProperty(raacct, "resultcode", cr.getResultCode());
				FormDataUtil.setProperty(raacct, "resultmessage", cr.getResultMessage());
				FormDataUtil.setProperty(raacct, "dn", cr.getDn());
				FormDataUtil.setProperty(raacct, "sequenceno", cr.getSequenceNo());
				FormDataUtil.setProperty(raacct, "serialno", cr.getSerialNo());
				FormDataUtil.setProperty(raacct, "authcode", cr.getAuthCode());
				FormDataUtil.setProperty(raacct, "starttime", cr.getStartTime());
				FormDataUtil.setProperty(raacct, "endtime", cr.getEndTime());
				FormDataUtil.setProperty(raacct, "custtype", custtype);
				FormDataUtil.setProperty(raacct, "custid", custid);
				FormDataUtil.setProperty(raacct, "custname", custname);
				FormDataUtil.setProperty(raacct, "paperkind", paperkind);
				FormDataUtil.setProperty(raacct, "paperid", paperid);
				FormDataUtil.setProperty(raacct, "state", "1");
				ServiceClient.getResponseFormData(raacct, "RAInertAcct");
			} catch (RATKException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			String respdesc="该借款人已申请过证书，请不要重复申请";
			FormDataUtil.setProperty(outData, "respdesc",respdesc);
			FormDataUtil.setProperty(outData, "respcode","N");
		}
		return ServiceClient.parseObjToJson(outData);
	}
	
	//补发证书  =ContForRC2101
		@RequestMapping(value="RechangeRAInfo", produces = "text/html; charset=utf-8")
		@ResponseBody 
		public String RechangeRAInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
			User user = Utils.getCurrentUser();
			String custid = user.getCustId();	
			FormData fds = FormDataUtil.createInputForm("RASelAcct");
			FormDataUtil.setProperty(fds, "custid", custid);
			FormData outData= ServiceClient.getResponseFormData(fds, "RASelAcct");
			String flag = FormDataUtil.getProperty(outData, "id").toString();		
			if (flag.isEmpty()) {
				String respdesc="该借款人没有申请过证书，不能完成证书补发";
				FormDataUtil.setProperty(outData, "respdesc",respdesc);
				FormDataUtil.setProperty(outData, "respcode","N"); 
			}else{
				try {
					CertServiceResponseVO cr = Inter2702.toinprocess(model, outData);	
					FormDataUtil.setProperty(outData, "respdesc", cr.getResultMessage());
					FormDataUtil.setProperty(outData, "respcode", cr.getResultCode());
					// 记录到系统
					if (cr.getResultCode().equals("0000")) {
						FormData raacct = FormDataUtil.createInputForm("RAUpdateAccount");
						FormDataUtil.setProperty(raacct, "custid", custid);
	 					FormDataUtil.setProperty(raacct, "serialno", cr.getSerialNo());
						FormDataUtil.setProperty(raacct, "authcode", cr.getAuthCode());
						FormDataUtil.setProperty(raacct, "starttime", cr.getStartTime());
						FormDataUtil.setProperty(raacct, "endtime", cr.getEndTime());
						FormDataUtil.setProperty(raacct, "state", "2");
						ServiceClient.getResponseFormData(raacct, "RAUpdateAccount");
					}
				} catch (RATKException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			}
			return ServiceClient.parseObjToJson(outData);
		}
		// 数字证书下载
		@RequestMapping(value = "ContForRA2401CHECK.html", produces = "text/html; charset=utf-8")
		@ResponseBody
		public String ContForRA2401CHECK(HttpServletRequest request, HttpServletResponse response, Model model) {
			//duxt start for ra
			//调用后台交易获取PDF签章控件下载地址
			FormData formData1 = FormDataUtil.createInputForm("GetControlDownLoadURL");
			FormDataUtil.setProperty(formData1,"ctltype", "2"); //下载控件类型，1：合同PDF签章控件
			FormData outData1 = ServiceClient.getResponseFormData(formData1, "GetControlDownLoadURL");
			if(FormDataUtil.isSucceed(outData1)){
				String ctlurl = (String)FormDataUtil.getProperty(outData1, "ctlurl");
				model.addAttribute("ctlurl", ctlurl);
			} 
			//duxt start for ra
			User user = Utils.getCurrentUser();
			String custid = user.getCustId();	
			FormData fd = FormDataUtil.createInputForm("RASelAcct");
			FormDataUtil.setProperty(fd, "custid", custid);
			FormData outData = ServiceClient.getResponseFormData(fd, "RASelAcct");
			String states = FormDataUtil.getProperty(outData, "state").toString();
			if (states.equals("0")) {
				FormDataUtil.setProperty(outData, "respdesc", "该证书状态为已下载状态，请不要重复下载");
				FormDataUtil.setProperty(outData, "respcode", "0");
			} else {
				FormDataUtil.setProperty(outData, "respcode", "0000");
			}
			return ServiceClient.parseObjToJson(outData);
		}
		// 数字证书下载
		@RequestMapping(value = "ContForRA2401.html", produces = "text/html; charset=utf-8")
		@ResponseBody
		public String ContForRA2401(HttpServletRequest request, HttpServletResponse response, Model model) {
			User user = Utils.getCurrentUser();
			String custid = user.getCustId();	
			String p10 = request.getParameter("p10");
			FormData fd = FormDataUtil.createInputForm("RASelAcct");
			FormDataUtil.setProperty(fd, "custid", custid);
			FormData outData = ServiceClient.getResponseFormData(fd, "RASelAcct");
			String states = FormDataUtil.getProperty(outData, "state").toString();
		if (states.equals("0")) {
			FormDataUtil.setProperty(outData, "respdesc", "该证书状态为已下载状态，请不要重复下载");
			FormDataUtil.setProperty(outData, "respcode", "0");
		} else {
			try {
				FormDataUtil.setProperty(outData, "p10", p10);	
				CertServiceResponseVO cr = Inter2401.toinprocess(model, outData, p10);
				FormDataUtil.setProperty(outData, "respdesc", cr.getResultMessage());
				FormDataUtil.setProperty(outData, "respcode", cr.getResultCode());
				FormDataUtil.setProperty(outData, "signaturecert", cr.getSignatureCert());
				if (cr.getResultCode().equals("0000")) {
					// 记录申请下载到系统
					FormData raacct = FormDataUtil.createInputForm("RAUpdateAcct");
					FormDataUtil.setProperty(raacct, "custid", custid);
					FormDataUtil.setProperty(raacct, "state", "0");
					ServiceClient.getResponseFormData(raacct, "RAUpdateAcct");
				}
			} catch (RATKException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
			return ServiceClient.parseObjToJson(outData);
		}
		
		//更新 证书  =2703
		@RequestMapping(value="UpdateRAInfo", produces = "text/html; charset=utf-8")
		@ResponseBody 
		public String UpdateRAInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
			User user = Utils.getCurrentUser();
			String custid = user.getCustId();	
			FormData fds = FormDataUtil.createInputForm("RASelAcct");
			FormDataUtil.setProperty(fds, "custid", custid);
			FormData outData= ServiceClient.getResponseFormData(fds, "RASelAcct");
			String flag = FormDataUtil.getProperty(outData, "flag").toString();		
			String id = FormDataUtil.getProperty(outData, "id").toString();	
			String state = FormDataUtil.getProperty(outData, "state").toString();	
			if (flag.isEmpty() && id.isEmpty()) {
				String respdesc="该借款人没有申请过证书，不能完成证书更新";
				FormDataUtil.setProperty(outData, "respdesc",respdesc);
				FormDataUtil.setProperty(outData, "respcode","N"); 
			}else if (flag.equals("1")) {
				try {
					CertServiceResponseVO cr = Inter2703.toinprocess(model, outData);	
					FormDataUtil.setProperty(outData, "respdesc", cr.getResultMessage());
					FormDataUtil.setProperty(outData, "respcode", cr.getResultCode());
					// 记录到系统
					if (cr.getResultCode().equals("0000")) {
						FormData raacct = FormDataUtil.createInputForm("RAUpdateAccount");
						FormDataUtil.setProperty(raacct, "custid", custid);
	 					FormDataUtil.setProperty(raacct, "serialno", cr.getSerialNo());
						FormDataUtil.setProperty(raacct, "authcode", cr.getAuthCode());
						FormDataUtil.setProperty(raacct, "starttime", cr.getStartTime());
						FormDataUtil.setProperty(raacct, "endtime", cr.getEndTime());
						FormDataUtil.setProperty(raacct, "state", "3");
						ServiceClient.getResponseFormData(raacct, "RAUpdateAccount");
					}
				} catch (RATKException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}else if(state.equals("3")){
				String respdesc="该借款人已经更新证书成功，请点击“下载证书”按钮";
				FormDataUtil.setProperty(outData, "respdesc",respdesc);
				FormDataUtil.setProperty(outData, "respcode","N"); 
			}else{
				String respdesc="该借款人没有缴费，请先完成缴费后再来更新证书";
				FormDataUtil.setProperty(outData, "respdesc",respdesc);
				FormDataUtil.setProperty(outData, "respcode","N");  
			}
			return ServiceClient.parseObjToJson(outData);
		}
		
		//更新 证书  =2703
				@RequestMapping(value="UpdateDBRAInfo", produces = "text/html; charset=utf-8")
				@ResponseBody 
				public String UpdateDBRAInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
					User user = Utils.getCurrentUser();
					String custid = user.getCustId();	
					FormData fds = FormDataUtil.createInputForm("RASelAcct");
					FormDataUtil.setProperty(fds, "custid", custid);
					FormData outData= ServiceClient.getResponseFormData(fds, "RASelAcct");
					String flag = FormDataUtil.getProperty(outData, "flag").toString();	
					String id = FormDataUtil.getProperty(outData, "id").toString();	
					String state = FormDataUtil.getProperty(outData, "state").toString();	
					if (flag.isEmpty() && id.isEmpty()) {
						String respdesc="该担保公司没有申请过证书，不能完成证书更新";
						FormDataUtil.setProperty(outData, "respdesc",respdesc);
						FormDataUtil.setProperty(outData, "respcode","N"); 
					}else if (flag.equals("1")) {
						try {
							CertServiceResponseVO cr = Inter2703.toinprocess(model, outData);	
							FormDataUtil.setProperty(outData, "respdesc", cr.getResultMessage());
							FormDataUtil.setProperty(outData, "respcode", cr.getResultCode());
							// 记录到系统
							if (cr.getResultCode().equals("0000")) {
								FormData raacct = FormDataUtil.createInputForm("RAUpdateAccount");
								FormDataUtil.setProperty(raacct, "custid", custid);
			 					FormDataUtil.setProperty(raacct, "serialno", cr.getSerialNo());
								FormDataUtil.setProperty(raacct, "authcode", cr.getAuthCode());
								FormDataUtil.setProperty(raacct, "starttime", cr.getStartTime());
								FormDataUtil.setProperty(raacct, "endtime", cr.getEndTime());
								FormDataUtil.setProperty(raacct, "state", "3");
								ServiceClient.getResponseFormData(raacct, "RAUpdateAccount");
							}
						 } catch (RATKException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						 }
					}else if(state.equals("3")){
						String respdesc="该担保公司已经更新证书成功，请点击“下载证书”按钮";
						FormDataUtil.setProperty(outData, "respdesc",respdesc);
						FormDataUtil.setProperty(outData, "respcode","N"); 
					}else{
						String respdesc="该担保公司没有缴费，请先完成缴费后再来更新证书";
						FormDataUtil.setProperty(outData, "respdesc",respdesc);
						FormDataUtil.setProperty(outData, "respcode","N");  
					} 
					return ServiceClient.parseObjToJson(outData);
				}
}
