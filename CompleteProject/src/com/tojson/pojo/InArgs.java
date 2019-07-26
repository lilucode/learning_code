package com.tojson.pojo;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
public class InArgs {
	@JSONField(name = "arg")
	private List<Arg> args;
	public List<Arg> getArgs() {
		return args;
	}
	public void setArgs(List<Arg> args) {
		this.args = args;
	}
	
}