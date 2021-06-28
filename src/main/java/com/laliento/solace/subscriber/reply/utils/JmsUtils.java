package com.laliento.solace.subscriber.reply.utils;

import java.util.UUID;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laliento.solace.subscriber.reply.exception.JmsAPiException;

import lombok.SneakyThrows;

public class JmsUtils {

	private JmsUtils() {
	}

	private static ObjectMapper mapper = new ObjectMapper();

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	@SneakyThrows
	public static String objectToString(Object obj, boolean pretty) {
		return pretty ? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj)
				: mapper.writeValueAsString(obj);
	}

	@SneakyThrows
	public static String objectToString(Object obj) {
		return mapper.writeValueAsString(obj);
	}

	@SneakyThrows
	public static <T> T stringToObject(String input, Class<T> inputClass) {
		return mapper.readValue(input, inputClass);
	}

	public static <T> T messageToObject(Message msjReply, String uuid, Class<T> inputClass) throws JmsAPiException {
		try {
			if (msjReply instanceof BytesMessage) {
				BytesMessage byteMessage = (BytesMessage) msjReply;
				byte[] byteData = null;
				byteData = new byte[(int) byteMessage.getBodyLength()];
				byteMessage.readBytes(byteData);
				byteMessage.reset();
				String stringMessage = new String(byteData);
				return stringToObject(stringMessage, inputClass);
			} else if (!(msjReply instanceof TextMessage))
				throw new Exception(Constants.REPLY_PAYLOAD_INVALID);
			String msjJson = ((TextMessage) msjReply).getText();
			return stringToObject(msjJson, inputClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JmsAPiException(e, String.format(Constants.MESSAGE_UNPROCESABLE_TO_OBJECT, uuid));
		}
	}

	public static <T> T waithResponseByUUID(JmsTemplate jmsReply, String queueReply, String uuid, Class<T> inputClass)
			throws JmsAPiException {
		Message msjReply = jmsReply.receiveSelected(queueReply, "JMSCorrelationID='" + uuid + "'");
		return messageToObject(msjReply, uuid, inputClass);
	}
}
