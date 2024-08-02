package com;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class IndexController_inSpringBoot {
	
//	@Autowired
//	EmpService empSvc;
	
//	@Autowired
//	DeptService deptSvc;
	
    @GetMapping("/")
    public String index(Model model) {
        return "back-end/index"; //view
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

}