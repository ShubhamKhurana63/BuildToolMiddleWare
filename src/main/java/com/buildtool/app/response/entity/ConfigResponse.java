package com.buildtool.app.response.entity;

import java.util.List;

import com.buildtool.app.utils.BaseResponse;

public class ConfigResponse extends BaseResponse {
	private List<String> releaseList;
	private List<String> actionList;

	public List<String> getReleaseList() {
		return releaseList;
	}

	public void setReleaseList(List<String> releaseList) {
		this.releaseList = releaseList;
	}

	public List<String> getActionList() {
		return actionList;
	}

	public void setActionList(List<String> actionList) {
		this.actionList = actionList;
	}

}
