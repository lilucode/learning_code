package com.tojson.pojo;

public class FileDescription {

    private String author;
    private String function;
    private String remark;
    public void setAuthor(String author) {
         this.author = author;
     }
     public String getAuthor() {
         return author;
     }

    public void setFunction(String function) {
         this.function = function;
     }
     public String getFunction() {
         return function;
     }

    public void setRemark(String remark) {
         this.remark = remark;
     }
     public String getRemark() {
         return remark;
     }
	@Override
	public String toString() {
		return "FileDescription [author=" + author + ", function=" + function + ", remark=" + remark + "]";
	}

}