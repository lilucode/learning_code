package com.tojson.pojo;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
public class InArgs {
    private List<Arg> inarg;
	public List<Arg> getArg() {
		return inarg;
	}
	public void setArg(List<Arg> arg) {
		this.inarg = arg;
	}
	@Override
	public String toString() {
		return "InArgs [inarg=" + inarg + "]";
	}
	

}