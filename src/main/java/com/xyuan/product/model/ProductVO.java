package com.xyuan.product.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="product")
public class ProductVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="productID", updatable = false, insertable = false)
	private Integer productID;
	
	@NotEmpty(message = "請輸入商品名稱")
	@Column(name = "productName")
	private String productName;
	
	@NotNull(message = "商品價格:不可為空")
	@Column(name="productPrice")
	private Integer productPrice;

	@Column(name="productDprice")
	private Integer productDprice;
	
	
	@Column(name="productDes")
	private String productDes;		//text
	
	
	@NotNull(message = "商品庫存:不可為空")
	@Column(name="productInv")
	private Integer productInv;
	
	
	@NotNull(message = "商品狀態:不可為空")
	@Column(name="productStatus")
	private byte productStatus;		//tinyint
	
	@NotNull(message = "商品種類:不可為空")
	@Column(name="productTag")
	private Byte productTag;
	
	@Column(name="productPic")
	private byte[] productPic;	//MEDIUMBLOB
	
	@Column(name="productUpdateTime")
	private Timestamp productUpdateTime;

	@Column(name="productCreateTime",insertable = false, updatable = false)
	private Timestamp productCreateTime;
	
//	@ManyToOne
//	@JoinColumn(name="memberID") 
//	private MemberVO memberVO;
	
	@Column(name="memberID", nullable = false)
	private Integer memberID;

	public Integer getProductID() {
		return productID;
	}

	public void setProductID(Integer productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getProductDprice() {
		return productDprice;
	}

	public void setProductDprice(Integer productDprice) {
		this.productDprice = productDprice;
	}

	public String getProductDes() {
		return productDes;
	}

	public void setProductDes(String productDes) {
		this.productDes = productDes;
	}

	public Integer getProductInv() {
		return productInv;
	}

	public void setProductInv(Integer productInv) {
		this.productInv = productInv;
	}

	public byte getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(byte productStatus) {
		this.productStatus = productStatus;
	}

	public Byte getProductTag() {
		return productTag;
	}

	public void setProductTag(Byte productTag) {
		this.productTag = productTag;
	}

	public byte[] getProductPic() {
		return productPic;
	}

	public void setProductPic(byte[] productPic) {
		this.productPic = productPic;
	}

	public Timestamp getProductUpdateTime() {
		return productUpdateTime;
	}

	public void setProductUpdateTime(Timestamp productUpdateTime) {
		this.productUpdateTime = productUpdateTime;
	}

	public Timestamp getProductCreateTime() {
		return productCreateTime;
	}

	public void setProductCreateTime(Timestamp productCreateTime) {
		this.productCreateTime = productCreateTime;
	}

	public Integer getMemberID() {
		return memberID;
	}

	public void setMemberID(Integer memberID) {
		this.memberID = memberID;
	}

	public ProductVO(Integer productID, @NotEmpty(message = "請輸入商品名稱") String productName,
			@NotNull(message = "商品價格:不可為空") Integer productPrice, Integer productDprice, String productDes,
			@NotNull(message = "商品庫存:不可為空") Integer productInv, @NotNull(message = "商品狀態:不可為空") byte productStatus,
			Byte productTag, byte[] productPic, Timestamp productUpdateTime, Timestamp productCreateTime,
			Integer memberID) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productDprice = productDprice;
		this.productDes = productDes;
		this.productInv = productInv;
		this.productStatus = productStatus;
		this.productTag = productTag;
		this.productPic = productPic;
		this.productUpdateTime = productUpdateTime;
		this.productCreateTime = productCreateTime;
		this.memberID = memberID;
	}

	public ProductVO() {
		super();
	}
	
	
	
	
	
}
