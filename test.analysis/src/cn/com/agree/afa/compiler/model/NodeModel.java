package cn.com.agree.afa.compiler.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NodeModel implements Comparable<NodeModel> {
	private int id;
	private String desc;
	private int type;
	private String cptName;
	private boolean async;
	private int value;
	private int checkTradeExist = ExitType.DEFAULT_ERROR;
	private boolean skip;
	private int defaultStatus;
	private boolean debugMode;
	private DebugInfo debugInfo;
	private List<Arg> inputArgs;
	private List<Arg> outputArgs;
	private String aspectUsed;
	private Map<Integer, Integer> nodeRelations = new TreeMap<>();
	public static final String DEFAULT_BEGIN_DESC = "开始";
	public static final String DEFAULT_BEGIN_CPTNAME = "Begin";
	public static final String DEFAULT_NORMAL_END_DESC = "正常结束";
	public static final String DEFAULT_EXCEPTION_END_DESC = "异常结束";
	public static final String DEFAULT_END_CPTNAME = "End";
	private InfoLogContext logContext = new InfoLogContext();

	public int compareTo(NodeModel o) {
		if (this.id > o.id) {
			return 1;
		}
		return this.id == o.id ? 0 : -1;
	}

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

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCptName() {
		return this.cptName;
	}

	public void setCptName(String cptName) {
		this.cptName = cptName;
	}

	public boolean isAsync() {
		return this.async;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
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

	public void setDefaultStatus(int defaultStatus) {
		this.defaultStatus = defaultStatus;
	}

	public DebugInfo getDebugInfo() {
		return this.debugInfo;
	}

	public void setDebugInfo(DebugInfo debugInfo) {
		this.debugInfo = debugInfo;
	}

	public List<Arg> getInputArgs() {
		return this.inputArgs;
	}

	public void setInputArgs(List<Arg> inputArgs) {
		this.inputArgs = inputArgs;
	}

	public List<Arg> getOutputArgs() {
		return this.outputArgs;
	}

	public boolean isDebugMode() {
		return this.debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	public void setOutputArgs(List<Arg> outputArgs) {
		this.outputArgs = outputArgs;
	}

	public void addNextNodeId(int status, int nextNodeId) {
		if (nextNodeId > 0)
			this.nodeRelations.put(Integer.valueOf(status), Integer.valueOf(nextNodeId));
	}

	public int getNextNodeId(int status) {
		Integer nextNodeId = (Integer) this.nodeRelations.get(Integer.valueOf(status));
		return nextNodeId != null ? nextNodeId.intValue() : 0;
	}

	public Map<Integer, Integer> getNodeRelations() {
		return this.nodeRelations;
	}

	public int getCheckTradeExist() {
		return this.checkTradeExist;
	}

	public void setCheckTradeExist(int checkTradeExist) {
		this.checkTradeExist = checkTradeExist;
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
		return "[NodeModel] id:" + id + "desc:" + desc + "type:" + type + "cptName:" + cptName + "async:" + async
				+ "value:" + value + "checkTradeExist:" + checkTradeExist + "skip:" + skip + "defaultStatus:"
				+ defaultStatus + "debugMode:" + debugMode + "debugInfo:" + debugInfo + "inputArgs:" + inputArgs
				+ "outputArgs:" + outputArgs + "aspectUsed:" + aspectUsed + "nodeRelations:" + nodeRelations;
	}
}
