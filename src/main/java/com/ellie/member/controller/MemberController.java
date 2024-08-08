package com.ellie.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ellie.member.model.MemberVO;
import com.ellie.member.model.MemberService;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("addMember")
    public String addMember(ModelMap model) {
        MemberVO memberVO = new MemberVO();
        model.addAttribute("memberVO", memberVO);
        return "back-end/member/addMember";
    }

    @PostMapping("insert")
    public String insert(@Valid MemberVO memberVO, BindingResult result, ModelMap model,
                         @RequestParam("memberPic") MultipartFile[] parts) throws IOException {
        if (result.hasErrors()) {
            return "back-end/member/addMember";
        }

        if (!parts[0].isEmpty()) {
            memberVO.setMemberPic(parts[0].getBytes());
        } else {
            memberVO.setMemberPic(null); 
        }

        memberService.addMember(memberVO);
        List<MemberVO> list = memberService.getAll();
        model.addAttribute("memberListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/member/listAllMember"; // 新增成功後重導至 listAllMember
    }

    @PostMapping("getOne_For_Update")
    public String getOne_For_Update(@RequestParam("memberID") Integer memberID, ModelMap model) {
        MemberVO memberVO = memberService.getOneMember(memberID);
        model.addAttribute("memberVO", memberVO);
        return "back-end/member/updateMember"; // 查詢完成後轉交 updateMember.html
    }

    @PostMapping("update")
    public String update(@Valid MemberVO memberVO, BindingResult result, ModelMap model,
                         @RequestParam("memberPic") MultipartFile[] parts) throws IOException {
        if (result.hasErrors()) {
            return "back-end/member/updateMember";
        }

        if (!parts[0].isEmpty()) {
            memberVO.setMemberPic(parts[0].getBytes());
        } else {
            byte[] existingPic = memberService.getOneMember(memberVO.getMemberID()).getMemberPic();
            memberVO.setMemberPic(existingPic);
        }

        memberService.updateMember(memberVO);
        model.addAttribute("success", "- (修改成功)");
        memberVO = memberService.getOneMember(memberVO.getMemberID());
        model.addAttribute("memberVO", memberVO);
        return "back-end/member/listOneMember"; // 修改成功後轉交 listOneMember.html
    }

    @PostMapping("delete")
    public String delete(@RequestParam("memberID") Integer memberID, ModelMap model) {
        memberService.deleteMember(memberID);
        List<MemberVO> list = memberService.getAll();
        model.addAttribute("memberListData", list);
        model.addAttribute("success", "- (刪除成功)");
        return "back-end/member/listAllMember"; // 刪除完成後轉交 listAllMember.html
    }

    @PostMapping("listMembers_ByCompositeQuery")
    public String listMembers_ByCompositeQuery(HttpServletRequest req, ModelMap model) {
        Map<String, String[]> map = req.getParameterMap();
        List<MemberVO> list = memberService.getAll(map);
        model.addAttribute("memberListData", list); // 用於 listAllMember.html
        return "back-end/member/listAllMember";
    }
}
