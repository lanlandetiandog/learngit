package com.jkgroup.kingkaid.bo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年6月16日 上午9:15:05
 */
@XmlRootElement
public class All {

	List<Options> options;

	public List<Options> getOptions() {
		return options;
	}

	public void setOptions(List<Options> options) {
		this.options = options;
	}

}
