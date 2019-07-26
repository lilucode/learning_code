package com.tojson.pojo;

import com.alibaba.fastjson.annotation.JSONField;
public class Geometry {
	@JSONField(ordinal = 1)
    private int x;
	@JSONField(ordinal = 2)
    private int y;
	@JSONField(ordinal = 3)
    private int width;
	@JSONField(ordinal = 4)
    private int height;
   
	
     public void setX(int x) {
		this.x = x;
	}

	public int getX() {
         return x;
     }

    public void setY(int y) {
         this.y = y;
     }
     public int getY() {
         return y;
     }

    public void setWidth(int width) {
         this.width = width;
     }
     public int getWidth() {
         return width;
     }

    public void setHeight(int height) {
         this.height = height;
     }
     public int getHeight() {
         return height;
     }

	@Override
	public String toString() {
		return "Geometry [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
	}
     
}