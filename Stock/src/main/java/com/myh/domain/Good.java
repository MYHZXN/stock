package com.myh.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Good implements Serializable{

	@Id
	@Column(length = 15)
	private String id;

	@Column(nullable = false,length = 13)
	private String barcode;
	@Column(nullable = false,length = 30)
	private String name;
	
	@Column(length = 50)
	private String model;
	
	@OneToOne
	private Type type;
	
	@Column(length = 50)
	private String manufacturer;
	
	@OneToOne
	private Unit measure_unit;
	
	@Column(nullable = false)
	private int in_price;
	
	@Column(nullable = false)
	private int out_price;
	
	@Column(length = 300)
	private String pic;
	
	@Column(nullable = false)
	private int cur_stock;
	
	@Column(nullable = false)
	private int min_stock;
	
	@Column(nullable = false)
	private int max_stock;
	
	@Column(length = 30)
	private String expand_1;
	@Column(length = 30)
	private String expand_2;
	@Column(length = 30)
	private String expand_3;
	@Column(length = 300)
	private String remark;
	
	
	public Good() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Good(String id, String barcode, String name, Unit measure_unit,
			int in_price, int out_price, int cur_stock, int min_stock,
			int max_stock) {
		super();
		this.id = id;
		this.barcode = barcode;
		this.name = name;
		this.measure_unit = measure_unit;
		this.in_price = in_price;
		this.out_price = out_price;
		this.cur_stock = cur_stock;
		this.min_stock = min_stock;
		this.max_stock = max_stock;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel(){
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer_id(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Unit getMeasure_unit() {
		return measure_unit;
	}

	public void setMeasure_unit(Unit measure_unit) {
		this.measure_unit = measure_unit;
	}

	public int getIn_price() {
		return in_price;
	}

	public void setIn_price(int in_price) {
		this.in_price = in_price;
	}

	public int getOut_price() {
		return out_price;
	}

	public void setOut_price(int out_price) {
		this.out_price = out_price;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public int getCur_stock() {
		return cur_stock;
	}

	public void setCur_stock(int cur_stock) {
		this.cur_stock = cur_stock;
	}

	public int getMin_stock() {
		return min_stock;
	}

	public void setMin_stock(int min_stock) {
		this.min_stock = min_stock;
	}

	public int getMax_stock() {
		return max_stock;
	}

	public void setMax_stock(int max_stock) {
		this.max_stock = max_stock;
	}

	public String getExpand_1() {
		return expand_1;
	}

	public void setExpand_1(String expand_1) {
		this.expand_1 = expand_1;
	}

	public String getExpand_2() {
		return expand_2;
	}

	public void setExpand_2(String expand_2) {
		this.expand_2 = expand_2;
	}

	public String getExpand_3() {
		return expand_3;
	}

	public void setExpand_3(String expand_3) {
		this.expand_3 = expand_3;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}

