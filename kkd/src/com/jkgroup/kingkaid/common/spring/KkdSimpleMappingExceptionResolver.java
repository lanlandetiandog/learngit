package com.jkgroup.kingkaid.common.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 自定义未捕获异常处理类
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年12月14日 下午10:06:31
 */

public class KkdSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

	public static final Logger log = LoggerFactory.getLogger(KkdSimpleMappingExceptionResolver.class); 
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		log.error("Uncaught Exception", ex);
		String xheader = WebUtils.toHttp(request).getHeader("x-requested-with");
        if("XMLHttpRequest".equalsIgnoreCase(xheader)) {
        	applyStatusCodeIfPossible(request, response, 500);
        	return null;
        } else {
        	return super.doResolveException(request, response, handler, ex);
        }
	}
}
