package com.myh.domain.form;

import java.util.Arrays;

public class InventoryForm {

	private String id;
	private String date;
	private String user;
	private String remark;
	private String[] goodIds;
	private String[] checknums;
	
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
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String[] getGoodIds() {
		return goodIds;
	}
	public void setGoodIds(String[] goodIds) {
		this.goodIds = goodIds;
	}
	public String[] getChecknums() {
		return checknums;
	}
	public void setChecknums(String[] checknums) {
		this.checknums = checknums;
	}
	@Override
	public String toString() {
		return "InventoryForm [id=" + id + ", date=" + date + ", user=" + user
				+ ", remark=" + remark + ", goodIds="
				+ Arrays.toString(goodIds) + ", checknums="
				+ Arrays.toString(checknums) + "]";
	}
	
}
