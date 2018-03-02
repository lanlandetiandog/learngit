package com.jkgroup.kingkaid.utils.cls;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jkgroup.kingkaid.bo.MessageWrapper;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.utils.FilterExt;
import com.jkgroup.kingkaid.utils.xml.ParseMIXml;

/**
 * FormData工具类
 * @author pan
 *
 */
public class FormDataUtil extends ClassLoader{

	private static final Logger logger = LoggerFactory.getLogger(FormDataUtil.class);
	
	//自定义实体的默认包路径
	public static final String FORM_ENTITY_BASE_PACKAGE = "com.jkgroup.kingkaid.bo.formdata";
	
	//public static final String CLASSPATH = new File(FormDataUtil.class.getResource("/").getPath())+"";
	public static final String CLASSPATH = FormDataUtil.class.getClassLoader().getResource("/").getPath();
	
	public FormDataUtil() {
	}

	public FormDataUtil(ClassLoader parentLoader) {
		super(parentLoader);
	}  
	
	/**
	 * 根据包名和类名获取类型
	 * 
	 * @param <T>
	 * @param packagePath
	 * @param className
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClazzByPackClassName(String packagePath ,String className){
		String entityName = packagePath + "." + className;
		Class<T> clazz = null;
		try {
			clazz = (Class<T>)Class.forName(entityName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}
	
	/**
	 * 根据包名和类名获取实例
	 * @param <T>
	 * @param packagePath
	 * @param className
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getObjByPackClassName(String packagePath ,String className){
		String entityName = packagePath + "." + className;
		T obj = null;
		try {
			Class<T> clazz = (Class<T>)Class.forName(entityName);
			obj = clazz.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	/** 
     * 加载某个类 
     * @param c 
     * @return 
     * @throws IOException 
     */  
    @SuppressWarnings( { "unchecked" })  
    public <T> Class<T> loadClassByClassName(String className){
        byte[] bs = null;
		try {
			bs = loadByteCode(className);
		} catch (IOException e) {
			e.printStackTrace();
		}  
        Class<T> clazz = (Class<T>)super.defineClass(className, bs, 0, bs.length);
        return clazz;  
    }  
  
    /** 
     * 加载某个类的字节码 
     * @param c 
     * @return 
     * @throws IOException 
     */  
	public byte[] loadByteCode(String className) throws IOException {  
        int iRead = 0;  
        String path = CLASSPATH + "/" + className.replaceAll("\\.", "/") + ".class";
  
        FileInputStream in = null;  
        ByteArrayOutputStream buffer = null;  
        try {  
            in = new FileInputStream(path);  
            buffer = new ByteArrayOutputStream();  
            while ((iRead = in.read()) != -1) {  
                buffer.write(iRead);  
            }  
            return buffer.toByteArray();  
        } finally { 
        	if(in != null)
            in.close();
        	if(buffer != null)
            buffer.close();
        }  
    }
    
    /**
     * 执行get方法获取属性值
     * @param formData 
     * @param field
     * @return
     */
    public static Object getProperty(FormData formData,String field){
    	Object value = null;
    	try {
			value = PropertyUtils.getProperty(formData, StringUtils.lowerCase(field));
		} catch (IllegalAccessException e) {
			logger.error(" 执行set方法错误，不能访问对象或对象不存在 .....  ",e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(" 执行set方法错误，不能访问对象或对象不存在 .....  ",e.getMessage());
		} catch (NoSuchMethodException e) {
			logger.error(" 执行get方法错误，该方法不存在 .....  ",e.getMessage());
		}
    	return value;
    }
    
    /**
     * 打印formdata属性值
     * @param formData
     */
    public static void print(FormData formData){
//		if (formData != null) {
//			Field[] fields = formData.getClass().getDeclaredFields();
//			logger.info(" print  -----------------------");
//			for (Field field : fields) {
//				logger.info(" name : {}  -  value : {}", field.getName(),
//						getProperty(formData, field.getName()));
//			}
//		} else {
//			logger.info(" formDat is null ");
//		}
    }
    
    /**
     * 执行get方法获取属性值
     * @param formData 
     * @param field
     * @return
     */
    public static void setProperty(FormData formData,String field,Object value){
    	try {
    		field = StringUtils.lowerCase(field);  		
			BeanUtils.setProperty(formData, field,value);
		} catch (IllegalAccessException e) {
			logger.error(" 执行set方法错误，不能访问对象或对象不存在 .....  ",e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(" 执行set方法错误，不能访问对象或对象不存在 .....  ",e.getMessage());
		}
    }
    
    /**
     * 创建InputFormData对象
     * @param keyName
     * @return
     */
    public static FormData createInputForm(String keyName){
    	ParseMIXml.buildFormData(keyName);
    	return FormDataUtil.getObjByPackClassName(FormDataUtil.FORM_ENTITY_BASE_PACKAGE,FormData.INPUT+keyName);
    }
    
    /**
     * 根据过滤器构造FormData
     * @param filters
     * @param keyName
     * @return
     */
    public static FormData buildFormDataByFilterExt(final List<FilterExt> filters,String keyName){
    	FormData formData = createInputForm(keyName);
    	for(FilterExt filter : filters){
    		setProperty(formData, filter.getFilterName(), filter.getValue());
    	}
    	return formData;
    }
    
    /**
     * 判断是否成功
     * @param formData
     * @return
     */
    public static boolean isSucceed(FormData formData) {
    	return FormDataUtil.getProperty(formData, "respcode").equals("S");
    }
    
    /**
     * 创建失败的MessageWrapper实例
     * @param formData
     * @return
     */
    public static MessageWrapper buildFailedMsgWrapper(FormData formData) {
    	MessageWrapper mw = new MessageWrapper();
    	mw.setStatus(false);
    	String fdCode = (String) getProperty(formData, "respcode");
    	String fdMsg = (String) getProperty(formData, "respdesc");
    	// 判断是自定义异常还是系统异常
    	if(fdCode.length() >= 6 && fdCode.substring(fdCode.length() - 6).matches("[a-zA-Z]{3}\\d{3}||[a-zA-Z]{2}\\d{4}")) {
//    		mw.setCode(fdCode.substring(fdCode.length() - 6));
    		mw.setMessage(fdMsg);
    	} else {
//    		mw.setCode(MessageWrapper.SYS_ERROR_CODE);
    		mw.setMessage(MessageWrapper.SYS_ERROR_MSG);
    	}
		return mw;
    }
    
    /**
     * 获取错误信息
     * @param formData
     * @return
     */
    public static String getErrorMessage(FormData formData) {
    	String fdCode = (String) getProperty(formData, "respcode");
    	String fdMsg = (String) getProperty(formData, "respdesc");
    	// 判断是自定义异常还是系统异常
    	if(fdCode.length() >= 6 && fdCode.substring(fdCode.length() - 6).matches("[a-zA-Z]{3}\\d{3}||[a-zA-Z]{2}\\d{4}")) {
    		fdCode = fdCode.substring(fdCode.length() - 6);
    		return fdMsg;
    	} else {
    		return MessageWrapper.SYS_ERROR_MSG;
    	}
    }
    
}
