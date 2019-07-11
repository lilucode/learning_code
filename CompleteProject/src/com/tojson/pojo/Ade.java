package com.tojson.pojo;

import java.util.List;

public class Ade {
	public List<String> hList;

	public List<String> getList() {
		return hList;
	}

	public void setList(List<String> list) {
		this.hList = list;
	}

	@Override
	public String toString() {
		return "Ade [hList=" + hList + "]";
	}
	
}
