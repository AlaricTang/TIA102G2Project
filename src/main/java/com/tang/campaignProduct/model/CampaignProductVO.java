package com.tang.campaignProduct.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CampaignProduct")
public class CampaignProductVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CampaignProductID", updatable = false, insertable = false)
	private Integer campaignProductID;
	
//	@ManyToOne
//	@JoinColumn(name="campaignID")
//	private CampaignVO campaignVO;
	
	@Column(name="campaignID")
	private Integer campaignID;
	
//	@ManyToOne
//	@JoinColumn(name="productID")
//	private ProductVO productVO;
	
	@Column(name="productID")
	private Integer productID;

	
//	@ManyToOne
//	@JoinColumn(name="drinkID")
//	private DrinkVO drinkVO;
	
	@Column(name="drinkID")
	private Integer drinkID;
	


	public CampaignProductVO() {
	}

	public Integer getCampaignProductID() {
		return campaignProductID;
	}

	public void setCampaignProductID(Integer campaignProductID) {
		this.campaignProductID = campaignProductID;
	}

	public Integer getCampaignID() {
		return campaignID;
	}

	public void setCampaignID(Integer campaignID) {
		this.campaignID = campaignID;
	}

	public Integer getProductID() {
		return productID;
	}

	public void setProductID(Integer productID) {
		this.productID = productID;
	}
	
	public Integer getDrinkID() {
		return drinkID;
	}

	public void setDrinkID(Integer drinkID) {
		this.drinkID = drinkID;
	}
	
}
