package com.tang.drinkOrderDetail.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="drinkorderdetail")
public class DrinkOrderDetailVO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id //@Id代表這個屬性是這個Entity的唯一識別屬性，並且對映到Table的主鍵 
	@GeneratedValue(strategy = GenerationType.IDENTITY)//@GeneratedValue的generator屬性指定要用哪個generator //【strategy的GenerationType, 有四種值: AUTO, IDENTITY, SEQUENCE, TABLE】 
	@Column(name="drinkorderdetailID", updatable = false, insertable = false)//@Column指這個屬性是對應到資料庫Table的哪一個欄位   //【非必要，但當欄位名稱與屬性名稱不同時則一定要用】
	private Integer drinkOrderDetailID;
	
//	@ManyToOne
//	@JoinColumn(name="drinkOrderID")
//	private DrinkOrderVO drinkOrderVO;

	@Column(name="drinkOrderID")
	private Integer drinkOrderID;

//	@ManyToOne
//	@JoinColumn(name="drinkID")
//	private DrinkVO drinkVO;

	@Column(name="drinkID")
	private Integer drinkID;
	
	@NotEmpty(message="請選擇冷熱")
	@Column(name="drinkOrderDetailIsHot")
	private Byte drinkOrderDetailIsHot;
	
	@NotEmpty(message="請選擇是否使用環保杯")
	@Column(name="drinkOrderDetailUseCup")
	private Byte drinkOrderDetailUseCup;
	
	@NotEmpty(message = "請輸入此飲品價錢")
	@Column(name="drinkOrderDetailPrice")
	private Integer drinkOrderDetailPrice;
	
	@NotEmpty(message="請確認數量")
	@Column(name="drinkOrderDetailAmount")
	private Integer drinkOrderDetailAmount;

	
	@NotEmpty(message="是否為寄杯")
	@Column(name="drinkOrderDetailIsJibei")
	private Byte drinkOrderDetailIsJibei;
	
	public DrinkOrderDetailVO() {
	}
	
	

	public Integer getDrinkOrderDetailID() {
		return drinkOrderDetailID;
	}
	public void setDrinkOrderDetailID(Integer drinkOrderDetailID) {
		this.drinkOrderDetailID = drinkOrderDetailID;
	}



	public Integer getDrinkOrderID() {
		return drinkOrderID;
	}
	public void setDrinkOrderID(Integer drinkOrderID) {
		this.drinkOrderID = drinkOrderID;
	}



	public Integer getDrinkID() {
		return drinkID;
	}
	public void setDrinkID(Integer drinkID) {
		this.drinkID = drinkID;
	}



	public Byte getDrinkOrderDetailIsHot() {
		return drinkOrderDetailIsHot;
	}
	public void setDrinkOrderDetailIsHot(Byte drinkOrderDetailIsHot) {
		this.drinkOrderDetailIsHot = drinkOrderDetailIsHot;
	}



	public Byte getDrinkOrderDetailUseCup() {
		return drinkOrderDetailUseCup;
	}
	public void setDrinkOrderDetailUseCup(Byte drinkOrderDetailUseCup) {
		this.drinkOrderDetailUseCup = drinkOrderDetailUseCup;
	}



	public Integer getDrinkOrderDetailPrice() {
		return drinkOrderDetailPrice;
	}
	public void setDrinkOrderDetailPrice(Integer drinkOrderDetailPrice) {
		this.drinkOrderDetailPrice = drinkOrderDetailPrice;
	}



	public Integer getDrinkOrderDetailAmount() {
		return drinkOrderDetailAmount;
	}
	public void setDrinkOrderDetailAmount(Integer drinkOrderDetailAmount) {
		this.drinkOrderDetailAmount = drinkOrderDetailAmount;
	}



	public Byte getDrinkOrderDetailIsJibei() {
		return drinkOrderDetailIsJibei;
	}
	public void setDrinkOrderDetailIsJibei(Byte drinkOrderDetailIsJibei) {
		this.drinkOrderDetailIsJibei = drinkOrderDetailIsJibei;
	}



	@Override
	public String toString() {
		return "DrinkOrderDetailVO [drinkOrderDetailID=" + drinkOrderDetailID + ", drinkOrderID=" + drinkOrderID
				+ ", drinkID=" + drinkID + ", drinkOrderDetailIsHot=" + drinkOrderDetailIsHot
				+ ", drinkOrderDetailUseCup=" + drinkOrderDetailUseCup + ", drinkOrderDetailPrice="
				+ drinkOrderDetailPrice + ", drinkOrderDetailAmount=" + drinkOrderDetailAmount + "]";
	}



	
	
	
	
}
