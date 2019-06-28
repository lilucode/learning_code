package cn.com.agree.afa.compiler.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NodeModel implements Comparable<NodeModel> {
	private int id;
	private String idString;		//Component/Implementation/Node/Id
	private String desc;			//Component/Implementation/Node/Desp
	private int type;				//Component/Implementation/Node/Type
	private String cptName;			//Component/Implementation/Node/Target
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
	private String targetNodeId;	//Component/Implementation/Node/SourceConnections/Connection/targetId
	private String terminalDesc;	//Component/Implementation/Node/Terminals/Terminal/Desp
	private String terminalName;	//Component/Implementation/Node/Terminals/Terminal/Name
	private String endId;			//Component/Implementation/Node/Id
	private String targetId;		// Component/Implementation/Node/SourceConnections/Connection/targetId
	private String location;		//Component/Implementation/Node/Constraint/Location
	private String size;			//Component/Implementation/Node/Constraint/Size
	private Map<String, Integer> nodeRelations = new TreeMap<>();
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
	
	public String getTargetNodeId() {
		return targetNodeId;
	}

	public void setTargetNodeId(String targetNodeId) {
		this.targetNodeId = targetNodeId;
	}

	public String getTerminalDesc() {
		return terminalDesc;
	}

	public void setTerminalDesc(String terminalDesc) {
		this.terminalDesc = terminalDesc;
	}

	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public String getEndId() {
		return endId;
	}

	public void setEndId(String endId) {
		this.endId = endId;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
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

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdString() {
		return idString;
	}

	public void setIdString(String idString) {
		this.idString = idString;
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
			this.nodeRelations.put(String.valueOf(status), Integer.valueOf(nextNodeId));
	}

	public int getNextNodeId(int status) {
		Integer nextNodeId = (Integer) this.nodeRelations.get(String.valueOf(status));
		return nextNodeId != null ? nextNodeId.intValue() : 0;
	}

	public Map<String, Integer> getNodeRelations() {
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
