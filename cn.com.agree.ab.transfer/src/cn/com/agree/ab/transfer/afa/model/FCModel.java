package cn.com.agree.ab.transfer.afa.model;

import java.util.ArrayList;
import java.util.List;

public class FCModel {

	private String uuid;
	private String createDate;
	private String modifyDate;
	
	private List<StepModel> stepModels = new ArrayList<StepModel>();

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public List<StepModel> getStepModels() {
		return stepModels;
	}

	public void addStepModel(StepModel stepModel) {
		this.stepModels.add(stepModel);
	}

	
}
