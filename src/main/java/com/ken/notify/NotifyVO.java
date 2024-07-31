package com.ken.notify;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="notify" )
public class NotifyVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notifyID", updatable = false, insertable = false)
	private Integer notifyID;
	
//	private Integer userID;
	
	@NotEmpty(message = "請輸入標題")
	@Column(name = "notifySubject")
	private String notifySubject;
	
	@NotEmpty(message = "請輸入訊息")
	@Column(name = "notifyMessage")
	private String notifyMessage;
	
	@Column(name = "notifyTime",updatable = false, insertable = false)
	private Date notifyTime;
}
