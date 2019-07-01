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
 * @author PuYun &lt;pu.yun@agree.com.cn&gt; 2014年3月12日 下午2:10:10
 * 
 */
public abstract class ComponentElement
{
    private String caption;

    private String description;
    
    private String id;
    
    private String showId;

    private Map<String, List<ArgElement>> inArgMap = new ConcurrentHashMap<String, List<ArgElement>>();

    private Map<String, List<ArgElement>> outArgMap = new ConcurrentHashMap<String, List<ArgElement>>();

    private List<ComponentOut> out = new ArrayList<ComponentOut>();
    
    private Map<String, String> exception = new ConcurrentHashMap<String, String>();
    
    private String name;

    private Geometry geometry = new Geometry();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public Map<String, String> getException() {
		return exception;
	}

	public List<ComponentOut> getOut() {
		return out;
	}

	public void addOut(ComponentOut outNextMap) {
		this.out.add(outNextMap);
	}

    public void addInArg(List<ArgElement> ae)
    {
        inArgMap.put("arg", ae);
    }

    public void addOutArg(List<ArgElement> ae)
    {
        outArgMap.put("arg", ae);
    }

    /**
     * @return the caption
     */
    public String getCaption()
    {
        return caption;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }


    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @return the inArgMap
     */
    public Map<String, List<ArgElement>> getInArgMap()
    {
        return inArgMap;
    }

    /**
     * @return the outArgMap
     */
    public Map<String, List<ArgElement>> getOutArgMap()
    {
        return outArgMap;
    }



    /**
     * @param caption
     *        the caption to set
     */
    public void setCaption(String caption)
    {
        this.caption = caption;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }


	/**
     * @param id
     *        the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the showId
     */
    public String getShowId()
    {
        return showId;
    }

    /**
     * @param showId the showId to set
     */
    public void setShowId(String showId)
    {
        this.showId = showId;
    }

}
