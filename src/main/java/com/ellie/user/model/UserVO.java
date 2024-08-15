package com.ellie.user.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

	@Entity
	@Table(name = "User")
	public class UserVO implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "userID")
		private Integer userID;

		@NotEmpty(message = "電子郵件: 請勿空白")
		@Column(name = "userEmail", nullable = false, unique = true)
		private String userEmail;

		@NotEmpty(message = "密碼: 請勿空白")
		@Column(name = "userPwd", nullable = false)
		private String userPwd;

		@NotEmpty(message = "姓名: 請勿空白")
		@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$", message = "員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間")
		@Column(name = "userName")
		private String userName;

		@Column(name = "userBirth")
		@Temporal(TemporalType.DATE)
		private Date userBirth;

		@NotEmpty(message = "請填寫電話號碼")
		@Column(name = "userPhone")
		private String userPhone;

		
		@Column(name = "userGender")
		private Integer userGender;

		@NotEmpty(message = "地址請勿空白")
		@Column(name = "userAddr")
		private String userAddr;

		@Column(name = "userCreateTime", updatable = false, insertable = false)
		private Timestamp userCreateTime;

		public Integer getUserId() {
			return userID;
		}

		public void setUserId(Integer userID) {
			this.userID = userID;
		}

		public String getUserEmail() {
			return userEmail;
		}

		public void setUserEmail(String userEmail) {
			this.userEmail = userEmail;
		}

		public String getUserPwd() {
			return userPwd;
		}

		public void setUserPwd(String userPwd) {
			this.userPwd = userPwd;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public Date getUserBirth() {
			return userBirth;
		}

		public void setUserBirth(Date userBirth) {
			this.userBirth = userBirth;
		}

		public String getUserPhone() {
			return userPhone;
		}

		public void setUserPhone(String userPhone) {
			this.userPhone = userPhone;
		}

		public Integer getUserGender() {
			return userGender;
		}

		public void setUserGender(Integer userGender) {
			this.userGender = userGender;
		}

		public String getUserAddr() {
			return userAddr;
		}

		public void setUserAddr(String userAddr) {
			this.userAddr = userAddr;
		}

		public Timestamp getUserCreateTime() {
			return userCreateTime;
		}

		public void setUserCreateTime(Timestamp userCreateTime) {
			this.userCreateTime = userCreateTime;
		}

		@Override
		public String toString() {
			return "UserVO [userId=" + userID + ", userEmail=" + userEmail + ", userPwd=" + userPwd + ", userName="
					+ userName + ", userBirth=" + userBirth + ", userPhone=" + userPhone + ", userGender=" + userGender
					+ ", userAddr=" + userAddr + ", userCreateTime=" + userCreateTime + "]";
		}
	}
