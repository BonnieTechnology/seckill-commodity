package com.seckill.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seckill.component.redis.OrderPrefix;
import com.seckill.component.redis.RedisService;
import com.seckill.exception.GlobleException;
import com.seckill.mapper.OrderMapper;
import com.seckill.pojo.Order;
import com.seckill.pojo.OrderItem;
import com.seckill.pojo.SeckillItem;
import com.seckill.pojo.User;
import com.seckill.service.OrderService;


@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderMapper orderMapper;
	
	@Autowired
	RedisService redisService;

	@Override
	public Order getOrderBySeckillIdUserId(int seckillId, int id) {
		Order order = orderMapper.getOrderBySeckillIdUserId(seckillId, id);
		return order;
	}

	@Transactional
	public Order createOrder(User user, SeckillItem item) {
		//生成订单号：用户id+时间
		String orderId = ""+user.getId()+System.currentTimeMillis();
		
		Order order = new Order();
		order.setOrderId(orderId);
		order.setBuyerNick(user.getNickName());
		order.setCreateTime(new Date());
		order.setPayment(""+item.getSeckillPrice());
		order.setStatus(1);
		order.setUserId(user.getId());
		order.setSeckillId(item.getSeckillId());
		
		int row = orderMapper.crateOrder(order);
		if(row != 1) {
			throw new GlobleException(501, "下单失败！");
		}
		
		OrderItem orderItem = new OrderItem();
		orderItem.setItemId(""+item.getSeckillId());
		orderItem.setNum(1);
		orderItem.setOrderId(orderId);
		orderItem.setPicPath(item.getItemImage());
		orderItem.setPrice(0L+item.getSeckillPrice());
		orderItem.setTitle(item.getItemTitle());
		orderItem.setTotalFee(""+orderItem.getPrice());
		
		int rows = orderMapper.insertOrderItem(orderItem);
		if(rows != 1) {
			throw new GlobleException(501, "下单失败！");
		}
		
		redisService.set(OrderPrefix.order, orderId, order);
		return order;
	}

	@Override
	public Order getOrderByOrderId(String orderId) {
		// TODO Auto-generated method stub
		return orderMapper.getOrderByOrderId(orderId);
	}



}
