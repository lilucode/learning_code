package com.tojson.util;

public class CheckName {
	public String name(String target) {
		String finalFile = null;
		if (target.contains(".xml")) {
			finalFile = target.replace(".xml", ".json");
		}else if (target.contains(".lfc") ){
			finalFile = target.replace(".lfc", ".json");
		}else if (target.contains(".bcpt") ){
			finalFile = target.replace(".bcpt", ".json");
		}
		return finalFile;
	}
}
