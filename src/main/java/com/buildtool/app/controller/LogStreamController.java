package com.buildtool.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.buildtool.app.exception.BuildToolWebException;
import com.buildtool.app.manager.LogStreamManager;
import com.buildtool.app.response.entity.LogStreamResponse;

@Controller
public class LogStreamController {

	@Autowired
	LogStreamManager logStreamManager;

	@RequestMapping(value = "/logs/stream", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LogStreamResponse> getStreamedLogs(HttpServletRequest httpServletRequest,
			@RequestParam("release") String release, @RequestParam("action") String action)
			throws BuildToolWebException {
		LogStreamResponse logStreamResponse = logStreamManager.fetchFile(httpServletRequest, release, action);
		return new ResponseEntity<LogStreamResponse>(logStreamResponse, HttpStatus.OK);
	}

}
