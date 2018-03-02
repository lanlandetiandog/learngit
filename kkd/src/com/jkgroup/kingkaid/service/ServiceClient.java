package com.jkgroup.kingkaid.service;

import java.io.IOException;
import java.text.DateFormat;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.common.WebMessageConfig;
import com.jkgroup.kingkaid.utils.bo.MessageKey;
import com.jkgroup.kingkaid.utils.bo.Page;
import com.jkgroup.kingkaid.utils.http.PostHttpClient;
import com.jkgroup.kingkaid.utils.xml.ParseMIXml;
import com.jkgroup.kingkaid.utils.xml.RequestXmlBuilder;
import com.jkgroup.kingkaid.utils.xml.ResponseXmlParse;

public class ServiceClient{

	/**
	 * 接口访问通用方法<br/>
	 * <b>返回单一对象FormData</b>
	 * @param params
	 * @param keyName
	 * @return
	 */
	public static FormData getResponseFormData(FormData formData,String keyName){
		return ResponseXmlParse.parseXmlToObject(buildResponseXml(formData,keyName,null), keyName);
	}
	
	/**
	 * 接口访问通用方法<br/>
	 * <b>返回单一对象FormData</b>
	 * @param params
	 * @param keyName
	 * @return
	 */
	public static List<FormData> getResponseFormDataList(FormData formData,String keyName){
		return ResponseXmlParse.parseXmlToList(buildResponseXml(formData,keyName,null), keyName);
	}
	
	/**
	 * 接口访问通用方法<br/>
	 * <b>返回单一对象FormData</b>
	 * @param params
	 * @param keyName
	 * @return
	 */
	public static Page<FormData> getResponseFormDataPage(FormData formData,String keyName,Page page){
		return ResponseXmlParse.parseXmlToPage(buildResponseXml(formData,keyName,page), keyName);
	}
	
	/**
	 * 接口访问通用方法<br/>
	 * <b>返回单一对象FormData</b>
	 * @param params
	 * @param keyName
	 * @return
	 */
	public static FormData getResponseFormData(String[] params,String keyName){
		ParseMIXml.buildFormData(keyName);
		return ResponseXmlParse.parseXmlToObject(buildResponseXml(params,keyName,null), keyName);
	}
	
	/**
	 * 接口访问通用方法<br/>
	 * <b>返回多个对象List<FormData></b>
	 * @param params
	 * @param keyName
	 * @return
	 */
	public static List<FormData> getResponseFormList(String[] params,String keyName){
		ParseMIXml.buildFormData(keyName);
		return ResponseXmlParse.parseXmlToList(buildResponseXml(params,keyName,null), keyName);
	}
	
	/**
	 * 接口访问通用方法<br/>
	 * <b>返回多个对象List<FormData></b>
	 * @param params
	 * @param keyName
	 * @return
	 */
	public static Page<FormData> getResponseFormPage(String[] params,String keyName,Page page){
		ParseMIXml.buildFormData(keyName);
		return ResponseXmlParse.parseXmlToPage(buildResponseXml(params,keyName,page), keyName);
	}
	
	/**
	 * 转化对象为Json字符串
	 * @param t
	 * @return
	 */
	public static <T> String parseObjToJson(T t) {
		String json = "";
		try {
			json = new ObjectMapper().writeValueAsString(t);
		} catch (JsonGenerationException e) {
			json = "";
		} catch (JsonMappingException e) {
			json = "";
		} catch (IOException e) {
			json = "";
		}
		json = json.replace("null", "\"\"");
		return json;
	}
	
	/**
	 * 转化对象为Json字符串
	 * @param t
	 * @param dateFormat
	 * 			自定义的日期输出格式
	 * @return
	 */
	public static <T> String parseObjToJson(T t, DateFormat dateFormat) {
		String json = "";
		try {
			ObjectWriter writer = new ObjectMapper().writer(dateFormat);
			json = writer.writeValueAsString(t);
		} catch (JsonGenerationException e) {
			json = "";
		} catch (JsonMappingException e) {
			json = "";
		} catch (IOException e) {
			json = "";
		}
		json = json.replace("null", "\"\"");
		return json;
	}
	
	/**
	 * 根据请求参数统一构造报文请求
	 * @param params
	 * @param keyName
	 * @return
	 */
	private static String buildResponseXml(String[] params,String keyName,Page page){
		String paramXml = RequestXmlBuilder.buildXml(keyName,params,page);
		MessageKey messagekey = WebMessageConfig.getInstance().get(keyName);
		String message = messagekey.getMessage();
		String responseXml = PostHttpClient.postRequest(message,paramXml);
		return responseXml;
	}
	
	/**
	 * 根据Input输入报文对象统一构造报文请求
	 * @param params
	 * @param keyName
	 * @return
	 */
	private static String buildResponseXml(FormData formData,String keyName,Page page){
		String paramXml = RequestXmlBuilder.buildXml(keyName,formData,page);
		MessageKey messagekey = WebMessageConfig.getInstance().get(keyName);
		String message = messagekey.getMessage();
		String responseXml = PostHttpClient.postRequest(message,paramXml);
		return responseXml;
	}
}
