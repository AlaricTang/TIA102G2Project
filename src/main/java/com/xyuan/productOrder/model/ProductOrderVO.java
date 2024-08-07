package com.xyuan.productOrder.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "productOrder")
public class ProductOrderVO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id		
	@GeneratedValue(strategy = GenerationType.IDENTITY)		//auto increment
	@Column(name="productOrderID", updatable = false, insertable = false)
	private Integer productOrderID;
	
//	@ManyToOne
//	@JoinColumn(name="userID") 
//	private UserVO userVO;
	
	@NotNull(message="請登入")
	@Column(name="userID", updatable = false)
	private Integer userID;
	
	@NotNull(message="總金額：不可為空")
	@Column(name="productOrderTTPrice", updatable = false)
	private Integer productOrderTTPrice;
	
	@NotNull(message="商品總數：不可為空")
	@Column(name="productOrderAmount", updatable = false)
	private Integer productOrderAmount;
	
	@NotNull(message = "訂單狀態:不可為空")
	@Column(name="productOrderStatus", nullable = false)
	private Byte productOrderStatus;

	@NotEmpty(message = "訂單地址:不可為空")
	@Column(name="productOrderAddr", updatable = false)
	private String productOrderAddr;

	@Column(name="productOrderUpdateTime", insertable = false)
	private Timestamp productOrderUpdateTime;
	
	@NotNull(message="訂單建立時間，請勿留空")
	@Column(name="productOrderCreateTime", updatable = false, insertable = false)
	private Timestamp productOrderCreateTime;
	
	@NotNull(message = "付款方式:不可為空")
	@Column(name="productOrderPayM", updatable = false)
	private Byte productOrderPayM;
	
//	@ManyToOne
//	@JoinColumn(name="memberID")
//	private MemberVO memberVO;

	@NotNull(message = "最新修改(建立)之員工")
	@Column(name="memberID", insertable = false)
	private Integer memberID;

	@NotEmpty(message = "訂單Email：請填寫")
	@Email(message="Please provide a valid email address")
	@Column(name="receiverMail", updatable = false)
	private String receiverMail;
	
	@NotEmpty(message = "收件人電話:不可為空")
	@Column(name="receiverPhone", updatable = false)
	private String receiverPhone;
	
	@NotEmpty(message = "收件人姓名:不可為空")
	@Column(name="receiverName", updatable = false)
	private String receiverName;
	
	@Column(name="productOrderNote")
	private String productOrderNote;

	@NotNull
	@Column(name="productOrderPayStatus")
	private Byte productOrderPayStatus;

	public Integer getProductOrderID() {
		return productOrderID;
	}

	public void setProductOrderID(Integer productOrderID) {
		this.productOrderID = productOrderID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getProductOrderTTPrice() {
		return productOrderTTPrice;
	}

	public void setProductOrderTTPrice(Integer productOrderTTPrice) {
		this.productOrderTTPrice = productOrderTTPrice;
	}

	public Integer getProductOrderAmount() {
		return productOrderAmount;
	}

	public void setProductOrderAmount(Integer productOrderAmount) {
		this.productOrderAmount = productOrderAmount;
	}

	public Byte getProductOrderStatus() {
		return productOrderStatus;
	}

	public void setProductOrderStatus(Byte productOrderStatus) {
		this.productOrderStatus = productOrderStatus;
	}

	public String getProductOrderAddr() {
		return productOrderAddr;
	}

	public void setProductOrderAddr(String productOrderAddr) {
		this.productOrderAddr = productOrderAddr;
	}

	public Timestamp getProductOrderUpdateTime() {
		return productOrderUpdateTime;
	}

	public void setProductOrderUpdateTime(Timestamp productOrderUpdateTime) {
		this.productOrderUpdateTime = productOrderUpdateTime;
	}

	public Timestamp getProductOrderCreateTime() {
		return productOrderCreateTime;
	}

	public void setProductOrderCreateTime(Timestamp productOrderCreateTime) {
		this.productOrderCreateTime = productOrderCreateTime;
	}

	public Byte getProductOrderPayM() {
		return productOrderPayM;
	}

	public void setProductOrderPayM(Byte productOrderPayM) {
		this.productOrderPayM = productOrderPayM;
	}

	public Integer getMemberID() {
		return memberID;
	}

	public void setMemberID(Integer memberID) {
		this.memberID = memberID;
	}

	public String getReceiverMail() {
		return receiverMail;
	}

	public void setReceiverMail(String receiverMail) {
		this.receiverMail = receiverMail;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getProductOrderNote() {
		return productOrderNote;
	}

	public void setProductOrderNote(String productOrderNote) {
		this.productOrderNote = productOrderNote;
	}

	public ProductOrderVO(Integer productOrderID, @NotNull(message = "請登入") Integer userID,
			@NotNull(message = "總金額：不可為空") Integer productOrderTTPrice,
			@NotNull(message = "商品總數：不可為空") Integer productOrderAmount,
			@NotNull(message = "訂單狀態:不可為空") Byte productOrderStatus,
			@NotEmpty(message = "訂單地址:不可為空") String productOrderAddr, Timestamp productOrderUpdateTime,
			@NotNull(message = "訂單建立時間，請勿留空") Timestamp productOrderCreateTime,
			@NotNull(message = "付款方式:不可為空") Byte productOrderPayM, @NotNull(message = "最新修改(建立)之員工") Integer memberID,
			@NotEmpty(message = "訂單Email：請填寫") String receiverMail,
			@NotEmpty(message = "收件人電話:不可為空") String receiverPhone,
			@NotEmpty(message = "收件人姓名:不可為空") String receiverName, String productOrderNote) {
		super();
		this.productOrderID = productOrderID;
		this.userID = userID;
		this.productOrderTTPrice = productOrderTTPrice;
		this.productOrderAmount = productOrderAmount;
		this.productOrderStatus = productOrderStatus;
		this.productOrderAddr = productOrderAddr;
		this.productOrderUpdateTime = productOrderUpdateTime;
		this.productOrderCreateTime = productOrderCreateTime;
		this.productOrderPayM = productOrderPayM;
		this.memberID = memberID;
		this.receiverMail = receiverMail;
		this.receiverPhone = receiverPhone;
		this.receiverName = receiverName;
		this.productOrderNote = productOrderNote;
	}

	public ProductOrderVO() {
		super();
	}

	public Byte getProductOrderPayStatus() {
		return productOrderPayStatus;
	}
	public void setProductOrderPayStatus(Byte productOrderPayStatus) {
		this.productOrderPayStatus= productOrderPayStatus;
	}
	

}
