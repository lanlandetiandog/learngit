package com.jkgroup.kingkaid.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.jkgroup.kingkaid.utils.bo.Page;

/**
 * 分页对象
 * @author pan
 * @create data 2015-04-01
 */
public class PageUtil {

	public static final int PAGE_SIZE = 10;
	
	/**
	 * 构造分页对象
	 * @param request
	 * @return
	 */
	public static Page pageRequest(HttpServletRequest request){
		String pageSize = request.getParameter("pageSize");
		String pageNo = request.getParameter("pageNo");
		String array = request.getParameter("array");
		String totalItem = request.getParameter("totalItem");
		
		Page page = new Page();
		if(StringUtils.isBlank(pageNo))
			return null;
		
		page.setPageNo(Integer.parseInt(pageNo));
		page.setPageSize(PAGE_SIZE);

		page.setTotalItem(0);
		
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize));
		
		
		if(StringUtils.isNotBlank(array))
			page.setArray(array);
		
		if(StringUtils.isNotBlank(totalItem))
			page.setTotalItem(Integer.parseInt(totalItem));
		
		return page;
	}
}
