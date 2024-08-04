package com.tang.drinkOrder.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ellie.store.model.StoreService;
import com.ellie.store.model.StoreVO;
import com.ellie.user.model.UserVO;
import com.redis.drinkCartTest.DrinkCartService;
import com.tang.drinkOrder.model.DrinkOrderService;
import com.tang.drinkOrder.model.DrinkOrderVO;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailService;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailVO;


//我用前後台去分controller
//這裡是前台
@Controller
@RequestMapping("/drinkOrder")
public class DrinkOrderController {

	@Autowired
	DrinkOrderService drinkOrderSvc;
	
	@Autowired
	DrinkOrderDetailService drinkOrderDetailSvc;

	@Autowired
	DrinkCartService drinkCartService; 
	
	@Autowired
	StoreService storeService;
	
	//購物車頁面進來
	//跳轉到 要下單頁面
		//需要將購物人資訊  購物車物品列出來 
		//給user確認 放好VO存 session
	@GetMapping("drinkOrderPage")
	public String drinkOrderPage(ModelMap model, HttpSession session) throws IOException {
		DrinkOrderVO drinkOrderVO = new DrinkOrderVO();
		List<DrinkOrderDetailVO> drinkCartList = new ArrayList<>();
		
		

		//訂購人
		UserVO user = (UserVO)session.getAttribute("user");
		Integer userID = user.getUserId();
		drinkOrderVO.setUserID(userID);
		
		//訂購門市
		String str_drinkOrderStore = drinkCartService.getDrinkOrder("drinkOrderStore");	//從Jedis拿 先假定都從Jedis拿
		StoreVO drinkOrderStore =  storeService.getOneStore(Integer.valueOf(str_drinkOrderStore));
		drinkOrderVO.setStoreID(drinkOrderStore.getStoreID());
		
		//取貨時間
//		String str_drinkOrderPickTime =(String) session.getAttribute("drinkOrderPickTime");   //範例 從session拿的話
		String str_drinkOrderPickTime = drinkCartService.getDrinkOrder("drinkOrderPickTime"); 
		str_drinkOrderPickTime = str_drinkOrderPickTime.replace("T", " ") + ":00";
		Timestamp drinkOrderPickTime = Timestamp.valueOf(str_drinkOrderPickTime);
		drinkOrderVO.setDrinkOrderPickTime(drinkOrderPickTime);
		
		//訂單金額
		String str_drinkOrderAmount = drinkCartService.getDrinkOrder("drinkOrderAmount");
		drinkOrderVO.setDrinkOrderAmount(Integer.valueOf(str_drinkOrderAmount));
		
		//使用環保杯數
		String str_cupNumber = drinkCartService.getDrinkOrder("cupNumber");
		drinkOrderVO.setCupNumber(Integer.valueOf(str_cupNumber));
		
		//把訂單資訊存session
		session.setAttribute("drinkOrderVO",drinkOrderVO);
		
		
		//給前端看
		model.addAttribute("userName",user.getUserName());
		model.addAttribute("drinkOrderStore",drinkOrderStore.getStoreName());
		model.addAttribute("drinkOrderPickTime",str_drinkOrderPickTime);
		model.addAttribute("drinkOrderAmount",str_drinkOrderAmount);
		
		
		//購物車列表
		drinkCartList = drinkCartService.getDrinkCart(userID);
		if(drinkCartList == null) {
			return "購物車頁面";
		}
		model.addAttribute("drinkCartList",drinkCartList);
		return "back-end/drinkOrder/drinkOrderPage";
	}
	
	
//	後端去下單,跳轉到成功頁面
	@PostMapping("orderSuccess")
	public String orderSuccess(ModelMap model,HttpSession session)throws IOException{
//		if(result.hasErrors()) {
//			return "back-end/drinkOrder/drinkOrderPage";
//		}

		//判斷訂單正確與否 & 存訂單
		DrinkOrderVO drinkOrderVO = (DrinkOrderVO)session.getAttribute("drinkOrder"); //取得訂單
		
		//判斷環保杯數 滿足
		StoreVO store = storeService.getOneStore(drinkOrderVO.getStoreID()); 
		if(store.getStoreCups() < drinkOrderVO.getCupNumber()) {
			return "失敗頁面";
		}
		
		drinkOrderVO.setDrinkOrderStatus(Byte.valueOf("0")); //訂單 狀態預設為 未完成
		drinkOrderVO.setDrinkOrderPayStatus(Byte.valueOf("0"));//訂單 狀態預設為 未付款
		if(drinkOrderVO.getDrinkOrderPayM() == 1) { //如果為線上付款 去綠界
			//綠界實行
			drinkOrderVO.setDrinkOrderPayStatus(Byte.valueOf("1")); //執行完 狀態設為 已付款
		}
		DrinkOrderVO saveDrinkOrder = drinkOrderSvc.addAndGetDrinkOrder(drinkOrderVO); //存訂單
		
		//去Redis 取得user的購物車明細
		Integer userID = drinkOrderVO.getUserID(); //獲取訂購人 ID
		List<DrinkOrderDetailVO> cartDrinks =  drinkCartService.getDrinkCart(userID); //取出他的購物車明細
		
		for(DrinkOrderDetailVO drinkDetails : cartDrinks ) { //根據購物車 訂單明細
			drinkOrderDetailSvc.addDrinkOrderDetail(drinkDetails);//存訂單明細
		}
		
		drinkCartService.deleteDrinkDetail(userID); //下訂完 刪購物車
				
		model.addAttribute("saveDrinkOrder", saveDrinkOrder);//成功頁面要呈現訂單資訊(含訂單編號 ㄏㄏㄚ訂單時間等)
		return "redirect:/drinkOrder/orderSuccess";
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
