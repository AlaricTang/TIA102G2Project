package com.redis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service("jedisService")
public class JedisService {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private Gson gson;

    // =============== 基本 Key-Value 操作 ===============

    /**
     * 刪除指定 key
     */
    public void delete(String key) throws IOException {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
        }
    }

    /**
     * 儲存單一 key-value
     */
    public void saveOneOne(String key, String value) throws IOException {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
        }
    }

    /**
     * 取得單一 key 對應的 value
     */
    public String getOneOne(String key) throws IOException {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        }
    }

    // =============== Hash 操作 (key-field-value) ===============

    /**
     * 儲存 hash 欄位
     */
    public void saveOneOneOne(String key, String field, String value) throws IOException {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset(key, field, value);
        }
    }

    /**
     * 取得 hash 欄位值
     */
    public String getOneOneOne(String key, String field) throws IOException {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hget(key, field);
        }
    }

    /**
     * 刪除 hash 欄位
     */
    public void deleteOneOneOne(String key, String field) throws IOException {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hdel(key, field);
        }
    }

    // =============== List 操作 (key-list) ===============

    /**
     * 將物件序列化後加入 list
     */
    public void saveItemToList(String key, Object item) {
        try (Jedis jedis = jedisPool.getResource()) {
            String jsonString = gson.toJson(item);
            jedis.rpush(key, jsonString);
        }
    }

    /**
     * 儲存整個 list（會先刪除原本的 key）
     */
    public void saveList(String key, List<Object> list) throws IOException {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
            for (Object item : list) {
                String itemJson = gson.toJson(item);
                jedis.rpush(key, itemJson);
            }
        }
    }

    /**
     * 取得 list 內容並反序列化
     */
    public List<Object> getItemsFromList(String key) throws IOException {
        try (Jedis jedis = jedisPool.getResource()) {
            List<String> jsonList = jedis.lrange(key, 0, -1);
            Type type = new TypeToken<List<Object>>() {}.getType();
            return gson.fromJson(jsonList.toString(), type);
        }
    }

    /**
     * 從 list 移除指定物件
     */
    public void removeItemFromList(String key, Object item) {
        try (Jedis jedis = jedisPool.getResource()) {
            String jsonString = gson.toJson(item);
            jedis.lrem(key, 0, jsonString);
        }
    }

    // =============== Hash-List 操作 (key-field-list) ===============

    /**
     * 將 value 加入 hash 的某個 field 對應的 list
     */
    @SuppressWarnings("unchecked")
    public void saveItemToHash(String key, String field, Object value) {
        try (Jedis jedis = jedisPool.getResource()) {
            // 取得該 field 原本的 list
            String json = jedis.hget(key, field);
            List<Object> items = new ArrayList<>();
            if (json != null) {
                items = gson.fromJson(json, List.class);
            }
            items.add(value);
            jedis.hset(key, field, gson.toJson(items));
        }
    }

    /**
     * 儲存整個 list 到 hash 的某個 field（會覆蓋原本的 field）
     */
    public void saveListToHash(String key, String field, List<Object> list) throws IOException {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset(key, field, gson.toJson(list));
        }
    }
}
