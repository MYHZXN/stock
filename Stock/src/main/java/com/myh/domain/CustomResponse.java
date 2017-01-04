package com.myh.domain;

public class CustomResponse {

	private String code;
	private String msg;
	private Object obj;
	
	public CustomResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomResponse(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	
	
}
