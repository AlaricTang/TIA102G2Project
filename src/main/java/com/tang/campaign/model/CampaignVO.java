package com.tang.campaign.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.tang.campaignProduct.model.CampaignProductVO;

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
	
	//註1:【現在是設定成 cascade="all" lazy="false" inverse="true"之意】
	//註2:【mappedBy="多方的關聯屬性名"：用在雙向關聯中，把關係的控制權反轉】【deptVO是EmpVO的屬性】
	//註3:【原預設為 @OneToMany(fetch=FetchType.LAZY, mappedBy="deptVO")之意】--> 【是指原為  lazy="true"  inverse="true"之意】
	//FetchType.EAGER : Defines that data must be eagerly fetched
	//FetchType.LAZY  : Defines that data can be lazily fetched
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="campaignVO")
	@OrderBy("campaignProductID asc")
	private Set<CampaignProductVO> campaignProducts = new HashSet<CampaignProductVO>();
	
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
	

	public Set<CampaignProductVO> getCampaignProducts() {
		return campaignProducts;
	}

	public void setCampaignProducts(Set<CampaignProductVO> campaignProducts) {
		this.campaignProducts = campaignProducts;
	}
	
}
