package com.tojson.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.alibaba.fastjson.annotation.JSONField;
@XmlRootElement
//@XmlType(propOrder = { "caption" , "start" , "geometry" , "fileDescription" , "dataBasket" , "inArgs" , "outArgs" , "internalVars" , "endstep" , "end" , "lfc" , "csd" , "component"})
public class JsonRootBean {
	@JSONField(ordinal = 1,name ="start")
	private String start;
	@JSONField(ordinal = 2)
	private String caption;
	@JSONField(ordinal = 3)
	private Geometry geometry;
	@JSONField(ordinal = 4)
	private FileDescription fileDescription;
	@JSONField(ordinal = 5)
	private DataBasket dataBasket;
	@JSONField(ordinal = 6)
	private InArgs inArgs;
	@JSONField(ordinal = 7)
	private OutArgs outArgs;
	@JSONField(ordinal = 8)
	private InternalVars internalVars;
	@JSONField(ordinal = 9)
	private Endstep endstep;
	@JSONField(ordinal = 10)
	private List<End> end;
	@JSONField(ordinal = 11)
	private List<Component> component;
	@JSONField(ordinal = 12)
	private List<Csd> csd;
	@JSONField(ordinal = 13)
	private List<Lfc> lfc;
	@JSONField(ordinal = 14)
	@XmlElement(name = "start")
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	@XmlElement(name = "caption")
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	@XmlElement(name = "geometry")
	public Geometry getGeometry() {
		return geometry;
	}
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	@XmlElement(name = "fileDescription")
	public FileDescription getFileDescription() {
		return fileDescription;
	}
	public void setFileDescription(FileDescription fileDescription) {
		this.fileDescription = fileDescription;
	}

	@XmlElement(name = "dataBasket")
	public DataBasket getDataBasket() {
		return dataBasket;
	}
	public void setDataBasket(DataBasket dataBasket) {
		this.dataBasket = dataBasket;
	}
	@XmlElement(name = "inArgs")
	public InArgs getInArgs() {
		return inArgs;
	}
	public void setInArgs(InArgs inArgs) {
		this.inArgs = inArgs;
	}
	@XmlElement(name = "outArgs")
	public OutArgs getOutArgs() {
		return outArgs;
	}
	public void setOutArgs(OutArgs outArgs) {
		this.outArgs = outArgs;
	}
	@XmlElement(name = "internalVars")
	public InternalVars getInternalVars() {
		return internalVars;
	}
	public void setInternalVars(InternalVars internalVars) {
		this.internalVars = internalVars;
	}
	@XmlElement(name = "endstep")
	public Endstep getEndstep() {
		return endstep;
	}
	public void setEndstep(Endstep endstep) {
		this.endstep = endstep;
	}
	@XmlElementWrapper(name="ends")  
    @XmlElement(name="end") 
	public List<End> getEnd() {
		return end;
	}
	public void setEnd(List<End> end) {
		this.end = end;
	}
	@XmlElementWrapper(name="components")  
    @XmlElement(name="component") 
	public List<Component> getComponent() {
		return component;
	}
	public void setComponent(List<Component> component) {
		this.component = component;
	}

	@XmlElementWrapper(name="csds")  
    @XmlElement(name="csd") 
	public List<Csd> getCsd() {
		return csd;
	}
	public void setCsd(List<Csd> csd) {
		this.csd = csd;
	}
	@XmlElementWrapper(name="lfcs")  
    @XmlElement(name="lfc") 
	public List<Lfc> getLfc() {
		return lfc;
	}
	public void setLfc(List<Lfc> lfc) {
		this.lfc = lfc;
	}
	@Override
	public String toString() {
		return "JsonRootBean [start=" + start + ", caption=" + caption + ", geometry=" + geometry + ", fileDescription="
				+ fileDescription + ", dataBasket=" + dataBasket + ", inArgs=" + inArgs + ", outArgs=" + outArgs
				+ ", internalVars=" + internalVars + ", endstep=" + endstep + ", end=" + end + ", component="
				+ component + ", csd=" + csd + ", lfc=" + lfc + "]";
	}
	
}