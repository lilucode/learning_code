package com.tojson.pojo;
public class Arg {

    private String name;
    private String caption;
    private String description;
    private String example;
    private String editor;
    private String type;
    private String value;
    private String required;
    private String contains;
    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setCaption(String caption) {
         this.caption = caption;
     }
     public String getCaption() {
         return caption;
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

    public void setEditor(String editor) {
         this.editor = editor;
     }
     public String getEditor() {
         return editor;
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

    public void setRequired(String required) {
         this.required = required;
     }
     public String getRequired() {
         return required;
     }

    public void setContains(String contains) {
         this.contains = contains;
     }
     public String getContains() {
         return contains;
     }
	@Override
	public String toString() {
		return "Arg [name=" + name + ", caption=" + caption + ", description=" + description + ", example=" + example
				+ ", editor=" + editor + ", type=" + type + ", value=" + value + ", required=" + required
				+ ", contains=" + contains + "]";
	}

}