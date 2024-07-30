package com.xyuan.productOrder.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "productOrder")
public class ProductOrderVO {

	@Id		
	@GeneratedValue(strategy = GenerationType.IDENTITY)		//auto increment
	@Column(name="productOrderID", updatable = false, insertable = false)
	private Integer productOrderID;
	
	@Column(name="userID", nullable = false, updatable = false)
	private Integer userID;
	
	@Column(name="productOrderTTPrice", nullable = false, updatable = false)
	private Integer productOrderTTPrice;
	
	@Column(name="productOrderAmount", nullable = false, updatable = false)
	private Integer productOrderAmount;
	
	@Column(name="productOrderStatus", nullable = false)
	private Byte productOrderStatus;

	@Column(name="productOrderAddr", nullable = false, updatable = false)
	private String productOrderAddr;

	@Column(name="productOrderUpdateTime", insertable = false)
	private Timestamp productOrderUpdateTime;
	
	@Column(name="productOrderCreateTime", nullable = false, updatable = false, insertable = false)
	private Timestamp productOrderCreateTime;
	
	@Column(name="productOrderPayM", nullable = false, updatable = false)
	private Byte productOrderPayM;
	
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
