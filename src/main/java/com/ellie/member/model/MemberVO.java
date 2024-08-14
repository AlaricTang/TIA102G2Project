package com.ellie.member.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.sql.Timestamp;
import java.util.Arrays;

@Entity
@Table(name = "member")
public class MemberVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "memberID", nullable = false, updatable = false)
	private Integer memberID;

	@NotEmpty(message = "員工帳號:不可為空")
	@Column(name = "memberAcc")
	private String memberAcc;
	
	@NotEmpty(message = "員工密碼:不可為空")
	@Column(name = "memberPwd")
	private String memberPwd;

	@NotEmpty(message = "員工姓名:不可為空")
	@Column(name = "memberName")
	private String memberName;

	@Column(name = "memberEmail")
	private String memberEmail;

	@Column(name = "memberGender")
	private Integer memberGender;

	@Column(name = "memberPic", columnDefinition="MEDIUMBLOB")
	private byte[] memberPic;

	
	@Column(name = "memberCreateDate", updatable = false, insertable = false)
	private Timestamp memberCreateDate;

//	@NotEmpty(message = "不可為空")
	@Column(name = "createdByMemberID")
	private Integer createdByMemberID;

	@Column(name = "editedByMemberID")
	private Integer editedByMemberID;

	@ManyToOne
	@JoinColumn(name = "createdByMemberID", insertable = false, updatable = false)
	private MemberVO createdByMember;

	@ManyToOne
	@JoinColumn(name = "editedByMemberID", insertable = false, updatable = false)
	private MemberVO editedByMember;

	
	public Integer getMemberID() {
		return memberID;
	}

	public void setMemberID(Integer memberID) {
		this.memberID = memberID;
	}

	public String getMemberAcc() {
		return memberAcc;
	}

	public void setMemberAcc(String memberAcc) {
		this.memberAcc = memberAcc;
	}

	public String getMemberPwd() {
		return memberPwd;
	}

	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public Integer getMemberGender() {
		return memberGender;
	}

	public void setMemberGender(Integer memberGender) {
		this.memberGender = memberGender;
	}

	public byte[] getMemberPic() {
		return memberPic;
	}

	public void setMemberPic(byte[] memberPic) {
		this.memberPic = memberPic;
	}

	public Timestamp getMemberCreateDate() {
		return memberCreateDate;
	}

	public void setMemberCreateDate(Timestamp memberCreateDate) {
		this.memberCreateDate = memberCreateDate;
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
		return "MemberVO [memberID=" + memberID + ", memberAcc=" + memberAcc + ", memberPwd=" + memberPwd
				+ ", memberName=" + memberName + ", memberEmail=" + memberEmail + ", memberGender=" + memberGender
				+ ", memberPic=" + Arrays.toString(memberPic) + ", memberCreateDate=" + memberCreateDate
				+ ", createdByMemberID=" + createdByMemberID + ", editedByMemberID=" + editedByMemberID
				+ ", createdByMember=" + createdByMember + ", editedByMember=" + editedByMember + "]";
	}

}
