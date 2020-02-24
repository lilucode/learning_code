package cn.com.agree.ab.transfer.runtime.lfc;

import java.util.ArrayList;
import java.util.List;

public class Xm {

	private String description;
	private String caption;
	private String xmName;
	private String xmCaption;
	private String isList = "false";
	private List<String> dataBasket = new ArrayList<String>();

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getXmName() {
		return xmName;
	}

	public void setXmName(String xmName) {
		this.xmName = xmName;
	}

	public String getXmCaption() {
		return xmCaption;
	}

	public void setXmCaption(String xmCaption) {
		this.xmCaption = xmCaption;
	}

	public String getIsList() {
		return isList;
	}

	public void setIsList(String isList) {
		this.isList = isList;
	}

	public List<String> getDataBasket() {
		return dataBasket;
	}

	public void setDataBasket(String str) {
		dataBasket.add(str);
	}

}
