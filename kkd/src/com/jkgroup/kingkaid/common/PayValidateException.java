package com.jkgroup.kingkaid.common;

/**
 *
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年9月28日 上午9:53:50
 */

public class PayValidateException extends RuntimeException {

	private static final long serialVersionUID = 4473479530519315876L;

	public PayValidateException(String message, Throwable cause) {
		super(message, cause);
	}

	public PayValidateException(String message) {
		super(message);
	}
	
}
