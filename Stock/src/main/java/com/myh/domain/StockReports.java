package com.myh.domain;

import java.math.BigDecimal;
import java.math.BigInteger;

public class StockReports {

	private String id;
	private String name;
	private int curstock;
	private BigInteger incout;
	private BigDecimal innum;
	private BigDecimal inworth;
	private BigInteger outcout;
	private BigDecimal outnum;
	private BigDecimal outworth;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCurstock() {
		return curstock;
	}
	public void setCurstock(int curstock) {
		this.curstock = curstock;
	}
	public BigInteger getIncout() {
		return incout;
	}
	public void setIncout(BigInteger incout) {
		this.incout = incout;
	}
	public BigDecimal getInnum() {
		return innum;
	}
	public void setInnum(BigDecimal innum) {
		this.innum = innum;
	}
	public BigDecimal getInworth() {
		return inworth;
	}
	public void setInworth(BigDecimal inworth) {
		this.inworth = inworth;
	}
	public BigInteger getOutcout() {
		return outcout;
	}
	public void setOutcout(BigInteger outcout) {
		this.outcout = outcout;
	}
	public BigDecimal getOutnum() {
		return outnum;
	}
	public void setOutnum(BigDecimal outnum) {
		this.outnum = outnum;
	}
	public BigDecimal getOutworth() {
		return outworth;
	}
	public void setOutworth(BigDecimal outworth) {
		this.outworth = outworth;
	}
	@Override
	public String toString() {
		return "StockReports [id=" + id + ", name=" + name + ", incout="
				+ incout + ", innum=" + innum + ", inworth=" + inworth
				+ ", outcout=" + outcout + ", outnum=" + outnum + ", outworth="
				+ outworth + "]";
	}

	
}
