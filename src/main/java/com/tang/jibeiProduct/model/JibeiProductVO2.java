package com.tang.jibeiProduct.model;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.google.gson.annotations.Expose;
import com.ken.drink.model.DrinkVO;
import com.xyuan.jibeiOrderDetail.model.JibeiOrderDetailVO;

@Entity
@Table(name="jibeiproduct")
public class JibeiProductVO2 implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id //@Id代表這個屬性是這個Entity的唯一識別屬性，並且對映到Table的主鍵 
	@GeneratedValue(strategy = GenerationType.IDENTITY)//@GeneratedValue的generator屬性指定要用哪個generator //【strategy的GenerationType, 有四種值: AUTO, IDENTITY, SEQUENCE, TABLE】 
	@Column(name="jibeiProductID", updatable = false, insertable = false)//@Column指這個屬性是對應到資料庫Table的哪一個欄位   //【非必要，但當欄位名稱與屬性名稱不同時則一定要用】
	private Integer jibeiProductID;
	
	
	@ManyToOne
	@JoinColumn(name="drinkID") // 指定用來join table的column
	private DrinkVO drinkVO;
	
//	@NotNull(message = "請選擇商品")
//	@Column(name="drinkID")
//	private Integer drinkID;
	
	@NotNull(message = "請給予價錢")
	@Column(name="jibeiProductPrice")
	private Integer jibeiProductPrice;
	
	@Column(name="jibeiProductDes")
	private String jibeiProductDes;

	@NotNull(message = "請綁定該商品寄杯數量")
	@Column(name="jibeiProductAmount")
	private Integer jibeiProductAmount;
	
	@NotNull(message = "給予上下架之狀態")
	@Column(name="jibeiProductStatus")
	private Byte jibeiProductStatus;
	
	@Expose(serialize = false)
	@Column(name="jibeiProductUpdateTime")
	private Timestamp jibeiProductUpdateTime;
	
	@Expose(serialize = false)
	@Column(name="jibeiProductCreateTime" ,updatable = false, insertable = false)
	private Timestamp jibeiProductCreateTime;
	
//	@ManyToOne
//	@JoinColumn(name="memberID")
//	private MemberVO memberVO;

	@NotNull(message = "最新修改(建立)之員工")
	@Column(name="memberID")
	private Integer memberID;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="jibeiProductVO")
	@OrderBy("jibeiOrderDetailID asc")
	private Set<JibeiOrderDetailVO> jibeiOrderDetailVO = new HashSet<JibeiOrderDetailVO>();
	
	
	public JibeiProductVO2() {
	}

	public Integer getJibeiProductID() {
		return jibeiProductID;
	}

	public void setJibeiProductID(Integer jibeiProductID) {
		this.jibeiProductID = jibeiProductID;
	}

//	public Integer getDrinkID() {
//		return drinkID;
//	}
//
//	public void setDrinkID(Integer drinkID) {
//		this.drinkID = drinkID;
//	}
	
	

	public Integer getJibeiProductPrice() {
		return jibeiProductPrice;
	}

	public DrinkVO getDrinkVO() {
		return drinkVO;
	}

	public void setDrinkVO(DrinkVO drinkVO) {
		this.drinkVO = drinkVO;
	}

	public void setJibeiProductPrice(Integer jibeiProductPrice) {
		this.jibeiProductPrice = jibeiProductPrice;
	}

	public String getJibeiProductDes() {
		return jibeiProductDes;
	}

	public void setJibeiProductDes(String jibeiProductDes) {
		this.jibeiProductDes = jibeiProductDes;
	}

	public Integer getJibeiProductAmount() {
		return jibeiProductAmount;
	}

	public void setJibeiProductAmount(Integer jibeiProductAmount) {
		this.jibeiProductAmount = jibeiProductAmount;
	}

	public Byte getJibeiProductStatus() {
		return jibeiProductStatus;
	}

	public void setJibeiProductStatus(Byte jibeiProductStatus) {
		this.jibeiProductStatus = jibeiProductStatus;
	}

	public Timestamp getJibeiProductUpdateTime() {
		return jibeiProductUpdateTime;
	}

	public void setJibeiProductUpdateTime(Timestamp jibeiProductUpdateTime) {
		this.jibeiProductUpdateTime = jibeiProductUpdateTime;
	}

	public Timestamp getJibeiProductCreateTime() {
		return jibeiProductCreateTime;
	}

	public void setJibeiProductCreateTime(Timestamp jibeiProductCreateTime) {
		this.jibeiProductCreateTime = jibeiProductCreateTime;
	}

	public Integer getMemberID() {
		return memberID;
	}

	public void setMemberID(Integer memberID) {
		this.memberID = memberID;
	}
	
	
	
}
