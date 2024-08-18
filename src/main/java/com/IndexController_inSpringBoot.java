package com;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ken.cup.model.CupService;
import com.ken.cup.model.CupVO;
import com.ken.cupRecord.model.CupRecordService;
import com.ken.cupRecord.model.CupRecordVO;
import com.ken.drink.model.DrinkService;
import com.ken.drink.model.DrinkVO;



@Controller
public class IndexController_inSpringBoot {
	
//	@Autowired
//	EmpService empSvc;
	
//	@Autowired
//	DeptService deptSvc;
	
	@Autowired
	DrinkService drinkSvc;
	
	@Autowired	
	CupService cupSvc;
	
	@Autowired
	CupRecordService cupRecordSvc;
	
    @GetMapping("/")
    public String index(Model model) {
    	List<DrinkVO> drinkList = drinkSvc.getAll();
    	model.addAttribute("drinkList",drinkList);
        return "index"; //view
    }
    
    @GetMapping("/backendHomepage")
    public String backendHomepage(Model model,HttpSession session) {
    	if(session.getAttribute("member") != null) {
    		return "backendHomepage";
    	}else {
    		return "back-end/member/login";
    	}
    }
    
    // =================== 飲品 ======================= //
    @GetMapping("/drink/select_page")
    public String select_page_drink(Model model) {
    	return "back-end/drink/select_page";
    }
    
    @GetMapping("drink/listAllDrinkFront")
    public String listAllDrinkFront(Model model) {
    	return "back-end/drink/listAllDrinkFront";
    }
    
    @ModelAttribute("drinkListData") // for select_page.html
    protected List<DrinkVO> referenceListDataDrink(Model model) {
		
    	List<DrinkVO> list = drinkSvc.getAll();
		return list;
	}
    
    // =================== 飲品 ======================= //
    // =================== 環保杯 ======================= //
    @GetMapping("/cup/select_page")
    public String select_page_cup(Model model) {
    	return "back-end/cup/select_page";
    }
    
    @GetMapping("/cup/listAllCup")
	public String listAllCup(Model model) {
		return "back-end/cup/listAllCup";
	}
    
    @GetMapping("/cup/userRentCup")
    public String rentCup(Model model) {
    	return "back-end/cup/userRentCup";
    }
    
    @GetMapping("/cup/userReturnCup")
    public String returnCup(Model model) {
    	return "back-end/cup/userReturnCup";
    }
    
    @GetMapping("/cup/discardCup")
    public String discardCup(Model model) {
    	return "back-end/cup/discardCup";
    }
    
    @GetMapping("/cup/countCup")
    public String countCups(Model model) {
    	return "back-end/cup/select_page";
    }
    
	@GetMapping("/cup/addManyCupForm")
	public String showAddManyCupForm() {
	    return "back-end/cup/addManyCupForm";
	}
    
    @ModelAttribute("cupListData") // for select_page.html
    protected List<CupVO> referenceListDataCup(Model model) {
		
    	List<CupVO> list = cupSvc.getAll();
		return list;
	}
    
    // =================== 環保杯 ======================= // 
    // =================== 環保杯紀錄 ======================= //
    @GetMapping("/cupRecord/select_page")
    public String select_page_cupRecord(Model model) {
    	return "back-end/cupRecord/select_page";
    }
    
    @GetMapping("/cupRecord/listAllCupRecord")
	public String listAllCupRecord(Model model) {
		return "back-end/cupRecord/listAllCupRecord";
	}
    
    @ModelAttribute("cupRecordListData") // for select_page.html
    protected List<CupRecordVO> referenceListDataCupRecord(Model model) {
		
    	List<CupRecordVO> list = cupRecordSvc.getAll();
		return list;
	}
    // =================== 環保杯紀錄 ======================= //
}