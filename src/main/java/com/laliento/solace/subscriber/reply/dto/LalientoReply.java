package com.laliento.solace.subscriber.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LalientoReply {
	String nameReply;
	String ocuppationReply;
}
