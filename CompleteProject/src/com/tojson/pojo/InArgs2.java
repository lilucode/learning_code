package com.tojson.pojo;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
public class InArgs2 {
	@JSONField(name = "arg")
    private List<InOutArg> inOutArgs;
	public List<InOutArg> getInOutArgs() {
		return inOutArgs;
	}
	public void setInOutArgs(List<InOutArg> inOutArgs) {
		this.inOutArgs = inOutArgs;
	}
	
}