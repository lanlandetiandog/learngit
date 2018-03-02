package com.jkgroup.kingkaid.common;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @CreateDate 2015-03-25
 * @author pan
 *
 */
public class PropertiesUtil {
	
	private static PropertiesUtil instance = null;
	
	private Properties p = null;
	
	private final static Logger log = LoggerFactory.getLogger(PropertiesUtil.class);
	
	/**
	 * 报文服务地址
	 */
	public static final String SERVICE_URI = "SERVICE_URI";
	
	/**
	 * 文件服务地址
	 */
	public static final String FILE_URI = "FILE_URI";
	/**
	 * RA服务地址
	 */
	public static final String RA_URL = "RA_URL";
	
	/**
	 * RA服务地址
	 */
	public static final String MOBILE_DOWN = "MOBILE_DOWN";
	
	/**
	 * SHARE_URI分享中间页
	 */
	public static final String SHARE_URI = "SHARE_URI";
	
	/**
	 * 
	 */
	private PropertiesUtil(){
		if( p == null){
			p = new Properties();
			log.debug("加载application.properties文件...");
			InputStream in = new BufferedInputStream(PropertiesUtil.class
					.getResourceAsStream("/application.properties"));
			try {
				p.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取实例
	 * @return
	 */
	public static PropertiesUtil getIntance(){
		if(instance == null){
			instance = new PropertiesUtil();
		}
		return instance;
	}
	
	/**
	 * 获取文件服务接口URI
	 * @return
	 */
	public static String getFileUri() {
		return getValueByKey(FILE_URI);
	}
	
	/**
	 * 获取报文服务接口URI
	 * @return
	 */
	public static String getServicesUri() {
		return getValueByKey(SERVICE_URI);
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	private static String getValueByKey(String key) {
		PropertiesUtil util = PropertiesUtil.getIntance();
		return util.p.getProperty(key);
	}
	/**
	 * RA服务地址
	 * @param key
	 * @return
	 */
	public static String getRAUri() {
		return getValueByKey(RA_URL);
	}
	
	/**
	 * 本地URL地址
	 * @param key
	 * @return
	 */
	public static String getMobileUrl() {
		return getValueByKey(MOBILE_DOWN);
	}
	
	/**
	 * SHARE_URI分享中间页
	 */
	public static String getSHAREUri(){
		return getValueByKey(SHARE_URI);
	}
}

