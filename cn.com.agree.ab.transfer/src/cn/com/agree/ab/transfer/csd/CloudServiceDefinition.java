package cn.com.agree.ab.transfer.csd;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(orders = { "caption", "descripsion", "serviceMetadata", "request", "response" })
public class CloudServiceDefinition {

	private String caption = "";

	private String descripsion = "";

	private ServiceMetadata serviceMetadata = new ServiceMetadata();

	private CSDRequest request = new CSDRequest();

	private CSDResponse response = new CSDResponse();

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getDescripsion() {
		return descripsion;
	}

	public void setDescripsion(String descripsion) {
		this.descripsion = descripsion;
	}

	public ServiceMetadata getServiceMetadata() {
		return serviceMetadata;
	}

	public void setServiceMetadata(ServiceMetadata serviceMetadata) {
		this.serviceMetadata = serviceMetadata;
	}

	public CSDRequest getRequest() {
		return request;
	}

	public void setRequest(CSDRequest request) {
		this.request = request;
	}

	public CSDResponse getResponse() {
		return response;
	}

	public void setResponse(CSDResponse response) {
		this.response = response;
	}

}
