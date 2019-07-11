package com.tojson.pojo;
import java.util.List;
public class OutArgs {
    private List<Arg> arg; 
    public void setArg(List<Arg> arg) {
		this.arg = arg;
	} 
	public List<Arg> getArg() {
		return arg;
	}
	@Override
	public String toString() {
		return "OutArgs [arg=" + arg + "]";
	}
	
}