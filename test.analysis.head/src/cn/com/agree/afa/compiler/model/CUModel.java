package cn.com.agree.afa.compiler.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class CUModel {
	protected static final String SUFFIX_JAVA = ".java";
	protected static final String SUFFIX_JAR = ".jar";
	private String outputPath;
	private Map<String, TradeModel> innerTrades;

	public String getOutputPath() {
		return this.outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public abstract String getClassName();

	public abstract String getClassPackageName();

	public abstract File getJarFile();

	public abstract File getJavaFile();

	public Map<String, TradeModel> getInnerTrades() {
		return this.innerTrades;
	}

	public void addInnerTrades(String id, TradeModel model) {
		if (this.innerTrades == null) {
			this.innerTrades = new HashMap<>();
		}
		this.innerTrades.put(id, model);
	}
}
