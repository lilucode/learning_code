package cn.com.agree.ab.transfer.afa.model;

import java.util.List;

public class BStepModel extends StepModel {
	private String cptName;			//<RefImpl>
	private List<Arg> inputArgs;	//InArgs
	private List<Arg> outputArgs;	//OutArgs

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
