package com.laliento.solace.subscriber.reply.utils;

public class Constants {

	private Constants() {}
	
	public static final String JMS_REQUEST = "jmsRequest";
	public static final String JMS_REPLY = "jmsReply";
	public static final String MESSAGE_UNPROCESABLE_TO_OBJECT = "Message unprocesable to object. uuid --> %s";
	public static final String MESSAGE_UNPROCESABLE_NO_UUID = "The message hasn't uuid.";
	public static final String REPLY_PAYLOAD_INVALID = "Reply Message invalid type";
	public static final String CACHE_KEY = "cacheKey";
	public static final String IS_XML = "JMS_SOLACE_isXML";
	
}
