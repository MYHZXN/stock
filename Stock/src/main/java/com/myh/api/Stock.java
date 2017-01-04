package com.myh.api;


public class Stock {
	
	private String id;
	private String type;
	private String date;
	private String Worth;
	private String user;
	private String documents;
	
	public Stock() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Stock(String id, String type, String date, String worth,
			String user, String documents) {
		super();
		this.id = id;
		this.type = type;
		this.date = date;
		Worth = worth;
		this.user = user;
		this.documents = documents;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWorth() {
		return Worth;
	}

	public void setWorth(String worth) {
		Worth = worth;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDocuments() {
		return documents;
	}

	public void setDocuments(String documents) {
		this.documents = documents;
	}
	
	
}
