package cn.com.agree.afa.compiler.model;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import cn.com.agree.ab.a5.runtime.lfc.LogicFlowControl;

public class BCModel {
	private String name; // Component/RefImpl
	private String desc; // Component/Desp
	private String auth; // Component/Auth
	private String debugMode;
	private boolean isDebugComplie;
	private String aspectUsed;
	private Set<NodeModel> nodeModels; // Node
	private List<ComponentArg> inputArgs; // Component/InArgs
	private List<ComponentArg> outputArgs; // Component/OutArgs
	private InfoLogContext logContext = new InfoLogContext();


	public List<ComponentArg> getInputArgs() {
		return inputArgs;
	}

	public void setInputArgs(List<ComponentArg> inputArgs) {
		this.inputArgs = inputArgs;
	}

	public List<ComponentArg> getOutputArgs() {
		return outputArgs;
	}

	public void setOutputArgs(List<ComponentArg> outputArgs) {
		this.outputArgs = outputArgs;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getAuth() {
		return this.auth;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setNodeModels(Set<NodeModel> nodeModels) {
		this.nodeModels = new TreeSet<>(nodeModels);
	}

	public Set<NodeModel> getNodeModels() {
		return this.nodeModels;
	}

	public String getDebugMode() {
		return this.debugMode;
	}

	public void setDebugMode(String debugMode) {
		this.debugMode = debugMode;
	}

	public boolean isDebugComplie() {
		return this.isDebugComplie;
	}

	public void setDebugComplie(boolean isDebugComplie) {
		this.isDebugComplie = isDebugComplie;
	}

	public String getAspectUsed() {
		return this.aspectUsed;
	}

	public void setAspectUsed(String aspectUsed) {
		this.aspectUsed = aspectUsed;
	}

	public int getInfoLogLevel() {
		return this.logContext.getInfoLogLevel();
	}

	public void setInfoLogLevel(int infoLogLevel) {
		this.logContext.setInfoLogLevel(infoLogLevel);
	}

	public int getInfoLogMode() {
		return this.logContext.getInfoLogMode();
	}

	public void setInfoLogMode(int infoLogMode) {
		this.logContext.setInfoLogMode(infoLogMode);
	}

	@Override
	public String toString() {
		return "[BCModel] name:" + name + ",desc:" + desc + ",auth:" + auth
				+ ",debugMode:" + debugMode + ",isDebugComplie:"
				+ isDebugComplie + ",aspectUsed:" + aspectUsed + ",nodeModels:"
				+ nodeModels + ",inputArgs:" + inputArgs + ",outputArgs:"
				+ outputArgs;
	}
}
