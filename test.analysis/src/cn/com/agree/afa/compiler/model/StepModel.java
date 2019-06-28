package cn.com.agree.afa.compiler.model;

import java.util.Map;
import java.util.TreeMap;

public class StepModel {
	private int id;
	private String desc;
	private boolean skip;
	private int defaultStatus;
	private String parentTradeName;
	private String simpleParentName;
	private boolean isNoop;
	private Map<String, Integer> stepRelations = new TreeMap<>();
	public static final String DEFAULT_STRING = "";
	private InfoLogContext logContext = new InfoLogContext();

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isSkip() {
		return this.skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public int getDefaultStatus() {
		return this.defaultStatus;
	}

	public void setDefaultStatus(int status) {
		this.defaultStatus = status;
	}

	public void addNextStepId(int status, int nextStepId) {
		if (nextStepId > 0)
			this.stepRelations.put(String.valueOf(status), Integer.valueOf(nextStepId));
	}

	public Map<String, Integer> getStepRelations() {
		return this.stepRelations;
	}

	public String getParentTradeName() {
		return this.parentTradeName;
	}

	public void setParentTradeName(String tradeName) {
		this.parentTradeName = tradeName;
		int DotIndex = tradeName.lastIndexOf('.');
		if (DotIndex == -1)
			this.simpleParentName = tradeName;
		else
			this.simpleParentName = tradeName.substring(DotIndex + 1);
	}

	public String getSimpleParentName() {
		return this.simpleParentName;
	}

	public void setNoop(boolean isNoop) {
		this.isNoop = isNoop;
	}

	public boolean isNoop() {
		return this.isNoop;
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
		return "[StepModel] id:" + id + ",desc:" + desc + ",skip:" + skip + ",defaultStatus:" + defaultStatus + ",parentTradeName:"
				+ parentTradeName + ",simpleParentName:" + simpleParentName + ",isNoop:" + isNoop + ",stepRelations:"
				+ stepRelations;
	}

}
