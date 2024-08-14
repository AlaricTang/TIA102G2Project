package com.ellie.member.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ellie.member.model.MemberVO;
import com.ellie.member.model.MemberService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;

	// 後台登入頁面
	@GetMapping("login")
	public String login() {
		return "back-end/member/login"; // 後台登入頁面
	}

	// 處理後台登入
	@PostMapping("login")
	public String login(@RequestParam("memberAcc") String memberAcc, 
	                    @RequestParam("memberPwd") String memberPwd,
	                    @RequestParam("role") String role, // 店家端或員工端
	                    HttpSession session, ModelMap model) {
	    MemberVO member = memberService.findByAcc(memberAcc);
	    if (member != null && member.getMemberPwd().equals(memberPwd)) {
	        session.setAttribute("member", member);
	        session.setAttribute("role", role); // 儲存角色資訊
	        // 統一跳轉到後台首頁
	        return "redirect:/back-end/backendHomepage";
	    } else {
	        model.addAttribute("errorMessage", "帳號或密碼錯誤");
	        return "back-end/member/login"; // 登入頁面
	    }
	}


	 // 登出功能
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

	// 顯示員工列表
	@GetMapping("/listAllMembers")
	public String listAllMembers(ModelMap modelMap) {
		List<MemberVO> members = memberService.getAll();
		modelMap.addAttribute("members", members);
		return "back-end/member/listAllMembers"; // 返回顯示員工列表
	}

	// 顯示新增員工頁面
	@GetMapping("/addMember")
	public String showAddMemberForm(ModelMap modelMap) {
		modelMap.addAttribute("memberVO", new MemberVO());
		return "back-end/member/addMember"; // 返回新增員工頁面
	}

	// 新增員工
	@PostMapping("/addMember")
	public String addMember(@ModelAttribute("memberVO") MemberVO memberVO, BindingResult result,
			@RequestParam("memberPic") MultipartFile[] parts, ModelMap model) throws IOException {
		result = removeFieldError(memberVO, result, "memberPic");
		if (result.hasErrors()) {	
			return "back-end/member/addMember";
		}

		// 檢查是否有圖片檔案
		
		if (parts != null && parts.length > 0 && !parts[0].isEmpty()) {
			memberVO.setMemberPic(parts[0].getBytes());
		} else {
			memberVO.setMemberPic(null); // 或者設置一個默認的圖片
		}
		memberVO.setCreatedByMemberID(1);
		memberService.addMember(memberVO);

		List<MemberVO> list = memberService.getAll();
		model.addAttribute("members", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/member/listAllMembers";
	}

	// 顯示修改員工頁面
	@GetMapping("/updateMember")
	public String showUpdateMemberForm(@RequestParam("memberID") Integer memberID, ModelMap modelMap) {
		MemberVO memberVO = memberService.getOneMember(memberID);
		if (memberVO != null) {
			modelMap.addAttribute("memberVO", memberVO);
			return "back-end/member/updateMember"; // 返回顯示員工修改頁面
		} else {
			return "redirect:/member/listAllMembers"; // 如果找不到該員工回到員工列表
		}
	}

	// 修改員工資料
	@PostMapping("/updateMember")
	public String updateMember(@ModelAttribute("memberVO") @Valid MemberVO memberVO, BindingResult result,
			@RequestParam("memberPic") MultipartFile[] parts, ModelMap model) throws IOException {
		result = removeFieldError(memberVO, result, "memberPic");
		if (result.hasErrors()) {
			return "back-end/member/updateMember";
		}

		// 檢查是否有圖片檔案
		if (parts != null && parts.length > 0 && !parts[0].isEmpty()) {
			memberVO.setMemberPic(parts[0].getBytes());
		} else {
			// 如果沒有上傳新的圖片，保留原有圖片
			byte[] existingPic = memberService.getOneMember(memberVO.getMemberID()).getMemberPic();
			memberVO.setMemberPic(existingPic);
		}

		memberService.updateMember(memberVO);
		model.addAttribute("success", "- (修改成功)");

		// 更新後重新顯示修改頁面
		MemberVO updatedMember = memberService.getOneMember(memberVO.getMemberID());
		model.addAttribute("memberVO", updatedMember);
		return "back-end/member/updateMember";
	}

	// 刪除員工
	@PostMapping("/deleteMember")
	public String deleteMember(@RequestParam("memberID") Integer memberID, ModelMap modelMap) {
		memberService.deleteMember(memberID); // 刪除指定的員工
		List<MemberVO> list = memberService.getAll(); // 重新獲取所有員工
		modelMap.addAttribute("members", list);
		modelMap.addAttribute("success", "- (刪除成功)");
		return "back-end/member/listAllMembers"; // 刪除成功後重導至列表頁面
	}

//	// 根據複合查詢條件列出員工
//	@PostMapping("/listMembers_ByCompositeQuery")
//	public String listMembersByCompositeQuery(HttpServletRequest req, ModelMap modelMap) {
//		Map<String, String[]> map = req.getParameterMap();
//		List<MemberVO> list = memberService.getAll(map);
//		modelMap.addAttribute("members", list);
//		return "back-end/member/listAllMembers"; // 返回顯示員工列表
//	}
	public BindingResult removeFieldError(MemberVO memberVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(memberVO, "memberVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
}
