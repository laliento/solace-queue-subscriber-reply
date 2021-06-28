package com.laliento.solace.subscriber.reply.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class JmsAPiException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String error;

	public JmsAPiException(Throwable throwable, String error) {
		super(throwable);
		this.error = error;
	}
}
