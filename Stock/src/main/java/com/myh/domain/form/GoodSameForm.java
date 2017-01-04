package com.myh.domain.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class GoodSameForm {

	@NotEmpty(message = "不能为空")
	@Size(max = 30, message = "长度不能大于30")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
