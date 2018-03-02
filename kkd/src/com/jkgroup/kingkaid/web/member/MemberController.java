package com.jkgroup.kingkaid.web.member;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jkgroup.jkd.seo.entity.DataResource;
import com.jkgroup.jkd.seo.service.SeoService;
import com.jkgroup.kingkaid.bo.MessageWrapper;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.CommonServiceClient;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.service.shiro.RandomValidateCode;
import com.jkgroup.kingkaid.utils.CipherUtil;
import com.jkgroup.kingkaid.utils.FilterExt;
import com.jkgroup.kingkaid.utils.MathUtil;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 *
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年6月1日 下午8:50:46
 */

@Controller
@RequestMapping("member")
public class MemberController {
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    @Autowired 
	private SeoService seoService;

	/**
	 * 用户协议页面
	 * @return
	 */
	@RequestMapping("protocol.html")
	public String toProtocol() {
		return "member/protocol";
	}
	
	@RequestMapping("protocolOld.html")
	public String toprotocolOld() {
		return "member/protocolOld";
	}
	
	/**
	 * 注册页面
	 * @return
	 */
	@RequestMapping("register.html")
	public String toMemberRegister(Model model, String inviteCode) {
	    if(validateICode(inviteCode)) {
            model.addAttribute("inviteCode", inviteCode);
        }
	    return "member/reg_new";
	}
	
	/**
	 * 获得协议内容
	 * @return
	 */
	@RequestMapping("continfo")
	@ResponseBody
	public String getContinfo(HttpServletRequest request){
		String json = "{}"; 
		String cont_type=(request.getParameter("cont_type")==null) ? "3":"33";
		FormData formData = FormDataUtil.createInputForm("ContInfoSelect");
		FormDataUtil.setProperty(formData, "cont_type", cont_type);
		FormDataUtil.setProperty(formData, "custid", "");
		FormData outData = ServiceClient.getResponseFormData(formData, "ContInfoSelect");
		if(FormDataUtil.isSucceed(outData)){
			String continfo = (String) FormDataUtil.getProperty(outData, "continfo");
			try {
				continfo = new String(Base64.decodeBase64(continfo), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// 不可能的错误
			}
			FormDataUtil.setProperty(outData, "continfo", continfo);
			json = ServiceClient.parseObjToJson(outData);
		}else{
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData)) ;
		}
		return json;
	}
	 
	/**
	 * 找回密码页面
	 * @return
	 */
	@RequestMapping("foundpwd.html")
	public String toFoundPwd() {
		return "member/foundpwd";
	}
	
	/**
	 * 密码重置页面
	 * @return
	 */
	@RequestMapping("resetpwd.html")
	public String toResetPwd(Model model) {
		Session session = SecurityUtils.getSubject().getSession();
		String memberid = (String) session.getAttribute("resetPwdMemberid");
		if(memberid == null) {
			// 说明这不是从foundpwd.html做完手机验证跳过来的，是非法的
			model.addAttribute("result", new MessageWrapper(false, "请先进行手机验证"));
			return "member/tmp_after_resetpwd";
		}
		return "member/resetpwd";
	}
	
	@RequestMapping("tmp_after_register")
	public String toRegisterTmp() {
		return "member/tmp_after_register";
	}
	
	/**
	 * 检查会员名是否存在
	 * 此方法被前台JS验证框架限制，仅能返回true或false
	 * @return
	 */
	@RequestMapping(value="checkName", method=RequestMethod.POST)
	@ResponseBody
	public String checkMemberName(String memberName) {
		return nameUsed(memberName) + "";
	}
	
	private boolean nameUsed(String memberName) {
		FormData fd = FormDataUtil.createInputForm("CheckMemberName");
		FormDataUtil.setProperty(fd, "membername", memberName);
		FormData outData = ServiceClient.getResponseFormData(fd, "CheckMemberName");
		return FormDataUtil.isSucceed(outData);
	}
	
	/**
	 * 检查手机号码是否存在
	 * 此方法被前台JS验证框架限制，仅能返回true或false
	 * @return
	 */
	@RequestMapping(value="checkMobile", method=RequestMethod.POST)
	@ResponseBody
	public String checkMobileNumber(String memberMobile) {
		return mobileUsed(memberMobile) + "";
	}
	
	private boolean mobileUsed(String memberMobile) {
		FormData fd = FormDataUtil.createInputForm("CheckMemberMobile");
		FormDataUtil.setProperty(fd, "mobilenumber", memberMobile);
		FormData outData = ServiceClient.getResponseFormData(fd, "CheckMemberMobile");
		return FormDataUtil.isSucceed(outData);
	}
	
	/**
	 * 检查手机号码是否存在
	 * 此方法被前台JS验证框架限制，仅能返回true或false
	 * @return
	 */
	@RequestMapping(value="checkPaperID", method=RequestMethod.POST)
	@ResponseBody
	public String checkIdNo(String paperid) {
		FormData fd = FormDataUtil.createInputForm("CheckMemberPaperId");
		FormDataUtil.setProperty(fd, "paperid", paperid);
		FormData outData = ServiceClient.getResponseFormData(fd, "CheckMemberPaperId");
		if(FormDataUtil.isSucceed(outData)) {
			return "true";
		} else {
	    	return "false";
		}
	}
	
	/**
	 * 检查邀请码是否存在
	 * 此方法被前台JS验证框架限制，仅能返回true或false
	 * @return
	 */
	@RequestMapping(value="checkICode", method=RequestMethod.POST)
	@ResponseBody
	public String checkInviteCode(String inviteCode) {
		return Boolean.toString(validateICode(inviteCode));
	}
	
	/**
	 * 会员注册
	 * @param request
	 * @return
	 */
	@RequestMapping(value="register", method=RequestMethod.POST)
	public String registerMember(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		List<FilterExt> list = FilterExt.buildFromHttpRequest(request);
		FormData fd = FormDataUtil.buildFormDataByFilterExt(list, "MemberCountRegister");
		
		String loginPassword = (String) FormDataUtil.getProperty(fd, "loginpassword");
		String filter_mobilenumber = (String) FormDataUtil.getProperty(fd, "mobilenumber");
		// 再次验证用户名和密码
		if(StringUtils.isEmpty(loginPassword) || !loginPassword.matches("(?![a-zA-Z]+$)(?!\\d+$)[A-Za-z0-9~!@#$%^&*()_+`\\-={}:\";'<>?,.\\/]{8,20}")) {
			redirectAttributes.addFlashAttribute("regErrorMsg", "密码必须是8-20位长的字母、数字和符号的组合");
			return "redirect:register.html";
		}
		if(StringUtils.isEmpty(filter_mobilenumber) || !filter_mobilenumber.matches("^1\\d{10}$")) {
			redirectAttributes.addFlashAttribute("regErrorMsg", "请输入正确的手机号码");
			return "redirect:register.html";
		}
		if(!mobileUsed(filter_mobilenumber)) {
			redirectAttributes.addFlashAttribute("regErrorMsg", "手机号码已占用");
			return "redirect:register.html";
		}
		
		//for 增加百度统计 start
		Session session = SecurityUtils.getSubject().getSession();	 
		String sessionid = null;
		if (session.getAttribute("counturl") != null) {
			String counturl = session.getAttribute("counturl").toString(); 		
			log.debug("counturl:{}",counturl);
			
			int b = counturl.lastIndexOf("=");
			sessionid = counturl.substring(b+1, counturl.length());
			
			String counturl_new=MathUtil.encodeBase64File(counturl);			
			FormDataUtil.setProperty(fd, "counturl", counturl_new);	
		}
		FormDataUtil.setProperty(fd, "membertype", "0");
		//for 增加百度统计 end
		// 02-网站注册
		FormDataUtil.setProperty(fd, "registersource", "02");
		FormDataUtil.setProperty(fd, "loginpassword", loginPassword);
		FormDataUtil.setProperty(fd, "nameupdate", "0");
		FormData outData = ServiceClient.getResponseFormData(fd, "MemberCountRegister");
		String memberName = FormDataUtil.getProperty(outData, "memberName") + "";
		if(FormDataUtil.isSucceed(outData)) {
			// 注册完直接登录
		    UsernamePasswordToken token = new UsernamePasswordToken(memberName, loginPassword);  
		    SecurityUtils.getSubject().login(token);
		    session.setAttribute("mobileNumber", filter_mobilenumber);
		    if(sessionid!=null){
		    	FormData formdata = FormDataUtil.createInputForm("MemberIdByCountType");
				FormDataUtil.setProperty(formdata, "counttype", sessionid);
				FormData outdata = ServiceClient.getResponseFormData(formdata, "MemberIdByCountType");
				if(FormDataUtil.isSucceed(outdata)) {
					try {
						String memberId = (String) FormDataUtil.getProperty(outdata, "memberid");
						DataResource dataResource = seoService.findDataById(sessionid);
						if(memberId!=null&&dataResource!=null){
							dataResource.setMemberId(memberId);
							seoService.saveData(dataResource);	
						}			
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		    }
			return "redirect:tmp_after_register.html";
		} else {
		    redirectAttributes.addFlashAttribute("regErrorMsg", FormDataUtil.getErrorMessage(outData));
			return "redirect:register.html";
		}
	}
	
	/**
	 * 绑定西安银行手机发送验证码(该手机/Email不存在于会员表中)
	 * @param destination
	 * @param channel
	 * @return
	 */
	@RequestMapping(value="CtpsendVCode", method=RequestMethod.POST)
	@ResponseBody
	public String sendVCode(String destination, int channel, int option) {
		String result = "";
	    changeMemoryCode(RandomValidateCode.VCODEKEY);
	    FormData outData = CommonServiceClient.sendVCode(destination, channel, option);
	    if(FormDataUtil.isSucceed(outData)) {
	        result = ServiceClient.parseObjToJson(new MessageWrapper(true, "发送成功"));
	    } else {
	        result = ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData));
	    }
		return result;
	}
	
	
	/**
	 * 发送验证码(该手机/Email不存在于会员表中)
	 * @param destination
	 * @param channel
	 * @return
	 */
	@RequestMapping(value="sendVCode", method=RequestMethod.POST)
	@ResponseBody
	public String sendVCode(String destination, int channel, int option, String code) {
		String result = "";
		code = StringUtils.trimToEmpty(code);
		String validateCode = (String) SecurityUtils.getSubject().getSession().getAttribute(RandomValidateCode.VCODEKEY);
		if (code.equalsIgnoreCase(validateCode)) { // 验证码校验成功
		    changeMemoryCode(RandomValidateCode.VCODEKEY);
		    FormData outData = CommonServiceClient.sendVCode(destination, channel, option);
		    if(FormDataUtil.isSucceed(outData)) {
		        result = ServiceClient.parseObjToJson(new MessageWrapper(true, "发送成功"));
		    } else {
		        result = ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData));
		    }
		} else {
		    result = ServiceClient.parseObjToJson(new MessageWrapper(false, "验证码不正确"));
		}
		return result;
	}
	
	/**
	 * 发送验证码(该手机/Email必须与某一会员绑定)
	 * @param destination
	 * @param channel
	 * @return
	 */
	@RequestMapping(value="sendmVCode", method=RequestMethod.POST)
	@ResponseBody
	public String sendmVCode(String destination, int channel, int option, String code) {
        String result = "";
        code = StringUtils.trimToEmpty(code);
        String validateCode = (String) SecurityUtils.getSubject().getSession().getAttribute(RandomValidateCode.VCODEMMMKEY);
        if (code.equalsIgnoreCase(validateCode)) { // 验证码校验成功
            changeMemoryCode(RandomValidateCode.VCODEMMMKEY);
            FormData outData = CommonServiceClient.sendmVCode(destination, channel, option);
            if (FormDataUtil.isSucceed(outData)) {
                Session session = SecurityUtils.getSubject().getSession();
                session.setAttribute("resetPwdMemberid", FormDataUtil.getProperty(outData, "memberid"));
                session.setAttribute("resetPwdMemberName", FormDataUtil.getProperty(outData, "membername"));
                session.setAttribute("resetSalt", FormDataUtil.getProperty(outData, "salt"));
                // 记录resetPwdMemberid生成的时间，后面获取时可以定义一个超时时间
                session.setAttribute("resetPwdmemberidMillis", System.currentTimeMillis());
                result = ServiceClient.parseObjToJson(new MessageWrapper(true, "发送成功"));
            } else {
                result = ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData));
            }
        } else {
            result = ServiceClient.parseObjToJson(new MessageWrapper(false, "验证码不正确"));
        }
        return result;
	}
	
	/**
	 * 重置密码-验证码校验
	 * @param destination
	 * @param verifyCode
	 * @return
	 */
	@RequestMapping(value="verifyForResetPwd", method=RequestMethod.POST)
	@ResponseBody
	public String verifyForResetPwd(String destination, String verifyCode) {
		FormData outData = CommonServiceClient.checkVcode(destination, verifyCode);
		if(FormDataUtil.isSucceed(outData)) {
			return ServiceClient.parseObjToJson(new MessageWrapper(true));
		} else {
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData));
		}
	}
	
	/**
	 * 重置密码操作
	 * @param model
	 * @param loginPassword
	 * @return
	 */
	@RequestMapping(value="resetPwd", method=RequestMethod.POST)
	public String resetPwd(Model model, String loginPassword) {
		Session session = SecurityUtils.getSubject().getSession();
		String memberId = (String) session.getAttribute("resetPwdMemberid");
		String memberName = (String) session.getAttribute("resetPwdMemberName");
		String salt = (String) session.getAttribute("resetSalt");
		long storeMillis = (Long) session.getAttribute("resetPwdmemberidMillis");
		MessageWrapper wrapper = new MessageWrapper();
		if(StringUtils.isBlank(memberId) || StringUtils.isBlank(memberName)) {
			// 说明这不是从foundpwd.html做完手机验证跳过来的，是非法的
			wrapper.setStatus(false);
			wrapper.setMessage("请先进行手机验证");
			model.addAttribute("result", wrapper);
			return "member/tmp_after_resetpwd";
		}
		if(System.currentTimeMillis() - storeMillis > 2 * 60 * 1000) {
			// 为了更安全加一个操作时间
			// 操作超时
			wrapper.setStatus(false);
			wrapper.setMessage("操作超时");
			model.addAttribute("result", wrapper);
			return "member/tmp_after_resetpwd";
		}
		FormData inputFormData = FormDataUtil.createInputForm("ResetPassword");
		FormDataUtil.setProperty(inputFormData, "memberid", memberId);
		if(StringUtils.isEmpty(salt)) 
			loginPassword = CipherUtil.generatePassword(loginPassword, memberName);
		else 
			loginPassword = CipherUtil.encodePassword(loginPassword, salt);
		FormDataUtil.setProperty(inputFormData, "loginpassword", loginPassword);
		FormData outData = ServiceClient.getResponseFormData(inputFormData, "ResetPassword");
		if(FormDataUtil.isSucceed(outData)) {
			session.removeAttribute("resetPwdMemberid");
			session.removeAttribute("resetPwdMemberName");
			session.removeAttribute("resetPwdmemberidMillis");
			session.removeAttribute("resetSalt");
			wrapper.setStatus(true);
			wrapper.setMessage("密码重置成功，请重新登录");
		} else {
			//后台失败
			wrapper.setStatus(false);
			wrapper.setMessage("操作失败");
		}
		// 成功
		model.addAttribute("result", wrapper);
		return "member/tmp_after_resetpwd";
	}
	
	private boolean validateICode(String inviteCode) {
	    if(StringUtils.isBlank(inviteCode)) {
	        return false;
	    }
		FormData fd = FormDataUtil.createInputForm("ValidateInviteCode");
		FormDataUtil.setProperty(fd, "invitecode", inviteCode);
		FormData outData = ServiceClient.getResponseFormData(fd, "ValidateInviteCode");
		return FormDataUtil.isSucceed(outData);
	}
	
	/**
	 * 本方法处理通过电子邮箱重置密码的情况，这个URL即为邮件中发送的URL
	 * @param email
	 * @param verifyCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value="resetPwdFromEmail", method=RequestMethod.GET)
	private String emailResetPwd(String email, String verifyCode, Model model) {
		FormData inData = FormDataUtil.createInputForm("CheckVCodeGetMember");
		FormDataUtil.setProperty(inData, "senddestination", email);
		FormDataUtil.setProperty(inData, "verifycode", verifyCode);
		FormData outData = ServiceClient.getResponseFormData(inData, "CheckVCodeGetMember");
		if(FormDataUtil.isSucceed(outData)) {
			if(StringUtils.isNotBlank((String)FormDataUtil.getProperty(outData, "memberid")) 
				&& StringUtils.isNotBlank((String)FormDataUtil.getProperty(outData, "membername"))) {
				Session session = SecurityUtils.getSubject().getSession();
				session.setAttribute("resetPwdMemberid", FormDataUtil.getProperty(outData, "memberid"));
				session.setAttribute("resetPwdMemberName", FormDataUtil.getProperty(outData, "membername"));
				session.setAttribute("resetSalt", FormDataUtil.getProperty(outData, "salt"));
				// 记录resetPwdMemberid生成的时间，后面获取时可以定义一个超时时间
				session.setAttribute("resetPwdmemberidMillis", System.currentTimeMillis());
				return "member/resetpwd";
			} else {
				model.addAttribute("result", new MessageWrapper(false, "未能找到与此邮箱绑定的会员"));
				return "member/tmp_after_resetpwd";
			}
		} else {
			model.addAttribute("result", FormDataUtil.buildFailedMsgWrapper(outData));
			return "member/tmp_after_resetpwd";
		}
	}
	
	   /**
     * 获取验证码
     * @param request
     * @param response
     */
    @RequestMapping(value="getVCodeValidateCode")
    public void getVCodeValidateCode(HttpServletRequest request,HttpServletResponse response){
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRegVerificationCode(request, response);//输出图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 获取验证码
     * @param request
     * @param response
     */
    @RequestMapping(value="getVCodeMValidateCode")
    public void getVCodeMValidateCode(HttpServletRequest request,HttpServletResponse response){
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getVCodeMRandcode(request, response);//输出图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 检查验证码是否正确
     * 受制于前端框架限制，返回true或false
     * @param code
     * @return
     */
    @RequestMapping(value="checkVCodeValCode")
    @ResponseBody
    public String checkVCodeValidCode(HttpServletRequest request) {
        String code = StringUtils.trimToEmpty(request.getParameter("code"));
        String validateCode = (String) SecurityUtils.getSubject().getSession().getAttribute(RandomValidateCode.VCODEKEY);
        return Boolean.toString(code.equalsIgnoreCase(validateCode));
    }
    
    /**
     * 检查验证码是否正确
     * 受制于前端框架限制，返回true或false
     * @param code
     * @return
     */
    @RequestMapping(value="checkVCodeMValCode")
    @ResponseBody
    public String checkVCodeMValidCode(HttpServletRequest request) {
        String code = StringUtils.trimToEmpty(request.getParameter("code"));
        String validateCode = (String) SecurityUtils.getSubject().getSession().getAttribute(RandomValidateCode.VCODEMMMKEY);
        return Boolean.toString(code.equalsIgnoreCase(validateCode));
    }
    
    
    private void changeMemoryCode(String sessionKey) {
        Random r = new Random();
        String vcode = "" + randString.charAt(r.nextInt(35)) + randString.charAt(r.nextInt(35)) 
                + randString.charAt(r.nextInt(35)) + randString.charAt(r.nextInt(35))
                + randString.charAt(r.nextInt(35)) + randString.charAt(r.nextInt(35));
        Session session = SecurityUtils.getSubject().getSession();
        session.removeAttribute(sessionKey);
        session.setAttribute(sessionKey, vcode);
    }
    
}
