package com.seckill.pojo;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String mobile;
	private String nickName;
	private String password;
	private String salt;
	private String head;
	@Override
	public String toString() {
		return "User [id=" + id + ", mobile=" + mobile + ", nickName=" + nickName + ", password=" + password + ", salt="
				+ salt + ", head=" + head + ", refisterDate=" + refisterDate + ", loginCount=" + loginCount + "]";
	}
	private Date refisterDate;
	private int loginCount;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public Date getRefisterDate() {
		return refisterDate;
	}
	public void setRefisterDate(Date refisterDate) {
		this.refisterDate = refisterDate;
	}
	public int getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
	

}





