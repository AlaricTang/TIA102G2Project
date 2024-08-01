package com.xyuan.productOrder.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

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
	
	@NotEmpty(message="請登入")
	@Column(name="userID", updatable = false)
	private Integer userID;
	
	@NotEmpty(message="總金額：不可為空")
	@Column(name="productOrderTTPrice", updatable = false)
	private Integer productOrderTTPrice;
	
	@NotEmpty(message="商品總數：不可為空")
	@Column(name="productOrderAmount", updatable = false)
	private Integer productOrderAmount;
	
	@NotEmpty(message = "訂單狀態:不可為空")
	@Column(name="productOrderStatus", nullable = false)
	private Byte productOrderStatus;

	@NotEmpty(message = "訂單地址:不可為空")
	@Column(name="productOrderAddr", updatable = false)
	private String productOrderAddr;

	@Column(name="productOrderUpdateTime", insertable = false)
	private Timestamp productOrderUpdateTime;
	
	@NotEmpty(message="訂單建立時間，請勿留空")
	@Column(name="productOrderCreateTime", updatable = false, insertable = false)
	private Timestamp productOrderCreateTime;
	
	@NotEmpty(message = "付款方式:不可為空")
	@Column(name="productOrderPayM", updatable = false)
	private Byte productOrderPayM;
	
//	@ManyToOne
//	@JoinColumn(name="memberID") 
//	private MemberVO memberVO;
	
	@Column(name="memberID", nullable = false)
	private Integer memberID;

	
	
	
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




	public ProductOrderVO() {
	}




	@Override
	public String toString() {
		return "ProductOrder [productOrderID=" + productOrderID + ", userID=" + userID + ", productOrderTTPrice="
				+ productOrderTTPrice + ", productOrderAmount=" + productOrderAmount + ", productOrderStatus="
				+ productOrderStatus + ", productOrderAddr=" + productOrderAddr + ", productOrderUpdateTime="
				+ productOrderUpdateTime + ", productOrderCreateTime=" + productOrderCreateTime + ", productOrderPayM="
				+ productOrderPayM + ", memberID=" + memberID + "]";
	}

	

}
