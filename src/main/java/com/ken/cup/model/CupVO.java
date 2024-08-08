package com.ken.cup.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="Cup" )
public class CupVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cupID", updatable = false ,insertable = false)
	private Integer cupID;
	
//	@ManyToOne
//	@JoinColumn(name = "UserID")
	@Column(name = "userID")
	private Integer userID;
	
//	@ManyToOne
//	@JoinColumn(name = "StoreID")
	@Column(name = "storeID")
	private Integer storeID;
	
//    @ManyToOne
//    @JoinColumn(name = "memberID")
	@Column(name = "memberID")
	private Integer memberID;
	
	@Column(name = "cupStatus")
	private Integer cupStatus; // 0(歸還), 1(出租), 2(報廢)
	
//	@Temporal(TemporalType.DATE)
    @Column(name = "cupRentDate" ,insertable = false)
	private Date cupRentDate;
	

    @Column(name = "cupCreateDate",updatable = false ,insertable = false)
	private Date cupCreateDate;

    public CupVO() {
    	
    }

	public Integer getCupID() {
		return cupID;
	}


	public void setCupID(Integer cupID) {
		this.cupID = cupID;
	}


//	public UserVO getUserID() {
//		return this.userID;
//	}
	
	public Integer getUserID() {
		return userID;
	}


	public void setUserID(Integer userID) {
		this.userID = userID;
	}


//	public StoreVO getStoreID() {
//		return this.storeVO;
//	}
	
	public Integer getStoreID() {
		return storeID;
	}


	public void setStoreID(Integer storeID) {
		this.storeID = storeID;
	}

//	public MemberVO getMemberID() {
//		return this.memberVO;
//	}
	

	public Integer getMemberID() {
		return memberID;
	}


	public void setMemberID(Integer memberID) {
		this.memberID = memberID;
	}


	public Integer getCupStatus() {
		return cupStatus;
	}


	public void setCupStatus(Integer cupStatus) {
		this.cupStatus = cupStatus;
	}


	public Date getCupRentDate() {
		return cupRentDate;
	}


	public void setCupRentDate(Date cupRentDate) {
		this.cupRentDate = cupRentDate;
	}


	public Date getCupCreateDate() {
		return cupCreateDate;
	}


	public void setCupCreateDate(Date cupCreateDate) {
		this.cupCreateDate = cupCreateDate;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
   
}
