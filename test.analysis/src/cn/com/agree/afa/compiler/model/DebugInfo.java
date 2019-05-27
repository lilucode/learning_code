package cn.com.agree.afa.compiler.model;

import java.util.ArrayList;
import java.util.List;

public class DebugInfo {
	private boolean debug;
	private List<String> codes = new ArrayList<>();
	private int status;
	private String outputArgs;

	public boolean isDebug() {
		return this.debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public List<String> getCodes() {
		return this.codes;
	}

	public void addCode(String code) {
		this.codes.add(code);
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOutputArgs() {
		return this.outputArgs;
	}

	public void setOutputArgs(String outputArgs) {
		this.outputArgs = outputArgs;
	}

	@Override
	public String toString() {
		return "[DebugInfo] debug:" + debug + ",codes:" + codes + ",status:" + status + ",outputArgs:" + outputArgs;
	}
}
