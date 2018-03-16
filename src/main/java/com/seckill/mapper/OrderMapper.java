package com.seckill.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.seckill.pojo.Order;
import com.seckill.pojo.OrderItem;

/**
 * 订单mapper
 * @author Administrator
 *
 */
@Mapper
public interface OrderMapper {
	
	@Select("select * from tb_order where order_id in (select order_id from tb_order_item where item_id = #{seckillId}) and user_id = #{userId}")
	Order getOrderBySeckillIdUserId(@Param("seckillId") int seckillId,@Param("userId") int userId);

	@Insert("insert into tb_order (order_id,payment,status,buyer_nick,user_id,create_time,seckill_id) values (#{orderId},#{payment},#{status},#{buyerNick},#{userId},#{createTime},#{seckillId})")
	int crateOrder(Order order);

	@Insert("insert into tb_order_item values (#{itemId},#{orderId},#{num},#{title},#{price},#{totalFee},#{picPath})")
	int insertOrderItem(OrderItem orderItem);
	@Select("select * from tb_order where order_id = #{orderId}")
	Order getOrderByOrderId(String orderId);
	

}
