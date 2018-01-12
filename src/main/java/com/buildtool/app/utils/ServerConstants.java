package com.buildtool.app.utils;

public enum ServerConstants {

	REMOTE_SERVER_HOST("172.20.152.44"), REMOTE_USERNAME("root"), REMOTE_SERVER_PORT("22"), REMOTE_PASSWORD(
			"g0vmware"), TRIGGER_SCRIPT_CHANNEL("exec"), PRINCIPAL_HEADER("principal"), LOG_STREAM_CHANNEL(
					"sftp");

	private String serverConstantValue;

	private ServerConstants(String value) {
		this.serverConstantValue = value;

	}

	public String getServerConstant() {
		return serverConstantValue;
	}

	public void setServerConstant(String serverConstant) {
		this.serverConstantValue = serverConstant;
	}

}
