package com.laliento.solace.subscriber.reply.service;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import com.laliento.solace.subscriber.reply.config.SolaceProperties;
import com.laliento.solace.subscriber.reply.exception.JmsAPiException;
import com.laliento.solace.subscriber.reply.utils.Constants;
import com.laliento.solace.subscriber.reply.utils.JmsUtils;

@Service
public abstract class ProcesorService<S, R> {

	@Autowired
	JmsTemplate jmsReply;

	@Autowired
	SolaceProperties solaceProperties;

	public void processComplete(final Message message, final MessageHeaders jmsHeaders, Class<S> subscriber)
			throws JmsAPiException {
		String uuid;
		try {
			uuid = message.getJMSCorrelationID();
			if (StringUtils.isAllBlank(uuid))
				throw new JmsAPiException(new IllegalArgumentException(), Constants.MESSAGE_UNPROCESABLE_TO_OBJECT);
		} catch (JMSException e1) {
			e1.printStackTrace();
			throw new JmsAPiException(e1, Constants.MESSAGE_UNPROCESABLE_TO_OBJECT);
		}

		try {
			String cache = message.getStringProperty(Constants.CACHE_KEY);
			S input = (S) JmsUtils.messageToObject(message, uuid, subscriber);
			R reply = process(input);
			System.out.println(uuid);
			jmsReply.convertAndSend(solaceProperties.getQueueReply(), reply, msj -> {
				msj.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
				msj.setJMSCorrelationID(uuid);
				msj.setStringProperty(Constants.CACHE_KEY, cache);
				msj.setJMSTimestamp(System.currentTimeMillis());
				msj.setJMSType("LalientoEvent");
				msj.setJMSPriority(Message.DEFAULT_PRIORITY);
				msj.setBooleanProperty(Constants.IS_XML, false);
				return msj;
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new JmsAPiException(e, String.format(Constants.MESSAGE_UNPROCESABLE_TO_OBJECT, uuid));
		}

	}

	protected abstract R process(S input);

}
