package com.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil {

	private static JedisPool pool = null;

	private JedisPoolUtil() {
	}

//	public static JedisPool jedisPool() {
//		JedisPoolConfig poolConfig = new JedisPoolConfig();
//		poolConfig.setMaxTotal(8);
//		poolConfig.setMaxIdle(8);
//		poolConfig.setMaxWaitMillis(10000);
//		return new JedisPool(poolConfig, host, port);
//	}
	public static JedisPool getJedisPool() {
		if (pool == null) {
			synchronized (JedisPoolUtil.class) {
				if (pool == null) {
					JedisPoolConfig config = new JedisPoolConfig();
					config.setMaxTotal(8);// 設定最大連線數
					config.setMaxIdle(8);// 設定最大空閒數
					config.setMaxWaitMillis(10000);// 設定超時時間
					pool = new JedisPool(config, "localhost", 6379);// 初始化連線池
				}
			}
		}
		return pool;
	}

	// 可在ServletContextListener的contextDestroyed裡呼叫此方法註銷Redis連線池
	public static void shutdownJedisPool() {
		if (pool != null)
			pool.destroy();
	}
}
