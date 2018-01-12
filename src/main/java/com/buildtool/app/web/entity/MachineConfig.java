package com.buildtool.app.web.entity;

import java.util.List;

public class MachineConfig {
	private String buildMachineAddress;
	private List<String> release;
	private List<String> actions;
	private logFileStream fileStream;
	private Scripts scripts;

	public Scripts getScripts() {
		return scripts;
	}

	public void setScripts(Scripts scripts) {
		this.scripts = scripts;
	}

	public logFileStream getFileStream() {
		return fileStream;
	}

	public void setFileStream(logFileStream fileStream) {
		this.fileStream = fileStream;
	}

	public String getBuildMachineAddress() {
		return buildMachineAddress;
	}

	public void setBuildMachineAddress(String buildMachineAddress) {
		this.buildMachineAddress = buildMachineAddress;
	}

	public List<String> getRelease() {
		return release;
	}

	public void setRelease(List<String> release) {
		this.release = release;
	}

	public List<String> getActions() {
		return actions;
	}

	public void setActions(List<String> actions) {
		this.actions = actions;
	}

}
