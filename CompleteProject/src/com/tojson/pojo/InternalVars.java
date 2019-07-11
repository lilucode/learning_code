package com.tojson.pojo;
import java.util.List;
public class InternalVars {
    private List<Arg> arg;
    public void setArg(List<Arg> arg) {
         this.arg = arg;
     }
     public List<Arg> getArg() {
         return arg;
     }
	@Override
	public String toString() {
		return "InternalVars [arg=" + arg + "]";
	}
     
}