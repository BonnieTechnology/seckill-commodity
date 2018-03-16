package com.seckill.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seckill.mapper.SeckillItemMapper;
import com.seckill.pojo.SeckillItem;
import com.seckill.service.ItemService;

/**
 * 秒杀商品业务表
 * @author Administrator
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	SeckillItemMapper itemMapper;
	

	@Override
	public List<SeckillItem> getList() {
		return itemMapper.getList();
	}


	@Override
	public SeckillItem getSeckillItemByItemId(String itemId) {
		
		return itemMapper.getSeckillItemByItemId(itemId);
	}


	@Override
	public SeckillItem getSeckillItemById(int seckilkllId) {
		
		return itemMapper.getSeckillItemById(seckilkllId);
	}

}
