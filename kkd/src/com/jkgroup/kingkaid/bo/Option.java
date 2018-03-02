package com.jkgroup.kingkaid.bo;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年6月15日 下午8:59:39
 */

public class Option {

	private String value;
	private String text;

	@XmlAttribute
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@XmlAttribute
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
