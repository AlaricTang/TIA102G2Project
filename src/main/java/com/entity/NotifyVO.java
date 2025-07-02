package com.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="notify" )
public class NotifyVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notifyID", updatable = false, insertable = false)
	private Integer notifyID;
	
//	@ManyToOne
//	@JoinColumn(name = "userID", insertable = false, updatable = false)
//	private UserVO userVO;  // 假設User類已存在並且映射到User表
	
//	@ManyToOne
//	@JoinColumn(name = "userID")
	@Column(name = "userID")
	private Integer userID;
	
	@NotEmpty(message = "請輸入標題")
	@Column(name = "notifySubject")
	private String notifySubject;
	
	@NotEmpty(message = "請輸入訊息")
	@Column(name = "notifyMessage")
	private String notifyMessage;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "notifyTime",updatable = false, insertable = false)
	private Date notifyTime;
	
	public NotifyVO() {
		
	}

	public Integer getNotifyID() {
		return notifyID;
	}

	public void setNotifyID(Integer notifyID) {
		this.notifyID = notifyID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getNotifySubject() {
		return notifySubject;
	}

	public void setNotifySubject(String notifySubject) {
		this.notifySubject = notifySubject;
	}

	public String getNotifyMessage() {
		return notifyMessage;
	}

	public void setNotifyMessage(String notifyMessage) {
		this.notifyMessage = notifyMessage;
	}

	public Date getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}
	
	
}
