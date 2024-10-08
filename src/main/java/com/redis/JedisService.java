package com.redis;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service("jedisService")
public class JedisService {

	@Autowired
	JedisPool jedisPool;
	
	@Autowired
	private Gson gson;
	
	public JedisService(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	
	public void delete(String key)throws IOException{
		try(Jedis jedis = jedisPool.getResource()){
			jedis.del(key);
		}
	}
	
	// ===============  key Value ===============
	public void saveOneOne(String key,String value)throws IOException{
		try(Jedis jedis = jedisPool.getResource()){
			 jedis.set(key, value);
		 }
	}
	public String getOneOne(String key)throws IOException{
		try(Jedis jedis = jedisPool.getResource()){
			return jedis.get(key);
		}
	}
	
	// ===============  key filed Value ===============
	public void saveOneOneOne(String key, String filed, String value )throws IOException{
		try(Jedis jedis = jedisPool.getResource()){
			jedis.hset(key, filed, value);
		}
	}
	
	public String getOneOneOne(String key, String filed )throws IOException{
		try(Jedis jedis = jedisPool.getResource()){
			return jedis.hget(key, filed);
		}
	}
	
	public void deleteOneOneOne(String key, String filed)throws IOException{
		try (Jedis jedis = jedisPool.getResource()){
			jedis.hdel(key, filed);
		}
	}
	

	
	// =============== key List for allType  ===============
	public void saveItemToList(String key, Object item) {
		 try(Jedis jedis = jedisPool.getResource()){
			 String jsonString = gson.toJson(item);
			 jedis.rpush(key, jsonString);//jedis.rpush(key, itemJson);同樣的意思
		 }
	}
	
	public void saveList(String key,  List<Object> List)throws IOException {
		try(Jedis jedis = jedisPool.getResource()){
			jedis.del(key);
			
            for (Object item : List) {
            	String itemJson = gson.toJson(item); 
                jedis.rpush(key, itemJson); 
            }
		}
	}
	
	public List<Object> getItemsFromList(String key)throws IOException {
		try(Jedis jedis = jedisPool.getResource()){
			List<String> jsonList = jedis.lrange(key, 0, -1);
			Type type = new TypeToken<List<Object>>() {}.getType();
			return gson.fromJson(jsonList.toString(), type);
		}
	}
	
	
	
    public void removeItemFromList(String key, Object item) {
		try(Jedis jedis = jedisPool.getResource()){
			String jsonString = gson.toJson(item);
			jedis.lrem(key, 0, jsonString);
		}
    }
	
	// =============== key field List for allType  ===============
	@SuppressWarnings("unchecked")
	public void saveItemToHash(String key, String field, Object value) {	
		try(Jedis jedis = jedisPool.getResource()){
			
			String json = jedis.hget(key, value.toString());
			List<Object> items= new ArrayList<>();
			
			if(json != null) {
				items = gson.fromJson(json, List.class);
			}
			
			items.add(value);
			jedis.hset(key, field, gson.toJson(items));
		}
	}
	
//cart:userID, drink, 
	public void saveListToHash(String key, String field, List<Object> List)throws IOException {
		try(Jedis jedis = jedisPool.getResource()){
			jedis.hdel(key,field);
			
	        for (Object item : List) {
	            String itemJson = gson.toJson(item); // 将每个对象序列化为JSON字符串
	            jedis.rpush(key, itemJson); // 使用rpush命令将JSON字符串添加到列表末尾
	        }
		}
	}
	

}


//.
//.					   _ooOoo
//.					  o8888888o
//.					  88" . "88 
//.					  (| -_- |)
//.					  O\  =  /O
//.					___/`---'\____
//.				 .'  \\|     |//  `.
//.			    /  \\|||  :  |||//  \
//.			   /  _||||| -:- |||||_  \
//.			   |   | \\\  -  /// |   |
//.			   | \_|  ''\---/''  |   |
//.			   \  .-\__       __/-.  /
//.			 ___`. .'  /--.--\ `. . __
//.		  ."" '<  `.___\_<|>_/__.'  >'"".
//.      | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//.      \  \ `-.   \_ __\ /__ _/   .-` /  /
//. ======`-.____`-.___\_____/___.-`____.-'======
//.                    `=---='
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//.               佛祖保佑       永無BUG
