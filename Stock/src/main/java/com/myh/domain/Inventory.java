package com.myh.domain;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Inventory {

	@Id
	@Column(length = 15)
	private String id;
	
	@Column(nullable = false)
	private Date date;
	
	@OneToOne
	private User user;
	
	@JsonIgnore
	@OneToMany(mappedBy="inventory",fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	private List<InventoryItem> inventoryItems;
	
	@Column(length = 300)
	private String remark;

	public Inventory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Inventory(String id, Date date, User user,
			List<InventoryItem> inventoryItems, String remark) {
		super();
		this.id = id;
		this.date = date;
		this.user = user;
		this.inventoryItems = inventoryItems;
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<InventoryItem> getInventoryItems() {
		return inventoryItems;
	}

	public void setInventoryItems(List<InventoryItem> inventoryItems) {
		this.inventoryItems = inventoryItems;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Inventory [id=" + id + ", date=" + date + ", user=" + user
				+ ", inventoryItems=" + inventoryItems + ", remark=" + remark
				+ "]";
	}

	
}
