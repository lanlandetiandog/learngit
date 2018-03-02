package com.jkgroup.kingkaid.bo;

/**
 * 匿名用户信息
 * @author pan
 *
 */
public class Anon {

	private String sessionId = "";
	
	private String ip = "";
	
	private String channel = "02";		//访问渠道
	
	private String os = "";
	
	private String browser = "";
	
	private String browserVersion = "";

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}
}
