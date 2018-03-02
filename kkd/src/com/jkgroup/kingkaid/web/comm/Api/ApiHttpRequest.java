package com.jkgroup.kingkaid.web.comm.Api;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class ApiHttpRequest {

	private static final int TIMEOUT = 300000;
	private static Logger logger = LoggerFactory.getLogger(ApiHttpRequest.class);
	
	
	public static String httpRequestHome(HttpServletRequest request){
		String json ="";
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
		httpClient.getParams().setParameter("http.protocol.cookie-policy",CookiePolicy.BROWSER_COMPATIBILITY);
		httpClient.getParams().setSoTimeout(TIMEOUT);

		String token = request.getParameter("token");
		String date = request.getParameter("date");
		String pageNo = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		
		
//		PostMethod postMethod =new PostMethod("http://localhost:9000/member/api/p2phome/getProjectsByDate");
		PostMethod postMethod =new PostMethod("http://172.16.16.66:8080/api/p2phome/getProjectsByDate");
//		PostMethod postMethod =new PostMethod("http://xdc.kingkaid.com/api/p2phome/getProjectsByDate");
		postMethod.setParameter("token", token);
		postMethod.setParameter("date", date);
		postMethod.setParameter("pageNo", pageNo);
		postMethod.setParameter("pageSize", pageSize);

		
		int statusCode;
		try {
			statusCode = httpClient.executeMethod(postMethod);
			if(statusCode!=HttpStatus.SC_OK){

				logger.error(" HTTP Request Exception Status : {} ",statusCode);
			}else{
				json = postMethod.getResponseBodyAsString();
				logger.debug(json);
				System.out.println(json);
				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
