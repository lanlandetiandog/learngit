package com.jkgroup.kingkaid.service.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.PathMatchingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.utils.Utils;


public class SysUserFilter extends PathMatchingFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(SysUserFilter.class);
	
	@Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		logger.info(" session cache user ... ");
		request.setAttribute(Constants.CURRENT_USER, Utils.getCurrentUser());
        return true;
    }
	
}
