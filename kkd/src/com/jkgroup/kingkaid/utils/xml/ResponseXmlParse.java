package com.jkgroup.kingkaid.utils.xml;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.bo.Page;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

public class ResponseXmlParse {
	
	public static final String FIELD = "d";
	
	public static final String STATUS = "status";
	
	public static final String RECORD = "record";
	
	public static final String RECORDSET = "recordset";
	
	public static final String ANNEX = "annex";
	
	public static final String NAMESPACE = " xmlns=\"http://www.webafx.org/ns/lws/1.0\"";
	
	private static final Logger logger = LoggerFactory.getLogger(ResponseXmlParse.class);
			
	/**
	 * 解析返回xml报文
	 * @param xml
	 * @return 
	 */
	public static FormData parseXmlToObject(String xml,String keyName){		
		
		if(StringUtils.isBlank(xml)){
			return null;
		}
		
		xml = StringUtils.replace(xml, NAMESPACE, "");
		
		logger.debug("【{}】响应XML报文 : {}",keyName,xml);
		
		FormData formData = FormDataUtil.getObjByPackClassName(FormDataUtil.FORM_ENTITY_BASE_PACKAGE,FormData.OUTPUT+keyName);
		try {
			Document doc = DocumentHelper.parseText(xml);
			Element rootElt = doc.getRootElement();
			List<Element> elements = rootElt.elements();
			for(Element ele : elements){
				String nodeName = ele.getName();
				if(nodeName.equals(FIELD)){
					//处理单条记录
					formData = setProperty(ele.elements(),keyName);
				}else if(nodeName.equals(STATUS)){
					error(formData,ele);
				}else if(nodeName.equals(RECORD)){
					//处理单条记录
					formData = setProperty(ele.elements(),keyName);
				}
			}
			
		} catch (DocumentException e) {
			logger.error(" 错误的xml文件...",e);
			FormDataUtil.setProperty(formData, "respcode", FormData.FAILURE);
			FormDataUtil.setProperty(formData, "respdesc", "解析返回报文出错");
		}
		return formData;
	}
	

	
	/**
	 * 解析返回报文
	 * @param xml
	 * @return List<FormData>
	 */
	public static List<FormData> parseXmlToList(String xml,String keyName){
		if(StringUtils.isBlank(xml)){
			return null;
		}
		
		xml = StringUtils.replace(xml, NAMESPACE, "");
		
		logger.debug("【{}】响应XML报文 : {}",keyName,xml);
		
		List<FormData> formDatas = new ArrayList<FormData>();
		try {
			Document doc = DocumentHelper.parseText(xml);
			List<Element> subElements = doc.selectNodes("/message/recordset/record");
			if(null != subElements && subElements.size() > 0){
				for(Element ele : subElements){
					FormData formData = setProperty(ele.elements(),keyName);
					formDatas.add(formData);
				}				
			}
		} catch (DocumentException e) {
			logger.error(" 错误的xml文件...",e);
		}
		return formDatas;
	}
	
	/**
	 * 解析返回报文
	 * @param xml
	 * @return
	 */
	public static Page<FormData> parseXmlToPage(String xml,String keyName){
		if(StringUtils.isBlank(xml)){
			return null;
		}

		xml = StringUtils.replace(xml, NAMESPACE, "");
		
		logger.debug("【{}】响应XML报文 : {}",keyName,xml);
		
		Page<FormData> page = new Page<FormData>();
		List<FormData> formDatas = page.getResult();
		try {
			Document doc = DocumentHelper.parseText(xml);			
			List<Element> subElements = doc.selectNodes("/message/recordset/record");
			for(Element ele : subElements){
				FormData formData = setProperty(ele.elements(),keyName);
				formDatas.add(formData);
			}

			List<Element> annexEles = doc.selectNodes("/message/recordset/annex");
			for(Element ele : annexEles){
				setPage(page, ele);
			}
		} catch (DocumentException e) {
			logger.error(" 错误的xml文件...",e);
		}
		return page;
	}
	
	/**
	 * 错误报文处理
	 * @param formData
	 * @param ele
	 */
	private static void error(FormData formData,Element ele){
		String code = StringUtils.trimToEmpty(StringUtils.isBlank(ele.attributeValue("code"))?"":ele.attributeValue("code"));
		String msg = StringUtils.trimToEmpty(StringUtils.isBlank(ele.getText())?"":ele.getText());
		
		if(StringUtils.isNotBlank(code) && code.length() >1 && code.indexOf(".")!=-1)
			code = code.substring(code.lastIndexOf("."), code.length());
		
		if(StringUtils.isNotBlank(msg) && msg.length() > 50)
			msg = msg.substring(0,50);
		
		FormDataUtil.setProperty(formData, "respcode", code);
		FormDataUtil.setProperty(formData, "respdesc", msg);
	}
	
	/**
	 * formdata 赋值 执行set方法
	 * @param cls
	 * @param formData
	 * @param subEles
	 * @return
	 */
	private static FormData setProperty(List<Element> subEles,String keyName){
		FormData formData = FormDataUtil.getObjByPackClassName(FormDataUtil.FORM_ENTITY_BASE_PACKAGE,FormData.OUTPUT+keyName);
		Field[] fields = formData.getClass().getDeclaredFields();
		for(int i = 0 ;i < fields.length ; i++){
			Field field = fields[i];
			Element element = subEles.get(i);
			if(field != null && element != null){
				if(field.getType().equals(Date.class)) {
					Date date = DateUtils.parseDateFromBmStr(element.getText());
					if(date != null) {
						FormDataUtil.setProperty(formData, field.getName(), date);
					}
				} else {
					FormDataUtil.setProperty(formData, field.getName(), element.getText());
				}
			}
		}
		return formData;
	}
	
	/**
	 * 设置分页信息
	 * @param page
	 * @param subEles
	 * @return
	 */
	private static Page<FormData> setPage(Page<FormData> page,Element elements){
		List<Element> eles = elements.elements();
		//每页显示数
		Integer pageSize = Integer.parseInt(eles.get(0).getText());
		//总条数
		Integer totalItem = Integer.parseInt(eles.get(1).getText());
		//已查询多少条
		Integer queryTotal = Integer.parseInt(eles.get(3).getText());
		//共多少页
		Integer pageTotal = totalItem/pageSize + 1 ;
		Integer pageNo = queryTotal/pageSize <= 0 ? queryTotal/pageSize+1 : queryTotal/pageSize;
		
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setPageTotal(pageTotal);
		page.setTotalItem(totalItem);
		
		return page;
	}
	
	public static void main(String[] args) {
		
	}
}
