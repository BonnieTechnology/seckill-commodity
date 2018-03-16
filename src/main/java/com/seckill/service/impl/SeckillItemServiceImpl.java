package com.seckill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seckill.mapper.SeckillItemMapper;
import com.seckill.pojo.SeckillItem;
import com.seckill.service.SeckillItemService;


@Service
public class SeckillItemServiceImpl implements SeckillItemService {

	@Autowired
	SeckillItemMapper seckillItemMapper;
	
	@Override
	public int reduceStock(SeckillItem seckillitem) {
		
		return seckillItemMapper.reduceStock(seckillitem);
	}

}
