package com.entity;

import java.sql.Timestamp;

// 將 javax.persistence.* 和 javax.validation.* 改為 jakarta.*
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "customer")
public class CustomerVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer customerID;
	private String customerName;
	private String customerEmail;
	private String customerPhone;
	private String customerSubject;
	private String customerMessage;
	private Timestamp customerTime;
//	private Set<CustomerVO> customers = new HashSet<CustomerVO>();

	public CustomerVO() {
	}

	@Id
	@Column(name = "customerID")
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
//	@NotNull
	public Integer getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}

	@Column(name = "customerName")
	@NotEmpty
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "customerEmail")
	@NotEmpty
	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	@Column(name = "customerPhone")
	@NotEmpty
	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	@Column(name = "customerSubject")
	@NotEmpty
	public String getCustomerSubject() {
		return customerSubject;
	}

	public void setCustomerSubject(String customerSubject) {
		this.customerSubject = customerSubject;
	}

	@Column(name = "customerMessage")
	@NotEmpty
	public String getCustomerMessage() {
		return customerMessage;
	}

	public void setCustomerMessage(String customerMessage) {
		this.customerMessage = customerMessage;
	}

	@Column(name ="customerTime")
//	@NotNull
	@CreationTimestamp//自動生成時間
	public Timestamp getCustomerTime() {
		return customerTime;
	}

	public void setCustomerTime(Timestamp customerTime) {
		this.customerTime = customerTime;
	}
	
	
}
