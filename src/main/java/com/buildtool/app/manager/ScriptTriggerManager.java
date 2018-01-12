package com.buildtool.app.manager;

import com.buildtool.app.exception.BuildToolWebException;
import com.buildtool.app.request.entity.ScriptTriggerRequest;
import com.buildtool.app.response.entity.ScriptTriggerResponse;

public interface ScriptTriggerManager {

	public ScriptTriggerResponse triggerScript(ScriptTriggerRequest scriptTriggerRequest) throws BuildToolWebException;

}
