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
public class OutStock {

	@Id
	@Column(length = 15)
	private String id;
	
	@Column(length = 30, nullable = false)
	private String type;
	
	@Column(nullable = false, length = 100)
	private String documents;
	
	@Column(nullable = false)
	private Date date;
	
	@OneToOne
	private User user;
	
	@JsonIgnore
	@OneToMany(mappedBy="outStock",fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	private List<OutStockItem> outStockItems;
	
	@Column(length = 300)
	private String remark;

	public OutStock() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OutStock(String id, String type, String documents, Date date,
			User user, List<OutStockItem> outStockItems) {
		super();
		this.id = id;
		this.type = type;
		this.documents = documents;
		this.date = date;
		this.user = user;
		this.outStockItems = outStockItems;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDocuments() {
		return documents;
	}

	public void setDocuments(String documents) {
		this.documents = documents;
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

	public List<OutStockItem> getOutStockItems() {
		return outStockItems;
	}

	public void setOutStockItems(List<OutStockItem> outStockItems) {
		this.outStockItems = outStockItems;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
}
