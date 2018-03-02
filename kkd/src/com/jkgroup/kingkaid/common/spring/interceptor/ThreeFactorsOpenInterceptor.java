package com.jkgroup.kingkaid.common.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.utils.Utils;

public class ThreeFactorsOpenInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		User user = Utils.getCurrentUser();
		if (user != null && !user.isCorp() && "2".equals(user.getIsSignCtp())) {
			response.sendRedirect(request.getContextPath() + "/auth/cust/openpay3f_page.html");
			return false;
		} else {
			return true;
		}
	}

}
