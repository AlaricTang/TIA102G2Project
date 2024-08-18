package com.tang.drinkOrder.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ellie.member.model.MemberService;
import com.ellie.member.model.MemberVO;
import com.ellie.store.model.StoreService;
import com.ellie.store.model.StoreVO;
import com.ellie.user.model.UserService;
import com.ellie.user.model.UserVO;
import com.ken.drink.model.DrinkService;
import com.ken.drink.model.DrinkVO;
import com.redis.drinkCartTest.DrinkCartService;
import com.tang.drinkOrder.model.DrinkOrderService;
import com.tang.drinkOrder.model.DrinkOrderVO;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailService;
import com.tang.drinkOrderDetail.model.DrinkOrderDetailVO;


@Controller
@RequestMapping("/drinkOrder")
public class DrinkOrderFrontController {

	@Autowired
	DrinkOrderService drinkOrderSvc;
	
	@Autowired
	DrinkOrderDetailService drinkOrderDetailSvc;

	@Autowired
	DrinkCartService drinkCartService; 
	
	@Autowired
	StoreService storeService;
	
	@Autowired
	DrinkService drinkService;
	
	@Autowired
	UserService userSvc;

	@Autowired
	MemberService memberSvc;
	
	// ============================ 跳轉到 要下單頁面 ============================
		//需要將 購物人資訊  購物車物品 列出來 
		//給user確認 放好VO存 session
	@GetMapping("drinkOrderPage")
	public String drinkOrderPage(ModelMap model, HttpSession session) throws IOException {
		int totalPrice = 0;
		int cupNumber=0;
		DrinkOrderVO drinkOrderVO = new DrinkOrderVO();
		List<DrinkOrderDetailVO> drinkCartList = new ArrayList<>();

		//訂購人
		UserVO user = (UserVO)session.getAttribute("user");
		Integer userID = user.getUserId();
		drinkOrderVO.setUserID(userID);
		
		//訂購門市
		String str_drinkOrderStore = drinkCartService.getOneDrinkOrder(userID,"drinkOrderStore");	//從Jedis拿 先假定都從Jedis拿 =======================================
		StoreVO drinkOrderStore =  storeService.getOneStore(Integer.valueOf(str_drinkOrderStore));
		drinkOrderVO.setStoreID(Integer.valueOf(str_drinkOrderStore));
		
		//取貨時間
		String str_drinkOrderPickTime = drinkCartService.getOneDrinkOrder(userID,"drinkOrderPickTime");  //=======================================
		str_drinkOrderPickTime = str_drinkOrderPickTime.replace("T", " ") + ":00";
		Timestamp drinkOrderPickTime = Timestamp.valueOf(str_drinkOrderPickTime);
		drinkOrderVO.setDrinkOrderPickTime(drinkOrderPickTime);
		
		//使用環保杯數
		String str_cupNumber = drinkCartService.getOneDrinkOrder(userID,"cupNumber"); //=======================================
		cupNumber = Integer.valueOf(str_cupNumber);
		drinkOrderVO.setCupNumber(cupNumber);
		
		//購物車列表 + 確認訂單金額價錢
		drinkCartList = drinkCartService.getDrinkCart(userID); 
		
		for(DrinkOrderDetailVO drinkCartItem : drinkCartList) {
			int detail_pirce= 0;
			if(drinkCartItem.getDrinkOrderDetailIsJibei() == 0) {
				DrinkVO drink = drinkService.getOneDrink(drinkCartItem.getDrinkID());
//				if(drink.getDrinkDPrice().) {
//					current_pirce= drink.getDrinkDPrice() * drinkCartItem.getDrinkOrderDetailAmount();
//				}else {
				detail_pirce= drink.getDrinkPrice()* drinkCartItem.getDrinkOrderDetailAmount();
//				}
			}
			
			totalPrice += detail_pirce;
			drinkCartItem.setDrinkOrderDetailPrice(detail_pirce);
		}
		
		//若使用者確認完 傳此drinkOrderVO
		drinkOrderVO.setDrinkOrderTTPrice(totalPrice);
		drinkOrderVO.setDrinkOrderAmount(drinkCartList.size());
		drinkOrderVO.setCupNumber(cupNumber);
		
		//給前端用
		List<DrinkVO> drinkList = drinkService.getAll();
		
		//把訂單資訊存session
		session.setAttribute("drinkOrderVO",drinkOrderVO);
		session.setAttribute("drinkCartList", drinkCartList);
		
		//給前端看
		model.addAttribute("userName",user.getUserName());
		model.addAttribute("drinkOrderStore",drinkOrderStore.getStoreName());
		model.addAttribute("drinkOrderPickTime",str_drinkOrderPickTime);
		model.addAttribute("drinkOrderAmount",totalPrice);
		model.addAttribute("cupNumber",cupNumber);
		model.addAttribute("drinkCartList",drinkCartList);
		model.addAttribute("drinkList",drinkList);
		return "back-end/drinkOrder/drinkOrderPage";
	}
	
	
//	============================ 下單,跳轉到成功頁面 ============================
//  這裡含 存訂單明細的動作
	@GetMapping("order")
	synchronized public String order(ModelMap model,HttpSession session, RedirectAttributes redirectAttributes)throws IOException{

		//取得訂單 =======================================
		DrinkOrderVO drinkOrderVO = (DrinkOrderVO)session.getAttribute("drinkOrderVO"); 
		Integer userID = drinkOrderVO.getUserID(); //獲取訂購人 ID
		
		
		//判斷目前 店家環保杯數 是否滿足
		StoreVO store = storeService.getOneStore(drinkOrderVO.getStoreID()); 
		if(store.getStoreCups() != null) {
			if(store.getStoreCups() < drinkOrderVO.getCupNumber()) {
				return "redirect:/drinkOrder/orderFail";
			}
		}else {
			return "redirect:/drinkOrder/orderFail";
		}
		
		//補齊 店家 付款狀態 訂單狀態 付款方式
		drinkOrderVO.setStoreID(Integer.valueOf(drinkCartService.getOneDrinkOrder(userID, "drinkOrderStore")));
		drinkOrderVO.setDrinkOrderStatus(Byte.valueOf("0")); //訂單 狀態預設為 未完成
		drinkOrderVO.setDrinkOrderPayStatus(Byte.valueOf("0"));//付款訂單 狀態預設為 未付款
		drinkOrderVO.setDrinkOrderPayM(Byte.valueOf("0")); //付款方式 先統一取貨付款
		
//		if(drinkOrderVO.getDrinkOrderPayM() == 1) { //如果為線上付款 去綠界
//			//綠界實行
//			drinkOrderVO.setDrinkOrderPayStatus(Byte.valueOf("1")); //執行完 狀態設為 已付款
//		}
		
		DrinkOrderVO saveDrinkOrder = drinkOrderSvc.addAndGetDrinkOrder(drinkOrderVO); //存訂單

		@SuppressWarnings("unchecked")
		List<DrinkOrderDetailVO> cartDrinks = (List<DrinkOrderDetailVO>)session.getAttribute("drinkCartList"); //取出他的購物車明細
		
		for(DrinkOrderDetailVO drinkDetails : cartDrinks ) { //根據購物車 取訂單明細
			drinkDetails.setDrinkOrderVO(saveDrinkOrder); //訂單明細 綁 訂單ID 
			drinkOrderDetailSvc.addDrinkOrderDetail(drinkDetails);//存訂單明細
		}
		
		Integer storeCup = store.getStoreCups();
		store.setStoreCups(storeCup - drinkOrderVO.getCupNumber());
		storeService.updateStore(store);

		drinkCartService.deleteDrinkOrder(userID);//下訂完 刪購物人資訊
		drinkCartService.deleteDrinkCart(userID); //下訂完 刪購物車明細
				
		redirectAttributes.addAttribute("saveDrinkOrder", saveDrinkOrder);//成功頁面要呈現訂單資訊(含訂單編號 訂單時間等)
		return "redirect:/drinkOrder/orderSuccess"; //這會跑下面這個
	}
	
	@GetMapping("orderSuccess")
	public String orderSuccess(@ModelAttribute("saveDrinkOrder") DrinkOrderVO saveDrinkOrder, ModelMap model) {
		model.addAttribute("saveDrinkOrder",saveDrinkOrder);
		return "back-end/drinkOrder/orderSuccess";
	}

	@GetMapping("orderFail")
	public String orderFail(DrinkOrderVO saveDrinkOrder, ModelMap model) {
		return "back-end/drinkOrder/orderFail";
	}
	
	//============================ 錯誤驗證 會原沒登入的跳轉 ============================
	@GetMapping("userDrinkOrder")
	public String userDrinkOrder(
			@ModelAttribute("drinkOrderDetailList") ArrayList<DrinkOrderDetailVO> drinkOrderDetailList,
			ModelMap model,HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("user");
		model.addAttribute("user",user);
		List<DrinkOrderVO> userDrinkOrderList = drinkOrderSvc.getAllUserDrinkOrder(user.getUserId());
		model.addAttribute("userDrinkOrderList",userDrinkOrderList);
		return "back-end/drinkOrder/userDrinkOrder";
	}
	
	@PostMapping("userCancelDrinkOrder")
	public String userCancelDrinkOrder(@RequestParam("drinkOrderID") String drinkOrderID, ModelMap model) {
		DrinkOrderVO drinkOrder = drinkOrderSvc.getOneDrinkOrder(Integer.valueOf(drinkOrderID));
		drinkOrder.setDrinkOrderStatus(Byte.valueOf("2"));
		drinkOrderSvc.updateDrinkOrder(drinkOrder);
		
		StoreVO store = storeService.getOneStore(drinkOrder.getStoreID());
		store.setStoreCups(store.getStoreCups() + drinkOrder.getCupNumber());
		storeService.updateStore(store);
		return "redirect:/drinkOrder/userDrinkOrder";
	}
	
	
	
//=================================================================
	@GetMapping("fakeLoggingPage")
	public String fakeLoggingPage() {
		return "back-end/drinkOrder/fakeLogging";
	}
	
	@PostMapping("fakeLogging")
	public String fakeLogging(@RequestParam("user") String user,HttpSession session) {
		UserVO testUser = userSvc.getOneUser(Integer.valueOf(user));
		session.setAttribute("user",testUser);
		return "redirect:/";
	}
	
	@GetMapping("fakeLeave")
	public String fakeLeave(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/";
	}
	
	@GetMapping("fakeLoginPage")
	public String fakeLoginPage() {
		return "back-end/drinkOrder/fakeLoginPage";
	}
	
	@PostMapping("fakeLogin")
	public String fakeLogin(@RequestParam("member") String member,HttpSession session) {
		MemberVO testMember = memberSvc.getOneMember(Integer.valueOf(member));
		session.setAttribute("member",testMember);
		return "redirect:/backendHomepage";
	}
	
	@GetMapping("fakeLogout")
	public String fakeLogout(HttpSession session) {
		session.removeAttribute("member");
		return "redirect:/backendHomepage";
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
