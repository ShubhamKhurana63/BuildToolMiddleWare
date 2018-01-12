package com.buildtool.app.manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import com.buildtool.app.exception.BuildToolWebException;
import com.buildtool.app.request.entity.Principal;
import com.buildtool.app.response.entity.LogStreamResponse;
import com.buildtool.app.utils.BuildMachineSpecificConfigFactory;
import com.buildtool.app.utils.ProjectUtils;
import com.buildtool.app.utils.ServerConstants;
import com.buildtool.app.web.entity.MachineConfig;
import com.buildtool.app.web.entity.StreamedLogsDataEntity;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

@Service
public class LogStreamManagerImpl implements LogStreamManager {

	@Override
	public LogStreamResponse fetchFile(HttpServletRequest httpServletRequest, String release, String action)
			throws BuildToolWebException {
		LogStreamResponse logStreamResponse = null;
		Principal principal = extractPrincipal(httpServletRequest);
		validateRequestParams(release, action);
		StreamedLogsDataEntity streamedLogsDataEntity = null;
		Session session = null;
		String password = ProjectUtils.decodeBase64EncodedPassword(principal.getPassword());
		try {
			session = ProjectUtils.getSession(principal.getHost(), principal.getUserName(), password);
			MachineConfig machineConfig = BuildMachineSpecificConfigFactory
					.getConfigForBuildMachine(principal.getHost());
			String directoryName = ProjectUtils.getFileName(session, release, machineConfig);
			validateFileName(directoryName);
			streamedLogsDataEntity = streamLogData(session, directoryName, release, action, machineConfig);
			logStreamResponse = getLogStreamResponse(streamedLogsDataEntity);
		} catch (JSchException | IOException e) {
			e.printStackTrace();
			throw new BuildToolWebException(e.getMessage());
		} finally {
			if (session != null) {
				session.disconnect();
			}
		}
		return logStreamResponse;
	}

	private void validateFileName(String fileName) throws BuildToolWebException {
		if (fileName == null) {
			throw new BuildToolWebException("no directory found");
		}
	}

	private void validateRequestParams(String release, String action) throws BuildToolWebException {

		if (release == null || action == null) {
			throw new BuildToolWebException("request params not found");
		}

	}

	private StreamedLogsDataEntity streamLogData(Session session, String directoryName, String release, String action,
			MachineConfig machineConfig) throws BuildToolWebException {
		Channel channel = null;
		StreamedLogsDataEntity streamedLogsDataEntity = null;
		if (session != null) {
			try {
				channel = session.openChannel(ServerConstants.LOG_STREAM_CHANNEL.getServerConstant());
				ChannelSftp channelSftp = (ChannelSftp) channel;
				channelSftp.connect();
				String directory = "/build/" + release + "/logs/" + directoryName;
				channelSftp.cd(directory);
				Boolean isInitialStreamPresent = validateStream(channelSftp, machineConfig.getFileStream().getInitial(),
						directory);
				Boolean isActionStreamPresent = validateStream(channelSftp, machineConfig.getFileStream().getAction(),
						directory);
				Boolean isPostActionStreamPresent = validateStream(channelSftp,
						machineConfig.getFileStream().getPostAction(), directory);
				String initalStreamString = getStreamString(machineConfig, channelSftp, isInitialStreamPresent);
				String actionStreamString = getStreamString(machineConfig, channelSftp, isActionStreamPresent);
				String imageStreamString = getStreamString(machineConfig, channelSftp, isPostActionStreamPresent);
				streamedLogsDataEntity = prepareStreamedLogsDataObject(initalStreamString, actionStreamString,
						imageStreamString);
			} catch (JSchException | SftpException | IOException e) {
				e.printStackTrace();
				throw new BuildToolWebException(e.getMessage());
			} finally {
				if (channel != null) {
					channel.disconnect();
				}
			}
		} else {
			throw new BuildToolWebException("session not available");
		}
		return streamedLogsDataEntity;
	}

	private String getStreamString(MachineConfig machineConfig, ChannelSftp channelSftp, Boolean isInitialStreamPresent)
			throws SftpException, IOException {
		InputStream initialStream;
		StringWriter initialStreamWriter = null;
		if (isInitialStreamPresent) {
			initialStream = channelSftp.get(machineConfig.getFileStream().getInitial());
			initialStreamWriter = new StringWriter();
			IOUtils.copy(initialStream, initialStreamWriter, "UTF-8");
			return initialStreamWriter.toString();
		}
		return null;
	}

	private Boolean validateStream(ChannelSftp channelSftp, String initial, String directory) {
		;
		Boolean isPresent = true;
		try {
			SftpATTRS sftpAttrs = channelSftp.stat(directory + initial);
		} catch (SftpException e) {
			if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
				isPresent = false;
				return isPresent;
			}
		}
		return isPresent;
	}

	private StreamedLogsDataEntity prepareStreamedLogsDataObject(String initalStreamString, String actionStreamString,
			String imageStreamString) {
		StreamedLogsDataEntity streamedLogsDataEntity = new StreamedLogsDataEntity();
		streamedLogsDataEntity.setPrepareStreamLogs(initalStreamString);
		streamedLogsDataEntity.setErrorStreamLogs(actionStreamString);
		streamedLogsDataEntity.setImageStreamLogs(imageStreamString);
		return streamedLogsDataEntity;
	}

	private Principal extractPrincipal(HttpServletRequest httpServletRequest) {
		Principal principal = new Principal();
		principal.setHost(httpServletRequest.getHeader("hostMachine"));
		principal.setPassword(httpServletRequest.getHeader("password"));
		principal.setUserName(httpServletRequest.getHeader("userName"));
		return principal;
	}

	private LogStreamResponse getLogStreamResponse(StreamedLogsDataEntity response) {
		LogStreamResponse logStreamResponse = new LogStreamResponse();
		logStreamResponse.setResponseCode(200);
		logStreamResponse.setResponseMessage("logs fetched");
		logStreamResponse.setActionLogString(response.getErrorStreamLogs());
		logStreamResponse.setImageLogString(response.getImageStreamLogs());
		logStreamResponse.setPrepareTreeLogString(response.getPrepareStreamLogs());
		return logStreamResponse;
	}

}
