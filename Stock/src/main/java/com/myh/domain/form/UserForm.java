package com.myh.domain.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class UserForm {

	@Size(max = 10,message = "长度不能大于10")
	private String id;
	
	@NotEmpty(message = "不能为空")
	private String name;
	
	@NotEmpty(message = "不能为空")
	private String password;
	
	private String operate_good_type;
	private String operate_good_id;
	
	@NotEmpty(message = "不能为空")
	private String role;
	
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
