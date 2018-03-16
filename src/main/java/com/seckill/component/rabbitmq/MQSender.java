package com.seckill.component.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seckill.component.redis.RedisService;
import com.seckill.pojo.vo.SeckillMQVo;


@Service
public class MQSender {
	
	@Autowired
	AmqpTemplate amqpTemplate;
	
	private static Logger log = LoggerFactory.getLogger(MQSender.class);
	
//	public void send(Object obj) {
//		String msg = RedisService.beanToString(obj);
//		log.info(msg);
//		amqpTemplate.convertAndSend(MQConfig.QUEUE,msg);
//	}

	public void sendSeckillMQ(SeckillMQVo mm) {
		String msg = RedisService.beanToString(mm);
		amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
	}
	
	
}
