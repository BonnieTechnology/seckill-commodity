package com.seckill.service;

import com.seckill.pojo.Order;
import com.seckill.pojo.SeckillItem;
import com.seckill.pojo.User;

public interface OrderService {

	Order getOrderBySeckillIdUserId(int seckillId, int id);

	Order createOrder(User user, SeckillItem item);

	Order getOrderByOrderId(String orderId);
	

}
