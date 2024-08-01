package com.xyuan.jibeiOrderDetail.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="jibeiOrderDetail")
public class JibeiOrderDetailVO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id		//PK
	@GeneratedValue
	@Column(name="jibeiOrderDetailID", updatable = false, insertable = false)
	private Integer jibeiOrderDetailID;
	
//	@ManyToOne
//	@JoinColumn(name="productOrderID") // 指定用來join table的column
//	private ProductOrderIDVO productOrderIDVO;
	
	@NotNull(message = "請選擇商品訂單")
	@Column(name="productOrderID", updatable = false)
	private Integer productOrderID;
	
//	@ManyToOne
//	@JoinColumn(name="jibeiProductID") // 指定用來join table的column
//	private JibeiProductIDVO jibeiProductIDVO;
	
	@NotNull(message = "請選擇寄杯商品")
	@Column(name="jibeiProductID",updatable = false)
	private Integer jibeiProductID;
	
	@NotNull(message = "訂單總數:不可為空")
	@Column(name="jibeiOrderDetailAmount",updatable = false)
	private Integer jibeiOrderDetailAmount;
	
	@NotNull(message = "訂單總價:不可為空")
	@Column(name="jibeiOrderDetailPrice",updatable = false)
	private Integer jibeiOrderDetailPrice;

	public JibeiOrderDetailVO() {
		super();
	}

	public JibeiOrderDetailVO(Integer jibeiOrderDetailID, @NotEmpty(message = "請進行登入") Integer productOrderID,
			@NotEmpty(message = "請選擇寄杯商品") Integer jibeiProductID,
			@NotEmpty(message = "訂單總數:不可為空") Integer jibeiOrderDetailAmount,
			@NotEmpty(message = "訂單總價:不可為空") Integer jibeiOrderDetailPrice) {
		super();
		this.jibeiOrderDetailID = jibeiOrderDetailID;
		this.productOrderID = productOrderID;
		this.jibeiProductID = jibeiProductID;
		this.jibeiOrderDetailAmount = jibeiOrderDetailAmount;
		this.jibeiOrderDetailPrice = jibeiOrderDetailPrice;
	}

	public Integer getJibeiOrderDetailID() {
		return jibeiOrderDetailID;
	}

	public void setJibeiOrderDetailID(Integer jibeiOrderDetailID) {
		this.jibeiOrderDetailID = jibeiOrderDetailID;
	}

	public Integer getProductOrderID() {
		return productOrderID;
	}

	public void setProductOrderID(Integer productOrderID) {
		this.productOrderID = productOrderID;
	}

	public Integer getJibeiProductID() {
		return jibeiProductID;
	}

	public void setJibeiProductID(Integer jibeiProductID) {
		this.jibeiProductID = jibeiProductID;
	}

	public Integer getJibeiOrderDetailAmount() {
		return jibeiOrderDetailAmount;
	}

	public void setJibeiOrderDetailAmount(Integer jibeiOrderDetailAmount) {
		this.jibeiOrderDetailAmount = jibeiOrderDetailAmount;
	}

	public Integer getJibeiOrderDetailPrice() {
		return jibeiOrderDetailPrice;
	}

	public void setJibeiOrderDetailPrice(Integer jibeiOrderDetailPrice) {
		this.jibeiOrderDetailPrice = jibeiOrderDetailPrice;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
