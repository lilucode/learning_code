package com.tojson.pojo;

import java.util.List;

public class Lfc {
	private String id;
	private String caption;
	private String name;
	private String showId;
	private Geometry geometry;
	private InArgs inArgs;
	private OutArgs outArgs;
	private List<Out> out;
	private String mappingPath;
	private List<String> mappings;
	private List<Ade> ades;
	private String lfcPath;
	private FileDescription fileDescription;
	private Exception exception;
	
	public List<Ade> getAdes() {
		return ades;
	}
	public void setAdes(List<Ade> ades) {
		this.ades = ades;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShowId() {
		return showId;
	}
	public void setShowId(String showId) {
		this.showId = showId;
	}
	public Geometry getGeometry() {
		return geometry;
	}
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	public InArgs getInArgs() {
		return inArgs;
	}
	public void setInArgs(InArgs inArgs) {
		this.inArgs = inArgs;
	}
	public OutArgs getOutArgs() {
		return outArgs;
	}
	public void setOutArgs(OutArgs outArgs) {
		this.outArgs = outArgs;
	}
	public List<Out> getOut() {
		return out;
	}
	public void setOut(List<Out> out) {
		this.out = out;
	}
	public String getMappingPath() {
		return mappingPath;
	}
	public void setMappingPath(String mappingPath) {
		this.mappingPath = mappingPath;
	}
	public List<String> getMappings() {
		return mappings;
	}
	public void setMappings(List<String> mappings) {
		this.mappings = mappings;
	}
	public String getLfcPath() {
		return lfcPath;
	}
	public void setLfcPath(String lfcPath) {
		this.lfcPath = lfcPath;
	}
	public FileDescription getFileDescription() {
		return fileDescription;
	}
	public void setFileDescription(FileDescription fileDescription) {
		this.fileDescription = fileDescription;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	@Override
	public String toString() {
		return "Lfc [id=" + id + ", caption=" + caption + ", name=" + name + ", showId=" + showId + ", geometry="
				+ geometry + ", inArgs=" + inArgs + ", outArgs=" + outArgs + ", out=" + out + ", mappingPath="
				+ mappingPath + ", mappings=" + mappings + ", ades=" + ades + ", lfcPath=" + lfcPath
				+ ", fileDescription=" + fileDescription + ", exception=" + exception + "]";
	}
	
}
