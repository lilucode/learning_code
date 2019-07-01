/**
 * Copyright 赞同科技.
 * All rights reserved.
 */
package cn.com.agree.ab.a5.runtime.lfc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author PuYun &lt;pu.yun@agree.com.cn&gt; 2014年5月12日 上午11:13:09
 * 
 */
public class LfcComponentElement extends ComponentElement {
	private String path;

	private String name;

	private Map<String, String> mapping;

	private Geometry geometry = new Geometry();

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public Map<String, String> getMapping() {
		return mapping;
	}

	public void addMapping(String target, String source) {
		if (mapping == null) {
			mapping = new ConcurrentHashMap<String, String>();
		}
		mapping.put(target, source);
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
