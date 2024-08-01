package com.ellie.store.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.ellie.member.model.MemberVO;

import java.sql.Date;
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
	@Column(name = "storeAcc", nullable = false)
	private String storeAcc;

	@NotEmpty(message = "店家密碼:不可為空")
	@Column(name = "storePwd", nullable = false)
	private String storePwd;

	@NotEmpty(message = "店家名稱:不可為空")
	@Column(name = "storeName", nullable = false)
	private String storeName;

	@NotEmpty(message = "店家地址:不可為空")
	@Column(name = "storeAddr", nullable = false)
	private String storeAddr;

	@Column(name = "storeMap", nullable = false)
	private String storeMap;

	@NotEmpty(message = "請輸入電話")
	@Column(name = "storePhone", nullable = false)
	private String storePhone;

	@Column(name = "storeDes")
	private String storeDes;

	@NotEmpty(message = "請上傳圖片")
	@Column(name = "storePic", columnDefinition="MEDIUMBLOB")
	private byte[] storePic;

	@Column(name = "storeCups")
	private Integer storeCups;

	@Column(name = "storeOpenTime", nullable = false)
	private Time storeOpenTime;

	@Column(name = "storeCloseTime", nullable = false)
	private Time storeCloseTime;

	@Column(name = "storeCreateDate", nullable = false)
	private Date storeCreateDate;

	@Column(name = "createdByMemberID")
	private Integer createdByMemberID;

	@Column(name = "editedByMemberID")
	private Integer editedByMemberID;

	@Column(name = "storeUpdateTime")
	private Timestamp storeUpdateTime;

	@ManyToOne
	@JoinColumn(name = "createdByMemberID", insertable = false, updatable = false)
	private MemberVO createdByMember;

	@ManyToOne
	@JoinColumn(name = "editedByMemberID", insertable = false, updatable = false)
	private MemberVO editedByMember;

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

	public MemberVO getCreatedByMember() {
		return createdByMember;
	}

	public void setCreatedByMember(MemberVO createdByMember) {
		this.createdByMember = createdByMember;
	}

	public MemberVO getEditedByMember() {
		return editedByMember;
	}

	public void setEditedByMember(MemberVO editedByMember) {
		this.editedByMember = editedByMember;
	}

	@Override
	public String toString() {
		return "StoreVO [storeID=" + storeID + ", storeAcc=" + storeAcc + ", storePwd=" + storePwd + ", storeName="
				+ storeName + ", storeAddr=" + storeAddr + ", storeMap=" + storeMap + ", storePhone=" + storePhone
				+ ", storeDes=" + storeDes + ", storePic=" + Arrays.toString(storePic) + ", storeCups=" + storeCups
				+ ", storeOpenTime=" + storeOpenTime + ", storeCloseTime=" + storeCloseTime + ", storeCreateDate="
				+ storeCreateDate + ", createdByMemberID=" + createdByMemberID + ", editedByMemberID="
				+ editedByMemberID + ", storeUpdateTime=" + storeUpdateTime + ", createdByMember=" + createdByMember
				+ ", editedByMember=" + editedByMember + "]";
	}

}
