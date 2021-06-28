package com.laliento.solace.subscriber.reply.exception;

import org.springframework.util.ErrorHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomJmsErrorHandler implements ErrorHandler{

	@Override
	public void handleError(Throwable t) {
		log.error(t.getCause().getMessage(),t);
	}

}
