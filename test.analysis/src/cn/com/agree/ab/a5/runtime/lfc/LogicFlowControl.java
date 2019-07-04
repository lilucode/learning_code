/**
 * Copyright 赞同科技.
 * All rights reserved.
 */
package cn.com.agree.ab.a5.runtime.lfc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.annotation.JSONType;

@SuppressWarnings("rawtypes")
@JSONType(orders={"start","caption","geometry","fileDescription","dataBasket","inArgs","outArgs","internalVars","endstep","end","lfc","csd","component"})
public class LogicFlowControl // implements IDncContainer
{
	private int start;

	private String caption = "";

	private Geometry geometry = new Geometry();

	private FileDescription fileDescription = new FileDescription();

	private DataNameCollection dataBasket = new DataNameCollection();

	private Map<String, List<ArgElement>> inArgs = new ConcurrentHashMap<String, List<ArgElement>>();

	private Map<String, List<ArgElement>> outArgs = new ConcurrentHashMap<String, List<ArgElement>>();

	private InternalVar internalVars = new InternalVar();

	private Endstep endstep = new Endstep();

	private List<Map<String, String>> end = new ArrayList<Map<String, String>>();

	private List<ComponentElement> lfc = new ArrayList<ComponentElement>();

	private List csd = new ArrayList();

	private List<ComponentElement> component = new ArrayList<ComponentElement>();

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
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

	public DataNameCollection getDataBasket() {
		return dataBasket;
	}

	public void setDataBasket(DataNameCollection dataBasket) {
		this.dataBasket = dataBasket;
	}

	// @Override
	// public DataNameCollection getDnc(String path) {
	// return dnc.getDnc(path);
	// }
	//
	// @Override
	// public boolean supportDnc(String path) {
	// return dnc.supportDnc(path);
	// }

	public Map<String, List<ArgElement>> getInArgs() {
		return inArgs;
	}

	public void addInArgs(List<ArgElement> ae) {
		inArgs.put("arg", ae);
	}

	public Map<String, List<ArgElement>> getOutArgs() {
		return outArgs;
	}

	public void addOutArgs(List<ArgElement> ae) {
		outArgs.put("arg", ae);
	}

	public InternalVar getInternalVars() {
		return internalVars;
	}

	public void setInternalVars(InternalVar internalVars) {
		this.internalVars = internalVars;
	}

	public Endstep getEndstep() {
		return endstep;
	}

	public void setEndstep(Endstep endstep) {
		this.endstep = endstep;
	}

	public List<Map<String, String>> getEnd() {
		return end;
	}

	public void setEnd(String id) {
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		map.put("caption", "正常出口");
		map.put("name", "正常出口");
		map.put("id", id);
		end.add(map);
	}

	public List<ComponentElement> getLfc() {
		return lfc;
	}

	public void addLfc(ComponentElement lfc) {
		this.lfc.add(lfc);
	}

	public List getCsd() {
		return csd;
	}

	public void setCsd(List csd) {
		this.csd = csd;
	}

	public List<ComponentElement> getComponent() {
		return component;
	}

	public void addComponent(ComponentElement component) {
		this.component.add(component);
	}

	public LogicFlowControl() {
	}

}
