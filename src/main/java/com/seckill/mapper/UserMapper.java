package com.seckill.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.seckill.pojo.User;
/**
 * 用户mapper
 * @author Administrator
 *
 */
@Mapper
public interface UserMapper {
	
	@Select("select * from tb_seckill_user where mobile = #{mobile}")
	User getByMobile(String mobile);
	
	
	

}
