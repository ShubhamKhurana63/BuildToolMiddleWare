package com.buildtool.app.request.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

 public class Principal {

	@JsonProperty("host")
	private String host;
	@JsonProperty("userName")
	private String userName;
	@JsonProperty("password")
	private String password;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
