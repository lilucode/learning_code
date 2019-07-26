package com.tojson.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class InOutArg {
	@JSONField(ordinal = 1)
    private String name;
	@JSONField(ordinal = 2)
    private String type;
	@JSONField(ordinal = 3)
    private String description;
	@JSONField(ordinal = 4)
    private String example;
	@JSONField(ordinal = 5)
    private String value;
    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }
    public void setDescription(String description) {
         this.description = description;
     }
     public String getDescription() {
         return description;
     }

    public void setExample(String example) {
         this.example = example;
     }
     public String getExample() {
         return example;
     }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setValue(String value) {
         this.value = value;
     }
     public String getValue() {
         return value;
     }

}