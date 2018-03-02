package com.jkgroup.kingkaid.utils.xml;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.Anon;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.Array;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.bo.formdata.Range;
import com.jkgroup.kingkaid.common.WebMessageConfig;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.bo.Page;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

public class RequestXmlBuilder {

	private static final Logger logger = LoggerFactory.getLogger(RequestXmlBuilder.class);
	
	private static final String BEGIN_ELEMENT = "<message xmlns=\"http://www.webafx.org/ns/lws/1.0\">";
	
	private static final String END_ELEMENT = "</message>";
	
	private static final String PAGE_ANNEX_BEGIN_ELEMENT = "<annex type=\"http://www.webafx.org/datatypes/annex#RandomPaging\">";
	
	private static final String ANNEX_END_ELEMENT = "</annex>";
	
	private static final String SESSION_ANNEX_BEGIN_ELEMENT = "<annex type=\"http://www.bmsoft.com/datatypes/annex#EBank\">";
	
	private static final String LOG_ANNEX_BEGIN_ELEMENT = "<annex type=\"http://www.bmsoft.com/datatypes/annex#ELog\">";
	
	private static final String BODY_BEGIN_ELEMENT = "<d>";
	
	private static final String BODY_END_ELEMENT = "</d>";
	
	private static final String RANGE_BODY_BEGIN = "<c>";
	
	private static final String RANGE_BODY_END = "</c>";
	
	private static final String NULL_ELEMENT = "<null/>";
	
	private static final String ARRAY_BEGIN = "<array>";
	
	private static final String ARRAY_END = "</array>";
	
	private static final String RANGE_BEGIN = "<range>";
	
	private static final String RANGE_END = "</range>";
	
	/**
	 * 
	 * @param formData
	 * @param page
	 * @return
	 */
	public static String buildXml(String keyName,FormData formData,Page page){
		StringBuffer requestMi = new StringBuffer();
		Field[] fields = formData.getClass().getDeclaredFields();
		for(Field field : fields){
			if(field.getType().getName().equals(String.class.getName())){
				Object fieldValue = FormDataUtil.getProperty(formData, field.getName());
				if(null != fieldValue){
					requestMi.append(BODY_BEGIN_ELEMENT+fieldValue.toString()+BODY_END_ELEMENT);
				}else{
					requestMi.append(NULL_ELEMENT);
				}
			}else if(field.getType().getName().equals(Range.class.getName())){
				String fieldName = "";
				Range range = (Range) FormDataUtil.getProperty(formData, field.getName());
				if(null != range){
					String beginDate = range.getBeginDate();
					String endDate = range.getEndDate();
					fieldName=RANGE_BEGIN
							+ (StringUtils.isBlank(beginDate) ? NULL_ELEMENT : RANGE_BODY_BEGIN+beginDate+RANGE_BODY_END)
							+ (StringUtils.isBlank(endDate) ? NULL_ELEMENT : RANGE_BODY_BEGIN+endDate+RANGE_BODY_END)
							+RANGE_END;
					requestMi.append(fieldName);
				}else{
					requestMi.append(NULL_ELEMENT);
				}
			}else if(field.getType().getName().equals(Array.class.getName())){
				String fieldName = "";
				Array array = (Array) FormDataUtil.getProperty(formData, field.getName());
				if(null != array && array.size() > 0){
					fieldName = ARRAY_BEGIN;
					for(int i = 0 ; i <array.size() ; i++){
						fieldName += BODY_BEGIN_ELEMENT+array.get(i)+BODY_END_ELEMENT;
					}
					fieldName += ARRAY_END;
					requestMi.append(fieldName);
				}else{
					requestMi.append(NULL_ELEMENT);
				}
			} else if(field.getType().getName().equals(Date.class.getName())) {
				Date fieldValue = (Date) FormDataUtil.getProperty(formData, field.getName());
				if(null != fieldValue){
					requestMi.append(BODY_BEGIN_ELEMENT+DateUtils.getStrFromMDatetime(fieldValue)+BODY_END_ELEMENT);
				}else{
					requestMi.append(NULL_ELEMENT);
				}
			}
		}
		return insertMessage(keyName,requestMi,page);
	}
	
	/**
	 * 转换请求报文
	 * @param fieldNames
	 * @return
	 */
	public static String buildXml(String keyName,String[] fieldNames,Page page){
		StringBuffer requestMi = new StringBuffer();
		for(String fieldName : fieldNames){
			if(StringUtils.isNotBlank(fieldName)){
				requestMi.append(BODY_BEGIN_ELEMENT+fieldName+BODY_END_ELEMENT);
			}else{
				requestMi.append(NULL_ELEMENT);
			}
		}
		return insertMessage(keyName,requestMi,page);
	}
	
	/**
	 * 转换请求报文
	 * @param formData
	 * @return
	 */
	public static String buildXml(String keyName,List<String> list,Page page){
		StringBuffer requestMi = new StringBuffer();
		for(String str : list){
			if(StringUtils.isNotBlank(str)){
				requestMi.append(BODY_BEGIN_ELEMENT+str+BODY_END_ELEMENT);
			}else{
				requestMi.append(NULL_ELEMENT);
			}
		}
		return insertMessage(keyName,requestMi,page);
	}
	
	/**
	 * 转换请求报文
	 * @param formData
	 * @return
	 */
	public static String buildXmlBy(String keyName,Map<String,String> map,Page page){
		StringBuffer requestMi = new StringBuffer();
		for(Entry<String, String> entry : map.entrySet()){
			if(StringUtils.isNotBlank(entry.getValue())){
				requestMi.append(BODY_BEGIN_ELEMENT+entry.getValue()+BODY_END_ELEMENT);
			}else{
				requestMi.append(NULL_ELEMENT);
			}
		}
		return insertMessage(keyName,requestMi,page);
	}
	
	/**
	 * 请求报文权限、分组、分页信息
	 * @return
	 */
	private static String getPageAnnex(Page page){
		StringBuffer annex = new StringBuffer();
		if(page != null){
			annex.append(PAGE_ANNEX_BEGIN_ELEMENT);
			annex.append(BODY_BEGIN_ELEMENT).append(page.getPageSize()).append(BODY_END_ELEMENT);
//			annex.append(BODY_BEGIN_ELEMENT).append(page.getTotalItem()).append(BODY_END_ELEMENT);
			// 总条数是每次请求后台都实时查出的，传入无意义，仅作接收只用，所以传NULL
			annex.append(NULL_ELEMENT);
			annex.append(ARRAY_BEGIN);
			annex.append(page.getArray());
			annex.append(ARRAY_END);
			annex.append(BODY_BEGIN_ELEMENT).append(page.getStartRowNo()).append(BODY_END_ELEMENT);
			annex.append(ANNEX_END_ELEMENT);
		}
		return annex.toString();
	}
	
	/**
	 * 接口访问权限控制信息
	 * @return
	 */
	private static String getSessionAnnex(){
		StringBuffer annex = new StringBuffer();
		annex.append(SESSION_ANNEX_BEGIN_ELEMENT);
		annex.append(BODY_BEGIN_ELEMENT).append("90000").append(BODY_END_ELEMENT);
		annex.append(BODY_BEGIN_ELEMENT).append("99999").append(BODY_END_ELEMENT);
		annex.append(BODY_BEGIN_ELEMENT).append("111111").append(BODY_END_ELEMENT);
		annex.append(ANNEX_END_ELEMENT);
		return annex.toString();
	}
	
	/**
	 * 接口访问权限控制信息
	 * @return
	 */
	private static String getLogAnnex(String keyName){
		StringBuffer annex = new StringBuffer();
		annex.append(LOG_ANNEX_BEGIN_ELEMENT);
		User user = Utils.getCurrentUser();
		String tradeDesc = WebMessageConfig.get(keyName).getLabel();
		if(null == user){
			Anon anon = (Anon) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_ANON);
			if(null == anon){
				for(int i = 0 ; i <10 ;i++){
					annex.append(NULL_ELEMENT);					
				}
			}else{
				annex.append(NULL_ELEMENT);
				annex.append(NULL_ELEMENT);
				annex.append(NULL_ELEMENT);
				annex.append(BODY_BEGIN_ELEMENT).append(anon.getIp()).append(BODY_END_ELEMENT);
				annex.append(BODY_BEGIN_ELEMENT).append(anon.getChannel()).append(BODY_END_ELEMENT);
				annex.append(NULL_ELEMENT);
				annex.append(NULL_ELEMENT);
				annex.append(NULL_ELEMENT);
				annex.append(BODY_BEGIN_ELEMENT).append(anon.getBrowser()).append(BODY_END_ELEMENT);
				annex.append(NULL_ELEMENT);
			}
			annex.append(BODY_BEGIN_ELEMENT).append(keyName).append(BODY_END_ELEMENT);
			annex.append(BODY_BEGIN_ELEMENT).append(StringUtils.trimToEmpty(tradeDesc)).append(BODY_END_ELEMENT);
		}else{
			annex.append(BODY_BEGIN_ELEMENT).append(user.getMemberId()).append(BODY_END_ELEMENT);
			annex.append(BODY_BEGIN_ELEMENT).append(user.getMemberName()).append(BODY_END_ELEMENT);
			annex.append(BODY_BEGIN_ELEMENT).append(user.getCustId()).append(BODY_END_ELEMENT);
			annex.append(BODY_BEGIN_ELEMENT).append(user.getIp()).append(BODY_END_ELEMENT);
			annex.append(BODY_BEGIN_ELEMENT).append(user.getChannel()).append(BODY_END_ELEMENT);
			annex.append(NULL_ELEMENT);
			annex.append(NULL_ELEMENT);
			annex.append(NULL_ELEMENT);
			annex.append(BODY_BEGIN_ELEMENT).append(user.getBrowser()).append(BODY_END_ELEMENT);
			annex.append(NULL_ELEMENT);
			annex.append(BODY_BEGIN_ELEMENT).append(keyName).append(BODY_END_ELEMENT);
			annex.append(BODY_BEGIN_ELEMENT).append(StringUtils.trimToEmpty(tradeDesc)).append(BODY_END_ELEMENT);
		}
		annex.append(ANNEX_END_ELEMENT);
		return annex.toString();
	}
	
	private static String insertMessage(String keyName,StringBuffer xml,Page page){
		xml.insert(0, BEGIN_ELEMENT);
		if(page != null){
			xml.insert(xml.length(), getPageAnnex(page));
		}
		xml.insert(xml.length(), getSessionAnnex());
		xml.insert(xml.length(), getLogAnnex(keyName));
		xml.insert(xml.length(), END_ELEMENT).toString();					
		logger.debug("【{}】请求报文 : {} ",keyName,xml);
		return xml.toString();
	}

}
