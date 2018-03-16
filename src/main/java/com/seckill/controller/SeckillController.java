package com.seckill.controller;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seckill.common.Result;
import com.seckill.component.rabbitmq.MQSender;
import com.seckill.component.redis.ItemPrefix;
import com.seckill.component.redis.RedisService;
import com.seckill.pojo.Order;
import com.seckill.pojo.SeckillItem;
import com.seckill.pojo.User;
import com.seckill.pojo.vo.SeckillMQVo;
import com.seckill.service.ItemService;
import com.seckill.service.OrderService;
import com.seckill.service.SeckillService;
import com.seckill.service.UserService;
import com.seckill.util.validator.AccessLimit;


@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {

	@Autowired
	UserService userService;

	@Autowired
	ItemService itemService;

	@Autowired
	OrderService orderService;

	@Autowired
	SeckillService seckillService;

	@Autowired
	RedisService redisService;

	@Autowired
	MQSender sender;

	private HashMap<Integer, Boolean> localOverMap =  new HashMap<Integer, Boolean>();


	/**
	 * 系统初始化时自动会加载
	 **/
	@Override
	public void afterPropertiesSet() throws Exception {
		List<SeckillItem> itemList = itemService.getList();
		if(itemList == null) {
			return;
		}
		for (SeckillItem seckillItem : itemList) {
			redisService.set(ItemPrefix.stock, ""+seckillItem.getSeckillId(), seckillItem.getMaxNum());
			localOverMap.put(seckillItem.getSeckillId(), false);
		}
	}


	@RequestMapping(value="/{path}/doseckill",method=RequestMethod.POST)
	@ResponseBody
	public Result doseckill(Model model,User user,@RequestParam int itemId,@PathVariable String path){

		if(user == null) {
			return Result.error(504, "您没有登录，请登录！");
		}
		boolean check = seckillService.checkPath(user,itemId,path);
		if(!check) {
			return Result.error(507, "非法请求！");
		}

		long stock = redisService.decr(ItemPrefix.stock,""+itemId);
		if(stock < 0) {
			return Result.error(503, "商品已售 罄！请关注下次秒杀！");
		}
		//是否已经生成了订单
		Order order = orderService.getOrderBySeckillIdUserId(itemId,user.getId());
		if(order != null) {
			return Result.error(502, "您已经抢购到此商品！");
		}
		//使用消息队列
		SeckillMQVo mm = new SeckillMQVo();
		mm.setSeckillId(itemId);
		mm.setUser(user);
		sender.sendSeckillMQ(mm);
		return Result.success(0);

		/*
		//判断库存
		SeckillItem seckillitem = itemService.getSeckillItemByItemId(itemId);
		int maxNum = seckillitem.getMaxNum();
		if(maxNum <= 0) {
			model.addAttribute("errormsg", "商品已售 罄！请关注下次秒杀！");
			return Result.error(503, "seckill_fail");
		}
		//是否已经生成了订单
		Order order = orderService.getOrderBySeckillIdUserId(seckillitem.getSeckillId(),user.getId());
		if(order != null) {
			model.addAttribute("errormsg", "您已经抢购到了   “"+seckillitem.getItemTitle()+"”");
			model.addAttribute("seckillOrder", order);
			model.addAttribute("item", seckillitem);
			return Result.error(502, "seckill_fail");
		}
		//下单
		Order seckillOrder = seckillService.seckill(user,seckillitem);
		model.addAttribute("seckillOrder", seckillOrder);
		model.addAttribute("item", seckillitem);
		OrderAndItemVo vo = new OrderAndItemVo();
		vo.setItem(seckillitem);
		vo.setOrder(seckillOrder);
		return Result.success(vo);*/
	}

	@RequestMapping(value="/result",method=RequestMethod.GET)
	@ResponseBody
	public Result getResult(Model model,User user,@RequestParam int itemId){

		if(user == null) {
			return Result.error(504, "您没有登录，请登录！");
		}

		long result = seckillService.getSeckillResult(user.getId(),itemId);
		return Result.success(result);
	}

	@RequestMapping("/gopay/{orderId}/{itemId}")
	public String gopay(Model model,User user,@PathVariable String orderId,@PathVariable Integer itemId) {
		Order seckillOrder = seckillService.getOrderById(orderId);
		SeckillItem seckillitem = itemService.getSeckillItemById(itemId);
		model.addAttribute("seckillOrder", seckillOrder);
		model.addAttribute("item", seckillitem);
		return "order_detail";
	}

	@RequestMapping(value="/verifyCode", method=RequestMethod.GET)
	@ResponseBody
	public Result getMiaoshaVerifyCod(HttpServletResponse response,User user,
			@RequestParam("itemId")int itemId) {
		if(user == null) {
			return Result.error(501,"您没有登录！");
		}
		try {
			BufferedImage image  = seckillService.createVerifyCode(user, itemId);
			OutputStream out = response.getOutputStream();
			ImageIO.write(image, "JPEG", out);
			out.flush();
			out.close();
			return Result.success("");
		}catch(Exception e) {
			e.printStackTrace();
			return Result.error(502, "秒杀失败！");
		}
	}


	/*@RequestMapping("/verifyCode")
	@ResponseBody
	public Result verifyCode(HttpServletResponse response,User user,@RequestParam int itemId) {
		if(user == null) {
			return Result.error(501,"您没有登录！");
		}
		BufferedImage image = seckillService.createVerifyCode(user, itemId);
		try {
			OutputStream out = response.getOutputStream();
			ImageIO.write(image, "JPEG",out);
			out.flush();
			out.close();
			return null;
		}catch(Exception e) {
			return Result.error(502, "秒杀失败！");
		}
	}
	 */
	@AccessLimit(seconds=5, maxCount=5, needLogin=true)
	@RequestMapping(value="/path", method=RequestMethod.GET)
	@ResponseBody
	public Result getMiaoshaPath(HttpServletRequest request, User user,
			@RequestParam("itemId")long seckillId,
			@RequestParam(value="verifyCode", defaultValue="0")int verifyCode
			) {
		if(user == null) {
			return Result.error(504,"您还没有登录！");
		}
		boolean check = seckillService.checkVerifyCode(user, seckillId, verifyCode);
		if(!check) {
			return Result.error(506,"验证码错误！");
		}
		String path  =seckillService.createMiaoshaPath(user, seckillId);
		return Result.success(path);
	}



}
