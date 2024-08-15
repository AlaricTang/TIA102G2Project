package com.ellie.user.controller;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ellie.user.model.UserService;
import com.ellie.user.model.UserVO;

@Controller
@RequestMapping("/user")
public class UserFrontController {

	@Autowired
	UserService userService;

	// 會員基本資料頁面
	@GetMapping("viewProfile")
	public String viewProfile(HttpSession session, ModelMap model) {
		UserVO user = (UserVO) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}
		model.addAttribute("userVO", user);
		return "back-end/user/viewProfile";
	}

	// 登入頁面
	@GetMapping("login")
	public String login() {
		return "back-end/user/login";
	}

	// 處理登入
	@PostMapping("login")
	public String login(@RequestParam("userEmail") String userEmail, @RequestParam("userPwd") String userPwd,
			HttpSession session, ModelMap model) {
		UserVO user = userService.findByEmail(userEmail);
		if (user != null && user.getUserPwd().equals(userPwd)) {
			session.setAttribute("user", user);
			return "redirect:/";
		} else {
			model.addAttribute("errorMessage", "帳號或密碼錯誤");
			return "back-end/user/login";
		}
	}

	// 註冊頁面
	@GetMapping("register")
	public String register() {
		return "back-end/user/register";
	}

	// 處理註冊
	@PostMapping("register")
	public String register(@ModelAttribute("userVO") UserVO userVO, BindingResult result,
			@RequestParam("userBirth") String userBirth,  ModelMap model) throws ParseException {
		result = removeFieldError(userVO, result, "userBirth");

		if (result.hasErrors()) {
			return "back-end/user/register"; // 如果有錯誤，返回註冊頁面
		}
		if (userBirth != null && userBirth.length() > 0 ) {
            SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = simpleDateFormat.parse(userBirth);
            userVO.setUserBirth(date);
        } 
		
		else {
		}
//		userVO.setUserCreateTime(new Timestamp(System.currentTimeMillis()));
//		userVO.setUserGender(1);
		userService.addUser(userVO);
		model.addAttribute("successMessage", "註冊成功，請登入");
		return "back-end/user/login";
	}

	// 忘記密碼頁面
	@GetMapping("forgotPassword")
	public String forgotPassword() {
		return "back-end/user/forgotPassword";
	}
	
//	@PostMapping("forgotPassword")
//	public String forgotPassword(@RequestParam) {
//		
//	}

	// 重設密碼頁面
	@GetMapping("resetPassword")
	public String resetPassword() {
		return "back-end/user/resetPassword";
	}

	// 處理重設密碼
	@PostMapping("resetPassword")
	public String resetPassword(@RequestParam("userEmail") String userEmail,
			@RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword,
			ModelMap model) {
		if (!newPassword.equals(confirmPassword)) {
			model.addAttribute("errorMessage", "新密碼與確認密碼不相符");
			return "back-end/user/resetPassword";
		}

		UserVO user = userService.findByEmail(userEmail);
		if (user != null) {
			user.setUserPwd(newPassword);
			userService.updateUser(user);
			model.addAttribute("successMessage", "密碼重設成功，請登入");
			return "back-end/user/login";
		} else {
			model.addAttribute("errorMessage", "該信箱未註冊");
			return "back-end/user/resetPassword";
		}
	}

	// 處理會員登出
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/";
	}

	public BindingResult removeFieldError(UserVO userVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
		result = new BeanPropertyBindingResult(userVO, "userVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
}
