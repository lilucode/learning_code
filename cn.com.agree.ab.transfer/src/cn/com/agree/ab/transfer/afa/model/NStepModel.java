package cn.com.agree.ab.transfer.afa.model;

import java.util.ArrayList;
import java.util.List;

public class NStepModel extends StepModel {
	private String refImpl;
	private List<NodeModel> nodeModels;

	public NStepModel() {
		setNoop(false);
	}

	public void setNodeModels(List<NodeModel>nodeModels) {
		this.nodeModels = new ArrayList<>(nodeModels);
	}

	public List<NodeModel> getNodeModels() {
		return this.nodeModels;
	}
	
	public String getRefImpl() {
		return refImpl;
	}

	public void setRefImpl(String refImpl) {
		this.refImpl = refImpl;
	}

	@Override
	public String toString() {
		return "[NStepModel] nodeModels:" + nodeModels;
	}
}
