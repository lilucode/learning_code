/**
 * Copyright 赞同科技.
 * All rights reserved.
 */
package cn.com.agree.ab.a5.runtime.lfc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author PuYun &lt;pu.yun@agree.com.cn&gt; 2014年3月11日 下午4:24:19
 * 
 */
@SuppressWarnings("rawtypes")
public class LogicFlowControl implements IDncContainer {

	private String start = "";

	private String caption = "";

	private Geometry geometry = new Geometry();

	private FileDescription fileDescription = new FileDescription();

	private DataNameCollection dnc;

	private Map<String, List<ArgElement>> inArgMap = new ConcurrentHashMap<String, List<ArgElement>>();

	private Map<String, List<ArgElement>> outArgMap = new ConcurrentHashMap<String, List<ArgElement>>();

	private Endstep endstep = new Endstep();

	private List<Map<String,String>> end = new ArrayList<Map<String,String>>();

	private List<ComponentElement> lfc = new ArrayList<ComponentElement>();

	private List csd = new ArrayList();

	private List<ComponentElement> component = new ArrayList<ComponentElement>();

	public List<Map<String, String>> getEnd() {
		return end;
	}

	public void setEnd(String id) {
		Map<String,String> map=new ConcurrentHashMap<String, String>();
		map.put("caption", "正常出口");
		map.put("name", "正常出口");
		map.put("id", id);
		end.add(map);
	}

	public Endstep getEndstep() {
		return endstep;
	}

	public void setEndstep(Endstep endstep) {
		this.endstep = endstep;
	}

	public List getCsd() {
		return csd;
	}

	public void setCsd(List csd) {
		this.csd = csd;
	}

	private VarMap varMap;

	public List<ComponentElement> getComponent() {
		return component;
	}

	public void addComponent(ComponentElement component) {
		this.component.add(component);
	}

	public List<ComponentElement> getLfc() {
		return lfc;
	}

	public void addLfc(ComponentElement lfc) {
		this.lfc.add(lfc);
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public FileDescription getFileDescription() {
		return fileDescription;
	}

	public void setFileDescription(FileDescription fileDescription) {
		this.fileDescription = fileDescription;
	}

	public LogicFlowControl() {
	}

	public VarMap getVarMap() {
		return varMap;
	}

	public void addVarMap(String expression, Object value) {
		if (varMap == null) {
			varMap = new VarMap();
		}
		varMap.put(expression, value);
	}

	public void setDnc(DataNameCollection dnc) {
		this.dnc = dnc;
	}

	@Override
	public DataNameCollection getDnc() {
		return dnc;
	}

	@Override
	public DataNameCollection getDnc(String path) {
		return dnc.getDnc(path);
	}

	@Override
	public boolean supportDnc(String path) {
		return dnc.supportDnc(path);
	}

	public Map<String, List<ArgElement>> getInArgMap() {
		return inArgMap;
	}

	/**
	 * @return the outArgMap
	 */
	public Map<String, List<ArgElement>> getOutArgMap() {
		return outArgMap;
	}

	public void addInArg(List<ArgElement> ae) {
		inArgMap.put("arg", ae);
	}

	public void addOutArg(List<ArgElement> ae) {
		outArgMap.put("arg", ae);
	}

}
