package com.buildtool.app.request.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScriptTriggerRequest extends Principal {
	@JsonProperty("release")
	private String release;
	@JsonProperty("action")
	private String action;

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
