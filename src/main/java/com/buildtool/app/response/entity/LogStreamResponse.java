package com.buildtool.app.response.entity;

import com.buildtool.app.utils.BaseResponse;

public class LogStreamResponse extends BaseResponse {

	
	
	private String actionLogString;
	private String prepareTreeLogString;
	private String imageLogString;

	public String getActionLogString() {
		return actionLogString;
	}

	public void setActionLogString(String actionLogString) {
		this.actionLogString = actionLogString;
	}

	public String getPrepareTreeLogString() {
		return prepareTreeLogString;
	}

	public void setPrepareTreeLogString(String prepareTreeLogString) {
		this.prepareTreeLogString = prepareTreeLogString;
	}

	public String getImageLogString() {
		return imageLogString;
	}

	public void setImageLogString(String imageLogString) {
		this.imageLogString = imageLogString;
	}
	
	
	

}
