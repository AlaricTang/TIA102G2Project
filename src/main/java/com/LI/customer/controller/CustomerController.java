package com.LI.customer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

import com.LI.customer.model.CustomerVO;
import com.LI.customer.model.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerSvc;

    @GetMapping("select_page")
    public String getSelectPage(Model model) {
    	CustomerVO customerVO = new CustomerVO();
    	customerVO.setCustomerName("王小明");
    	customerVO.setCustomerEmail("@gmail.com");
    	customerVO.setCustomerPhone("0912345678");
        model.addAttribute("customerVO", customerVO);
        return "back-end/customer/select_page";
    }

    @PostMapping("insert")
    public String insert(@Valid @ModelAttribute("customerVO") CustomerVO customerVO, 
                         BindingResult result, 
                         RedirectAttributes redirectAttributes) throws IOException {
        
        if (result.hasErrors()) {
            return "back-end/customer/select_page";
        }

        // 錯誤驗證
        if (!customerVO.getCustomerName().matches("^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$")) {
            result.rejectValue("customerName", "error.customerName", "聯絡姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
        	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        }
        
        if (!customerVO.getCustomerEmail().matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            result.rejectValue("customerEmail", "error.customerEmail", "電子信箱: 信箱格式不正確，請檢查是否輸入錯誤");
        }
        
        if (!customerVO.getCustomerPhone().matches("[0-9]{4}[0-9]{3}[0-9]{3}")) {
            result.rejectValue("customerPhone", "error.customerPhone", "行動電話: 電話格式不正確，請檢查是否輸入錯誤");
        }
        
        if (customerVO.getCustomerSubject().length() > 20) {
            result.rejectValue("customerSubject", "error.customerSubject", "留言主旨: 請將主旨保持在20字以內，以便我們更高效地處理您的需求。");
        }
        
        if (customerVO.getCustomerMessage().length() > 200) {
            result.rejectValue("customerMessage", "error.customerMessage", "留言內容: 請將內容保持在200字以內，以便我們更高效地處理您的需求。");
        }

        if (result.hasErrors()) {
            return "back-end/customer/select_page";
        }
        
        // 新增留言資料
        customerSvc.addCustomer(customerVO);

        // 成功送出留言後，顯示成功訊息
        redirectAttributes.addFlashAttribute("success", "訊息已送出成功! \r\n感謝您的提問，我們將於周一至周五，\r\n上班時間AM09:00~PM17:00，為您做回覆");
        
        // 新增成功後，重導至原本留言頁面(select_page)
        return "redirect:/customer/select_page"; 
    }

    @ExceptionHandler(Exception.class)
    public String handleError(HttpServletRequest req, Exception ex, Model model) {
        model.addAttribute("errorMessage", "發生錯誤: " + ex.getMessage());
        List<CustomerVO> list = customerSvc.getAll();
        model.addAttribute("customerListData", list);
        return "back-end/customer/select_page";
    }
}