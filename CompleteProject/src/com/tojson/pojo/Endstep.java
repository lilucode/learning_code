package com.tojson.pojo;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
public class Endstep {
	@JSONField(ordinal = 1)
    private String id;
	@JSONField(ordinal = 2)
    private Geometry geometry;
	@JSONField(ordinal = 3)
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