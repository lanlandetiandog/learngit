package com.jkgroup.kingkaid.common.spring;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.jkgroup.kingkaid.common.WebMessageConfig;
import com.jkgroup.kingkaid.utils.bo.MessageKey;
import com.jkgroup.kingkaid.utils.xml.ParseMIXml;

/**
 * @CreateDate 2015-03-25
 * @author pan
 *
 */
@Component
public class PostInitializerRunner implements ApplicationListener<ContextRefreshedEvent> {

	private static Logger logger = LoggerFactory.getLogger(PostInitializerRunner.class);
	
	private static boolean inited = false;
	
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(!inited){
			WebMessageConfig.getInstance();
//			buildFormData();

			inited = true;
		}
		
	}

	/**
	 * 一次性初始化所有FormData对象
	 */
	public void buildFormData(){
		logger.debug(" FormData 对象开始生成...");
		
		long beginTime  = new Date().getTime();
		//一次性初始化所有 class FormDate 对象 
		Map<String, MessageKey> map = WebMessageConfig.getInstance().getMap();
		
		Set<String> set = map.keySet();
		
		for(String str : set){
			ParseMIXml.buildFormData(str);
		}
		
		long endTime  = new Date().getTime();
		
		logger.debug(" FormData对象完成构建 , 耗时【{}:ms】 - 【{}:s】  生成数量 : "+set.size()+"对 ",(endTime - beginTime),(endTime - beginTime)/1000);
	}
}