package com.xyuan.productOrderDetail.model;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.xyuan.product.model.ProductVO;
import com.xyuan.productOrder.model.ProductOrderVO;

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