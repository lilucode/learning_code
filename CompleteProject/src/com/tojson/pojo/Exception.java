package com.tojson.pojo;
public class Exception {

    private String name;
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