package com.laliento.solace.subscriber.reply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class SolaceQueueSubscriberReplyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolaceQueueSubscriberReplyApplication.class, args);
	}

}
