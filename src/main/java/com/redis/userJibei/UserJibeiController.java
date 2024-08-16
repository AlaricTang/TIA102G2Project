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
import com.redis.drinkCartTest.DrinkCartService;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailVO;

@Controller
@RequestMapping("/user")
public class UserJibeiController {

	@Autowired
	UserJibeiService userJibeiSvc;
	
	@Autowired
	DrinkService drinkSvc;
	
	@Autowired
	DrinkCartService drinkCartSvc;
	
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
		//會員的寄杯
		List<UserJibeiVO> userJibeiList = userJibeiSvc.getUserJibei(user.getUserId());
		model.addAttribute("userJibeiList",userJibeiList);
		//購物車中的寄杯
		List<DrinkOrderDetailVO> cartDrinkOrderList = drinkCartSvc.getJibeiInDrinkCart(user.getUserId());
		model.addAttribute("cartDrinkOrderList",cartDrinkOrderList);
		return "back-end/jibeiProduct/goRedeemUserJibei";
	}
	
	//前往 兌換寄杯詳細頁面
	@GetMapping("goDetailUserJibei")
	public String goDetailUserJibei(
			@RequestParam("drinkID") String drinkID ,HttpSession session, ModelMap model) throws IOException {
		UserVO user = (UserVO)session.getAttribute("user");
		//會員的某寄杯
		UserJibeiVO userJibei = userJibeiSvc.getOneUserJibei(user.getUserId(), drinkID);
		model.addAttribute("userJibei",userJibei);
		//取得某飲品資訊
		DrinkVO drinkVO = drinkSvc.getOneDrink(Integer.valueOf(drinkID));
		model.addAttribute("drinkVO",drinkVO);
		//購物車中的某寄杯
		DrinkOrderDetailVO cartOneDrinkOrder = drinkCartSvc.getOneInDrinkCart(user.getUserId(), drinkID);
		model.addAttribute("cartOneDrinkOrder",cartOneDrinkOrder);
		
		return "back-end/jibeiProduct/userJibeiDetail";
	}
	
	
	
	//進行兌換
	@PostMapping("redeemUserJibei")
	public String redeemUserJibei(
			@RequestParam("drinkID") String drinkID,
			@RequestParam("drinkOrderDetailIsHot") String drinkOrderDetailIsHot,
			@RequestParam("drinkOrderDetailUseCup") String drinkOrderDetailUseCup,
			@RequestParam("drinkOrderDetailAmount") String drinkOrderDetailAmount,
			ModelMap model,HttpSession session) throws IOException{
		
		//要放到購物車detailVO
		DrinkOrderDetailVO drinkItem = new DrinkOrderDetailVO();
	    drinkItem.setDrinkID(Integer.valueOf(drinkID));
	    drinkItem.setDrinkOrderDetailIsHot(Byte.valueOf(drinkOrderDetailIsHot));
	    drinkItem.setDrinkOrderDetailUseCup(Byte.valueOf(drinkOrderDetailUseCup));
	    drinkItem.setDrinkOrderDetailAmount(Integer.valueOf(drinkOrderDetailAmount));
	    drinkItem.setDrinkOrderDetailIsJibei(Byte.valueOf("1"));
		
	    //放入該user購物車
	    UserVO user = (UserVO)session.getAttribute("user");
	    drinkCartSvc.addDrinkCartItem(user.getUserId(), drinkItem);

//	    userJibeiSvc.redeemUserJibei(user.getUserId(), Integer.valueOf(drinkID), Integer.valueOf(drinkOrderDetailAmount));
	    
	    //寄杯 數量確認
	    if(Byte.valueOf(drinkOrderDetailUseCup) == 1) {
	    	String str_cupNumber = drinkCartSvc.getOneDrinkOrder(user.getUserId(), "cupNumber");
	    	Integer cupNumber = Integer.valueOf(str_cupNumber)+1;
	    	drinkCartSvc.setOneDrinkOrder(user.getUserId(), "cupNumber", cupNumber.toString()  );                      
	    }
		return "redirect:/user/goRedeemUserJibei";
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
//		userJibeiVO.setDrinkPic(drink.getDrinkPic());
		userJibeiSvc.addUserJibei(user.getUserId(),userJibeiVO);
		return "redirect:/user/userJibeiList";
	}
}
