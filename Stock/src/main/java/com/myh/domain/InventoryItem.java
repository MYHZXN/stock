package com.myh.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class InventoryItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="inventory_id", nullable=false)
	private Inventory inventory;
	
	@OneToOne
	private Good good;
	
	@Column(nullable = false)
	private int num;
	
	@Column(nullable = false)
	private int checknum;

	public InventoryItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InventoryItem(Inventory inventory, Good good, int num,
			int checknum) {
		super();
		this.inventory = inventory;
		this.good = good;
		this.num = num;
		this.checknum = checknum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Good getGood() {
		return good;
	}

	public void setGood(Good good) {
		this.good = good;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getChecknum() {
		return checknum;
	}

	public void setChecknum(int checknum) {
		this.checknum = checknum;
	}

	
}
