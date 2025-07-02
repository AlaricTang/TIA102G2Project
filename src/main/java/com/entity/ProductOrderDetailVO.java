package com.entity;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="productOrderDetail")
public class ProductOrderDetailVO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="productOrderDetailID", updatable = false, insertable = false)
	private Integer productOrderDetailID;
	
	@ManyToOne
	@JoinColumn(name="productOrderID")
	private ProductOrderVO productOrderVO;
	
	@ManyToOne
	@JoinColumn(name="productID")
	private ProductVO productVO;
	
	@Column(name="productOrderDetailAmount")
	private Integer productOrderDetailAmount;
	
	@Column(name="productOrderDetailPrice")
	private Integer productOrderDetailPrice;

	public Integer getProductOrderDetailID() {
		return productOrderDetailID;
	}

	public void setProductOrderDetailID(Integer productOrderDetailID) {
		this.productOrderDetailID = productOrderDetailID;
	}

	public ProductOrderVO getProductOrderVO() {
		return productOrderVO;
	}

	public void setProductOrderVO(ProductOrderVO productOrderVO) {
		this.productOrderVO = productOrderVO;
	}

	public ProductVO getProductVO() {
		return productVO;
	}

	public void setProductVO(ProductVO productVO) {
		this.productVO = productVO;
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

	public ProductOrderDetailVO() {
	}
	
	
	
	
}