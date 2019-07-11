package com.tojson.pojo;
import java.util.List;
public class Endstep {

    private String id;
    private Geometry geometry;
    private List<In> in;
    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

    public void setGeometry(Geometry geometry) {
         this.geometry = geometry;
     }
     public Geometry getGeometry() {
         return geometry;
     }

    public void setIn(List<In> in) {
         this.in = in;
     }
     public List<In> getIn() {
         return in;
     }
	@Override
	public String toString() {
		return "Endstep [id=" + id + ", geometry=" + geometry + ", in=" + in + "]";
	}

}