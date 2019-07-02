/**
 * Copyright 赞同科技.
 * All rights reserved.
 */
package cn.com.agree.ab.a5.runtime.lfc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PuYun &lt;pu.yun@agree.com.cn&gt; 2014年5月12日 上午11:13:09
 * 
 */
@SuppressWarnings("rawtypes")
public class LfcComponentElement extends ComponentElement {
	private String path;
	private String mappingPath = "";
	private String lfcPath = "";
	private List mappings = new ArrayList();
	private List ades = new ArrayList();

	private FileDescription fileDescription = new FileDescription();

	public String getMappingPath() {
		return mappingPath;
	}

	public void setMappingPath(String mappingPath) {
		this.mappingPath = mappingPath;
	}

	public String getLfcPath() {
		return lfcPath;
	}

	public void setLfcPath(String lfcPath) {
		this.lfcPath = lfcPath;
	}

	public List getMappings() {
		return mappings;
	}

	public void setMappings(List mappings) {
		this.mappings = mappings;
	}

	public List getAdes() {
		return ades;
	}

	public void setAdes(List ades) {
		this.ades = ades;
	}

	public FileDescription getFileDescription() {
		return fileDescription;
	}

	public void setFileDescription(FileDescription fileDescription) {
		this.fileDescription = fileDescription;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

}
