package com.tojson.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class End {
	@JSONField(ordinal = 1)
    private String caption;
	@JSONField(ordinal = 2)
    private String name;
	@JSONField(ordinal = 3)
    private String id;
    public void setCaption(String caption) {
         this.caption = caption;
     }
     public String getCaption() {
         return caption;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }
	@Override
	public String toString() {
		return "End [caption=" + caption + ", name=" + name + ", id=" + id + "]";
	}
     
}