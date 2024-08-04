package com.tang.drinkOrder.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.ellie.user.model.UserVO;

@Entity
@Table(name="drinkorder")
public class DrinkOrderVO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id //@Id代表這個屬性是這個Entity的唯一識別屬性，並且對映到Table的主鍵 
	@GeneratedValue(strategy = GenerationType.IDENTITY)//@GeneratedValue的generator屬性指定要用哪個generator //【strategy的GenerationType, 有四種值: AUTO, IDENTITY, SEQUENCE, TABLE】 
	@Column(name="drinkorderID", updatable = false, insertable = false)//@Column指這個屬性是對應到資料庫Table的哪一個欄位   //【非必要，但當欄位名稱與屬性名稱不同時則一定要用】
	private Integer drinkOrderID;
	
//	@ManyToOne
//	@JoinColumn(name="userID") // 指定用來join table的column
//	private UserVO userVO;
	
	@NotNull(message = "請進行登入")
	@Column(name="userID", updatable = false)
	private Integer userID;
	
//	@ManyToOne
//	@JoinColumn(name="storeID")
//	private StoreVO storeVO;

	@NotNull(message = "請選擇店家")
	@Column(name="storeID", updatable = false)
	private Integer storeID;
	
	@NotNull(message = "訂單總數:不可為空")
	@Column(name="drinkOrderAmount",updatable = false)
	private Integer drinkOrderAmount;

	@NotNull(message = "取貨時間:不可為空")
	@Future(message = "取貨日期:須為今日(不含)以後")
	@Column(name="drinkOrderPickTime",updatable = false)
	private Timestamp drinkOrderPickTime;
	
	@NotNull(message = "付款方式:不可為空")
	@Column(name="drinkOrderPayM",updatable = false)
	private Byte drinkOrderPayM;
	
	@NotNull(message = "訂單總價:不可為空")
	@Column(name="drinkOrderTTPrice",updatable = false)
	private Integer drinkOrderTTPrice;
	
	@NotNull(message = "訂單狀態:不可為空")
	@Column(name="drinkOrderStatus")
	private Byte drinkOrderStatus;
	
	@NotNull
	@Column(name="drinkOrderPayStatus")
	private Byte drinkOrderPayStatus;
	
	@Column(name="drinkOrderUpdateTime", insertable = false)
	private Timestamp drinkOrderUpdateTime;
	
	@Column(name="drinkOrderCreateTime" ,updatable = false, insertable = false)
	private Timestamp drinkOrderCreateTime;
		
//	@ManyToOne
//	@JoinColumn(name="memberID")
//	private MemberVO memberVO;

	@NotNull(message = "最新修改(建立)之員工")
	@Column(name="memberID", insertable = false)
	private Integer memberID;

	@Column(name = "cupNumber",updatable = false)
	private Integer cupNumber;
	
	//其他地方的fk
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "drinkOrderVO")
//	@OrderBy("drinkOrderDetailID asc")
//	private Set<DrinkOrderDetailVO> drinkOrderDetails = new HashSet<>();
	
	
	public DrinkOrderVO() {
	}

	public Integer getDrinkOrderID() {
		return drinkOrderID;
	}
	public void setDrinkOrderID(Integer drinkOrderID) {
		this.drinkOrderID = drinkOrderID;
	}

//	public UserVO getUserVO() {
//		return userVO;
//	}
//
//	public void setUserVO(UserVO userVO) {
//		this.userVO = userVO;
//	}
	
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	
	
	

	public Integer getStoreID() {
		return storeID;
	}
	public void setStoreID(Integer storeID) {
		this.storeID = storeID;
	}

	
	
	public Integer getDrinkOrderAmount() {
		return drinkOrderAmount;
	}
	public void setDrinkOrderAmount(Integer drinkOrderAmount) {
		this.drinkOrderAmount = drinkOrderAmount;
	}

	
	
	public Timestamp getDrinkOrderPickTime() {
		return drinkOrderPickTime;
	}
	public void setDrinkOrderPickTime(Timestamp drinkOrderPickTime) {
		this.drinkOrderPickTime = drinkOrderPickTime;
	}

	
	
	public Byte getDrinkOrderPayM() {
		return drinkOrderPayM;
	}
	public void setDrinkOrderPayM(Byte drinkOrderPayM) {
		this.drinkOrderPayM = drinkOrderPayM;
	}

	
	
	public Integer getDrinkOrderTTPrice() {
		return drinkOrderTTPrice;
	}
	public void setDrinkOrderTTPrice(Integer drinkOrderTTPrice) {
		this.drinkOrderTTPrice = drinkOrderTTPrice;
	}

	
	
	public Byte getDrinkOrderStatus() {
		return drinkOrderStatus;
	}
	public void setDrinkOrderStatus(Byte drinkOrderStatus) {
		this.drinkOrderStatus = drinkOrderStatus;
	}
	
	
	
	public Byte getDrinkOrderPayStatus() {
		return drinkOrderPayStatus;
	}
	public void setDrinkOrderPayStatus(Byte drinkOrderPayStatus) {
		this.drinkOrderPayStatus = drinkOrderPayStatus;
	}

	
	
	public Timestamp getDrinkOrderUpdateTime() {
		return drinkOrderUpdateTime;
	}
	public void setDrinkOrderUpdateTime(Timestamp drinkOrderUpdateTime) {
		this.drinkOrderUpdateTime = drinkOrderUpdateTime;
	}

	
	
	public Timestamp getDrinkOrderCreateTime() {
		return drinkOrderCreateTime;
	}
	public void setDrinkOrderCreateTime(Timestamp drinkOrderCreateTime) {
		this.drinkOrderCreateTime = drinkOrderCreateTime;
	}

	
	
	public Integer getMemberID() {
		return memberID;
	}
	public void setMemberID(Integer memberID) {
		this.memberID = memberID;
	}
	
	public Integer getCupNumber() {
		return cupNumber;
	}
	public void setCupNumber(Integer cupNumber) {
		this.cupNumber = cupNumber;
	}



//	public Set<DrinkOrderDetailVO> getDrinkOrderDetails() {
//		return drinkOrderDetails;
//	}
//
//	public void setDrinkOrderDetails(Set<DrinkOrderDetailVO> drinkOrderDetails) {
//		this.drinkOrderDetails = drinkOrderDetails;
//	}

	@Override
	public String toString() {
		return "DrinkOrderVO [drinkOrderID=" + drinkOrderID + ", userID=" + userID + ", storeID=" + storeID
				+ ", drinkOrderAmount=" + drinkOrderAmount + ", drinkOrderPickTime=" + drinkOrderPickTime
				+ ", drinkOrderPayM=" + drinkOrderPayM + ", drinkOrderTTPrice=" + drinkOrderTTPrice
				+ ", drinkOrderStatus=" + drinkOrderStatus + ", drinkOrderPayStatus=" + drinkOrderPayStatus
				+ ", drinkOrderUpdateTime=" + drinkOrderUpdateTime + ", drinkOrderCreateTime=" + drinkOrderCreateTime
				+ ", memberID=" + memberID + ", cupNumber=" + cupNumber + "]";
	}
	
	
}
