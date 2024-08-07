package com.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyuan.jibeiOrderDetail.model.JibeiOrderDetailVO;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service("jedisService")
public class JedisService {

	private final JedisPool jedisPool;
	
	public JedisService(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	
	public void delete(String key)throws IOException{
		try(Jedis jedis = jedisPool.getResource()){
			jedis.del(key);
		}
	}
	
	// =============== for key Value ===============
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
	
	// =============== for key filed Value ===============
	public void saveUserOneOne(String key, String filed, String value )throws IOException{
		try(Jedis jedis = jedisPool.getResource()){
			jedis.hset(key, filed, value);
		}
	}
	
	public String getUserOneOne(String key, String filed )throws IOException{
		try(Jedis jedis = jedisPool.getResource()){
			return jedis.hget(key, filed);
		}
	}
	
	public void deleteUserOneOne(String key, String filed)throws IOException{
		try (Jedis jedis = jedisPool.getResource()){
			jedis.hdel(key, filed);
		}
	}
	

	
	// =============== key List for allType  ===============
	public void saveToList(String key, Object value)throws IOException{
		 ObjectMapper objectMapper = new ObjectMapper();
		 String valueJson = objectMapper.writeValueAsString(value);
		 try(Jedis jedis = jedisPool.getResource()){
			 jedis.rpush(key, valueJson);
		 }
	}
	
	public void saveList(String key,  List<Object> List)throws IOException {
		try(Jedis jedis = jedisPool.getResource()){
			jedis.del(key);
			ObjectMapper objectMapper = new ObjectMapper();
			
            for (Object item : List) {
                String itemJson = objectMapper.writeValueAsString(item); // 将每个对象序列化为JSON字符串
                jedis.rpush(key, itemJson); // 使用rpush命令将JSON字符串添加到列表末尾
            }
		}
	}
	
	public List<Object> getList(String key)throws IOException {
		try(Jedis jedis = jedisPool.getResource()){
			ObjectMapper objectMapper = new ObjectMapper();
			List<Object> valueList = new ArrayList<>();			
			List<String> voListJson = jedis.lrange(key,0,-1);//取得 不移除

			if( voListJson != null) {
				for(String voJson : voListJson) {				
					valueList.add(objectMapper.readValue(voJson,Object.class)); 
				}
				return valueList;
			}
		}
		return null;
	}
	

	
	
	
	// =============== for DrinkCart ===============
//	public void saveDrinkCart(String userID, DrinkOrderDetailVO beDrinkOrderDetail)throws IOException{
//		 ObjectMapper objectMapper = new ObjectMapper();
//		 String valueJson = objectMapper.writeValueAsString(beDrinkOrderDetail);
//		 try(Jedis jedis = jedisPool.getResource()){
//			 jedis.rpush(userID, valueJson);
//		 }
//	}
//	
//	public List<DrinkOrderDetailVO> getDrinkCart(String userID)throws IOException {
//		try(Jedis jedis = jedisPool.getResource()){
//			ObjectMapper objectMapper = new ObjectMapper();
//			List<DrinkOrderDetailVO> beDrinkOrderDetailList = new ArrayList<>();			
//			List<String> voListJson = jedis.lrange(userID,0,-1);
//			
//			if( voListJson != null) {
//				for(String voJson : voListJson) {				
//					beDrinkOrderDetailList.add(objectMapper.readValue(voJson,DrinkOrderDetailVO.class)); 
//				}
//				return beDrinkOrderDetailList;
//			}
//		}
//		return null;
//	}
	

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
