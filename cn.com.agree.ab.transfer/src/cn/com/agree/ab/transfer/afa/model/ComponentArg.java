package cn.com.agree.ab.transfer.afa.model;

public class ComponentArg {

	private String key = "";	// Component/InArgs/Arg/Key
	private String defValue = "";	// Component/InArgs/Arg/DefValue
	private String desp = "";	// Component/InArgs/Arg/Desp

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
