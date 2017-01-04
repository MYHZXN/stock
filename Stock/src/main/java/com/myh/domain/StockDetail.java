package com.myh.domain;


public class StockDetail {

	private String id;
	private String date;
	private String type;
	private String goodId;
	private String goodName;
	private int num;
	private int worth;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGoodId() {
		return goodId;
	}
	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getWorth() {
		return worth;
	}
	public void setWorth(int worth) {
		this.worth = worth;
	}
	@Override
	public String toString() {
		return "StockDetail [id=" + id + ", date=" + date + ", type=" + type
				+ ", goodId=" + goodId + ", goodName=" + goodName + ", num="
				+ num + ", worth=" + worth + "]";
	}

}
