package com.yicheng6.sso.app.model;

import java.util.Date;

public class ReceiveMessage {

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
	
	public class AuthToken {

		private String username;

		private Date createAt;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public Date getCreateAt() {
			return createAt;
		}

		public void setCreateAt(Date createAt) {
			this.createAt = createAt;
		}
	}
}
