package com.jkgroup.kingkaid.utils.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象
 * @author pan
 *
 */
public class Page<T> {

	//每页显示条数--JS控件--小贷后台
	private Integer pageSize;
	
	//当前页数--JS控件
	private Integer pageNo = 0;
	
	//总页数--空闲
	private Integer pageTotal;
	
	//总条数--JS控件--小贷后台
	private Integer totalItem;
	
	//前一页总条数--小贷后台
	private Integer startRowNo;
	
	private List<T> result = new ArrayList<T>();
	
	private String array;	//order by 
	
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}

	public Integer getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(Integer totalItem) {
		this.totalItem = totalItem;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public String getArray() {
		return array;
	}

	public void setArray(String array) {
		this.array = array;
	}

	public Integer getStartRowNo() {
		return startRowNo;
	}

	public void setStartRowNo(Integer startRowNo) {
		this.startRowNo = startRowNo;
	}
	
	/**
	 * 由前台JS控件传参构建调用后台服务需要的Page对象
	 * @param pageSize 每页条数
	 * @param pageNo 当前页（就是需要展示的那一页）
	 * @return
	 */
	public static <T> Page<T> buildPageFromRequest(int pageSize, int pageNo) {
		Page<T> page = new Page<T>();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		page.setStartRowNo(pageSize * pageNo);
		return page;
	}
	
}
