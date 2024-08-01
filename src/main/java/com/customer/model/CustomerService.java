package com.customer.model;

import java.sql.Timestamp;
import java.util.List;

public class CustomerService {

	private CustomerDAO_interface dao;

	public CustomerService() {
		dao = new CustomerJDBCDAO();
	}

	public CustomerVO addCustomer(String name, String email, String phone,
			String subject, String message, Timestamp time) {

		CustomerVO customerVO = new CustomerVO();

		customerVO.setCustomer_name(name);
		customerVO.setCustomer_email(email);
		customerVO.setCustomer_phone(phone);
		customerVO.setCustomer_subject(subject);
		customerVO.setCustomer_message(message);
		customerVO.setCustomer_time(time);
		dao.insert(customerVO);

		return customerVO;
	}

	public CustomerVO updateCustomer(Integer id, String name, String email,
			String phone, String subject, String message,Timestamp time) {

		CustomerVO customerVO = new CustomerVO();

		customerVO.setCustomer_id(id);
		customerVO.setCustomer_name(name);
		customerVO.setCustomer_email(email);
		customerVO.setCustomer_phone(phone);
		customerVO.setCustomer_subject(subject);
		customerVO.setCustomer_message(message);
		customerVO.setCustomer_time(time);
		
		dao.update(customerVO);

		return customerVO;
	}

	public void deleteCustomer(Integer customer_id) {
		dao.delete(customer_id);
	}

	public CustomerVO getOneCustomer(Integer customer_id) {
		return dao.findByPrimaryKey(customer_id);
	}

	public List<CustomerVO> getAll() {
		return dao.getAll();
	}
}
