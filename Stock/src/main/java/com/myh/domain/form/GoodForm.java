package com.myh.domain.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class GoodForm {

	@Size(max = 15,message = "长度不能大于15")
	private String id;
	
	@NotEmpty(message = "不能为空")
	@Size(min = 12, max = 12, message = "长度为12")
	private String barcode;
	
	@NotEmpty(message = "不能为空")
	@Size(max = 30, message = "长度不能大于30")
	private String name;
	
	@Size(max = 50, message = "长度不能大于50")
	private String model;
	
	private int type;
	
	@Size(max = 50, message = "长度不能大于50")
	private String manufacturer;
	
	private int measure_unit;
	
	private float in_price;
	private float out_price;
	private int cur_stock;
	
	private int min_stock;
	private int max_stock;
	private String remark;
	private String expand_1;
	private String expand_2;
	private String expand_3;
	
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
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public int getMeasure_unit() {
		return measure_unit;
	}
	public void setMeasure_unit(int measure_unit) {
		this.measure_unit = measure_unit;
	}
	public float getIn_price() {
		return in_price;
	}
	public void setIn_price(float in_price) {
		this.in_price = in_price;
	}
	public float getOut_price() {
		return out_price;
	}
	public void setOut_price(float out_price) {
		this.out_price = out_price;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	
}
