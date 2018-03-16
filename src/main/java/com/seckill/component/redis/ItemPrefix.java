package com.seckill.component.redis;

public class ItemPrefix extends RedisBasePrefix {

	
	private static final int COOKIE_EXPRIE = 60;
	
	 public ItemPrefix(int exprieSeconds, String prefix) {
		super(exprieSeconds, prefix);
	}

	public static ItemPrefix token = new ItemPrefix(COOKIE_EXPRIE,"ITEM_LIST_");
	public static ItemPrefix stock = new ItemPrefix(0,"ITEM_");
	public static ItemPrefix isOver = new ItemPrefix(0,"isOver_");

	
	

	
	


}
