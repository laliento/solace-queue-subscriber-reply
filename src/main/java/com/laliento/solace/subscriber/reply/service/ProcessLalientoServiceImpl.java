package com.laliento.solace.subscriber.reply.service;

import org.springframework.stereotype.Service;

import com.laliento.solace.subscriber.reply.dto.LalientoReply;
import com.laliento.solace.subscriber.reply.dto.LalientoSubscriber;

@Service
public class ProcessLalientoServiceImpl extends ProcesorService<LalientoSubscriber, LalientoReply> {

	@Override
	public LalientoReply process(LalientoSubscriber input) {
		return LalientoReply.builder().nameReply(input.getNamePublish() + "-updated")
				.ocuppationReply(input.getOcuppationPublish() + "-updated").build();
	}

}
