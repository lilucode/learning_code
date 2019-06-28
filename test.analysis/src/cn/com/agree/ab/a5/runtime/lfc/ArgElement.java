/**
 * Copyright 赞同科技.
 * All rights reserved.
 */
package cn.com.agree.ab.a5.runtime.lfc;

/**
 * LFC文件中的InArg元素或OutArg元素
 * 
 * @author PuYun &lt;pu.yun@agree.com.cn&gt; 2014年3月12日 下午2:12:06
 * 
 */
public class ArgElement
{
    private String caption;

    private String content;
    
    private String name;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    /**
     * @return the caption
     */
    public String getCaption()
    {
        return caption;
    }

    /**
     * @return the content
     */
    public String getContent()
    {
        return content;
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
     * @param content
     *        the content to set
     */
    public void setContent(String content)
    {
        this.content = content;
    }

}
