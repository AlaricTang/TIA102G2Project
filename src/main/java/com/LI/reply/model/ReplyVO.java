package com.LI.reply.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.LI.customer.model.CustomerVO;

@Entity
@Table(name = "reply")
public class ReplyVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer replyID;
	private CustomerVO customer;
	private String replySubject;
	private String replyMessage;
	private Timestamp replyTime;
	private Integer memberID;
//	private Set<ReplyVO> replys = new HashSet<ReplyVO>();

	

	public ReplyVO() {
	}

	@Id
	@Column(name = "replyID")
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	public Integer getReplyID() {
		return replyID;
	}

	public void setReplyID(Integer replyID) {
		this.replyID = replyID;
	}

//	@ManyToOne
//	@JoinColumn(name = "customerID")
//	public CustomerVO getCustomer() {
//		return customer;
//	}

	public void setCustomerID(CustomerVO customer) {
		this.customer = customer;
	}

	@Column(name = "replySubject")
	@NotEmpty
	public String getReplySubject() {
		return replySubject;
	}

	public void setReplySubject(String replySubject) {
		this.replySubject = replySubject;
	}

	@Column(name = "replyMessage")
	@NotEmpty
	public String getReplyMessage() {
		return replyMessage;
	}

	public void setReplyMessage(String replyMessage) {
		this.replyMessage = replyMessage;
	}

	@Column(name = "replyTime")
	@NotNull
	public Timestamp getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Timestamp replyTime) {
		this.replyTime = replyTime;
	}

	@Column(name = "memberID")
	@NotNull
	public Integer getMemberID() {
		return memberID;
	}

	public void setMemberID(Integer memberID) {
		this.memberID = memberID;
	}

//	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="customerVO")
//	@OrderBy("reply asc")
//	public Set<ReplyVO> getReplys() {
//		return replys;
//	}
//
//	public void setReplys(Set<ReplyVO> replys) {
//		this.replys = replys;
//	}

	
	
}
