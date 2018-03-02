package com.jkgroup.kingkaid.utils.pay;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

/**
 * 生成签名数据字符串
 * @author pan
 *
 */
public class ChkValueUtil {

	/**
	 * 生成签名数据字符串
	 * @param param
	 * @param cmdId
	 * @return
	 */
	public static String getMsgData(String[] param){
		String msgData = "";
		for(String str : param){
			String value = StringUtils.trimToEmpty(str);
			msgData += value;
		}
		return msgData;
	}
	
	/**
	 * 城市签名数据字符串
	 * @param paramMap
	 * @param param
	 * @return
	 */
	public static String getMsgData(Map<String,String> paramMap,String[] param){
		String msgData = "";
		for(Entry<String, String> entry : paramMap.entrySet()){
			for(String str : param){
				if(entry.getKey().equals(str)){
					msgData += entry.getValue();
				}
			}
		}
		return msgData;
	}
}
