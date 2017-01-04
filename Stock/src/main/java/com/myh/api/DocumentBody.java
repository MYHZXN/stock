package com.myh.api;

import java.util.Map;

import com.myh.domain.Good;

public class DocumentBody {

	private String type;
	private Good[] goods;
	private int[] nums;
	private String user;
	private String document;
	private String remark;
	
	public DocumentBody() {
		super();
	}
	
	public DocumentBody(String type, Good[] goods, int[] nums, String user,
			String document, String remark) {
		super();
		this.type = type;
		this.goods = goods;
		this.nums = nums;
		this.user = user;
		this.document = document;
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Good[] getGoods() {
		return goods;
	}

	public void setGoods(Good[] goods) {
		this.goods = goods;
	}

	public int[] getNums() {
		return nums;
	}

	public void setNums(int[] nums) {
		this.nums = nums;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
