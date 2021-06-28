package com.laliento.solace.subscriber.reply.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "solace.msproperties")
public class SolaceProperties {

	private String broker;
	private String vpn;
	private String user;
	private String pwd;
	private String sslEnable;
	private String sslUrl;
	private String sslPass;
	private String concurrency;
	private int sessionCacheSize;
	private String queueRequest;
	private String queueReply;
	private int receiveTimeOut;
}
