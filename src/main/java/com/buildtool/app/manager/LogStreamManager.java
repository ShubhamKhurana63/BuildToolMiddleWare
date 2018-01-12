package com.buildtool.app.manager;

import javax.servlet.http.HttpServletRequest;

import com.buildtool.app.exception.BuildToolWebException;
import com.buildtool.app.response.entity.LogStreamResponse;

public interface LogStreamManager {

	public LogStreamResponse fetchFile(HttpServletRequest httpServletRequest, String release, String action)
			throws BuildToolWebException;

}
