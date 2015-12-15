package com.yicheng6.sso.auth.model;

import java.util.Date;

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
