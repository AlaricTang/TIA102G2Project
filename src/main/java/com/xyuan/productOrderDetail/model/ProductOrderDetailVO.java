package com.xyuan.productOrderDetail.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="productOrderDetail")
public class ProductOrderDetailVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id		//PK
	@GeneratedValue
	@Column(name="productOrderDetailID", updatable = false, insertable = false)
	private Integer productOrderDetailID;
	
	//FK
//	@ManyToOne
//	@JoinColumn(name="productOrderID") 
//	private ProductOrderIDVO productOrderIDVO;
	
	@NotEmpty(message = "請選擇商品訂單")
	@Column(name="productOrderID", updatable = false)
	private Integer productOrderID;
	
	//FK
//	@ManyToOne
//	@JoinColumn(name="productID") 
//	private ProductIDVO productIDVO;
	
	@NotEmpty(message = "請選擇商品")
	@Column(name="productID", updatable = false)
	private Integer productID;
	
	@NotEmpty(message = "訂單總數:不可為空")
	@Column(name="productOrderDetailAmount",updatable = false)
	private Integer productOrderDetailAmount;
	
	@NotEmpty(message = "訂單總價:不可為空")
	@Column(name="productOrderDetailPrice",updatable = false)
	private Integer productOrderDetailPrice;

	public Integer getProductOrderDetailID() {
		return productOrderDetailID;
	}

	public void setProductOrderDetailID(Integer productOrderDetailID) {
		this.productOrderDetailID = productOrderDetailID;
	}

	public Integer getProductOrderID() {
		return productOrderID;
	}

	public void setProductOrderID(Integer productOrderID) {
		this.productOrderID = productOrderID;
	}

	public Integer getProductID() {
		return productID;
	}

	public void setProductID(Integer productID) {
		this.productID = productID;
	}

	public Integer getProductOrderDetailAmount() {
		return productOrderDetailAmount;
	}

	public void setProductOrderDetailAmount(Integer productOrderDetailAmount) {
		this.productOrderDetailAmount = productOrderDetailAmount;
	}

	public Integer getProductOrderDetailPrice() {
		return productOrderDetailPrice;
	}

	public void setProductOrderDetailPrice(Integer productOrderDetailPrice) {
		this.productOrderDetailPrice = productOrderDetailPrice;
	}

	public ProductOrderDetailVO(Integer productOrderDetailID, @NotEmpty(message = "請選擇商品訂單") Integer productOrderID,
			@NotEmpty(message = "請選擇商品") Integer productID,
			@NotEmpty(message = "訂單總數:不可為空") Integer productOrderDetailAmount,
			@NotEmpty(message = "訂單總價:不可為空") Integer productOrderDetailPrice) {
		super();
		this.productOrderDetailID = productOrderDetailID;
		this.productOrderID = productOrderID;
		this.productID = productID;
		this.productOrderDetailAmount = productOrderDetailAmount;
		this.productOrderDetailPrice = productOrderDetailPrice;
	}

	public ProductOrderDetailVO() {
		super();
	}
	
	
	
	
}
