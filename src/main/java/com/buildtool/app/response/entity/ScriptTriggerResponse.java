package com.buildtool.app.response.entity;

import org.springframework.stereotype.Component;

import com.buildtool.app.utils.BaseResponse;

@Component
public class ScriptTriggerResponse extends BaseResponse {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
