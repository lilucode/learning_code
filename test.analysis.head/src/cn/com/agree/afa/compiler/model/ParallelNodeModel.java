package cn.com.agree.afa.compiler.model;

import java.util.HashMap;
import java.util.Map;

public class ParallelNodeModel extends NodeModel {
	private Map<String, ParallelTerminal> parallelTerminals = new HashMap<>();
	private boolean sync;
	private int outStatus;

	public int getOutStatus() {
		return this.outStatus;
	}

	public void setOutStatus(int outStatus) {
		this.outStatus = outStatus;
	}

	public Map<String, ParallelTerminal> getTerminals() {
		return this.parallelTerminals;
	}

	public void setTerminals(Map<String, ParallelTerminal> terminals) {
		this.parallelTerminals = terminals;
	}

	public boolean isSync() {
		return this.sync;
	}

	public void setSync(boolean sync) {
		this.sync = sync;
	}
	@Override
	public String toString() {
		return "[ParallelNodeModel] parallelTerminals:"+parallelTerminals+",sync:"+sync+",outStatus:"+outStatus;
	}
}
