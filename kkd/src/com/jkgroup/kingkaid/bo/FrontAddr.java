package com.jkgroup.kingkaid.bo;

/**
 * 前台地址包装类
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年6月15日 下午7:34:42
 */

public class FrontAddr {
	
	private String code;
	private String block;
	
	public FrontAddr() {
		super();
	}
	
	public FrontAddr(String code, String block) {
		super();
		this.code = code;
		this.block = block;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	
}
