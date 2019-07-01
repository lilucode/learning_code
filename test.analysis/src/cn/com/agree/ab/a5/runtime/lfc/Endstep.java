package cn.com.agree.ab.a5.runtime.lfc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Endstep {

	private String id="1000";
	
	private Geometry geometry = new Geometry();

	private List<Map<String,String>> in=new ArrayList<Map<String,String>>();
	
	public Endstep() {
		Map<String,String> map=new ConcurrentHashMap<String, String>();
		map.put("caption", "正常出口");
		map.put("name", "正常出口");
		map.put("id", "1");
		this.in.add(map);
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public List<Map<String, String>> getIn() {
		return in;
	}

	public void setIn(List<Map<String, String>> in) {
		this.in = in;
	}
	
	
	
}
