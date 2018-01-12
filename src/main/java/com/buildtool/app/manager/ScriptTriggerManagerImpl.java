package com.buildtool.app.manager;

import org.springframework.stereotype.Service;

import com.buildtool.app.exception.BuildToolWebException;
import com.buildtool.app.request.entity.ScriptTriggerRequest;
import com.buildtool.app.response.entity.ScriptTriggerResponse;
import com.buildtool.app.utils.BuildMachineSpecificConfigFactory;
import com.buildtool.app.utils.ProjectUtils;
import com.buildtool.app.utils.ServerConstants;
import com.buildtool.app.web.entity.MachineConfig;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Service
public class ScriptTriggerManagerImpl implements ScriptTriggerManager {

	@Override
	public ScriptTriggerResponse triggerScript(ScriptTriggerRequest scriptTriggerRequest) throws BuildToolWebException {
		validateScriptTriggerRequest(scriptTriggerRequest);
		String password = ProjectUtils.decodeBase64EncodedPassword(scriptTriggerRequest.getPassword());
		Session session = null;
		System.out.println("==================== triggering script============");
		try {
			session = ProjectUtils.getSession(scriptTriggerRequest.getHost(), scriptTriggerRequest.getUserName(),
					password);
			MachineConfig config = BuildMachineSpecificConfigFactory
					.getConfigForBuildMachine(scriptTriggerRequest.getHost());
			Boolean isTriggered = connectChannel(session, scriptTriggerRequest, config);
			System.out.println("====================script triggered============");
			ScriptTriggerResponse response = triggerScriptResponseAdapter(isTriggered);
			return response;
		} catch (JSchException e) {
			e.printStackTrace();
			throw new BuildToolWebException(e.getMessage());
		} finally {
			if (session != null) {
				session.disconnect();
			}
		}
	}

	private void validateScriptTriggerRequest(ScriptTriggerRequest scriptTriggerRequest) throws BuildToolWebException {
		if (scriptTriggerRequest == null || scriptTriggerRequest.getAction() == null
				|| scriptTriggerRequest.getRelease() == null || scriptTriggerRequest.getPassword() == null
				|| scriptTriggerRequest.getUserName() == null || scriptTriggerRequest.getHost() == null) {
			throw new BuildToolWebException("bad request");
		}

	}

	private Boolean connectChannel(Session session, ScriptTriggerRequest request, MachineConfig config)
			throws JSchException, BuildToolWebException {
		Boolean isConnected = false;
		if (session != null) {
			ChannelExec channelExec = (ChannelExec) session
					.openChannel(ServerConstants.TRIGGER_SCRIPT_CHANNEL.getServerConstant());
			channelExec.setCommand("sh" + " " + config.getScripts().getTriggerScript() + " " + request.getRelease()
					+ " " + request.getAction());
			channelExec.connect();
			int exitStatus = channelExec.getExitStatus();
			System.out.println("============" + exitStatus + "================");
			if (exitStatus > 0) {
				throw new BuildToolWebException("script triggerinbg failed");
			}
			isConnected = true;
		}
		return isConnected;
	}
	

	private ScriptTriggerResponse triggerScriptResponseAdapter(Boolean isTriggered) throws BuildToolWebException {
		ScriptTriggerResponse scriptTriggerResponse = new ScriptTriggerResponse();
		if (isTriggered) {
			scriptTriggerResponse.setMessage("script triggered");
			scriptTriggerResponse.setResponseCode(200);
			scriptTriggerResponse.setResponseMessage("triggered");
		} else {
			throw new BuildToolWebException("script not triggered");
		}
		return scriptTriggerResponse;
	}

}
