package com.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConfiguration {


	@Bean
	public JedisPool getJedisPool() {
		
		JedisPoolConfig config = new JedisPoolConfig();
		
		config.setMaxTotal(8);// 設定最大連線數
		config.setMaxIdle(8);// 設定最大空閒數
		config.setMaxWaitMillis(10000);// 設定超時時間
		
		JedisPool jedisPool =  new JedisPool(config, "localhost", 6379);// 初始化連線池
		return jedisPool;
	
	}
}
