package com.jkgroup.kingkaid.utils.bo;

/**
 * 报文交易接口
 * @author pan
 *
 */
public class MessageKey {

	//报文交易名称
	private String name;
	
	//报文地址
	private String message;
	
	//mi文件
	private String mi;
	
	private String label;
	
	public MessageKey(){}
	
	public MessageKey(String name,String message,String mi){
		this.name = name;
		this.message = message;
		this.mi = mi;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMi() {
		return mi;
	}

	public void setMi(String mi) {
		this.mi = mi;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
