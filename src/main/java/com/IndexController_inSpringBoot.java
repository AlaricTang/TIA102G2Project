package com;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ken.cup.model.CupService;
import com.ken.cup.model.CupVO;
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
	
    @GetMapping("/")
    public String index(Model model) {
        return "index"; //view
    }
    
    // http://......../hello?name=peter1
//    @GetMapping("/hello")
//    public String indexWithParam(
//            @RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {
//        model.addAttribute("message", name);
//        return "index"; //view
//    }
    
  
    //=========== 以下第63~75行是提供給 /src/main/resources/templates/back-end/emp/select_page.html 與 listAllEmp.html 要使用的資料 ===================   
//    @GetMapping("/emp/select_page")
//	public String select_page(Model model) {
//		return "back-end/emp/select_page";
//	}
//    
//    @GetMapping("/emp/listAllEmp")
//	public String listAllEmp(Model model) {
//		return "back-end/emp/listAllEmp";
//	}
//    
//    @ModelAttribute("empListData")  // for select_page.html 第97 109行用 // for listAllEmp.html 第85行用
//	protected List<EmpVO> referenceListData(Model model) {
//		
//    	List<EmpVO> list = empSvc.getAll();
//		return list;
//	}
//    
//	@ModelAttribute("deptListData") // for select_page.html 第135行用
//	protected List<DeptVO> referenceListData_Dept(Model model) {
//		model.addAttribute("deptVO", new DeptVO()); // for select_page.html 第133行用
//		List<DeptVO> list = deptSvc.getAll();
//		return list;
//	}
    
    // =================== 飲品 ======================= //
    @GetMapping("/drink/select_page")
    public String select_page_drink(Model model) {
    	return "back-end/drink/select_page";
    }
    
//    @GetMapping("/drink/listAllDrink")
//	public String listAllDrink(Model model) {
//		return "back-end/drink/listAllDrink";
//	}
    
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
    
    @ModelAttribute("cupListData") // for select_page.html
    protected List<CupVO> referenceListDataCup(Model model) {
		
    	List<CupVO> list = cupSvc.getAll();
		return list;
	}
    
    // =================== 環保杯 ======================= //
}