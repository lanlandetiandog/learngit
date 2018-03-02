package com.jkgroup.kingkaid.utils.http;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostHttpClient {

	private static final Logger logger = LoggerFactory.getLogger(PostHttpClient.class);

	private static final int TIMEOUT = 300000;
	
	
	/**
	 * POST报文请求
	 * @param url
	 * @param requestParam
	 * @return
	 */
	public static String postRequest(String url, String requestParam) {
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
		httpClient.getParams().setParameter("http.protocol.cookie-policy",
				CookiePolicy.BROWSER_COMPATIBILITY);
		httpClient.getParams().setSoTimeout(TIMEOUT);
		PostMethod postMethod = UTF8PostMethod.getPostMethod(url);
		try {
			postMethod.setRequestEntity(new ByteArrayRequestEntity(requestParam.getBytes("UTF-8"), "xml"));
		} catch (Exception e1) {
			e1.printStackTrace();
			return "<message><status code=\"404\">数据加密错误..</status></message>";
		}
		int statusCode;
		String data = "";
		try {
			statusCode = httpClient.executeMethod(postMethod);
			// 请求处理成功
			if (statusCode == HttpStatus.SC_OK) {
				data = new String(postMethod.getResponseBody(), "utf-8");
			} else {
				data = "<message><status code=\""+ statusCode + "\">网络通信故障..</status></message>";
			}
		} catch (HttpException e) {
			data = "<message><status code=\"404\">无法连接服务器..</status></message>";
		} catch (Exception e) {
			data = "<message><status code=\"404\">数据读取错误..</status></message>";
		} finally {
			postMethod.releaseConnection();
		}
		return StringUtils.trim(data);
	}

	/**
	 * mi报文GET请求
	 * 返回mi报文格式
	 * @param url
	 * @return
	 */
	public static String getRequest(String url) {
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		String html = "";
		int statusCode;
		try {
			statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: {}", getMethod.getStatusLine());
				html = "<message-interface><status code=\""+ statusCode + "\">"+statusCode+"网络通信故障..</status></message-interface>";
			}else{
				// 读取内容
				byte[] responseBody = IOUtils.toByteArray(getMethod.getResponseBodyAsStream());
				// 处理内容
				html = new String(responseBody,"UTF-8");
			}
		} catch (HttpException e) {
			logger.error(" HttpException ",e.getMessage());
			html = "<message-interface><status code=\"404\">无法连接服务器..</status></message-interface>";
		} catch (IOException e) {
			logger.error(" IOException ",e.getMessage());
			html = "<message-interface><status code=\"404\">数据读取错误..</status></message-interface>";
		}finally{
			getMethod.releaseConnection();
		}
		return html;
	}

}
