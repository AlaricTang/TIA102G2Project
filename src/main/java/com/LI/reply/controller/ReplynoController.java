package com.LI.reply.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.LI.customer.model.CustomerService;
import com.LI.customer.model.CustomerVO;
import com.LI.reply.model.EmailService;
//import com.reply.model.MailService;
import com.LI.reply.model.ReplyService;
import com.LI.reply.model.ReplyVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

// 僅後台
@Controller
@Validated // 小心報錯
@RequestMapping("/reply")
public class ReplynoController {

	@Autowired
	ReplyService replySvc;

	@Autowired
	CustomerService customerSvc;

	@Autowired
	EmailService emailService;

	// Reply表格查詢特定客戶留言+客服回覆用
	@GetMapping("getOne_For_Display") // 顯示單一筆資料//如果有需要的時候把get改成post
	public String getOne_For_Display(

			// 接收請求參數 - 輸入格式的錯誤處理
			@RequestParam("customerID") String customerID, ModelMap model) {

		// 查詢單筆客戶留言
		CustomerVO customerVO = customerSvc.getOneCustomer(Integer.valueOf(customerID));
		model.addAttribute("customerVO", customerVO);

		// 查詢該客戶的所有回覆紀錄
		List<ReplyVO> replyList = replySvc.getRepliesByCustomerID(Integer.valueOf(customerID));
		model.addAttribute("replyListData", replyList);

		return "back-end/reply/listOneCustomer";
	}

	@GetMapping("listOneCustomer") // 測試用，開啟listOneCustomer用
	public String getListOneCustomer() {
		return "back-end/reply/listOneCustomer";
	}

	@GetMapping("addreply") // 測試用，開啟addreply用
	public String getAddreply() {
		return "back-end/reply/addreply";
	}

// 新增回覆+發送MAIL
	// 顯示新增回覆的頁面
	@GetMapping("/addReply/{customerID}")
	public String showAddReplyPage(@PathVariable("customerID") Integer customerID, Model model) {
		
		// 查詢單筆客戶留言
		CustomerVO customerVO = customerSvc.getOneCustomer(Integer.valueOf(customerID));
		model.addAttribute("customerVO", customerVO);

		model.addAttribute("customerID", customerID);
		return "back-end/reply/addReply";
	}

	// 新增客服回覆並發送郵件
	@PostMapping("/addReply")
	public String addReply(@RequestParam("customerID") Integer customerID,
			@RequestParam("replySubject") String replySubject, @RequestParam("replyMessage") String replyMessage,
			@RequestParam("memberID") Integer memberID, ModelMap model) {

		// 建立回覆物件並存入資料庫
		ReplyVO replyVO = new ReplyVO();
		replyVO.setCustomerID(customerID);
		replyVO.setReplySubject(replySubject);
		replyVO.setReplyMessage(replyMessage);
		replyVO.setReplyTime(new Timestamp(System.currentTimeMillis()));
		replyVO.setMemberID(memberID);
		replySvc.addReply(replyVO);

		// 查詢客戶資料以取得客戶信箱
		CustomerVO customerVO = customerSvc.getOneCustomer(customerID);
		String to = customerVO.getCustomerEmail();
		String subject = replySubject;
		String messageText = replyMessage;

		// 發送回覆郵件
		emailService.sendMail(to, subject, messageText);

		// 將新增回覆訊息顯示在回覆列表
		return "redirect:/reply/getOne_For_Display?customerID=" + customerID;
	}

}