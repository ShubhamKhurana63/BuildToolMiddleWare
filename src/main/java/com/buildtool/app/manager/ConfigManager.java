package com.buildtool.app.manager;

import com.buildtool.app.exception.BuildToolWebException;
import com.buildtool.app.response.entity.ConfigResponse;

public interface ConfigManager {

	public ConfigResponse getConfigForBuild(String buildMachine) throws BuildToolWebException;

}
