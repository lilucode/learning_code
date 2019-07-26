package com.tojson.pojo;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
public class Component {
	@JSONField(ordinal = 1)
    private String id;
	@JSONField(ordinal = 2)
    private String caption;
	@JSONField(ordinal = 3)
    private String name;
	@JSONField(ordinal = 4)
    private String showId;
	@JSONField(ordinal = 5)
    private Geometry geometry;
	@JSONField(ordinal = 6)
    private InArgs inArgs;
	@JSONField(ordinal = 7)
    private OutArgs outArgs;
	@JSONField(ordinal = 8)
    private List<Out> out;
	@JSONField(ordinal = 9)
    private Exception exception;
    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

    public void setCaption(String caption) {
         this.caption = caption;
     }
     public String getCaption() {
         return caption;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setShowId(String showId) {
         this.showId = showId;
     }
     public String getShowId() {
         return showId;
     }

    public void setGeometry(Geometry geometry) {
         this.geometry = geometry;
     }
     public Geometry getGeometry() {
         return geometry;
     }

    public void setInArgs(InArgs inArgs) {
         this.inArgs = inArgs;
     }
     public InArgs getInArgs() {
         return inArgs;
     }

    public void setOutArgs(OutArgs outArgs) {
         this.outArgs = outArgs;
     }
     public OutArgs getOutArgs() {
         return outArgs;
     }

    public void setOut(List<Out> out) {
         this.out = out;
     }
     public List<Out> getOut() {
         return out;
     }

    public void setException(Exception exception) {
         this.exception = exception;
     }
     public Exception getException() {
         return exception;
     }
	@Override
	public String toString() {
		return "Component [id=" + id + ", caption=" + caption + ", name=" + name + ", showId=" + showId + ", geometry="
				+ geometry + ", inArgs=" + inArgs + ", outArgs=" + outArgs + ", out=" + out + ", exception=" + exception
				+ "]";
	}
     
}