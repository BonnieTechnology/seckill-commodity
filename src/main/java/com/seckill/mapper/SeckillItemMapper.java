package com.seckill.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.seckill.pojo.SeckillItem;

/**
 * 商品mapper
 * @author Administrator
 *
 */
@Mapper
public interface SeckillItemMapper {
	
	@Select("select * from tb_seckill_item")
	List<SeckillItem> getList();
	
	@Select("select * from tb_seckill_item where item_id = #{itemId}")
	SeckillItem getSeckillItemByItemId(String itemId);

	@Update("update tb_seckill_item set max_num=max_num-1 where seckill_id = #{seckillId} and item_id = #{itemId} and max_num > 0")
	int reduceStock(SeckillItem seckillitem);
	@Select("select * from tb_seckill_item where seckill_id = #{seckilkllId}")
	SeckillItem getSeckillItemById(int seckilkllId);
	
	
	

}
