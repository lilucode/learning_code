package cn.com.agree.ab.transfer.afa.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NodeModel implements Comparable<NodeModel> {
	private int id;
	private int type; 			// Component/Implementation/Node/Type
	private String idString; 	// Component/Implementation/Node/Id
	private String name; 		// Component/Implementation/Node/Name
	private String desc; 		// Component/Implementation/Node/Desp
	private String cptName; 	// Component/Implementation/Node/Target
	private String location; 	// Component/Implementation/Node/Constraint/Location
	private String size; 		// Component/Implementation/Node/Constraint/Size
	private List<Arg> inputArgs; 	// Component/Implementation/Node/InArgs/Arg
	private List<Arg> outputArgs; 	// Component/Implementation/Node/OutArgs/Arg
	private String filePath; 		// Component/Implementation/Node/FilePath
	private List<TerminalsMode> terminals = new ArrayList<TerminalsMode>(); 	// 出口
																				// Component/Implementation/Node/Terminals
	private String targetId; 	// Component/Implementation/Node/SourceConnections/Connection/targetId 开始id
	private String endId; 		// Component/Implementation/Node/Id 正常结束id
	private boolean async;
	private int value;
//	private int checkTradeExist = ExitType.DEFAULT_ERROR;
	private boolean skip;
	private int defaultStatus;
	private boolean debugMode;
//	private DebugInfo debugInfo;
	private String aspectUsed;
	private String terminalName;
	private String terminalDesc;
	private String targetNodeId;
	private Map<String, Integer> nodeRelations = new TreeMap<>();
	public static final String DEFAULT_BEGIN_DESC = "开始";
	public static final String DEFAULT_BEGIN_CPTNAME = "Begin";
	public static final String DEFAULT_NORMAL_END_DESC = "正常结束";
	public static final String DEFAULT_EXCEPTION_END_DESC = "异常结束";
	public static final String DEFAULT_END_CPTNAME = "End";
//	private InfoLogContext logContext = new InfoLogContext();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilePath() throws Exception{
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getTargetNodeId() {
		return targetNodeId;
	}

	public void setTargetNodeId(String targetNodeId) {
		this.targetNodeId = targetNodeId;
	}

	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public String getTerminalDesc() {
		return terminalDesc;
	}

	public void setTerminalDesc(String terminalDesc) {
		this.terminalDesc = terminalDesc;
	}

	public void setTerminals(List<TerminalsMode> terminals) {
		this.terminals = terminals;
	}

	public List<TerminalsMode> getTerminals() {
		return terminals;
	}

	public void addTerminals(TerminalsMode terminal) {
		this.terminals.add(terminal);
	}

	public int compareTo(NodeModel o) {
		if (this.id > o.id) {
			return 1;
		}
		return this.id == o.id ? 0 : -1;
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

//	public DebugInfo getDebugInfo() {
//		return this.debugInfo;
//	}
//
//	public void setDebugInfo(DebugInfo debugInfo) {
//		this.debugInfo = debugInfo;
//	}

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

//	public int getCheckTradeExist() {
//		return this.checkTradeExist;
//	}
//
//	public void setCheckTradeExist(int checkTradeExist) {
//		this.checkTradeExist = checkTradeExist;
//	}

	public String getAspectUsed() {
		return this.aspectUsed;
	}

	public void setAspectUsed(String aspectUsed) {
		this.aspectUsed = aspectUsed;
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
		return "[NodeModel] id:" + id + "desc:" + desc + "type:" + type + "cptName:" + cptName + "async:" + async
				+ "value:" + value + "checkTradeExist:" + "checkTradeExist" + "skip:" + skip + "defaultStatus:"
				+ defaultStatus + "debugMode:" + debugMode + "debugInfo:" + "debugInfo" + "inputArgs:" + inputArgs
				+ "outputArgs:" + outputArgs + "aspectUsed:" + aspectUsed + "nodeRelations:" + nodeRelations;
	}
}
