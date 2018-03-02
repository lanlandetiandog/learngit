package com.jkgroup.kingkaid.common.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.Anon;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.BrowseTool;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 拦截器<br>
 * 日志拦截
 * @author pan
 *
 */
public class AnonInterceptor extends HandlerInterceptorAdapter {

	private String excludePath = "/static";

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String servletPath = request.getServletPath();
		if( ! servletPath.startsWith(excludePath)){
		//	request.setAttribute(Constants.CURRENT_USER, Utils.getCurrentUser());
			
			//防止JSP页面缓存为了防止浏览器缓存当前访问的JSP动态页面
			// 将过期日期设置为一个过去时间 
			response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT"); 
			// 设置 HTTP/1.1 no-cache 头 
			response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate"); 
			// 设置 IE 扩展 HTTP/1.1 no-cache headers， 用户自己添加 
			response.addHeader("Cache-Control", "post-check=0, pre-check=0"); 
			// 设置标准 HTTP/1.0 no-cache header. 
			response.setHeader("Pragma", "no-cache");
			//
			response.setDateHeader("Expires", 0);
			
			User user = Utils.getCurrentUser();
			if(user!=null){
				String interfaceName = user.isCorp() ? "OpenPayCorpSelect" : "OpenPaySelect";
				FormData data = FormDataUtil.createInputForm(interfaceName);
				FormDataUtil.setProperty(data, "memberid", user.getMemberId());
				data = ServiceClient.getResponseFormData(data, interfaceName);
				
				String memberstates = String.valueOf(FormDataUtil.getProperty(data, "memberstate"));
				String custacno = String.valueOf(FormDataUtil.getProperty(data, "custacno"));				
				String custname = String.valueOf(FormDataUtil.getProperty(data, "custname"));
				String paperid;
				String custid;
                if(user.getMemberType().equals("0")){
                	paperid = String.valueOf(FormDataUtil.getProperty(data, "paperid"));
                	custid = String.valueOf(FormDataUtil.getProperty(data, "custid"));
				}else{
					paperid = String.valueOf(FormDataUtil.getProperty(data, "leadcustpaperid"));
					custid = String.valueOf(FormDataUtil.getProperty(data, "memberid"));
				}				
				user.setMemberState(memberstates);
				user.setCustAcNo(custacno);
				user.setCustId(custid);
				user.setCustName(custname);
				user.setPaperId(paperid);
			}
			
			request.setAttribute(Constants.CURRENT_USER,user);
		}
		
	}

	/**
	 * 在执行controller 方法之前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		Anon anon = (Anon) session.getAttribute(Constants.CURRENT_ANON);
		if(null == anon){
			anon = new Anon();
			String ip = StringUtils.trimToEmpty(Utils.getIpAddr(request));
			String agent = request.getHeader("User-agent");
			anon.setSessionId(request.getSession().getId());
			anon.setIp(ip);
			anon.setBrowser(StringUtils.trimToEmpty(BrowseTool.checkBrowse(agent)));
			session.setAttribute(Constants.CURRENT_ANON, anon);
		}
		return true;
	}
}
