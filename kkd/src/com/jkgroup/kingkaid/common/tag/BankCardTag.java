package com.jkgroup.kingkaid.common.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

public class BankCardTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cardId;
	
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@Override
	public int doEndTag() throws JspException {
		String formatNum = "";
		try {
			if(StringUtils.isNotBlank(cardId)){
				formatNum = cardId.substring(0, 4)+"**********"+cardId.substring(cardId.length()-4, cardId.length());
			}
			pageContext.getOut().write(formatNum);
		} catch (IOException e) {
			
		}
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}

}
