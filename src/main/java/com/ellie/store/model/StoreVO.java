package com.ellie.store.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.DynamicUpdate;

import com.ellie.member.model.MemberVO;

import java.util.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;


@Entity
@Table(name = "store")
public class StoreVO {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeID", nullable = false, updatable = false)
    private Integer storeID;

	@NotEmpty(message = "店家帳號:不可為空")
	@Column(name = "storeAcc")
	private String storeAcc;

	@NotEmpty(message = "店家密碼:不可為空")
	@Column(name = "storePwd")
	private String storePwd;

	@NotEmpty(message = "店家名稱:不可為空")
	@Column(name = "storeName")
	private String storeName;

	@NotEmpty(message = "店家地址:不可為空")
	@Column(name = "storeAddr")
	private String storeAddr;

//	@NotEmpty(message =  "店家地圖:不可為空")
	@Column(name = "storeMap", updatable = false)
	private String storeMap;

	@Column(name = "storePhone")
	private String storePhone;

	@Column(name = "storeDes")
	private String storeDes;

	@Column(name = "storePic", columnDefinition="MEDIUMBLOB")
	private byte[] storePic;

	@Column(name = "storeCups")
	private Integer storeCups;


	@Column(name = "storeOpenTime")
	private Time storeOpenTime;


	@Column(name = "storeCloseTime")
	private Time storeCloseTime;


	@Column(name = "storeCreateDate",updatable = false, insertable = false )
	private Date storeCreateDate;

//	@NotEmpty(message = "不可為空")
	@Column(name = "createdByMemberID")
	private Integer createdByMemberID;

	@Column(name = "editedByMemberID")
	private Integer editedByMemberID;

	@Column(name = "storeUpdateTime")
	private Timestamp storeUpdateTime;

	@ManyToOne
	@JoinColumn(name = "createdByMemberID", insertable = false, updatable = false)
	private StoreVO createdByMember;


	@ManyToOne
	@JoinColumn(name = "editedByMemberID", insertable = false, updatable = false)
	private StoreVO editedByMember;

	public Integer getStoreID() {
		return storeID;
	}

	public void setStoreID(Integer storeID) {
		this.storeID = storeID;
	}

	public String getStoreAcc() {
		return storeAcc;
	}

	public void setStoreAcc(String storeAcc) {
		this.storeAcc = storeAcc;
	}

	public String getStorePwd() {
		return storePwd;
	}

	public void setStorePwd(String storePwd) {
		this.storePwd = storePwd;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreAddr() {
		return storeAddr;
	}

	public void setStoreAddr(String storeAddr) {
		this.storeAddr = storeAddr;
	}

	public String getStoreMap() {
		return storeMap;
	}

	public void setStoreMap(String storeMap) {
		this.storeMap = storeMap;
	}

	public String getStorePhone() {
		return storePhone;
	}

	public void setStorePhone(String storePhone) {
		this.storePhone = storePhone;
	}

	public String getStoreDes() {
		return storeDes;
	}

	public void setStoreDes(String storeDes) {
		this.storeDes = storeDes;
	}

	public byte[] getStorePic() {
		return storePic;
	}

	public void setStorePic(byte[] storePic) {
		this.storePic = storePic;
	}

	public Integer getStoreCups() {
		return storeCups;
	}

	public void setStoreCups(Integer storeCups) {
		this.storeCups = storeCups;
	}

	public Time getStoreOpenTime() {
		return storeOpenTime;
	}

	public void setStoreOpenTime(Time storeOpenTime) {
		this.storeOpenTime = storeOpenTime;
	}

	public Time getStoreCloseTime() {
		return storeCloseTime;
	}

	public void setStoreCloseTime(Time storeCloseTime) {
		this.storeCloseTime = storeCloseTime;
	}

	public Date getStoreCreateDate() {
		return storeCreateDate;
	}

	public void setStoreCreateDate(Date storeCreateDate) {
		this.storeCreateDate = storeCreateDate;
	}

	public Integer getCreatedByMemberID() {
		return createdByMemberID;
	}

	public void setCreatedByMemberID(Integer createdByMemberID) {
		this.createdByMemberID = createdByMemberID;
	}

	public Integer getEditedByMemberID() {
		return editedByMemberID;
	}

	public void setEditedByMemberID(Integer editedByMemberID) {
		this.editedByMemberID = editedByMemberID;
	}

	public Timestamp getStoreUpdateTime() {
		return storeUpdateTime;
	}

	public void setStoreUpdateTime(Timestamp storeUpdateTime) {
		this.storeUpdateTime = storeUpdateTime;
	}

	public StoreVO getCreatedByMember() {
		return createdByMember;
	}

	public void setCreatedByMember(StoreVO createdByMember) {
		this.createdByMember = createdByMember;
	}

	public StoreVO getEditedByMember() {
		return editedByMember;
	}

	public void setEditedByMember(StoreVO editedByMember) {
		this.editedByMember = editedByMember;
	}


	@Override
	public String toString() {
		return "StoreVO [storeID=" + storeID + ", storeAcc=" + storeAcc + ", storePwd=" + storePwd + ", storeName="
				+ storeName + ", storeAddr=" + storeAddr + ", storeMap=" + storeMap + ", storePhone=" + storePhone
				+ ", storeDes=" + storeDes + ", storePic=" + Arrays.toString(storePic) + ", storeCups=" + storeCups
				+ ", storeOpenTime=" + storeOpenTime + ", storeCloseTime=" + storeCloseTime + ", storeCreateDate="
				+ storeCreateDate + ", createdByMemberID=" + createdByMemberID + ", editedByMemberID="
				+ editedByMemberID + ", storeUpdateTime=" + storeUpdateTime +" ]";
	}

}
