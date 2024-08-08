package com.redis.favorite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {
	
	@Autowired
	FavoriteService favoriteService ;

	// 加收藏
	@PostMapping("/add")
	public String addFavorite(@RequestParam Integer userID, @RequestParam Integer productID) {
		try {
			favoriteService.addFavorite(userID, productID);
			return "收藏成功";
		} catch (IOException e) {
			e.printStackTrace();
			return "收藏失敗";
		}
	}

	// 會員所有收藏
	@GetMapping("/list/{userID}")
	public List<Object> getList(@PathVariable Integer userID) {
		try {	
			return favoriteService.getFavorites(userID);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 刪除收藏
	@DeleteMapping("/remove")
	public String removeFavorite(@RequestParam Integer userID, @RequestParam Integer productID) {
		try {
			favoriteService.removeFavorite(userID, productID);
			return "刪除成功";
		} catch (IOException e) {
			e.printStackTrace();
			return "刪除失敗";
		}
	}
}
