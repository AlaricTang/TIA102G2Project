package com.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@Configuration
public class JedisConfiguration {

    @Value("${redis.host:localhost}")
    private String redisHost;

    @Value("${redis.port:6379}")
    private int redisPort;

    /**
     * 配置 Jedis 連線池
     * destroyMethod 設為空字串，避免 Spring 關閉時自動調用 destroy 造成 JMX 註冊問題
     */
    @Bean(destroyMethod = "")
    public JedisPool getJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();

        // 最大連線數（同時可用的連線數），預設為8，這裡調高為16
        config.setMaxTotal(16);

        // 最大空閒連線數，預設為8
        // config.setMaxIdle(8);

        // 最小空閒連線數，預設為0，這裡設為2
        config.setMinIdle(2);

        // 取得連線最大等待時間（毫秒），預設為-1（無限等待），這裡設為10000
        config.setMaxWait(Duration.ofMillis(10000));

        // 禁用 JMX 註冊，預設為true，這裡設為false避免 JMX 註冊衝突
        config.setJmxEnabled(false);

        // 當連線耗盡時是否阻塞，預設為true
        // config.setBlockWhenExhausted(true);

        // 每次從池中取出連線時進行測試，預設為false，這裡設為true提升穩定性
        config.setTestOnBorrow(true);

        // 歸還連線時進行測試，預設為false，這裡設為true提升穩定性
        config.setTestOnReturn(true);

        // 使用 properties 注入的 host/port
        return new JedisPool(config, redisHost, redisPort);
    }
}
