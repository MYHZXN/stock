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
public class InStockItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="instock_id", nullable=false)
	private InStock inStock;
	
	@OneToOne
	private Good good;
	
	@Column(nullable = false)
	private int num;
	
	@Column(nullable = false)
	private int worth;

	public InStockItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InStockItem(InStock inStock, Good good, int num, int worth) {
		super();
		this.inStock = inStock;
		this.good = good;
		this.num = num;
		this.worth = worth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InStock getInStock() {
		return inStock;
	}

	public void setInStock(InStock inStock) {
		this.inStock = inStock;
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

	public int getWorth() {
		return worth;
	}

	public void setWorth(int worth) {
		this.worth = worth;
	}
	
	@Override
	public String toString() {
		return "InStockItem [id=" + id + ", inStock=" + inStock + ", good="
				+ good + ", num=" + num + ", worth=" + worth + "]";
	}

	
}
