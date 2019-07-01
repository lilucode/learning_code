/**
 * Copyright 赞同科技.
 * All rights reserved.
 */
package cn.com.agree.ab.a5.runtime.lfc;

/**
 * @author PuYun &lt;pu.yun@agree.com.cn&gt; 2014年5月12日 上午11:27:10
 * 
 */
public class LogicletComponentElement extends ComponentElement
{
    private String name;

    private Geometry geometry = new Geometry();

    
    public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	/**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *        the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }
}
