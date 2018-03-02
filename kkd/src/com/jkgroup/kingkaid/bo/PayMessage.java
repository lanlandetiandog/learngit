package com.jkgroup.kingkaid.bo;

import java.util.Date;
import java.util.Map;

import com.jkgroup.kingkaid.utils.pay.PayUtil.PayChannel;
import com.jkgroup.kingkaid.utils.pay.PayUtil.PayOption;

/**
 *
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年10月13日 上午11:00:44
 */

public class PayMessage {
	
	private PayChannel channel;
	private PayOption option;
	private Date optionTime;
	private String listId;
	private boolean async;
	private boolean status;
	private Map<String, String> rawData;
	
	public PayMessage(PayChannel channel, boolean isAsync, Map<String, String> rawData) {
		super();
		this.channel = channel;
		this.rawData = rawData;
		this.async = isAsync;
		this.optionTime = new Date();
	}
	
	public PayChannel getChannel() {
		return channel;
	}
	public void setChannel(PayChannel channel) {
		this.channel = channel;
	}
	public PayOption getOption() {
		return option;
	}
	public void setOption(PayOption option) {
		this.option = option;
	}
	public Date getOptionTime() {
		return optionTime;
	}
	public void setOptionTime(Date optionTime) {
		this.optionTime = optionTime;
	}
	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Map<String, String> getRawData() {
		return rawData;
	}
	public void setRawData(Map<String, String> rawData) {
		this.rawData = rawData;
	}
	public boolean isAsync() {
		return async;
	}
	public void setAsync(boolean async) {
		this.async = async;
	}
	
}
