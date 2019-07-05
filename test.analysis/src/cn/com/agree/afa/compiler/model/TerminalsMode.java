package cn.com.agree.afa.compiler.model;

public class TerminalsMode {

	private String terminalName; // Component/Implementation/Node/Terminals/Terminal/Name
	private String terminalDesc; // Component/Implementation/Node/Terminals/Terminal/Desp
	private String targetNodeId; // Component/Implementation/Node/SourceConnections/Connection/targetId

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

	public String getTargetNodeId() {
		return targetNodeId;
	}

	public void setTargetNodeId(String targetNodeId) {
		this.targetNodeId = targetNodeId;
	}

}
