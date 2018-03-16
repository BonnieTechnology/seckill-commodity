package com.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class MainApplication  {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MainApplication.class, args);
	}
	
//	//war包启动的方法
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		// TODO Auto-generated method stub
//		return builder.sources(MainApplication.class);
//	}
	
}
