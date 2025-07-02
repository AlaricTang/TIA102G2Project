package com.entity;

import java.sql.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "reply")
public class ReplyVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "replyID")
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer replyID;
	
//	@ManyToOne
//	@JoinColumn(name = "customerID")
//	private CustomerVO customerVO;
	
	@NotNull
	@Column(name = "customerID",updatable = false)
	private Integer customerID;
	
	@NotEmpty
	@Column(name = "replySubject")
	private String replySubject;
	
	@NotEmpty
	@Column(name = "replyMessage")
	private String replyMessage;
	
//	@NotNull
	@Column(name = "replyTime")
	private Timestamp replyTime;
	
//	@ManyToOne
//	@JoinColumn(name = "memberID")
//	private MemberVO memberVO;
	
	@NotNull
	@Column(name = "memberID")
	private Integer memberID;
//	private Set<ReplyVO> replys = new HashSet<ReplyVO>();

	

	public ReplyVO() {
	}


	
	public Integer getReplyID() {
		return replyID;
	}
	public void setReplyID(Integer replyID) {
		this.replyID = replyID;
	}



	public Integer getCustomerID() {
		return customerID;
	}
	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}
	
	
	
	public String getReplySubject() {
		return replySubject;
	}
	public void setReplySubject(String replySubject) {
		this.replySubject = replySubject;
	}



	public String getReplyMessage() {
		return replyMessage;
	}
	public void setReplyMessage(String replyMessage) {
		this.replyMessage = replyMessage;
	}



	public Timestamp getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Timestamp replyTime) {
		this.replyTime = replyTime;
	}



	public Integer getMemberID() {
		return memberID;
	}
	public void setMemberID(Integer memberID) {
		this.memberID = memberID;
	}

	
	
//
//	@Id
//	@Column(name = "replyID")
//	@GeneratedValue(strategy = GenerationType.IDENTITY) 
//	public Integer getReplyID() {
//		return replyID;
//	}
//
//	public void setReplyID(Integer replyID) {
//		this.replyID = replyID;
//	}
//
////	@ManyToOne
////	@JoinColumn(name = "customerID")
////	public CustomerVO getCustomer() {
////		return customer;
////	}
//
//	public void setCustomerID(CustomerVO customer) {
//		this.customer = customer;
//	}
//
//	@Column(name = "replySubject")
//	@NotEmpty
//	public String getReplySubject() {
//		return replySubject;
//	}
//
//	public void setReplySubject(String replySubject) {
//		this.replySubject = replySubject;
//	}
//
//	@Column(name = "replyMessage")
//	@NotEmpty
//	public String getReplyMessage() {
//		return replyMessage;
//	}
//
//	public void setReplyMessage(String replyMessage) {
//		this.replyMessage = replyMessage;
//	}
//
//	@Column(name = "replyTime")
//	@NotNull
//	public Timestamp getReplyTime() {
//		return replyTime;
//	}
//
//	public void setReplyTime(Timestamp replyTime) {
//		this.replyTime = replyTime;
//	}
//
//	@Column(name = "memberID")
//	@NotNull
//	public Integer getMemberID() {
//		return memberID;
//	}
//
//	public void setMemberID(Integer memberID) {
//		this.memberID = memberID;
//	}
//
////	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="customerVO")
////	@OrderBy("reply asc")
////	public Set<ReplyVO> getReplys() {
////		return replys;
////	}
////
////	public void setReplys(Set<ReplyVO> replys) {
////		this.replys = replys;
////	}
//
//	
	
}
