/**
 * Copyright 赞同科技.
 * All rights reserved.
 */
package cn.com.agree.ab.a5.runtime.lfc;

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
    
    private String exceptionNext;

    private String id;
    
    private String showId;

    private Map<String, ArgElement> inArgMap = new ConcurrentHashMap<String, ArgElement>();

    private Map<String, ArgElement> outArgMap = new ConcurrentHashMap<String, ArgElement>();

    private Map<String, String> outNextMap = new ConcurrentHashMap<String, String>();

    /**
     * 
     */
    public void addInArg(ArgElement ae)
    {
        inArgMap.put(ae.getName(), ae);
    }

    /**
     * 
     */
    public void addOutArg(ArgElement ae)
    {
        outArgMap.put(ae.getName(), ae);
    }

    /**
     * 
     */
    public void addOutNext(String id, String next)
    {
        outNextMap.put(id, next);
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
     * @return the exceptionNext
     */
    public String getExceptionNext()
    {
        return exceptionNext;
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
    public Map<String, ArgElement> getInArgMap()
    {
        return inArgMap;
    }

    /**
     * @return the outArgMap
     */
    public Map<String, ArgElement> getOutArgMap()
    {
        return outArgMap;
    }

    /**
     * @return the outNextMap
     */
    public Map<String, String> getOutNextMap()
    {
        return outNextMap;
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
     * @param exceptionNext
     *        the exceptionNext to set
     */
    public void setExceptionNext(String exceptionNext)
    {
        this.exceptionNext = exceptionNext;
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
