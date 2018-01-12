package com.buildtool.app.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseResponse {

	private Integer responseCode;
	private String responseMessage;

	@JsonProperty(required = true)
	public Integer getResponseCode() {
		return responseCode;
	}

	@JsonProperty(required = true)
	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

}
