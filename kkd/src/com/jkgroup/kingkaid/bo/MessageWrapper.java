package com.jkgroup.kingkaid.bo;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年6月9日 上午9:18:24
 */

public class MessageWrapper {
	
	public final static String SYS_ERROR_CODE = "SYS";
	public final static String SYS_ERROR_MSG = "系统异常,请稍后再试";

	private boolean status;
	private String message;
	private Object body;
	private Map<String, Object> annex;
	
	public MessageWrapper() {
		super();
	}
	
	public MessageWrapper(boolean status) {
		super();
		this.status = status;
	}
	
	public MessageWrapper(boolean status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}
	public void addAnnex(String key, Object value) {
		if(annex == null) {
			annex = new HashMap<String, Object>();
		}
		annex.put(key, value);
	}
	public void removeAnnex(String key) {
		if(annex != null && annex.containsKey(key)) {
			annex.remove(key);
		}
	}
	public Map<String, Object> getAnnex() {
		return annex;
	}
	public void setAnnex(Map<String, Object> annex) {
		this.annex = annex;
	}
	
}
