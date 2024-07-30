package com.ken.drink.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="Drink" )
public class DrinkVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "drinkID", updatable = false ,insertable = false)
	private Integer drinkID;
	
	@NotEmpty(message = "請輸入飲品名稱")
	@Column(name = "drinkName")
	private String drinkName;
	
	@NotEmpty(message = "請輸入飲品價格")
	@Column(name = "drinkPrice")
    private Integer drinkPrice;
    
	
    @Column(name = "drinkDes")
    private String drinkDes;
    
    
    @Column(name = "drinkPic", columnDefinition="MEDIUMBLOB")
    private byte[] drinkPic;
    
    @NotEmpty(message = "飲品標籤:不可為空")
    @Column(name = "drinkTag")
    private String drinkTag;
    
    @NotEmpty(message = "飲品狀態:不可為空")
    @Column(name = "drinkStatus")
    private Byte drinkStatus;  // Change Integer to Byte
    
    
    @Column(name = "drinkUpdateDate" ,insertable = false)
    private Date drinkUpdateDate;
    
    
    @NotEmpty(message="飲品建立時間，請勿留空")
    @Column(name = "drinkCreateDate", updatable = false, insertable = false)
    private Date drinkCreateDate;
    
//    @ManyToOne
//    @JoinColumn(name = "editedByMemberID")
    @Column(name = "editedByMemberID" , insertable = false)
    private Integer editedByMemberID;
    
//    @ManyToOne
//    @JoinColumn(name = "createdByMemberID")
    @Column(name = "createdByMemberID" , insertable = false)
    private Integer createdByMemberID;

	public Integer getDrinkID() {
		return drinkID;
	}

	public void setDrinkID(Integer drinkID) {
		this.drinkID = drinkID;
	}

	public String getDrinkName() {
		return drinkName;
	}

	public void setDrinkName(String drinkName) {
		this.drinkName = drinkName;
	}

	public Integer getDrinkPrice() {
		return drinkPrice;
	}

	public void setDrinkPrice(Integer drinkPrice) {
		this.drinkPrice = drinkPrice;
	}

	public String getDrinkDes() {
		return drinkDes;
	}

	public void setDrinkDes(String drinkDes) {
		this.drinkDes = drinkDes;
	}

	public byte[] getDrinkPic() {
		return drinkPic;
	}

	public void setDrinkPic(byte[] drinkPic) {
		this.drinkPic = drinkPic;
	}

	public String getDrinkTag() {
		return drinkTag;
	}

	public void setDrinkTag(String drinkTag) {
		this.drinkTag = drinkTag;
	}

	public Byte getDrinkStatus() {
		return drinkStatus;
	}

	public void setDrinkStatus(Byte drinkStatus) {
		this.drinkStatus = drinkStatus;
	}

	public Date getDrinkUpdateDate() {
		return drinkUpdateDate;
	}

	public void setDrinkUpdateDate(Date drinkUpdateDate) {
		this.drinkUpdateDate = drinkUpdateDate;
	}

	public Date getDrinkCreateDate() {
		return drinkCreateDate;
	}

	public void setDrinkCreateDate(Date drinkCreateDate) {
		this.drinkCreateDate = drinkCreateDate;
	}

	public Integer getEditedByMemberID() {
		return editedByMemberID;
	}

	public void setEditedByMemberID(Integer editedByMemberID) {
		this.editedByMemberID = editedByMemberID;
	}

	public Integer getCreatedByMemberID() {
		return createdByMemberID;
	}

	public void setCreatedByMemberID(Integer createdByMemberID) {
		this.createdByMemberID = createdByMemberID;
	}

	@Override
	public String toString() {
		return "DrinkVO [drinkID=" + drinkID + ", drinkName=" + drinkName + ", drinkPrice=" + drinkPrice + ", drinkDes="
				+ drinkDes + ", drinkTag=" + drinkTag + ", drinkStatus=" + drinkStatus + ", drinkUpdateDate="
				+ drinkUpdateDate + ", drinkCreateDate=" + drinkCreateDate + ", editedByMemberID=" + editedByMemberID
				+ ", createdByMemberID=" + createdByMemberID + "]";
	}
	
	
	
}
