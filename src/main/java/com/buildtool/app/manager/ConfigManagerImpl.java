package com.buildtool.app.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.buildtool.app.exception.BuildToolWebException;
import com.buildtool.app.response.entity.ConfigResponse;
import com.buildtool.app.web.entity.BuildMachineConfigWrapper;
import com.buildtool.app.web.entity.MachineConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ConfigManagerImpl implements ConfigManager {

	@Override
	public ConfigResponse getConfigForBuild(String buildMachine) throws BuildToolWebException {
		validateBuildMachine(buildMachine);
		MachineConfig machineConfig = readConfig(buildMachine);
		validateMachineConfig(machineConfig);
		ConfigResponse configResponse = getConfigResponseFromMachineConfig(machineConfig);
		return configResponse;
	}

	private ConfigResponse getConfigResponseFromMachineConfig(MachineConfig config) {
		List<String> actionList = new ArrayList<>();
		actionList.addAll(config.getActions());
		List<String> releaseList = new ArrayList<>();
		releaseList.addAll(config.getRelease());
		ConfigResponse configResponse = new ConfigResponse();
		configResponse.setActionList(actionList);
		configResponse.setReleaseList(releaseList);
		configResponse.setResponseCode(200);
		configResponse.setResponseMessage("configuration fecthed");
		return configResponse;
	}

	private void validateMachineConfig(MachineConfig machineConfig) throws BuildToolWebException {
		if (machineConfig == null) {
			throw new BuildToolWebException("configuration not found");
		}
	}

	private MachineConfig readConfig(String buildMachine) throws BuildToolWebException {
		MachineConfig machineConfig = null;
		try {
			File file = new File("config/config.json");
			ObjectMapper objectMapper = new ObjectMapper();
			BuildMachineConfigWrapper buildMachineConfigWrapper = objectMapper.readValue(file,
					BuildMachineConfigWrapper.class);
			if (buildMachineConfigWrapper != null) {
				List<MachineConfig> machineConfigList = buildMachineConfigWrapper.getMachineConfigList();
				machineConfig = machineConfigList.stream().filter(x -> buildMachine.equals(x.getBuildMachineAddress()))
						.findAny().orElse(null);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new BuildToolWebException("unable to read config file");
		}
		return machineConfig;
	}

	private void validateBuildMachine(String buildMachine) throws BuildToolWebException {
		if (buildMachine == null) {
			throw new BuildToolWebException("build machine not valid");
		}
	}

}
