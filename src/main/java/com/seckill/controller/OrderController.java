package com.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seckill.common.Result;
import com.seckill.pojo.Order;
import com.seckill.pojo.SeckillItem;
import com.seckill.pojo.User;
import com.seckill.pojo.vo.OrderAndItemVo;
import com.seckill.service.ItemService;
import com.seckill.service.OrderService;
import com.seckill.service.SeckillService;
import com.seckill.service.UserService;


@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	UserService userService;

	@Autowired
	ItemService itemService;

	@Autowired
	OrderService orderService;

	@Autowired
	SeckillService seckillService;

	@RequestMapping(value="/detail")
	@ResponseBody
	public Result doseckill(Model model,User user,@RequestParam String orderId){
		if(user == null) {
			return Result.error(501, "您还没有登录");
		}
		Order order = orderService.getOrderByOrderId(orderId);
		if(order == null) {
			return Result.error(504, "订单不存在");
		}
		int seckilkllId = order.getSeckillId();
		SeckillItem item = itemService.getSeckillItemById(seckilkllId);
		OrderAndItemVo vo = new OrderAndItemVo();
		vo.setItem(item);
		vo.setOrder(order);
		return Result.success(vo);
	}

}
