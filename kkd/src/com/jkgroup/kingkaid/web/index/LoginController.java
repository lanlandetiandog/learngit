package com.jkgroup.kingkaid.web.index;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.service.shiro.QRCodeKit;
import com.jkgroup.kingkaid.service.shiro.RandomValidateCode;
import com.jkgroup.kingkaid.utils.Utils;

/**
 * 用户登入、登出处理类
 * @author pan
 * @CreateDate 2015-03-24
 *
 */
@Controller
public class LoginController {
	
	 private final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping(value="login.html")
	public String toLogin(){
		return "login_new";
	}
	
	/**
	 * 检查验证码是否正确
	 * 受制于前端框架限制，返回true或false
	 * @param code
	 * @return
	 */
	@RequestMapping(value="checkValCode")
	@ResponseBody
	public String checkValidCode(HttpServletRequest request) {
		String code = StringUtils.trimToEmpty(request.getParameter("code"));
		String validateCode = (String) SecurityUtils.getSubject().getSession().getAttribute(RandomValidateCode.RANDOMCODEKEY);
		return Boolean.toString(code.equalsIgnoreCase(validateCode));
	}
	
	/**
	 * 登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="login", method=RequestMethod.POST)
	public String login(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String username = StringUtils.trimToEmpty(request.getParameter("username"));
		String password = StringUtils.trimToEmpty(request.getParameter("password"));
		String code = StringUtils.trimToEmpty(request.getParameter("val_code"));
		String validateCode = (String) SecurityUtils.getSubject().getSession().getAttribute(RandomValidateCode.RANDOMCODEKEY);
		
		// 判断用户名密码不能为空
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			redirectAttributes.addFlashAttribute("errorMsg", "用户名密码不能为空");
			return "redirect:login.html";
		}
		
		log.info("in login: web code is {}, session code is {}", code, validateCode);
		// 验证码不正确
		if(StringUtils.isBlank(code) || !code.equalsIgnoreCase(validateCode)) {
			redirectAttributes.addFlashAttribute("errorMsg", "验证码错误");
			return "redirect:login.html";
		}
		
		// 删除session验证码
		SecurityUtils.getSubject().getSession().removeAttribute(RandomValidateCode.RANDOMCODEKEY);
		
		// Shiro登录验证
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		String errorMsg = null;
		try{
			subject.login(token);
		} catch (IncorrectCredentialsException ex) {
			// 用户名不存在或者密码错误
			errorMsg = "用户名不存在或者密码错误";
		} catch (UnknownAccountException ex) {
			// 用户名不存在或者密码错误
			errorMsg = "用户名不存在或者密码错误";
		} catch (LockedAccountException ex) {
			// 账户已经被锁定，请联系管理员
			errorMsg = "账户已经被锁定，请联系管理员";
		} catch (ExcessiveAttemptsException ex) {
			// 登录过于频繁，账户被锁定1小时
			errorMsg = "登录过于频繁，账户被锁定1小时";
        } catch (Exception e) {
        	log.error("登录异常！", e);
        	errorMsg = "用户名不存在或者密码错误";
        }
		if(null == errorMsg) {
			// 决定应该跳转到那个页面
			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
			User user = Utils.getCurrentUser();
			request.getSession().setAttribute("membername", user.getMemberName());
			request.getSession().setAttribute("isGuarantee", user.isGuarantee());
			request.getSession().setAttribute("mobileNumber", user.getMobileNumber());
			if(savedRequest != null) {
				// shiro 获取的之前request的url包含context path
				// spring mvc redirect 会自动加上context path
				// 所以重复了，需要删去一个
				String contextPath = WebUtils.getContextPath(request);
				return "redirect:" + savedRequest.getRequestUrl().replace(contextPath, "");
			}
			return "redirect:/";
		} else {
			redirectAttributes.addFlashAttribute("errorMsg", errorMsg);
			return "redirect:login.html";
		}
	}
	
	/**
	 * 获取验证码
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="getValidateCode")
	public void getValidateCode(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getLoginVerificationCode(request, response);//输出图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@RequestMapping(value="getCodeZXing")
	public void getCodeZXing(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
//        RandomValidateCode randomValidateCode = new RandomValidateCode();
        QRCodeKit qrCodeKit = new QRCodeKit();
        try {
//            randomValidateCode.getCodezxing(request, response);//输出图片方法
        	qrCodeKit.getCodezxing(request, response);//输出图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	/**
	 * 注销
	 * @return
	 */
	@RequestMapping("logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "redirect:/";
	}
}
