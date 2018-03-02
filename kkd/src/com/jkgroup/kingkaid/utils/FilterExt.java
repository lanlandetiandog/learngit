package com.jkgroup.kingkaid.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 请求参数过滤
 * @author pan
 *
 */
public class FilterExt implements java.io.Serializable{
	
	private String filterName;
	
	private String value;

	/**
	 * @param filterName
	 * @param value
	 */
	public FilterExt(final String filterName, final String value) {
		this.filterName  = filterName;
		this.value = value;
	}
	
	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 从HttpRequest中创建FilterExt列表, 默认Filter属性名前缀为filter.
	 * 
	 * @see #buildFromHttpRequest(HttpServletRequest, String)
	 */
	public static List<FilterExt> buildFromHttpRequest(
			final HttpServletRequest request) {
		return buildFromHttpRequest(request, "filter");
	}

	/**
	 * 从HttpRequest中创建FilterExt列表
	 * FilterExt命名规则为Filter_属性名.
	 */
	private static List<FilterExt> buildFromHttpRequest(
			final HttpServletRequest request, final String filterPrefix) {
		List<FilterExt> filterList = new ArrayList<FilterExt>();

		// 从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
		Map<String, Object> filterParamMap = getParametersStartingWith(request, filterPrefix + "_");

		// 分析参数Map,构造PropertyFilter列表
		for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
			String filterName = entry.getKey();
			Object v = entry.getValue();
			String[] values = new String[]{};
			if( v instanceof String[]){
				values = (String[]) v;
			}else{
				values = new String[]{(String)v};
			}
			for(String val : values){
//				String value = (String) entry.getValue();
				// 如果value值为空,则忽略此filter.
				if (StringUtils.isNotBlank(val)) {
					FilterExt filter = new FilterExt(filterName, val);
					filterList.add(filter);
				}
			}
			
		}

		return filterList;
	}
	
	/**
	 * 取得带相同前缀的Request Parameters.
	 * 
	 * 返回的结果的Parameter名已去除前缀.
	 */
	public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
		if(null == request){
			throw new IllegalArgumentException("Request must not be null");
		}
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}
}
