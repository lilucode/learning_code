package com.tojson.pojo;
import java.util.List;
public class InternalVars {
    private List<InOutArg> arg;
    public void setArg(List<InOutArg> arg) {
         this.arg = arg;
     }
     public List<InOutArg> getArg() {
         return arg;
     }
	@Override
	public String toString() {
		return "InternalVars [arg=" + arg + "]";
	}
     
}