package com.redis.favorite;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.redis.JedisService;


@Service
public class FavoriteService {
	
	@Autowired
	JedisService jedisSvc;


	    private static final String FAVORITE_KEY_PREFIX = "favorite:";

	    // 儲存收藏
	    public void addFavorite(Integer userID, Integer productID) throws IOException {
	        String key = FAVORITE_KEY_PREFIX + userID;
	        jedisSvc.saveOneOneOne(key, productID.toString(), productID.toString());
	    }

	    // 會員所有收藏的商品
	    public List<Object> getFavorites(Integer userID) throws IOException {
	        String key = FAVORITE_KEY_PREFIX + userID;
	        return jedisSvc.getItemsFromList(key);
	    }
	    
	    // 刪除收藏
	    public void removeFavorite(Integer userID, Integer productID) throws IOException {
	        String key = FAVORITE_KEY_PREFIX + userID;
	        jedisSvc.delete(key);
	    }

	
	}

