package com.myh.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class User {

	@Id
	@Column(length = 10)
	private String id;
	
	@Column(nullable = false, length = 30)
	private String name;

	@Column(nullable = false, length = 30)
	private String password;
	
	@Column(length = 30)
	private String role;
	
	@Column(length = 30)
	private String operate_good_type;
	
	@Column(length = 30)
	private String operate_good_id;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getOperate_good_type() {
		return operate_good_type;
	}

	public void setOperate_good_type(String operate_good_type) {
		this.operate_good_type = operate_good_type;
	}

	public String getOperate_good_id() {
		return operate_good_id;
	}

	public void setOperate_good_id(String operate_good_id) {
		this.operate_good_id = operate_good_id;
	}
	
	
	
}
