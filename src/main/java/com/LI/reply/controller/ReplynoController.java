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
	@PostMapping("getOne_For_Display") //顯示單一筆資料
	public String getOne_For_Display(
			
		// 接收請求參數 - 輸入格式的錯誤處理
		@RequestParam("customerID") String customerID ,ModelMap model) {
		
		// 查詢單筆客戶留言
		CustomerVO customerVO = customerSvc.getOneCustomer(Integer.valueOf(customerID));
		model.addAttribute("customerVO", customerVO);
		
		// 查詢該客戶的所有回覆紀錄
	    List<ReplyVO> replyList = replySvc.getRepliesByCustomerID(Integer.valueOf(customerID));
	    model.addAttribute("replyListData", replyList);
	    
		return "back-end/reply/listOneCustomer"; 
		}

	
//	// 新增客服回覆跟驗證輸入 + mail
//	@PostMapping("addReply")
//    public String addReply(
//            @RequestParam("customerID") String customerID,
//            @RequestParam("replySubject") String replySubject,
//            @RequestParam("replyMessage") String replyMessage,
//            ModelMap model,HttpSession session) {
//
//        // 檢查輸入資料
//        if (replySubject == null || replySubject.trim().isEmpty() ||
//            replyMessage == null || replyMessage.trim().isEmpty()) {
//            model.addAttribute("errorMessage", "回覆主旨和內容不能為空");
//            return "back-end/reply/addReply";
//        }
//
////        MemberVO member  = (MemberVO)session.getAttribute("member"); 未上傳未連上VO
//        
//        
//        // 建立新回覆物件
//        ReplyVO replyVO = new ReplyVO();
//        replyVO.setCustomerID(Integer.valueOf(customerID));
//        replyVO.setReplySubject(replySubject);
//        replyVO.setReplyMessage(replyMessage);
//        //replyVO.setReplyTime(new Timestamp(System.currentTimeMillis()));
////        replyVO.setMemberID(member.getMemberID()); // 需要實現獲取當前回覆員工ID的方法
//
//        // 儲存回覆記錄
//        replySvc.addReply(replyVO);
//
//        // 發送Email
//        try {
//            sendEmail(customerID, replySubject, replyMessage);
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", "Email發送失敗");
//            return "back-end/reply/addReply";
//        }
//
//        return "redirect:/getOne_For_Display?customerID=" + customerID;
//    }
//
//	private void sendEmail(String customerID, String subject, String content) {
//        // 獲取客戶Email
//        CustomerVO customerVO = customerSvc.getOneCustomer(Integer.valueOf(customerID));
//        String to = customerVO.getCustomerEmail();
//
//        // 發送Email
//        emailService.sendSimpleMessage(to, subject, content);
//    }
	
	// ======================= 第二種mail ==============================

//	// 設定傳送郵件:至收信人的Email信箱,Email主旨,Email內容
//		public void sendMail(String to, String subject, String messageText) {
//
//			try {
//				// 設定使用SSL連線至 Gmail smtp Server
//				Properties props = new Properties();
//				props.put("mail.smtp.host", "smtp.gmail.com");
//				props.put("mail.smtp.socketFactory.port", "465");
//				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//				props.put("mail.smtp.auth", "true");
//				props.put("mail.smtp.port", "465");
//
//				final String myGmail = "ixlogic.wu@gmail.com";
//				final String myGmail_password = "ddjomltcnypgcstn";
//				Session session = Session.getInstance(props, new Authenticator() {
//					protected PasswordAuthentication getPasswordAuthentication() {
//						return new PasswordAuthentication(myGmail, myGmail_password);
//					}
//				});
//
//				Message message = new MimeMessage(session);
//				message.setFrom(new InternetAddress(myGmail));
//				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//
//				// 設定信中的主旨
//				message.setSubject(subject);
//				// 設定信中的內容
//				message.setText(messageText);
//
//				Transport.send(message);
//				System.out.println("傳送成功!");
//			} catch (MessagingException e) {
//				System.out.println("傳送失敗!");
//				e.printStackTrace();
//			}
//		}
//
//		public static void main(String args[]) {
//
//			String to = "juitch27931@hotmail.com";
//
//			String subject = "密碼通知"; // 待連接
//
//			String ch_name = "peter1"; // 待連接
//			String passRandom = "111"; // 待連接
//			String messageText = "Hello! " + ch_name + " 請謹記此密碼: " + passRandom + "\n" + " (已經啟用)"; // 待連接
//
//			// 發送mail
//			MailService mailService = new MailService();
//			mailService.sendMail(to, subject, messageText);
//		}
	
	
	// ================ 第三種 新增回覆+發送MAIL =================
	// 顯示新增回覆的頁面
    @GetMapping("addReply/{customerID}")
    public String showAddReplyPage(@PathVariable("customerID") Integer customerID, Model model) {
        model.addAttribute("customerID", customerID);
        return "back-end/reply/addReply";
    }

    // 新增客服回覆並發送郵件
    @PostMapping("addReply")
    public String addReply(
        @RequestParam("customerID") Integer customerID,
        @RequestParam("replySubject") String replySubject,
        @RequestParam("replyMessage") String replyMessage,
        @RequestParam("memberID") Integer memberID,
        ModelMap model) {

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