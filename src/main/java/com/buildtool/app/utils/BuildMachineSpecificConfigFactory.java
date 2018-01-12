package com.buildtool.app.utils;

import java.io.IOException;

import org.springframework.util.ObjectUtils;

import com.buildtool.app.exception.BuildToolWebException;
import com.buildtool.app.web.entity.BuildMachineConfigWrapper;
import com.buildtool.app.web.entity.MachineConfig;

public class BuildMachineSpecificConfigFactory {

	public static MachineConfig getConfigForBuildMachine(String hostMachine) throws BuildToolWebException {
		validateHostMachine(hostMachine);
		BuildMachineConfigWrapper buildMachineConfigWrapper;
		MachineConfig machineConfig;
		try {
			buildMachineConfigWrapper = ConfigPuller.getBuildMachinesConfig();
			validatePulledConfig(buildMachineConfigWrapper);
			machineConfig = getMachineConfigForHostMachine(buildMachineConfigWrapper, hostMachine);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BuildToolWebException("config pull failed");
		}
		return machineConfig;
	}

	private static MachineConfig getMachineConfigForHostMachine(BuildMachineConfigWrapper buildMachineConfigWrapper,
			String hostMachine) {
		MachineConfig machineConfig = buildMachineConfigWrapper.getMachineConfigList().stream()
				.filter(x -> x.getBuildMachineAddress().equals(hostMachine)).findAny().orElse(null);
		return machineConfig;
	}

	private static void validatePulledConfig(BuildMachineConfigWrapper buildMachineConfigWrapper)
			throws BuildToolWebException {
		if (buildMachineConfigWrapper == null
				|| ObjectUtils.isEmpty(buildMachineConfigWrapper.getMachineConfigList())) {
			throw new BuildToolWebException("config pull failed");
		}
	}

	private static void validateHostMachine(String hostMachine) throws BuildToolWebException {
		if (hostMachine == null) {
			throw new BuildToolWebException("hostmachine is not valid");
		}
	}
}
