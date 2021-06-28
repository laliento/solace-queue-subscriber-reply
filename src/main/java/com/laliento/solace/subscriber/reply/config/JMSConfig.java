package com.laliento.solace.subscriber.reply.config;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laliento.solace.subscriber.reply.exception.CustomJmsErrorHandler;
import com.laliento.solace.subscriber.reply.utils.Constants;
import com.solacesystems.jms.SolConnectionFactory;
import com.solacesystems.jms.SolJmsUtility;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class JMSConfig {

	@Autowired
	private SolaceProperties solaceProperties;

	@Bean(Constants.JMS_REQUEST)
	public JmsTemplate jmsRequest() {
		JmsTemplate jms = new JmsTemplate();
		jms.setConnectionFactory(getConnectionFactory());
		jms.setReceiveTimeout(solaceProperties.getReceiveTimeOut());
		jms.setMessageConverter(getMessageConverter());
		return jms;
	}

	private MessageConverter getMessageConverter() {
		final MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setObjectMapper(new ObjectMapper());
		return converter;
	}

	private ConnectionFactory getConnectionFactory() {
		try {
//			Connection
			SolConnectionFactory cf = SolJmsUtility.createConnectionFactory();
			cf.setHost(solaceProperties.getBroker());
			cf.setVPN(solaceProperties.getVpn());
			cf.setUsername(solaceProperties.getUser());
			cf.setPassword(solaceProperties.getPwd());
			cf.setSSLKeyStore(solaceProperties.getSslUrl());
			cf.setSSLTrustStorePassword(solaceProperties.getSslPass());
			cf.setSSLValidateCertificate(Boolean.valueOf(solaceProperties.getSslEnable()));
//			CachingConnection
			CachingConnectionFactory ccf = new CachingConnectionFactory(cf);
			ccf.setSessionCacheSize(solaceProperties.getSessionCacheSize());
//			Listener and ErrorHandler
			DefaultJmsListenerContainerFactory jlc = new DefaultJmsListenerContainerFactory();
			jlc.setConnectionFactory(cf);
			jlc.setSessionTransacted(Boolean.FALSE);
			jlc.setErrorHandler(new CustomJmsErrorHandler());
			jlc.setConcurrency(solaceProperties.getConcurrency());
			jlc.setReceiveTimeout(Long.valueOf(solaceProperties.getReceiveTimeOut()));
			return ccf;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getCause().getMessage(), e);
			return null;
		}
	}

}
