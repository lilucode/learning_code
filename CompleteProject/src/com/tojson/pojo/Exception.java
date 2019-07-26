package com.tojson.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class Exception {
	@JSONField(ordinal = 1)
    private String name;
	@JSONField(ordinal = 2)
    private String next;
    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setNext(String next) {
         this.next = next;
     }
     public String getNext() {
         return next;
     }
	@Override
	public String toString() {
		return "Exception [name=" + name + ", next=" + next + "]";
	}
     
}