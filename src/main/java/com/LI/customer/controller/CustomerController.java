package com.LI.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.LI.customer.model.CustomerVO;
import com.LI.customer.model.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerSvc;

    @GetMapping("select_page")
    public String getSelectPage(Model model) {
        if (!model.containsAttribute("customerVO")) {
            model.addAttribute("customerVO", new CustomerVO());
        }
        return "back-end/customer/select_page";
    }

    @PostMapping("insert")
    @ResponseBody
    public Map<String, Object> insert(@ModelAttribute CustomerVO customerVO) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        
        // 錯誤驗證
        if (customerVO.getCustomerName() == null || customerVO.getCustomerName().trim().isEmpty()) {
            errors.put("customerNameError", "請輸入您的姓名");
        } else if (!Pattern.matches("^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$", customerVO.getCustomerName().trim())) {
            errors.put("customerNameError", "只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
        }
        
        if (customerVO.getCustomerEmail() == null || customerVO.getCustomerEmail().trim().isEmpty()) {
            errors.put("customerEmailError", "請輸入您的電子信箱");
        } else if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$", customerVO.getCustomerEmail().trim())) {
            errors.put("customerEmailError", "信箱格式不正確，請檢查是否輸入錯誤");
        }
        
        if (customerVO.getCustomerPhone() == null || customerVO.getCustomerPhone().trim().isEmpty()) {
            errors.put("customerPhoneError", "請輸入您的行動電話");
        } else if (!Pattern.matches("(09)+[0-9]{8}", customerVO.getCustomerPhone().trim())) {
            errors.put("customerPhoneError", "電話格式不正確，請檢查是否輸入錯誤");
        }
        
        if (customerVO.getCustomerSubject() == null || customerVO.getCustomerSubject().trim().isEmpty()) {
            errors.put("customerSubjectError", "主旨不得為空");
        } else if (customerVO.getCustomerSubject().trim().length() > 20) {
            errors.put("customerSubjectError", "請將主旨保持在20字以內，以便我們更高效地處理您的需求");
        }
        
        if (customerVO.getCustomerMessage() == null || customerVO.getCustomerMessage().trim().isEmpty()) {
            errors.put("customerMessageError", "內容不得為空");
        } else if (customerVO.getCustomerMessage().trim().length() > 200) {
            errors.put("customerMessageError", "請將內容保持在200字以內，以便我們更高效地處理您的需求");
        }

        if (errors.isEmpty()) {
            customerSvc.addCustomer(customerVO);
            response.put("success", true);
            response.put("message", "訊息已送出成功! 感謝您的提問，我們將於週一至週五 09:00 - 17:00 回覆您的訊息");
        } else {
            response.put("success", false);
            response.put("errors", errors);
        }
        
        return response;
    }
}