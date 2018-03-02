package com.jkgroup.kingkaid.utils.http;

import org.apache.commons.httpclient.methods.PostMethod;

/**
 * POST构造请求数据
 * @author pd
 *
 */
public class UTF8PostMethod extends PostMethod {

	/**
	 * 
	 * @param url
	 */
	public UTF8PostMethod(String url) {
		super(url);
	}

	/**
	 * 设置默认编码格式为UTF-8
	 */
	@Override
	public String getRequestCharSet() {
		return "UTF-8";
	}
	
	/**
	 * 创建postMethod 
	 * @return
	 */
	public static PostMethod getPostMethod(String url) {
		return new UTF8PostMethod(url);
	}
}
