package com.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service("redisService")
public class JedisService {

	private static JedisPool jedisPool = JedisPoolUtil.getJedisPool();
	
	public JedisService(JedisPool jedisPool) {
		JedisService.jedisPool = jedisPool;
	}
	
	// =============== for String ===============
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
	

	
	// =============== List for allType  ===============
	public void saveToList(String key, Object value)throws IOException{
		 ObjectMapper objectMapper = new ObjectMapper();
		 String valueJson = objectMapper.writeValueAsString(value);
		 try(Jedis jedis = jedisPool.getResource()){
			 jedis.rpush(key, valueJson);
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
			
//			for(int i=0;i<jedis.llen(key);i++) {	//取得 並移除
//				String voJson = jedis.lpop(key);
//				valueList.add(objectMapper.readValue(voJson,Object.class)) ;
//			}
		}
		return null;
	}
	
	public void deleteList(String key)throws IOException{
		try(Jedis jedis = jedisPool.getResource()){
			jedis.del(key);
		}
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
