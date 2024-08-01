package com.customer.model;

import java.sql.Timestamp;

public class CustomerVO implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer customer_id;
	private String customer_name;
	private String customer_email;
	private String customer_phone;
	private String customer_subject;
	private String customer_message;
	private Timestamp customer_time;
	
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getCustomer_email() {
		return customer_email;
	}
	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}
	public String getCustomer_phone() {
		return customer_phone;
	}
	public void setCustomer_phone(String customer_phone) {
		this.customer_phone = customer_phone;
	}
	public String getCustomer_subject() {
		return customer_subject;
	}
	public void setCustomer_subject(String customer_subject) {
		this.customer_subject = customer_subject;
	}
	public String getCustomer_message() {
		return customer_message;
	}
	public void setCustomer_message(String customer_message) {
		this.customer_message = customer_message;
	}
	public Timestamp getCustomer_time() {
		return customer_time;
	}
	public void setCustomer_time(Timestamp customer_time) {
		this.customer_time = customer_time;
	}
	public CustomerVO(Integer customer_id, String customer_name, String customer_email, String customer_phone,
			String customer_subject, String customer_message, Timestamp customer_time) {
		super();
		this.customer_id = customer_id;
		this.customer_name = customer_name;
		this.customer_email = customer_email;
		this.customer_phone = customer_phone;
		this.customer_subject = customer_subject;
		this.customer_message = customer_message;
		this.customer_time = customer_time;
	}
	public CustomerVO() {
		super();
	}
	
	
	
}
