package com.jkgroup.kingkaid.utils.cls;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

/**
 * java反射工具类
 * @author pan
 *
 */
public class ReflectUtil {

	/**
	 * 执行get方法取值
	 * @param formData
	 * @return
	 */
	public static String invoke(Object obj,String fieldName){
		String value = "";
		try {
			fieldName = StringUtils.upperCase(fieldName.substring(0,1))+fieldName.substring(1, fieldName.length());
			Method method = obj.getClass().getMethod("get" + fieldName);
			value = method.invoke(obj).toString();
		} catch (Exception e) {
			return "";
		} 
		return value;
	}
	
	/**
	 * 执行set方法赋值
	 * @param obj
	 * @param fieldName
	 * @param value
	 */
	public static void invoke(Object obj,String fieldName,String value){
		try {
			fieldName = StringUtils.upperCase(fieldName.substring(0,1))+fieldName.substring(1, fieldName.length());
			Method method = obj.getClass().getMethod("set" + fieldName);
			method.invoke(obj,value);
		} catch (Exception e) {
			
		} 
	}
}
