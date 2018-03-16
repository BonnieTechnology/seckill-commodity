package com.seckill.pojo;

import java.io.Serializable;
import java.util.Date;

public class SeckillItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private int seckillId;
	private int seckillPrice;
	private int maxNum;
	private int itemId;
	private String itemTitle;
	private int itemPrice;
	private String itemImage;
	private Date startDate;
	private Date endDate;
	private Date createDate;
	private Date modifuedDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSeckillId() {
		return seckillId;
	}
	public void setSeckillId(int seckillId) {
		this.seckillId = seckillId;
	}
	public int getSeckillPrice() {
		return seckillPrice;
	}
	public void setSeckillPrice(int seckillPrice) {
		this.seckillPrice = seckillPrice;
	}
	public String getItemTitle() {
		return itemTitle;
	}
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
	public int getItemPrice() {
		return itemPrice;
	}
	public int getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}
	
	public String getItemImage() {
		return itemImage;
	}
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifuedDate() {
		return modifuedDate;
	}
	public void setModifuedDate(Date modifuedDate) {
		this.modifuedDate = modifuedDate;
	}
	
	
	

}
