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
 * LFC文件中Component元素
 * 
 */
public abstract class ComponentElement
{
	private String id;

	private String caption;
	
    private String name;
    
    private String showId;

    private Geometry geometry = new Geometry();

    private Map<String, List<ArgElement>> inArgs = new ConcurrentHashMap<String, List<ArgElement>>();

    private Map<String, List<ArgElement>> outArgs = new ConcurrentHashMap<String, List<ArgElement>>();

    private List<ComponentOut> out = new ArrayList<ComponentOut>();
    
    private ComponentException exception = new ComponentException();
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCaption()
    {
    	return caption;
    }

    public void setCaption(String caption)
    {
        this.caption = caption;
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getShowId()
    {
        return showId;
    }

    public void setShowId(String showId)
    {
        this.showId = showId;
    }
    
	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

    public Map<String, List<ArgElement>> getInArgs()
    {
        return inArgs;
    }

    public void addInArgs(List<ArgElement> ae)
    {
        inArgs.put("arg", ae);
    }

    public Map<String, List<ArgElement>> getOutArgs()
    {
    	return outArgs;
    }
    
    public void addOutArgs(List<ArgElement> ae)
    {
        outArgs.put("arg", ae);
    }
    
    public List<ComponentOut> getOut() {
    	return out;
    }
    
    public void addOut(ComponentOut outNextMap) {
    	this.out.add(outNextMap);
    }

	public ComponentException getException() {
		return exception;
	}

	public void setException(ComponentException exception) {
		this.exception = exception;
	}
	
}
