package com.jkgroup.kingkaid.service.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 添加ajax未登录请求的处理
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年7月27日 下午6:57:40
 */

public class KkdFormAuthenticationFilter extends FormAuthenticationFilter {

	private static final Logger log = LoggerFactory.getLogger(KkdFormAuthenticationFilter.class);
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }
            
            String xheader = WebUtils.toHttp(request).getHeader("x-requested-with");
            if("XMLHttpRequest".equalsIgnoreCase(xheader)) {
            	WebUtils.toHttp(response).sendError(401);
            } else {
            	saveRequestAndRedirectToLogin(request, response);
            }
            
            return false;
        }
    }
	
}
