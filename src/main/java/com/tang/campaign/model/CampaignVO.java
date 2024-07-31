package com.tang.campaign.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Campaign")
public class CampaignVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="campaignID", updatable = false, insertable = false)
	private Integer campaignID;
	
	@Column(name="campaignName")
	private String campaignName;
	
	//折數
	@Column(name="campaignDiscount")
	private Integer campaignDiscount;
	
	@Column(name="campaignDes")
	private String campaignDes;
	
	
	@Column(name="campaignStartDate")
	private Date campaignStartDate;
	
	
	@Column(name="campaignEndDate")
	private Date campaignEndDate;
	
	@Column(name="campaignPic")
	private byte[] campaignPic;
	
	public CampaignVO() {
	}

	public Integer getCampaignID() {
		return campaignID;
	}

	public void setCampaignID(Integer campaignID) {
		this.campaignID = campaignID;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public Integer getCampaignDiscount() {
		return campaignDiscount;
	}

	public void setCampaignDiscount(Integer campaignDiscount) {
		this.campaignDiscount = campaignDiscount;
	}

	public String getCampaignDes() {
		return campaignDes;
	}

	public void setCampaignDes(String campaignDes) {
		this.campaignDes = campaignDes;
	}

	public Date getCampaignStartDate() {
		return campaignStartDate;
	}

	public void setCampaignStartDate(Date campaignStartDate) {
		this.campaignStartDate = campaignStartDate;
	}

	public Date getCampaignEndDate() {
		return campaignEndDate;
	}

	public void setCampaignEndDate(Date campaignEndDate) {
		this.campaignEndDate = campaignEndDate;
	}

	public byte[] getCampaignPic() {
		return campaignPic;
	}

	public void setCampaignPic(byte[] campaignPic) {
		this.campaignPic = campaignPic;
	}
	
	
	
}
