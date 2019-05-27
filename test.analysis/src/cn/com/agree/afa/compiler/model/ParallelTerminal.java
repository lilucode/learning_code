package cn.com.agree.afa.compiler.model;

import java.util.List;

public class ParallelTerminal {
	private String desc;
	private String name;
	private String resultId;
	private String timeoutInMill;
	private boolean hasResponse;
	private TradeModel tradeModel;
	private String times;
	private List<Arg> terminalInArgs;
	private String threadShared;
	private String appCode;
	private String tradeCode;
	private NodeModel asyncScenarioNode;
	private List<Arg> scenarioNodeArgs;

	public String getAppCode() {
		return this.appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getTradeCode() {
		return this.tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResultId() {
		return this.resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public TradeModel getTradeModel() {
		return this.tradeModel;
	}

	public void setTradeModel(TradeModel tradeModel) {
		this.tradeModel = tradeModel;
	}

	public String getTimeoutInMill() {
		return this.timeoutInMill;
	}

	public void setTimeoutInMill(String timeoutInMill) {
		this.timeoutInMill = timeoutInMill;
	}

	public boolean getHasResponse() {
		return this.hasResponse;
	}

	public void setHasResponse(boolean hasResponse) {
		this.hasResponse = hasResponse;
	}

	public String getTimes() {
		return this.times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public NodeModel getAsyncScenarioNode() {
		return this.asyncScenarioNode;
	}

	public void setAsyncScenarioNode(NodeModel asyncScenarioNode) {
		this.asyncScenarioNode = asyncScenarioNode;
	}

	public String getThreadShared() {
		return this.threadShared;
	}

	public void setThreadShared(String threadShared) {
		this.threadShared = threadShared;
	}

	public List<Arg> getTerminalInArgs() {
		return this.terminalInArgs;
	}

	public void setTerminalInArgs(List<Arg> terminalInArgs) {
		this.terminalInArgs = terminalInArgs;
	}

	public List<Arg> getScenarioNodeArgs() {
		return this.scenarioNodeArgs;
	}

	public void setScenarioNodeArgs(List<Arg> scenarioNodeArgs) {
		this.scenarioNodeArgs = scenarioNodeArgs;
	}

	@Override
	public String toString() {
		return "[ParallelTerminal] desc:" + desc + ",name:" + name + ",resultId:" + resultId + ",timeoutInMill:" + timeoutInMill
				+ ",hasResponse:" + hasResponse + ",tradeModel:" + tradeModel + ",times:" + times + ",terminalInArgs:"
				+ terminalInArgs + ",threadShared:" + threadShared + ",appCode:" + appCode + ",tradeCode:" + tradeCode
				+ ",asyncScenarioNode:" + asyncScenarioNode + ",scenarioNodeArgs:" + scenarioNodeArgs;
	}
}
