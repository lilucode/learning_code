/**
 * Copyright 赞同科技.
 * All rights reserved.
 */
package cn.com.agree.ab.a5.runtime.lfc;

/**
 * LFC文件中的InArg元素或OutArg元素
 * 
 */
public class ArgElement {

	private String name = "";
	private String type = "";
	private String description = "";
	private String example = "";
	private String value = "";

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
