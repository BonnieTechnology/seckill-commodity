package com.seckill.component.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MQConfig {
	
	public static final String QUEUE = "SECKILL";
	
	@Bean
	public Queue queue() {
		return new Queue(QUEUE,true);
	}

}
