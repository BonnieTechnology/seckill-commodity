package com.seckill.service;

import java.awt.image.BufferedImage;

import com.seckill.pojo.Order;
import com.seckill.pojo.SeckillItem;
import com.seckill.pojo.User;


public interface SeckillService {

	Order seckill(User user, SeckillItem item);

	Order getOrderById(String orderId);

	BufferedImage createVerifyCode(User user, int itemId);

	boolean checkVerifyCode(User user, long seckillId, int verifyCode);

	String createMiaoshaPath(User user, long seckillId);

	long getSeckillResult(int id, int itemId);

	boolean checkPath(User user, int itemId, String path);
	
	

}
