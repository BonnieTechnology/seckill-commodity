package com.seckill.service;

import java.util.List;

import com.seckill.pojo.SeckillItem;


public interface ItemService {
	
	List<SeckillItem> getList();


	SeckillItem getSeckillItemById(int seckilkllId);

	SeckillItem getSeckillItemByItemId(String itemId);



}
