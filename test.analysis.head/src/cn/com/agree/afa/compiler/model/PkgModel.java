package cn.com.agree.afa.compiler.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PkgModel extends CUModel {
	private String name;
	private String desc;
	private List<BCModel> bcModels = new ArrayList<>();

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<BCModel> getBCModels() {
		return this.bcModels;
	}

	public void addBCModel(BCModel bcModel) {
		this.bcModels.add(bcModel);
	}

	public String getClassName() {
		return this.name;
	}

	public String getClassPackageName() {
		return "bc";
	}

	public File getJarFile() {
		return new File(getOutputPath(), this.name + ".jar");
	}

	public File getJavaFile() {
		return new File(getOutputPath(), getClassName() + ".java");
	}
	
	@Override
	public String toString() {
		return "[PkgModel] name:" + name + ",desc:" + desc + ",bcModels:" + bcModels.toString();
	}
	
}
