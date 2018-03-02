package com.jkgroup.kingkaid.service.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class PermsAuthorizationFilter extends AuthorizationFilter{

	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
    	boolean result = false;
        Subject subject = getSubject(request, response);
        String[] permsArray = (String[]) mappedValue;
        if (permsArray == null || permsArray.length == 0) {
            return true;
        }
        for(String perm : permsArray){
        	if(subject.isPermitted(perm)){
        		result = true;
        		break;
        	}
        }
        return result;
    }

}
