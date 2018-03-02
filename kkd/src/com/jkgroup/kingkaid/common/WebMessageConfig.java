package com.jkgroup.kingkaid.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jkgroup.kingkaid.utils.bo.MessageKey;

/**
 * 解析message-config.XML
 * @author pan
 */
public class WebMessageConfig {

	private Document doc = null;
	
	private static final Map<String, MessageKey> map = new HashMap<String,MessageKey>();
	
	private static WebMessageConfig install = null;
	
	private final static Logger log = LoggerFactory.getLogger(WebMessageConfig.class);
	
	private WebMessageConfig(){
		init();
	}

	public static WebMessageConfig getInstance(){
		if(install == null){
			install = new WebMessageConfig();				
		}
		return install;
	}
	
	private void init(){
		if (doc == null) {
			SAXReader reader = new SAXReader();
			try {
				log.debug("加载web-message-config.xml文件...");
				doc = reader.read(getClass().getResourceAsStream(
						"/config/web-message-config.xml"));
				parseConfig();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 解析XML
	 */
	@SuppressWarnings("unchecked")
	private void parseConfig(){
		String servicesUri = PropertiesUtil.getServicesUri();
		List<Element> elements = doc.selectNodes("/config/key");
		
		if(null != elements && elements.size() > 0){
			for(Element ele : elements){
				MessageKey key = new MessageKey();
				key.setName(ele.attributeValue("name"));
				key.setMessage(servicesUri+"/xml/"+ele.getText());
				key.setMi(servicesUri+"/xmlmi/"+ele.getText()+".mi");
				key.setLabel(StringUtils.trimToEmpty(ele.attributeValue("label")));
				map.put(ele.attributeValue("name"), key);
			}
		}
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static MessageKey get(String name){
		return map.get(name);
	}
	
	public Map<String,MessageKey> getMap(){
		return map;
	}
}
