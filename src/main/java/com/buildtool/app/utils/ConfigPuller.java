package com.buildtool.app.utils;

import java.io.File;
import java.io.IOException;

import com.buildtool.app.web.entity.BuildMachineConfigWrapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigPuller {

	private static BuildMachineConfigWrapper buildMachineConfigWrapper;

	private ConfigPuller() {

	}

	public static BuildMachineConfigWrapper getBuildMachinesConfig()
			throws JsonParseException, JsonMappingException, IOException {
		if (buildMachineConfigWrapper == null) {
			synchronized (ConfigPuller.class) {
				if (buildMachineConfigWrapper == null) {
					File file = new File("config/config.json");
					ObjectMapper objectMapper = new ObjectMapper();
					buildMachineConfigWrapper = objectMapper.readValue(file, BuildMachineConfigWrapper.class);
				}
			}
		}
		return buildMachineConfigWrapper;
	}

}
