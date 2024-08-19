package com.ellie.user.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ellie.member.model.MemberVO;
import com.ellie.user.model.UserService;
import com.ellie.user.model.UserVO;
import com.ellie.user.util.GmailService;

@Controller
@RequestMapping("/user")
public class UserFrontController {

	@Autowired
	UserService userService;

	@Autowired
	GmailService gmailService;

	// 會員基本資料頁面
	@GetMapping("viewProfile")
	public String viewProfile(HttpSession session, ModelMap model) {
		UserVO user = (UserVO) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}
//		System.out.println("Viewing profile for user: " + user.getUserEmail());
		UserVO userVO = userService.findById(user.getUserId());
		model.addAttribute("userVO", userVO);
		return "back-end/user/viewProfile";
	}

	@GetMapping("/updateProfile")
	public String updateProfileForm(@RequestParam("userId") Integer userId, ModelMap model) {
		UserVO user = userService.getOneUser(userId);
		if (user != null) {
			model.addAttribute("userVO", user);
			return "back-end/user/updateProfile";
		} else {
			return "redirect:/user/viewProfile";
		}
	}

	// 處理會員資料更新
	@PostMapping("/updateProfile")
	public String updateProfile(@ModelAttribute("userVO") UserVO userVO, BindingResult result,
								@RequestParam("userBirth") String userBirth, ModelMap model) throws ParseException {
		result = removeFieldError(userVO, result, "userBirth");

		if (result.hasErrors()) {
			return "back-end/user/updateProfile";
		}

		if (userBirth != null && userBirth.length() > 0) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = simpleDateFormat.parse(userBirth);
			userVO.setUserBirth(date);
		}
		String pwd = userService.findById(userVO.getUserId()).getUserPwd();
		userVO.setUserPwd(pwd);
		userService.updateUser(userVO);
		model.addAttribute("success", " (修改成功)");
//		System.out.println("Updated user: " + userVO);

		// 更新後重新顯示修改頁面
		UserVO updatedUser = userService.findById(userVO.getUserId());
		model.addAttribute("userVO", updatedUser);
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
			@RequestParam("userBirth") String userBirth, ModelMap model) throws ParseException {
		result = removeFieldError(userVO, result, "userBirth");

		if (result.hasErrors()) {
			return "back-end/user/register"; // 如果有錯誤，返回註冊頁面
		}

		if (userBirth != null && userBirth.length() > 0) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = simpleDateFormat.parse(userBirth);
			userVO.setUserBirth(date);
		}

		userService.addUser(userVO);
		model.addAttribute("successMessage", "註冊成功，請登入");
		return "back-end/user/login";
	}

	// 忘記密碼頁面
	@GetMapping("forgotPassword")
	public String forgotPassword() {
		return "back-end/user/forgotPassword";
	}

	// 處理忘記密碼
	@PostMapping("forgotPassword")
	public String forgotPassword(@RequestParam("userEmail") String userEmail, ModelMap model) {
		UserVO user = userService.findByEmail(userEmail);
		if (user != null) {
			// 發送重設密碼的信件，信件中包含重設密碼的指示
			String subject = "密碼重設請求";
			String messageText = "請點擊以下連結進行密碼重設: http://tia102g2.ddns.net/user/resetPassword/"+userEmail;
			gmailService.sendMail(userEmail, subject, messageText);

			model.addAttribute("successMessage", "重設密碼的信件已發送，請檢查您的信箱");
			return "back-end/user/forgotPassword";
		} else {
			model.addAttribute("errorMessage", "該信箱未註冊");
			return "back-end/user/forgotPassword";
		}
	}

	// 重設密碼頁面
	@GetMapping("resetPassword/{userEmail}")
	public String resetPassword(@PathVariable("userEmail") String userEmail, ModelMap model) {
		model.addAttribute("userEmail", userEmail);
		return "back-end/user/resetPassword";
	}

	// 處理重設密碼
	@PostMapping("resetPassword")
	public String resetPassword(@RequestParam("userEmail") String userEmail,
								@RequestParam("newPassword") String newPassword, 
								@RequestParam("confirmPassword") String confirmPassword,
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
