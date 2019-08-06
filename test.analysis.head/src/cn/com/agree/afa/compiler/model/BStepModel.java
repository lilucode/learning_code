package cn.com.agree.afa.compiler.model;

import java.util.List;

public class BStepModel extends StepModel {
	private String cptName;
	private List<Arg> inputArgs;
	private List<Arg> outputArgs;

	public BStepModel() {
		setNoop(false);
	}

	public String getCptName() {
		return this.cptName;
	}

	public void setCptName(String cptName) {
		this.cptName = cptName;
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

	public void setOutputArgs(List<Arg> outputArgs) {
		this.outputArgs = outputArgs;
	}
	
	@Override
	public String toString() {
		return "[BStepModel] cptName:"+cptName+",inputArgs:"+inputArgs+",outputArgs:"+outputArgs;
	}
}
