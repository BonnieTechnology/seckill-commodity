package com.seckill.component.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seckill.component.redis.RedisService;
import com.seckill.pojo.Order;
import com.seckill.pojo.SeckillItem;
import com.seckill.pojo.User;
import com.seckill.pojo.vo.SeckillMQVo;
import com.seckill.service.ItemService;
import com.seckill.service.OrderService;
import com.seckill.service.SeckillService;


@Service
public class MQReceiver {

	@Autowired
	ItemService itemService;
	@Autowired
	OrderService orderService;
	@Autowired
	SeckillService seckillService;

	private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

	@RabbitListener(queues=MQConfig.QUEUE)
	public void receive(String msg) {
		log.info(msg);
		SeckillMQVo mm = RedisService.StringToBean(msg, SeckillMQVo.class);
		User user = mm.getUser();
		Integer seckillId = mm.getSeckillId();
		
		SeckillItem seckillitem = itemService.getSeckillItemById(seckillId);
		int maxNum = seckillitem.getMaxNum();
		if(maxNum <= 0) {
			return;
		}
		//是否已经生成了订单
		Order order = orderService.getOrderBySeckillIdUserId(seckillitem.getSeckillId(),user.getId());
		if(order != null) {
			return;
		}

		//下单
		Order seckillOrder = seckillService.seckill(user,seckillitem);
	}

}
