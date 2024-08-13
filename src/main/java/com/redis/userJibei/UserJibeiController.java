package com.redis.userJibei;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ellie.user.model.UserVO;
import com.ken.drink.model.DrinkService;
import com.ken.drink.model.DrinkVO;

@Controller
@RequestMapping("/user")
public class UserJibeiController {

	@Autowired
	UserJibeiService userJibeiSvc;
	
	@Autowired
	DrinkService drinkSvc;
	
	//前往 查看會員寄杯頁面
	@GetMapping("userJibeiList")
	public String userJibeiList(ModelMap model, HttpSession session) throws IOException {
		UserVO user = (UserVO)session.getAttribute("user");
		List<UserJibeiVO> userJibeiList = userJibeiSvc.getUserJibei(user.getUserId());
		
		model.addAttribute("userJibeiList",userJibeiList);
		return "back-end/jibeiProduct/userJibeiList";
	}
	
	//前往 兌換寄杯頁面
	@GetMapping("goRedeemUserJibei")
	public String goRedeemUserJibei(ModelMap model, HttpSession session) throws IOException {
		UserVO user = (UserVO)session.getAttribute("user");
		List<UserJibeiVO> userJibeiList = userJibeiSvc.getUserJibei(user.getUserId());
		model.addAttribute("userJibeiList",userJibeiList);
		return "back-end/jibeiProduct/goRedeemUserJibei";
	}
	
	//進行兌換
	@PostMapping("redeemUserJibei")
	public String redeemUserJibei(
			@RequestParam("drinkID") String drinkID,
			@RequestParam("number") String number,
			ModelMap model,HttpSession session) throws IOException{
		
		UserVO user = (UserVO)session.getAttribute("user");
		if( !userJibeiSvc.redeemUserJibei(user.getUserId(), Integer.valueOf(drinkID), Integer.valueOf(number))) {
			model.addAttribute("errorMessage","兌換失敗");
			return "back-end/jibeiProduct/userJibeiList";
		}
		
		return "redirect:/user/userJibeiList";
	}
	
	@PostMapping("fakeBuyJibei")
	public String fakeBuyJibei(
			@RequestParam("drinkID") String drinkID,
			@RequestParam("number") String number,
			HttpSession session
			) throws IOException {
		UserVO user = (UserVO)session.getAttribute("user");
		UserJibeiVO userJibeiVO = new UserJibeiVO(Integer.valueOf(drinkID), Integer.valueOf(number));
		DrinkVO drink = drinkSvc.getOneDrink(Integer.valueOf(drinkID));
		userJibeiVO.setDrinkName(drink.getDrinkName());
		userJibeiVO.setDrinkDes(drink.getDrinkDes());
		userJibeiVO.setDrinkPic(drink.getDrinkPic());
		userJibeiSvc.addUserJibei(user.getUserId(),userJibeiVO);
		return "redirect:/user/userJibeiList";
	}
}
