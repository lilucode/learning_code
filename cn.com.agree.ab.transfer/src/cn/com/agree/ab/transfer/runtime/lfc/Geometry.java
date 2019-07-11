package cn.com.agree.ab.transfer.runtime.lfc;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(orders = { "x", "y", "width", "height" })
public class Geometry {

	private int x;
	private int y;
	private int width;
	private int height;

	public Geometry() {

	}

	public Geometry(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
