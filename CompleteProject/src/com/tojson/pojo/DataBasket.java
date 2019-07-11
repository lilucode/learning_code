package com.tojson.pojo;

import java.util.List;

public class DataBasket {

	private List<Ade> ade;

	public void setAde(List<Ade> ade) {
		this.ade = ade;
	}

	public List<Ade> getAde() {
		return ade;
	}

	@Override
	public String toString() {
		return "DataBasket [ade=" + ade + "]";
	}
	
}