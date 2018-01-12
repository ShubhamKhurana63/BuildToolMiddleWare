package com.buildtool.app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.bind.DatatypeConverter;

import com.buildtool.app.web.entity.MachineConfig;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ProjectUtils {

	public static String decodeBase64EncodedPassword(String password) {
		if (password != null) {
			return new String(DatatypeConverter.parseBase64Binary(password));
		}
		return null;
	}

	public static Session getSession(String host, String userName, String password)
			throws NumberFormatException, JSchException {
		Session session = getJsch().getSession(userName, host,
				Integer.parseInt(ServerConstants.REMOTE_SERVER_PORT.getServerConstant()));
		session.setConfig("StrictHostKeyChecking", "no");
		session.setPassword(password);
		session.connect();
		return session;
	}

	private static JSch getJsch() {
		return new JSch();
	}

	public static String getFileName(Session session, String release, MachineConfig machineConfig)
			throws JSchException, IOException {
		String fileName = null;
		if (session != null) {
			ChannelExec channelExec = (ChannelExec) session
					.openChannel(ServerConstants.TRIGGER_SCRIPT_CHANNEL.getServerConstant());
			channelExec.setCommand("sh" + " " + machineConfig.getScripts().getLogSearch() + " " + release);
			channelExec.connect();
			int exitStatus = channelExec.getExitStatus();
			InputStream in = channelExec.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			fileName = reader.readLine();
			System.out.println("============" + exitStatus + "================");
			if (exitStatus > 0) {
				System.out.println("Remote script exec error! " + exitStatus);
			}
		}
		return fileName;
	}

}
