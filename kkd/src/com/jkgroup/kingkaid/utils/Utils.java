package com.jkgroup.kingkaid.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;

import com.jkgroup.kingkaid.bo.User;

/**
 * 全局工具类
 * @author pan
 *
 */
public class Utils {

	/**
	 * 获取当前用户
	 * @return
	 */
	public static User getCurrentUser(){
		if(SecurityUtils.getSubject() != null 
				&& SecurityUtils.getSubject().getPrincipals() != null){
			User user = SecurityUtils.getSubject().getPrincipals().oneByType(User.class);
			return user;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取登录IP
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		// 如果客户端经过多级反向代理，则X-Forwarded-For的值并不止一个，而是一串IP值，
		// 取X-Forwarded-For中第一个非unknown的有效IP字符串即为用户真实IP
		String ipResult = getIp(ip);
		if (ipResult != null) {
			return ipResult;
		}

		ip = request.getHeader("Proxy-Client-IP");
		ipResult = getIp(ip);
		if (ipResult != null) {
			return ipResult;
		}
		ip = request.getHeader("WL-Proxy-Client-IP");
		ipResult = getIp(ip);
		if (ipResult != null) {
			return ipResult;
		}
		ip = request.getRemoteAddr();
		ipResult = getIp(ip);
		if (ipResult != null) {
			return ipResult;
		}
		return "";
	}
	
	private static String getIp(String ip){
		try {
			if(StringUtils.isBlank(ip)){
				return null;
			}
			
			if(ip.contains(",")){
				return ip.split(",")[0];
			}
			
			return ip;
		} catch (Exception e) {
			
		}
		return null;
	}
}
