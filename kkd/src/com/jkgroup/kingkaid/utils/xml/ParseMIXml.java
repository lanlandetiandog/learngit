package com.jkgroup.kingkaid.utils.xml;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jkgroup.kingkaid.bo.formdata.FieldType;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.common.WebMessageConfig;
import com.jkgroup.kingkaid.utils.cache.MessageInterfaceCache;
import com.jkgroup.kingkaid.utils.cls.EntityClassWriter;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.http.PostHttpClient;

/**
 * 组织请求Input对象 与 Output对象
 * @author pan
 * @Create Date 2015-03-27
 */
public class ParseMIXml {
	
	public static final String MESSAGE_INTERFACE = "message-interface";
	
	public static final String INPUT = "input";
	
	public static final String OUTPUT = "output";
	
	public static final String RECORD = "record";
	
	public static final String RECORDSET = "recordset";
	
	public static final String FIELD = "field";
	
	private static final Logger logger = LoggerFactory.getLogger(ParseMIXml.class);
	
	/**
	 * 生成输入输入formdata
	 * @param keyName
	 */
	@SuppressWarnings("static-access")
	public static void buildFormData(/*String xml,*/String keyName){
		
		//查询是否有缓存
		if(MessageInterfaceCache.contains(keyName)){
			return;
		}
		
		String miURI = WebMessageConfig.getInstance().get(keyName).getMi();
		String xml = PostHttpClient.getRequest(miURI);
		
		logger.debug(" miuri : {} response : {}", miURI, xml);
		
		parseInput(xml,keyName);
		parseOutput(xml,keyName);
		
	}
	
	/**
	 * 生成输入报文InputForm
	 */
	private static void parseInput(String xml,String keyName){
		List<Element> elements = parseText(xml,INPUT);
		createCls(elements,keyName,INPUT);
	}
	
	/**
	 * 生成输出报文OutputForm
	 */
	private static void parseOutput(String xml,String keyName){
		List<Element> elements = parseText(xml,OUTPUT);
		createCls(elements,keyName,OUTPUT);
	}
	
	/**
	 * 返回输入/输出报文节点
	 * @param xml
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<Element> parseText(String xml,String type){
		List<Element> elements = null;
		try {
			xml = StringUtils.trimToEmpty(xml);
			Document doc = DocumentHelper.parseText(xml);
			if(type.equals(INPUT)){
				elements = doc.selectNodes("/"+MESSAGE_INTERFACE+"/"+type+"/"+FIELD);				
			}else{
				elements = doc.selectNodes("/"+MESSAGE_INTERFACE+"/"+type+"/"+FIELD);
				
				if(null == elements || elements.size() <=0){
					elements = doc.selectNodes("/"+MESSAGE_INTERFACE+"/"+type+"/"+RECORD+"/"+FIELD);
				}
				
				if(null == elements || elements.size() <=0){
					elements = doc.selectNodes("/"+MESSAGE_INTERFACE+"/"+type+"/"+RECORDSET+"/"+FIELD);
				}
			}
		} catch (DocumentException e) {
			logger.error("解析mi报文错误 ！{} ",e.getMessage());
			return null;
		}
		return elements;
	}
	
	/**
	 * 生成CLASS文件
	 */
	private static void createCls(List<Element> fieldEles,String keyName,String type){
		if(null != fieldEles && fieldEles.size() > 0){			
			List<String> fields = new ArrayList<String>();
			List<FieldType> types = new ArrayList<FieldType>();
			for(int i = 0 ; i < fieldEles.size() ; i++){
				String field = StringUtils.trim(StringUtils.lowerCase(fieldEles.get(i).attributeValue("name")));
				if(fieldEles.get(i).attributeValue("type").equalsIgnoreCase("date{}")){
					types.add(FieldType.Range);
				}else if(fieldEles.get(i).attributeValue("type").toLowerCase().matches("date(\\(.*\\))?")) {
					types.add(FieldType.Date);
				}else if(fieldEles.get(i).attributeValue("type").equalsIgnoreCase("string[]")){
					types.add(FieldType.Array);
				}else{
					types.add(FieldType.String);				
				}
				fields.add(field);
			}
			
			if(type.equals(INPUT)){
				keyName = "Input"+keyName;
			}else{
				keyName = "Output"+keyName;
			}
			
			EntityClassWriter.build(keyName, fields,types);			
			
			FormData cls = FormDataUtil.getObjByPackClassName(FormDataUtil.FORM_ENTITY_BASE_PACKAGE,keyName);
			
			keyName = keyName.replaceAll("Input", "");
			keyName = keyName.replaceAll("Output", "");
			
			if(!MessageInterfaceCache.contains(keyName)){
				MessageInterfaceCache.put(keyName, cls.getClass().getName());
			}
		}
	}
	
}
