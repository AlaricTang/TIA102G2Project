package com.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil {

	private static JedisPool pool = null;

	private JedisPoolUtil() {
	}


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


	public static void shutdownJedisPool() {
		if (pool != null)
			pool.destroy();
	}
}
