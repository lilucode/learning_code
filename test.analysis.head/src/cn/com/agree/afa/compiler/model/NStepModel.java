package cn.com.agree.afa.compiler.model;

import java.util.Set;
import java.util.TreeSet;

public class NStepModel extends StepModel {
	private Set<NodeModel> nodeModels;

	public NStepModel() {
		setNoop(false);
	}

	public void setNodeModels(Set<NodeModel> nodeModels) {
		this.nodeModels = new TreeSet<>(nodeModels);
	}

	public Set<NodeModel> getNodeModels() {
		return this.nodeModels;
	}
	
	@Override
	public String toString() {
		return "[NStepModel] nodeModels:" + nodeModels;
	}
}
