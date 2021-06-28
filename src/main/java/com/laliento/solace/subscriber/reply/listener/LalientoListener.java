package com.laliento.solace.subscriber.reply.listener;

import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.laliento.solace.subscriber.reply.dto.LalientoReply;
import com.laliento.solace.subscriber.reply.dto.LalientoSubscriber;
import com.laliento.solace.subscriber.reply.exception.JmsAPiException;
import com.laliento.solace.subscriber.reply.service.ProcesorService;

@Component
public class LalientoListener {

	@Autowired
	ProcesorService<LalientoSubscriber, LalientoReply> procesor;

	@JmsListener(destination = "${solace.msproperties.queueRequest}")
	public void process(final Message message, final MessageHeaders jmsHeaders) {
		try {
			procesor.processComplete(message, jmsHeaders, LalientoSubscriber.class);
		} catch (JmsAPiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
