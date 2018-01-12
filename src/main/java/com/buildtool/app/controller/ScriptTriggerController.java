package com.buildtool.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buildtool.app.exception.BuildToolWebException;
import com.buildtool.app.manager.ScriptTriggerManager;
import com.buildtool.app.request.entity.ScriptTriggerRequest;
import com.buildtool.app.response.entity.ScriptTriggerResponse;

@Controller
public class ScriptTriggerController {

	@Autowired
	private ScriptTriggerManager scriptTriggerManager;

	@RequestMapping(value = "/script/trigger", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public <T> ResponseEntity<ScriptTriggerResponse> triggerScript(
			@RequestBody ScriptTriggerRequest scriptTriggerRequest) throws BuildToolWebException {
		ScriptTriggerResponse scriptTriggerResponse = scriptTriggerManager.triggerScript(scriptTriggerRequest);
		return new ResponseEntity<>(scriptTriggerResponse, HttpStatus.OK);
	}

}
