package com.LI.Customer.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.customer.model.CustomerService;
import com.customer.model.CustomerVO;
@WebServlet("/back-end/customer/customer.do")
public class CustomerServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
//
//				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("customer_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入留言ID"); 
				}
				// Send the use back to the form, if there were errors // 去往哪頁
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/customer/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer customer_id = null;
				try {
					customer_id = Integer.valueOf(str);
				} catch (Exception e) {
					errorMsgs.add("留言ID格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/customer/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				CustomerService customerSvc = new CustomerService();
				CustomerVO customerVO = customerSvc.getOneCustomer(customer_id);
				if (customerVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/customer/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("customerVO", customerVO); // 資料庫取出的customerVO物件,存入req
				String url = "/back-end/customer/listOneCustomer.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneCustomer.jsp
				successView.forward(req, res);
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllCustomer.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
				/***************************1.接收請求參數****************************************/
				Integer customer_id = Integer.valueOf(req.getParameter("customer_id"));
				
				/***************************2.開始查詢資料****************************************/
				CustomerService customerSvc = new CustomerService();
				CustomerVO customerVO = customerSvc.getOneCustomer(customer_id);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("customerVO", customerVO);         // 資料庫取出的customerVO物件,存入req
				String url = "/back-end/customer/update_customer_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_customer_input.jsp
				successView.forward(req, res);
		}
		
		
		if ("update".equals(action)) { // 來自update_customer_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
//				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
Integer customer_id = Integer.valueOf(req.getParameter("customer_id").trim());
				
String customer_name = req.getParameter("customer_name");
				String customer_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (customer_name == null || customer_name.trim().length() == 0) {
					errorMsgs.add("客戶姓名: 請勿空白");
				} else if(!customer_name.trim().matches(customer_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("客戶姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				String emailregex = "^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$";
String customer_email = req.getParameter("customer_email").trim();
				if (customer_email == null || customer_email.trim().length() == 0) {
					errorMsgs.add("欄位請勿空白");
				}else if(!customer_email.trim().matches(emailregex)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("客戶信箱: 信箱地址不正確，請檢查是否輸入錯誤");
	            }
				
				String phoneregex = "[0-9]{4}[0-9]{3}[0-9]{3}";
String customer_phone = req.getParameter("customer_phone").trim();
				if (customer_phone == null || customer_phone.trim().length() == 0) {
					errorMsgs.add("欄位請勿空白");
				}else if(!customer_phone.trim().matches(phoneregex)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("客戶行動電話: 輸入格式不正確");
				}

				String customer_subject = req.getParameter("customer_subject");
//				String customer_subjectReg = "^{2,10}$";
				if (customer_subject == null || customer_subject.trim().length() == 0) {
					errorMsgs.add("留言主旨: 請勿空白");
				} 
				
				String customer_message = req.getParameter("customer_message");
//				String customer_messageReg = "^{2,10}$";
				if (customer_message == null || customer_message.trim().length() == 0) {
					errorMsgs.add("留言內容: 請勿空白");
				} 

//				Timestamp customer_time = Timestamp.valueOf(req.getParameter("customer_time").trim());

//Integer deptno = Integer.valueOf(req.getParameter("deptno").trim());
//
				CustomerVO customerVO = new CustomerVO();
				customerVO.setCustomer_id(customer_id);
				customerVO.setCustomer_name(customer_name);
				customerVO.setCustomer_email(customer_email);
				customerVO.setCustomer_phone(customer_phone);
				customerVO.setCustomer_subject(customer_subject);
				customerVO.setCustomer_message(customer_message);
//				customerVO.setCustomer_time(customer_time);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
req.setAttribute("customerVO", customerVO); // 含有輸入格式錯誤的customerVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/customer/update_customer_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
//				
				/***************************2.開始修改資料*****************************************/
				CustomerService customerSvc = new CustomerService();
				customerVO = customerSvc.updateCustomer(customer_id, customer_name, customer_email, customer_phone, customer_subject, customer_message, null);
				
//				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("customerVO", customerVO); // 資料庫update成功後,正確的的customerVO物件,存入req
				String url = "/back-end/customer/listOneCustomer.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneCustomer.jsp
				successView.forward(req, res);
		}

        if ("insert".equals(action)) { // 來自addCustomer.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

//				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			
			String customer_name = req.getParameter("customer_name");
			String customer_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (customer_name == null || customer_name.trim().length() == 0) {
				errorMsgs.add("客戶姓名: 請勿空白");
			} else if(!customer_name.trim().matches(customer_nameReg)) { //以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("客戶姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
            }

			String emailregex = "^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$";
String customer_email = req.getParameter("customer_email").trim();
			if (customer_email == null || customer_email.trim().length() == 0) {
				errorMsgs.add("欄位請勿空白");
			}else if(!customer_email.trim().matches(emailregex)) { //以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("客戶信箱: 信箱地址不正確，請檢查是否輸入錯誤");
            }
			
			String phoneregex = "[0-9]{4}[0-9]{3}[0-9]{3}";
String customer_phone = req.getParameter("customer_phone").trim();
			if (customer_phone == null || customer_phone.trim().length() == 0) {
				errorMsgs.add("欄位請勿空白");
			}else if(!customer_phone.trim().matches(phoneregex)) { //以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("客戶行動電話: 輸入格式不正確");
			}

			String customer_subject = req.getParameter("customer_subject");
//			String customer_subjectReg = "^{2,10}$";
			if (customer_subject == null || customer_subject.trim().length() == 0) {
				errorMsgs.add("留言主旨: 請勿空白");
			} 
			
			String customer_message = req.getParameter("customer_message");
//			String customer_messageReg = "^{2,10}$";
			if (customer_message == null || customer_message.trim().length() == 0) {
				errorMsgs.add("留言內容: 請勿空白");
			} 
			
//			Timestamp customer_time = Timestamp.valueOf(req.getParameter("customer_time").trim());
			
			CustomerVO customerVO = new CustomerVO();

			customerVO.setCustomer_name(customer_name);
			customerVO.setCustomer_email(customer_email);
			customerVO.setCustomer_phone(customer_phone);
			customerVO.setCustomer_subject(customer_subject);
			customerVO.setCustomer_message(customer_message);
//			customerVO.setCustomer_time(null);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
req.setAttribute("customerVO", customerVO); // 含有輸入格式錯誤的customerVO物件,也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/customer/addCustomer.jsp");
				failureView.forward(req, res);
				return; //程式中斷
			}

//				
//				/***************************2.開始新增資料***************************************/
			CustomerService customerSvc = new CustomerService();
				customerVO = customerSvc.addCustomer(customer_name, customer_email, customer_phone, customer_subject, customer_message,null);
//				
//				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/customer/listAllCustomer.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllCustomer.jsp
				successView.forward(req, res);				
		}
		
		
		if ("delete".equals(action)) { // 來自listAllCustomer.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
				/***************************1.接收請求參數***************************************/
				Integer customer_id = Integer.valueOf(req.getParameter("customer_id"));
				
				/***************************2.開始刪除資料***************************************/
				CustomerService customerSvc = new CustomerService();
				customerSvc.deleteCustomer(customer_id);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/customer/listAllCustomer.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
		}
	}
}
	

	

