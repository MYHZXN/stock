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
public class OutStockItem {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="outstock_id", nullable=false)
	private OutStock outStock;
	
	@OneToOne
	private Good good;
	
	@Column(nullable = false)
	private int num;
	
	@Column(nullable = false)
	private int worth;
	
	public OutStockItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OutStockItem(OutStock outStock, Good good, int num, int worth) {
		super();
		this.outStock = outStock;
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

	public OutStock getOutStock() {
		return outStock;
	}

	public void setOutStock(OutStock outStock) {
		this.outStock = outStock;
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


}
