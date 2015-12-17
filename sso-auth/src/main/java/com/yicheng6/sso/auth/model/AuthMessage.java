package com.yicheng6.sso.auth.model;

public class AuthMessage {
	
	private String code;
	
	private AuthToken token;
	
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public AuthToken getToken() {
		return token;
	}

	public void setToken(AuthToken token) {
		this.token = token;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
