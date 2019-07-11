package cn.com.agree.ab.transfer.afa.model;

import java.util.Map;
import java.util.TreeMap;

public class StepModel {
	private String id;		//<Id>
	private String desc;	//<Desp>
	private int type;	//<Type>
	private String filePath;	//<FilePath>
	//位置
	private String location; 	// <Location>
	private String size; 		// <Size>
	//出口
	private String trueId;		//<True>
	private String falseId;		//<False>
	
	private boolean skip;
	private int defaultStatus;
	private String parentTradeName;
	private String simpleParentName;
	private boolean isNoop;
	private Map<String, Integer> stepRelations = new TreeMap<>();
	public static final String DEFAULT_STRING = "";
//	private InfoLogContext logContext = new InfoLogContext();
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getTrueId() {
		return trueId;
	}

	public void setTrueId(String trueId) {
		this.trueId = trueId;
	}

	public String getFalseId() {
		return falseId;
	}

	public void setFalseId(String falseId) {
		this.falseId = falseId;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
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

//	public int getInfoLogLevel() {
//		return this.logContext.getInfoLogLevel();
//	}
//
//	public void setInfoLogLevel(int infoLogLevel) {
//		this.logContext.setInfoLogLevel(infoLogLevel);
//	}
//
//	public int getInfoLogMode() {
//		return this.logContext.getInfoLogMode();
//	}
//
//	public void setInfoLogMode(int infoLogMode) {
//		this.logContext.setInfoLogMode(infoLogMode);
//	}

	@Override
	public String toString() {
		return "[StepModel] id:" + id + ",desc:" + desc + ",skip:" + skip + ",defaultStatus:" + defaultStatus + ",parentTradeName:"
				+ parentTradeName + ",simpleParentName:" + simpleParentName + ",isNoop:" + isNoop + ",stepRelations:"
				+ stepRelations;
	}

}
