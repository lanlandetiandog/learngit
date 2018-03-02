package com.jkgroup.kingkaid.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @CreateDate 2015-03-25
 * @author pan
 *
 */
@Component
public class ApplicationContextSpring implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	
	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		if(applicationContext == null){
			this.applicationContext = ctx;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanId){
		T obj = null;
		if(applicationContext != null){
			obj = (T)applicationContext.getBean(beanId);
		}
		
		return obj;
	}

}
