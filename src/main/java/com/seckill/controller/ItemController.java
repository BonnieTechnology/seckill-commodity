package com.seckill.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import com.seckill.common.Result;
import com.seckill.component.redis.ItemPrefix;
import com.seckill.component.redis.RedisService;
import com.seckill.pojo.SeckillItem;
import com.seckill.pojo.User;
import com.seckill.pojo.vo.ItemVo;
import com.seckill.service.ItemService;
import com.seckill.service.UserService;


@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	UserService userService;
	@Autowired
	RedisService redisService;
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	ThymeleafViewResolver ThymeleafViewResolver;
	
	@Autowired
	ApplicationContext applicationContext;
	

	@RequestMapping(value="/items_list",produces="text/html")
	@ResponseBody
	public String longin(HttpServletRequest request,HttpServletResponse response,Model model,User user){
		model.addAttribute("user",user);
		List<SeckillItem> itemList = itemService.getList();
		model.addAttribute("itemList", itemList);
		//return "items_list";
		//先缓存
		String html = redisService.get(ItemPrefix.token, "SECKILL", String.class);
		if(!StringUtils.isEmpty(html)) {
			return html;
		}
		//是空,手动渲染
		WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(),model.asMap());
		html = ThymeleafViewResolver.getTemplateEngine().process("items_list", ctx);
		if(!StringUtils.isEmpty(html)) {
			redisService.set(ItemPrefix.token, "SECKILL", html);
		}
		return html;
		
		
	}

	/*@RequestMapping("/itemDetail1/{itemId}")
	public String itemDetail1(@PathVariable("itemId") String itemId,Model model,User user) {
		SeckillItem item = itemService.getSeckillItemByItemId(itemId);
		model.addAttribute("item", item);
		//状态判断
		long startTime = item.getStartDate().getTime();
		long endTiem = item.getEndDate().getTime();
		long now = System.currentTimeMillis();
		
		int status = 0;//状态
		long remainSeconds = 0;//差多久
		if(now < startTime) {
			status = 0;
			remainSeconds = startTime - now;
		}else if(now > endTiem) {
			status = 2;
			remainSeconds = -1;
		}else {
			status = 1;
			remainSeconds = 0;
		}
		model.addAttribute("status", status);
		model.addAttribute("remainSeconds", remainSeconds);
		return "item_detail";
	}*/
	
	@RequestMapping(value = "/itemDetail/{itemId}")
	@ResponseBody
	public Result itemDetail(@PathVariable("itemId") String itemId,Model model,User user) {
		SeckillItem item = itemService.getSeckillItemByItemId(itemId);
		//状态判断
		long startTime = item.getStartDate().getTime();
		long endTiem = item.getEndDate().getTime();
		long now = System.currentTimeMillis();
		
		int status = 0;//状态
		long remainSeconds = 0;//差多久
		if(now < startTime) {
			status = 0;
			remainSeconds = startTime - now;
		}else if(now > endTiem) {
			status = 2;
			remainSeconds = -1;
		}else {
			status = 1;
			remainSeconds = 0;
		}
		ItemVo vo = new ItemVo();
		vo.setItem(item);
		vo.setRemainSeconds(remainSeconds);
		vo.setStatus(status);
		vo.setUser(user);
		return Result.success(vo);
	}

}
