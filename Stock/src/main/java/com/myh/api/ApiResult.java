package com.myh.api;

public class ApiResult<T> {

	private int code;
	private String msg;
	private T object;
	
	public ApiResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApiResult(int code, String msg, T object) {
		super();
		this.code = code;
		this.msg = msg;
		this.object = object;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "ApiResult [code=" + code + ", msg=" + msg + ", object="
				+ object + "]";
	}
	
}
