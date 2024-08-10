package com.ken.cupRecord.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cuprecord")
public class CupRecordVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cupRecordID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cupRecordID;
	
//	@ManyToOne
//	@JoinColumn(name = "sserID")
//	private UserVO userVO;
	
	@Column(name = "userID")
	private Integer userID;
	
//	@ManyToOne
//	@JoinColumn(name = "cupID")
//	private CupVO cupVO;
	
	@Column(name = "cupID")
	private Integer cupID;
	
//	@ManyToOne
//	@JoinColumn(name = "drinkOrderID")
//	private DrinkOrderVO drinkVO;
	
	@Column(name = "drinkOrderID")
	private Integer drinkOrderID;
	
	
//	@ManyToOne
//	@JoinColumn(name = "storeRentID")
//	private StoreVO storeVO;
	
	@Column(name = "storeRentID")
	private Integer storeRentID;
	
	
//	@ManyToOne
//	@JoinColumn(name = "storeReturnID")
//	private StoreVO storeVO;
	@Column(name = "storeReturnID")
	private Integer storeReturnID;
	
//	@Temporal(TemporalType.DATE)
	@Column(name = "cupRecordRentDate")
	private Date cupRecordRentDate;
	
//	@Temporal(TemporalType.DATE)
	@Column(name = "cupRecordReturnDate")
	private Date cupRecordReturnDate;
	
	public CupRecordVO() {
		
	}


	public Integer getCupRecordID() {
		return cupRecordID;
	}


	public void setCupRecordID(Integer cupRecordID) {
		this.cupRecordID = cupRecordID;
	}


	public Integer getUserID() {
		return userID;
	}


	public void setUserID(Integer userID) {
		this.userID = userID;
	}


	public Integer getCupID() {
		return cupID;
	}


	public void setCupID(Integer cupID) {
		this.cupID = cupID;
	}


	public Integer getDrinkOrderID() {
		return drinkOrderID;
	}


	public void setDrinkOrderID(Integer drinkOrderID) {
		this.drinkOrderID = drinkOrderID;
	}


	public Integer getStoreRentID() {
		return storeRentID;
	}


	public void setStoreRentID(Integer storeRentID) {
		this.storeRentID = storeRentID;
	}


	public Integer getStoreReturnID() {
		return storeReturnID;
	}


	public void setStoreReturnID(Integer storeReturnID) {
		this.storeReturnID = storeReturnID;
	}


	public Date getCupRecordRentDate() {
		return cupRecordRentDate;
	}


	public void setCupRecordRentDate(Date cupRecordRentDate) {
		this.cupRecordRentDate = cupRecordRentDate;
	}


	public Date getCupRecordReturnDate() {
		return cupRecordReturnDate;
	}


	public void setCupRecordReturnDate(Date cupRecordReturnDate) {
		this.cupRecordReturnDate = cupRecordReturnDate;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
