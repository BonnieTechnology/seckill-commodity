package com.seckill.pojo.vo;

import com.seckill.pojo.SeckillItem;
import com.seckill.pojo.User;

/**
 * 封装页面需要的数据
 * @author Administrator
 *
 */
public class ItemVo {
	
	private int status;
	private long remainSeconds;
	private SeckillItem item;
	private User user;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getRemainSeconds() {
		return remainSeconds;
	}
	public void setRemainSeconds(long remainSeconds) {
		this.remainSeconds = remainSeconds;
	}
	public SeckillItem getItem() {
		return item;
	}
	public void setItem(SeckillItem item) {
		this.item = item;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
