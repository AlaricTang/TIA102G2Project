package com.tang.drinkOrder.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;

import com.tang.drinkOrderDetail.model.DrinkOrderDetailVO;

@Entity
@Table(name="drinkorderdetail")
public class DrinkOrderVO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id //@Id代表這個屬性是這個Entity的唯一識別屬性，並且對映到Table的主鍵 
	@GeneratedValue(strategy = GenerationType.IDENTITY)//@GeneratedValue的generator屬性指定要用哪個generator //【strategy的GenerationType, 有四種值: AUTO, IDENTITY, SEQUENCE, TABLE】 
	@Column(name="drinkorderdetailID", updatable = false, insertable = false)//@Column指這個屬性是對應到資料庫Table的哪一個欄位   //【非必要，但當欄位名稱與屬性名稱不同時則一定要用】
	private Integer drinkorderdetailID;
//test
//	@ManyToOne
//	@JoinColumn(name="userID") // 指定用來join table的column
//	private UserVO userVO;
	
	@NotEmpty(message = "請進行登入")
	@Column(name="userID", updatable = false)
	private Integer userID;
	
//	@ManyToOne
//	@JoinColumn(name="storeID")
//	private StoreVO storeVO;

	@NotEmpty(message = "請選擇店家")
	@Column(name="storeID", updatable = false)
	private Integer storeID;
	
	@NotEmpty(message = "訂單總數:不可為空")
	@Column(name="drinkOrderAmount",updatable = false)
	private Integer drinkOrderAmount;

	@NotEmpty(message = "取貨時間:不可為空")
	@Future(message = "取貨日期:須為今日(不含)以後")
	@Column(name="drinkOrderPickTime",updatable = false)
	private Timestamp drinkOrderPickTime;
	
	@NotEmpty(message = "付款方式:不可為空")
	@Column(name="drinkOrderPayM",updatable = false)
	private Byte drinkOrderPayM;
	
	@NotEmpty(message = "訂單總價:不可為空")
	@Column(name="drinkOrderTTPrice",updatable = false)
	private Integer drinkOrderTTPrice;
	
	@NotEmpty(message = "訂單狀態:不可為空")
	@Column(name="drinkOrderStatus")
	private Byte drinkOrderStatus;
	
	@Column(name="drinkOrderUpdateTime", insertable = false)
	private Timestamp drinkOrderUpdateTime;
	
	@NotEmpty(message="訂單建立時間，請勿留空")
	@Column(name="drinkOrderCreateTime" ,updatable = false, insertable = false)
	private Timestamp drinkOrderCreateTime;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "drinkOrderVO")
	@OrderBy("drinkOrderDetailID asc")
	private Set<DrinkOrderDetailVO> drinkOrderDetails = new HashSet<>();
	
//	@ManyToOne
//	@JoinColumn(name="memberID")
//	private MemberVO memberVO;
	

	@NotEmpty(message = "最新修改(建立)之員工")
	@Column(name="memberID", insertable = false)
	private Integer memberID;

	
	
	public DrinkOrderVO() {
	}

	public Integer getDrinkorderdetailID() {
		return drinkorderdetailID;
	}

	public void setDrinkorderdetailID(Integer drinkorderdetailID) {
		this.drinkorderdetailID = drinkorderdetailID;
	}

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

	
	public Set<DrinkOrderDetailVO> getDrinkOrderDetails() {
		return drinkOrderDetails;
	}

	public void setDrinkOrderDetails(Set<DrinkOrderDetailVO> drinkOrderDetails) {
		this.drinkOrderDetails = drinkOrderDetails;
	}
	
	@Override
	public String toString() {
		return "DrinkOrderVO [drinkorderdetailID=" + drinkorderdetailID + ", userID=" + userID + ", storeID=" + storeID
				+ ", drinkOrderAmount=" + drinkOrderAmount + ", drinkOrderPickTime=" + drinkOrderPickTime
				+ ", drinkOrderPayM=" + drinkOrderPayM + ", drinkOrderTTPrice=" + drinkOrderTTPrice
				+ ", drinkOrderStatus=" + drinkOrderStatus + ", drinkOrderUpdateTime=" + drinkOrderUpdateTime
				+ ", drinkOrderCreateTime=" + drinkOrderCreateTime + ", memberID=" + memberID + "]";
	}
	
	
	
}
