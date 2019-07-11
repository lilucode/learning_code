package cn.com.agree.ab.transfer.afa.model;

/**
 * 出入参
 *
 */
public class Arg {
	
	private String key = "";	// Component/Implementation/Node/InArgs/Arg/Key
	private String name = "";	// Component/Implementation/Node/InArgs/Arg/Name
	private String type = "";	// Component/Implementation/Node/InArgs/Arg/Type
	private String arg = "";	// Component/Implementation/Node/InArgs/Arg/Arg
	private String value = "";
	private int id;
	private int logLevel = 4;

	public Arg copy() {
		Arg arg = new Arg();
		arg.id = this.id;
		arg.key = this.key;
		arg.value = this.value;
		arg.logLevel = this.logLevel;
		arg.type = this.type;
		return arg;
	}

	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getArg() {
		return arg;
	}


	public void setArg(String arg) {
		this.arg = arg;
	}


	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getLogLevel() {
		return this.logLevel;
	}

	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "[Arg] id:" + id + ",key:" + key + ",logLevel:" + logLevel + ",value:" + value + ",type:" + type;
	}
}
