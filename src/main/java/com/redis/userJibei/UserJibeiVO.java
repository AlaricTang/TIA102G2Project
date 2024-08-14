package com.redis.userJibei;

public class UserJibeiVO {
	private Integer drinkID;
	
	private String drinkDes;

	private String drinkName;
	
	private Integer number;
	
	private byte[] drinkPic;


	public UserJibeiVO() {
		super();
	}
	
	public UserJibeiVO(Integer drinkID, Integer number) {
		this.drinkID = drinkID;
		this.number = number;
	}

	public Integer getDrinkID() {
		return drinkID;
	}

	public void setDrinkID(Integer drinkID) {
		this.drinkID = drinkID;
	}
	
	public String getDrinkDes() {
		return drinkDes;
	}

	public void setDrinkDes(String drinkDes) {
		this.drinkDes = drinkDes;
	}

	public String getDrinkName() {
		return drinkName;
	}

	public void setDrinkName(String drinkName) {
		this.drinkName = drinkName;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	

	public byte[] getDrinkPic() {
		return drinkPic;
	}

	public void setDrinkPic(byte[] drinkPic) {
		this.drinkPic = drinkPic;
	}
}
