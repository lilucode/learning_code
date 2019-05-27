package cn.com.agree.afa.compiler.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TradeModel extends CUModel {
	private String appCode;
	private String appDesc;
	private String tradeCode;
	private String tradeDesc;
	private String author;
	private String creationDate;
	private String modificationDate;
	private List<StepModel> stepModels = new ArrayList<>();
	private boolean executeOnWGStartup;
	private boolean executeOnServiceStartup;
	private boolean executeBeforeServiceDestroy;
	private boolean debugMode;
	private String aspectUsed;
	private String parentName;
	private String simpleParentName;
	public static final String DEFAULT_STRING = "";
	public static final int DEFAULT_INT = 0;
	public static final boolean DEFAULT_BOOLEAN = false;
	private InfoLogContext logContext = new InfoLogContext();

	public String getAppCode() {
		return this.appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppDesc() {
		return this.appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public String getTradeCode() {
		return this.tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getTradeDesc() {
		return this.tradeDesc;
	}

	public void setTradeDesc(String tradeDesc) {
		this.tradeDesc = tradeDesc;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getModificationDate() {
		return this.modificationDate;
	}

	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}

	public List<StepModel> getStepModels() {
		return this.stepModels;
	}

	public void addStepModel(StepModel stepModel) {
		this.stepModels.add(stepModel);
	}

	public String getClassName() {
		return "T" + this.tradeCode;
	}

	public String getClassPackageName() {
		return this.appCode + "." + this.tradeCode;
	}

	public File getJarFile() {
		return new File(getOutputPath(), this.appCode + "." + this.tradeCode + ".jar");
	}

	public File getJavaFile() {
		return new File(getOutputPath(), getClassName() + ".java");
	}

	public boolean isExecuteOnWGStartup() {
		return this.executeOnWGStartup;
	}

	public void setExecuteOnWGStartup(boolean executeOnWGStartup) {
		this.executeOnWGStartup = executeOnWGStartup;
	}

	public boolean isExecuteOnServiceStartup() {
		return this.executeOnServiceStartup;
	}

	public void setExecuteOnServiceStartup(boolean executeOnServiceStartup) {
		this.executeOnServiceStartup = executeOnServiceStartup;
	}

	public boolean isExecuteBeforeServiceDestroy() {
		return this.executeBeforeServiceDestroy;
	}

	public void setExecuteBeforeServiceDestroy(boolean executeBeforeServiceDestroy) {
		this.executeBeforeServiceDestroy = executeBeforeServiceDestroy;
	}

	public boolean isDebugMode() {
		return this.debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	public String getAspectUsed() {
		return (this.aspectUsed == null) || (this.aspectUsed.isEmpty()) ? "0" : this.aspectUsed;
	}

	public void setAspectUsed(String aspectUsed) {
		this.aspectUsed = aspectUsed;
	}

	public Set<Integer> getStepIds() {
		Set<Integer> ids = new HashSet<>();
		for (StepModel step : this.stepModels) {
			ids.add(Integer.valueOf(step.getId()));
		}
		return ids;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
		int DotIndex = parentName.lastIndexOf('.');
		if (DotIndex == -1)
			this.simpleParentName = parentName;
		else
			this.simpleParentName = parentName.substring(DotIndex + 1);
	}

	public String getSimpleParentName() {
		return this.simpleParentName;
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
		return "[TradeModel] appCode:" + appCode + ",appDesc:" + appDesc + ",tradeCode:" + tradeCode + ",tradeDesc:" + tradeDesc
				+ ",author:" + author + ",creationDate:" + creationDate + ",modificationDate:" + modificationDate
				+ ",stepModels:" + stepModels + ",executeOnWGStartup:" + executeOnWGStartup
				+ ",executeOnServiceStartup:" + executeOnServiceStartup + ",executeBeforeServiceDestroy:"
				+ executeBeforeServiceDestroy + ",debugMode:" + debugMode + ",aspectUsed:" + aspectUsed + ",parentName:"
				+ parentName + ",simpleParentName:" + simpleParentName;
	}
}
