package com.praksa.KitchenBackEnd.controllers.utils;

public class RESTError {
	
	private Integer code;
	private String message;
	
	public RESTError() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public RESTError(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
