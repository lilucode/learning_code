package cn.com.agree.afa.compiler.model;

public class ComponentArg {

	private String key = "";
	private String defValue = "";
	private String desp = "";
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDefValue() {
		return defValue;
	}
	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}
	public String getDesp() {
		return desp;
	}
	public void setDesp(String desp) {
		this.desp = desp;
	}

	@Override
	public String toString() {
		return "[ComponentArg] key:" + key + ",defValue:" + defValue + ",desp:" + desp;
	}
	
}
