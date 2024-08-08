package com.ellie.member.controller;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ellie.member.model.MemberVO;
import com.ellie.member.model.MemberService;

import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/member")
public class MemberController {


    @Autowired
    MemberService memberService;

    /*
     * 這個方法會處理 addMember.html 的請求。
     */
    @GetMapping("addMember")
    public String addMember(ModelMap model) {
        MemberVO memberVO = new MemberVO();
        model.addAttribute("memberVO", memberVO);
        return "back-end/member/addMember";
    }

    /*
     * 這個方法會在 addMember.html 表單提交後被呼叫，處理 POST 請求
     * 同時會進行輸入驗證
     */
    @PostMapping("insert")
    public String insert(@Valid MemberVO memberVO, BindingResult result, ModelMap model) {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        if (result.hasErrors()) {
            return "back-end/member/addMember";
        }
        /*************************** 2.開始新增資料 *****************************************/
        memberService.addMember(memberVO);
        /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
        List<MemberVO> list = memberService.getAll();
        model.addAttribute("memberListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/member/listAllMember"; // 新增成功後重導至 listAllMembers
    }

    /*
     * 這個方法會在 listAllMember.html 表單提交後被呼叫，處理 POST 請求
     */
    @PostMapping("getOne_For_Update")
    public String getOne_For_Update(@RequestParam("memberID") Integer memberID, ModelMap model) {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        /*************************** 2.開始查詢資料 *****************************************/
        MemberVO memberVO = memberService.getOneMember(memberID);
        /*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
        model.addAttribute("memberVO", memberVO);
        return "back-end/member/updateMember"; // 查詢完成後轉交 update_member_input.html
    }

    /*
     * 這個方法會在 updateMember.html 表單提交後被呼叫，處理 POST 請求
     * 同時會進行輸入驗證
     */
    @PostMapping("update")
    public String update(@Valid MemberVO memberVO, BindingResult result, ModelMap model) {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        if (result.hasErrors()) {
            return "back-end/member/updateMember";
        }
        /*************************** 2.開始修改資料 *****************************************/
        memberService.updateMember(memberVO);
        /*************************** 3.修改完成,準備轉交(Send the Success view) **************/
        model.addAttribute("success", "- (修改成功)");
        memberVO = memberService.getOneMember(memberVO.getMemberID());
        model.addAttribute("memberVO", memberVO);
        return "back-end/member/listOneMember"; // 修改成功後轉交 listOneMember.html
    }

    /*
     * 這個方法會在 listAllMember.html 表單提交後被呼叫，處理 POST 請求
     */
    @PostMapping("delete")
    public String delete(@RequestParam("memberID") Integer memberID, ModelMap model) {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        /*************************** 2.開始刪除資料 *****************************************/
        memberService.deleteMember(memberID);
        /*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
        List<MemberVO> list = memberService.getAll();
        model.addAttribute("memberListData", list);
        model.addAttribute("success", "- (刪除成功)");
        return "back-end/member/listAllMember"; // 刪除完成後轉交 listAllMembers.html
    }

    /*
     * 這個方法會在 select_page.html 表單提交後被呼叫，處理 POST 請求
     */
    @PostMapping("listMembers_ByCompositeQuery")
    public String listMembers_ByCompositeQuery(HttpServletRequest req, Model model) {
        Map<String, String[]> map = req.getParameterMap();
        List<MemberVO> list = memberService.getAll(map);
        model.addAttribute("memberListData", list); // 用於 listAllMembers.html
        return "back-end/member/listAllMember";
    }
}
