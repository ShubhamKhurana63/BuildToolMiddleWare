package com.buildtool.app.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buildtool.app.exception.BuildToolWebException;
import com.buildtool.app.manager.ConfigManager;
import com.buildtool.app.response.entity.ConfigResponse;

@Controller
public class ConfigurationController {

	@Autowired
	ConfigManager configManager;

	private static final Logger LOGGER = Logger.getLogger(ConfigurationController.class);

	@RequestMapping(value = "/config/{buildmachine}/", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<ConfigResponse> getConfig(@PathVariable("buildmachine") String buildMachine)
			throws BuildToolWebException {	
		ConfigResponse configResponse = configManager.getConfigForBuild(buildMachine);
		return new ResponseEntity<ConfigResponse>(configResponse, HttpStatus.OK);
	}

}
