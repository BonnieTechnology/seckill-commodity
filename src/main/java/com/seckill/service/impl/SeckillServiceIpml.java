package com.seckill.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seckill.common.util.MD5Util;
import com.seckill.common.util.UUIDUtil;
import com.seckill.component.redis.ItemPrefix;
import com.seckill.component.redis.RedisService;
import com.seckill.component.redis.SeckillPrefix;
import com.seckill.exception.GlobleException;
import com.seckill.pojo.Order;
import com.seckill.pojo.SeckillItem;
import com.seckill.pojo.User;
import com.seckill.service.OrderService;
import com.seckill.service.SeckillItemService;
import com.seckill.service.SeckillService;


@Service
public class SeckillServiceIpml implements SeckillService {

	@Autowired
	SeckillItemService seckillItemService;
	@Autowired
	OrderService orderService;

	@Autowired
	RedisService redisService;

	@Transactional
	public Order seckill(User user, SeckillItem item) {
		//减库存
		int row = seckillItemService.reduceStock(item);
		if(row == 1) {
			//添加订单
			Order order = orderService.createOrder(user,item);
			return order;
		}
		//表示卖完了
		setItemOver(item.getSeckillId());
		throw new GlobleException(501, "下单失败！");
	}

	private void setItemOver(int seckillId) {
		redisService.set(ItemPrefix.isOver, ""+seckillId, true);
	}

	@Override
	public Order getOrderById(String orderId) {

		return orderService.getOrderByOrderId(orderId);
	}

	//验证码
	@Override
	public BufferedImage createVerifyCode(User user, int itemId) {
		if(user == null || itemId <= 0) {
			return null;
		}
		int width = 80;
		int height = 32;
		//create the image
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		// set the background color
		g.setColor(new Color(0xDCDCDC));
		//背景颜色的填充
		g.fillRect(0, 0, width, height);
		// 画了矩形框
		g.setColor(Color.black);
		g.drawRect(0, 0, width - 1, height - 1);
		// 随机生成50个干扰的点
		Random rdm = new Random();
		// make some confusion
		for (int i = 0; i < 50; i++) {
			int x = rdm.nextInt(width);
			int y = rdm.nextInt(height);
			g.drawOval(x, y, 0, 0);
		}
		// generate a random code
		String verifyCode = generateVerifyCode(rdm);
		g.setColor(new Color(0, 100, 0));
		g.setFont(new Font("Candara", Font.BOLD, 24));
		g.drawString(verifyCode, 8, 24);
		g.dispose();
		//把验证码存到redis中
		int rnd = calc(verifyCode);
		redisService.set(SeckillPrefix.getMiaoshaVerifyCode, user.getId()+","+itemId, rnd);
		//输出图片	
		return image;
	}
	//ScriptEngineManager利用计算结果
	private int calc(String verifyCode) {
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("JavaScript");
			return (Integer)engine.eval(verifyCode);
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	//设置数学的算法
	private static char[] ops = new char[] {'+', '-', '*'};
	private String generateVerifyCode(Random rdm) {
		int num1 = rdm.nextInt(10);
		int num2 = rdm.nextInt(10);
		int num3 = rdm.nextInt(10);
		char op1 = ops[rdm.nextInt(3)];
		char op2 = ops[rdm.nextInt(3)];
		String exp = ""+ num1 + op1 + num2 + op2 + num3;
		return exp;
	}

	@Override
	public boolean checkVerifyCode(User user, long seckillId, int verifyCode) {
		if(user == null || seckillId <=0) {
			return false;
		}
		Integer codeOld = redisService.get(SeckillPrefix.getMiaoshaVerifyCode, user.getId()+","+seckillId, Integer.class);
		if(codeOld == null || codeOld - verifyCode != 0 ) {
			return false;
		}
		redisService.delete(SeckillPrefix.getMiaoshaVerifyCode, user.getId()+","+seckillId);
		return true;
	}

	public String createMiaoshaPath(User user, long itemId) {
		if(user == null || itemId <=0) {
			return null;
		}
		String str = MD5Util.md5(UUIDUtil.uuid()+"123456");
		redisService.set(SeckillPrefix.getMiaoshaPath, ""+user.getId() + "_"+ itemId, str);
		return str;
	}

	@Override
	public long getSeckillResult(int id, int itemId) {
		Order order = orderService.getOrderBySeckillIdUserId(itemId, id);
		if(order != null) {
			return Long.parseLong(order.getOrderId());
		}else {
			boolean isOver = getItemOver(itemId);
			if(isOver) {
				return -1;
			}else {
				return 0;
			}
		}
	}

	private boolean getItemOver(int itemId) {
		return redisService.get(ItemPrefix.isOver, ""+itemId, boolean.class);
	}

	@Override
	public boolean checkPath(User user, int itemId, String path) {
		if(user ==null || path == null) {
			return false;
		}
		String url = redisService.get(SeckillPrefix.getMiaoshaPath, ""+user.getId() + "_"+ itemId,String.class);
		return path.equals(url);
	}

}






