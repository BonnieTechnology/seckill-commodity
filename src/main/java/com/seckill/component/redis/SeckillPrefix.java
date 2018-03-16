package com.seckill.component.redis;

public class SeckillPrefix extends RedisBasePrefix {

	
	 public SeckillPrefix(int exprieSeconds, String prefix) {
		super(exprieSeconds, prefix);
	}

	 public static SeckillPrefix isGoodsOver = new SeckillPrefix(0, "go");
		public static SeckillPrefix getMiaoshaPath = new SeckillPrefix(60, "mp");
		public static SeckillPrefix getMiaoshaVerifyCode = new SeckillPrefix(300, "vc");
	
}
