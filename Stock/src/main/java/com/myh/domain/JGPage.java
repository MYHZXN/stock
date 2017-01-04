package com.myh.domain;

import java.util.List;

public class JGPage<T> {
	private Long records;
	private int page;
	private int total;
	private List<T> rows;
	
	public JGPage() {
		super();
	}

	public JGPage(Long records, int page, int total, List<T> rows) {
		super();
		this.records = records;
		this.page = page;
		this.total = total;
		this.rows = rows;
	}

	public Long getRecords() {
		return records;
	}

	public void setRecords(Long records) {
		this.records = records;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	
	
}
